/*    1:     */ package org.apache.commons.collections;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileInputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.LineNumberReader;
/*    8:     */ import java.io.OutputStream;
/*    9:     */ import java.io.PrintStream;
/*   10:     */ import java.io.PrintWriter;
/*   11:     */ import java.io.Reader;
/*   12:     */ import java.util.ArrayList;
/*   13:     */ import java.util.Enumeration;
/*   14:     */ import java.util.Hashtable;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.List;
/*   17:     */ import java.util.NoSuchElementException;
/*   18:     */ import java.util.Properties;
/*   19:     */ import java.util.StringTokenizer;
/*   20:     */ import java.util.Vector;
/*   21:     */ 
/*   22:     */ public class ExtendedProperties
/*   23:     */   extends Hashtable
/*   24:     */ {
/*   25:     */   private ExtendedProperties defaults;
/*   26:     */   protected String file;
/*   27:     */   protected String basePath;
/*   28: 168 */   protected String fileSeparator = System.getProperty("file.separator");
/*   29: 173 */   protected boolean isInitialized = false;
/*   30: 179 */   protected static String include = "include";
/*   31: 187 */   protected ArrayList keysAsListed = new ArrayList();
/*   32:     */   protected static final String START_TOKEN = "${";
/*   33:     */   protected static final String END_TOKEN = "}";
/*   34:     */   
/*   35:     */   protected String interpolate(String base)
/*   36:     */   {
/*   37: 201 */     return interpolateHelper(base, null);
/*   38:     */   }
/*   39:     */   
/*   40:     */   protected String interpolateHelper(String base, List priorVariables)
/*   41:     */   {
/*   42: 220 */     if (base == null) {
/*   43: 221 */       return null;
/*   44:     */     }
/*   45: 226 */     if (priorVariables == null)
/*   46:     */     {
/*   47: 227 */       priorVariables = new ArrayList();
/*   48: 228 */       priorVariables.add(base);
/*   49:     */     }
/*   50: 231 */     int begin = -1;
/*   51: 232 */     int end = -1;
/*   52: 233 */     int prec = 0 - "}".length();
/*   53: 234 */     String variable = null;
/*   54: 235 */     StringBuffer result = new StringBuffer();
/*   55: 239 */     while (((begin = base.indexOf("${", prec + "}".length())) > -1) && ((end = base.indexOf("}", begin)) > -1))
/*   56:     */     {
/*   57: 240 */       result.append(base.substring(prec + "}".length(), begin));
/*   58: 241 */       variable = base.substring(begin + "${".length(), end);
/*   59: 244 */       if (priorVariables.contains(variable))
/*   60:     */       {
/*   61: 245 */         String initialBase = priorVariables.remove(0).toString();
/*   62: 246 */         priorVariables.add(variable);
/*   63: 247 */         StringBuffer priorVariableSb = new StringBuffer();
/*   64: 251 */         for (Iterator it = priorVariables.iterator(); it.hasNext();)
/*   65:     */         {
/*   66: 252 */           priorVariableSb.append(it.next());
/*   67: 253 */           if (it.hasNext()) {
/*   68: 254 */             priorVariableSb.append("->");
/*   69:     */           }
/*   70:     */         }
/*   71: 258 */         throw new IllegalStateException("infinite loop in property interpolation of " + initialBase + ": " + priorVariableSb.toString());
/*   72:     */       }
/*   73: 263 */       priorVariables.add(variable);
/*   74:     */       
/*   75:     */ 
/*   76:     */ 
/*   77: 267 */       Object value = getProperty(variable);
/*   78: 268 */       if (value != null)
/*   79:     */       {
/*   80: 269 */         result.append(interpolateHelper(value.toString(), priorVariables));
/*   81:     */         
/*   82:     */ 
/*   83:     */ 
/*   84:     */ 
/*   85:     */ 
/*   86: 275 */         priorVariables.remove(priorVariables.size() - 1);
/*   87:     */       }
/*   88: 276 */       else if ((this.defaults != null) && (this.defaults.getString(variable, null) != null))
/*   89:     */       {
/*   90: 277 */         result.append(this.defaults.getString(variable));
/*   91:     */       }
/*   92:     */       else
/*   93:     */       {
/*   94: 280 */         result.append("${").append(variable).append("}");
/*   95:     */       }
/*   96: 282 */       prec = end;
/*   97:     */     }
/*   98: 284 */     result.append(base.substring(prec + "}".length(), base.length()));
/*   99:     */     
/*  100: 286 */     return result.toString();
/*  101:     */   }
/*  102:     */   
/*  103:     */   private static String escape(String s)
/*  104:     */   {
/*  105: 293 */     StringBuffer buf = new StringBuffer(s);
/*  106: 294 */     for (int i = 0; i < buf.length(); i++)
/*  107:     */     {
/*  108: 295 */       char c = buf.charAt(i);
/*  109: 296 */       if ((c == ',') || (c == '\\'))
/*  110:     */       {
/*  111: 297 */         buf.insert(i, '\\');
/*  112: 298 */         i++;
/*  113:     */       }
/*  114:     */     }
/*  115: 301 */     return buf.toString();
/*  116:     */   }
/*  117:     */   
/*  118:     */   private static String unescape(String s)
/*  119:     */   {
/*  120: 308 */     StringBuffer buf = new StringBuffer(s);
/*  121: 309 */     for (int i = 0; i < buf.length() - 1; i++)
/*  122:     */     {
/*  123: 310 */       char c1 = buf.charAt(i);
/*  124: 311 */       char c2 = buf.charAt(i + 1);
/*  125: 312 */       if ((c1 == '\\') && (c2 == '\\')) {
/*  126: 313 */         buf.deleteCharAt(i);
/*  127:     */       }
/*  128:     */     }
/*  129: 316 */     return buf.toString();
/*  130:     */   }
/*  131:     */   
/*  132:     */   private static int countPreceding(String line, int index, char ch)
/*  133:     */   {
/*  134: 325 */     for (int i = index - 1; i >= 0; i--) {
/*  135: 326 */       if (line.charAt(i) != ch) {
/*  136:     */         break;
/*  137:     */       }
/*  138:     */     }
/*  139: 330 */     return index - 1 - i;
/*  140:     */   }
/*  141:     */   
/*  142:     */   private static boolean endsWithSlash(String line)
/*  143:     */   {
/*  144: 337 */     if (!line.endsWith("\\")) {
/*  145: 338 */       return false;
/*  146:     */     }
/*  147: 340 */     return countPreceding(line, line.length() - 1, '\\') % 2 == 0;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public ExtendedProperties() {}
/*  151:     */   
/*  152:     */   static class PropertiesReader
/*  153:     */     extends LineNumberReader
/*  154:     */   {
/*  155:     */     public PropertiesReader(Reader reader)
/*  156:     */     {
/*  157: 356 */       super();
/*  158:     */     }
/*  159:     */     
/*  160:     */     public String readProperty()
/*  161:     */       throws IOException
/*  162:     */     {
/*  163: 366 */       StringBuffer buffer = new StringBuffer();
/*  164: 367 */       String line = readLine();
/*  165: 368 */       while (line != null)
/*  166:     */       {
/*  167: 369 */         line = line.trim();
/*  168: 370 */         if ((line.length() != 0) && (line.charAt(0) != '#')) {
/*  169: 371 */           if (ExtendedProperties.endsWithSlash(line))
/*  170:     */           {
/*  171: 372 */             line = line.substring(0, line.length() - 1);
/*  172: 373 */             buffer.append(line);
/*  173:     */           }
/*  174:     */           else
/*  175:     */           {
/*  176: 375 */             buffer.append(line);
/*  177: 376 */             return buffer.toString();
/*  178:     */           }
/*  179:     */         }
/*  180: 379 */         line = readLine();
/*  181:     */       }
/*  182: 381 */       return null;
/*  183:     */     }
/*  184:     */   }
/*  185:     */   
/*  186:     */   static class PropertiesTokenizer
/*  187:     */     extends StringTokenizer
/*  188:     */   {
/*  189:     */     static final String DELIMITER = ",";
/*  190:     */     
/*  191:     */     public PropertiesTokenizer(String string)
/*  192:     */     {
/*  193: 402 */       super(",");
/*  194:     */     }
/*  195:     */     
/*  196:     */     public boolean hasMoreTokens()
/*  197:     */     {
/*  198: 411 */       return super.hasMoreTokens();
/*  199:     */     }
/*  200:     */     
/*  201:     */     public String nextToken()
/*  202:     */     {
/*  203: 420 */       StringBuffer buffer = new StringBuffer();
/*  204: 422 */       while (hasMoreTokens())
/*  205:     */       {
/*  206: 423 */         String token = super.nextToken();
/*  207: 424 */         if (ExtendedProperties.endsWithSlash(token))
/*  208:     */         {
/*  209: 425 */           buffer.append(token.substring(0, token.length() - 1));
/*  210: 426 */           buffer.append(",");
/*  211:     */         }
/*  212:     */         else
/*  213:     */         {
/*  214: 428 */           buffer.append(token);
/*  215: 429 */           break;
/*  216:     */         }
/*  217:     */       }
/*  218: 433 */       return buffer.toString().trim();
/*  219:     */     }
/*  220:     */   }
/*  221:     */   
/*  222:     */   public ExtendedProperties(String file)
/*  223:     */     throws IOException
/*  224:     */   {
/*  225: 451 */     this(file, null);
/*  226:     */   }
/*  227:     */   
/*  228:     */   public ExtendedProperties(String file, String defaultFile)
/*  229:     */     throws IOException
/*  230:     */   {
/*  231: 462 */     this.file = file;
/*  232:     */     
/*  233: 464 */     this.basePath = new File(file).getAbsolutePath();
/*  234: 465 */     this.basePath = this.basePath.substring(0, this.basePath.lastIndexOf(this.fileSeparator) + 1);
/*  235:     */     
/*  236: 467 */     FileInputStream in = null;
/*  237:     */     try
/*  238:     */     {
/*  239: 469 */       in = new FileInputStream(file);
/*  240: 470 */       load(in);
/*  241:     */       try
/*  242:     */       {
/*  243: 473 */         if (in != null) {
/*  244: 474 */           in.close();
/*  245:     */         }
/*  246:     */       }
/*  247:     */       catch (IOException ex) {}
/*  248: 479 */       if (defaultFile == null) {
/*  249:     */         return;
/*  250:     */       }
/*  251:     */     }
/*  252:     */     finally
/*  253:     */     {
/*  254:     */       try
/*  255:     */       {
/*  256: 473 */         if (in != null) {
/*  257: 474 */           in.close();
/*  258:     */         }
/*  259:     */       }
/*  260:     */       catch (IOException ex) {}
/*  261:     */     }
/*  262: 480 */     this.defaults = new ExtendedProperties(defaultFile);
/*  263:     */   }
/*  264:     */   
/*  265:     */   public boolean isInitialized()
/*  266:     */   {
/*  267: 489 */     return this.isInitialized;
/*  268:     */   }
/*  269:     */   
/*  270:     */   public String getInclude()
/*  271:     */   {
/*  272: 499 */     return include;
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void setInclude(String inc)
/*  276:     */   {
/*  277: 509 */     include = inc;
/*  278:     */   }
/*  279:     */   
/*  280:     */   public void load(InputStream input)
/*  281:     */     throws IOException
/*  282:     */   {
/*  283: 519 */     load(input, null);
/*  284:     */   }
/*  285:     */   
/*  286:     */   /* Error */
/*  287:     */   public synchronized void load(InputStream input, String enc)
/*  288:     */     throws IOException
/*  289:     */   {
/*  290:     */     // Byte code:
/*  291:     */     //   0: aconst_null
/*  292:     */     //   1: astore_3
/*  293:     */     //   2: aload_2
/*  294:     */     //   3: ifnull +25 -> 28
/*  295:     */     //   6: new 63	org/apache/commons/collections/ExtendedProperties$PropertiesReader
/*  296:     */     //   9: dup
/*  297:     */     //   10: new 64	java/io/InputStreamReader
/*  298:     */     //   13: dup
/*  299:     */     //   14: aload_1
/*  300:     */     //   15: aload_2
/*  301:     */     //   16: invokespecial 65	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
/*  302:     */     //   19: invokespecial 66	org/apache/commons/collections/ExtendedProperties$PropertiesReader:<init>	(Ljava/io/Reader;)V
/*  303:     */     //   22: astore_3
/*  304:     */     //   23: goto +5 -> 28
/*  305:     */     //   26: astore 4
/*  306:     */     //   28: aload_3
/*  307:     */     //   29: ifnonnull +42 -> 71
/*  308:     */     //   32: new 63	org/apache/commons/collections/ExtendedProperties$PropertiesReader
/*  309:     */     //   35: dup
/*  310:     */     //   36: new 64	java/io/InputStreamReader
/*  311:     */     //   39: dup
/*  312:     */     //   40: aload_1
/*  313:     */     //   41: ldc 68
/*  314:     */     //   43: invokespecial 65	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
/*  315:     */     //   46: invokespecial 66	org/apache/commons/collections/ExtendedProperties$PropertiesReader:<init>	(Ljava/io/Reader;)V
/*  316:     */     //   49: astore_3
/*  317:     */     //   50: goto +21 -> 71
/*  318:     */     //   53: astore 4
/*  319:     */     //   55: new 63	org/apache/commons/collections/ExtendedProperties$PropertiesReader
/*  320:     */     //   58: dup
/*  321:     */     //   59: new 64	java/io/InputStreamReader
/*  322:     */     //   62: dup
/*  323:     */     //   63: aload_1
/*  324:     */     //   64: invokespecial 69	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
/*  325:     */     //   67: invokespecial 66	org/apache/commons/collections/ExtendedProperties$PropertiesReader:<init>	(Ljava/io/Reader;)V
/*  326:     */     //   70: astore_3
/*  327:     */     //   71: aload_3
/*  328:     */     //   72: invokevirtual 70	org/apache/commons/collections/ExtendedProperties$PropertiesReader:readProperty	()Ljava/lang/String;
/*  329:     */     //   75: astore 4
/*  330:     */     //   77: aload 4
/*  331:     */     //   79: ifnonnull +9 -> 88
/*  332:     */     //   82: aload_0
/*  333:     */     //   83: iconst_1
/*  334:     */     //   84: putfield 45	org/apache/commons/collections/ExtendedProperties:isInitialized	Z
/*  335:     */     //   87: return
/*  336:     */     //   88: aload 4
/*  337:     */     //   90: bipush 61
/*  338:     */     //   92: invokevirtual 71	java/lang/String:indexOf	(I)I
/*  339:     */     //   95: istore 5
/*  340:     */     //   97: iload 5
/*  341:     */     //   99: ifle +205 -> 304
/*  342:     */     //   102: aload 4
/*  343:     */     //   104: iconst_0
/*  344:     */     //   105: iload 5
/*  345:     */     //   107: invokevirtual 12	java/lang/String:substring	(II)Ljava/lang/String;
/*  346:     */     //   110: invokevirtual 72	java/lang/String:trim	()Ljava/lang/String;
/*  347:     */     //   113: astore 6
/*  348:     */     //   115: aload 4
/*  349:     */     //   117: iload 5
/*  350:     */     //   119: iconst_1
/*  351:     */     //   120: iadd
/*  352:     */     //   121: invokevirtual 73	java/lang/String:substring	(I)Ljava/lang/String;
/*  353:     */     //   124: invokevirtual 72	java/lang/String:trim	()Ljava/lang/String;
/*  354:     */     //   127: astore 7
/*  355:     */     //   129: ldc 74
/*  356:     */     //   131: aload 7
/*  357:     */     //   133: invokevirtual 75	java/lang/String:equals	(Ljava/lang/Object;)Z
/*  358:     */     //   136: ifeq +6 -> 142
/*  359:     */     //   139: goto -68 -> 71
/*  360:     */     //   142: aload_0
/*  361:     */     //   143: invokevirtual 76	org/apache/commons/collections/ExtendedProperties:getInclude	()Ljava/lang/String;
/*  362:     */     //   146: ifnull +150 -> 296
/*  363:     */     //   149: aload 6
/*  364:     */     //   151: aload_0
/*  365:     */     //   152: invokevirtual 76	org/apache/commons/collections/ExtendedProperties:getInclude	()Ljava/lang/String;
/*  366:     */     //   155: invokevirtual 77	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
/*  367:     */     //   158: ifeq +138 -> 296
/*  368:     */     //   161: aconst_null
/*  369:     */     //   162: astore 8
/*  370:     */     //   164: aload 7
/*  371:     */     //   166: aload_0
/*  372:     */     //   167: getfield 44	org/apache/commons/collections/ExtendedProperties:fileSeparator	Ljava/lang/String;
/*  373:     */     //   170: invokevirtual 78	java/lang/String:startsWith	(Ljava/lang/String;)Z
/*  374:     */     //   173: ifeq +17 -> 190
/*  375:     */     //   176: new 49	java/io/File
/*  376:     */     //   179: dup
/*  377:     */     //   180: aload 7
/*  378:     */     //   182: invokespecial 50	java/io/File:<init>	(Ljava/lang/String;)V
/*  379:     */     //   185: astore 8
/*  380:     */     //   187: goto +72 -> 259
/*  381:     */     //   190: aload 7
/*  382:     */     //   192: new 8	java/lang/StringBuffer
/*  383:     */     //   195: dup
/*  384:     */     //   196: invokespecial 9	java/lang/StringBuffer:<init>	()V
/*  385:     */     //   199: ldc 79
/*  386:     */     //   201: invokevirtual 13	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/*  387:     */     //   204: aload_0
/*  388:     */     //   205: getfield 44	org/apache/commons/collections/ExtendedProperties:fileSeparator	Ljava/lang/String;
/*  389:     */     //   208: invokevirtual 13	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/*  390:     */     //   211: invokevirtual 25	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*  391:     */     //   214: invokevirtual 78	java/lang/String:startsWith	(Ljava/lang/String;)Z
/*  392:     */     //   217: ifeq +11 -> 228
/*  393:     */     //   220: aload 7
/*  394:     */     //   222: iconst_2
/*  395:     */     //   223: invokevirtual 73	java/lang/String:substring	(I)Ljava/lang/String;
/*  396:     */     //   226: astore 7
/*  397:     */     //   228: new 49	java/io/File
/*  398:     */     //   231: dup
/*  399:     */     //   232: new 8	java/lang/StringBuffer
/*  400:     */     //   235: dup
/*  401:     */     //   236: invokespecial 9	java/lang/StringBuffer:<init>	()V
/*  402:     */     //   239: aload_0
/*  403:     */     //   240: getfield 52	org/apache/commons/collections/ExtendedProperties:basePath	Ljava/lang/String;
/*  404:     */     //   243: invokevirtual 13	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/*  405:     */     //   246: aload 7
/*  406:     */     //   248: invokevirtual 13	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/*  407:     */     //   251: invokevirtual 25	java/lang/StringBuffer:toString	()Ljava/lang/String;
/*  408:     */     //   254: invokespecial 50	java/io/File:<init>	(Ljava/lang/String;)V
/*  409:     */     //   257: astore 8
/*  410:     */     //   259: aload 8
/*  411:     */     //   261: ifnull +32 -> 293
/*  412:     */     //   264: aload 8
/*  413:     */     //   266: invokevirtual 80	java/io/File:exists	()Z
/*  414:     */     //   269: ifeq +24 -> 293
/*  415:     */     //   272: aload 8
/*  416:     */     //   274: invokevirtual 81	java/io/File:canRead	()Z
/*  417:     */     //   277: ifeq +16 -> 293
/*  418:     */     //   280: aload_0
/*  419:     */     //   281: new 54	java/io/FileInputStream
/*  420:     */     //   284: dup
/*  421:     */     //   285: aload 8
/*  422:     */     //   287: invokespecial 82	java/io/FileInputStream:<init>	(Ljava/io/File;)V
/*  423:     */     //   290: invokevirtual 56	org/apache/commons/collections/ExtendedProperties:load	(Ljava/io/InputStream;)V
/*  424:     */     //   293: goto +11 -> 304
/*  425:     */     //   296: aload_0
/*  426:     */     //   297: aload 6
/*  427:     */     //   299: aload 7
/*  428:     */     //   301: invokevirtual 83	org/apache/commons/collections/ExtendedProperties:addProperty	(Ljava/lang/String;Ljava/lang/Object;)V
/*  429:     */     //   304: goto -233 -> 71
/*  430:     */     //   307: astore 9
/*  431:     */     //   309: aload_0
/*  432:     */     //   310: iconst_1
/*  433:     */     //   311: putfield 45	org/apache/commons/collections/ExtendedProperties:isInitialized	Z
/*  434:     */     //   314: aload 9
/*  435:     */     //   316: athrow
/*  436:     */     // Line number table:
/*  437:     */     //   Java source line #531	-> byte code offset #0
/*  438:     */     //   Java source line #532	-> byte code offset #2
/*  439:     */     //   Java source line #534	-> byte code offset #6
/*  440:     */     //   Java source line #538	-> byte code offset #23
/*  441:     */     //   Java source line #536	-> byte code offset #26
/*  442:     */     //   Java source line #541	-> byte code offset #28
/*  443:     */     //   Java source line #543	-> byte code offset #32
/*  444:     */     //   Java source line #549	-> byte code offset #50
/*  445:     */     //   Java source line #545	-> byte code offset #53
/*  446:     */     //   Java source line #548	-> byte code offset #55
/*  447:     */     //   Java source line #554	-> byte code offset #71
/*  448:     */     //   Java source line #555	-> byte code offset #77
/*  449:     */     //   Java source line #598	-> byte code offset #82
/*  450:     */     //   Java source line #599	-> byte code offset #87
/*  451:     */     //   Java source line #558	-> byte code offset #88
/*  452:     */     //   Java source line #560	-> byte code offset #97
/*  453:     */     //   Java source line #561	-> byte code offset #102
/*  454:     */     //   Java source line #562	-> byte code offset #115
/*  455:     */     //   Java source line #565	-> byte code offset #129
/*  456:     */     //   Java source line #566	-> byte code offset #139
/*  457:     */     //   Java source line #569	-> byte code offset #142
/*  458:     */     //   Java source line #571	-> byte code offset #161
/*  459:     */     //   Java source line #573	-> byte code offset #164
/*  460:     */     //   Java source line #575	-> byte code offset #176
/*  461:     */     //   Java source line #577	-> byte code offset #187
/*  462:     */     //   Java source line #581	-> byte code offset #190
/*  463:     */     //   Java source line #582	-> byte code offset #220
/*  464:     */     //   Java source line #585	-> byte code offset #228
/*  465:     */     //   Java source line #588	-> byte code offset #259
/*  466:     */     //   Java source line #589	-> byte code offset #280
/*  467:     */     //   Java source line #591	-> byte code offset #293
/*  468:     */     //   Java source line #592	-> byte code offset #296
/*  469:     */     //   Java source line #595	-> byte code offset #304
/*  470:     */     //   Java source line #598	-> byte code offset #307
/*  471:     */     //   Java source line #599	-> byte code offset #314
/*  472:     */     // Local variable table:
/*  473:     */     //   start	length	slot	name	signature
/*  474:     */     //   0	317	0	this	ExtendedProperties
/*  475:     */     //   0	317	1	input	InputStream
/*  476:     */     //   0	317	2	enc	String
/*  477:     */     //   1	71	3	reader	PropertiesReader
/*  478:     */     //   26	3	4	ex	java.io.UnsupportedEncodingException
/*  479:     */     //   53	3	4	ex	java.io.UnsupportedEncodingException
/*  480:     */     //   75	41	4	line	String
/*  481:     */     //   95	23	5	equalSign	int
/*  482:     */     //   113	185	6	key	String
/*  483:     */     //   127	173	7	value	String
/*  484:     */     //   162	124	8	file	File
/*  485:     */     //   307	8	9	localObject	Object
/*  486:     */     // Exception table:
/*  487:     */     //   from	to	target	type
/*  488:     */     //   6	23	26	java/io/UnsupportedEncodingException
/*  489:     */     //   32	50	53	java/io/UnsupportedEncodingException
/*  490:     */     //   71	82	307	finally
/*  491:     */     //   88	309	307	finally
/*  492:     */   }
/*  493:     */   
/*  494:     */   public Object getProperty(String key)
/*  495:     */   {
/*  496: 611 */     Object obj = get(key);
/*  497: 613 */     if (obj == null) {
/*  498: 616 */       if (this.defaults != null) {
/*  499: 617 */         obj = this.defaults.get(key);
/*  500:     */       }
/*  501:     */     }
/*  502: 621 */     return obj;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void addProperty(String key, Object value)
/*  506:     */   {
/*  507: 644 */     if ((value instanceof String))
/*  508:     */     {
/*  509: 645 */       String str = (String)value;
/*  510: 646 */       if (str.indexOf(",") > 0)
/*  511:     */       {
/*  512: 648 */         PropertiesTokenizer tokenizer = new PropertiesTokenizer(str);
/*  513: 649 */         while (tokenizer.hasMoreTokens())
/*  514:     */         {
/*  515: 650 */           String token = tokenizer.nextToken();
/*  516: 651 */           addPropertyInternal(key, unescape(token));
/*  517:     */         }
/*  518:     */       }
/*  519:     */       else
/*  520:     */       {
/*  521: 655 */         addPropertyInternal(key, unescape(str));
/*  522:     */       }
/*  523:     */     }
/*  524:     */     else
/*  525:     */     {
/*  526: 658 */       addPropertyInternal(key, value);
/*  527:     */     }
/*  528: 662 */     this.isInitialized = true;
/*  529:     */   }
/*  530:     */   
/*  531:     */   private void addPropertyDirect(String key, Object value)
/*  532:     */   {
/*  533: 674 */     if (!containsKey(key)) {
/*  534: 675 */       this.keysAsListed.add(key);
/*  535:     */     }
/*  536: 677 */     put(key, value);
/*  537:     */   }
/*  538:     */   
/*  539:     */   private void addPropertyInternal(String key, Object value)
/*  540:     */   {
/*  541: 692 */     Object current = get(key);
/*  542: 694 */     if ((current instanceof String))
/*  543:     */     {
/*  544: 696 */       List values = new Vector(2);
/*  545: 697 */       values.add(current);
/*  546: 698 */       values.add(value);
/*  547: 699 */       put(key, values);
/*  548:     */     }
/*  549: 701 */     else if ((current instanceof List))
/*  550:     */     {
/*  551: 703 */       ((List)current).add(value);
/*  552:     */     }
/*  553:     */     else
/*  554:     */     {
/*  555: 707 */       if (!containsKey(key)) {
/*  556: 708 */         this.keysAsListed.add(key);
/*  557:     */       }
/*  558: 710 */       put(key, value);
/*  559:     */     }
/*  560:     */   }
/*  561:     */   
/*  562:     */   public void setProperty(String key, Object value)
/*  563:     */   {
/*  564: 723 */     clearProperty(key);
/*  565: 724 */     addProperty(key, value);
/*  566:     */   }
/*  567:     */   
/*  568:     */   public synchronized void save(OutputStream output, String header)
/*  569:     */     throws IOException
/*  570:     */   {
/*  571: 737 */     if (output == null) {
/*  572: 738 */       return;
/*  573:     */     }
/*  574: 740 */     PrintWriter theWrtr = new PrintWriter(output);
/*  575: 741 */     if (header != null) {
/*  576: 742 */       theWrtr.println(header);
/*  577:     */     }
/*  578: 745 */     Enumeration theKeys = keys();
/*  579: 746 */     while (theKeys.hasMoreElements())
/*  580:     */     {
/*  581: 747 */       String key = (String)theKeys.nextElement();
/*  582: 748 */       Object value = get(key);
/*  583:     */       Iterator it;
/*  584: 749 */       if (value != null) {
/*  585: 750 */         if ((value instanceof String))
/*  586:     */         {
/*  587: 751 */           StringBuffer currentOutput = new StringBuffer();
/*  588: 752 */           currentOutput.append(key);
/*  589: 753 */           currentOutput.append("=");
/*  590: 754 */           currentOutput.append(escape((String)value));
/*  591: 755 */           theWrtr.println(currentOutput.toString());
/*  592:     */         }
/*  593: 757 */         else if ((value instanceof List))
/*  594:     */         {
/*  595: 758 */           List values = (List)value;
/*  596: 759 */           for (it = values.iterator(); it.hasNext();)
/*  597:     */           {
/*  598: 760 */             String currentElement = (String)it.next();
/*  599: 761 */             StringBuffer currentOutput = new StringBuffer();
/*  600: 762 */             currentOutput.append(key);
/*  601: 763 */             currentOutput.append("=");
/*  602: 764 */             currentOutput.append(escape(currentElement));
/*  603: 765 */             theWrtr.println(currentOutput.toString());
/*  604:     */           }
/*  605:     */         }
/*  606:     */       }
/*  607: 769 */       theWrtr.println();
/*  608: 770 */       theWrtr.flush();
/*  609:     */     }
/*  610:     */   }
/*  611:     */   
/*  612:     */   public void combine(ExtendedProperties props)
/*  613:     */   {
/*  614: 782 */     for (Iterator it = props.getKeys(); it.hasNext();)
/*  615:     */     {
/*  616: 783 */       String key = (String)it.next();
/*  617: 784 */       setProperty(key, props.get(key));
/*  618:     */     }
/*  619:     */   }
/*  620:     */   
/*  621:     */   public void clearProperty(String key)
/*  622:     */   {
/*  623: 794 */     if (containsKey(key))
/*  624:     */     {
/*  625: 797 */       for (int i = 0; i < this.keysAsListed.size(); i++) {
/*  626: 798 */         if (this.keysAsListed.get(i).equals(key))
/*  627:     */         {
/*  628: 799 */           this.keysAsListed.remove(i);
/*  629: 800 */           break;
/*  630:     */         }
/*  631:     */       }
/*  632: 803 */       remove(key);
/*  633:     */     }
/*  634:     */   }
/*  635:     */   
/*  636:     */   public Iterator getKeys()
/*  637:     */   {
/*  638: 814 */     return this.keysAsListed.iterator();
/*  639:     */   }
/*  640:     */   
/*  641:     */   public Iterator getKeys(String prefix)
/*  642:     */   {
/*  643: 825 */     Iterator keys = getKeys();
/*  644: 826 */     ArrayList matchingKeys = new ArrayList();
/*  645: 828 */     while (keys.hasNext())
/*  646:     */     {
/*  647: 829 */       Object key = keys.next();
/*  648: 831 */       if (((key instanceof String)) && (((String)key).startsWith(prefix))) {
/*  649: 832 */         matchingKeys.add(key);
/*  650:     */       }
/*  651:     */     }
/*  652: 835 */     return matchingKeys.iterator();
/*  653:     */   }
/*  654:     */   
/*  655:     */   public ExtendedProperties subset(String prefix)
/*  656:     */   {
/*  657: 847 */     ExtendedProperties c = new ExtendedProperties();
/*  658: 848 */     Iterator keys = getKeys();
/*  659: 849 */     boolean validSubset = false;
/*  660: 851 */     while (keys.hasNext())
/*  661:     */     {
/*  662: 852 */       Object key = keys.next();
/*  663: 854 */       if (((key instanceof String)) && (((String)key).startsWith(prefix)))
/*  664:     */       {
/*  665: 855 */         if (!validSubset) {
/*  666: 856 */           validSubset = true;
/*  667:     */         }
/*  668: 865 */         String newKey = null;
/*  669: 866 */         if (((String)key).length() == prefix.length()) {
/*  670: 867 */           newKey = prefix;
/*  671:     */         } else {
/*  672: 869 */           newKey = ((String)key).substring(prefix.length() + 1);
/*  673:     */         }
/*  674: 877 */         c.addPropertyDirect(newKey, get(key));
/*  675:     */       }
/*  676:     */     }
/*  677: 881 */     if (validSubset) {
/*  678: 882 */       return c;
/*  679:     */     }
/*  680: 884 */     return null;
/*  681:     */   }
/*  682:     */   
/*  683:     */   public void display()
/*  684:     */   {
/*  685: 892 */     Iterator i = getKeys();
/*  686: 894 */     while (i.hasNext())
/*  687:     */     {
/*  688: 895 */       String key = (String)i.next();
/*  689: 896 */       Object value = get(key);
/*  690: 897 */       System.out.println(key + " => " + value);
/*  691:     */     }
/*  692:     */   }
/*  693:     */   
/*  694:     */   public String getString(String key)
/*  695:     */   {
/*  696: 910 */     return getString(key, null);
/*  697:     */   }
/*  698:     */   
/*  699:     */   public String getString(String key, String defaultValue)
/*  700:     */   {
/*  701: 924 */     Object value = get(key);
/*  702: 926 */     if ((value instanceof String)) {
/*  703: 927 */       return interpolate((String)value);
/*  704:     */     }
/*  705: 929 */     if (value == null)
/*  706:     */     {
/*  707: 930 */       if (this.defaults != null) {
/*  708: 931 */         return interpolate(this.defaults.getString(key, defaultValue));
/*  709:     */       }
/*  710: 933 */       return interpolate(defaultValue);
/*  711:     */     }
/*  712: 935 */     if ((value instanceof List)) {
/*  713: 936 */       return interpolate((String)((List)value).get(0));
/*  714:     */     }
/*  715: 938 */     throw new ClassCastException('\'' + key + "' doesn't map to a String object");
/*  716:     */   }
/*  717:     */   
/*  718:     */   public Properties getProperties(String key)
/*  719:     */   {
/*  720: 954 */     return getProperties(key, new Properties());
/*  721:     */   }
/*  722:     */   
/*  723:     */   public Properties getProperties(String key, Properties defaults)
/*  724:     */   {
/*  725: 972 */     String[] tokens = getStringArray(key);
/*  726:     */     
/*  727:     */ 
/*  728: 975 */     Properties props = new Properties(defaults);
/*  729: 976 */     for (int i = 0; i < tokens.length; i++)
/*  730:     */     {
/*  731: 977 */       String token = tokens[i];
/*  732: 978 */       int equalSign = token.indexOf('=');
/*  733: 979 */       if (equalSign > 0)
/*  734:     */       {
/*  735: 980 */         String pkey = token.substring(0, equalSign).trim();
/*  736: 981 */         String pvalue = token.substring(equalSign + 1).trim();
/*  737: 982 */         props.put(pkey, pvalue);
/*  738:     */       }
/*  739:     */       else
/*  740:     */       {
/*  741: 984 */         throw new IllegalArgumentException('\'' + token + "' does not contain " + "an equals sign");
/*  742:     */       }
/*  743:     */     }
/*  744: 987 */     return props;
/*  745:     */   }
/*  746:     */   
/*  747:     */   public String[] getStringArray(String key)
/*  748:     */   {
/*  749:1000 */     Object value = get(key);
/*  750:1003 */     if ((value instanceof String))
/*  751:     */     {
/*  752:1004 */       List values = new Vector(1);
/*  753:1005 */       values.add(value);
/*  754:     */     }
/*  755:     */     else
/*  756:     */     {
/*  757:     */       List values;
/*  758:1007 */       if ((value instanceof List))
/*  759:     */       {
/*  760:1008 */         values = (List)value;
/*  761:     */       }
/*  762:     */       else
/*  763:     */       {
/*  764:1010 */         if (value == null)
/*  765:     */         {
/*  766:1011 */           if (this.defaults != null) {
/*  767:1012 */             return this.defaults.getStringArray(key);
/*  768:     */           }
/*  769:1014 */           return new String[0];
/*  770:     */         }
/*  771:1017 */         throw new ClassCastException('\'' + key + "' doesn't map to a String/List object");
/*  772:     */       }
/*  773:     */     }
/*  774:     */     List values;
/*  775:1020 */     String[] tokens = new String[values.size()];
/*  776:1021 */     for (int i = 0; i < tokens.length; i++) {
/*  777:1022 */       tokens[i] = ((String)values.get(i));
/*  778:     */     }
/*  779:1025 */     return tokens;
/*  780:     */   }
/*  781:     */   
/*  782:     */   public Vector getVector(String key)
/*  783:     */   {
/*  784:1038 */     return getVector(key, null);
/*  785:     */   }
/*  786:     */   
/*  787:     */   public Vector getVector(String key, Vector defaultValue)
/*  788:     */   {
/*  789:1054 */     Object value = get(key);
/*  790:1056 */     if ((value instanceof List)) {
/*  791:1057 */       return new Vector((List)value);
/*  792:     */     }
/*  793:1059 */     if ((value instanceof String))
/*  794:     */     {
/*  795:1060 */       Vector values = new Vector(1);
/*  796:1061 */       values.add(value);
/*  797:1062 */       put(key, values);
/*  798:1063 */       return values;
/*  799:     */     }
/*  800:1065 */     if (value == null)
/*  801:     */     {
/*  802:1066 */       if (this.defaults != null) {
/*  803:1067 */         return this.defaults.getVector(key, defaultValue);
/*  804:     */       }
/*  805:1069 */       return defaultValue == null ? new Vector() : defaultValue;
/*  806:     */     }
/*  807:1072 */     throw new ClassCastException('\'' + key + "' doesn't map to a Vector object");
/*  808:     */   }
/*  809:     */   
/*  810:     */   public List getList(String key)
/*  811:     */   {
/*  812:1089 */     return getList(key, null);
/*  813:     */   }
/*  814:     */   
/*  815:     */   public List getList(String key, List defaultValue)
/*  816:     */   {
/*  817:1106 */     Object value = get(key);
/*  818:1108 */     if ((value instanceof List)) {
/*  819:1109 */       return new ArrayList((List)value);
/*  820:     */     }
/*  821:1111 */     if ((value instanceof String))
/*  822:     */     {
/*  823:1112 */       List values = new ArrayList(1);
/*  824:1113 */       values.add(value);
/*  825:1114 */       put(key, values);
/*  826:1115 */       return values;
/*  827:     */     }
/*  828:1117 */     if (value == null)
/*  829:     */     {
/*  830:1118 */       if (this.defaults != null) {
/*  831:1119 */         return this.defaults.getList(key, defaultValue);
/*  832:     */       }
/*  833:1121 */       return defaultValue == null ? new ArrayList() : defaultValue;
/*  834:     */     }
/*  835:1124 */     throw new ClassCastException('\'' + key + "' doesn't map to a List object");
/*  836:     */   }
/*  837:     */   
/*  838:     */   public boolean getBoolean(String key)
/*  839:     */   {
/*  840:1139 */     Boolean b = getBoolean(key, null);
/*  841:1140 */     if (b != null) {
/*  842:1141 */       return b.booleanValue();
/*  843:     */     }
/*  844:1143 */     throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
/*  845:     */   }
/*  846:     */   
/*  847:     */   public boolean getBoolean(String key, boolean defaultValue)
/*  848:     */   {
/*  849:1157 */     return getBoolean(key, new Boolean(defaultValue)).booleanValue();
/*  850:     */   }
/*  851:     */   
/*  852:     */   public Boolean getBoolean(String key, Boolean defaultValue)
/*  853:     */   {
/*  854:1172 */     Object value = get(key);
/*  855:1174 */     if ((value instanceof Boolean)) {
/*  856:1175 */       return (Boolean)value;
/*  857:     */     }
/*  858:1177 */     if ((value instanceof String))
/*  859:     */     {
/*  860:1178 */       String s = testBoolean((String)value);
/*  861:1179 */       Boolean b = new Boolean(s);
/*  862:1180 */       put(key, b);
/*  863:1181 */       return b;
/*  864:     */     }
/*  865:1183 */     if (value == null)
/*  866:     */     {
/*  867:1184 */       if (this.defaults != null) {
/*  868:1185 */         return this.defaults.getBoolean(key, defaultValue);
/*  869:     */       }
/*  870:1187 */       return defaultValue;
/*  871:     */     }
/*  872:1190 */     throw new ClassCastException('\'' + key + "' doesn't map to a Boolean object");
/*  873:     */   }
/*  874:     */   
/*  875:     */   public String testBoolean(String value)
/*  876:     */   {
/*  877:1207 */     String s = value.toLowerCase();
/*  878:1209 */     if ((s.equals("true")) || (s.equals("on")) || (s.equals("yes"))) {
/*  879:1210 */       return "true";
/*  880:     */     }
/*  881:1211 */     if ((s.equals("false")) || (s.equals("off")) || (s.equals("no"))) {
/*  882:1212 */       return "false";
/*  883:     */     }
/*  884:1214 */     return null;
/*  885:     */   }
/*  886:     */   
/*  887:     */   public byte getByte(String key)
/*  888:     */   {
/*  889:1231 */     Byte b = getByte(key, null);
/*  890:1232 */     if (b != null) {
/*  891:1233 */       return b.byteValue();
/*  892:     */     }
/*  893:1235 */     throw new NoSuchElementException('\'' + key + " doesn't map to an existing object");
/*  894:     */   }
/*  895:     */   
/*  896:     */   public byte getByte(String key, byte defaultValue)
/*  897:     */   {
/*  898:1251 */     return getByte(key, new Byte(defaultValue)).byteValue();
/*  899:     */   }
/*  900:     */   
/*  901:     */   public Byte getByte(String key, Byte defaultValue)
/*  902:     */   {
/*  903:1267 */     Object value = get(key);
/*  904:1269 */     if ((value instanceof Byte)) {
/*  905:1270 */       return (Byte)value;
/*  906:     */     }
/*  907:1272 */     if ((value instanceof String))
/*  908:     */     {
/*  909:1273 */       Byte b = new Byte((String)value);
/*  910:1274 */       put(key, b);
/*  911:1275 */       return b;
/*  912:     */     }
/*  913:1277 */     if (value == null)
/*  914:     */     {
/*  915:1278 */       if (this.defaults != null) {
/*  916:1279 */         return this.defaults.getByte(key, defaultValue);
/*  917:     */       }
/*  918:1281 */       return defaultValue;
/*  919:     */     }
/*  920:1284 */     throw new ClassCastException('\'' + key + "' doesn't map to a Byte object");
/*  921:     */   }
/*  922:     */   
/*  923:     */   public short getShort(String key)
/*  924:     */   {
/*  925:1301 */     Short s = getShort(key, null);
/*  926:1302 */     if (s != null) {
/*  927:1303 */       return s.shortValue();
/*  928:     */     }
/*  929:1305 */     throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
/*  930:     */   }
/*  931:     */   
/*  932:     */   public short getShort(String key, short defaultValue)
/*  933:     */   {
/*  934:1321 */     return getShort(key, new Short(defaultValue)).shortValue();
/*  935:     */   }
/*  936:     */   
/*  937:     */   public Short getShort(String key, Short defaultValue)
/*  938:     */   {
/*  939:1337 */     Object value = get(key);
/*  940:1339 */     if ((value instanceof Short)) {
/*  941:1340 */       return (Short)value;
/*  942:     */     }
/*  943:1342 */     if ((value instanceof String))
/*  944:     */     {
/*  945:1343 */       Short s = new Short((String)value);
/*  946:1344 */       put(key, s);
/*  947:1345 */       return s;
/*  948:     */     }
/*  949:1347 */     if (value == null)
/*  950:     */     {
/*  951:1348 */       if (this.defaults != null) {
/*  952:1349 */         return this.defaults.getShort(key, defaultValue);
/*  953:     */       }
/*  954:1351 */       return defaultValue;
/*  955:     */     }
/*  956:1354 */     throw new ClassCastException('\'' + key + "' doesn't map to a Short object");
/*  957:     */   }
/*  958:     */   
/*  959:     */   public int getInt(String name)
/*  960:     */   {
/*  961:1366 */     return getInteger(name);
/*  962:     */   }
/*  963:     */   
/*  964:     */   public int getInt(String name, int def)
/*  965:     */   {
/*  966:1378 */     return getInteger(name, def);
/*  967:     */   }
/*  968:     */   
/*  969:     */   public int getInteger(String key)
/*  970:     */   {
/*  971:1394 */     Integer i = getInteger(key, null);
/*  972:1395 */     if (i != null) {
/*  973:1396 */       return i.intValue();
/*  974:     */     }
/*  975:1398 */     throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
/*  976:     */   }
/*  977:     */   
/*  978:     */   public int getInteger(String key, int defaultValue)
/*  979:     */   {
/*  980:1414 */     Integer i = getInteger(key, null);
/*  981:1416 */     if (i == null) {
/*  982:1417 */       return defaultValue;
/*  983:     */     }
/*  984:1419 */     return i.intValue();
/*  985:     */   }
/*  986:     */   
/*  987:     */   public Integer getInteger(String key, Integer defaultValue)
/*  988:     */   {
/*  989:1435 */     Object value = get(key);
/*  990:1437 */     if ((value instanceof Integer)) {
/*  991:1438 */       return (Integer)value;
/*  992:     */     }
/*  993:1440 */     if ((value instanceof String))
/*  994:     */     {
/*  995:1441 */       Integer i = new Integer((String)value);
/*  996:1442 */       put(key, i);
/*  997:1443 */       return i;
/*  998:     */     }
/*  999:1445 */     if (value == null)
/* 1000:     */     {
/* 1001:1446 */       if (this.defaults != null) {
/* 1002:1447 */         return this.defaults.getInteger(key, defaultValue);
/* 1003:     */       }
/* 1004:1449 */       return defaultValue;
/* 1005:     */     }
/* 1006:1452 */     throw new ClassCastException('\'' + key + "' doesn't map to a Integer object");
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public long getLong(String key)
/* 1010:     */   {
/* 1011:1469 */     Long l = getLong(key, null);
/* 1012:1470 */     if (l != null) {
/* 1013:1471 */       return l.longValue();
/* 1014:     */     }
/* 1015:1473 */     throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public long getLong(String key, long defaultValue)
/* 1019:     */   {
/* 1020:1489 */     return getLong(key, new Long(defaultValue)).longValue();
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public Long getLong(String key, Long defaultValue)
/* 1024:     */   {
/* 1025:1505 */     Object value = get(key);
/* 1026:1507 */     if ((value instanceof Long)) {
/* 1027:1508 */       return (Long)value;
/* 1028:     */     }
/* 1029:1510 */     if ((value instanceof String))
/* 1030:     */     {
/* 1031:1511 */       Long l = new Long((String)value);
/* 1032:1512 */       put(key, l);
/* 1033:1513 */       return l;
/* 1034:     */     }
/* 1035:1515 */     if (value == null)
/* 1036:     */     {
/* 1037:1516 */       if (this.defaults != null) {
/* 1038:1517 */         return this.defaults.getLong(key, defaultValue);
/* 1039:     */       }
/* 1040:1519 */       return defaultValue;
/* 1041:     */     }
/* 1042:1522 */     throw new ClassCastException('\'' + key + "' doesn't map to a Long object");
/* 1043:     */   }
/* 1044:     */   
/* 1045:     */   public float getFloat(String key)
/* 1046:     */   {
/* 1047:1539 */     Float f = getFloat(key, null);
/* 1048:1540 */     if (f != null) {
/* 1049:1541 */       return f.floatValue();
/* 1050:     */     }
/* 1051:1543 */     throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public float getFloat(String key, float defaultValue)
/* 1055:     */   {
/* 1056:1559 */     return getFloat(key, new Float(defaultValue)).floatValue();
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public Float getFloat(String key, Float defaultValue)
/* 1060:     */   {
/* 1061:1575 */     Object value = get(key);
/* 1062:1577 */     if ((value instanceof Float)) {
/* 1063:1578 */       return (Float)value;
/* 1064:     */     }
/* 1065:1580 */     if ((value instanceof String))
/* 1066:     */     {
/* 1067:1581 */       Float f = new Float((String)value);
/* 1068:1582 */       put(key, f);
/* 1069:1583 */       return f;
/* 1070:     */     }
/* 1071:1585 */     if (value == null)
/* 1072:     */     {
/* 1073:1586 */       if (this.defaults != null) {
/* 1074:1587 */         return this.defaults.getFloat(key, defaultValue);
/* 1075:     */       }
/* 1076:1589 */       return defaultValue;
/* 1077:     */     }
/* 1078:1592 */     throw new ClassCastException('\'' + key + "' doesn't map to a Float object");
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public double getDouble(String key)
/* 1082:     */   {
/* 1083:1609 */     Double d = getDouble(key, null);
/* 1084:1610 */     if (d != null) {
/* 1085:1611 */       return d.doubleValue();
/* 1086:     */     }
/* 1087:1613 */     throw new NoSuchElementException('\'' + key + "' doesn't map to an existing object");
/* 1088:     */   }
/* 1089:     */   
/* 1090:     */   public double getDouble(String key, double defaultValue)
/* 1091:     */   {
/* 1092:1629 */     return getDouble(key, new Double(defaultValue)).doubleValue();
/* 1093:     */   }
/* 1094:     */   
/* 1095:     */   public Double getDouble(String key, Double defaultValue)
/* 1096:     */   {
/* 1097:1645 */     Object value = get(key);
/* 1098:1647 */     if ((value instanceof Double)) {
/* 1099:1648 */       return (Double)value;
/* 1100:     */     }
/* 1101:1650 */     if ((value instanceof String))
/* 1102:     */     {
/* 1103:1651 */       Double d = new Double((String)value);
/* 1104:1652 */       put(key, d);
/* 1105:1653 */       return d;
/* 1106:     */     }
/* 1107:1655 */     if (value == null)
/* 1108:     */     {
/* 1109:1656 */       if (this.defaults != null) {
/* 1110:1657 */         return this.defaults.getDouble(key, defaultValue);
/* 1111:     */       }
/* 1112:1659 */       return defaultValue;
/* 1113:     */     }
/* 1114:1662 */     throw new ClassCastException('\'' + key + "' doesn't map to a Double object");
/* 1115:     */   }
/* 1116:     */   
/* 1117:     */   public static ExtendedProperties convertProperties(Properties props)
/* 1118:     */   {
/* 1119:1676 */     ExtendedProperties c = new ExtendedProperties();
/* 1120:1678 */     for (Enumeration e = props.propertyNames(); e.hasMoreElements();)
/* 1121:     */     {
/* 1122:1679 */       String s = (String)e.nextElement();
/* 1123:1680 */       c.setProperty(s, props.getProperty(s));
/* 1124:     */     }
/* 1125:1683 */     return c;
/* 1126:     */   }
/* 1127:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ExtendedProperties
 * JD-Core Version:    0.7.0.1
 */