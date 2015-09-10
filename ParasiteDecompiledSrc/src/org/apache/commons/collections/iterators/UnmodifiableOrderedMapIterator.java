/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.OrderedMapIterator;
/*  4:   */ import org.apache.commons.collections.Unmodifiable;
/*  5:   */ 
/*  6:   */ public final class UnmodifiableOrderedMapIterator
/*  7:   */   implements OrderedMapIterator, Unmodifiable
/*  8:   */ {
/*  9:   */   private OrderedMapIterator iterator;
/* 10:   */   
/* 11:   */   public static OrderedMapIterator decorate(OrderedMapIterator iterator)
/* 12:   */   {
/* 13:43 */     if (iterator == null) {
/* 14:44 */       throw new IllegalArgumentException("OrderedMapIterator must not be null");
/* 15:   */     }
/* 16:46 */     if ((iterator instanceof Unmodifiable)) {
/* 17:47 */       return iterator;
/* 18:   */     }
/* 19:49 */     return new UnmodifiableOrderedMapIterator(iterator);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private UnmodifiableOrderedMapIterator(OrderedMapIterator iterator)
/* 23:   */   {
/* 24:60 */     this.iterator = iterator;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean hasNext()
/* 28:   */   {
/* 29:65 */     return this.iterator.hasNext();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Object next()
/* 33:   */   {
/* 34:69 */     return this.iterator.next();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean hasPrevious()
/* 38:   */   {
/* 39:73 */     return this.iterator.hasPrevious();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Object previous()
/* 43:   */   {
/* 44:77 */     return this.iterator.previous();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object getKey()
/* 48:   */   {
/* 49:81 */     return this.iterator.getKey();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getValue()
/* 53:   */   {
/* 54:85 */     return this.iterator.getValue();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Object setValue(Object value)
/* 58:   */   {
/* 59:89 */     throw new UnsupportedOperationException("setValue() is not supported");
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void remove()
/* 63:   */   {
/* 64:93 */     throw new UnsupportedOperationException("remove() is not supported");
/* 65:   */   }
/* 66:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.UnmodifiableOrderedMapIterator
 * JD-Core Version:    0.7.0.1
 */