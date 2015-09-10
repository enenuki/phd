/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.NoSuchElementException;
/*  4:   */ 
/*  5:   */ abstract class AbstractEmptyIterator
/*  6:   */ {
/*  7:   */   public boolean hasNext()
/*  8:   */   {
/*  9:39 */     return false;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public Object next()
/* 13:   */   {
/* 14:43 */     throw new NoSuchElementException("Iterator contains no elements");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean hasPrevious()
/* 18:   */   {
/* 19:47 */     return false;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object previous()
/* 23:   */   {
/* 24:51 */     throw new NoSuchElementException("Iterator contains no elements");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public int nextIndex()
/* 28:   */   {
/* 29:55 */     return 0;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public int previousIndex()
/* 33:   */   {
/* 34:59 */     return -1;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void add(Object obj)
/* 38:   */   {
/* 39:63 */     throw new UnsupportedOperationException("add() not supported for empty Iterator");
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void set(Object obj)
/* 43:   */   {
/* 44:67 */     throw new IllegalStateException("Iterator contains no elements");
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void remove()
/* 48:   */   {
/* 49:71 */     throw new IllegalStateException("Iterator contains no elements");
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getKey()
/* 53:   */   {
/* 54:75 */     throw new IllegalStateException("Iterator contains no elements");
/* 55:   */   }
/* 56:   */   
/* 57:   */   public Object getValue()
/* 58:   */   {
/* 59:79 */     throw new IllegalStateException("Iterator contains no elements");
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Object setValue(Object value)
/* 63:   */   {
/* 64:83 */     throw new IllegalStateException("Iterator contains no elements");
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void reset() {}
/* 68:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.AbstractEmptyIterator
 * JD-Core Version:    0.7.0.1
 */