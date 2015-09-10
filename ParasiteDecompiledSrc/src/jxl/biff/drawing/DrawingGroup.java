/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import jxl.common.Assert;
/*   8:    */ import jxl.common.Logger;
/*   9:    */ import jxl.read.biff.Record;
/*  10:    */ import jxl.write.biff.File;
/*  11:    */ 
/*  12:    */ public class DrawingGroup
/*  13:    */   implements EscherStream
/*  14:    */ {
/*  15: 42 */   private static Logger logger = Logger.getLogger(DrawingGroup.class);
/*  16:    */   private byte[] drawingData;
/*  17:    */   private EscherContainer escherData;
/*  18:    */   private BStoreContainer bstoreContainer;
/*  19:    */   private boolean initialized;
/*  20:    */   private ArrayList drawings;
/*  21:    */   private int numBlips;
/*  22:    */   private int numCharts;
/*  23:    */   private int drawingGroupId;
/*  24:    */   private boolean drawingsOmitted;
/*  25:    */   private Origin origin;
/*  26:    */   private HashMap imageFiles;
/*  27:    */   private int maxObjectId;
/*  28:    */   private int maxShapeId;
/*  29:    */   
/*  30:    */   public DrawingGroup(Origin o)
/*  31:    */   {
/*  32:118 */     this.origin = o;
/*  33:119 */     this.initialized = (o == Origin.WRITE);
/*  34:120 */     this.drawings = new ArrayList();
/*  35:121 */     this.imageFiles = new HashMap();
/*  36:122 */     this.drawingsOmitted = false;
/*  37:123 */     this.maxObjectId = 1;
/*  38:124 */     this.maxShapeId = 1024;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public DrawingGroup(DrawingGroup dg)
/*  42:    */   {
/*  43:137 */     this.drawingData = dg.drawingData;
/*  44:138 */     this.escherData = dg.escherData;
/*  45:139 */     this.bstoreContainer = dg.bstoreContainer;
/*  46:140 */     this.initialized = dg.initialized;
/*  47:141 */     this.drawingData = dg.drawingData;
/*  48:142 */     this.escherData = dg.escherData;
/*  49:143 */     this.bstoreContainer = dg.bstoreContainer;
/*  50:144 */     this.numBlips = dg.numBlips;
/*  51:145 */     this.numCharts = dg.numCharts;
/*  52:146 */     this.drawingGroupId = dg.drawingGroupId;
/*  53:147 */     this.drawingsOmitted = dg.drawingsOmitted;
/*  54:148 */     this.origin = dg.origin;
/*  55:149 */     this.imageFiles = ((HashMap)dg.imageFiles.clone());
/*  56:150 */     this.maxObjectId = dg.maxObjectId;
/*  57:151 */     this.maxShapeId = dg.maxShapeId;
/*  58:    */     
/*  59:    */ 
/*  60:    */ 
/*  61:155 */     this.drawings = new ArrayList();
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void add(MsoDrawingGroupRecord mso)
/*  65:    */   {
/*  66:169 */     addData(mso.getData());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void add(Record cont)
/*  70:    */   {
/*  71:180 */     addData(cont.getData());
/*  72:    */   }
/*  73:    */   
/*  74:    */   private void addData(byte[] msodata)
/*  75:    */   {
/*  76:190 */     if (this.drawingData == null)
/*  77:    */     {
/*  78:192 */       this.drawingData = new byte[msodata.length];
/*  79:193 */       System.arraycopy(msodata, 0, this.drawingData, 0, msodata.length);
/*  80:194 */       return;
/*  81:    */     }
/*  82:198 */     byte[] newdata = new byte[this.drawingData.length + msodata.length];
/*  83:199 */     System.arraycopy(this.drawingData, 0, newdata, 0, this.drawingData.length);
/*  84:200 */     System.arraycopy(msodata, 0, newdata, this.drawingData.length, msodata.length);
/*  85:201 */     this.drawingData = newdata;
/*  86:    */   }
/*  87:    */   
/*  88:    */   final void addDrawing(DrawingGroupObject d)
/*  89:    */   {
/*  90:211 */     this.drawings.add(d);
/*  91:212 */     this.maxObjectId = Math.max(this.maxObjectId, d.getObjectId());
/*  92:213 */     this.maxShapeId = Math.max(this.maxShapeId, d.getShapeId());
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void add(Chart c)
/*  96:    */   {
/*  97:223 */     this.numCharts += 1;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void add(DrawingGroupObject d)
/* 101:    */   {
/* 102:233 */     if (this.origin == Origin.READ)
/* 103:    */     {
/* 104:235 */       this.origin = Origin.READ_WRITE;
/* 105:236 */       BStoreContainer bsc = getBStoreContainer();
/* 106:237 */       Dgg dgg = (Dgg)this.escherData.getChildren()[0];
/* 107:238 */       this.drawingGroupId = (dgg.getCluster(1).drawingGroupId - this.numBlips - 1);
/* 108:239 */       this.numBlips = (bsc != null ? bsc.getNumBlips() : 0);
/* 109:241 */       if (bsc != null) {
/* 110:243 */         Assert.verify(this.numBlips == bsc.getNumBlips());
/* 111:    */       }
/* 112:    */     }
/* 113:247 */     if (!(d instanceof Drawing))
/* 114:    */     {
/* 115:251 */       this.maxObjectId += 1;
/* 116:252 */       this.maxShapeId += 1;
/* 117:253 */       d.setDrawingGroup(this);
/* 118:254 */       d.setObjectId(this.maxObjectId, this.numBlips + 1, this.maxShapeId);
/* 119:255 */       if (this.drawings.size() > this.maxObjectId) {
/* 120:257 */         logger.warn("drawings length " + this.drawings.size() + " exceeds the max object id " + this.maxObjectId);
/* 121:    */       }
/* 122:261 */       return;
/* 123:    */     }
/* 124:264 */     Drawing drawing = (Drawing)d;
/* 125:    */     
/* 126:    */ 
/* 127:267 */     Drawing refImage = (Drawing)this.imageFiles.get(d.getImageFilePath());
/* 128:270 */     if (refImage == null)
/* 129:    */     {
/* 130:274 */       this.maxObjectId += 1;
/* 131:275 */       this.maxShapeId += 1;
/* 132:276 */       this.drawings.add(drawing);
/* 133:277 */       drawing.setDrawingGroup(this);
/* 134:278 */       drawing.setObjectId(this.maxObjectId, this.numBlips + 1, this.maxShapeId);
/* 135:279 */       this.numBlips += 1;
/* 136:280 */       this.imageFiles.put(drawing.getImageFilePath(), drawing);
/* 137:    */     }
/* 138:    */     else
/* 139:    */     {
/* 140:287 */       refImage.setReferenceCount(refImage.getReferenceCount() + 1);
/* 141:288 */       drawing.setDrawingGroup(this);
/* 142:289 */       drawing.setObjectId(refImage.getObjectId(), refImage.getBlipId(), refImage.getShapeId());
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void remove(DrawingGroupObject d)
/* 147:    */   {
/* 148:304 */     if (getBStoreContainer() == null) {
/* 149:306 */       return;
/* 150:    */     }
/* 151:309 */     if (this.origin == Origin.READ)
/* 152:    */     {
/* 153:311 */       this.origin = Origin.READ_WRITE;
/* 154:312 */       this.numBlips = getBStoreContainer().getNumBlips();
/* 155:313 */       Dgg dgg = (Dgg)this.escherData.getChildren()[0];
/* 156:314 */       this.drawingGroupId = (dgg.getCluster(1).drawingGroupId - this.numBlips - 1);
/* 157:    */     }
/* 158:318 */     EscherRecord[] children = getBStoreContainer().getChildren();
/* 159:319 */     BlipStoreEntry bse = (BlipStoreEntry)children[(d.getBlipId() - 1)];
/* 160:    */     
/* 161:321 */     bse.dereference();
/* 162:323 */     if (bse.getReferenceCount() == 0)
/* 163:    */     {
/* 164:326 */       getBStoreContainer().remove(bse);
/* 165:329 */       for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 166:    */       {
/* 167:331 */         DrawingGroupObject drawing = (DrawingGroupObject)i.next();
/* 168:333 */         if (drawing.getBlipId() > d.getBlipId()) {
/* 169:335 */           drawing.setObjectId(drawing.getObjectId(), drawing.getBlipId() - 1, drawing.getShapeId());
/* 170:    */         }
/* 171:    */       }
/* 172:341 */       this.numBlips -= 1;
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   private void initialize()
/* 177:    */   {
/* 178:351 */     EscherRecordData er = new EscherRecordData(this, 0);
/* 179:    */     
/* 180:353 */     Assert.verify(er.isContainer());
/* 181:    */     
/* 182:355 */     this.escherData = new EscherContainer(er);
/* 183:    */     
/* 184:357 */     Assert.verify(this.escherData.getLength() == this.drawingData.length);
/* 185:358 */     Assert.verify(this.escherData.getType() == EscherRecordType.DGG_CONTAINER);
/* 186:    */     
/* 187:360 */     this.initialized = true;
/* 188:    */   }
/* 189:    */   
/* 190:    */   private BStoreContainer getBStoreContainer()
/* 191:    */   {
/* 192:370 */     if (this.bstoreContainer == null)
/* 193:    */     {
/* 194:372 */       if (!this.initialized) {
/* 195:374 */         initialize();
/* 196:    */       }
/* 197:377 */       EscherRecord[] children = this.escherData.getChildren();
/* 198:378 */       if ((children.length > 1) && (children[1].getType() == EscherRecordType.BSTORE_CONTAINER)) {
/* 199:381 */         this.bstoreContainer = ((BStoreContainer)children[1]);
/* 200:    */       }
/* 201:    */     }
/* 202:385 */     return this.bstoreContainer;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public byte[] getData()
/* 206:    */   {
/* 207:395 */     return this.drawingData;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void write(File outputFile)
/* 211:    */     throws IOException
/* 212:    */   {
/* 213:406 */     if (this.origin == Origin.WRITE)
/* 214:    */     {
/* 215:408 */       DggContainer dggContainer = new DggContainer();
/* 216:    */       
/* 217:410 */       Dgg dgg = new Dgg(this.numBlips + this.numCharts + 1, this.numBlips);
/* 218:    */       
/* 219:412 */       dgg.addCluster(1, 0);
/* 220:413 */       dgg.addCluster(this.numBlips + 1, 0);
/* 221:    */       
/* 222:415 */       dggContainer.add(dgg);
/* 223:    */       
/* 224:417 */       int drawingsAdded = 0;
/* 225:418 */       BStoreContainer bstoreCont = new BStoreContainer();
/* 226:421 */       for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 227:    */       {
/* 228:423 */         Object o = i.next();
/* 229:424 */         if ((o instanceof Drawing))
/* 230:    */         {
/* 231:426 */           Drawing d = (Drawing)o;
/* 232:427 */           BlipStoreEntry bse = new BlipStoreEntry(d);
/* 233:    */           
/* 234:429 */           bstoreCont.add(bse);
/* 235:430 */           drawingsAdded++;
/* 236:    */         }
/* 237:    */       }
/* 238:433 */       if (drawingsAdded > 0)
/* 239:    */       {
/* 240:435 */         bstoreCont.setNumBlips(drawingsAdded);
/* 241:436 */         dggContainer.add(bstoreCont);
/* 242:    */       }
/* 243:439 */       Opt opt = new Opt();
/* 244:    */       
/* 245:441 */       dggContainer.add(opt);
/* 246:    */       
/* 247:443 */       SplitMenuColors splitMenuColors = new SplitMenuColors();
/* 248:444 */       dggContainer.add(splitMenuColors);
/* 249:    */       
/* 250:446 */       this.drawingData = dggContainer.getData();
/* 251:    */     }
/* 252:448 */     else if (this.origin == Origin.READ_WRITE)
/* 253:    */     {
/* 254:450 */       DggContainer dggContainer = new DggContainer();
/* 255:    */       
/* 256:452 */       Dgg dgg = new Dgg(this.numBlips + this.numCharts + 1, this.numBlips);
/* 257:    */       
/* 258:454 */       dgg.addCluster(1, 0);
/* 259:455 */       dgg.addCluster(this.drawingGroupId + this.numBlips + 1, 0);
/* 260:    */       
/* 261:457 */       dggContainer.add(dgg);
/* 262:    */       
/* 263:459 */       BStoreContainer bstoreCont = new BStoreContainer();
/* 264:460 */       bstoreCont.setNumBlips(this.numBlips);
/* 265:    */       
/* 266:    */ 
/* 267:463 */       BStoreContainer readBStoreContainer = getBStoreContainer();
/* 268:465 */       if (readBStoreContainer != null)
/* 269:    */       {
/* 270:467 */         EscherRecord[] children = readBStoreContainer.getChildren();
/* 271:468 */         for (int i = 0; i < children.length; i++)
/* 272:    */         {
/* 273:470 */           BlipStoreEntry bse = (BlipStoreEntry)children[i];
/* 274:471 */           bstoreCont.add(bse);
/* 275:    */         }
/* 276:    */       }
/* 277:476 */       for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 278:    */       {
/* 279:478 */         DrawingGroupObject dgo = (DrawingGroupObject)i.next();
/* 280:479 */         if ((dgo instanceof Drawing))
/* 281:    */         {
/* 282:481 */           Drawing d = (Drawing)dgo;
/* 283:482 */           if (d.getOrigin() == Origin.WRITE)
/* 284:    */           {
/* 285:484 */             BlipStoreEntry bse = new BlipStoreEntry(d);
/* 286:485 */             bstoreCont.add(bse);
/* 287:    */           }
/* 288:    */         }
/* 289:    */       }
/* 290:490 */       dggContainer.add(bstoreCont);
/* 291:    */       
/* 292:492 */       Opt opt = new Opt();
/* 293:    */       
/* 294:494 */       opt.addProperty(191, false, false, 524296);
/* 295:495 */       opt.addProperty(385, false, false, 134217737);
/* 296:496 */       opt.addProperty(448, false, false, 134217792);
/* 297:    */       
/* 298:498 */       dggContainer.add(opt);
/* 299:    */       
/* 300:500 */       SplitMenuColors splitMenuColors = new SplitMenuColors();
/* 301:501 */       dggContainer.add(splitMenuColors);
/* 302:    */       
/* 303:503 */       this.drawingData = dggContainer.getData();
/* 304:    */     }
/* 305:506 */     MsoDrawingGroupRecord msodg = new MsoDrawingGroupRecord(this.drawingData);
/* 306:507 */     outputFile.write(msodg);
/* 307:    */   }
/* 308:    */   
/* 309:    */   final int getNumberOfBlips()
/* 310:    */   {
/* 311:517 */     return this.numBlips;
/* 312:    */   }
/* 313:    */   
/* 314:    */   byte[] getImageData(int blipId)
/* 315:    */   {
/* 316:529 */     this.numBlips = getBStoreContainer().getNumBlips();
/* 317:    */     
/* 318:531 */     Assert.verify(blipId <= this.numBlips);
/* 319:532 */     Assert.verify((this.origin == Origin.READ) || (this.origin == Origin.READ_WRITE));
/* 320:    */     
/* 321:    */ 
/* 322:535 */     EscherRecord[] children = getBStoreContainer().getChildren();
/* 323:536 */     BlipStoreEntry bse = (BlipStoreEntry)children[(blipId - 1)];
/* 324:    */     
/* 325:538 */     return bse.getImageData();
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setDrawingsOmitted(MsoDrawingRecord mso, ObjRecord obj)
/* 329:    */   {
/* 330:550 */     this.drawingsOmitted = true;
/* 331:552 */     if (obj != null) {
/* 332:554 */       this.maxObjectId = Math.max(this.maxObjectId, obj.getObjectId());
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean hasDrawingsOmitted()
/* 337:    */   {
/* 338:565 */     return this.drawingsOmitted;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public void updateData(DrawingGroup dg)
/* 342:    */   {
/* 343:580 */     this.drawingsOmitted = dg.drawingsOmitted;
/* 344:581 */     this.maxObjectId = dg.maxObjectId;
/* 345:582 */     this.maxShapeId = dg.maxShapeId;
/* 346:    */   }
/* 347:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.DrawingGroup
 * JD-Core Version:    0.7.0.1
 */