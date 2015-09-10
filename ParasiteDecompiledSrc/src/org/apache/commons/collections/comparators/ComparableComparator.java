/*   1:    */ package org.apache.commons.collections.comparators;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Comparator;
/*   5:    */ 
/*   6:    */ public class ComparableComparator
/*   7:    */   implements Comparator, Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = -291439688585137865L;
/*  10: 51 */   private static final ComparableComparator instance = new ComparableComparator();
/*  11:    */   
/*  12:    */   public static ComparableComparator getInstance()
/*  13:    */   {
/*  14: 64 */     return instance;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public int compare(Object obj1, Object obj2)
/*  18:    */   {
/*  19: 92 */     return ((Comparable)obj1).compareTo(obj2);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public int hashCode()
/*  23:    */   {
/*  24:104 */     return "ComparableComparator".hashCode();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public boolean equals(Object object)
/*  28:    */   {
/*  29:123 */     return (this == object) || ((null != object) && (object.getClass().equals(getClass())));
/*  30:    */   }
/*  31:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.comparators.ComparableComparator
 * JD-Core Version:    0.7.0.1
 */