/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.apache.commons.collections.Unmodifiable;
/*  5:   */ 
/*  6:   */ public final class UnmodifiableIterator
/*  7:   */   implements Iterator, Unmodifiable
/*  8:   */ {
/*  9:   */   private Iterator iterator;
/* 10:   */   
/* 11:   */   public static Iterator decorate(Iterator iterator)
/* 12:   */   {
/* 13:46 */     if (iterator == null) {
/* 14:47 */       throw new IllegalArgumentException("Iterator must not be null");
/* 15:   */     }
/* 16:49 */     if ((iterator instanceof Unmodifiable)) {
/* 17:50 */       return iterator;
/* 18:   */     }
/* 19:52 */     return new UnmodifiableIterator(iterator);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private UnmodifiableIterator(Iterator iterator)
/* 23:   */   {
/* 24:63 */     this.iterator = iterator;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean hasNext()
/* 28:   */   {
/* 29:68 */     return this.iterator.hasNext();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Object next()
/* 33:   */   {
/* 34:72 */     return this.iterator.next();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void remove()
/* 38:   */   {
/* 39:76 */     throw new UnsupportedOperationException("remove() is not supported");
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.UnmodifiableIterator
 * JD-Core Version:    0.7.0.1
 */