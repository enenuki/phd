/*   1:    */ package org.apache.commons.io.comparator;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import org.apache.commons.io.FileUtils;
/*   7:    */ 
/*   8:    */ public class SizeFileComparator
/*   9:    */   extends AbstractFileComparator
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12: 55 */   public static final Comparator<File> SIZE_COMPARATOR = new SizeFileComparator();
/*  13: 58 */   public static final Comparator<File> SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
/*  14: 64 */   public static final Comparator<File> SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
/*  15: 70 */   public static final Comparator<File> SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
/*  16:    */   private final boolean sumDirectoryContents;
/*  17:    */   
/*  18:    */   public SizeFileComparator()
/*  19:    */   {
/*  20: 79 */     this.sumDirectoryContents = false;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public SizeFileComparator(boolean sumDirectoryContents)
/*  24:    */   {
/*  25: 94 */     this.sumDirectoryContents = sumDirectoryContents;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int compare(File file1, File file2)
/*  29:    */   {
/*  30:109 */     long size1 = 0L;
/*  31:110 */     if (file1.isDirectory()) {
/*  32:111 */       size1 = (this.sumDirectoryContents) && (file1.exists()) ? FileUtils.sizeOfDirectory(file1) : 0L;
/*  33:    */     } else {
/*  34:113 */       size1 = file1.length();
/*  35:    */     }
/*  36:115 */     long size2 = 0L;
/*  37:116 */     if (file2.isDirectory()) {
/*  38:117 */       size2 = (this.sumDirectoryContents) && (file2.exists()) ? FileUtils.sizeOfDirectory(file2) : 0L;
/*  39:    */     } else {
/*  40:119 */       size2 = file2.length();
/*  41:    */     }
/*  42:121 */     long result = size1 - size2;
/*  43:122 */     if (result < 0L) {
/*  44:123 */       return -1;
/*  45:    */     }
/*  46:124 */     if (result > 0L) {
/*  47:125 */       return 1;
/*  48:    */     }
/*  49:127 */     return 0;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toString()
/*  53:    */   {
/*  54:138 */     return super.toString() + "[sumDirectoryContents=" + this.sumDirectoryContents + "]";
/*  55:    */   }
/*  56:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.SizeFileComparator
 * JD-Core Version:    0.7.0.1
 */