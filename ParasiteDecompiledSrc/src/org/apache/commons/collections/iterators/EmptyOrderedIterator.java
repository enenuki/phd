/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.OrderedIterator;
/*  4:   */ import org.apache.commons.collections.ResettableIterator;
/*  5:   */ 
/*  6:   */ public class EmptyOrderedIterator
/*  7:   */   extends AbstractEmptyIterator
/*  8:   */   implements OrderedIterator, ResettableIterator
/*  9:   */ {
/* 10:36 */   public static final OrderedIterator INSTANCE = new EmptyOrderedIterator();
/* 11:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EmptyOrderedIterator
 * JD-Core Version:    0.7.0.1
 */