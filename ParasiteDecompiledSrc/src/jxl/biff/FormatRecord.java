/*   1:    */ package jxl.biff;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.DecimalFormat;
/*   5:    */ import java.text.NumberFormat;
/*   6:    */ import java.text.SimpleDateFormat;
/*   7:    */ import jxl.WorkbookSettings;
/*   8:    */ import jxl.common.Logger;
/*   9:    */ import jxl.read.biff.Record;
/*  10:    */ 
/*  11:    */ public class FormatRecord
/*  12:    */   extends WritableRecordData
/*  13:    */   implements DisplayFormat, jxl.format.Format
/*  14:    */ {
/*  15: 42 */   public static Logger logger = Logger.getLogger(FormatRecord.class);
/*  16:    */   private boolean initialized;
/*  17:    */   private byte[] data;
/*  18:    */   private int indexCode;
/*  19:    */   private String formatString;
/*  20:    */   private boolean date;
/*  21:    */   private boolean number;
/*  22:    */   private java.text.Format format;
/*  23: 82 */   private static String[] dateStrings = { "dd", "mm", "yy", "hh", "ss", "m/", "/d" };
/*  24: 98 */   public static final BiffType biff8 = new BiffType(null);
/*  25: 99 */   public static final BiffType biff7 = new BiffType(null);
/*  26:    */   
/*  27:    */   FormatRecord(String fmt, int refno)
/*  28:    */   {
/*  29:109 */     super(Type.FORMAT);
/*  30:110 */     this.formatString = fmt;
/*  31:111 */     this.indexCode = refno;
/*  32:112 */     this.initialized = true;
/*  33:    */   }
/*  34:    */   
/*  35:    */   protected FormatRecord()
/*  36:    */   {
/*  37:120 */     super(Type.FORMAT);
/*  38:121 */     this.initialized = false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   protected FormatRecord(FormatRecord fr)
/*  42:    */   {
/*  43:131 */     super(Type.FORMAT);
/*  44:132 */     this.initialized = false;
/*  45:    */     
/*  46:134 */     this.formatString = fr.formatString;
/*  47:135 */     this.date = fr.date;
/*  48:136 */     this.number = fr.number;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public FormatRecord(Record t, WorkbookSettings ws, BiffType biffType)
/*  52:    */   {
/*  53:150 */     super(t);
/*  54:    */     
/*  55:152 */     byte[] data = getRecord().getData();
/*  56:153 */     this.indexCode = IntegerHelper.getInt(data[0], data[1]);
/*  57:154 */     this.initialized = true;
/*  58:156 */     if (biffType == biff8)
/*  59:    */     {
/*  60:158 */       int numchars = IntegerHelper.getInt(data[2], data[3]);
/*  61:159 */       if (data[4] == 0) {
/*  62:161 */         this.formatString = StringHelper.getString(data, numchars, 5, ws);
/*  63:    */       } else {
/*  64:165 */         this.formatString = StringHelper.getUnicodeString(data, numchars, 5);
/*  65:    */       }
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:170 */       int numchars = data[2];
/*  70:171 */       byte[] chars = new byte[numchars];
/*  71:172 */       System.arraycopy(data, 3, chars, 0, chars.length);
/*  72:173 */       this.formatString = new String(chars);
/*  73:    */     }
/*  74:176 */     this.date = false;
/*  75:177 */     this.number = false;
/*  76:180 */     for (int i = 0; i < dateStrings.length; i++)
/*  77:    */     {
/*  78:182 */       String dateString = dateStrings[i];
/*  79:183 */       if ((this.formatString.indexOf(dateString) != -1) || (this.formatString.indexOf(dateString.toUpperCase()) != -1))
/*  80:    */       {
/*  81:186 */         this.date = true;
/*  82:187 */         break;
/*  83:    */       }
/*  84:    */     }
/*  85:192 */     if (!this.date) {
/*  86:194 */       if ((this.formatString.indexOf('#') != -1) || (this.formatString.indexOf('0') != -1)) {
/*  87:197 */         this.number = true;
/*  88:    */       }
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public byte[] getData()
/*  93:    */   {
/*  94:209 */     this.data = new byte[this.formatString.length() * 2 + 3 + 2];
/*  95:    */     
/*  96:211 */     IntegerHelper.getTwoBytes(this.indexCode, this.data, 0);
/*  97:212 */     IntegerHelper.getTwoBytes(this.formatString.length(), this.data, 2);
/*  98:213 */     this.data[4] = 1;
/*  99:214 */     StringHelper.getUnicodeBytes(this.formatString, this.data, 5);
/* 100:    */     
/* 101:216 */     return this.data;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getFormatIndex()
/* 105:    */   {
/* 106:226 */     return this.indexCode;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean isInitialized()
/* 110:    */   {
/* 111:236 */     return this.initialized;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void initialize(int pos)
/* 115:    */   {
/* 116:248 */     this.indexCode = pos;
/* 117:249 */     this.initialized = true;
/* 118:    */   }
/* 119:    */   
/* 120:    */   protected final String replace(String input, String search, String replace)
/* 121:    */   {
/* 122:263 */     String fmtstr = input;
/* 123:264 */     int pos = fmtstr.indexOf(search);
/* 124:265 */     while (pos != -1)
/* 125:    */     {
/* 126:267 */       StringBuffer tmp = new StringBuffer(fmtstr.substring(0, pos));
/* 127:268 */       tmp.append(replace);
/* 128:269 */       tmp.append(fmtstr.substring(pos + search.length()));
/* 129:270 */       fmtstr = tmp.toString();
/* 130:271 */       pos = fmtstr.indexOf(search);
/* 131:    */     }
/* 132:273 */     return fmtstr;
/* 133:    */   }
/* 134:    */   
/* 135:    */   protected final void setFormatString(String s)
/* 136:    */   {
/* 137:284 */     this.formatString = s;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final boolean isDate()
/* 141:    */   {
/* 142:294 */     return this.date;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public final boolean isNumber()
/* 146:    */   {
/* 147:304 */     return this.number;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public final NumberFormat getNumberFormat()
/* 151:    */   {
/* 152:314 */     if ((this.format != null) && ((this.format instanceof NumberFormat))) {
/* 153:316 */       return (NumberFormat)this.format;
/* 154:    */     }
/* 155:    */     try
/* 156:    */     {
/* 157:321 */       String fs = this.formatString;
/* 158:    */       
/* 159:    */ 
/* 160:324 */       fs = replace(fs, "E+", "E");
/* 161:325 */       fs = replace(fs, "_)", "");
/* 162:326 */       fs = replace(fs, "_", "");
/* 163:327 */       fs = replace(fs, "[Red]", "");
/* 164:328 */       fs = replace(fs, "\\", "");
/* 165:    */       
/* 166:330 */       this.format = new DecimalFormat(fs);
/* 167:    */     }
/* 168:    */     catch (IllegalArgumentException e)
/* 169:    */     {
/* 170:336 */       this.format = new DecimalFormat("#.###");
/* 171:    */     }
/* 172:339 */     return (NumberFormat)this.format;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public final DateFormat getDateFormat()
/* 176:    */   {
/* 177:349 */     if ((this.format != null) && ((this.format instanceof DateFormat))) {
/* 178:351 */       return (DateFormat)this.format;
/* 179:    */     }
/* 180:354 */     String fmt = this.formatString;
/* 181:    */     
/* 182:    */ 
/* 183:357 */     int pos = fmt.indexOf("AM/PM");
/* 184:358 */     while (pos != -1)
/* 185:    */     {
/* 186:360 */       StringBuffer sb = new StringBuffer(fmt.substring(0, pos));
/* 187:361 */       sb.append('a');
/* 188:362 */       sb.append(fmt.substring(pos + 5));
/* 189:363 */       fmt = sb.toString();
/* 190:364 */       pos = fmt.indexOf("AM/PM");
/* 191:    */     }
/* 192:369 */     pos = fmt.indexOf("ss.0");
/* 193:370 */     while (pos != -1)
/* 194:    */     {
/* 195:372 */       StringBuffer sb = new StringBuffer(fmt.substring(0, pos));
/* 196:373 */       sb.append("ss.SSS");
/* 197:    */       
/* 198:    */ 
/* 199:376 */       pos += 4;
/* 200:377 */       while ((pos < fmt.length()) && (fmt.charAt(pos) == '0')) {
/* 201:379 */         pos++;
/* 202:    */       }
/* 203:382 */       sb.append(fmt.substring(pos));
/* 204:383 */       fmt = sb.toString();
/* 205:384 */       pos = fmt.indexOf("ss.0");
/* 206:    */     }
/* 207:389 */     StringBuffer sb = new StringBuffer();
/* 208:390 */     for (int i = 0; i < fmt.length(); i++) {
/* 209:392 */       if (fmt.charAt(i) != '\\') {
/* 210:394 */         sb.append(fmt.charAt(i));
/* 211:    */       }
/* 212:    */     }
/* 213:398 */     fmt = sb.toString();
/* 214:402 */     if (fmt.charAt(0) == '[')
/* 215:    */     {
/* 216:404 */       int end = fmt.indexOf(']');
/* 217:405 */       if (end != -1) {
/* 218:407 */         fmt = fmt.substring(end + 1);
/* 219:    */       }
/* 220:    */     }
/* 221:412 */     fmt = replace(fmt, ";@", "");
/* 222:    */     
/* 223:    */ 
/* 224:    */ 
/* 225:416 */     char[] formatBytes = fmt.toCharArray();
/* 226:418 */     for (int i = 0; i < formatBytes.length; i++) {
/* 227:420 */       if (formatBytes[i] == 'm') {
/* 228:424 */         if ((i > 0) && ((formatBytes[(i - 1)] == 'm') || (formatBytes[(i - 1)] == 'M')))
/* 229:    */         {
/* 230:426 */           formatBytes[i] = formatBytes[(i - 1)];
/* 231:    */         }
/* 232:    */         else
/* 233:    */         {
/* 234:434 */           int minuteDist = 2147483647;
/* 235:435 */           for (int j = i - 1; j > 0; j--) {
/* 236:437 */             if (formatBytes[j] == 'h')
/* 237:    */             {
/* 238:439 */               minuteDist = i - j;
/* 239:440 */               break;
/* 240:    */             }
/* 241:    */           }
/* 242:444 */           for (int j = i + 1; j < formatBytes.length; j++) {
/* 243:446 */             if (formatBytes[j] == 'h')
/* 244:    */             {
/* 245:448 */               minuteDist = Math.min(minuteDist, j - i);
/* 246:449 */               break;
/* 247:    */             }
/* 248:    */           }
/* 249:453 */           for (int j = i - 1; j > 0; j--) {
/* 250:455 */             if (formatBytes[j] == 'H')
/* 251:    */             {
/* 252:457 */               minuteDist = i - j;
/* 253:458 */               break;
/* 254:    */             }
/* 255:    */           }
/* 256:462 */           for (int j = i + 1; j < formatBytes.length; j++) {
/* 257:464 */             if (formatBytes[j] == 'H')
/* 258:    */             {
/* 259:466 */               minuteDist = Math.min(minuteDist, j - i);
/* 260:467 */               break;
/* 261:    */             }
/* 262:    */           }
/* 263:472 */           for (int j = i - 1; j > 0; j--) {
/* 264:474 */             if (formatBytes[j] == 's')
/* 265:    */             {
/* 266:476 */               minuteDist = Math.min(minuteDist, i - j);
/* 267:477 */               break;
/* 268:    */             }
/* 269:    */           }
/* 270:480 */           for (int j = i + 1; j < formatBytes.length; j++) {
/* 271:482 */             if (formatBytes[j] == 's')
/* 272:    */             {
/* 273:484 */               minuteDist = Math.min(minuteDist, j - i);
/* 274:485 */               break;
/* 275:    */             }
/* 276:    */           }
/* 277:491 */           int monthDist = 2147483647;
/* 278:492 */           for (int j = i - 1; j > 0; j--) {
/* 279:494 */             if (formatBytes[j] == 'd')
/* 280:    */             {
/* 281:496 */               monthDist = i - j;
/* 282:497 */               break;
/* 283:    */             }
/* 284:    */           }
/* 285:501 */           for (int j = i + 1; j < formatBytes.length; j++) {
/* 286:503 */             if (formatBytes[j] == 'd')
/* 287:    */             {
/* 288:505 */               monthDist = Math.min(monthDist, j - i);
/* 289:506 */               break;
/* 290:    */             }
/* 291:    */           }
/* 292:510 */           for (int j = i - 1; j > 0; j--) {
/* 293:512 */             if (formatBytes[j] == 'y')
/* 294:    */             {
/* 295:514 */               monthDist = Math.min(monthDist, i - j);
/* 296:515 */               break;
/* 297:    */             }
/* 298:    */           }
/* 299:518 */           for (int j = i + 1; j < formatBytes.length; j++) {
/* 300:520 */             if (formatBytes[j] == 'y')
/* 301:    */             {
/* 302:522 */               monthDist = Math.min(monthDist, j - i);
/* 303:523 */               break;
/* 304:    */             }
/* 305:    */           }
/* 306:527 */           if (monthDist < minuteDist)
/* 307:    */           {
/* 308:530 */             formatBytes[i] = Character.toUpperCase(formatBytes[i]);
/* 309:    */           }
/* 310:532 */           else if ((monthDist == minuteDist) && (monthDist != 2147483647))
/* 311:    */           {
/* 312:537 */             char ind = formatBytes[(i - monthDist)];
/* 313:538 */             if ((ind == 'y') || (ind == 'd')) {
/* 314:541 */               formatBytes[i] = Character.toUpperCase(formatBytes[i]);
/* 315:    */             }
/* 316:    */           }
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:    */     try
/* 321:    */     {
/* 322:550 */       this.format = new SimpleDateFormat(new String(formatBytes));
/* 323:    */     }
/* 324:    */     catch (IllegalArgumentException e)
/* 325:    */     {
/* 326:555 */       this.format = new SimpleDateFormat("dd MM yyyy hh:mm:ss");
/* 327:    */     }
/* 328:557 */     return (DateFormat)this.format;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public int getIndexCode()
/* 332:    */   {
/* 333:567 */     return this.indexCode;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public String getFormatString()
/* 337:    */   {
/* 338:577 */     return this.formatString;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public boolean isBuiltIn()
/* 342:    */   {
/* 343:587 */     return false;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public int hashCode()
/* 347:    */   {
/* 348:596 */     return this.formatString.hashCode();
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean equals(Object o)
/* 352:    */   {
/* 353:608 */     if (o == this) {
/* 354:610 */       return true;
/* 355:    */     }
/* 356:613 */     if (!(o instanceof FormatRecord)) {
/* 357:615 */       return false;
/* 358:    */     }
/* 359:618 */     FormatRecord fr = (FormatRecord)o;
/* 360:621 */     if ((this.initialized) && (fr.initialized))
/* 361:    */     {
/* 362:624 */       if ((this.date != fr.date) || (this.number != fr.number)) {
/* 363:627 */         return false;
/* 364:    */       }
/* 365:630 */       return this.formatString.equals(fr.formatString);
/* 366:    */     }
/* 367:634 */     return this.formatString.equals(fr.formatString);
/* 368:    */   }
/* 369:    */   
/* 370:    */   private static class BiffType {}
/* 371:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.FormatRecord
 * JD-Core Version:    0.7.0.1
 */