/*   1:    */ package org.apache.james.mime4j.util;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.FieldPosition;
/*   5:    */ import java.text.SimpleDateFormat;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.Locale;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Random;
/*  12:    */ import java.util.TimeZone;
/*  13:    */ import org.apache.commons.logging.Log;
/*  14:    */ import org.apache.commons.logging.LogFactory;
/*  15:    */ 
/*  16:    */ public final class MimeUtil
/*  17:    */ {
/*  18: 40 */   private static final Log log = LogFactory.getLog(MimeUtil.class);
/*  19:    */   public static final String ENC_QUOTED_PRINTABLE = "quoted-printable";
/*  20:    */   public static final String ENC_BINARY = "binary";
/*  21:    */   public static final String ENC_BASE64 = "base64";
/*  22:    */   public static final String ENC_8BIT = "8bit";
/*  23:    */   public static final String ENC_7BIT = "7bit";
/*  24:    */   public static final String MIME_HEADER_MIME_VERSION = "mime-version";
/*  25:    */   public static final String MIME_HEADER_CONTENT_ID = "content-id";
/*  26:    */   public static final String MIME_HEADER_CONTENT_DESCRIPTION = "content-description";
/*  27:    */   public static final String MIME_HEADER_CONTENT_DISPOSITION = "content-disposition";
/*  28:    */   public static final String PARAM_FILENAME = "filename";
/*  29:    */   public static final String PARAM_MODIFICATION_DATE = "modification-date";
/*  30:    */   public static final String PARAM_CREATION_DATE = "creation-date";
/*  31:    */   public static final String PARAM_READ_DATE = "read-date";
/*  32:    */   public static final String PARAM_SIZE = "size";
/*  33:    */   public static final String MIME_HEADER_LANGAUGE = "content-language";
/*  34:    */   public static final String MIME_HEADER_LOCATION = "content-location";
/*  35:    */   public static final String MIME_HEADER_MD5 = "content-md5";
/*  36:116 */   private static final Random random = new Random();
/*  37:119 */   private static int counter = 0;
/*  38:    */   
/*  39:    */   public static boolean isSameMimeType(String pType1, String pType2)
/*  40:    */   {
/*  41:130 */     return (pType1 != null) && (pType2 != null) && (pType1.equalsIgnoreCase(pType2));
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean isMessage(String pMimeType)
/*  45:    */   {
/*  46:137 */     return (pMimeType != null) && (pMimeType.equalsIgnoreCase("message/rfc822"));
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static boolean isMultipart(String pMimeType)
/*  50:    */   {
/*  51:144 */     return (pMimeType != null) && (pMimeType.toLowerCase().startsWith("multipart/"));
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static boolean isBase64Encoding(String pTransferEncoding)
/*  55:    */   {
/*  56:151 */     return "base64".equalsIgnoreCase(pTransferEncoding);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static boolean isQuotedPrintableEncoded(String pTransferEncoding)
/*  60:    */   {
/*  61:158 */     return "quoted-printable".equalsIgnoreCase(pTransferEncoding);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Map<String, String> getHeaderParams(String pValue)
/*  65:    */   {
/*  66:177 */     pValue = pValue.trim();
/*  67:    */     
/*  68:179 */     pValue = unfold(pValue);
/*  69:    */     
/*  70:181 */     Map<String, String> result = new HashMap();
/*  71:    */     String rest;
/*  72:    */     String main;
/*  73:    */     String rest;
/*  74:186 */     if (pValue.indexOf(";") == -1)
/*  75:    */     {
/*  76:187 */       String main = pValue;
/*  77:188 */       rest = null;
/*  78:    */     }
/*  79:    */     else
/*  80:    */     {
/*  81:190 */       main = pValue.substring(0, pValue.indexOf(";"));
/*  82:191 */       rest = pValue.substring(main.length() + 1);
/*  83:    */     }
/*  84:194 */     result.put("", main);
/*  85:195 */     if (rest != null)
/*  86:    */     {
/*  87:196 */       char[] chars = rest.toCharArray();
/*  88:197 */       StringBuilder paramName = new StringBuilder(64);
/*  89:198 */       StringBuilder paramValue = new StringBuilder(64);
/*  90:    */       
/*  91:200 */       byte READY_FOR_NAME = 0;
/*  92:201 */       byte IN_NAME = 1;
/*  93:202 */       byte READY_FOR_VALUE = 2;
/*  94:203 */       byte IN_VALUE = 3;
/*  95:204 */       byte IN_QUOTED_VALUE = 4;
/*  96:205 */       byte VALUE_DONE = 5;
/*  97:206 */       byte ERROR = 99;
/*  98:    */       
/*  99:208 */       byte state = 0;
/* 100:209 */       boolean escaped = false;
/* 101:210 */       for (char c : chars)
/* 102:    */       {
/* 103:    */         boolean fallThrough;
/* 104:211 */         switch (state)
/* 105:    */         {
/* 106:    */         case 99: 
/* 107:213 */           if (c == ';') {
/* 108:214 */             state = 0;
/* 109:    */           }
/* 110:    */           break;
/* 111:    */         case 0: 
/* 112:218 */           if (c == '=')
/* 113:    */           {
/* 114:219 */             log.error("Expected header param name, got '='");
/* 115:220 */             state = 99;
/* 116:    */           }
/* 117:    */           else
/* 118:    */           {
/* 119:224 */             paramName.setLength(0);
/* 120:225 */             paramValue.setLength(0);
/* 121:    */             
/* 122:227 */             state = 1;
/* 123:    */           }
/* 124:    */           break;
/* 125:    */         case 1: 
/* 126:231 */           if (c == '=')
/* 127:    */           {
/* 128:232 */             if (paramName.length() == 0) {
/* 129:233 */               state = 99;
/* 130:    */             } else {
/* 131:235 */               state = 2;
/* 132:    */             }
/* 133:    */           }
/* 134:    */           else {
/* 135:240 */             paramName.append(c);
/* 136:    */           }
/* 137:241 */           break;
/* 138:    */         case 2: 
/* 139:244 */           fallThrough = false;
/* 140:245 */           switch (c)
/* 141:    */           {
/* 142:    */           case '\t': 
/* 143:    */           case ' ': 
/* 144:    */             break;
/* 145:    */           case '"': 
/* 146:251 */             state = 4;
/* 147:252 */             break;
/* 148:    */           default: 
/* 149:255 */             state = 3;
/* 150:256 */             fallThrough = true;
/* 151:    */           }
/* 152:259 */           if (!fallThrough) {
/* 153:    */             break;
/* 154:    */           }
/* 155:    */         case 3: 
/* 156:265 */           fallThrough = false;
/* 157:266 */           switch (c)
/* 158:    */           {
/* 159:    */           case '\t': 
/* 160:    */           case ' ': 
/* 161:    */           case ';': 
/* 162:270 */             result.put(paramName.toString().trim().toLowerCase(), paramValue.toString().trim());
/* 163:    */             
/* 164:    */ 
/* 165:273 */             state = 5;
/* 166:274 */             fallThrough = true;
/* 167:275 */             break;
/* 168:    */           default: 
/* 169:277 */             paramValue.append(c);
/* 170:    */           }
/* 171:280 */           if (!fallThrough) {
/* 172:    */             break;
/* 173:    */           }
/* 174:    */         case 5: 
/* 175:284 */           switch (c)
/* 176:    */           {
/* 177:    */           case ';': 
/* 178:286 */             state = 0;
/* 179:287 */             break;
/* 180:    */           case '\t': 
/* 181:    */           case ' ': 
/* 182:    */             break;
/* 183:    */           default: 
/* 184:294 */             state = 99;
/* 185:    */           }
/* 186:295 */           break;
/* 187:    */         case 4: 
/* 188:300 */           switch (c)
/* 189:    */           {
/* 190:    */           case '"': 
/* 191:302 */             if (!escaped)
/* 192:    */             {
/* 193:304 */               result.put(paramName.toString().trim().toLowerCase(), paramValue.toString());
/* 194:    */               
/* 195:    */ 
/* 196:307 */               state = 5;
/* 197:    */             }
/* 198:    */             else
/* 199:    */             {
/* 200:309 */               escaped = false;
/* 201:310 */               paramValue.append(c);
/* 202:    */             }
/* 203:312 */             break;
/* 204:    */           case '\\': 
/* 205:315 */             if (escaped) {
/* 206:316 */               paramValue.append('\\');
/* 207:    */             }
/* 208:318 */             escaped = !escaped;
/* 209:319 */             break;
/* 210:    */           default: 
/* 211:322 */             if (escaped) {
/* 212:323 */               paramValue.append('\\');
/* 213:    */             }
/* 214:325 */             escaped = false;
/* 215:326 */             paramValue.append(c);
/* 216:    */           }
/* 217:    */           break;
/* 218:    */         }
/* 219:    */       }
/* 220:335 */       if (state == 3) {
/* 221:336 */         result.put(paramName.toString().trim().toLowerCase(), paramValue.toString().trim());
/* 222:    */       }
/* 223:    */     }
/* 224:342 */     return result;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static String createUniqueBoundary()
/* 228:    */   {
/* 229:352 */     StringBuilder sb = new StringBuilder();
/* 230:353 */     sb.append("-=Part.");
/* 231:354 */     sb.append(Integer.toHexString(nextCounterValue()));
/* 232:355 */     sb.append('.');
/* 233:356 */     sb.append(Long.toHexString(random.nextLong()));
/* 234:357 */     sb.append('.');
/* 235:358 */     sb.append(Long.toHexString(System.currentTimeMillis()));
/* 236:359 */     sb.append('.');
/* 237:360 */     sb.append(Long.toHexString(random.nextLong()));
/* 238:361 */     sb.append("=-");
/* 239:362 */     return sb.toString();
/* 240:    */   }
/* 241:    */   
/* 242:    */   public static String createUniqueMessageId(String hostName)
/* 243:    */   {
/* 244:378 */     StringBuilder sb = new StringBuilder("<Mime4j.");
/* 245:379 */     sb.append(Integer.toHexString(nextCounterValue()));
/* 246:380 */     sb.append('.');
/* 247:381 */     sb.append(Long.toHexString(random.nextLong()));
/* 248:382 */     sb.append('.');
/* 249:383 */     sb.append(Long.toHexString(System.currentTimeMillis()));
/* 250:384 */     if (hostName != null)
/* 251:    */     {
/* 252:385 */       sb.append('@');
/* 253:386 */       sb.append(hostName);
/* 254:    */     }
/* 255:388 */     sb.append('>');
/* 256:389 */     return sb.toString();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public static String formatDate(Date date, TimeZone zone)
/* 260:    */   {
/* 261:403 */     DateFormat df = (DateFormat)RFC822_DATE_FORMAT.get();
/* 262:405 */     if (zone == null) {
/* 263:406 */       df.setTimeZone(TimeZone.getDefault());
/* 264:    */     } else {
/* 265:408 */       df.setTimeZone(zone);
/* 266:    */     }
/* 267:411 */     return df.format(date);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public static String fold(String s, int usedCharacters)
/* 271:    */   {
/* 272:431 */     int maxCharacters = 76;
/* 273:    */     
/* 274:433 */     int length = s.length();
/* 275:434 */     if (usedCharacters + length <= 76) {
/* 276:435 */       return s;
/* 277:    */     }
/* 278:437 */     StringBuilder sb = new StringBuilder();
/* 279:    */     
/* 280:439 */     int lastLineBreak = -usedCharacters;
/* 281:440 */     int wspIdx = indexOfWsp(s, 0);
/* 282:    */     for (;;)
/* 283:    */     {
/* 284:442 */       if (wspIdx == length)
/* 285:    */       {
/* 286:443 */         sb.append(s.substring(Math.max(0, lastLineBreak)));
/* 287:444 */         return sb.toString();
/* 288:    */       }
/* 289:447 */       int nextWspIdx = indexOfWsp(s, wspIdx + 1);
/* 290:449 */       if (nextWspIdx - lastLineBreak > 76)
/* 291:    */       {
/* 292:450 */         sb.append(s.substring(Math.max(0, lastLineBreak), wspIdx));
/* 293:451 */         sb.append("\r\n");
/* 294:452 */         lastLineBreak = wspIdx;
/* 295:    */       }
/* 296:455 */       wspIdx = nextWspIdx;
/* 297:    */     }
/* 298:    */   }
/* 299:    */   
/* 300:    */   public static String unfold(String s)
/* 301:    */   {
/* 302:467 */     int length = s.length();
/* 303:468 */     for (int idx = 0; idx < length; idx++)
/* 304:    */     {
/* 305:469 */       char c = s.charAt(idx);
/* 306:470 */       if ((c == '\r') || (c == '\n')) {
/* 307:471 */         return unfold0(s, idx);
/* 308:    */       }
/* 309:    */     }
/* 310:475 */     return s;
/* 311:    */   }
/* 312:    */   
/* 313:    */   private static String unfold0(String s, int crlfIdx)
/* 314:    */   {
/* 315:479 */     int length = s.length();
/* 316:480 */     StringBuilder sb = new StringBuilder(length);
/* 317:482 */     if (crlfIdx > 0) {
/* 318:483 */       sb.append(s.substring(0, crlfIdx));
/* 319:    */     }
/* 320:486 */     for (int idx = crlfIdx + 1; idx < length; idx++)
/* 321:    */     {
/* 322:487 */       char c = s.charAt(idx);
/* 323:488 */       if ((c != '\r') && (c != '\n')) {
/* 324:489 */         sb.append(c);
/* 325:    */       }
/* 326:    */     }
/* 327:493 */     return sb.toString();
/* 328:    */   }
/* 329:    */   
/* 330:    */   private static int indexOfWsp(String s, int fromIndex)
/* 331:    */   {
/* 332:497 */     int len = s.length();
/* 333:498 */     for (int index = fromIndex; index < len; index++)
/* 334:    */     {
/* 335:499 */       char c = s.charAt(index);
/* 336:500 */       if ((c == ' ') || (c == '\t')) {
/* 337:501 */         return index;
/* 338:    */       }
/* 339:    */     }
/* 340:503 */     return len;
/* 341:    */   }
/* 342:    */   
/* 343:    */   private static synchronized int nextCounterValue()
/* 344:    */   {
/* 345:507 */     return counter++;
/* 346:    */   }
/* 347:    */   
/* 348:510 */   private static final ThreadLocal<DateFormat> RFC822_DATE_FORMAT = new ThreadLocal()
/* 349:    */   {
/* 350:    */     protected DateFormat initialValue()
/* 351:    */     {
/* 352:513 */       return new MimeUtil.Rfc822DateFormat();
/* 353:    */     }
/* 354:    */   };
/* 355:    */   
/* 356:    */   private static final class Rfc822DateFormat
/* 357:    */     extends SimpleDateFormat
/* 358:    */   {
/* 359:    */     private static final long serialVersionUID = 1L;
/* 360:    */     
/* 361:    */     public Rfc822DateFormat()
/* 362:    */     {
/* 363:521 */       super(Locale.US);
/* 364:    */     }
/* 365:    */     
/* 366:    */     public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos)
/* 367:    */     {
/* 368:527 */       StringBuffer sb = super.format(date, toAppendTo, pos);
/* 369:    */       
/* 370:529 */       int zoneMillis = this.calendar.get(15);
/* 371:530 */       int dstMillis = this.calendar.get(16);
/* 372:531 */       int minutes = (zoneMillis + dstMillis) / 1000 / 60;
/* 373:533 */       if (minutes < 0)
/* 374:    */       {
/* 375:534 */         sb.append('-');
/* 376:535 */         minutes = -minutes;
/* 377:    */       }
/* 378:    */       else
/* 379:    */       {
/* 380:537 */         sb.append('+');
/* 381:    */       }
/* 382:540 */       sb.append(String.format("%02d%02d", new Object[] { Integer.valueOf(minutes / 60), Integer.valueOf(minutes % 60) }));
/* 383:    */       
/* 384:542 */       return sb;
/* 385:    */     }
/* 386:    */   }
/* 387:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.util.MimeUtil
 * JD-Core Version:    0.7.0.1
 */