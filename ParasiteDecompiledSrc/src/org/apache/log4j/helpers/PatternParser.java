/*   1:    */ package org.apache.log4j.helpers;
/*   2:    */ 
/*   3:    */ import java.text.DateFormat;
/*   4:    */ import java.text.SimpleDateFormat;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.apache.log4j.Layout;
/*  10:    */ import org.apache.log4j.Priority;
/*  11:    */ import org.apache.log4j.spi.LocationInfo;
/*  12:    */ import org.apache.log4j.spi.LoggingEvent;
/*  13:    */ 
/*  14:    */ public class PatternParser
/*  15:    */ {
/*  16:    */   private static final char ESCAPE_CHAR = '%';
/*  17:    */   private static final int LITERAL_STATE = 0;
/*  18:    */   private static final int CONVERTER_STATE = 1;
/*  19:    */   private static final int DOT_STATE = 3;
/*  20:    */   private static final int MIN_STATE = 4;
/*  21:    */   private static final int MAX_STATE = 5;
/*  22:    */   static final int FULL_LOCATION_CONVERTER = 1000;
/*  23:    */   static final int METHOD_LOCATION_CONVERTER = 1001;
/*  24:    */   static final int CLASS_LOCATION_CONVERTER = 1002;
/*  25:    */   static final int LINE_LOCATION_CONVERTER = 1003;
/*  26:    */   static final int FILE_LOCATION_CONVERTER = 1004;
/*  27:    */   static final int RELATIVE_TIME_CONVERTER = 2000;
/*  28:    */   static final int THREAD_CONVERTER = 2001;
/*  29:    */   static final int LEVEL_CONVERTER = 2002;
/*  30:    */   static final int NDC_CONVERTER = 2003;
/*  31:    */   static final int MESSAGE_CONVERTER = 2004;
/*  32:    */   int state;
/*  33: 68 */   protected StringBuffer currentLiteral = new StringBuffer(32);
/*  34:    */   protected int patternLength;
/*  35:    */   protected int i;
/*  36:    */   PatternConverter head;
/*  37:    */   PatternConverter tail;
/*  38: 73 */   protected FormattingInfo formattingInfo = new FormattingInfo();
/*  39:    */   protected String pattern;
/*  40:    */   
/*  41:    */   public PatternParser(String pattern)
/*  42:    */   {
/*  43: 78 */     this.pattern = pattern;
/*  44: 79 */     this.patternLength = pattern.length();
/*  45: 80 */     this.state = 0;
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void addToList(PatternConverter pc)
/*  49:    */   {
/*  50: 85 */     if (this.head == null)
/*  51:    */     {
/*  52: 86 */       this.head = (this.tail = pc);
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56: 88 */       this.tail.next = pc;
/*  57: 89 */       this.tail = pc;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected String extractOption()
/*  62:    */   {
/*  63: 95 */     if ((this.i < this.patternLength) && (this.pattern.charAt(this.i) == '{'))
/*  64:    */     {
/*  65: 96 */       int end = this.pattern.indexOf('}', this.i);
/*  66: 97 */       if (end > this.i)
/*  67:    */       {
/*  68: 98 */         String r = this.pattern.substring(this.i + 1, end);
/*  69: 99 */         this.i = (end + 1);
/*  70:100 */         return r;
/*  71:    */       }
/*  72:    */     }
/*  73:103 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected int extractPrecisionOption()
/*  77:    */   {
/*  78:112 */     String opt = extractOption();
/*  79:113 */     int r = 0;
/*  80:114 */     if (opt != null) {
/*  81:    */       try
/*  82:    */       {
/*  83:116 */         r = Integer.parseInt(opt);
/*  84:117 */         if (r <= 0)
/*  85:    */         {
/*  86:118 */           LogLog.error("Precision option (" + opt + ") isn't a positive integer.");
/*  87:    */           
/*  88:120 */           r = 0;
/*  89:    */         }
/*  90:    */       }
/*  91:    */       catch (NumberFormatException e)
/*  92:    */       {
/*  93:124 */         LogLog.error("Category option \"" + opt + "\" not a decimal integer.", e);
/*  94:    */       }
/*  95:    */     }
/*  96:127 */     return r;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public PatternConverter parse()
/* 100:    */   {
/* 101:133 */     this.i = 0;
/* 102:134 */     while (this.i < this.patternLength)
/* 103:    */     {
/* 104:135 */       char c = this.pattern.charAt(this.i++);
/* 105:136 */       switch (this.state)
/* 106:    */       {
/* 107:    */       case 0: 
/* 108:139 */         if (this.i == this.patternLength) {
/* 109:140 */           this.currentLiteral.append(c);
/* 110:143 */         } else if (c == '%') {
/* 111:145 */           switch (this.pattern.charAt(this.i))
/* 112:    */           {
/* 113:    */           case '%': 
/* 114:147 */             this.currentLiteral.append(c);
/* 115:148 */             this.i += 1;
/* 116:149 */             break;
/* 117:    */           case 'n': 
/* 118:151 */             this.currentLiteral.append(Layout.LINE_SEP);
/* 119:152 */             this.i += 1;
/* 120:153 */             break;
/* 121:    */           default: 
/* 122:155 */             if (this.currentLiteral.length() != 0) {
/* 123:156 */               addToList(new LiteralPatternConverter(this.currentLiteral.toString()));
/* 124:    */             }
/* 125:161 */             this.currentLiteral.setLength(0);
/* 126:162 */             this.currentLiteral.append(c);
/* 127:163 */             this.state = 1;
/* 128:164 */             this.formattingInfo.reset(); break;
/* 129:    */           }
/* 130:    */         } else {
/* 131:168 */           this.currentLiteral.append(c);
/* 132:    */         }
/* 133:170 */         break;
/* 134:    */       case 1: 
/* 135:172 */         this.currentLiteral.append(c);
/* 136:173 */         switch (c)
/* 137:    */         {
/* 138:    */         case '-': 
/* 139:175 */           this.formattingInfo.leftAlign = true;
/* 140:176 */           break;
/* 141:    */         case '.': 
/* 142:178 */           this.state = 3;
/* 143:179 */           break;
/* 144:    */         default: 
/* 145:181 */           if ((c >= '0') && (c <= '9'))
/* 146:    */           {
/* 147:182 */             this.formattingInfo.min = (c - '0');
/* 148:183 */             this.state = 4;
/* 149:    */           }
/* 150:    */           else
/* 151:    */           {
/* 152:186 */             finalizeConverter(c);
/* 153:    */           }
/* 154:    */           break;
/* 155:    */         }
/* 156:188 */         break;
/* 157:    */       case 4: 
/* 158:190 */         this.currentLiteral.append(c);
/* 159:191 */         if ((c >= '0') && (c <= '9')) {
/* 160:192 */           this.formattingInfo.min = (this.formattingInfo.min * 10 + (c - '0'));
/* 161:193 */         } else if (c == '.') {
/* 162:194 */           this.state = 3;
/* 163:    */         } else {
/* 164:196 */           finalizeConverter(c);
/* 165:    */         }
/* 166:198 */         break;
/* 167:    */       case 3: 
/* 168:200 */         this.currentLiteral.append(c);
/* 169:201 */         if ((c >= '0') && (c <= '9'))
/* 170:    */         {
/* 171:202 */           this.formattingInfo.max = (c - '0');
/* 172:203 */           this.state = 5;
/* 173:    */         }
/* 174:    */         else
/* 175:    */         {
/* 176:206 */           LogLog.error("Error occured in position " + this.i + ".\n Was expecting digit, instead got char \"" + c + "\".");
/* 177:    */           
/* 178:208 */           this.state = 0;
/* 179:    */         }
/* 180:210 */         break;
/* 181:    */       case 5: 
/* 182:212 */         this.currentLiteral.append(c);
/* 183:213 */         if ((c >= '0') && (c <= '9'))
/* 184:    */         {
/* 185:214 */           this.formattingInfo.max = (this.formattingInfo.max * 10 + (c - '0'));
/* 186:    */         }
/* 187:    */         else
/* 188:    */         {
/* 189:216 */           finalizeConverter(c);
/* 190:217 */           this.state = 0;
/* 191:    */         }
/* 192:    */         break;
/* 193:    */       }
/* 194:    */     }
/* 195:222 */     if (this.currentLiteral.length() != 0) {
/* 196:223 */       addToList(new LiteralPatternConverter(this.currentLiteral.toString()));
/* 197:    */     }
/* 198:226 */     return this.head;
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected void finalizeConverter(char c)
/* 202:    */   {
/* 203:231 */     PatternConverter pc = null;
/* 204:232 */     switch (c)
/* 205:    */     {
/* 206:    */     case 'c': 
/* 207:234 */       pc = new CategoryPatternConverter(this.formattingInfo, extractPrecisionOption());
/* 208:    */       
/* 209:    */ 
/* 210:    */ 
/* 211:238 */       this.currentLiteral.setLength(0);
/* 212:239 */       break;
/* 213:    */     case 'C': 
/* 214:241 */       pc = new ClassNamePatternConverter(this.formattingInfo, extractPrecisionOption());
/* 215:    */       
/* 216:    */ 
/* 217:    */ 
/* 218:245 */       this.currentLiteral.setLength(0);
/* 219:246 */       break;
/* 220:    */     case 'd': 
/* 221:248 */       String dateFormatStr = "ISO8601";
/* 222:    */       
/* 223:250 */       String dOpt = extractOption();
/* 224:251 */       if (dOpt != null) {
/* 225:252 */         dateFormatStr = dOpt;
/* 226:    */       }
/* 227:    */       DateFormat df;
/* 228:    */       DateFormat df;
/* 229:254 */       if (dateFormatStr.equalsIgnoreCase("ISO8601"))
/* 230:    */       {
/* 231:256 */         df = new ISO8601DateFormat();
/* 232:    */       }
/* 233:    */       else
/* 234:    */       {
/* 235:    */         DateFormat df;
/* 236:257 */         if (dateFormatStr.equalsIgnoreCase("ABSOLUTE"))
/* 237:    */         {
/* 238:259 */           df = new AbsoluteTimeDateFormat();
/* 239:    */         }
/* 240:    */         else
/* 241:    */         {
/* 242:    */           DateFormat df;
/* 243:260 */           if (dateFormatStr.equalsIgnoreCase("DATE")) {
/* 244:262 */             df = new DateTimeDateFormat();
/* 245:    */           } else {
/* 246:    */             try
/* 247:    */             {
/* 248:265 */               df = new SimpleDateFormat(dateFormatStr);
/* 249:    */             }
/* 250:    */             catch (IllegalArgumentException e)
/* 251:    */             {
/* 252:268 */               LogLog.error("Could not instantiate SimpleDateFormat with " + dateFormatStr, e);
/* 253:    */               
/* 254:270 */               df = (DateFormat)OptionConverter.instantiateByClassName("org.apache.log4j.helpers.ISO8601DateFormat", DateFormat.class, null);
/* 255:    */             }
/* 256:    */           }
/* 257:    */         }
/* 258:    */       }
/* 259:275 */       pc = new DatePatternConverter(this.formattingInfo, df);
/* 260:    */       
/* 261:    */ 
/* 262:278 */       this.currentLiteral.setLength(0);
/* 263:279 */       break;
/* 264:    */     case 'F': 
/* 265:281 */       pc = new LocationPatternConverter(this.formattingInfo, 1004);
/* 266:    */       
/* 267:    */ 
/* 268:    */ 
/* 269:285 */       this.currentLiteral.setLength(0);
/* 270:286 */       break;
/* 271:    */     case 'l': 
/* 272:288 */       pc = new LocationPatternConverter(this.formattingInfo, 1000);
/* 273:    */       
/* 274:    */ 
/* 275:    */ 
/* 276:292 */       this.currentLiteral.setLength(0);
/* 277:293 */       break;
/* 278:    */     case 'L': 
/* 279:295 */       pc = new LocationPatternConverter(this.formattingInfo, 1003);
/* 280:    */       
/* 281:    */ 
/* 282:    */ 
/* 283:299 */       this.currentLiteral.setLength(0);
/* 284:300 */       break;
/* 285:    */     case 'm': 
/* 286:302 */       pc = new BasicPatternConverter(this.formattingInfo, 2004);
/* 287:    */       
/* 288:    */ 
/* 289:305 */       this.currentLiteral.setLength(0);
/* 290:306 */       break;
/* 291:    */     case 'M': 
/* 292:308 */       pc = new LocationPatternConverter(this.formattingInfo, 1001);
/* 293:    */       
/* 294:    */ 
/* 295:    */ 
/* 296:312 */       this.currentLiteral.setLength(0);
/* 297:313 */       break;
/* 298:    */     case 'p': 
/* 299:315 */       pc = new BasicPatternConverter(this.formattingInfo, 2002);
/* 300:    */       
/* 301:    */ 
/* 302:318 */       this.currentLiteral.setLength(0);
/* 303:319 */       break;
/* 304:    */     case 'r': 
/* 305:321 */       pc = new BasicPatternConverter(this.formattingInfo, 2000);
/* 306:    */       
/* 307:    */ 
/* 308:    */ 
/* 309:325 */       this.currentLiteral.setLength(0);
/* 310:326 */       break;
/* 311:    */     case 't': 
/* 312:328 */       pc = new BasicPatternConverter(this.formattingInfo, 2001);
/* 313:    */       
/* 314:    */ 
/* 315:331 */       this.currentLiteral.setLength(0);
/* 316:332 */       break;
/* 317:    */     case 'x': 
/* 318:348 */       pc = new BasicPatternConverter(this.formattingInfo, 2003);
/* 319:    */       
/* 320:350 */       this.currentLiteral.setLength(0);
/* 321:351 */       break;
/* 322:    */     case 'X': 
/* 323:353 */       String xOpt = extractOption();
/* 324:354 */       pc = new MDCPatternConverter(this.formattingInfo, xOpt);
/* 325:355 */       this.currentLiteral.setLength(0);
/* 326:356 */       break;
/* 327:    */     case 'D': 
/* 328:    */     case 'E': 
/* 329:    */     case 'G': 
/* 330:    */     case 'H': 
/* 331:    */     case 'I': 
/* 332:    */     case 'J': 
/* 333:    */     case 'K': 
/* 334:    */     case 'N': 
/* 335:    */     case 'O': 
/* 336:    */     case 'P': 
/* 337:    */     case 'Q': 
/* 338:    */     case 'R': 
/* 339:    */     case 'S': 
/* 340:    */     case 'T': 
/* 341:    */     case 'U': 
/* 342:    */     case 'V': 
/* 343:    */     case 'W': 
/* 344:    */     case 'Y': 
/* 345:    */     case 'Z': 
/* 346:    */     case '[': 
/* 347:    */     case '\\': 
/* 348:    */     case ']': 
/* 349:    */     case '^': 
/* 350:    */     case '_': 
/* 351:    */     case '`': 
/* 352:    */     case 'a': 
/* 353:    */     case 'b': 
/* 354:    */     case 'e': 
/* 355:    */     case 'f': 
/* 356:    */     case 'g': 
/* 357:    */     case 'h': 
/* 358:    */     case 'i': 
/* 359:    */     case 'j': 
/* 360:    */     case 'k': 
/* 361:    */     case 'n': 
/* 362:    */     case 'o': 
/* 363:    */     case 'q': 
/* 364:    */     case 's': 
/* 365:    */     case 'u': 
/* 366:    */     case 'v': 
/* 367:    */     case 'w': 
/* 368:    */     default: 
/* 369:358 */       LogLog.error("Unexpected char [" + c + "] at position " + this.i + " in conversion patterrn.");
/* 370:    */       
/* 371:360 */       pc = new LiteralPatternConverter(this.currentLiteral.toString());
/* 372:361 */       this.currentLiteral.setLength(0);
/* 373:    */     }
/* 374:364 */     addConverter(pc);
/* 375:    */   }
/* 376:    */   
/* 377:    */   protected void addConverter(PatternConverter pc)
/* 378:    */   {
/* 379:369 */     this.currentLiteral.setLength(0);
/* 380:    */     
/* 381:371 */     addToList(pc);
/* 382:    */     
/* 383:373 */     this.state = 0;
/* 384:    */     
/* 385:375 */     this.formattingInfo.reset();
/* 386:    */   }
/* 387:    */   
/* 388:    */   private static class BasicPatternConverter
/* 389:    */     extends PatternConverter
/* 390:    */   {
/* 391:    */     int type;
/* 392:    */     
/* 393:    */     BasicPatternConverter(FormattingInfo formattingInfo, int type)
/* 394:    */     {
/* 395:386 */       super();
/* 396:387 */       this.type = type;
/* 397:    */     }
/* 398:    */     
/* 399:    */     public String convert(LoggingEvent event)
/* 400:    */     {
/* 401:392 */       switch (this.type)
/* 402:    */       {
/* 403:    */       case 2000: 
/* 404:394 */         return Long.toString(event.timeStamp - LoggingEvent.getStartTime());
/* 405:    */       case 2001: 
/* 406:396 */         return event.getThreadName();
/* 407:    */       case 2002: 
/* 408:398 */         return event.getLevel().toString();
/* 409:    */       case 2003: 
/* 410:400 */         return event.getNDC();
/* 411:    */       case 2004: 
/* 412:402 */         return event.getRenderedMessage();
/* 413:    */       }
/* 414:404 */       return null;
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   private static class LiteralPatternConverter
/* 419:    */     extends PatternConverter
/* 420:    */   {
/* 421:    */     private String literal;
/* 422:    */     
/* 423:    */     LiteralPatternConverter(String value)
/* 424:    */     {
/* 425:413 */       this.literal = value;
/* 426:    */     }
/* 427:    */     
/* 428:    */     public final void format(StringBuffer sbuf, LoggingEvent event)
/* 429:    */     {
/* 430:419 */       sbuf.append(this.literal);
/* 431:    */     }
/* 432:    */     
/* 433:    */     public String convert(LoggingEvent event)
/* 434:    */     {
/* 435:424 */       return this.literal;
/* 436:    */     }
/* 437:    */   }
/* 438:    */   
/* 439:    */   private static class DatePatternConverter
/* 440:    */     extends PatternConverter
/* 441:    */   {
/* 442:    */     private DateFormat df;
/* 443:    */     private Date date;
/* 444:    */     
/* 445:    */     DatePatternConverter(FormattingInfo formattingInfo, DateFormat df)
/* 446:    */     {
/* 447:433 */       super();
/* 448:434 */       this.date = new Date();
/* 449:435 */       this.df = df;
/* 450:    */     }
/* 451:    */     
/* 452:    */     public String convert(LoggingEvent event)
/* 453:    */     {
/* 454:440 */       this.date.setTime(event.timeStamp);
/* 455:441 */       String converted = null;
/* 456:    */       try
/* 457:    */       {
/* 458:443 */         converted = this.df.format(this.date);
/* 459:    */       }
/* 460:    */       catch (Exception ex)
/* 461:    */       {
/* 462:446 */         LogLog.error("Error occured while converting date.", ex);
/* 463:    */       }
/* 464:448 */       return converted;
/* 465:    */     }
/* 466:    */   }
/* 467:    */   
/* 468:    */   private static class MDCPatternConverter
/* 469:    */     extends PatternConverter
/* 470:    */   {
/* 471:    */     private String key;
/* 472:    */     
/* 473:    */     MDCPatternConverter(FormattingInfo formattingInfo, String key)
/* 474:    */     {
/* 475:456 */       super();
/* 476:457 */       this.key = key;
/* 477:    */     }
/* 478:    */     
/* 479:    */     public String convert(LoggingEvent event)
/* 480:    */     {
/* 481:462 */       if (this.key == null)
/* 482:    */       {
/* 483:463 */         StringBuffer buf = new StringBuffer("{");
/* 484:464 */         Map properties = event.getProperties();
/* 485:465 */         if (properties.size() > 0)
/* 486:    */         {
/* 487:466 */           Object[] keys = properties.keySet().toArray();
/* 488:467 */           Arrays.sort(keys);
/* 489:468 */           for (int i = 0; i < keys.length; i++)
/* 490:    */           {
/* 491:469 */             buf.append('{');
/* 492:470 */             buf.append(keys[i]);
/* 493:471 */             buf.append(',');
/* 494:472 */             buf.append(properties.get(keys[i]));
/* 495:473 */             buf.append('}');
/* 496:    */           }
/* 497:    */         }
/* 498:476 */         buf.append('}');
/* 499:477 */         return buf.toString();
/* 500:    */       }
/* 501:479 */       Object val = event.getMDC(this.key);
/* 502:480 */       if (val == null) {
/* 503:481 */         return null;
/* 504:    */       }
/* 505:483 */       return val.toString();
/* 506:    */     }
/* 507:    */   }
/* 508:    */   
/* 509:    */   private class LocationPatternConverter
/* 510:    */     extends PatternConverter
/* 511:    */   {
/* 512:    */     int type;
/* 513:    */     
/* 514:    */     LocationPatternConverter(FormattingInfo formattingInfo, int type)
/* 515:    */     {
/* 516:494 */       super();
/* 517:495 */       this.type = type;
/* 518:    */     }
/* 519:    */     
/* 520:    */     public String convert(LoggingEvent event)
/* 521:    */     {
/* 522:500 */       LocationInfo locationInfo = event.getLocationInformation();
/* 523:501 */       switch (this.type)
/* 524:    */       {
/* 525:    */       case 1000: 
/* 526:503 */         return locationInfo.fullInfo;
/* 527:    */       case 1001: 
/* 528:505 */         return locationInfo.getMethodName();
/* 529:    */       case 1003: 
/* 530:507 */         return locationInfo.getLineNumber();
/* 531:    */       case 1004: 
/* 532:509 */         return locationInfo.getFileName();
/* 533:    */       }
/* 534:510 */       return null;
/* 535:    */     }
/* 536:    */   }
/* 537:    */   
/* 538:    */   private static abstract class NamedPatternConverter
/* 539:    */     extends PatternConverter
/* 540:    */   {
/* 541:    */     int precision;
/* 542:    */     
/* 543:    */     NamedPatternConverter(FormattingInfo formattingInfo, int precision)
/* 544:    */     {
/* 545:519 */       super();
/* 546:520 */       this.precision = precision;
/* 547:    */     }
/* 548:    */     
/* 549:    */     abstract String getFullyQualifiedName(LoggingEvent paramLoggingEvent);
/* 550:    */     
/* 551:    */     public String convert(LoggingEvent event)
/* 552:    */     {
/* 553:528 */       String n = getFullyQualifiedName(event);
/* 554:529 */       if (this.precision <= 0) {
/* 555:530 */         return n;
/* 556:    */       }
/* 557:532 */       int len = n.length();
/* 558:    */       
/* 559:    */ 
/* 560:    */ 
/* 561:    */ 
/* 562:537 */       int end = len - 1;
/* 563:538 */       for (int i = this.precision; i > 0; i--)
/* 564:    */       {
/* 565:539 */         end = n.lastIndexOf('.', end - 1);
/* 566:540 */         if (end == -1) {
/* 567:541 */           return n;
/* 568:    */         }
/* 569:    */       }
/* 570:543 */       return n.substring(end + 1, len);
/* 571:    */     }
/* 572:    */   }
/* 573:    */   
/* 574:    */   private class ClassNamePatternConverter
/* 575:    */     extends PatternParser.NamedPatternConverter
/* 576:    */   {
/* 577:    */     ClassNamePatternConverter(FormattingInfo formattingInfo, int precision)
/* 578:    */     {
/* 579:551 */       super(precision);
/* 580:    */     }
/* 581:    */     
/* 582:    */     String getFullyQualifiedName(LoggingEvent event)
/* 583:    */     {
/* 584:555 */       return event.getLocationInformation().getClassName();
/* 585:    */     }
/* 586:    */   }
/* 587:    */   
/* 588:    */   private class CategoryPatternConverter
/* 589:    */     extends PatternParser.NamedPatternConverter
/* 590:    */   {
/* 591:    */     CategoryPatternConverter(FormattingInfo formattingInfo, int precision)
/* 592:    */     {
/* 593:562 */       super(precision);
/* 594:    */     }
/* 595:    */     
/* 596:    */     String getFullyQualifiedName(LoggingEvent event)
/* 597:    */     {
/* 598:566 */       return event.getLoggerName();
/* 599:    */     }
/* 600:    */   }
/* 601:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.helpers.PatternParser
 * JD-Core Version:    0.7.0.1
 */