/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.text.DateFormat;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import jxl.common.Assert;
/*  10:    */ import jxl.common.Logger;
/*  11:    */ import jxl.format.Colour;
/*  12:    */ import jxl.format.RGB;
/*  13:    */ import jxl.write.biff.File;
/*  14:    */ 
/*  15:    */ public class FormattingRecords
/*  16:    */ {
/*  17: 44 */   private static Logger logger = Logger.getLogger(FormattingRecords.class);
/*  18:    */   private HashMap formats;
/*  19:    */   private ArrayList formatsList;
/*  20:    */   private ArrayList xfRecords;
/*  21:    */   private int nextCustomIndexNumber;
/*  22:    */   private Fonts fonts;
/*  23:    */   private PaletteRecord palette;
/*  24:    */   private static final int customFormatStartIndex = 164;
/*  25:    */   private static final int maxFormatRecordsIndex = 441;
/*  26:    */   private static final int minXFRecords = 21;
/*  27:    */   
/*  28:    */   public FormattingRecords(Fonts f)
/*  29:    */   {
/*  30:101 */     this.xfRecords = new ArrayList(10);
/*  31:102 */     this.formats = new HashMap(10);
/*  32:103 */     this.formatsList = new ArrayList(10);
/*  33:104 */     this.fonts = f;
/*  34:105 */     this.nextCustomIndexNumber = 164;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public final void addStyle(XFRecord xf)
/*  38:    */     throws NumFormatRecordsException
/*  39:    */   {
/*  40:120 */     if (!xf.isInitialized())
/*  41:    */     {
/*  42:122 */       int pos = this.xfRecords.size();
/*  43:123 */       xf.initialize(pos, this, this.fonts);
/*  44:124 */       this.xfRecords.add(xf);
/*  45:    */     }
/*  46:131 */     else if (xf.getXFIndex() >= this.xfRecords.size())
/*  47:    */     {
/*  48:133 */       this.xfRecords.add(xf);
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final void addFormat(DisplayFormat fr)
/*  53:    */     throws NumFormatRecordsException
/*  54:    */   {
/*  55:151 */     if ((fr.isInitialized()) && (fr.getFormatIndex() >= 441))
/*  56:    */     {
/*  57:154 */       logger.warn("Format index exceeds Excel maximum - assigning custom number");
/*  58:    */       
/*  59:156 */       fr.initialize(this.nextCustomIndexNumber);
/*  60:157 */       this.nextCustomIndexNumber += 1;
/*  61:    */     }
/*  62:161 */     if (!fr.isInitialized())
/*  63:    */     {
/*  64:163 */       fr.initialize(this.nextCustomIndexNumber);
/*  65:164 */       this.nextCustomIndexNumber += 1;
/*  66:    */     }
/*  67:167 */     if (this.nextCustomIndexNumber > 441)
/*  68:    */     {
/*  69:169 */       this.nextCustomIndexNumber = 441;
/*  70:170 */       throw new NumFormatRecordsException();
/*  71:    */     }
/*  72:173 */     if (fr.getFormatIndex() >= this.nextCustomIndexNumber) {
/*  73:175 */       this.nextCustomIndexNumber = (fr.getFormatIndex() + 1);
/*  74:    */     }
/*  75:178 */     if (!fr.isBuiltIn())
/*  76:    */     {
/*  77:180 */       this.formatsList.add(fr);
/*  78:181 */       this.formats.put(new Integer(fr.getFormatIndex()), fr);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final boolean isDate(int pos)
/*  83:    */   {
/*  84:195 */     XFRecord xfr = (XFRecord)this.xfRecords.get(pos);
/*  85:197 */     if (xfr.isDate()) {
/*  86:199 */       return true;
/*  87:    */     }
/*  88:202 */     FormatRecord fr = (FormatRecord)this.formats.get(new Integer(xfr.getFormatRecord()));
/*  89:    */     
/*  90:    */ 
/*  91:205 */     return fr == null ? false : fr.isDate();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public final DateFormat getDateFormat(int pos)
/*  95:    */   {
/*  96:217 */     XFRecord xfr = (XFRecord)this.xfRecords.get(pos);
/*  97:219 */     if (xfr.isDate()) {
/*  98:221 */       return xfr.getDateFormat();
/*  99:    */     }
/* 100:224 */     FormatRecord fr = (FormatRecord)this.formats.get(new Integer(xfr.getFormatRecord()));
/* 101:227 */     if (fr == null) {
/* 102:229 */       return null;
/* 103:    */     }
/* 104:232 */     return fr.isDate() ? fr.getDateFormat() : null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final NumberFormat getNumberFormat(int pos)
/* 108:    */   {
/* 109:244 */     XFRecord xfr = (XFRecord)this.xfRecords.get(pos);
/* 110:246 */     if (xfr.isNumber()) {
/* 111:248 */       return xfr.getNumberFormat();
/* 112:    */     }
/* 113:251 */     FormatRecord fr = (FormatRecord)this.formats.get(new Integer(xfr.getFormatRecord()));
/* 114:254 */     if (fr == null) {
/* 115:256 */       return null;
/* 116:    */     }
/* 117:259 */     return fr.isNumber() ? fr.getNumberFormat() : null;
/* 118:    */   }
/* 119:    */   
/* 120:    */   FormatRecord getFormatRecord(int index)
/* 121:    */   {
/* 122:270 */     return (FormatRecord)this.formats.get(new Integer(index));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void write(File outputFile)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:282 */     Iterator i = this.formatsList.iterator();
/* 129:283 */     FormatRecord fr = null;
/* 130:284 */     while (i.hasNext())
/* 131:    */     {
/* 132:286 */       fr = (FormatRecord)i.next();
/* 133:287 */       outputFile.write(fr);
/* 134:    */     }
/* 135:291 */     i = this.xfRecords.iterator();
/* 136:292 */     XFRecord xfr = null;
/* 137:293 */     while (i.hasNext())
/* 138:    */     {
/* 139:295 */       xfr = (XFRecord)i.next();
/* 140:296 */       outputFile.write(xfr);
/* 141:    */     }
/* 142:300 */     BuiltInStyle style = new BuiltInStyle(16, 3);
/* 143:301 */     outputFile.write(style);
/* 144:    */     
/* 145:303 */     style = new BuiltInStyle(17, 6);
/* 146:304 */     outputFile.write(style);
/* 147:    */     
/* 148:306 */     style = new BuiltInStyle(18, 4);
/* 149:307 */     outputFile.write(style);
/* 150:    */     
/* 151:309 */     style = new BuiltInStyle(19, 7);
/* 152:310 */     outputFile.write(style);
/* 153:    */     
/* 154:312 */     style = new BuiltInStyle(0, 0);
/* 155:313 */     outputFile.write(style);
/* 156:    */     
/* 157:315 */     style = new BuiltInStyle(20, 5);
/* 158:316 */     outputFile.write(style);
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected final Fonts getFonts()
/* 162:    */   {
/* 163:326 */     return this.fonts;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public final XFRecord getXFRecord(int index)
/* 167:    */   {
/* 168:338 */     return (XFRecord)this.xfRecords.get(index);
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected final int getNumberOfFormatRecords()
/* 172:    */   {
/* 173:350 */     return this.formatsList.size();
/* 174:    */   }
/* 175:    */   
/* 176:    */   public IndexMapping rationalizeFonts()
/* 177:    */   {
/* 178:360 */     return this.fonts.rationalize();
/* 179:    */   }
/* 180:    */   
/* 181:    */   public IndexMapping rationalize(IndexMapping fontMapping, IndexMapping formatMapping)
/* 182:    */   {
/* 183:378 */     XFRecord xfr = null;
/* 184:379 */     for (Iterator it = this.xfRecords.iterator(); it.hasNext();)
/* 185:    */     {
/* 186:381 */       xfr = (XFRecord)it.next();
/* 187:383 */       if (xfr.getFormatRecord() >= 164) {
/* 188:385 */         xfr.setFormatIndex(formatMapping.getNewIndex(xfr.getFormatRecord()));
/* 189:    */       }
/* 190:388 */       xfr.setFontIndex(fontMapping.getNewIndex(xfr.getFontIndex()));
/* 191:    */     }
/* 192:391 */     ArrayList newrecords = new ArrayList(21);
/* 193:392 */     IndexMapping mapping = new IndexMapping(this.xfRecords.size());
/* 194:393 */     int numremoved = 0;
/* 195:    */     
/* 196:395 */     int numXFRecords = Math.min(21, this.xfRecords.size());
/* 197:397 */     for (int i = 0; i < numXFRecords; i++)
/* 198:    */     {
/* 199:399 */       newrecords.add(this.xfRecords.get(i));
/* 200:400 */       mapping.setMapping(i, i);
/* 201:    */     }
/* 202:403 */     if (numXFRecords < 21)
/* 203:    */     {
/* 204:405 */       logger.warn("There are less than the expected minimum number of XF records");
/* 205:    */       
/* 206:407 */       return mapping;
/* 207:    */     }
/* 208:411 */     for (int i = 21; i < this.xfRecords.size(); i++)
/* 209:    */     {
/* 210:413 */       XFRecord xf = (XFRecord)this.xfRecords.get(i);
/* 211:    */       
/* 212:    */ 
/* 213:416 */       boolean duplicate = false;
/* 214:417 */       Iterator it = newrecords.iterator();
/* 215:418 */       while ((it.hasNext()) && (!duplicate))
/* 216:    */       {
/* 217:420 */         XFRecord xf2 = (XFRecord)it.next();
/* 218:421 */         if (xf2.equals(xf))
/* 219:    */         {
/* 220:423 */           duplicate = true;
/* 221:424 */           mapping.setMapping(i, mapping.getNewIndex(xf2.getXFIndex()));
/* 222:425 */           numremoved++;
/* 223:    */         }
/* 224:    */       }
/* 225:430 */       if (!duplicate)
/* 226:    */       {
/* 227:432 */         newrecords.add(xf);
/* 228:433 */         mapping.setMapping(i, i - numremoved);
/* 229:    */       }
/* 230:    */     }
/* 231:440 */     for (Iterator i = this.xfRecords.iterator(); i.hasNext();)
/* 232:    */     {
/* 233:442 */       XFRecord xf = (XFRecord)i.next();
/* 234:443 */       xf.rationalize(mapping);
/* 235:    */     }
/* 236:447 */     this.xfRecords = newrecords;
/* 237:    */     
/* 238:449 */     return mapping;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public IndexMapping rationalizeDisplayFormats()
/* 242:    */   {
/* 243:462 */     ArrayList newformats = new ArrayList();
/* 244:463 */     int numremoved = 0;
/* 245:464 */     IndexMapping mapping = new IndexMapping(this.nextCustomIndexNumber);
/* 246:    */     
/* 247:    */ 
/* 248:467 */     Iterator i = this.formatsList.iterator();
/* 249:468 */     DisplayFormat df = null;
/* 250:469 */     DisplayFormat df2 = null;
/* 251:470 */     boolean duplicate = false;
/* 252:471 */     while (i.hasNext())
/* 253:    */     {
/* 254:473 */       df = (DisplayFormat)i.next();
/* 255:    */       
/* 256:475 */       Assert.verify(!df.isBuiltIn());
/* 257:    */       
/* 258:    */ 
/* 259:478 */       Iterator i2 = newformats.iterator();
/* 260:479 */       duplicate = false;
/* 261:480 */       while ((i2.hasNext()) && (!duplicate))
/* 262:    */       {
/* 263:482 */         df2 = (DisplayFormat)i2.next();
/* 264:483 */         if (df2.equals(df))
/* 265:    */         {
/* 266:485 */           duplicate = true;
/* 267:486 */           mapping.setMapping(df.getFormatIndex(), mapping.getNewIndex(df2.getFormatIndex()));
/* 268:    */           
/* 269:488 */           numremoved++;
/* 270:    */         }
/* 271:    */       }
/* 272:493 */       if (!duplicate)
/* 273:    */       {
/* 274:495 */         newformats.add(df);
/* 275:496 */         int indexnum = df.getFormatIndex() - numremoved;
/* 276:497 */         if (indexnum > 441)
/* 277:    */         {
/* 278:499 */           logger.warn("Too many number formats - using default format.");
/* 279:500 */           indexnum = 0;
/* 280:    */         }
/* 281:502 */         mapping.setMapping(df.getFormatIndex(), df.getFormatIndex() - numremoved);
/* 282:    */       }
/* 283:    */     }
/* 284:508 */     this.formatsList = newformats;
/* 285:    */     
/* 286:    */ 
/* 287:511 */     i = this.formatsList.iterator();
/* 288:513 */     while (i.hasNext())
/* 289:    */     {
/* 290:515 */       df = (DisplayFormat)i.next();
/* 291:516 */       df.initialize(mapping.getNewIndex(df.getFormatIndex()));
/* 292:    */     }
/* 293:519 */     return mapping;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public PaletteRecord getPalette()
/* 297:    */   {
/* 298:529 */     return this.palette;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setPalette(PaletteRecord pr)
/* 302:    */   {
/* 303:539 */     this.palette = pr;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setColourRGB(Colour c, int r, int g, int b)
/* 307:    */   {
/* 308:552 */     if (this.palette == null) {
/* 309:554 */       this.palette = new PaletteRecord();
/* 310:    */     }
/* 311:556 */     this.palette.setColourRGB(c, r, g, b);
/* 312:    */   }
/* 313:    */   
/* 314:    */   public RGB getColourRGB(Colour c)
/* 315:    */   {
/* 316:566 */     if (this.palette == null) {
/* 317:568 */       return c.getDefaultRGB();
/* 318:    */     }
/* 319:571 */     return this.palette.getColourRGB(c);
/* 320:    */   }
/* 321:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.FormattingRecords
 * JD-Core Version:    0.7.0.1
 */