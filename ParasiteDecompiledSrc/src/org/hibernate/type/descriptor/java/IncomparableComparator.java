/*  1:   */ package org.hibernate.type.descriptor.java;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ 
/*  5:   */ public class IncomparableComparator
/*  6:   */   implements Comparator
/*  7:   */ {
/*  8:33 */   public static final IncomparableComparator INSTANCE = new IncomparableComparator();
/*  9:   */   
/* 10:   */   public int compare(Object o1, Object o2)
/* 11:   */   {
/* 12:36 */     return 0;
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.descriptor.java.IncomparableComparator
 * JD-Core Version:    0.7.0.1
 */