/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.ListIterator;
/*  4:   */ import org.apache.commons.collections.Unmodifiable;
/*  5:   */ 
/*  6:   */ public final class UnmodifiableListIterator
/*  7:   */   implements ListIterator, Unmodifiable
/*  8:   */ {
/*  9:   */   private ListIterator iterator;
/* 10:   */   
/* 11:   */   public static ListIterator decorate(ListIterator iterator)
/* 12:   */   {
/* 13:44 */     if (iterator == null) {
/* 14:45 */       throw new IllegalArgumentException("ListIterator must not be null");
/* 15:   */     }
/* 16:47 */     if ((iterator instanceof Unmodifiable)) {
/* 17:48 */       return iterator;
/* 18:   */     }
/* 19:50 */     return new UnmodifiableListIterator(iterator);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private UnmodifiableListIterator(ListIterator iterator)
/* 23:   */   {
/* 24:61 */     this.iterator = iterator;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean hasNext()
/* 28:   */   {
/* 29:66 */     return this.iterator.hasNext();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Object next()
/* 33:   */   {
/* 34:70 */     return this.iterator.next();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int nextIndex()
/* 38:   */   {
/* 39:74 */     return this.iterator.nextIndex();
/* 40:   */   }
/* 41:   */   
/* 42:   */   public boolean hasPrevious()
/* 43:   */   {
/* 44:78 */     return this.iterator.hasPrevious();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Object previous()
/* 48:   */   {
/* 49:82 */     return this.iterator.previous();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int previousIndex()
/* 53:   */   {
/* 54:86 */     return this.iterator.previousIndex();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void remove()
/* 58:   */   {
/* 59:90 */     throw new UnsupportedOperationException("remove() is not supported");
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void set(Object obj)
/* 63:   */   {
/* 64:94 */     throw new UnsupportedOperationException("set() is not supported");
/* 65:   */   }
/* 66:   */   
/* 67:   */   public void add(Object obj)
/* 68:   */   {
/* 69:98 */     throw new UnsupportedOperationException("add() is not supported");
/* 70:   */   }
/* 71:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.UnmodifiableListIterator
 * JD-Core Version:    0.7.0.1
 */