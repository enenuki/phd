/*   1:    */ package org.apache.commons.io.comparator;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import org.apache.commons.io.IOCase;
/*   7:    */ 
/*   8:    */ public class PathFileComparator
/*   9:    */   extends AbstractFileComparator
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12: 55 */   public static final Comparator<File> PATH_COMPARATOR = new PathFileComparator();
/*  13: 58 */   public static final Comparator<File> PATH_REVERSE = new ReverseComparator(PATH_COMPARATOR);
/*  14: 61 */   public static final Comparator<File> PATH_INSENSITIVE_COMPARATOR = new PathFileComparator(IOCase.INSENSITIVE);
/*  15: 64 */   public static final Comparator<File> PATH_INSENSITIVE_REVERSE = new ReverseComparator(PATH_INSENSITIVE_COMPARATOR);
/*  16: 67 */   public static final Comparator<File> PATH_SYSTEM_COMPARATOR = new PathFileComparator(IOCase.SYSTEM);
/*  17: 70 */   public static final Comparator<File> PATH_SYSTEM_REVERSE = new ReverseComparator(PATH_SYSTEM_COMPARATOR);
/*  18:    */   private final IOCase caseSensitivity;
/*  19:    */   
/*  20:    */   public PathFileComparator()
/*  21:    */   {
/*  22: 79 */     this.caseSensitivity = IOCase.SENSITIVE;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public PathFileComparator(IOCase caseSensitivity)
/*  26:    */   {
/*  27: 88 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int compare(File file1, File file2)
/*  31:    */   {
/*  32:103 */     return this.caseSensitivity.checkCompareTo(file1.getPath(), file2.getPath());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String toString()
/*  36:    */   {
/*  37:113 */     return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
/*  38:    */   }
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.PathFileComparator
 * JD-Core Version:    0.7.0.1
 */