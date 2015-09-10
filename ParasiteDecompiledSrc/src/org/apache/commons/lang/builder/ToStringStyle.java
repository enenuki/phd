/*    1:     */ package org.apache.commons.lang.builder;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.lang.reflect.Array;
/*    5:     */ import java.util.Collection;
/*    6:     */ import java.util.Map;
/*    7:     */ import java.util.WeakHashMap;
/*    8:     */ import org.apache.commons.lang.ClassUtils;
/*    9:     */ import org.apache.commons.lang.ObjectUtils;
/*   10:     */ import org.apache.commons.lang.SystemUtils;
/*   11:     */ 
/*   12:     */ public abstract class ToStringStyle
/*   13:     */   implements Serializable
/*   14:     */ {
/*   15:  80 */   public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
/*   16:  94 */   public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
/*   17: 105 */   public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
/*   18: 117 */   public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
/*   19: 127 */   public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
/*   20: 135 */   private static final ThreadLocal REGISTRY = new ThreadLocal();
/*   21:     */   
/*   22:     */   static Map getRegistry()
/*   23:     */   {
/*   24: 146 */     return (Map)REGISTRY.get();
/*   25:     */   }
/*   26:     */   
/*   27:     */   static boolean isRegistered(Object value)
/*   28:     */   {
/*   29: 161 */     Map m = getRegistry();
/*   30: 162 */     return (m != null) && (m.containsKey(value));
/*   31:     */   }
/*   32:     */   
/*   33:     */   static void register(Object value)
/*   34:     */   {
/*   35: 175 */     if (value != null)
/*   36:     */     {
/*   37: 176 */       Map m = getRegistry();
/*   38: 177 */       if (m == null)
/*   39:     */       {
/*   40: 178 */         m = new WeakHashMap();
/*   41: 179 */         REGISTRY.set(m);
/*   42:     */       }
/*   43: 181 */       m.put(value, null);
/*   44:     */     }
/*   45:     */   }
/*   46:     */   
/*   47:     */   static void unregister(Object value)
/*   48:     */   {
/*   49: 198 */     if (value != null)
/*   50:     */     {
/*   51: 199 */       Map m = getRegistry();
/*   52: 200 */       if (m != null)
/*   53:     */       {
/*   54: 201 */         m.remove(value);
/*   55: 202 */         if (m.isEmpty()) {
/*   56: 203 */           REGISTRY.set(null);
/*   57:     */         }
/*   58:     */       }
/*   59:     */     }
/*   60:     */   }
/*   61:     */   
/*   62: 212 */   private boolean useFieldNames = true;
/*   63: 217 */   private boolean useClassName = true;
/*   64: 222 */   private boolean useShortClassName = false;
/*   65: 227 */   private boolean useIdentityHashCode = true;
/*   66: 232 */   private String contentStart = "[";
/*   67: 237 */   private String contentEnd = "]";
/*   68: 242 */   private String fieldNameValueSeparator = "=";
/*   69: 247 */   private boolean fieldSeparatorAtStart = false;
/*   70: 252 */   private boolean fieldSeparatorAtEnd = false;
/*   71: 257 */   private String fieldSeparator = ",";
/*   72: 262 */   private String arrayStart = "{";
/*   73: 267 */   private String arraySeparator = ",";
/*   74: 272 */   private boolean arrayContentDetail = true;
/*   75: 277 */   private String arrayEnd = "}";
/*   76: 283 */   private boolean defaultFullDetail = true;
/*   77: 288 */   private String nullText = "<null>";
/*   78: 293 */   private String sizeStartText = "<size=";
/*   79: 298 */   private String sizeEndText = ">";
/*   80: 303 */   private String summaryObjectStartText = "<";
/*   81: 308 */   private String summaryObjectEndText = ">";
/*   82:     */   
/*   83:     */   public void appendSuper(StringBuffer buffer, String superToString)
/*   84:     */   {
/*   85: 332 */     appendToString(buffer, superToString);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public void appendToString(StringBuffer buffer, String toString)
/*   89:     */   {
/*   90: 346 */     if (toString != null)
/*   91:     */     {
/*   92: 347 */       int pos1 = toString.indexOf(this.contentStart) + this.contentStart.length();
/*   93: 348 */       int pos2 = toString.lastIndexOf(this.contentEnd);
/*   94: 349 */       if ((pos1 != pos2) && (pos1 >= 0) && (pos2 >= 0))
/*   95:     */       {
/*   96: 350 */         String data = toString.substring(pos1, pos2);
/*   97: 351 */         if (this.fieldSeparatorAtStart) {
/*   98: 352 */           removeLastFieldSeparator(buffer);
/*   99:     */         }
/*  100: 354 */         buffer.append(data);
/*  101: 355 */         appendFieldSeparator(buffer);
/*  102:     */       }
/*  103:     */     }
/*  104:     */   }
/*  105:     */   
/*  106:     */   public void appendStart(StringBuffer buffer, Object object)
/*  107:     */   {
/*  108: 367 */     if (object != null)
/*  109:     */     {
/*  110: 368 */       appendClassName(buffer, object);
/*  111: 369 */       appendIdentityHashCode(buffer, object);
/*  112: 370 */       appendContentStart(buffer);
/*  113: 371 */       if (this.fieldSeparatorAtStart) {
/*  114: 372 */         appendFieldSeparator(buffer);
/*  115:     */       }
/*  116:     */     }
/*  117:     */   }
/*  118:     */   
/*  119:     */   public void appendEnd(StringBuffer buffer, Object object)
/*  120:     */   {
/*  121: 385 */     if (!this.fieldSeparatorAtEnd) {
/*  122: 386 */       removeLastFieldSeparator(buffer);
/*  123:     */     }
/*  124: 388 */     appendContentEnd(buffer);
/*  125: 389 */     unregister(object);
/*  126:     */   }
/*  127:     */   
/*  128:     */   protected void removeLastFieldSeparator(StringBuffer buffer)
/*  129:     */   {
/*  130: 399 */     int len = buffer.length();
/*  131: 400 */     int sepLen = this.fieldSeparator.length();
/*  132: 401 */     if ((len > 0) && (sepLen > 0) && (len >= sepLen))
/*  133:     */     {
/*  134: 402 */       boolean match = true;
/*  135: 403 */       for (int i = 0; i < sepLen; i++) {
/*  136: 404 */         if (buffer.charAt(len - 1 - i) != this.fieldSeparator.charAt(sepLen - 1 - i))
/*  137:     */         {
/*  138: 405 */           match = false;
/*  139: 406 */           break;
/*  140:     */         }
/*  141:     */       }
/*  142: 409 */       if (match) {
/*  143: 410 */         buffer.setLength(len - sepLen);
/*  144:     */       }
/*  145:     */     }
/*  146:     */   }
/*  147:     */   
/*  148:     */   public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail)
/*  149:     */   {
/*  150: 429 */     appendFieldStart(buffer, fieldName);
/*  151: 431 */     if (value == null) {
/*  152: 432 */       appendNullText(buffer, fieldName);
/*  153:     */     } else {
/*  154: 435 */       appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
/*  155:     */     }
/*  156: 438 */     appendFieldEnd(buffer, fieldName);
/*  157:     */   }
/*  158:     */   
/*  159:     */   protected void appendInternal(StringBuffer buffer, String fieldName, Object value, boolean detail)
/*  160:     */   {
/*  161: 461 */     if ((isRegistered(value)) && (!(value instanceof Number)) && (!(value instanceof Boolean)) && (!(value instanceof Character)))
/*  162:     */     {
/*  163: 463 */       appendCyclicObject(buffer, fieldName, value);
/*  164: 464 */       return;
/*  165:     */     }
/*  166: 467 */     register(value);
/*  167:     */     try
/*  168:     */     {
/*  169: 470 */       if ((value instanceof Collection))
/*  170:     */       {
/*  171: 471 */         if (detail) {
/*  172: 472 */           appendDetail(buffer, fieldName, (Collection)value);
/*  173:     */         } else {
/*  174: 474 */           appendSummarySize(buffer, fieldName, ((Collection)value).size());
/*  175:     */         }
/*  176:     */       }
/*  177: 477 */       else if ((value instanceof Map))
/*  178:     */       {
/*  179: 478 */         if (detail) {
/*  180: 479 */           appendDetail(buffer, fieldName, (Map)value);
/*  181:     */         } else {
/*  182: 481 */           appendSummarySize(buffer, fieldName, ((Map)value).size());
/*  183:     */         }
/*  184:     */       }
/*  185: 484 */       else if ((value instanceof long[]))
/*  186:     */       {
/*  187: 485 */         if (detail) {
/*  188: 486 */           appendDetail(buffer, fieldName, (long[])value);
/*  189:     */         } else {
/*  190: 488 */           appendSummary(buffer, fieldName, (long[])value);
/*  191:     */         }
/*  192:     */       }
/*  193: 491 */       else if ((value instanceof int[]))
/*  194:     */       {
/*  195: 492 */         if (detail) {
/*  196: 493 */           appendDetail(buffer, fieldName, (int[])value);
/*  197:     */         } else {
/*  198: 495 */           appendSummary(buffer, fieldName, (int[])value);
/*  199:     */         }
/*  200:     */       }
/*  201: 498 */       else if ((value instanceof short[]))
/*  202:     */       {
/*  203: 499 */         if (detail) {
/*  204: 500 */           appendDetail(buffer, fieldName, (short[])value);
/*  205:     */         } else {
/*  206: 502 */           appendSummary(buffer, fieldName, (short[])value);
/*  207:     */         }
/*  208:     */       }
/*  209: 505 */       else if ((value instanceof byte[]))
/*  210:     */       {
/*  211: 506 */         if (detail) {
/*  212: 507 */           appendDetail(buffer, fieldName, (byte[])value);
/*  213:     */         } else {
/*  214: 509 */           appendSummary(buffer, fieldName, (byte[])value);
/*  215:     */         }
/*  216:     */       }
/*  217: 512 */       else if ((value instanceof char[]))
/*  218:     */       {
/*  219: 513 */         if (detail) {
/*  220: 514 */           appendDetail(buffer, fieldName, (char[])value);
/*  221:     */         } else {
/*  222: 516 */           appendSummary(buffer, fieldName, (char[])value);
/*  223:     */         }
/*  224:     */       }
/*  225: 519 */       else if ((value instanceof double[]))
/*  226:     */       {
/*  227: 520 */         if (detail) {
/*  228: 521 */           appendDetail(buffer, fieldName, (double[])value);
/*  229:     */         } else {
/*  230: 523 */           appendSummary(buffer, fieldName, (double[])value);
/*  231:     */         }
/*  232:     */       }
/*  233: 526 */       else if ((value instanceof float[]))
/*  234:     */       {
/*  235: 527 */         if (detail) {
/*  236: 528 */           appendDetail(buffer, fieldName, (float[])value);
/*  237:     */         } else {
/*  238: 530 */           appendSummary(buffer, fieldName, (float[])value);
/*  239:     */         }
/*  240:     */       }
/*  241: 533 */       else if ((value instanceof boolean[]))
/*  242:     */       {
/*  243: 534 */         if (detail) {
/*  244: 535 */           appendDetail(buffer, fieldName, (boolean[])value);
/*  245:     */         } else {
/*  246: 537 */           appendSummary(buffer, fieldName, (boolean[])value);
/*  247:     */         }
/*  248:     */       }
/*  249: 540 */       else if (value.getClass().isArray())
/*  250:     */       {
/*  251: 541 */         if (detail) {
/*  252: 542 */           appendDetail(buffer, fieldName, (Object[])value);
/*  253:     */         } else {
/*  254: 544 */           appendSummary(buffer, fieldName, (Object[])value);
/*  255:     */         }
/*  256:     */       }
/*  257: 548 */       else if (detail) {
/*  258: 549 */         appendDetail(buffer, fieldName, value);
/*  259:     */       } else {
/*  260: 551 */         appendSummary(buffer, fieldName, value);
/*  261:     */       }
/*  262:     */     }
/*  263:     */     finally
/*  264:     */     {
/*  265: 555 */       unregister(value);
/*  266:     */     }
/*  267:     */   }
/*  268:     */   
/*  269:     */   protected void appendCyclicObject(StringBuffer buffer, String fieldName, Object value)
/*  270:     */   {
/*  271: 572 */     ObjectUtils.identityToString(buffer, value);
/*  272:     */   }
/*  273:     */   
/*  274:     */   protected void appendDetail(StringBuffer buffer, String fieldName, Object value)
/*  275:     */   {
/*  276: 585 */     buffer.append(value);
/*  277:     */   }
/*  278:     */   
/*  279:     */   protected void appendDetail(StringBuffer buffer, String fieldName, Collection coll)
/*  280:     */   {
/*  281: 597 */     buffer.append(coll);
/*  282:     */   }
/*  283:     */   
/*  284:     */   protected void appendDetail(StringBuffer buffer, String fieldName, Map map)
/*  285:     */   {
/*  286: 609 */     buffer.append(map);
/*  287:     */   }
/*  288:     */   
/*  289:     */   protected void appendSummary(StringBuffer buffer, String fieldName, Object value)
/*  290:     */   {
/*  291: 622 */     buffer.append(this.summaryObjectStartText);
/*  292: 623 */     buffer.append(getShortClassName(value.getClass()));
/*  293: 624 */     buffer.append(this.summaryObjectEndText);
/*  294:     */   }
/*  295:     */   
/*  296:     */   public void append(StringBuffer buffer, String fieldName, long value)
/*  297:     */   {
/*  298: 638 */     appendFieldStart(buffer, fieldName);
/*  299: 639 */     appendDetail(buffer, fieldName, value);
/*  300: 640 */     appendFieldEnd(buffer, fieldName);
/*  301:     */   }
/*  302:     */   
/*  303:     */   protected void appendDetail(StringBuffer buffer, String fieldName, long value)
/*  304:     */   {
/*  305: 652 */     buffer.append(value);
/*  306:     */   }
/*  307:     */   
/*  308:     */   public void append(StringBuffer buffer, String fieldName, int value)
/*  309:     */   {
/*  310: 666 */     appendFieldStart(buffer, fieldName);
/*  311: 667 */     appendDetail(buffer, fieldName, value);
/*  312: 668 */     appendFieldEnd(buffer, fieldName);
/*  313:     */   }
/*  314:     */   
/*  315:     */   protected void appendDetail(StringBuffer buffer, String fieldName, int value)
/*  316:     */   {
/*  317: 680 */     buffer.append(value);
/*  318:     */   }
/*  319:     */   
/*  320:     */   public void append(StringBuffer buffer, String fieldName, short value)
/*  321:     */   {
/*  322: 694 */     appendFieldStart(buffer, fieldName);
/*  323: 695 */     appendDetail(buffer, fieldName, value);
/*  324: 696 */     appendFieldEnd(buffer, fieldName);
/*  325:     */   }
/*  326:     */   
/*  327:     */   protected void appendDetail(StringBuffer buffer, String fieldName, short value)
/*  328:     */   {
/*  329: 708 */     buffer.append(value);
/*  330:     */   }
/*  331:     */   
/*  332:     */   public void append(StringBuffer buffer, String fieldName, byte value)
/*  333:     */   {
/*  334: 722 */     appendFieldStart(buffer, fieldName);
/*  335: 723 */     appendDetail(buffer, fieldName, value);
/*  336: 724 */     appendFieldEnd(buffer, fieldName);
/*  337:     */   }
/*  338:     */   
/*  339:     */   protected void appendDetail(StringBuffer buffer, String fieldName, byte value)
/*  340:     */   {
/*  341: 736 */     buffer.append(value);
/*  342:     */   }
/*  343:     */   
/*  344:     */   public void append(StringBuffer buffer, String fieldName, char value)
/*  345:     */   {
/*  346: 750 */     appendFieldStart(buffer, fieldName);
/*  347: 751 */     appendDetail(buffer, fieldName, value);
/*  348: 752 */     appendFieldEnd(buffer, fieldName);
/*  349:     */   }
/*  350:     */   
/*  351:     */   protected void appendDetail(StringBuffer buffer, String fieldName, char value)
/*  352:     */   {
/*  353: 764 */     buffer.append(value);
/*  354:     */   }
/*  355:     */   
/*  356:     */   public void append(StringBuffer buffer, String fieldName, double value)
/*  357:     */   {
/*  358: 778 */     appendFieldStart(buffer, fieldName);
/*  359: 779 */     appendDetail(buffer, fieldName, value);
/*  360: 780 */     appendFieldEnd(buffer, fieldName);
/*  361:     */   }
/*  362:     */   
/*  363:     */   protected void appendDetail(StringBuffer buffer, String fieldName, double value)
/*  364:     */   {
/*  365: 792 */     buffer.append(value);
/*  366:     */   }
/*  367:     */   
/*  368:     */   public void append(StringBuffer buffer, String fieldName, float value)
/*  369:     */   {
/*  370: 806 */     appendFieldStart(buffer, fieldName);
/*  371: 807 */     appendDetail(buffer, fieldName, value);
/*  372: 808 */     appendFieldEnd(buffer, fieldName);
/*  373:     */   }
/*  374:     */   
/*  375:     */   protected void appendDetail(StringBuffer buffer, String fieldName, float value)
/*  376:     */   {
/*  377: 820 */     buffer.append(value);
/*  378:     */   }
/*  379:     */   
/*  380:     */   public void append(StringBuffer buffer, String fieldName, boolean value)
/*  381:     */   {
/*  382: 834 */     appendFieldStart(buffer, fieldName);
/*  383: 835 */     appendDetail(buffer, fieldName, value);
/*  384: 836 */     appendFieldEnd(buffer, fieldName);
/*  385:     */   }
/*  386:     */   
/*  387:     */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean value)
/*  388:     */   {
/*  389: 848 */     buffer.append(value);
/*  390:     */   }
/*  391:     */   
/*  392:     */   public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail)
/*  393:     */   {
/*  394: 862 */     appendFieldStart(buffer, fieldName);
/*  395: 864 */     if (array == null) {
/*  396: 865 */       appendNullText(buffer, fieldName);
/*  397: 867 */     } else if (isFullDetail(fullDetail)) {
/*  398: 868 */       appendDetail(buffer, fieldName, array);
/*  399:     */     } else {
/*  400: 871 */       appendSummary(buffer, fieldName, array);
/*  401:     */     }
/*  402: 874 */     appendFieldEnd(buffer, fieldName);
/*  403:     */   }
/*  404:     */   
/*  405:     */   protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array)
/*  406:     */   {
/*  407: 889 */     buffer.append(this.arrayStart);
/*  408: 890 */     for (int i = 0; i < array.length; i++)
/*  409:     */     {
/*  410: 891 */       Object item = array[i];
/*  411: 892 */       if (i > 0) {
/*  412: 893 */         buffer.append(this.arraySeparator);
/*  413:     */       }
/*  414: 895 */       if (item == null) {
/*  415: 896 */         appendNullText(buffer, fieldName);
/*  416:     */       } else {
/*  417: 899 */         appendInternal(buffer, fieldName, item, this.arrayContentDetail);
/*  418:     */       }
/*  419:     */     }
/*  420: 902 */     buffer.append(this.arrayEnd);
/*  421:     */   }
/*  422:     */   
/*  423:     */   protected void reflectionAppendArrayDetail(StringBuffer buffer, String fieldName, Object array)
/*  424:     */   {
/*  425: 915 */     buffer.append(this.arrayStart);
/*  426: 916 */     int length = Array.getLength(array);
/*  427: 917 */     for (int i = 0; i < length; i++)
/*  428:     */     {
/*  429: 918 */       Object item = Array.get(array, i);
/*  430: 919 */       if (i > 0) {
/*  431: 920 */         buffer.append(this.arraySeparator);
/*  432:     */       }
/*  433: 922 */       if (item == null) {
/*  434: 923 */         appendNullText(buffer, fieldName);
/*  435:     */       } else {
/*  436: 926 */         appendInternal(buffer, fieldName, item, this.arrayContentDetail);
/*  437:     */       }
/*  438:     */     }
/*  439: 929 */     buffer.append(this.arrayEnd);
/*  440:     */   }
/*  441:     */   
/*  442:     */   protected void appendSummary(StringBuffer buffer, String fieldName, Object[] array)
/*  443:     */   {
/*  444: 942 */     appendSummarySize(buffer, fieldName, array.length);
/*  445:     */   }
/*  446:     */   
/*  447:     */   public void append(StringBuffer buffer, String fieldName, long[] array, Boolean fullDetail)
/*  448:     */   {
/*  449: 958 */     appendFieldStart(buffer, fieldName);
/*  450: 960 */     if (array == null) {
/*  451: 961 */       appendNullText(buffer, fieldName);
/*  452: 963 */     } else if (isFullDetail(fullDetail)) {
/*  453: 964 */       appendDetail(buffer, fieldName, array);
/*  454:     */     } else {
/*  455: 967 */       appendSummary(buffer, fieldName, array);
/*  456:     */     }
/*  457: 970 */     appendFieldEnd(buffer, fieldName);
/*  458:     */   }
/*  459:     */   
/*  460:     */   protected void appendDetail(StringBuffer buffer, String fieldName, long[] array)
/*  461:     */   {
/*  462: 983 */     buffer.append(this.arrayStart);
/*  463: 984 */     for (int i = 0; i < array.length; i++)
/*  464:     */     {
/*  465: 985 */       if (i > 0) {
/*  466: 986 */         buffer.append(this.arraySeparator);
/*  467:     */       }
/*  468: 988 */       appendDetail(buffer, fieldName, array[i]);
/*  469:     */     }
/*  470: 990 */     buffer.append(this.arrayEnd);
/*  471:     */   }
/*  472:     */   
/*  473:     */   protected void appendSummary(StringBuffer buffer, String fieldName, long[] array)
/*  474:     */   {
/*  475:1003 */     appendSummarySize(buffer, fieldName, array.length);
/*  476:     */   }
/*  477:     */   
/*  478:     */   public void append(StringBuffer buffer, String fieldName, int[] array, Boolean fullDetail)
/*  479:     */   {
/*  480:1019 */     appendFieldStart(buffer, fieldName);
/*  481:1021 */     if (array == null) {
/*  482:1022 */       appendNullText(buffer, fieldName);
/*  483:1024 */     } else if (isFullDetail(fullDetail)) {
/*  484:1025 */       appendDetail(buffer, fieldName, array);
/*  485:     */     } else {
/*  486:1028 */       appendSummary(buffer, fieldName, array);
/*  487:     */     }
/*  488:1031 */     appendFieldEnd(buffer, fieldName);
/*  489:     */   }
/*  490:     */   
/*  491:     */   protected void appendDetail(StringBuffer buffer, String fieldName, int[] array)
/*  492:     */   {
/*  493:1044 */     buffer.append(this.arrayStart);
/*  494:1045 */     for (int i = 0; i < array.length; i++)
/*  495:     */     {
/*  496:1046 */       if (i > 0) {
/*  497:1047 */         buffer.append(this.arraySeparator);
/*  498:     */       }
/*  499:1049 */       appendDetail(buffer, fieldName, array[i]);
/*  500:     */     }
/*  501:1051 */     buffer.append(this.arrayEnd);
/*  502:     */   }
/*  503:     */   
/*  504:     */   protected void appendSummary(StringBuffer buffer, String fieldName, int[] array)
/*  505:     */   {
/*  506:1064 */     appendSummarySize(buffer, fieldName, array.length);
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void append(StringBuffer buffer, String fieldName, short[] array, Boolean fullDetail)
/*  510:     */   {
/*  511:1080 */     appendFieldStart(buffer, fieldName);
/*  512:1082 */     if (array == null) {
/*  513:1083 */       appendNullText(buffer, fieldName);
/*  514:1085 */     } else if (isFullDetail(fullDetail)) {
/*  515:1086 */       appendDetail(buffer, fieldName, array);
/*  516:     */     } else {
/*  517:1089 */       appendSummary(buffer, fieldName, array);
/*  518:     */     }
/*  519:1092 */     appendFieldEnd(buffer, fieldName);
/*  520:     */   }
/*  521:     */   
/*  522:     */   protected void appendDetail(StringBuffer buffer, String fieldName, short[] array)
/*  523:     */   {
/*  524:1105 */     buffer.append(this.arrayStart);
/*  525:1106 */     for (int i = 0; i < array.length; i++)
/*  526:     */     {
/*  527:1107 */       if (i > 0) {
/*  528:1108 */         buffer.append(this.arraySeparator);
/*  529:     */       }
/*  530:1110 */       appendDetail(buffer, fieldName, array[i]);
/*  531:     */     }
/*  532:1112 */     buffer.append(this.arrayEnd);
/*  533:     */   }
/*  534:     */   
/*  535:     */   protected void appendSummary(StringBuffer buffer, String fieldName, short[] array)
/*  536:     */   {
/*  537:1125 */     appendSummarySize(buffer, fieldName, array.length);
/*  538:     */   }
/*  539:     */   
/*  540:     */   public void append(StringBuffer buffer, String fieldName, byte[] array, Boolean fullDetail)
/*  541:     */   {
/*  542:1141 */     appendFieldStart(buffer, fieldName);
/*  543:1143 */     if (array == null) {
/*  544:1144 */       appendNullText(buffer, fieldName);
/*  545:1146 */     } else if (isFullDetail(fullDetail)) {
/*  546:1147 */       appendDetail(buffer, fieldName, array);
/*  547:     */     } else {
/*  548:1150 */       appendSummary(buffer, fieldName, array);
/*  549:     */     }
/*  550:1153 */     appendFieldEnd(buffer, fieldName);
/*  551:     */   }
/*  552:     */   
/*  553:     */   protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array)
/*  554:     */   {
/*  555:1166 */     buffer.append(this.arrayStart);
/*  556:1167 */     for (int i = 0; i < array.length; i++)
/*  557:     */     {
/*  558:1168 */       if (i > 0) {
/*  559:1169 */         buffer.append(this.arraySeparator);
/*  560:     */       }
/*  561:1171 */       appendDetail(buffer, fieldName, array[i]);
/*  562:     */     }
/*  563:1173 */     buffer.append(this.arrayEnd);
/*  564:     */   }
/*  565:     */   
/*  566:     */   protected void appendSummary(StringBuffer buffer, String fieldName, byte[] array)
/*  567:     */   {
/*  568:1186 */     appendSummarySize(buffer, fieldName, array.length);
/*  569:     */   }
/*  570:     */   
/*  571:     */   public void append(StringBuffer buffer, String fieldName, char[] array, Boolean fullDetail)
/*  572:     */   {
/*  573:1202 */     appendFieldStart(buffer, fieldName);
/*  574:1204 */     if (array == null) {
/*  575:1205 */       appendNullText(buffer, fieldName);
/*  576:1207 */     } else if (isFullDetail(fullDetail)) {
/*  577:1208 */       appendDetail(buffer, fieldName, array);
/*  578:     */     } else {
/*  579:1211 */       appendSummary(buffer, fieldName, array);
/*  580:     */     }
/*  581:1214 */     appendFieldEnd(buffer, fieldName);
/*  582:     */   }
/*  583:     */   
/*  584:     */   protected void appendDetail(StringBuffer buffer, String fieldName, char[] array)
/*  585:     */   {
/*  586:1227 */     buffer.append(this.arrayStart);
/*  587:1228 */     for (int i = 0; i < array.length; i++)
/*  588:     */     {
/*  589:1229 */       if (i > 0) {
/*  590:1230 */         buffer.append(this.arraySeparator);
/*  591:     */       }
/*  592:1232 */       appendDetail(buffer, fieldName, array[i]);
/*  593:     */     }
/*  594:1234 */     buffer.append(this.arrayEnd);
/*  595:     */   }
/*  596:     */   
/*  597:     */   protected void appendSummary(StringBuffer buffer, String fieldName, char[] array)
/*  598:     */   {
/*  599:1247 */     appendSummarySize(buffer, fieldName, array.length);
/*  600:     */   }
/*  601:     */   
/*  602:     */   public void append(StringBuffer buffer, String fieldName, double[] array, Boolean fullDetail)
/*  603:     */   {
/*  604:1263 */     appendFieldStart(buffer, fieldName);
/*  605:1265 */     if (array == null) {
/*  606:1266 */       appendNullText(buffer, fieldName);
/*  607:1268 */     } else if (isFullDetail(fullDetail)) {
/*  608:1269 */       appendDetail(buffer, fieldName, array);
/*  609:     */     } else {
/*  610:1272 */       appendSummary(buffer, fieldName, array);
/*  611:     */     }
/*  612:1275 */     appendFieldEnd(buffer, fieldName);
/*  613:     */   }
/*  614:     */   
/*  615:     */   protected void appendDetail(StringBuffer buffer, String fieldName, double[] array)
/*  616:     */   {
/*  617:1288 */     buffer.append(this.arrayStart);
/*  618:1289 */     for (int i = 0; i < array.length; i++)
/*  619:     */     {
/*  620:1290 */       if (i > 0) {
/*  621:1291 */         buffer.append(this.arraySeparator);
/*  622:     */       }
/*  623:1293 */       appendDetail(buffer, fieldName, array[i]);
/*  624:     */     }
/*  625:1295 */     buffer.append(this.arrayEnd);
/*  626:     */   }
/*  627:     */   
/*  628:     */   protected void appendSummary(StringBuffer buffer, String fieldName, double[] array)
/*  629:     */   {
/*  630:1308 */     appendSummarySize(buffer, fieldName, array.length);
/*  631:     */   }
/*  632:     */   
/*  633:     */   public void append(StringBuffer buffer, String fieldName, float[] array, Boolean fullDetail)
/*  634:     */   {
/*  635:1324 */     appendFieldStart(buffer, fieldName);
/*  636:1326 */     if (array == null) {
/*  637:1327 */       appendNullText(buffer, fieldName);
/*  638:1329 */     } else if (isFullDetail(fullDetail)) {
/*  639:1330 */       appendDetail(buffer, fieldName, array);
/*  640:     */     } else {
/*  641:1333 */       appendSummary(buffer, fieldName, array);
/*  642:     */     }
/*  643:1336 */     appendFieldEnd(buffer, fieldName);
/*  644:     */   }
/*  645:     */   
/*  646:     */   protected void appendDetail(StringBuffer buffer, String fieldName, float[] array)
/*  647:     */   {
/*  648:1349 */     buffer.append(this.arrayStart);
/*  649:1350 */     for (int i = 0; i < array.length; i++)
/*  650:     */     {
/*  651:1351 */       if (i > 0) {
/*  652:1352 */         buffer.append(this.arraySeparator);
/*  653:     */       }
/*  654:1354 */       appendDetail(buffer, fieldName, array[i]);
/*  655:     */     }
/*  656:1356 */     buffer.append(this.arrayEnd);
/*  657:     */   }
/*  658:     */   
/*  659:     */   protected void appendSummary(StringBuffer buffer, String fieldName, float[] array)
/*  660:     */   {
/*  661:1369 */     appendSummarySize(buffer, fieldName, array.length);
/*  662:     */   }
/*  663:     */   
/*  664:     */   public void append(StringBuffer buffer, String fieldName, boolean[] array, Boolean fullDetail)
/*  665:     */   {
/*  666:1385 */     appendFieldStart(buffer, fieldName);
/*  667:1387 */     if (array == null) {
/*  668:1388 */       appendNullText(buffer, fieldName);
/*  669:1390 */     } else if (isFullDetail(fullDetail)) {
/*  670:1391 */       appendDetail(buffer, fieldName, array);
/*  671:     */     } else {
/*  672:1394 */       appendSummary(buffer, fieldName, array);
/*  673:     */     }
/*  674:1397 */     appendFieldEnd(buffer, fieldName);
/*  675:     */   }
/*  676:     */   
/*  677:     */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean[] array)
/*  678:     */   {
/*  679:1410 */     buffer.append(this.arrayStart);
/*  680:1411 */     for (int i = 0; i < array.length; i++)
/*  681:     */     {
/*  682:1412 */       if (i > 0) {
/*  683:1413 */         buffer.append(this.arraySeparator);
/*  684:     */       }
/*  685:1415 */       appendDetail(buffer, fieldName, array[i]);
/*  686:     */     }
/*  687:1417 */     buffer.append(this.arrayEnd);
/*  688:     */   }
/*  689:     */   
/*  690:     */   protected void appendSummary(StringBuffer buffer, String fieldName, boolean[] array)
/*  691:     */   {
/*  692:1430 */     appendSummarySize(buffer, fieldName, array.length);
/*  693:     */   }
/*  694:     */   
/*  695:     */   protected void appendClassName(StringBuffer buffer, Object object)
/*  696:     */   {
/*  697:1442 */     if ((this.useClassName) && (object != null))
/*  698:     */     {
/*  699:1443 */       register(object);
/*  700:1444 */       if (this.useShortClassName) {
/*  701:1445 */         buffer.append(getShortClassName(object.getClass()));
/*  702:     */       } else {
/*  703:1447 */         buffer.append(object.getClass().getName());
/*  704:     */       }
/*  705:     */     }
/*  706:     */   }
/*  707:     */   
/*  708:     */   protected void appendIdentityHashCode(StringBuffer buffer, Object object)
/*  709:     */   {
/*  710:1459 */     if ((isUseIdentityHashCode()) && (object != null))
/*  711:     */     {
/*  712:1460 */       register(object);
/*  713:1461 */       buffer.append('@');
/*  714:1462 */       buffer.append(Integer.toHexString(System.identityHashCode(object)));
/*  715:     */     }
/*  716:     */   }
/*  717:     */   
/*  718:     */   protected void appendContentStart(StringBuffer buffer)
/*  719:     */   {
/*  720:1472 */     buffer.append(this.contentStart);
/*  721:     */   }
/*  722:     */   
/*  723:     */   protected void appendContentEnd(StringBuffer buffer)
/*  724:     */   {
/*  725:1481 */     buffer.append(this.contentEnd);
/*  726:     */   }
/*  727:     */   
/*  728:     */   protected void appendNullText(StringBuffer buffer, String fieldName)
/*  729:     */   {
/*  730:1493 */     buffer.append(this.nullText);
/*  731:     */   }
/*  732:     */   
/*  733:     */   protected void appendFieldSeparator(StringBuffer buffer)
/*  734:     */   {
/*  735:1502 */     buffer.append(this.fieldSeparator);
/*  736:     */   }
/*  737:     */   
/*  738:     */   protected void appendFieldStart(StringBuffer buffer, String fieldName)
/*  739:     */   {
/*  740:1512 */     if ((this.useFieldNames) && (fieldName != null))
/*  741:     */     {
/*  742:1513 */       buffer.append(fieldName);
/*  743:1514 */       buffer.append(this.fieldNameValueSeparator);
/*  744:     */     }
/*  745:     */   }
/*  746:     */   
/*  747:     */   protected void appendFieldEnd(StringBuffer buffer, String fieldName)
/*  748:     */   {
/*  749:1525 */     appendFieldSeparator(buffer);
/*  750:     */   }
/*  751:     */   
/*  752:     */   protected void appendSummarySize(StringBuffer buffer, String fieldName, int size)
/*  753:     */   {
/*  754:1544 */     buffer.append(this.sizeStartText);
/*  755:1545 */     buffer.append(size);
/*  756:1546 */     buffer.append(this.sizeEndText);
/*  757:     */   }
/*  758:     */   
/*  759:     */   protected boolean isFullDetail(Boolean fullDetailRequest)
/*  760:     */   {
/*  761:1564 */     if (fullDetailRequest == null) {
/*  762:1565 */       return this.defaultFullDetail;
/*  763:     */     }
/*  764:1567 */     return fullDetailRequest.booleanValue();
/*  765:     */   }
/*  766:     */   
/*  767:     */   protected String getShortClassName(Class cls)
/*  768:     */   {
/*  769:1580 */     return ClassUtils.getShortClassName(cls);
/*  770:     */   }
/*  771:     */   
/*  772:     */   protected boolean isUseClassName()
/*  773:     */   {
/*  774:1594 */     return this.useClassName;
/*  775:     */   }
/*  776:     */   
/*  777:     */   protected void setUseClassName(boolean useClassName)
/*  778:     */   {
/*  779:1603 */     this.useClassName = useClassName;
/*  780:     */   }
/*  781:     */   
/*  782:     */   protected boolean isUseShortClassName()
/*  783:     */   {
/*  784:1615 */     return this.useShortClassName;
/*  785:     */   }
/*  786:     */   
/*  787:     */   /**
/*  788:     */    * @deprecated
/*  789:     */    */
/*  790:     */   protected boolean isShortClassName()
/*  791:     */   {
/*  792:1626 */     return this.useShortClassName;
/*  793:     */   }
/*  794:     */   
/*  795:     */   protected void setUseShortClassName(boolean useShortClassName)
/*  796:     */   {
/*  797:1636 */     this.useShortClassName = useShortClassName;
/*  798:     */   }
/*  799:     */   
/*  800:     */   /**
/*  801:     */    * @deprecated
/*  802:     */    */
/*  803:     */   protected void setShortClassName(boolean shortClassName)
/*  804:     */   {
/*  805:1647 */     this.useShortClassName = shortClassName;
/*  806:     */   }
/*  807:     */   
/*  808:     */   protected boolean isUseIdentityHashCode()
/*  809:     */   {
/*  810:1658 */     return this.useIdentityHashCode;
/*  811:     */   }
/*  812:     */   
/*  813:     */   protected void setUseIdentityHashCode(boolean useIdentityHashCode)
/*  814:     */   {
/*  815:1667 */     this.useIdentityHashCode = useIdentityHashCode;
/*  816:     */   }
/*  817:     */   
/*  818:     */   protected boolean isUseFieldNames()
/*  819:     */   {
/*  820:1678 */     return this.useFieldNames;
/*  821:     */   }
/*  822:     */   
/*  823:     */   protected void setUseFieldNames(boolean useFieldNames)
/*  824:     */   {
/*  825:1687 */     this.useFieldNames = useFieldNames;
/*  826:     */   }
/*  827:     */   
/*  828:     */   protected boolean isDefaultFullDetail()
/*  829:     */   {
/*  830:1699 */     return this.defaultFullDetail;
/*  831:     */   }
/*  832:     */   
/*  833:     */   protected void setDefaultFullDetail(boolean defaultFullDetail)
/*  834:     */   {
/*  835:1709 */     this.defaultFullDetail = defaultFullDetail;
/*  836:     */   }
/*  837:     */   
/*  838:     */   protected boolean isArrayContentDetail()
/*  839:     */   {
/*  840:1720 */     return this.arrayContentDetail;
/*  841:     */   }
/*  842:     */   
/*  843:     */   protected void setArrayContentDetail(boolean arrayContentDetail)
/*  844:     */   {
/*  845:1729 */     this.arrayContentDetail = arrayContentDetail;
/*  846:     */   }
/*  847:     */   
/*  848:     */   protected String getArrayStart()
/*  849:     */   {
/*  850:1740 */     return this.arrayStart;
/*  851:     */   }
/*  852:     */   
/*  853:     */   protected void setArrayStart(String arrayStart)
/*  854:     */   {
/*  855:1752 */     if (arrayStart == null) {
/*  856:1753 */       arrayStart = "";
/*  857:     */     }
/*  858:1755 */     this.arrayStart = arrayStart;
/*  859:     */   }
/*  860:     */   
/*  861:     */   protected String getArrayEnd()
/*  862:     */   {
/*  863:1766 */     return this.arrayEnd;
/*  864:     */   }
/*  865:     */   
/*  866:     */   protected void setArrayEnd(String arrayEnd)
/*  867:     */   {
/*  868:1778 */     if (arrayEnd == null) {
/*  869:1779 */       arrayEnd = "";
/*  870:     */     }
/*  871:1781 */     this.arrayEnd = arrayEnd;
/*  872:     */   }
/*  873:     */   
/*  874:     */   protected String getArraySeparator()
/*  875:     */   {
/*  876:1792 */     return this.arraySeparator;
/*  877:     */   }
/*  878:     */   
/*  879:     */   protected void setArraySeparator(String arraySeparator)
/*  880:     */   {
/*  881:1804 */     if (arraySeparator == null) {
/*  882:1805 */       arraySeparator = "";
/*  883:     */     }
/*  884:1807 */     this.arraySeparator = arraySeparator;
/*  885:     */   }
/*  886:     */   
/*  887:     */   protected String getContentStart()
/*  888:     */   {
/*  889:1818 */     return this.contentStart;
/*  890:     */   }
/*  891:     */   
/*  892:     */   protected void setContentStart(String contentStart)
/*  893:     */   {
/*  894:1830 */     if (contentStart == null) {
/*  895:1831 */       contentStart = "";
/*  896:     */     }
/*  897:1833 */     this.contentStart = contentStart;
/*  898:     */   }
/*  899:     */   
/*  900:     */   protected String getContentEnd()
/*  901:     */   {
/*  902:1844 */     return this.contentEnd;
/*  903:     */   }
/*  904:     */   
/*  905:     */   protected void setContentEnd(String contentEnd)
/*  906:     */   {
/*  907:1856 */     if (contentEnd == null) {
/*  908:1857 */       contentEnd = "";
/*  909:     */     }
/*  910:1859 */     this.contentEnd = contentEnd;
/*  911:     */   }
/*  912:     */   
/*  913:     */   protected String getFieldNameValueSeparator()
/*  914:     */   {
/*  915:1870 */     return this.fieldNameValueSeparator;
/*  916:     */   }
/*  917:     */   
/*  918:     */   protected void setFieldNameValueSeparator(String fieldNameValueSeparator)
/*  919:     */   {
/*  920:1882 */     if (fieldNameValueSeparator == null) {
/*  921:1883 */       fieldNameValueSeparator = "";
/*  922:     */     }
/*  923:1885 */     this.fieldNameValueSeparator = fieldNameValueSeparator;
/*  924:     */   }
/*  925:     */   
/*  926:     */   protected String getFieldSeparator()
/*  927:     */   {
/*  928:1896 */     return this.fieldSeparator;
/*  929:     */   }
/*  930:     */   
/*  931:     */   protected void setFieldSeparator(String fieldSeparator)
/*  932:     */   {
/*  933:1908 */     if (fieldSeparator == null) {
/*  934:1909 */       fieldSeparator = "";
/*  935:     */     }
/*  936:1911 */     this.fieldSeparator = fieldSeparator;
/*  937:     */   }
/*  938:     */   
/*  939:     */   protected boolean isFieldSeparatorAtStart()
/*  940:     */   {
/*  941:1924 */     return this.fieldSeparatorAtStart;
/*  942:     */   }
/*  943:     */   
/*  944:     */   protected void setFieldSeparatorAtStart(boolean fieldSeparatorAtStart)
/*  945:     */   {
/*  946:1935 */     this.fieldSeparatorAtStart = fieldSeparatorAtStart;
/*  947:     */   }
/*  948:     */   
/*  949:     */   protected boolean isFieldSeparatorAtEnd()
/*  950:     */   {
/*  951:1948 */     return this.fieldSeparatorAtEnd;
/*  952:     */   }
/*  953:     */   
/*  954:     */   protected void setFieldSeparatorAtEnd(boolean fieldSeparatorAtEnd)
/*  955:     */   {
/*  956:1959 */     this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
/*  957:     */   }
/*  958:     */   
/*  959:     */   protected String getNullText()
/*  960:     */   {
/*  961:1970 */     return this.nullText;
/*  962:     */   }
/*  963:     */   
/*  964:     */   protected void setNullText(String nullText)
/*  965:     */   {
/*  966:1982 */     if (nullText == null) {
/*  967:1983 */       nullText = "";
/*  968:     */     }
/*  969:1985 */     this.nullText = nullText;
/*  970:     */   }
/*  971:     */   
/*  972:     */   protected String getSizeStartText()
/*  973:     */   {
/*  974:1999 */     return this.sizeStartText;
/*  975:     */   }
/*  976:     */   
/*  977:     */   protected void setSizeStartText(String sizeStartText)
/*  978:     */   {
/*  979:2014 */     if (sizeStartText == null) {
/*  980:2015 */       sizeStartText = "";
/*  981:     */     }
/*  982:2017 */     this.sizeStartText = sizeStartText;
/*  983:     */   }
/*  984:     */   
/*  985:     */   protected String getSizeEndText()
/*  986:     */   {
/*  987:2031 */     return this.sizeEndText;
/*  988:     */   }
/*  989:     */   
/*  990:     */   protected void setSizeEndText(String sizeEndText)
/*  991:     */   {
/*  992:2046 */     if (sizeEndText == null) {
/*  993:2047 */       sizeEndText = "";
/*  994:     */     }
/*  995:2049 */     this.sizeEndText = sizeEndText;
/*  996:     */   }
/*  997:     */   
/*  998:     */   protected String getSummaryObjectStartText()
/*  999:     */   {
/* 1000:2063 */     return this.summaryObjectStartText;
/* 1001:     */   }
/* 1002:     */   
/* 1003:     */   protected void setSummaryObjectStartText(String summaryObjectStartText)
/* 1004:     */   {
/* 1005:2078 */     if (summaryObjectStartText == null) {
/* 1006:2079 */       summaryObjectStartText = "";
/* 1007:     */     }
/* 1008:2081 */     this.summaryObjectStartText = summaryObjectStartText;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   protected String getSummaryObjectEndText()
/* 1012:     */   {
/* 1013:2095 */     return this.summaryObjectEndText;
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   protected void setSummaryObjectEndText(String summaryObjectEndText)
/* 1017:     */   {
/* 1018:2110 */     if (summaryObjectEndText == null) {
/* 1019:2111 */       summaryObjectEndText = "";
/* 1020:     */     }
/* 1021:2113 */     this.summaryObjectEndText = summaryObjectEndText;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   private static final class DefaultToStringStyle
/* 1025:     */     extends ToStringStyle
/* 1026:     */   {
/* 1027:     */     private static final long serialVersionUID = 1L;
/* 1028:     */     
/* 1029:     */     private Object readResolve()
/* 1030:     */     {
/* 1031:2148 */       return ToStringStyle.DEFAULT_STYLE;
/* 1032:     */     }
/* 1033:     */   }
/* 1034:     */   
/* 1035:     */   private static final class NoFieldNameToStringStyle
/* 1036:     */     extends ToStringStyle
/* 1037:     */   {
/* 1038:     */     private static final long serialVersionUID = 1L;
/* 1039:     */     
/* 1040:     */     NoFieldNameToStringStyle()
/* 1041:     */     {
/* 1042:2173 */       setUseFieldNames(false);
/* 1043:     */     }
/* 1044:     */     
/* 1045:     */     private Object readResolve()
/* 1046:     */     {
/* 1047:2182 */       return ToStringStyle.NO_FIELD_NAMES_STYLE;
/* 1048:     */     }
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   private static final class ShortPrefixToStringStyle
/* 1052:     */     extends ToStringStyle
/* 1053:     */   {
/* 1054:     */     private static final long serialVersionUID = 1L;
/* 1055:     */     
/* 1056:     */     ShortPrefixToStringStyle()
/* 1057:     */     {
/* 1058:2207 */       setUseShortClassName(true);
/* 1059:2208 */       setUseIdentityHashCode(false);
/* 1060:     */     }
/* 1061:     */     
/* 1062:     */     private Object readResolve()
/* 1063:     */     {
/* 1064:2216 */       return ToStringStyle.SHORT_PREFIX_STYLE;
/* 1065:     */     }
/* 1066:     */   }
/* 1067:     */   
/* 1068:     */   private static final class SimpleToStringStyle
/* 1069:     */     extends ToStringStyle
/* 1070:     */   {
/* 1071:     */     private static final long serialVersionUID = 1L;
/* 1072:     */     
/* 1073:     */     SimpleToStringStyle()
/* 1074:     */     {
/* 1075:2239 */       setUseClassName(false);
/* 1076:2240 */       setUseIdentityHashCode(false);
/* 1077:2241 */       setUseFieldNames(false);
/* 1078:2242 */       setContentStart("");
/* 1079:2243 */       setContentEnd("");
/* 1080:     */     }
/* 1081:     */     
/* 1082:     */     private Object readResolve()
/* 1083:     */     {
/* 1084:2251 */       return ToStringStyle.SIMPLE_STYLE;
/* 1085:     */     }
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   private static final class MultiLineToStringStyle
/* 1089:     */     extends ToStringStyle
/* 1090:     */   {
/* 1091:     */     private static final long serialVersionUID = 1L;
/* 1092:     */     
/* 1093:     */     MultiLineToStringStyle()
/* 1094:     */     {
/* 1095:2275 */       setContentStart("[");
/* 1096:2276 */       setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
/* 1097:2277 */       setFieldSeparatorAtStart(true);
/* 1098:2278 */       setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
/* 1099:     */     }
/* 1100:     */     
/* 1101:     */     private Object readResolve()
/* 1102:     */     {
/* 1103:2287 */       return ToStringStyle.MULTI_LINE_STYLE;
/* 1104:     */     }
/* 1105:     */   }
/* 1106:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.builder.ToStringStyle
 * JD-Core Version:    0.7.0.1
 */