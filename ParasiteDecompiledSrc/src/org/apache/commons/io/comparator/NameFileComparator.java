/*   1:    */ package org.apache.commons.io.comparator;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Comparator;
/*   6:    */ import org.apache.commons.io.IOCase;
/*   7:    */ 
/*   8:    */ public class NameFileComparator
/*   9:    */   extends AbstractFileComparator
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12: 55 */   public static final Comparator<File> NAME_COMPARATOR = new NameFileComparator();
/*  13: 58 */   public static final Comparator<File> NAME_REVERSE = new ReverseComparator(NAME_COMPARATOR);
/*  14: 61 */   public static final Comparator<File> NAME_INSENSITIVE_COMPARATOR = new NameFileComparator(IOCase.INSENSITIVE);
/*  15: 64 */   public static final Comparator<File> NAME_INSENSITIVE_REVERSE = new ReverseComparator(NAME_INSENSITIVE_COMPARATOR);
/*  16: 67 */   public static final Comparator<File> NAME_SYSTEM_COMPARATOR = new NameFileComparator(IOCase.SYSTEM);
/*  17: 70 */   public static final Comparator<File> NAME_SYSTEM_REVERSE = new ReverseComparator(NAME_SYSTEM_COMPARATOR);
/*  18:    */   private final IOCase caseSensitivity;
/*  19:    */   
/*  20:    */   public NameFileComparator()
/*  21:    */   {
/*  22: 79 */     this.caseSensitivity = IOCase.SENSITIVE;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public NameFileComparator(IOCase caseSensitivity)
/*  26:    */   {
/*  27: 88 */     this.caseSensitivity = (caseSensitivity == null ? IOCase.SENSITIVE : caseSensitivity);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public int compare(File file1, File file2)
/*  31:    */   {
/*  32:102 */     return this.caseSensitivity.checkCompareTo(file1.getName(), file2.getName());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String toString()
/*  36:    */   {
/*  37:112 */     return super.toString() + "[caseSensitivity=" + this.caseSensitivity + "]";
/*  38:    */   }
/*  39:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.comparator.NameFileComparator
 * JD-Core Version:    0.7.0.1
 */