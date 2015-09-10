/*   1:    */ package org.apache.commons.lang.time;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Calendar;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.GregorianCalendar;
/*   7:    */ import java.util.TimeZone;
/*   8:    */ import org.apache.commons.lang.StringUtils;
/*   9:    */ import org.apache.commons.lang.text.StrBuilder;
/*  10:    */ 
/*  11:    */ public class DurationFormatUtils
/*  12:    */ {
/*  13:    */   public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
/*  14:    */   
/*  15:    */   public static String formatDurationHMS(long durationMillis)
/*  16:    */   {
/*  17: 82 */     return formatDuration(durationMillis, "H:mm:ss.SSS");
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static String formatDurationISO(long durationMillis)
/*  21:    */   {
/*  22: 97 */     return formatDuration(durationMillis, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static String formatDuration(long durationMillis, String format)
/*  26:    */   {
/*  27:112 */     return formatDuration(durationMillis, format, true);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static String formatDuration(long durationMillis, String format, boolean padWithZeros)
/*  31:    */   {
/*  32:130 */     Token[] tokens = lexx(format);
/*  33:    */     
/*  34:132 */     int days = 0;
/*  35:133 */     int hours = 0;
/*  36:134 */     int minutes = 0;
/*  37:135 */     int seconds = 0;
/*  38:136 */     int milliseconds = 0;
/*  39:138 */     if (Token.containsTokenWithValue(tokens, d))
/*  40:    */     {
/*  41:139 */       days = (int)(durationMillis / 86400000L);
/*  42:140 */       durationMillis -= days * 86400000L;
/*  43:    */     }
/*  44:142 */     if (Token.containsTokenWithValue(tokens, H))
/*  45:    */     {
/*  46:143 */       hours = (int)(durationMillis / 3600000L);
/*  47:144 */       durationMillis -= hours * 3600000L;
/*  48:    */     }
/*  49:146 */     if (Token.containsTokenWithValue(tokens, m))
/*  50:    */     {
/*  51:147 */       minutes = (int)(durationMillis / 60000L);
/*  52:148 */       durationMillis -= minutes * 60000L;
/*  53:    */     }
/*  54:150 */     if (Token.containsTokenWithValue(tokens, s))
/*  55:    */     {
/*  56:151 */       seconds = (int)(durationMillis / 1000L);
/*  57:152 */       durationMillis -= seconds * 1000L;
/*  58:    */     }
/*  59:154 */     if (Token.containsTokenWithValue(tokens, S)) {
/*  60:155 */       milliseconds = (int)durationMillis;
/*  61:    */     }
/*  62:158 */     return format(tokens, 0, 0, days, hours, minutes, seconds, milliseconds, padWithZeros);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static String formatDurationWords(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements)
/*  66:    */   {
/*  67:180 */     String duration = formatDuration(durationMillis, "d' days 'H' hours 'm' minutes 's' seconds'");
/*  68:181 */     if (suppressLeadingZeroElements)
/*  69:    */     {
/*  70:183 */       duration = " " + duration;
/*  71:184 */       String tmp = StringUtils.replaceOnce(duration, " 0 days", "");
/*  72:185 */       if (tmp.length() != duration.length())
/*  73:    */       {
/*  74:186 */         duration = tmp;
/*  75:187 */         tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
/*  76:188 */         if (tmp.length() != duration.length())
/*  77:    */         {
/*  78:189 */           duration = tmp;
/*  79:190 */           tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
/*  80:191 */           duration = tmp;
/*  81:192 */           if (tmp.length() != duration.length()) {
/*  82:193 */             duration = StringUtils.replaceOnce(tmp, " 0 seconds", "");
/*  83:    */           }
/*  84:    */         }
/*  85:    */       }
/*  86:197 */       if (duration.length() != 0) {
/*  87:199 */         duration = duration.substring(1);
/*  88:    */       }
/*  89:    */     }
/*  90:202 */     if (suppressTrailingZeroElements)
/*  91:    */     {
/*  92:203 */       String tmp = StringUtils.replaceOnce(duration, " 0 seconds", "");
/*  93:204 */       if (tmp.length() != duration.length())
/*  94:    */       {
/*  95:205 */         duration = tmp;
/*  96:206 */         tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
/*  97:207 */         if (tmp.length() != duration.length())
/*  98:    */         {
/*  99:208 */           duration = tmp;
/* 100:209 */           tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
/* 101:210 */           if (tmp.length() != duration.length()) {
/* 102:211 */             duration = StringUtils.replaceOnce(tmp, " 0 days", "");
/* 103:    */           }
/* 104:    */         }
/* 105:    */       }
/* 106:    */     }
/* 107:217 */     duration = " " + duration;
/* 108:218 */     duration = StringUtils.replaceOnce(duration, " 1 seconds", " 1 second");
/* 109:219 */     duration = StringUtils.replaceOnce(duration, " 1 minutes", " 1 minute");
/* 110:220 */     duration = StringUtils.replaceOnce(duration, " 1 hours", " 1 hour");
/* 111:221 */     duration = StringUtils.replaceOnce(duration, " 1 days", " 1 day");
/* 112:222 */     return duration.trim();
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static String formatPeriodISO(long startMillis, long endMillis)
/* 116:    */   {
/* 117:236 */     return formatPeriod(startMillis, endMillis, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, TimeZone.getDefault());
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static String formatPeriod(long startMillis, long endMillis, String format)
/* 121:    */   {
/* 122:249 */     return formatPeriod(startMillis, endMillis, format, true, TimeZone.getDefault());
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static String formatPeriod(long startMillis, long endMillis, String format, boolean padWithZeros, TimeZone timezone)
/* 126:    */   {
/* 127:284 */     Token[] tokens = lexx(format);
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:288 */     Calendar start = Calendar.getInstance(timezone);
/* 132:289 */     start.setTime(new Date(startMillis));
/* 133:290 */     Calendar end = Calendar.getInstance(timezone);
/* 134:291 */     end.setTime(new Date(endMillis));
/* 135:    */     
/* 136:    */ 
/* 137:294 */     int milliseconds = end.get(14) - start.get(14);
/* 138:295 */     int seconds = end.get(13) - start.get(13);
/* 139:296 */     int minutes = end.get(12) - start.get(12);
/* 140:297 */     int hours = end.get(11) - start.get(11);
/* 141:298 */     int days = end.get(5) - start.get(5);
/* 142:299 */     int months = end.get(2) - start.get(2);
/* 143:300 */     int years = end.get(1) - start.get(1);
/* 144:303 */     while (milliseconds < 0)
/* 145:    */     {
/* 146:304 */       milliseconds += 1000;
/* 147:305 */       seconds--;
/* 148:    */     }
/* 149:307 */     while (seconds < 0)
/* 150:    */     {
/* 151:308 */       seconds += 60;
/* 152:309 */       minutes--;
/* 153:    */     }
/* 154:311 */     while (minutes < 0)
/* 155:    */     {
/* 156:312 */       minutes += 60;
/* 157:313 */       hours--;
/* 158:    */     }
/* 159:315 */     while (hours < 0)
/* 160:    */     {
/* 161:316 */       hours += 24;
/* 162:317 */       days--;
/* 163:    */     }
/* 164:320 */     if (Token.containsTokenWithValue(tokens, M))
/* 165:    */     {
/* 166:321 */       while (days < 0)
/* 167:    */       {
/* 168:322 */         days += start.getActualMaximum(5);
/* 169:323 */         months--;
/* 170:324 */         start.add(2, 1);
/* 171:    */       }
/* 172:327 */       while (months < 0)
/* 173:    */       {
/* 174:328 */         months += 12;
/* 175:329 */         years--;
/* 176:    */       }
/* 177:332 */       if ((!Token.containsTokenWithValue(tokens, y)) && (years != 0)) {
/* 178:333 */         while (years != 0)
/* 179:    */         {
/* 180:334 */           months += 12 * years;
/* 181:335 */           years = 0;
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:341 */       if (!Token.containsTokenWithValue(tokens, y))
/* 188:    */       {
/* 189:342 */         int target = end.get(1);
/* 190:343 */         if (months < 0) {
/* 191:345 */           target--;
/* 192:    */         }
/* 193:348 */         while (start.get(1) != target)
/* 194:    */         {
/* 195:349 */           days += start.getActualMaximum(6) - start.get(6);
/* 196:352 */           if (((start instanceof GregorianCalendar)) && 
/* 197:353 */             (start.get(2) == 1) && (start.get(5) == 29)) {
/* 198:356 */             days++;
/* 199:    */           }
/* 200:360 */           start.add(1, 1);
/* 201:    */           
/* 202:362 */           days += start.get(6);
/* 203:    */         }
/* 204:365 */         years = 0;
/* 205:    */       }
/* 206:368 */       while (start.get(2) != end.get(2))
/* 207:    */       {
/* 208:369 */         days += start.getActualMaximum(5);
/* 209:370 */         start.add(2, 1);
/* 210:    */       }
/* 211:373 */       months = 0;
/* 212:375 */       while (days < 0)
/* 213:    */       {
/* 214:376 */         days += start.getActualMaximum(5);
/* 215:377 */         months--;
/* 216:378 */         start.add(2, 1);
/* 217:    */       }
/* 218:    */     }
/* 219:387 */     if (!Token.containsTokenWithValue(tokens, d))
/* 220:    */     {
/* 221:388 */       hours += 24 * days;
/* 222:389 */       days = 0;
/* 223:    */     }
/* 224:391 */     if (!Token.containsTokenWithValue(tokens, H))
/* 225:    */     {
/* 226:392 */       minutes += 60 * hours;
/* 227:393 */       hours = 0;
/* 228:    */     }
/* 229:395 */     if (!Token.containsTokenWithValue(tokens, m))
/* 230:    */     {
/* 231:396 */       seconds += 60 * minutes;
/* 232:397 */       minutes = 0;
/* 233:    */     }
/* 234:399 */     if (!Token.containsTokenWithValue(tokens, s))
/* 235:    */     {
/* 236:400 */       milliseconds += 1000 * seconds;
/* 237:401 */       seconds = 0;
/* 238:    */     }
/* 239:404 */     return format(tokens, years, months, days, hours, minutes, seconds, milliseconds, padWithZeros);
/* 240:    */   }
/* 241:    */   
/* 242:    */   static String format(Token[] tokens, int years, int months, int days, int hours, int minutes, int seconds, int milliseconds, boolean padWithZeros)
/* 243:    */   {
/* 244:424 */     StrBuilder buffer = new StrBuilder();
/* 245:425 */     boolean lastOutputSeconds = false;
/* 246:426 */     int sz = tokens.length;
/* 247:427 */     for (int i = 0; i < sz; i++)
/* 248:    */     {
/* 249:428 */       Token token = tokens[i];
/* 250:429 */       Object value = token.getValue();
/* 251:430 */       int count = token.getCount();
/* 252:431 */       if ((value instanceof StringBuffer))
/* 253:    */       {
/* 254:432 */         buffer.append(value.toString());
/* 255:    */       }
/* 256:434 */       else if (value == y)
/* 257:    */       {
/* 258:435 */         buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(years), count, '0') : Integer.toString(years));
/* 259:    */         
/* 260:437 */         lastOutputSeconds = false;
/* 261:    */       }
/* 262:438 */       else if (value == M)
/* 263:    */       {
/* 264:439 */         buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(months), count, '0') : Integer.toString(months));
/* 265:    */         
/* 266:441 */         lastOutputSeconds = false;
/* 267:    */       }
/* 268:442 */       else if (value == d)
/* 269:    */       {
/* 270:443 */         buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(days), count, '0') : Integer.toString(days));
/* 271:    */         
/* 272:445 */         lastOutputSeconds = false;
/* 273:    */       }
/* 274:446 */       else if (value == H)
/* 275:    */       {
/* 276:447 */         buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(hours), count, '0') : Integer.toString(hours));
/* 277:    */         
/* 278:449 */         lastOutputSeconds = false;
/* 279:    */       }
/* 280:450 */       else if (value == m)
/* 281:    */       {
/* 282:451 */         buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(minutes), count, '0') : Integer.toString(minutes));
/* 283:    */         
/* 284:453 */         lastOutputSeconds = false;
/* 285:    */       }
/* 286:454 */       else if (value == s)
/* 287:    */       {
/* 288:455 */         buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(seconds), count, '0') : Integer.toString(seconds));
/* 289:    */         
/* 290:457 */         lastOutputSeconds = true;
/* 291:    */       }
/* 292:458 */       else if (value == S)
/* 293:    */       {
/* 294:459 */         if (lastOutputSeconds)
/* 295:    */         {
/* 296:460 */           milliseconds += 1000;
/* 297:461 */           String str = padWithZeros ? StringUtils.leftPad(Integer.toString(milliseconds), count, '0') : Integer.toString(milliseconds);
/* 298:    */           
/* 299:    */ 
/* 300:464 */           buffer.append(str.substring(1));
/* 301:    */         }
/* 302:    */         else
/* 303:    */         {
/* 304:466 */           buffer.append(padWithZeros ? StringUtils.leftPad(Integer.toString(milliseconds), count, '0') : Integer.toString(milliseconds));
/* 305:    */         }
/* 306:470 */         lastOutputSeconds = false;
/* 307:    */       }
/* 308:    */     }
/* 309:474 */     return buffer.toString();
/* 310:    */   }
/* 311:    */   
/* 312:477 */   static final Object y = "y";
/* 313:478 */   static final Object M = "M";
/* 314:479 */   static final Object d = "d";
/* 315:480 */   static final Object H = "H";
/* 316:481 */   static final Object m = "m";
/* 317:482 */   static final Object s = "s";
/* 318:483 */   static final Object S = "S";
/* 319:    */   
/* 320:    */   static Token[] lexx(String format)
/* 321:    */   {
/* 322:492 */     char[] array = format.toCharArray();
/* 323:493 */     ArrayList list = new ArrayList(array.length);
/* 324:    */     
/* 325:495 */     boolean inLiteral = false;
/* 326:496 */     StringBuffer buffer = null;
/* 327:497 */     Token previous = null;
/* 328:498 */     int sz = array.length;
/* 329:499 */     for (int i = 0; i < sz; i++)
/* 330:    */     {
/* 331:500 */       char ch = array[i];
/* 332:501 */       if ((inLiteral) && (ch != '\''))
/* 333:    */       {
/* 334:502 */         buffer.append(ch);
/* 335:    */       }
/* 336:    */       else
/* 337:    */       {
/* 338:505 */         Object value = null;
/* 339:506 */         switch (ch)
/* 340:    */         {
/* 341:    */         case '\'': 
/* 342:509 */           if (inLiteral)
/* 343:    */           {
/* 344:510 */             buffer = null;
/* 345:511 */             inLiteral = false;
/* 346:    */           }
/* 347:    */           else
/* 348:    */           {
/* 349:513 */             buffer = new StringBuffer();
/* 350:514 */             list.add(new Token(buffer));
/* 351:515 */             inLiteral = true;
/* 352:    */           }
/* 353:517 */           break;
/* 354:    */         case 'y': 
/* 355:518 */           value = y; break;
/* 356:    */         case 'M': 
/* 357:519 */           value = M; break;
/* 358:    */         case 'd': 
/* 359:520 */           value = d; break;
/* 360:    */         case 'H': 
/* 361:521 */           value = H; break;
/* 362:    */         case 'm': 
/* 363:522 */           value = m; break;
/* 364:    */         case 's': 
/* 365:523 */           value = s; break;
/* 366:    */         case 'S': 
/* 367:524 */           value = S; break;
/* 368:    */         default: 
/* 369:526 */           if (buffer == null)
/* 370:    */           {
/* 371:527 */             buffer = new StringBuffer();
/* 372:528 */             list.add(new Token(buffer));
/* 373:    */           }
/* 374:530 */           buffer.append(ch);
/* 375:    */         }
/* 376:533 */         if (value != null)
/* 377:    */         {
/* 378:534 */           if ((previous != null) && (previous.getValue() == value))
/* 379:    */           {
/* 380:535 */             previous.increment();
/* 381:    */           }
/* 382:    */           else
/* 383:    */           {
/* 384:537 */             Token token = new Token(value);
/* 385:538 */             list.add(token);
/* 386:539 */             previous = token;
/* 387:    */           }
/* 388:541 */           buffer = null;
/* 389:    */         }
/* 390:    */       }
/* 391:    */     }
/* 392:544 */     return (Token[])list.toArray(new Token[list.size()]);
/* 393:    */   }
/* 394:    */   
/* 395:    */   static class Token
/* 396:    */   {
/* 397:    */     private Object value;
/* 398:    */     private int count;
/* 399:    */     
/* 400:    */     static boolean containsTokenWithValue(Token[] tokens, Object value)
/* 401:    */     {
/* 402:560 */       int sz = tokens.length;
/* 403:561 */       for (int i = 0; i < sz; i++) {
/* 404:562 */         if (tokens[i].getValue() == value) {
/* 405:563 */           return true;
/* 406:    */         }
/* 407:    */       }
/* 408:566 */       return false;
/* 409:    */     }
/* 410:    */     
/* 411:    */     Token(Object value)
/* 412:    */     {
/* 413:578 */       this.value = value;
/* 414:579 */       this.count = 1;
/* 415:    */     }
/* 416:    */     
/* 417:    */     Token(Object value, int count)
/* 418:    */     {
/* 419:590 */       this.value = value;
/* 420:591 */       this.count = count;
/* 421:    */     }
/* 422:    */     
/* 423:    */     void increment()
/* 424:    */     {
/* 425:598 */       this.count += 1;
/* 426:    */     }
/* 427:    */     
/* 428:    */     int getCount()
/* 429:    */     {
/* 430:607 */       return this.count;
/* 431:    */     }
/* 432:    */     
/* 433:    */     Object getValue()
/* 434:    */     {
/* 435:616 */       return this.value;
/* 436:    */     }
/* 437:    */     
/* 438:    */     public boolean equals(Object obj2)
/* 439:    */     {
/* 440:626 */       if ((obj2 instanceof Token))
/* 441:    */       {
/* 442:627 */         Token tok2 = (Token)obj2;
/* 443:628 */         if (this.value.getClass() != tok2.value.getClass()) {
/* 444:629 */           return false;
/* 445:    */         }
/* 446:631 */         if (this.count != tok2.count) {
/* 447:632 */           return false;
/* 448:    */         }
/* 449:634 */         if ((this.value instanceof StringBuffer)) {
/* 450:635 */           return this.value.toString().equals(tok2.value.toString());
/* 451:    */         }
/* 452:636 */         if ((this.value instanceof Number)) {
/* 453:637 */           return this.value.equals(tok2.value);
/* 454:    */         }
/* 455:639 */         return this.value == tok2.value;
/* 456:    */       }
/* 457:642 */       return false;
/* 458:    */     }
/* 459:    */     
/* 460:    */     public int hashCode()
/* 461:    */     {
/* 462:653 */       return this.value.hashCode();
/* 463:    */     }
/* 464:    */     
/* 465:    */     public String toString()
/* 466:    */     {
/* 467:662 */       return StringUtils.repeat(this.value.toString(), this.count);
/* 468:    */     }
/* 469:    */   }
/* 470:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.time.DurationFormatUtils
 * JD-Core Version:    0.7.0.1
 */