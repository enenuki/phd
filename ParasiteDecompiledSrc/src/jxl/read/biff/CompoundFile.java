/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import jxl.WorkbookSettings;
/*   6:    */ import jxl.biff.BaseCompoundFile;
/*   7:    */ import jxl.biff.BaseCompoundFile.PropertyStorage;
/*   8:    */ import jxl.biff.IntegerHelper;
/*   9:    */ import jxl.common.Logger;
/*  10:    */ 
/*  11:    */ public final class CompoundFile
/*  12:    */   extends BaseCompoundFile
/*  13:    */ {
/*  14: 40 */   private static Logger logger = Logger.getLogger(CompoundFile.class);
/*  15:    */   private byte[] data;
/*  16:    */   private int numBigBlockDepotBlocks;
/*  17:    */   private int sbdStartBlock;
/*  18:    */   private int rootStartBlock;
/*  19:    */   private int extensionBlock;
/*  20:    */   private int numExtensionBlocks;
/*  21:    */   private byte[] rootEntry;
/*  22:    */   private int[] bigBlockChain;
/*  23:    */   private int[] smallBlockChain;
/*  24:    */   private int[] bigBlockDepotBlocks;
/*  25:    */   private ArrayList propertySets;
/*  26:    */   private WorkbookSettings settings;
/*  27:    */   private BaseCompoundFile.PropertyStorage rootEntryPropertyStorage;
/*  28:    */   
/*  29:    */   public CompoundFile(byte[] d, WorkbookSettings ws)
/*  30:    */     throws BiffException
/*  31:    */   {
/*  32:108 */     this.data = d;
/*  33:109 */     this.settings = ws;
/*  34:112 */     for (int i = 0; i < IDENTIFIER.length; i++) {
/*  35:114 */       if (this.data[i] != IDENTIFIER[i]) {
/*  36:116 */         throw new BiffException(BiffException.unrecognizedOLEFile);
/*  37:    */       }
/*  38:    */     }
/*  39:120 */     this.propertySets = new ArrayList();
/*  40:121 */     this.numBigBlockDepotBlocks = IntegerHelper.getInt(this.data[44], this.data[45], this.data[46], this.data[47]);
/*  41:    */     
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:126 */     this.sbdStartBlock = IntegerHelper.getInt(this.data[60], this.data[61], this.data[62], this.data[63]);
/*  46:    */     
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:131 */     this.rootStartBlock = IntegerHelper.getInt(this.data[48], this.data[49], this.data[50], this.data[51]);
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:136 */     this.extensionBlock = IntegerHelper.getInt(this.data[68], this.data[69], this.data[70], this.data[71]);
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:141 */     this.numExtensionBlocks = IntegerHelper.getInt(this.data[72], this.data[73], this.data[74], this.data[75]);
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:147 */     this.bigBlockDepotBlocks = new int[this.numBigBlockDepotBlocks];
/*  67:    */     
/*  68:149 */     int pos = 76;
/*  69:    */     
/*  70:151 */     int bbdBlocks = this.numBigBlockDepotBlocks;
/*  71:153 */     if (this.numExtensionBlocks != 0) {
/*  72:155 */       bbdBlocks = 109;
/*  73:    */     }
/*  74:158 */     for (int i = 0; i < bbdBlocks; i++)
/*  75:    */     {
/*  76:160 */       this.bigBlockDepotBlocks[i] = IntegerHelper.getInt(d[pos], d[(pos + 1)], d[(pos + 2)], d[(pos + 3)]);
/*  77:    */       
/*  78:162 */       pos += 4;
/*  79:    */     }
/*  80:165 */     for (int j = 0; j < this.numExtensionBlocks; j++)
/*  81:    */     {
/*  82:167 */       pos = (this.extensionBlock + 1) * 512;
/*  83:168 */       int blocksToRead = Math.min(this.numBigBlockDepotBlocks - bbdBlocks, 127);
/*  84:171 */       for (int i = bbdBlocks; i < bbdBlocks + blocksToRead; i++)
/*  85:    */       {
/*  86:173 */         this.bigBlockDepotBlocks[i] = IntegerHelper.getInt(d[pos], d[(pos + 1)], d[(pos + 2)], d[(pos + 3)]);
/*  87:    */         
/*  88:175 */         pos += 4;
/*  89:    */       }
/*  90:178 */       bbdBlocks += blocksToRead;
/*  91:179 */       if (bbdBlocks < this.numBigBlockDepotBlocks) {
/*  92:181 */         this.extensionBlock = IntegerHelper.getInt(d[pos], d[(pos + 1)], d[(pos + 2)], d[(pos + 3)]);
/*  93:    */       }
/*  94:    */     }
/*  95:186 */     readBigBlockDepot();
/*  96:187 */     readSmallBlockDepot();
/*  97:    */     
/*  98:189 */     this.rootEntry = readData(this.rootStartBlock);
/*  99:190 */     readPropertySets();
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void readBigBlockDepot()
/* 103:    */   {
/* 104:198 */     int pos = 0;
/* 105:199 */     int index = 0;
/* 106:200 */     this.bigBlockChain = new int[this.numBigBlockDepotBlocks * 512 / 4];
/* 107:202 */     for (int i = 0; i < this.numBigBlockDepotBlocks; i++)
/* 108:    */     {
/* 109:204 */       pos = (this.bigBlockDepotBlocks[i] + 1) * 512;
/* 110:206 */       for (int j = 0; j < 128; j++)
/* 111:    */       {
/* 112:208 */         this.bigBlockChain[index] = IntegerHelper.getInt(this.data[pos], this.data[(pos + 1)], this.data[(pos + 2)], this.data[(pos + 3)]);
/* 113:    */         
/* 114:210 */         pos += 4;
/* 115:211 */         index++;
/* 116:    */       }
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   private void readSmallBlockDepot()
/* 121:    */     throws BiffException
/* 122:    */   {
/* 123:221 */     int pos = 0;
/* 124:222 */     int index = 0;
/* 125:223 */     int sbdBlock = this.sbdStartBlock;
/* 126:224 */     this.smallBlockChain = new int[0];
/* 127:228 */     if (sbdBlock == -1)
/* 128:    */     {
/* 129:230 */       logger.warn("invalid small block depot number");
/* 130:231 */       return;
/* 131:    */     }
/* 132:234 */     for (int blockCount = 0; (blockCount <= this.bigBlockChain.length) && (sbdBlock != -2); blockCount++)
/* 133:    */     {
/* 134:238 */       int[] oldChain = this.smallBlockChain;
/* 135:239 */       this.smallBlockChain = new int[this.smallBlockChain.length + 128];
/* 136:240 */       System.arraycopy(oldChain, 0, this.smallBlockChain, 0, oldChain.length);
/* 137:    */       
/* 138:242 */       pos = (sbdBlock + 1) * 512;
/* 139:244 */       for (int j = 0; j < 128; j++)
/* 140:    */       {
/* 141:246 */         this.smallBlockChain[index] = IntegerHelper.getInt(this.data[pos], this.data[(pos + 1)], this.data[(pos + 2)], this.data[(pos + 3)]);
/* 142:    */         
/* 143:248 */         pos += 4;
/* 144:249 */         index++;
/* 145:    */       }
/* 146:252 */       sbdBlock = this.bigBlockChain[sbdBlock];
/* 147:    */     }
/* 148:255 */     if (blockCount > this.bigBlockChain.length) {
/* 149:259 */       throw new BiffException(BiffException.corruptFileFormat);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void readPropertySets()
/* 154:    */   {
/* 155:268 */     int offset = 0;
/* 156:269 */     byte[] d = null;
/* 157:271 */     while (offset < this.rootEntry.length)
/* 158:    */     {
/* 159:273 */       d = new byte[''];
/* 160:274 */       System.arraycopy(this.rootEntry, offset, d, 0, d.length);
/* 161:275 */       BaseCompoundFile.PropertyStorage ps = new BaseCompoundFile.PropertyStorage(this, d);
/* 162:279 */       if ((ps.name == null) || (ps.name.length() == 0)) {
/* 163:281 */         if (ps.type == 5)
/* 164:    */         {
/* 165:283 */           ps.name = "Root Entry";
/* 166:284 */           logger.warn("Property storage name for " + ps.type + " is empty - setting to " + "Root Entry");
/* 167:    */         }
/* 168:289 */         else if (ps.size != 0)
/* 169:    */         {
/* 170:291 */           logger.warn("Property storage type " + ps.type + " is non-empty and has no associated name");
/* 171:    */         }
/* 172:    */       }
/* 173:296 */       this.propertySets.add(ps);
/* 174:297 */       if (ps.name.equalsIgnoreCase("Root Entry")) {
/* 175:299 */         this.rootEntryPropertyStorage = ps;
/* 176:    */       }
/* 177:301 */       offset += 128;
/* 178:    */     }
/* 179:304 */     if (this.rootEntryPropertyStorage == null) {
/* 180:306 */       this.rootEntryPropertyStorage = ((BaseCompoundFile.PropertyStorage)this.propertySets.get(0));
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public byte[] getStream(String streamName)
/* 185:    */     throws BiffException
/* 186:    */   {
/* 187:319 */     BaseCompoundFile.PropertyStorage ps = findPropertyStorage(streamName, this.rootEntryPropertyStorage);
/* 188:324 */     if (ps == null) {
/* 189:326 */       ps = getPropertyStorage(streamName);
/* 190:    */     }
/* 191:329 */     if ((ps.size >= 4096) || (streamName.equalsIgnoreCase("Root Entry"))) {
/* 192:332 */       return getBigBlockStream(ps);
/* 193:    */     }
/* 194:336 */     return getSmallBlockStream(ps);
/* 195:    */   }
/* 196:    */   
/* 197:    */   public byte[] getStream(int psIndex)
/* 198:    */     throws BiffException
/* 199:    */   {
/* 200:350 */     BaseCompoundFile.PropertyStorage ps = getPropertyStorage(psIndex);
/* 201:352 */     if ((ps.size >= 4096) || (ps.name.equalsIgnoreCase("Root Entry"))) {
/* 202:355 */       return getBigBlockStream(ps);
/* 203:    */     }
/* 204:359 */     return getSmallBlockStream(ps);
/* 205:    */   }
/* 206:    */   
/* 207:    */   public BaseCompoundFile.PropertyStorage findPropertyStorage(String name)
/* 208:    */   {
/* 209:371 */     return findPropertyStorage(name, this.rootEntryPropertyStorage);
/* 210:    */   }
/* 211:    */   
/* 212:    */   private BaseCompoundFile.PropertyStorage findPropertyStorage(String name, BaseCompoundFile.PropertyStorage base)
/* 213:    */   {
/* 214:381 */     if (base.child == -1) {
/* 215:383 */       return null;
/* 216:    */     }
/* 217:387 */     BaseCompoundFile.PropertyStorage child = getPropertyStorage(base.child);
/* 218:388 */     if (child.name.equalsIgnoreCase(name)) {
/* 219:390 */       return child;
/* 220:    */     }
/* 221:394 */     BaseCompoundFile.PropertyStorage prev = child;
/* 222:395 */     while (prev.previous != -1)
/* 223:    */     {
/* 224:397 */       prev = getPropertyStorage(prev.previous);
/* 225:398 */       if (prev.name.equalsIgnoreCase(name)) {
/* 226:400 */         return prev;
/* 227:    */       }
/* 228:    */     }
/* 229:405 */     BaseCompoundFile.PropertyStorage next = child;
/* 230:406 */     while (next.next != -1)
/* 231:    */     {
/* 232:408 */       next = getPropertyStorage(next.next);
/* 233:409 */       if (next.name.equalsIgnoreCase(name)) {
/* 234:411 */         return next;
/* 235:    */       }
/* 236:    */     }
/* 237:415 */     return findPropertyStorage(name, child);
/* 238:    */   }
/* 239:    */   
/* 240:    */   /**
/* 241:    */    * @deprecated
/* 242:    */    */
/* 243:    */   private BaseCompoundFile.PropertyStorage getPropertyStorage(String name)
/* 244:    */     throws BiffException
/* 245:    */   {
/* 246:429 */     Iterator i = this.propertySets.iterator();
/* 247:430 */     boolean found = false;
/* 248:431 */     boolean multiple = false;
/* 249:432 */     BaseCompoundFile.PropertyStorage ps = null;
/* 250:433 */     while (i.hasNext())
/* 251:    */     {
/* 252:435 */       BaseCompoundFile.PropertyStorage ps2 = (BaseCompoundFile.PropertyStorage)i.next();
/* 253:436 */       if (ps2.name.equalsIgnoreCase(name))
/* 254:    */       {
/* 255:438 */         multiple = found == true;
/* 256:439 */         found = true;
/* 257:440 */         ps = ps2;
/* 258:    */       }
/* 259:    */     }
/* 260:444 */     if (multiple) {
/* 261:446 */       logger.warn("found multiple copies of property set " + name);
/* 262:    */     }
/* 263:449 */     if (!found) {
/* 264:451 */       throw new BiffException(BiffException.streamNotFound);
/* 265:    */     }
/* 266:454 */     return ps;
/* 267:    */   }
/* 268:    */   
/* 269:    */   private BaseCompoundFile.PropertyStorage getPropertyStorage(int index)
/* 270:    */   {
/* 271:464 */     return (BaseCompoundFile.PropertyStorage)this.propertySets.get(index);
/* 272:    */   }
/* 273:    */   
/* 274:    */   private byte[] getBigBlockStream(BaseCompoundFile.PropertyStorage ps)
/* 275:    */   {
/* 276:475 */     int numBlocks = ps.size / 512;
/* 277:476 */     if (ps.size % 512 != 0) {
/* 278:478 */       numBlocks++;
/* 279:    */     }
/* 280:481 */     byte[] streamData = new byte[numBlocks * 512];
/* 281:    */     
/* 282:483 */     int block = ps.startBlock;
/* 283:    */     
/* 284:485 */     int count = 0;
/* 285:486 */     int pos = 0;
/* 286:487 */     while ((block != -2) && (count < numBlocks))
/* 287:    */     {
/* 288:489 */       pos = (block + 1) * 512;
/* 289:490 */       System.arraycopy(this.data, pos, streamData, count * 512, 512);
/* 290:    */       
/* 291:492 */       count++;
/* 292:493 */       block = this.bigBlockChain[block];
/* 293:    */     }
/* 294:496 */     if ((block != -2) && (count == numBlocks)) {
/* 295:498 */       logger.warn("Property storage size inconsistent with block chain.");
/* 296:    */     }
/* 297:501 */     return streamData;
/* 298:    */   }
/* 299:    */   
/* 300:    */   private byte[] getSmallBlockStream(BaseCompoundFile.PropertyStorage ps)
/* 301:    */     throws BiffException
/* 302:    */   {
/* 303:513 */     byte[] rootdata = readData(this.rootEntryPropertyStorage.startBlock);
/* 304:514 */     byte[] sbdata = new byte[0];
/* 305:    */     
/* 306:516 */     int block = ps.startBlock;
/* 307:517 */     int pos = 0;
/* 308:519 */     for (int blockCount = 0; (blockCount <= this.smallBlockChain.length) && (block != -2); blockCount++)
/* 309:    */     {
/* 310:523 */       byte[] olddata = sbdata;
/* 311:524 */       sbdata = new byte[olddata.length + 64];
/* 312:525 */       System.arraycopy(olddata, 0, sbdata, 0, olddata.length);
/* 313:    */       
/* 314:    */ 
/* 315:528 */       pos = block * 64;
/* 316:529 */       System.arraycopy(rootdata, pos, sbdata, olddata.length, 64);
/* 317:    */       
/* 318:531 */       block = this.smallBlockChain[block];
/* 319:533 */       if (block == -1)
/* 320:    */       {
/* 321:535 */         logger.warn("Incorrect terminator for small block stream " + ps.name);
/* 322:536 */         block = -2;
/* 323:    */       }
/* 324:    */     }
/* 325:540 */     if (blockCount > this.smallBlockChain.length) {
/* 326:544 */       throw new BiffException(BiffException.corruptFileFormat);
/* 327:    */     }
/* 328:547 */     return sbdata;
/* 329:    */   }
/* 330:    */   
/* 331:    */   private byte[] readData(int bl)
/* 332:    */     throws BiffException
/* 333:    */   {
/* 334:559 */     int block = bl;
/* 335:560 */     int pos = 0;
/* 336:561 */     byte[] entry = new byte[0];
/* 337:563 */     for (int blockCount = 0; (blockCount <= this.bigBlockChain.length) && (block != -2); blockCount++)
/* 338:    */     {
/* 339:567 */       byte[] oldEntry = entry;
/* 340:568 */       entry = new byte[oldEntry.length + 512];
/* 341:569 */       System.arraycopy(oldEntry, 0, entry, 0, oldEntry.length);
/* 342:570 */       pos = (block + 1) * 512;
/* 343:571 */       System.arraycopy(this.data, pos, entry, oldEntry.length, 512);
/* 344:573 */       if (this.bigBlockChain[block] == block) {
/* 345:575 */         throw new BiffException(BiffException.corruptFileFormat);
/* 346:    */       }
/* 347:577 */       block = this.bigBlockChain[block];
/* 348:    */     }
/* 349:580 */     if (blockCount > this.bigBlockChain.length) {
/* 350:584 */       throw new BiffException(BiffException.corruptFileFormat);
/* 351:    */     }
/* 352:587 */     return entry;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public int getNumberOfPropertySets()
/* 356:    */   {
/* 357:596 */     return this.propertySets.size();
/* 358:    */   }
/* 359:    */   
/* 360:    */   public BaseCompoundFile.PropertyStorage getPropertySet(int index)
/* 361:    */   {
/* 362:608 */     return getPropertyStorage(index);
/* 363:    */   }
/* 364:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.CompoundFile
 * JD-Core Version:    0.7.0.1
 */