/*   1:    */ package org.apache.commons.collections.comparators;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ 
/*   6:    */ public class ReverseComparator
/*   7:    */   implements Comparator, Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 2858887242028539265L;
/*  10:    */   private Comparator comparator;
/*  11:    */   
/*  12:    */   public ReverseComparator()
/*  13:    */   {
/*  14: 52 */     this(null);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ReverseComparator(Comparator comparator)
/*  18:    */   {
/*  19: 65 */     if (comparator != null) {
/*  20: 66 */       this.comparator = comparator;
/*  21:    */     } else {
/*  22: 68 */       this.comparator = ComparableComparator.getInstance();
/*  23:    */     }
/*  24:    */   }
/*  25:    */   
/*  26:    */   public int compare(Object obj1, Object obj2)
/*  27:    */   {
/*  28: 81 */     return this.comparator.compare(obj2, obj1);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public int hashCode()
/*  32:    */   {
/*  33: 93 */     return "ReverseComparator".hashCode() ^ this.comparator.hashCode();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public boolean equals(Object object)
/*  37:    */   {
/*  38:113 */     if (this == object) {
/*  39:114 */       return true;
/*  40:    */     }
/*  41:115 */     if (null == object) {
/*  42:116 */       return false;
/*  43:    */     }
/*  44:117 */     if (object.getClass().equals(getClass()))
/*  45:    */     {
/*  46:118 */       ReverseComparator thatrc = (ReverseComparator)object;
/*  47:119 */       return this.comparator.equals(thatrc.comparator);
/*  48:    */     }
/*  49:121 */     return false;
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.ReverseComparator
 * JD-Core Version:    0.7.0.1
 */