/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.nio.charset.Charset;
/*   6:    */ import java.nio.charset.IllegalCharsetNameException;
/*   7:    */ import java.util.List;
/*   8:    */ import org.apache.commons.lang.ArrayUtils;
/*   9:    */ import org.apache.commons.logging.Log;
/*  10:    */ import org.apache.commons.logging.LogFactory;
/*  11:    */ 
/*  12:    */ public final class EncodingSniffer
/*  13:    */ {
/*  14: 39 */   private static final Log LOG = LogFactory.getLog(EncodingSniffer.class);
/*  15:    */   static final String UTF16_LE = "UTF-16LE";
/*  16:    */   static final String UTF16_BE = "UTF-16BE";
/*  17:    */   static final String UTF8 = "UTF-8";
/*  18: 51 */   private static final byte[][] COMMENT_START = { { 60 }, { 33 }, { 45 }, { 45 } };
/*  19: 59 */   private static final byte[][] META_START = { { 60 }, { 109, 77 }, { 101, 69 }, { 116, 84 }, { 97, 65 }, { 9, 10, 12, 13, 32, 47 } };
/*  20: 69 */   private static final byte[][] OTHER_START = { { 60 }, { 33, 47, 63 } };
/*  21: 75 */   private static final byte[][] CHARSET_START = { { 99, 67 }, { 104, 72 }, { 97, 65 }, { 114, 82 }, { 115, 83 }, { 101, 69 }, { 116, 84 } };
/*  22:    */   private static final int SIZE_OF_HTML_CONTENT_SNIFFED = 4096;
/*  23:    */   private static final int SIZE_OF_XML_CONTENT_SNIFFED = 512;
/*  24:    */   
/*  25:    */   public static String sniffEncoding(List<NameValuePair> headers, InputStream content)
/*  26:    */     throws IOException
/*  27:    */   {
/*  28:128 */     if (isHtml(headers)) {
/*  29:129 */       return sniffHtmlEncoding(headers, content);
/*  30:    */     }
/*  31:131 */     if (isXml(headers)) {
/*  32:132 */       return sniffXmlEncoding(headers, content);
/*  33:    */     }
/*  34:135 */     return sniffUnknownContentTypeEncoding(headers, content);
/*  35:    */   }
/*  36:    */   
/*  37:    */   static boolean isHtml(List<NameValuePair> headers)
/*  38:    */   {
/*  39:146 */     return contentTypeEndsWith(headers, new String[] { "text/html" });
/*  40:    */   }
/*  41:    */   
/*  42:    */   static boolean isXml(List<NameValuePair> headers)
/*  43:    */   {
/*  44:156 */     return contentTypeEndsWith(headers, new String[] { "text/xml", "application/xml", "text/vnd.wap.wml", "+xml" });
/*  45:    */   }
/*  46:    */   
/*  47:    */   static boolean contentTypeEndsWith(List<NameValuePair> headers, String... contentTypeEndings)
/*  48:    */   {
/*  49:169 */     for (NameValuePair pair : headers)
/*  50:    */     {
/*  51:170 */       String name = pair.getName();
/*  52:171 */       if ("content-type".equalsIgnoreCase(name))
/*  53:    */       {
/*  54:172 */         String value = pair.getValue();
/*  55:173 */         int i = value.indexOf(';');
/*  56:174 */         if (i != -1) {
/*  57:175 */           value = value.substring(0, i);
/*  58:    */         }
/*  59:177 */         value = value.trim();
/*  60:178 */         boolean found = false;
/*  61:179 */         for (String ending : contentTypeEndings) {
/*  62:180 */           if (value.toLowerCase().endsWith(ending.toLowerCase()))
/*  63:    */           {
/*  64:181 */             found = true;
/*  65:182 */             break;
/*  66:    */           }
/*  67:    */         }
/*  68:185 */         return found;
/*  69:    */       }
/*  70:    */     }
/*  71:188 */     return false;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static String sniffHtmlEncoding(List<NameValuePair> headers, InputStream content)
/*  75:    */     throws IOException
/*  76:    */   {
/*  77:208 */     String encoding = sniffEncodingFromHttpHeaders(headers);
/*  78:209 */     if ((encoding != null) || (content == null)) {
/*  79:210 */       return encoding;
/*  80:    */     }
/*  81:213 */     byte[] bytes = read(content, 3);
/*  82:214 */     encoding = sniffEncodingFromUnicodeBom(bytes);
/*  83:215 */     if (encoding != null) {
/*  84:216 */       return encoding;
/*  85:    */     }
/*  86:219 */     bytes = readAndPrepend(content, 4096, bytes);
/*  87:220 */     encoding = sniffEncodingFromMetaTag(bytes);
/*  88:221 */     return encoding;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String sniffXmlEncoding(List<NameValuePair> headers, InputStream content)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94:240 */     String encoding = sniffEncodingFromHttpHeaders(headers);
/*  95:241 */     if ((encoding != null) || (content == null)) {
/*  96:242 */       return encoding;
/*  97:    */     }
/*  98:245 */     byte[] bytes = read(content, 3);
/*  99:246 */     encoding = sniffEncodingFromUnicodeBom(bytes);
/* 100:247 */     if (encoding != null) {
/* 101:248 */       return encoding;
/* 102:    */     }
/* 103:251 */     bytes = readAndPrepend(content, 512, bytes);
/* 104:252 */     encoding = sniffEncodingFromXmlDeclaration(bytes);
/* 105:253 */     return encoding;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String sniffUnknownContentTypeEncoding(List<NameValuePair> headers, InputStream content)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:273 */     String encoding = sniffEncodingFromHttpHeaders(headers);
/* 112:274 */     if ((encoding != null) || (content == null)) {
/* 113:275 */       return encoding;
/* 114:    */     }
/* 115:278 */     byte[] bytes = read(content, 3);
/* 116:279 */     encoding = sniffEncodingFromUnicodeBom(bytes);
/* 117:280 */     return encoding;
/* 118:    */   }
/* 119:    */   
/* 120:    */   static String sniffEncodingFromHttpHeaders(List<NameValuePair> headers)
/* 121:    */   {
/* 122:291 */     String encoding = null;
/* 123:292 */     for (NameValuePair pair : headers)
/* 124:    */     {
/* 125:293 */       String name = pair.getName();
/* 126:294 */       if ("content-type".equalsIgnoreCase(name))
/* 127:    */       {
/* 128:295 */         String value = pair.getValue();
/* 129:296 */         encoding = extractEncodingFromContentType(value);
/* 130:297 */         if (encoding != null) {
/* 131:    */           break;
/* 132:    */         }
/* 133:    */       }
/* 134:    */     }
/* 135:302 */     if ((encoding != null) && (LOG.isDebugEnabled())) {
/* 136:303 */       LOG.debug("Encoding found in HTTP headers: '" + encoding + "'.");
/* 137:    */     }
/* 138:305 */     return encoding;
/* 139:    */   }
/* 140:    */   
/* 141:    */   static String sniffEncodingFromUnicodeBom(byte[] bytes)
/* 142:    */   {
/* 143:317 */     String encoding = null;
/* 144:318 */     byte[] markerUTF8 = { -17, -69, -65 };
/* 145:319 */     byte[] markerUTF16BE = { -2, -1 };
/* 146:320 */     byte[] markerUTF16LE = { -1, -2 };
/* 147:321 */     if ((bytes != null) && (ArrayUtils.isEquals(markerUTF8, ArrayUtils.subarray(bytes, 0, 3)))) {
/* 148:322 */       encoding = "UTF-8";
/* 149:324 */     } else if ((bytes != null) && (ArrayUtils.isEquals(markerUTF16BE, ArrayUtils.subarray(bytes, 0, 2)))) {
/* 150:325 */       encoding = "UTF-16BE";
/* 151:327 */     } else if ((bytes != null) && (ArrayUtils.isEquals(markerUTF16LE, ArrayUtils.subarray(bytes, 0, 2)))) {
/* 152:328 */       encoding = "UTF-16LE";
/* 153:    */     }
/* 154:330 */     if ((encoding != null) && (LOG.isDebugEnabled())) {
/* 155:331 */       LOG.debug("Encoding found in Unicode Byte Order Mark: '" + encoding + "'.");
/* 156:    */     }
/* 157:333 */     return encoding;
/* 158:    */   }
/* 159:    */   
/* 160:    */   static String sniffEncodingFromMetaTag(byte[] bytes)
/* 161:    */   {
/* 162:344 */     for (int i = 0; i < bytes.length; i++) {
/* 163:345 */       if (matches(bytes, i, COMMENT_START))
/* 164:    */       {
/* 165:346 */         i = indexOfSubArray(bytes, new byte[] { 45, 45, 62 }, i);
/* 166:347 */         if (i == -1) {
/* 167:    */           break;
/* 168:    */         }
/* 169:350 */         i += 2;
/* 170:    */       }
/* 171:352 */       else if (matches(bytes, i, META_START))
/* 172:    */       {
/* 173:353 */         i += META_START.length;
/* 174:354 */         for (Attribute att = getAttribute(bytes, i); att != null; att = getAttribute(bytes, i))
/* 175:    */         {
/* 176:355 */           i = att.getUpdatedIndex();
/* 177:356 */           String name = att.getName();
/* 178:357 */           String value = att.getValue();
/* 179:358 */           if (("charset".equals(name)) || ("content".equals(name)))
/* 180:    */           {
/* 181:359 */             String charset = null;
/* 182:360 */             if ("charset".equals(name))
/* 183:    */             {
/* 184:361 */               charset = value;
/* 185:    */             }
/* 186:363 */             else if ("content".equals(name))
/* 187:    */             {
/* 188:364 */               charset = extractEncodingFromContentType(value);
/* 189:365 */               if (charset == null) {
/* 190:    */                 continue;
/* 191:    */               }
/* 192:    */             }
/* 193:369 */             if (("UTF-16BE".equalsIgnoreCase(charset)) || ("UTF-16LE".equalsIgnoreCase(charset))) {
/* 194:370 */               charset = "UTF-8";
/* 195:    */             }
/* 196:372 */             if (isSupportedCharset(charset))
/* 197:    */             {
/* 198:373 */               if (LOG.isDebugEnabled()) {
/* 199:374 */                 LOG.debug("Encoding found in meta tag: '" + charset + "'.");
/* 200:    */               }
/* 201:376 */               return charset;
/* 202:    */             }
/* 203:    */           }
/* 204:    */         }
/* 205:    */       }
/* 206:381 */       else if ((i + 1 < bytes.length) && (bytes[i] == 60) && (Character.isLetter(bytes[(i + 1)])))
/* 207:    */       {
/* 208:382 */         i = skipToAnyOf(bytes, i, new byte[] { 9, 10, 12, 13, 32, 62 });
/* 209:383 */         if (i == -1) {
/* 210:    */           break;
/* 211:    */         }
/* 212:    */         Attribute att;
/* 213:387 */         while ((att = getAttribute(bytes, i)) != null) {
/* 214:388 */           i = att.getUpdatedIndex();
/* 215:    */         }
/* 216:    */       }
/* 217:391 */       else if ((i + 2 < bytes.length) && (bytes[i] == 60) && (bytes[(i + 1)] == 47) && (Character.isLetter(bytes[(i + 2)])))
/* 218:    */       {
/* 219:392 */         i = skipToAnyOf(bytes, i, new byte[] { 9, 10, 12, 13, 32, 62 });
/* 220:393 */         if (i == -1) {
/* 221:    */           break;
/* 222:    */         }
/* 223:    */         Attribute attribute;
/* 224:397 */         while ((attribute = getAttribute(bytes, i)) != null) {
/* 225:398 */           i = attribute.getUpdatedIndex();
/* 226:    */         }
/* 227:    */       }
/* 228:401 */       else if (matches(bytes, i, OTHER_START))
/* 229:    */       {
/* 230:402 */         i = skipToAnyOf(bytes, i, new byte[] { 62 });
/* 231:403 */         if (i == -1) {
/* 232:    */           break;
/* 233:    */         }
/* 234:    */       }
/* 235:    */     }
/* 236:408 */     return null;
/* 237:    */   }
/* 238:    */   
/* 239:    */   static Attribute getAttribute(byte[] bytes, int i)
/* 240:    */   {
/* 241:421 */     if (i >= bytes.length) {
/* 242:422 */       return null;
/* 243:    */     }
/* 244:424 */     while ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32) || (bytes[i] == 47))
/* 245:    */     {
/* 246:425 */       i++;
/* 247:426 */       if (i >= bytes.length) {
/* 248:427 */         return null;
/* 249:    */       }
/* 250:    */     }
/* 251:430 */     if (bytes[i] == 62) {
/* 252:431 */       return null;
/* 253:    */     }
/* 254:433 */     StringBuilder name = new StringBuilder();
/* 255:434 */     StringBuilder value = new StringBuilder();
/* 256:435 */     for (;; i++)
/* 257:    */     {
/* 258:436 */       if (i >= bytes.length) {
/* 259:437 */         return new Attribute(name.toString(), value.toString(), i);
/* 260:    */       }
/* 261:439 */       if ((bytes[i] == 61) && (name.length() > 0))
/* 262:    */       {
/* 263:440 */         i++;
/* 264:441 */         break;
/* 265:    */       }
/* 266:443 */       if ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32))
/* 267:    */       {
/* 268:444 */         while ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32))
/* 269:    */         {
/* 270:445 */           i++;
/* 271:446 */           if (i >= bytes.length) {
/* 272:447 */             return new Attribute(name.toString(), value.toString(), i);
/* 273:    */           }
/* 274:    */         }
/* 275:450 */         if (bytes[i] != 61) {
/* 276:451 */           return new Attribute(name.toString(), value.toString(), i);
/* 277:    */         }
/* 278:453 */         i++;
/* 279:454 */         break;
/* 280:    */       }
/* 281:456 */       if ((bytes[i] == 47) || (bytes[i] == 62)) {
/* 282:457 */         return new Attribute(name.toString(), value.toString(), i);
/* 283:    */       }
/* 284:459 */       name.append((char)bytes[i]);
/* 285:    */     }
/* 286:461 */     if (i >= bytes.length) {
/* 287:462 */       return new Attribute(name.toString(), value.toString(), i);
/* 288:    */     }
/* 289:464 */     while ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32))
/* 290:    */     {
/* 291:465 */       i++;
/* 292:466 */       if (i >= bytes.length) {
/* 293:467 */         return new Attribute(name.toString(), value.toString(), i);
/* 294:    */       }
/* 295:    */     }
/* 296:470 */     if ((bytes[i] == 34) || (bytes[i] == 39))
/* 297:    */     {
/* 298:471 */       byte b = bytes[i];
/* 299:472 */       for (i++; i < bytes.length; i++)
/* 300:    */       {
/* 301:473 */         if (bytes[i] == b)
/* 302:    */         {
/* 303:474 */           i++;
/* 304:475 */           return new Attribute(name.toString(), value.toString(), i);
/* 305:    */         }
/* 306:477 */         if ((bytes[i] >= 65) && (bytes[i] <= 90))
/* 307:    */         {
/* 308:478 */           byte b2 = (byte)(bytes[i] + 32);
/* 309:479 */           value.append((char)b2);
/* 310:    */         }
/* 311:    */         else
/* 312:    */         {
/* 313:482 */           value.append((char)bytes[i]);
/* 314:    */         }
/* 315:    */       }
/* 316:485 */       return new Attribute(name.toString(), value.toString(), i);
/* 317:    */     }
/* 318:487 */     if (bytes[i] == 62) {
/* 319:488 */       return new Attribute(name.toString(), value.toString(), i);
/* 320:    */     }
/* 321:490 */     if ((bytes[i] >= 65) && (bytes[i] <= 90))
/* 322:    */     {
/* 323:491 */       byte b = (byte)(bytes[i] + 32);
/* 324:492 */       value.append((char)b);
/* 325:493 */       i++;
/* 326:    */     }
/* 327:    */     else
/* 328:    */     {
/* 329:496 */       value.append((char)bytes[i]);
/* 330:497 */       i++;
/* 331:    */     }
/* 332:499 */     for (; i < bytes.length; i++)
/* 333:    */     {
/* 334:500 */       if ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32) || (bytes[i] == 62)) {
/* 335:501 */         return new Attribute(name.toString(), value.toString(), i);
/* 336:    */       }
/* 337:503 */       if ((bytes[i] >= 65) && (bytes[i] <= 90))
/* 338:    */       {
/* 339:504 */         byte b = (byte)(bytes[i] + 32);
/* 340:505 */         value.append((char)b);
/* 341:    */       }
/* 342:    */       else
/* 343:    */       {
/* 344:508 */         value.append((char)bytes[i]);
/* 345:    */       }
/* 346:    */     }
/* 347:511 */     return new Attribute(name.toString(), value.toString(), i);
/* 348:    */   }
/* 349:    */   
/* 350:    */   static String extractEncodingFromContentType(String s)
/* 351:    */   {
/* 352:524 */     if (s == null) {
/* 353:525 */       return null;
/* 354:    */     }
/* 355:527 */     byte[] bytes = s.getBytes();
/* 356:529 */     for (int i = 0; i < bytes.length; i++) {
/* 357:530 */       if (matches(bytes, i, CHARSET_START))
/* 358:    */       {
/* 359:531 */         i += CHARSET_START.length;
/* 360:532 */         break;
/* 361:    */       }
/* 362:    */     }
/* 363:535 */     if (i == bytes.length) {
/* 364:536 */       return null;
/* 365:    */     }
/* 366:538 */     while ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32))
/* 367:    */     {
/* 368:539 */       i++;
/* 369:540 */       if (i == bytes.length) {
/* 370:541 */         return null;
/* 371:    */       }
/* 372:    */     }
/* 373:544 */     if (bytes[i] != 61) {
/* 374:545 */       return null;
/* 375:    */     }
/* 376:547 */     i++;
/* 377:548 */     if (i == bytes.length) {
/* 378:549 */       return null;
/* 379:    */     }
/* 380:551 */     while ((bytes[i] == 9) || (bytes[i] == 10) || (bytes[i] == 12) || (bytes[i] == 13) || (bytes[i] == 32))
/* 381:    */     {
/* 382:552 */       i++;
/* 383:553 */       if (i == bytes.length) {
/* 384:554 */         return null;
/* 385:    */       }
/* 386:    */     }
/* 387:557 */     if (bytes[i] == 34)
/* 388:    */     {
/* 389:558 */       if (bytes.length <= i + 1) {
/* 390:559 */         return null;
/* 391:    */       }
/* 392:561 */       int index = ArrayUtils.indexOf(bytes, (byte)34, i + 1);
/* 393:562 */       if (index == -1) {
/* 394:563 */         return null;
/* 395:    */       }
/* 396:565 */       String charset = new String(ArrayUtils.subarray(bytes, i + 1, index));
/* 397:566 */       return isSupportedCharset(charset) ? charset : null;
/* 398:    */     }
/* 399:568 */     if (bytes[i] == 39)
/* 400:    */     {
/* 401:569 */       if (bytes.length <= i + 1) {
/* 402:570 */         return null;
/* 403:    */       }
/* 404:572 */       int index = ArrayUtils.indexOf(bytes, (byte)39, i + 1);
/* 405:573 */       if (index == -1) {
/* 406:574 */         return null;
/* 407:    */       }
/* 408:576 */       String charset = new String(ArrayUtils.subarray(bytes, i + 1, index));
/* 409:577 */       return isSupportedCharset(charset) ? charset : null;
/* 410:    */     }
/* 411:579 */     int end = skipToAnyOf(bytes, i, new byte[] { 9, 10, 12, 13, 32, 59 });
/* 412:580 */     if (end == -1) {
/* 413:581 */       end = bytes.length;
/* 414:    */     }
/* 415:583 */     String charset = new String(ArrayUtils.subarray(bytes, i, end));
/* 416:584 */     return isSupportedCharset(charset) ? charset : null;
/* 417:    */   }
/* 418:    */   
/* 419:    */   static String sniffEncodingFromXmlDeclaration(byte[] bytes)
/* 420:    */   {
/* 421:595 */     String encoding = null;
/* 422:596 */     byte[] declarationPrefix = "<?xml ".getBytes();
/* 423:597 */     if (ArrayUtils.isEquals(declarationPrefix, ArrayUtils.subarray(bytes, 0, declarationPrefix.length)))
/* 424:    */     {
/* 425:598 */       int index = ArrayUtils.indexOf(bytes, (byte)63, 2);
/* 426:599 */       if ((index + 1 < bytes.length) && (bytes[(index + 1)] == 62))
/* 427:    */       {
/* 428:600 */         String declaration = new String(bytes, 0, index + 2);
/* 429:601 */         int start = declaration.indexOf("encoding");
/* 430:602 */         if (start != -1)
/* 431:    */         {
/* 432:603 */           start += 8;
/* 433:    */           char delimiter;
/* 434:    */           for (;;)
/* 435:    */           {
/* 436:607 */             switch (declaration.charAt(start))
/* 437:    */             {
/* 438:    */             case '"': 
/* 439:    */             case '\'': 
/* 440:610 */               delimiter = declaration.charAt(start);
/* 441:611 */               start += 1;
/* 442:612 */               break;
/* 443:    */             default: 
/* 444:615 */               start++;
/* 445:    */             }
/* 446:    */           }
/* 447:618 */           int end = declaration.indexOf(delimiter, start);
/* 448:619 */           encoding = declaration.substring(start, end);
/* 449:    */         }
/* 450:    */       }
/* 451:    */     }
/* 452:623 */     if ((encoding != null) && (!isSupportedCharset(encoding))) {
/* 453:624 */       encoding = null;
/* 454:    */     }
/* 455:626 */     if ((encoding != null) && (LOG.isDebugEnabled())) {
/* 456:627 */       LOG.debug("Encoding found in XML declaration: '" + encoding + "'.");
/* 457:    */     }
/* 458:629 */     return encoding;
/* 459:    */   }
/* 460:    */   
/* 461:    */   static boolean isSupportedCharset(String charset)
/* 462:    */   {
/* 463:    */     try
/* 464:    */     {
/* 465:640 */       return Charset.isSupported(charset);
/* 466:    */     }
/* 467:    */     catch (IllegalCharsetNameException e) {}
/* 468:643 */     return false;
/* 469:    */   }
/* 470:    */   
/* 471:    */   static boolean matches(byte[] bytes, int i, byte[][] sought)
/* 472:    */   {
/* 473:658 */     if (i + sought.length > bytes.length) {
/* 474:659 */       return false;
/* 475:    */     }
/* 476:661 */     for (int x = 0; x < sought.length; x++)
/* 477:    */     {
/* 478:662 */       byte[] possibilities = sought[x];
/* 479:663 */       boolean match = false;
/* 480:664 */       for (int y = 0; y < possibilities.length; y++) {
/* 481:665 */         if (bytes[(i + x)] == possibilities[y])
/* 482:    */         {
/* 483:666 */           match = true;
/* 484:667 */           break;
/* 485:    */         }
/* 486:    */       }
/* 487:670 */       if (!match) {
/* 488:671 */         return false;
/* 489:    */       }
/* 490:    */     }
/* 491:674 */     return true;
/* 492:    */   }
/* 493:    */   
/* 494:    */   static int skipToAnyOf(byte[] bytes, int i, byte[] targets)
/* 495:    */   {
/* 496:687 */     for (; i < bytes.length; i++) {
/* 497:688 */       if (ArrayUtils.contains(targets, bytes[i])) {
/* 498:    */         break;
/* 499:    */       }
/* 500:    */     }
/* 501:692 */     if (i == bytes.length) {
/* 502:693 */       i = -1;
/* 503:    */     }
/* 504:695 */     return i;
/* 505:    */   }
/* 506:    */   
/* 507:    */   static int indexOfSubArray(byte[] array, byte[] subarray, int startIndex)
/* 508:    */   {
/* 509:708 */     for (int i = startIndex; i < array.length; i++)
/* 510:    */     {
/* 511:709 */       boolean found = true;
/* 512:710 */       if (i + subarray.length > array.length) {
/* 513:    */         break;
/* 514:    */       }
/* 515:713 */       for (int j = 0; j < subarray.length; j++)
/* 516:    */       {
/* 517:714 */         byte a = array[(i + j)];
/* 518:715 */         byte b = subarray[j];
/* 519:716 */         if (a != b)
/* 520:    */         {
/* 521:717 */           found = false;
/* 522:718 */           break;
/* 523:    */         }
/* 524:    */       }
/* 525:721 */       if (found) {
/* 526:722 */         return i;
/* 527:    */       }
/* 528:    */     }
/* 529:725 */     return -1;
/* 530:    */   }
/* 531:    */   
/* 532:    */   static byte[] read(InputStream content, int size)
/* 533:    */     throws IOException
/* 534:    */   {
/* 535:739 */     byte[] bytes = new byte[size];
/* 536:740 */     int count = content.read(bytes);
/* 537:741 */     if (count == -1)
/* 538:    */     {
/* 539:742 */       bytes = new byte[0];
/* 540:    */     }
/* 541:744 */     else if (count < size)
/* 542:    */     {
/* 543:745 */       byte[] smaller = new byte[count];
/* 544:746 */       System.arraycopy(bytes, 0, smaller, 0, count);
/* 545:747 */       bytes = smaller;
/* 546:    */     }
/* 547:749 */     return bytes;
/* 548:    */   }
/* 549:    */   
/* 550:    */   static byte[] readAndPrepend(InputStream content, int size, byte[] prefix)
/* 551:    */     throws IOException
/* 552:    */   {
/* 553:765 */     byte[] bytes = read(content, size);
/* 554:766 */     byte[] joined = new byte[prefix.length + bytes.length];
/* 555:767 */     System.arraycopy(prefix, 0, joined, 0, prefix.length);
/* 556:768 */     System.arraycopy(bytes, 0, joined, prefix.length, bytes.length);
/* 557:769 */     return joined;
/* 558:    */   }
/* 559:    */   
/* 560:    */   static class Attribute
/* 561:    */   {
/* 562:    */     private final String name_;
/* 563:    */     private final String value_;
/* 564:    */     private final int updatedIndex_;
/* 565:    */     
/* 566:    */     Attribute(String name, String value, int updatedIndex)
/* 567:    */     {
/* 568:777 */       this.name_ = name;
/* 569:778 */       this.value_ = value;
/* 570:779 */       this.updatedIndex_ = updatedIndex;
/* 571:    */     }
/* 572:    */     
/* 573:    */     String getName()
/* 574:    */     {
/* 575:782 */       return this.name_;
/* 576:    */     }
/* 577:    */     
/* 578:    */     String getValue()
/* 579:    */     {
/* 580:785 */       return this.value_;
/* 581:    */     }
/* 582:    */     
/* 583:    */     int getUpdatedIndex()
/* 584:    */     {
/* 585:788 */       return this.updatedIndex_;
/* 586:    */     }
/* 587:    */   }
/* 588:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.EncodingSniffer
 * JD-Core Version:    0.7.0.1
 */