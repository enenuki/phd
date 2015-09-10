/*    1:     */ package org.apache.log4j.lf5.viewer;
/*    2:     */ 
/*    3:     */ import java.awt.Color;
/*    4:     */ import java.awt.Component;
/*    5:     */ import java.awt.Container;
/*    6:     */ import java.awt.Dimension;
/*    7:     */ import java.awt.FlowLayout;
/*    8:     */ import java.awt.Font;
/*    9:     */ import java.awt.Frame;
/*   10:     */ import java.awt.GraphicsEnvironment;
/*   11:     */ import java.awt.Toolkit;
/*   12:     */ import java.awt.Window;
/*   13:     */ import java.awt.event.ActionEvent;
/*   14:     */ import java.awt.event.ActionListener;
/*   15:     */ import java.awt.event.WindowAdapter;
/*   16:     */ import java.awt.event.WindowEvent;
/*   17:     */ import java.io.File;
/*   18:     */ import java.io.IOException;
/*   19:     */ import java.io.InputStream;
/*   20:     */ import java.net.MalformedURLException;
/*   21:     */ import java.net.URL;
/*   22:     */ import java.util.ArrayList;
/*   23:     */ import java.util.EventObject;
/*   24:     */ import java.util.HashMap;
/*   25:     */ import java.util.Iterator;
/*   26:     */ import java.util.List;
/*   27:     */ import java.util.Map;
/*   28:     */ import java.util.StringTokenizer;
/*   29:     */ import java.util.Vector;
/*   30:     */ import javax.swing.AbstractButton;
/*   31:     */ import javax.swing.BorderFactory;
/*   32:     */ import javax.swing.ImageIcon;
/*   33:     */ import javax.swing.JButton;
/*   34:     */ import javax.swing.JCheckBoxMenuItem;
/*   35:     */ import javax.swing.JColorChooser;
/*   36:     */ import javax.swing.JComboBox;
/*   37:     */ import javax.swing.JComponent;
/*   38:     */ import javax.swing.JFileChooser;
/*   39:     */ import javax.swing.JFrame;
/*   40:     */ import javax.swing.JLabel;
/*   41:     */ import javax.swing.JMenu;
/*   42:     */ import javax.swing.JMenuBar;
/*   43:     */ import javax.swing.JMenuItem;
/*   44:     */ import javax.swing.JOptionPane;
/*   45:     */ import javax.swing.JPanel;
/*   46:     */ import javax.swing.JRootPane;
/*   47:     */ import javax.swing.JScrollBar;
/*   48:     */ import javax.swing.JScrollPane;
/*   49:     */ import javax.swing.JSplitPane;
/*   50:     */ import javax.swing.JTable;
/*   51:     */ import javax.swing.JTextArea;
/*   52:     */ import javax.swing.JToolBar;
/*   53:     */ import javax.swing.KeyStroke;
/*   54:     */ import javax.swing.ListSelectionModel;
/*   55:     */ import javax.swing.SwingUtilities;
/*   56:     */ import javax.swing.text.JTextComponent;
/*   57:     */ import org.apache.log4j.lf5.LogLevel;
/*   58:     */ import org.apache.log4j.lf5.LogRecord;
/*   59:     */ import org.apache.log4j.lf5.LogRecordFilter;
/*   60:     */ import org.apache.log4j.lf5.util.DateFormatManager;
/*   61:     */ import org.apache.log4j.lf5.util.LogFileParser;
/*   62:     */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel;
/*   63:     */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree;
/*   64:     */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath;
/*   65:     */ import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
/*   66:     */ import org.apache.log4j.lf5.viewer.configure.MRUFileManager;
/*   67:     */ 
/*   68:     */ public class LogBrokerMonitor
/*   69:     */ {
/*   70:     */   public static final String DETAILED_VIEW = "Detailed";
/*   71:     */   protected JFrame _logMonitorFrame;
/*   72: 100 */   protected int _logMonitorFrameWidth = 550;
/*   73: 101 */   protected int _logMonitorFrameHeight = 500;
/*   74:     */   protected LogTable _table;
/*   75:     */   protected CategoryExplorerTree _categoryExplorerTree;
/*   76:     */   protected String _searchText;
/*   77: 105 */   protected String _NDCTextFilter = "";
/*   78: 106 */   protected LogLevel _leastSevereDisplayedLogLevel = LogLevel.DEBUG;
/*   79:     */   protected JScrollPane _logTableScrollPane;
/*   80:     */   protected JLabel _statusLabel;
/*   81: 110 */   protected Object _lock = new Object();
/*   82:     */   protected JComboBox _fontSizeCombo;
/*   83: 113 */   protected int _fontSize = 10;
/*   84: 114 */   protected String _fontName = "Dialog";
/*   85: 115 */   protected String _currentView = "Detailed";
/*   86: 117 */   protected boolean _loadSystemFonts = false;
/*   87: 118 */   protected boolean _trackTableScrollPane = true;
/*   88:     */   protected Dimension _lastTableViewportSize;
/*   89: 120 */   protected boolean _callSystemExitOnClose = false;
/*   90: 121 */   protected List _displayedLogBrokerProperties = new Vector();
/*   91: 123 */   protected Map _logLevelMenuItems = new HashMap();
/*   92: 124 */   protected Map _logTableColumnMenuItems = new HashMap();
/*   93: 126 */   protected List _levels = null;
/*   94: 127 */   protected List _columns = null;
/*   95: 128 */   protected boolean _isDisposed = false;
/*   96: 130 */   protected ConfigurationManager _configurationManager = null;
/*   97: 131 */   protected MRUFileManager _mruFileManager = null;
/*   98: 132 */   protected File _fileLocation = null;
/*   99:     */   
/*  100:     */   public LogBrokerMonitor(List logLevels)
/*  101:     */   {
/*  102: 147 */     this._levels = logLevels;
/*  103: 148 */     this._columns = LogTableColumn.getLogTableColumns();
/*  104:     */     
/*  105:     */ 
/*  106:     */ 
/*  107: 152 */     String callSystemExitOnClose = System.getProperty("monitor.exit");
/*  108: 154 */     if (callSystemExitOnClose == null) {
/*  109: 155 */       callSystemExitOnClose = "false";
/*  110:     */     }
/*  111: 157 */     callSystemExitOnClose = callSystemExitOnClose.trim().toLowerCase();
/*  112: 159 */     if (callSystemExitOnClose.equals("true")) {
/*  113: 160 */       this._callSystemExitOnClose = true;
/*  114:     */     }
/*  115: 163 */     initComponents();
/*  116:     */     
/*  117:     */ 
/*  118: 166 */     this._logMonitorFrame.addWindowListener(new LogBrokerMonitorWindowAdaptor(this));
/*  119:     */   }
/*  120:     */   
/*  121:     */   public void show(int delay)
/*  122:     */   {
/*  123: 180 */     if (this._logMonitorFrame.isVisible()) {
/*  124: 181 */       return;
/*  125:     */     }
/*  126: 184 */     SwingUtilities.invokeLater(new Runnable()
/*  127:     */     {
/*  128:     */       private final int val$delay;
/*  129:     */       
/*  130:     */       public void run()
/*  131:     */       {
/*  132: 186 */         Thread.yield();
/*  133: 187 */         LogBrokerMonitor.this.pause(this.val$delay);
/*  134: 188 */         LogBrokerMonitor.this._logMonitorFrame.setVisible(true);
/*  135:     */       }
/*  136:     */     });
/*  137:     */   }
/*  138:     */   
/*  139:     */   public void show()
/*  140:     */   {
/*  141: 194 */     show(0);
/*  142:     */   }
/*  143:     */   
/*  144:     */   public void dispose()
/*  145:     */   {
/*  146: 201 */     this._logMonitorFrame.dispose();
/*  147: 202 */     this._isDisposed = true;
/*  148: 204 */     if (this._callSystemExitOnClose == true) {
/*  149: 205 */       System.exit(0);
/*  150:     */     }
/*  151:     */   }
/*  152:     */   
/*  153:     */   public void hide()
/*  154:     */   {
/*  155: 213 */     this._logMonitorFrame.setVisible(false);
/*  156:     */   }
/*  157:     */   
/*  158:     */   public DateFormatManager getDateFormatManager()
/*  159:     */   {
/*  160: 220 */     return this._table.getDateFormatManager();
/*  161:     */   }
/*  162:     */   
/*  163:     */   public void setDateFormatManager(DateFormatManager dfm)
/*  164:     */   {
/*  165: 227 */     this._table.setDateFormatManager(dfm);
/*  166:     */   }
/*  167:     */   
/*  168:     */   public boolean getCallSystemExitOnClose()
/*  169:     */   {
/*  170: 235 */     return this._callSystemExitOnClose;
/*  171:     */   }
/*  172:     */   
/*  173:     */   public void setCallSystemExitOnClose(boolean callSystemExitOnClose)
/*  174:     */   {
/*  175: 243 */     this._callSystemExitOnClose = callSystemExitOnClose;
/*  176:     */   }
/*  177:     */   
/*  178:     */   public void addMessage(LogRecord lr)
/*  179:     */   {
/*  180: 252 */     if (this._isDisposed == true) {
/*  181: 255 */       return;
/*  182:     */     }
/*  183: 258 */     SwingUtilities.invokeLater(new Runnable()
/*  184:     */     {
/*  185:     */       private final LogRecord val$lr;
/*  186:     */       
/*  187:     */       public void run()
/*  188:     */       {
/*  189: 260 */         LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().addLogRecord(this.val$lr);
/*  190: 261 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().addLogRecord(this.val$lr);
/*  191: 262 */         LogBrokerMonitor.this.updateStatusLabel();
/*  192:     */       }
/*  193:     */     });
/*  194:     */   }
/*  195:     */   
/*  196:     */   public void setMaxNumberOfLogRecords(int maxNumberOfLogRecords)
/*  197:     */   {
/*  198: 268 */     this._table.getFilteredLogTableModel().setMaxNumberOfLogRecords(maxNumberOfLogRecords);
/*  199:     */   }
/*  200:     */   
/*  201:     */   public JFrame getBaseFrame()
/*  202:     */   {
/*  203: 272 */     return this._logMonitorFrame;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public void setTitle(String title)
/*  207:     */   {
/*  208: 276 */     this._logMonitorFrame.setTitle(title + " - LogFactor5");
/*  209:     */   }
/*  210:     */   
/*  211:     */   public void setFrameSize(int width, int height)
/*  212:     */   {
/*  213: 280 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/*  214: 281 */     if ((0 < width) && (width < screen.width)) {
/*  215: 282 */       this._logMonitorFrameWidth = width;
/*  216:     */     }
/*  217: 284 */     if ((0 < height) && (height < screen.height)) {
/*  218: 285 */       this._logMonitorFrameHeight = height;
/*  219:     */     }
/*  220: 287 */     updateFrameSize();
/*  221:     */   }
/*  222:     */   
/*  223:     */   public void setFontSize(int fontSize)
/*  224:     */   {
/*  225: 291 */     changeFontSizeCombo(this._fontSizeCombo, fontSize);
/*  226:     */   }
/*  227:     */   
/*  228:     */   public void addDisplayedProperty(Object messageLine)
/*  229:     */   {
/*  230: 297 */     this._displayedLogBrokerProperties.add(messageLine);
/*  231:     */   }
/*  232:     */   
/*  233:     */   public Map getLogLevelMenuItems()
/*  234:     */   {
/*  235: 301 */     return this._logLevelMenuItems;
/*  236:     */   }
/*  237:     */   
/*  238:     */   public Map getLogTableColumnMenuItems()
/*  239:     */   {
/*  240: 305 */     return this._logTableColumnMenuItems;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public JCheckBoxMenuItem getTableColumnMenuItem(LogTableColumn column)
/*  244:     */   {
/*  245: 309 */     return getLogTableColumnMenuItem(column);
/*  246:     */   }
/*  247:     */   
/*  248:     */   public CategoryExplorerTree getCategoryExplorerTree()
/*  249:     */   {
/*  250: 313 */     return this._categoryExplorerTree;
/*  251:     */   }
/*  252:     */   
/*  253:     */   public String getNDCTextFilter()
/*  254:     */   {
/*  255: 319 */     return this._NDCTextFilter;
/*  256:     */   }
/*  257:     */   
/*  258:     */   public void setNDCLogRecordFilter(String textFilter)
/*  259:     */   {
/*  260: 326 */     this._table.getFilteredLogTableModel().setLogRecordFilter(createNDCLogRecordFilter(textFilter));
/*  261:     */   }
/*  262:     */   
/*  263:     */   protected void setSearchText(String text)
/*  264:     */   {
/*  265: 334 */     this._searchText = text;
/*  266:     */   }
/*  267:     */   
/*  268:     */   protected void setNDCTextFilter(String text)
/*  269:     */   {
/*  270: 341 */     if (text == null) {
/*  271: 342 */       this._NDCTextFilter = "";
/*  272:     */     } else {
/*  273: 344 */       this._NDCTextFilter = text;
/*  274:     */     }
/*  275:     */   }
/*  276:     */   
/*  277:     */   protected void sortByNDC()
/*  278:     */   {
/*  279: 352 */     String text = this._NDCTextFilter;
/*  280: 353 */     if ((text == null) || (text.length() == 0)) {
/*  281: 354 */       return;
/*  282:     */     }
/*  283: 358 */     this._table.getFilteredLogTableModel().setLogRecordFilter(createNDCLogRecordFilter(text));
/*  284:     */   }
/*  285:     */   
/*  286:     */   protected void findSearchText()
/*  287:     */   {
/*  288: 363 */     String text = this._searchText;
/*  289: 364 */     if ((text == null) || (text.length() == 0)) {
/*  290: 365 */       return;
/*  291:     */     }
/*  292: 367 */     int startRow = getFirstSelectedRow();
/*  293: 368 */     int foundRow = findRecord(startRow, text, this._table.getFilteredLogTableModel().getFilteredRecords());
/*  294:     */     
/*  295:     */ 
/*  296:     */ 
/*  297:     */ 
/*  298: 373 */     selectRow(foundRow);
/*  299:     */   }
/*  300:     */   
/*  301:     */   protected int getFirstSelectedRow()
/*  302:     */   {
/*  303: 377 */     return this._table.getSelectionModel().getMinSelectionIndex();
/*  304:     */   }
/*  305:     */   
/*  306:     */   protected void selectRow(int foundRow)
/*  307:     */   {
/*  308: 381 */     if (foundRow == -1)
/*  309:     */     {
/*  310: 382 */       String message = this._searchText + " not found.";
/*  311: 383 */       JOptionPane.showMessageDialog(this._logMonitorFrame, message, "Text not found", 1);
/*  312:     */       
/*  313:     */ 
/*  314:     */ 
/*  315:     */ 
/*  316:     */ 
/*  317: 389 */       return;
/*  318:     */     }
/*  319: 391 */     LF5SwingUtils.selectRow(foundRow, this._table, this._logTableScrollPane);
/*  320:     */   }
/*  321:     */   
/*  322:     */   protected int findRecord(int startRow, String searchText, List records)
/*  323:     */   {
/*  324: 399 */     if (startRow < 0) {
/*  325: 400 */       startRow = 0;
/*  326:     */     } else {
/*  327: 402 */       startRow++;
/*  328:     */     }
/*  329: 404 */     int len = records.size();
/*  330: 406 */     for (int i = startRow; i < len; i++) {
/*  331: 407 */       if (matches((LogRecord)records.get(i), searchText)) {
/*  332: 408 */         return i;
/*  333:     */       }
/*  334:     */     }
/*  335: 412 */     len = startRow;
/*  336: 413 */     for (int i = 0; i < len; i++) {
/*  337: 414 */       if (matches((LogRecord)records.get(i), searchText)) {
/*  338: 415 */         return i;
/*  339:     */       }
/*  340:     */     }
/*  341: 419 */     return -1;
/*  342:     */   }
/*  343:     */   
/*  344:     */   protected boolean matches(LogRecord record, String text)
/*  345:     */   {
/*  346: 427 */     String message = record.getMessage();
/*  347: 428 */     String NDC = record.getNDC();
/*  348: 430 */     if (((message == null) && (NDC == null)) || (text == null)) {
/*  349: 431 */       return false;
/*  350:     */     }
/*  351: 433 */     if ((message.toLowerCase().indexOf(text.toLowerCase()) == -1) && (NDC.toLowerCase().indexOf(text.toLowerCase()) == -1)) {
/*  352: 435 */       return false;
/*  353:     */     }
/*  354: 438 */     return true;
/*  355:     */   }
/*  356:     */   
/*  357:     */   protected void refresh(JTextArea textArea)
/*  358:     */   {
/*  359: 447 */     String text = textArea.getText();
/*  360: 448 */     textArea.setText("");
/*  361: 449 */     textArea.setText(text);
/*  362:     */   }
/*  363:     */   
/*  364:     */   protected void refreshDetailTextArea()
/*  365:     */   {
/*  366: 453 */     refresh(this._table._detailTextArea);
/*  367:     */   }
/*  368:     */   
/*  369:     */   protected void clearDetailTextArea()
/*  370:     */   {
/*  371: 457 */     this._table._detailTextArea.setText("");
/*  372:     */   }
/*  373:     */   
/*  374:     */   protected int changeFontSizeCombo(JComboBox box, int requestedSize)
/*  375:     */   {
/*  376: 466 */     int len = box.getItemCount();
/*  377:     */     
/*  378:     */ 
/*  379: 469 */     Object selectedObject = box.getItemAt(0);
/*  380: 470 */     int selectedValue = Integer.parseInt(String.valueOf(selectedObject));
/*  381: 471 */     for (int i = 0; i < len; i++)
/*  382:     */     {
/*  383: 472 */       Object currentObject = box.getItemAt(i);
/*  384: 473 */       int currentValue = Integer.parseInt(String.valueOf(currentObject));
/*  385: 474 */       if ((selectedValue < currentValue) && (currentValue <= requestedSize))
/*  386:     */       {
/*  387: 475 */         selectedValue = currentValue;
/*  388: 476 */         selectedObject = currentObject;
/*  389:     */       }
/*  390:     */     }
/*  391: 479 */     box.setSelectedItem(selectedObject);
/*  392: 480 */     return selectedValue;
/*  393:     */   }
/*  394:     */   
/*  395:     */   protected void setFontSizeSilently(int fontSize)
/*  396:     */   {
/*  397: 487 */     this._fontSize = fontSize;
/*  398: 488 */     setFontSize(this._table._detailTextArea, fontSize);
/*  399: 489 */     selectRow(0);
/*  400: 490 */     setFontSize(this._table, fontSize);
/*  401:     */   }
/*  402:     */   
/*  403:     */   protected void setFontSize(Component component, int fontSize)
/*  404:     */   {
/*  405: 494 */     Font oldFont = component.getFont();
/*  406: 495 */     Font newFont = new Font(oldFont.getFontName(), oldFont.getStyle(), fontSize);
/*  407:     */     
/*  408: 497 */     component.setFont(newFont);
/*  409:     */   }
/*  410:     */   
/*  411:     */   protected void updateFrameSize()
/*  412:     */   {
/*  413: 501 */     this._logMonitorFrame.setSize(this._logMonitorFrameWidth, this._logMonitorFrameHeight);
/*  414: 502 */     centerFrame(this._logMonitorFrame);
/*  415:     */   }
/*  416:     */   
/*  417:     */   protected void pause(int millis)
/*  418:     */   {
/*  419:     */     try
/*  420:     */     {
/*  421: 507 */       Thread.sleep(millis);
/*  422:     */     }
/*  423:     */     catch (InterruptedException e) {}
/*  424:     */   }
/*  425:     */   
/*  426:     */   protected void initComponents()
/*  427:     */   {
/*  428: 517 */     this._logMonitorFrame = new JFrame("LogFactor5");
/*  429:     */     
/*  430: 519 */     this._logMonitorFrame.setDefaultCloseOperation(0);
/*  431:     */     
/*  432: 521 */     String resource = "/org/apache/log4j/lf5/viewer/images/lf5_small_icon.gif";
/*  433:     */     
/*  434: 523 */     URL lf5IconURL = getClass().getResource(resource);
/*  435: 525 */     if (lf5IconURL != null) {
/*  436: 526 */       this._logMonitorFrame.setIconImage(new ImageIcon(lf5IconURL).getImage());
/*  437:     */     }
/*  438: 528 */     updateFrameSize();
/*  439:     */     
/*  440:     */ 
/*  441:     */ 
/*  442:     */ 
/*  443: 533 */     JTextArea detailTA = createDetailTextArea();
/*  444: 534 */     JScrollPane detailTAScrollPane = new JScrollPane(detailTA);
/*  445: 535 */     this._table = new LogTable(detailTA);
/*  446: 536 */     setView(this._currentView, this._table);
/*  447: 537 */     this._table.setFont(new Font(this._fontName, 0, this._fontSize));
/*  448: 538 */     this._logTableScrollPane = new JScrollPane(this._table);
/*  449: 540 */     if (this._trackTableScrollPane) {
/*  450: 541 */       this._logTableScrollPane.getVerticalScrollBar().addAdjustmentListener(new TrackingAdjustmentListener());
/*  451:     */     }
/*  452: 550 */     JSplitPane tableViewerSplitPane = new JSplitPane();
/*  453: 551 */     tableViewerSplitPane.setOneTouchExpandable(true);
/*  454: 552 */     tableViewerSplitPane.setOrientation(0);
/*  455: 553 */     tableViewerSplitPane.setLeftComponent(this._logTableScrollPane);
/*  456: 554 */     tableViewerSplitPane.setRightComponent(detailTAScrollPane);
/*  457:     */     
/*  458:     */ 
/*  459:     */ 
/*  460:     */ 
/*  461:     */ 
/*  462:     */ 
/*  463:     */ 
/*  464: 562 */     tableViewerSplitPane.setDividerLocation(350);
/*  465:     */     
/*  466:     */ 
/*  467:     */ 
/*  468:     */ 
/*  469:     */ 
/*  470: 568 */     this._categoryExplorerTree = new CategoryExplorerTree();
/*  471:     */     
/*  472: 570 */     this._table.getFilteredLogTableModel().setLogRecordFilter(createLogRecordFilter());
/*  473:     */     
/*  474: 572 */     JScrollPane categoryExplorerTreeScrollPane = new JScrollPane(this._categoryExplorerTree);
/*  475:     */     
/*  476: 574 */     categoryExplorerTreeScrollPane.setPreferredSize(new Dimension(130, 400));
/*  477:     */     
/*  478:     */ 
/*  479: 577 */     this._mruFileManager = new MRUFileManager();
/*  480:     */     
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485: 583 */     JSplitPane splitPane = new JSplitPane();
/*  486: 584 */     splitPane.setOneTouchExpandable(true);
/*  487: 585 */     splitPane.setRightComponent(tableViewerSplitPane);
/*  488: 586 */     splitPane.setLeftComponent(categoryExplorerTreeScrollPane);
/*  489:     */     
/*  490: 588 */     splitPane.setDividerLocation(130);
/*  491:     */     
/*  492:     */ 
/*  493:     */ 
/*  494:     */ 
/*  495: 593 */     this._logMonitorFrame.getRootPane().setJMenuBar(createMenuBar());
/*  496: 594 */     this._logMonitorFrame.getContentPane().add(splitPane, "Center");
/*  497: 595 */     this._logMonitorFrame.getContentPane().add(createToolBar(), "North");
/*  498:     */     
/*  499: 597 */     this._logMonitorFrame.getContentPane().add(createStatusArea(), "South");
/*  500:     */     
/*  501:     */ 
/*  502: 600 */     makeLogTableListenToCategoryExplorer();
/*  503: 601 */     addTableModelProperties();
/*  504:     */     
/*  505:     */ 
/*  506:     */ 
/*  507:     */ 
/*  508: 606 */     this._configurationManager = new ConfigurationManager(this, this._table);
/*  509:     */   }
/*  510:     */   
/*  511:     */   protected LogRecordFilter createLogRecordFilter()
/*  512:     */   {
/*  513: 611 */     LogRecordFilter result = new LogRecordFilter()
/*  514:     */     {
/*  515:     */       public boolean passes(LogRecord record)
/*  516:     */       {
/*  517: 613 */         CategoryPath path = new CategoryPath(record.getCategory());
/*  518: 614 */         return (LogBrokerMonitor.this.getMenuItem(record.getLevel()).isSelected()) && (LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().isCategoryPathActive(path));
/*  519:     */       }
/*  520: 618 */     };
/*  521: 619 */     return result;
/*  522:     */   }
/*  523:     */   
/*  524:     */   protected LogRecordFilter createNDCLogRecordFilter(String text)
/*  525:     */   {
/*  526: 625 */     this._NDCTextFilter = text;
/*  527: 626 */     LogRecordFilter result = new LogRecordFilter()
/*  528:     */     {
/*  529:     */       public boolean passes(LogRecord record)
/*  530:     */       {
/*  531: 628 */         String NDC = record.getNDC();
/*  532: 629 */         CategoryPath path = new CategoryPath(record.getCategory());
/*  533: 630 */         if ((NDC == null) || (LogBrokerMonitor.this._NDCTextFilter == null)) {
/*  534: 631 */           return false;
/*  535:     */         }
/*  536: 632 */         if (NDC.toLowerCase().indexOf(LogBrokerMonitor.this._NDCTextFilter.toLowerCase()) == -1) {
/*  537: 633 */           return false;
/*  538:     */         }
/*  539: 635 */         return (LogBrokerMonitor.this.getMenuItem(record.getLevel()).isSelected()) && (LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().isCategoryPathActive(path));
/*  540:     */       }
/*  541: 640 */     };
/*  542: 641 */     return result;
/*  543:     */   }
/*  544:     */   
/*  545:     */   protected void updateStatusLabel()
/*  546:     */   {
/*  547: 646 */     this._statusLabel.setText(getRecordsDisplayedMessage());
/*  548:     */   }
/*  549:     */   
/*  550:     */   protected String getRecordsDisplayedMessage()
/*  551:     */   {
/*  552: 650 */     FilteredLogTableModel model = this._table.getFilteredLogTableModel();
/*  553: 651 */     return getStatusText(model.getRowCount(), model.getTotalRowCount());
/*  554:     */   }
/*  555:     */   
/*  556:     */   protected void addTableModelProperties()
/*  557:     */   {
/*  558: 655 */     FilteredLogTableModel model = this._table.getFilteredLogTableModel();
/*  559:     */     
/*  560: 657 */     addDisplayedProperty(new Object()
/*  561:     */     {
/*  562:     */       public String toString()
/*  563:     */       {
/*  564: 659 */         return LogBrokerMonitor.this.getRecordsDisplayedMessage();
/*  565:     */       }
/*  566: 661 */     });
/*  567: 662 */     addDisplayedProperty(new Object()
/*  568:     */     {
/*  569:     */       private final FilteredLogTableModel val$model;
/*  570:     */       
/*  571:     */       public String toString()
/*  572:     */       {
/*  573: 664 */         return "Maximum number of displayed LogRecords: " + this.val$model._maxNumberOfLogRecords;
/*  574:     */       }
/*  575:     */     });
/*  576:     */   }
/*  577:     */   
/*  578:     */   protected String getStatusText(int displayedRows, int totalRows)
/*  579:     */   {
/*  580: 671 */     StringBuffer result = new StringBuffer();
/*  581: 672 */     result.append("Displaying: ");
/*  582: 673 */     result.append(displayedRows);
/*  583: 674 */     result.append(" records out of a total of: ");
/*  584: 675 */     result.append(totalRows);
/*  585: 676 */     result.append(" records.");
/*  586: 677 */     return result.toString();
/*  587:     */   }
/*  588:     */   
/*  589:     */   protected void makeLogTableListenToCategoryExplorer()
/*  590:     */   {
/*  591: 681 */     ActionListener listener = new ActionListener()
/*  592:     */     {
/*  593:     */       public void actionPerformed(ActionEvent e)
/*  594:     */       {
/*  595: 683 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  596: 684 */         LogBrokerMonitor.this.updateStatusLabel();
/*  597:     */       }
/*  598: 686 */     };
/*  599: 687 */     this._categoryExplorerTree.getExplorerModel().addActionListener(listener);
/*  600:     */   }
/*  601:     */   
/*  602:     */   protected JPanel createStatusArea()
/*  603:     */   {
/*  604: 691 */     JPanel statusArea = new JPanel();
/*  605: 692 */     JLabel status = new JLabel("No log records to display.");
/*  606:     */     
/*  607: 694 */     this._statusLabel = status;
/*  608: 695 */     status.setHorizontalAlignment(2);
/*  609:     */     
/*  610: 697 */     statusArea.setBorder(BorderFactory.createEtchedBorder());
/*  611: 698 */     statusArea.setLayout(new FlowLayout(0, 0, 0));
/*  612: 699 */     statusArea.add(status);
/*  613:     */     
/*  614: 701 */     return statusArea;
/*  615:     */   }
/*  616:     */   
/*  617:     */   protected JTextArea createDetailTextArea()
/*  618:     */   {
/*  619: 705 */     JTextArea detailTA = new JTextArea();
/*  620: 706 */     detailTA.setFont(new Font("Monospaced", 0, 14));
/*  621: 707 */     detailTA.setTabSize(3);
/*  622: 708 */     detailTA.setLineWrap(true);
/*  623: 709 */     detailTA.setWrapStyleWord(false);
/*  624: 710 */     return detailTA;
/*  625:     */   }
/*  626:     */   
/*  627:     */   protected JMenuBar createMenuBar()
/*  628:     */   {
/*  629: 714 */     JMenuBar menuBar = new JMenuBar();
/*  630: 715 */     menuBar.add(createFileMenu());
/*  631: 716 */     menuBar.add(createEditMenu());
/*  632: 717 */     menuBar.add(createLogLevelMenu());
/*  633: 718 */     menuBar.add(createViewMenu());
/*  634: 719 */     menuBar.add(createConfigureMenu());
/*  635: 720 */     menuBar.add(createHelpMenu());
/*  636:     */     
/*  637: 722 */     return menuBar;
/*  638:     */   }
/*  639:     */   
/*  640:     */   protected JMenu createLogLevelMenu()
/*  641:     */   {
/*  642: 726 */     JMenu result = new JMenu("Log Level");
/*  643: 727 */     result.setMnemonic('l');
/*  644: 728 */     Iterator levels = getLogLevels();
/*  645: 729 */     while (levels.hasNext()) {
/*  646: 730 */       result.add(getMenuItem((LogLevel)levels.next()));
/*  647:     */     }
/*  648: 733 */     result.addSeparator();
/*  649: 734 */     result.add(createAllLogLevelsMenuItem());
/*  650: 735 */     result.add(createNoLogLevelsMenuItem());
/*  651: 736 */     result.addSeparator();
/*  652: 737 */     result.add(createLogLevelColorMenu());
/*  653: 738 */     result.add(createResetLogLevelColorMenuItem());
/*  654:     */     
/*  655: 740 */     return result;
/*  656:     */   }
/*  657:     */   
/*  658:     */   protected JMenuItem createAllLogLevelsMenuItem()
/*  659:     */   {
/*  660: 744 */     JMenuItem result = new JMenuItem("Show all LogLevels");
/*  661: 745 */     result.setMnemonic('s');
/*  662: 746 */     result.addActionListener(new ActionListener()
/*  663:     */     {
/*  664:     */       public void actionPerformed(ActionEvent e)
/*  665:     */       {
/*  666: 748 */         LogBrokerMonitor.this.selectAllLogLevels(true);
/*  667: 749 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  668: 750 */         LogBrokerMonitor.this.updateStatusLabel();
/*  669:     */       }
/*  670: 752 */     });
/*  671: 753 */     return result;
/*  672:     */   }
/*  673:     */   
/*  674:     */   protected JMenuItem createNoLogLevelsMenuItem()
/*  675:     */   {
/*  676: 757 */     JMenuItem result = new JMenuItem("Hide all LogLevels");
/*  677: 758 */     result.setMnemonic('h');
/*  678: 759 */     result.addActionListener(new ActionListener()
/*  679:     */     {
/*  680:     */       public void actionPerformed(ActionEvent e)
/*  681:     */       {
/*  682: 761 */         LogBrokerMonitor.this.selectAllLogLevels(false);
/*  683: 762 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  684: 763 */         LogBrokerMonitor.this.updateStatusLabel();
/*  685:     */       }
/*  686: 765 */     });
/*  687: 766 */     return result;
/*  688:     */   }
/*  689:     */   
/*  690:     */   protected JMenu createLogLevelColorMenu()
/*  691:     */   {
/*  692: 770 */     JMenu colorMenu = new JMenu("Configure LogLevel Colors");
/*  693: 771 */     colorMenu.setMnemonic('c');
/*  694: 772 */     Iterator levels = getLogLevels();
/*  695: 773 */     while (levels.hasNext()) {
/*  696: 774 */       colorMenu.add(createSubMenuItem((LogLevel)levels.next()));
/*  697:     */     }
/*  698: 777 */     return colorMenu;
/*  699:     */   }
/*  700:     */   
/*  701:     */   protected JMenuItem createResetLogLevelColorMenuItem()
/*  702:     */   {
/*  703: 781 */     JMenuItem result = new JMenuItem("Reset LogLevel Colors");
/*  704: 782 */     result.setMnemonic('r');
/*  705: 783 */     result.addActionListener(new ActionListener()
/*  706:     */     {
/*  707:     */       public void actionPerformed(ActionEvent e)
/*  708:     */       {
/*  709: 786 */         LogLevel.resetLogLevelColorMap();
/*  710:     */         
/*  711:     */ 
/*  712: 789 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  713:     */       }
/*  714: 791 */     });
/*  715: 792 */     return result;
/*  716:     */   }
/*  717:     */   
/*  718:     */   protected void selectAllLogLevels(boolean selected)
/*  719:     */   {
/*  720: 796 */     Iterator levels = getLogLevels();
/*  721: 797 */     while (levels.hasNext()) {
/*  722: 798 */       getMenuItem((LogLevel)levels.next()).setSelected(selected);
/*  723:     */     }
/*  724:     */   }
/*  725:     */   
/*  726:     */   protected JCheckBoxMenuItem getMenuItem(LogLevel level)
/*  727:     */   {
/*  728: 803 */     JCheckBoxMenuItem result = (JCheckBoxMenuItem)this._logLevelMenuItems.get(level);
/*  729: 804 */     if (result == null)
/*  730:     */     {
/*  731: 805 */       result = createMenuItem(level);
/*  732: 806 */       this._logLevelMenuItems.put(level, result);
/*  733:     */     }
/*  734: 808 */     return result;
/*  735:     */   }
/*  736:     */   
/*  737:     */   protected JMenuItem createSubMenuItem(LogLevel level)
/*  738:     */   {
/*  739: 812 */     JMenuItem result = new JMenuItem(level.toString());
/*  740: 813 */     LogLevel logLevel = level;
/*  741: 814 */     result.setMnemonic(level.toString().charAt(0));
/*  742: 815 */     result.addActionListener(new ActionListener()
/*  743:     */     {
/*  744:     */       private final JMenuItem val$result;
/*  745:     */       private final LogLevel val$logLevel;
/*  746:     */       
/*  747:     */       public void actionPerformed(ActionEvent e)
/*  748:     */       {
/*  749: 817 */         LogBrokerMonitor.this.showLogLevelColorChangeDialog(this.val$result, this.val$logLevel);
/*  750:     */       }
/*  751: 820 */     });
/*  752: 821 */     return result;
/*  753:     */   }
/*  754:     */   
/*  755:     */   protected void showLogLevelColorChangeDialog(JMenuItem result, LogLevel level)
/*  756:     */   {
/*  757: 826 */     JMenuItem menuItem = result;
/*  758: 827 */     Color newColor = JColorChooser.showDialog(this._logMonitorFrame, "Choose LogLevel Color", result.getForeground());
/*  759: 832 */     if (newColor != null)
/*  760:     */     {
/*  761: 834 */       level.setLogLevelColorMap(level, newColor);
/*  762: 835 */       this._table.getFilteredLogTableModel().refresh();
/*  763:     */     }
/*  764:     */   }
/*  765:     */   
/*  766:     */   protected JCheckBoxMenuItem createMenuItem(LogLevel level)
/*  767:     */   {
/*  768: 841 */     JCheckBoxMenuItem result = new JCheckBoxMenuItem(level.toString());
/*  769: 842 */     result.setSelected(true);
/*  770: 843 */     result.setMnemonic(level.toString().charAt(0));
/*  771: 844 */     result.addActionListener(new ActionListener()
/*  772:     */     {
/*  773:     */       public void actionPerformed(ActionEvent e)
/*  774:     */       {
/*  775: 846 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/*  776: 847 */         LogBrokerMonitor.this.updateStatusLabel();
/*  777:     */       }
/*  778: 849 */     });
/*  779: 850 */     return result;
/*  780:     */   }
/*  781:     */   
/*  782:     */   protected JMenu createViewMenu()
/*  783:     */   {
/*  784: 855 */     JMenu result = new JMenu("View");
/*  785: 856 */     result.setMnemonic('v');
/*  786: 857 */     Iterator columns = getLogTableColumns();
/*  787: 858 */     while (columns.hasNext()) {
/*  788: 859 */       result.add(getLogTableColumnMenuItem((LogTableColumn)columns.next()));
/*  789:     */     }
/*  790: 862 */     result.addSeparator();
/*  791: 863 */     result.add(createAllLogTableColumnsMenuItem());
/*  792: 864 */     result.add(createNoLogTableColumnsMenuItem());
/*  793: 865 */     return result;
/*  794:     */   }
/*  795:     */   
/*  796:     */   protected JCheckBoxMenuItem getLogTableColumnMenuItem(LogTableColumn column)
/*  797:     */   {
/*  798: 869 */     JCheckBoxMenuItem result = (JCheckBoxMenuItem)this._logTableColumnMenuItems.get(column);
/*  799: 870 */     if (result == null)
/*  800:     */     {
/*  801: 871 */       result = createLogTableColumnMenuItem(column);
/*  802: 872 */       this._logTableColumnMenuItems.put(column, result);
/*  803:     */     }
/*  804: 874 */     return result;
/*  805:     */   }
/*  806:     */   
/*  807:     */   protected JCheckBoxMenuItem createLogTableColumnMenuItem(LogTableColumn column)
/*  808:     */   {
/*  809: 878 */     JCheckBoxMenuItem result = new JCheckBoxMenuItem(column.toString());
/*  810:     */     
/*  811: 880 */     result.setSelected(true);
/*  812: 881 */     result.setMnemonic(column.toString().charAt(0));
/*  813: 882 */     result.addActionListener(new ActionListener()
/*  814:     */     {
/*  815:     */       public void actionPerformed(ActionEvent e)
/*  816:     */       {
/*  817: 885 */         List selectedColumns = LogBrokerMonitor.this.updateView();
/*  818: 886 */         LogBrokerMonitor.this._table.setView(selectedColumns);
/*  819:     */       }
/*  820: 888 */     });
/*  821: 889 */     return result;
/*  822:     */   }
/*  823:     */   
/*  824:     */   protected List updateView()
/*  825:     */   {
/*  826: 893 */     ArrayList updatedList = new ArrayList();
/*  827: 894 */     Iterator columnIterator = this._columns.iterator();
/*  828: 895 */     while (columnIterator.hasNext())
/*  829:     */     {
/*  830: 896 */       LogTableColumn column = (LogTableColumn)columnIterator.next();
/*  831: 897 */       JCheckBoxMenuItem result = getLogTableColumnMenuItem(column);
/*  832: 899 */       if (result.isSelected()) {
/*  833: 900 */         updatedList.add(column);
/*  834:     */       }
/*  835:     */     }
/*  836: 904 */     return updatedList;
/*  837:     */   }
/*  838:     */   
/*  839:     */   protected JMenuItem createAllLogTableColumnsMenuItem()
/*  840:     */   {
/*  841: 908 */     JMenuItem result = new JMenuItem("Show all Columns");
/*  842: 909 */     result.setMnemonic('s');
/*  843: 910 */     result.addActionListener(new ActionListener()
/*  844:     */     {
/*  845:     */       public void actionPerformed(ActionEvent e)
/*  846:     */       {
/*  847: 912 */         LogBrokerMonitor.this.selectAllLogTableColumns(true);
/*  848:     */         
/*  849: 914 */         List selectedColumns = LogBrokerMonitor.this.updateView();
/*  850: 915 */         LogBrokerMonitor.this._table.setView(selectedColumns);
/*  851:     */       }
/*  852: 917 */     });
/*  853: 918 */     return result;
/*  854:     */   }
/*  855:     */   
/*  856:     */   protected JMenuItem createNoLogTableColumnsMenuItem()
/*  857:     */   {
/*  858: 922 */     JMenuItem result = new JMenuItem("Hide all Columns");
/*  859: 923 */     result.setMnemonic('h');
/*  860: 924 */     result.addActionListener(new ActionListener()
/*  861:     */     {
/*  862:     */       public void actionPerformed(ActionEvent e)
/*  863:     */       {
/*  864: 926 */         LogBrokerMonitor.this.selectAllLogTableColumns(false);
/*  865:     */         
/*  866: 928 */         List selectedColumns = LogBrokerMonitor.this.updateView();
/*  867: 929 */         LogBrokerMonitor.this._table.setView(selectedColumns);
/*  868:     */       }
/*  869: 931 */     });
/*  870: 932 */     return result;
/*  871:     */   }
/*  872:     */   
/*  873:     */   protected void selectAllLogTableColumns(boolean selected)
/*  874:     */   {
/*  875: 936 */     Iterator columns = getLogTableColumns();
/*  876: 937 */     while (columns.hasNext()) {
/*  877: 938 */       getLogTableColumnMenuItem((LogTableColumn)columns.next()).setSelected(selected);
/*  878:     */     }
/*  879:     */   }
/*  880:     */   
/*  881:     */   protected JMenu createFileMenu()
/*  882:     */   {
/*  883: 943 */     JMenu fileMenu = new JMenu("File");
/*  884: 944 */     fileMenu.setMnemonic('f');
/*  885:     */     
/*  886: 946 */     fileMenu.add(createOpenMI());
/*  887: 947 */     fileMenu.add(createOpenURLMI());
/*  888: 948 */     fileMenu.addSeparator();
/*  889: 949 */     fileMenu.add(createCloseMI());
/*  890: 950 */     createMRUFileListMI(fileMenu);
/*  891: 951 */     fileMenu.addSeparator();
/*  892: 952 */     fileMenu.add(createExitMI());
/*  893: 953 */     return fileMenu;
/*  894:     */   }
/*  895:     */   
/*  896:     */   protected JMenuItem createOpenMI()
/*  897:     */   {
/*  898: 961 */     JMenuItem result = new JMenuItem("Open...");
/*  899: 962 */     result.setMnemonic('o');
/*  900: 963 */     result.addActionListener(new ActionListener()
/*  901:     */     {
/*  902:     */       public void actionPerformed(ActionEvent e)
/*  903:     */       {
/*  904: 965 */         LogBrokerMonitor.this.requestOpen();
/*  905:     */       }
/*  906: 967 */     });
/*  907: 968 */     return result;
/*  908:     */   }
/*  909:     */   
/*  910:     */   protected JMenuItem createOpenURLMI()
/*  911:     */   {
/*  912: 976 */     JMenuItem result = new JMenuItem("Open URL...");
/*  913: 977 */     result.setMnemonic('u');
/*  914: 978 */     result.addActionListener(new ActionListener()
/*  915:     */     {
/*  916:     */       public void actionPerformed(ActionEvent e)
/*  917:     */       {
/*  918: 980 */         LogBrokerMonitor.this.requestOpenURL();
/*  919:     */       }
/*  920: 982 */     });
/*  921: 983 */     return result;
/*  922:     */   }
/*  923:     */   
/*  924:     */   protected JMenuItem createCloseMI()
/*  925:     */   {
/*  926: 987 */     JMenuItem result = new JMenuItem("Close");
/*  927: 988 */     result.setMnemonic('c');
/*  928: 989 */     result.setAccelerator(KeyStroke.getKeyStroke("control Q"));
/*  929: 990 */     result.addActionListener(new ActionListener()
/*  930:     */     {
/*  931:     */       public void actionPerformed(ActionEvent e)
/*  932:     */       {
/*  933: 992 */         LogBrokerMonitor.this.requestClose();
/*  934:     */       }
/*  935: 994 */     });
/*  936: 995 */     return result;
/*  937:     */   }
/*  938:     */   
/*  939:     */   protected void createMRUFileListMI(JMenu menu)
/*  940:     */   {
/*  941:1004 */     String[] files = this._mruFileManager.getMRUFileList();
/*  942:1006 */     if (files != null)
/*  943:     */     {
/*  944:1007 */       menu.addSeparator();
/*  945:1008 */       for (int i = 0; i < files.length; i++)
/*  946:     */       {
/*  947:1009 */         JMenuItem result = new JMenuItem(i + 1 + " " + files[i]);
/*  948:1010 */         result.setMnemonic(i + 1);
/*  949:1011 */         result.addActionListener(new ActionListener()
/*  950:     */         {
/*  951:     */           public void actionPerformed(ActionEvent e)
/*  952:     */           {
/*  953:1013 */             LogBrokerMonitor.this.requestOpenMRU(e);
/*  954:     */           }
/*  955:1015 */         });
/*  956:1016 */         menu.add(result);
/*  957:     */       }
/*  958:     */     }
/*  959:     */   }
/*  960:     */   
/*  961:     */   protected JMenuItem createExitMI()
/*  962:     */   {
/*  963:1022 */     JMenuItem result = new JMenuItem("Exit");
/*  964:1023 */     result.setMnemonic('x');
/*  965:1024 */     result.addActionListener(new ActionListener()
/*  966:     */     {
/*  967:     */       public void actionPerformed(ActionEvent e)
/*  968:     */       {
/*  969:1026 */         LogBrokerMonitor.this.requestExit();
/*  970:     */       }
/*  971:1028 */     });
/*  972:1029 */     return result;
/*  973:     */   }
/*  974:     */   
/*  975:     */   protected JMenu createConfigureMenu()
/*  976:     */   {
/*  977:1033 */     JMenu configureMenu = new JMenu("Configure");
/*  978:1034 */     configureMenu.setMnemonic('c');
/*  979:1035 */     configureMenu.add(createConfigureSave());
/*  980:1036 */     configureMenu.add(createConfigureReset());
/*  981:1037 */     configureMenu.add(createConfigureMaxRecords());
/*  982:     */     
/*  983:1039 */     return configureMenu;
/*  984:     */   }
/*  985:     */   
/*  986:     */   protected JMenuItem createConfigureSave()
/*  987:     */   {
/*  988:1043 */     JMenuItem result = new JMenuItem("Save");
/*  989:1044 */     result.setMnemonic('s');
/*  990:1045 */     result.addActionListener(new ActionListener()
/*  991:     */     {
/*  992:     */       public void actionPerformed(ActionEvent e)
/*  993:     */       {
/*  994:1047 */         LogBrokerMonitor.this.saveConfiguration();
/*  995:     */       }
/*  996:1050 */     });
/*  997:1051 */     return result;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   protected JMenuItem createConfigureReset()
/* 1001:     */   {
/* 1002:1055 */     JMenuItem result = new JMenuItem("Reset");
/* 1003:1056 */     result.setMnemonic('r');
/* 1004:1057 */     result.addActionListener(new ActionListener()
/* 1005:     */     {
/* 1006:     */       public void actionPerformed(ActionEvent e)
/* 1007:     */       {
/* 1008:1059 */         LogBrokerMonitor.this.resetConfiguration();
/* 1009:     */       }
/* 1010:1062 */     });
/* 1011:1063 */     return result;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   protected JMenuItem createConfigureMaxRecords()
/* 1015:     */   {
/* 1016:1067 */     JMenuItem result = new JMenuItem("Set Max Number of Records");
/* 1017:1068 */     result.setMnemonic('m');
/* 1018:1069 */     result.addActionListener(new ActionListener()
/* 1019:     */     {
/* 1020:     */       public void actionPerformed(ActionEvent e)
/* 1021:     */       {
/* 1022:1071 */         LogBrokerMonitor.this.setMaxRecordConfiguration();
/* 1023:     */       }
/* 1024:1074 */     });
/* 1025:1075 */     return result;
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   protected void saveConfiguration()
/* 1029:     */   {
/* 1030:1080 */     this._configurationManager.save();
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   protected void resetConfiguration()
/* 1034:     */   {
/* 1035:1084 */     this._configurationManager.reset();
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   protected void setMaxRecordConfiguration()
/* 1039:     */   {
/* 1040:1088 */     LogFactor5InputDialog inputDialog = new LogFactor5InputDialog(getBaseFrame(), "Set Max Number of Records", "", 10);
/* 1041:     */     
/* 1042:     */ 
/* 1043:1091 */     String temp = inputDialog.getText();
/* 1044:1093 */     if (temp != null) {
/* 1045:     */       try
/* 1046:     */       {
/* 1047:1095 */         setMaxNumberOfLogRecords(Integer.parseInt(temp));
/* 1048:     */       }
/* 1049:     */       catch (NumberFormatException e)
/* 1050:     */       {
/* 1051:1097 */         LogFactor5ErrorDialog error = new LogFactor5ErrorDialog(getBaseFrame(), "'" + temp + "' is an invalid parameter.\nPlease try again.");
/* 1052:     */         
/* 1053:     */ 
/* 1054:1100 */         setMaxRecordConfiguration();
/* 1055:     */       }
/* 1056:     */     }
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   protected JMenu createHelpMenu()
/* 1060:     */   {
/* 1061:1107 */     JMenu helpMenu = new JMenu("Help");
/* 1062:1108 */     helpMenu.setMnemonic('h');
/* 1063:1109 */     helpMenu.add(createHelpProperties());
/* 1064:1110 */     return helpMenu;
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   protected JMenuItem createHelpProperties()
/* 1068:     */   {
/* 1069:1114 */     String title = "LogFactor5 Properties";
/* 1070:1115 */     JMenuItem result = new JMenuItem("LogFactor5 Properties");
/* 1071:1116 */     result.setMnemonic('l');
/* 1072:1117 */     result.addActionListener(new ActionListener()
/* 1073:     */     {
/* 1074:     */       public void actionPerformed(ActionEvent e)
/* 1075:     */       {
/* 1076:1119 */         LogBrokerMonitor.this.showPropertiesDialog("LogFactor5 Properties");
/* 1077:     */       }
/* 1078:1121 */     });
/* 1079:1122 */     return result;
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   protected void showPropertiesDialog(String title)
/* 1083:     */   {
/* 1084:1126 */     JOptionPane.showMessageDialog(this._logMonitorFrame, this._displayedLogBrokerProperties.toArray(), title, -1);
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   protected JMenu createEditMenu()
/* 1088:     */   {
/* 1089:1135 */     JMenu editMenu = new JMenu("Edit");
/* 1090:1136 */     editMenu.setMnemonic('e');
/* 1091:1137 */     editMenu.add(createEditFindMI());
/* 1092:1138 */     editMenu.add(createEditFindNextMI());
/* 1093:1139 */     editMenu.addSeparator();
/* 1094:1140 */     editMenu.add(createEditSortNDCMI());
/* 1095:1141 */     editMenu.add(createEditRestoreAllNDCMI());
/* 1096:1142 */     return editMenu;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   protected JMenuItem createEditFindNextMI()
/* 1100:     */   {
/* 1101:1146 */     JMenuItem editFindNextMI = new JMenuItem("Find Next");
/* 1102:1147 */     editFindNextMI.setMnemonic('n');
/* 1103:1148 */     editFindNextMI.setAccelerator(KeyStroke.getKeyStroke("F3"));
/* 1104:1149 */     editFindNextMI.addActionListener(new ActionListener()
/* 1105:     */     {
/* 1106:     */       public void actionPerformed(ActionEvent e)
/* 1107:     */       {
/* 1108:1151 */         LogBrokerMonitor.this.findSearchText();
/* 1109:     */       }
/* 1110:1153 */     });
/* 1111:1154 */     return editFindNextMI;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   protected JMenuItem createEditFindMI()
/* 1115:     */   {
/* 1116:1158 */     JMenuItem editFindMI = new JMenuItem("Find");
/* 1117:1159 */     editFindMI.setMnemonic('f');
/* 1118:1160 */     editFindMI.setAccelerator(KeyStroke.getKeyStroke("control F"));
/* 1119:     */     
/* 1120:1162 */     editFindMI.addActionListener(new ActionListener()
/* 1121:     */     {
/* 1122:     */       public void actionPerformed(ActionEvent e)
/* 1123:     */       {
/* 1124:1165 */         String inputValue = JOptionPane.showInputDialog(LogBrokerMonitor.this._logMonitorFrame, "Find text: ", "Search Record Messages", 3);
/* 1125:     */         
/* 1126:     */ 
/* 1127:     */ 
/* 1128:     */ 
/* 1129:     */ 
/* 1130:     */ 
/* 1131:1172 */         LogBrokerMonitor.this.setSearchText(inputValue);
/* 1132:1173 */         LogBrokerMonitor.this.findSearchText();
/* 1133:     */       }
/* 1134:1177 */     });
/* 1135:1178 */     return editFindMI;
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   protected JMenuItem createEditSortNDCMI()
/* 1139:     */   {
/* 1140:1185 */     JMenuItem editSortNDCMI = new JMenuItem("Sort by NDC");
/* 1141:1186 */     editSortNDCMI.setMnemonic('s');
/* 1142:1187 */     editSortNDCMI.addActionListener(new ActionListener()
/* 1143:     */     {
/* 1144:     */       public void actionPerformed(ActionEvent e)
/* 1145:     */       {
/* 1146:1190 */         String inputValue = JOptionPane.showInputDialog(LogBrokerMonitor.this._logMonitorFrame, "Sort by this NDC: ", "Sort Log Records by NDC", 3);
/* 1147:     */         
/* 1148:     */ 
/* 1149:     */ 
/* 1150:     */ 
/* 1151:     */ 
/* 1152:     */ 
/* 1153:1197 */         LogBrokerMonitor.this.setNDCTextFilter(inputValue);
/* 1154:1198 */         LogBrokerMonitor.this.sortByNDC();
/* 1155:1199 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/* 1156:1200 */         LogBrokerMonitor.this.updateStatusLabel();
/* 1157:     */       }
/* 1158:1204 */     });
/* 1159:1205 */     return editSortNDCMI;
/* 1160:     */   }
/* 1161:     */   
/* 1162:     */   protected JMenuItem createEditRestoreAllNDCMI()
/* 1163:     */   {
/* 1164:1211 */     JMenuItem editRestoreAllNDCMI = new JMenuItem("Restore all NDCs");
/* 1165:1212 */     editRestoreAllNDCMI.setMnemonic('r');
/* 1166:1213 */     editRestoreAllNDCMI.addActionListener(new ActionListener()
/* 1167:     */     {
/* 1168:     */       public void actionPerformed(ActionEvent e)
/* 1169:     */       {
/* 1170:1216 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().setLogRecordFilter(LogBrokerMonitor.this.createLogRecordFilter());
/* 1171:     */         
/* 1172:1218 */         LogBrokerMonitor.this.setNDCTextFilter("");
/* 1173:1219 */         LogBrokerMonitor.this._table.getFilteredLogTableModel().refresh();
/* 1174:1220 */         LogBrokerMonitor.this.updateStatusLabel();
/* 1175:     */       }
/* 1176:1223 */     });
/* 1177:1224 */     return editRestoreAllNDCMI;
/* 1178:     */   }
/* 1179:     */   
/* 1180:     */   protected JToolBar createToolBar()
/* 1181:     */   {
/* 1182:1228 */     JToolBar tb = new JToolBar();
/* 1183:1229 */     tb.putClientProperty("JToolBar.isRollover", Boolean.TRUE);
/* 1184:1230 */     JComboBox fontCombo = new JComboBox();
/* 1185:1231 */     JComboBox fontSizeCombo = new JComboBox();
/* 1186:1232 */     this._fontSizeCombo = fontSizeCombo;
/* 1187:     */     
/* 1188:1234 */     ClassLoader cl = getClass().getClassLoader();
/* 1189:1235 */     if (cl == null) {
/* 1190:1236 */       cl = ClassLoader.getSystemClassLoader();
/* 1191:     */     }
/* 1192:1238 */     URL newIconURL = cl.getResource("org/apache/log4j/lf5/viewer/images/channelexplorer_new.gif");
/* 1193:     */     
/* 1194:     */ 
/* 1195:1241 */     ImageIcon newIcon = null;
/* 1196:1243 */     if (newIconURL != null) {
/* 1197:1244 */       newIcon = new ImageIcon(newIconURL);
/* 1198:     */     }
/* 1199:1247 */     JButton newButton = new JButton("Clear Log Table");
/* 1200:1249 */     if (newIcon != null) {
/* 1201:1250 */       newButton.setIcon(newIcon);
/* 1202:     */     }
/* 1203:1253 */     newButton.setToolTipText("Clear Log Table.");
/* 1204:     */     
/* 1205:     */ 
/* 1206:1256 */     newButton.addActionListener(new ActionListener()
/* 1207:     */     {
/* 1208:     */       public void actionPerformed(ActionEvent e)
/* 1209:     */       {
/* 1210:1259 */         LogBrokerMonitor.this._table.clearLogRecords();
/* 1211:1260 */         LogBrokerMonitor.this._categoryExplorerTree.getExplorerModel().resetAllNodeCounts();
/* 1212:1261 */         LogBrokerMonitor.this.updateStatusLabel();
/* 1213:1262 */         LogBrokerMonitor.this.clearDetailTextArea();
/* 1214:1263 */         LogRecord.resetSequenceNumber();
/* 1215:     */       }
/* 1216:1267 */     });
/* 1217:1268 */     Toolkit tk = Toolkit.getDefaultToolkit();
/* 1218:     */     String[] fonts;
/* 1219:     */     String[] fonts;
/* 1220:1273 */     if (this._loadSystemFonts) {
/* 1221:1274 */       fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
/* 1222:     */     } else {
/* 1223:1277 */       fonts = tk.getFontList();
/* 1224:     */     }
/* 1225:1280 */     for (int j = 0; j < fonts.length; j++) {
/* 1226:1281 */       fontCombo.addItem(fonts[j]);
/* 1227:     */     }
/* 1228:1284 */     fontCombo.setSelectedItem(this._fontName);
/* 1229:     */     
/* 1230:1286 */     fontCombo.addActionListener(new ActionListener()
/* 1231:     */     {
/* 1232:     */       public void actionPerformed(ActionEvent e)
/* 1233:     */       {
/* 1234:1290 */         JComboBox box = (JComboBox)e.getSource();
/* 1235:1291 */         String font = (String)box.getSelectedItem();
/* 1236:1292 */         LogBrokerMonitor.this._table.setFont(new Font(font, 0, LogBrokerMonitor.this._fontSize));
/* 1237:1293 */         LogBrokerMonitor.this._fontName = font;
/* 1238:     */       }
/* 1239:1297 */     });
/* 1240:1298 */     fontSizeCombo.addItem("8");
/* 1241:1299 */     fontSizeCombo.addItem("9");
/* 1242:1300 */     fontSizeCombo.addItem("10");
/* 1243:1301 */     fontSizeCombo.addItem("12");
/* 1244:1302 */     fontSizeCombo.addItem("14");
/* 1245:1303 */     fontSizeCombo.addItem("16");
/* 1246:1304 */     fontSizeCombo.addItem("18");
/* 1247:1305 */     fontSizeCombo.addItem("24");
/* 1248:     */     
/* 1249:1307 */     fontSizeCombo.setSelectedItem(String.valueOf(this._fontSize));
/* 1250:1308 */     fontSizeCombo.addActionListener(new ActionListener()
/* 1251:     */     {
/* 1252:     */       public void actionPerformed(ActionEvent e)
/* 1253:     */       {
/* 1254:1311 */         JComboBox box = (JComboBox)e.getSource();
/* 1255:1312 */         String size = (String)box.getSelectedItem();
/* 1256:1313 */         int s = Integer.valueOf(size).intValue();
/* 1257:     */         
/* 1258:1315 */         LogBrokerMonitor.this.setFontSizeSilently(s);
/* 1259:1316 */         LogBrokerMonitor.this.refreshDetailTextArea();
/* 1260:1317 */         LogBrokerMonitor.this._fontSize = s;
/* 1261:     */       }
/* 1262:1321 */     });
/* 1263:1322 */     tb.add(new JLabel(" Font: "));
/* 1264:1323 */     tb.add(fontCombo);
/* 1265:1324 */     tb.add(fontSizeCombo);
/* 1266:1325 */     tb.addSeparator();
/* 1267:1326 */     tb.addSeparator();
/* 1268:1327 */     tb.add(newButton);
/* 1269:     */     
/* 1270:1329 */     newButton.setAlignmentY(0.5F);
/* 1271:1330 */     newButton.setAlignmentX(0.5F);
/* 1272:     */     
/* 1273:1332 */     fontCombo.setMaximumSize(fontCombo.getPreferredSize());
/* 1274:1333 */     fontSizeCombo.setMaximumSize(fontSizeCombo.getPreferredSize());
/* 1275:     */     
/* 1276:     */ 
/* 1277:1336 */     return tb;
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   protected void setView(String viewString, LogTable table)
/* 1281:     */   {
/* 1282:1354 */     if ("Detailed".equals(viewString))
/* 1283:     */     {
/* 1284:1355 */       table.setDetailedView();
/* 1285:     */     }
/* 1286:     */     else
/* 1287:     */     {
/* 1288:1357 */       String message = viewString + "does not match a supported view.";
/* 1289:1358 */       throw new IllegalArgumentException(message);
/* 1290:     */     }
/* 1291:1360 */     this._currentView = viewString;
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   protected JComboBox createLogLevelCombo()
/* 1295:     */   {
/* 1296:1364 */     JComboBox result = new JComboBox();
/* 1297:1365 */     Iterator levels = getLogLevels();
/* 1298:1366 */     while (levels.hasNext()) {
/* 1299:1367 */       result.addItem(levels.next());
/* 1300:     */     }
/* 1301:1369 */     result.setSelectedItem(this._leastSevereDisplayedLogLevel);
/* 1302:     */     
/* 1303:1371 */     result.addActionListener(new ActionListener()
/* 1304:     */     {
/* 1305:     */       public void actionPerformed(ActionEvent e)
/* 1306:     */       {
/* 1307:1373 */         JComboBox box = (JComboBox)e.getSource();
/* 1308:1374 */         LogLevel level = (LogLevel)box.getSelectedItem();
/* 1309:1375 */         LogBrokerMonitor.this.setLeastSevereDisplayedLogLevel(level);
/* 1310:     */       }
/* 1311:1377 */     });
/* 1312:1378 */     result.setMaximumSize(result.getPreferredSize());
/* 1313:1379 */     return result;
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   protected void setLeastSevereDisplayedLogLevel(LogLevel level)
/* 1317:     */   {
/* 1318:1383 */     if ((level == null) || (this._leastSevereDisplayedLogLevel == level)) {
/* 1319:1384 */       return;
/* 1320:     */     }
/* 1321:1386 */     this._leastSevereDisplayedLogLevel = level;
/* 1322:1387 */     this._table.getFilteredLogTableModel().refresh();
/* 1323:1388 */     updateStatusLabel();
/* 1324:     */   }
/* 1325:     */   
/* 1326:     */   /**
/* 1327:     */    * @deprecated
/* 1328:     */    */
/* 1329:     */   protected void trackTableScrollPane() {}
/* 1330:     */   
/* 1331:     */   protected void centerFrame(JFrame frame)
/* 1332:     */   {
/* 1333:1406 */     Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
/* 1334:1407 */     Dimension comp = frame.getSize();
/* 1335:     */     
/* 1336:1409 */     frame.setLocation((screen.width - comp.width) / 2, (screen.height - comp.height) / 2);
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   protected void requestOpen()
/* 1340:     */   {
/* 1341:     */     JFileChooser chooser;
/* 1342:     */     JFileChooser chooser;
/* 1343:1421 */     if (this._fileLocation == null) {
/* 1344:1422 */       chooser = new JFileChooser();
/* 1345:     */     } else {
/* 1346:1424 */       chooser = new JFileChooser(this._fileLocation);
/* 1347:     */     }
/* 1348:1427 */     int returnVal = chooser.showOpenDialog(this._logMonitorFrame);
/* 1349:1428 */     if (returnVal == 0)
/* 1350:     */     {
/* 1351:1429 */       File f = chooser.getSelectedFile();
/* 1352:1430 */       if (loadLogFile(f))
/* 1353:     */       {
/* 1354:1431 */         this._fileLocation = chooser.getSelectedFile();
/* 1355:1432 */         this._mruFileManager.set(f);
/* 1356:1433 */         updateMRUList();
/* 1357:     */       }
/* 1358:     */     }
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   protected void requestOpenURL()
/* 1362:     */   {
/* 1363:1443 */     LogFactor5InputDialog inputDialog = new LogFactor5InputDialog(getBaseFrame(), "Open URL", "URL:");
/* 1364:     */     
/* 1365:1445 */     String temp = inputDialog.getText();
/* 1366:     */     LogFactor5ErrorDialog error;
/* 1367:1447 */     if (temp != null)
/* 1368:     */     {
/* 1369:1448 */       if (temp.indexOf("://") == -1) {
/* 1370:1449 */         temp = "http://" + temp;
/* 1371:     */       }
/* 1372:     */       try
/* 1373:     */       {
/* 1374:1453 */         URL url = new URL(temp);
/* 1375:1454 */         if (loadLogFile(url))
/* 1376:     */         {
/* 1377:1455 */           this._mruFileManager.set(url);
/* 1378:1456 */           updateMRUList();
/* 1379:     */         }
/* 1380:     */       }
/* 1381:     */       catch (MalformedURLException e)
/* 1382:     */       {
/* 1383:1459 */         error = new LogFactor5ErrorDialog(getBaseFrame(), "Error reading URL.");
/* 1384:     */       }
/* 1385:     */     }
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   protected void updateMRUList()
/* 1389:     */   {
/* 1390:1470 */     JMenu menu = this._logMonitorFrame.getJMenuBar().getMenu(0);
/* 1391:1471 */     menu.removeAll();
/* 1392:1472 */     menu.add(createOpenMI());
/* 1393:1473 */     menu.add(createOpenURLMI());
/* 1394:1474 */     menu.addSeparator();
/* 1395:1475 */     menu.add(createCloseMI());
/* 1396:1476 */     createMRUFileListMI(menu);
/* 1397:1477 */     menu.addSeparator();
/* 1398:1478 */     menu.add(createExitMI());
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   protected void requestClose()
/* 1402:     */   {
/* 1403:1482 */     setCallSystemExitOnClose(false);
/* 1404:1483 */     closeAfterConfirm();
/* 1405:     */   }
/* 1406:     */   
/* 1407:     */   protected void requestOpenMRU(ActionEvent e)
/* 1408:     */   {
/* 1409:1490 */     String file = e.getActionCommand();
/* 1410:1491 */     StringTokenizer st = new StringTokenizer(file);
/* 1411:1492 */     String num = st.nextToken().trim();
/* 1412:1493 */     file = st.nextToken("\n");
/* 1413:     */     LogFactor5ErrorDialog error;
/* 1414:     */     try
/* 1415:     */     {
/* 1416:1496 */       int index = Integer.parseInt(num) - 1;
/* 1417:     */       
/* 1418:1498 */       InputStream in = this._mruFileManager.getInputStream(index);
/* 1419:1499 */       LogFileParser lfp = new LogFileParser(in);
/* 1420:1500 */       lfp.parse(this);
/* 1421:     */       
/* 1422:1502 */       this._mruFileManager.moveToTop(index);
/* 1423:1503 */       updateMRUList();
/* 1424:     */     }
/* 1425:     */     catch (Exception me)
/* 1426:     */     {
/* 1427:1506 */       error = new LogFactor5ErrorDialog(getBaseFrame(), "Unable to load file " + file);
/* 1428:     */     }
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   protected void requestExit()
/* 1432:     */   {
/* 1433:1513 */     this._mruFileManager.save();
/* 1434:1514 */     setCallSystemExitOnClose(true);
/* 1435:1515 */     closeAfterConfirm();
/* 1436:     */   }
/* 1437:     */   
/* 1438:     */   protected void closeAfterConfirm()
/* 1439:     */   {
/* 1440:1519 */     StringBuffer message = new StringBuffer();
/* 1441:1521 */     if (!this._callSystemExitOnClose)
/* 1442:     */     {
/* 1443:1522 */       message.append("Are you sure you want to close the logging ");
/* 1444:1523 */       message.append("console?\n");
/* 1445:1524 */       message.append("(Note: This will not shut down the Virtual Machine,\n");
/* 1446:1525 */       message.append("or the Swing event thread.)");
/* 1447:     */     }
/* 1448:     */     else
/* 1449:     */     {
/* 1450:1527 */       message.append("Are you sure you want to exit?\n");
/* 1451:1528 */       message.append("This will shut down the Virtual Machine.\n");
/* 1452:     */     }
/* 1453:1531 */     String title = "Are you sure you want to dispose of the Logging Console?";
/* 1454:1534 */     if (this._callSystemExitOnClose == true) {
/* 1455:1535 */       title = "Are you sure you want to exit?";
/* 1456:     */     }
/* 1457:1537 */     int value = JOptionPane.showConfirmDialog(this._logMonitorFrame, message.toString(), title, 2, 3, null);
/* 1458:1546 */     if (value == 0) {
/* 1459:1547 */       dispose();
/* 1460:     */     }
/* 1461:     */   }
/* 1462:     */   
/* 1463:     */   protected Iterator getLogLevels()
/* 1464:     */   {
/* 1465:1552 */     return this._levels.iterator();
/* 1466:     */   }
/* 1467:     */   
/* 1468:     */   protected Iterator getLogTableColumns()
/* 1469:     */   {
/* 1470:1556 */     return this._columns.iterator();
/* 1471:     */   }
/* 1472:     */   
/* 1473:     */   protected boolean loadLogFile(File file)
/* 1474:     */   {
/* 1475:1563 */     boolean ok = false;
/* 1476:     */     LogFactor5ErrorDialog error;
/* 1477:     */     try
/* 1478:     */     {
/* 1479:1565 */       LogFileParser lfp = new LogFileParser(file);
/* 1480:1566 */       lfp.parse(this);
/* 1481:1567 */       ok = true;
/* 1482:     */     }
/* 1483:     */     catch (IOException e)
/* 1484:     */     {
/* 1485:1569 */       error = new LogFactor5ErrorDialog(getBaseFrame(), "Error reading " + file.getName());
/* 1486:     */     }
/* 1487:1573 */     return ok;
/* 1488:     */   }
/* 1489:     */   
/* 1490:     */   protected boolean loadLogFile(URL url)
/* 1491:     */   {
/* 1492:1580 */     boolean ok = false;
/* 1493:     */     LogFactor5ErrorDialog error;
/* 1494:     */     try
/* 1495:     */     {
/* 1496:1582 */       LogFileParser lfp = new LogFileParser(url.openStream());
/* 1497:1583 */       lfp.parse(this);
/* 1498:1584 */       ok = true;
/* 1499:     */     }
/* 1500:     */     catch (IOException e)
/* 1501:     */     {
/* 1502:1586 */       error = new LogFactor5ErrorDialog(getBaseFrame(), "Error reading URL:" + url.getFile());
/* 1503:     */     }
/* 1504:1589 */     return ok;
/* 1505:     */   }
/* 1506:     */   
/* 1507:     */   class LogBrokerMonitorWindowAdaptor
/* 1508:     */     extends WindowAdapter
/* 1509:     */   {
/* 1510:     */     protected LogBrokerMonitor _monitor;
/* 1511:     */     
/* 1512:     */     public LogBrokerMonitorWindowAdaptor(LogBrokerMonitor monitor)
/* 1513:     */     {
/* 1514:1603 */       this._monitor = monitor;
/* 1515:     */     }
/* 1516:     */     
/* 1517:     */     public void windowClosing(WindowEvent ev)
/* 1518:     */     {
/* 1519:1607 */       this._monitor.requestClose();
/* 1520:     */     }
/* 1521:     */   }
/* 1522:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.LogBrokerMonitor
 * JD-Core Version:    0.7.0.1
 */