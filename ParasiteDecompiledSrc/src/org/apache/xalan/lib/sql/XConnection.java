/*    1:     */ package org.apache.xalan.lib.sql;
/*    2:     */ 
/*    3:     */ import java.sql.Connection;
/*    4:     */ import java.sql.SQLException;
/*    5:     */ import java.sql.SQLWarning;
/*    6:     */ import java.util.Hashtable;
/*    7:     */ import java.util.Properties;
/*    8:     */ import java.util.StringTokenizer;
/*    9:     */ import java.util.Vector;
/*   10:     */ import javax.xml.transform.ErrorListener;
/*   11:     */ import javax.xml.transform.TransformerException;
/*   12:     */ import org.apache.xalan.extensions.ExpressionContext;
/*   13:     */ import org.apache.xml.dtm.DTM;
/*   14:     */ import org.apache.xml.dtm.DTMIterator;
/*   15:     */ import org.apache.xml.dtm.DTMManager;
/*   16:     */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*   17:     */ import org.apache.xml.dtm.ref.DTMNodeIterator;
/*   18:     */ import org.apache.xml.dtm.ref.DTMNodeProxy;
/*   19:     */ import org.apache.xpath.XPathContext;
/*   20:     */ import org.apache.xpath.XPathContext.XPathExpressionContext;
/*   21:     */ import org.apache.xpath.axes.NodeSequence;
/*   22:     */ import org.apache.xpath.objects.XBooleanStatic;
/*   23:     */ import org.apache.xpath.objects.XNodeSet;
/*   24:     */ import org.w3c.dom.Element;
/*   25:     */ import org.w3c.dom.NamedNodeMap;
/*   26:     */ import org.w3c.dom.Node;
/*   27:     */ import org.w3c.dom.NodeList;
/*   28:     */ 
/*   29:     */ public class XConnection
/*   30:     */ {
/*   31:     */   private static final boolean DEBUG = false;
/*   32:  80 */   private ConnectionPool m_ConnectionPool = null;
/*   33:  87 */   private Connection m_Connection = null;
/*   34: 101 */   private boolean m_DefaultPoolingEnabled = false;
/*   35: 111 */   private Vector m_OpenSQLDocuments = new Vector();
/*   36: 119 */   private ConnectionPoolManager m_PoolMgr = new ConnectionPoolManager();
/*   37: 125 */   private Vector m_ParameterList = new Vector();
/*   38: 133 */   private Exception m_Error = null;
/*   39: 141 */   private SQLDocument m_LastSQLDocumentWithError = null;
/*   40: 148 */   private boolean m_FullErrors = false;
/*   41: 157 */   private SQLQueryParser m_QueryParser = new SQLQueryParser();
/*   42: 161 */   private boolean m_IsDefaultPool = false;
/*   43: 169 */   private boolean m_IsStreamingEnabled = true;
/*   44: 174 */   private boolean m_InlineVariables = false;
/*   45: 182 */   private boolean m_IsMultipleResultsEnabled = false;
/*   46: 189 */   private boolean m_IsStatementCachingEnabled = false;
/*   47:     */   
/*   48:     */   public XConnection() {}
/*   49:     */   
/*   50:     */   public XConnection(ExpressionContext exprContext, String connPoolName)
/*   51:     */   {
/*   52: 210 */     connect(exprContext, connPoolName);
/*   53:     */   }
/*   54:     */   
/*   55:     */   public XConnection(ExpressionContext exprContext, String driver, String dbURL)
/*   56:     */   {
/*   57: 220 */     connect(exprContext, driver, dbURL);
/*   58:     */   }
/*   59:     */   
/*   60:     */   public XConnection(ExpressionContext exprContext, NodeList list)
/*   61:     */   {
/*   62: 229 */     connect(exprContext, list);
/*   63:     */   }
/*   64:     */   
/*   65:     */   public XConnection(ExpressionContext exprContext, String driver, String dbURL, String user, String password)
/*   66:     */   {
/*   67: 241 */     connect(exprContext, driver, dbURL, user, password);
/*   68:     */   }
/*   69:     */   
/*   70:     */   public XConnection(ExpressionContext exprContext, String driver, String dbURL, Element protocolElem)
/*   71:     */   {
/*   72: 252 */     connect(exprContext, driver, dbURL, protocolElem);
/*   73:     */   }
/*   74:     */   
/*   75:     */   public XBooleanStatic connect(ExpressionContext exprContext, String name)
/*   76:     */   {
/*   77:     */     try
/*   78:     */     {
/*   79: 273 */       this.m_ConnectionPool = this.m_PoolMgr.getPool(name);
/*   80: 275 */       if (this.m_ConnectionPool == null)
/*   81:     */       {
/*   82: 278 */         ConnectionPool pool = new JNDIConnectionPool(name);
/*   83: 280 */         if (pool.testConnection())
/*   84:     */         {
/*   85: 286 */           this.m_PoolMgr.registerPool(name, pool);
/*   86: 287 */           this.m_ConnectionPool = pool;
/*   87:     */           
/*   88: 289 */           this.m_IsDefaultPool = false;
/*   89: 290 */           return new XBooleanStatic(true);
/*   90:     */         }
/*   91: 294 */         throw new IllegalArgumentException("Invalid ConnectionPool name or JNDI Datasource path: " + name);
/*   92:     */       }
/*   93: 300 */       this.m_IsDefaultPool = false;
/*   94: 301 */       return new XBooleanStatic(true);
/*   95:     */     }
/*   96:     */     catch (Exception e)
/*   97:     */     {
/*   98: 306 */       setError(e, exprContext);
/*   99:     */     }
/*  100: 307 */     return new XBooleanStatic(false);
/*  101:     */   }
/*  102:     */   
/*  103:     */   public XBooleanStatic connect(ExpressionContext exprContext, String driver, String dbURL)
/*  104:     */   {
/*  105:     */     try
/*  106:     */     {
/*  107: 324 */       init(driver, dbURL, new Properties());
/*  108: 325 */       return new XBooleanStatic(true);
/*  109:     */     }
/*  110:     */     catch (SQLException e)
/*  111:     */     {
/*  112: 329 */       setError(e, exprContext);
/*  113: 330 */       return new XBooleanStatic(false);
/*  114:     */     }
/*  115:     */     catch (Exception e)
/*  116:     */     {
/*  117: 334 */       setError(e, exprContext);
/*  118:     */     }
/*  119: 335 */     return new XBooleanStatic(false);
/*  120:     */   }
/*  121:     */   
/*  122:     */   public XBooleanStatic connect(ExpressionContext exprContext, Element protocolElem)
/*  123:     */   {
/*  124:     */     try
/*  125:     */     {
/*  126: 348 */       initFromElement(protocolElem);
/*  127: 349 */       return new XBooleanStatic(true);
/*  128:     */     }
/*  129:     */     catch (SQLException e)
/*  130:     */     {
/*  131: 353 */       setError(e, exprContext);
/*  132: 354 */       return new XBooleanStatic(false);
/*  133:     */     }
/*  134:     */     catch (Exception e)
/*  135:     */     {
/*  136: 358 */       setError(e, exprContext);
/*  137:     */     }
/*  138: 359 */     return new XBooleanStatic(false);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public XBooleanStatic connect(ExpressionContext exprContext, NodeList list)
/*  142:     */   {
/*  143:     */     try
/*  144:     */     {
/*  145: 372 */       initFromElement((Element)list.item(0));
/*  146: 373 */       return new XBooleanStatic(true);
/*  147:     */     }
/*  148:     */     catch (SQLException e)
/*  149:     */     {
/*  150: 377 */       setError(e, exprContext);
/*  151: 378 */       return new XBooleanStatic(false);
/*  152:     */     }
/*  153:     */     catch (Exception e)
/*  154:     */     {
/*  155: 382 */       setError(e, exprContext);
/*  156:     */     }
/*  157: 383 */     return new XBooleanStatic(false);
/*  158:     */   }
/*  159:     */   
/*  160:     */   public XBooleanStatic connect(ExpressionContext exprContext, String driver, String dbURL, String user, String password)
/*  161:     */   {
/*  162:     */     try
/*  163:     */     {
/*  164: 400 */       Properties prop = new Properties();
/*  165: 401 */       prop.put("user", user);
/*  166: 402 */       prop.put("password", password);
/*  167:     */       
/*  168: 404 */       init(driver, dbURL, prop);
/*  169:     */       
/*  170: 406 */       return new XBooleanStatic(true);
/*  171:     */     }
/*  172:     */     catch (SQLException e)
/*  173:     */     {
/*  174: 410 */       setError(e, exprContext);
/*  175: 411 */       return new XBooleanStatic(false);
/*  176:     */     }
/*  177:     */     catch (Exception e)
/*  178:     */     {
/*  179: 415 */       setError(e, exprContext);
/*  180:     */     }
/*  181: 416 */     return new XBooleanStatic(false);
/*  182:     */   }
/*  183:     */   
/*  184:     */   public XBooleanStatic connect(ExpressionContext exprContext, String driver, String dbURL, Element protocolElem)
/*  185:     */   {
/*  186:     */     try
/*  187:     */     {
/*  188: 434 */       Properties prop = new Properties();
/*  189:     */       
/*  190: 436 */       NamedNodeMap atts = protocolElem.getAttributes();
/*  191: 438 */       for (int i = 0; i < atts.getLength(); i++) {
/*  192: 440 */         prop.put(atts.item(i).getNodeName(), atts.item(i).getNodeValue());
/*  193:     */       }
/*  194: 443 */       init(driver, dbURL, prop);
/*  195:     */       
/*  196: 445 */       return new XBooleanStatic(true);
/*  197:     */     }
/*  198:     */     catch (SQLException e)
/*  199:     */     {
/*  200: 449 */       setError(e, exprContext);
/*  201: 450 */       return new XBooleanStatic(false);
/*  202:     */     }
/*  203:     */     catch (Exception e)
/*  204:     */     {
/*  205: 454 */       setError(e, exprContext);
/*  206:     */     }
/*  207: 455 */     return new XBooleanStatic(false);
/*  208:     */   }
/*  209:     */   
/*  210:     */   private void initFromElement(Element e)
/*  211:     */     throws SQLException
/*  212:     */   {
/*  213: 490 */     Properties prop = new Properties();
/*  214: 491 */     String driver = "";
/*  215: 492 */     String dbURL = "";
/*  216: 493 */     Node n = e.getFirstChild();
/*  217: 495 */     if (null == n) {
/*  218:     */       return;
/*  219:     */     }
/*  220:     */     do
/*  221:     */     {
/*  222: 499 */       String nName = n.getNodeName();
/*  223: 501 */       if (nName.equalsIgnoreCase("dbdriver"))
/*  224:     */       {
/*  225: 503 */         driver = "";
/*  226: 504 */         Node n1 = n.getFirstChild();
/*  227: 505 */         if (null != n1) {
/*  228: 507 */           driver = n1.getNodeValue();
/*  229:     */         }
/*  230:     */       }
/*  231: 511 */       if (nName.equalsIgnoreCase("dburl"))
/*  232:     */       {
/*  233: 513 */         dbURL = "";
/*  234: 514 */         Node n1 = n.getFirstChild();
/*  235: 515 */         if (null != n1) {
/*  236: 517 */           dbURL = n1.getNodeValue();
/*  237:     */         }
/*  238:     */       }
/*  239: 521 */       if (nName.equalsIgnoreCase("password"))
/*  240:     */       {
/*  241: 523 */         String s = "";
/*  242: 524 */         Node n1 = n.getFirstChild();
/*  243: 525 */         if (null != n1) {
/*  244: 527 */           s = n1.getNodeValue();
/*  245:     */         }
/*  246: 529 */         prop.put("password", s);
/*  247:     */       }
/*  248: 532 */       if (nName.equalsIgnoreCase("user"))
/*  249:     */       {
/*  250: 534 */         String s = "";
/*  251: 535 */         Node n1 = n.getFirstChild();
/*  252: 536 */         if (null != n1) {
/*  253: 538 */           s = n1.getNodeValue();
/*  254:     */         }
/*  255: 540 */         prop.put("user", s);
/*  256:     */       }
/*  257: 543 */       if (nName.equalsIgnoreCase("protocol"))
/*  258:     */       {
/*  259: 545 */         String Name = "";
/*  260:     */         
/*  261: 547 */         NamedNodeMap attrs = n.getAttributes();
/*  262: 548 */         Node n1 = attrs.getNamedItem("name");
/*  263: 549 */         if (null != n1)
/*  264:     */         {
/*  265: 551 */           String s = "";
/*  266: 552 */           Name = n1.getNodeValue();
/*  267:     */           
/*  268: 554 */           Node n2 = n.getFirstChild();
/*  269: 555 */           if (null != n2) {
/*  270: 555 */             s = n2.getNodeValue();
/*  271:     */           }
/*  272: 557 */           prop.put(Name, s);
/*  273:     */         }
/*  274:     */       }
/*  275: 561 */     } while ((n = n.getNextSibling()) != null);
/*  276: 563 */     init(driver, dbURL, prop);
/*  277:     */   }
/*  278:     */   
/*  279:     */   private void init(String driver, String dbURL, Properties prop)
/*  280:     */     throws SQLException
/*  281:     */   {
/*  282: 580 */     Connection con = null;
/*  283:     */     
/*  284:     */ 
/*  285:     */ 
/*  286:     */ 
/*  287: 585 */     String user = prop.getProperty("user");
/*  288: 586 */     if (user == null) {
/*  289: 586 */       user = "";
/*  290:     */     }
/*  291: 588 */     String passwd = prop.getProperty("password");
/*  292: 589 */     if (passwd == null) {
/*  293: 589 */       passwd = "";
/*  294:     */     }
/*  295: 592 */     String poolName = driver + dbURL + user + passwd;
/*  296: 593 */     ConnectionPool cpool = this.m_PoolMgr.getPool(poolName);
/*  297: 595 */     if (cpool == null)
/*  298:     */     {
/*  299: 608 */       DefaultConnectionPool defpool = new DefaultConnectionPool();
/*  300:     */       
/*  301:     */ 
/*  302:     */ 
/*  303:     */ 
/*  304: 613 */       defpool.setDriver(driver);
/*  305: 614 */       defpool.setURL(dbURL);
/*  306: 615 */       defpool.setProtocol(prop);
/*  307: 619 */       if (this.m_DefaultPoolingEnabled) {
/*  308: 619 */         defpool.setPoolEnabled(true);
/*  309:     */       }
/*  310: 621 */       this.m_PoolMgr.registerPool(poolName, defpool);
/*  311: 622 */       this.m_ConnectionPool = defpool;
/*  312:     */     }
/*  313:     */     else
/*  314:     */     {
/*  315: 626 */       this.m_ConnectionPool = cpool;
/*  316:     */     }
/*  317: 629 */     this.m_IsDefaultPool = true;
/*  318:     */     try
/*  319:     */     {
/*  320: 637 */       con = this.m_ConnectionPool.getConnection();
/*  321:     */     }
/*  322:     */     catch (SQLException e)
/*  323:     */     {
/*  324: 641 */       if (con != null)
/*  325:     */       {
/*  326: 643 */         this.m_ConnectionPool.releaseConnectionOnError(con);
/*  327: 644 */         con = null;
/*  328:     */       }
/*  329: 646 */       throw e;
/*  330:     */     }
/*  331:     */     finally
/*  332:     */     {
/*  333: 650 */       if (con != null) {
/*  334: 650 */         this.m_ConnectionPool.releaseConnection(con);
/*  335:     */       }
/*  336:     */     }
/*  337:     */   }
/*  338:     */   
/*  339:     */   public ConnectionPool getConnectionPool()
/*  340:     */   {
/*  341: 660 */     return this.m_ConnectionPool;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public DTM query(ExpressionContext exprContext, String queryString)
/*  345:     */   {
/*  346: 678 */     SQLDocument doc = null;
/*  347:     */     try
/*  348:     */     {
/*  349: 685 */       if (null == this.m_ConnectionPool) {
/*  350: 685 */         return null;
/*  351:     */       }
/*  352: 687 */       SQLQueryParser query = this.m_QueryParser.parse(this, queryString, 1);
/*  353:     */       
/*  354:     */ 
/*  355:     */ 
/*  356: 691 */       doc = SQLDocument.getNewDocument(exprContext);
/*  357: 692 */       doc.execute(this, query);
/*  358:     */       
/*  359:     */ 
/*  360: 695 */       this.m_OpenSQLDocuments.addElement(doc);
/*  361:     */     }
/*  362:     */     catch (Exception e)
/*  363:     */     {
/*  364: 704 */       if (doc != null)
/*  365:     */       {
/*  366: 706 */         if (doc.hasErrors()) {
/*  367: 708 */           setError(e, doc, doc.checkWarnings());
/*  368:     */         }
/*  369: 711 */         doc.close(this.m_IsDefaultPool);
/*  370: 712 */         doc = null;
/*  371:     */       }
/*  372:     */     }
/*  373:     */     finally {}
/*  374: 721 */     return doc;
/*  375:     */   }
/*  376:     */   
/*  377:     */   public DTM pquery(ExpressionContext exprContext, String queryString)
/*  378:     */   {
/*  379: 738 */     return pquery(exprContext, queryString, null);
/*  380:     */   }
/*  381:     */   
/*  382:     */   public DTM pquery(ExpressionContext exprContext, String queryString, String typeInfo)
/*  383:     */   {
/*  384: 760 */     SQLDocument doc = null;
/*  385:     */     try
/*  386:     */     {
/*  387: 767 */       if (null == this.m_ConnectionPool) {
/*  388: 767 */         return null;
/*  389:     */       }
/*  390: 769 */       SQLQueryParser query = this.m_QueryParser.parse(this, queryString, 0);
/*  391: 775 */       if (!this.m_InlineVariables)
/*  392:     */       {
/*  393: 777 */         addTypeToData(typeInfo);
/*  394: 778 */         query.setParameters(this.m_ParameterList);
/*  395:     */       }
/*  396: 781 */       doc = SQLDocument.getNewDocument(exprContext);
/*  397: 782 */       doc.execute(this, query);
/*  398:     */       
/*  399:     */ 
/*  400: 785 */       this.m_OpenSQLDocuments.addElement(doc);
/*  401:     */     }
/*  402:     */     catch (Exception e)
/*  403:     */     {
/*  404: 794 */       if (doc != null)
/*  405:     */       {
/*  406: 796 */         if (doc.hasErrors()) {
/*  407: 798 */           setError(e, doc, doc.checkWarnings());
/*  408:     */         }
/*  409: 803 */         doc.close(this.m_IsDefaultPool);
/*  410: 804 */         doc = null;
/*  411:     */       }
/*  412:     */     }
/*  413:     */     finally {}
/*  414: 813 */     return doc;
/*  415:     */   }
/*  416:     */   
/*  417:     */   public void skipRec(ExpressionContext exprContext, Object o, int value)
/*  418:     */   {
/*  419: 828 */     SQLDocument sqldoc = null;
/*  420: 829 */     DTMNodeIterator nodei = null;
/*  421:     */     
/*  422: 831 */     sqldoc = locateSQLDocument(exprContext, o);
/*  423: 832 */     if (sqldoc != null) {
/*  424: 832 */       sqldoc.skip(value);
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   private void addTypeToData(String typeInfo)
/*  429:     */   {
/*  430: 841 */     if ((typeInfo != null) && (this.m_ParameterList != null))
/*  431:     */     {
/*  432: 845 */       StringTokenizer plist = new StringTokenizer(typeInfo);
/*  433:     */       
/*  434:     */ 
/*  435:     */ 
/*  436:     */ 
/*  437:     */ 
/*  438: 851 */       int indx = 0;
/*  439: 852 */       while (plist.hasMoreTokens())
/*  440:     */       {
/*  441: 854 */         String value = plist.nextToken();
/*  442: 855 */         QueryParameter qp = (QueryParameter)this.m_ParameterList.elementAt(indx);
/*  443: 856 */         if (null != qp) {
/*  444: 858 */           qp.setTypeName(value);
/*  445:     */         }
/*  446: 861 */         indx++;
/*  447:     */       }
/*  448:     */     }
/*  449:     */   }
/*  450:     */   
/*  451:     */   public void addParameter(String value)
/*  452:     */   {
/*  453: 873 */     addParameterWithType(value, null);
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void addParameterWithType(String value, String Type)
/*  457:     */   {
/*  458: 884 */     this.m_ParameterList.addElement(new QueryParameter(value, Type));
/*  459:     */   }
/*  460:     */   
/*  461:     */   public void addParameterFromElement(Element e)
/*  462:     */   {
/*  463: 896 */     NamedNodeMap attrs = e.getAttributes();
/*  464: 897 */     Node Type = attrs.getNamedItem("type");
/*  465: 898 */     Node n1 = e.getFirstChild();
/*  466: 899 */     if (null != n1)
/*  467:     */     {
/*  468: 901 */       String value = n1.getNodeValue();
/*  469: 902 */       if (value == null) {
/*  470: 902 */         value = "";
/*  471:     */       }
/*  472: 903 */       this.m_ParameterList.addElement(new QueryParameter(value, Type.getNodeValue()));
/*  473:     */     }
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void addParameterFromElement(NodeList nl)
/*  477:     */   {
/*  478: 932 */     int count = nl.getLength();
/*  479: 933 */     for (int x = 0; x < count; x++) {
/*  480: 935 */       addParameters((Element)nl.item(x));
/*  481:     */     }
/*  482:     */   }
/*  483:     */   
/*  484:     */   private void addParameters(Element elem)
/*  485:     */   {
/*  486: 956 */     Node n = elem.getFirstChild();
/*  487: 958 */     if (null == n) {
/*  488:     */       return;
/*  489:     */     }
/*  490:     */     do
/*  491:     */     {
/*  492: 962 */       if (n.getNodeType() == 1)
/*  493:     */       {
/*  494: 964 */         NamedNodeMap attrs = n.getAttributes();
/*  495: 965 */         Node Type = attrs.getNamedItem("type");
/*  496:     */         String TypeStr;
/*  497: 968 */         if (Type == null) {
/*  498: 968 */           TypeStr = "string";
/*  499:     */         } else {
/*  500: 969 */           TypeStr = Type.getNodeValue();
/*  501:     */         }
/*  502: 971 */         Node n1 = n.getFirstChild();
/*  503: 972 */         if (null != n1)
/*  504:     */         {
/*  505: 974 */           String value = n1.getNodeValue();
/*  506: 975 */           if (value == null) {
/*  507: 975 */             value = "";
/*  508:     */           }
/*  509: 978 */           this.m_ParameterList.addElement(new QueryParameter(value, TypeStr));
/*  510:     */         }
/*  511:     */       }
/*  512: 982 */     } while ((n = n.getNextSibling()) != null);
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void clearParameters()
/*  516:     */   {
/*  517: 990 */     this.m_ParameterList.removeAllElements();
/*  518:     */   }
/*  519:     */   
/*  520:     */   /**
/*  521:     */    * @deprecated
/*  522:     */    */
/*  523:     */   public void enableDefaultConnectionPool()
/*  524:     */   {
/*  525:1011 */     this.m_DefaultPoolingEnabled = true;
/*  526:1013 */     if (this.m_ConnectionPool == null) {
/*  527:1013 */       return;
/*  528:     */     }
/*  529:1014 */     if (this.m_IsDefaultPool) {
/*  530:1014 */       return;
/*  531:     */     }
/*  532:1016 */     this.m_ConnectionPool.setPoolEnabled(true);
/*  533:     */   }
/*  534:     */   
/*  535:     */   /**
/*  536:     */    * @deprecated
/*  537:     */    */
/*  538:     */   public void disableDefaultConnectionPool()
/*  539:     */   {
/*  540:1030 */     this.m_DefaultPoolingEnabled = false;
/*  541:1032 */     if (this.m_ConnectionPool == null) {
/*  542:1032 */       return;
/*  543:     */     }
/*  544:1033 */     if (!this.m_IsDefaultPool) {
/*  545:1033 */       return;
/*  546:     */     }
/*  547:1035 */     this.m_ConnectionPool.setPoolEnabled(false);
/*  548:     */   }
/*  549:     */   
/*  550:     */   /**
/*  551:     */    * @deprecated
/*  552:     */    */
/*  553:     */   public void enableStreamingMode()
/*  554:     */   {
/*  555:1053 */     this.m_IsStreamingEnabled = true;
/*  556:     */   }
/*  557:     */   
/*  558:     */   /**
/*  559:     */    * @deprecated
/*  560:     */    */
/*  561:     */   public void disableStreamingMode()
/*  562:     */   {
/*  563:1070 */     this.m_IsStreamingEnabled = false;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public DTM getError()
/*  567:     */   {
/*  568:1080 */     if (this.m_FullErrors) {
/*  569:1082 */       for (int idx = 0; idx < this.m_OpenSQLDocuments.size(); idx++)
/*  570:     */       {
/*  571:1084 */         SQLDocument doc = (SQLDocument)this.m_OpenSQLDocuments.elementAt(idx);
/*  572:1085 */         SQLWarning warn = doc.checkWarnings();
/*  573:1086 */         if (warn != null) {
/*  574:1086 */           setError(null, doc, warn);
/*  575:     */         }
/*  576:     */       }
/*  577:     */     }
/*  578:1090 */     return buildErrorDocument();
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void close()
/*  582:     */     throws SQLException
/*  583:     */   {
/*  584:1108 */     while (this.m_OpenSQLDocuments.size() != 0)
/*  585:     */     {
/*  586:1110 */       SQLDocument d = (SQLDocument)this.m_OpenSQLDocuments.elementAt(0);
/*  587:     */       try
/*  588:     */       {
/*  589:1115 */         d.close(this.m_IsDefaultPool);
/*  590:     */       }
/*  591:     */       catch (Exception se) {}
/*  592:1119 */       this.m_OpenSQLDocuments.removeElementAt(0);
/*  593:     */     }
/*  594:1122 */     if (null != this.m_Connection)
/*  595:     */     {
/*  596:1124 */       this.m_ConnectionPool.releaseConnection(this.m_Connection);
/*  597:1125 */       this.m_Connection = null;
/*  598:     */     }
/*  599:     */   }
/*  600:     */   
/*  601:     */   public void close(ExpressionContext exprContext, Object doc)
/*  602:     */     throws SQLException
/*  603:     */   {
/*  604:1144 */     SQLDocument sqlDoc = locateSQLDocument(exprContext, doc);
/*  605:1145 */     if (sqlDoc != null)
/*  606:     */     {
/*  607:1149 */       sqlDoc.close(this.m_IsDefaultPool);
/*  608:1150 */       this.m_OpenSQLDocuments.remove(sqlDoc);
/*  609:     */     }
/*  610:     */   }
/*  611:     */   
/*  612:     */   private SQLDocument locateSQLDocument(ExpressionContext exprContext, Object doc)
/*  613:     */   {
/*  614:     */     try
/*  615:     */     {
/*  616:1169 */       if ((doc instanceof DTMNodeIterator))
/*  617:     */       {
/*  618:1171 */         DTMNodeIterator dtmIter = (DTMNodeIterator)doc;
/*  619:     */         try
/*  620:     */         {
/*  621:1174 */           DTMNodeProxy root = (DTMNodeProxy)dtmIter.getRoot();
/*  622:1175 */           return (SQLDocument)root.getDTM();
/*  623:     */         }
/*  624:     */         catch (Exception e)
/*  625:     */         {
/*  626:1179 */           XNodeSet xNS = (XNodeSet)dtmIter.getDTMIterator();
/*  627:1180 */           DTMIterator iter = xNS.getContainedIter();
/*  628:1181 */           DTM dtm = iter.getDTM(xNS.nextNode());
/*  629:1182 */           return (SQLDocument)dtm;
/*  630:     */         }
/*  631:     */       }
/*  632:1192 */       setError(new Exception("SQL Extension:close - Can Not Identify SQLDocument"), exprContext);
/*  633:1193 */       return null;
/*  634:     */     }
/*  635:     */     catch (Exception e)
/*  636:     */     {
/*  637:1197 */       setError(e, exprContext);
/*  638:     */     }
/*  639:1198 */     return null;
/*  640:     */   }
/*  641:     */   
/*  642:     */   private SQLErrorDocument buildErrorDocument()
/*  643:     */   {
/*  644:1209 */     SQLErrorDocument eDoc = null;
/*  645:1211 */     if (this.m_LastSQLDocumentWithError != null)
/*  646:     */     {
/*  647:1217 */       ExpressionContext ctx = this.m_LastSQLDocumentWithError.getExpressionContext();
/*  648:1218 */       SQLWarning warn = this.m_LastSQLDocumentWithError.checkWarnings();
/*  649:     */       try
/*  650:     */       {
/*  651:1223 */         DTMManager mgr = ((XPathContext.XPathExpressionContext)ctx).getDTMManager();
/*  652:     */         
/*  653:1225 */         DTMManagerDefault mgrDefault = (DTMManagerDefault)mgr;
/*  654:1226 */         int dtmIdent = mgrDefault.getFirstFreeDTMID();
/*  655:     */         
/*  656:1228 */         eDoc = new SQLErrorDocument(mgr, dtmIdent << 16, this.m_Error, warn, this.m_FullErrors);
/*  657:     */         
/*  658:     */ 
/*  659:     */ 
/*  660:     */ 
/*  661:1233 */         mgrDefault.addDTM(eDoc, dtmIdent);
/*  662:     */         
/*  663:     */ 
/*  664:1236 */         this.m_Error = null;
/*  665:1237 */         this.m_LastSQLDocumentWithError = null;
/*  666:     */       }
/*  667:     */       catch (Exception e)
/*  668:     */       {
/*  669:1241 */         eDoc = null;
/*  670:     */       }
/*  671:     */     }
/*  672:1245 */     return eDoc;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public void setError(Exception excp, ExpressionContext expr)
/*  676:     */   {
/*  677:     */     try
/*  678:     */     {
/*  679:1258 */       ErrorListener listen = expr.getErrorListener();
/*  680:1259 */       if ((listen != null) && (excp != null)) {
/*  681:1262 */         listen.warning(new TransformerException(excp.toString(), expr.getXPathContext().getSAXLocator(), excp));
/*  682:     */       }
/*  683:     */     }
/*  684:     */     catch (Exception e) {}
/*  685:     */   }
/*  686:     */   
/*  687:     */   public void setError(Exception excp, SQLDocument doc, SQLWarning warn)
/*  688:     */   {
/*  689:1277 */     ExpressionContext cont = doc.getExpressionContext();
/*  690:1278 */     this.m_LastSQLDocumentWithError = doc;
/*  691:     */     try
/*  692:     */     {
/*  693:1282 */       ErrorListener listen = cont.getErrorListener();
/*  694:1283 */       if ((listen != null) && (excp != null)) {
/*  695:1284 */         listen.warning(new TransformerException(excp.toString(), cont.getXPathContext().getSAXLocator(), excp));
/*  696:     */       }
/*  697:1288 */       if ((listen != null) && (warn != null)) {
/*  698:1290 */         listen.warning(new TransformerException(warn.toString(), cont.getXPathContext().getSAXLocator(), warn));
/*  699:     */       }
/*  700:1295 */       if (excp != null) {
/*  701:1295 */         this.m_Error = excp;
/*  702:     */       }
/*  703:1297 */       if (warn != null)
/*  704:     */       {
/*  705:1301 */         SQLWarning tw = new SQLWarning(warn.getMessage(), warn.getSQLState(), warn.getErrorCode());
/*  706:     */         
/*  707:     */ 
/*  708:1304 */         SQLWarning nw = warn.getNextWarning();
/*  709:1305 */         while (nw != null)
/*  710:     */         {
/*  711:1307 */           tw.setNextWarning(new SQLWarning(nw.getMessage(), nw.getSQLState(), nw.getErrorCode()));
/*  712:     */           
/*  713:     */ 
/*  714:1310 */           nw = nw.getNextWarning();
/*  715:     */         }
/*  716:1313 */         tw.setNextWarning(new SQLWarning(warn.getMessage(), warn.getSQLState(), warn.getErrorCode()));
/*  717:     */       }
/*  718:     */     }
/*  719:     */     catch (Exception e) {}
/*  720:     */   }
/*  721:     */   
/*  722:     */   public void setFeature(String feature, String setting)
/*  723:     */   {
/*  724:1335 */     boolean value = false;
/*  725:1337 */     if ("true".equalsIgnoreCase(setting)) {
/*  726:1337 */       value = true;
/*  727:     */     }
/*  728:1339 */     if ("streaming".equalsIgnoreCase(feature))
/*  729:     */     {
/*  730:1341 */       this.m_IsStreamingEnabled = value;
/*  731:     */     }
/*  732:1343 */     else if ("inline-variables".equalsIgnoreCase(feature))
/*  733:     */     {
/*  734:1345 */       this.m_InlineVariables = value;
/*  735:     */     }
/*  736:1347 */     else if ("multiple-results".equalsIgnoreCase(feature))
/*  737:     */     {
/*  738:1349 */       this.m_IsMultipleResultsEnabled = value;
/*  739:     */     }
/*  740:1351 */     else if ("cache-statements".equalsIgnoreCase(feature))
/*  741:     */     {
/*  742:1353 */       this.m_IsStatementCachingEnabled = value;
/*  743:     */     }
/*  744:1355 */     else if ("default-pool-enabled".equalsIgnoreCase(feature))
/*  745:     */     {
/*  746:1357 */       this.m_DefaultPoolingEnabled = value;
/*  747:1359 */       if (this.m_ConnectionPool == null) {
/*  748:1359 */         return;
/*  749:     */       }
/*  750:1360 */       if (this.m_IsDefaultPool) {
/*  751:1360 */         return;
/*  752:     */       }
/*  753:1362 */       this.m_ConnectionPool.setPoolEnabled(value);
/*  754:     */     }
/*  755:1364 */     else if ("full-errors".equalsIgnoreCase(feature))
/*  756:     */     {
/*  757:1366 */       this.m_FullErrors = value;
/*  758:     */     }
/*  759:     */   }
/*  760:     */   
/*  761:     */   public String getFeature(String feature)
/*  762:     */   {
/*  763:1377 */     String value = null;
/*  764:1379 */     if ("streaming".equalsIgnoreCase(feature)) {
/*  765:1380 */       value = this.m_IsStreamingEnabled ? "true" : "false";
/*  766:1381 */     } else if ("inline-variables".equalsIgnoreCase(feature)) {
/*  767:1382 */       value = this.m_InlineVariables ? "true" : "false";
/*  768:1383 */     } else if ("multiple-results".equalsIgnoreCase(feature)) {
/*  769:1384 */       value = this.m_IsMultipleResultsEnabled ? "true" : "false";
/*  770:1385 */     } else if ("cache-statements".equalsIgnoreCase(feature)) {
/*  771:1386 */       value = this.m_IsStatementCachingEnabled ? "true" : "false";
/*  772:1387 */     } else if ("default-pool-enabled".equalsIgnoreCase(feature)) {
/*  773:1388 */       value = this.m_DefaultPoolingEnabled ? "true" : "false";
/*  774:1389 */     } else if ("full-errors".equalsIgnoreCase(feature)) {
/*  775:1390 */       value = this.m_FullErrors ? "true" : "false";
/*  776:     */     }
/*  777:1392 */     return value;
/*  778:     */   }
/*  779:     */   
/*  780:     */   protected void finalize()
/*  781:     */   {
/*  782:     */     try
/*  783:     */     {
/*  784:1405 */       close();
/*  785:     */     }
/*  786:     */     catch (Exception e) {}
/*  787:     */   }
/*  788:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.XConnection
 * JD-Core Version:    0.7.0.1
 */