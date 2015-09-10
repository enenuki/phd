/*    1:     */ package org.apache.xalan.templates;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.ObjectInputStream;
/*    5:     */ import java.io.ObjectOutputStream;
/*    6:     */ import java.io.Serializable;
/*    7:     */ import java.util.Enumeration;
/*    8:     */ import java.util.Hashtable;
/*    9:     */ import java.util.Stack;
/*   10:     */ import java.util.Vector;
/*   11:     */ import javax.xml.transform.SourceLocator;
/*   12:     */ import javax.xml.transform.TransformerException;
/*   13:     */ import org.apache.xml.utils.QName;
/*   14:     */ import org.apache.xml.utils.StringVector;
/*   15:     */ import org.apache.xml.utils.SystemIDResolver;
/*   16:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*   17:     */ 
/*   18:     */ public class Stylesheet
/*   19:     */   extends ElemTemplateElement
/*   20:     */   implements Serializable
/*   21:     */ {
/*   22:     */   static final long serialVersionUID = 2085337282743043776L;
/*   23:     */   public static final String STYLESHEET_EXT = ".lxc";
/*   24:     */   private String m_XmlnsXsl;
/*   25:     */   private StringVector m_ExtensionElementURIs;
/*   26:     */   private StringVector m_ExcludeResultPrefixs;
/*   27:     */   private String m_Id;
/*   28:     */   private String m_Version;
/*   29:     */   
/*   30:     */   public Stylesheet(Stylesheet parent)
/*   31:     */   {
/*   32:  93 */     if (null != parent)
/*   33:     */     {
/*   34:  95 */       this.m_stylesheetParent = parent;
/*   35:  96 */       this.m_stylesheetRoot = parent.getStylesheetRoot();
/*   36:     */     }
/*   37:     */   }
/*   38:     */   
/*   39:     */   public Stylesheet getStylesheet()
/*   40:     */   {
/*   41: 109 */     return this;
/*   42:     */   }
/*   43:     */   
/*   44:     */   public boolean isAggregatedType()
/*   45:     */   {
/*   46: 120 */     return false;
/*   47:     */   }
/*   48:     */   
/*   49:     */   public boolean isRoot()
/*   50:     */   {
/*   51: 130 */     return false;
/*   52:     */   }
/*   53:     */   
/*   54:     */   private void readObject(ObjectInputStream stream)
/*   55:     */     throws IOException, TransformerException
/*   56:     */   {
/*   57:     */     try
/*   58:     */     {
/*   59: 153 */       stream.defaultReadObject();
/*   60:     */     }
/*   61:     */     catch (ClassNotFoundException cnfe)
/*   62:     */     {
/*   63: 157 */       throw new TransformerException(cnfe);
/*   64:     */     }
/*   65:     */   }
/*   66:     */   
/*   67:     */   private void writeObject(ObjectOutputStream stream)
/*   68:     */     throws IOException
/*   69:     */   {
/*   70: 175 */     stream.defaultWriteObject();
/*   71:     */   }
/*   72:     */   
/*   73:     */   public void setXmlnsXsl(String v)
/*   74:     */   {
/*   75: 196 */     this.m_XmlnsXsl = v;
/*   76:     */   }
/*   77:     */   
/*   78:     */   public String getXmlnsXsl()
/*   79:     */   {
/*   80: 207 */     return this.m_XmlnsXsl;
/*   81:     */   }
/*   82:     */   
/*   83:     */   public void setExtensionElementPrefixes(StringVector v)
/*   84:     */   {
/*   85: 225 */     this.m_ExtensionElementURIs = v;
/*   86:     */   }
/*   87:     */   
/*   88:     */   public String getExtensionElementPrefix(int i)
/*   89:     */     throws ArrayIndexOutOfBoundsException
/*   90:     */   {
/*   91: 242 */     if (null == this.m_ExtensionElementURIs) {
/*   92: 243 */       throw new ArrayIndexOutOfBoundsException();
/*   93:     */     }
/*   94: 245 */     return this.m_ExtensionElementURIs.elementAt(i);
/*   95:     */   }
/*   96:     */   
/*   97:     */   public int getExtensionElementPrefixCount()
/*   98:     */   {
/*   99: 256 */     return null != this.m_ExtensionElementURIs ? this.m_ExtensionElementURIs.size() : 0;
/*  100:     */   }
/*  101:     */   
/*  102:     */   public boolean containsExtensionElementURI(String uri)
/*  103:     */   {
/*  104: 271 */     if (null == this.m_ExtensionElementURIs) {
/*  105: 272 */       return false;
/*  106:     */     }
/*  107: 274 */     return this.m_ExtensionElementURIs.contains(uri);
/*  108:     */   }
/*  109:     */   
/*  110:     */   public void setExcludeResultPrefixes(StringVector v)
/*  111:     */   {
/*  112: 297 */     this.m_ExcludeResultPrefixs = v;
/*  113:     */   }
/*  114:     */   
/*  115:     */   public String getExcludeResultPrefix(int i)
/*  116:     */     throws ArrayIndexOutOfBoundsException
/*  117:     */   {
/*  118: 320 */     if (null == this.m_ExcludeResultPrefixs) {
/*  119: 321 */       throw new ArrayIndexOutOfBoundsException();
/*  120:     */     }
/*  121: 323 */     return this.m_ExcludeResultPrefixs.elementAt(i);
/*  122:     */   }
/*  123:     */   
/*  124:     */   public int getExcludeResultPrefixCount()
/*  125:     */   {
/*  126: 334 */     return null != this.m_ExcludeResultPrefixs ? this.m_ExcludeResultPrefixs.size() : 0;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public boolean containsExcludeResultPrefix(String prefix, String uri)
/*  130:     */   {
/*  131: 351 */     if ((null == this.m_ExcludeResultPrefixs) || (uri == null)) {
/*  132: 352 */       return false;
/*  133:     */     }
/*  134: 356 */     for (int i = 0; i < this.m_ExcludeResultPrefixs.size(); i++) {
/*  135: 358 */       if (uri.equals(getNamespaceForPrefix(this.m_ExcludeResultPrefixs.elementAt(i)))) {
/*  136: 359 */         return true;
/*  137:     */       }
/*  138:     */     }
/*  139: 362 */     return false;
/*  140:     */   }
/*  141:     */   
/*  142:     */   public void setId(String v)
/*  143:     */   {
/*  144: 384 */     this.m_Id = v;
/*  145:     */   }
/*  146:     */   
/*  147:     */   public String getId()
/*  148:     */   {
/*  149: 395 */     return this.m_Id;
/*  150:     */   }
/*  151:     */   
/*  152: 408 */   private boolean m_isCompatibleMode = false;
/*  153:     */   private Vector m_imports;
/*  154:     */   private Vector m_includes;
/*  155:     */   Stack m_DecimalFormatDeclarations;
/*  156:     */   private Vector m_whitespaceStrippingElements;
/*  157:     */   private Vector m_whitespacePreservingElements;
/*  158:     */   private Vector m_output;
/*  159:     */   private Vector m_keyDeclarations;
/*  160:     */   private Vector m_attributeSets;
/*  161:     */   private Vector m_topLevelVariables;
/*  162:     */   private Vector m_templates;
/*  163:     */   private Vector m_prefix_aliases;
/*  164:     */   private Hashtable m_NonXslTopLevel;
/*  165:     */   
/*  166:     */   public void setVersion(String v)
/*  167:     */   {
/*  168: 418 */     this.m_Version = v;
/*  169: 419 */     this.m_isCompatibleMode = (Double.valueOf(v).doubleValue() > 1.0D);
/*  170:     */   }
/*  171:     */   
/*  172:     */   public boolean getCompatibleMode()
/*  173:     */   {
/*  174: 429 */     return this.m_isCompatibleMode;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public String getVersion()
/*  178:     */   {
/*  179: 440 */     return this.m_Version;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public void setImport(StylesheetComposed v)
/*  183:     */   {
/*  184: 458 */     if (null == this.m_imports) {
/*  185: 459 */       this.m_imports = new Vector();
/*  186:     */     }
/*  187: 463 */     this.m_imports.addElement(v);
/*  188:     */   }
/*  189:     */   
/*  190:     */   public StylesheetComposed getImport(int i)
/*  191:     */     throws ArrayIndexOutOfBoundsException
/*  192:     */   {
/*  193: 480 */     if (null == this.m_imports) {
/*  194: 481 */       throw new ArrayIndexOutOfBoundsException();
/*  195:     */     }
/*  196: 483 */     return (StylesheetComposed)this.m_imports.elementAt(i);
/*  197:     */   }
/*  198:     */   
/*  199:     */   public int getImportCount()
/*  200:     */   {
/*  201: 494 */     return null != this.m_imports ? this.m_imports.size() : 0;
/*  202:     */   }
/*  203:     */   
/*  204:     */   public void setInclude(Stylesheet v)
/*  205:     */   {
/*  206: 512 */     if (null == this.m_includes) {
/*  207: 513 */       this.m_includes = new Vector();
/*  208:     */     }
/*  209: 515 */     this.m_includes.addElement(v);
/*  210:     */   }
/*  211:     */   
/*  212:     */   public Stylesheet getInclude(int i)
/*  213:     */     throws ArrayIndexOutOfBoundsException
/*  214:     */   {
/*  215: 531 */     if (null == this.m_includes) {
/*  216: 532 */       throw new ArrayIndexOutOfBoundsException();
/*  217:     */     }
/*  218: 534 */     return (Stylesheet)this.m_includes.elementAt(i);
/*  219:     */   }
/*  220:     */   
/*  221:     */   public int getIncludeCount()
/*  222:     */   {
/*  223: 545 */     return null != this.m_includes ? this.m_includes.size() : 0;
/*  224:     */   }
/*  225:     */   
/*  226:     */   public void setDecimalFormat(DecimalFormatProperties edf)
/*  227:     */   {
/*  228: 563 */     if (null == this.m_DecimalFormatDeclarations) {
/*  229: 564 */       this.m_DecimalFormatDeclarations = new Stack();
/*  230:     */     }
/*  231: 568 */     this.m_DecimalFormatDeclarations.push(edf);
/*  232:     */   }
/*  233:     */   
/*  234:     */   public DecimalFormatProperties getDecimalFormat(QName name)
/*  235:     */   {
/*  236: 584 */     if (null == this.m_DecimalFormatDeclarations) {
/*  237: 585 */       return null;
/*  238:     */     }
/*  239: 587 */     int n = getDecimalFormatCount();
/*  240: 589 */     for (int i = n - 1; i >= 0; i++)
/*  241:     */     {
/*  242: 591 */       DecimalFormatProperties dfp = getDecimalFormat(i);
/*  243: 593 */       if (dfp.getName().equals(name)) {
/*  244: 594 */         return dfp;
/*  245:     */       }
/*  246:     */     }
/*  247: 597 */     return null;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public DecimalFormatProperties getDecimalFormat(int i)
/*  251:     */     throws ArrayIndexOutOfBoundsException
/*  252:     */   {
/*  253: 615 */     if (null == this.m_DecimalFormatDeclarations) {
/*  254: 616 */       throw new ArrayIndexOutOfBoundsException();
/*  255:     */     }
/*  256: 618 */     return (DecimalFormatProperties)this.m_DecimalFormatDeclarations.elementAt(i);
/*  257:     */   }
/*  258:     */   
/*  259:     */   public int getDecimalFormatCount()
/*  260:     */   {
/*  261: 629 */     return null != this.m_DecimalFormatDeclarations ? this.m_DecimalFormatDeclarations.size() : 0;
/*  262:     */   }
/*  263:     */   
/*  264:     */   public void setStripSpaces(WhiteSpaceInfo wsi)
/*  265:     */   {
/*  266: 649 */     if (null == this.m_whitespaceStrippingElements) {
/*  267: 651 */       this.m_whitespaceStrippingElements = new Vector();
/*  268:     */     }
/*  269: 654 */     this.m_whitespaceStrippingElements.addElement(wsi);
/*  270:     */   }
/*  271:     */   
/*  272:     */   public WhiteSpaceInfo getStripSpace(int i)
/*  273:     */     throws ArrayIndexOutOfBoundsException
/*  274:     */   {
/*  275: 670 */     if (null == this.m_whitespaceStrippingElements) {
/*  276: 671 */       throw new ArrayIndexOutOfBoundsException();
/*  277:     */     }
/*  278: 673 */     return (WhiteSpaceInfo)this.m_whitespaceStrippingElements.elementAt(i);
/*  279:     */   }
/*  280:     */   
/*  281:     */   public int getStripSpaceCount()
/*  282:     */   {
/*  283: 684 */     return null != this.m_whitespaceStrippingElements ? this.m_whitespaceStrippingElements.size() : 0;
/*  284:     */   }
/*  285:     */   
/*  286:     */   public void setPreserveSpaces(WhiteSpaceInfo wsi)
/*  287:     */   {
/*  288: 704 */     if (null == this.m_whitespacePreservingElements) {
/*  289: 706 */       this.m_whitespacePreservingElements = new Vector();
/*  290:     */     }
/*  291: 709 */     this.m_whitespacePreservingElements.addElement(wsi);
/*  292:     */   }
/*  293:     */   
/*  294:     */   public WhiteSpaceInfo getPreserveSpace(int i)
/*  295:     */     throws ArrayIndexOutOfBoundsException
/*  296:     */   {
/*  297: 725 */     if (null == this.m_whitespacePreservingElements) {
/*  298: 726 */       throw new ArrayIndexOutOfBoundsException();
/*  299:     */     }
/*  300: 728 */     return (WhiteSpaceInfo)this.m_whitespacePreservingElements.elementAt(i);
/*  301:     */   }
/*  302:     */   
/*  303:     */   public int getPreserveSpaceCount()
/*  304:     */   {
/*  305: 739 */     return null != this.m_whitespacePreservingElements ? this.m_whitespacePreservingElements.size() : 0;
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void setOutput(OutputProperties v)
/*  309:     */   {
/*  310: 758 */     if (null == this.m_output) {
/*  311: 760 */       this.m_output = new Vector();
/*  312:     */     }
/*  313: 763 */     this.m_output.addElement(v);
/*  314:     */   }
/*  315:     */   
/*  316:     */   public OutputProperties getOutput(int i)
/*  317:     */     throws ArrayIndexOutOfBoundsException
/*  318:     */   {
/*  319: 779 */     if (null == this.m_output) {
/*  320: 780 */       throw new ArrayIndexOutOfBoundsException();
/*  321:     */     }
/*  322: 782 */     return (OutputProperties)this.m_output.elementAt(i);
/*  323:     */   }
/*  324:     */   
/*  325:     */   public int getOutputCount()
/*  326:     */   {
/*  327: 793 */     return null != this.m_output ? this.m_output.size() : 0;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public void setKey(KeyDeclaration v)
/*  331:     */   {
/*  332: 812 */     if (null == this.m_keyDeclarations) {
/*  333: 813 */       this.m_keyDeclarations = new Vector();
/*  334:     */     }
/*  335: 815 */     this.m_keyDeclarations.addElement(v);
/*  336:     */   }
/*  337:     */   
/*  338:     */   public KeyDeclaration getKey(int i)
/*  339:     */     throws ArrayIndexOutOfBoundsException
/*  340:     */   {
/*  341: 831 */     if (null == this.m_keyDeclarations) {
/*  342: 832 */       throw new ArrayIndexOutOfBoundsException();
/*  343:     */     }
/*  344: 834 */     return (KeyDeclaration)this.m_keyDeclarations.elementAt(i);
/*  345:     */   }
/*  346:     */   
/*  347:     */   public int getKeyCount()
/*  348:     */   {
/*  349: 845 */     return null != this.m_keyDeclarations ? this.m_keyDeclarations.size() : 0;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void setAttributeSet(ElemAttributeSet attrSet)
/*  353:     */   {
/*  354: 863 */     if (null == this.m_attributeSets) {
/*  355: 865 */       this.m_attributeSets = new Vector();
/*  356:     */     }
/*  357: 868 */     this.m_attributeSets.addElement(attrSet);
/*  358:     */   }
/*  359:     */   
/*  360:     */   public ElemAttributeSet getAttributeSet(int i)
/*  361:     */     throws ArrayIndexOutOfBoundsException
/*  362:     */   {
/*  363: 885 */     if (null == this.m_attributeSets) {
/*  364: 886 */       throw new ArrayIndexOutOfBoundsException();
/*  365:     */     }
/*  366: 888 */     return (ElemAttributeSet)this.m_attributeSets.elementAt(i);
/*  367:     */   }
/*  368:     */   
/*  369:     */   public int getAttributeSetCount()
/*  370:     */   {
/*  371: 899 */     return null != this.m_attributeSets ? this.m_attributeSets.size() : 0;
/*  372:     */   }
/*  373:     */   
/*  374:     */   public void setVariable(ElemVariable v)
/*  375:     */   {
/*  376: 917 */     if (null == this.m_topLevelVariables) {
/*  377: 918 */       this.m_topLevelVariables = new Vector();
/*  378:     */     }
/*  379: 920 */     this.m_topLevelVariables.addElement(v);
/*  380:     */   }
/*  381:     */   
/*  382:     */   public ElemVariable getVariableOrParam(QName qname)
/*  383:     */   {
/*  384: 934 */     if (null != this.m_topLevelVariables)
/*  385:     */     {
/*  386: 936 */       int n = getVariableOrParamCount();
/*  387: 938 */       for (int i = 0; i < n; i++)
/*  388:     */       {
/*  389: 940 */         ElemVariable var = getVariableOrParam(i);
/*  390: 942 */         if (var.getName().equals(qname)) {
/*  391: 943 */           return var;
/*  392:     */         }
/*  393:     */       }
/*  394:     */     }
/*  395: 947 */     return null;
/*  396:     */   }
/*  397:     */   
/*  398:     */   public ElemVariable getVariable(QName qname)
/*  399:     */   {
/*  400: 962 */     if (null != this.m_topLevelVariables)
/*  401:     */     {
/*  402: 964 */       int n = getVariableOrParamCount();
/*  403: 966 */       for (int i = 0; i < n; i++)
/*  404:     */       {
/*  405: 968 */         ElemVariable var = getVariableOrParam(i);
/*  406: 969 */         if ((var.getXSLToken() == 73) && (var.getName().equals(qname))) {
/*  407: 971 */           return var;
/*  408:     */         }
/*  409:     */       }
/*  410:     */     }
/*  411: 975 */     return null;
/*  412:     */   }
/*  413:     */   
/*  414:     */   public ElemVariable getVariableOrParam(int i)
/*  415:     */     throws ArrayIndexOutOfBoundsException
/*  416:     */   {
/*  417: 991 */     if (null == this.m_topLevelVariables) {
/*  418: 992 */       throw new ArrayIndexOutOfBoundsException();
/*  419:     */     }
/*  420: 994 */     return (ElemVariable)this.m_topLevelVariables.elementAt(i);
/*  421:     */   }
/*  422:     */   
/*  423:     */   public int getVariableOrParamCount()
/*  424:     */   {
/*  425:1005 */     return null != this.m_topLevelVariables ? this.m_topLevelVariables.size() : 0;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void setParam(ElemParam v)
/*  429:     */   {
/*  430:1016 */     setVariable(v);
/*  431:     */   }
/*  432:     */   
/*  433:     */   public ElemParam getParam(QName qname)
/*  434:     */   {
/*  435:1030 */     if (null != this.m_topLevelVariables)
/*  436:     */     {
/*  437:1032 */       int n = getVariableOrParamCount();
/*  438:1034 */       for (int i = 0; i < n; i++)
/*  439:     */       {
/*  440:1036 */         ElemVariable var = getVariableOrParam(i);
/*  441:1037 */         if ((var.getXSLToken() == 41) && (var.getName().equals(qname))) {
/*  442:1039 */           return (ElemParam)var;
/*  443:     */         }
/*  444:     */       }
/*  445:     */     }
/*  446:1043 */     return null;
/*  447:     */   }
/*  448:     */   
/*  449:     */   public void setTemplate(ElemTemplate v)
/*  450:     */   {
/*  451:1061 */     if (null == this.m_templates) {
/*  452:1062 */       this.m_templates = new Vector();
/*  453:     */     }
/*  454:1064 */     this.m_templates.addElement(v);
/*  455:1065 */     v.setStylesheet(this);
/*  456:     */   }
/*  457:     */   
/*  458:     */   public ElemTemplate getTemplate(int i)
/*  459:     */     throws TransformerException
/*  460:     */   {
/*  461:1081 */     if (null == this.m_templates) {
/*  462:1082 */       throw new ArrayIndexOutOfBoundsException();
/*  463:     */     }
/*  464:1084 */     return (ElemTemplate)this.m_templates.elementAt(i);
/*  465:     */   }
/*  466:     */   
/*  467:     */   public int getTemplateCount()
/*  468:     */   {
/*  469:1095 */     return null != this.m_templates ? this.m_templates.size() : 0;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void setNamespaceAlias(NamespaceAlias na)
/*  473:     */   {
/*  474:1113 */     if (this.m_prefix_aliases == null) {
/*  475:1114 */       this.m_prefix_aliases = new Vector();
/*  476:     */     }
/*  477:1116 */     this.m_prefix_aliases.addElement(na);
/*  478:     */   }
/*  479:     */   
/*  480:     */   public NamespaceAlias getNamespaceAlias(int i)
/*  481:     */     throws ArrayIndexOutOfBoundsException
/*  482:     */   {
/*  483:1133 */     if (null == this.m_prefix_aliases) {
/*  484:1134 */       throw new ArrayIndexOutOfBoundsException();
/*  485:     */     }
/*  486:1136 */     return (NamespaceAlias)this.m_prefix_aliases.elementAt(i);
/*  487:     */   }
/*  488:     */   
/*  489:     */   public int getNamespaceAliasCount()
/*  490:     */   {
/*  491:1147 */     return null != this.m_prefix_aliases ? this.m_prefix_aliases.size() : 0;
/*  492:     */   }
/*  493:     */   
/*  494:     */   public void setNonXslTopLevel(QName name, Object obj)
/*  495:     */   {
/*  496:1166 */     if (null == this.m_NonXslTopLevel) {
/*  497:1167 */       this.m_NonXslTopLevel = new Hashtable();
/*  498:     */     }
/*  499:1169 */     this.m_NonXslTopLevel.put(name, obj);
/*  500:     */   }
/*  501:     */   
/*  502:     */   public Object getNonXslTopLevel(QName name)
/*  503:     */   {
/*  504:1182 */     return null != this.m_NonXslTopLevel ? this.m_NonXslTopLevel.get(name) : null;
/*  505:     */   }
/*  506:     */   
/*  507:1191 */   private String m_href = null;
/*  508:     */   private String m_publicId;
/*  509:     */   private String m_systemId;
/*  510:     */   private StylesheetRoot m_stylesheetRoot;
/*  511:     */   private Stylesheet m_stylesheetParent;
/*  512:     */   
/*  513:     */   public String getHref()
/*  514:     */   {
/*  515:1208 */     return this.m_href;
/*  516:     */   }
/*  517:     */   
/*  518:     */   public void setHref(String baseIdent)
/*  519:     */   {
/*  520:1218 */     this.m_href = baseIdent;
/*  521:     */   }
/*  522:     */   
/*  523:     */   public void setLocaterInfo(SourceLocator locator)
/*  524:     */   {
/*  525:1229 */     if (null != locator)
/*  526:     */     {
/*  527:1231 */       this.m_publicId = locator.getPublicId();
/*  528:1232 */       this.m_systemId = locator.getSystemId();
/*  529:1234 */       if (null != this.m_systemId) {
/*  530:     */         try
/*  531:     */         {
/*  532:1238 */           this.m_href = SystemIDResolver.getAbsoluteURI(this.m_systemId, null);
/*  533:     */         }
/*  534:     */         catch (TransformerException se) {}
/*  535:     */       }
/*  536:1247 */       super.setLocaterInfo(locator);
/*  537:     */     }
/*  538:     */   }
/*  539:     */   
/*  540:     */   public StylesheetRoot getStylesheetRoot()
/*  541:     */   {
/*  542:1266 */     return this.m_stylesheetRoot;
/*  543:     */   }
/*  544:     */   
/*  545:     */   public void setStylesheetRoot(StylesheetRoot v)
/*  546:     */   {
/*  547:1277 */     this.m_stylesheetRoot = v;
/*  548:     */   }
/*  549:     */   
/*  550:     */   public Stylesheet getStylesheetParent()
/*  551:     */   {
/*  552:1295 */     return this.m_stylesheetParent;
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void setStylesheetParent(Stylesheet v)
/*  556:     */   {
/*  557:1306 */     this.m_stylesheetParent = v;
/*  558:     */   }
/*  559:     */   
/*  560:     */   public StylesheetComposed getStylesheetComposed()
/*  561:     */   {
/*  562:1318 */     Stylesheet sheet = this;
/*  563:1320 */     while (!sheet.isAggregatedType()) {
/*  564:1322 */       sheet = sheet.getStylesheetParent();
/*  565:     */     }
/*  566:1325 */     return (StylesheetComposed)sheet;
/*  567:     */   }
/*  568:     */   
/*  569:     */   public short getNodeType()
/*  570:     */   {
/*  571:1335 */     return 9;
/*  572:     */   }
/*  573:     */   
/*  574:     */   public int getXSLToken()
/*  575:     */   {
/*  576:1347 */     return 25;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public String getNodeName()
/*  580:     */   {
/*  581:1357 */     return "stylesheet";
/*  582:     */   }
/*  583:     */   
/*  584:     */   public void replaceTemplate(ElemTemplate v, int i)
/*  585:     */     throws TransformerException
/*  586:     */   {
/*  587:1375 */     if (null == this.m_templates) {
/*  588:1376 */       throw new ArrayIndexOutOfBoundsException();
/*  589:     */     }
/*  590:1378 */     replaceChild(v, (ElemTemplateElement)this.m_templates.elementAt(i));
/*  591:1379 */     this.m_templates.setElementAt(v, i);
/*  592:1380 */     v.setStylesheet(this);
/*  593:     */   }
/*  594:     */   
/*  595:     */   protected void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/*  596:     */   {
/*  597:1389 */     int s = getImportCount();
/*  598:1390 */     for (int j = 0; j < s; j++) {
/*  599:1392 */       getImport(j).callVisitors(visitor);
/*  600:     */     }
/*  601:1395 */     s = getIncludeCount();
/*  602:1396 */     for (int j = 0; j < s; j++) {
/*  603:1398 */       getInclude(j).callVisitors(visitor);
/*  604:     */     }
/*  605:1401 */     s = getOutputCount();
/*  606:1402 */     for (int j = 0; j < s; j++) {
/*  607:1404 */       visitor.visitTopLevelInstruction(getOutput(j));
/*  608:     */     }
/*  609:1409 */     s = getAttributeSetCount();
/*  610:1410 */     for (int j = 0; j < s; j++)
/*  611:     */     {
/*  612:1412 */       ElemAttributeSet attrSet = getAttributeSet(j);
/*  613:1413 */       if (visitor.visitTopLevelInstruction(attrSet)) {
/*  614:1415 */         attrSet.callChildVisitors(visitor);
/*  615:     */       }
/*  616:     */     }
/*  617:1420 */     s = getDecimalFormatCount();
/*  618:1421 */     for (int j = 0; j < s; j++) {
/*  619:1423 */       visitor.visitTopLevelInstruction(getDecimalFormat(j));
/*  620:     */     }
/*  621:1428 */     s = getKeyCount();
/*  622:1429 */     for (int j = 0; j < s; j++) {
/*  623:1431 */       visitor.visitTopLevelInstruction(getKey(j));
/*  624:     */     }
/*  625:1436 */     s = getNamespaceAliasCount();
/*  626:1437 */     for (int j = 0; j < s; j++) {
/*  627:1439 */       visitor.visitTopLevelInstruction(getNamespaceAlias(j));
/*  628:     */     }
/*  629:1444 */     s = getTemplateCount();
/*  630:1445 */     for (int j = 0; j < s; j++) {
/*  631:     */       try
/*  632:     */       {
/*  633:1449 */         ElemTemplate template = getTemplate(j);
/*  634:1450 */         if (visitor.visitTopLevelInstruction(template)) {
/*  635:1452 */           template.callChildVisitors(visitor);
/*  636:     */         }
/*  637:     */       }
/*  638:     */       catch (TransformerException te)
/*  639:     */       {
/*  640:1457 */         throw new WrappedRuntimeException(te);
/*  641:     */       }
/*  642:     */     }
/*  643:1463 */     s = getVariableOrParamCount();
/*  644:1464 */     for (int j = 0; j < s; j++)
/*  645:     */     {
/*  646:1466 */       ElemVariable var = getVariableOrParam(j);
/*  647:1467 */       if (visitor.visitTopLevelVariableOrParamDecl(var)) {
/*  648:1469 */         var.callChildVisitors(visitor);
/*  649:     */       }
/*  650:     */     }
/*  651:1475 */     s = getStripSpaceCount();
/*  652:1476 */     for (int j = 0; j < s; j++) {
/*  653:1478 */       visitor.visitTopLevelInstruction(getStripSpace(j));
/*  654:     */     }
/*  655:1481 */     s = getPreserveSpaceCount();
/*  656:1482 */     for (int j = 0; j < s; j++) {
/*  657:1484 */       visitor.visitTopLevelInstruction(getPreserveSpace(j));
/*  658:     */     }
/*  659:1487 */     if (null != this.m_NonXslTopLevel)
/*  660:     */     {
/*  661:1489 */       Enumeration elements = this.m_NonXslTopLevel.elements();
/*  662:1490 */       while (elements.hasMoreElements())
/*  663:     */       {
/*  664:1492 */         ElemTemplateElement elem = (ElemTemplateElement)elements.nextElement();
/*  665:1493 */         if (visitor.visitTopLevelInstruction(elem)) {
/*  666:1495 */           elem.callChildVisitors(visitor);
/*  667:     */         }
/*  668:     */       }
/*  669:     */     }
/*  670:     */   }
/*  671:     */   
/*  672:     */   protected boolean accept(XSLTVisitor visitor)
/*  673:     */   {
/*  674:1512 */     return visitor.visitStylesheet(this);
/*  675:     */   }
/*  676:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.Stylesheet
 * JD-Core Version:    0.7.0.1
 */