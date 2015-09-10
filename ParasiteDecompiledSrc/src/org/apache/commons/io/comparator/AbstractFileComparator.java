/*  1:   */ package org.apache.commons.io.comparator;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.util.Arrays;
/*  5:   */ import java.util.Collections;
/*  6:   */ import java.util.Comparator;
/*  7:   */ import java.util.List;
/*  8:   */ 
/*  9:   */ abstract class AbstractFileComparator
/* 10:   */   implements Comparator<File>
/* 11:   */ {
/* 12:   */   public File[] sort(File... files)
/* 13:   */   {
/* 14:44 */     if (files != null) {
/* 15:45 */       Arrays.sort(files, this);
/* 16:   */     }
/* 17:47 */     return files;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public List<File> sort(List<File> files)
/* 21:   */   {
/* 22:61 */     if (files != null) {
/* 23:62 */       Collections.sort(files, this);
/* 24:   */     }
/* 25:64 */     return files;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String toString()
/* 29:   */   {
/* 30:74 */     return getClass().getSimpleName();
/* 31:   */   }
/* 32:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.AbstractFileComparator
 * JD-Core Version:    0.7.0.1
 */