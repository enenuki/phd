/*  1:   */ package org.hibernate.internal.util.compare;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.util.Comparator;
/*  5:   */ 
/*  6:   */ public class ComparableComparator
/*  7:   */   implements Comparator<Comparable>, Serializable
/*  8:   */ {
/*  9:37 */   public static final Comparator INSTANCE = new ComparableComparator();
/* 10:   */   
/* 11:   */   public int compare(Comparable one, Comparable another)
/* 12:   */   {
/* 13:41 */     return one.compareTo(another);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.compare.ComparableComparator
 * JD-Core Version:    0.7.0.1
 */