/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.MapIterator;
/*  4:   */ 
/*  5:   */ public class AbstractMapIteratorDecorator
/*  6:   */   implements MapIterator
/*  7:   */ {
/*  8:   */   protected final MapIterator iterator;
/*  9:   */   
/* 10:   */   public AbstractMapIteratorDecorator(MapIterator iterator)
/* 11:   */   {
/* 12:45 */     if (iterator == null) {
/* 13:46 */       throw new IllegalArgumentException("MapIterator must not be null");
/* 14:   */     }
/* 15:48 */     this.iterator = iterator;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected MapIterator getMapIterator()
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
/* 33:   */   public void remove()
/* 34:   */   {
/* 35:70 */     this.iterator.remove();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Object getKey()
/* 39:   */   {
/* 40:74 */     return this.iterator.getKey();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Object getValue()
/* 44:   */   {
/* 45:78 */     return this.iterator.getValue();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Object setValue(Object obj)
/* 49:   */   {
/* 50:82 */     return this.iterator.setValue(obj);
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.AbstractMapIteratorDecorator
 * JD-Core Version:    0.7.0.1
 */