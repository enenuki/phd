/*    1:     */ package jxl.biff.drawing;
/*    2:     */ 
/*    3:     */ import java.io.FileInputStream;
/*    4:     */ import java.io.IOException;
/*    5:     */ import jxl.CellView;
/*    6:     */ import jxl.Image;
/*    7:     */ import jxl.Sheet;
/*    8:     */ import jxl.common.Assert;
/*    9:     */ import jxl.common.LengthConverter;
/*   10:     */ import jxl.common.LengthUnit;
/*   11:     */ import jxl.common.Logger;
/*   12:     */ import jxl.format.CellFormat;
/*   13:     */ import jxl.format.Font;
/*   14:     */ 
/*   15:     */ public class Drawing
/*   16:     */   implements DrawingGroupObject, Image
/*   17:     */ {
/*   18:  45 */   private static Logger logger = Logger.getLogger(Drawing.class);
/*   19:     */   private EscherContainer readSpContainer;
/*   20:     */   private MsoDrawingRecord msoDrawingRecord;
/*   21:     */   private ObjRecord objRecord;
/*   22:  65 */   private boolean initialized = false;
/*   23:     */   private java.io.File imageFile;
/*   24:     */   private byte[] imageData;
/*   25:     */   private int objectId;
/*   26:     */   private int blipId;
/*   27:     */   private double x;
/*   28:     */   private double y;
/*   29:     */   private double width;
/*   30:     */   private double height;
/*   31:     */   private int referenceCount;
/*   32:     */   private EscherContainer escherData;
/*   33:     */   private Origin origin;
/*   34:     */   private DrawingGroup drawingGroup;
/*   35:     */   private DrawingData drawingData;
/*   36:     */   private ShapeType type;
/*   37:     */   private int shapeId;
/*   38:     */   private int drawingNumber;
/*   39:     */   private Sheet sheet;
/*   40:     */   private PNGReader pngReader;
/*   41:     */   private ImageAnchorProperties imageAnchorProperties;
/*   42:     */   
/*   43:     */   protected static class ImageAnchorProperties
/*   44:     */   {
/*   45:     */     private int value;
/*   46: 167 */     private static ImageAnchorProperties[] o = new ImageAnchorProperties[0];
/*   47:     */     
/*   48:     */     ImageAnchorProperties(int v)
/*   49:     */     {
/*   50: 171 */       this.value = v;
/*   51:     */       
/*   52: 173 */       ImageAnchorProperties[] oldArray = o;
/*   53: 174 */       o = new ImageAnchorProperties[oldArray.length + 1];
/*   54: 175 */       System.arraycopy(oldArray, 0, o, 0, oldArray.length);
/*   55: 176 */       o[oldArray.length] = this;
/*   56:     */     }
/*   57:     */     
/*   58:     */     int getValue()
/*   59:     */     {
/*   60: 181 */       return this.value;
/*   61:     */     }
/*   62:     */     
/*   63:     */     static ImageAnchorProperties getImageAnchorProperties(int val)
/*   64:     */     {
/*   65: 186 */       ImageAnchorProperties iap = Drawing.MOVE_AND_SIZE_WITH_CELLS;
/*   66: 187 */       int pos = 0;
/*   67: 188 */       while (pos < o.length)
/*   68:     */       {
/*   69: 190 */         if (o[pos].getValue() == val)
/*   70:     */         {
/*   71: 192 */           iap = o[pos];
/*   72: 193 */           break;
/*   73:     */         }
/*   74: 197 */         pos++;
/*   75:     */       }
/*   76: 200 */       return iap;
/*   77:     */     }
/*   78:     */   }
/*   79:     */   
/*   80: 205 */   public static ImageAnchorProperties MOVE_AND_SIZE_WITH_CELLS = new ImageAnchorProperties(1);
/*   81: 207 */   public static ImageAnchorProperties MOVE_WITH_CELLS = new ImageAnchorProperties(2);
/*   82: 209 */   public static ImageAnchorProperties NO_MOVE_OR_SIZE_WITH_CELLS = new ImageAnchorProperties(3);
/*   83:     */   private static final double DEFAULT_FONT_SIZE = 10.0D;
/*   84:     */   
/*   85:     */   public Drawing(MsoDrawingRecord mso, ObjRecord obj, DrawingData dd, DrawingGroup dg, Sheet s)
/*   86:     */   {
/*   87: 231 */     this.drawingGroup = dg;
/*   88: 232 */     this.msoDrawingRecord = mso;
/*   89: 233 */     this.drawingData = dd;
/*   90: 234 */     this.objRecord = obj;
/*   91: 235 */     this.sheet = s;
/*   92: 236 */     this.initialized = false;
/*   93: 237 */     this.origin = Origin.READ;
/*   94: 238 */     this.drawingData.addData(this.msoDrawingRecord.getData());
/*   95: 239 */     this.drawingNumber = (this.drawingData.getNumDrawings() - 1);
/*   96: 240 */     this.drawingGroup.addDrawing(this);
/*   97:     */     
/*   98: 242 */     Assert.verify((mso != null) && (obj != null));
/*   99:     */     
/*  100: 244 */     initialize();
/*  101:     */   }
/*  102:     */   
/*  103:     */   protected Drawing(DrawingGroupObject dgo, DrawingGroup dg)
/*  104:     */   {
/*  105: 255 */     Drawing d = (Drawing)dgo;
/*  106: 256 */     Assert.verify(d.origin == Origin.READ);
/*  107: 257 */     this.msoDrawingRecord = d.msoDrawingRecord;
/*  108: 258 */     this.objRecord = d.objRecord;
/*  109: 259 */     this.initialized = false;
/*  110: 260 */     this.origin = Origin.READ;
/*  111: 261 */     this.drawingData = d.drawingData;
/*  112: 262 */     this.drawingGroup = dg;
/*  113: 263 */     this.drawingNumber = d.drawingNumber;
/*  114: 264 */     this.drawingGroup.addDrawing(this);
/*  115:     */   }
/*  116:     */   
/*  117:     */   public Drawing(double x, double y, double w, double h, java.io.File image)
/*  118:     */   {
/*  119: 282 */     this.imageFile = image;
/*  120: 283 */     this.initialized = true;
/*  121: 284 */     this.origin = Origin.WRITE;
/*  122: 285 */     this.x = x;
/*  123: 286 */     this.y = y;
/*  124: 287 */     this.width = w;
/*  125: 288 */     this.height = h;
/*  126: 289 */     this.referenceCount = 1;
/*  127: 290 */     this.imageAnchorProperties = MOVE_WITH_CELLS;
/*  128: 291 */     this.type = ShapeType.PICTURE_FRAME;
/*  129:     */   }
/*  130:     */   
/*  131:     */   public Drawing(double x, double y, double w, double h, byte[] image)
/*  132:     */   {
/*  133: 309 */     this.imageData = image;
/*  134: 310 */     this.initialized = true;
/*  135: 311 */     this.origin = Origin.WRITE;
/*  136: 312 */     this.x = x;
/*  137: 313 */     this.y = y;
/*  138: 314 */     this.width = w;
/*  139: 315 */     this.height = h;
/*  140: 316 */     this.referenceCount = 1;
/*  141: 317 */     this.imageAnchorProperties = MOVE_WITH_CELLS;
/*  142: 318 */     this.type = ShapeType.PICTURE_FRAME;
/*  143:     */   }
/*  144:     */   
/*  145:     */   private void initialize()
/*  146:     */   {
/*  147: 326 */     this.readSpContainer = this.drawingData.getSpContainer(this.drawingNumber);
/*  148: 327 */     Assert.verify(this.readSpContainer != null);
/*  149:     */     
/*  150: 329 */     EscherRecord[] children = this.readSpContainer.getChildren();
/*  151:     */     
/*  152: 331 */     Sp sp = (Sp)this.readSpContainer.getChildren()[0];
/*  153: 332 */     this.shapeId = sp.getShapeId();
/*  154: 333 */     this.objectId = this.objRecord.getObjectId();
/*  155: 334 */     this.type = ShapeType.getType(sp.getShapeType());
/*  156: 336 */     if (this.type == ShapeType.UNKNOWN) {
/*  157: 338 */       logger.warn("Unknown shape type");
/*  158:     */     }
/*  159: 341 */     Opt opt = (Opt)this.readSpContainer.getChildren()[1];
/*  160: 343 */     if (opt.getProperty(260) != null) {
/*  161: 345 */       this.blipId = opt.getProperty(260).value;
/*  162:     */     }
/*  163: 348 */     if (opt.getProperty(261) != null)
/*  164:     */     {
/*  165: 350 */       this.imageFile = new java.io.File(opt.getProperty(261).stringValue);
/*  166:     */     }
/*  167: 354 */     else if (this.type == ShapeType.PICTURE_FRAME)
/*  168:     */     {
/*  169: 356 */       logger.warn("no filename property for drawing");
/*  170: 357 */       this.imageFile = new java.io.File(Integer.toString(this.blipId));
/*  171:     */     }
/*  172: 361 */     ClientAnchor clientAnchor = null;
/*  173: 362 */     for (int i = 0; (i < children.length) && (clientAnchor == null); i++) {
/*  174: 364 */       if (children[i].getType() == EscherRecordType.CLIENT_ANCHOR) {
/*  175: 366 */         clientAnchor = (ClientAnchor)children[i];
/*  176:     */       }
/*  177:     */     }
/*  178: 370 */     if (clientAnchor == null)
/*  179:     */     {
/*  180: 372 */       logger.warn("client anchor not found");
/*  181:     */     }
/*  182:     */     else
/*  183:     */     {
/*  184: 376 */       this.x = clientAnchor.getX1();
/*  185: 377 */       this.y = clientAnchor.getY1();
/*  186: 378 */       this.width = (clientAnchor.getX2() - this.x);
/*  187: 379 */       this.height = (clientAnchor.getY2() - this.y);
/*  188: 380 */       this.imageAnchorProperties = ImageAnchorProperties.getImageAnchorProperties(clientAnchor.getProperties());
/*  189:     */     }
/*  190: 384 */     if (this.blipId == 0) {
/*  191: 386 */       logger.warn("linked drawings are not supported");
/*  192:     */     }
/*  193: 389 */     this.initialized = true;
/*  194:     */   }
/*  195:     */   
/*  196:     */   public java.io.File getImageFile()
/*  197:     */   {
/*  198: 399 */     return this.imageFile;
/*  199:     */   }
/*  200:     */   
/*  201:     */   public String getImageFilePath()
/*  202:     */   {
/*  203: 411 */     if (this.imageFile == null) {
/*  204: 414 */       return this.blipId != 0 ? Integer.toString(this.blipId) : "__new__image__";
/*  205:     */     }
/*  206: 417 */     return this.imageFile.getPath();
/*  207:     */   }
/*  208:     */   
/*  209:     */   public final void setObjectId(int objid, int bip, int sid)
/*  210:     */   {
/*  211: 430 */     this.objectId = objid;
/*  212: 431 */     this.blipId = bip;
/*  213: 432 */     this.shapeId = sid;
/*  214: 434 */     if (this.origin == Origin.READ) {
/*  215: 436 */       this.origin = Origin.READ_WRITE;
/*  216:     */     }
/*  217:     */   }
/*  218:     */   
/*  219:     */   public final int getObjectId()
/*  220:     */   {
/*  221: 447 */     if (!this.initialized) {
/*  222: 449 */       initialize();
/*  223:     */     }
/*  224: 452 */     return this.objectId;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public int getShapeId()
/*  228:     */   {
/*  229: 462 */     if (!this.initialized) {
/*  230: 464 */       initialize();
/*  231:     */     }
/*  232: 467 */     return this.shapeId;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public final int getBlipId()
/*  236:     */   {
/*  237: 477 */     if (!this.initialized) {
/*  238: 479 */       initialize();
/*  239:     */     }
/*  240: 482 */     return this.blipId;
/*  241:     */   }
/*  242:     */   
/*  243:     */   public MsoDrawingRecord getMsoDrawingRecord()
/*  244:     */   {
/*  245: 492 */     return this.msoDrawingRecord;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public EscherContainer getSpContainer()
/*  249:     */   {
/*  250: 502 */     if (!this.initialized) {
/*  251: 504 */       initialize();
/*  252:     */     }
/*  253: 507 */     if (this.origin == Origin.READ) {
/*  254: 509 */       return getReadSpContainer();
/*  255:     */     }
/*  256: 512 */     SpContainer spContainer = new SpContainer();
/*  257: 513 */     Sp sp = new Sp(this.type, this.shapeId, 2560);
/*  258: 514 */     spContainer.add(sp);
/*  259: 515 */     Opt opt = new Opt();
/*  260: 516 */     opt.addProperty(260, true, false, this.blipId);
/*  261: 518 */     if (this.type == ShapeType.PICTURE_FRAME)
/*  262:     */     {
/*  263: 520 */       String filePath = this.imageFile != null ? this.imageFile.getPath() : "";
/*  264: 521 */       opt.addProperty(261, true, true, filePath.length() * 2, filePath);
/*  265: 522 */       opt.addProperty(447, false, false, 65536);
/*  266: 523 */       opt.addProperty(959, false, false, 524288);
/*  267: 524 */       spContainer.add(opt);
/*  268:     */     }
/*  269: 527 */     ClientAnchor clientAnchor = new ClientAnchor(this.x, this.y, this.x + this.width, this.y + this.height, this.imageAnchorProperties.getValue());
/*  270:     */     
/*  271:     */ 
/*  272: 530 */     spContainer.add(clientAnchor);
/*  273: 531 */     ClientData clientData = new ClientData();
/*  274: 532 */     spContainer.add(clientData);
/*  275:     */     
/*  276: 534 */     return spContainer;
/*  277:     */   }
/*  278:     */   
/*  279:     */   public void setDrawingGroup(DrawingGroup dg)
/*  280:     */   {
/*  281: 545 */     this.drawingGroup = dg;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public DrawingGroup getDrawingGroup()
/*  285:     */   {
/*  286: 555 */     return this.drawingGroup;
/*  287:     */   }
/*  288:     */   
/*  289:     */   public Origin getOrigin()
/*  290:     */   {
/*  291: 565 */     return this.origin;
/*  292:     */   }
/*  293:     */   
/*  294:     */   public int getReferenceCount()
/*  295:     */   {
/*  296: 575 */     return this.referenceCount;
/*  297:     */   }
/*  298:     */   
/*  299:     */   public void setReferenceCount(int r)
/*  300:     */   {
/*  301: 585 */     this.referenceCount = r;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public double getX()
/*  305:     */   {
/*  306: 595 */     if (!this.initialized) {
/*  307: 597 */       initialize();
/*  308:     */     }
/*  309: 599 */     return this.x;
/*  310:     */   }
/*  311:     */   
/*  312:     */   public void setX(double x)
/*  313:     */   {
/*  314: 609 */     if (this.origin == Origin.READ)
/*  315:     */     {
/*  316: 611 */       if (!this.initialized) {
/*  317: 613 */         initialize();
/*  318:     */       }
/*  319: 615 */       this.origin = Origin.READ_WRITE;
/*  320:     */     }
/*  321: 618 */     this.x = x;
/*  322:     */   }
/*  323:     */   
/*  324:     */   public double getY()
/*  325:     */   {
/*  326: 628 */     if (!this.initialized) {
/*  327: 630 */       initialize();
/*  328:     */     }
/*  329: 633 */     return this.y;
/*  330:     */   }
/*  331:     */   
/*  332:     */   public void setY(double y)
/*  333:     */   {
/*  334: 643 */     if (this.origin == Origin.READ)
/*  335:     */     {
/*  336: 645 */       if (!this.initialized) {
/*  337: 647 */         initialize();
/*  338:     */       }
/*  339: 649 */       this.origin = Origin.READ_WRITE;
/*  340:     */     }
/*  341: 652 */     this.y = y;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public double getWidth()
/*  345:     */   {
/*  346: 663 */     if (!this.initialized) {
/*  347: 665 */       initialize();
/*  348:     */     }
/*  349: 668 */     return this.width;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public void setWidth(double w)
/*  353:     */   {
/*  354: 678 */     if (this.origin == Origin.READ)
/*  355:     */     {
/*  356: 680 */       if (!this.initialized) {
/*  357: 682 */         initialize();
/*  358:     */       }
/*  359: 684 */       this.origin = Origin.READ_WRITE;
/*  360:     */     }
/*  361: 687 */     this.width = w;
/*  362:     */   }
/*  363:     */   
/*  364:     */   public double getHeight()
/*  365:     */   {
/*  366: 697 */     if (!this.initialized) {
/*  367: 699 */       initialize();
/*  368:     */     }
/*  369: 702 */     return this.height;
/*  370:     */   }
/*  371:     */   
/*  372:     */   public void setHeight(double h)
/*  373:     */   {
/*  374: 712 */     if (this.origin == Origin.READ)
/*  375:     */     {
/*  376: 714 */       if (!this.initialized) {
/*  377: 716 */         initialize();
/*  378:     */       }
/*  379: 718 */       this.origin = Origin.READ_WRITE;
/*  380:     */     }
/*  381: 721 */     this.height = h;
/*  382:     */   }
/*  383:     */   
/*  384:     */   private EscherContainer getReadSpContainer()
/*  385:     */   {
/*  386: 732 */     if (!this.initialized) {
/*  387: 734 */       initialize();
/*  388:     */     }
/*  389: 737 */     return this.readSpContainer;
/*  390:     */   }
/*  391:     */   
/*  392:     */   public byte[] getImageData()
/*  393:     */   {
/*  394: 747 */     Assert.verify((this.origin == Origin.READ) || (this.origin == Origin.READ_WRITE));
/*  395: 749 */     if (!this.initialized) {
/*  396: 751 */       initialize();
/*  397:     */     }
/*  398: 754 */     return this.drawingGroup.getImageData(this.blipId);
/*  399:     */   }
/*  400:     */   
/*  401:     */   public byte[] getImageBytes()
/*  402:     */     throws IOException
/*  403:     */   {
/*  404: 764 */     if ((this.origin == Origin.READ) || (this.origin == Origin.READ_WRITE)) {
/*  405: 766 */       return getImageData();
/*  406:     */     }
/*  407: 769 */     Assert.verify(this.origin == Origin.WRITE);
/*  408: 771 */     if (this.imageFile == null)
/*  409:     */     {
/*  410: 773 */       Assert.verify(this.imageData != null);
/*  411: 774 */       return this.imageData;
/*  412:     */     }
/*  413: 777 */     byte[] data = new byte[(int)this.imageFile.length()];
/*  414: 778 */     FileInputStream fis = new FileInputStream(this.imageFile);
/*  415: 779 */     fis.read(data, 0, data.length);
/*  416: 780 */     fis.close();
/*  417: 781 */     return data;
/*  418:     */   }
/*  419:     */   
/*  420:     */   public ShapeType getType()
/*  421:     */   {
/*  422: 791 */     return this.type;
/*  423:     */   }
/*  424:     */   
/*  425:     */   public void writeAdditionalRecords(jxl.write.biff.File outputFile)
/*  426:     */     throws IOException
/*  427:     */   {
/*  428: 802 */     if (this.origin == Origin.READ)
/*  429:     */     {
/*  430: 804 */       outputFile.write(this.objRecord);
/*  431: 805 */       return;
/*  432:     */     }
/*  433: 809 */     ObjRecord objrec = new ObjRecord(this.objectId, ObjRecord.PICTURE);
/*  434:     */     
/*  435: 811 */     outputFile.write(objrec);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public void writeTailRecords(jxl.write.biff.File outputFile)
/*  439:     */     throws IOException
/*  440:     */   {}
/*  441:     */   
/*  442:     */   public double getColumn()
/*  443:     */   {
/*  444: 833 */     return getX();
/*  445:     */   }
/*  446:     */   
/*  447:     */   public double getRow()
/*  448:     */   {
/*  449: 843 */     return getY();
/*  450:     */   }
/*  451:     */   
/*  452:     */   public boolean isFirst()
/*  453:     */   {
/*  454: 855 */     return this.msoDrawingRecord.isFirst();
/*  455:     */   }
/*  456:     */   
/*  457:     */   public boolean isFormObject()
/*  458:     */   {
/*  459: 867 */     return false;
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void removeRow(int r)
/*  463:     */   {
/*  464: 877 */     if (this.y > r) {
/*  465: 879 */       setY(r);
/*  466:     */     }
/*  467:     */   }
/*  468:     */   
/*  469:     */   private double getWidthInPoints()
/*  470:     */   {
/*  471: 891 */     if (this.sheet == null)
/*  472:     */     {
/*  473: 893 */       logger.warn("calculating image width:  sheet is null");
/*  474: 894 */       return 0.0D;
/*  475:     */     }
/*  476: 898 */     int firstCol = (int)this.x;
/*  477: 899 */     int lastCol = (int)Math.ceil(this.x + this.width) - 1;
/*  478:     */     
/*  479:     */ 
/*  480:     */ 
/*  481:     */ 
/*  482:     */ 
/*  483:     */ 
/*  484:     */ 
/*  485:     */ 
/*  486: 908 */     CellView cellView = this.sheet.getColumnView(firstCol);
/*  487: 909 */     int firstColWidth = cellView.getSize();
/*  488: 910 */     double firstColImageWidth = (1.0D - (this.x - firstCol)) * firstColWidth;
/*  489: 911 */     double pointSize = cellView.getFormat() != null ? cellView.getFormat().getFont().getPointSize() : 10.0D;
/*  490:     */     
/*  491: 913 */     double firstColWidthInPoints = firstColImageWidth * 0.59D * pointSize / 256.0D;
/*  492:     */     
/*  493:     */ 
/*  494:     */ 
/*  495: 917 */     int lastColWidth = 0;
/*  496: 918 */     double lastColImageWidth = 0.0D;
/*  497: 919 */     double lastColWidthInPoints = 0.0D;
/*  498: 920 */     if (lastCol != firstCol)
/*  499:     */     {
/*  500: 922 */       cellView = this.sheet.getColumnView(lastCol);
/*  501: 923 */       lastColWidth = cellView.getSize();
/*  502: 924 */       lastColImageWidth = (this.x + this.width - lastCol) * lastColWidth;
/*  503: 925 */       pointSize = cellView.getFormat() != null ? cellView.getFormat().getFont().getPointSize() : 10.0D;
/*  504:     */       
/*  505: 927 */       lastColWidthInPoints = lastColImageWidth * 0.59D * pointSize / 256.0D;
/*  506:     */     }
/*  507: 931 */     double width = 0.0D;
/*  508: 932 */     for (int i = 0; i < lastCol - firstCol - 1; i++)
/*  509:     */     {
/*  510: 934 */       cellView = this.sheet.getColumnView(firstCol + 1 + i);
/*  511: 935 */       pointSize = cellView.getFormat() != null ? cellView.getFormat().getFont().getPointSize() : 10.0D;
/*  512:     */       
/*  513: 937 */       width += cellView.getSize() * 0.59D * pointSize / 256.0D;
/*  514:     */     }
/*  515: 941 */     double widthInPoints = width + firstColWidthInPoints + lastColWidthInPoints;
/*  516:     */     
/*  517:     */ 
/*  518: 944 */     return widthInPoints;
/*  519:     */   }
/*  520:     */   
/*  521:     */   private double getHeightInPoints()
/*  522:     */   {
/*  523: 955 */     if (this.sheet == null)
/*  524:     */     {
/*  525: 957 */       logger.warn("calculating image height:  sheet is null");
/*  526: 958 */       return 0.0D;
/*  527:     */     }
/*  528: 962 */     int firstRow = (int)this.y;
/*  529: 963 */     int lastRow = (int)Math.ceil(this.y + this.height) - 1;
/*  530:     */     
/*  531:     */ 
/*  532:     */ 
/*  533: 967 */     int firstRowHeight = this.sheet.getRowView(firstRow).getSize();
/*  534: 968 */     double firstRowImageHeight = (1.0D - (this.y - firstRow)) * firstRowHeight;
/*  535:     */     
/*  536:     */ 
/*  537:     */ 
/*  538: 972 */     int lastRowHeight = 0;
/*  539: 973 */     double lastRowImageHeight = 0.0D;
/*  540: 974 */     if (lastRow != firstRow)
/*  541:     */     {
/*  542: 976 */       lastRowHeight = this.sheet.getRowView(lastRow).getSize();
/*  543: 977 */       lastRowImageHeight = (this.y + this.height - lastRow) * lastRowHeight;
/*  544:     */     }
/*  545: 981 */     double height = 0.0D;
/*  546: 982 */     for (int i = 0; i < lastRow - firstRow - 1; i++) {
/*  547: 984 */       height += this.sheet.getRowView(firstRow + 1 + i).getSize();
/*  548:     */     }
/*  549: 988 */     double heightInTwips = height + firstRowHeight + lastRowHeight;
/*  550:     */     
/*  551:     */ 
/*  552:     */ 
/*  553: 992 */     double heightInPoints = heightInTwips / 20.0D;
/*  554:     */     
/*  555: 994 */     return heightInPoints;
/*  556:     */   }
/*  557:     */   
/*  558:     */   public double getWidth(LengthUnit unit)
/*  559:     */   {
/*  560:1005 */     double widthInPoints = getWidthInPoints();
/*  561:1006 */     return widthInPoints * LengthConverter.getConversionFactor(LengthUnit.POINTS, unit);
/*  562:     */   }
/*  563:     */   
/*  564:     */   public double getHeight(LengthUnit unit)
/*  565:     */   {
/*  566:1018 */     double heightInPoints = getHeightInPoints();
/*  567:1019 */     return heightInPoints * LengthConverter.getConversionFactor(LengthUnit.POINTS, unit);
/*  568:     */   }
/*  569:     */   
/*  570:     */   public int getImageWidth()
/*  571:     */   {
/*  572:1032 */     return getPngReader().getWidth();
/*  573:     */   }
/*  574:     */   
/*  575:     */   public int getImageHeight()
/*  576:     */   {
/*  577:1044 */     return getPngReader().getHeight();
/*  578:     */   }
/*  579:     */   
/*  580:     */   public double getHorizontalResolution(LengthUnit unit)
/*  581:     */   {
/*  582:1056 */     int res = getPngReader().getHorizontalResolution();
/*  583:1057 */     return res / LengthConverter.getConversionFactor(LengthUnit.METRES, unit);
/*  584:     */   }
/*  585:     */   
/*  586:     */   public double getVerticalResolution(LengthUnit unit)
/*  587:     */   {
/*  588:1068 */     int res = getPngReader().getVerticalResolution();
/*  589:1069 */     return res / LengthConverter.getConversionFactor(LengthUnit.METRES, unit);
/*  590:     */   }
/*  591:     */   
/*  592:     */   private PNGReader getPngReader()
/*  593:     */   {
/*  594:1074 */     if (this.pngReader != null) {
/*  595:1076 */       return this.pngReader;
/*  596:     */     }
/*  597:1079 */     byte[] imdata = null;
/*  598:1080 */     if ((this.origin == Origin.READ) || (this.origin == Origin.READ_WRITE)) {
/*  599:1082 */       imdata = getImageData();
/*  600:     */     } else {
/*  601:     */       try
/*  602:     */       {
/*  603:1088 */         imdata = getImageBytes();
/*  604:     */       }
/*  605:     */       catch (IOException e)
/*  606:     */       {
/*  607:1092 */         logger.warn("Could not read image file");
/*  608:1093 */         imdata = new byte[0];
/*  609:     */       }
/*  610:     */     }
/*  611:1097 */     this.pngReader = new PNGReader(imdata);
/*  612:1098 */     this.pngReader.read();
/*  613:1099 */     return this.pngReader;
/*  614:     */   }
/*  615:     */   
/*  616:     */   protected void setImageAnchor(ImageAnchorProperties iap)
/*  617:     */   {
/*  618:1107 */     this.imageAnchorProperties = iap;
/*  619:1109 */     if (this.origin == Origin.READ) {
/*  620:1111 */       this.origin = Origin.READ_WRITE;
/*  621:     */     }
/*  622:     */   }
/*  623:     */   
/*  624:     */   protected ImageAnchorProperties getImageAnchor()
/*  625:     */   {
/*  626:1120 */     if (!this.initialized) {
/*  627:1122 */       initialize();
/*  628:     */     }
/*  629:1125 */     return this.imageAnchorProperties;
/*  630:     */   }
/*  631:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.Drawing
 * JD-Core Version:    0.7.0.1
 */