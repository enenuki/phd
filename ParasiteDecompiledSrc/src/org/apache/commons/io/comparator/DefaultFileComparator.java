/*  1:   */ package org.apache.commons.io.comparator;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.Serializable;
/*  5:   */ import java.util.Comparator;
/*  6:   */ 
/*  7:   */ public class DefaultFileComparator
/*  8:   */   extends AbstractFileComparator
/*  9:   */   implements Serializable
/* 10:   */ {
/* 11:50 */   public static final Comparator<File> DEFAULT_COMPARATOR = new DefaultFileComparator();
/* 12:53 */   public static final Comparator<File> DEFAULT_REVERSE = new ReverseComparator(DEFAULT_COMPARATOR);
/* 13:   */   
/* 14:   */   public int compare(File file1, File file2)
/* 15:   */   {
/* 16:64 */     return file1.compareTo(file2);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.DefaultFileComparator
 * JD-Core Version:    0.7.0.1
 */