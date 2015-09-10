/*    1:     */ package org.apache.xalan.lib.sql;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.sql.CallableStatement;
/*    5:     */ import java.sql.Connection;
/*    6:     */ import java.sql.PreparedStatement;
/*    7:     */ import java.sql.ResultSet;
/*    8:     */ import java.sql.ResultSetMetaData;
/*    9:     */ import java.sql.SQLException;
/*   10:     */ import java.sql.SQLWarning;
/*   11:     */ import java.sql.Statement;
/*   12:     */ import java.util.Vector;
/*   13:     */ import org.apache.xalan.extensions.ExpressionContext;
/*   14:     */ import org.apache.xml.dtm.DTMManager;
/*   15:     */ import org.apache.xml.dtm.ref.DTMDefaultBase;
/*   16:     */ import org.apache.xml.dtm.ref.DTMManagerDefault;
/*   17:     */ import org.apache.xml.dtm.ref.ExpandedNameTable;
/*   18:     */ import org.apache.xml.utils.SuballocatedIntVector;
/*   19:     */ import org.apache.xpath.XPathContext.XPathExpressionContext;
/*   20:     */ 
/*   21:     */ public class SQLDocument
/*   22:     */   extends DTMDocument
/*   23:     */ {
/*   24:  47 */   private boolean DEBUG = false;
/*   25:     */   private static final String S_NAMESPACE = "http://xml.apache.org/xalan/SQLExtension";
/*   26:     */   private static final String S_SQL = "sql";
/*   27:     */   private static final String S_ROW_SET = "row-set";
/*   28:     */   private static final String S_METADATA = "metadata";
/*   29:     */   private static final String S_COLUMN_HEADER = "column-header";
/*   30:     */   private static final String S_ROW = "row";
/*   31:     */   private static final String S_COL = "col";
/*   32:     */   private static final String S_OUT_PARAMETERS = "out-parameters";
/*   33:     */   private static final String S_CATALOGUE_NAME = "catalogue-name";
/*   34:     */   private static final String S_DISPLAY_SIZE = "column-display-size";
/*   35:     */   private static final String S_COLUMN_LABEL = "column-label";
/*   36:     */   private static final String S_COLUMN_NAME = "column-name";
/*   37:     */   private static final String S_COLUMN_TYPE = "column-type";
/*   38:     */   private static final String S_COLUMN_TYPENAME = "column-typename";
/*   39:     */   private static final String S_PRECISION = "precision";
/*   40:     */   private static final String S_SCALE = "scale";
/*   41:     */   private static final String S_SCHEMA_NAME = "schema-name";
/*   42:     */   private static final String S_TABLE_NAME = "table-name";
/*   43:     */   private static final String S_CASESENSITIVE = "case-sensitive";
/*   44:     */   private static final String S_DEFINITELYWRITABLE = "definitely-writable";
/*   45:     */   private static final String S_ISNULLABLE = "nullable";
/*   46:     */   private static final String S_ISSIGNED = "signed";
/*   47:     */   private static final String S_ISWRITEABLE = "writable";
/*   48:     */   private static final String S_ISSEARCHABLE = "searchable";
/*   49: 133 */   private int m_SQL_TypeID = 0;
/*   50: 136 */   private int m_MetaData_TypeID = 0;
/*   51: 139 */   private int m_ColumnHeader_TypeID = 0;
/*   52: 142 */   private int m_RowSet_TypeID = 0;
/*   53: 145 */   private int m_Row_TypeID = 0;
/*   54: 148 */   private int m_Col_TypeID = 0;
/*   55: 151 */   private int m_OutParameter_TypeID = 0;
/*   56: 155 */   private int m_ColAttrib_CATALOGUE_NAME_TypeID = 0;
/*   57: 158 */   private int m_ColAttrib_DISPLAY_SIZE_TypeID = 0;
/*   58: 161 */   private int m_ColAttrib_COLUMN_LABEL_TypeID = 0;
/*   59: 164 */   private int m_ColAttrib_COLUMN_NAME_TypeID = 0;
/*   60: 167 */   private int m_ColAttrib_COLUMN_TYPE_TypeID = 0;
/*   61: 170 */   private int m_ColAttrib_COLUMN_TYPENAME_TypeID = 0;
/*   62: 173 */   private int m_ColAttrib_PRECISION_TypeID = 0;
/*   63: 176 */   private int m_ColAttrib_SCALE_TypeID = 0;
/*   64: 179 */   private int m_ColAttrib_SCHEMA_NAME_TypeID = 0;
/*   65: 182 */   private int m_ColAttrib_TABLE_NAME_TypeID = 0;
/*   66: 185 */   private int m_ColAttrib_CASESENSITIVE_TypeID = 0;
/*   67: 188 */   private int m_ColAttrib_DEFINITELYWRITEABLE_TypeID = 0;
/*   68: 191 */   private int m_ColAttrib_ISNULLABLE_TypeID = 0;
/*   69: 194 */   private int m_ColAttrib_ISSIGNED_TypeID = 0;
/*   70: 197 */   private int m_ColAttrib_ISWRITEABLE_TypeID = 0;
/*   71: 200 */   private int m_ColAttrib_ISSEARCHABLE_TypeID = 0;
/*   72: 205 */   private Statement m_Statement = null;
/*   73: 211 */   private ExpressionContext m_ExpressionContext = null;
/*   74: 217 */   private ConnectionPool m_ConnectionPool = null;
/*   75: 222 */   private ResultSet m_ResultSet = null;
/*   76: 228 */   private SQLQueryParser m_QueryParser = null;
/*   77:     */   private int[] m_ColHeadersIdx;
/*   78:     */   private int m_ColCount;
/*   79: 247 */   private int m_MetaDataIdx = -1;
/*   80: 253 */   private int m_RowSetIdx = -1;
/*   81: 257 */   private int m_SQLIdx = -1;
/*   82: 263 */   private int m_FirstRowIdx = -1;
/*   83: 270 */   private int m_LastRowIdx = -1;
/*   84: 276 */   private boolean m_StreamingMode = true;
/*   85: 281 */   private boolean m_MultipleResults = false;
/*   86: 288 */   private boolean m_HasErrors = false;
/*   87: 293 */   private boolean m_IsStatementCachingEnabled = false;
/*   88: 298 */   private XConnection m_XConnection = null;
/*   89:     */   
/*   90:     */   public SQLDocument(DTMManager mgr, int ident)
/*   91:     */   {
/*   92: 311 */     super(mgr, ident);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static SQLDocument getNewDocument(ExpressionContext exprContext)
/*   96:     */   {
/*   97: 322 */     DTMManager mgr = ((XPathContext.XPathExpressionContext)exprContext).getDTMManager();
/*   98:     */     
/*   99: 324 */     DTMManagerDefault mgrDefault = (DTMManagerDefault)mgr;
/*  100:     */     
/*  101:     */ 
/*  102: 327 */     int dtmIdent = mgrDefault.getFirstFreeDTMID();
/*  103:     */     
/*  104: 329 */     SQLDocument doc = new SQLDocument(mgr, dtmIdent << 16);
/*  105:     */     
/*  106:     */ 
/*  107:     */ 
/*  108: 333 */     mgrDefault.addDTM(doc, dtmIdent);
/*  109: 334 */     doc.setExpressionContext(exprContext);
/*  110:     */     
/*  111: 336 */     return doc;
/*  112:     */   }
/*  113:     */   
/*  114:     */   protected void setExpressionContext(ExpressionContext expr)
/*  115:     */   {
/*  116: 347 */     this.m_ExpressionContext = expr;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public ExpressionContext getExpressionContext()
/*  120:     */   {
/*  121: 355 */     return this.m_ExpressionContext;
/*  122:     */   }
/*  123:     */   
/*  124:     */   public void execute(XConnection xconn, SQLQueryParser query)
/*  125:     */     throws SQLException
/*  126:     */   {
/*  127:     */     try
/*  128:     */     {
/*  129: 364 */       this.m_StreamingMode = "true".equals(xconn.getFeature("streaming"));
/*  130: 365 */       this.m_MultipleResults = "true".equals(xconn.getFeature("multiple-results"));
/*  131: 366 */       this.m_IsStatementCachingEnabled = "true".equals(xconn.getFeature("cache-statements"));
/*  132: 367 */       this.m_XConnection = xconn;
/*  133: 368 */       this.m_QueryParser = query;
/*  134:     */       
/*  135: 370 */       executeSQLStatement();
/*  136:     */       
/*  137: 372 */       createExpandedNameTable();
/*  138:     */       
/*  139:     */ 
/*  140: 375 */       this.m_DocumentIdx = addElement(0, this.m_Document_TypeID, -1, -1);
/*  141: 376 */       this.m_SQLIdx = addElement(1, this.m_SQL_TypeID, this.m_DocumentIdx, -1);
/*  142: 379 */       if (!this.m_MultipleResults) {
/*  143: 380 */         extractSQLMetaData(this.m_ResultSet.getMetaData());
/*  144:     */       }
/*  145:     */     }
/*  146:     */     catch (SQLException e)
/*  147:     */     {
/*  148: 394 */       this.m_HasErrors = true;
/*  149: 395 */       throw e;
/*  150:     */     }
/*  151:     */   }
/*  152:     */   
/*  153:     */   private void executeSQLStatement()
/*  154:     */     throws SQLException
/*  155:     */   {
/*  156: 401 */     this.m_ConnectionPool = this.m_XConnection.getConnectionPool();
/*  157:     */     
/*  158: 403 */     Connection conn = this.m_ConnectionPool.getConnection();
/*  159: 405 */     if (!this.m_QueryParser.hasParameters())
/*  160:     */     {
/*  161: 407 */       this.m_Statement = conn.createStatement();
/*  162: 408 */       this.m_ResultSet = this.m_Statement.executeQuery(this.m_QueryParser.getSQLQuery());
/*  163:     */     }
/*  164: 413 */     else if (this.m_QueryParser.isCallable())
/*  165:     */     {
/*  166: 415 */       CallableStatement cstmt = conn.prepareCall(this.m_QueryParser.getSQLQuery());
/*  167:     */       
/*  168: 417 */       this.m_QueryParser.registerOutputParameters(cstmt);
/*  169: 418 */       this.m_QueryParser.populateStatement(cstmt, this.m_ExpressionContext);
/*  170: 419 */       this.m_Statement = cstmt;
/*  171: 420 */       if (!cstmt.execute()) {
/*  172: 420 */         throw new SQLException("Error in Callable Statement");
/*  173:     */       }
/*  174: 422 */       this.m_ResultSet = this.m_Statement.getResultSet();
/*  175:     */     }
/*  176:     */     else
/*  177:     */     {
/*  178: 426 */       PreparedStatement stmt = conn.prepareStatement(this.m_QueryParser.getSQLQuery());
/*  179:     */       
/*  180: 428 */       this.m_QueryParser.populateStatement(stmt, this.m_ExpressionContext);
/*  181: 429 */       this.m_Statement = stmt;
/*  182: 430 */       this.m_ResultSet = stmt.executeQuery();
/*  183:     */     }
/*  184:     */   }
/*  185:     */   
/*  186:     */   public void skip(int value)
/*  187:     */   {
/*  188:     */     try
/*  189:     */     {
/*  190: 445 */       if (this.m_ResultSet != null) {
/*  191: 445 */         this.m_ResultSet.relative(value);
/*  192:     */       }
/*  193:     */     }
/*  194:     */     catch (Exception origEx)
/*  195:     */     {
/*  196:     */       try
/*  197:     */       {
/*  198: 453 */         for (int x = 0; x < value; x++) {
/*  199: 455 */           if (!this.m_ResultSet.next()) {
/*  200:     */             break;
/*  201:     */           }
/*  202:     */         }
/*  203:     */       }
/*  204:     */       catch (Exception e)
/*  205:     */       {
/*  206: 461 */         this.m_XConnection.setError(origEx, this, checkWarnings());
/*  207: 462 */         this.m_XConnection.setError(e, this, checkWarnings());
/*  208:     */       }
/*  209:     */     }
/*  210:     */   }
/*  211:     */   
/*  212:     */   private void extractSQLMetaData(ResultSetMetaData meta)
/*  213:     */   {
/*  214: 482 */     this.m_MetaDataIdx = addElement(1, this.m_MetaData_TypeID, this.m_MultipleResults ? this.m_RowSetIdx : this.m_SQLIdx, -1);
/*  215:     */     try
/*  216:     */     {
/*  217: 486 */       this.m_ColCount = meta.getColumnCount();
/*  218: 487 */       this.m_ColHeadersIdx = new int[this.m_ColCount];
/*  219:     */     }
/*  220:     */     catch (Exception e)
/*  221:     */     {
/*  222: 491 */       this.m_XConnection.setError(e, this, checkWarnings());
/*  223:     */     }
/*  224: 497 */     int lastColHeaderIdx = -1;
/*  225:     */     
/*  226:     */ 
/*  227: 500 */     int i = 1;
/*  228: 501 */     for (i = 1; i <= this.m_ColCount; i++)
/*  229:     */     {
/*  230: 503 */       this.m_ColHeadersIdx[(i - 1)] = addElement(2, this.m_ColumnHeader_TypeID, this.m_MetaDataIdx, lastColHeaderIdx);
/*  231:     */       
/*  232:     */ 
/*  233: 506 */       lastColHeaderIdx = this.m_ColHeadersIdx[(i - 1)];
/*  234:     */       try
/*  235:     */       {
/*  236: 511 */         addAttributeToNode(meta.getColumnName(i), this.m_ColAttrib_COLUMN_NAME_TypeID, lastColHeaderIdx);
/*  237:     */       }
/*  238:     */       catch (Exception e)
/*  239:     */       {
/*  240: 517 */         addAttributeToNode("Not Supported", this.m_ColAttrib_COLUMN_NAME_TypeID, lastColHeaderIdx);
/*  241:     */       }
/*  242:     */       try
/*  243:     */       {
/*  244: 524 */         addAttributeToNode(meta.getColumnLabel(i), this.m_ColAttrib_COLUMN_LABEL_TypeID, lastColHeaderIdx);
/*  245:     */       }
/*  246:     */       catch (Exception e)
/*  247:     */       {
/*  248: 530 */         addAttributeToNode("Not Supported", this.m_ColAttrib_COLUMN_LABEL_TypeID, lastColHeaderIdx);
/*  249:     */       }
/*  250:     */       try
/*  251:     */       {
/*  252: 537 */         addAttributeToNode(meta.getCatalogName(i), this.m_ColAttrib_CATALOGUE_NAME_TypeID, lastColHeaderIdx);
/*  253:     */       }
/*  254:     */       catch (Exception e)
/*  255:     */       {
/*  256: 543 */         addAttributeToNode("Not Supported", this.m_ColAttrib_CATALOGUE_NAME_TypeID, lastColHeaderIdx);
/*  257:     */       }
/*  258:     */       try
/*  259:     */       {
/*  260: 550 */         addAttributeToNode(new Integer(meta.getColumnDisplaySize(i)), this.m_ColAttrib_DISPLAY_SIZE_TypeID, lastColHeaderIdx);
/*  261:     */       }
/*  262:     */       catch (Exception e)
/*  263:     */       {
/*  264: 556 */         addAttributeToNode("Not Supported", this.m_ColAttrib_DISPLAY_SIZE_TypeID, lastColHeaderIdx);
/*  265:     */       }
/*  266:     */       try
/*  267:     */       {
/*  268: 563 */         addAttributeToNode(new Integer(meta.getColumnType(i)), this.m_ColAttrib_COLUMN_TYPE_TypeID, lastColHeaderIdx);
/*  269:     */       }
/*  270:     */       catch (Exception e)
/*  271:     */       {
/*  272: 569 */         addAttributeToNode("Not Supported", this.m_ColAttrib_COLUMN_TYPE_TypeID, lastColHeaderIdx);
/*  273:     */       }
/*  274:     */       try
/*  275:     */       {
/*  276: 576 */         addAttributeToNode(meta.getColumnTypeName(i), this.m_ColAttrib_COLUMN_TYPENAME_TypeID, lastColHeaderIdx);
/*  277:     */       }
/*  278:     */       catch (Exception e)
/*  279:     */       {
/*  280: 582 */         addAttributeToNode("Not Supported", this.m_ColAttrib_COLUMN_TYPENAME_TypeID, lastColHeaderIdx);
/*  281:     */       }
/*  282:     */       try
/*  283:     */       {
/*  284: 589 */         addAttributeToNode(new Integer(meta.getPrecision(i)), this.m_ColAttrib_PRECISION_TypeID, lastColHeaderIdx);
/*  285:     */       }
/*  286:     */       catch (Exception e)
/*  287:     */       {
/*  288: 595 */         addAttributeToNode("Not Supported", this.m_ColAttrib_PRECISION_TypeID, lastColHeaderIdx);
/*  289:     */       }
/*  290:     */       try
/*  291:     */       {
/*  292: 601 */         addAttributeToNode(new Integer(meta.getScale(i)), this.m_ColAttrib_SCALE_TypeID, lastColHeaderIdx);
/*  293:     */       }
/*  294:     */       catch (Exception e)
/*  295:     */       {
/*  296: 607 */         addAttributeToNode("Not Supported", this.m_ColAttrib_SCALE_TypeID, lastColHeaderIdx);
/*  297:     */       }
/*  298:     */       try
/*  299:     */       {
/*  300: 614 */         addAttributeToNode(meta.getSchemaName(i), this.m_ColAttrib_SCHEMA_NAME_TypeID, lastColHeaderIdx);
/*  301:     */       }
/*  302:     */       catch (Exception e)
/*  303:     */       {
/*  304: 620 */         addAttributeToNode("Not Supported", this.m_ColAttrib_SCHEMA_NAME_TypeID, lastColHeaderIdx);
/*  305:     */       }
/*  306:     */       try
/*  307:     */       {
/*  308: 626 */         addAttributeToNode(meta.getTableName(i), this.m_ColAttrib_TABLE_NAME_TypeID, lastColHeaderIdx);
/*  309:     */       }
/*  310:     */       catch (Exception e)
/*  311:     */       {
/*  312: 632 */         addAttributeToNode("Not Supported", this.m_ColAttrib_TABLE_NAME_TypeID, lastColHeaderIdx);
/*  313:     */       }
/*  314:     */       try
/*  315:     */       {
/*  316: 639 */         addAttributeToNode(meta.isCaseSensitive(i) ? "true" : "false", this.m_ColAttrib_CASESENSITIVE_TypeID, lastColHeaderIdx);
/*  317:     */       }
/*  318:     */       catch (Exception e)
/*  319:     */       {
/*  320: 645 */         addAttributeToNode("Not Supported", this.m_ColAttrib_CASESENSITIVE_TypeID, lastColHeaderIdx);
/*  321:     */       }
/*  322:     */       try
/*  323:     */       {
/*  324: 652 */         addAttributeToNode(meta.isDefinitelyWritable(i) ? "true" : "false", this.m_ColAttrib_DEFINITELYWRITEABLE_TypeID, lastColHeaderIdx);
/*  325:     */       }
/*  326:     */       catch (Exception e)
/*  327:     */       {
/*  328: 658 */         addAttributeToNode("Not Supported", this.m_ColAttrib_DEFINITELYWRITEABLE_TypeID, lastColHeaderIdx);
/*  329:     */       }
/*  330:     */       try
/*  331:     */       {
/*  332: 665 */         addAttributeToNode(meta.isNullable(i) != 0 ? "true" : "false", this.m_ColAttrib_ISNULLABLE_TypeID, lastColHeaderIdx);
/*  333:     */       }
/*  334:     */       catch (Exception e)
/*  335:     */       {
/*  336: 671 */         addAttributeToNode("Not Supported", this.m_ColAttrib_ISNULLABLE_TypeID, lastColHeaderIdx);
/*  337:     */       }
/*  338:     */       try
/*  339:     */       {
/*  340: 678 */         addAttributeToNode(meta.isSigned(i) ? "true" : "false", this.m_ColAttrib_ISSIGNED_TypeID, lastColHeaderIdx);
/*  341:     */       }
/*  342:     */       catch (Exception e)
/*  343:     */       {
/*  344: 684 */         addAttributeToNode("Not Supported", this.m_ColAttrib_ISSIGNED_TypeID, lastColHeaderIdx);
/*  345:     */       }
/*  346:     */       try
/*  347:     */       {
/*  348: 691 */         addAttributeToNode(meta.isWritable(i) == true ? "true" : "false", this.m_ColAttrib_ISWRITEABLE_TypeID, lastColHeaderIdx);
/*  349:     */       }
/*  350:     */       catch (Exception e)
/*  351:     */       {
/*  352: 697 */         addAttributeToNode("Not Supported", this.m_ColAttrib_ISWRITEABLE_TypeID, lastColHeaderIdx);
/*  353:     */       }
/*  354:     */       try
/*  355:     */       {
/*  356: 704 */         addAttributeToNode(meta.isSearchable(i) == true ? "true" : "false", this.m_ColAttrib_ISSEARCHABLE_TypeID, lastColHeaderIdx);
/*  357:     */       }
/*  358:     */       catch (Exception e)
/*  359:     */       {
/*  360: 710 */         addAttributeToNode("Not Supported", this.m_ColAttrib_ISSEARCHABLE_TypeID, lastColHeaderIdx);
/*  361:     */       }
/*  362:     */     }
/*  363:     */   }
/*  364:     */   
/*  365:     */   protected void createExpandedNameTable()
/*  366:     */   {
/*  367: 724 */     super.createExpandedNameTable();
/*  368:     */     
/*  369: 726 */     this.m_SQL_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "sql", 1);
/*  370:     */     
/*  371:     */ 
/*  372: 729 */     this.m_MetaData_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "metadata", 1);
/*  373:     */     
/*  374:     */ 
/*  375: 732 */     this.m_ColumnHeader_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "column-header", 1);
/*  376:     */     
/*  377: 734 */     this.m_RowSet_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "row-set", 1);
/*  378:     */     
/*  379: 736 */     this.m_Row_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "row", 1);
/*  380:     */     
/*  381: 738 */     this.m_Col_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "col", 1);
/*  382:     */     
/*  383: 740 */     this.m_OutParameter_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "out-parameters", 1);
/*  384:     */     
/*  385:     */ 
/*  386: 743 */     this.m_ColAttrib_CATALOGUE_NAME_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "catalogue-name", 2);
/*  387:     */     
/*  388: 745 */     this.m_ColAttrib_DISPLAY_SIZE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "column-display-size", 2);
/*  389:     */     
/*  390: 747 */     this.m_ColAttrib_COLUMN_LABEL_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "column-label", 2);
/*  391:     */     
/*  392: 749 */     this.m_ColAttrib_COLUMN_NAME_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "column-name", 2);
/*  393:     */     
/*  394: 751 */     this.m_ColAttrib_COLUMN_TYPE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "column-type", 2);
/*  395:     */     
/*  396: 753 */     this.m_ColAttrib_COLUMN_TYPENAME_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "column-typename", 2);
/*  397:     */     
/*  398: 755 */     this.m_ColAttrib_PRECISION_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "precision", 2);
/*  399:     */     
/*  400: 757 */     this.m_ColAttrib_SCALE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "scale", 2);
/*  401:     */     
/*  402: 759 */     this.m_ColAttrib_SCHEMA_NAME_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "schema-name", 2);
/*  403:     */     
/*  404: 761 */     this.m_ColAttrib_TABLE_NAME_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "table-name", 2);
/*  405:     */     
/*  406: 763 */     this.m_ColAttrib_CASESENSITIVE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "case-sensitive", 2);
/*  407:     */     
/*  408: 765 */     this.m_ColAttrib_DEFINITELYWRITEABLE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "definitely-writable", 2);
/*  409:     */     
/*  410: 767 */     this.m_ColAttrib_ISNULLABLE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "nullable", 2);
/*  411:     */     
/*  412: 769 */     this.m_ColAttrib_ISSIGNED_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "signed", 2);
/*  413:     */     
/*  414: 771 */     this.m_ColAttrib_ISWRITEABLE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "writable", 2);
/*  415:     */     
/*  416: 773 */     this.m_ColAttrib_ISSEARCHABLE_TypeID = this.m_expandedNameTable.getExpandedTypeID("http://xml.apache.org/xalan/SQLExtension", "searchable", 2);
/*  417:     */   }
/*  418:     */   
/*  419:     */   private boolean addRowToDTMFromResultSet()
/*  420:     */   {
/*  421:     */     try
/*  422:     */     {
/*  423: 792 */       if (this.m_FirstRowIdx == -1)
/*  424:     */       {
/*  425: 794 */         this.m_RowSetIdx = addElement(1, this.m_RowSet_TypeID, this.m_SQLIdx, this.m_MultipleResults ? this.m_RowSetIdx : this.m_MetaDataIdx);
/*  426: 796 */         if (this.m_MultipleResults) {
/*  427: 796 */           extractSQLMetaData(this.m_ResultSet.getMetaData());
/*  428:     */         }
/*  429:     */       }
/*  430: 802 */       if (!this.m_ResultSet.next())
/*  431:     */       {
/*  432: 809 */         if ((this.m_StreamingMode) && (this.m_LastRowIdx != -1)) {
/*  433: 812 */           this.m_nextsib.setElementAt(-1, this.m_LastRowIdx);
/*  434:     */         }
/*  435: 815 */         this.m_ResultSet.close();
/*  436: 816 */         if (this.m_MultipleResults)
/*  437:     */         {
/*  438: 818 */           while ((goto 121) || ((!this.m_Statement.getMoreResults()) && (this.m_Statement.getUpdateCount() >= 0))) {}
/*  439: 819 */           this.m_ResultSet = this.m_Statement.getResultSet();
/*  440:     */         }
/*  441:     */         else
/*  442:     */         {
/*  443: 822 */           this.m_ResultSet = null;
/*  444:     */         }
/*  445: 824 */         if (this.m_ResultSet != null)
/*  446:     */         {
/*  447: 826 */           this.m_FirstRowIdx = -1;
/*  448: 827 */           addRowToDTMFromResultSet();
/*  449:     */         }
/*  450:     */         else
/*  451:     */         {
/*  452: 831 */           Vector parameters = this.m_QueryParser.getParameters();
/*  453: 833 */           if (parameters != null)
/*  454:     */           {
/*  455: 835 */             int outParamIdx = addElement(1, this.m_OutParameter_TypeID, this.m_SQLIdx, this.m_RowSetIdx);
/*  456: 836 */             int lastColID = -1;
/*  457: 837 */             for (int indx = 0; indx < parameters.size(); indx++)
/*  458:     */             {
/*  459: 839 */               QueryParameter parm = (QueryParameter)parameters.elementAt(indx);
/*  460: 840 */               if (parm.isOutput())
/*  461:     */               {
/*  462: 842 */                 Object rawobj = ((CallableStatement)this.m_Statement).getObject(indx + 1);
/*  463: 843 */                 lastColID = addElementWithData(rawobj, 2, this.m_Col_TypeID, outParamIdx, lastColID);
/*  464: 844 */                 addAttributeToNode(parm.getName(), this.m_ColAttrib_COLUMN_NAME_TypeID, lastColID);
/*  465: 845 */                 addAttributeToNode(parm.getName(), this.m_ColAttrib_COLUMN_LABEL_TypeID, lastColID);
/*  466: 846 */                 addAttributeToNode(new Integer(parm.getType()), this.m_ColAttrib_COLUMN_TYPE_TypeID, lastColID);
/*  467: 847 */                 addAttributeToNode(parm.getTypeName(), this.m_ColAttrib_COLUMN_TYPENAME_TypeID, lastColID);
/*  468:     */               }
/*  469:     */             }
/*  470:     */           }
/*  471: 852 */           SQLWarning warn = checkWarnings();
/*  472: 853 */           if (warn != null) {
/*  473: 853 */             this.m_XConnection.setError(null, null, warn);
/*  474:     */           }
/*  475:     */         }
/*  476: 856 */         return false;
/*  477:     */       }
/*  478: 860 */       if (this.m_FirstRowIdx == -1)
/*  479:     */       {
/*  480: 862 */         this.m_FirstRowIdx = addElement(2, this.m_Row_TypeID, this.m_RowSetIdx, this.m_MultipleResults ? this.m_MetaDataIdx : -1);
/*  481:     */         
/*  482:     */ 
/*  483: 865 */         this.m_LastRowIdx = this.m_FirstRowIdx;
/*  484: 867 */         if (this.m_StreamingMode) {
/*  485: 870 */           this.m_nextsib.setElementAt(this.m_LastRowIdx, this.m_LastRowIdx);
/*  486:     */         }
/*  487:     */       }
/*  488: 878 */       else if (!this.m_StreamingMode)
/*  489:     */       {
/*  490: 880 */         this.m_LastRowIdx = addElement(2, this.m_Row_TypeID, this.m_RowSetIdx, this.m_LastRowIdx);
/*  491:     */       }
/*  492: 886 */       int colID = _firstch(this.m_LastRowIdx);
/*  493:     */       
/*  494:     */ 
/*  495: 889 */       int pcolID = -1;
/*  496: 892 */       for (int i = 1; i <= this.m_ColCount; i++)
/*  497:     */       {
/*  498: 896 */         Object o = this.m_ResultSet.getObject(i);
/*  499: 901 */         if (colID == -1)
/*  500:     */         {
/*  501: 903 */           pcolID = addElementWithData(o, 3, this.m_Col_TypeID, this.m_LastRowIdx, pcolID);
/*  502: 904 */           cloneAttributeFromNode(pcolID, this.m_ColHeadersIdx[(i - 1)]);
/*  503:     */         }
/*  504:     */         else
/*  505:     */         {
/*  506: 910 */           int dataIdent = _firstch(colID);
/*  507: 911 */           if (dataIdent == -1) {
/*  508: 913 */             error("Streaming Mode, Data Error");
/*  509:     */           } else {
/*  510: 917 */             this.m_ObjectArray.setAt(dataIdent, o);
/*  511:     */           }
/*  512:     */         }
/*  513: 924 */         if (colID != -1) {
/*  514: 926 */           colID = _nextsib(colID);
/*  515:     */         }
/*  516:     */       }
/*  517:     */     }
/*  518:     */     catch (Exception e)
/*  519:     */     {
/*  520: 933 */       if (this.DEBUG) {
/*  521: 935 */         System.out.println("SQL Error Fetching next row [" + e.getLocalizedMessage() + "]");
/*  522:     */       }
/*  523: 939 */       this.m_XConnection.setError(e, this, checkWarnings());
/*  524: 940 */       this.m_HasErrors = true;
/*  525:     */     }
/*  526: 944 */     return true;
/*  527:     */   }
/*  528:     */   
/*  529:     */   public boolean hasErrors()
/*  530:     */   {
/*  531: 954 */     return this.m_HasErrors;
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void close(boolean flushConnPool)
/*  535:     */   {
/*  536:     */     try
/*  537:     */     {
/*  538: 969 */       SQLWarning warn = checkWarnings();
/*  539: 970 */       if (warn != null) {
/*  540: 970 */         this.m_XConnection.setError(null, null, warn);
/*  541:     */       }
/*  542:     */     }
/*  543:     */     catch (Exception e) {}
/*  544:     */     try
/*  545:     */     {
/*  546: 976 */       if (null != this.m_ResultSet)
/*  547:     */       {
/*  548: 978 */         this.m_ResultSet.close();
/*  549: 979 */         this.m_ResultSet = null;
/*  550:     */       }
/*  551:     */     }
/*  552:     */     catch (Exception e) {}
/*  553: 985 */     Connection conn = null;
/*  554:     */     try
/*  555:     */     {
/*  556: 989 */       if (null != this.m_Statement)
/*  557:     */       {
/*  558: 991 */         conn = this.m_Statement.getConnection();
/*  559: 992 */         this.m_Statement.close();
/*  560: 993 */         this.m_Statement = null;
/*  561:     */       }
/*  562:     */     }
/*  563:     */     catch (Exception e) {}
/*  564:     */     try
/*  565:     */     {
/*  566:1000 */       if (conn != null) {
/*  567:1002 */         if (this.m_HasErrors) {
/*  568:1002 */           this.m_ConnectionPool.releaseConnectionOnError(conn);
/*  569:     */         } else {
/*  570:1003 */           this.m_ConnectionPool.releaseConnection(conn);
/*  571:     */         }
/*  572:     */       }
/*  573:     */     }
/*  574:     */     catch (Exception e) {}
/*  575:1009 */     getManager().release(this, true);
/*  576:     */   }
/*  577:     */   
/*  578:     */   protected boolean nextNode()
/*  579:     */   {
/*  580:1017 */     if (this.DEBUG) {
/*  581:1017 */       System.out.println("nextNode()");
/*  582:     */     }
/*  583:     */     try
/*  584:     */     {
/*  585:1020 */       return false;
/*  586:     */     }
/*  587:     */     catch (Exception e) {}
/*  588:1025 */     return false;
/*  589:     */   }
/*  590:     */   
/*  591:     */   protected int _nextsib(int identity)
/*  592:     */   {
/*  593:1040 */     if (this.m_ResultSet != null)
/*  594:     */     {
/*  595:1042 */       int id = _exptype(identity);
/*  596:1045 */       if (this.m_FirstRowIdx == -1) {
/*  597:1047 */         addRowToDTMFromResultSet();
/*  598:     */       }
/*  599:1050 */       if ((id == this.m_Row_TypeID) && (identity >= this.m_LastRowIdx))
/*  600:     */       {
/*  601:1054 */         if (this.DEBUG) {
/*  602:1054 */           System.out.println("reading from the ResultSet");
/*  603:     */         }
/*  604:1055 */         addRowToDTMFromResultSet();
/*  605:     */       }
/*  606:1057 */       else if ((this.m_MultipleResults) && (identity == this.m_RowSetIdx))
/*  607:     */       {
/*  608:1059 */         if (this.DEBUG) {
/*  609:1059 */           System.out.println("reading for next ResultSet");
/*  610:     */         }
/*  611:1060 */         int startIdx = this.m_RowSetIdx;
/*  612:1061 */         while ((startIdx == this.m_RowSetIdx) && (this.m_ResultSet != null)) {
/*  613:1062 */           addRowToDTMFromResultSet();
/*  614:     */         }
/*  615:     */       }
/*  616:     */     }
/*  617:1066 */     return super._nextsib(identity);
/*  618:     */   }
/*  619:     */   
/*  620:     */   public void documentRegistration()
/*  621:     */   {
/*  622:1071 */     if (this.DEBUG) {
/*  623:1071 */       System.out.println("Document Registration");
/*  624:     */     }
/*  625:     */   }
/*  626:     */   
/*  627:     */   public void documentRelease()
/*  628:     */   {
/*  629:1076 */     if (this.DEBUG) {
/*  630:1076 */       System.out.println("Document Release");
/*  631:     */     }
/*  632:     */   }
/*  633:     */   
/*  634:     */   public SQLWarning checkWarnings()
/*  635:     */   {
/*  636:1081 */     SQLWarning warn = null;
/*  637:1082 */     if (this.m_Statement != null) {
/*  638:     */       try
/*  639:     */       {
/*  640:1086 */         warn = this.m_Statement.getWarnings();
/*  641:1087 */         this.m_Statement.clearWarnings();
/*  642:     */       }
/*  643:     */       catch (SQLException se) {}
/*  644:     */     }
/*  645:1091 */     return warn;
/*  646:     */   }
/*  647:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.lib.sql.SQLDocument
 * JD-Core Version:    0.7.0.1
 */