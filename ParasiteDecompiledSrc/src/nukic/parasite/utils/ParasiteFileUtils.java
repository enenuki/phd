/*  1:   */ package nukic.parasite.utils;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Collection;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.List;
/*  8:   */ import org.apache.commons.io.FileUtils;
/*  9:   */ 
/* 10:   */ public class ParasiteFileUtils
/* 11:   */ {
/* 12:   */   public static List<String> findAllCsvFilesInFolder(String csvFileFolder)
/* 13:   */   {
/* 14:14 */     List<String> csvFilePaths = new ArrayList();
/* 15:15 */     String[] extensions = { "csv" };
/* 16:16 */     File root = new File(csvFileFolder);
/* 17:17 */     Collection<File> files = FileUtils.listFiles(root, extensions, true);
/* 18:18 */     Iterator<File> i = files.iterator();
/* 19:19 */     while (i.hasNext())
/* 20:   */     {
/* 21:20 */       File f = (File)i.next();
/* 22:21 */       MainLogger.debug("Found file: " + f.getAbsolutePath());
/* 23:22 */       csvFilePaths.add(f.getAbsolutePath());
/* 24:   */     }
/* 25:24 */     return csvFilePaths;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static List<String> findAllCsvFilesByTicker(String csvFileFolder, String ticker)
/* 29:   */   {
/* 30:28 */     List<String> wantedCsvFilePaths = new ArrayList();
/* 31:29 */     String[] extensions = { "csv" };
/* 32:30 */     File root = new File(csvFileFolder);
/* 33:31 */     Collection<File> files = FileUtils.listFiles(root, extensions, true);
/* 34:32 */     Iterator<File> i = files.iterator();
/* 35:33 */     while (i.hasNext())
/* 36:   */     {
/* 37:34 */       File f = (File)i.next();
/* 38:   */       
/* 39:36 */       String absoultePath = f.getAbsolutePath();
/* 40:37 */       String fileName = getFileNameFromPath(correctPath(absoultePath));
/* 41:41 */       if ((fileName.contains(ticker.toLowerCase())) || (fileName.contains(ticker.toUpperCase()))) {
/* 42:43 */         wantedCsvFilePaths.add(absoultePath);
/* 43:   */       }
/* 44:   */     }
/* 45:47 */     return wantedCsvFilePaths;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static String getFileNameFromPath(String absoultePath)
/* 49:   */   {
/* 50:51 */     String separator = System.getProperty("file.separator");
/* 51:52 */     if (separator.equals("\\")) {
/* 52:53 */       separator = "\\\\";
/* 53:   */     }
/* 54:55 */     String[] tokens = absoultePath.split(separator);
/* 55:56 */     return tokens[(tokens.length - 1)];
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static String correctPath(String absoultePath)
/* 59:   */   {
/* 60:63 */     absoultePath = absoultePath.replace("//", "/");
/* 61:   */     
/* 62:65 */     String separator = System.getProperty("file.separator");
/* 63:66 */     String incorrectSeparator = "";
/* 64:68 */     if (separator.equals("/"))
/* 65:   */     {
/* 66:69 */       incorrectSeparator = "\\";
/* 67:   */     }
/* 68:70 */     else if (separator.equals("\\"))
/* 69:   */     {
/* 70:71 */       incorrectSeparator = "/";
/* 71:   */     }
/* 72:   */     else
/* 73:   */     {
/* 74:73 */       MainLogger.error("Incorrect path seprator!");
/* 75:74 */       return null;
/* 76:   */     }
/* 77:76 */     return absoultePath.replace(incorrectSeparator, separator);
/* 78:   */   }
/* 79:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     nukic.parasite.utils.ParasiteFileUtils
 * JD-Core Version:    0.7.0.1
 */