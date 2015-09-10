/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.text.NumberFormat;
/*    5:     */ import java.text.ParseException;
/*    6:     */ import java.util.Collections;
/*    7:     */ import java.util.Enumeration;
/*    8:     */ import java.util.HashMap;
/*    9:     */ import java.util.Iterator;
/*   10:     */ import java.util.Map;
/*   11:     */ import java.util.Map.Entry;
/*   12:     */ import java.util.Properties;
/*   13:     */ import java.util.ResourceBundle;
/*   14:     */ import java.util.Set;
/*   15:     */ import java.util.SortedMap;
/*   16:     */ import java.util.TreeMap;
/*   17:     */ import org.apache.commons.collections.map.FixedSizeMap;
/*   18:     */ import org.apache.commons.collections.map.FixedSizeSortedMap;
/*   19:     */ import org.apache.commons.collections.map.LazyMap;
/*   20:     */ import org.apache.commons.collections.map.LazySortedMap;
/*   21:     */ import org.apache.commons.collections.map.ListOrderedMap;
/*   22:     */ import org.apache.commons.collections.map.MultiValueMap;
/*   23:     */ import org.apache.commons.collections.map.PredicatedMap;
/*   24:     */ import org.apache.commons.collections.map.PredicatedSortedMap;
/*   25:     */ import org.apache.commons.collections.map.TransformedMap;
/*   26:     */ import org.apache.commons.collections.map.TransformedSortedMap;
/*   27:     */ import org.apache.commons.collections.map.TypedMap;
/*   28:     */ import org.apache.commons.collections.map.TypedSortedMap;
/*   29:     */ import org.apache.commons.collections.map.UnmodifiableMap;
/*   30:     */ import org.apache.commons.collections.map.UnmodifiableSortedMap;
/*   31:     */ 
/*   32:     */ public class MapUtils
/*   33:     */ {
/*   34:  96 */   public static final Map EMPTY_MAP = UnmodifiableMap.decorate(new HashMap(1));
/*   35: 101 */   public static final SortedMap EMPTY_SORTED_MAP = UnmodifiableSortedMap.decorate(new TreeMap());
/*   36:     */   private static final String INDENT_STRING = "    ";
/*   37:     */   
/*   38:     */   public static Object getObject(Map map, Object key)
/*   39:     */   {
/*   40: 123 */     if (map != null) {
/*   41: 124 */       return map.get(key);
/*   42:     */     }
/*   43: 126 */     return null;
/*   44:     */   }
/*   45:     */   
/*   46:     */   public static String getString(Map map, Object key)
/*   47:     */   {
/*   48: 139 */     if (map != null)
/*   49:     */     {
/*   50: 140 */       Object answer = map.get(key);
/*   51: 141 */       if (answer != null) {
/*   52: 142 */         return answer.toString();
/*   53:     */       }
/*   54:     */     }
/*   55: 145 */     return null;
/*   56:     */   }
/*   57:     */   
/*   58:     */   public static Boolean getBoolean(Map map, Object key)
/*   59:     */   {
/*   60: 163 */     if (map != null)
/*   61:     */     {
/*   62: 164 */       Object answer = map.get(key);
/*   63: 165 */       if (answer != null)
/*   64:     */       {
/*   65: 166 */         if ((answer instanceof Boolean)) {
/*   66: 167 */           return (Boolean)answer;
/*   67:     */         }
/*   68: 169 */         if ((answer instanceof String)) {
/*   69: 170 */           return new Boolean((String)answer);
/*   70:     */         }
/*   71: 172 */         if ((answer instanceof Number))
/*   72:     */         {
/*   73: 173 */           Number n = (Number)answer;
/*   74: 174 */           return n.intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
/*   75:     */         }
/*   76:     */       }
/*   77:     */     }
/*   78: 178 */     return null;
/*   79:     */   }
/*   80:     */   
/*   81:     */   public static Number getNumber(Map map, Object key)
/*   82:     */   {
/*   83: 195 */     if (map != null)
/*   84:     */     {
/*   85: 196 */       Object answer = map.get(key);
/*   86: 197 */       if (answer != null)
/*   87:     */       {
/*   88: 198 */         if ((answer instanceof Number)) {
/*   89: 199 */           return (Number)answer;
/*   90:     */         }
/*   91: 201 */         if ((answer instanceof String)) {
/*   92:     */           try
/*   93:     */           {
/*   94: 203 */             String text = (String)answer;
/*   95: 204 */             return NumberFormat.getInstance().parse(text);
/*   96:     */           }
/*   97:     */           catch (ParseException e)
/*   98:     */           {
/*   99: 207 */             logInfo(e);
/*  100:     */           }
/*  101:     */         }
/*  102:     */       }
/*  103:     */     }
/*  104: 212 */     return null;
/*  105:     */   }
/*  106:     */   
/*  107:     */   public static Byte getByte(Map map, Object key)
/*  108:     */   {
/*  109: 225 */     Number answer = getNumber(map, key);
/*  110: 226 */     if (answer == null) {
/*  111: 227 */       return null;
/*  112:     */     }
/*  113: 228 */     if ((answer instanceof Byte)) {
/*  114: 229 */       return (Byte)answer;
/*  115:     */     }
/*  116: 231 */     return new Byte(answer.byteValue());
/*  117:     */   }
/*  118:     */   
/*  119:     */   public static Short getShort(Map map, Object key)
/*  120:     */   {
/*  121: 244 */     Number answer = getNumber(map, key);
/*  122: 245 */     if (answer == null) {
/*  123: 246 */       return null;
/*  124:     */     }
/*  125: 247 */     if ((answer instanceof Short)) {
/*  126: 248 */       return (Short)answer;
/*  127:     */     }
/*  128: 250 */     return new Short(answer.shortValue());
/*  129:     */   }
/*  130:     */   
/*  131:     */   public static Integer getInteger(Map map, Object key)
/*  132:     */   {
/*  133: 263 */     Number answer = getNumber(map, key);
/*  134: 264 */     if (answer == null) {
/*  135: 265 */       return null;
/*  136:     */     }
/*  137: 266 */     if ((answer instanceof Integer)) {
/*  138: 267 */       return (Integer)answer;
/*  139:     */     }
/*  140: 269 */     return new Integer(answer.intValue());
/*  141:     */   }
/*  142:     */   
/*  143:     */   public static Long getLong(Map map, Object key)
/*  144:     */   {
/*  145: 282 */     Number answer = getNumber(map, key);
/*  146: 283 */     if (answer == null) {
/*  147: 284 */       return null;
/*  148:     */     }
/*  149: 285 */     if ((answer instanceof Long)) {
/*  150: 286 */       return (Long)answer;
/*  151:     */     }
/*  152: 288 */     return new Long(answer.longValue());
/*  153:     */   }
/*  154:     */   
/*  155:     */   public static Float getFloat(Map map, Object key)
/*  156:     */   {
/*  157: 301 */     Number answer = getNumber(map, key);
/*  158: 302 */     if (answer == null) {
/*  159: 303 */       return null;
/*  160:     */     }
/*  161: 304 */     if ((answer instanceof Float)) {
/*  162: 305 */       return (Float)answer;
/*  163:     */     }
/*  164: 307 */     return new Float(answer.floatValue());
/*  165:     */   }
/*  166:     */   
/*  167:     */   public static Double getDouble(Map map, Object key)
/*  168:     */   {
/*  169: 320 */     Number answer = getNumber(map, key);
/*  170: 321 */     if (answer == null) {
/*  171: 322 */       return null;
/*  172:     */     }
/*  173: 323 */     if ((answer instanceof Double)) {
/*  174: 324 */       return (Double)answer;
/*  175:     */     }
/*  176: 326 */     return new Double(answer.doubleValue());
/*  177:     */   }
/*  178:     */   
/*  179:     */   public static Map getMap(Map map, Object key)
/*  180:     */   {
/*  181: 340 */     if (map != null)
/*  182:     */     {
/*  183: 341 */       Object answer = map.get(key);
/*  184: 342 */       if ((answer != null) && ((answer instanceof Map))) {
/*  185: 343 */         return (Map)answer;
/*  186:     */       }
/*  187:     */     }
/*  188: 346 */     return null;
/*  189:     */   }
/*  190:     */   
/*  191:     */   public static Object getObject(Map map, Object key, Object defaultValue)
/*  192:     */   {
/*  193: 362 */     if (map != null)
/*  194:     */     {
/*  195: 363 */       Object answer = map.get(key);
/*  196: 364 */       if (answer != null) {
/*  197: 365 */         return answer;
/*  198:     */       }
/*  199:     */     }
/*  200: 368 */     return defaultValue;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public static String getString(Map map, Object key, String defaultValue)
/*  204:     */   {
/*  205: 384 */     String answer = getString(map, key);
/*  206: 385 */     if (answer == null) {
/*  207: 386 */       answer = defaultValue;
/*  208:     */     }
/*  209: 388 */     return answer;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public static Boolean getBoolean(Map map, Object key, Boolean defaultValue)
/*  213:     */   {
/*  214: 404 */     Boolean answer = getBoolean(map, key);
/*  215: 405 */     if (answer == null) {
/*  216: 406 */       answer = defaultValue;
/*  217:     */     }
/*  218: 408 */     return answer;
/*  219:     */   }
/*  220:     */   
/*  221:     */   public static Number getNumber(Map map, Object key, Number defaultValue)
/*  222:     */   {
/*  223: 424 */     Number answer = getNumber(map, key);
/*  224: 425 */     if (answer == null) {
/*  225: 426 */       answer = defaultValue;
/*  226:     */     }
/*  227: 428 */     return answer;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public static Byte getByte(Map map, Object key, Byte defaultValue)
/*  231:     */   {
/*  232: 444 */     Byte answer = getByte(map, key);
/*  233: 445 */     if (answer == null) {
/*  234: 446 */       answer = defaultValue;
/*  235:     */     }
/*  236: 448 */     return answer;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public static Short getShort(Map map, Object key, Short defaultValue)
/*  240:     */   {
/*  241: 464 */     Short answer = getShort(map, key);
/*  242: 465 */     if (answer == null) {
/*  243: 466 */       answer = defaultValue;
/*  244:     */     }
/*  245: 468 */     return answer;
/*  246:     */   }
/*  247:     */   
/*  248:     */   public static Integer getInteger(Map map, Object key, Integer defaultValue)
/*  249:     */   {
/*  250: 484 */     Integer answer = getInteger(map, key);
/*  251: 485 */     if (answer == null) {
/*  252: 486 */       answer = defaultValue;
/*  253:     */     }
/*  254: 488 */     return answer;
/*  255:     */   }
/*  256:     */   
/*  257:     */   public static Long getLong(Map map, Object key, Long defaultValue)
/*  258:     */   {
/*  259: 504 */     Long answer = getLong(map, key);
/*  260: 505 */     if (answer == null) {
/*  261: 506 */       answer = defaultValue;
/*  262:     */     }
/*  263: 508 */     return answer;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public static Float getFloat(Map map, Object key, Float defaultValue)
/*  267:     */   {
/*  268: 524 */     Float answer = getFloat(map, key);
/*  269: 525 */     if (answer == null) {
/*  270: 526 */       answer = defaultValue;
/*  271:     */     }
/*  272: 528 */     return answer;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public static Double getDouble(Map map, Object key, Double defaultValue)
/*  276:     */   {
/*  277: 544 */     Double answer = getDouble(map, key);
/*  278: 545 */     if (answer == null) {
/*  279: 546 */       answer = defaultValue;
/*  280:     */     }
/*  281: 548 */     return answer;
/*  282:     */   }
/*  283:     */   
/*  284:     */   public static Map getMap(Map map, Object key, Map defaultValue)
/*  285:     */   {
/*  286: 564 */     Map answer = getMap(map, key);
/*  287: 565 */     if (answer == null) {
/*  288: 566 */       answer = defaultValue;
/*  289:     */     }
/*  290: 568 */     return answer;
/*  291:     */   }
/*  292:     */   
/*  293:     */   public static boolean getBooleanValue(Map map, Object key)
/*  294:     */   {
/*  295: 589 */     Boolean booleanObject = getBoolean(map, key);
/*  296: 590 */     if (booleanObject == null) {
/*  297: 591 */       return false;
/*  298:     */     }
/*  299: 593 */     return booleanObject.booleanValue();
/*  300:     */   }
/*  301:     */   
/*  302:     */   public static byte getByteValue(Map map, Object key)
/*  303:     */   {
/*  304: 606 */     Byte byteObject = getByte(map, key);
/*  305: 607 */     if (byteObject == null) {
/*  306: 608 */       return 0;
/*  307:     */     }
/*  308: 610 */     return byteObject.byteValue();
/*  309:     */   }
/*  310:     */   
/*  311:     */   public static short getShortValue(Map map, Object key)
/*  312:     */   {
/*  313: 623 */     Short shortObject = getShort(map, key);
/*  314: 624 */     if (shortObject == null) {
/*  315: 625 */       return 0;
/*  316:     */     }
/*  317: 627 */     return shortObject.shortValue();
/*  318:     */   }
/*  319:     */   
/*  320:     */   public static int getIntValue(Map map, Object key)
/*  321:     */   {
/*  322: 640 */     Integer integerObject = getInteger(map, key);
/*  323: 641 */     if (integerObject == null) {
/*  324: 642 */       return 0;
/*  325:     */     }
/*  326: 644 */     return integerObject.intValue();
/*  327:     */   }
/*  328:     */   
/*  329:     */   public static long getLongValue(Map map, Object key)
/*  330:     */   {
/*  331: 657 */     Long longObject = getLong(map, key);
/*  332: 658 */     if (longObject == null) {
/*  333: 659 */       return 0L;
/*  334:     */     }
/*  335: 661 */     return longObject.longValue();
/*  336:     */   }
/*  337:     */   
/*  338:     */   public static float getFloatValue(Map map, Object key)
/*  339:     */   {
/*  340: 674 */     Float floatObject = getFloat(map, key);
/*  341: 675 */     if (floatObject == null) {
/*  342: 676 */       return 0.0F;
/*  343:     */     }
/*  344: 678 */     return floatObject.floatValue();
/*  345:     */   }
/*  346:     */   
/*  347:     */   public static double getDoubleValue(Map map, Object key)
/*  348:     */   {
/*  349: 691 */     Double doubleObject = getDouble(map, key);
/*  350: 692 */     if (doubleObject == null) {
/*  351: 693 */       return 0.0D;
/*  352:     */     }
/*  353: 695 */     return doubleObject.doubleValue();
/*  354:     */   }
/*  355:     */   
/*  356:     */   public static boolean getBooleanValue(Map map, Object key, boolean defaultValue)
/*  357:     */   {
/*  358: 718 */     Boolean booleanObject = getBoolean(map, key);
/*  359: 719 */     if (booleanObject == null) {
/*  360: 720 */       return defaultValue;
/*  361:     */     }
/*  362: 722 */     return booleanObject.booleanValue();
/*  363:     */   }
/*  364:     */   
/*  365:     */   public static byte getByteValue(Map map, Object key, byte defaultValue)
/*  366:     */   {
/*  367: 738 */     Byte byteObject = getByte(map, key);
/*  368: 739 */     if (byteObject == null) {
/*  369: 740 */       return defaultValue;
/*  370:     */     }
/*  371: 742 */     return byteObject.byteValue();
/*  372:     */   }
/*  373:     */   
/*  374:     */   public static short getShortValue(Map map, Object key, short defaultValue)
/*  375:     */   {
/*  376: 758 */     Short shortObject = getShort(map, key);
/*  377: 759 */     if (shortObject == null) {
/*  378: 760 */       return defaultValue;
/*  379:     */     }
/*  380: 762 */     return shortObject.shortValue();
/*  381:     */   }
/*  382:     */   
/*  383:     */   public static int getIntValue(Map map, Object key, int defaultValue)
/*  384:     */   {
/*  385: 778 */     Integer integerObject = getInteger(map, key);
/*  386: 779 */     if (integerObject == null) {
/*  387: 780 */       return defaultValue;
/*  388:     */     }
/*  389: 782 */     return integerObject.intValue();
/*  390:     */   }
/*  391:     */   
/*  392:     */   public static long getLongValue(Map map, Object key, long defaultValue)
/*  393:     */   {
/*  394: 798 */     Long longObject = getLong(map, key);
/*  395: 799 */     if (longObject == null) {
/*  396: 800 */       return defaultValue;
/*  397:     */     }
/*  398: 802 */     return longObject.longValue();
/*  399:     */   }
/*  400:     */   
/*  401:     */   public static float getFloatValue(Map map, Object key, float defaultValue)
/*  402:     */   {
/*  403: 818 */     Float floatObject = getFloat(map, key);
/*  404: 819 */     if (floatObject == null) {
/*  405: 820 */       return defaultValue;
/*  406:     */     }
/*  407: 822 */     return floatObject.floatValue();
/*  408:     */   }
/*  409:     */   
/*  410:     */   public static double getDoubleValue(Map map, Object key, double defaultValue)
/*  411:     */   {
/*  412: 838 */     Double doubleObject = getDouble(map, key);
/*  413: 839 */     if (doubleObject == null) {
/*  414: 840 */       return defaultValue;
/*  415:     */     }
/*  416: 842 */     return doubleObject.doubleValue();
/*  417:     */   }
/*  418:     */   
/*  419:     */   public static Properties toProperties(Map map)
/*  420:     */   {
/*  421: 855 */     Properties answer = new Properties();
/*  422:     */     Iterator iter;
/*  423: 856 */     if (map != null) {
/*  424: 857 */       for (iter = map.entrySet().iterator(); iter.hasNext();)
/*  425:     */       {
/*  426: 858 */         Map.Entry entry = (Map.Entry)iter.next();
/*  427: 859 */         Object key = entry.getKey();
/*  428: 860 */         Object value = entry.getValue();
/*  429: 861 */         answer.put(key, value);
/*  430:     */       }
/*  431:     */     }
/*  432: 864 */     return answer;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public static Map toMap(ResourceBundle resourceBundle)
/*  436:     */   {
/*  437: 875 */     Enumeration enumeration = resourceBundle.getKeys();
/*  438: 876 */     Map map = new HashMap();
/*  439: 878 */     while (enumeration.hasMoreElements())
/*  440:     */     {
/*  441: 879 */       String key = (String)enumeration.nextElement();
/*  442: 880 */       Object value = resourceBundle.getObject(key);
/*  443: 881 */       map.put(key, value);
/*  444:     */     }
/*  445: 884 */     return map;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public static void verbosePrint(PrintStream out, Object label, Map map)
/*  449:     */   {
/*  450: 912 */     verbosePrintInternal(out, label, map, new ArrayStack(), false);
/*  451:     */   }
/*  452:     */   
/*  453:     */   public static void debugPrint(PrintStream out, Object label, Map map)
/*  454:     */   {
/*  455: 938 */     verbosePrintInternal(out, label, map, new ArrayStack(), true);
/*  456:     */   }
/*  457:     */   
/*  458:     */   protected static void logInfo(Exception ex)
/*  459:     */   {
/*  460: 951 */     System.out.println("INFO: Exception: " + ex);
/*  461:     */   }
/*  462:     */   
/*  463:     */   private static void verbosePrintInternal(PrintStream out, Object label, Map map, ArrayStack lineage, boolean debug)
/*  464:     */   {
/*  465: 984 */     printIndent(out, lineage.size());
/*  466: 986 */     if (map == null)
/*  467:     */     {
/*  468: 987 */       if (label != null)
/*  469:     */       {
/*  470: 988 */         out.print(label);
/*  471: 989 */         out.print(" = ");
/*  472:     */       }
/*  473: 991 */       out.println("null");
/*  474: 992 */       return;
/*  475:     */     }
/*  476: 994 */     if (label != null)
/*  477:     */     {
/*  478: 995 */       out.print(label);
/*  479: 996 */       out.println(" = ");
/*  480:     */     }
/*  481: 999 */     printIndent(out, lineage.size());
/*  482:1000 */     out.println("{");
/*  483:     */     
/*  484:1002 */     lineage.push(map);
/*  485:1004 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/*  486:     */     {
/*  487:1005 */       Map.Entry entry = (Map.Entry)it.next();
/*  488:1006 */       Object childKey = entry.getKey();
/*  489:1007 */       Object childValue = entry.getValue();
/*  490:1008 */       if (((childValue instanceof Map)) && (!lineage.contains(childValue)))
/*  491:     */       {
/*  492:1009 */         verbosePrintInternal(out, childKey == null ? "null" : childKey, (Map)childValue, lineage, debug);
/*  493:     */       }
/*  494:     */       else
/*  495:     */       {
/*  496:1016 */         printIndent(out, lineage.size());
/*  497:1017 */         out.print(childKey);
/*  498:1018 */         out.print(" = ");
/*  499:     */         
/*  500:1020 */         int lineageIndex = lineage.indexOf(childValue);
/*  501:1021 */         if (lineageIndex == -1) {
/*  502:1022 */           out.print(childValue);
/*  503:1023 */         } else if (lineage.size() - 1 == lineageIndex) {
/*  504:1024 */           out.print("(this Map)");
/*  505:     */         } else {
/*  506:1026 */           out.print("(ancestor[" + (lineage.size() - 1 - lineageIndex - 1) + "] Map)");
/*  507:     */         }
/*  508:1032 */         if ((debug) && (childValue != null))
/*  509:     */         {
/*  510:1033 */           out.print(' ');
/*  511:1034 */           out.println(childValue.getClass().getName());
/*  512:     */         }
/*  513:     */         else
/*  514:     */         {
/*  515:1036 */           out.println();
/*  516:     */         }
/*  517:     */       }
/*  518:     */     }
/*  519:1041 */     lineage.pop();
/*  520:     */     
/*  521:1043 */     printIndent(out, lineage.size());
/*  522:1044 */     out.println(debug ? "} " + map.getClass().getName() : "}");
/*  523:     */   }
/*  524:     */   
/*  525:     */   private static void printIndent(PrintStream out, int indent)
/*  526:     */   {
/*  527:1053 */     for (int i = 0; i < indent; i++) {
/*  528:1054 */       out.print("    ");
/*  529:     */     }
/*  530:     */   }
/*  531:     */   
/*  532:     */   public static Map invertMap(Map map)
/*  533:     */   {
/*  534:1074 */     Map out = new HashMap(map.size());
/*  535:1075 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/*  536:     */     {
/*  537:1076 */       Map.Entry entry = (Map.Entry)it.next();
/*  538:1077 */       out.put(entry.getValue(), entry.getKey());
/*  539:     */     }
/*  540:1079 */     return out;
/*  541:     */   }
/*  542:     */   
/*  543:     */   public static void safeAddToMap(Map map, Object key, Object value)
/*  544:     */     throws NullPointerException
/*  545:     */   {
/*  546:1101 */     if (value == null) {
/*  547:1102 */       map.put(key, "");
/*  548:     */     } else {
/*  549:1104 */       map.put(key, value);
/*  550:     */     }
/*  551:     */   }
/*  552:     */   
/*  553:     */   public static Map putAll(Map map, Object[] array)
/*  554:     */   {
/*  555:1157 */     map.size();
/*  556:1158 */     if ((array == null) || (array.length == 0)) {
/*  557:1159 */       return map;
/*  558:     */     }
/*  559:1161 */     Object obj = array[0];
/*  560:     */     int i;
/*  561:1162 */     if ((obj instanceof Map.Entry)) {
/*  562:1163 */       for (int i = 0; i < array.length; i++)
/*  563:     */       {
/*  564:1164 */         Map.Entry entry = (Map.Entry)array[i];
/*  565:1165 */         map.put(entry.getKey(), entry.getValue());
/*  566:     */       }
/*  567:1167 */     } else if ((obj instanceof KeyValue)) {
/*  568:1168 */       for (int i = 0; i < array.length; i++)
/*  569:     */       {
/*  570:1169 */         KeyValue keyval = (KeyValue)array[i];
/*  571:1170 */         map.put(keyval.getKey(), keyval.getValue());
/*  572:     */       }
/*  573:1172 */     } else if ((obj instanceof Object[])) {
/*  574:1173 */       for (int i = 0; i < array.length; i++)
/*  575:     */       {
/*  576:1174 */         Object[] sub = (Object[])array[i];
/*  577:1175 */         if ((sub == null) || (sub.length < 2)) {
/*  578:1176 */           throw new IllegalArgumentException("Invalid array element: " + i);
/*  579:     */         }
/*  580:1178 */         map.put(sub[0], sub[1]);
/*  581:     */       }
/*  582:     */     } else {
/*  583:1181 */       for (i = 0; i < array.length - 1;) {
/*  584:1182 */         map.put(array[(i++)], array[(i++)]);
/*  585:     */       }
/*  586:     */     }
/*  587:1185 */     return map;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public static boolean isEmpty(Map map)
/*  591:     */   {
/*  592:1199 */     return (map == null) || (map.isEmpty());
/*  593:     */   }
/*  594:     */   
/*  595:     */   public static boolean isNotEmpty(Map map)
/*  596:     */   {
/*  597:1212 */     return !isEmpty(map);
/*  598:     */   }
/*  599:     */   
/*  600:     */   public static Map synchronizedMap(Map map)
/*  601:     */   {
/*  602:1241 */     return Collections.synchronizedMap(map);
/*  603:     */   }
/*  604:     */   
/*  605:     */   public static Map unmodifiableMap(Map map)
/*  606:     */   {
/*  607:1254 */     return UnmodifiableMap.decorate(map);
/*  608:     */   }
/*  609:     */   
/*  610:     */   public static Map predicatedMap(Map map, Predicate keyPred, Predicate valuePred)
/*  611:     */   {
/*  612:1273 */     return PredicatedMap.decorate(map, keyPred, valuePred);
/*  613:     */   }
/*  614:     */   
/*  615:     */   public static Map typedMap(Map map, Class keyType, Class valueType)
/*  616:     */   {
/*  617:1288 */     return TypedMap.decorate(map, keyType, valueType);
/*  618:     */   }
/*  619:     */   
/*  620:     */   public static Map transformedMap(Map map, Transformer keyTransformer, Transformer valueTransformer)
/*  621:     */   {
/*  622:1313 */     return TransformedMap.decorate(map, keyTransformer, valueTransformer);
/*  623:     */   }
/*  624:     */   
/*  625:     */   public static Map fixedSizeMap(Map map)
/*  626:     */   {
/*  627:1327 */     return FixedSizeMap.decorate(map);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public static Map lazyMap(Map map, Factory factory)
/*  631:     */   {
/*  632:1359 */     return LazyMap.decorate(map, factory);
/*  633:     */   }
/*  634:     */   
/*  635:     */   public static Map lazyMap(Map map, Transformer transformerFactory)
/*  636:     */   {
/*  637:1398 */     return LazyMap.decorate(map, transformerFactory);
/*  638:     */   }
/*  639:     */   
/*  640:     */   public static Map orderedMap(Map map)
/*  641:     */   {
/*  642:1413 */     return ListOrderedMap.decorate(map);
/*  643:     */   }
/*  644:     */   
/*  645:     */   public static Map multiValueMap(Map map)
/*  646:     */   {
/*  647:1426 */     return MultiValueMap.decorate(map);
/*  648:     */   }
/*  649:     */   
/*  650:     */   public static Map multiValueMap(Map map, Class collectionClass)
/*  651:     */   {
/*  652:1441 */     return MultiValueMap.decorate(map, collectionClass);
/*  653:     */   }
/*  654:     */   
/*  655:     */   public static Map multiValueMap(Map map, Factory collectionFactory)
/*  656:     */   {
/*  657:1456 */     return MultiValueMap.decorate(map, collectionFactory);
/*  658:     */   }
/*  659:     */   
/*  660:     */   public static Map synchronizedSortedMap(SortedMap map)
/*  661:     */   {
/*  662:1485 */     return Collections.synchronizedSortedMap(map);
/*  663:     */   }
/*  664:     */   
/*  665:     */   public static Map unmodifiableSortedMap(SortedMap map)
/*  666:     */   {
/*  667:1498 */     return UnmodifiableSortedMap.decorate(map);
/*  668:     */   }
/*  669:     */   
/*  670:     */   public static SortedMap predicatedSortedMap(SortedMap map, Predicate keyPred, Predicate valuePred)
/*  671:     */   {
/*  672:1517 */     return PredicatedSortedMap.decorate(map, keyPred, valuePred);
/*  673:     */   }
/*  674:     */   
/*  675:     */   public static SortedMap typedSortedMap(SortedMap map, Class keyType, Class valueType)
/*  676:     */   {
/*  677:1531 */     return TypedSortedMap.decorate(map, keyType, valueType);
/*  678:     */   }
/*  679:     */   
/*  680:     */   public static SortedMap transformedSortedMap(SortedMap map, Transformer keyTransformer, Transformer valueTransformer)
/*  681:     */   {
/*  682:1556 */     return TransformedSortedMap.decorate(map, keyTransformer, valueTransformer);
/*  683:     */   }
/*  684:     */   
/*  685:     */   public static SortedMap fixedSizeSortedMap(SortedMap map)
/*  686:     */   {
/*  687:1570 */     return FixedSizeSortedMap.decorate(map);
/*  688:     */   }
/*  689:     */   
/*  690:     */   public static SortedMap lazySortedMap(SortedMap map, Factory factory)
/*  691:     */   {
/*  692:1603 */     return LazySortedMap.decorate(map, factory);
/*  693:     */   }
/*  694:     */   
/*  695:     */   public static SortedMap lazySortedMap(SortedMap map, Transformer transformerFactory)
/*  696:     */   {
/*  697:1642 */     return LazySortedMap.decorate(map, transformerFactory);
/*  698:     */   }
/*  699:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.MapUtils
 * JD-Core Version:    0.7.0.1
 */