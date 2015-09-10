/*    1:     */ package org.apache.commons.io;
/*    2:     */ 
/*    3:     */ import java.io.File;
/*    4:     */ import java.io.FileFilter;
/*    5:     */ import java.io.FileInputStream;
/*    6:     */ import java.io.FileNotFoundException;
/*    7:     */ import java.io.FileOutputStream;
/*    8:     */ import java.io.IOException;
/*    9:     */ import java.io.InputStream;
/*   10:     */ import java.io.OutputStream;
/*   11:     */ import java.net.URI;
/*   12:     */ import java.net.URL;
/*   13:     */ import java.net.URLConnection;
/*   14:     */ import java.nio.ByteBuffer;
/*   15:     */ import java.nio.CharBuffer;
/*   16:     */ import java.nio.channels.FileChannel;
/*   17:     */ import java.nio.charset.Charset;
/*   18:     */ import java.util.ArrayList;
/*   19:     */ import java.util.Collection;
/*   20:     */ import java.util.Date;
/*   21:     */ import java.util.Iterator;
/*   22:     */ import java.util.LinkedList;
/*   23:     */ import java.util.List;
/*   24:     */ import java.util.zip.CRC32;
/*   25:     */ import java.util.zip.CheckedInputStream;
/*   26:     */ import java.util.zip.Checksum;
/*   27:     */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*   28:     */ import org.apache.commons.io.filefilter.FalseFileFilter;
/*   29:     */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*   30:     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*   31:     */ import org.apache.commons.io.filefilter.SuffixFileFilter;
/*   32:     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*   33:     */ import org.apache.commons.io.output.NullOutputStream;
/*   34:     */ 
/*   35:     */ public class FileUtils
/*   36:     */ {
/*   37:     */   public static final long ONE_KB = 1024L;
/*   38:     */   public static final long ONE_MB = 1048576L;
/*   39:     */   private static final long FIFTY_MB = 52428800L;
/*   40:     */   public static final long ONE_GB = 1073741824L;
/*   41: 116 */   public static final File[] EMPTY_FILE_ARRAY = new File[0];
/*   42: 121 */   private static final Charset UTF8 = Charset.forName("UTF-8");
/*   43:     */   
/*   44:     */   public static String getTempDirectoryPath()
/*   45:     */   {
/*   46: 132 */     return System.getProperty("java.io.tmpdir");
/*   47:     */   }
/*   48:     */   
/*   49:     */   public static File getTempDirectory()
/*   50:     */   {
/*   51: 143 */     return new File(getTempDirectoryPath());
/*   52:     */   }
/*   53:     */   
/*   54:     */   public static String getUserDirectoryPath()
/*   55:     */   {
/*   56: 154 */     return System.getProperty("user.home");
/*   57:     */   }
/*   58:     */   
/*   59:     */   public static File getUserDirectory()
/*   60:     */   {
/*   61: 165 */     return new File(getUserDirectoryPath());
/*   62:     */   }
/*   63:     */   
/*   64:     */   public static FileInputStream openInputStream(File file)
/*   65:     */     throws IOException
/*   66:     */   {
/*   67: 188 */     if (file.exists())
/*   68:     */     {
/*   69: 189 */       if (file.isDirectory()) {
/*   70: 190 */         throw new IOException("File '" + file + "' exists but is a directory");
/*   71:     */       }
/*   72: 192 */       if (!file.canRead()) {
/*   73: 193 */         throw new IOException("File '" + file + "' cannot be read");
/*   74:     */       }
/*   75:     */     }
/*   76:     */     else
/*   77:     */     {
/*   78: 196 */       throw new FileNotFoundException("File '" + file + "' does not exist");
/*   79:     */     }
/*   80: 198 */     return new FileInputStream(file);
/*   81:     */   }
/*   82:     */   
/*   83:     */   public static FileOutputStream openOutputStream(File file)
/*   84:     */     throws IOException
/*   85:     */   {
/*   86: 223 */     if (file.exists())
/*   87:     */     {
/*   88: 224 */       if (file.isDirectory()) {
/*   89: 225 */         throw new IOException("File '" + file + "' exists but is a directory");
/*   90:     */       }
/*   91: 227 */       if (!file.canWrite()) {
/*   92: 228 */         throw new IOException("File '" + file + "' cannot be written to");
/*   93:     */       }
/*   94:     */     }
/*   95:     */     else
/*   96:     */     {
/*   97: 231 */       File parent = file.getParentFile();
/*   98: 232 */       if ((parent != null) && (!parent.exists()) && 
/*   99: 233 */         (!parent.mkdirs())) {
/*  100: 234 */         throw new IOException("File '" + file + "' could not be created");
/*  101:     */       }
/*  102:     */     }
/*  103: 238 */     return new FileOutputStream(file);
/*  104:     */   }
/*  105:     */   
/*  106:     */   public static String byteCountToDisplaySize(long size)
/*  107:     */   {
/*  108:     */     String displaySize;
/*  109:     */     String displaySize;
/*  110: 258 */     if (size / 1073741824L > 0L)
/*  111:     */     {
/*  112: 259 */       displaySize = String.valueOf(size / 1073741824L) + " GB";
/*  113:     */     }
/*  114:     */     else
/*  115:     */     {
/*  116:     */       String displaySize;
/*  117: 260 */       if (size / 1048576L > 0L)
/*  118:     */       {
/*  119: 261 */         displaySize = String.valueOf(size / 1048576L) + " MB";
/*  120:     */       }
/*  121:     */       else
/*  122:     */       {
/*  123:     */         String displaySize;
/*  124: 262 */         if (size / 1024L > 0L) {
/*  125: 263 */           displaySize = String.valueOf(size / 1024L) + " KB";
/*  126:     */         } else {
/*  127: 265 */           displaySize = String.valueOf(size) + " bytes";
/*  128:     */         }
/*  129:     */       }
/*  130:     */     }
/*  131: 267 */     return displaySize;
/*  132:     */   }
/*  133:     */   
/*  134:     */   public static void touch(File file)
/*  135:     */     throws IOException
/*  136:     */   {
/*  137: 284 */     if (!file.exists())
/*  138:     */     {
/*  139: 285 */       OutputStream out = openOutputStream(file);
/*  140: 286 */       IOUtils.closeQuietly(out);
/*  141:     */     }
/*  142: 288 */     boolean success = file.setLastModified(System.currentTimeMillis());
/*  143: 289 */     if (!success) {
/*  144: 290 */       throw new IOException("Unable to set the last modification time for " + file);
/*  145:     */     }
/*  146:     */   }
/*  147:     */   
/*  148:     */   public static File[] convertFileCollectionToFileArray(Collection<File> files)
/*  149:     */   {
/*  150: 304 */     return (File[])files.toArray(new File[files.size()]);
/*  151:     */   }
/*  152:     */   
/*  153:     */   private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter)
/*  154:     */   {
/*  155: 318 */     File[] found = directory.listFiles(filter);
/*  156: 319 */     if (found != null) {
/*  157: 320 */       for (File file : found) {
/*  158: 321 */         if (file.isDirectory()) {
/*  159: 322 */           innerListFiles(files, file, filter);
/*  160:     */         } else {
/*  161: 324 */           files.add(file);
/*  162:     */         }
/*  163:     */       }
/*  164:     */     }
/*  165:     */   }
/*  166:     */   
/*  167:     */   public static Collection<File> listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter)
/*  168:     */   {
/*  169: 357 */     if (!directory.isDirectory()) {
/*  170: 358 */       throw new IllegalArgumentException("Parameter 'directory' is not a directory");
/*  171:     */     }
/*  172: 361 */     if (fileFilter == null) {
/*  173: 362 */       throw new NullPointerException("Parameter 'fileFilter' is null");
/*  174:     */     }
/*  175: 366 */     IOFileFilter effFileFilter = FileFilterUtils.and(new IOFileFilter[] { fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE) });
/*  176:     */     IOFileFilter effDirFilter;
/*  177:     */     IOFileFilter effDirFilter;
/*  178: 371 */     if (dirFilter == null) {
/*  179: 372 */       effDirFilter = FalseFileFilter.INSTANCE;
/*  180:     */     } else {
/*  181: 374 */       effDirFilter = FileFilterUtils.and(new IOFileFilter[] { dirFilter, DirectoryFileFilter.INSTANCE });
/*  182:     */     }
/*  183: 379 */     Collection<File> files = new LinkedList();
/*  184: 380 */     innerListFiles(files, directory, FileFilterUtils.or(new IOFileFilter[] { effFileFilter, effDirFilter }));
/*  185:     */     
/*  186: 382 */     return files;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public static Iterator<File> iterateFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter)
/*  190:     */   {
/*  191: 404 */     return listFiles(directory, fileFilter, dirFilter).iterator();
/*  192:     */   }
/*  193:     */   
/*  194:     */   private static String[] toSuffixes(String[] extensions)
/*  195:     */   {
/*  196: 416 */     String[] suffixes = new String[extensions.length];
/*  197: 417 */     for (int i = 0; i < extensions.length; i++) {
/*  198: 418 */       suffixes[i] = ("." + extensions[i]);
/*  199:     */     }
/*  200: 420 */     return suffixes;
/*  201:     */   }
/*  202:     */   
/*  203:     */   public static Collection<File> listFiles(File directory, String[] extensions, boolean recursive)
/*  204:     */   {
/*  205:     */     IOFileFilter filter;
/*  206:     */     IOFileFilter filter;
/*  207: 437 */     if (extensions == null)
/*  208:     */     {
/*  209: 438 */       filter = TrueFileFilter.INSTANCE;
/*  210:     */     }
/*  211:     */     else
/*  212:     */     {
/*  213: 440 */       String[] suffixes = toSuffixes(extensions);
/*  214: 441 */       filter = new SuffixFileFilter(suffixes);
/*  215:     */     }
/*  216: 443 */     return listFiles(directory, filter, recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
/*  217:     */   }
/*  218:     */   
/*  219:     */   public static Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive)
/*  220:     */   {
/*  221: 461 */     return listFiles(directory, extensions, recursive).iterator();
/*  222:     */   }
/*  223:     */   
/*  224:     */   public static boolean contentEquals(File file1, File file2)
/*  225:     */     throws IOException
/*  226:     */   {
/*  227: 481 */     boolean file1Exists = file1.exists();
/*  228: 482 */     if (file1Exists != file2.exists()) {
/*  229: 483 */       return false;
/*  230:     */     }
/*  231: 486 */     if (!file1Exists) {
/*  232: 488 */       return true;
/*  233:     */     }
/*  234: 491 */     if ((file1.isDirectory()) || (file2.isDirectory())) {
/*  235: 493 */       throw new IOException("Can't compare directories, only files");
/*  236:     */     }
/*  237: 496 */     if (file1.length() != file2.length()) {
/*  238: 498 */       return false;
/*  239:     */     }
/*  240: 501 */     if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
/*  241: 503 */       return true;
/*  242:     */     }
/*  243: 506 */     InputStream input1 = null;
/*  244: 507 */     InputStream input2 = null;
/*  245:     */     try
/*  246:     */     {
/*  247: 509 */       input1 = new FileInputStream(file1);
/*  248: 510 */       input2 = new FileInputStream(file2);
/*  249: 511 */       return IOUtils.contentEquals(input1, input2);
/*  250:     */     }
/*  251:     */     finally
/*  252:     */     {
/*  253: 514 */       IOUtils.closeQuietly(input1);
/*  254: 515 */       IOUtils.closeQuietly(input2);
/*  255:     */     }
/*  256:     */   }
/*  257:     */   
/*  258:     */   public static File toFile(URL url)
/*  259:     */   {
/*  260: 535 */     if ((url == null) || (!"file".equalsIgnoreCase(url.getProtocol()))) {
/*  261: 536 */       return null;
/*  262:     */     }
/*  263: 538 */     String filename = url.getFile().replace('/', File.separatorChar);
/*  264: 539 */     filename = decodeUrl(filename);
/*  265: 540 */     return new File(filename);
/*  266:     */   }
/*  267:     */   
/*  268:     */   static String decodeUrl(String url)
/*  269:     */   {
/*  270: 559 */     String decoded = url;
/*  271: 560 */     if ((url != null) && (url.indexOf('%') >= 0))
/*  272:     */     {
/*  273: 561 */       int n = url.length();
/*  274: 562 */       StringBuffer buffer = new StringBuffer();
/*  275: 563 */       ByteBuffer bytes = ByteBuffer.allocate(n);
/*  276: 564 */       for (int i = 0; i < n;) {
/*  277: 565 */         if (url.charAt(i) == '%') {
/*  278:     */           try
/*  279:     */           {
/*  280:     */             do
/*  281:     */             {
/*  282: 568 */               byte octet = (byte)Integer.parseInt(url.substring(i + 1, i + 3), 16);
/*  283: 569 */               bytes.put(octet);
/*  284: 570 */               i += 3;
/*  285: 571 */             } while ((i < n) && (url.charAt(i) == '%'));
/*  286: 577 */             if (bytes.position() <= 0) {
/*  287:     */               continue;
/*  288:     */             }
/*  289: 578 */             bytes.flip();
/*  290: 579 */             buffer.append(UTF8.decode(bytes).toString());
/*  291: 580 */             bytes.clear(); continue;
/*  292:     */           }
/*  293:     */           catch (RuntimeException e) {}finally
/*  294:     */           {
/*  295: 577 */             if (bytes.position() > 0)
/*  296:     */             {
/*  297: 578 */               bytes.flip();
/*  298: 579 */               buffer.append(UTF8.decode(bytes).toString());
/*  299: 580 */               bytes.clear();
/*  300:     */             }
/*  301:     */           }
/*  302:     */         } else {
/*  303: 584 */           buffer.append(url.charAt(i++));
/*  304:     */         }
/*  305:     */       }
/*  306: 586 */       decoded = buffer.toString();
/*  307:     */     }
/*  308: 588 */     return decoded;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public static File[] toFiles(URL[] urls)
/*  312:     */   {
/*  313: 611 */     if ((urls == null) || (urls.length == 0)) {
/*  314: 612 */       return EMPTY_FILE_ARRAY;
/*  315:     */     }
/*  316: 614 */     File[] files = new File[urls.length];
/*  317: 615 */     for (int i = 0; i < urls.length; i++)
/*  318:     */     {
/*  319: 616 */       URL url = urls[i];
/*  320: 617 */       if (url != null)
/*  321:     */       {
/*  322: 618 */         if (!url.getProtocol().equals("file")) {
/*  323: 619 */           throw new IllegalArgumentException("URL could not be converted to a File: " + url);
/*  324:     */         }
/*  325: 622 */         files[i] = toFile(url);
/*  326:     */       }
/*  327:     */     }
/*  328: 625 */     return files;
/*  329:     */   }
/*  330:     */   
/*  331:     */   public static URL[] toURLs(File[] files)
/*  332:     */     throws IOException
/*  333:     */   {
/*  334: 638 */     URL[] urls = new URL[files.length];
/*  335: 640 */     for (int i = 0; i < urls.length; i++) {
/*  336: 641 */       urls[i] = files[i].toURI().toURL();
/*  337:     */     }
/*  338: 644 */     return urls;
/*  339:     */   }
/*  340:     */   
/*  341:     */   public static void copyFileToDirectory(File srcFile, File destDir)
/*  342:     */     throws IOException
/*  343:     */   {
/*  344: 670 */     copyFileToDirectory(srcFile, destDir, true);
/*  345:     */   }
/*  346:     */   
/*  347:     */   public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate)
/*  348:     */     throws IOException
/*  349:     */   {
/*  350: 699 */     if (destDir == null) {
/*  351: 700 */       throw new NullPointerException("Destination must not be null");
/*  352:     */     }
/*  353: 702 */     if ((destDir.exists()) && (!destDir.isDirectory())) {
/*  354: 703 */       throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
/*  355:     */     }
/*  356: 705 */     File destFile = new File(destDir, srcFile.getName());
/*  357: 706 */     copyFile(srcFile, destFile, preserveFileDate);
/*  358:     */   }
/*  359:     */   
/*  360:     */   public static void copyFile(File srcFile, File destFile)
/*  361:     */     throws IOException
/*  362:     */   {
/*  363: 731 */     copyFile(srcFile, destFile, true);
/*  364:     */   }
/*  365:     */   
/*  366:     */   public static void copyFile(File srcFile, File destFile, boolean preserveFileDate)
/*  367:     */     throws IOException
/*  368:     */   {
/*  369: 760 */     if (srcFile == null) {
/*  370: 761 */       throw new NullPointerException("Source must not be null");
/*  371:     */     }
/*  372: 763 */     if (destFile == null) {
/*  373: 764 */       throw new NullPointerException("Destination must not be null");
/*  374:     */     }
/*  375: 766 */     if (!srcFile.exists()) {
/*  376: 767 */       throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
/*  377:     */     }
/*  378: 769 */     if (srcFile.isDirectory()) {
/*  379: 770 */       throw new IOException("Source '" + srcFile + "' exists but is a directory");
/*  380:     */     }
/*  381: 772 */     if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
/*  382: 773 */       throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
/*  383:     */     }
/*  384: 775 */     if ((destFile.getParentFile() != null) && (!destFile.getParentFile().exists()) && 
/*  385: 776 */       (!destFile.getParentFile().mkdirs())) {
/*  386: 777 */       throw new IOException("Destination '" + destFile + "' directory cannot be created");
/*  387:     */     }
/*  388: 780 */     if ((destFile.exists()) && (!destFile.canWrite())) {
/*  389: 781 */       throw new IOException("Destination '" + destFile + "' exists but is read-only");
/*  390:     */     }
/*  391: 783 */     doCopyFile(srcFile, destFile, preserveFileDate);
/*  392:     */   }
/*  393:     */   
/*  394:     */   private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate)
/*  395:     */     throws IOException
/*  396:     */   {
/*  397: 795 */     if ((destFile.exists()) && (destFile.isDirectory())) {
/*  398: 796 */       throw new IOException("Destination '" + destFile + "' exists but is a directory");
/*  399:     */     }
/*  400: 799 */     FileInputStream fis = null;
/*  401: 800 */     FileOutputStream fos = null;
/*  402: 801 */     FileChannel input = null;
/*  403: 802 */     FileChannel output = null;
/*  404:     */     try
/*  405:     */     {
/*  406: 804 */       fis = new FileInputStream(srcFile);
/*  407: 805 */       fos = new FileOutputStream(destFile);
/*  408: 806 */       input = fis.getChannel();
/*  409: 807 */       output = fos.getChannel();
/*  410: 808 */       long size = input.size();
/*  411: 809 */       long pos = 0L;
/*  412: 810 */       long count = 0L;
/*  413: 811 */       while (pos < size)
/*  414:     */       {
/*  415: 812 */         count = size - pos > 52428800L ? 52428800L : size - pos;
/*  416: 813 */         pos += output.transferFrom(input, pos, count);
/*  417:     */       }
/*  418:     */     }
/*  419:     */     finally
/*  420:     */     {
/*  421: 816 */       IOUtils.closeQuietly(output);
/*  422: 817 */       IOUtils.closeQuietly(fos);
/*  423: 818 */       IOUtils.closeQuietly(input);
/*  424: 819 */       IOUtils.closeQuietly(fis);
/*  425:     */     }
/*  426: 822 */     if (srcFile.length() != destFile.length()) {
/*  427: 823 */       throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
/*  428:     */     }
/*  429: 826 */     if (preserveFileDate) {
/*  430: 827 */       destFile.setLastModified(srcFile.lastModified());
/*  431:     */     }
/*  432:     */   }
/*  433:     */   
/*  434:     */   public static void copyDirectoryToDirectory(File srcDir, File destDir)
/*  435:     */     throws IOException
/*  436:     */   {
/*  437: 856 */     if (srcDir == null) {
/*  438: 857 */       throw new NullPointerException("Source must not be null");
/*  439:     */     }
/*  440: 859 */     if ((srcDir.exists()) && (!srcDir.isDirectory())) {
/*  441: 860 */       throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
/*  442:     */     }
/*  443: 862 */     if (destDir == null) {
/*  444: 863 */       throw new NullPointerException("Destination must not be null");
/*  445:     */     }
/*  446: 865 */     if ((destDir.exists()) && (!destDir.isDirectory())) {
/*  447: 866 */       throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
/*  448:     */     }
/*  449: 868 */     copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
/*  450:     */   }
/*  451:     */   
/*  452:     */   public static void copyDirectory(File srcDir, File destDir)
/*  453:     */     throws IOException
/*  454:     */   {
/*  455: 896 */     copyDirectory(srcDir, destDir, true);
/*  456:     */   }
/*  457:     */   
/*  458:     */   public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate)
/*  459:     */     throws IOException
/*  460:     */   {
/*  461: 927 */     copyDirectory(srcDir, destDir, null, preserveFileDate);
/*  462:     */   }
/*  463:     */   
/*  464:     */   public static void copyDirectory(File srcDir, File destDir, FileFilter filter)
/*  465:     */     throws IOException
/*  466:     */   {
/*  467: 976 */     copyDirectory(srcDir, destDir, filter, true);
/*  468:     */   }
/*  469:     */   
/*  470:     */   public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate)
/*  471:     */     throws IOException
/*  472:     */   {
/*  473:1027 */     if (srcDir == null) {
/*  474:1028 */       throw new NullPointerException("Source must not be null");
/*  475:     */     }
/*  476:1030 */     if (destDir == null) {
/*  477:1031 */       throw new NullPointerException("Destination must not be null");
/*  478:     */     }
/*  479:1033 */     if (!srcDir.exists()) {
/*  480:1034 */       throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
/*  481:     */     }
/*  482:1036 */     if (!srcDir.isDirectory()) {
/*  483:1037 */       throw new IOException("Source '" + srcDir + "' exists but is not a directory");
/*  484:     */     }
/*  485:1039 */     if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
/*  486:1040 */       throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
/*  487:     */     }
/*  488:1044 */     List<String> exclusionList = null;
/*  489:1045 */     if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath()))
/*  490:     */     {
/*  491:1046 */       File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
/*  492:1047 */       if ((srcFiles != null) && (srcFiles.length > 0))
/*  493:     */       {
/*  494:1048 */         exclusionList = new ArrayList(srcFiles.length);
/*  495:1049 */         for (File srcFile : srcFiles)
/*  496:     */         {
/*  497:1050 */           File copiedFile = new File(destDir, srcFile.getName());
/*  498:1051 */           exclusionList.add(copiedFile.getCanonicalPath());
/*  499:     */         }
/*  500:     */       }
/*  501:     */     }
/*  502:1055 */     doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
/*  503:     */   }
/*  504:     */   
/*  505:     */   private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate, List<String> exclusionList)
/*  506:     */     throws IOException
/*  507:     */   {
/*  508:1072 */     File[] files = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
/*  509:1073 */     if (files == null) {
/*  510:1074 */       throw new IOException("Failed to list contents of " + srcDir);
/*  511:     */     }
/*  512:1076 */     if (destDir.exists())
/*  513:     */     {
/*  514:1077 */       if (!destDir.isDirectory()) {
/*  515:1078 */         throw new IOException("Destination '" + destDir + "' exists but is not a directory");
/*  516:     */       }
/*  517:     */     }
/*  518:1081 */     else if (!destDir.mkdirs()) {
/*  519:1082 */       throw new IOException("Destination '" + destDir + "' directory cannot be created");
/*  520:     */     }
/*  521:1085 */     if (!destDir.canWrite()) {
/*  522:1086 */       throw new IOException("Destination '" + destDir + "' cannot be written to");
/*  523:     */     }
/*  524:1088 */     for (File file : files)
/*  525:     */     {
/*  526:1089 */       File copiedFile = new File(destDir, file.getName());
/*  527:1090 */       if ((exclusionList == null) || (!exclusionList.contains(file.getCanonicalPath()))) {
/*  528:1091 */         if (file.isDirectory()) {
/*  529:1092 */           doCopyDirectory(file, copiedFile, filter, preserveFileDate, exclusionList);
/*  530:     */         } else {
/*  531:1094 */           doCopyFile(file, copiedFile, preserveFileDate);
/*  532:     */         }
/*  533:     */       }
/*  534:     */     }
/*  535:1100 */     if (preserveFileDate) {
/*  536:1101 */       destDir.setLastModified(srcDir.lastModified());
/*  537:     */     }
/*  538:     */   }
/*  539:     */   
/*  540:     */   public static void copyURLToFile(URL source, File destination)
/*  541:     */     throws IOException
/*  542:     */   {
/*  543:1126 */     InputStream input = source.openStream();
/*  544:1127 */     copyInputStreamToFile(input, destination);
/*  545:     */   }
/*  546:     */   
/*  547:     */   public static void copyURLToFile(URL source, File destination, int connectionTimeout, int readTimeout)
/*  548:     */     throws IOException
/*  549:     */   {
/*  550:1152 */     URLConnection connection = source.openConnection();
/*  551:1153 */     connection.setConnectTimeout(connectionTimeout);
/*  552:1154 */     connection.setReadTimeout(readTimeout);
/*  553:1155 */     InputStream input = connection.getInputStream();
/*  554:1156 */     copyInputStreamToFile(input, destination);
/*  555:     */   }
/*  556:     */   
/*  557:     */   public static void copyInputStreamToFile(InputStream source, File destination)
/*  558:     */     throws IOException
/*  559:     */   {
/*  560:     */     try
/*  561:     */     {
/*  562:1176 */       FileOutputStream output = openOutputStream(destination);
/*  563:     */       try
/*  564:     */       {
/*  565:1178 */         IOUtils.copy(source, output);
/*  566:     */       }
/*  567:     */       finally {}
/*  568:     */     }
/*  569:     */     finally
/*  570:     */     {
/*  571:1183 */       IOUtils.closeQuietly(source);
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   public static void deleteDirectory(File directory)
/*  576:     */     throws IOException
/*  577:     */   {
/*  578:1195 */     if (!directory.exists()) {
/*  579:1196 */       return;
/*  580:     */     }
/*  581:1199 */     if (!isSymlink(directory)) {
/*  582:1200 */       cleanDirectory(directory);
/*  583:     */     }
/*  584:1203 */     if (!directory.delete())
/*  585:     */     {
/*  586:1204 */       String message = "Unable to delete directory " + directory + ".";
/*  587:     */       
/*  588:1206 */       throw new IOException(message);
/*  589:     */     }
/*  590:     */   }
/*  591:     */   
/*  592:     */   public static boolean deleteQuietly(File file)
/*  593:     */   {
/*  594:1226 */     if (file == null) {
/*  595:1227 */       return false;
/*  596:     */     }
/*  597:     */     try
/*  598:     */     {
/*  599:1230 */       if (file.isDirectory()) {
/*  600:1231 */         cleanDirectory(file);
/*  601:     */       }
/*  602:     */     }
/*  603:     */     catch (Exception ignored) {}
/*  604:     */     try
/*  605:     */     {
/*  606:1237 */       return file.delete();
/*  607:     */     }
/*  608:     */     catch (Exception ignored) {}
/*  609:1239 */     return false;
/*  610:     */   }
/*  611:     */   
/*  612:     */   public static void cleanDirectory(File directory)
/*  613:     */     throws IOException
/*  614:     */   {
/*  615:1250 */     if (!directory.exists())
/*  616:     */     {
/*  617:1251 */       String message = directory + " does not exist";
/*  618:1252 */       throw new IllegalArgumentException(message);
/*  619:     */     }
/*  620:1255 */     if (!directory.isDirectory())
/*  621:     */     {
/*  622:1256 */       String message = directory + " is not a directory";
/*  623:1257 */       throw new IllegalArgumentException(message);
/*  624:     */     }
/*  625:1260 */     File[] files = directory.listFiles();
/*  626:1261 */     if (files == null) {
/*  627:1262 */       throw new IOException("Failed to list contents of " + directory);
/*  628:     */     }
/*  629:1265 */     IOException exception = null;
/*  630:1266 */     for (File file : files) {
/*  631:     */       try
/*  632:     */       {
/*  633:1268 */         forceDelete(file);
/*  634:     */       }
/*  635:     */       catch (IOException ioe)
/*  636:     */       {
/*  637:1270 */         exception = ioe;
/*  638:     */       }
/*  639:     */     }
/*  640:1274 */     if (null != exception) {
/*  641:1275 */       throw exception;
/*  642:     */     }
/*  643:     */   }
/*  644:     */   
/*  645:     */   public static boolean waitFor(File file, int seconds)
/*  646:     */   {
/*  647:1292 */     int timeout = 0;
/*  648:1293 */     int tick = 0;
/*  649:     */     for (;;)
/*  650:     */     {
/*  651:1294 */       if (!file.exists())
/*  652:     */       {
/*  653:1295 */         if (tick++ >= 10)
/*  654:     */         {
/*  655:1296 */           tick = 0;
/*  656:1297 */           if (timeout++ > seconds) {
/*  657:1298 */             return false;
/*  658:     */           }
/*  659:     */         }
/*  660:     */         try
/*  661:     */         {
/*  662:1302 */           Thread.sleep(100L);
/*  663:     */         }
/*  664:     */         catch (InterruptedException ignore) {}catch (Exception ex) {}
/*  665:     */       }
/*  666:     */     }
/*  667:1309 */     return true;
/*  668:     */   }
/*  669:     */   
/*  670:     */   public static String readFileToString(File file, String encoding)
/*  671:     */     throws IOException
/*  672:     */   {
/*  673:1324 */     InputStream in = null;
/*  674:     */     try
/*  675:     */     {
/*  676:1326 */       in = openInputStream(file);
/*  677:1327 */       return IOUtils.toString(in, encoding);
/*  678:     */     }
/*  679:     */     finally
/*  680:     */     {
/*  681:1329 */       IOUtils.closeQuietly(in);
/*  682:     */     }
/*  683:     */   }
/*  684:     */   
/*  685:     */   public static String readFileToString(File file)
/*  686:     */     throws IOException
/*  687:     */   {
/*  688:1344 */     return readFileToString(file, null);
/*  689:     */   }
/*  690:     */   
/*  691:     */   public static byte[] readFileToByteArray(File file)
/*  692:     */     throws IOException
/*  693:     */   {
/*  694:1357 */     InputStream in = null;
/*  695:     */     try
/*  696:     */     {
/*  697:1359 */       in = openInputStream(file);
/*  698:1360 */       return IOUtils.toByteArray(in);
/*  699:     */     }
/*  700:     */     finally
/*  701:     */     {
/*  702:1362 */       IOUtils.closeQuietly(in);
/*  703:     */     }
/*  704:     */   }
/*  705:     */   
/*  706:     */   public static List<String> readLines(File file, String encoding)
/*  707:     */     throws IOException
/*  708:     */   {
/*  709:1378 */     InputStream in = null;
/*  710:     */     try
/*  711:     */     {
/*  712:1380 */       in = openInputStream(file);
/*  713:1381 */       return IOUtils.readLines(in, encoding);
/*  714:     */     }
/*  715:     */     finally
/*  716:     */     {
/*  717:1383 */       IOUtils.closeQuietly(in);
/*  718:     */     }
/*  719:     */   }
/*  720:     */   
/*  721:     */   public static List<String> readLines(File file)
/*  722:     */     throws IOException
/*  723:     */   {
/*  724:1397 */     return readLines(file, null);
/*  725:     */   }
/*  726:     */   
/*  727:     */   public static LineIterator lineIterator(File file, String encoding)
/*  728:     */     throws IOException
/*  729:     */   {
/*  730:1432 */     InputStream in = null;
/*  731:     */     try
/*  732:     */     {
/*  733:1434 */       in = openInputStream(file);
/*  734:1435 */       return IOUtils.lineIterator(in, encoding);
/*  735:     */     }
/*  736:     */     catch (IOException ex)
/*  737:     */     {
/*  738:1437 */       IOUtils.closeQuietly(in);
/*  739:1438 */       throw ex;
/*  740:     */     }
/*  741:     */     catch (RuntimeException ex)
/*  742:     */     {
/*  743:1440 */       IOUtils.closeQuietly(in);
/*  744:1441 */       throw ex;
/*  745:     */     }
/*  746:     */   }
/*  747:     */   
/*  748:     */   public static LineIterator lineIterator(File file)
/*  749:     */     throws IOException
/*  750:     */   {
/*  751:1455 */     return lineIterator(file, null);
/*  752:     */   }
/*  753:     */   
/*  754:     */   public static void writeStringToFile(File file, String data, String encoding)
/*  755:     */     throws IOException
/*  756:     */   {
/*  757:1472 */     OutputStream out = null;
/*  758:     */     try
/*  759:     */     {
/*  760:1474 */       out = openOutputStream(file);
/*  761:1475 */       IOUtils.write(data, out, encoding);
/*  762:     */     }
/*  763:     */     finally
/*  764:     */     {
/*  765:1477 */       IOUtils.closeQuietly(out);
/*  766:     */     }
/*  767:     */   }
/*  768:     */   
/*  769:     */   public static void writeStringToFile(File file, String data)
/*  770:     */     throws IOException
/*  771:     */   {
/*  772:1489 */     writeStringToFile(file, data, null);
/*  773:     */   }
/*  774:     */   
/*  775:     */   public static void write(File file, CharSequence data)
/*  776:     */     throws IOException
/*  777:     */   {
/*  778:1501 */     String str = data == null ? null : data.toString();
/*  779:1502 */     writeStringToFile(file, str);
/*  780:     */   }
/*  781:     */   
/*  782:     */   public static void write(File file, CharSequence data, String encoding)
/*  783:     */     throws IOException
/*  784:     */   {
/*  785:1516 */     String str = data == null ? null : data.toString();
/*  786:1517 */     writeStringToFile(file, str, encoding);
/*  787:     */   }
/*  788:     */   
/*  789:     */   public static void writeByteArrayToFile(File file, byte[] data)
/*  790:     */     throws IOException
/*  791:     */   {
/*  792:1532 */     OutputStream out = null;
/*  793:     */     try
/*  794:     */     {
/*  795:1534 */       out = openOutputStream(file);
/*  796:1535 */       out.write(data);
/*  797:     */     }
/*  798:     */     finally
/*  799:     */     {
/*  800:1537 */       IOUtils.closeQuietly(out);
/*  801:     */     }
/*  802:     */   }
/*  803:     */   
/*  804:     */   public static void writeLines(File file, String encoding, Collection<?> lines)
/*  805:     */     throws IOException
/*  806:     */   {
/*  807:1557 */     writeLines(file, encoding, lines, null);
/*  808:     */   }
/*  809:     */   
/*  810:     */   public static void writeLines(File file, Collection<?> lines)
/*  811:     */     throws IOException
/*  812:     */   {
/*  813:1571 */     writeLines(file, null, lines, null);
/*  814:     */   }
/*  815:     */   
/*  816:     */   public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding)
/*  817:     */     throws IOException
/*  818:     */   {
/*  819:1592 */     OutputStream out = null;
/*  820:     */     try
/*  821:     */     {
/*  822:1594 */       out = openOutputStream(file);
/*  823:1595 */       IOUtils.writeLines(lines, lineEnding, out, encoding);
/*  824:     */     }
/*  825:     */     finally
/*  826:     */     {
/*  827:1597 */       IOUtils.closeQuietly(out);
/*  828:     */     }
/*  829:     */   }
/*  830:     */   
/*  831:     */   public static void writeLines(File file, Collection<?> lines, String lineEnding)
/*  832:     */     throws IOException
/*  833:     */   {
/*  834:1613 */     writeLines(file, null, lines, lineEnding);
/*  835:     */   }
/*  836:     */   
/*  837:     */   public static void forceDelete(File file)
/*  838:     */     throws IOException
/*  839:     */   {
/*  840:1633 */     if (file.isDirectory())
/*  841:     */     {
/*  842:1634 */       deleteDirectory(file);
/*  843:     */     }
/*  844:     */     else
/*  845:     */     {
/*  846:1636 */       boolean filePresent = file.exists();
/*  847:1637 */       if (!file.delete())
/*  848:     */       {
/*  849:1638 */         if (!filePresent) {
/*  850:1639 */           throw new FileNotFoundException("File does not exist: " + file);
/*  851:     */         }
/*  852:1641 */         String message = "Unable to delete file: " + file;
/*  853:     */         
/*  854:1643 */         throw new IOException(message);
/*  855:     */       }
/*  856:     */     }
/*  857:     */   }
/*  858:     */   
/*  859:     */   public static void forceDeleteOnExit(File file)
/*  860:     */     throws IOException
/*  861:     */   {
/*  862:1657 */     if (file.isDirectory()) {
/*  863:1658 */       deleteDirectoryOnExit(file);
/*  864:     */     } else {
/*  865:1660 */       file.deleteOnExit();
/*  866:     */     }
/*  867:     */   }
/*  868:     */   
/*  869:     */   private static void deleteDirectoryOnExit(File directory)
/*  870:     */     throws IOException
/*  871:     */   {
/*  872:1672 */     if (!directory.exists()) {
/*  873:1673 */       return;
/*  874:     */     }
/*  875:1676 */     if (!isSymlink(directory)) {
/*  876:1677 */       cleanDirectoryOnExit(directory);
/*  877:     */     }
/*  878:1679 */     directory.deleteOnExit();
/*  879:     */   }
/*  880:     */   
/*  881:     */   private static void cleanDirectoryOnExit(File directory)
/*  882:     */     throws IOException
/*  883:     */   {
/*  884:1690 */     if (!directory.exists())
/*  885:     */     {
/*  886:1691 */       String message = directory + " does not exist";
/*  887:1692 */       throw new IllegalArgumentException(message);
/*  888:     */     }
/*  889:1695 */     if (!directory.isDirectory())
/*  890:     */     {
/*  891:1696 */       String message = directory + " is not a directory";
/*  892:1697 */       throw new IllegalArgumentException(message);
/*  893:     */     }
/*  894:1700 */     File[] files = directory.listFiles();
/*  895:1701 */     if (files == null) {
/*  896:1702 */       throw new IOException("Failed to list contents of " + directory);
/*  897:     */     }
/*  898:1705 */     IOException exception = null;
/*  899:1706 */     for (File file : files) {
/*  900:     */       try
/*  901:     */       {
/*  902:1708 */         forceDeleteOnExit(file);
/*  903:     */       }
/*  904:     */       catch (IOException ioe)
/*  905:     */       {
/*  906:1710 */         exception = ioe;
/*  907:     */       }
/*  908:     */     }
/*  909:1714 */     if (null != exception) {
/*  910:1715 */       throw exception;
/*  911:     */     }
/*  912:     */   }
/*  913:     */   
/*  914:     */   public static void forceMkdir(File directory)
/*  915:     */     throws IOException
/*  916:     */   {
/*  917:1731 */     if (directory.exists())
/*  918:     */     {
/*  919:1732 */       if (!directory.isDirectory())
/*  920:     */       {
/*  921:1733 */         String message = "File " + directory + " exists and is " + "not a directory. Unable to create directory.";
/*  922:     */         
/*  923:     */ 
/*  924:     */ 
/*  925:     */ 
/*  926:1738 */         throw new IOException(message);
/*  927:     */       }
/*  928:     */     }
/*  929:1741 */     else if (!directory.mkdirs()) {
/*  930:1744 */       if (!directory.isDirectory())
/*  931:     */       {
/*  932:1746 */         String message = "Unable to create directory " + directory;
/*  933:     */         
/*  934:1748 */         throw new IOException(message);
/*  935:     */       }
/*  936:     */     }
/*  937:     */   }
/*  938:     */   
/*  939:     */   public static long sizeOf(File file)
/*  940:     */   {
/*  941:1775 */     if (!file.exists())
/*  942:     */     {
/*  943:1776 */       String message = file + " does not exist";
/*  944:1777 */       throw new IllegalArgumentException(message);
/*  945:     */     }
/*  946:1780 */     if (file.isDirectory()) {
/*  947:1781 */       return sizeOfDirectory(file);
/*  948:     */     }
/*  949:1783 */     return file.length();
/*  950:     */   }
/*  951:     */   
/*  952:     */   public static long sizeOfDirectory(File directory)
/*  953:     */   {
/*  954:1796 */     if (!directory.exists())
/*  955:     */     {
/*  956:1797 */       String message = directory + " does not exist";
/*  957:1798 */       throw new IllegalArgumentException(message);
/*  958:     */     }
/*  959:1801 */     if (!directory.isDirectory())
/*  960:     */     {
/*  961:1802 */       String message = directory + " is not a directory";
/*  962:1803 */       throw new IllegalArgumentException(message);
/*  963:     */     }
/*  964:1806 */     long size = 0L;
/*  965:     */     
/*  966:1808 */     File[] files = directory.listFiles();
/*  967:1809 */     if (files == null) {
/*  968:1810 */       return 0L;
/*  969:     */     }
/*  970:1812 */     for (File file : files) {
/*  971:1813 */       size += sizeOf(file);
/*  972:     */     }
/*  973:1816 */     return size;
/*  974:     */   }
/*  975:     */   
/*  976:     */   public static boolean isFileNewer(File file, File reference)
/*  977:     */   {
/*  978:1834 */     if (reference == null) {
/*  979:1835 */       throw new IllegalArgumentException("No specified reference file");
/*  980:     */     }
/*  981:1837 */     if (!reference.exists()) {
/*  982:1838 */       throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
/*  983:     */     }
/*  984:1841 */     return isFileNewer(file, reference.lastModified());
/*  985:     */   }
/*  986:     */   
/*  987:     */   public static boolean isFileNewer(File file, Date date)
/*  988:     */   {
/*  989:1857 */     if (date == null) {
/*  990:1858 */       throw new IllegalArgumentException("No specified date");
/*  991:     */     }
/*  992:1860 */     return isFileNewer(file, date.getTime());
/*  993:     */   }
/*  994:     */   
/*  995:     */   public static boolean isFileNewer(File file, long timeMillis)
/*  996:     */   {
/*  997:1876 */     if (file == null) {
/*  998:1877 */       throw new IllegalArgumentException("No specified file");
/*  999:     */     }
/* 1000:1879 */     if (!file.exists()) {
/* 1001:1880 */       return false;
/* 1002:     */     }
/* 1003:1882 */     return file.lastModified() > timeMillis;
/* 1004:     */   }
/* 1005:     */   
/* 1006:     */   public static boolean isFileOlder(File file, File reference)
/* 1007:     */   {
/* 1008:1901 */     if (reference == null) {
/* 1009:1902 */       throw new IllegalArgumentException("No specified reference file");
/* 1010:     */     }
/* 1011:1904 */     if (!reference.exists()) {
/* 1012:1905 */       throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
/* 1013:     */     }
/* 1014:1908 */     return isFileOlder(file, reference.lastModified());
/* 1015:     */   }
/* 1016:     */   
/* 1017:     */   public static boolean isFileOlder(File file, Date date)
/* 1018:     */   {
/* 1019:1924 */     if (date == null) {
/* 1020:1925 */       throw new IllegalArgumentException("No specified date");
/* 1021:     */     }
/* 1022:1927 */     return isFileOlder(file, date.getTime());
/* 1023:     */   }
/* 1024:     */   
/* 1025:     */   public static boolean isFileOlder(File file, long timeMillis)
/* 1026:     */   {
/* 1027:1943 */     if (file == null) {
/* 1028:1944 */       throw new IllegalArgumentException("No specified file");
/* 1029:     */     }
/* 1030:1946 */     if (!file.exists()) {
/* 1031:1947 */       return false;
/* 1032:     */     }
/* 1033:1949 */     return file.lastModified() < timeMillis;
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public static long checksumCRC32(File file)
/* 1037:     */     throws IOException
/* 1038:     */   {
/* 1039:1965 */     CRC32 crc = new CRC32();
/* 1040:1966 */     checksum(file, crc);
/* 1041:1967 */     return crc.getValue();
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public static Checksum checksum(File file, Checksum checksum)
/* 1045:     */     throws IOException
/* 1046:     */   {
/* 1047:1988 */     if (file.isDirectory()) {
/* 1048:1989 */       throw new IllegalArgumentException("Checksums can't be computed on directories");
/* 1049:     */     }
/* 1050:1991 */     InputStream in = null;
/* 1051:     */     try
/* 1052:     */     {
/* 1053:1993 */       in = new CheckedInputStream(new FileInputStream(file), checksum);
/* 1054:1994 */       IOUtils.copy(in, new NullOutputStream());
/* 1055:     */     }
/* 1056:     */     finally
/* 1057:     */     {
/* 1058:1996 */       IOUtils.closeQuietly(in);
/* 1059:     */     }
/* 1060:1998 */     return checksum;
/* 1061:     */   }
/* 1062:     */   
/* 1063:     */   public static void moveDirectory(File srcDir, File destDir)
/* 1064:     */     throws IOException
/* 1065:     */   {
/* 1066:2014 */     if (srcDir == null) {
/* 1067:2015 */       throw new NullPointerException("Source must not be null");
/* 1068:     */     }
/* 1069:2017 */     if (destDir == null) {
/* 1070:2018 */       throw new NullPointerException("Destination must not be null");
/* 1071:     */     }
/* 1072:2020 */     if (!srcDir.exists()) {
/* 1073:2021 */       throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
/* 1074:     */     }
/* 1075:2023 */     if (!srcDir.isDirectory()) {
/* 1076:2024 */       throw new IOException("Source '" + srcDir + "' is not a directory");
/* 1077:     */     }
/* 1078:2026 */     if (destDir.exists()) {
/* 1079:2027 */       throw new FileExistsException("Destination '" + destDir + "' already exists");
/* 1080:     */     }
/* 1081:2029 */     boolean rename = srcDir.renameTo(destDir);
/* 1082:2030 */     if (!rename)
/* 1083:     */     {
/* 1084:2031 */       copyDirectory(srcDir, destDir);
/* 1085:2032 */       deleteDirectory(srcDir);
/* 1086:2033 */       if (srcDir.exists()) {
/* 1087:2034 */         throw new IOException("Failed to delete original directory '" + srcDir + "' after copy to '" + destDir + "'");
/* 1088:     */       }
/* 1089:     */     }
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   public static void moveDirectoryToDirectory(File src, File destDir, boolean createDestDir)
/* 1093:     */     throws IOException
/* 1094:     */   {
/* 1095:2053 */     if (src == null) {
/* 1096:2054 */       throw new NullPointerException("Source must not be null");
/* 1097:     */     }
/* 1098:2056 */     if (destDir == null) {
/* 1099:2057 */       throw new NullPointerException("Destination directory must not be null");
/* 1100:     */     }
/* 1101:2059 */     if ((!destDir.exists()) && (createDestDir)) {
/* 1102:2060 */       destDir.mkdirs();
/* 1103:     */     }
/* 1104:2062 */     if (!destDir.exists()) {
/* 1105:2063 */       throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
/* 1106:     */     }
/* 1107:2066 */     if (!destDir.isDirectory()) {
/* 1108:2067 */       throw new IOException("Destination '" + destDir + "' is not a directory");
/* 1109:     */     }
/* 1110:2069 */     moveDirectory(src, new File(destDir, src.getName()));
/* 1111:     */   }
/* 1112:     */   
/* 1113:     */   public static void moveFile(File srcFile, File destFile)
/* 1114:     */     throws IOException
/* 1115:     */   {
/* 1116:2086 */     if (srcFile == null) {
/* 1117:2087 */       throw new NullPointerException("Source must not be null");
/* 1118:     */     }
/* 1119:2089 */     if (destFile == null) {
/* 1120:2090 */       throw new NullPointerException("Destination must not be null");
/* 1121:     */     }
/* 1122:2092 */     if (!srcFile.exists()) {
/* 1123:2093 */       throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
/* 1124:     */     }
/* 1125:2095 */     if (srcFile.isDirectory()) {
/* 1126:2096 */       throw new IOException("Source '" + srcFile + "' is a directory");
/* 1127:     */     }
/* 1128:2098 */     if (destFile.exists()) {
/* 1129:2099 */       throw new FileExistsException("Destination '" + destFile + "' already exists");
/* 1130:     */     }
/* 1131:2101 */     if (destFile.isDirectory()) {
/* 1132:2102 */       throw new IOException("Destination '" + destFile + "' is a directory");
/* 1133:     */     }
/* 1134:2104 */     boolean rename = srcFile.renameTo(destFile);
/* 1135:2105 */     if (!rename)
/* 1136:     */     {
/* 1137:2106 */       copyFile(srcFile, destFile);
/* 1138:2107 */       if (!srcFile.delete())
/* 1139:     */       {
/* 1140:2108 */         deleteQuietly(destFile);
/* 1141:2109 */         throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
/* 1142:     */       }
/* 1143:     */     }
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir)
/* 1147:     */     throws IOException
/* 1148:     */   {
/* 1149:2128 */     if (srcFile == null) {
/* 1150:2129 */       throw new NullPointerException("Source must not be null");
/* 1151:     */     }
/* 1152:2131 */     if (destDir == null) {
/* 1153:2132 */       throw new NullPointerException("Destination directory must not be null");
/* 1154:     */     }
/* 1155:2134 */     if ((!destDir.exists()) && (createDestDir)) {
/* 1156:2135 */       destDir.mkdirs();
/* 1157:     */     }
/* 1158:2137 */     if (!destDir.exists()) {
/* 1159:2138 */       throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
/* 1160:     */     }
/* 1161:2141 */     if (!destDir.isDirectory()) {
/* 1162:2142 */       throw new IOException("Destination '" + destDir + "' is not a directory");
/* 1163:     */     }
/* 1164:2144 */     moveFile(srcFile, new File(destDir, srcFile.getName()));
/* 1165:     */   }
/* 1166:     */   
/* 1167:     */   public static void moveToDirectory(File src, File destDir, boolean createDestDir)
/* 1168:     */     throws IOException
/* 1169:     */   {
/* 1170:2162 */     if (src == null) {
/* 1171:2163 */       throw new NullPointerException("Source must not be null");
/* 1172:     */     }
/* 1173:2165 */     if (destDir == null) {
/* 1174:2166 */       throw new NullPointerException("Destination must not be null");
/* 1175:     */     }
/* 1176:2168 */     if (!src.exists()) {
/* 1177:2169 */       throw new FileNotFoundException("Source '" + src + "' does not exist");
/* 1178:     */     }
/* 1179:2171 */     if (src.isDirectory()) {
/* 1180:2172 */       moveDirectoryToDirectory(src, destDir, createDestDir);
/* 1181:     */     } else {
/* 1182:2174 */       moveFileToDirectory(src, destDir, createDestDir);
/* 1183:     */     }
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   public static boolean isSymlink(File file)
/* 1187:     */     throws IOException
/* 1188:     */   {
/* 1189:2190 */     if (file == null) {
/* 1190:2191 */       throw new NullPointerException("File must not be null");
/* 1191:     */     }
/* 1192:2193 */     if (FilenameUtils.isSystemWindows()) {
/* 1193:2194 */       return false;
/* 1194:     */     }
/* 1195:2196 */     File fileInCanonicalDir = null;
/* 1196:2197 */     if (file.getParent() == null)
/* 1197:     */     {
/* 1198:2198 */       fileInCanonicalDir = file;
/* 1199:     */     }
/* 1200:     */     else
/* 1201:     */     {
/* 1202:2200 */       File canonicalDir = file.getParentFile().getCanonicalFile();
/* 1203:2201 */       fileInCanonicalDir = new File(canonicalDir, file.getName());
/* 1204:     */     }
/* 1205:2204 */     if (fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile())) {
/* 1206:2205 */       return false;
/* 1207:     */     }
/* 1208:2207 */     return true;
/* 1209:     */   }
/* 1210:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.FileUtils
 * JD-Core Version:    0.7.0.1
 */