/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.util.Date;
/*   6:    */ import org.apache.commons.io.FileUtils;
/*   7:    */ 
/*   8:    */ public class AgeFileFilter
/*   9:    */   extends AbstractFileFilter
/*  10:    */   implements Serializable
/*  11:    */ {
/*  12:    */   private final long cutoff;
/*  13:    */   private final boolean acceptOlder;
/*  14:    */   
/*  15:    */   public AgeFileFilter(long cutoff)
/*  16:    */   {
/*  17: 66 */     this(cutoff, true);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public AgeFileFilter(long cutoff, boolean acceptOlder)
/*  21:    */   {
/*  22: 78 */     this.acceptOlder = acceptOlder;
/*  23: 79 */     this.cutoff = cutoff;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public AgeFileFilter(Date cutoffDate)
/*  27:    */   {
/*  28: 89 */     this(cutoffDate, true);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public AgeFileFilter(Date cutoffDate, boolean acceptOlder)
/*  32:    */   {
/*  33:101 */     this(cutoffDate.getTime(), acceptOlder);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public AgeFileFilter(File cutoffReference)
/*  37:    */   {
/*  38:112 */     this(cutoffReference, true);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public AgeFileFilter(File cutoffReference, boolean acceptOlder)
/*  42:    */   {
/*  43:126 */     this(cutoffReference.lastModified(), acceptOlder);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean accept(File file)
/*  47:    */   {
/*  48:144 */     boolean newer = FileUtils.isFileNewer(file, this.cutoff);
/*  49:145 */     return this.acceptOlder ? false : !newer ? true : newer;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String toString()
/*  53:    */   {
/*  54:155 */     String condition = this.acceptOlder ? "<=" : ">";
/*  55:156 */     return super.toString() + "(" + condition + this.cutoff + ")";
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.AgeFileFilter
 * JD-Core Version:    0.7.0.1
 */