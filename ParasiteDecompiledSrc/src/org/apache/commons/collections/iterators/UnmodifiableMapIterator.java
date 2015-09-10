/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.MapIterator;
/*  4:   */ import org.apache.commons.collections.Unmodifiable;
/*  5:   */ 
/*  6:   */ public final class UnmodifiableMapIterator
/*  7:   */   implements MapIterator, Unmodifiable
/*  8:   */ {
/*  9:   */   private MapIterator iterator;
/* 10:   */   
/* 11:   */   public static MapIterator decorate(MapIterator iterator)
/* 12:   */   {
/* 13:43 */     if (iterator == null) {
/* 14:44 */       throw new IllegalArgumentException("MapIterator must not be null");
/* 15:   */     }
/* 16:46 */     if ((iterator instanceof Unmodifiable)) {
/* 17:47 */       return iterator;
/* 18:   */     }
/* 19:49 */     return new UnmodifiableMapIterator(iterator);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private UnmodifiableMapIterator(MapIterator iterator)
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
/* 37:   */   public Object getKey()
/* 38:   */   {
/* 39:73 */     return this.iterator.getKey();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Object getValue()
/* 43:   */   {
/* 44:77 */     return this.iterator.getValue();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object setValue(Object value)
/* 48:   */   {
/* 49:81 */     throw new UnsupportedOperationException("setValue() is not supported");
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void remove()
/* 53:   */   {
/* 54:85 */     throw new UnsupportedOperationException("remove() is not supported");
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.UnmodifiableMapIterator
 * JD-Core Version:    0.7.0.1
 */