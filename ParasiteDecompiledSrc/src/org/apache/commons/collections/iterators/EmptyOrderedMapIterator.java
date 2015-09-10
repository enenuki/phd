/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.OrderedMapIterator;
/*  4:   */ import org.apache.commons.collections.ResettableIterator;
/*  5:   */ 
/*  6:   */ public class EmptyOrderedMapIterator
/*  7:   */   extends AbstractEmptyIterator
/*  8:   */   implements OrderedMapIterator, ResettableIterator
/*  9:   */ {
/* 10:36 */   public static final OrderedMapIterator INSTANCE = new EmptyOrderedMapIterator();
/* 11:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EmptyOrderedMapIterator
 * JD-Core Version:    0.7.0.1
 */