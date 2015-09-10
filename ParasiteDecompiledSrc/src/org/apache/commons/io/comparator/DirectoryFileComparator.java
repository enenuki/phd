/*  1:   */ package org.apache.commons.io.comparator;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.Comparator;
/*  6:   */ 
/*  7:   */ public class DirectoryFileComparator
/*  8:   */   extends AbstractFileComparator
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:49 */   public static final Comparator<File> DIRECTORY_COMPARATOR = new DirectoryFileComparator();
/* 12:52 */   public static final Comparator<File> DIRECTORY_REVERSE = new ReverseComparator(DIRECTORY_COMPARATOR);
/* 13:   */   
/* 14:   */   public int compare(File file1, File file2)
/* 15:   */   {
/* 16:63 */     return getType(file1) - getType(file2);
/* 17:   */   }
/* 18:   */   
/* 19:   */   private int getType(File file)
/* 20:   */   {
/* 21:73 */     if (file.isDirectory()) {
/* 22:74 */       return 1;
/* 23:   */     }
/* 24:76 */     return 2;
/* 25:   */   }
/* 26:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.DirectoryFileComparator
 * JD-Core Version:    0.7.0.1
 */