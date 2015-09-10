/*   1:    */ package org.apache.log4j.lf5.viewer.configure;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileWriter;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.PrintWriter;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Enumeration;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Set;
/*  15:    */ import javax.swing.AbstractButton;
/*  16:    */ import javax.swing.JCheckBoxMenuItem;
/*  17:    */ import javax.swing.JTree;
/*  18:    */ import javax.swing.tree.DefaultMutableTreeNode;
/*  19:    */ import javax.swing.tree.TreePath;
/*  20:    */ import javax.xml.parsers.DocumentBuilder;
/*  21:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  22:    */ import org.apache.log4j.lf5.LogLevel;
/*  23:    */ import org.apache.log4j.lf5.LogLevelFormatException;
/*  24:    */ import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
/*  25:    */ import org.apache.log4j.lf5.viewer.LogTable;
/*  26:    */ import org.apache.log4j.lf5.viewer.LogTableColumn;
/*  27:    */ import org.apache.log4j.lf5.viewer.LogTableColumnFormatException;
/*  28:    */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel;
/*  29:    */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree;
/*  30:    */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNode;
/*  31:    */ import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath;
/*  32:    */ import org.w3c.dom.Document;
/*  33:    */ import org.w3c.dom.NamedNodeMap;
/*  34:    */ import org.w3c.dom.Node;
/*  35:    */ import org.w3c.dom.NodeList;
/*  36:    */ 
/*  37:    */ public class ConfigurationManager
/*  38:    */ {
/*  39:    */   private static final String CONFIG_FILE_NAME = "lf5_configuration.xml";
/*  40:    */   private static final String NAME = "name";
/*  41:    */   private static final String PATH = "path";
/*  42:    */   private static final String SELECTED = "selected";
/*  43:    */   private static final String EXPANDED = "expanded";
/*  44:    */   private static final String CATEGORY = "category";
/*  45:    */   private static final String FIRST_CATEGORY_NAME = "Categories";
/*  46:    */   private static final String LEVEL = "level";
/*  47:    */   private static final String COLORLEVEL = "colorlevel";
/*  48:    */   private static final String RED = "red";
/*  49:    */   private static final String GREEN = "green";
/*  50:    */   private static final String BLUE = "blue";
/*  51:    */   private static final String COLUMN = "column";
/*  52:    */   private static final String NDCTEXTFILTER = "searchtext";
/*  53: 85 */   private LogBrokerMonitor _monitor = null;
/*  54: 86 */   private LogTable _table = null;
/*  55:    */   
/*  56:    */   public ConfigurationManager(LogBrokerMonitor monitor, LogTable table)
/*  57:    */   {
/*  58: 93 */     this._monitor = monitor;
/*  59: 94 */     this._table = table;
/*  60: 95 */     load();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void save()
/*  64:    */   {
/*  65:102 */     CategoryExplorerModel model = this._monitor.getCategoryExplorerTree().getExplorerModel();
/*  66:103 */     CategoryNode root = model.getRootCategoryNode();
/*  67:    */     
/*  68:105 */     StringBuffer xml = new StringBuffer(2048);
/*  69:106 */     openXMLDocument(xml);
/*  70:107 */     openConfigurationXML(xml);
/*  71:108 */     processLogRecordFilter(this._monitor.getNDCTextFilter(), xml);
/*  72:109 */     processLogLevels(this._monitor.getLogLevelMenuItems(), xml);
/*  73:110 */     processLogLevelColors(this._monitor.getLogLevelMenuItems(), LogLevel.getLogLevelColorMap(), xml);
/*  74:    */     
/*  75:112 */     processLogTableColumns(LogTableColumn.getLogTableColumns(), xml);
/*  76:113 */     processConfigurationNode(root, xml);
/*  77:114 */     closeConfigurationXML(xml);
/*  78:115 */     store(xml.toString());
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void reset()
/*  82:    */   {
/*  83:119 */     deleteConfigurationFile();
/*  84:120 */     collapseTree();
/*  85:121 */     selectAllNodes();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static String treePathToString(TreePath path)
/*  89:    */   {
/*  90:126 */     StringBuffer sb = new StringBuffer();
/*  91:127 */     CategoryNode n = null;
/*  92:128 */     Object[] objects = path.getPath();
/*  93:129 */     for (int i = 1; i < objects.length; i++)
/*  94:    */     {
/*  95:130 */       n = (CategoryNode)objects[i];
/*  96:131 */       if (i > 1) {
/*  97:132 */         sb.append(".");
/*  98:    */       }
/*  99:134 */       sb.append(n.getTitle());
/* 100:    */     }
/* 101:136 */     return sb.toString();
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void load()
/* 105:    */   {
/* 106:143 */     File file = new File(getFilename());
/* 107:144 */     if (file.exists()) {
/* 108:    */       try
/* 109:    */       {
/* 110:146 */         DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
/* 111:    */         
/* 112:148 */         DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
/* 113:149 */         Document doc = docBuilder.parse(file);
/* 114:150 */         processRecordFilter(doc);
/* 115:151 */         processCategories(doc);
/* 116:152 */         processLogLevels(doc);
/* 117:153 */         processLogLevelColors(doc);
/* 118:154 */         processLogTableColumns(doc);
/* 119:    */       }
/* 120:    */       catch (Exception e)
/* 121:    */       {
/* 122:158 */         System.err.println("Unable process configuration file at " + getFilename() + ". Error Message=" + e.getMessage());
/* 123:    */       }
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   protected void processRecordFilter(Document doc)
/* 128:    */   {
/* 129:171 */     NodeList nodeList = doc.getElementsByTagName("searchtext");
/* 130:    */     
/* 131:    */ 
/* 132:174 */     Node n = nodeList.item(0);
/* 133:177 */     if (n == null) {
/* 134:178 */       return;
/* 135:    */     }
/* 136:181 */     NamedNodeMap map = n.getAttributes();
/* 137:182 */     String text = getValue(map, "name");
/* 138:184 */     if ((text == null) || (text.equals(""))) {
/* 139:185 */       return;
/* 140:    */     }
/* 141:187 */     this._monitor.setNDCLogRecordFilter(text);
/* 142:    */   }
/* 143:    */   
/* 144:    */   protected void processCategories(Document doc)
/* 145:    */   {
/* 146:191 */     CategoryExplorerTree tree = this._monitor.getCategoryExplorerTree();
/* 147:192 */     CategoryExplorerModel model = tree.getExplorerModel();
/* 148:193 */     NodeList nodeList = doc.getElementsByTagName("category");
/* 149:    */     
/* 150:    */ 
/* 151:196 */     NamedNodeMap map = nodeList.item(0).getAttributes();
/* 152:197 */     int j = getValue(map, "name").equalsIgnoreCase("Categories") ? 1 : 0;
/* 153:200 */     for (int i = nodeList.getLength() - 1; i >= j; i--)
/* 154:    */     {
/* 155:201 */       Node n = nodeList.item(i);
/* 156:202 */       map = n.getAttributes();
/* 157:203 */       CategoryNode chnode = model.addCategory(new CategoryPath(getValue(map, "path")));
/* 158:204 */       chnode.setSelected(getValue(map, "selected").equalsIgnoreCase("true"));
/* 159:205 */       if (getValue(map, "expanded").equalsIgnoreCase("true")) {}
/* 160:206 */       tree.expandPath(model.getTreePathToRoot(chnode));
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   protected void processLogLevels(Document doc)
/* 165:    */   {
/* 166:212 */     NodeList nodeList = doc.getElementsByTagName("level");
/* 167:213 */     Map menuItems = this._monitor.getLogLevelMenuItems();
/* 168:215 */     for (int i = 0; i < nodeList.getLength(); i++)
/* 169:    */     {
/* 170:216 */       Node n = nodeList.item(i);
/* 171:217 */       NamedNodeMap map = n.getAttributes();
/* 172:218 */       String name = getValue(map, "name");
/* 173:    */       try
/* 174:    */       {
/* 175:220 */         JCheckBoxMenuItem item = (JCheckBoxMenuItem)menuItems.get(LogLevel.valueOf(name));
/* 176:    */         
/* 177:222 */         item.setSelected(getValue(map, "selected").equalsIgnoreCase("true"));
/* 178:    */       }
/* 179:    */       catch (LogLevelFormatException e) {}
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected void processLogLevelColors(Document doc)
/* 184:    */   {
/* 185:230 */     NodeList nodeList = doc.getElementsByTagName("colorlevel");
/* 186:231 */     LogLevel.getLogLevelColorMap();
/* 187:233 */     for (int i = 0; i < nodeList.getLength(); i++)
/* 188:    */     {
/* 189:234 */       Node n = nodeList.item(i);
/* 190:237 */       if (n == null) {
/* 191:238 */         return;
/* 192:    */       }
/* 193:241 */       NamedNodeMap map = n.getAttributes();
/* 194:242 */       String name = getValue(map, "name");
/* 195:    */       try
/* 196:    */       {
/* 197:244 */         LogLevel level = LogLevel.valueOf(name);
/* 198:245 */         int red = Integer.parseInt(getValue(map, "red"));
/* 199:246 */         int green = Integer.parseInt(getValue(map, "green"));
/* 200:247 */         int blue = Integer.parseInt(getValue(map, "blue"));
/* 201:248 */         Color c = new Color(red, green, blue);
/* 202:249 */         if (level != null) {
/* 203:250 */           level.setLogLevelColorMap(level, c);
/* 204:    */         }
/* 205:    */       }
/* 206:    */       catch (LogLevelFormatException e) {}
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected void processLogTableColumns(Document doc)
/* 211:    */   {
/* 212:260 */     NodeList nodeList = doc.getElementsByTagName("column");
/* 213:261 */     Map menuItems = this._monitor.getLogTableColumnMenuItems();
/* 214:262 */     List selectedColumns = new ArrayList();
/* 215:263 */     for (int i = 0; i < nodeList.getLength(); i++)
/* 216:    */     {
/* 217:264 */       Node n = nodeList.item(i);
/* 218:267 */       if (n == null) {
/* 219:268 */         return;
/* 220:    */       }
/* 221:270 */       NamedNodeMap map = n.getAttributes();
/* 222:271 */       String name = getValue(map, "name");
/* 223:    */       try
/* 224:    */       {
/* 225:273 */         LogTableColumn column = LogTableColumn.valueOf(name);
/* 226:274 */         JCheckBoxMenuItem item = (JCheckBoxMenuItem)menuItems.get(column);
/* 227:    */         
/* 228:276 */         item.setSelected(getValue(map, "selected").equalsIgnoreCase("true"));
/* 229:278 */         if (item.isSelected()) {
/* 230:279 */           selectedColumns.add(column);
/* 231:    */         }
/* 232:    */       }
/* 233:    */       catch (LogTableColumnFormatException e) {}
/* 234:285 */       if (selectedColumns.isEmpty()) {
/* 235:286 */         this._table.setDetailedView();
/* 236:    */       } else {
/* 237:288 */         this._table.setView(selectedColumns);
/* 238:    */       }
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   protected String getValue(NamedNodeMap map, String attr)
/* 243:    */   {
/* 244:295 */     Node n = map.getNamedItem(attr);
/* 245:296 */     return n.getNodeValue();
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected void collapseTree()
/* 249:    */   {
/* 250:301 */     CategoryExplorerTree tree = this._monitor.getCategoryExplorerTree();
/* 251:302 */     for (int i = tree.getRowCount() - 1; i > 0; i--) {
/* 252:303 */       tree.collapseRow(i);
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   protected void selectAllNodes()
/* 257:    */   {
/* 258:308 */     CategoryExplorerModel model = this._monitor.getCategoryExplorerTree().getExplorerModel();
/* 259:309 */     CategoryNode root = model.getRootCategoryNode();
/* 260:310 */     Enumeration all = root.breadthFirstEnumeration();
/* 261:311 */     CategoryNode n = null;
/* 262:312 */     while (all.hasMoreElements())
/* 263:    */     {
/* 264:313 */       n = (CategoryNode)all.nextElement();
/* 265:314 */       n.setSelected(true);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   protected void store(String s)
/* 270:    */   {
/* 271:    */     try
/* 272:    */     {
/* 273:321 */       PrintWriter writer = new PrintWriter(new FileWriter(getFilename()));
/* 274:322 */       writer.print(s);
/* 275:323 */       writer.close();
/* 276:    */     }
/* 277:    */     catch (IOException e)
/* 278:    */     {
/* 279:326 */       e.printStackTrace();
/* 280:    */     }
/* 281:    */   }
/* 282:    */   
/* 283:    */   protected void deleteConfigurationFile()
/* 284:    */   {
/* 285:    */     try
/* 286:    */     {
/* 287:333 */       File f = new File(getFilename());
/* 288:334 */       if (f.exists()) {
/* 289:335 */         f.delete();
/* 290:    */       }
/* 291:    */     }
/* 292:    */     catch (SecurityException e)
/* 293:    */     {
/* 294:338 */       System.err.println("Cannot delete " + getFilename() + " because a security violation occured.");
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   protected String getFilename()
/* 299:    */   {
/* 300:344 */     String home = System.getProperty("user.home");
/* 301:345 */     String sep = System.getProperty("file.separator");
/* 302:    */     
/* 303:347 */     return home + sep + "lf5" + sep + "lf5_configuration.xml";
/* 304:    */   }
/* 305:    */   
/* 306:    */   private void processConfigurationNode(CategoryNode node, StringBuffer xml)
/* 307:    */   {
/* 308:354 */     CategoryExplorerModel model = this._monitor.getCategoryExplorerTree().getExplorerModel();
/* 309:    */     
/* 310:356 */     Enumeration all = node.breadthFirstEnumeration();
/* 311:357 */     CategoryNode n = null;
/* 312:358 */     while (all.hasMoreElements())
/* 313:    */     {
/* 314:359 */       n = (CategoryNode)all.nextElement();
/* 315:360 */       exportXMLElement(n, model.getTreePathToRoot(n), xml);
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   private void processLogLevels(Map logLevelMenuItems, StringBuffer xml)
/* 320:    */   {
/* 321:366 */     xml.append("\t<loglevels>\r\n");
/* 322:367 */     Iterator it = logLevelMenuItems.keySet().iterator();
/* 323:368 */     while (it.hasNext())
/* 324:    */     {
/* 325:369 */       LogLevel level = (LogLevel)it.next();
/* 326:370 */       JCheckBoxMenuItem item = (JCheckBoxMenuItem)logLevelMenuItems.get(level);
/* 327:371 */       exportLogLevelXMLElement(level.getLabel(), item.isSelected(), xml);
/* 328:    */     }
/* 329:374 */     xml.append("\t</loglevels>\r\n");
/* 330:    */   }
/* 331:    */   
/* 332:    */   private void processLogLevelColors(Map logLevelMenuItems, Map logLevelColors, StringBuffer xml)
/* 333:    */   {
/* 334:378 */     xml.append("\t<loglevelcolors>\r\n");
/* 335:    */     
/* 336:380 */     Iterator it = logLevelMenuItems.keySet().iterator();
/* 337:381 */     while (it.hasNext())
/* 338:    */     {
/* 339:382 */       LogLevel level = (LogLevel)it.next();
/* 340:    */       
/* 341:384 */       Color color = (Color)logLevelColors.get(level);
/* 342:385 */       exportLogLevelColorXMLElement(level.getLabel(), color, xml);
/* 343:    */     }
/* 344:388 */     xml.append("\t</loglevelcolors>\r\n");
/* 345:    */   }
/* 346:    */   
/* 347:    */   private void processLogTableColumns(List logTableColumnMenuItems, StringBuffer xml)
/* 348:    */   {
/* 349:393 */     xml.append("\t<logtablecolumns>\r\n");
/* 350:394 */     Iterator it = logTableColumnMenuItems.iterator();
/* 351:395 */     while (it.hasNext())
/* 352:    */     {
/* 353:396 */       LogTableColumn column = (LogTableColumn)it.next();
/* 354:397 */       JCheckBoxMenuItem item = this._monitor.getTableColumnMenuItem(column);
/* 355:398 */       exportLogTableColumnXMLElement(column.getLabel(), item.isSelected(), xml);
/* 356:    */     }
/* 357:401 */     xml.append("\t</logtablecolumns>\r\n");
/* 358:    */   }
/* 359:    */   
/* 360:    */   private void processLogRecordFilter(String text, StringBuffer xml)
/* 361:    */   {
/* 362:407 */     xml.append("\t<").append("searchtext").append(" ");
/* 363:408 */     xml.append("name").append("=\"").append(text).append("\"");
/* 364:409 */     xml.append("/>\r\n");
/* 365:    */   }
/* 366:    */   
/* 367:    */   private void openXMLDocument(StringBuffer xml)
/* 368:    */   {
/* 369:413 */     xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
/* 370:    */   }
/* 371:    */   
/* 372:    */   private void openConfigurationXML(StringBuffer xml)
/* 373:    */   {
/* 374:417 */     xml.append("<configuration>\r\n");
/* 375:    */   }
/* 376:    */   
/* 377:    */   private void closeConfigurationXML(StringBuffer xml)
/* 378:    */   {
/* 379:421 */     xml.append("</configuration>\r\n");
/* 380:    */   }
/* 381:    */   
/* 382:    */   private void exportXMLElement(CategoryNode node, TreePath path, StringBuffer xml)
/* 383:    */   {
/* 384:425 */     CategoryExplorerTree tree = this._monitor.getCategoryExplorerTree();
/* 385:    */     
/* 386:427 */     xml.append("\t<").append("category").append(" ");
/* 387:428 */     xml.append("name").append("=\"").append(node.getTitle()).append("\" ");
/* 388:429 */     xml.append("path").append("=\"").append(treePathToString(path)).append("\" ");
/* 389:430 */     xml.append("expanded").append("=\"").append(tree.isExpanded(path)).append("\" ");
/* 390:431 */     xml.append("selected").append("=\"").append(node.isSelected()).append("\"/>\r\n");
/* 391:    */   }
/* 392:    */   
/* 393:    */   private void exportLogLevelXMLElement(String label, boolean selected, StringBuffer xml)
/* 394:    */   {
/* 395:435 */     xml.append("\t\t<").append("level").append(" ").append("name");
/* 396:436 */     xml.append("=\"").append(label).append("\" ");
/* 397:437 */     xml.append("selected").append("=\"").append(selected);
/* 398:438 */     xml.append("\"/>\r\n");
/* 399:    */   }
/* 400:    */   
/* 401:    */   private void exportLogLevelColorXMLElement(String label, Color color, StringBuffer xml)
/* 402:    */   {
/* 403:442 */     xml.append("\t\t<").append("colorlevel").append(" ").append("name");
/* 404:443 */     xml.append("=\"").append(label).append("\" ");
/* 405:444 */     xml.append("red").append("=\"").append(color.getRed()).append("\" ");
/* 406:445 */     xml.append("green").append("=\"").append(color.getGreen()).append("\" ");
/* 407:446 */     xml.append("blue").append("=\"").append(color.getBlue());
/* 408:447 */     xml.append("\"/>\r\n");
/* 409:    */   }
/* 410:    */   
/* 411:    */   private void exportLogTableColumnXMLElement(String label, boolean selected, StringBuffer xml)
/* 412:    */   {
/* 413:451 */     xml.append("\t\t<").append("column").append(" ").append("name");
/* 414:452 */     xml.append("=\"").append(label).append("\" ");
/* 415:453 */     xml.append("selected").append("=\"").append(selected);
/* 416:454 */     xml.append("\"/>\r\n");
/* 417:    */   }
/* 418:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.lf5.viewer.configure.ConfigurationManager
 * JD-Core Version:    0.7.0.1
 */