/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.apache.commons.collections.ResettableIterator;
/*  5:   */ 
/*  6:   */ public class EmptyIterator
/*  7:   */   extends AbstractEmptyIterator
/*  8:   */   implements ResettableIterator
/*  9:   */ {
/* 10:41 */   public static final ResettableIterator RESETTABLE_INSTANCE = new EmptyIterator();
/* 11:46 */   public static final Iterator INSTANCE = RESETTABLE_INSTANCE;
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EmptyIterator
 * JD-Core Version:    0.7.0.1
 */