/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.OrderedMapIterator;
/*  4:   */ 
/*  5:   */ public class AbstractOrderedMapIteratorDecorator
/*  6:   */   implements OrderedMapIterator
/*  7:   */ {
/*  8:   */   protected final OrderedMapIterator iterator;
/*  9:   */   
/* 10:   */   public AbstractOrderedMapIteratorDecorator(OrderedMapIterator iterator)
/* 11:   */   {
/* 12:45 */     if (iterator == null) {
/* 13:46 */       throw new IllegalArgumentException("OrderedMapIterator must not be null");
/* 14:   */     }
/* 15:48 */     this.iterator = iterator;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected OrderedMapIterator getOrderedMapIterator()
/* 19:   */   {
/* 20:57 */     return this.iterator;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public boolean hasNext()
/* 24:   */   {
/* 25:62 */     return this.iterator.hasNext();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Object next()
/* 29:   */   {
/* 30:66 */     return this.iterator.next();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean hasPrevious()
/* 34:   */   {
/* 35:70 */     return this.iterator.hasPrevious();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object previous()
/* 39:   */   {
/* 40:74 */     return this.iterator.previous();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void remove()
/* 44:   */   {
/* 45:78 */     this.iterator.remove();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Object getKey()
/* 49:   */   {
/* 50:82 */     return this.iterator.getKey();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public Object getValue()
/* 54:   */   {
/* 55:86 */     return this.iterator.getValue();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public Object setValue(Object obj)
/* 59:   */   {
/* 60:90 */     return this.iterator.setValue(obj);
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.AbstractOrderedMapIteratorDecorator
 * JD-Core Version:    0.7.0.1
 */