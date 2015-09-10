/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.ListIterator;
/*  4:   */ 
/*  5:   */ public class AbstractListIteratorDecorator
/*  6:   */   implements ListIterator
/*  7:   */ {
/*  8:   */   protected final ListIterator iterator;
/*  9:   */   
/* 10:   */   public AbstractListIteratorDecorator(ListIterator iterator)
/* 11:   */   {
/* 12:46 */     if (iterator == null) {
/* 13:47 */       throw new IllegalArgumentException("ListIterator must not be null");
/* 14:   */     }
/* 15:49 */     this.iterator = iterator;
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected ListIterator getListIterator()
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
/* 33:   */   public int nextIndex()
/* 34:   */   {
/* 35:71 */     return this.iterator.nextIndex();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean hasPrevious()
/* 39:   */   {
/* 40:75 */     return this.iterator.hasPrevious();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Object previous()
/* 44:   */   {
/* 45:79 */     return this.iterator.previous();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public int previousIndex()
/* 49:   */   {
/* 50:83 */     return this.iterator.previousIndex();
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void remove()
/* 54:   */   {
/* 55:87 */     this.iterator.remove();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void set(Object obj)
/* 59:   */   {
/* 60:91 */     this.iterator.set(obj);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void add(Object obj)
/* 64:   */   {
/* 65:95 */     this.iterator.add(obj);
/* 66:   */   }
/* 67:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.AbstractListIteratorDecorator
 * JD-Core Version:    0.7.0.1
 */