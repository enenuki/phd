/*  1:   */ package org.hibernate.internal.util.collections;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ 
/*  5:   */ public final class EmptyIterator
/*  6:   */   implements Iterator
/*  7:   */ {
/*  8:34 */   public static final Iterator INSTANCE = new EmptyIterator();
/*  9:   */   
/* 10:   */   public boolean hasNext()
/* 11:   */   {
/* 12:37 */     return false;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Object next()
/* 16:   */   {
/* 17:41 */     throw new UnsupportedOperationException();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void remove()
/* 21:   */   {
/* 22:45 */     throw new UnsupportedOperationException();
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.EmptyIterator
 * JD-Core Version:    0.7.0.1
 */