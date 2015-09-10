/*  1:   */ package org.apache.commons.io.comparator;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.Comparator;
/*  6:   */ 
/*  7:   */ public class LastModifiedFileComparator
/*  8:   */   extends AbstractFileComparator
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:51 */   public static final Comparator<File> LASTMODIFIED_COMPARATOR = new LastModifiedFileComparator();
/* 12:54 */   public static final Comparator<File> LASTMODIFIED_REVERSE = new ReverseComparator(LASTMODIFIED_COMPARATOR);
/* 13:   */   
/* 14:   */   public int compare(File file1, File file2)
/* 15:   */   {
/* 16:68 */     long result = file1.lastModified() - file2.lastModified();
/* 17:69 */     if (result < 0L) {
/* 18:70 */       return -1;
/* 19:   */     }
/* 20:71 */     if (result > 0L) {
/* 21:72 */       return 1;
/* 22:   */     }
/* 23:74 */     return 0;
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.LastModifiedFileComparator
 * JD-Core Version:    0.7.0.1
 */