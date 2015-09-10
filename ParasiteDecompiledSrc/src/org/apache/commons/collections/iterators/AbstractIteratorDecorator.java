/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ 
/*  5:   */ public class AbstractIteratorDecorator
/*  6:   */   implements Iterator
/*  7:   */ {
/*  8:   */   protected final Iterator iterator;
/*  9:   */   
/* 10:   */   public AbstractIteratorDecorator(Iterator iterator)
/* 11:   */   {
/* 12:46 */     if (iterator == null) {
/* 13:47 */       throw new IllegalArgumentException("Iterator must not be null");
/* 14:   */     }
/* 15:49 */     this.iterator = iterator;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected Iterator getIterator()
/* 19:   */   {
/* 20:58 */     return this.iterator;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean hasNext()
/* 24:   */   {
/* 25:63 */     return this.iterator.hasNext();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object next()
/* 29:   */   {
/* 30:67 */     return this.iterator.next();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void remove()
/* 34:   */   {
/* 35:71 */     this.iterator.remove();
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.AbstractIteratorDecorator
 * JD-Core Version:    0.7.0.1
 */