/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.apache.commons.collections.functors.UniquePredicate;
/*  5:   */ 
/*  6:   */ public class UniqueFilterIterator
/*  7:   */   extends FilterIterator
/*  8:   */ {
/*  9:   */   public UniqueFilterIterator(Iterator iterator)
/* 10:   */   {
/* 11:43 */     super(iterator, UniquePredicate.getInstance());
/* 12:   */   }
/* 13:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.UniqueFilterIterator
 * JD-Core Version:    0.7.0.1
 */