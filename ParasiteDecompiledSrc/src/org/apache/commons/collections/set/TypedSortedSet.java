/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.SortedSet;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedSortedSet
/*  7:   */ {
/*  8:   */   public static SortedSet decorate(SortedSet set, Class type)
/*  9:   */   {
/* 10:51 */     return new PredicatedSortedSet(set, InstanceofPredicate.getInstance(type));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.TypedSortedSet
 * JD-Core Version:    0.7.0.1
 */