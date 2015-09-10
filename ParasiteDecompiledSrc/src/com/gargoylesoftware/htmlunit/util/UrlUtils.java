/*   1:    */ package com.gargoylesoftware.htmlunit.util;
/*   2:    */ 
/*   3:    */ import com.gargoylesoftware.htmlunit.WebAssert;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.net.MalformedURLException;
/*   6:    */ import java.net.URL;
/*   7:    */ import java.util.BitSet;
/*   8:    */ import org.apache.commons.codec.DecoderException;
/*   9:    */ import org.apache.commons.codec.net.URLCodec;
/*  10:    */ 
/*  11:    */ public final class UrlUtils
/*  12:    */ {
/*  13: 39 */   private static final BitSet PATH_ALLOWED_CHARS = new BitSet(256);
/*  14: 40 */   private static final BitSet QUERY_ALLOWED_CHARS = new BitSet(256);
/*  15: 41 */   private static final BitSet ANCHOR_ALLOWED_CHARS = new BitSet(256);
/*  16: 42 */   private static final URLCreator URL_CREATOR = URLCreator.getCreator();
/*  17:    */   
/*  18:    */   static
/*  19:    */   {
/*  20: 48 */     BitSet reserved = new BitSet(256);
/*  21: 49 */     reserved.set(59);
/*  22: 50 */     reserved.set(47);
/*  23: 51 */     reserved.set(63);
/*  24: 52 */     reserved.set(58);
/*  25: 53 */     reserved.set(64);
/*  26: 54 */     reserved.set(38);
/*  27: 55 */     reserved.set(61);
/*  28: 56 */     reserved.set(43);
/*  29: 57 */     reserved.set(36);
/*  30: 58 */     reserved.set(44);
/*  31:    */     
/*  32: 60 */     BitSet mark = new BitSet(256);
/*  33: 61 */     mark.set(45);
/*  34: 62 */     mark.set(95);
/*  35: 63 */     mark.set(46);
/*  36: 64 */     mark.set(33);
/*  37: 65 */     mark.set(126);
/*  38: 66 */     mark.set(42);
/*  39: 67 */     mark.set(39);
/*  40: 68 */     mark.set(40);
/*  41: 69 */     mark.set(41);
/*  42:    */     
/*  43: 71 */     BitSet alpha = new BitSet(256);
/*  44: 72 */     for (int i = 97; i <= 122; i++) {
/*  45: 73 */       alpha.set(i);
/*  46:    */     }
/*  47: 75 */     for (int i = 65; i <= 90; i++) {
/*  48: 76 */       alpha.set(i);
/*  49:    */     }
/*  50: 79 */     BitSet digit = new BitSet(256);
/*  51: 80 */     for (int i = 48; i <= 57; i++) {
/*  52: 81 */       digit.set(i);
/*  53:    */     }
/*  54: 84 */     BitSet alphanumeric = new BitSet(256);
/*  55: 85 */     alphanumeric.or(alpha);
/*  56: 86 */     alphanumeric.or(digit);
/*  57:    */     
/*  58: 88 */     BitSet unreserved = new BitSet(256);
/*  59: 89 */     unreserved.or(alphanumeric);
/*  60: 90 */     unreserved.or(mark);
/*  61:    */     
/*  62: 92 */     BitSet hex = new BitSet(256);
/*  63: 93 */     hex.or(digit);
/*  64: 94 */     for (int i = 97; i <= 102; i++) {
/*  65: 95 */       hex.set(i);
/*  66:    */     }
/*  67: 97 */     for (int i = 65; i <= 70; i++) {
/*  68: 98 */       hex.set(i);
/*  69:    */     }
/*  70:101 */     BitSet escaped = new BitSet(256);
/*  71:102 */     escaped.set(37);
/*  72:103 */     escaped.or(hex);
/*  73:    */     
/*  74:105 */     BitSet uric = new BitSet(256);
/*  75:106 */     uric.or(reserved);
/*  76:107 */     uric.or(unreserved);
/*  77:108 */     uric.or(escaped);
/*  78:    */     
/*  79:110 */     BitSet pchar = new BitSet(256);
/*  80:111 */     pchar.or(unreserved);
/*  81:112 */     pchar.or(escaped);
/*  82:113 */     pchar.set(58);
/*  83:114 */     pchar.set(64);
/*  84:115 */     pchar.set(38);
/*  85:116 */     pchar.set(61);
/*  86:117 */     pchar.set(43);
/*  87:118 */     pchar.set(36);
/*  88:119 */     pchar.set(44);
/*  89:    */     
/*  90:121 */     BitSet param = pchar;
/*  91:    */     
/*  92:123 */     BitSet segment = new BitSet(256);
/*  93:124 */     segment.or(pchar);
/*  94:125 */     segment.set(59);
/*  95:126 */     segment.or(param);
/*  96:    */     
/*  97:128 */     BitSet pathSegments = new BitSet(256);
/*  98:129 */     pathSegments.set(47);
/*  99:130 */     pathSegments.or(segment);
/* 100:    */     
/* 101:132 */     BitSet absPath = new BitSet(256);
/* 102:133 */     absPath.set(47);
/* 103:134 */     absPath.or(pathSegments);
/* 104:    */     
/* 105:136 */     BitSet allowedAbsPath = new BitSet(256);
/* 106:137 */     allowedAbsPath.or(absPath);
/* 107:    */     
/* 108:139 */     BitSet allowedFragment = new BitSet(256);
/* 109:140 */     allowedFragment.or(uric);
/* 110:    */     
/* 111:    */ 
/* 112:143 */     BitSet allowedQuery = new BitSet(256);
/* 113:144 */     allowedQuery.or(uric);
/* 114:    */     
/* 115:146 */     PATH_ALLOWED_CHARS.or(allowedAbsPath);
/* 116:147 */     QUERY_ALLOWED_CHARS.or(allowedQuery);
/* 117:148 */     ANCHOR_ALLOWED_CHARS.or(allowedFragment);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static URL toUrlSafe(String url)
/* 121:    */   {
/* 122:    */     try
/* 123:    */     {
/* 124:171 */       return toUrlUnsafe(url);
/* 125:    */     }
/* 126:    */     catch (MalformedURLException e)
/* 127:    */     {
/* 128:175 */       throw new RuntimeException(e);
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static URL toUrlUnsafe(String url)
/* 133:    */     throws MalformedURLException
/* 134:    */   {
/* 135:192 */     WebAssert.notNull("url", url);
/* 136:193 */     return URL_CREATOR.toUrlUnsafeClassic(url);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static URL encodeUrl(URL url, boolean minimalQueryEncoding)
/* 140:    */   {
/* 141:207 */     String p = url.getProtocol();
/* 142:208 */     if (("javascript".equalsIgnoreCase(p)) || ("about".equalsIgnoreCase(p)) || ("data".equalsIgnoreCase(p))) {
/* 143:210 */       return url;
/* 144:    */     }
/* 145:    */     try
/* 146:    */     {
/* 147:213 */       String path = url.getPath();
/* 148:214 */       if (path != null) {
/* 149:215 */         path = encode(path, PATH_ALLOWED_CHARS, "utf-8");
/* 150:    */       }
/* 151:217 */       String query = url.getQuery();
/* 152:218 */       if (query != null) {
/* 153:219 */         if (minimalQueryEncoding) {
/* 154:220 */           query = org.apache.commons.lang.StringUtils.replace(query, " ", "%20");
/* 155:    */         } else {
/* 156:223 */           query = encode(query, QUERY_ALLOWED_CHARS, "windows-1252");
/* 157:    */         }
/* 158:    */       }
/* 159:226 */       String anchor = url.getRef();
/* 160:227 */       if (anchor != null) {
/* 161:228 */         anchor = encode(anchor, ANCHOR_ALLOWED_CHARS, "utf-8");
/* 162:    */       }
/* 163:230 */       return createNewUrl(url.getProtocol(), url.getHost(), url.getPort(), path, anchor, query);
/* 164:    */     }
/* 165:    */     catch (MalformedURLException e)
/* 166:    */     {
/* 167:234 */       throw new RuntimeException(e);
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static String encodeAnchor(String anchor)
/* 172:    */   {
/* 173:245 */     if (anchor != null) {
/* 174:246 */       anchor = encode(anchor, ANCHOR_ALLOWED_CHARS, "utf-8");
/* 175:    */     }
/* 176:248 */     return anchor;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static String decode(String escaped)
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:259 */       byte[] bytes = escaped.getBytes("US-ASCII");
/* 184:260 */       byte[] bytes2 = URLCodec.decodeUrl(bytes);
/* 185:261 */       return new String(bytes2, "UTF-8");
/* 186:    */     }
/* 187:    */     catch (UnsupportedEncodingException e)
/* 188:    */     {
/* 189:265 */       throw new RuntimeException(e);
/* 190:    */     }
/* 191:    */     catch (DecoderException e)
/* 192:    */     {
/* 193:269 */       throw new RuntimeException(e);
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   private static String encode(String unescaped, BitSet allowed, String charset)
/* 198:    */   {
/* 199:    */     try
/* 200:    */     {
/* 201:283 */       byte[] bytes = unescaped.getBytes(charset);
/* 202:284 */       byte[] bytes2 = URLCodec.encodeUrl(allowed, bytes);
/* 203:285 */       return new String(bytes2, "US-ASCII");
/* 204:    */     }
/* 205:    */     catch (UnsupportedEncodingException e)
/* 206:    */     {
/* 207:289 */       throw new RuntimeException(e);
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static URL getUrlWithNewProtocol(URL u, String newProtocol)
/* 212:    */     throws MalformedURLException
/* 213:    */   {
/* 214:301 */     return createNewUrl(newProtocol, u.getHost(), u.getPort(), u.getPath(), u.getRef(), u.getQuery());
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static URL getUrlWithNewHost(URL u, String newHost)
/* 218:    */     throws MalformedURLException
/* 219:    */   {
/* 220:312 */     return createNewUrl(u.getProtocol(), newHost, u.getPort(), u.getPath(), u.getRef(), u.getQuery());
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static URL getUrlWithNewPort(URL u, int newPort)
/* 224:    */     throws MalformedURLException
/* 225:    */   {
/* 226:323 */     return createNewUrl(u.getProtocol(), u.getHost(), newPort, u.getPath(), u.getRef(), u.getQuery());
/* 227:    */   }
/* 228:    */   
/* 229:    */   public static URL getUrlWithNewPath(URL u, String newPath)
/* 230:    */     throws MalformedURLException
/* 231:    */   {
/* 232:334 */     return createNewUrl(u.getProtocol(), u.getHost(), u.getPort(), newPath, u.getRef(), u.getQuery());
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static URL getUrlWithNewRef(URL u, String newRef)
/* 236:    */     throws MalformedURLException
/* 237:    */   {
/* 238:345 */     return createNewUrl(u.getProtocol(), u.getHost(), u.getPort(), u.getPath(), newRef, u.getQuery());
/* 239:    */   }
/* 240:    */   
/* 241:    */   public static URL getUrlWithNewQuery(URL u, String newQuery)
/* 242:    */     throws MalformedURLException
/* 243:    */   {
/* 244:356 */     return createNewUrl(u.getProtocol(), u.getHost(), u.getPort(), u.getPath(), u.getRef(), newQuery);
/* 245:    */   }
/* 246:    */   
/* 247:    */   private static URL createNewUrl(String protocol, String host, int port, String path, String ref, String query)
/* 248:    */     throws MalformedURLException
/* 249:    */   {
/* 250:372 */     StringBuilder s = new StringBuilder();
/* 251:373 */     s.append(protocol);
/* 252:374 */     s.append("://");
/* 253:375 */     s.append(host);
/* 254:376 */     if (port != -1) {
/* 255:377 */       s.append(":").append(port);
/* 256:    */     }
/* 257:379 */     if ((path != null) && (path.length() > 0))
/* 258:    */     {
/* 259:380 */       if ('/' != path.charAt(0)) {
/* 260:381 */         s.append("/");
/* 261:    */       }
/* 262:383 */       s.append(path);
/* 263:    */     }
/* 264:385 */     if (query != null) {
/* 265:386 */       s.append("?").append(query);
/* 266:    */     }
/* 267:388 */     if (ref != null)
/* 268:    */     {
/* 269:389 */       if ((ref.length() <= 0) || ('#' != ref.charAt(0))) {
/* 270:390 */         s.append("#");
/* 271:    */       }
/* 272:392 */       s.append(ref);
/* 273:    */     }
/* 274:394 */     URL url = new URL(s.toString());
/* 275:395 */     return url;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public static String resolveUrl(String baseUrl, String relativeUrl)
/* 279:    */   {
/* 280:408 */     if (baseUrl == null) {
/* 281:409 */       throw new IllegalArgumentException("Base URL must not be null");
/* 282:    */     }
/* 283:411 */     if (relativeUrl == null) {
/* 284:412 */       throw new IllegalArgumentException("Relative URL must not be null");
/* 285:    */     }
/* 286:414 */     Url url = resolveUrl(parseUrl(baseUrl.trim()), relativeUrl.trim());
/* 287:    */     
/* 288:416 */     return url.toString();
/* 289:    */   }
/* 290:    */   
/* 291:    */   public static String resolveUrl(URL baseUrl, String relativeUrl)
/* 292:    */   {
/* 293:429 */     if (baseUrl == null) {
/* 294:430 */       throw new IllegalArgumentException("Base URL must not be null");
/* 295:    */     }
/* 296:432 */     return resolveUrl(baseUrl.toExternalForm(), relativeUrl);
/* 297:    */   }
/* 298:    */   
/* 299:    */   private static Url parseUrl(String spec)
/* 300:    */   {
/* 301:454 */     Url url = new Url();
/* 302:455 */     int startIndex = 0;
/* 303:456 */     int endIndex = spec.length();
/* 304:    */     
/* 305:    */ 
/* 306:    */ 
/* 307:    */ 
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:472 */     int crosshatchIndex = StringUtils.indexOf(spec, '#', startIndex, endIndex);
/* 320:474 */     if (crosshatchIndex >= 0)
/* 321:    */     {
/* 322:475 */       url.fragment_ = spec.substring(crosshatchIndex + 1, endIndex);
/* 323:476 */       endIndex = crosshatchIndex;
/* 324:    */     }
/* 325:486 */     int colonIndex = StringUtils.indexOf(spec, ':', startIndex, endIndex);
/* 326:488 */     if (colonIndex > 0)
/* 327:    */     {
/* 328:489 */       String scheme = spec.substring(startIndex, colonIndex);
/* 329:490 */       if (isValidScheme(scheme))
/* 330:    */       {
/* 331:491 */         url.scheme_ = scheme;
/* 332:492 */         startIndex = colonIndex + 1;
/* 333:    */       }
/* 334:    */     }
/* 335:    */     int locationStartIndex;
/* 336:    */     int locationEndIndex;
/* 337:510 */     if (spec.startsWith("//", startIndex))
/* 338:    */     {
/* 339:511 */       int locationStartIndex = startIndex + 2;
/* 340:512 */       int locationEndIndex = StringUtils.indexOf(spec, '/', locationStartIndex, endIndex);
/* 341:513 */       if (locationEndIndex >= 0) {
/* 342:514 */         startIndex = locationEndIndex;
/* 343:    */       }
/* 344:    */     }
/* 345:    */     else
/* 346:    */     {
/* 347:518 */       locationStartIndex = -1;
/* 348:519 */       locationEndIndex = -1;
/* 349:    */     }
/* 350:530 */     int questionMarkIndex = StringUtils.indexOf(spec, '?', startIndex, endIndex);
/* 351:532 */     if (questionMarkIndex >= 0)
/* 352:    */     {
/* 353:533 */       if ((locationStartIndex >= 0) && (locationEndIndex < 0))
/* 354:    */       {
/* 355:537 */         locationEndIndex = questionMarkIndex;
/* 356:538 */         startIndex = questionMarkIndex;
/* 357:    */       }
/* 358:540 */       url.query_ = spec.substring(questionMarkIndex + 1, endIndex);
/* 359:541 */       endIndex = questionMarkIndex;
/* 360:    */     }
/* 361:551 */     int semicolonIndex = StringUtils.indexOf(spec, ';', startIndex, endIndex);
/* 362:553 */     if (semicolonIndex >= 0)
/* 363:    */     {
/* 364:554 */       if ((locationStartIndex >= 0) && (locationEndIndex < 0))
/* 365:    */       {
/* 366:558 */         locationEndIndex = semicolonIndex;
/* 367:559 */         startIndex = semicolonIndex;
/* 368:    */       }
/* 369:561 */       url.parameters_ = spec.substring(semicolonIndex + 1, endIndex);
/* 370:562 */       endIndex = semicolonIndex;
/* 371:    */     }
/* 372:572 */     if ((locationStartIndex >= 0) && (locationEndIndex < 0)) {
/* 373:575 */       locationEndIndex = endIndex;
/* 374:577 */     } else if (startIndex < endIndex) {
/* 375:578 */       url.path_ = spec.substring(startIndex, endIndex);
/* 376:    */     }
/* 377:581 */     if ((locationStartIndex >= 0) && (locationEndIndex >= 0)) {
/* 378:582 */       url.location_ = spec.substring(locationStartIndex, locationEndIndex);
/* 379:    */     }
/* 380:584 */     return url;
/* 381:    */   }
/* 382:    */   
/* 383:    */   private static boolean isValidScheme(String scheme)
/* 384:    */   {
/* 385:591 */     int length = scheme.length();
/* 386:592 */     if (length < 1) {
/* 387:593 */       return false;
/* 388:    */     }
/* 389:595 */     char c = scheme.charAt(0);
/* 390:596 */     if (!Character.isLetter(c)) {
/* 391:597 */       return false;
/* 392:    */     }
/* 393:599 */     for (int i = 1; i < length; i++)
/* 394:    */     {
/* 395:600 */       c = scheme.charAt(i);
/* 396:601 */       if ((!Character.isLetterOrDigit(c)) && (c != '.') && (c != '+') && (c != '-')) {
/* 397:602 */         return false;
/* 398:    */       }
/* 399:    */     }
/* 400:605 */     return true;
/* 401:    */   }
/* 402:    */   
/* 403:    */   private static Url resolveUrl(Url baseUrl, String relativeUrl)
/* 404:    */   {
/* 405:627 */     Url url = parseUrl(relativeUrl);
/* 406:632 */     if (baseUrl == null) {
/* 407:633 */       return url;
/* 408:    */     }
/* 409:640 */     if (relativeUrl.length() == 0) {
/* 410:641 */       return new Url(baseUrl);
/* 411:    */     }
/* 412:645 */     if (url.scheme_ != null) {
/* 413:646 */       return url;
/* 414:    */     }
/* 415:650 */     url.scheme_ = baseUrl.scheme_;
/* 416:654 */     if (url.location_ != null) {
/* 417:655 */       return url;
/* 418:    */     }
/* 419:657 */     url.location_ = baseUrl.location_;
/* 420:660 */     if ((url.path_ != null) && (url.path_.length() > 0) && ('/' == url.path_.charAt(0)))
/* 421:    */     {
/* 422:661 */       url.path_ = removeLeadingSlashPoints(url.path_);
/* 423:662 */       return url;
/* 424:    */     }
/* 425:667 */     if (url.path_ == null)
/* 426:    */     {
/* 427:668 */       url.path_ = baseUrl.path_;
/* 428:672 */       if (url.parameters_ != null) {
/* 429:673 */         return url;
/* 430:    */       }
/* 431:675 */       url.parameters_ = baseUrl.parameters_;
/* 432:679 */       if (url.query_ != null) {
/* 433:680 */         return url;
/* 434:    */       }
/* 435:682 */       url.query_ = baseUrl.query_;
/* 436:683 */       return url;
/* 437:    */     }
/* 438:690 */     String basePath = baseUrl.path_;
/* 439:691 */     String path = "";
/* 440:693 */     if (basePath != null)
/* 441:    */     {
/* 442:694 */       int lastSlashIndex = basePath.lastIndexOf('/');
/* 443:696 */       if (lastSlashIndex >= 0) {
/* 444:697 */         path = basePath.substring(0, lastSlashIndex + 1);
/* 445:    */       }
/* 446:    */     }
/* 447:    */     else
/* 448:    */     {
/* 449:701 */       path = "/";
/* 450:    */     }
/* 451:703 */     path = path.concat(url.path_);
/* 452:    */     int pathSegmentIndex;
/* 453:708 */     while ((pathSegmentIndex = path.indexOf("/./")) >= 0) {
/* 454:709 */       path = path.substring(0, pathSegmentIndex + 1).concat(path.substring(pathSegmentIndex + 3));
/* 455:    */     }
/* 456:713 */     if (path.endsWith("/.")) {
/* 457:714 */       path = path.substring(0, path.length() - 1);
/* 458:    */     }
/* 459:721 */     while ((pathSegmentIndex = path.indexOf("/../")) > 0)
/* 460:    */     {
/* 461:722 */       String pathSegment = path.substring(0, pathSegmentIndex);
/* 462:723 */       int slashIndex = pathSegment.lastIndexOf('/');
/* 463:725 */       if (slashIndex >= 0) {
/* 464:728 */         if (!"..".equals(pathSegment.substring(slashIndex))) {
/* 465:729 */           path = path.substring(0, slashIndex + 1).concat(path.substring(pathSegmentIndex + 4));
/* 466:    */         }
/* 467:    */       }
/* 468:    */     }
/* 469:735 */     if (path.endsWith("/.."))
/* 470:    */     {
/* 471:736 */       String pathSegment = path.substring(0, path.length() - 3);
/* 472:737 */       int slashIndex = pathSegment.lastIndexOf('/');
/* 473:739 */       if (slashIndex >= 0) {
/* 474:740 */         path = path.substring(0, slashIndex + 1);
/* 475:    */       }
/* 476:    */     }
/* 477:744 */     path = removeLeadingSlashPoints(path);
/* 478:    */     
/* 479:746 */     url.path_ = path;
/* 480:    */     
/* 481:    */ 
/* 482:    */ 
/* 483:750 */     return url;
/* 484:    */   }
/* 485:    */   
/* 486:    */   private static String removeLeadingSlashPoints(String path)
/* 487:    */   {
/* 488:757 */     while (path.startsWith("/..")) {
/* 489:758 */       path = path.substring(3);
/* 490:    */     }
/* 491:761 */     return path;
/* 492:    */   }
/* 493:    */   
/* 494:    */   private static class Url
/* 495:    */   {
/* 496:    */     private String scheme_;
/* 497:    */     private String location_;
/* 498:    */     private String path_;
/* 499:    */     private String parameters_;
/* 500:    */     private String query_;
/* 501:    */     private String fragment_;
/* 502:    */     
/* 503:    */     public Url() {}
/* 504:    */     
/* 505:    */     public Url(Url url)
/* 506:    */     {
/* 507:791 */       this.scheme_ = url.scheme_;
/* 508:792 */       this.location_ = url.location_;
/* 509:793 */       this.path_ = url.path_;
/* 510:794 */       this.parameters_ = url.parameters_;
/* 511:795 */       this.query_ = url.query_;
/* 512:796 */       this.fragment_ = url.fragment_;
/* 513:    */     }
/* 514:    */     
/* 515:    */     public String toString()
/* 516:    */     {
/* 517:806 */       StringBuilder sb = new StringBuilder();
/* 518:808 */       if (this.scheme_ != null)
/* 519:    */       {
/* 520:809 */         sb.append(this.scheme_);
/* 521:810 */         sb.append(':');
/* 522:    */       }
/* 523:812 */       if (this.location_ != null)
/* 524:    */       {
/* 525:813 */         sb.append("//");
/* 526:814 */         sb.append(this.location_);
/* 527:    */       }
/* 528:816 */       if (this.path_ != null) {
/* 529:817 */         sb.append(this.path_);
/* 530:    */       }
/* 531:819 */       if (this.parameters_ != null)
/* 532:    */       {
/* 533:820 */         sb.append(';');
/* 534:821 */         sb.append(this.parameters_);
/* 535:    */       }
/* 536:823 */       if (this.query_ != null)
/* 537:    */       {
/* 538:824 */         sb.append('?');
/* 539:825 */         sb.append(this.query_);
/* 540:    */       }
/* 541:827 */       if (this.fragment_ != null)
/* 542:    */       {
/* 543:828 */         sb.append('#');
/* 544:829 */         sb.append(this.fragment_);
/* 545:    */       }
/* 546:831 */       return sb.toString();
/* 547:    */     }
/* 548:    */   }
/* 549:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.gargoylesoftware.htmlunit.util.UrlUtils
 * JD-Core Version:    0.7.0.1
 */