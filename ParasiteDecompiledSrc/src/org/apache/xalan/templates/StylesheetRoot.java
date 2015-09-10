/*    1:     */ package org.apache.xalan.templates;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.text.DecimalFormatSymbols;
/*    5:     */ import java.util.ArrayList;
/*    6:     */ import java.util.HashMap;
/*    7:     */ import java.util.Hashtable;
/*    8:     */ import java.util.Properties;
/*    9:     */ import java.util.Vector;
/*   10:     */ import javax.xml.transform.ErrorListener;
/*   11:     */ import javax.xml.transform.Templates;
/*   12:     */ import javax.xml.transform.Transformer;
/*   13:     */ import javax.xml.transform.TransformerConfigurationException;
/*   14:     */ import javax.xml.transform.TransformerException;
/*   15:     */ import org.apache.xalan.extensions.ExtensionNamespacesManager;
/*   16:     */ import org.apache.xalan.processor.XSLTSchema;
/*   17:     */ import org.apache.xalan.res.XSLMessages;
/*   18:     */ import org.apache.xalan.transformer.TransformerImpl;
/*   19:     */ import org.apache.xml.dtm.DTM;
/*   20:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   21:     */ import org.apache.xml.utils.IntStack;
/*   22:     */ import org.apache.xml.utils.QName;
/*   23:     */ import org.apache.xpath.XPath;
/*   24:     */ import org.apache.xpath.XPathContext;
/*   25:     */ 
/*   26:     */ public class StylesheetRoot
/*   27:     */   extends StylesheetComposed
/*   28:     */   implements Serializable, Templates
/*   29:     */ {
/*   30:     */   static final long serialVersionUID = 3875353123529147855L;
/*   31:  61 */   private boolean m_optimizer = true;
/*   32:  66 */   private boolean m_incremental = false;
/*   33:  71 */   private boolean m_source_location = false;
/*   34:  76 */   private boolean m_isSecureProcessing = false;
/*   35:     */   private HashMap m_availElems;
/*   36:     */   
/*   37:     */   public StylesheetRoot(ErrorListener errorListener)
/*   38:     */     throws TransformerConfigurationException
/*   39:     */   {
/*   40:  85 */     super(null);
/*   41:     */     
/*   42:  87 */     setStylesheetRoot(this);
/*   43:     */     try
/*   44:     */     {
/*   45:  91 */       this.m_selectDefault = new XPath("node()", this, this, 0, errorListener);
/*   46:     */       
/*   47:  93 */       initDefaultRule(errorListener);
/*   48:     */     }
/*   49:     */     catch (TransformerException se)
/*   50:     */     {
/*   51:  97 */       throw new TransformerConfigurationException(XSLMessages.createMessage("ER_CANNOT_INIT_DEFAULT_TEMPLATES", null), se);
/*   52:     */     }
/*   53:     */   }
/*   54:     */   
/*   55:     */   public StylesheetRoot(XSLTSchema schema, ErrorListener listener)
/*   56:     */     throws TransformerConfigurationException
/*   57:     */   {
/*   58: 117 */     this(listener);
/*   59: 118 */     this.m_availElems = schema.getElemsAvailable();
/*   60:     */   }
/*   61:     */   
/*   62:     */   public boolean isRoot()
/*   63:     */   {
/*   64: 128 */     return true;
/*   65:     */   }
/*   66:     */   
/*   67:     */   public void setSecureProcessing(boolean flag)
/*   68:     */   {
/*   69: 136 */     this.m_isSecureProcessing = flag;
/*   70:     */   }
/*   71:     */   
/*   72:     */   public boolean isSecureProcessing()
/*   73:     */   {
/*   74: 144 */     return this.m_isSecureProcessing;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public HashMap getAvailableElements()
/*   78:     */   {
/*   79: 155 */     return this.m_availElems;
/*   80:     */   }
/*   81:     */   
/*   82: 158 */   private transient ExtensionNamespacesManager m_extNsMgr = null;
/*   83:     */   private StylesheetComposed[] m_globalImportList;
/*   84:     */   private OutputProperties m_outputProperties;
/*   85:     */   
/*   86:     */   public ExtensionNamespacesManager getExtensionNamespacesManager()
/*   87:     */   {
/*   88: 166 */     if (this.m_extNsMgr == null) {
/*   89: 167 */       this.m_extNsMgr = new ExtensionNamespacesManager();
/*   90:     */     }
/*   91: 168 */     return this.m_extNsMgr;
/*   92:     */   }
/*   93:     */   
/*   94:     */   public Vector getExtensions()
/*   95:     */   {
/*   96: 178 */     return this.m_extNsMgr != null ? this.m_extNsMgr.getExtensions() : null;
/*   97:     */   }
/*   98:     */   
/*   99:     */   public Transformer newTransformer()
/*  100:     */   {
/*  101: 200 */     return new TransformerImpl(this);
/*  102:     */   }
/*  103:     */   
/*  104:     */   public Properties getDefaultOutputProps()
/*  105:     */   {
/*  106: 206 */     return this.m_outputProperties.getProperties();
/*  107:     */   }
/*  108:     */   
/*  109:     */   public Properties getOutputProperties()
/*  110:     */   {
/*  111: 222 */     return (Properties)getDefaultOutputProps().clone();
/*  112:     */   }
/*  113:     */   
/*  114:     */   public void recompose()
/*  115:     */     throws TransformerException
/*  116:     */   {
/*  117: 242 */     Vector recomposableElements = new Vector();
/*  118: 246 */     if (null == this.m_globalImportList)
/*  119:     */     {
/*  120: 249 */       Vector importList = new Vector();
/*  121:     */       
/*  122: 251 */       addImports(this, true, importList);
/*  123:     */       
/*  124:     */ 
/*  125:     */ 
/*  126:     */ 
/*  127:     */ 
/*  128:     */ 
/*  129:     */ 
/*  130: 259 */       this.m_globalImportList = new StylesheetComposed[importList.size()];
/*  131:     */       
/*  132: 261 */       int i = 0;
/*  133: 261 */       for (int j = importList.size() - 1; i < importList.size(); i++)
/*  134:     */       {
/*  135: 263 */         this.m_globalImportList[j] = ((StylesheetComposed)importList.elementAt(i));
/*  136:     */         
/*  137:     */ 
/*  138:     */ 
/*  139: 267 */         this.m_globalImportList[j].recomposeIncludes(this.m_globalImportList[j]);
/*  140:     */         
/*  141: 269 */         this.m_globalImportList[(j--)].recomposeImports();
/*  142:     */       }
/*  143:     */     }
/*  144: 273 */     int n = getGlobalImportCount();
/*  145: 275 */     for (int i = 0; i < n; i++)
/*  146:     */     {
/*  147: 277 */       StylesheetComposed imported = getGlobalImport(i);
/*  148: 278 */       imported.recompose(recomposableElements);
/*  149:     */     }
/*  150: 283 */     QuickSort2(recomposableElements, 0, recomposableElements.size() - 1);
/*  151:     */     
/*  152:     */ 
/*  153:     */ 
/*  154:     */ 
/*  155: 288 */     this.m_outputProperties = new OutputProperties("");
/*  156:     */     
/*  157:     */ 
/*  158: 291 */     this.m_attrSets = new HashMap();
/*  159: 292 */     this.m_decimalFormatSymbols = new Hashtable();
/*  160: 293 */     this.m_keyDecls = new Vector();
/*  161: 294 */     this.m_namespaceAliasComposed = new Hashtable();
/*  162: 295 */     this.m_templateList = new TemplateList();
/*  163: 296 */     this.m_variables = new Vector();
/*  164: 302 */     for (int i = recomposableElements.size() - 1; i >= 0; i--) {
/*  165: 303 */       ((ElemTemplateElement)recomposableElements.elementAt(i)).recompose(this);
/*  166:     */     }
/*  167: 320 */     initComposeState();
/*  168:     */     
/*  169:     */ 
/*  170: 323 */     this.m_templateList.compose(this);
/*  171:     */     
/*  172:     */ 
/*  173: 326 */     this.m_outputProperties.compose(this);
/*  174: 327 */     this.m_outputProperties.endCompose(this);
/*  175:     */     
/*  176:     */ 
/*  177:     */ 
/*  178:     */ 
/*  179: 332 */     n = getGlobalImportCount();
/*  180: 334 */     for (int i = 0; i < n; i++)
/*  181:     */     {
/*  182: 336 */       StylesheetComposed imported = getGlobalImport(i);
/*  183: 337 */       int includedCount = imported.getIncludeCountComposed();
/*  184: 338 */       for (int j = -1; j < includedCount; j++)
/*  185:     */       {
/*  186: 340 */         Stylesheet included = imported.getIncludeComposed(j);
/*  187: 341 */         composeTemplates(included);
/*  188:     */       }
/*  189:     */     }
/*  190: 345 */     if (this.m_extNsMgr != null) {
/*  191: 346 */       this.m_extNsMgr.registerUnregisteredNamespaces();
/*  192:     */     }
/*  193: 348 */     clearComposeState();
/*  194:     */   }
/*  195:     */   
/*  196:     */   void composeTemplates(ElemTemplateElement templ)
/*  197:     */     throws TransformerException
/*  198:     */   {
/*  199: 361 */     templ.compose(this);
/*  200: 363 */     for (ElemTemplateElement child = templ.getFirstChildElem(); child != null; child = child.getNextSiblingElem()) {
/*  201: 366 */       composeTemplates(child);
/*  202:     */     }
/*  203: 369 */     templ.endCompose(this);
/*  204:     */   }
/*  205:     */   
/*  206:     */   protected void addImports(Stylesheet stylesheet, boolean addToList, Vector importList)
/*  207:     */   {
/*  208: 404 */     int n = stylesheet.getImportCount();
/*  209: 406 */     if (n > 0) {
/*  210: 408 */       for (int i = 0; i < n; i++)
/*  211:     */       {
/*  212: 410 */         Stylesheet imported = stylesheet.getImport(i);
/*  213:     */         
/*  214: 412 */         addImports(imported, true, importList);
/*  215:     */       }
/*  216:     */     }
/*  217: 416 */     n = stylesheet.getIncludeCount();
/*  218: 418 */     if (n > 0) {
/*  219: 420 */       for (int i = 0; i < n; i++)
/*  220:     */       {
/*  221: 422 */         Stylesheet included = stylesheet.getInclude(i);
/*  222:     */         
/*  223: 424 */         addImports(included, false, importList);
/*  224:     */       }
/*  225:     */     }
/*  226: 428 */     if (addToList) {
/*  227: 429 */       importList.addElement(stylesheet);
/*  228:     */     }
/*  229:     */   }
/*  230:     */   
/*  231:     */   public StylesheetComposed getGlobalImport(int i)
/*  232:     */   {
/*  233: 443 */     return this.m_globalImportList[i];
/*  234:     */   }
/*  235:     */   
/*  236:     */   public int getGlobalImportCount()
/*  237:     */   {
/*  238: 456 */     return this.m_globalImportList != null ? this.m_globalImportList.length : 1;
/*  239:     */   }
/*  240:     */   
/*  241:     */   public int getImportNumber(StylesheetComposed sheet)
/*  242:     */   {
/*  243: 472 */     if (this == sheet) {
/*  244: 473 */       return 0;
/*  245:     */     }
/*  246: 475 */     int n = getGlobalImportCount();
/*  247: 477 */     for (int i = 0; i < n; i++) {
/*  248: 479 */       if (sheet == getGlobalImport(i)) {
/*  249: 480 */         return i;
/*  250:     */       }
/*  251:     */     }
/*  252: 483 */     return -1;
/*  253:     */   }
/*  254:     */   
/*  255:     */   void recomposeOutput(OutputProperties oprops)
/*  256:     */     throws TransformerException
/*  257:     */   {
/*  258: 502 */     this.m_outputProperties.copyFrom(oprops);
/*  259:     */   }
/*  260:     */   
/*  261:     */   public OutputProperties getOutputComposed()
/*  262:     */   {
/*  263: 521 */     return this.m_outputProperties;
/*  264:     */   }
/*  265:     */   
/*  266: 526 */   private boolean m_outputMethodSet = false;
/*  267:     */   private HashMap m_attrSets;
/*  268:     */   private Hashtable m_decimalFormatSymbols;
/*  269:     */   private Vector m_keyDecls;
/*  270:     */   private Hashtable m_namespaceAliasComposed;
/*  271:     */   private TemplateList m_templateList;
/*  272:     */   private Vector m_variables;
/*  273:     */   private TemplateList m_whiteSpaceInfoList;
/*  274:     */   private ElemTemplate m_defaultTextRule;
/*  275:     */   private ElemTemplate m_defaultRule;
/*  276:     */   private ElemTemplate m_defaultRootRule;
/*  277:     */   private ElemTemplate m_startRule;
/*  278:     */   XPath m_selectDefault;
/*  279:     */   private transient ComposeState m_composeState;
/*  280:     */   
/*  281:     */   public boolean isOutputMethodSet()
/*  282:     */   {
/*  283: 536 */     return this.m_outputMethodSet;
/*  284:     */   }
/*  285:     */   
/*  286:     */   void recomposeAttributeSets(ElemAttributeSet attrSet)
/*  287:     */   {
/*  288: 553 */     ArrayList attrSetList = (ArrayList)this.m_attrSets.get(attrSet.getName());
/*  289: 555 */     if (null == attrSetList)
/*  290:     */     {
/*  291: 557 */       attrSetList = new ArrayList();
/*  292:     */       
/*  293: 559 */       this.m_attrSets.put(attrSet.getName(), attrSetList);
/*  294:     */     }
/*  295: 562 */     attrSetList.add(attrSet);
/*  296:     */   }
/*  297:     */   
/*  298:     */   public ArrayList getAttributeSetComposed(QName name)
/*  299:     */     throws ArrayIndexOutOfBoundsException
/*  300:     */   {
/*  301: 578 */     return (ArrayList)this.m_attrSets.get(name);
/*  302:     */   }
/*  303:     */   
/*  304:     */   void recomposeDecimalFormats(DecimalFormatProperties dfp)
/*  305:     */   {
/*  306: 594 */     DecimalFormatSymbols oldDfs = (DecimalFormatSymbols)this.m_decimalFormatSymbols.get(dfp.getName());
/*  307: 596 */     if (null == oldDfs)
/*  308:     */     {
/*  309: 598 */       this.m_decimalFormatSymbols.put(dfp.getName(), dfp.getDecimalFormatSymbols());
/*  310:     */     }
/*  311: 600 */     else if (!dfp.getDecimalFormatSymbols().equals(oldDfs))
/*  312:     */     {
/*  313:     */       String themsg;
/*  314: 603 */       if (dfp.getName().equals(new QName(""))) {
/*  315: 606 */         themsg = XSLMessages.createWarning("WG_ONE_DEFAULT_XSLDECIMALFORMAT_ALLOWED", new Object[0]);
/*  316:     */       } else {
/*  317: 613 */         themsg = XSLMessages.createWarning("WG_XSLDECIMALFORMAT_NAMES_MUST_BE_UNIQUE", new Object[] { dfp.getName() });
/*  318:     */       }
/*  319: 618 */       error(themsg);
/*  320:     */     }
/*  321:     */   }
/*  322:     */   
/*  323:     */   public DecimalFormatSymbols getDecimalFormatComposed(QName name)
/*  324:     */   {
/*  325: 640 */     return (DecimalFormatSymbols)this.m_decimalFormatSymbols.get(name);
/*  326:     */   }
/*  327:     */   
/*  328:     */   void recomposeKeys(KeyDeclaration keyDecl)
/*  329:     */   {
/*  330: 657 */     this.m_keyDecls.addElement(keyDecl);
/*  331:     */   }
/*  332:     */   
/*  333:     */   public Vector getKeysComposed()
/*  334:     */   {
/*  335: 668 */     return this.m_keyDecls;
/*  336:     */   }
/*  337:     */   
/*  338:     */   void recomposeNamespaceAliases(NamespaceAlias nsAlias)
/*  339:     */   {
/*  340: 684 */     this.m_namespaceAliasComposed.put(nsAlias.getStylesheetNamespace(), nsAlias);
/*  341:     */   }
/*  342:     */   
/*  343:     */   public NamespaceAlias getNamespaceAliasComposed(String uri)
/*  344:     */   {
/*  345: 699 */     return (NamespaceAlias)(null == this.m_namespaceAliasComposed ? null : this.m_namespaceAliasComposed.get(uri));
/*  346:     */   }
/*  347:     */   
/*  348:     */   void recomposeTemplates(ElemTemplate template)
/*  349:     */   {
/*  350: 716 */     this.m_templateList.setTemplate(template);
/*  351:     */   }
/*  352:     */   
/*  353:     */   public final TemplateList getTemplateListComposed()
/*  354:     */   {
/*  355: 727 */     return this.m_templateList;
/*  356:     */   }
/*  357:     */   
/*  358:     */   public final void setTemplateListComposed(TemplateList templateList)
/*  359:     */   {
/*  360: 739 */     this.m_templateList = templateList;
/*  361:     */   }
/*  362:     */   
/*  363:     */   public ElemTemplate getTemplateComposed(XPathContext xctxt, int targetNode, QName mode, boolean quietConflictWarnings, DTM dtm)
/*  364:     */     throws TransformerException
/*  365:     */   {
/*  366: 764 */     return this.m_templateList.getTemplate(xctxt, targetNode, mode, quietConflictWarnings, dtm);
/*  367:     */   }
/*  368:     */   
/*  369:     */   public ElemTemplate getTemplateComposed(XPathContext xctxt, int targetNode, QName mode, int maxImportLevel, int endImportLevel, boolean quietConflictWarnings, DTM dtm)
/*  370:     */     throws TransformerException
/*  371:     */   {
/*  372: 796 */     return this.m_templateList.getTemplate(xctxt, targetNode, mode, maxImportLevel, endImportLevel, quietConflictWarnings, dtm);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public ElemTemplate getTemplateComposed(QName qname)
/*  376:     */   {
/*  377: 813 */     return this.m_templateList.getTemplate(qname);
/*  378:     */   }
/*  379:     */   
/*  380:     */   void recomposeVariables(ElemVariable elemVar)
/*  381:     */   {
/*  382: 830 */     if (getVariableOrParamComposed(elemVar.getName()) == null)
/*  383:     */     {
/*  384: 832 */       elemVar.setIsTopLevel(true);
/*  385: 833 */       elemVar.setIndex(this.m_variables.size());
/*  386: 834 */       this.m_variables.addElement(elemVar);
/*  387:     */     }
/*  388:     */   }
/*  389:     */   
/*  390:     */   public ElemVariable getVariableOrParamComposed(QName qname)
/*  391:     */   {
/*  392: 848 */     if (null != this.m_variables)
/*  393:     */     {
/*  394: 850 */       int n = this.m_variables.size();
/*  395: 852 */       for (int i = 0; i < n; i++)
/*  396:     */       {
/*  397: 854 */         ElemVariable var = (ElemVariable)this.m_variables.elementAt(i);
/*  398: 855 */         if (var.getName().equals(qname)) {
/*  399: 856 */           return var;
/*  400:     */         }
/*  401:     */       }
/*  402:     */     }
/*  403: 860 */     return null;
/*  404:     */   }
/*  405:     */   
/*  406:     */   public Vector getVariablesAndParamsComposed()
/*  407:     */   {
/*  408: 871 */     return this.m_variables;
/*  409:     */   }
/*  410:     */   
/*  411:     */   void recomposeWhiteSpaceInfo(WhiteSpaceInfo wsi)
/*  412:     */   {
/*  413: 888 */     if (null == this.m_whiteSpaceInfoList) {
/*  414: 889 */       this.m_whiteSpaceInfoList = new TemplateList();
/*  415:     */     }
/*  416: 891 */     this.m_whiteSpaceInfoList.setTemplate(wsi);
/*  417:     */   }
/*  418:     */   
/*  419:     */   public boolean shouldCheckWhitespace()
/*  420:     */   {
/*  421: 903 */     return null != this.m_whiteSpaceInfoList;
/*  422:     */   }
/*  423:     */   
/*  424:     */   public WhiteSpaceInfo getWhiteSpaceInfo(XPathContext support, int targetElement, DTM dtm)
/*  425:     */     throws TransformerException
/*  426:     */   {
/*  427: 921 */     if (null != this.m_whiteSpaceInfoList) {
/*  428: 922 */       return (WhiteSpaceInfo)this.m_whiteSpaceInfoList.getTemplate(support, targetElement, null, false, dtm);
/*  429:     */     }
/*  430: 925 */     return null;
/*  431:     */   }
/*  432:     */   
/*  433:     */   public boolean shouldStripWhiteSpace(XPathContext support, int targetElement)
/*  434:     */     throws TransformerException
/*  435:     */   {
/*  436: 942 */     if (null != this.m_whiteSpaceInfoList) {
/*  437: 944 */       while (-1 != targetElement)
/*  438:     */       {
/*  439: 946 */         DTM dtm = support.getDTM(targetElement);
/*  440: 947 */         WhiteSpaceInfo info = (WhiteSpaceInfo)this.m_whiteSpaceInfoList.getTemplate(support, targetElement, null, false, dtm);
/*  441: 949 */         if (null != info) {
/*  442: 950 */           return info.getShouldStripSpace();
/*  443:     */         }
/*  444: 952 */         int parent = dtm.getParent(targetElement);
/*  445: 953 */         if ((-1 != parent) && (1 == dtm.getNodeType(parent))) {
/*  446: 954 */           targetElement = parent;
/*  447:     */         } else {
/*  448: 956 */           targetElement = -1;
/*  449:     */         }
/*  450:     */       }
/*  451:     */     }
/*  452: 959 */     return false;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public boolean canStripWhiteSpace()
/*  456:     */   {
/*  457: 970 */     return null != this.m_whiteSpaceInfoList;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public final ElemTemplate getDefaultTextRule()
/*  461:     */   {
/*  462: 991 */     return this.m_defaultTextRule;
/*  463:     */   }
/*  464:     */   
/*  465:     */   public final ElemTemplate getDefaultRule()
/*  466:     */   {
/*  467:1010 */     return this.m_defaultRule;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public final ElemTemplate getDefaultRootRule()
/*  471:     */   {
/*  472:1034 */     return this.m_defaultRootRule;
/*  473:     */   }
/*  474:     */   
/*  475:     */   public final ElemTemplate getStartRule()
/*  476:     */   {
/*  477:1052 */     return this.m_startRule;
/*  478:     */   }
/*  479:     */   
/*  480:     */   private void initDefaultRule(ErrorListener errorListener)
/*  481:     */     throws TransformerException
/*  482:     */   {
/*  483:1071 */     this.m_defaultRule = new ElemTemplate();
/*  484:     */     
/*  485:1073 */     this.m_defaultRule.setStylesheet(this);
/*  486:     */     
/*  487:1075 */     XPath defMatch = new XPath("*", this, this, 1, errorListener);
/*  488:     */     
/*  489:1077 */     this.m_defaultRule.setMatch(defMatch);
/*  490:     */     
/*  491:1079 */     ElemApplyTemplates childrenElement = new ElemApplyTemplates();
/*  492:     */     
/*  493:1081 */     childrenElement.setIsDefaultTemplate(true);
/*  494:1082 */     childrenElement.setSelect(this.m_selectDefault);
/*  495:1083 */     this.m_defaultRule.appendChild(childrenElement);
/*  496:     */     
/*  497:1085 */     this.m_startRule = this.m_defaultRule;
/*  498:     */     
/*  499:     */ 
/*  500:1088 */     this.m_defaultTextRule = new ElemTemplate();
/*  501:     */     
/*  502:1090 */     this.m_defaultTextRule.setStylesheet(this);
/*  503:     */     
/*  504:1092 */     defMatch = new XPath("text() | @*", this, this, 1, errorListener);
/*  505:     */     
/*  506:1094 */     this.m_defaultTextRule.setMatch(defMatch);
/*  507:     */     
/*  508:1096 */     ElemValueOf elemValueOf = new ElemValueOf();
/*  509:     */     
/*  510:1098 */     this.m_defaultTextRule.appendChild(elemValueOf);
/*  511:     */     
/*  512:1100 */     XPath selectPattern = new XPath(".", this, this, 0, errorListener);
/*  513:     */     
/*  514:1102 */     elemValueOf.setSelect(selectPattern);
/*  515:     */     
/*  516:     */ 
/*  517:1105 */     this.m_defaultRootRule = new ElemTemplate();
/*  518:     */     
/*  519:1107 */     this.m_defaultRootRule.setStylesheet(this);
/*  520:     */     
/*  521:1109 */     defMatch = new XPath("/", this, this, 1, errorListener);
/*  522:     */     
/*  523:1111 */     this.m_defaultRootRule.setMatch(defMatch);
/*  524:     */     
/*  525:1113 */     childrenElement = new ElemApplyTemplates();
/*  526:     */     
/*  527:1115 */     childrenElement.setIsDefaultTemplate(true);
/*  528:1116 */     this.m_defaultRootRule.appendChild(childrenElement);
/*  529:1117 */     childrenElement.setSelect(this.m_selectDefault);
/*  530:     */   }
/*  531:     */   
/*  532:     */   private void QuickSort2(Vector v, int lo0, int hi0)
/*  533:     */   {
/*  534:1141 */     int lo = lo0;
/*  535:1142 */     int hi = hi0;
/*  536:1144 */     if (hi0 > lo0)
/*  537:     */     {
/*  538:1148 */       ElemTemplateElement midNode = (ElemTemplateElement)v.elementAt((lo0 + hi0) / 2);
/*  539:1151 */       while (lo <= hi)
/*  540:     */       {
/*  541:     */         do
/*  542:     */         {
/*  543:1155 */           if (lo >= hi0) {
/*  544:     */             break;
/*  545:     */           }
/*  546:1155 */         } while (((ElemTemplateElement)v.elementAt(lo)).compareTo(midNode) < 0);
/*  547:1162 */         while ((hi > lo0) && (((ElemTemplateElement)v.elementAt(hi)).compareTo(midNode) > 0)) {
/*  548:1163 */           hi--;
/*  549:     */         }
/*  550:1167 */         if (lo <= hi)
/*  551:     */         {
/*  552:1169 */           ElemTemplateElement node = (ElemTemplateElement)v.elementAt(lo);
/*  553:1170 */           v.setElementAt(v.elementAt(hi), lo);
/*  554:1171 */           v.setElementAt(node, hi);
/*  555:     */           
/*  556:1173 */           lo++;
/*  557:1174 */           hi--;
/*  558:     */         }
/*  559:     */       }
/*  560:1180 */       if (lo0 < hi) {
/*  561:1182 */         QuickSort2(v, lo0, hi);
/*  562:     */       }
/*  563:1187 */       if (lo < hi0) {
/*  564:1189 */         QuickSort2(v, lo, hi0);
/*  565:     */       }
/*  566:     */     }
/*  567:     */   }
/*  568:     */   
/*  569:     */   void initComposeState()
/*  570:     */   {
/*  571:1201 */     this.m_composeState = new ComposeState();
/*  572:     */   }
/*  573:     */   
/*  574:     */   ComposeState getComposeState()
/*  575:     */   {
/*  576:1210 */     return this.m_composeState;
/*  577:     */   }
/*  578:     */   
/*  579:     */   private void clearComposeState()
/*  580:     */   {
/*  581:1218 */     this.m_composeState = null;
/*  582:     */   }
/*  583:     */   
/*  584:1221 */   private String m_extensionHandlerClass = "org.apache.xalan.extensions.ExtensionHandlerExsltFunction";
/*  585:     */   
/*  586:     */   public String setExtensionHandlerClass(String handlerClassName)
/*  587:     */   {
/*  588:1231 */     String oldvalue = this.m_extensionHandlerClass;
/*  589:1232 */     this.m_extensionHandlerClass = handlerClassName;
/*  590:1233 */     return oldvalue;
/*  591:     */   }
/*  592:     */   
/*  593:     */   public String getExtensionHandlerClass()
/*  594:     */   {
/*  595:1240 */     return this.m_extensionHandlerClass;
/*  596:     */   }
/*  597:     */   
/*  598:     */   class ComposeState
/*  599:     */   {
/*  600:     */     ComposeState()
/*  601:     */     {
/*  602:1250 */       int size = StylesheetRoot.this.m_variables.size();
/*  603:1251 */       for (int i = 0; i < size; i++)
/*  604:     */       {
/*  605:1253 */         ElemVariable ev = (ElemVariable)StylesheetRoot.this.m_variables.elementAt(i);
/*  606:1254 */         this.m_variableNames.addElement(ev.getName());
/*  607:     */       }
/*  608:     */     }
/*  609:     */     
/*  610:1259 */     private ExpandedNameTable m_ent = new ExpandedNameTable();
/*  611:     */     
/*  612:     */     public int getQNameID(QName qname)
/*  613:     */     {
/*  614:1272 */       return this.m_ent.getExpandedTypeID(qname.getNamespace(), qname.getLocalName(), 1);
/*  615:     */     }
/*  616:     */     
/*  617:1283 */     private Vector m_variableNames = new Vector();
/*  618:     */     
/*  619:     */     int addVariableName(QName qname)
/*  620:     */     {
/*  621:1293 */       int pos = this.m_variableNames.size();
/*  622:1294 */       this.m_variableNames.addElement(qname);
/*  623:1295 */       int frameSize = this.m_variableNames.size() - getGlobalsSize();
/*  624:1296 */       if (frameSize > this.m_maxStackFrameSize) {
/*  625:1297 */         this.m_maxStackFrameSize += 1;
/*  626:     */       }
/*  627:1298 */       return pos;
/*  628:     */     }
/*  629:     */     
/*  630:     */     void resetStackFrameSize()
/*  631:     */     {
/*  632:1303 */       this.m_maxStackFrameSize = 0;
/*  633:     */     }
/*  634:     */     
/*  635:     */     int getFrameSize()
/*  636:     */     {
/*  637:1308 */       return this.m_maxStackFrameSize;
/*  638:     */     }
/*  639:     */     
/*  640:     */     int getCurrentStackFrameSize()
/*  641:     */     {
/*  642:1318 */       return this.m_variableNames.size();
/*  643:     */     }
/*  644:     */     
/*  645:     */     void setCurrentStackFrameSize(int sz)
/*  646:     */     {
/*  647:1326 */       this.m_variableNames.setSize(sz);
/*  648:     */     }
/*  649:     */     
/*  650:     */     int getGlobalsSize()
/*  651:     */     {
/*  652:1331 */       return StylesheetRoot.this.m_variables.size();
/*  653:     */     }
/*  654:     */     
/*  655:1334 */     IntStack m_marks = new IntStack();
/*  656:     */     private int m_maxStackFrameSize;
/*  657:     */     
/*  658:     */     void pushStackMark()
/*  659:     */     {
/*  660:1338 */       this.m_marks.push(getCurrentStackFrameSize());
/*  661:     */     }
/*  662:     */     
/*  663:     */     void popStackMark()
/*  664:     */     {
/*  665:1343 */       int mark = this.m_marks.pop();
/*  666:1344 */       setCurrentStackFrameSize(mark);
/*  667:     */     }
/*  668:     */     
/*  669:     */     Vector getVariableNames()
/*  670:     */     {
/*  671:1356 */       return this.m_variableNames;
/*  672:     */     }
/*  673:     */   }
/*  674:     */   
/*  675:     */   public boolean getOptimizer()
/*  676:     */   {
/*  677:1367 */     return this.m_optimizer;
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void setOptimizer(boolean b)
/*  681:     */   {
/*  682:1374 */     this.m_optimizer = b;
/*  683:     */   }
/*  684:     */   
/*  685:     */   public boolean getIncremental()
/*  686:     */   {
/*  687:1381 */     return this.m_incremental;
/*  688:     */   }
/*  689:     */   
/*  690:     */   public boolean getSource_location()
/*  691:     */   {
/*  692:1388 */     return this.m_source_location;
/*  693:     */   }
/*  694:     */   
/*  695:     */   public void setIncremental(boolean b)
/*  696:     */   {
/*  697:1395 */     this.m_incremental = b;
/*  698:     */   }
/*  699:     */   
/*  700:     */   public void setSource_location(boolean b)
/*  701:     */   {
/*  702:1402 */     this.m_source_location = b;
/*  703:     */   }
/*  704:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.StylesheetRoot
 * JD-Core Version:    0.7.0.1
 */