/*    1:     */ package org.apache.commons.lang;
/*    2:     */ 
/*    3:     */ import java.util.ArrayList;
/*    4:     */ import java.util.Collection;
/*    5:     */ import java.util.Iterator;
/*    6:     */ import java.util.List;
/*    7:     */ import java.util.Locale;
/*    8:     */ import org.apache.commons.lang.text.StrBuilder;
/*    9:     */ 
/*   10:     */ public class StringUtils
/*   11:     */ {
/*   12:     */   public static final String EMPTY = "";
/*   13:     */   public static final int INDEX_NOT_FOUND = -1;
/*   14:     */   private static final int PAD_LIMIT = 8192;
/*   15:     */   
/*   16:     */   public static boolean isEmpty(String str)
/*   17:     */   {
/*   18: 195 */     return (str == null) || (str.length() == 0);
/*   19:     */   }
/*   20:     */   
/*   21:     */   public static boolean isNotEmpty(String str)
/*   22:     */   {
/*   23: 213 */     return !isEmpty(str);
/*   24:     */   }
/*   25:     */   
/*   26:     */   public static boolean isBlank(String str)
/*   27:     */   {
/*   28:     */     int strLen;
/*   29: 233 */     if ((str == null) || ((strLen = str.length()) == 0)) {
/*   30: 234 */       return true;
/*   31:     */     }
/*   32:     */     int strLen;
/*   33: 236 */     for (int i = 0; i < strLen; i++) {
/*   34: 237 */       if (!Character.isWhitespace(str.charAt(i))) {
/*   35: 238 */         return false;
/*   36:     */       }
/*   37:     */     }
/*   38: 241 */     return true;
/*   39:     */   }
/*   40:     */   
/*   41:     */   public static boolean isNotBlank(String str)
/*   42:     */   {
/*   43: 261 */     return !isBlank(str);
/*   44:     */   }
/*   45:     */   
/*   46:     */   /**
/*   47:     */    * @deprecated
/*   48:     */    */
/*   49:     */   public static String clean(String str)
/*   50:     */   {
/*   51: 286 */     return str == null ? "" : str.trim();
/*   52:     */   }
/*   53:     */   
/*   54:     */   public static String trim(String str)
/*   55:     */   {
/*   56: 313 */     return str == null ? null : str.trim();
/*   57:     */   }
/*   58:     */   
/*   59:     */   public static String trimToNull(String str)
/*   60:     */   {
/*   61: 339 */     String ts = trim(str);
/*   62: 340 */     return isEmpty(ts) ? null : ts;
/*   63:     */   }
/*   64:     */   
/*   65:     */   public static String trimToEmpty(String str)
/*   66:     */   {
/*   67: 365 */     return str == null ? "" : str.trim();
/*   68:     */   }
/*   69:     */   
/*   70:     */   public static String strip(String str)
/*   71:     */   {
/*   72: 393 */     return strip(str, null);
/*   73:     */   }
/*   74:     */   
/*   75:     */   public static String stripToNull(String str)
/*   76:     */   {
/*   77: 420 */     if (str == null) {
/*   78: 421 */       return null;
/*   79:     */     }
/*   80: 423 */     str = strip(str, null);
/*   81: 424 */     return str.length() == 0 ? null : str;
/*   82:     */   }
/*   83:     */   
/*   84:     */   public static String stripToEmpty(String str)
/*   85:     */   {
/*   86: 450 */     return str == null ? "" : strip(str, null);
/*   87:     */   }
/*   88:     */   
/*   89:     */   public static String strip(String str, String stripChars)
/*   90:     */   {
/*   91: 480 */     if (isEmpty(str)) {
/*   92: 481 */       return str;
/*   93:     */     }
/*   94: 483 */     str = stripStart(str, stripChars);
/*   95: 484 */     return stripEnd(str, stripChars);
/*   96:     */   }
/*   97:     */   
/*   98:     */   public static String stripStart(String str, String stripChars)
/*   99:     */   {
/*  100:     */     int strLen;
/*  101: 513 */     if ((str == null) || ((strLen = str.length()) == 0)) {
/*  102: 514 */       return str;
/*  103:     */     }
/*  104:     */     int strLen;
/*  105: 516 */     int start = 0;
/*  106: 517 */     if (stripChars == null) {
/*  107: 518 */       while ((start != strLen) && (Character.isWhitespace(str.charAt(start)))) {
/*  108: 519 */         start++;
/*  109:     */       }
/*  110:     */     }
/*  111: 521 */     if (stripChars.length() == 0) {
/*  112: 522 */       return str;
/*  113:     */     }
/*  114: 524 */     while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
/*  115: 525 */       start++;
/*  116:     */     }
/*  117: 528 */     return str.substring(start);
/*  118:     */   }
/*  119:     */   
/*  120:     */   public static String stripEnd(String str, String stripChars)
/*  121:     */   {
/*  122:     */     int end;
/*  123: 558 */     if ((str == null) || ((end = str.length()) == 0)) {
/*  124: 559 */       return str;
/*  125:     */     }
/*  126:     */     int end;
/*  127: 562 */     if (stripChars == null) {
/*  128: 563 */       while ((end != 0) && (Character.isWhitespace(str.charAt(end - 1)))) {
/*  129: 564 */         end--;
/*  130:     */       }
/*  131:     */     }
/*  132: 566 */     if (stripChars.length() == 0) {
/*  133: 567 */       return str;
/*  134:     */     }
/*  135: 569 */     while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
/*  136: 570 */       end--;
/*  137:     */     }
/*  138: 573 */     return str.substring(0, end);
/*  139:     */   }
/*  140:     */   
/*  141:     */   public static String[] stripAll(String[] strs)
/*  142:     */   {
/*  143: 598 */     return stripAll(strs, null);
/*  144:     */   }
/*  145:     */   
/*  146:     */   public static String[] stripAll(String[] strs, String stripChars)
/*  147:     */   {
/*  148:     */     int strsLen;
/*  149: 628 */     if ((strs == null) || ((strsLen = strs.length) == 0)) {
/*  150: 629 */       return strs;
/*  151:     */     }
/*  152:     */     int strsLen;
/*  153: 631 */     String[] newArr = new String[strsLen];
/*  154: 632 */     for (int i = 0; i < strsLen; i++) {
/*  155: 633 */       newArr[i] = strip(strs[i], stripChars);
/*  156:     */     }
/*  157: 635 */     return newArr;
/*  158:     */   }
/*  159:     */   
/*  160:     */   public static boolean equals(String str1, String str2)
/*  161:     */   {
/*  162: 661 */     return str1 == null ? false : str2 == null ? true : str1.equals(str2);
/*  163:     */   }
/*  164:     */   
/*  165:     */   public static boolean equalsIgnoreCase(String str1, String str2)
/*  166:     */   {
/*  167: 686 */     return str1 == null ? false : str2 == null ? true : str1.equalsIgnoreCase(str2);
/*  168:     */   }
/*  169:     */   
/*  170:     */   public static int indexOf(String str, char searchChar)
/*  171:     */   {
/*  172: 711 */     if (isEmpty(str)) {
/*  173: 712 */       return -1;
/*  174:     */     }
/*  175: 714 */     return str.indexOf(searchChar);
/*  176:     */   }
/*  177:     */   
/*  178:     */   public static int indexOf(String str, char searchChar, int startPos)
/*  179:     */   {
/*  180: 743 */     if (isEmpty(str)) {
/*  181: 744 */       return -1;
/*  182:     */     }
/*  183: 746 */     return str.indexOf(searchChar, startPos);
/*  184:     */   }
/*  185:     */   
/*  186:     */   public static int indexOf(String str, String searchStr)
/*  187:     */   {
/*  188: 773 */     if ((str == null) || (searchStr == null)) {
/*  189: 774 */       return -1;
/*  190:     */     }
/*  191: 776 */     return str.indexOf(searchStr);
/*  192:     */   }
/*  193:     */   
/*  194:     */   public static int ordinalIndexOf(String str, String searchStr, int ordinal)
/*  195:     */   {
/*  196: 813 */     return ordinalIndexOf(str, searchStr, ordinal, false);
/*  197:     */   }
/*  198:     */   
/*  199:     */   private static int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex)
/*  200:     */   {
/*  201: 831 */     if ((str == null) || (searchStr == null) || (ordinal <= 0)) {
/*  202: 832 */       return -1;
/*  203:     */     }
/*  204: 834 */     if (searchStr.length() == 0) {
/*  205: 835 */       return lastIndex ? str.length() : 0;
/*  206:     */     }
/*  207: 837 */     int found = 0;
/*  208: 838 */     int index = lastIndex ? str.length() : -1;
/*  209:     */     do
/*  210:     */     {
/*  211: 840 */       if (lastIndex) {
/*  212: 841 */         index = str.lastIndexOf(searchStr, index - 1);
/*  213:     */       } else {
/*  214: 843 */         index = str.indexOf(searchStr, index + 1);
/*  215:     */       }
/*  216: 845 */       if (index < 0) {
/*  217: 846 */         return index;
/*  218:     */       }
/*  219: 848 */       found++;
/*  220: 849 */     } while (found < ordinal);
/*  221: 850 */     return index;
/*  222:     */   }
/*  223:     */   
/*  224:     */   public static int indexOf(String str, String searchStr, int startPos)
/*  225:     */   {
/*  226: 886 */     if ((str == null) || (searchStr == null)) {
/*  227: 887 */       return -1;
/*  228:     */     }
/*  229: 890 */     if ((searchStr.length() == 0) && (startPos >= str.length())) {
/*  230: 891 */       return str.length();
/*  231:     */     }
/*  232: 893 */     return str.indexOf(searchStr, startPos);
/*  233:     */   }
/*  234:     */   
/*  235:     */   public static int indexOfIgnoreCase(String str, String searchStr)
/*  236:     */   {
/*  237: 921 */     return indexOfIgnoreCase(str, searchStr, 0);
/*  238:     */   }
/*  239:     */   
/*  240:     */   public static int indexOfIgnoreCase(String str, String searchStr, int startPos)
/*  241:     */   {
/*  242: 956 */     if ((str == null) || (searchStr == null)) {
/*  243: 957 */       return -1;
/*  244:     */     }
/*  245: 959 */     if (startPos < 0) {
/*  246: 960 */       startPos = 0;
/*  247:     */     }
/*  248: 962 */     int endLimit = str.length() - searchStr.length() + 1;
/*  249: 963 */     if (startPos > endLimit) {
/*  250: 964 */       return -1;
/*  251:     */     }
/*  252: 966 */     if (searchStr.length() == 0) {
/*  253: 967 */       return startPos;
/*  254:     */     }
/*  255: 969 */     for (int i = startPos; i < endLimit; i++) {
/*  256: 970 */       if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
/*  257: 971 */         return i;
/*  258:     */       }
/*  259:     */     }
/*  260: 974 */     return -1;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public static int lastIndexOf(String str, char searchChar)
/*  264:     */   {
/*  265: 999 */     if (isEmpty(str)) {
/*  266:1000 */       return -1;
/*  267:     */     }
/*  268:1002 */     return str.lastIndexOf(searchChar);
/*  269:     */   }
/*  270:     */   
/*  271:     */   public static int lastIndexOf(String str, char searchChar, int startPos)
/*  272:     */   {
/*  273:1033 */     if (isEmpty(str)) {
/*  274:1034 */       return -1;
/*  275:     */     }
/*  276:1036 */     return str.lastIndexOf(searchChar, startPos);
/*  277:     */   }
/*  278:     */   
/*  279:     */   public static int lastIndexOf(String str, String searchStr)
/*  280:     */   {
/*  281:1062 */     if ((str == null) || (searchStr == null)) {
/*  282:1063 */       return -1;
/*  283:     */     }
/*  284:1065 */     return str.lastIndexOf(searchStr);
/*  285:     */   }
/*  286:     */   
/*  287:     */   public static int lastOrdinalIndexOf(String str, String searchStr, int ordinal)
/*  288:     */   {
/*  289:1102 */     return ordinalIndexOf(str, searchStr, ordinal, true);
/*  290:     */   }
/*  291:     */   
/*  292:     */   public static int lastIndexOf(String str, String searchStr, int startPos)
/*  293:     */   {
/*  294:1134 */     if ((str == null) || (searchStr == null)) {
/*  295:1135 */       return -1;
/*  296:     */     }
/*  297:1137 */     return str.lastIndexOf(searchStr, startPos);
/*  298:     */   }
/*  299:     */   
/*  300:     */   public static int lastIndexOfIgnoreCase(String str, String searchStr)
/*  301:     */   {
/*  302:1163 */     if ((str == null) || (searchStr == null)) {
/*  303:1164 */       return -1;
/*  304:     */     }
/*  305:1166 */     return lastIndexOfIgnoreCase(str, searchStr, str.length());
/*  306:     */   }
/*  307:     */   
/*  308:     */   public static int lastIndexOfIgnoreCase(String str, String searchStr, int startPos)
/*  309:     */   {
/*  310:1198 */     if ((str == null) || (searchStr == null)) {
/*  311:1199 */       return -1;
/*  312:     */     }
/*  313:1201 */     if (startPos > str.length() - searchStr.length()) {
/*  314:1202 */       startPos = str.length() - searchStr.length();
/*  315:     */     }
/*  316:1204 */     if (startPos < 0) {
/*  317:1205 */       return -1;
/*  318:     */     }
/*  319:1207 */     if (searchStr.length() == 0) {
/*  320:1208 */       return startPos;
/*  321:     */     }
/*  322:1211 */     for (int i = startPos; i >= 0; i--) {
/*  323:1212 */       if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
/*  324:1213 */         return i;
/*  325:     */       }
/*  326:     */     }
/*  327:1216 */     return -1;
/*  328:     */   }
/*  329:     */   
/*  330:     */   public static boolean contains(String str, char searchChar)
/*  331:     */   {
/*  332:1241 */     if (isEmpty(str)) {
/*  333:1242 */       return false;
/*  334:     */     }
/*  335:1244 */     return str.indexOf(searchChar) >= 0;
/*  336:     */   }
/*  337:     */   
/*  338:     */   public static boolean contains(String str, String searchStr)
/*  339:     */   {
/*  340:1269 */     if ((str == null) || (searchStr == null)) {
/*  341:1270 */       return false;
/*  342:     */     }
/*  343:1272 */     return str.indexOf(searchStr) >= 0;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public static boolean containsIgnoreCase(String str, String searchStr)
/*  347:     */   {
/*  348:1299 */     if ((str == null) || (searchStr == null)) {
/*  349:1300 */       return false;
/*  350:     */     }
/*  351:1302 */     int len = searchStr.length();
/*  352:1303 */     int max = str.length() - len;
/*  353:1304 */     for (int i = 0; i <= max; i++) {
/*  354:1305 */       if (str.regionMatches(true, i, searchStr, 0, len)) {
/*  355:1306 */         return true;
/*  356:     */       }
/*  357:     */     }
/*  358:1309 */     return false;
/*  359:     */   }
/*  360:     */   
/*  361:     */   public static int indexOfAny(String str, char[] searchChars)
/*  362:     */   {
/*  363:1337 */     if ((isEmpty(str)) || (ArrayUtils.isEmpty(searchChars))) {
/*  364:1338 */       return -1;
/*  365:     */     }
/*  366:1340 */     int csLen = str.length();
/*  367:1341 */     int csLast = csLen - 1;
/*  368:1342 */     int searchLen = searchChars.length;
/*  369:1343 */     int searchLast = searchLen - 1;
/*  370:1344 */     for (int i = 0; i < csLen; i++)
/*  371:     */     {
/*  372:1345 */       char ch = str.charAt(i);
/*  373:1346 */       for (int j = 0; j < searchLen; j++) {
/*  374:1347 */         if (searchChars[j] == ch) {
/*  375:1348 */           if ((i < csLast) && (j < searchLast) && (CharUtils.isHighSurrogate(ch)))
/*  376:     */           {
/*  377:1350 */             if (searchChars[(j + 1)] == str.charAt(i + 1)) {
/*  378:1351 */               return i;
/*  379:     */             }
/*  380:     */           }
/*  381:     */           else {
/*  382:1354 */             return i;
/*  383:     */           }
/*  384:     */         }
/*  385:     */       }
/*  386:     */     }
/*  387:1359 */     return -1;
/*  388:     */   }
/*  389:     */   
/*  390:     */   public static int indexOfAny(String str, String searchChars)
/*  391:     */   {
/*  392:1385 */     if ((isEmpty(str)) || (isEmpty(searchChars))) {
/*  393:1386 */       return -1;
/*  394:     */     }
/*  395:1388 */     return indexOfAny(str, searchChars.toCharArray());
/*  396:     */   }
/*  397:     */   
/*  398:     */   public static boolean containsAny(String str, char[] searchChars)
/*  399:     */   {
/*  400:1417 */     if ((isEmpty(str)) || (ArrayUtils.isEmpty(searchChars))) {
/*  401:1418 */       return false;
/*  402:     */     }
/*  403:1420 */     int csLength = str.length();
/*  404:1421 */     int searchLength = searchChars.length;
/*  405:1422 */     int csLast = csLength - 1;
/*  406:1423 */     int searchLast = searchLength - 1;
/*  407:1424 */     for (int i = 0; i < csLength; i++)
/*  408:     */     {
/*  409:1425 */       char ch = str.charAt(i);
/*  410:1426 */       for (int j = 0; j < searchLength; j++) {
/*  411:1427 */         if (searchChars[j] == ch) {
/*  412:1428 */           if (CharUtils.isHighSurrogate(ch))
/*  413:     */           {
/*  414:1429 */             if (j == searchLast) {
/*  415:1431 */               return true;
/*  416:     */             }
/*  417:1433 */             if ((i < csLast) && (searchChars[(j + 1)] == str.charAt(i + 1))) {
/*  418:1434 */               return true;
/*  419:     */             }
/*  420:     */           }
/*  421:     */           else
/*  422:     */           {
/*  423:1438 */             return true;
/*  424:     */           }
/*  425:     */         }
/*  426:     */       }
/*  427:     */     }
/*  428:1443 */     return false;
/*  429:     */   }
/*  430:     */   
/*  431:     */   public static boolean containsAny(String str, String searchChars)
/*  432:     */   {
/*  433:1474 */     if (searchChars == null) {
/*  434:1475 */       return false;
/*  435:     */     }
/*  436:1477 */     return containsAny(str, searchChars.toCharArray());
/*  437:     */   }
/*  438:     */   
/*  439:     */   public static int indexOfAnyBut(String str, char[] searchChars)
/*  440:     */   {
/*  441:1505 */     if ((isEmpty(str)) || (ArrayUtils.isEmpty(searchChars))) {
/*  442:1506 */       return -1;
/*  443:     */     }
/*  444:1508 */     int csLen = str.length();
/*  445:1509 */     int csLast = csLen - 1;
/*  446:1510 */     int searchLen = searchChars.length;
/*  447:1511 */     int searchLast = searchLen - 1;
/*  448:     */     label121:
/*  449:1513 */     for (int i = 0; i < csLen; i++)
/*  450:     */     {
/*  451:1514 */       char ch = str.charAt(i);
/*  452:1515 */       for (int j = 0; j < searchLen; j++) {
/*  453:1516 */         if ((searchChars[j] == ch) && (
/*  454:1517 */           (i >= csLast) || (j >= searchLast) || (!CharUtils.isHighSurrogate(ch)) || 
/*  455:1518 */           (searchChars[(j + 1)] == str.charAt(i + 1)))) {
/*  456:     */           break label121;
/*  457:     */         }
/*  458:     */       }
/*  459:1526 */       return i;
/*  460:     */     }
/*  461:1528 */     return -1;
/*  462:     */   }
/*  463:     */   
/*  464:     */   public static int indexOfAnyBut(String str, String searchChars)
/*  465:     */   {
/*  466:1554 */     if ((isEmpty(str)) || (isEmpty(searchChars))) {
/*  467:1555 */       return -1;
/*  468:     */     }
/*  469:1557 */     int strLen = str.length();
/*  470:1558 */     for (int i = 0; i < strLen; i++)
/*  471:     */     {
/*  472:1559 */       char ch = str.charAt(i);
/*  473:1560 */       boolean chFound = searchChars.indexOf(ch) >= 0;
/*  474:1561 */       if ((i + 1 < strLen) && (CharUtils.isHighSurrogate(ch)))
/*  475:     */       {
/*  476:1562 */         char ch2 = str.charAt(i + 1);
/*  477:1563 */         if ((chFound) && (searchChars.indexOf(ch2) < 0)) {
/*  478:1564 */           return i;
/*  479:     */         }
/*  480:     */       }
/*  481:1567 */       else if (!chFound)
/*  482:     */       {
/*  483:1568 */         return i;
/*  484:     */       }
/*  485:     */     }
/*  486:1572 */     return -1;
/*  487:     */   }
/*  488:     */   
/*  489:     */   public static boolean containsOnly(String str, char[] valid)
/*  490:     */   {
/*  491:1600 */     if ((valid == null) || (str == null)) {
/*  492:1601 */       return false;
/*  493:     */     }
/*  494:1603 */     if (str.length() == 0) {
/*  495:1604 */       return true;
/*  496:     */     }
/*  497:1606 */     if (valid.length == 0) {
/*  498:1607 */       return false;
/*  499:     */     }
/*  500:1609 */     return indexOfAnyBut(str, valid) == -1;
/*  501:     */   }
/*  502:     */   
/*  503:     */   public static boolean containsOnly(String str, String validChars)
/*  504:     */   {
/*  505:1635 */     if ((str == null) || (validChars == null)) {
/*  506:1636 */       return false;
/*  507:     */     }
/*  508:1638 */     return containsOnly(str, validChars.toCharArray());
/*  509:     */   }
/*  510:     */   
/*  511:     */   public static boolean containsNone(String str, char[] searchChars)
/*  512:     */   {
/*  513:1666 */     if ((str == null) || (searchChars == null)) {
/*  514:1667 */       return true;
/*  515:     */     }
/*  516:1669 */     int csLen = str.length();
/*  517:1670 */     int csLast = csLen - 1;
/*  518:1671 */     int searchLen = searchChars.length;
/*  519:1672 */     int searchLast = searchLen - 1;
/*  520:1673 */     for (int i = 0; i < csLen; i++)
/*  521:     */     {
/*  522:1674 */       char ch = str.charAt(i);
/*  523:1675 */       for (int j = 0; j < searchLen; j++) {
/*  524:1676 */         if (searchChars[j] == ch) {
/*  525:1677 */           if (CharUtils.isHighSurrogate(ch))
/*  526:     */           {
/*  527:1678 */             if (j == searchLast) {
/*  528:1680 */               return false;
/*  529:     */             }
/*  530:1682 */             if ((i < csLast) && (searchChars[(j + 1)] == str.charAt(i + 1))) {
/*  531:1683 */               return false;
/*  532:     */             }
/*  533:     */           }
/*  534:     */           else
/*  535:     */           {
/*  536:1687 */             return false;
/*  537:     */           }
/*  538:     */         }
/*  539:     */       }
/*  540:     */     }
/*  541:1692 */     return true;
/*  542:     */   }
/*  543:     */   
/*  544:     */   public static boolean containsNone(String str, String invalidChars)
/*  545:     */   {
/*  546:1718 */     if ((str == null) || (invalidChars == null)) {
/*  547:1719 */       return true;
/*  548:     */     }
/*  549:1721 */     return containsNone(str, invalidChars.toCharArray());
/*  550:     */   }
/*  551:     */   
/*  552:     */   public static int indexOfAny(String str, String[] searchStrs)
/*  553:     */   {
/*  554:1753 */     if ((str == null) || (searchStrs == null)) {
/*  555:1754 */       return -1;
/*  556:     */     }
/*  557:1756 */     int sz = searchStrs.length;
/*  558:     */     
/*  559:     */ 
/*  560:1759 */     int ret = 2147483647;
/*  561:     */     
/*  562:1761 */     int tmp = 0;
/*  563:1762 */     for (int i = 0; i < sz; i++)
/*  564:     */     {
/*  565:1763 */       String search = searchStrs[i];
/*  566:1764 */       if (search != null)
/*  567:     */       {
/*  568:1767 */         tmp = str.indexOf(search);
/*  569:1768 */         if (tmp != -1) {
/*  570:1772 */           if (tmp < ret) {
/*  571:1773 */             ret = tmp;
/*  572:     */           }
/*  573:     */         }
/*  574:     */       }
/*  575:     */     }
/*  576:1777 */     return ret == 2147483647 ? -1 : ret;
/*  577:     */   }
/*  578:     */   
/*  579:     */   public static int lastIndexOfAny(String str, String[] searchStrs)
/*  580:     */   {
/*  581:1806 */     if ((str == null) || (searchStrs == null)) {
/*  582:1807 */       return -1;
/*  583:     */     }
/*  584:1809 */     int sz = searchStrs.length;
/*  585:1810 */     int ret = -1;
/*  586:1811 */     int tmp = 0;
/*  587:1812 */     for (int i = 0; i < sz; i++)
/*  588:     */     {
/*  589:1813 */       String search = searchStrs[i];
/*  590:1814 */       if (search != null)
/*  591:     */       {
/*  592:1817 */         tmp = str.lastIndexOf(search);
/*  593:1818 */         if (tmp > ret) {
/*  594:1819 */           ret = tmp;
/*  595:     */         }
/*  596:     */       }
/*  597:     */     }
/*  598:1822 */     return ret;
/*  599:     */   }
/*  600:     */   
/*  601:     */   public static String substring(String str, int start)
/*  602:     */   {
/*  603:1852 */     if (str == null) {
/*  604:1853 */       return null;
/*  605:     */     }
/*  606:1857 */     if (start < 0) {
/*  607:1858 */       start = str.length() + start;
/*  608:     */     }
/*  609:1861 */     if (start < 0) {
/*  610:1862 */       start = 0;
/*  611:     */     }
/*  612:1864 */     if (start > str.length()) {
/*  613:1865 */       return "";
/*  614:     */     }
/*  615:1868 */     return str.substring(start);
/*  616:     */   }
/*  617:     */   
/*  618:     */   public static String substring(String str, int start, int end)
/*  619:     */   {
/*  620:1907 */     if (str == null) {
/*  621:1908 */       return null;
/*  622:     */     }
/*  623:1912 */     if (end < 0) {
/*  624:1913 */       end = str.length() + end;
/*  625:     */     }
/*  626:1915 */     if (start < 0) {
/*  627:1916 */       start = str.length() + start;
/*  628:     */     }
/*  629:1920 */     if (end > str.length()) {
/*  630:1921 */       end = str.length();
/*  631:     */     }
/*  632:1925 */     if (start > end) {
/*  633:1926 */       return "";
/*  634:     */     }
/*  635:1929 */     if (start < 0) {
/*  636:1930 */       start = 0;
/*  637:     */     }
/*  638:1932 */     if (end < 0) {
/*  639:1933 */       end = 0;
/*  640:     */     }
/*  641:1936 */     return str.substring(start, end);
/*  642:     */   }
/*  643:     */   
/*  644:     */   public static String left(String str, int len)
/*  645:     */   {
/*  646:1962 */     if (str == null) {
/*  647:1963 */       return null;
/*  648:     */     }
/*  649:1965 */     if (len < 0) {
/*  650:1966 */       return "";
/*  651:     */     }
/*  652:1968 */     if (str.length() <= len) {
/*  653:1969 */       return str;
/*  654:     */     }
/*  655:1971 */     return str.substring(0, len);
/*  656:     */   }
/*  657:     */   
/*  658:     */   public static String right(String str, int len)
/*  659:     */   {
/*  660:1995 */     if (str == null) {
/*  661:1996 */       return null;
/*  662:     */     }
/*  663:1998 */     if (len < 0) {
/*  664:1999 */       return "";
/*  665:     */     }
/*  666:2001 */     if (str.length() <= len) {
/*  667:2002 */       return str;
/*  668:     */     }
/*  669:2004 */     return str.substring(str.length() - len);
/*  670:     */   }
/*  671:     */   
/*  672:     */   public static String mid(String str, int pos, int len)
/*  673:     */   {
/*  674:2033 */     if (str == null) {
/*  675:2034 */       return null;
/*  676:     */     }
/*  677:2036 */     if ((len < 0) || (pos > str.length())) {
/*  678:2037 */       return "";
/*  679:     */     }
/*  680:2039 */     if (pos < 0) {
/*  681:2040 */       pos = 0;
/*  682:     */     }
/*  683:2042 */     if (str.length() <= pos + len) {
/*  684:2043 */       return str.substring(pos);
/*  685:     */     }
/*  686:2045 */     return str.substring(pos, pos + len);
/*  687:     */   }
/*  688:     */   
/*  689:     */   public static String substringBefore(String str, String separator)
/*  690:     */   {
/*  691:2078 */     if ((isEmpty(str)) || (separator == null)) {
/*  692:2079 */       return str;
/*  693:     */     }
/*  694:2081 */     if (separator.length() == 0) {
/*  695:2082 */       return "";
/*  696:     */     }
/*  697:2084 */     int pos = str.indexOf(separator);
/*  698:2085 */     if (pos == -1) {
/*  699:2086 */       return str;
/*  700:     */     }
/*  701:2088 */     return str.substring(0, pos);
/*  702:     */   }
/*  703:     */   
/*  704:     */   public static String substringAfter(String str, String separator)
/*  705:     */   {
/*  706:2120 */     if (isEmpty(str)) {
/*  707:2121 */       return str;
/*  708:     */     }
/*  709:2123 */     if (separator == null) {
/*  710:2124 */       return "";
/*  711:     */     }
/*  712:2126 */     int pos = str.indexOf(separator);
/*  713:2127 */     if (pos == -1) {
/*  714:2128 */       return "";
/*  715:     */     }
/*  716:2130 */     return str.substring(pos + separator.length());
/*  717:     */   }
/*  718:     */   
/*  719:     */   public static String substringBeforeLast(String str, String separator)
/*  720:     */   {
/*  721:2161 */     if ((isEmpty(str)) || (isEmpty(separator))) {
/*  722:2162 */       return str;
/*  723:     */     }
/*  724:2164 */     int pos = str.lastIndexOf(separator);
/*  725:2165 */     if (pos == -1) {
/*  726:2166 */       return str;
/*  727:     */     }
/*  728:2168 */     return str.substring(0, pos);
/*  729:     */   }
/*  730:     */   
/*  731:     */   public static String substringAfterLast(String str, String separator)
/*  732:     */   {
/*  733:2201 */     if (isEmpty(str)) {
/*  734:2202 */       return str;
/*  735:     */     }
/*  736:2204 */     if (isEmpty(separator)) {
/*  737:2205 */       return "";
/*  738:     */     }
/*  739:2207 */     int pos = str.lastIndexOf(separator);
/*  740:2208 */     if ((pos == -1) || (pos == str.length() - separator.length())) {
/*  741:2209 */       return "";
/*  742:     */     }
/*  743:2211 */     return str.substring(pos + separator.length());
/*  744:     */   }
/*  745:     */   
/*  746:     */   public static String substringBetween(String str, String tag)
/*  747:     */   {
/*  748:2238 */     return substringBetween(str, tag, tag);
/*  749:     */   }
/*  750:     */   
/*  751:     */   public static String substringBetween(String str, String open, String close)
/*  752:     */   {
/*  753:2269 */     if ((str == null) || (open == null) || (close == null)) {
/*  754:2270 */       return null;
/*  755:     */     }
/*  756:2272 */     int start = str.indexOf(open);
/*  757:2273 */     if (start != -1)
/*  758:     */     {
/*  759:2274 */       int end = str.indexOf(close, start + open.length());
/*  760:2275 */       if (end != -1) {
/*  761:2276 */         return str.substring(start + open.length(), end);
/*  762:     */       }
/*  763:     */     }
/*  764:2279 */     return null;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public static String[] substringsBetween(String str, String open, String close)
/*  768:     */   {
/*  769:2305 */     if ((str == null) || (isEmpty(open)) || (isEmpty(close))) {
/*  770:2306 */       return null;
/*  771:     */     }
/*  772:2308 */     int strLen = str.length();
/*  773:2309 */     if (strLen == 0) {
/*  774:2310 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  775:     */     }
/*  776:2312 */     int closeLen = close.length();
/*  777:2313 */     int openLen = open.length();
/*  778:2314 */     List list = new ArrayList();
/*  779:2315 */     int pos = 0;
/*  780:2316 */     while (pos < strLen - closeLen)
/*  781:     */     {
/*  782:2317 */       int start = str.indexOf(open, pos);
/*  783:2318 */       if (start < 0) {
/*  784:     */         break;
/*  785:     */       }
/*  786:2321 */       start += openLen;
/*  787:2322 */       int end = str.indexOf(close, start);
/*  788:2323 */       if (end < 0) {
/*  789:     */         break;
/*  790:     */       }
/*  791:2326 */       list.add(str.substring(start, end));
/*  792:2327 */       pos = end + closeLen;
/*  793:     */     }
/*  794:2329 */     if (list.isEmpty()) {
/*  795:2330 */       return null;
/*  796:     */     }
/*  797:2332 */     return (String[])list.toArray(new String[list.size()]);
/*  798:     */   }
/*  799:     */   
/*  800:     */   /**
/*  801:     */    * @deprecated
/*  802:     */    */
/*  803:     */   public static String getNestedString(String str, String tag)
/*  804:     */   {
/*  805:2360 */     return substringBetween(str, tag, tag);
/*  806:     */   }
/*  807:     */   
/*  808:     */   /**
/*  809:     */    * @deprecated
/*  810:     */    */
/*  811:     */   public static String getNestedString(String str, String open, String close)
/*  812:     */   {
/*  813:2390 */     return substringBetween(str, open, close);
/*  814:     */   }
/*  815:     */   
/*  816:     */   public static String[] split(String str)
/*  817:     */   {
/*  818:2418 */     return split(str, null, -1);
/*  819:     */   }
/*  820:     */   
/*  821:     */   public static String[] split(String str, char separatorChar)
/*  822:     */   {
/*  823:2446 */     return splitWorker(str, separatorChar, false);
/*  824:     */   }
/*  825:     */   
/*  826:     */   public static String[] split(String str, String separatorChars)
/*  827:     */   {
/*  828:2475 */     return splitWorker(str, separatorChars, -1, false);
/*  829:     */   }
/*  830:     */   
/*  831:     */   public static String[] split(String str, String separatorChars, int max)
/*  832:     */   {
/*  833:2509 */     return splitWorker(str, separatorChars, max, false);
/*  834:     */   }
/*  835:     */   
/*  836:     */   public static String[] splitByWholeSeparator(String str, String separator)
/*  837:     */   {
/*  838:2536 */     return splitByWholeSeparatorWorker(str, separator, -1, false);
/*  839:     */   }
/*  840:     */   
/*  841:     */   public static String[] splitByWholeSeparator(String str, String separator, int max)
/*  842:     */   {
/*  843:2567 */     return splitByWholeSeparatorWorker(str, separator, max, false);
/*  844:     */   }
/*  845:     */   
/*  846:     */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator)
/*  847:     */   {
/*  848:2596 */     return splitByWholeSeparatorWorker(str, separator, -1, true);
/*  849:     */   }
/*  850:     */   
/*  851:     */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max)
/*  852:     */   {
/*  853:2629 */     return splitByWholeSeparatorWorker(str, separator, max, true);
/*  854:     */   }
/*  855:     */   
/*  856:     */   private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens)
/*  857:     */   {
/*  858:2649 */     if (str == null) {
/*  859:2650 */       return null;
/*  860:     */     }
/*  861:2653 */     int len = str.length();
/*  862:2655 */     if (len == 0) {
/*  863:2656 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  864:     */     }
/*  865:2659 */     if ((separator == null) || ("".equals(separator))) {
/*  866:2661 */       return splitWorker(str, null, max, preserveAllTokens);
/*  867:     */     }
/*  868:2664 */     int separatorLength = separator.length();
/*  869:     */     
/*  870:2666 */     ArrayList substrings = new ArrayList();
/*  871:2667 */     int numberOfSubstrings = 0;
/*  872:2668 */     int beg = 0;
/*  873:2669 */     int end = 0;
/*  874:2670 */     while (end < len)
/*  875:     */     {
/*  876:2671 */       end = str.indexOf(separator, beg);
/*  877:2673 */       if (end > -1)
/*  878:     */       {
/*  879:2674 */         if (end > beg)
/*  880:     */         {
/*  881:2675 */           numberOfSubstrings++;
/*  882:2677 */           if (numberOfSubstrings == max)
/*  883:     */           {
/*  884:2678 */             end = len;
/*  885:2679 */             substrings.add(str.substring(beg));
/*  886:     */           }
/*  887:     */           else
/*  888:     */           {
/*  889:2683 */             substrings.add(str.substring(beg, end));
/*  890:     */             
/*  891:     */ 
/*  892:     */ 
/*  893:     */ 
/*  894:2688 */             beg = end + separatorLength;
/*  895:     */           }
/*  896:     */         }
/*  897:     */         else
/*  898:     */         {
/*  899:2692 */           if (preserveAllTokens)
/*  900:     */           {
/*  901:2693 */             numberOfSubstrings++;
/*  902:2694 */             if (numberOfSubstrings == max)
/*  903:     */             {
/*  904:2695 */               end = len;
/*  905:2696 */               substrings.add(str.substring(beg));
/*  906:     */             }
/*  907:     */             else
/*  908:     */             {
/*  909:2698 */               substrings.add("");
/*  910:     */             }
/*  911:     */           }
/*  912:2701 */           beg = end + separatorLength;
/*  913:     */         }
/*  914:     */       }
/*  915:     */       else
/*  916:     */       {
/*  917:2705 */         substrings.add(str.substring(beg));
/*  918:2706 */         end = len;
/*  919:     */       }
/*  920:     */     }
/*  921:2710 */     return (String[])substrings.toArray(new String[substrings.size()]);
/*  922:     */   }
/*  923:     */   
/*  924:     */   public static String[] splitPreserveAllTokens(String str)
/*  925:     */   {
/*  926:2739 */     return splitWorker(str, null, -1, true);
/*  927:     */   }
/*  928:     */   
/*  929:     */   public static String[] splitPreserveAllTokens(String str, char separatorChar)
/*  930:     */   {
/*  931:2775 */     return splitWorker(str, separatorChar, true);
/*  932:     */   }
/*  933:     */   
/*  934:     */   private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens)
/*  935:     */   {
/*  936:2793 */     if (str == null) {
/*  937:2794 */       return null;
/*  938:     */     }
/*  939:2796 */     int len = str.length();
/*  940:2797 */     if (len == 0) {
/*  941:2798 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  942:     */     }
/*  943:2800 */     List list = new ArrayList();
/*  944:2801 */     int i = 0;int start = 0;
/*  945:2802 */     boolean match = false;
/*  946:2803 */     boolean lastMatch = false;
/*  947:2804 */     while (i < len) {
/*  948:2805 */       if (str.charAt(i) == separatorChar)
/*  949:     */       {
/*  950:2806 */         if ((match) || (preserveAllTokens))
/*  951:     */         {
/*  952:2807 */           list.add(str.substring(start, i));
/*  953:2808 */           match = false;
/*  954:2809 */           lastMatch = true;
/*  955:     */         }
/*  956:2811 */         i++;start = i;
/*  957:     */       }
/*  958:     */       else
/*  959:     */       {
/*  960:2814 */         lastMatch = false;
/*  961:2815 */         match = true;
/*  962:2816 */         i++;
/*  963:     */       }
/*  964:     */     }
/*  965:2818 */     if ((match) || ((preserveAllTokens) && (lastMatch))) {
/*  966:2819 */       list.add(str.substring(start, i));
/*  967:     */     }
/*  968:2821 */     return (String[])list.toArray(new String[list.size()]);
/*  969:     */   }
/*  970:     */   
/*  971:     */   public static String[] splitPreserveAllTokens(String str, String separatorChars)
/*  972:     */   {
/*  973:2858 */     return splitWorker(str, separatorChars, -1, true);
/*  974:     */   }
/*  975:     */   
/*  976:     */   public static String[] splitPreserveAllTokens(String str, String separatorChars, int max)
/*  977:     */   {
/*  978:2898 */     return splitWorker(str, separatorChars, max, true);
/*  979:     */   }
/*  980:     */   
/*  981:     */   private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens)
/*  982:     */   {
/*  983:2920 */     if (str == null) {
/*  984:2921 */       return null;
/*  985:     */     }
/*  986:2923 */     int len = str.length();
/*  987:2924 */     if (len == 0) {
/*  988:2925 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*  989:     */     }
/*  990:2927 */     List list = new ArrayList();
/*  991:2928 */     int sizePlus1 = 1;
/*  992:2929 */     int i = 0;int start = 0;
/*  993:2930 */     boolean match = false;
/*  994:2931 */     boolean lastMatch = false;
/*  995:2932 */     if (separatorChars == null) {
/*  996:2934 */       while (i < len) {
/*  997:2935 */         if (Character.isWhitespace(str.charAt(i)))
/*  998:     */         {
/*  999:2936 */           if ((match) || (preserveAllTokens))
/* 1000:     */           {
/* 1001:2937 */             lastMatch = true;
/* 1002:2938 */             if (sizePlus1++ == max)
/* 1003:     */             {
/* 1004:2939 */               i = len;
/* 1005:2940 */               lastMatch = false;
/* 1006:     */             }
/* 1007:2942 */             list.add(str.substring(start, i));
/* 1008:2943 */             match = false;
/* 1009:     */           }
/* 1010:2945 */           i++;start = i;
/* 1011:     */         }
/* 1012:     */         else
/* 1013:     */         {
/* 1014:2948 */           lastMatch = false;
/* 1015:2949 */           match = true;
/* 1016:2950 */           i++;
/* 1017:     */         }
/* 1018:     */       }
/* 1019:     */     }
/* 1020:2952 */     if (separatorChars.length() == 1)
/* 1021:     */     {
/* 1022:2954 */       char sep = separatorChars.charAt(0);
/* 1023:2955 */       while (i < len) {
/* 1024:2956 */         if (str.charAt(i) == sep)
/* 1025:     */         {
/* 1026:2957 */           if ((match) || (preserveAllTokens))
/* 1027:     */           {
/* 1028:2958 */             lastMatch = true;
/* 1029:2959 */             if (sizePlus1++ == max)
/* 1030:     */             {
/* 1031:2960 */               i = len;
/* 1032:2961 */               lastMatch = false;
/* 1033:     */             }
/* 1034:2963 */             list.add(str.substring(start, i));
/* 1035:2964 */             match = false;
/* 1036:     */           }
/* 1037:2966 */           i++;start = i;
/* 1038:     */         }
/* 1039:     */         else
/* 1040:     */         {
/* 1041:2969 */           lastMatch = false;
/* 1042:2970 */           match = true;
/* 1043:2971 */           i++;
/* 1044:     */         }
/* 1045:     */       }
/* 1046:     */     }
/* 1047:     */     else
/* 1048:     */     {
/* 1049:2975 */       while (i < len) {
/* 1050:2976 */         if (separatorChars.indexOf(str.charAt(i)) >= 0)
/* 1051:     */         {
/* 1052:2977 */           if ((match) || (preserveAllTokens))
/* 1053:     */           {
/* 1054:2978 */             lastMatch = true;
/* 1055:2979 */             if (sizePlus1++ == max)
/* 1056:     */             {
/* 1057:2980 */               i = len;
/* 1058:2981 */               lastMatch = false;
/* 1059:     */             }
/* 1060:2983 */             list.add(str.substring(start, i));
/* 1061:2984 */             match = false;
/* 1062:     */           }
/* 1063:2986 */           i++;start = i;
/* 1064:     */         }
/* 1065:     */         else
/* 1066:     */         {
/* 1067:2989 */           lastMatch = false;
/* 1068:2990 */           match = true;
/* 1069:2991 */           i++;
/* 1070:     */         }
/* 1071:     */       }
/* 1072:     */     }
/* 1073:2994 */     if ((match) || ((preserveAllTokens) && (lastMatch))) {
/* 1074:2995 */       list.add(str.substring(start, i));
/* 1075:     */     }
/* 1076:2997 */     return (String[])list.toArray(new String[list.size()]);
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public static String[] splitByCharacterType(String str)
/* 1080:     */   {
/* 1081:3020 */     return splitByCharacterType(str, false);
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public static String[] splitByCharacterTypeCamelCase(String str)
/* 1085:     */   {
/* 1086:3048 */     return splitByCharacterType(str, true);
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   private static String[] splitByCharacterType(String str, boolean camelCase)
/* 1090:     */   {
/* 1091:3066 */     if (str == null) {
/* 1092:3067 */       return null;
/* 1093:     */     }
/* 1094:3069 */     if (str.length() == 0) {
/* 1095:3070 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/* 1096:     */     }
/* 1097:3072 */     char[] c = str.toCharArray();
/* 1098:3073 */     List list = new ArrayList();
/* 1099:3074 */     int tokenStart = 0;
/* 1100:3075 */     int currentType = Character.getType(c[tokenStart]);
/* 1101:3076 */     for (int pos = tokenStart + 1; pos < c.length; pos++)
/* 1102:     */     {
/* 1103:3077 */       int type = Character.getType(c[pos]);
/* 1104:3078 */       if (type != currentType)
/* 1105:     */       {
/* 1106:3081 */         if ((camelCase) && (type == 2) && (currentType == 1))
/* 1107:     */         {
/* 1108:3082 */           int newTokenStart = pos - 1;
/* 1109:3083 */           if (newTokenStart != tokenStart)
/* 1110:     */           {
/* 1111:3084 */             list.add(new String(c, tokenStart, newTokenStart - tokenStart));
/* 1112:3085 */             tokenStart = newTokenStart;
/* 1113:     */           }
/* 1114:     */         }
/* 1115:     */         else
/* 1116:     */         {
/* 1117:3088 */           list.add(new String(c, tokenStart, pos - tokenStart));
/* 1118:3089 */           tokenStart = pos;
/* 1119:     */         }
/* 1120:3091 */         currentType = type;
/* 1121:     */       }
/* 1122:     */     }
/* 1123:3093 */     list.add(new String(c, tokenStart, c.length - tokenStart));
/* 1124:3094 */     return (String[])list.toArray(new String[list.size()]);
/* 1125:     */   }
/* 1126:     */   
/* 1127:     */   /**
/* 1128:     */    * @deprecated
/* 1129:     */    */
/* 1130:     */   public static String concatenate(Object[] array)
/* 1131:     */   {
/* 1132:3120 */     return join(array, null);
/* 1133:     */   }
/* 1134:     */   
/* 1135:     */   public static String join(Object[] array)
/* 1136:     */   {
/* 1137:3144 */     return join(array, null);
/* 1138:     */   }
/* 1139:     */   
/* 1140:     */   public static String join(Object[] array, char separator)
/* 1141:     */   {
/* 1142:3170 */     if (array == null) {
/* 1143:3171 */       return null;
/* 1144:     */     }
/* 1145:3174 */     return join(array, separator, 0, array.length);
/* 1146:     */   }
/* 1147:     */   
/* 1148:     */   public static String join(Object[] array, char separator, int startIndex, int endIndex)
/* 1149:     */   {
/* 1150:3204 */     if (array == null) {
/* 1151:3205 */       return null;
/* 1152:     */     }
/* 1153:3207 */     int bufSize = endIndex - startIndex;
/* 1154:3208 */     if (bufSize <= 0) {
/* 1155:3209 */       return "";
/* 1156:     */     }
/* 1157:3212 */     bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + 1);
/* 1158:3213 */     StrBuilder buf = new StrBuilder(bufSize);
/* 1159:3215 */     for (int i = startIndex; i < endIndex; i++)
/* 1160:     */     {
/* 1161:3216 */       if (i > startIndex) {
/* 1162:3217 */         buf.append(separator);
/* 1163:     */       }
/* 1164:3219 */       if (array[i] != null) {
/* 1165:3220 */         buf.append(array[i]);
/* 1166:     */       }
/* 1167:     */     }
/* 1168:3223 */     return buf.toString();
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   public static String join(Object[] array, String separator)
/* 1172:     */   {
/* 1173:3251 */     if (array == null) {
/* 1174:3252 */       return null;
/* 1175:     */     }
/* 1176:3254 */     return join(array, separator, 0, array.length);
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public static String join(Object[] array, String separator, int startIndex, int endIndex)
/* 1180:     */   {
/* 1181:3285 */     if (array == null) {
/* 1182:3286 */       return null;
/* 1183:     */     }
/* 1184:3288 */     if (separator == null) {
/* 1185:3289 */       separator = "";
/* 1186:     */     }
/* 1187:3294 */     int bufSize = endIndex - startIndex;
/* 1188:3295 */     if (bufSize <= 0) {
/* 1189:3296 */       return "";
/* 1190:     */     }
/* 1191:3299 */     bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());
/* 1192:     */     
/* 1193:     */ 
/* 1194:3302 */     StrBuilder buf = new StrBuilder(bufSize);
/* 1195:3304 */     for (int i = startIndex; i < endIndex; i++)
/* 1196:     */     {
/* 1197:3305 */       if (i > startIndex) {
/* 1198:3306 */         buf.append(separator);
/* 1199:     */       }
/* 1200:3308 */       if (array[i] != null) {
/* 1201:3309 */         buf.append(array[i]);
/* 1202:     */       }
/* 1203:     */     }
/* 1204:3312 */     return buf.toString();
/* 1205:     */   }
/* 1206:     */   
/* 1207:     */   public static String join(Iterator iterator, char separator)
/* 1208:     */   {
/* 1209:3332 */     if (iterator == null) {
/* 1210:3333 */       return null;
/* 1211:     */     }
/* 1212:3335 */     if (!iterator.hasNext()) {
/* 1213:3336 */       return "";
/* 1214:     */     }
/* 1215:3338 */     Object first = iterator.next();
/* 1216:3339 */     if (!iterator.hasNext()) {
/* 1217:3340 */       return ObjectUtils.toString(first);
/* 1218:     */     }
/* 1219:3344 */     StrBuilder buf = new StrBuilder(256);
/* 1220:3345 */     if (first != null) {
/* 1221:3346 */       buf.append(first);
/* 1222:     */     }
/* 1223:3349 */     while (iterator.hasNext())
/* 1224:     */     {
/* 1225:3350 */       buf.append(separator);
/* 1226:3351 */       Object obj = iterator.next();
/* 1227:3352 */       if (obj != null) {
/* 1228:3353 */         buf.append(obj);
/* 1229:     */       }
/* 1230:     */     }
/* 1231:3357 */     return buf.toString();
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public static String join(Iterator iterator, String separator)
/* 1235:     */   {
/* 1236:3376 */     if (iterator == null) {
/* 1237:3377 */       return null;
/* 1238:     */     }
/* 1239:3379 */     if (!iterator.hasNext()) {
/* 1240:3380 */       return "";
/* 1241:     */     }
/* 1242:3382 */     Object first = iterator.next();
/* 1243:3383 */     if (!iterator.hasNext()) {
/* 1244:3384 */       return ObjectUtils.toString(first);
/* 1245:     */     }
/* 1246:3388 */     StrBuilder buf = new StrBuilder(256);
/* 1247:3389 */     if (first != null) {
/* 1248:3390 */       buf.append(first);
/* 1249:     */     }
/* 1250:3393 */     while (iterator.hasNext())
/* 1251:     */     {
/* 1252:3394 */       if (separator != null) {
/* 1253:3395 */         buf.append(separator);
/* 1254:     */       }
/* 1255:3397 */       Object obj = iterator.next();
/* 1256:3398 */       if (obj != null) {
/* 1257:3399 */         buf.append(obj);
/* 1258:     */       }
/* 1259:     */     }
/* 1260:3402 */     return buf.toString();
/* 1261:     */   }
/* 1262:     */   
/* 1263:     */   public static String join(Collection collection, char separator)
/* 1264:     */   {
/* 1265:3420 */     if (collection == null) {
/* 1266:3421 */       return null;
/* 1267:     */     }
/* 1268:3423 */     return join(collection.iterator(), separator);
/* 1269:     */   }
/* 1270:     */   
/* 1271:     */   public static String join(Collection collection, String separator)
/* 1272:     */   {
/* 1273:3441 */     if (collection == null) {
/* 1274:3442 */       return null;
/* 1275:     */     }
/* 1276:3444 */     return join(collection.iterator(), separator);
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   /**
/* 1280:     */    * @deprecated
/* 1281:     */    */
/* 1282:     */   public static String deleteSpaces(String str)
/* 1283:     */   {
/* 1284:3476 */     if (str == null) {
/* 1285:3477 */       return null;
/* 1286:     */     }
/* 1287:3479 */     return CharSetUtils.delete(str, " \t\r\n\b");
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   public static String deleteWhitespace(String str)
/* 1291:     */   {
/* 1292:3497 */     if (isEmpty(str)) {
/* 1293:3498 */       return str;
/* 1294:     */     }
/* 1295:3500 */     int sz = str.length();
/* 1296:3501 */     char[] chs = new char[sz];
/* 1297:3502 */     int count = 0;
/* 1298:3503 */     for (int i = 0; i < sz; i++) {
/* 1299:3504 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 1300:3505 */         chs[(count++)] = str.charAt(i);
/* 1301:     */       }
/* 1302:     */     }
/* 1303:3508 */     if (count == sz) {
/* 1304:3509 */       return str;
/* 1305:     */     }
/* 1306:3511 */     return new String(chs, 0, count);
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public static String removeStart(String str, String remove)
/* 1310:     */   {
/* 1311:3541 */     if ((isEmpty(str)) || (isEmpty(remove))) {
/* 1312:3542 */       return str;
/* 1313:     */     }
/* 1314:3544 */     if (str.startsWith(remove)) {
/* 1315:3545 */       return str.substring(remove.length());
/* 1316:     */     }
/* 1317:3547 */     return str;
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   public static String removeStartIgnoreCase(String str, String remove)
/* 1321:     */   {
/* 1322:3576 */     if ((isEmpty(str)) || (isEmpty(remove))) {
/* 1323:3577 */       return str;
/* 1324:     */     }
/* 1325:3579 */     if (startsWithIgnoreCase(str, remove)) {
/* 1326:3580 */       return str.substring(remove.length());
/* 1327:     */     }
/* 1328:3582 */     return str;
/* 1329:     */   }
/* 1330:     */   
/* 1331:     */   public static String removeEnd(String str, String remove)
/* 1332:     */   {
/* 1333:3610 */     if ((isEmpty(str)) || (isEmpty(remove))) {
/* 1334:3611 */       return str;
/* 1335:     */     }
/* 1336:3613 */     if (str.endsWith(remove)) {
/* 1337:3614 */       return str.substring(0, str.length() - remove.length());
/* 1338:     */     }
/* 1339:3616 */     return str;
/* 1340:     */   }
/* 1341:     */   
/* 1342:     */   public static String removeEndIgnoreCase(String str, String remove)
/* 1343:     */   {
/* 1344:3646 */     if ((isEmpty(str)) || (isEmpty(remove))) {
/* 1345:3647 */       return str;
/* 1346:     */     }
/* 1347:3649 */     if (endsWithIgnoreCase(str, remove)) {
/* 1348:3650 */       return str.substring(0, str.length() - remove.length());
/* 1349:     */     }
/* 1350:3652 */     return str;
/* 1351:     */   }
/* 1352:     */   
/* 1353:     */   public static String remove(String str, String remove)
/* 1354:     */   {
/* 1355:3679 */     if ((isEmpty(str)) || (isEmpty(remove))) {
/* 1356:3680 */       return str;
/* 1357:     */     }
/* 1358:3682 */     return replace(str, remove, "", -1);
/* 1359:     */   }
/* 1360:     */   
/* 1361:     */   public static String remove(String str, char remove)
/* 1362:     */   {
/* 1363:3705 */     if ((isEmpty(str)) || (str.indexOf(remove) == -1)) {
/* 1364:3706 */       return str;
/* 1365:     */     }
/* 1366:3708 */     char[] chars = str.toCharArray();
/* 1367:3709 */     int pos = 0;
/* 1368:3710 */     for (int i = 0; i < chars.length; i++) {
/* 1369:3711 */       if (chars[i] != remove) {
/* 1370:3712 */         chars[(pos++)] = chars[i];
/* 1371:     */       }
/* 1372:     */     }
/* 1373:3715 */     return new String(chars, 0, pos);
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public static String replaceOnce(String text, String searchString, String replacement)
/* 1377:     */   {
/* 1378:3744 */     return replace(text, searchString, replacement, 1);
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public static String replace(String text, String searchString, String replacement)
/* 1382:     */   {
/* 1383:3771 */     return replace(text, searchString, replacement, -1);
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public static String replace(String text, String searchString, String replacement, int max)
/* 1387:     */   {
/* 1388:3803 */     if ((isEmpty(text)) || (isEmpty(searchString)) || (replacement == null) || (max == 0)) {
/* 1389:3804 */       return text;
/* 1390:     */     }
/* 1391:3806 */     int start = 0;
/* 1392:3807 */     int end = text.indexOf(searchString, start);
/* 1393:3808 */     if (end == -1) {
/* 1394:3809 */       return text;
/* 1395:     */     }
/* 1396:3811 */     int replLength = searchString.length();
/* 1397:3812 */     int increase = replacement.length() - replLength;
/* 1398:3813 */     increase = increase < 0 ? 0 : increase;
/* 1399:3814 */     increase *= (max > 64 ? 64 : max < 0 ? 16 : max);
/* 1400:3815 */     StrBuilder buf = new StrBuilder(text.length() + increase);
/* 1401:3816 */     while (end != -1)
/* 1402:     */     {
/* 1403:3817 */       buf.append(text.substring(start, end)).append(replacement);
/* 1404:3818 */       start = end + replLength;
/* 1405:3819 */       max--;
/* 1406:3819 */       if (max == 0) {
/* 1407:     */         break;
/* 1408:     */       }
/* 1409:3822 */       end = text.indexOf(searchString, start);
/* 1410:     */     }
/* 1411:3824 */     buf.append(text.substring(start));
/* 1412:3825 */     return buf.toString();
/* 1413:     */   }
/* 1414:     */   
/* 1415:     */   public static String replaceEach(String text, String[] searchList, String[] replacementList)
/* 1416:     */   {
/* 1417:3868 */     return replaceEach(text, searchList, replacementList, false, 0);
/* 1418:     */   }
/* 1419:     */   
/* 1420:     */   public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList)
/* 1421:     */   {
/* 1422:3919 */     int timeToLive = searchList == null ? 0 : searchList.length;
/* 1423:3920 */     return replaceEach(text, searchList, replacementList, true, timeToLive);
/* 1424:     */   }
/* 1425:     */   
/* 1426:     */   private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive)
/* 1427:     */   {
/* 1428:3978 */     if ((text == null) || (text.length() == 0) || (searchList == null) || (searchList.length == 0) || (replacementList == null) || (replacementList.length == 0)) {
/* 1429:3981 */       return text;
/* 1430:     */     }
/* 1431:3985 */     if (timeToLive < 0) {
/* 1432:3986 */       throw new IllegalStateException("TimeToLive of " + timeToLive + " is less than 0: " + text);
/* 1433:     */     }
/* 1434:3989 */     int searchLength = searchList.length;
/* 1435:3990 */     int replacementLength = replacementList.length;
/* 1436:3993 */     if (searchLength != replacementLength) {
/* 1437:3994 */       throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
/* 1438:     */     }
/* 1439:4001 */     boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
/* 1440:     */     
/* 1441:     */ 
/* 1442:4004 */     int textIndex = -1;
/* 1443:4005 */     int replaceIndex = -1;
/* 1444:4006 */     int tempIndex = -1;
/* 1445:4010 */     for (int i = 0; i < searchLength; i++) {
/* 1446:4011 */       if ((noMoreMatchesForReplIndex[i] == 0) && (searchList[i] != null) && (searchList[i].length() != 0) && (replacementList[i] != null))
/* 1447:     */       {
/* 1448:4016 */         tempIndex = text.indexOf(searchList[i]);
/* 1449:4019 */         if (tempIndex == -1)
/* 1450:     */         {
/* 1451:4020 */           noMoreMatchesForReplIndex[i] = true;
/* 1452:     */         }
/* 1453:4022 */         else if ((textIndex == -1) || (tempIndex < textIndex))
/* 1454:     */         {
/* 1455:4023 */           textIndex = tempIndex;
/* 1456:4024 */           replaceIndex = i;
/* 1457:     */         }
/* 1458:     */       }
/* 1459:     */     }
/* 1460:4031 */     if (textIndex == -1) {
/* 1461:4032 */       return text;
/* 1462:     */     }
/* 1463:4035 */     int start = 0;
/* 1464:     */     
/* 1465:     */ 
/* 1466:4038 */     int increase = 0;
/* 1467:4041 */     for (int i = 0; i < searchList.length; i++) {
/* 1468:4042 */       if ((searchList[i] != null) && (replacementList[i] != null))
/* 1469:     */       {
/* 1470:4045 */         int greater = replacementList[i].length() - searchList[i].length();
/* 1471:4046 */         if (greater > 0) {
/* 1472:4047 */           increase += 3 * greater;
/* 1473:     */         }
/* 1474:     */       }
/* 1475:     */     }
/* 1476:4051 */     increase = Math.min(increase, text.length() / 5);
/* 1477:     */     
/* 1478:4053 */     StrBuilder buf = new StrBuilder(text.length() + increase);
/* 1479:4055 */     while (textIndex != -1)
/* 1480:     */     {
/* 1481:4057 */       for (int i = start; i < textIndex; i++) {
/* 1482:4058 */         buf.append(text.charAt(i));
/* 1483:     */       }
/* 1484:4060 */       buf.append(replacementList[replaceIndex]);
/* 1485:     */       
/* 1486:4062 */       start = textIndex + searchList[replaceIndex].length();
/* 1487:     */       
/* 1488:4064 */       textIndex = -1;
/* 1489:4065 */       replaceIndex = -1;
/* 1490:4066 */       tempIndex = -1;
/* 1491:4069 */       for (int i = 0; i < searchLength; i++) {
/* 1492:4070 */         if ((noMoreMatchesForReplIndex[i] == 0) && (searchList[i] != null) && (searchList[i].length() != 0) && (replacementList[i] != null))
/* 1493:     */         {
/* 1494:4075 */           tempIndex = text.indexOf(searchList[i], start);
/* 1495:4078 */           if (tempIndex == -1)
/* 1496:     */           {
/* 1497:4079 */             noMoreMatchesForReplIndex[i] = true;
/* 1498:     */           }
/* 1499:4081 */           else if ((textIndex == -1) || (tempIndex < textIndex))
/* 1500:     */           {
/* 1501:4082 */             textIndex = tempIndex;
/* 1502:4083 */             replaceIndex = i;
/* 1503:     */           }
/* 1504:     */         }
/* 1505:     */       }
/* 1506:     */     }
/* 1507:4090 */     int textLength = text.length();
/* 1508:4091 */     for (int i = start; i < textLength; i++) {
/* 1509:4092 */       buf.append(text.charAt(i));
/* 1510:     */     }
/* 1511:4094 */     String result = buf.toString();
/* 1512:4095 */     if (!repeat) {
/* 1513:4096 */       return result;
/* 1514:     */     }
/* 1515:4099 */     return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public static String replaceChars(String str, char searchChar, char replaceChar)
/* 1519:     */   {
/* 1520:4125 */     if (str == null) {
/* 1521:4126 */       return null;
/* 1522:     */     }
/* 1523:4128 */     return str.replace(searchChar, replaceChar);
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public static String replaceChars(String str, String searchChars, String replaceChars)
/* 1527:     */   {
/* 1528:4168 */     if ((isEmpty(str)) || (isEmpty(searchChars))) {
/* 1529:4169 */       return str;
/* 1530:     */     }
/* 1531:4171 */     if (replaceChars == null) {
/* 1532:4172 */       replaceChars = "";
/* 1533:     */     }
/* 1534:4174 */     boolean modified = false;
/* 1535:4175 */     int replaceCharsLength = replaceChars.length();
/* 1536:4176 */     int strLength = str.length();
/* 1537:4177 */     StrBuilder buf = new StrBuilder(strLength);
/* 1538:4178 */     for (int i = 0; i < strLength; i++)
/* 1539:     */     {
/* 1540:4179 */       char ch = str.charAt(i);
/* 1541:4180 */       int index = searchChars.indexOf(ch);
/* 1542:4181 */       if (index >= 0)
/* 1543:     */       {
/* 1544:4182 */         modified = true;
/* 1545:4183 */         if (index < replaceCharsLength) {
/* 1546:4184 */           buf.append(replaceChars.charAt(index));
/* 1547:     */         }
/* 1548:     */       }
/* 1549:     */       else
/* 1550:     */       {
/* 1551:4187 */         buf.append(ch);
/* 1552:     */       }
/* 1553:     */     }
/* 1554:4190 */     if (modified) {
/* 1555:4191 */       return buf.toString();
/* 1556:     */     }
/* 1557:4193 */     return str;
/* 1558:     */   }
/* 1559:     */   
/* 1560:     */   /**
/* 1561:     */    * @deprecated
/* 1562:     */    */
/* 1563:     */   public static String overlayString(String text, String overlay, int start, int end)
/* 1564:     */   {
/* 1565:4224 */     return new StrBuilder(start + overlay.length() + text.length() - end + 1).append(text.substring(0, start)).append(overlay).append(text.substring(end)).toString();
/* 1566:     */   }
/* 1567:     */   
/* 1568:     */   public static String overlay(String str, String overlay, int start, int end)
/* 1569:     */   {
/* 1570:4261 */     if (str == null) {
/* 1571:4262 */       return null;
/* 1572:     */     }
/* 1573:4264 */     if (overlay == null) {
/* 1574:4265 */       overlay = "";
/* 1575:     */     }
/* 1576:4267 */     int len = str.length();
/* 1577:4268 */     if (start < 0) {
/* 1578:4269 */       start = 0;
/* 1579:     */     }
/* 1580:4271 */     if (start > len) {
/* 1581:4272 */       start = len;
/* 1582:     */     }
/* 1583:4274 */     if (end < 0) {
/* 1584:4275 */       end = 0;
/* 1585:     */     }
/* 1586:4277 */     if (end > len) {
/* 1587:4278 */       end = len;
/* 1588:     */     }
/* 1589:4280 */     if (start > end)
/* 1590:     */     {
/* 1591:4281 */       int temp = start;
/* 1592:4282 */       start = end;
/* 1593:4283 */       end = temp;
/* 1594:     */     }
/* 1595:4285 */     return new StrBuilder(len + start - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
/* 1596:     */   }
/* 1597:     */   
/* 1598:     */   public static String chomp(String str)
/* 1599:     */   {
/* 1600:4320 */     if (isEmpty(str)) {
/* 1601:4321 */       return str;
/* 1602:     */     }
/* 1603:4324 */     if (str.length() == 1)
/* 1604:     */     {
/* 1605:4325 */       char ch = str.charAt(0);
/* 1606:4326 */       if ((ch == '\r') || (ch == '\n')) {
/* 1607:4327 */         return "";
/* 1608:     */       }
/* 1609:4329 */       return str;
/* 1610:     */     }
/* 1611:4332 */     int lastIdx = str.length() - 1;
/* 1612:4333 */     char last = str.charAt(lastIdx);
/* 1613:4335 */     if (last == '\n')
/* 1614:     */     {
/* 1615:4336 */       if (str.charAt(lastIdx - 1) == '\r') {
/* 1616:4337 */         lastIdx--;
/* 1617:     */       }
/* 1618:     */     }
/* 1619:4339 */     else if (last != '\r') {
/* 1620:4340 */       lastIdx++;
/* 1621:     */     }
/* 1622:4342 */     return str.substring(0, lastIdx);
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   public static String chomp(String str, String separator)
/* 1626:     */   {
/* 1627:4372 */     if ((isEmpty(str)) || (separator == null)) {
/* 1628:4373 */       return str;
/* 1629:     */     }
/* 1630:4375 */     if (str.endsWith(separator)) {
/* 1631:4376 */       return str.substring(0, str.length() - separator.length());
/* 1632:     */     }
/* 1633:4378 */     return str;
/* 1634:     */   }
/* 1635:     */   
/* 1636:     */   /**
/* 1637:     */    * @deprecated
/* 1638:     */    */
/* 1639:     */   public static String chompLast(String str)
/* 1640:     */   {
/* 1641:4392 */     return chompLast(str, "\n");
/* 1642:     */   }
/* 1643:     */   
/* 1644:     */   /**
/* 1645:     */    * @deprecated
/* 1646:     */    */
/* 1647:     */   public static String chompLast(String str, String sep)
/* 1648:     */   {
/* 1649:4406 */     if (str.length() == 0) {
/* 1650:4407 */       return str;
/* 1651:     */     }
/* 1652:4409 */     String sub = str.substring(str.length() - sep.length());
/* 1653:4410 */     if (sep.equals(sub)) {
/* 1654:4411 */       return str.substring(0, str.length() - sep.length());
/* 1655:     */     }
/* 1656:4413 */     return str;
/* 1657:     */   }
/* 1658:     */   
/* 1659:     */   /**
/* 1660:     */    * @deprecated
/* 1661:     */    */
/* 1662:     */   public static String getChomp(String str, String sep)
/* 1663:     */   {
/* 1664:4429 */     int idx = str.lastIndexOf(sep);
/* 1665:4430 */     if (idx == str.length() - sep.length()) {
/* 1666:4431 */       return sep;
/* 1667:     */     }
/* 1668:4432 */     if (idx != -1) {
/* 1669:4433 */       return str.substring(idx);
/* 1670:     */     }
/* 1671:4435 */     return "";
/* 1672:     */   }
/* 1673:     */   
/* 1674:     */   /**
/* 1675:     */    * @deprecated
/* 1676:     */    */
/* 1677:     */   public static String prechomp(String str, String sep)
/* 1678:     */   {
/* 1679:4451 */     int idx = str.indexOf(sep);
/* 1680:4452 */     if (idx == -1) {
/* 1681:4453 */       return str;
/* 1682:     */     }
/* 1683:4455 */     return str.substring(idx + sep.length());
/* 1684:     */   }
/* 1685:     */   
/* 1686:     */   /**
/* 1687:     */    * @deprecated
/* 1688:     */    */
/* 1689:     */   public static String getPrechomp(String str, String sep)
/* 1690:     */   {
/* 1691:4471 */     int idx = str.indexOf(sep);
/* 1692:4472 */     if (idx == -1) {
/* 1693:4473 */       return "";
/* 1694:     */     }
/* 1695:4475 */     return str.substring(0, idx + sep.length());
/* 1696:     */   }
/* 1697:     */   
/* 1698:     */   public static String chop(String str)
/* 1699:     */   {
/* 1700:4504 */     if (str == null) {
/* 1701:4505 */       return null;
/* 1702:     */     }
/* 1703:4507 */     int strLen = str.length();
/* 1704:4508 */     if (strLen < 2) {
/* 1705:4509 */       return "";
/* 1706:     */     }
/* 1707:4511 */     int lastIdx = strLen - 1;
/* 1708:4512 */     String ret = str.substring(0, lastIdx);
/* 1709:4513 */     char last = str.charAt(lastIdx);
/* 1710:4514 */     if ((last == '\n') && 
/* 1711:4515 */       (ret.charAt(lastIdx - 1) == '\r')) {
/* 1712:4516 */       return ret.substring(0, lastIdx - 1);
/* 1713:     */     }
/* 1714:4519 */     return ret;
/* 1715:     */   }
/* 1716:     */   
/* 1717:     */   /**
/* 1718:     */    * @deprecated
/* 1719:     */    */
/* 1720:     */   public static String chopNewline(String str)
/* 1721:     */   {
/* 1722:4533 */     int lastIdx = str.length() - 1;
/* 1723:4534 */     if (lastIdx <= 0) {
/* 1724:4535 */       return "";
/* 1725:     */     }
/* 1726:4537 */     char last = str.charAt(lastIdx);
/* 1727:4538 */     if (last == '\n')
/* 1728:     */     {
/* 1729:4539 */       if (str.charAt(lastIdx - 1) == '\r') {
/* 1730:4540 */         lastIdx--;
/* 1731:     */       }
/* 1732:     */     }
/* 1733:     */     else {
/* 1734:4543 */       lastIdx++;
/* 1735:     */     }
/* 1736:4545 */     return str.substring(0, lastIdx);
/* 1737:     */   }
/* 1738:     */   
/* 1739:     */   /**
/* 1740:     */    * @deprecated
/* 1741:     */    */
/* 1742:     */   public static String escape(String str)
/* 1743:     */   {
/* 1744:4567 */     return StringEscapeUtils.escapeJava(str);
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   public static String repeat(String str, int repeat)
/* 1748:     */   {
/* 1749:4593 */     if (str == null) {
/* 1750:4594 */       return null;
/* 1751:     */     }
/* 1752:4596 */     if (repeat <= 0) {
/* 1753:4597 */       return "";
/* 1754:     */     }
/* 1755:4599 */     int inputLength = str.length();
/* 1756:4600 */     if ((repeat == 1) || (inputLength == 0)) {
/* 1757:4601 */       return str;
/* 1758:     */     }
/* 1759:4603 */     if ((inputLength == 1) && (repeat <= 8192)) {
/* 1760:4604 */       return padding(repeat, str.charAt(0));
/* 1761:     */     }
/* 1762:4607 */     int outputLength = inputLength * repeat;
/* 1763:4608 */     switch (inputLength)
/* 1764:     */     {
/* 1765:     */     case 1: 
/* 1766:4610 */       char ch = str.charAt(0);
/* 1767:4611 */       char[] output1 = new char[outputLength];
/* 1768:4612 */       for (int i = repeat - 1; i >= 0; i--) {
/* 1769:4613 */         output1[i] = ch;
/* 1770:     */       }
/* 1771:4615 */       return new String(output1);
/* 1772:     */     case 2: 
/* 1773:4617 */       char ch0 = str.charAt(0);
/* 1774:4618 */       char ch1 = str.charAt(1);
/* 1775:4619 */       char[] output2 = new char[outputLength];
/* 1776:4620 */       for (int i = repeat * 2 - 2; i >= 0; i--)
/* 1777:     */       {
/* 1778:4621 */         output2[i] = ch0;
/* 1779:4622 */         output2[(i + 1)] = ch1;i--;
/* 1780:     */       }
/* 1781:4624 */       return new String(output2);
/* 1782:     */     }
/* 1783:4626 */     StrBuilder buf = new StrBuilder(outputLength);
/* 1784:4627 */     for (int i = 0; i < repeat; i++) {
/* 1785:4628 */       buf.append(str);
/* 1786:     */     }
/* 1787:4630 */     return buf.toString();
/* 1788:     */   }
/* 1789:     */   
/* 1790:     */   public static String repeat(String str, String separator, int repeat)
/* 1791:     */   {
/* 1792:4655 */     if ((str == null) || (separator == null)) {
/* 1793:4656 */       return repeat(str, repeat);
/* 1794:     */     }
/* 1795:4659 */     String result = repeat(str + separator, repeat);
/* 1796:4660 */     return removeEnd(result, separator);
/* 1797:     */   }
/* 1798:     */   
/* 1799:     */   private static String padding(int repeat, char padChar)
/* 1800:     */     throws IndexOutOfBoundsException
/* 1801:     */   {
/* 1802:4688 */     if (repeat < 0) {
/* 1803:4689 */       throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
/* 1804:     */     }
/* 1805:4691 */     char[] buf = new char[repeat];
/* 1806:4692 */     for (int i = 0; i < buf.length; i++) {
/* 1807:4693 */       buf[i] = padChar;
/* 1808:     */     }
/* 1809:4695 */     return new String(buf);
/* 1810:     */   }
/* 1811:     */   
/* 1812:     */   public static String rightPad(String str, int size)
/* 1813:     */   {
/* 1814:4718 */     return rightPad(str, size, ' ');
/* 1815:     */   }
/* 1816:     */   
/* 1817:     */   public static String rightPad(String str, int size, char padChar)
/* 1818:     */   {
/* 1819:4743 */     if (str == null) {
/* 1820:4744 */       return null;
/* 1821:     */     }
/* 1822:4746 */     int pads = size - str.length();
/* 1823:4747 */     if (pads <= 0) {
/* 1824:4748 */       return str;
/* 1825:     */     }
/* 1826:4750 */     if (pads > 8192) {
/* 1827:4751 */       return rightPad(str, size, String.valueOf(padChar));
/* 1828:     */     }
/* 1829:4753 */     return str.concat(padding(pads, padChar));
/* 1830:     */   }
/* 1831:     */   
/* 1832:     */   public static String rightPad(String str, int size, String padStr)
/* 1833:     */   {
/* 1834:4780 */     if (str == null) {
/* 1835:4781 */       return null;
/* 1836:     */     }
/* 1837:4783 */     if (isEmpty(padStr)) {
/* 1838:4784 */       padStr = " ";
/* 1839:     */     }
/* 1840:4786 */     int padLen = padStr.length();
/* 1841:4787 */     int strLen = str.length();
/* 1842:4788 */     int pads = size - strLen;
/* 1843:4789 */     if (pads <= 0) {
/* 1844:4790 */       return str;
/* 1845:     */     }
/* 1846:4792 */     if ((padLen == 1) && (pads <= 8192)) {
/* 1847:4793 */       return rightPad(str, size, padStr.charAt(0));
/* 1848:     */     }
/* 1849:4796 */     if (pads == padLen) {
/* 1850:4797 */       return str.concat(padStr);
/* 1851:     */     }
/* 1852:4798 */     if (pads < padLen) {
/* 1853:4799 */       return str.concat(padStr.substring(0, pads));
/* 1854:     */     }
/* 1855:4801 */     char[] padding = new char[pads];
/* 1856:4802 */     char[] padChars = padStr.toCharArray();
/* 1857:4803 */     for (int i = 0; i < pads; i++) {
/* 1858:4804 */       padding[i] = padChars[(i % padLen)];
/* 1859:     */     }
/* 1860:4806 */     return str.concat(new String(padding));
/* 1861:     */   }
/* 1862:     */   
/* 1863:     */   public static String leftPad(String str, int size)
/* 1864:     */   {
/* 1865:4830 */     return leftPad(str, size, ' ');
/* 1866:     */   }
/* 1867:     */   
/* 1868:     */   public static String leftPad(String str, int size, char padChar)
/* 1869:     */   {
/* 1870:4855 */     if (str == null) {
/* 1871:4856 */       return null;
/* 1872:     */     }
/* 1873:4858 */     int pads = size - str.length();
/* 1874:4859 */     if (pads <= 0) {
/* 1875:4860 */       return str;
/* 1876:     */     }
/* 1877:4862 */     if (pads > 8192) {
/* 1878:4863 */       return leftPad(str, size, String.valueOf(padChar));
/* 1879:     */     }
/* 1880:4865 */     return padding(pads, padChar).concat(str);
/* 1881:     */   }
/* 1882:     */   
/* 1883:     */   public static String leftPad(String str, int size, String padStr)
/* 1884:     */   {
/* 1885:4892 */     if (str == null) {
/* 1886:4893 */       return null;
/* 1887:     */     }
/* 1888:4895 */     if (isEmpty(padStr)) {
/* 1889:4896 */       padStr = " ";
/* 1890:     */     }
/* 1891:4898 */     int padLen = padStr.length();
/* 1892:4899 */     int strLen = str.length();
/* 1893:4900 */     int pads = size - strLen;
/* 1894:4901 */     if (pads <= 0) {
/* 1895:4902 */       return str;
/* 1896:     */     }
/* 1897:4904 */     if ((padLen == 1) && (pads <= 8192)) {
/* 1898:4905 */       return leftPad(str, size, padStr.charAt(0));
/* 1899:     */     }
/* 1900:4908 */     if (pads == padLen) {
/* 1901:4909 */       return padStr.concat(str);
/* 1902:     */     }
/* 1903:4910 */     if (pads < padLen) {
/* 1904:4911 */       return padStr.substring(0, pads).concat(str);
/* 1905:     */     }
/* 1906:4913 */     char[] padding = new char[pads];
/* 1907:4914 */     char[] padChars = padStr.toCharArray();
/* 1908:4915 */     for (int i = 0; i < pads; i++) {
/* 1909:4916 */       padding[i] = padChars[(i % padLen)];
/* 1910:     */     }
/* 1911:4918 */     return new String(padding).concat(str);
/* 1912:     */   }
/* 1913:     */   
/* 1914:     */   public static int length(String str)
/* 1915:     */   {
/* 1916:4931 */     return str == null ? 0 : str.length();
/* 1917:     */   }
/* 1918:     */   
/* 1919:     */   public static String center(String str, int size)
/* 1920:     */   {
/* 1921:4960 */     return center(str, size, ' ');
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   public static String center(String str, int size, char padChar)
/* 1925:     */   {
/* 1926:4988 */     if ((str == null) || (size <= 0)) {
/* 1927:4989 */       return str;
/* 1928:     */     }
/* 1929:4991 */     int strLen = str.length();
/* 1930:4992 */     int pads = size - strLen;
/* 1931:4993 */     if (pads <= 0) {
/* 1932:4994 */       return str;
/* 1933:     */     }
/* 1934:4996 */     str = leftPad(str, strLen + pads / 2, padChar);
/* 1935:4997 */     str = rightPad(str, size, padChar);
/* 1936:4998 */     return str;
/* 1937:     */   }
/* 1938:     */   
/* 1939:     */   public static String center(String str, int size, String padStr)
/* 1940:     */   {
/* 1941:5028 */     if ((str == null) || (size <= 0)) {
/* 1942:5029 */       return str;
/* 1943:     */     }
/* 1944:5031 */     if (isEmpty(padStr)) {
/* 1945:5032 */       padStr = " ";
/* 1946:     */     }
/* 1947:5034 */     int strLen = str.length();
/* 1948:5035 */     int pads = size - strLen;
/* 1949:5036 */     if (pads <= 0) {
/* 1950:5037 */       return str;
/* 1951:     */     }
/* 1952:5039 */     str = leftPad(str, strLen + pads / 2, padStr);
/* 1953:5040 */     str = rightPad(str, size, padStr);
/* 1954:5041 */     return str;
/* 1955:     */   }
/* 1956:     */   
/* 1957:     */   public static String upperCase(String str)
/* 1958:     */   {
/* 1959:5066 */     if (str == null) {
/* 1960:5067 */       return null;
/* 1961:     */     }
/* 1962:5069 */     return str.toUpperCase();
/* 1963:     */   }
/* 1964:     */   
/* 1965:     */   public static String upperCase(String str, Locale locale)
/* 1966:     */   {
/* 1967:5089 */     if (str == null) {
/* 1968:5090 */       return null;
/* 1969:     */     }
/* 1970:5092 */     return str.toUpperCase(locale);
/* 1971:     */   }
/* 1972:     */   
/* 1973:     */   public static String lowerCase(String str)
/* 1974:     */   {
/* 1975:5115 */     if (str == null) {
/* 1976:5116 */       return null;
/* 1977:     */     }
/* 1978:5118 */     return str.toLowerCase();
/* 1979:     */   }
/* 1980:     */   
/* 1981:     */   public static String lowerCase(String str, Locale locale)
/* 1982:     */   {
/* 1983:5138 */     if (str == null) {
/* 1984:5139 */       return null;
/* 1985:     */     }
/* 1986:5141 */     return str.toLowerCase(locale);
/* 1987:     */   }
/* 1988:     */   
/* 1989:     */   public static String capitalize(String str)
/* 1990:     */   {
/* 1991:     */     int strLen;
/* 1992:5166 */     if ((str == null) || ((strLen = str.length()) == 0)) {
/* 1993:5167 */       return str;
/* 1994:     */     }
/* 1995:     */     int strLen;
/* 1996:5169 */     return new StrBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
/* 1997:     */   }
/* 1998:     */   
/* 1999:     */   /**
/* 2000:     */    * @deprecated
/* 2001:     */    */
/* 2002:     */   public static String capitalise(String str)
/* 2003:     */   {
/* 2004:5185 */     return capitalize(str);
/* 2005:     */   }
/* 2006:     */   
/* 2007:     */   public static String uncapitalize(String str)
/* 2008:     */   {
/* 2009:     */     int strLen;
/* 2010:5210 */     if ((str == null) || ((strLen = str.length()) == 0)) {
/* 2011:5211 */       return str;
/* 2012:     */     }
/* 2013:     */     int strLen;
/* 2014:5213 */     return new StrBuilder(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
/* 2015:     */   }
/* 2016:     */   
/* 2017:     */   /**
/* 2018:     */    * @deprecated
/* 2019:     */    */
/* 2020:     */   public static String uncapitalise(String str)
/* 2021:     */   {
/* 2022:5229 */     return uncapitalize(str);
/* 2023:     */   }
/* 2024:     */   
/* 2025:     */   public static String swapCase(String str)
/* 2026:     */   {
/* 2027:     */     int strLen;
/* 2028:5261 */     if ((str == null) || ((strLen = str.length()) == 0)) {
/* 2029:5262 */       return str;
/* 2030:     */     }
/* 2031:     */     int strLen;
/* 2032:5264 */     StrBuilder buffer = new StrBuilder(strLen);
/* 2033:     */     
/* 2034:5266 */     char ch = '\000';
/* 2035:5267 */     for (int i = 0; i < strLen; i++)
/* 2036:     */     {
/* 2037:5268 */       ch = str.charAt(i);
/* 2038:5269 */       if (Character.isUpperCase(ch)) {
/* 2039:5270 */         ch = Character.toLowerCase(ch);
/* 2040:5271 */       } else if (Character.isTitleCase(ch)) {
/* 2041:5272 */         ch = Character.toLowerCase(ch);
/* 2042:5273 */       } else if (Character.isLowerCase(ch)) {
/* 2043:5274 */         ch = Character.toUpperCase(ch);
/* 2044:     */       }
/* 2045:5276 */       buffer.append(ch);
/* 2046:     */     }
/* 2047:5278 */     return buffer.toString();
/* 2048:     */   }
/* 2049:     */   
/* 2050:     */   /**
/* 2051:     */    * @deprecated
/* 2052:     */    */
/* 2053:     */   public static String capitaliseAllWords(String str)
/* 2054:     */   {
/* 2055:5294 */     return WordUtils.capitalize(str);
/* 2056:     */   }
/* 2057:     */   
/* 2058:     */   public static int countMatches(String str, String sub)
/* 2059:     */   {
/* 2060:5319 */     if ((isEmpty(str)) || (isEmpty(sub))) {
/* 2061:5320 */       return 0;
/* 2062:     */     }
/* 2063:5322 */     int count = 0;
/* 2064:5323 */     int idx = 0;
/* 2065:5324 */     while ((idx = str.indexOf(sub, idx)) != -1)
/* 2066:     */     {
/* 2067:5325 */       count++;
/* 2068:5326 */       idx += sub.length();
/* 2069:     */     }
/* 2070:5328 */     return count;
/* 2071:     */   }
/* 2072:     */   
/* 2073:     */   public static boolean isAlpha(String str)
/* 2074:     */   {
/* 2075:5352 */     if (str == null) {
/* 2076:5353 */       return false;
/* 2077:     */     }
/* 2078:5355 */     int sz = str.length();
/* 2079:5356 */     for (int i = 0; i < sz; i++) {
/* 2080:5357 */       if (!Character.isLetter(str.charAt(i))) {
/* 2081:5358 */         return false;
/* 2082:     */       }
/* 2083:     */     }
/* 2084:5361 */     return true;
/* 2085:     */   }
/* 2086:     */   
/* 2087:     */   public static boolean isAlphaSpace(String str)
/* 2088:     */   {
/* 2089:5386 */     if (str == null) {
/* 2090:5387 */       return false;
/* 2091:     */     }
/* 2092:5389 */     int sz = str.length();
/* 2093:5390 */     for (int i = 0; i < sz; i++) {
/* 2094:5391 */       if ((!Character.isLetter(str.charAt(i))) && (str.charAt(i) != ' ')) {
/* 2095:5392 */         return false;
/* 2096:     */       }
/* 2097:     */     }
/* 2098:5395 */     return true;
/* 2099:     */   }
/* 2100:     */   
/* 2101:     */   public static boolean isAlphanumeric(String str)
/* 2102:     */   {
/* 2103:5419 */     if (str == null) {
/* 2104:5420 */       return false;
/* 2105:     */     }
/* 2106:5422 */     int sz = str.length();
/* 2107:5423 */     for (int i = 0; i < sz; i++) {
/* 2108:5424 */       if (!Character.isLetterOrDigit(str.charAt(i))) {
/* 2109:5425 */         return false;
/* 2110:     */       }
/* 2111:     */     }
/* 2112:5428 */     return true;
/* 2113:     */   }
/* 2114:     */   
/* 2115:     */   public static boolean isAlphanumericSpace(String str)
/* 2116:     */   {
/* 2117:5453 */     if (str == null) {
/* 2118:5454 */       return false;
/* 2119:     */     }
/* 2120:5456 */     int sz = str.length();
/* 2121:5457 */     for (int i = 0; i < sz; i++) {
/* 2122:5458 */       if ((!Character.isLetterOrDigit(str.charAt(i))) && (str.charAt(i) != ' ')) {
/* 2123:5459 */         return false;
/* 2124:     */       }
/* 2125:     */     }
/* 2126:5462 */     return true;
/* 2127:     */   }
/* 2128:     */   
/* 2129:     */   public static boolean isAsciiPrintable(String str)
/* 2130:     */   {
/* 2131:5491 */     if (str == null) {
/* 2132:5492 */       return false;
/* 2133:     */     }
/* 2134:5494 */     int sz = str.length();
/* 2135:5495 */     for (int i = 0; i < sz; i++) {
/* 2136:5496 */       if (!CharUtils.isAsciiPrintable(str.charAt(i))) {
/* 2137:5497 */         return false;
/* 2138:     */       }
/* 2139:     */     }
/* 2140:5500 */     return true;
/* 2141:     */   }
/* 2142:     */   
/* 2143:     */   public static boolean isNumeric(String str)
/* 2144:     */   {
/* 2145:5525 */     if (str == null) {
/* 2146:5526 */       return false;
/* 2147:     */     }
/* 2148:5528 */     int sz = str.length();
/* 2149:5529 */     for (int i = 0; i < sz; i++) {
/* 2150:5530 */       if (!Character.isDigit(str.charAt(i))) {
/* 2151:5531 */         return false;
/* 2152:     */       }
/* 2153:     */     }
/* 2154:5534 */     return true;
/* 2155:     */   }
/* 2156:     */   
/* 2157:     */   public static boolean isNumericSpace(String str)
/* 2158:     */   {
/* 2159:5561 */     if (str == null) {
/* 2160:5562 */       return false;
/* 2161:     */     }
/* 2162:5564 */     int sz = str.length();
/* 2163:5565 */     for (int i = 0; i < sz; i++) {
/* 2164:5566 */       if ((!Character.isDigit(str.charAt(i))) && (str.charAt(i) != ' ')) {
/* 2165:5567 */         return false;
/* 2166:     */       }
/* 2167:     */     }
/* 2168:5570 */     return true;
/* 2169:     */   }
/* 2170:     */   
/* 2171:     */   public static boolean isWhitespace(String str)
/* 2172:     */   {
/* 2173:5593 */     if (str == null) {
/* 2174:5594 */       return false;
/* 2175:     */     }
/* 2176:5596 */     int sz = str.length();
/* 2177:5597 */     for (int i = 0; i < sz; i++) {
/* 2178:5598 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 2179:5599 */         return false;
/* 2180:     */       }
/* 2181:     */     }
/* 2182:5602 */     return true;
/* 2183:     */   }
/* 2184:     */   
/* 2185:     */   public static boolean isAllLowerCase(String str)
/* 2186:     */   {
/* 2187:5624 */     if ((str == null) || (isEmpty(str))) {
/* 2188:5625 */       return false;
/* 2189:     */     }
/* 2190:5627 */     int sz = str.length();
/* 2191:5628 */     for (int i = 0; i < sz; i++) {
/* 2192:5629 */       if (!Character.isLowerCase(str.charAt(i))) {
/* 2193:5630 */         return false;
/* 2194:     */       }
/* 2195:     */     }
/* 2196:5633 */     return true;
/* 2197:     */   }
/* 2198:     */   
/* 2199:     */   public static boolean isAllUpperCase(String str)
/* 2200:     */   {
/* 2201:5655 */     if ((str == null) || (isEmpty(str))) {
/* 2202:5656 */       return false;
/* 2203:     */     }
/* 2204:5658 */     int sz = str.length();
/* 2205:5659 */     for (int i = 0; i < sz; i++) {
/* 2206:5660 */       if (!Character.isUpperCase(str.charAt(i))) {
/* 2207:5661 */         return false;
/* 2208:     */       }
/* 2209:     */     }
/* 2210:5664 */     return true;
/* 2211:     */   }
/* 2212:     */   
/* 2213:     */   public static String defaultString(String str)
/* 2214:     */   {
/* 2215:5686 */     return str == null ? "" : str;
/* 2216:     */   }
/* 2217:     */   
/* 2218:     */   public static String defaultString(String str, String defaultStr)
/* 2219:     */   {
/* 2220:5707 */     return str == null ? defaultStr : str;
/* 2221:     */   }
/* 2222:     */   
/* 2223:     */   public static String defaultIfBlank(String str, String defaultStr)
/* 2224:     */   {
/* 2225:5729 */     return isBlank(str) ? defaultStr : str;
/* 2226:     */   }
/* 2227:     */   
/* 2228:     */   public static String defaultIfEmpty(String str, String defaultStr)
/* 2229:     */   {
/* 2230:5750 */     return isEmpty(str) ? defaultStr : str;
/* 2231:     */   }
/* 2232:     */   
/* 2233:     */   public static String reverse(String str)
/* 2234:     */   {
/* 2235:5770 */     if (str == null) {
/* 2236:5771 */       return null;
/* 2237:     */     }
/* 2238:5773 */     return new StrBuilder(str).reverse().toString();
/* 2239:     */   }
/* 2240:     */   
/* 2241:     */   public static String reverseDelimited(String str, char separatorChar)
/* 2242:     */   {
/* 2243:5796 */     if (str == null) {
/* 2244:5797 */       return null;
/* 2245:     */     }
/* 2246:5801 */     String[] strs = split(str, separatorChar);
/* 2247:5802 */     ArrayUtils.reverse(strs);
/* 2248:5803 */     return join(strs, separatorChar);
/* 2249:     */   }
/* 2250:     */   
/* 2251:     */   /**
/* 2252:     */    * @deprecated
/* 2253:     */    */
/* 2254:     */   public static String reverseDelimitedString(String str, String separatorChars)
/* 2255:     */   {
/* 2256:5829 */     if (str == null) {
/* 2257:5830 */       return null;
/* 2258:     */     }
/* 2259:5834 */     String[] strs = split(str, separatorChars);
/* 2260:5835 */     ArrayUtils.reverse(strs);
/* 2261:5836 */     if (separatorChars == null) {
/* 2262:5837 */       return join(strs, ' ');
/* 2263:     */     }
/* 2264:5839 */     return join(strs, separatorChars);
/* 2265:     */   }
/* 2266:     */   
/* 2267:     */   public static String abbreviate(String str, int maxWidth)
/* 2268:     */   {
/* 2269:5877 */     return abbreviate(str, 0, maxWidth);
/* 2270:     */   }
/* 2271:     */   
/* 2272:     */   public static String abbreviate(String str, int offset, int maxWidth)
/* 2273:     */   {
/* 2274:5916 */     if (str == null) {
/* 2275:5917 */       return null;
/* 2276:     */     }
/* 2277:5919 */     if (maxWidth < 4) {
/* 2278:5920 */       throw new IllegalArgumentException("Minimum abbreviation width is 4");
/* 2279:     */     }
/* 2280:5922 */     if (str.length() <= maxWidth) {
/* 2281:5923 */       return str;
/* 2282:     */     }
/* 2283:5925 */     if (offset > str.length()) {
/* 2284:5926 */       offset = str.length();
/* 2285:     */     }
/* 2286:5928 */     if (str.length() - offset < maxWidth - 3) {
/* 2287:5929 */       offset = str.length() - (maxWidth - 3);
/* 2288:     */     }
/* 2289:5931 */     if (offset <= 4) {
/* 2290:5932 */       return str.substring(0, maxWidth - 3) + "...";
/* 2291:     */     }
/* 2292:5934 */     if (maxWidth < 7) {
/* 2293:5935 */       throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
/* 2294:     */     }
/* 2295:5937 */     if (offset + (maxWidth - 3) < str.length()) {
/* 2296:5938 */       return "..." + abbreviate(str.substring(offset), maxWidth - 3);
/* 2297:     */     }
/* 2298:5940 */     return "..." + str.substring(str.length() - (maxWidth - 3));
/* 2299:     */   }
/* 2300:     */   
/* 2301:     */   public static String abbreviateMiddle(String str, String middle, int length)
/* 2302:     */   {
/* 2303:5973 */     if ((isEmpty(str)) || (isEmpty(middle))) {
/* 2304:5974 */       return str;
/* 2305:     */     }
/* 2306:5977 */     if ((length >= str.length()) || (length < middle.length() + 2)) {
/* 2307:5978 */       return str;
/* 2308:     */     }
/* 2309:5981 */     int targetSting = length - middle.length();
/* 2310:5982 */     int startOffset = targetSting / 2 + targetSting % 2;
/* 2311:5983 */     int endOffset = str.length() - targetSting / 2;
/* 2312:     */     
/* 2313:5985 */     StrBuilder builder = new StrBuilder(length);
/* 2314:5986 */     builder.append(str.substring(0, startOffset));
/* 2315:5987 */     builder.append(middle);
/* 2316:5988 */     builder.append(str.substring(endOffset));
/* 2317:     */     
/* 2318:5990 */     return builder.toString();
/* 2319:     */   }
/* 2320:     */   
/* 2321:     */   public static String difference(String str1, String str2)
/* 2322:     */   {
/* 2323:6021 */     if (str1 == null) {
/* 2324:6022 */       return str2;
/* 2325:     */     }
/* 2326:6024 */     if (str2 == null) {
/* 2327:6025 */       return str1;
/* 2328:     */     }
/* 2329:6027 */     int at = indexOfDifference(str1, str2);
/* 2330:6028 */     if (at == -1) {
/* 2331:6029 */       return "";
/* 2332:     */     }
/* 2333:6031 */     return str2.substring(at);
/* 2334:     */   }
/* 2335:     */   
/* 2336:     */   public static int indexOfDifference(String str1, String str2)
/* 2337:     */   {
/* 2338:6058 */     if (str1 == str2) {
/* 2339:6059 */       return -1;
/* 2340:     */     }
/* 2341:6061 */     if ((str1 == null) || (str2 == null)) {
/* 2342:6062 */       return 0;
/* 2343:     */     }
/* 2344:6065 */     for (int i = 0; (i < str1.length()) && (i < str2.length()); i++) {
/* 2345:6066 */       if (str1.charAt(i) != str2.charAt(i)) {
/* 2346:     */         break;
/* 2347:     */       }
/* 2348:     */     }
/* 2349:6070 */     if ((i < str2.length()) || (i < str1.length())) {
/* 2350:6071 */       return i;
/* 2351:     */     }
/* 2352:6073 */     return -1;
/* 2353:     */   }
/* 2354:     */   
/* 2355:     */   public static int indexOfDifference(String[] strs)
/* 2356:     */   {
/* 2357:6108 */     if ((strs == null) || (strs.length <= 1)) {
/* 2358:6109 */       return -1;
/* 2359:     */     }
/* 2360:6111 */     boolean anyStringNull = false;
/* 2361:6112 */     boolean allStringsNull = true;
/* 2362:6113 */     int arrayLen = strs.length;
/* 2363:6114 */     int shortestStrLen = 2147483647;
/* 2364:6115 */     int longestStrLen = 0;
/* 2365:6120 */     for (int i = 0; i < arrayLen; i++) {
/* 2366:6121 */       if (strs[i] == null)
/* 2367:     */       {
/* 2368:6122 */         anyStringNull = true;
/* 2369:6123 */         shortestStrLen = 0;
/* 2370:     */       }
/* 2371:     */       else
/* 2372:     */       {
/* 2373:6125 */         allStringsNull = false;
/* 2374:6126 */         shortestStrLen = Math.min(strs[i].length(), shortestStrLen);
/* 2375:6127 */         longestStrLen = Math.max(strs[i].length(), longestStrLen);
/* 2376:     */       }
/* 2377:     */     }
/* 2378:6132 */     if ((allStringsNull) || ((longestStrLen == 0) && (!anyStringNull))) {
/* 2379:6133 */       return -1;
/* 2380:     */     }
/* 2381:6137 */     if (shortestStrLen == 0) {
/* 2382:6138 */       return 0;
/* 2383:     */     }
/* 2384:6142 */     int firstDiff = -1;
/* 2385:6143 */     for (int stringPos = 0; stringPos < shortestStrLen; stringPos++)
/* 2386:     */     {
/* 2387:6144 */       char comparisonChar = strs[0].charAt(stringPos);
/* 2388:6145 */       for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
/* 2389:6146 */         if (strs[arrayPos].charAt(stringPos) != comparisonChar)
/* 2390:     */         {
/* 2391:6147 */           firstDiff = stringPos;
/* 2392:6148 */           break;
/* 2393:     */         }
/* 2394:     */       }
/* 2395:6151 */       if (firstDiff != -1) {
/* 2396:     */         break;
/* 2397:     */       }
/* 2398:     */     }
/* 2399:6156 */     if ((firstDiff == -1) && (shortestStrLen != longestStrLen)) {
/* 2400:6160 */       return shortestStrLen;
/* 2401:     */     }
/* 2402:6162 */     return firstDiff;
/* 2403:     */   }
/* 2404:     */   
/* 2405:     */   public static String getCommonPrefix(String[] strs)
/* 2406:     */   {
/* 2407:6199 */     if ((strs == null) || (strs.length == 0)) {
/* 2408:6200 */       return "";
/* 2409:     */     }
/* 2410:6202 */     int smallestIndexOfDiff = indexOfDifference(strs);
/* 2411:6203 */     if (smallestIndexOfDiff == -1)
/* 2412:     */     {
/* 2413:6205 */       if (strs[0] == null) {
/* 2414:6206 */         return "";
/* 2415:     */       }
/* 2416:6208 */       return strs[0];
/* 2417:     */     }
/* 2418:6209 */     if (smallestIndexOfDiff == 0) {
/* 2419:6211 */       return "";
/* 2420:     */     }
/* 2421:6214 */     return strs[0].substring(0, smallestIndexOfDiff);
/* 2422:     */   }
/* 2423:     */   
/* 2424:     */   public static int getLevenshteinDistance(String s, String t)
/* 2425:     */   {
/* 2426:6255 */     if ((s == null) || (t == null)) {
/* 2427:6256 */       throw new IllegalArgumentException("Strings must not be null");
/* 2428:     */     }
/* 2429:6276 */     int n = s.length();
/* 2430:6277 */     int m = t.length();
/* 2431:6279 */     if (n == 0) {
/* 2432:6280 */       return m;
/* 2433:     */     }
/* 2434:6281 */     if (m == 0) {
/* 2435:6282 */       return n;
/* 2436:     */     }
/* 2437:6285 */     if (n > m)
/* 2438:     */     {
/* 2439:6287 */       String tmp = s;
/* 2440:6288 */       s = t;
/* 2441:6289 */       t = tmp;
/* 2442:6290 */       n = m;
/* 2443:6291 */       m = t.length();
/* 2444:     */     }
/* 2445:6294 */     int[] p = new int[n + 1];
/* 2446:6295 */     int[] d = new int[n + 1];
/* 2447:6306 */     for (int i = 0; i <= n; i++) {
/* 2448:6307 */       p[i] = i;
/* 2449:     */     }
/* 2450:6310 */     for (int j = 1; j <= m; j++)
/* 2451:     */     {
/* 2452:6311 */       char t_j = t.charAt(j - 1);
/* 2453:6312 */       d[0] = j;
/* 2454:6314 */       for (i = 1; i <= n; i++)
/* 2455:     */       {
/* 2456:6315 */         int cost = s.charAt(i - 1) == t_j ? 0 : 1;
/* 2457:     */         
/* 2458:6317 */         d[i] = Math.min(Math.min(d[(i - 1)] + 1, p[i] + 1), p[(i - 1)] + cost);
/* 2459:     */       }
/* 2460:6321 */       int[] _d = p;
/* 2461:6322 */       p = d;
/* 2462:6323 */       d = _d;
/* 2463:     */     }
/* 2464:6328 */     return p[n];
/* 2465:     */   }
/* 2466:     */   
/* 2467:     */   public static boolean startsWith(String str, String prefix)
/* 2468:     */   {
/* 2469:6356 */     return startsWith(str, prefix, false);
/* 2470:     */   }
/* 2471:     */   
/* 2472:     */   public static boolean startsWithIgnoreCase(String str, String prefix)
/* 2473:     */   {
/* 2474:6381 */     return startsWith(str, prefix, true);
/* 2475:     */   }
/* 2476:     */   
/* 2477:     */   private static boolean startsWith(String str, String prefix, boolean ignoreCase)
/* 2478:     */   {
/* 2479:6396 */     if ((str == null) || (prefix == null)) {
/* 2480:6397 */       return (str == null) && (prefix == null);
/* 2481:     */     }
/* 2482:6399 */     if (prefix.length() > str.length()) {
/* 2483:6400 */       return false;
/* 2484:     */     }
/* 2485:6402 */     return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
/* 2486:     */   }
/* 2487:     */   
/* 2488:     */   public static boolean startsWithAny(String string, String[] searchStrings)
/* 2489:     */   {
/* 2490:6425 */     if ((isEmpty(string)) || (ArrayUtils.isEmpty(searchStrings))) {
/* 2491:6426 */       return false;
/* 2492:     */     }
/* 2493:6428 */     for (int i = 0; i < searchStrings.length; i++)
/* 2494:     */     {
/* 2495:6429 */       String searchString = searchStrings[i];
/* 2496:6430 */       if (startsWith(string, searchString)) {
/* 2497:6431 */         return true;
/* 2498:     */       }
/* 2499:     */     }
/* 2500:6434 */     return false;
/* 2501:     */   }
/* 2502:     */   
/* 2503:     */   public static boolean endsWith(String str, String suffix)
/* 2504:     */   {
/* 2505:6463 */     return endsWith(str, suffix, false);
/* 2506:     */   }
/* 2507:     */   
/* 2508:     */   public static boolean endsWithIgnoreCase(String str, String suffix)
/* 2509:     */   {
/* 2510:6489 */     return endsWith(str, suffix, true);
/* 2511:     */   }
/* 2512:     */   
/* 2513:     */   private static boolean endsWith(String str, String suffix, boolean ignoreCase)
/* 2514:     */   {
/* 2515:6504 */     if ((str == null) || (suffix == null)) {
/* 2516:6505 */       return (str == null) && (suffix == null);
/* 2517:     */     }
/* 2518:6507 */     if (suffix.length() > str.length()) {
/* 2519:6508 */       return false;
/* 2520:     */     }
/* 2521:6510 */     int strOffset = str.length() - suffix.length();
/* 2522:6511 */     return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
/* 2523:     */   }
/* 2524:     */   
/* 2525:     */   public static String normalizeSpace(String str)
/* 2526:     */   {
/* 2527:6545 */     str = strip(str);
/* 2528:6546 */     if ((str == null) || (str.length() <= 2)) {
/* 2529:6547 */       return str;
/* 2530:     */     }
/* 2531:6549 */     StrBuilder b = new StrBuilder(str.length());
/* 2532:6550 */     for (int i = 0; i < str.length(); i++)
/* 2533:     */     {
/* 2534:6551 */       char c = str.charAt(i);
/* 2535:6552 */       if (Character.isWhitespace(c))
/* 2536:     */       {
/* 2537:6553 */         if ((i > 0) && (!Character.isWhitespace(str.charAt(i - 1)))) {
/* 2538:6554 */           b.append(' ');
/* 2539:     */         }
/* 2540:     */       }
/* 2541:     */       else {
/* 2542:6557 */         b.append(c);
/* 2543:     */       }
/* 2544:     */     }
/* 2545:6560 */     return b.toString();
/* 2546:     */   }
/* 2547:     */   
/* 2548:     */   public static boolean endsWithAny(String string, String[] searchStrings)
/* 2549:     */   {
/* 2550:6582 */     if ((isEmpty(string)) || (ArrayUtils.isEmpty(searchStrings))) {
/* 2551:6583 */       return false;
/* 2552:     */     }
/* 2553:6585 */     for (int i = 0; i < searchStrings.length; i++)
/* 2554:     */     {
/* 2555:6586 */       String searchString = searchStrings[i];
/* 2556:6587 */       if (endsWith(string, searchString)) {
/* 2557:6588 */         return true;
/* 2558:     */       }
/* 2559:     */     }
/* 2560:6591 */     return false;
/* 2561:     */   }
/* 2562:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.StringUtils
 * JD-Core Version:    0.7.0.1
 */