/*   1:    */ package org.apache.commons.io.comparator;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import org.apache.commons.io.FilenameUtils;
/*   7:    */ import org.apache.commons.io.IOCase;
/*   8:    */ 
/*   9:    */ public class ExtensionFileComparator
/*  10:    */   extends AbstractFileComparator
/*  11:    */   implements Serializable
/*  12:    */ {
/*  13: 57 */   public static final Comparator<File> EXTENSION_COMPARATOR = new ExtensionFileComparator();
/*  14: 60 */   public static final Comparator<File> EXTENSION_REVERSE = new ReverseComparator(EXTENSION_COMPARATOR);
/*  15: 63 */   public static final Comparator<File> EXTENSION_INSENSITIVE_COMPARATOR = new ExtensionFileComparator(IOCase.INSENSITIVE);
/*  16: 67 */   public static final Comparator<File> EXTENSION_INSENSITIVE_REVERSE = new ReverseComparator(EXTENSION_INSENSITIVE_COMPARATOR);
/*  17: 71 */   public static final Comparator<File> EXTENSION_SYSTEM_COMPARATOR = new ExtensionFileComparator(IOCase.SYSTEM);
/*  18: 74 */   public static final Comparator<File> EXTENSION_SYSTEM_REVERSE = new ReverseComparator(EXTENSION_SYSTEM_COMPARATOR);
/*  19:    */   private final IOCase caseSensitivity;
/*  20:    */   
/*  21:    */   public ExtensionFileComparator()
/*  22:    */   {
/*  23: 83 */     this.caseSensitivity = IOCase.SENSITIVE;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ExtensionFileComparator(IOCase caseSensitivity)
/*  27:    */   {
/*  28: 92 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int compare(File file1, File file2)
/*  32:    */   {
/*  33:107 */     String suffix1 = FilenameUtils.getExtension(file1.getName());
/*  34:108 */     String suffix2 = FilenameUtils.getExtension(file2.getName());
/*  35:109 */     return this.caseSensitivity.checkCompareTo(suffix1, suffix2);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toString()
/*  39:    */   {
/*  40:119 */     return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
/*  41:    */   }
/*  42:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.ExtensionFileComparator
 * JD-Core Version:    0.7.0.1
 */