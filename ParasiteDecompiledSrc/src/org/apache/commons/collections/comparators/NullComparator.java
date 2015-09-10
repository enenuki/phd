/*   1:    */ package org.apache.commons.collections.comparators;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ 
/*   6:    */ public class NullComparator
/*   7:    */   implements Comparator, Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -5820772575483504339L;
/*  10:    */   private Comparator nonNullComparator;
/*  11:    */   private boolean nullsAreHigh;
/*  12:    */   
/*  13:    */   public NullComparator()
/*  14:    */   {
/*  15: 55 */     this(ComparableComparator.getInstance(), true);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public NullComparator(Comparator nonNullComparator)
/*  19:    */   {
/*  20: 72 */     this(nonNullComparator, true);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public NullComparator(boolean nullsAreHigh)
/*  24:    */   {
/*  25: 88 */     this(ComparableComparator.getInstance(), nullsAreHigh);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public NullComparator(Comparator nonNullComparator, boolean nullsAreHigh)
/*  29:    */   {
/*  30:111 */     this.nonNullComparator = nonNullComparator;
/*  31:112 */     this.nullsAreHigh = nullsAreHigh;
/*  32:114 */     if (nonNullComparator == null) {
/*  33:115 */       throw new NullPointerException("null nonNullComparator");
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int compare(Object o1, Object o2)
/*  38:    */   {
/*  39:137 */     if (o1 == o2) {
/*  40:137 */       return 0;
/*  41:    */     }
/*  42:138 */     if (o1 == null) {
/*  43:138 */       return this.nullsAreHigh ? 1 : -1;
/*  44:    */     }
/*  45:139 */     if (o2 == null) {
/*  46:139 */       return this.nullsAreHigh ? -1 : 1;
/*  47:    */     }
/*  48:140 */     return this.nonNullComparator.compare(o1, o2);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int hashCode()
/*  52:    */   {
/*  53:151 */     return (this.nullsAreHigh ? -1 : 1) * this.nonNullComparator.hashCode();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public boolean equals(Object obj)
/*  57:    */   {
/*  58:166 */     if (obj == null) {
/*  59:166 */       return false;
/*  60:    */     }
/*  61:167 */     if (obj == this) {
/*  62:167 */       return true;
/*  63:    */     }
/*  64:168 */     if (!obj.getClass().equals(getClass())) {
/*  65:168 */       return false;
/*  66:    */     }
/*  67:170 */     NullComparator other = (NullComparator)obj;
/*  68:    */     
/*  69:172 */     return (this.nullsAreHigh == other.nullsAreHigh) && (this.nonNullComparator.equals(other.nonNullComparator));
/*  70:    */   }
/*  71:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.NullComparator
 * JD-Core Version:    0.7.0.1
 */