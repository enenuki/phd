/*   1:    */ package org.apache.commons.io.filefilter;
/*   2:    */ 
/*   3:    */ import java.io.File;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ public class SizeFileFilter
/*   7:    */   extends AbstractFileFilter
/*   8:    */   implements Serializable
/*   9:    */ {
/*  10:    */   private final long size;
/*  11:    */   private final boolean acceptLarger;
/*  12:    */   
/*  13:    */   public SizeFileFilter(long size)
/*  14:    */   {
/*  15: 59 */     this(size, true);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public SizeFileFilter(long size, boolean acceptLarger)
/*  19:    */   {
/*  20: 72 */     if (size < 0L) {
/*  21: 73 */       throw new IllegalArgumentException("The size must be non-negative");
/*  22:    */     }
/*  23: 75 */     this.size = size;
/*  24: 76 */     this.acceptLarger = acceptLarger;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean accept(File file)
/*  28:    */   {
/*  29: 93 */     boolean smaller = file.length() < this.size;
/*  30: 94 */     return this.acceptLarger ? false : !smaller ? true : smaller;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public String toString()
/*  34:    */   {
/*  35:104 */     String condition = this.acceptLarger ? ">=" : "<";
/*  36:105 */     return super.toString() + "(" + condition + this.size + ")";
/*  37:    */   }
/*  38:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.io.filefilter.SizeFileFilter
 * JD-Core Version:    0.7.0.1
 */