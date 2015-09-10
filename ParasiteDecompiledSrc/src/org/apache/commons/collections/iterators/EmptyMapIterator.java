/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.MapIterator;
/*  4:   */ import org.apache.commons.collections.ResettableIterator;
/*  5:   */ 
/*  6:   */ public class EmptyMapIterator
/*  7:   */   extends AbstractEmptyIterator
/*  8:   */   implements MapIterator, ResettableIterator
/*  9:   */ {
/* 10:36 */   public static final MapIterator INSTANCE = new EmptyMapIterator();
/* 11:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EmptyMapIterator
 * JD-Core Version:    0.7.0.1
 */