/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.ListIterator;
/*  4:   */ import org.apache.commons.collections.ResettableListIterator;
/*  5:   */ 
/*  6:   */ public class EmptyListIterator
/*  7:   */   extends AbstractEmptyIterator
/*  8:   */   implements ResettableListIterator
/*  9:   */ {
/* 10:41 */   public static final ResettableListIterator RESETTABLE_INSTANCE = new EmptyListIterator();
/* 11:46 */   public static final ListIterator INSTANCE = RESETTABLE_INSTANCE;
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.EmptyListIterator
 * JD-Core Version:    0.7.0.1
 */