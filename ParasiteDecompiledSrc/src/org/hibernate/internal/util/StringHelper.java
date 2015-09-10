/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   9:    */ 
/*  10:    */ public final class StringHelper
/*  11:    */ {
/*  12:    */   private static final int ALIAS_TRUNCATE_LENGTH = 10;
/*  13:    */   public static final String WHITESPACE = " \n\r\f\t";
/*  14:    */   
/*  15:    */   public static int lastIndexOfLetter(String string)
/*  16:    */   {
/*  17: 51 */     for (int i = 0; i < string.length(); i++)
/*  18:    */     {
/*  19: 52 */       char character = string.charAt(i);
/*  20: 53 */       if (!Character.isLetter(character)) {
/*  21: 53 */         return i - 1;
/*  22:    */       }
/*  23:    */     }
/*  24: 55 */     return string.length() - 1;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static String join(String seperator, String[] strings)
/*  28:    */   {
/*  29: 59 */     int length = strings.length;
/*  30: 60 */     if (length == 0) {
/*  31: 60 */       return "";
/*  32:    */     }
/*  33: 61 */     StringBuilder buf = new StringBuilder(length * strings[0].length()).append(strings[0]);
/*  34: 63 */     for (int i = 1; i < length; i++) {
/*  35: 64 */       buf.append(seperator).append(strings[i]);
/*  36:    */     }
/*  37: 66 */     return buf.toString();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static String join(String seperator, Iterator objects)
/*  41:    */   {
/*  42: 70 */     StringBuilder buf = new StringBuilder();
/*  43: 71 */     if (objects.hasNext()) {
/*  44: 71 */       buf.append(objects.next());
/*  45:    */     }
/*  46: 72 */     while (objects.hasNext()) {
/*  47: 73 */       buf.append(seperator).append(objects.next());
/*  48:    */     }
/*  49: 75 */     return buf.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static String[] add(String[] x, String sep, String[] y)
/*  53:    */   {
/*  54: 79 */     String[] result = new String[x.length];
/*  55: 80 */     for (int i = 0; i < x.length; i++) {
/*  56: 81 */       result[i] = (x[i] + sep + y[i]);
/*  57:    */     }
/*  58: 83 */     return result;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static String repeat(String string, int times)
/*  62:    */   {
/*  63: 87 */     StringBuilder buf = new StringBuilder(string.length() * times);
/*  64: 88 */     for (int i = 0; i < times; i++) {
/*  65: 88 */       buf.append(string);
/*  66:    */     }
/*  67: 89 */     return buf.toString();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static String repeat(char character, int times)
/*  71:    */   {
/*  72: 93 */     char[] buffer = new char[times];
/*  73: 94 */     Arrays.fill(buffer, character);
/*  74: 95 */     return new String(buffer);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static String replace(String template, String placeholder, String replacement)
/*  78:    */   {
/*  79:100 */     return replace(template, placeholder, replacement, false);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static String[] replace(String[] templates, String placeholder, String replacement)
/*  83:    */   {
/*  84:104 */     String[] result = new String[templates.length];
/*  85:105 */     for (int i = 0; i < templates.length; i++) {
/*  86:106 */       result[i] = replace(templates[i], placeholder, replacement);
/*  87:    */     }
/*  88:108 */     return result;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static String replace(String template, String placeholder, String replacement, boolean wholeWords)
/*  92:    */   {
/*  93:112 */     return replace(template, placeholder, replacement, wholeWords, false);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static String replace(String template, String placeholder, String replacement, boolean wholeWords, boolean encloseInParensIfNecessary)
/*  97:    */   {
/*  98:120 */     if (template == null) {
/*  99:121 */       return template;
/* 100:    */     }
/* 101:123 */     int loc = template.indexOf(placeholder);
/* 102:124 */     if (loc < 0) {
/* 103:125 */       return template;
/* 104:    */     }
/* 105:128 */     String beforePlaceholder = template.substring(0, loc);
/* 106:129 */     String afterPlaceholder = template.substring(loc + placeholder.length());
/* 107:130 */     return replace(beforePlaceholder, afterPlaceholder, placeholder, replacement, wholeWords, encloseInParensIfNecessary);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static String replace(String beforePlaceholder, String afterPlaceholder, String placeholder, String replacement, boolean wholeWords, boolean encloseInParensIfNecessary)
/* 111:    */   {
/* 112:141 */     boolean actuallyReplace = (!wholeWords) || (afterPlaceholder.length() == 0) || (!Character.isJavaIdentifierPart(afterPlaceholder.charAt(0)));
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:145 */     boolean encloseInParens = (actuallyReplace) && (encloseInParensIfNecessary) && (getLastNonWhitespaceCharacter(beforePlaceholder) != '(') && (getFirstNonWhitespaceCharacter(afterPlaceholder) != ')');
/* 117:    */     
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:150 */     StringBuilder buf = new StringBuilder(beforePlaceholder);
/* 122:151 */     if (encloseInParens) {
/* 123:152 */       buf.append('(');
/* 124:    */     }
/* 125:154 */     buf.append(actuallyReplace ? replacement : placeholder);
/* 126:155 */     if (encloseInParens) {
/* 127:156 */       buf.append(')');
/* 128:    */     }
/* 129:158 */     buf.append(replace(afterPlaceholder, placeholder, replacement, wholeWords, encloseInParensIfNecessary));
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:167 */     return buf.toString();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static char getLastNonWhitespaceCharacter(String str)
/* 142:    */   {
/* 143:171 */     if ((str != null) && (str.length() > 0)) {
/* 144:172 */       for (int i = str.length() - 1; i >= 0; i--)
/* 145:    */       {
/* 146:173 */         char ch = str.charAt(i);
/* 147:174 */         if (!Character.isWhitespace(ch)) {
/* 148:175 */           return ch;
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:179 */     return '\000';
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static char getFirstNonWhitespaceCharacter(String str)
/* 156:    */   {
/* 157:183 */     if ((str != null) && (str.length() > 0)) {
/* 158:184 */       for (int i = 0; i < str.length(); i++)
/* 159:    */       {
/* 160:185 */         char ch = str.charAt(i);
/* 161:186 */         if (!Character.isWhitespace(ch)) {
/* 162:187 */           return ch;
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:191 */     return '\000';
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static String replaceOnce(String template, String placeholder, String replacement)
/* 170:    */   {
/* 171:195 */     if (template == null) {
/* 172:196 */       return template;
/* 173:    */     }
/* 174:198 */     int loc = template.indexOf(placeholder);
/* 175:199 */     if (loc < 0) {
/* 176:200 */       return template;
/* 177:    */     }
/* 178:203 */     return template.substring(0, loc) + replacement + template.substring(loc + placeholder.length());
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static String[] split(String seperators, String list)
/* 182:    */   {
/* 183:212 */     return split(seperators, list, false);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public static String[] split(String seperators, String list, boolean include)
/* 187:    */   {
/* 188:216 */     StringTokenizer tokens = new StringTokenizer(list, seperators, include);
/* 189:217 */     String[] result = new String[tokens.countTokens()];
/* 190:218 */     int i = 0;
/* 191:219 */     while (tokens.hasMoreTokens()) {
/* 192:220 */       result[(i++)] = tokens.nextToken();
/* 193:    */     }
/* 194:222 */     return result;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static String unqualify(String qualifiedName)
/* 198:    */   {
/* 199:226 */     int loc = qualifiedName.lastIndexOf(".");
/* 200:227 */     return loc < 0 ? qualifiedName : qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static String qualifier(String qualifiedName)
/* 204:    */   {
/* 205:231 */     int loc = qualifiedName.lastIndexOf(".");
/* 206:232 */     return loc < 0 ? "" : qualifiedName.substring(0, loc);
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static String collapse(String name)
/* 210:    */   {
/* 211:244 */     if (name == null) {
/* 212:245 */       return null;
/* 213:    */     }
/* 214:247 */     int breakPoint = name.lastIndexOf('.');
/* 215:248 */     if (breakPoint < 0) {
/* 216:249 */       return name;
/* 217:    */     }
/* 218:251 */     return collapseQualifier(name.substring(0, breakPoint), true) + name.substring(breakPoint);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public static String collapseQualifier(String qualifier, boolean includeDots)
/* 222:    */   {
/* 223:263 */     StringTokenizer tokenizer = new StringTokenizer(qualifier, ".");
/* 224:264 */     String collapsed = Character.toString(tokenizer.nextToken().charAt(0));
/* 225:265 */     while (tokenizer.hasMoreTokens())
/* 226:    */     {
/* 227:266 */       if (includeDots) {
/* 228:267 */         collapsed = collapsed + '.';
/* 229:    */       }
/* 230:269 */       collapsed = collapsed + tokenizer.nextToken().charAt(0);
/* 231:    */     }
/* 232:271 */     return collapsed;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static String partiallyUnqualify(String name, String qualifierBase)
/* 236:    */   {
/* 237:284 */     if ((name == null) || (!name.startsWith(qualifierBase))) {
/* 238:285 */       return name;
/* 239:    */     }
/* 240:287 */     return name.substring(qualifierBase.length() + 1);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public static String collapseQualifierBase(String name, String qualifierBase)
/* 244:    */   {
/* 245:301 */     if ((name == null) || (!name.startsWith(qualifierBase))) {
/* 246:302 */       return collapse(name);
/* 247:    */     }
/* 248:304 */     return collapseQualifier(qualifierBase, true) + name.substring(qualifierBase.length());
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static String[] suffix(String[] columns, String suffix)
/* 252:    */   {
/* 253:308 */     if (suffix == null) {
/* 254:308 */       return columns;
/* 255:    */     }
/* 256:309 */     String[] qualified = new String[columns.length];
/* 257:310 */     for (int i = 0; i < columns.length; i++) {
/* 258:311 */       qualified[i] = suffix(columns[i], suffix);
/* 259:    */     }
/* 260:313 */     return qualified;
/* 261:    */   }
/* 262:    */   
/* 263:    */   private static String suffix(String name, String suffix)
/* 264:    */   {
/* 265:317 */     return name + suffix;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static String root(String qualifiedName)
/* 269:    */   {
/* 270:321 */     int loc = qualifiedName.indexOf(".");
/* 271:322 */     return loc < 0 ? qualifiedName : qualifiedName.substring(0, loc);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public static String unroot(String qualifiedName)
/* 275:    */   {
/* 276:326 */     int loc = qualifiedName.indexOf(".");
/* 277:327 */     return loc < 0 ? qualifiedName : qualifiedName.substring(loc + 1, qualifiedName.length());
/* 278:    */   }
/* 279:    */   
/* 280:    */   public static boolean booleanValue(String tfString)
/* 281:    */   {
/* 282:331 */     String trimmed = tfString.trim().toLowerCase();
/* 283:332 */     return (trimmed.equals("true")) || (trimmed.equals("t"));
/* 284:    */   }
/* 285:    */   
/* 286:    */   public static String toString(Object[] array)
/* 287:    */   {
/* 288:336 */     int len = array.length;
/* 289:337 */     if (len == 0) {
/* 290:337 */       return "";
/* 291:    */     }
/* 292:338 */     StringBuilder buf = new StringBuilder(len * 12);
/* 293:339 */     for (int i = 0; i < len - 1; i++) {
/* 294:340 */       buf.append(array[i]).append(", ");
/* 295:    */     }
/* 296:342 */     return array[(len - 1)];
/* 297:    */   }
/* 298:    */   
/* 299:    */   public static String[] multiply(String string, Iterator placeholders, Iterator replacements)
/* 300:    */   {
/* 301:346 */     String[] result = { string };
/* 302:347 */     while (placeholders.hasNext()) {
/* 303:348 */       result = multiply(result, (String)placeholders.next(), (String[])replacements.next());
/* 304:    */     }
/* 305:350 */     return result;
/* 306:    */   }
/* 307:    */   
/* 308:    */   private static String[] multiply(String[] strings, String placeholder, String[] replacements)
/* 309:    */   {
/* 310:354 */     String[] results = new String[replacements.length * strings.length];
/* 311:355 */     int n = 0;
/* 312:356 */     for (int i = 0; i < replacements.length; i++) {
/* 313:357 */       for (int j = 0; j < strings.length; j++) {
/* 314:358 */         results[(n++)] = replaceOnce(strings[j], placeholder, replacements[i]);
/* 315:    */       }
/* 316:    */     }
/* 317:361 */     return results;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public static int countUnquoted(String string, char character)
/* 321:    */   {
/* 322:365 */     if ('\'' == character) {
/* 323:366 */       throw new IllegalArgumentException("Unquoted count of quotes is invalid");
/* 324:    */     }
/* 325:368 */     if (string == null) {
/* 326:369 */       return 0;
/* 327:    */     }
/* 328:373 */     int count = 0;
/* 329:374 */     int stringLength = string.length();
/* 330:375 */     boolean inQuote = false;
/* 331:376 */     for (int indx = 0; indx < stringLength; indx++)
/* 332:    */     {
/* 333:377 */       char c = string.charAt(indx);
/* 334:378 */       if (inQuote)
/* 335:    */       {
/* 336:379 */         if ('\'' == c) {
/* 337:380 */           inQuote = false;
/* 338:    */         }
/* 339:    */       }
/* 340:383 */       else if ('\'' == c) {
/* 341:384 */         inQuote = true;
/* 342:386 */       } else if (c == character) {
/* 343:387 */         count++;
/* 344:    */       }
/* 345:    */     }
/* 346:390 */     return count;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public static int[] locateUnquoted(String string, char character)
/* 350:    */   {
/* 351:394 */     if ('\'' == character) {
/* 352:395 */       throw new IllegalArgumentException("Unquoted count of quotes is invalid");
/* 353:    */     }
/* 354:397 */     if (string == null) {
/* 355:398 */       return new int[0];
/* 356:    */     }
/* 357:401 */     ArrayList locations = new ArrayList(20);
/* 358:    */     
/* 359:    */ 
/* 360:    */ 
/* 361:    */ 
/* 362:406 */     int stringLength = string.length();
/* 363:407 */     boolean inQuote = false;
/* 364:408 */     for (int indx = 0; indx < stringLength; indx++)
/* 365:    */     {
/* 366:409 */       char c = string.charAt(indx);
/* 367:410 */       if (inQuote)
/* 368:    */       {
/* 369:411 */         if ('\'' == c) {
/* 370:412 */           inQuote = false;
/* 371:    */         }
/* 372:    */       }
/* 373:415 */       else if ('\'' == c) {
/* 374:416 */         inQuote = true;
/* 375:418 */       } else if (c == character) {
/* 376:419 */         locations.add(Integer.valueOf(indx));
/* 377:    */       }
/* 378:    */     }
/* 379:422 */     return ArrayHelper.toIntArray(locations);
/* 380:    */   }
/* 381:    */   
/* 382:    */   public static boolean isNotEmpty(String string)
/* 383:    */   {
/* 384:426 */     return (string != null) && (string.length() > 0);
/* 385:    */   }
/* 386:    */   
/* 387:    */   public static boolean isEmpty(String string)
/* 388:    */   {
/* 389:430 */     return (string == null) || (string.length() == 0);
/* 390:    */   }
/* 391:    */   
/* 392:    */   public static String qualify(String prefix, String name)
/* 393:    */   {
/* 394:434 */     if ((name == null) || (prefix == null)) {
/* 395:435 */       throw new NullPointerException();
/* 396:    */     }
/* 397:437 */     return prefix.length() + name.length() + 1 + prefix + '.' + name;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public static String[] qualify(String prefix, String[] names)
/* 401:    */   {
/* 402:445 */     if (prefix == null) {
/* 403:445 */       return names;
/* 404:    */     }
/* 405:446 */     int len = names.length;
/* 406:447 */     String[] qualified = new String[len];
/* 407:448 */     for (int i = 0; i < len; i++) {
/* 408:449 */       qualified[i] = qualify(prefix, names[i]);
/* 409:    */     }
/* 410:451 */     return qualified;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public static int firstIndexOfChar(String sqlString, String string, int startindex)
/* 414:    */   {
/* 415:455 */     int matchAt = -1;
/* 416:456 */     for (int i = 0; i < string.length(); i++)
/* 417:    */     {
/* 418:457 */       int curMatch = sqlString.indexOf(string.charAt(i), startindex);
/* 419:458 */       if (curMatch >= 0) {
/* 420:459 */         if (matchAt == -1) {
/* 421:460 */           matchAt = curMatch;
/* 422:    */         } else {
/* 423:463 */           matchAt = Math.min(matchAt, curMatch);
/* 424:    */         }
/* 425:    */       }
/* 426:    */     }
/* 427:467 */     return matchAt;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public static String truncate(String string, int length)
/* 431:    */   {
/* 432:471 */     if (string.length() <= length) {
/* 433:472 */       return string;
/* 434:    */     }
/* 435:475 */     return string.substring(0, length);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public static String generateAlias(String description)
/* 439:    */   {
/* 440:480 */     return generateAliasRoot(description) + '_';
/* 441:    */   }
/* 442:    */   
/* 443:    */   public static String generateAlias(String description, int unique)
/* 444:    */   {
/* 445:493 */     return generateAliasRoot(description) + Integer.toString(unique) + '_';
/* 446:    */   }
/* 447:    */   
/* 448:    */   private static String generateAliasRoot(String description)
/* 449:    */   {
/* 450:507 */     String result = truncate(unqualifyEntityName(description), 10).toLowerCase().replace('/', '_').replace('$', '_');
/* 451:    */     
/* 452:    */ 
/* 453:    */ 
/* 454:511 */     result = cleanAlias(result);
/* 455:512 */     if (Character.isDigit(result.charAt(result.length() - 1))) {
/* 456:513 */       return result + "x";
/* 457:    */     }
/* 458:516 */     return result;
/* 459:    */   }
/* 460:    */   
/* 461:    */   private static String cleanAlias(String alias)
/* 462:    */   {
/* 463:528 */     char[] chars = alias.toCharArray();
/* 464:530 */     if (!Character.isLetter(chars[0])) {
/* 465:531 */       for (int i = 1; i < chars.length; i++) {
/* 466:534 */         if (Character.isLetter(chars[i])) {
/* 467:535 */           return alias.substring(i);
/* 468:    */         }
/* 469:    */       }
/* 470:    */     }
/* 471:539 */     return alias;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public static String unqualifyEntityName(String entityName)
/* 475:    */   {
/* 476:543 */     String result = unqualify(entityName);
/* 477:544 */     int slashPos = result.indexOf('/');
/* 478:545 */     if (slashPos > 0) {
/* 479:546 */       result = result.substring(0, slashPos - 1);
/* 480:    */     }
/* 481:548 */     return result;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public static String toUpperCase(String str)
/* 485:    */   {
/* 486:552 */     return str == null ? null : str.toUpperCase();
/* 487:    */   }
/* 488:    */   
/* 489:    */   public static String toLowerCase(String str)
/* 490:    */   {
/* 491:556 */     return str == null ? null : str.toLowerCase();
/* 492:    */   }
/* 493:    */   
/* 494:    */   public static String moveAndToBeginning(String filter)
/* 495:    */   {
/* 496:560 */     if (filter.trim().length() > 0)
/* 497:    */     {
/* 498:561 */       filter = filter + " and ";
/* 499:562 */       if (filter.startsWith(" and ")) {
/* 500:562 */         filter = filter.substring(4);
/* 501:    */       }
/* 502:    */     }
/* 503:564 */     return filter;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public static boolean isQuoted(String name)
/* 507:    */   {
/* 508:574 */     return (name != null) && (name.length() != 0) && (name.charAt(0) == '`') && (name.charAt(name.length() - 1) == '`');
/* 509:    */   }
/* 510:    */   
/* 511:    */   public static String quote(String name)
/* 512:    */   {
/* 513:585 */     if ((isEmpty(name)) || (isQuoted(name))) {
/* 514:586 */       return name;
/* 515:    */     }
/* 516:589 */     if ((name.startsWith("\"")) && (name.endsWith("\""))) {
/* 517:590 */       name = name.substring(1, name.length() - 1);
/* 518:    */     }
/* 519:593 */     return name.length() + 2 + '`' + name + '`';
/* 520:    */   }
/* 521:    */   
/* 522:    */   public static String unquote(String name)
/* 523:    */   {
/* 524:603 */     if (isQuoted(name)) {
/* 525:604 */       return name.substring(1, name.length() - 1);
/* 526:    */     }
/* 527:607 */     return name;
/* 528:    */   }
/* 529:    */   
/* 530:    */   public static boolean isQuoted(String name, Dialect dialect)
/* 531:    */   {
/* 532:625 */     return (name != null) && (name.length() != 0) && (((name.charAt(0) == '`') && (name.charAt(name.length() - 1) == '`')) || ((name.charAt(0) == dialect.openQuote()) && (name.charAt(name.length() - 1) == dialect.closeQuote())));
/* 533:    */   }
/* 534:    */   
/* 535:    */   public static String unquote(String name, Dialect dialect)
/* 536:    */   {
/* 537:639 */     if (isQuoted(name, dialect)) {
/* 538:640 */       return name.substring(1, name.length() - 1);
/* 539:    */     }
/* 540:643 */     return name;
/* 541:    */   }
/* 542:    */   
/* 543:    */   public static String[] unquote(String[] names, Dialect dialect)
/* 544:    */   {
/* 545:656 */     if (names == null) {
/* 546:657 */       return null;
/* 547:    */     }
/* 548:659 */     String[] unquoted = new String[names.length];
/* 549:660 */     for (int i = 0; i < names.length; i++) {
/* 550:661 */       unquoted[i] = unquote(names[i], dialect);
/* 551:    */     }
/* 552:663 */     return unquoted;
/* 553:    */   }
/* 554:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.StringHelper
 * JD-Core Version:    0.7.0.1
 */