/*   1:    */ package jxl.read.biff;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import jxl.WorkbookSettings;
/*   5:    */ import jxl.biff.BuiltInName;
/*   6:    */ import jxl.biff.IntegerHelper;
/*   7:    */ import jxl.biff.RecordData;
/*   8:    */ import jxl.biff.StringHelper;
/*   9:    */ import jxl.common.Assert;
/*  10:    */ import jxl.common.Logger;
/*  11:    */ 
/*  12:    */ public class NameRecord
/*  13:    */   extends RecordData
/*  14:    */ {
/*  15: 42 */   private static Logger logger = Logger.getLogger(NameRecord.class);
/*  16:    */   private String name;
/*  17:    */   private BuiltInName builtInName;
/*  18:    */   private int index;
/*  19: 63 */   private int sheetRef = 0;
/*  20:    */   private boolean isbiff8;
/*  21: 74 */   public static Biff7 biff7 = new Biff7(null);
/*  22:    */   private static final int commandMacro = 12;
/*  23:    */   private static final int builtIn = 32;
/*  24:    */   private static final int cellReference = 58;
/*  25:    */   private static final int areaReference = 59;
/*  26:    */   private static final int subExpression = 41;
/*  27:    */   private static final int union = 16;
/*  28:    */   private ArrayList ranges;
/*  29:    */   
/*  30:    */   public class NameRange
/*  31:    */   {
/*  32:    */     private int columnFirst;
/*  33:    */     private int rowFirst;
/*  34:    */     private int columnLast;
/*  35:    */     private int rowLast;
/*  36:    */     private int externalSheet;
/*  37:    */     
/*  38:    */     NameRange(int s1, int c1, int r1, int c2, int r2)
/*  39:    */     {
/*  40:127 */       this.columnFirst = c1;
/*  41:128 */       this.rowFirst = r1;
/*  42:129 */       this.columnLast = c2;
/*  43:130 */       this.rowLast = r2;
/*  44:131 */       this.externalSheet = s1;
/*  45:    */     }
/*  46:    */     
/*  47:    */     public int getFirstColumn()
/*  48:    */     {
/*  49:141 */       return this.columnFirst;
/*  50:    */     }
/*  51:    */     
/*  52:    */     public int getFirstRow()
/*  53:    */     {
/*  54:151 */       return this.rowFirst;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public int getLastColumn()
/*  58:    */     {
/*  59:161 */       return this.columnLast;
/*  60:    */     }
/*  61:    */     
/*  62:    */     public int getLastRow()
/*  63:    */     {
/*  64:171 */       return this.rowLast;
/*  65:    */     }
/*  66:    */     
/*  67:    */     public int getExternalSheet()
/*  68:    */     {
/*  69:181 */       return this.externalSheet;
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   NameRecord(Record t, WorkbookSettings ws, int ind)
/*  74:    */   {
/*  75:199 */     super(t);
/*  76:200 */     this.index = ind;
/*  77:201 */     this.isbiff8 = true;
/*  78:    */     try
/*  79:    */     {
/*  80:205 */       this.ranges = new ArrayList();
/*  81:    */       
/*  82:207 */       byte[] data = getRecord().getData();
/*  83:208 */       int option = IntegerHelper.getInt(data[0], data[1]);
/*  84:209 */       int length = data[3];
/*  85:210 */       this.sheetRef = IntegerHelper.getInt(data[8], data[9]);
/*  86:212 */       if ((option & 0x20) != 0) {
/*  87:214 */         this.builtInName = BuiltInName.getBuiltInName(data[15]);
/*  88:    */       } else {
/*  89:218 */         this.name = StringHelper.getString(data, length, 15, ws);
/*  90:    */       }
/*  91:221 */       if ((option & 0xC) != 0) {
/*  92:224 */         return;
/*  93:    */       }
/*  94:227 */       int pos = length + 15;
/*  95:229 */       if (data[pos] == 58)
/*  96:    */       {
/*  97:231 */         int sheet = IntegerHelper.getInt(data[(pos + 1)], data[(pos + 2)]);
/*  98:232 */         int row = IntegerHelper.getInt(data[(pos + 3)], data[(pos + 4)]);
/*  99:233 */         int columnMask = IntegerHelper.getInt(data[(pos + 5)], data[(pos + 6)]);
/* 100:234 */         int column = columnMask & 0xFF;
/* 101:    */         
/* 102:    */ 
/* 103:237 */         Assert.verify((columnMask & 0xC0000) == 0);
/* 104:    */         
/* 105:239 */         NameRange r = new NameRange(sheet, column, row, column, row);
/* 106:240 */         this.ranges.add(r);
/* 107:    */       }
/* 108:242 */       else if (data[pos] == 59)
/* 109:    */       {
/* 110:244 */         int sheet1 = 0;
/* 111:245 */         int r1 = 0;
/* 112:246 */         int columnMask = 0;
/* 113:247 */         int c1 = 0;
/* 114:248 */         int r2 = 0;
/* 115:249 */         int c2 = 0;
/* 116:250 */         NameRange range = null;
/* 117:252 */         while (pos < data.length)
/* 118:    */         {
/* 119:254 */           sheet1 = IntegerHelper.getInt(data[(pos + 1)], data[(pos + 2)]);
/* 120:255 */           r1 = IntegerHelper.getInt(data[(pos + 3)], data[(pos + 4)]);
/* 121:256 */           r2 = IntegerHelper.getInt(data[(pos + 5)], data[(pos + 6)]);
/* 122:    */           
/* 123:258 */           columnMask = IntegerHelper.getInt(data[(pos + 7)], data[(pos + 8)]);
/* 124:259 */           c1 = columnMask & 0xFF;
/* 125:    */           
/* 126:    */ 
/* 127:262 */           Assert.verify((columnMask & 0xC0000) == 0);
/* 128:    */           
/* 129:264 */           columnMask = IntegerHelper.getInt(data[(pos + 9)], data[(pos + 10)]);
/* 130:265 */           c2 = columnMask & 0xFF;
/* 131:    */           
/* 132:    */ 
/* 133:268 */           Assert.verify((columnMask & 0xC0000) == 0);
/* 134:    */           
/* 135:270 */           range = new NameRange(sheet1, c1, r1, c2, r2);
/* 136:271 */           this.ranges.add(range);
/* 137:    */           
/* 138:273 */           pos += 11;
/* 139:    */         }
/* 140:    */       }
/* 141:276 */       else if (data[pos] == 41)
/* 142:    */       {
/* 143:278 */         int sheet1 = 0;
/* 144:279 */         int r1 = 0;
/* 145:280 */         int columnMask = 0;
/* 146:281 */         int c1 = 0;
/* 147:282 */         int r2 = 0;
/* 148:283 */         int c2 = 0;
/* 149:284 */         NameRange range = null;
/* 150:287 */         if ((pos < data.length) && (data[pos] != 58) && (data[pos] != 59)) {
/* 151:291 */           if (data[pos] == 41) {
/* 152:293 */             pos += 3;
/* 153:295 */           } else if (data[pos] == 16) {
/* 154:297 */             pos++;
/* 155:    */           }
/* 156:    */         }
/* 157:301 */         while (pos < data.length)
/* 158:    */         {
/* 159:303 */           sheet1 = IntegerHelper.getInt(data[(pos + 1)], data[(pos + 2)]);
/* 160:304 */           r1 = IntegerHelper.getInt(data[(pos + 3)], data[(pos + 4)]);
/* 161:305 */           r2 = IntegerHelper.getInt(data[(pos + 5)], data[(pos + 6)]);
/* 162:    */           
/* 163:307 */           columnMask = IntegerHelper.getInt(data[(pos + 7)], data[(pos + 8)]);
/* 164:308 */           c1 = columnMask & 0xFF;
/* 165:    */           
/* 166:    */ 
/* 167:311 */           Assert.verify((columnMask & 0xC0000) == 0);
/* 168:    */           
/* 169:313 */           columnMask = IntegerHelper.getInt(data[(pos + 9)], data[(pos + 10)]);
/* 170:314 */           c2 = columnMask & 0xFF;
/* 171:    */           
/* 172:    */ 
/* 173:317 */           Assert.verify((columnMask & 0xC0000) == 0);
/* 174:    */           
/* 175:319 */           range = new NameRange(sheet1, c1, r1, c2, r2);
/* 176:320 */           this.ranges.add(range);
/* 177:    */           
/* 178:322 */           pos += 11;
/* 179:325 */           if ((pos < data.length) && (data[pos] != 58) && (data[pos] != 59)) {
/* 180:329 */             if (data[pos] == 41) {
/* 181:331 */               pos += 3;
/* 182:333 */             } else if (data[pos] == 16) {
/* 183:335 */               pos++;
/* 184:    */             }
/* 185:    */           }
/* 186:    */         }
/* 187:    */       }
/* 188:    */       else
/* 189:    */       {
/* 190:342 */         String n = this.name != null ? this.name : this.builtInName.getName();
/* 191:343 */         logger.warn("Cannot read name ranges for " + n + " - setting to empty");
/* 192:    */         
/* 193:345 */         NameRange range = new NameRange(0, 0, 0, 0, 0);
/* 194:346 */         this.ranges.add(range);
/* 195:    */       }
/* 196:    */     }
/* 197:    */     catch (Throwable t1)
/* 198:    */     {
/* 199:354 */       logger.warn("Cannot read name");
/* 200:355 */       this.name = "ERROR";
/* 201:    */     }
/* 202:    */   }
/* 203:    */   
/* 204:    */   NameRecord(Record t, WorkbookSettings ws, int ind, Biff7 dummy)
/* 205:    */   {
/* 206:369 */     super(t);
/* 207:370 */     this.index = ind;
/* 208:371 */     this.isbiff8 = false;
/* 209:    */     try
/* 210:    */     {
/* 211:375 */       this.ranges = new ArrayList();
/* 212:376 */       byte[] data = getRecord().getData();
/* 213:377 */       int length = data[3];
/* 214:378 */       this.sheetRef = IntegerHelper.getInt(data[8], data[9]);
/* 215:379 */       this.name = StringHelper.getString(data, length, 14, ws);
/* 216:    */       
/* 217:381 */       int pos = length + 14;
/* 218:383 */       if (pos >= data.length) {
/* 219:386 */         return;
/* 220:    */       }
/* 221:389 */       if (data[pos] == 58)
/* 222:    */       {
/* 223:391 */         int sheet = IntegerHelper.getInt(data[(pos + 11)], data[(pos + 12)]);
/* 224:392 */         int row = IntegerHelper.getInt(data[(pos + 15)], data[(pos + 16)]);
/* 225:393 */         int column = data[(pos + 17)];
/* 226:    */         
/* 227:395 */         NameRange r = new NameRange(sheet, column, row, column, row);
/* 228:396 */         this.ranges.add(r);
/* 229:    */       }
/* 230:398 */       else if (data[pos] == 59)
/* 231:    */       {
/* 232:400 */         int sheet1 = 0;
/* 233:401 */         int r1 = 0;
/* 234:402 */         int c1 = 0;
/* 235:403 */         int r2 = 0;
/* 236:404 */         int c2 = 0;
/* 237:405 */         NameRange range = null;
/* 238:407 */         while (pos < data.length)
/* 239:    */         {
/* 240:409 */           sheet1 = IntegerHelper.getInt(data[(pos + 11)], data[(pos + 12)]);
/* 241:410 */           r1 = IntegerHelper.getInt(data[(pos + 15)], data[(pos + 16)]);
/* 242:411 */           r2 = IntegerHelper.getInt(data[(pos + 17)], data[(pos + 18)]);
/* 243:    */           
/* 244:413 */           c1 = data[(pos + 19)];
/* 245:414 */           c2 = data[(pos + 20)];
/* 246:    */           
/* 247:416 */           range = new NameRange(sheet1, c1, r1, c2, r2);
/* 248:417 */           this.ranges.add(range);
/* 249:    */           
/* 250:419 */           pos += 21;
/* 251:    */         }
/* 252:    */       }
/* 253:422 */       else if (data[pos] == 41)
/* 254:    */       {
/* 255:424 */         int sheet1 = 0;
/* 256:425 */         int sheet2 = 0;
/* 257:426 */         int r1 = 0;
/* 258:427 */         int c1 = 0;
/* 259:428 */         int r2 = 0;
/* 260:429 */         int c2 = 0;
/* 261:430 */         NameRange range = null;
/* 262:433 */         if ((pos < data.length) && (data[pos] != 58) && (data[pos] != 59)) {
/* 263:437 */           if (data[pos] == 41) {
/* 264:439 */             pos += 3;
/* 265:441 */           } else if (data[pos] == 16) {
/* 266:443 */             pos++;
/* 267:    */           }
/* 268:    */         }
/* 269:447 */         while (pos < data.length)
/* 270:    */         {
/* 271:449 */           sheet1 = IntegerHelper.getInt(data[(pos + 11)], data[(pos + 12)]);
/* 272:450 */           r1 = IntegerHelper.getInt(data[(pos + 15)], data[(pos + 16)]);
/* 273:451 */           r2 = IntegerHelper.getInt(data[(pos + 17)], data[(pos + 18)]);
/* 274:    */           
/* 275:453 */           c1 = data[(pos + 19)];
/* 276:454 */           c2 = data[(pos + 20)];
/* 277:    */           
/* 278:456 */           range = new NameRange(sheet1, c1, r1, c2, r2);
/* 279:457 */           this.ranges.add(range);
/* 280:    */           
/* 281:459 */           pos += 21;
/* 282:462 */           if ((pos < data.length) && (data[pos] != 58) && (data[pos] != 59)) {
/* 283:466 */             if (data[pos] == 41) {
/* 284:468 */               pos += 3;
/* 285:470 */             } else if (data[pos] == 16) {
/* 286:472 */               pos++;
/* 287:    */             }
/* 288:    */           }
/* 289:    */         }
/* 290:    */       }
/* 291:    */     }
/* 292:    */     catch (Throwable t1)
/* 293:    */     {
/* 294:483 */       logger.warn("Cannot read name.");
/* 295:484 */       this.name = "ERROR";
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   public String getName()
/* 300:    */   {
/* 301:495 */     return this.name;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public BuiltInName getBuiltInName()
/* 305:    */   {
/* 306:505 */     return this.builtInName;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public NameRange[] getRanges()
/* 310:    */   {
/* 311:516 */     NameRange[] nr = new NameRange[this.ranges.size()];
/* 312:517 */     return (NameRange[])this.ranges.toArray(nr);
/* 313:    */   }
/* 314:    */   
/* 315:    */   int getIndex()
/* 316:    */   {
/* 317:527 */     return this.index;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public int getSheetRef()
/* 321:    */   {
/* 322:538 */     return this.sheetRef;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setSheetRef(int i)
/* 326:    */   {
/* 327:547 */     this.sheetRef = i;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public byte[] getData()
/* 331:    */   {
/* 332:557 */     return getRecord().getData();
/* 333:    */   }
/* 334:    */   
/* 335:    */   public boolean isBiff8()
/* 336:    */   {
/* 337:567 */     return this.isbiff8;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean isGlobal()
/* 341:    */   {
/* 342:577 */     return this.sheetRef == 0;
/* 343:    */   }
/* 344:    */   
/* 345:    */   private static class Biff7 {}
/* 346:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.read.biff.NameRecord
 * JD-Core Version:    0.7.0.1
 */