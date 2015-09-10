/*    1:     */ package org.apache.xalan.templates;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.text.DecimalFormat;
/*    5:     */ import java.text.DecimalFormatSymbols;
/*    6:     */ import java.text.NumberFormat;
/*    7:     */ import java.util.Locale;
/*    8:     */ import java.util.NoSuchElementException;
/*    9:     */ import java.util.ResourceBundle;
/*   10:     */ import java.util.Vector;
/*   11:     */ import javax.xml.transform.TransformerException;
/*   12:     */ import org.apache.xalan.trace.TraceManager;
/*   13:     */ import org.apache.xalan.transformer.CountersTable;
/*   14:     */ import org.apache.xalan.transformer.DecimalToRoman;
/*   15:     */ import org.apache.xalan.transformer.MsgMgr;
/*   16:     */ import org.apache.xalan.transformer.TransformerImpl;
/*   17:     */ import org.apache.xml.dtm.DTM;
/*   18:     */ import org.apache.xml.utils.FastStringBuffer;
/*   19:     */ import org.apache.xml.utils.NodeVector;
/*   20:     */ import org.apache.xml.utils.PrefixResolver;
/*   21:     */ import org.apache.xml.utils.StringBufferPool;
/*   22:     */ import org.apache.xml.utils.res.CharArrayWrapper;
/*   23:     */ import org.apache.xml.utils.res.IntArrayWrapper;
/*   24:     */ import org.apache.xml.utils.res.LongArrayWrapper;
/*   25:     */ import org.apache.xml.utils.res.StringArrayWrapper;
/*   26:     */ import org.apache.xml.utils.res.XResourceBundle;
/*   27:     */ import org.apache.xpath.Expression;
/*   28:     */ import org.apache.xpath.NodeSetDTM;
/*   29:     */ import org.apache.xpath.XPath;
/*   30:     */ import org.apache.xpath.XPathContext;
/*   31:     */ import org.apache.xpath.objects.XObject;
/*   32:     */ import org.w3c.dom.Node;
/*   33:     */ import org.xml.sax.ContentHandler;
/*   34:     */ import org.xml.sax.SAXException;
/*   35:     */ 
/*   36:     */ public class ElemNumber
/*   37:     */   extends ElemTemplateElement
/*   38:     */ {
/*   39:     */   static final long serialVersionUID = 8118472298274407610L;
/*   40:  83 */   private CharArrayWrapper m_alphaCountTable = null;
/*   41:     */   
/*   42:     */   private class MyPrefixResolver
/*   43:     */     implements PrefixResolver
/*   44:     */   {
/*   45:     */     DTM dtm;
/*   46:     */     int handle;
/*   47:     */     boolean handleNullPrefix;
/*   48:     */     
/*   49:     */     public MyPrefixResolver(Node xpathExpressionContext, DTM dtm, int handle, boolean handleNullPrefix)
/*   50:     */     {
/*   51:  96 */       this.dtm = dtm;
/*   52:  97 */       this.handle = handle;
/*   53:  98 */       this.handleNullPrefix = handleNullPrefix;
/*   54:     */     }
/*   55:     */     
/*   56:     */     public String getNamespaceForPrefix(String prefix)
/*   57:     */     {
/*   58: 105 */       return this.dtm.getNamespaceURI(this.handle);
/*   59:     */     }
/*   60:     */     
/*   61:     */     public String getNamespaceForPrefix(String prefix, Node context)
/*   62:     */     {
/*   63: 113 */       return getNamespaceForPrefix(prefix);
/*   64:     */     }
/*   65:     */     
/*   66:     */     public String getBaseIdentifier()
/*   67:     */     {
/*   68: 120 */       return ElemNumber.this.getBaseIdentifier();
/*   69:     */     }
/*   70:     */     
/*   71:     */     public boolean handlesNullPrefixes()
/*   72:     */     {
/*   73: 127 */       return this.handleNullPrefix;
/*   74:     */     }
/*   75:     */   }
/*   76:     */   
/*   77: 136 */   private XPath m_countMatchPattern = null;
/*   78:     */   
/*   79:     */   public void setCount(XPath v)
/*   80:     */   {
/*   81: 151 */     this.m_countMatchPattern = v;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public XPath getCount()
/*   85:     */   {
/*   86: 167 */     return this.m_countMatchPattern;
/*   87:     */   }
/*   88:     */   
/*   89: 181 */   private XPath m_fromMatchPattern = null;
/*   90:     */   
/*   91:     */   public void setFrom(XPath v)
/*   92:     */   {
/*   93: 197 */     this.m_fromMatchPattern = v;
/*   94:     */   }
/*   95:     */   
/*   96:     */   public XPath getFrom()
/*   97:     */   {
/*   98: 214 */     return this.m_fromMatchPattern;
/*   99:     */   }
/*  100:     */   
/*  101: 243 */   private int m_level = 1;
/*  102:     */   
/*  103:     */   public void setLevel(int v)
/*  104:     */   {
/*  105: 255 */     this.m_level = v;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public int getLevel()
/*  109:     */   {
/*  110: 268 */     return this.m_level;
/*  111:     */   }
/*  112:     */   
/*  113: 277 */   private XPath m_valueExpr = null;
/*  114:     */   
/*  115:     */   public void setValue(XPath v)
/*  116:     */   {
/*  117: 289 */     this.m_valueExpr = v;
/*  118:     */   }
/*  119:     */   
/*  120:     */   public XPath getValue()
/*  121:     */   {
/*  122: 302 */     return this.m_valueExpr;
/*  123:     */   }
/*  124:     */   
/*  125: 311 */   private AVT m_format_avt = null;
/*  126:     */   
/*  127:     */   public void setFormat(AVT v)
/*  128:     */   {
/*  129: 323 */     this.m_format_avt = v;
/*  130:     */   }
/*  131:     */   
/*  132:     */   public AVT getFormat()
/*  133:     */   {
/*  134: 336 */     return this.m_format_avt;
/*  135:     */   }
/*  136:     */   
/*  137: 344 */   private AVT m_lang_avt = null;
/*  138:     */   
/*  139:     */   public void setLang(AVT v)
/*  140:     */   {
/*  141: 359 */     this.m_lang_avt = v;
/*  142:     */   }
/*  143:     */   
/*  144:     */   public AVT getLang()
/*  145:     */   {
/*  146: 375 */     return this.m_lang_avt;
/*  147:     */   }
/*  148:     */   
/*  149: 383 */   private AVT m_lettervalue_avt = null;
/*  150:     */   
/*  151:     */   public void setLetterValue(AVT v)
/*  152:     */   {
/*  153: 395 */     this.m_lettervalue_avt = v;
/*  154:     */   }
/*  155:     */   
/*  156:     */   public AVT getLetterValue()
/*  157:     */   {
/*  158: 408 */     return this.m_lettervalue_avt;
/*  159:     */   }
/*  160:     */   
/*  161: 417 */   private AVT m_groupingSeparator_avt = null;
/*  162:     */   
/*  163:     */   public void setGroupingSeparator(AVT v)
/*  164:     */   {
/*  165: 430 */     this.m_groupingSeparator_avt = v;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public AVT getGroupingSeparator()
/*  169:     */   {
/*  170: 444 */     return this.m_groupingSeparator_avt;
/*  171:     */   }
/*  172:     */   
/*  173: 451 */   private AVT m_groupingSize_avt = null;
/*  174:     */   
/*  175:     */   public void setGroupingSize(AVT v)
/*  176:     */   {
/*  177: 462 */     this.m_groupingSize_avt = v;
/*  178:     */   }
/*  179:     */   
/*  180:     */   public AVT getGroupingSize()
/*  181:     */   {
/*  182: 474 */     return this.m_groupingSize_avt;
/*  183:     */   }
/*  184:     */   
/*  185: 487 */   private static final DecimalToRoman[] m_romanConvertTable = { new DecimalToRoman(1000L, "M", 900L, "CM"), new DecimalToRoman(500L, "D", 400L, "CD"), new DecimalToRoman(100L, "C", 90L, "XC"), new DecimalToRoman(50L, "L", 40L, "XL"), new DecimalToRoman(10L, "X", 9L, "IX"), new DecimalToRoman(5L, "V", 4L, "IV"), new DecimalToRoman(1L, "I", 1L, "I") };
/*  186:     */   
/*  187:     */   public void compose(StylesheetRoot sroot)
/*  188:     */     throws TransformerException
/*  189:     */   {
/*  190: 504 */     super.compose(sroot);
/*  191: 505 */     StylesheetRoot.ComposeState cstate = sroot.getComposeState();
/*  192: 506 */     Vector vnames = cstate.getVariableNames();
/*  193: 507 */     if (null != this.m_countMatchPattern) {
/*  194: 508 */       this.m_countMatchPattern.fixupVariables(vnames, cstate.getGlobalsSize());
/*  195:     */     }
/*  196: 509 */     if (null != this.m_format_avt) {
/*  197: 510 */       this.m_format_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  198:     */     }
/*  199: 511 */     if (null != this.m_fromMatchPattern) {
/*  200: 512 */       this.m_fromMatchPattern.fixupVariables(vnames, cstate.getGlobalsSize());
/*  201:     */     }
/*  202: 513 */     if (null != this.m_groupingSeparator_avt) {
/*  203: 514 */       this.m_groupingSeparator_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  204:     */     }
/*  205: 515 */     if (null != this.m_groupingSize_avt) {
/*  206: 516 */       this.m_groupingSize_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  207:     */     }
/*  208: 517 */     if (null != this.m_lang_avt) {
/*  209: 518 */       this.m_lang_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  210:     */     }
/*  211: 519 */     if (null != this.m_lettervalue_avt) {
/*  212: 520 */       this.m_lettervalue_avt.fixupVariables(vnames, cstate.getGlobalsSize());
/*  213:     */     }
/*  214: 521 */     if (null != this.m_valueExpr) {
/*  215: 522 */       this.m_valueExpr.fixupVariables(vnames, cstate.getGlobalsSize());
/*  216:     */     }
/*  217:     */   }
/*  218:     */   
/*  219:     */   public int getXSLToken()
/*  220:     */   {
/*  221: 534 */     return 35;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public String getNodeName()
/*  225:     */   {
/*  226: 544 */     return "number";
/*  227:     */   }
/*  228:     */   
/*  229:     */   public void execute(TransformerImpl transformer)
/*  230:     */     throws TransformerException
/*  231:     */   {
/*  232: 560 */     if (transformer.getDebug()) {
/*  233: 561 */       transformer.getTraceManager().fireTraceEvent(this);
/*  234:     */     }
/*  235: 563 */     int sourceNode = transformer.getXPathContext().getCurrentNode();
/*  236: 564 */     String countString = getCountString(transformer, sourceNode);
/*  237:     */     try
/*  238:     */     {
/*  239: 568 */       transformer.getResultTreeHandler().characters(countString.toCharArray(), 0, countString.length());
/*  240:     */     }
/*  241:     */     catch (SAXException se)
/*  242:     */     {
/*  243: 573 */       throw new TransformerException(se);
/*  244:     */     }
/*  245:     */     finally
/*  246:     */     {
/*  247: 577 */       if (transformer.getDebug()) {
/*  248: 578 */         transformer.getTraceManager().fireTraceEndEvent(this);
/*  249:     */       }
/*  250:     */     }
/*  251:     */   }
/*  252:     */   
/*  253:     */   public ElemTemplateElement appendChild(ElemTemplateElement newChild)
/*  254:     */   {
/*  255: 594 */     error("ER_CANNOT_ADD", new Object[] { newChild.getNodeName(), getNodeName() });
/*  256:     */     
/*  257:     */ 
/*  258:     */ 
/*  259:     */ 
/*  260: 599 */     return null;
/*  261:     */   }
/*  262:     */   
/*  263:     */   int findAncestor(XPathContext xctxt, XPath fromMatchPattern, XPath countMatchPattern, int context, ElemNumber namespaceContext)
/*  264:     */     throws TransformerException
/*  265:     */   {
/*  266: 623 */     DTM dtm = xctxt.getDTM(context);
/*  267: 624 */     while (-1 != context)
/*  268:     */     {
/*  269: 626 */       if ((null != fromMatchPattern) && 
/*  270:     */       
/*  271: 628 */         (fromMatchPattern.getMatchScore(xctxt, context) != (-1.0D / 0.0D))) {
/*  272:     */         break;
/*  273:     */       }
/*  274: 637 */       if ((null != countMatchPattern) && 
/*  275:     */       
/*  276: 639 */         (countMatchPattern.getMatchScore(xctxt, context) != (-1.0D / 0.0D))) {
/*  277:     */         break;
/*  278:     */       }
/*  279: 646 */       context = dtm.getParent(context);
/*  280:     */     }
/*  281: 649 */     return context;
/*  282:     */   }
/*  283:     */   
/*  284:     */   private int findPrecedingOrAncestorOrSelf(XPathContext xctxt, XPath fromMatchPattern, XPath countMatchPattern, int context, ElemNumber namespaceContext)
/*  285:     */     throws TransformerException
/*  286:     */   {
/*  287: 673 */     DTM dtm = xctxt.getDTM(context);
/*  288: 674 */     while (-1 != context)
/*  289:     */     {
/*  290: 676 */       if (null != fromMatchPattern) {
/*  291: 678 */         if (fromMatchPattern.getMatchScore(xctxt, context) != (-1.0D / 0.0D))
/*  292:     */         {
/*  293: 681 */           context = -1;
/*  294:     */           
/*  295: 683 */           break;
/*  296:     */         }
/*  297:     */       }
/*  298: 687 */       if ((null != countMatchPattern) && 
/*  299:     */       
/*  300: 689 */         (countMatchPattern.getMatchScore(xctxt, context) != (-1.0D / 0.0D))) {
/*  301:     */         break;
/*  302:     */       }
/*  303: 696 */       int prevSibling = dtm.getPreviousSibling(context);
/*  304: 698 */       if (-1 == prevSibling)
/*  305:     */       {
/*  306: 700 */         context = dtm.getParent(context);
/*  307:     */       }
/*  308:     */       else
/*  309:     */       {
/*  310: 706 */         context = dtm.getLastChild(prevSibling);
/*  311: 708 */         if (context == -1) {
/*  312: 709 */           context = prevSibling;
/*  313:     */         }
/*  314:     */       }
/*  315:     */     }
/*  316: 713 */     return context;
/*  317:     */   }
/*  318:     */   
/*  319:     */   XPath getCountMatchPattern(XPathContext support, int contextNode)
/*  320:     */     throws TransformerException
/*  321:     */   {
/*  322: 730 */     XPath countMatchPattern = this.m_countMatchPattern;
/*  323: 731 */     DTM dtm = support.getDTM(contextNode);
/*  324: 732 */     if (null == countMatchPattern) {
/*  325: 734 */       switch (dtm.getNodeType(contextNode))
/*  326:     */       {
/*  327:     */       case 1: 
/*  328:     */         MyPrefixResolver resolver;
/*  329: 739 */         if (dtm.getNamespaceURI(contextNode) == null) {
/*  330: 740 */           resolver = new MyPrefixResolver(dtm.getNode(contextNode), dtm, contextNode, false);
/*  331:     */         } else {
/*  332: 742 */           resolver = new MyPrefixResolver(dtm.getNode(contextNode), dtm, contextNode, true);
/*  333:     */         }
/*  334: 745 */         countMatchPattern = new XPath(dtm.getNodeName(contextNode), this, resolver, 1, support.getErrorListener());
/*  335:     */         
/*  336: 747 */         break;
/*  337:     */       case 2: 
/*  338: 752 */         countMatchPattern = new XPath("@" + dtm.getNodeName(contextNode), this, this, 1, support.getErrorListener());
/*  339:     */         
/*  340: 754 */         break;
/*  341:     */       case 3: 
/*  342:     */       case 4: 
/*  343: 759 */         countMatchPattern = new XPath("text()", this, this, 1, support.getErrorListener());
/*  344: 760 */         break;
/*  345:     */       case 8: 
/*  346: 764 */         countMatchPattern = new XPath("comment()", this, this, 1, support.getErrorListener());
/*  347: 765 */         break;
/*  348:     */       case 9: 
/*  349: 769 */         countMatchPattern = new XPath("/", this, this, 1, support.getErrorListener());
/*  350: 770 */         break;
/*  351:     */       case 7: 
/*  352: 774 */         countMatchPattern = new XPath("pi(" + dtm.getNodeName(contextNode) + ")", this, this, 1, support.getErrorListener());
/*  353:     */         
/*  354: 776 */         break;
/*  355:     */       case 5: 
/*  356:     */       case 6: 
/*  357:     */       default: 
/*  358: 778 */         countMatchPattern = null;
/*  359:     */       }
/*  360:     */     }
/*  361: 782 */     return countMatchPattern;
/*  362:     */   }
/*  363:     */   
/*  364:     */   String getCountString(TransformerImpl transformer, int sourceNode)
/*  365:     */     throws TransformerException
/*  366:     */   {
/*  367: 799 */     long[] list = null;
/*  368: 800 */     XPathContext xctxt = transformer.getXPathContext();
/*  369: 801 */     CountersTable ctable = transformer.getCountersTable();
/*  370: 803 */     if (null != this.m_valueExpr)
/*  371:     */     {
/*  372: 805 */       XObject countObj = this.m_valueExpr.execute(xctxt, sourceNode, this);
/*  373:     */       
/*  374: 807 */       double d_count = Math.floor(countObj.num() + 0.5D);
/*  375: 808 */       if (Double.isNaN(d_count)) {
/*  376: 808 */         return "NaN";
/*  377:     */       }
/*  378: 809 */       if ((d_count < 0.0D) && (Double.isInfinite(d_count))) {
/*  379: 809 */         return "-Infinity";
/*  380:     */       }
/*  381: 810 */       if (Double.isInfinite(d_count)) {
/*  382: 810 */         return "Infinity";
/*  383:     */       }
/*  384: 811 */       if (d_count == 0.0D) {
/*  385: 811 */         return "0";
/*  386:     */       }
/*  387: 813 */       long count = d_count;
/*  388: 814 */       list = new long[1];
/*  389: 815 */       list[0] = count;
/*  390:     */     }
/*  391: 820 */     else if (3 == this.m_level)
/*  392:     */     {
/*  393: 822 */       list = new long[1];
/*  394: 823 */       list[0] = ctable.countNode(xctxt, this, sourceNode);
/*  395:     */     }
/*  396:     */     else
/*  397:     */     {
/*  398: 827 */       NodeVector ancestors = getMatchingAncestors(xctxt, sourceNode, 1 == this.m_level);
/*  399:     */       
/*  400:     */ 
/*  401: 830 */       int lastIndex = ancestors.size() - 1;
/*  402: 832 */       if (lastIndex >= 0)
/*  403:     */       {
/*  404: 834 */         list = new long[lastIndex + 1];
/*  405: 836 */         for (int i = lastIndex; i >= 0; i--)
/*  406:     */         {
/*  407: 838 */           int target = ancestors.elementAt(i);
/*  408:     */           
/*  409: 840 */           list[(lastIndex - i)] = ctable.countNode(xctxt, this, target);
/*  410:     */         }
/*  411:     */       }
/*  412:     */     }
/*  413: 846 */     return null != list ? formatNumberList(transformer, list, sourceNode) : "";
/*  414:     */   }
/*  415:     */   
/*  416:     */   public int getPreviousNode(XPathContext xctxt, int pos)
/*  417:     */     throws TransformerException
/*  418:     */   {
/*  419: 864 */     XPath countMatchPattern = getCountMatchPattern(xctxt, pos);
/*  420: 865 */     DTM dtm = xctxt.getDTM(pos);
/*  421: 867 */     if (3 == this.m_level)
/*  422:     */     {
/*  423: 869 */       fromMatchPattern = this.m_fromMatchPattern;
/*  424: 874 */       while (-1 != pos)
/*  425:     */       {
/*  426: 880 */         next = dtm.getPreviousSibling(pos);
/*  427: 882 */         if (-1 == next)
/*  428:     */         {
/*  429: 884 */           next = dtm.getParent(pos);
/*  430: 886 */           if ((-1 != next) && (((null != fromMatchPattern) && (fromMatchPattern.getMatchScore(xctxt, next) != (-1.0D / 0.0D))) || (dtm.getNodeType(next) == 9)))
/*  431:     */           {
/*  432: 890 */             pos = -1;
/*  433:     */             
/*  434: 892 */             break;
/*  435:     */           }
/*  436:     */         }
/*  437:     */         else
/*  438:     */         {
/*  439: 899 */           child = next;
/*  440: 901 */           while (-1 != child)
/*  441:     */           {
/*  442: 903 */             child = dtm.getLastChild(next);
/*  443: 905 */             if (-1 != child) {
/*  444: 906 */               next = child;
/*  445:     */             }
/*  446:     */           }
/*  447:     */         }
/*  448: 910 */         pos = next;
/*  449: 912 */         if ((-1 != pos) && ((null == countMatchPattern) || (countMatchPattern.getMatchScore(xctxt, pos) != (-1.0D / 0.0D)))) {
/*  450:     */           break;
/*  451:     */         }
/*  452:     */       }
/*  453:     */     }
/*  454:     */     else
/*  455:     */     {
/*  456: 923 */       while (-1 != pos)
/*  457:     */       {
/*  458:     */         XPath fromMatchPattern;
/*  459:     */         int next;
/*  460:     */         int child;
/*  461: 925 */         pos = dtm.getPreviousSibling(pos);
/*  462: 927 */         if ((-1 != pos) && ((null == countMatchPattern) || (countMatchPattern.getMatchScore(xctxt, pos) != (-1.0D / 0.0D)))) {
/*  463:     */           break;
/*  464:     */         }
/*  465:     */       }
/*  466:     */     }
/*  467: 937 */     return pos;
/*  468:     */   }
/*  469:     */   
/*  470:     */   public int getTargetNode(XPathContext xctxt, int sourceNode)
/*  471:     */     throws TransformerException
/*  472:     */   {
/*  473: 954 */     int target = -1;
/*  474: 955 */     XPath countMatchPattern = getCountMatchPattern(xctxt, sourceNode);
/*  475: 957 */     if (3 == this.m_level) {
/*  476: 959 */       target = findPrecedingOrAncestorOrSelf(xctxt, this.m_fromMatchPattern, countMatchPattern, sourceNode, this);
/*  477:     */     } else {
/*  478: 965 */       target = findAncestor(xctxt, this.m_fromMatchPattern, countMatchPattern, sourceNode, this);
/*  479:     */     }
/*  480: 969 */     return target;
/*  481:     */   }
/*  482:     */   
/*  483:     */   NodeVector getMatchingAncestors(XPathContext xctxt, int node, boolean stopAtFirstFound)
/*  484:     */     throws TransformerException
/*  485:     */   {
/*  486: 990 */     NodeSetDTM ancestors = new NodeSetDTM(xctxt.getDTMManager());
/*  487: 991 */     XPath countMatchPattern = getCountMatchPattern(xctxt, node);
/*  488: 992 */     DTM dtm = xctxt.getDTM(node);
/*  489: 994 */     while (-1 != node)
/*  490:     */     {
/*  491: 996 */       if ((null != this.m_fromMatchPattern) && (this.m_fromMatchPattern.getMatchScore(xctxt, node) != (-1.0D / 0.0D)) && 
/*  492:     */       
/*  493:     */ 
/*  494:     */ 
/*  495:     */ 
/*  496:     */ 
/*  497:     */ 
/*  498:     */ 
/*  499:     */ 
/*  500:     */ 
/*  501:     */ 
/*  502:1007 */         (!stopAtFirstFound)) {
/*  503:     */         break;
/*  504:     */       }
/*  505:1011 */       if (null == countMatchPattern) {
/*  506:1012 */         System.out.println("Programmers error! countMatchPattern should never be null!");
/*  507:     */       }
/*  508:1015 */       if (countMatchPattern.getMatchScore(xctxt, node) != (-1.0D / 0.0D))
/*  509:     */       {
/*  510:1018 */         ancestors.addElement(node);
/*  511:1020 */         if (stopAtFirstFound) {
/*  512:     */           break;
/*  513:     */         }
/*  514:     */       }
/*  515:1024 */       node = dtm.getParent(node);
/*  516:     */     }
/*  517:1027 */     return ancestors;
/*  518:     */   }
/*  519:     */   
/*  520:     */   Locale getLocale(TransformerImpl transformer, int contextNode)
/*  521:     */     throws TransformerException
/*  522:     */   {
/*  523:1045 */     Locale locale = null;
/*  524:1047 */     if (null != this.m_lang_avt)
/*  525:     */     {
/*  526:1049 */       XPathContext xctxt = transformer.getXPathContext();
/*  527:1050 */       String langValue = this.m_lang_avt.evaluate(xctxt, contextNode, this);
/*  528:1052 */       if (null != langValue)
/*  529:     */       {
/*  530:1058 */         locale = new Locale(langValue.toUpperCase(), "");
/*  531:1061 */         if (null == locale)
/*  532:     */         {
/*  533:1063 */           transformer.getMsgMgr().warn(this, null, xctxt.getDTM(contextNode).getNode(contextNode), "WG_LOCALE_NOT_FOUND", new Object[] { langValue });
/*  534:     */           
/*  535:     */ 
/*  536:     */ 
/*  537:1067 */           locale = Locale.getDefault();
/*  538:     */         }
/*  539:     */       }
/*  540:     */     }
/*  541:     */     else
/*  542:     */     {
/*  543:1073 */       locale = Locale.getDefault();
/*  544:     */     }
/*  545:1076 */     return locale;
/*  546:     */   }
/*  547:     */   
/*  548:     */   private DecimalFormat getNumberFormatter(TransformerImpl transformer, int contextNode)
/*  549:     */     throws TransformerException
/*  550:     */   {
/*  551:1095 */     Locale locale = (Locale)getLocale(transformer, contextNode).clone();
/*  552:     */     
/*  553:     */ 
/*  554:1098 */     DecimalFormat formatter = null;
/*  555:     */     
/*  556:     */ 
/*  557:     */ 
/*  558:     */ 
/*  559:     */ 
/*  560:     */ 
/*  561:1105 */     String digitGroupSepValue = null != this.m_groupingSeparator_avt ? this.m_groupingSeparator_avt.evaluate(transformer.getXPathContext(), contextNode, this) : null;
/*  562:1113 */     if ((digitGroupSepValue != null) && (!this.m_groupingSeparator_avt.isSimple()) && (digitGroupSepValue.length() != 1)) {
/*  563:1116 */       transformer.getMsgMgr().warn(this, "WG_ILLEGAL_ATTRIBUTE_VALUE", new Object[] { "name", this.m_groupingSeparator_avt.getName() });
/*  564:     */     }
/*  565:1122 */     String nDigitsPerGroupValue = null != this.m_groupingSize_avt ? this.m_groupingSize_avt.evaluate(transformer.getXPathContext(), contextNode, this) : null;
/*  566:1128 */     if ((null != digitGroupSepValue) && (null != nDigitsPerGroupValue) && (digitGroupSepValue.length() > 0)) {
/*  567:     */       try
/*  568:     */       {
/*  569:1134 */         formatter = (DecimalFormat)NumberFormat.getNumberInstance(locale);
/*  570:1135 */         formatter.setGroupingSize(Integer.valueOf(nDigitsPerGroupValue).intValue());
/*  571:     */         
/*  572:     */ 
/*  573:1138 */         DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
/*  574:1139 */         symbols.setGroupingSeparator(digitGroupSepValue.charAt(0));
/*  575:1140 */         formatter.setDecimalFormatSymbols(symbols);
/*  576:1141 */         formatter.setGroupingUsed(true);
/*  577:     */       }
/*  578:     */       catch (NumberFormatException ex)
/*  579:     */       {
/*  580:1145 */         formatter.setGroupingUsed(false);
/*  581:     */       }
/*  582:     */     }
/*  583:1149 */     return formatter;
/*  584:     */   }
/*  585:     */   
/*  586:     */   String formatNumberList(TransformerImpl transformer, long[] list, int contextNode)
/*  587:     */     throws TransformerException
/*  588:     */   {
/*  589:1171 */     FastStringBuffer formattedNumber = StringBufferPool.get();
/*  590:     */     String numStr;
/*  591:     */     try
/*  592:     */     {
/*  593:1175 */       int nNumbers = list.length;int numberWidth = 1;
/*  594:1176 */       char numberType = '1';
/*  595:1177 */       String lastSepString = null;String formatTokenString = null;
/*  596:     */       
/*  597:     */ 
/*  598:     */ 
/*  599:     */ 
/*  600:     */ 
/*  601:     */ 
/*  602:     */ 
/*  603:     */ 
/*  604:1186 */       String lastSep = ".";
/*  605:1187 */       boolean isFirstToken = true;
/*  606:1188 */       String formatValue = null != this.m_format_avt ? this.m_format_avt.evaluate(transformer.getXPathContext(), contextNode, this) : null;
/*  607:1193 */       if (null == formatValue) {
/*  608:1194 */         formatValue = "1";
/*  609:     */       }
/*  610:1196 */       NumberFormatStringTokenizer formatTokenizer = new NumberFormatStringTokenizer(formatValue);
/*  611:     */       String formatToken;
/*  612:1201 */       for (int i = 0; i < nNumbers; i++)
/*  613:     */       {
/*  614:1205 */         if (formatTokenizer.hasMoreTokens())
/*  615:     */         {
/*  616:1207 */           formatToken = formatTokenizer.nextToken();
/*  617:1211 */           if (Character.isLetterOrDigit(formatToken.charAt(formatToken.length() - 1)))
/*  618:     */           {
/*  619:1214 */             numberWidth = formatToken.length();
/*  620:1215 */             numberType = formatToken.charAt(numberWidth - 1);
/*  621:     */           }
/*  622:1220 */           else if (formatTokenizer.isLetterOrDigitAhead())
/*  623:     */           {
/*  624:1222 */             formatTokenString = formatToken;
/*  625:1227 */             while (formatTokenizer.nextIsSep())
/*  626:     */             {
/*  627:1229 */               formatToken = formatTokenizer.nextToken();
/*  628:1230 */               formatTokenString = formatTokenString + formatToken;
/*  629:     */             }
/*  630:1237 */             if (!isFirstToken) {
/*  631:1238 */               lastSep = formatTokenString;
/*  632:     */             }
/*  633:1241 */             formatToken = formatTokenizer.nextToken();
/*  634:1242 */             numberWidth = formatToken.length();
/*  635:1243 */             numberType = formatToken.charAt(numberWidth - 1);
/*  636:     */           }
/*  637:     */           else
/*  638:     */           {
/*  639:1250 */             lastSepString = formatToken;
/*  640:1253 */             while (formatTokenizer.hasMoreTokens())
/*  641:     */             {
/*  642:1255 */               formatToken = formatTokenizer.nextToken();
/*  643:1256 */               lastSepString = lastSepString + formatToken;
/*  644:     */             }
/*  645:     */           }
/*  646:     */         }
/*  647:1265 */         if ((null != formatTokenString) && (isFirstToken)) {
/*  648:1267 */           formattedNumber.append(formatTokenString);
/*  649:1269 */         } else if ((null != lastSep) && (!isFirstToken)) {
/*  650:1270 */           formattedNumber.append(lastSep);
/*  651:     */         }
/*  652:1272 */         getFormattedNumber(transformer, contextNode, numberType, numberWidth, list[i], formattedNumber);
/*  653:     */         
/*  654:     */ 
/*  655:1275 */         isFirstToken = false;
/*  656:     */       }
/*  657:1280 */       while (formatTokenizer.isLetterOrDigitAhead()) {
/*  658:1282 */         formatTokenizer.nextToken();
/*  659:     */       }
/*  660:1285 */       if (lastSepString != null) {
/*  661:1286 */         formattedNumber.append(lastSepString);
/*  662:     */       }
/*  663:1288 */       while (formatTokenizer.hasMoreTokens())
/*  664:     */       {
/*  665:1290 */         formatToken = formatTokenizer.nextToken();
/*  666:     */         
/*  667:1292 */         formattedNumber.append(formatToken);
/*  668:     */       }
/*  669:1295 */       numStr = formattedNumber.toString();
/*  670:     */     }
/*  671:     */     finally
/*  672:     */     {
/*  673:1299 */       StringBufferPool.free(formattedNumber);
/*  674:     */     }
/*  675:1302 */     return numStr;
/*  676:     */   }
/*  677:     */   
/*  678:     */   private void getFormattedNumber(TransformerImpl transformer, int contextNode, char numberType, int numberWidth, long listElement, FastStringBuffer formattedNumber)
/*  679:     */     throws TransformerException
/*  680:     */   {
/*  681:1330 */     String letterVal = this.m_lettervalue_avt != null ? this.m_lettervalue_avt.evaluate(transformer.getXPathContext(), contextNode, this) : null;
/*  682:     */     
/*  683:     */ 
/*  684:     */ 
/*  685:     */ 
/*  686:     */ 
/*  687:     */ 
/*  688:     */ 
/*  689:1338 */     CharArrayWrapper alphaCountTable = null;
/*  690:     */     
/*  691:1340 */     XResourceBundle thisBundle = null;
/*  692:1342 */     switch (numberType)
/*  693:     */     {
/*  694:     */     case 'A': 
/*  695:1345 */       if (null == this.m_alphaCountTable)
/*  696:     */       {
/*  697:1346 */         thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", getLocale(transformer, contextNode));
/*  698:     */         
/*  699:     */ 
/*  700:1349 */         this.m_alphaCountTable = ((CharArrayWrapper)thisBundle.getObject("alphabet"));
/*  701:     */       }
/*  702:1351 */       int2alphaCount(listElement, this.m_alphaCountTable, formattedNumber);
/*  703:1352 */       break;
/*  704:     */     case 'a': 
/*  705:1354 */       if (null == this.m_alphaCountTable)
/*  706:     */       {
/*  707:1355 */         thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", getLocale(transformer, contextNode));
/*  708:     */         
/*  709:     */ 
/*  710:1358 */         this.m_alphaCountTable = ((CharArrayWrapper)thisBundle.getObject("alphabet"));
/*  711:     */       }
/*  712:1360 */       FastStringBuffer stringBuf = StringBufferPool.get();
/*  713:     */       try
/*  714:     */       {
/*  715:1364 */         int2alphaCount(listElement, this.m_alphaCountTable, stringBuf);
/*  716:1365 */         formattedNumber.append(stringBuf.toString().toLowerCase(getLocale(transformer, contextNode)));
/*  717:     */       }
/*  718:     */       finally
/*  719:     */       {
/*  720:1371 */         StringBufferPool.free(stringBuf);
/*  721:     */       }
/*  722:     */     case 'I': 
/*  723:1375 */       formattedNumber.append(long2roman(listElement, true));
/*  724:1376 */       break;
/*  725:     */     case 'i': 
/*  726:1378 */       formattedNumber.append(long2roman(listElement, true).toLowerCase(getLocale(transformer, contextNode)));
/*  727:     */       
/*  728:     */ 
/*  729:1381 */       break;
/*  730:     */     case 'あ': 
/*  731:1385 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "HA"));
/*  732:1388 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  733:1390 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  734:     */       } else {
/*  735:1392 */         formattedNumber.append(int2singlealphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet")));
/*  736:     */       }
/*  737:1397 */       break;
/*  738:     */     case 'い': 
/*  739:1402 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "HI"));
/*  740:1405 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  741:1407 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  742:     */       } else {
/*  743:1409 */         formattedNumber.append(int2singlealphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet")));
/*  744:     */       }
/*  745:1414 */       break;
/*  746:     */     case 'ア': 
/*  747:1419 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "A"));
/*  748:1422 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  749:1424 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  750:     */       } else {
/*  751:1426 */         formattedNumber.append(int2singlealphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet")));
/*  752:     */       }
/*  753:1431 */       break;
/*  754:     */     case 'イ': 
/*  755:1436 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "I"));
/*  756:1439 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  757:1441 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  758:     */       } else {
/*  759:1443 */         formattedNumber.append(int2singlealphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet")));
/*  760:     */       }
/*  761:1448 */       break;
/*  762:     */     case '一': 
/*  763:1453 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("zh", "CN"));
/*  764:1456 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  765:1459 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  766:     */       } else {
/*  767:1462 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  768:     */       }
/*  769:1466 */       break;
/*  770:     */     case '壹': 
/*  771:1471 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("zh", "TW"));
/*  772:1474 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  773:1476 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  774:     */       } else {
/*  775:1478 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  776:     */       }
/*  777:1482 */       break;
/*  778:     */     case '๑': 
/*  779:1487 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("th", ""));
/*  780:1490 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  781:1492 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  782:     */       } else {
/*  783:1494 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  784:     */       }
/*  785:1498 */       break;
/*  786:     */     case 'א': 
/*  787:1503 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("he", ""));
/*  788:1506 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  789:1508 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  790:     */       } else {
/*  791:1510 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  792:     */       }
/*  793:1514 */       break;
/*  794:     */     case 'ა': 
/*  795:1519 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ka", ""));
/*  796:1522 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  797:1524 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  798:     */       } else {
/*  799:1526 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  800:     */       }
/*  801:1530 */       break;
/*  802:     */     case 'α': 
/*  803:1535 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("el", ""));
/*  804:1538 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  805:1540 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  806:     */       } else {
/*  807:1542 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  808:     */       }
/*  809:1546 */       break;
/*  810:     */     case 'а': 
/*  811:1551 */       thisBundle = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("cy", ""));
/*  812:1554 */       if ((letterVal != null) && (letterVal.equals("traditional"))) {
/*  813:1556 */         formattedNumber.append(tradAlphaCount(listElement, thisBundle));
/*  814:     */       } else {
/*  815:1558 */         int2alphaCount(listElement, (CharArrayWrapper)thisBundle.getObject("alphabet"), formattedNumber);
/*  816:     */       }
/*  817:1562 */       break;
/*  818:     */     default: 
/*  819:1565 */       DecimalFormat formatter = getNumberFormatter(transformer, contextNode);
/*  820:1566 */       String padString = formatter == null ? String.valueOf(0) : formatter.format(0L);
/*  821:1567 */       String numString = formatter == null ? String.valueOf(listElement) : formatter.format(listElement);
/*  822:1568 */       int nPadding = numberWidth - numString.length();
/*  823:1570 */       for (int k = 0; k < nPadding; k++) {
/*  824:1572 */         formattedNumber.append(padString);
/*  825:     */       }
/*  826:1575 */       formattedNumber.append(numString);
/*  827:     */     }
/*  828:     */   }
/*  829:     */   
/*  830:     */   String getZeroString()
/*  831:     */   {
/*  832:1585 */     return "0";
/*  833:     */   }
/*  834:     */   
/*  835:     */   protected String int2singlealphaCount(long val, CharArrayWrapper table)
/*  836:     */   {
/*  837:1603 */     int radix = table.getLength();
/*  838:1606 */     if (val > radix) {
/*  839:1608 */       return getZeroString();
/*  840:     */     }
/*  841:1611 */     return new Character(table.getChar((int)val - 1)).toString();
/*  842:     */   }
/*  843:     */   
/*  844:     */   protected void int2alphaCount(long val, CharArrayWrapper aTable, FastStringBuffer stringBuf)
/*  845:     */   {
/*  846:1632 */     int radix = aTable.getLength();
/*  847:1633 */     char[] table = new char[radix];
/*  848:1638 */     for (int i = 0; i < radix - 1; i++) {
/*  849:1640 */       table[(i + 1)] = aTable.getChar(i);
/*  850:     */     }
/*  851:1643 */     table[0] = aTable.getChar(i);
/*  852:     */     
/*  853:     */ 
/*  854:     */ 
/*  855:     */ 
/*  856:1648 */     char[] buf = new char[100];
/*  857:     */     
/*  858:     */ 
/*  859:     */ 
/*  860:     */ 
/*  861:     */ 
/*  862:     */ 
/*  863:     */ 
/*  864:1656 */     int charPos = buf.length - 1;
/*  865:     */     
/*  866:     */ 
/*  867:1659 */     int lookupIndex = 1;
/*  868:     */     
/*  869:     */ 
/*  870:     */ 
/*  871:     */ 
/*  872:     */ 
/*  873:     */ 
/*  874:     */ 
/*  875:     */ 
/*  876:     */ 
/*  877:     */ 
/*  878:     */ 
/*  879:     */ 
/*  880:     */ 
/*  881:     */ 
/*  882:     */ 
/*  883:     */ 
/*  884:     */ 
/*  885:     */ 
/*  886:     */ 
/*  887:     */ 
/*  888:     */ 
/*  889:     */ 
/*  890:     */ 
/*  891:     */ 
/*  892:     */ 
/*  893:1685 */     long correction = 0L;
/*  894:     */     do
/*  895:     */     {
/*  896:1694 */       correction = (lookupIndex == 0) || ((correction != 0L) && (lookupIndex == radix - 1)) ? radix - 1 : 0L;
/*  897:     */       
/*  898:     */ 
/*  899:     */ 
/*  900:     */ 
/*  901:1699 */       lookupIndex = (int)(val + correction) % radix;
/*  902:     */       
/*  903:     */ 
/*  904:1702 */       val /= radix;
/*  905:1705 */       if ((lookupIndex == 0) && (val == 0L)) {
/*  906:     */         break;
/*  907:     */       }
/*  908:1709 */       buf[(charPos--)] = table[lookupIndex];
/*  909:1711 */     } while (val > 0L);
/*  910:1713 */     stringBuf.append(buf, charPos + 1, buf.length - charPos - 1);
/*  911:     */   }
/*  912:     */   
/*  913:     */   protected String tradAlphaCount(long val, XResourceBundle thisBundle)
/*  914:     */   {
/*  915:1733 */     if (val > 9223372036854775807L)
/*  916:     */     {
/*  917:1735 */       error("ER_NUMBER_TOO_BIG");
/*  918:1736 */       return "#error";
/*  919:     */     }
/*  920:1738 */     char[] table = null;
/*  921:     */     
/*  922:     */ 
/*  923:1741 */     int lookupIndex = 1;
/*  924:     */     
/*  925:     */ 
/*  926:     */ 
/*  927:     */ 
/*  928:1746 */     char[] buf = new char[100];
/*  929:     */     
/*  930:     */ 
/*  931:     */ 
/*  932:     */ 
/*  933:     */ 
/*  934:     */ 
/*  935:     */ 
/*  936:1754 */     int charPos = 0;
/*  937:     */     
/*  938:     */ 
/*  939:1757 */     IntArrayWrapper groups = (IntArrayWrapper)thisBundle.getObject("numberGroups");
/*  940:     */     
/*  941:     */ 
/*  942:1760 */     StringArrayWrapper tables = (StringArrayWrapper)thisBundle.getObject("tables");
/*  943:     */     
/*  944:     */ 
/*  945:     */ 
/*  946:     */ 
/*  947:1765 */     String numbering = thisBundle.getString("numbering");
/*  948:1768 */     if (numbering.equals("multiplicative-additive"))
/*  949:     */     {
/*  950:1770 */       String mult_order = thisBundle.getString("multiplierOrder");
/*  951:1771 */       LongArrayWrapper multiplier = (LongArrayWrapper)thisBundle.getObject("multiplier");
/*  952:     */       
/*  953:1773 */       CharArrayWrapper zeroChar = (CharArrayWrapper)thisBundle.getObject("zero");
/*  954:1774 */       int i = 0;
/*  955:1777 */       while ((i < multiplier.getLength()) && (val < multiplier.getLong(i))) {
/*  956:1779 */         i++;
/*  957:     */       }
/*  958:     */       do
/*  959:     */       {
/*  960:1784 */         if (i >= multiplier.getLength()) {
/*  961:     */           break;
/*  962:     */         }
/*  963:1790 */         if (val < multiplier.getLong(i))
/*  964:     */         {
/*  965:1792 */           if (zeroChar.getLength() == 0)
/*  966:     */           {
/*  967:1794 */             i++;
/*  968:     */           }
/*  969:     */           else
/*  970:     */           {
/*  971:1798 */             if (buf[(charPos - 1)] != zeroChar.getChar(0)) {
/*  972:1799 */               buf[(charPos++)] = zeroChar.getChar(0);
/*  973:     */             }
/*  974:1801 */             i++;
/*  975:     */           }
/*  976:     */         }
/*  977:1804 */         else if (val >= multiplier.getLong(i))
/*  978:     */         {
/*  979:1806 */           long mult = val / multiplier.getLong(i);
/*  980:     */           
/*  981:1808 */           val %= multiplier.getLong(i);
/*  982:     */           
/*  983:1810 */           int k = 0;
/*  984:1812 */           while (k < groups.getLength())
/*  985:     */           {
/*  986:1814 */             lookupIndex = 1;
/*  987:1816 */             if (mult / groups.getInt(k) <= 0L)
/*  988:     */             {
/*  989:1817 */               k++;
/*  990:     */             }
/*  991:     */             else
/*  992:     */             {
/*  993:1822 */               CharArrayWrapper THEletters = (CharArrayWrapper)thisBundle.getObject(tables.getString(k));
/*  994:     */               
/*  995:1824 */               table = new char[THEletters.getLength() + 1];
/*  996:1828 */               for (int j = 0; j < THEletters.getLength(); j++) {
/*  997:1830 */                 table[(j + 1)] = THEletters.getChar(j);
/*  998:     */               }
/*  999:1833 */               table[0] = THEletters.getChar(j - 1);
/* 1000:     */               
/* 1001:     */ 
/* 1002:1836 */               lookupIndex = (int)mult / groups.getInt(k);
/* 1003:1839 */               if ((lookupIndex == 0) && (mult == 0L)) {
/* 1004:     */                 break;
/* 1005:     */               }
/* 1006:1842 */               char multiplierChar = ((CharArrayWrapper)thisBundle.getObject("multiplierChar")).getChar(i);
/* 1007:1846 */               if (lookupIndex < table.length)
/* 1008:     */               {
/* 1009:1848 */                 if (mult_order.equals("precedes"))
/* 1010:     */                 {
/* 1011:1850 */                   buf[(charPos++)] = multiplierChar;
/* 1012:1851 */                   buf[(charPos++)] = table[lookupIndex]; break;
/* 1013:     */                 }
/* 1014:1857 */                 if ((lookupIndex != 1) || (i != multiplier.getLength() - 1)) {
/* 1015:1859 */                   buf[(charPos++)] = table[lookupIndex];
/* 1016:     */                 }
/* 1017:1861 */                 buf[(charPos++)] = multiplierChar;
/* 1018:     */                 
/* 1019:     */ 
/* 1020:1864 */                 break;
/* 1021:     */               }
/* 1022:1867 */               return "#error";
/* 1023:     */             }
/* 1024:     */           }
/* 1025:1871 */           i++;
/* 1026:     */         }
/* 1027:1874 */       } while (i < multiplier.getLength());
/* 1028:     */     }
/* 1029:1878 */     int count = 0;
/* 1030:1882 */     while (count < groups.getLength()) {
/* 1031:1884 */       if (val / groups.getInt(count) <= 0L)
/* 1032:     */       {
/* 1033:1885 */         count++;
/* 1034:     */       }
/* 1035:     */       else
/* 1036:     */       {
/* 1037:1888 */         CharArrayWrapper theletters = (CharArrayWrapper)thisBundle.getObject(tables.getString(count));
/* 1038:     */         
/* 1039:1890 */         table = new char[theletters.getLength() + 1];
/* 1040:1895 */         for (int j = 0; j < theletters.getLength(); j++) {
/* 1041:1897 */           table[(j + 1)] = theletters.getChar(j);
/* 1042:     */         }
/* 1043:1900 */         table[0] = theletters.getChar(j - 1);
/* 1044:     */         
/* 1045:     */ 
/* 1046:1903 */         lookupIndex = (int)val / groups.getInt(count);
/* 1047:     */         
/* 1048:     */ 
/* 1049:1906 */         val %= groups.getInt(count);
/* 1050:1909 */         if ((lookupIndex == 0) && (val == 0L)) {
/* 1051:     */           break;
/* 1052:     */         }
/* 1053:1912 */         if (lookupIndex < table.length) {
/* 1054:1916 */           buf[(charPos++)] = table[lookupIndex];
/* 1055:     */         } else {
/* 1056:1919 */           return "#error";
/* 1057:     */         }
/* 1058:1921 */         count++;
/* 1059:     */       }
/* 1060:     */     }
/* 1061:1926 */     return new String(buf, 0, charPos);
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   protected String long2roman(long val, boolean prefixesAreOK)
/* 1065:     */   {
/* 1066:1941 */     if (val <= 0L) {
/* 1067:1943 */       return getZeroString();
/* 1068:     */     }
/* 1069:1946 */     String roman = "";
/* 1070:1947 */     int place = 0;
/* 1071:1949 */     if (val <= 3999L) {
/* 1072:     */       do
/* 1073:     */       {
/* 1074:1953 */         while (val >= m_romanConvertTable[place].m_postValue)
/* 1075:     */         {
/* 1076:1955 */           roman = roman + m_romanConvertTable[place].m_postLetter;
/* 1077:1956 */           val -= m_romanConvertTable[place].m_postValue;
/* 1078:     */         }
/* 1079:1959 */         if (prefixesAreOK) {
/* 1080:1961 */           if (val >= m_romanConvertTable[place].m_preValue)
/* 1081:     */           {
/* 1082:1963 */             roman = roman + m_romanConvertTable[place].m_preLetter;
/* 1083:1964 */             val -= m_romanConvertTable[place].m_preValue;
/* 1084:     */           }
/* 1085:     */         }
/* 1086:1968 */         place++;
/* 1087:1970 */       } while (val > 0L);
/* 1088:     */     } else {
/* 1089:1974 */       roman = "#error";
/* 1090:     */     }
/* 1091:1977 */     return roman;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void callChildVisitors(XSLTVisitor visitor, boolean callAttrs)
/* 1095:     */   {
/* 1096:1986 */     if (callAttrs)
/* 1097:     */     {
/* 1098:1988 */       if (null != this.m_countMatchPattern) {
/* 1099:1989 */         this.m_countMatchPattern.getExpression().callVisitors(this.m_countMatchPattern, visitor);
/* 1100:     */       }
/* 1101:1990 */       if (null != this.m_fromMatchPattern) {
/* 1102:1991 */         this.m_fromMatchPattern.getExpression().callVisitors(this.m_fromMatchPattern, visitor);
/* 1103:     */       }
/* 1104:1992 */       if (null != this.m_valueExpr) {
/* 1105:1993 */         this.m_valueExpr.getExpression().callVisitors(this.m_valueExpr, visitor);
/* 1106:     */       }
/* 1107:1995 */       if (null != this.m_format_avt) {
/* 1108:1996 */         this.m_format_avt.callVisitors(visitor);
/* 1109:     */       }
/* 1110:1997 */       if (null != this.m_groupingSeparator_avt) {
/* 1111:1998 */         this.m_groupingSeparator_avt.callVisitors(visitor);
/* 1112:     */       }
/* 1113:1999 */       if (null != this.m_groupingSize_avt) {
/* 1114:2000 */         this.m_groupingSize_avt.callVisitors(visitor);
/* 1115:     */       }
/* 1116:2001 */       if (null != this.m_lang_avt) {
/* 1117:2002 */         this.m_lang_avt.callVisitors(visitor);
/* 1118:     */       }
/* 1119:2003 */       if (null != this.m_lettervalue_avt) {
/* 1120:2004 */         this.m_lettervalue_avt.callVisitors(visitor);
/* 1121:     */       }
/* 1122:     */     }
/* 1123:2007 */     super.callChildVisitors(visitor, callAttrs);
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   class NumberFormatStringTokenizer
/* 1127:     */   {
/* 1128:     */     private int currentPosition;
/* 1129:     */     private int maxPosition;
/* 1130:     */     private String str;
/* 1131:     */     
/* 1132:     */     public NumberFormatStringTokenizer(String str)
/* 1133:     */     {
/* 1134:2034 */       this.str = str;
/* 1135:2035 */       this.maxPosition = str.length();
/* 1136:     */     }
/* 1137:     */     
/* 1138:     */     public void reset()
/* 1139:     */     {
/* 1140:2043 */       this.currentPosition = 0;
/* 1141:     */     }
/* 1142:     */     
/* 1143:     */     public String nextToken()
/* 1144:     */     {
/* 1145:2056 */       if (this.currentPosition >= this.maxPosition) {
/* 1146:2058 */         throw new NoSuchElementException();
/* 1147:     */       }
/* 1148:2061 */       int start = this.currentPosition;
/* 1149:2064 */       while ((this.currentPosition < this.maxPosition) && (Character.isLetterOrDigit(this.str.charAt(this.currentPosition)))) {
/* 1150:2066 */         this.currentPosition += 1;
/* 1151:     */       }
/* 1152:2069 */       if ((start == this.currentPosition) && (!Character.isLetterOrDigit(this.str.charAt(this.currentPosition)))) {
/* 1153:2072 */         this.currentPosition += 1;
/* 1154:     */       }
/* 1155:2075 */       return this.str.substring(start, this.currentPosition);
/* 1156:     */     }
/* 1157:     */     
/* 1158:     */     public boolean isLetterOrDigitAhead()
/* 1159:     */     {
/* 1160:2086 */       int pos = this.currentPosition;
/* 1161:2088 */       while (pos < this.maxPosition)
/* 1162:     */       {
/* 1163:2090 */         if (Character.isLetterOrDigit(this.str.charAt(pos))) {
/* 1164:2091 */           return true;
/* 1165:     */         }
/* 1166:2093 */         pos++;
/* 1167:     */       }
/* 1168:2096 */       return false;
/* 1169:     */     }
/* 1170:     */     
/* 1171:     */     public boolean nextIsSep()
/* 1172:     */     {
/* 1173:2107 */       if (Character.isLetterOrDigit(this.str.charAt(this.currentPosition))) {
/* 1174:2108 */         return false;
/* 1175:     */       }
/* 1176:2110 */       return true;
/* 1177:     */     }
/* 1178:     */     
/* 1179:     */     public boolean hasMoreTokens()
/* 1180:     */     {
/* 1181:2122 */       return this.currentPosition < this.maxPosition;
/* 1182:     */     }
/* 1183:     */     
/* 1184:     */     public int countTokens()
/* 1185:     */     {
/* 1186:2137 */       int count = 0;
/* 1187:2138 */       int currpos = this.currentPosition;
/* 1188:2140 */       while (currpos < this.maxPosition)
/* 1189:     */       {
/* 1190:2142 */         int start = currpos;
/* 1191:2145 */         while ((currpos < this.maxPosition) && (Character.isLetterOrDigit(this.str.charAt(currpos)))) {
/* 1192:2147 */           currpos++;
/* 1193:     */         }
/* 1194:2150 */         if ((start == currpos) && (!Character.isLetterOrDigit(this.str.charAt(currpos)))) {
/* 1195:2153 */           currpos++;
/* 1196:     */         }
/* 1197:2156 */         count++;
/* 1198:     */       }
/* 1199:2159 */       return count;
/* 1200:     */     }
/* 1201:     */   }
/* 1202:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.ElemNumber
 * JD-Core Version:    0.7.0.1
 */