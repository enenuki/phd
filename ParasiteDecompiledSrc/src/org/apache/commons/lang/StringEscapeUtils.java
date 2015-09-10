/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.StringWriter;
/*   5:    */ import java.io.Writer;
/*   6:    */ import java.util.Locale;
/*   7:    */ import org.apache.commons.lang.exception.NestableRuntimeException;
/*   8:    */ import org.apache.commons.lang.text.StrBuilder;
/*   9:    */ 
/*  10:    */ public class StringEscapeUtils
/*  11:    */ {
/*  12:    */   private static final char CSV_DELIMITER = ',';
/*  13:    */   private static final char CSV_QUOTE = '"';
/*  14: 49 */   private static final String CSV_QUOTE_STR = String.valueOf('"');
/*  15: 50 */   private static final char[] CSV_SEARCH_CHARS = { ',', '"', '\r', '\n' };
/*  16:    */   
/*  17:    */   public static String escapeJava(String str)
/*  18:    */   {
/*  19: 90 */     return escapeJavaStyleString(str, false, false);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public static void escapeJava(Writer out, String str)
/*  23:    */     throws IOException
/*  24:    */   {
/*  25:106 */     escapeJavaStyleString(out, str, false, false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static String escapeJavaScript(String str)
/*  29:    */   {
/*  30:131 */     return escapeJavaStyleString(str, true, true);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static void escapeJavaScript(Writer out, String str)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36:147 */     escapeJavaStyleString(out, str, true, true);
/*  37:    */   }
/*  38:    */   
/*  39:    */   private static String escapeJavaStyleString(String str, boolean escapeSingleQuotes, boolean escapeForwardSlash)
/*  40:    */   {
/*  41:159 */     if (str == null) {
/*  42:160 */       return null;
/*  43:    */     }
/*  44:    */     try
/*  45:    */     {
/*  46:163 */       StringWriter writer = new StringWriter(str.length() * 2);
/*  47:164 */       escapeJavaStyleString(writer, str, escapeSingleQuotes, escapeForwardSlash);
/*  48:165 */       return writer.toString();
/*  49:    */     }
/*  50:    */     catch (IOException ioe)
/*  51:    */     {
/*  52:168 */       throw new UnhandledException(ioe);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static void escapeJavaStyleString(Writer out, String str, boolean escapeSingleQuote, boolean escapeForwardSlash)
/*  57:    */     throws IOException
/*  58:    */   {
/*  59:183 */     if (out == null) {
/*  60:184 */       throw new IllegalArgumentException("The Writer must not be null");
/*  61:    */     }
/*  62:186 */     if (str == null) {
/*  63:187 */       return;
/*  64:    */     }
/*  65:190 */     int sz = str.length();
/*  66:191 */     for (int i = 0; i < sz; i++)
/*  67:    */     {
/*  68:192 */       char ch = str.charAt(i);
/*  69:195 */       if (ch > '࿿') {
/*  70:196 */         out.write("\\u" + hex(ch));
/*  71:197 */       } else if (ch > 'ÿ') {
/*  72:198 */         out.write("\\u0" + hex(ch));
/*  73:199 */       } else if (ch > '') {
/*  74:200 */         out.write("\\u00" + hex(ch));
/*  75:201 */       } else if (ch < ' ') {
/*  76:202 */         switch (ch)
/*  77:    */         {
/*  78:    */         case '\b': 
/*  79:204 */           out.write(92);
/*  80:205 */           out.write(98);
/*  81:206 */           break;
/*  82:    */         case '\n': 
/*  83:208 */           out.write(92);
/*  84:209 */           out.write(110);
/*  85:210 */           break;
/*  86:    */         case '\t': 
/*  87:212 */           out.write(92);
/*  88:213 */           out.write(116);
/*  89:214 */           break;
/*  90:    */         case '\f': 
/*  91:216 */           out.write(92);
/*  92:217 */           out.write(102);
/*  93:218 */           break;
/*  94:    */         case '\r': 
/*  95:220 */           out.write(92);
/*  96:221 */           out.write(114);
/*  97:222 */           break;
/*  98:    */         case '\013': 
/*  99:    */         default: 
/* 100:224 */           if (ch > '\017') {
/* 101:225 */             out.write("\\u00" + hex(ch));
/* 102:    */           } else {
/* 103:227 */             out.write("\\u000" + hex(ch));
/* 104:    */           }
/* 105:229 */           break;
/* 106:    */         }
/* 107:    */       } else {
/* 108:232 */         switch (ch)
/* 109:    */         {
/* 110:    */         case '\'': 
/* 111:234 */           if (escapeSingleQuote) {
/* 112:235 */             out.write(92);
/* 113:    */           }
/* 114:237 */           out.write(39);
/* 115:238 */           break;
/* 116:    */         case '"': 
/* 117:240 */           out.write(92);
/* 118:241 */           out.write(34);
/* 119:242 */           break;
/* 120:    */         case '\\': 
/* 121:244 */           out.write(92);
/* 122:245 */           out.write(92);
/* 123:246 */           break;
/* 124:    */         case '/': 
/* 125:248 */           if (escapeForwardSlash) {
/* 126:249 */             out.write(92);
/* 127:    */           }
/* 128:251 */           out.write(47);
/* 129:252 */           break;
/* 130:    */         default: 
/* 131:254 */           out.write(ch);
/* 132:    */         }
/* 133:    */       }
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private static String hex(char ch)
/* 138:    */   {
/* 139:269 */     return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static String unescapeJava(String str)
/* 143:    */   {
/* 144:282 */     if (str == null) {
/* 145:283 */       return null;
/* 146:    */     }
/* 147:    */     try
/* 148:    */     {
/* 149:286 */       StringWriter writer = new StringWriter(str.length());
/* 150:287 */       unescapeJava(writer, str);
/* 151:288 */       return writer.toString();
/* 152:    */     }
/* 153:    */     catch (IOException ioe)
/* 154:    */     {
/* 155:291 */       throw new UnhandledException(ioe);
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static void unescapeJava(Writer out, String str)
/* 160:    */     throws IOException
/* 161:    */   {
/* 162:311 */     if (out == null) {
/* 163:312 */       throw new IllegalArgumentException("The Writer must not be null");
/* 164:    */     }
/* 165:314 */     if (str == null) {
/* 166:315 */       return;
/* 167:    */     }
/* 168:317 */     int sz = str.length();
/* 169:318 */     StrBuilder unicode = new StrBuilder(4);
/* 170:319 */     boolean hadSlash = false;
/* 171:320 */     boolean inUnicode = false;
/* 172:321 */     for (int i = 0; i < sz; i++)
/* 173:    */     {
/* 174:322 */       char ch = str.charAt(i);
/* 175:323 */       if (inUnicode)
/* 176:    */       {
/* 177:326 */         unicode.append(ch);
/* 178:327 */         if (unicode.length() == 4) {
/* 179:    */           try
/* 180:    */           {
/* 181:331 */             int value = Integer.parseInt(unicode.toString(), 16);
/* 182:332 */             out.write((char)value);
/* 183:333 */             unicode.setLength(0);
/* 184:334 */             inUnicode = false;
/* 185:335 */             hadSlash = false;
/* 186:    */           }
/* 187:    */           catch (NumberFormatException nfe)
/* 188:    */           {
/* 189:337 */             throw new NestableRuntimeException("Unable to parse unicode value: " + unicode, nfe);
/* 190:    */           }
/* 191:    */         }
/* 192:    */       }
/* 193:342 */       else if (hadSlash)
/* 194:    */       {
/* 195:344 */         hadSlash = false;
/* 196:345 */         switch (ch)
/* 197:    */         {
/* 198:    */         case '\\': 
/* 199:347 */           out.write(92);
/* 200:348 */           break;
/* 201:    */         case '\'': 
/* 202:350 */           out.write(39);
/* 203:351 */           break;
/* 204:    */         case '"': 
/* 205:353 */           out.write(34);
/* 206:354 */           break;
/* 207:    */         case 'r': 
/* 208:356 */           out.write(13);
/* 209:357 */           break;
/* 210:    */         case 'f': 
/* 211:359 */           out.write(12);
/* 212:360 */           break;
/* 213:    */         case 't': 
/* 214:362 */           out.write(9);
/* 215:363 */           break;
/* 216:    */         case 'n': 
/* 217:365 */           out.write(10);
/* 218:366 */           break;
/* 219:    */         case 'b': 
/* 220:368 */           out.write(8);
/* 221:369 */           break;
/* 222:    */         case 'u': 
/* 223:373 */           inUnicode = true;
/* 224:374 */           break;
/* 225:    */         default: 
/* 226:377 */           out.write(ch);
/* 227:378 */           break;
/* 228:    */         }
/* 229:    */       }
/* 230:381 */       else if (ch == '\\')
/* 231:    */       {
/* 232:382 */         hadSlash = true;
/* 233:    */       }
/* 234:    */       else
/* 235:    */       {
/* 236:385 */         out.write(ch);
/* 237:    */       }
/* 238:    */     }
/* 239:387 */     if (hadSlash) {
/* 240:390 */       out.write(92);
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static String unescapeJavaScript(String str)
/* 245:    */   {
/* 246:406 */     return unescapeJava(str);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public static void unescapeJavaScript(Writer out, String str)
/* 250:    */     throws IOException
/* 251:    */   {
/* 252:426 */     unescapeJava(out, str);
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static String escapeHtml(String str)
/* 256:    */   {
/* 257:458 */     if (str == null) {
/* 258:459 */       return null;
/* 259:    */     }
/* 260:    */     try
/* 261:    */     {
/* 262:462 */       StringWriter writer = new StringWriter((int)(str.length() * 1.5D));
/* 263:463 */       escapeHtml(writer, str);
/* 264:464 */       return writer.toString();
/* 265:    */     }
/* 266:    */     catch (IOException ioe)
/* 267:    */     {
/* 268:467 */       throw new UnhandledException(ioe);
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public static void escapeHtml(Writer writer, String string)
/* 273:    */     throws IOException
/* 274:    */   {
/* 275:501 */     if (writer == null) {
/* 276:502 */       throw new IllegalArgumentException("The Writer must not be null.");
/* 277:    */     }
/* 278:504 */     if (string == null) {
/* 279:505 */       return;
/* 280:    */     }
/* 281:507 */     Entities.HTML40.escape(writer, string);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public static String unescapeHtml(String str)
/* 285:    */   {
/* 286:528 */     if (str == null) {
/* 287:529 */       return null;
/* 288:    */     }
/* 289:    */     try
/* 290:    */     {
/* 291:532 */       StringWriter writer = new StringWriter((int)(str.length() * 1.5D));
/* 292:533 */       unescapeHtml(writer, str);
/* 293:534 */       return writer.toString();
/* 294:    */     }
/* 295:    */     catch (IOException ioe)
/* 296:    */     {
/* 297:537 */       throw new UnhandledException(ioe);
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static void unescapeHtml(Writer writer, String string)
/* 302:    */     throws IOException
/* 303:    */   {
/* 304:560 */     if (writer == null) {
/* 305:561 */       throw new IllegalArgumentException("The Writer must not be null.");
/* 306:    */     }
/* 307:563 */     if (string == null) {
/* 308:564 */       return;
/* 309:    */     }
/* 310:566 */     Entities.HTML40.unescape(writer, string);
/* 311:    */   }
/* 312:    */   
/* 313:    */   public static void escapeXml(Writer writer, String str)
/* 314:    */     throws IOException
/* 315:    */   {
/* 316:590 */     if (writer == null) {
/* 317:591 */       throw new IllegalArgumentException("The Writer must not be null.");
/* 318:    */     }
/* 319:593 */     if (str == null) {
/* 320:594 */       return;
/* 321:    */     }
/* 322:596 */     Entities.XML.escape(writer, str);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public static String escapeXml(String str)
/* 326:    */   {
/* 327:617 */     if (str == null) {
/* 328:618 */       return null;
/* 329:    */     }
/* 330:620 */     return Entities.XML.escape(str);
/* 331:    */   }
/* 332:    */   
/* 333:    */   public static void unescapeXml(Writer writer, String str)
/* 334:    */     throws IOException
/* 335:    */   {
/* 336:642 */     if (writer == null) {
/* 337:643 */       throw new IllegalArgumentException("The Writer must not be null.");
/* 338:    */     }
/* 339:645 */     if (str == null) {
/* 340:646 */       return;
/* 341:    */     }
/* 342:648 */     Entities.XML.unescape(writer, str);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public static String unescapeXml(String str)
/* 346:    */   {
/* 347:667 */     if (str == null) {
/* 348:668 */       return null;
/* 349:    */     }
/* 350:670 */     return Entities.XML.unescape(str);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public static String escapeSql(String str)
/* 354:    */   {
/* 355:693 */     if (str == null) {
/* 356:694 */       return null;
/* 357:    */     }
/* 358:696 */     return StringUtils.replace(str, "'", "''");
/* 359:    */   }
/* 360:    */   
/* 361:    */   public static String escapeCsv(String str)
/* 362:    */   {
/* 363:724 */     if (StringUtils.containsNone(str, CSV_SEARCH_CHARS)) {
/* 364:725 */       return str;
/* 365:    */     }
/* 366:    */     try
/* 367:    */     {
/* 368:728 */       StringWriter writer = new StringWriter();
/* 369:729 */       escapeCsv(writer, str);
/* 370:730 */       return writer.toString();
/* 371:    */     }
/* 372:    */     catch (IOException ioe)
/* 373:    */     {
/* 374:733 */       throw new UnhandledException(ioe);
/* 375:    */     }
/* 376:    */   }
/* 377:    */   
/* 378:    */   public static void escapeCsv(Writer out, String str)
/* 379:    */     throws IOException
/* 380:    */   {
/* 381:761 */     if (StringUtils.containsNone(str, CSV_SEARCH_CHARS))
/* 382:    */     {
/* 383:762 */       if (str != null) {
/* 384:763 */         out.write(str);
/* 385:    */       }
/* 386:765 */       return;
/* 387:    */     }
/* 388:767 */     out.write(34);
/* 389:768 */     for (int i = 0; i < str.length(); i++)
/* 390:    */     {
/* 391:769 */       char c = str.charAt(i);
/* 392:770 */       if (c == '"') {
/* 393:771 */         out.write(34);
/* 394:    */       }
/* 395:773 */       out.write(c);
/* 396:    */     }
/* 397:775 */     out.write(34);
/* 398:    */   }
/* 399:    */   
/* 400:    */   public static String unescapeCsv(String str)
/* 401:    */   {
/* 402:801 */     if (str == null) {
/* 403:802 */       return null;
/* 404:    */     }
/* 405:    */     try
/* 406:    */     {
/* 407:805 */       StringWriter writer = new StringWriter();
/* 408:806 */       unescapeCsv(writer, str);
/* 409:807 */       return writer.toString();
/* 410:    */     }
/* 411:    */     catch (IOException ioe)
/* 412:    */     {
/* 413:810 */       throw new UnhandledException(ioe);
/* 414:    */     }
/* 415:    */   }
/* 416:    */   
/* 417:    */   public static void unescapeCsv(Writer out, String str)
/* 418:    */     throws IOException
/* 419:    */   {
/* 420:838 */     if (str == null) {
/* 421:839 */       return;
/* 422:    */     }
/* 423:841 */     if (str.length() < 2)
/* 424:    */     {
/* 425:842 */       out.write(str);
/* 426:843 */       return;
/* 427:    */     }
/* 428:845 */     if ((str.charAt(0) != '"') || (str.charAt(str.length() - 1) != '"'))
/* 429:    */     {
/* 430:846 */       out.write(str);
/* 431:847 */       return;
/* 432:    */     }
/* 433:851 */     String quoteless = str.substring(1, str.length() - 1);
/* 434:853 */     if (StringUtils.containsAny(quoteless, CSV_SEARCH_CHARS)) {
/* 435:855 */       str = StringUtils.replace(quoteless, CSV_QUOTE_STR + CSV_QUOTE_STR, CSV_QUOTE_STR);
/* 436:    */     }
/* 437:858 */     out.write(str);
/* 438:    */   }
/* 439:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.StringEscapeUtils
 * JD-Core Version:    0.7.0.1
 */