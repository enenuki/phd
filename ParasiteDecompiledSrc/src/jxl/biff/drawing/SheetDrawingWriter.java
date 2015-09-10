/*   1:    */ package jxl.biff.drawing;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import jxl.WorkbookSettings;
/*   7:    */ import jxl.biff.IntegerHelper;
/*   8:    */ import jxl.common.Logger;
/*   9:    */ import jxl.write.biff.File;
/*  10:    */ 
/*  11:    */ public class SheetDrawingWriter
/*  12:    */ {
/*  13: 41 */   private static Logger logger = Logger.getLogger(SheetDrawingWriter.class);
/*  14:    */   private ArrayList drawings;
/*  15:    */   private boolean drawingsModified;
/*  16:    */   private Chart[] charts;
/*  17:    */   private WorkbookSettings workbookSettings;
/*  18:    */   
/*  19:    */   public SheetDrawingWriter(WorkbookSettings ws)
/*  20:    */   {
/*  21: 70 */     this.charts = new Chart[0];
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setDrawings(ArrayList dr, boolean mod)
/*  25:    */   {
/*  26: 81 */     this.drawings = dr;
/*  27: 82 */     this.drawingsModified = mod;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void write(File outputFile)
/*  31:    */     throws IOException
/*  32:    */   {
/*  33: 95 */     if ((this.drawings.size() == 0) && (this.charts.length == 0)) {
/*  34: 97 */       return;
/*  35:    */     }
/*  36:101 */     boolean modified = this.drawingsModified;
/*  37:102 */     int numImages = this.drawings.size();
/*  38:104 */     for (Iterator i = this.drawings.iterator(); (i.hasNext()) && (!modified);)
/*  39:    */     {
/*  40:106 */       DrawingGroupObject d = (DrawingGroupObject)i.next();
/*  41:107 */       if (d.getOrigin() != Origin.READ) {
/*  42:109 */         modified = true;
/*  43:    */       }
/*  44:    */     }
/*  45:115 */     if ((numImages > 0) && (!modified))
/*  46:    */     {
/*  47:117 */       DrawingGroupObject d2 = (DrawingGroupObject)this.drawings.get(0);
/*  48:118 */       if (!d2.isFirst()) {
/*  49:120 */         modified = true;
/*  50:    */       }
/*  51:    */     }
/*  52:126 */     if ((numImages == 0) && (this.charts.length == 1) && (this.charts[0].getMsoDrawingRecord() == null)) {
/*  53:130 */       modified = false;
/*  54:    */     }
/*  55:135 */     if (!modified)
/*  56:    */     {
/*  57:137 */       writeUnmodified(outputFile);
/*  58:138 */       return;
/*  59:    */     }
/*  60:141 */     Object[] spContainerData = new Object[numImages + this.charts.length];
/*  61:142 */     int length = 0;
/*  62:143 */     EscherContainer firstSpContainer = null;
/*  63:147 */     for (int i = 0; i < numImages; i++)
/*  64:    */     {
/*  65:149 */       DrawingGroupObject drawing = (DrawingGroupObject)this.drawings.get(i);
/*  66:    */       
/*  67:151 */       EscherContainer spc = drawing.getSpContainer();
/*  68:153 */       if (spc != null)
/*  69:    */       {
/*  70:155 */         byte[] data = spc.getData();
/*  71:156 */         spContainerData[i] = data;
/*  72:158 */         if (i == 0) {
/*  73:160 */           firstSpContainer = spc;
/*  74:    */         } else {
/*  75:164 */           length += data.length;
/*  76:    */         }
/*  77:    */       }
/*  78:    */     }
/*  79:170 */     for (int i = 0; i < this.charts.length; i++)
/*  80:    */     {
/*  81:172 */       EscherContainer spContainer = this.charts[i].getSpContainer();
/*  82:173 */       byte[] data = spContainer.getBytes();
/*  83:174 */       data = spContainer.setHeaderData(data);
/*  84:175 */       spContainerData[(i + numImages)] = data;
/*  85:177 */       if ((i == 0) && (numImages == 0)) {
/*  86:179 */         firstSpContainer = spContainer;
/*  87:    */       } else {
/*  88:183 */         length += data.length;
/*  89:    */       }
/*  90:    */     }
/*  91:188 */     DgContainer dgContainer = new DgContainer();
/*  92:189 */     Dg dg = new Dg(numImages + this.charts.length);
/*  93:190 */     dgContainer.add(dg);
/*  94:    */     
/*  95:192 */     SpgrContainer spgrContainer = new SpgrContainer();
/*  96:    */     
/*  97:194 */     SpContainer spContainer = new SpContainer();
/*  98:195 */     Spgr spgr = new Spgr();
/*  99:196 */     spContainer.add(spgr);
/* 100:197 */     Sp sp = new Sp(ShapeType.MIN, 1024, 5);
/* 101:198 */     spContainer.add(sp);
/* 102:199 */     spgrContainer.add(spContainer);
/* 103:    */     
/* 104:201 */     spgrContainer.add(firstSpContainer);
/* 105:    */     
/* 106:203 */     dgContainer.add(spgrContainer);
/* 107:    */     
/* 108:205 */     byte[] firstMsoData = dgContainer.getData();
/* 109:    */     
/* 110:    */ 
/* 111:208 */     int len = IntegerHelper.getInt(firstMsoData[4], firstMsoData[5], firstMsoData[6], firstMsoData[7]);
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:212 */     IntegerHelper.getFourBytes(len + length, firstMsoData, 4);
/* 116:    */     
/* 117:    */ 
/* 118:215 */     len = IntegerHelper.getInt(firstMsoData[28], firstMsoData[29], firstMsoData[30], firstMsoData[31]);
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:219 */     IntegerHelper.getFourBytes(len + length, firstMsoData, 28);
/* 123:226 */     if ((numImages > 0) && (((DrawingGroupObject)this.drawings.get(0)).isFormObject()))
/* 124:    */     {
/* 125:229 */       byte[] msodata2 = new byte[firstMsoData.length - 8];
/* 126:230 */       System.arraycopy(firstMsoData, 0, msodata2, 0, msodata2.length);
/* 127:231 */       firstMsoData = msodata2;
/* 128:    */     }
/* 129:234 */     MsoDrawingRecord msoDrawingRecord = new MsoDrawingRecord(firstMsoData);
/* 130:235 */     outputFile.write(msoDrawingRecord);
/* 131:237 */     if (numImages > 0)
/* 132:    */     {
/* 133:239 */       DrawingGroupObject firstDrawing = (DrawingGroupObject)this.drawings.get(0);
/* 134:240 */       firstDrawing.writeAdditionalRecords(outputFile);
/* 135:    */     }
/* 136:    */     else
/* 137:    */     {
/* 138:245 */       Chart chart = this.charts[0];
/* 139:246 */       ObjRecord objRecord = chart.getObjRecord();
/* 140:247 */       outputFile.write(objRecord);
/* 141:248 */       outputFile.write(chart);
/* 142:    */     }
/* 143:252 */     for (int i = 1; i < spContainerData.length; i++)
/* 144:    */     {
/* 145:254 */       byte[] bytes = (byte[])spContainerData[i];
/* 146:258 */       if ((i < numImages) && (((DrawingGroupObject)this.drawings.get(i)).isFormObject()))
/* 147:    */       {
/* 148:261 */         byte[] bytes2 = new byte[bytes.length - 8];
/* 149:262 */         System.arraycopy(bytes, 0, bytes2, 0, bytes2.length);
/* 150:263 */         bytes = bytes2;
/* 151:    */       }
/* 152:266 */       msoDrawingRecord = new MsoDrawingRecord(bytes);
/* 153:267 */       outputFile.write(msoDrawingRecord);
/* 154:269 */       if (i < numImages)
/* 155:    */       {
/* 156:272 */         DrawingGroupObject d = (DrawingGroupObject)this.drawings.get(i);
/* 157:273 */         d.writeAdditionalRecords(outputFile);
/* 158:    */       }
/* 159:    */       else
/* 160:    */       {
/* 161:277 */         Chart chart = this.charts[(i - numImages)];
/* 162:278 */         ObjRecord objRecord = chart.getObjRecord();
/* 163:279 */         outputFile.write(objRecord);
/* 164:280 */         outputFile.write(chart);
/* 165:    */       }
/* 166:    */     }
/* 167:285 */     for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 168:    */     {
/* 169:287 */       DrawingGroupObject dgo2 = (DrawingGroupObject)i.next();
/* 170:288 */       dgo2.writeTailRecords(outputFile);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void writeUnmodified(File outputFile)
/* 175:    */     throws IOException
/* 176:    */   {
/* 177:300 */     if ((this.charts.length == 0) && (this.drawings.size() == 0)) {
/* 178:303 */       return;
/* 179:    */     }
/* 180:305 */     if ((this.charts.length == 0) && (this.drawings.size() != 0))
/* 181:    */     {
/* 182:308 */       for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 183:    */       {
/* 184:310 */         DrawingGroupObject d = (DrawingGroupObject)i.next();
/* 185:311 */         outputFile.write(d.getMsoDrawingRecord());
/* 186:312 */         d.writeAdditionalRecords(outputFile);
/* 187:    */       }
/* 188:315 */       for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 189:    */       {
/* 190:317 */         DrawingGroupObject d = (DrawingGroupObject)i.next();
/* 191:318 */         d.writeTailRecords(outputFile);
/* 192:    */       }
/* 193:320 */       return;
/* 194:    */     }
/* 195:322 */     if ((this.drawings.size() == 0) && (this.charts.length != 0))
/* 196:    */     {
/* 197:325 */       Chart curChart = null;
/* 198:326 */       for (int i = 0; i < this.charts.length; i++)
/* 199:    */       {
/* 200:328 */         curChart = this.charts[i];
/* 201:329 */         if (curChart.getMsoDrawingRecord() != null) {
/* 202:331 */           outputFile.write(curChart.getMsoDrawingRecord());
/* 203:    */         }
/* 204:334 */         if (curChart.getObjRecord() != null) {
/* 205:336 */           outputFile.write(curChart.getObjRecord());
/* 206:    */         }
/* 207:339 */         outputFile.write(curChart);
/* 208:    */       }
/* 209:342 */       return;
/* 210:    */     }
/* 211:349 */     int numDrawings = this.drawings.size();
/* 212:350 */     int length = 0;
/* 213:351 */     EscherContainer[] spContainers = new EscherContainer[numDrawings + this.charts.length];
/* 214:    */     
/* 215:353 */     boolean[] isFormObject = new boolean[numDrawings + this.charts.length];
/* 216:355 */     for (int i = 0; i < numDrawings; i++)
/* 217:    */     {
/* 218:357 */       DrawingGroupObject d = (DrawingGroupObject)this.drawings.get(i);
/* 219:358 */       spContainers[i] = d.getSpContainer();
/* 220:360 */       if (i > 0) {
/* 221:362 */         length += spContainers[i].getLength();
/* 222:    */       }
/* 223:365 */       if (d.isFormObject()) {
/* 224:367 */         isFormObject[i] = true;
/* 225:    */       }
/* 226:    */     }
/* 227:371 */     for (int i = 0; i < this.charts.length; i++)
/* 228:    */     {
/* 229:373 */       spContainers[(i + numDrawings)] = this.charts[i].getSpContainer();
/* 230:374 */       length += spContainers[(i + numDrawings)].getLength();
/* 231:    */     }
/* 232:378 */     DgContainer dgContainer = new DgContainer();
/* 233:379 */     Dg dg = new Dg(numDrawings + this.charts.length);
/* 234:380 */     dgContainer.add(dg);
/* 235:    */     
/* 236:382 */     SpgrContainer spgrContainer = new SpgrContainer();
/* 237:    */     
/* 238:384 */     SpContainer spContainer = new SpContainer();
/* 239:385 */     Spgr spgr = new Spgr();
/* 240:386 */     spContainer.add(spgr);
/* 241:387 */     Sp sp = new Sp(ShapeType.MIN, 1024, 5);
/* 242:388 */     spContainer.add(sp);
/* 243:389 */     spgrContainer.add(spContainer);
/* 244:    */     
/* 245:391 */     spgrContainer.add(spContainers[0]);
/* 246:    */     
/* 247:393 */     dgContainer.add(spgrContainer);
/* 248:    */     
/* 249:395 */     byte[] firstMsoData = dgContainer.getData();
/* 250:    */     
/* 251:    */ 
/* 252:398 */     int len = IntegerHelper.getInt(firstMsoData[4], firstMsoData[5], firstMsoData[6], firstMsoData[7]);
/* 253:    */     
/* 254:    */ 
/* 255:    */ 
/* 256:402 */     IntegerHelper.getFourBytes(len + length, firstMsoData, 4);
/* 257:    */     
/* 258:    */ 
/* 259:405 */     len = IntegerHelper.getInt(firstMsoData[28], firstMsoData[29], firstMsoData[30], firstMsoData[31]);
/* 260:    */     
/* 261:    */ 
/* 262:    */ 
/* 263:409 */     IntegerHelper.getFourBytes(len + length, firstMsoData, 28);
/* 264:415 */     if (isFormObject[0] == 1)
/* 265:    */     {
/* 266:417 */       byte[] cbytes = new byte[firstMsoData.length - 8];
/* 267:418 */       System.arraycopy(firstMsoData, 0, cbytes, 0, cbytes.length);
/* 268:419 */       firstMsoData = cbytes;
/* 269:    */     }
/* 270:423 */     MsoDrawingRecord msoDrawingRecord = new MsoDrawingRecord(firstMsoData);
/* 271:424 */     outputFile.write(msoDrawingRecord);
/* 272:    */     
/* 273:426 */     DrawingGroupObject dgo = (DrawingGroupObject)this.drawings.get(0);
/* 274:427 */     dgo.writeAdditionalRecords(outputFile);
/* 275:430 */     for (int i = 1; i < spContainers.length; i++)
/* 276:    */     {
/* 277:432 */       byte[] bytes = spContainers[i].getBytes();
/* 278:433 */       byte[] bytes2 = spContainers[i].setHeaderData(bytes);
/* 279:437 */       if (isFormObject[i] == 1)
/* 280:    */       {
/* 281:439 */         byte[] cbytes = new byte[bytes2.length - 8];
/* 282:440 */         System.arraycopy(bytes2, 0, cbytes, 0, cbytes.length);
/* 283:441 */         bytes2 = cbytes;
/* 284:    */       }
/* 285:444 */       msoDrawingRecord = new MsoDrawingRecord(bytes2);
/* 286:445 */       outputFile.write(msoDrawingRecord);
/* 287:447 */       if (i < numDrawings)
/* 288:    */       {
/* 289:449 */         dgo = (DrawingGroupObject)this.drawings.get(i);
/* 290:450 */         dgo.writeAdditionalRecords(outputFile);
/* 291:    */       }
/* 292:    */       else
/* 293:    */       {
/* 294:454 */         Chart chart = this.charts[(i - numDrawings)];
/* 295:455 */         ObjRecord objRecord = chart.getObjRecord();
/* 296:456 */         outputFile.write(objRecord);
/* 297:457 */         outputFile.write(chart);
/* 298:    */       }
/* 299:    */     }
/* 300:462 */     for (Iterator i = this.drawings.iterator(); i.hasNext();)
/* 301:    */     {
/* 302:464 */       DrawingGroupObject dgo2 = (DrawingGroupObject)i.next();
/* 303:465 */       dgo2.writeTailRecords(outputFile);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   public void setCharts(Chart[] ch)
/* 308:    */   {
/* 309:476 */     this.charts = ch;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public Chart[] getCharts()
/* 313:    */   {
/* 314:486 */     return this.charts;
/* 315:    */   }
/* 316:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.drawing.SheetDrawingWriter
 * JD-Core Version:    0.7.0.1
 */