/*    1:     */ package org.apache.commons.io;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.util.ArrayList;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.Stack;
/*    7:     */ 
/*    8:     */ public class FilenameUtils
/*    9:     */ {
/*   10:     */   public static final char EXTENSION_SEPARATOR = '.';
/*   11: 104 */   public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
/*   12:     */   private static final char UNIX_SEPARATOR = '/';
/*   13:     */   private static final char WINDOWS_SEPARATOR = '\\';
/*   14: 119 */   private static final char SYSTEM_SEPARATOR = File.separatorChar;
/*   15:     */   private static final char OTHER_SEPARATOR;
/*   16:     */   
/*   17:     */   static
/*   18:     */   {
/*   19: 126 */     if (isSystemWindows()) {
/*   20: 127 */       OTHER_SEPARATOR = '/';
/*   21:     */     } else {
/*   22: 129 */       OTHER_SEPARATOR = '\\';
/*   23:     */     }
/*   24:     */   }
/*   25:     */   
/*   26:     */   static boolean isSystemWindows()
/*   27:     */   {
/*   28: 147 */     return SYSTEM_SEPARATOR == '\\';
/*   29:     */   }
/*   30:     */   
/*   31:     */   private static boolean isSeparator(char ch)
/*   32:     */   {
/*   33: 158 */     return (ch == '/') || (ch == '\\');
/*   34:     */   }
/*   35:     */   
/*   36:     */   public static String normalize(String filename)
/*   37:     */   {
/*   38: 203 */     return doNormalize(filename, SYSTEM_SEPARATOR, true);
/*   39:     */   }
/*   40:     */   
/*   41:     */   public static String normalize(String filename, boolean unixSeparator)
/*   42:     */   {
/*   43: 250 */     char separator = unixSeparator ? '/' : '\\';
/*   44: 251 */     return doNormalize(filename, separator, true);
/*   45:     */   }
/*   46:     */   
/*   47:     */   public static String normalizeNoEndSeparator(String filename)
/*   48:     */   {
/*   49: 297 */     return doNormalize(filename, SYSTEM_SEPARATOR, false);
/*   50:     */   }
/*   51:     */   
/*   52:     */   public static String normalizeNoEndSeparator(String filename, boolean unixSeparator)
/*   53:     */   {
/*   54: 344 */     char separator = unixSeparator ? '/' : '\\';
/*   55: 345 */     return doNormalize(filename, separator, false);
/*   56:     */   }
/*   57:     */   
/*   58:     */   private static String doNormalize(String filename, char separator, boolean keepSeparator)
/*   59:     */   {
/*   60: 357 */     if (filename == null) {
/*   61: 358 */       return null;
/*   62:     */     }
/*   63: 360 */     int size = filename.length();
/*   64: 361 */     if (size == 0) {
/*   65: 362 */       return filename;
/*   66:     */     }
/*   67: 364 */     int prefix = getPrefixLength(filename);
/*   68: 365 */     if (prefix < 0) {
/*   69: 366 */       return null;
/*   70:     */     }
/*   71: 369 */     char[] array = new char[size + 2];
/*   72: 370 */     filename.getChars(0, filename.length(), array, 0);
/*   73:     */     
/*   74:     */ 
/*   75: 373 */     char otherSeparator = separator == SYSTEM_SEPARATOR ? OTHER_SEPARATOR : SYSTEM_SEPARATOR;
/*   76: 374 */     for (int i = 0; i < array.length; i++) {
/*   77: 375 */       if (array[i] == otherSeparator) {
/*   78: 376 */         array[i] = separator;
/*   79:     */       }
/*   80:     */     }
/*   81: 381 */     boolean lastIsDirectory = true;
/*   82: 382 */     if (array[(size - 1)] != separator)
/*   83:     */     {
/*   84: 383 */       array[(size++)] = separator;
/*   85: 384 */       lastIsDirectory = false;
/*   86:     */     }
/*   87: 388 */     for (int i = prefix + 1; i < size; i++) {
/*   88: 389 */       if ((array[i] == separator) && (array[(i - 1)] == separator))
/*   89:     */       {
/*   90: 390 */         System.arraycopy(array, i, array, i - 1, size - i);
/*   91: 391 */         size--;
/*   92: 392 */         i--;
/*   93:     */       }
/*   94:     */     }
/*   95: 397 */     for (int i = prefix + 1; i < size; i++) {
/*   96: 398 */       if ((array[i] == separator) && (array[(i - 1)] == '.') && ((i == prefix + 1) || (array[(i - 2)] == separator)))
/*   97:     */       {
/*   98: 400 */         if (i == size - 1) {
/*   99: 401 */           lastIsDirectory = true;
/*  100:     */         }
/*  101: 403 */         System.arraycopy(array, i + 1, array, i - 1, size - i);
/*  102: 404 */         size -= 2;
/*  103: 405 */         i--;
/*  104:     */       }
/*  105:     */     }
/*  106:     */     label464:
/*  107: 411 */     for (int i = prefix + 2; i < size; i++) {
/*  108: 412 */       if ((array[i] == separator) && (array[(i - 1)] == '.') && (array[(i - 2)] == '.') && ((i == prefix + 2) || (array[(i - 3)] == separator)))
/*  109:     */       {
/*  110: 414 */         if (i == prefix + 2) {
/*  111: 415 */           return null;
/*  112:     */         }
/*  113: 417 */         if (i == size - 1) {
/*  114: 418 */           lastIsDirectory = true;
/*  115:     */         }
/*  116: 421 */         for (int j = i - 4; j >= prefix; j--) {
/*  117: 422 */           if (array[j] == separator)
/*  118:     */           {
/*  119: 424 */             System.arraycopy(array, i + 1, array, j + 1, size - i);
/*  120: 425 */             size -= i - j;
/*  121: 426 */             i = j + 1;
/*  122:     */             break label464;
/*  123:     */           }
/*  124:     */         }
/*  125: 431 */         System.arraycopy(array, i + 1, array, prefix, size - i);
/*  126: 432 */         size -= i + 1 - prefix;
/*  127: 433 */         i = prefix + 1;
/*  128:     */       }
/*  129:     */     }
/*  130: 437 */     if (size <= 0) {
/*  131: 438 */       return "";
/*  132:     */     }
/*  133: 440 */     if (size <= prefix) {
/*  134: 441 */       return new String(array, 0, size);
/*  135:     */     }
/*  136: 443 */     if ((lastIsDirectory) && (keepSeparator)) {
/*  137: 444 */       return new String(array, 0, size);
/*  138:     */     }
/*  139: 446 */     return new String(array, 0, size - 1);
/*  140:     */   }
/*  141:     */   
/*  142:     */   public static String concat(String basePath, String fullFilenameToAdd)
/*  143:     */   {
/*  144: 491 */     int prefix = getPrefixLength(fullFilenameToAdd);
/*  145: 492 */     if (prefix < 0) {
/*  146: 493 */       return null;
/*  147:     */     }
/*  148: 495 */     if (prefix > 0) {
/*  149: 496 */       return normalize(fullFilenameToAdd);
/*  150:     */     }
/*  151: 498 */     if (basePath == null) {
/*  152: 499 */       return null;
/*  153:     */     }
/*  154: 501 */     int len = basePath.length();
/*  155: 502 */     if (len == 0) {
/*  156: 503 */       return normalize(fullFilenameToAdd);
/*  157:     */     }
/*  158: 505 */     char ch = basePath.charAt(len - 1);
/*  159: 506 */     if (isSeparator(ch)) {
/*  160: 507 */       return normalize(basePath + fullFilenameToAdd);
/*  161:     */     }
/*  162: 509 */     return normalize(basePath + '/' + fullFilenameToAdd);
/*  163:     */   }
/*  164:     */   
/*  165:     */   public static String separatorsToUnix(String path)
/*  166:     */   {
/*  167: 521 */     if ((path == null) || (path.indexOf('\\') == -1)) {
/*  168: 522 */       return path;
/*  169:     */     }
/*  170: 524 */     return path.replace('\\', '/');
/*  171:     */   }
/*  172:     */   
/*  173:     */   public static String separatorsToWindows(String path)
/*  174:     */   {
/*  175: 534 */     if ((path == null) || (path.indexOf('/') == -1)) {
/*  176: 535 */       return path;
/*  177:     */     }
/*  178: 537 */     return path.replace('/', '\\');
/*  179:     */   }
/*  180:     */   
/*  181:     */   public static String separatorsToSystem(String path)
/*  182:     */   {
/*  183: 547 */     if (path == null) {
/*  184: 548 */       return null;
/*  185:     */     }
/*  186: 550 */     if (isSystemWindows()) {
/*  187: 551 */       return separatorsToWindows(path);
/*  188:     */     }
/*  189: 553 */     return separatorsToUnix(path);
/*  190:     */   }
/*  191:     */   
/*  192:     */   public static int getPrefixLength(String filename)
/*  193:     */   {
/*  194: 590 */     if (filename == null) {
/*  195: 591 */       return -1;
/*  196:     */     }
/*  197: 593 */     int len = filename.length();
/*  198: 594 */     if (len == 0) {
/*  199: 595 */       return 0;
/*  200:     */     }
/*  201: 597 */     char ch0 = filename.charAt(0);
/*  202: 598 */     if (ch0 == ':') {
/*  203: 599 */       return -1;
/*  204:     */     }
/*  205: 601 */     if (len == 1)
/*  206:     */     {
/*  207: 602 */       if (ch0 == '~') {
/*  208: 603 */         return 2;
/*  209:     */       }
/*  210: 605 */       return isSeparator(ch0) ? 1 : 0;
/*  211:     */     }
/*  212: 607 */     if (ch0 == '~')
/*  213:     */     {
/*  214: 608 */       int posUnix = filename.indexOf('/', 1);
/*  215: 609 */       int posWin = filename.indexOf('\\', 1);
/*  216: 610 */       if ((posUnix == -1) && (posWin == -1)) {
/*  217: 611 */         return len + 1;
/*  218:     */       }
/*  219: 613 */       posUnix = posUnix == -1 ? posWin : posUnix;
/*  220: 614 */       posWin = posWin == -1 ? posUnix : posWin;
/*  221: 615 */       return Math.min(posUnix, posWin) + 1;
/*  222:     */     }
/*  223: 617 */     char ch1 = filename.charAt(1);
/*  224: 618 */     if (ch1 == ':')
/*  225:     */     {
/*  226: 619 */       ch0 = Character.toUpperCase(ch0);
/*  227: 620 */       if ((ch0 >= 'A') && (ch0 <= 'Z'))
/*  228:     */       {
/*  229: 621 */         if ((len == 2) || (!isSeparator(filename.charAt(2)))) {
/*  230: 622 */           return 2;
/*  231:     */         }
/*  232: 624 */         return 3;
/*  233:     */       }
/*  234: 626 */       return -1;
/*  235:     */     }
/*  236: 628 */     if ((isSeparator(ch0)) && (isSeparator(ch1)))
/*  237:     */     {
/*  238: 629 */       int posUnix = filename.indexOf('/', 2);
/*  239: 630 */       int posWin = filename.indexOf('\\', 2);
/*  240: 631 */       if (((posUnix == -1) && (posWin == -1)) || (posUnix == 2) || (posWin == 2)) {
/*  241: 632 */         return -1;
/*  242:     */       }
/*  243: 634 */       posUnix = posUnix == -1 ? posWin : posUnix;
/*  244: 635 */       posWin = posWin == -1 ? posUnix : posWin;
/*  245: 636 */       return Math.min(posUnix, posWin) + 1;
/*  246:     */     }
/*  247: 638 */     return isSeparator(ch0) ? 1 : 0;
/*  248:     */   }
/*  249:     */   
/*  250:     */   public static int indexOfLastSeparator(String filename)
/*  251:     */   {
/*  252: 656 */     if (filename == null) {
/*  253: 657 */       return -1;
/*  254:     */     }
/*  255: 659 */     int lastUnixPos = filename.lastIndexOf('/');
/*  256: 660 */     int lastWindowsPos = filename.lastIndexOf('\\');
/*  257: 661 */     return Math.max(lastUnixPos, lastWindowsPos);
/*  258:     */   }
/*  259:     */   
/*  260:     */   public static int indexOfExtension(String filename)
/*  261:     */   {
/*  262: 678 */     if (filename == null) {
/*  263: 679 */       return -1;
/*  264:     */     }
/*  265: 681 */     int extensionPos = filename.lastIndexOf('.');
/*  266: 682 */     int lastSeparator = indexOfLastSeparator(filename);
/*  267: 683 */     return lastSeparator > extensionPos ? -1 : extensionPos;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public static String getPrefix(String filename)
/*  271:     */   {
/*  272: 717 */     if (filename == null) {
/*  273: 718 */       return null;
/*  274:     */     }
/*  275: 720 */     int len = getPrefixLength(filename);
/*  276: 721 */     if (len < 0) {
/*  277: 722 */       return null;
/*  278:     */     }
/*  279: 724 */     if (len > filename.length()) {
/*  280: 725 */       return filename + '/';
/*  281:     */     }
/*  282: 727 */     return filename.substring(0, len);
/*  283:     */   }
/*  284:     */   
/*  285:     */   public static String getPath(String filename)
/*  286:     */   {
/*  287: 753 */     return doGetPath(filename, 1);
/*  288:     */   }
/*  289:     */   
/*  290:     */   public static String getPathNoEndSeparator(String filename)
/*  291:     */   {
/*  292: 780 */     return doGetPath(filename, 0);
/*  293:     */   }
/*  294:     */   
/*  295:     */   private static String doGetPath(String filename, int separatorAdd)
/*  296:     */   {
/*  297: 791 */     if (filename == null) {
/*  298: 792 */       return null;
/*  299:     */     }
/*  300: 794 */     int prefix = getPrefixLength(filename);
/*  301: 795 */     if (prefix < 0) {
/*  302: 796 */       return null;
/*  303:     */     }
/*  304: 798 */     int index = indexOfLastSeparator(filename);
/*  305: 799 */     int endIndex = index + separatorAdd;
/*  306: 800 */     if ((prefix >= filename.length()) || (index < 0) || (prefix >= endIndex)) {
/*  307: 801 */       return "";
/*  308:     */     }
/*  309: 803 */     return filename.substring(prefix, endIndex);
/*  310:     */   }
/*  311:     */   
/*  312:     */   public static String getFullPath(String filename)
/*  313:     */   {
/*  314: 832 */     return doGetFullPath(filename, true);
/*  315:     */   }
/*  316:     */   
/*  317:     */   public static String getFullPathNoEndSeparator(String filename)
/*  318:     */   {
/*  319: 862 */     return doGetFullPath(filename, false);
/*  320:     */   }
/*  321:     */   
/*  322:     */   private static String doGetFullPath(String filename, boolean includeSeparator)
/*  323:     */   {
/*  324: 873 */     if (filename == null) {
/*  325: 874 */       return null;
/*  326:     */     }
/*  327: 876 */     int prefix = getPrefixLength(filename);
/*  328: 877 */     if (prefix < 0) {
/*  329: 878 */       return null;
/*  330:     */     }
/*  331: 880 */     if (prefix >= filename.length())
/*  332:     */     {
/*  333: 881 */       if (includeSeparator) {
/*  334: 882 */         return getPrefix(filename);
/*  335:     */       }
/*  336: 884 */       return filename;
/*  337:     */     }
/*  338: 887 */     int index = indexOfLastSeparator(filename);
/*  339: 888 */     if (index < 0) {
/*  340: 889 */       return filename.substring(0, prefix);
/*  341:     */     }
/*  342: 891 */     int end = index + (includeSeparator ? 1 : 0);
/*  343: 892 */     if (end == 0) {
/*  344: 893 */       end++;
/*  345:     */     }
/*  346: 895 */     return filename.substring(0, end);
/*  347:     */   }
/*  348:     */   
/*  349:     */   public static String getName(String filename)
/*  350:     */   {
/*  351: 916 */     if (filename == null) {
/*  352: 917 */       return null;
/*  353:     */     }
/*  354: 919 */     int index = indexOfLastSeparator(filename);
/*  355: 920 */     return filename.substring(index + 1);
/*  356:     */   }
/*  357:     */   
/*  358:     */   public static String getBaseName(String filename)
/*  359:     */   {
/*  360: 941 */     return removeExtension(getName(filename));
/*  361:     */   }
/*  362:     */   
/*  363:     */   public static String getExtension(String filename)
/*  364:     */   {
/*  365: 963 */     if (filename == null) {
/*  366: 964 */       return null;
/*  367:     */     }
/*  368: 966 */     int index = indexOfExtension(filename);
/*  369: 967 */     if (index == -1) {
/*  370: 968 */       return "";
/*  371:     */     }
/*  372: 970 */     return filename.substring(index + 1);
/*  373:     */   }
/*  374:     */   
/*  375:     */   public static String removeExtension(String filename)
/*  376:     */   {
/*  377: 993 */     if (filename == null) {
/*  378: 994 */       return null;
/*  379:     */     }
/*  380: 996 */     int index = indexOfExtension(filename);
/*  381: 997 */     if (index == -1) {
/*  382: 998 */       return filename;
/*  383:     */     }
/*  384:1000 */     return filename.substring(0, index);
/*  385:     */   }
/*  386:     */   
/*  387:     */   public static boolean equals(String filename1, String filename2)
/*  388:     */   {
/*  389:1017 */     return equals(filename1, filename2, false, IOCase.SENSITIVE);
/*  390:     */   }
/*  391:     */   
/*  392:     */   public static boolean equalsOnSystem(String filename1, String filename2)
/*  393:     */   {
/*  394:1032 */     return equals(filename1, filename2, false, IOCase.SYSTEM);
/*  395:     */   }
/*  396:     */   
/*  397:     */   public static boolean equalsNormalized(String filename1, String filename2)
/*  398:     */   {
/*  399:1048 */     return equals(filename1, filename2, true, IOCase.SENSITIVE);
/*  400:     */   }
/*  401:     */   
/*  402:     */   public static boolean equalsNormalizedOnSystem(String filename1, String filename2)
/*  403:     */   {
/*  404:1065 */     return equals(filename1, filename2, true, IOCase.SYSTEM);
/*  405:     */   }
/*  406:     */   
/*  407:     */   public static boolean equals(String filename1, String filename2, boolean normalized, IOCase caseSensitivity)
/*  408:     */   {
/*  409:1083 */     if ((filename1 == null) || (filename2 == null)) {
/*  410:1084 */       return (filename1 == null) && (filename2 == null);
/*  411:     */     }
/*  412:1086 */     if (normalized)
/*  413:     */     {
/*  414:1087 */       filename1 = normalize(filename1);
/*  415:1088 */       filename2 = normalize(filename2);
/*  416:1089 */       if ((filename1 == null) || (filename2 == null)) {
/*  417:1090 */         throw new NullPointerException("Error normalizing one or both of the file names");
/*  418:     */       }
/*  419:     */     }
/*  420:1094 */     if (caseSensitivity == null) {
/*  421:1095 */       caseSensitivity = IOCase.SENSITIVE;
/*  422:     */     }
/*  423:1097 */     return caseSensitivity.checkEquals(filename1, filename2);
/*  424:     */   }
/*  425:     */   
/*  426:     */   public static boolean isExtension(String filename, String extension)
/*  427:     */   {
/*  428:1113 */     if (filename == null) {
/*  429:1114 */       return false;
/*  430:     */     }
/*  431:1116 */     if ((extension == null) || (extension.length() == 0)) {
/*  432:1117 */       return indexOfExtension(filename) == -1;
/*  433:     */     }
/*  434:1119 */     String fileExt = getExtension(filename);
/*  435:1120 */     return fileExt.equals(extension);
/*  436:     */   }
/*  437:     */   
/*  438:     */   public static boolean isExtension(String filename, String[] extensions)
/*  439:     */   {
/*  440:1135 */     if (filename == null) {
/*  441:1136 */       return false;
/*  442:     */     }
/*  443:1138 */     if ((extensions == null) || (extensions.length == 0)) {
/*  444:1139 */       return indexOfExtension(filename) == -1;
/*  445:     */     }
/*  446:1141 */     String fileExt = getExtension(filename);
/*  447:1142 */     for (String extension : extensions) {
/*  448:1143 */       if (fileExt.equals(extension)) {
/*  449:1144 */         return true;
/*  450:     */       }
/*  451:     */     }
/*  452:1147 */     return false;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public static boolean isExtension(String filename, Collection<String> extensions)
/*  456:     */   {
/*  457:1162 */     if (filename == null) {
/*  458:1163 */       return false;
/*  459:     */     }
/*  460:1165 */     if ((extensions == null) || (extensions.isEmpty())) {
/*  461:1166 */       return indexOfExtension(filename) == -1;
/*  462:     */     }
/*  463:1168 */     String fileExt = getExtension(filename);
/*  464:1169 */     for (String extension : extensions) {
/*  465:1170 */       if (fileExt.equals(extension)) {
/*  466:1171 */         return true;
/*  467:     */       }
/*  468:     */     }
/*  469:1174 */     return false;
/*  470:     */   }
/*  471:     */   
/*  472:     */   public static boolean wildcardMatch(String filename, String wildcardMatcher)
/*  473:     */   {
/*  474:1201 */     return wildcardMatch(filename, wildcardMatcher, IOCase.SENSITIVE);
/*  475:     */   }
/*  476:     */   
/*  477:     */   public static boolean wildcardMatchOnSystem(String filename, String wildcardMatcher)
/*  478:     */   {
/*  479:1227 */     return wildcardMatch(filename, wildcardMatcher, IOCase.SYSTEM);
/*  480:     */   }
/*  481:     */   
/*  482:     */   public static boolean wildcardMatch(String filename, String wildcardMatcher, IOCase caseSensitivity)
/*  483:     */   {
/*  484:1245 */     if ((filename == null) && (wildcardMatcher == null)) {
/*  485:1246 */       return true;
/*  486:     */     }
/*  487:1248 */     if ((filename == null) || (wildcardMatcher == null)) {
/*  488:1249 */       return false;
/*  489:     */     }
/*  490:1251 */     if (caseSensitivity == null) {
/*  491:1252 */       caseSensitivity = IOCase.SENSITIVE;
/*  492:     */     }
/*  493:1254 */     String[] wcs = splitOnTokens(wildcardMatcher);
/*  494:1255 */     boolean anyChars = false;
/*  495:1256 */     int textIdx = 0;
/*  496:1257 */     int wcsIdx = 0;
/*  497:1258 */     Stack<int[]> backtrack = new Stack();
/*  498:     */     do
/*  499:     */     {
/*  500:1262 */       if (backtrack.size() > 0)
/*  501:     */       {
/*  502:1263 */         int[] array = (int[])backtrack.pop();
/*  503:1264 */         wcsIdx = array[0];
/*  504:1265 */         textIdx = array[1];
/*  505:1266 */         anyChars = true;
/*  506:     */       }
/*  507:1270 */       while (wcsIdx < wcs.length)
/*  508:     */       {
/*  509:1272 */         if (wcs[wcsIdx].equals("?"))
/*  510:     */         {
/*  511:1274 */           textIdx++;
/*  512:1275 */           if (textIdx > filename.length()) {
/*  513:     */             break;
/*  514:     */           }
/*  515:1278 */           anyChars = false;
/*  516:     */         }
/*  517:1280 */         else if (wcs[wcsIdx].equals("*"))
/*  518:     */         {
/*  519:1282 */           anyChars = true;
/*  520:1283 */           if (wcsIdx == wcs.length - 1) {
/*  521:1284 */             textIdx = filename.length();
/*  522:     */           }
/*  523:     */         }
/*  524:     */         else
/*  525:     */         {
/*  526:1289 */           if (anyChars)
/*  527:     */           {
/*  528:1291 */             textIdx = caseSensitivity.checkIndexOf(filename, textIdx, wcs[wcsIdx]);
/*  529:1292 */             if (textIdx == -1) {
/*  530:     */               break;
/*  531:     */             }
/*  532:1296 */             int repeat = caseSensitivity.checkIndexOf(filename, textIdx + 1, wcs[wcsIdx]);
/*  533:1297 */             if (repeat >= 0) {
/*  534:1298 */               backtrack.push(new int[] { wcsIdx, repeat });
/*  535:     */             }
/*  536:     */           }
/*  537:     */           else
/*  538:     */           {
/*  539:1302 */             if (!caseSensitivity.checkRegionMatches(filename, textIdx, wcs[wcsIdx])) {
/*  540:     */               break;
/*  541:     */             }
/*  542:     */           }
/*  543:1309 */           textIdx += wcs[wcsIdx].length();
/*  544:1310 */           anyChars = false;
/*  545:     */         }
/*  546:1313 */         wcsIdx++;
/*  547:     */       }
/*  548:1317 */       if ((wcsIdx == wcs.length) && (textIdx == filename.length())) {
/*  549:1318 */         return true;
/*  550:     */       }
/*  551:1321 */     } while (backtrack.size() > 0);
/*  552:1323 */     return false;
/*  553:     */   }
/*  554:     */   
/*  555:     */   static String[] splitOnTokens(String text)
/*  556:     */   {
/*  557:1338 */     if ((text.indexOf('?') == -1) && (text.indexOf('*') == -1)) {
/*  558:1339 */       return new String[] { text };
/*  559:     */     }
/*  560:1342 */     char[] array = text.toCharArray();
/*  561:1343 */     ArrayList<String> list = new ArrayList();
/*  562:1344 */     StringBuilder buffer = new StringBuilder();
/*  563:1345 */     for (int i = 0; i < array.length; i++) {
/*  564:1346 */       if ((array[i] == '?') || (array[i] == '*'))
/*  565:     */       {
/*  566:1347 */         if (buffer.length() != 0)
/*  567:     */         {
/*  568:1348 */           list.add(buffer.toString());
/*  569:1349 */           buffer.setLength(0);
/*  570:     */         }
/*  571:1351 */         if (array[i] == '?') {
/*  572:1352 */           list.add("?");
/*  573:1353 */         } else if ((list.size() == 0) || ((i > 0) && (!((String)list.get(list.size() - 1)).equals("*")))) {
/*  574:1355 */           list.add("*");
/*  575:     */         }
/*  576:     */       }
/*  577:     */       else
/*  578:     */       {
/*  579:1358 */         buffer.append(array[i]);
/*  580:     */       }
/*  581:     */     }
/*  582:1361 */     if (buffer.length() != 0) {
/*  583:1362 */       list.add(buffer.toString());
/*  584:     */     }
/*  585:1365 */     return (String[])list.toArray(new String[list.size()]);
/*  586:     */   }
/*  587:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.FilenameUtils
 * JD-Core Version:    0.7.0.1
 */