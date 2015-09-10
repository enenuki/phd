/*    1:     */ package org.apache.commons.io;
/*    2:     */ 
/*    3:     */ import java.io.BufferedInputStream;
/*    4:     */ import java.io.BufferedReader;
/*    5:     */ import java.io.ByteArrayInputStream;
/*    6:     */ import java.io.CharArrayWriter;
/*    7:     */ import java.io.Closeable;
/*    8:     */ import java.io.EOFException;
/*    9:     */ import java.io.File;
/*   10:     */ import java.io.IOException;
/*   11:     */ import java.io.InputStream;
/*   12:     */ import java.io.InputStreamReader;
/*   13:     */ import java.io.OutputStream;
/*   14:     */ import java.io.OutputStreamWriter;
/*   15:     */ import java.io.PrintWriter;
/*   16:     */ import java.io.Reader;
/*   17:     */ import java.io.Writer;
/*   18:     */ import java.net.Socket;
/*   19:     */ import java.util.ArrayList;
/*   20:     */ import java.util.Collection;
/*   21:     */ import java.util.List;
/*   22:     */ import org.apache.commons.io.output.ByteArrayOutputStream;
/*   23:     */ import org.apache.commons.io.output.StringBuilderWriter;
/*   24:     */ 
/*   25:     */ public class IOUtils
/*   26:     */ {
/*   27:     */   public static final char DIR_SEPARATOR_UNIX = '/';
/*   28:     */   public static final char DIR_SEPARATOR_WINDOWS = '\\';
/*   29:  99 */   public static final char DIR_SEPARATOR = File.separatorChar;
/*   30:     */   public static final String LINE_SEPARATOR_UNIX = "\n";
/*   31:     */   public static final String LINE_SEPARATOR_WINDOWS = "\r\n";
/*   32:     */   public static final String LINE_SEPARATOR;
/*   33:     */   private static final int DEFAULT_BUFFER_SIZE = 4096;
/*   34:     */   private static final int SKIP_BUFFER_SIZE = 2048;
/*   35:     */   private static char[] SKIP_CHAR_BUFFER;
/*   36:     */   private static byte[] SKIP_BYTE_BUFFER;
/*   37:     */   
/*   38:     */   static
/*   39:     */   {
/*   40: 114 */     StringBuilderWriter buf = new StringBuilderWriter(4);
/*   41: 115 */     PrintWriter out = new PrintWriter(buf);
/*   42: 116 */     out.println();
/*   43: 117 */     LINE_SEPARATOR = buf.toString();
/*   44: 118 */     out.close();
/*   45:     */   }
/*   46:     */   
/*   47:     */   public static void closeQuietly(Reader input)
/*   48:     */   {
/*   49: 170 */     closeQuietly(input);
/*   50:     */   }
/*   51:     */   
/*   52:     */   public static void closeQuietly(Writer output)
/*   53:     */   {
/*   54: 196 */     closeQuietly(output);
/*   55:     */   }
/*   56:     */   
/*   57:     */   public static void closeQuietly(InputStream input)
/*   58:     */   {
/*   59: 223 */     closeQuietly(input);
/*   60:     */   }
/*   61:     */   
/*   62:     */   public static void closeQuietly(OutputStream output)
/*   63:     */   {
/*   64: 251 */     closeQuietly(output);
/*   65:     */   }
/*   66:     */   
/*   67:     */   public static void closeQuietly(Closeable closeable)
/*   68:     */   {
/*   69:     */     try
/*   70:     */     {
/*   71: 279 */       if (closeable != null) {
/*   72: 280 */         closeable.close();
/*   73:     */       }
/*   74:     */     }
/*   75:     */     catch (IOException ioe) {}
/*   76:     */   }
/*   77:     */   
/*   78:     */   public static void closeQuietly(Socket sock)
/*   79:     */   {
/*   80: 311 */     if (sock != null) {
/*   81:     */       try
/*   82:     */       {
/*   83: 313 */         sock.close();
/*   84:     */       }
/*   85:     */       catch (IOException ioe) {}
/*   86:     */     }
/*   87:     */   }
/*   88:     */   
/*   89:     */   public static InputStream toBufferedInputStream(InputStream input)
/*   90:     */     throws IOException
/*   91:     */   {
/*   92: 342 */     return ByteArrayOutputStream.toBufferedInputStream(input);
/*   93:     */   }
/*   94:     */   
/*   95:     */   public static byte[] toByteArray(InputStream input)
/*   96:     */     throws IOException
/*   97:     */   {
/*   98: 359 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*   99: 360 */     copy(input, output);
/*  100: 361 */     return output.toByteArray();
/*  101:     */   }
/*  102:     */   
/*  103:     */   public static byte[] toByteArray(Reader input)
/*  104:     */     throws IOException
/*  105:     */   {
/*  106: 377 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  107: 378 */     copy(input, output);
/*  108: 379 */     return output.toByteArray();
/*  109:     */   }
/*  110:     */   
/*  111:     */   public static byte[] toByteArray(Reader input, String encoding)
/*  112:     */     throws IOException
/*  113:     */   {
/*  114: 401 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/*  115: 402 */     copy(input, output, encoding);
/*  116: 403 */     return output.toByteArray();
/*  117:     */   }
/*  118:     */   
/*  119:     */   @Deprecated
/*  120:     */   public static byte[] toByteArray(String input)
/*  121:     */     throws IOException
/*  122:     */   {
/*  123: 420 */     return input.getBytes();
/*  124:     */   }
/*  125:     */   
/*  126:     */   public static char[] toCharArray(InputStream is)
/*  127:     */     throws IOException
/*  128:     */   {
/*  129: 439 */     CharArrayWriter output = new CharArrayWriter();
/*  130: 440 */     copy(is, output);
/*  131: 441 */     return output.toCharArray();
/*  132:     */   }
/*  133:     */   
/*  134:     */   public static char[] toCharArray(InputStream is, String encoding)
/*  135:     */     throws IOException
/*  136:     */   {
/*  137: 463 */     CharArrayWriter output = new CharArrayWriter();
/*  138: 464 */     copy(is, output, encoding);
/*  139: 465 */     return output.toCharArray();
/*  140:     */   }
/*  141:     */   
/*  142:     */   public static char[] toCharArray(Reader input)
/*  143:     */     throws IOException
/*  144:     */   {
/*  145: 481 */     CharArrayWriter sw = new CharArrayWriter();
/*  146: 482 */     copy(input, sw);
/*  147: 483 */     return sw.toCharArray();
/*  148:     */   }
/*  149:     */   
/*  150:     */   public static String toString(InputStream input)
/*  151:     */     throws IOException
/*  152:     */   {
/*  153: 501 */     StringBuilderWriter sw = new StringBuilderWriter();
/*  154: 502 */     copy(input, sw);
/*  155: 503 */     return sw.toString();
/*  156:     */   }
/*  157:     */   
/*  158:     */   public static String toString(InputStream input, String encoding)
/*  159:     */     throws IOException
/*  160:     */   {
/*  161: 524 */     StringBuilderWriter sw = new StringBuilderWriter();
/*  162: 525 */     copy(input, sw, encoding);
/*  163: 526 */     return sw.toString();
/*  164:     */   }
/*  165:     */   
/*  166:     */   public static String toString(Reader input)
/*  167:     */     throws IOException
/*  168:     */   {
/*  169: 541 */     StringBuilderWriter sw = new StringBuilderWriter();
/*  170: 542 */     copy(input, sw);
/*  171: 543 */     return sw.toString();
/*  172:     */   }
/*  173:     */   
/*  174:     */   @Deprecated
/*  175:     */   public static String toString(byte[] input)
/*  176:     */     throws IOException
/*  177:     */   {
/*  178: 558 */     return new String(input);
/*  179:     */   }
/*  180:     */   
/*  181:     */   @Deprecated
/*  182:     */   public static String toString(byte[] input, String encoding)
/*  183:     */     throws IOException
/*  184:     */   {
/*  185: 578 */     if (encoding == null) {
/*  186: 579 */       return new String(input);
/*  187:     */     }
/*  188: 581 */     return new String(input, encoding);
/*  189:     */   }
/*  190:     */   
/*  191:     */   public static List<String> readLines(InputStream input)
/*  192:     */     throws IOException
/*  193:     */   {
/*  194: 601 */     InputStreamReader reader = new InputStreamReader(input);
/*  195: 602 */     return readLines(reader);
/*  196:     */   }
/*  197:     */   
/*  198:     */   public static List<String> readLines(InputStream input, String encoding)
/*  199:     */     throws IOException
/*  200:     */   {
/*  201: 623 */     if (encoding == null) {
/*  202: 624 */       return readLines(input);
/*  203:     */     }
/*  204: 626 */     InputStreamReader reader = new InputStreamReader(input, encoding);
/*  205: 627 */     return readLines(reader);
/*  206:     */   }
/*  207:     */   
/*  208:     */   public static List<String> readLines(Reader input)
/*  209:     */     throws IOException
/*  210:     */   {
/*  211: 645 */     BufferedReader reader = new BufferedReader(input);
/*  212: 646 */     List<String> list = new ArrayList();
/*  213: 647 */     String line = reader.readLine();
/*  214: 648 */     while (line != null)
/*  215:     */     {
/*  216: 649 */       list.add(line);
/*  217: 650 */       line = reader.readLine();
/*  218:     */     }
/*  219: 652 */     return list;
/*  220:     */   }
/*  221:     */   
/*  222:     */   public static LineIterator lineIterator(Reader reader)
/*  223:     */   {
/*  224: 685 */     return new LineIterator(reader);
/*  225:     */   }
/*  226:     */   
/*  227:     */   public static LineIterator lineIterator(InputStream input, String encoding)
/*  228:     */     throws IOException
/*  229:     */   {
/*  230: 720 */     Reader reader = null;
/*  231: 721 */     if (encoding == null) {
/*  232: 722 */       reader = new InputStreamReader(input);
/*  233:     */     } else {
/*  234: 724 */       reader = new InputStreamReader(input, encoding);
/*  235:     */     }
/*  236: 726 */     return new LineIterator(reader);
/*  237:     */   }
/*  238:     */   
/*  239:     */   public static InputStream toInputStream(CharSequence input)
/*  240:     */   {
/*  241: 739 */     return toInputStream(input.toString());
/*  242:     */   }
/*  243:     */   
/*  244:     */   public static InputStream toInputStream(CharSequence input, String encoding)
/*  245:     */     throws IOException
/*  246:     */   {
/*  247: 756 */     return toInputStream(input.toString(), encoding);
/*  248:     */   }
/*  249:     */   
/*  250:     */   public static InputStream toInputStream(String input)
/*  251:     */   {
/*  252: 769 */     byte[] bytes = input.getBytes();
/*  253: 770 */     return new ByteArrayInputStream(bytes);
/*  254:     */   }
/*  255:     */   
/*  256:     */   public static InputStream toInputStream(String input, String encoding)
/*  257:     */     throws IOException
/*  258:     */   {
/*  259: 787 */     byte[] bytes = encoding != null ? input.getBytes(encoding) : input.getBytes();
/*  260: 788 */     return new ByteArrayInputStream(bytes);
/*  261:     */   }
/*  262:     */   
/*  263:     */   public static void write(byte[] data, OutputStream output)
/*  264:     */     throws IOException
/*  265:     */   {
/*  266: 805 */     if (data != null) {
/*  267: 806 */       output.write(data);
/*  268:     */     }
/*  269:     */   }
/*  270:     */   
/*  271:     */   public static void write(byte[] data, Writer output)
/*  272:     */     throws IOException
/*  273:     */   {
/*  274: 824 */     if (data != null) {
/*  275: 825 */       output.write(new String(data));
/*  276:     */     }
/*  277:     */   }
/*  278:     */   
/*  279:     */   public static void write(byte[] data, Writer output, String encoding)
/*  280:     */     throws IOException
/*  281:     */   {
/*  282: 848 */     if (data != null) {
/*  283: 849 */       if (encoding == null) {
/*  284: 850 */         write(data, output);
/*  285:     */       } else {
/*  286: 852 */         output.write(new String(data, encoding));
/*  287:     */       }
/*  288:     */     }
/*  289:     */   }
/*  290:     */   
/*  291:     */   public static void write(char[] data, Writer output)
/*  292:     */     throws IOException
/*  293:     */   {
/*  294: 871 */     if (data != null) {
/*  295: 872 */       output.write(data);
/*  296:     */     }
/*  297:     */   }
/*  298:     */   
/*  299:     */   public static void write(char[] data, OutputStream output)
/*  300:     */     throws IOException
/*  301:     */   {
/*  302: 892 */     if (data != null) {
/*  303: 893 */       output.write(new String(data).getBytes());
/*  304:     */     }
/*  305:     */   }
/*  306:     */   
/*  307:     */   public static void write(char[] data, OutputStream output, String encoding)
/*  308:     */     throws IOException
/*  309:     */   {
/*  310: 917 */     if (data != null) {
/*  311: 918 */       if (encoding == null) {
/*  312: 919 */         write(data, output);
/*  313:     */       } else {
/*  314: 921 */         output.write(new String(data).getBytes(encoding));
/*  315:     */       }
/*  316:     */     }
/*  317:     */   }
/*  318:     */   
/*  319:     */   public static void write(CharSequence data, Writer output)
/*  320:     */     throws IOException
/*  321:     */   {
/*  322: 938 */     if (data != null) {
/*  323: 939 */       write(data.toString(), output);
/*  324:     */     }
/*  325:     */   }
/*  326:     */   
/*  327:     */   public static void write(CharSequence data, OutputStream output)
/*  328:     */     throws IOException
/*  329:     */   {
/*  330: 958 */     if (data != null) {
/*  331: 959 */       write(data.toString(), output);
/*  332:     */     }
/*  333:     */   }
/*  334:     */   
/*  335:     */   public static void write(CharSequence data, OutputStream output, String encoding)
/*  336:     */     throws IOException
/*  337:     */   {
/*  338: 981 */     if (data != null) {
/*  339: 982 */       write(data.toString(), output, encoding);
/*  340:     */     }
/*  341:     */   }
/*  342:     */   
/*  343:     */   public static void write(String data, Writer output)
/*  344:     */     throws IOException
/*  345:     */   {
/*  346: 998 */     if (data != null) {
/*  347: 999 */       output.write(data);
/*  348:     */     }
/*  349:     */   }
/*  350:     */   
/*  351:     */   public static void write(String data, OutputStream output)
/*  352:     */     throws IOException
/*  353:     */   {
/*  354:1018 */     if (data != null) {
/*  355:1019 */       output.write(data.getBytes());
/*  356:     */     }
/*  357:     */   }
/*  358:     */   
/*  359:     */   public static void write(String data, OutputStream output, String encoding)
/*  360:     */     throws IOException
/*  361:     */   {
/*  362:1041 */     if (data != null) {
/*  363:1042 */       if (encoding == null) {
/*  364:1043 */         write(data, output);
/*  365:     */       } else {
/*  366:1045 */         output.write(data.getBytes(encoding));
/*  367:     */       }
/*  368:     */     }
/*  369:     */   }
/*  370:     */   
/*  371:     */   @Deprecated
/*  372:     */   public static void write(StringBuffer data, Writer output)
/*  373:     */     throws IOException
/*  374:     */   {
/*  375:1065 */     if (data != null) {
/*  376:1066 */       output.write(data.toString());
/*  377:     */     }
/*  378:     */   }
/*  379:     */   
/*  380:     */   @Deprecated
/*  381:     */   public static void write(StringBuffer data, OutputStream output)
/*  382:     */     throws IOException
/*  383:     */   {
/*  384:1087 */     if (data != null) {
/*  385:1088 */       output.write(data.toString().getBytes());
/*  386:     */     }
/*  387:     */   }
/*  388:     */   
/*  389:     */   @Deprecated
/*  390:     */   public static void write(StringBuffer data, OutputStream output, String encoding)
/*  391:     */     throws IOException
/*  392:     */   {
/*  393:1112 */     if (data != null) {
/*  394:1113 */       if (encoding == null) {
/*  395:1114 */         write(data, output);
/*  396:     */       } else {
/*  397:1116 */         output.write(data.toString().getBytes(encoding));
/*  398:     */       }
/*  399:     */     }
/*  400:     */   }
/*  401:     */   
/*  402:     */   public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output)
/*  403:     */     throws IOException
/*  404:     */   {
/*  405:1137 */     if (lines == null) {
/*  406:1138 */       return;
/*  407:     */     }
/*  408:1140 */     if (lineEnding == null) {
/*  409:1141 */       lineEnding = LINE_SEPARATOR;
/*  410:     */     }
/*  411:1143 */     for (Object line : lines)
/*  412:     */     {
/*  413:1144 */       if (line != null) {
/*  414:1145 */         output.write(line.toString().getBytes());
/*  415:     */       }
/*  416:1147 */       output.write(lineEnding.getBytes());
/*  417:     */     }
/*  418:     */   }
/*  419:     */   
/*  420:     */   public static void writeLines(Collection<?> lines, String lineEnding, OutputStream output, String encoding)
/*  421:     */     throws IOException
/*  422:     */   {
/*  423:1169 */     if (encoding == null)
/*  424:     */     {
/*  425:1170 */       writeLines(lines, lineEnding, output);
/*  426:     */     }
/*  427:     */     else
/*  428:     */     {
/*  429:1172 */       if (lines == null) {
/*  430:1173 */         return;
/*  431:     */       }
/*  432:1175 */       if (lineEnding == null) {
/*  433:1176 */         lineEnding = LINE_SEPARATOR;
/*  434:     */       }
/*  435:1178 */       for (Object line : lines)
/*  436:     */       {
/*  437:1179 */         if (line != null) {
/*  438:1180 */           output.write(line.toString().getBytes(encoding));
/*  439:     */         }
/*  440:1182 */         output.write(lineEnding.getBytes(encoding));
/*  441:     */       }
/*  442:     */     }
/*  443:     */   }
/*  444:     */   
/*  445:     */   public static void writeLines(Collection<?> lines, String lineEnding, Writer writer)
/*  446:     */     throws IOException
/*  447:     */   {
/*  448:1200 */     if (lines == null) {
/*  449:1201 */       return;
/*  450:     */     }
/*  451:1203 */     if (lineEnding == null) {
/*  452:1204 */       lineEnding = LINE_SEPARATOR;
/*  453:     */     }
/*  454:1206 */     for (Object line : lines)
/*  455:     */     {
/*  456:1207 */       if (line != null) {
/*  457:1208 */         writer.write(line.toString());
/*  458:     */       }
/*  459:1210 */       writer.write(lineEnding);
/*  460:     */     }
/*  461:     */   }
/*  462:     */   
/*  463:     */   public static int copy(InputStream input, OutputStream output)
/*  464:     */     throws IOException
/*  465:     */   {
/*  466:1236 */     long count = copyLarge(input, output);
/*  467:1237 */     if (count > 2147483647L) {
/*  468:1238 */       return -1;
/*  469:     */     }
/*  470:1240 */     return (int)count;
/*  471:     */   }
/*  472:     */   
/*  473:     */   public static long copyLarge(InputStream input, OutputStream output)
/*  474:     */     throws IOException
/*  475:     */   {
/*  476:1259 */     byte[] buffer = new byte[4096];
/*  477:1260 */     long count = 0L;
/*  478:1261 */     int n = 0;
/*  479:1262 */     while (-1 != (n = input.read(buffer)))
/*  480:     */     {
/*  481:1263 */       output.write(buffer, 0, n);
/*  482:1264 */       count += n;
/*  483:     */     }
/*  484:1266 */     return count;
/*  485:     */   }
/*  486:     */   
/*  487:     */   public static void copy(InputStream input, Writer output)
/*  488:     */     throws IOException
/*  489:     */   {
/*  490:1286 */     InputStreamReader in = new InputStreamReader(input);
/*  491:1287 */     copy(in, output);
/*  492:     */   }
/*  493:     */   
/*  494:     */   public static void copy(InputStream input, Writer output, String encoding)
/*  495:     */     throws IOException
/*  496:     */   {
/*  497:1311 */     if (encoding == null)
/*  498:     */     {
/*  499:1312 */       copy(input, output);
/*  500:     */     }
/*  501:     */     else
/*  502:     */     {
/*  503:1314 */       InputStreamReader in = new InputStreamReader(input, encoding);
/*  504:1315 */       copy(in, output);
/*  505:     */     }
/*  506:     */   }
/*  507:     */   
/*  508:     */   public static int copy(Reader input, Writer output)
/*  509:     */     throws IOException
/*  510:     */   {
/*  511:1340 */     long count = copyLarge(input, output);
/*  512:1341 */     if (count > 2147483647L) {
/*  513:1342 */       return -1;
/*  514:     */     }
/*  515:1344 */     return (int)count;
/*  516:     */   }
/*  517:     */   
/*  518:     */   public static long copyLarge(Reader input, Writer output)
/*  519:     */     throws IOException
/*  520:     */   {
/*  521:1361 */     char[] buffer = new char[4096];
/*  522:1362 */     long count = 0L;
/*  523:1363 */     int n = 0;
/*  524:1364 */     while (-1 != (n = input.read(buffer)))
/*  525:     */     {
/*  526:1365 */       output.write(buffer, 0, n);
/*  527:1366 */       count += n;
/*  528:     */     }
/*  529:1368 */     return count;
/*  530:     */   }
/*  531:     */   
/*  532:     */   public static void copy(Reader input, OutputStream output)
/*  533:     */     throws IOException
/*  534:     */   {
/*  535:1392 */     OutputStreamWriter out = new OutputStreamWriter(output);
/*  536:1393 */     copy(input, out);
/*  537:     */     
/*  538:     */ 
/*  539:1396 */     out.flush();
/*  540:     */   }
/*  541:     */   
/*  542:     */   public static void copy(Reader input, OutputStream output, String encoding)
/*  543:     */     throws IOException
/*  544:     */   {
/*  545:1424 */     if (encoding == null)
/*  546:     */     {
/*  547:1425 */       copy(input, output);
/*  548:     */     }
/*  549:     */     else
/*  550:     */     {
/*  551:1427 */       OutputStreamWriter out = new OutputStreamWriter(output, encoding);
/*  552:1428 */       copy(input, out);
/*  553:     */       
/*  554:     */ 
/*  555:1431 */       out.flush();
/*  556:     */     }
/*  557:     */   }
/*  558:     */   
/*  559:     */   public static boolean contentEquals(InputStream input1, InputStream input2)
/*  560:     */     throws IOException
/*  561:     */   {
/*  562:1453 */     if (!(input1 instanceof BufferedInputStream)) {
/*  563:1454 */       input1 = new BufferedInputStream(input1);
/*  564:     */     }
/*  565:1456 */     if (!(input2 instanceof BufferedInputStream)) {
/*  566:1457 */       input2 = new BufferedInputStream(input2);
/*  567:     */     }
/*  568:1460 */     int ch = input1.read();
/*  569:1461 */     while (-1 != ch)
/*  570:     */     {
/*  571:1462 */       int ch2 = input2.read();
/*  572:1463 */       if (ch != ch2) {
/*  573:1464 */         return false;
/*  574:     */       }
/*  575:1466 */       ch = input1.read();
/*  576:     */     }
/*  577:1469 */     int ch2 = input2.read();
/*  578:1470 */     return ch2 == -1;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public static boolean contentEquals(Reader input1, Reader input2)
/*  582:     */     throws IOException
/*  583:     */   {
/*  584:1490 */     if (!(input1 instanceof BufferedReader)) {
/*  585:1491 */       input1 = new BufferedReader(input1);
/*  586:     */     }
/*  587:1493 */     if (!(input2 instanceof BufferedReader)) {
/*  588:1494 */       input2 = new BufferedReader(input2);
/*  589:     */     }
/*  590:1497 */     int ch = input1.read();
/*  591:1498 */     while (-1 != ch)
/*  592:     */     {
/*  593:1499 */       int ch2 = input2.read();
/*  594:1500 */       if (ch != ch2) {
/*  595:1501 */         return false;
/*  596:     */       }
/*  597:1503 */       ch = input1.read();
/*  598:     */     }
/*  599:1506 */     int ch2 = input2.read();
/*  600:1507 */     return ch2 == -1;
/*  601:     */   }
/*  602:     */   
/*  603:     */   public static long skip(InputStream input, long toSkip)
/*  604:     */     throws IOException
/*  605:     */   {
/*  606:1527 */     if (toSkip < 0L) {
/*  607:1528 */       throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
/*  608:     */     }
/*  609:1537 */     if (SKIP_BYTE_BUFFER == null) {
/*  610:1538 */       SKIP_BYTE_BUFFER = new byte[2048];
/*  611:     */     }
/*  612:1540 */     long remain = toSkip;
/*  613:1541 */     while (remain > 0L)
/*  614:     */     {
/*  615:1542 */       long n = input.read(SKIP_BYTE_BUFFER, 0, (int)Math.min(remain, 2048L));
/*  616:1543 */       if (n < 0L) {
/*  617:     */         break;
/*  618:     */       }
/*  619:1546 */       remain -= n;
/*  620:     */     }
/*  621:1548 */     return toSkip - remain;
/*  622:     */   }
/*  623:     */   
/*  624:     */   public static long skip(Reader input, long toSkip)
/*  625:     */     throws IOException
/*  626:     */   {
/*  627:1568 */     if (toSkip < 0L) {
/*  628:1569 */       throw new IllegalArgumentException("Skip count must be non-negative, actual: " + toSkip);
/*  629:     */     }
/*  630:1578 */     if (SKIP_CHAR_BUFFER == null) {
/*  631:1579 */       SKIP_CHAR_BUFFER = new char[2048];
/*  632:     */     }
/*  633:1581 */     long remain = toSkip;
/*  634:1582 */     while (remain > 0L)
/*  635:     */     {
/*  636:1583 */       long n = input.read(SKIP_CHAR_BUFFER, 0, (int)Math.min(remain, 2048L));
/*  637:1584 */       if (n < 0L) {
/*  638:     */         break;
/*  639:     */       }
/*  640:1587 */       remain -= n;
/*  641:     */     }
/*  642:1589 */     return toSkip - remain;
/*  643:     */   }
/*  644:     */   
/*  645:     */   public static void skipFully(InputStream input, long toSkip)
/*  646:     */     throws IOException
/*  647:     */   {
/*  648:1608 */     if (toSkip < 0L) {
/*  649:1609 */       throw new IllegalArgumentException("Bytes to skip must not be negative: " + toSkip);
/*  650:     */     }
/*  651:1611 */     long skipped = skip(input, toSkip);
/*  652:1612 */     if (skipped != toSkip) {
/*  653:1613 */       throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
/*  654:     */     }
/*  655:     */   }
/*  656:     */   
/*  657:     */   public static void skipFully(Reader input, long toSkip)
/*  658:     */     throws IOException
/*  659:     */   {
/*  660:1633 */     long skipped = skip(input, toSkip);
/*  661:1634 */     if (skipped != toSkip) {
/*  662:1635 */       throw new EOFException("Bytes to skip: " + toSkip + " actual: " + skipped);
/*  663:     */     }
/*  664:     */   }
/*  665:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.IOUtils
 * JD-Core Version:    0.7.0.1
 */