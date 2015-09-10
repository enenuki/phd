/*    1:     */ package jxl.write.biff;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.net.MalformedURLException;
/*    5:     */ import java.net.URL;
/*    6:     */ import java.util.ArrayList;
/*    7:     */ import jxl.CellType;
/*    8:     */ import jxl.Hyperlink;
/*    9:     */ import jxl.Range;
/*   10:     */ import jxl.biff.CellReferenceHelper;
/*   11:     */ import jxl.biff.IntegerHelper;
/*   12:     */ import jxl.biff.SheetRangeImpl;
/*   13:     */ import jxl.biff.StringHelper;
/*   14:     */ import jxl.biff.Type;
/*   15:     */ import jxl.biff.WritableRecordData;
/*   16:     */ import jxl.common.Assert;
/*   17:     */ import jxl.common.Logger;
/*   18:     */ import jxl.read.biff.Record;
/*   19:     */ import jxl.write.Label;
/*   20:     */ import jxl.write.WritableCell;
/*   21:     */ import jxl.write.WritableSheet;
/*   22:     */ 
/*   23:     */ public class HyperlinkRecord
/*   24:     */   extends WritableRecordData
/*   25:     */ {
/*   26:  54 */   private static Logger logger = Logger.getLogger(HyperlinkRecord.class);
/*   27:     */   private int firstRow;
/*   28:     */   private int lastRow;
/*   29:     */   private int firstColumn;
/*   30:     */   private int lastColumn;
/*   31:     */   private URL url;
/*   32:     */   private File file;
/*   33:     */   private String location;
/*   34:     */   private String contents;
/*   35:     */   private LinkType linkType;
/*   36:     */   private byte[] data;
/*   37:     */   private Range range;
/*   38:     */   private WritableSheet sheet;
/*   39:     */   private boolean modified;
/*   40: 124 */   private static final LinkType urlLink = new LinkType(null);
/*   41: 125 */   private static final LinkType fileLink = new LinkType(null);
/*   42: 126 */   private static final LinkType uncLink = new LinkType(null);
/*   43: 127 */   private static final LinkType workbookLink = new LinkType(null);
/*   44: 128 */   private static final LinkType unknown = new LinkType(null);
/*   45:     */   
/*   46:     */   protected HyperlinkRecord(Hyperlink h, WritableSheet s)
/*   47:     */   {
/*   48: 137 */     super(Type.HLINK);
/*   49: 139 */     if ((h instanceof jxl.read.biff.HyperlinkRecord)) {
/*   50: 141 */       copyReadHyperlink(h, s);
/*   51:     */     } else {
/*   52: 145 */       copyWritableHyperlink(h, s);
/*   53:     */     }
/*   54:     */   }
/*   55:     */   
/*   56:     */   private void copyReadHyperlink(Hyperlink h, WritableSheet s)
/*   57:     */   {
/*   58: 154 */     jxl.read.biff.HyperlinkRecord hl = (jxl.read.biff.HyperlinkRecord)h;
/*   59:     */     
/*   60: 156 */     this.data = hl.getRecord().getData();
/*   61: 157 */     this.sheet = s;
/*   62:     */     
/*   63:     */ 
/*   64: 160 */     this.firstRow = hl.getRow();
/*   65: 161 */     this.firstColumn = hl.getColumn();
/*   66: 162 */     this.lastRow = hl.getLastRow();
/*   67: 163 */     this.lastColumn = hl.getLastColumn();
/*   68: 164 */     this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*   69:     */     
/*   70:     */ 
/*   71:     */ 
/*   72: 168 */     this.linkType = unknown;
/*   73: 170 */     if (hl.isFile())
/*   74:     */     {
/*   75: 172 */       this.linkType = fileLink;
/*   76: 173 */       this.file = hl.getFile();
/*   77:     */     }
/*   78: 175 */     else if (hl.isURL())
/*   79:     */     {
/*   80: 177 */       this.linkType = urlLink;
/*   81: 178 */       this.url = hl.getURL();
/*   82:     */     }
/*   83: 180 */     else if (hl.isLocation())
/*   84:     */     {
/*   85: 182 */       this.linkType = workbookLink;
/*   86: 183 */       this.location = hl.getLocation();
/*   87:     */     }
/*   88: 186 */     this.modified = false;
/*   89:     */   }
/*   90:     */   
/*   91:     */   private void copyWritableHyperlink(Hyperlink hl, WritableSheet s)
/*   92:     */   {
/*   93: 197 */     HyperlinkRecord h = (HyperlinkRecord)hl;
/*   94:     */     
/*   95: 199 */     this.firstRow = h.firstRow;
/*   96: 200 */     this.lastRow = h.lastRow;
/*   97: 201 */     this.firstColumn = h.firstColumn;
/*   98: 202 */     this.lastColumn = h.lastColumn;
/*   99: 204 */     if (h.url != null) {
/*  100:     */       try
/*  101:     */       {
/*  102: 208 */         this.url = new URL(h.url.toString());
/*  103:     */       }
/*  104:     */       catch (MalformedURLException e)
/*  105:     */       {
/*  106: 213 */         Assert.verify(false);
/*  107:     */       }
/*  108:     */     }
/*  109: 217 */     if (h.file != null) {
/*  110: 219 */       this.file = new File(h.file.getPath());
/*  111:     */     }
/*  112: 222 */     this.location = h.location;
/*  113: 223 */     this.contents = h.contents;
/*  114: 224 */     this.linkType = h.linkType;
/*  115: 225 */     this.modified = true;
/*  116:     */     
/*  117: 227 */     this.sheet = s;
/*  118: 228 */     this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*  119:     */   }
/*  120:     */   
/*  121:     */   protected HyperlinkRecord(int col, int row, int lastcol, int lastrow, URL url, String desc)
/*  122:     */   {
/*  123: 248 */     super(Type.HLINK);
/*  124:     */     
/*  125: 250 */     this.firstColumn = col;
/*  126: 251 */     this.firstRow = row;
/*  127:     */     
/*  128: 253 */     this.lastColumn = Math.max(this.firstColumn, lastcol);
/*  129: 254 */     this.lastRow = Math.max(this.firstRow, lastrow);
/*  130:     */     
/*  131: 256 */     this.url = url;
/*  132: 257 */     this.contents = desc;
/*  133:     */     
/*  134: 259 */     this.linkType = urlLink;
/*  135:     */     
/*  136: 261 */     this.modified = true;
/*  137:     */   }
/*  138:     */   
/*  139:     */   protected HyperlinkRecord(int col, int row, int lastcol, int lastrow, File file, String desc)
/*  140:     */   {
/*  141: 277 */     super(Type.HLINK);
/*  142:     */     
/*  143: 279 */     this.firstColumn = col;
/*  144: 280 */     this.firstRow = row;
/*  145:     */     
/*  146: 282 */     this.lastColumn = Math.max(this.firstColumn, lastcol);
/*  147: 283 */     this.lastRow = Math.max(this.firstRow, lastrow);
/*  148: 284 */     this.contents = desc;
/*  149:     */     
/*  150: 286 */     this.file = file;
/*  151: 288 */     if (file.getPath().startsWith("\\\\")) {
/*  152: 290 */       this.linkType = uncLink;
/*  153:     */     } else {
/*  154: 294 */       this.linkType = fileLink;
/*  155:     */     }
/*  156: 297 */     this.modified = true;
/*  157:     */   }
/*  158:     */   
/*  159:     */   protected HyperlinkRecord(int col, int row, int lastcol, int lastrow, String desc, WritableSheet s, int destcol, int destrow, int lastdestcol, int lastdestrow)
/*  160:     */   {
/*  161: 321 */     super(Type.HLINK);
/*  162:     */     
/*  163: 323 */     this.firstColumn = col;
/*  164: 324 */     this.firstRow = row;
/*  165:     */     
/*  166: 326 */     this.lastColumn = Math.max(this.firstColumn, lastcol);
/*  167: 327 */     this.lastRow = Math.max(this.firstRow, lastrow);
/*  168:     */     
/*  169: 329 */     setLocation(s, destcol, destrow, lastdestcol, lastdestrow);
/*  170: 330 */     this.contents = desc;
/*  171:     */     
/*  172: 332 */     this.linkType = workbookLink;
/*  173:     */     
/*  174: 334 */     this.modified = true;
/*  175:     */   }
/*  176:     */   
/*  177:     */   public boolean isFile()
/*  178:     */   {
/*  179: 344 */     return this.linkType == fileLink;
/*  180:     */   }
/*  181:     */   
/*  182:     */   public boolean isUNC()
/*  183:     */   {
/*  184: 354 */     return this.linkType == uncLink;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public boolean isURL()
/*  188:     */   {
/*  189: 364 */     return this.linkType == urlLink;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public boolean isLocation()
/*  193:     */   {
/*  194: 374 */     return this.linkType == workbookLink;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public int getRow()
/*  198:     */   {
/*  199: 384 */     return this.firstRow;
/*  200:     */   }
/*  201:     */   
/*  202:     */   public int getColumn()
/*  203:     */   {
/*  204: 394 */     return this.firstColumn;
/*  205:     */   }
/*  206:     */   
/*  207:     */   public int getLastRow()
/*  208:     */   {
/*  209: 404 */     return this.lastRow;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public int getLastColumn()
/*  213:     */   {
/*  214: 414 */     return this.lastColumn;
/*  215:     */   }
/*  216:     */   
/*  217:     */   public URL getURL()
/*  218:     */   {
/*  219: 424 */     return this.url;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public File getFile()
/*  223:     */   {
/*  224: 434 */     return this.file;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public byte[] getData()
/*  228:     */   {
/*  229: 444 */     if (!this.modified) {
/*  230: 446 */       return this.data;
/*  231:     */     }
/*  232: 450 */     byte[] commonData = new byte[32];
/*  233:     */     
/*  234:     */ 
/*  235: 453 */     IntegerHelper.getTwoBytes(this.firstRow, commonData, 0);
/*  236: 454 */     IntegerHelper.getTwoBytes(this.lastRow, commonData, 2);
/*  237: 455 */     IntegerHelper.getTwoBytes(this.firstColumn, commonData, 4);
/*  238: 456 */     IntegerHelper.getTwoBytes(this.lastColumn, commonData, 6);
/*  239:     */     
/*  240:     */ 
/*  241: 459 */     commonData[8] = -48;
/*  242: 460 */     commonData[9] = -55;
/*  243: 461 */     commonData[10] = -22;
/*  244: 462 */     commonData[11] = 121;
/*  245: 463 */     commonData[12] = -7;
/*  246: 464 */     commonData[13] = -70;
/*  247: 465 */     commonData[14] = -50;
/*  248: 466 */     commonData[15] = 17;
/*  249: 467 */     commonData[16] = -116;
/*  250: 468 */     commonData[17] = -126;
/*  251: 469 */     commonData[18] = 0;
/*  252: 470 */     commonData[19] = -86;
/*  253: 471 */     commonData[20] = 0;
/*  254: 472 */     commonData[21] = 75;
/*  255: 473 */     commonData[22] = -87;
/*  256: 474 */     commonData[23] = 11;
/*  257: 475 */     commonData[24] = 2;
/*  258: 476 */     commonData[25] = 0;
/*  259: 477 */     commonData[26] = 0;
/*  260: 478 */     commonData[27] = 0;
/*  261:     */     
/*  262:     */ 
/*  263:     */ 
/*  264: 482 */     int optionFlags = 0;
/*  265: 483 */     if (isURL())
/*  266:     */     {
/*  267: 485 */       optionFlags = 3;
/*  268: 487 */       if (this.contents != null) {
/*  269: 489 */         optionFlags |= 0x14;
/*  270:     */       }
/*  271:     */     }
/*  272: 492 */     else if (isFile())
/*  273:     */     {
/*  274: 494 */       optionFlags = 1;
/*  275: 496 */       if (this.contents != null) {
/*  276: 498 */         optionFlags |= 0x14;
/*  277:     */       }
/*  278:     */     }
/*  279: 501 */     else if (isLocation())
/*  280:     */     {
/*  281: 503 */       optionFlags = 8;
/*  282:     */     }
/*  283: 505 */     else if (isUNC())
/*  284:     */     {
/*  285: 507 */       optionFlags = 259;
/*  286:     */     }
/*  287: 510 */     IntegerHelper.getFourBytes(optionFlags, commonData, 28);
/*  288: 512 */     if (isURL()) {
/*  289: 514 */       this.data = getURLData(commonData);
/*  290: 516 */     } else if (isFile()) {
/*  291: 518 */       this.data = getFileData(commonData);
/*  292: 520 */     } else if (isLocation()) {
/*  293: 522 */       this.data = getLocationData(commonData);
/*  294: 524 */     } else if (isUNC()) {
/*  295: 526 */       this.data = getUNCData(commonData);
/*  296:     */     }
/*  297: 529 */     return this.data;
/*  298:     */   }
/*  299:     */   
/*  300:     */   public String toString()
/*  301:     */   {
/*  302: 539 */     if (isFile()) {
/*  303: 541 */       return this.file.toString();
/*  304:     */     }
/*  305: 543 */     if (isURL()) {
/*  306: 545 */       return this.url.toString();
/*  307:     */     }
/*  308: 547 */     if (isUNC()) {
/*  309: 549 */       return this.file.toString();
/*  310:     */     }
/*  311: 553 */     return "";
/*  312:     */   }
/*  313:     */   
/*  314:     */   public Range getRange()
/*  315:     */   {
/*  316: 567 */     return this.range;
/*  317:     */   }
/*  318:     */   
/*  319:     */   public void setURL(URL url)
/*  320:     */   {
/*  321: 577 */     URL prevurl = this.url;
/*  322: 578 */     this.linkType = urlLink;
/*  323: 579 */     this.file = null;
/*  324: 580 */     this.location = null;
/*  325: 581 */     this.contents = null;
/*  326: 582 */     this.url = url;
/*  327: 583 */     this.modified = true;
/*  328: 585 */     if (this.sheet == null) {
/*  329: 588 */       return;
/*  330:     */     }
/*  331: 593 */     WritableCell wc = this.sheet.getWritableCell(this.firstColumn, this.firstRow);
/*  332: 595 */     if (wc.getType() == CellType.LABEL)
/*  333:     */     {
/*  334: 597 */       Label l = (Label)wc;
/*  335: 598 */       String prevurlString = prevurl.toString();
/*  336: 599 */       String prevurlString2 = "";
/*  337: 600 */       if ((prevurlString.charAt(prevurlString.length() - 1) == '/') || (prevurlString.charAt(prevurlString.length() - 1) == '\\')) {
/*  338: 603 */         prevurlString2 = prevurlString.substring(0, prevurlString.length() - 1);
/*  339:     */       }
/*  340: 607 */       if ((l.getString().equals(prevurlString)) || (l.getString().equals(prevurlString2))) {
/*  341: 610 */         l.setString(url.toString());
/*  342:     */       }
/*  343:     */     }
/*  344:     */   }
/*  345:     */   
/*  346:     */   public void setFile(File file)
/*  347:     */   {
/*  348: 622 */     this.linkType = fileLink;
/*  349: 623 */     this.url = null;
/*  350: 624 */     this.location = null;
/*  351: 625 */     this.contents = null;
/*  352: 626 */     this.file = file;
/*  353: 627 */     this.modified = true;
/*  354: 629 */     if (this.sheet == null) {
/*  355: 632 */       return;
/*  356:     */     }
/*  357: 636 */     WritableCell wc = this.sheet.getWritableCell(this.firstColumn, this.firstRow);
/*  358:     */     
/*  359: 638 */     Assert.verify(wc.getType() == CellType.LABEL);
/*  360:     */     
/*  361: 640 */     Label l = (Label)wc;
/*  362: 641 */     l.setString(file.toString());
/*  363:     */   }
/*  364:     */   
/*  365:     */   protected void setLocation(String desc, WritableSheet sheet, int destcol, int destrow, int lastdestcol, int lastdestrow)
/*  366:     */   {
/*  367: 659 */     this.linkType = workbookLink;
/*  368: 660 */     this.url = null;
/*  369: 661 */     this.file = null;
/*  370: 662 */     this.modified = true;
/*  371: 663 */     this.contents = desc;
/*  372:     */     
/*  373: 665 */     setLocation(sheet, destcol, destrow, lastdestcol, lastdestrow);
/*  374: 667 */     if (sheet == null) {
/*  375: 670 */       return;
/*  376:     */     }
/*  377: 674 */     WritableCell wc = sheet.getWritableCell(this.firstColumn, this.firstRow);
/*  378:     */     
/*  379: 676 */     Assert.verify(wc.getType() == CellType.LABEL);
/*  380:     */     
/*  381: 678 */     Label l = (Label)wc;
/*  382: 679 */     l.setString(desc);
/*  383:     */   }
/*  384:     */   
/*  385:     */   private void setLocation(WritableSheet sheet, int destcol, int destrow, int lastdestcol, int lastdestrow)
/*  386:     */   {
/*  387: 695 */     StringBuffer sb = new StringBuffer();
/*  388: 696 */     sb.append('\'');
/*  389: 698 */     if (sheet.getName().indexOf('\'') == -1)
/*  390:     */     {
/*  391: 700 */       sb.append(sheet.getName());
/*  392:     */     }
/*  393:     */     else
/*  394:     */     {
/*  395: 708 */       String sheetName = sheet.getName();
/*  396: 709 */       int pos = 0;
/*  397: 710 */       int nextPos = sheetName.indexOf('\'', pos);
/*  398: 712 */       while ((nextPos != -1) && (pos < sheetName.length()))
/*  399:     */       {
/*  400: 714 */         sb.append(sheetName.substring(pos, nextPos));
/*  401: 715 */         sb.append("''");
/*  402: 716 */         pos = nextPos + 1;
/*  403: 717 */         nextPos = sheetName.indexOf('\'', pos);
/*  404:     */       }
/*  405: 719 */       sb.append(sheetName.substring(pos));
/*  406:     */     }
/*  407: 722 */     sb.append('\'');
/*  408: 723 */     sb.append('!');
/*  409:     */     
/*  410: 725 */     lastdestcol = Math.max(destcol, lastdestcol);
/*  411: 726 */     lastdestrow = Math.max(destrow, lastdestrow);
/*  412:     */     
/*  413: 728 */     CellReferenceHelper.getCellReference(destcol, destrow, sb);
/*  414: 729 */     sb.append(':');
/*  415: 730 */     CellReferenceHelper.getCellReference(lastdestcol, lastdestrow, sb);
/*  416:     */     
/*  417: 732 */     this.location = sb.toString();
/*  418:     */   }
/*  419:     */   
/*  420:     */   void insertRow(int r)
/*  421:     */   {
/*  422: 744 */     Assert.verify((this.sheet != null) && (this.range != null));
/*  423: 746 */     if (r > this.lastRow) {
/*  424: 748 */       return;
/*  425:     */     }
/*  426: 751 */     if (r <= this.firstRow)
/*  427:     */     {
/*  428: 753 */       this.firstRow += 1;
/*  429: 754 */       this.modified = true;
/*  430:     */     }
/*  431: 757 */     if (r <= this.lastRow)
/*  432:     */     {
/*  433: 759 */       this.lastRow += 1;
/*  434: 760 */       this.modified = true;
/*  435:     */     }
/*  436: 763 */     if (this.modified) {
/*  437: 765 */       this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*  438:     */     }
/*  439:     */   }
/*  440:     */   
/*  441:     */   void insertColumn(int c)
/*  442:     */   {
/*  443: 780 */     Assert.verify((this.sheet != null) && (this.range != null));
/*  444: 782 */     if (c > this.lastColumn) {
/*  445: 784 */       return;
/*  446:     */     }
/*  447: 787 */     if (c <= this.firstColumn)
/*  448:     */     {
/*  449: 789 */       this.firstColumn += 1;
/*  450: 790 */       this.modified = true;
/*  451:     */     }
/*  452: 793 */     if (c <= this.lastColumn)
/*  453:     */     {
/*  454: 795 */       this.lastColumn += 1;
/*  455: 796 */       this.modified = true;
/*  456:     */     }
/*  457: 799 */     if (this.modified) {
/*  458: 801 */       this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*  459:     */     }
/*  460:     */   }
/*  461:     */   
/*  462:     */   void removeRow(int r)
/*  463:     */   {
/*  464: 816 */     Assert.verify((this.sheet != null) && (this.range != null));
/*  465: 818 */     if (r > this.lastRow) {
/*  466: 820 */       return;
/*  467:     */     }
/*  468: 823 */     if (r < this.firstRow)
/*  469:     */     {
/*  470: 825 */       this.firstRow -= 1;
/*  471: 826 */       this.modified = true;
/*  472:     */     }
/*  473: 829 */     if (r < this.lastRow)
/*  474:     */     {
/*  475: 831 */       this.lastRow -= 1;
/*  476: 832 */       this.modified = true;
/*  477:     */     }
/*  478: 835 */     if (this.modified)
/*  479:     */     {
/*  480: 837 */       Assert.verify(this.range != null);
/*  481: 838 */       this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*  482:     */     }
/*  483:     */   }
/*  484:     */   
/*  485:     */   void removeColumn(int c)
/*  486:     */   {
/*  487: 853 */     Assert.verify((this.sheet != null) && (this.range != null));
/*  488: 855 */     if (c > this.lastColumn) {
/*  489: 857 */       return;
/*  490:     */     }
/*  491: 860 */     if (c < this.firstColumn)
/*  492:     */     {
/*  493: 862 */       this.firstColumn -= 1;
/*  494: 863 */       this.modified = true;
/*  495:     */     }
/*  496: 866 */     if (c < this.lastColumn)
/*  497:     */     {
/*  498: 868 */       this.lastColumn -= 1;
/*  499: 869 */       this.modified = true;
/*  500:     */     }
/*  501: 872 */     if (this.modified)
/*  502:     */     {
/*  503: 874 */       Assert.verify(this.range != null);
/*  504: 875 */       this.range = new SheetRangeImpl(this.sheet, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*  505:     */     }
/*  506:     */   }
/*  507:     */   
/*  508:     */   private byte[] getURLData(byte[] cd)
/*  509:     */   {
/*  510: 889 */     String urlString = this.url.toString();
/*  511:     */     
/*  512: 891 */     int dataLength = cd.length + 20 + (urlString.length() + 1) * 2;
/*  513: 893 */     if (this.contents != null) {
/*  514: 895 */       dataLength += 4 + (this.contents.length() + 1) * 2;
/*  515:     */     }
/*  516: 898 */     byte[] d = new byte[dataLength];
/*  517:     */     
/*  518: 900 */     System.arraycopy(cd, 0, d, 0, cd.length);
/*  519:     */     
/*  520: 902 */     int urlPos = cd.length;
/*  521: 904 */     if (this.contents != null)
/*  522:     */     {
/*  523: 906 */       IntegerHelper.getFourBytes(this.contents.length() + 1, d, urlPos);
/*  524: 907 */       StringHelper.getUnicodeBytes(this.contents, d, urlPos + 4);
/*  525: 908 */       urlPos += (this.contents.length() + 1) * 2 + 4;
/*  526:     */     }
/*  527: 912 */     d[urlPos] = -32;
/*  528: 913 */     d[(urlPos + 1)] = -55;
/*  529: 914 */     d[(urlPos + 2)] = -22;
/*  530: 915 */     d[(urlPos + 3)] = 121;
/*  531: 916 */     d[(urlPos + 4)] = -7;
/*  532: 917 */     d[(urlPos + 5)] = -70;
/*  533: 918 */     d[(urlPos + 6)] = -50;
/*  534: 919 */     d[(urlPos + 7)] = 17;
/*  535: 920 */     d[(urlPos + 8)] = -116;
/*  536: 921 */     d[(urlPos + 9)] = -126;
/*  537: 922 */     d[(urlPos + 10)] = 0;
/*  538: 923 */     d[(urlPos + 11)] = -86;
/*  539: 924 */     d[(urlPos + 12)] = 0;
/*  540: 925 */     d[(urlPos + 13)] = 75;
/*  541: 926 */     d[(urlPos + 14)] = -87;
/*  542: 927 */     d[(urlPos + 15)] = 11;
/*  543:     */     
/*  544:     */ 
/*  545: 930 */     IntegerHelper.getFourBytes((urlString.length() + 1) * 2, d, urlPos + 16);
/*  546:     */     
/*  547:     */ 
/*  548: 933 */     StringHelper.getUnicodeBytes(urlString, d, urlPos + 20);
/*  549:     */     
/*  550: 935 */     return d;
/*  551:     */   }
/*  552:     */   
/*  553:     */   private byte[] getUNCData(byte[] cd)
/*  554:     */   {
/*  555: 946 */     String uncString = this.file.getPath();
/*  556:     */     
/*  557: 948 */     byte[] d = new byte[cd.length + uncString.length() * 2 + 2 + 4];
/*  558: 949 */     System.arraycopy(cd, 0, d, 0, cd.length);
/*  559:     */     
/*  560: 951 */     int urlPos = cd.length;
/*  561:     */     
/*  562:     */ 
/*  563: 954 */     int length = uncString.length() + 1;
/*  564: 955 */     IntegerHelper.getFourBytes(length, d, urlPos);
/*  565:     */     
/*  566:     */ 
/*  567: 958 */     StringHelper.getUnicodeBytes(uncString, d, urlPos + 4);
/*  568:     */     
/*  569: 960 */     return d;
/*  570:     */   }
/*  571:     */   
/*  572:     */   private byte[] getFileData(byte[] cd)
/*  573:     */   {
/*  574: 972 */     ArrayList path = new ArrayList();
/*  575: 973 */     ArrayList shortFileName = new ArrayList();
/*  576: 974 */     path.add(this.file.getName());
/*  577: 975 */     shortFileName.add(getShortName(this.file.getName()));
/*  578:     */     
/*  579: 977 */     File parent = this.file.getParentFile();
/*  580: 978 */     while (parent != null)
/*  581:     */     {
/*  582: 980 */       path.add(parent.getName());
/*  583: 981 */       shortFileName.add(getShortName(parent.getName()));
/*  584: 982 */       parent = parent.getParentFile();
/*  585:     */     }
/*  586: 987 */     int upLevelCount = 0;
/*  587: 988 */     int pos = path.size() - 1;
/*  588: 989 */     boolean upDir = true;
/*  589: 991 */     while (upDir)
/*  590:     */     {
/*  591: 993 */       String s = (String)path.get(pos);
/*  592: 994 */       if (s.equals(".."))
/*  593:     */       {
/*  594: 996 */         upLevelCount++;
/*  595: 997 */         path.remove(pos);
/*  596: 998 */         shortFileName.remove(pos);
/*  597:     */       }
/*  598:     */       else
/*  599:     */       {
/*  600:1002 */         upDir = false;
/*  601:     */       }
/*  602:1005 */       pos--;
/*  603:     */     }
/*  604:1008 */     StringBuffer filePathSB = new StringBuffer();
/*  605:1009 */     StringBuffer shortFilePathSB = new StringBuffer();
/*  606:1011 */     if (this.file.getPath().charAt(1) == ':')
/*  607:     */     {
/*  608:1013 */       char driveLetter = this.file.getPath().charAt(0);
/*  609:1014 */       if ((driveLetter != 'C') && (driveLetter != 'c'))
/*  610:     */       {
/*  611:1016 */         filePathSB.append(driveLetter);
/*  612:1017 */         filePathSB.append(':');
/*  613:1018 */         shortFilePathSB.append(driveLetter);
/*  614:1019 */         shortFilePathSB.append(':');
/*  615:     */       }
/*  616:     */     }
/*  617:1023 */     for (int i = path.size() - 1; i >= 0; i--)
/*  618:     */     {
/*  619:1025 */       filePathSB.append((String)path.get(i));
/*  620:1026 */       shortFilePathSB.append((String)shortFileName.get(i));
/*  621:1028 */       if (i != 0)
/*  622:     */       {
/*  623:1030 */         filePathSB.append("\\");
/*  624:1031 */         shortFilePathSB.append("\\");
/*  625:     */       }
/*  626:     */     }
/*  627:1036 */     String filePath = filePathSB.toString();
/*  628:1037 */     String shortFilePath = shortFilePathSB.toString();
/*  629:     */     
/*  630:1039 */     int dataLength = cd.length + 4 + (shortFilePath.length() + 1) + 16 + 2 + 8 + (filePath.length() + 1) * 2 + 24;
/*  631:1047 */     if (this.contents != null) {
/*  632:1049 */       dataLength += 4 + (this.contents.length() + 1) * 2;
/*  633:     */     }
/*  634:1053 */     byte[] d = new byte[dataLength];
/*  635:     */     
/*  636:1055 */     System.arraycopy(cd, 0, d, 0, cd.length);
/*  637:     */     
/*  638:1057 */     int filePos = cd.length;
/*  639:1060 */     if (this.contents != null)
/*  640:     */     {
/*  641:1062 */       IntegerHelper.getFourBytes(this.contents.length() + 1, d, filePos);
/*  642:1063 */       StringHelper.getUnicodeBytes(this.contents, d, filePos + 4);
/*  643:1064 */       filePos += (this.contents.length() + 1) * 2 + 4;
/*  644:     */     }
/*  645:1067 */     int curPos = filePos;
/*  646:     */     
/*  647:     */ 
/*  648:1070 */     d[curPos] = 3;
/*  649:1071 */     d[(curPos + 1)] = 3;
/*  650:1072 */     d[(curPos + 2)] = 0;
/*  651:1073 */     d[(curPos + 3)] = 0;
/*  652:1074 */     d[(curPos + 4)] = 0;
/*  653:1075 */     d[(curPos + 5)] = 0;
/*  654:1076 */     d[(curPos + 6)] = 0;
/*  655:1077 */     d[(curPos + 7)] = 0;
/*  656:1078 */     d[(curPos + 8)] = -64;
/*  657:1079 */     d[(curPos + 9)] = 0;
/*  658:1080 */     d[(curPos + 10)] = 0;
/*  659:1081 */     d[(curPos + 11)] = 0;
/*  660:1082 */     d[(curPos + 12)] = 0;
/*  661:1083 */     d[(curPos + 13)] = 0;
/*  662:1084 */     d[(curPos + 14)] = 0;
/*  663:1085 */     d[(curPos + 15)] = 70;
/*  664:     */     
/*  665:1087 */     curPos += 16;
/*  666:     */     
/*  667:     */ 
/*  668:1090 */     IntegerHelper.getTwoBytes(upLevelCount, d, curPos);
/*  669:1091 */     curPos += 2;
/*  670:     */     
/*  671:     */ 
/*  672:1094 */     IntegerHelper.getFourBytes(shortFilePath.length() + 1, d, curPos);
/*  673:     */     
/*  674:     */ 
/*  675:1097 */     StringHelper.getBytes(shortFilePath, d, curPos + 4);
/*  676:     */     
/*  677:1099 */     curPos += 4 + (shortFilePath.length() + 1);
/*  678:     */     
/*  679:     */ 
/*  680:1102 */     d[curPos] = -1;
/*  681:1103 */     d[(curPos + 1)] = -1;
/*  682:1104 */     d[(curPos + 2)] = -83;
/*  683:1105 */     d[(curPos + 3)] = -34;
/*  684:1106 */     d[(curPos + 4)] = 0;
/*  685:1107 */     d[(curPos + 5)] = 0;
/*  686:1108 */     d[(curPos + 6)] = 0;
/*  687:1109 */     d[(curPos + 7)] = 0;
/*  688:1110 */     d[(curPos + 8)] = 0;
/*  689:1111 */     d[(curPos + 9)] = 0;
/*  690:1112 */     d[(curPos + 10)] = 0;
/*  691:1113 */     d[(curPos + 11)] = 0;
/*  692:1114 */     d[(curPos + 12)] = 0;
/*  693:1115 */     d[(curPos + 13)] = 0;
/*  694:1116 */     d[(curPos + 14)] = 0;
/*  695:1117 */     d[(curPos + 15)] = 0;
/*  696:1118 */     d[(curPos + 16)] = 0;
/*  697:1119 */     d[(curPos + 17)] = 0;
/*  698:1120 */     d[(curPos + 18)] = 0;
/*  699:1121 */     d[(curPos + 19)] = 0;
/*  700:1122 */     d[(curPos + 20)] = 0;
/*  701:1123 */     d[(curPos + 21)] = 0;
/*  702:1124 */     d[(curPos + 22)] = 0;
/*  703:1125 */     d[(curPos + 23)] = 0;
/*  704:     */     
/*  705:1127 */     curPos += 24;
/*  706:     */     
/*  707:     */ 
/*  708:     */ 
/*  709:1131 */     int size = 6 + filePath.length() * 2;
/*  710:1132 */     IntegerHelper.getFourBytes(size, d, curPos);
/*  711:1133 */     curPos += 4;
/*  712:     */     
/*  713:     */ 
/*  714:     */ 
/*  715:1137 */     IntegerHelper.getFourBytes(filePath.length() * 2, d, curPos);
/*  716:1138 */     curPos += 4;
/*  717:     */     
/*  718:     */ 
/*  719:1141 */     d[curPos] = 3;
/*  720:1142 */     d[(curPos + 1)] = 0;
/*  721:     */     
/*  722:1144 */     curPos += 2;
/*  723:     */     
/*  724:     */ 
/*  725:1147 */     StringHelper.getUnicodeBytes(filePath, d, curPos);
/*  726:1148 */     curPos += (filePath.length() + 1) * 2;
/*  727:     */     
/*  728:     */ 
/*  729:     */ 
/*  730:     */ 
/*  731:     */ 
/*  732:     */ 
/*  733:     */ 
/*  734:     */ 
/*  735:     */ 
/*  736:     */ 
/*  737:     */ 
/*  738:     */ 
/*  739:     */ 
/*  740:     */ 
/*  741:     */ 
/*  742:     */ 
/*  743:     */ 
/*  744:     */ 
/*  745:     */ 
/*  746:1168 */     return d;
/*  747:     */   }
/*  748:     */   
/*  749:     */   private String getShortName(String s)
/*  750:     */   {
/*  751:1179 */     int sep = s.indexOf('.');
/*  752:     */     
/*  753:1181 */     String prefix = null;
/*  754:1182 */     String suffix = null;
/*  755:1184 */     if (sep == -1)
/*  756:     */     {
/*  757:1186 */       prefix = s;
/*  758:1187 */       suffix = "";
/*  759:     */     }
/*  760:     */     else
/*  761:     */     {
/*  762:1191 */       prefix = s.substring(0, sep);
/*  763:1192 */       suffix = s.substring(sep + 1);
/*  764:     */     }
/*  765:1195 */     if (prefix.length() > 8)
/*  766:     */     {
/*  767:1197 */       prefix = prefix.substring(0, 6) + "~" + (prefix.length() - 8);
/*  768:1198 */       prefix = prefix.substring(0, 8);
/*  769:     */     }
/*  770:1201 */     suffix = suffix.substring(0, Math.min(3, suffix.length()));
/*  771:1203 */     if (suffix.length() > 0) {
/*  772:1205 */       return prefix + '.' + suffix;
/*  773:     */     }
/*  774:1209 */     return prefix;
/*  775:     */   }
/*  776:     */   
/*  777:     */   private byte[] getLocationData(byte[] cd)
/*  778:     */   {
/*  779:1221 */     byte[] d = new byte[cd.length + 4 + (this.location.length() + 1) * 2];
/*  780:1222 */     System.arraycopy(cd, 0, d, 0, cd.length);
/*  781:     */     
/*  782:1224 */     int locPos = cd.length;
/*  783:     */     
/*  784:     */ 
/*  785:1227 */     IntegerHelper.getFourBytes(this.location.length() + 1, d, locPos);
/*  786:     */     
/*  787:     */ 
/*  788:1230 */     StringHelper.getUnicodeBytes(this.location, d, locPos + 4);
/*  789:     */     
/*  790:1232 */     return d;
/*  791:     */   }
/*  792:     */   
/*  793:     */   void initialize(WritableSheet s)
/*  794:     */   {
/*  795:1243 */     this.sheet = s;
/*  796:1244 */     this.range = new SheetRangeImpl(s, this.firstColumn, this.firstRow, this.lastColumn, this.lastRow);
/*  797:     */   }
/*  798:     */   
/*  799:     */   String getContents()
/*  800:     */   {
/*  801:1257 */     return this.contents;
/*  802:     */   }
/*  803:     */   
/*  804:     */   protected void setContents(String desc)
/*  805:     */   {
/*  806:1267 */     this.contents = desc;
/*  807:1268 */     this.modified = true;
/*  808:     */   }
/*  809:     */   
/*  810:     */   private static class LinkType {}
/*  811:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.write.biff.HyperlinkRecord
 * JD-Core Version:    0.7.0.1
 */