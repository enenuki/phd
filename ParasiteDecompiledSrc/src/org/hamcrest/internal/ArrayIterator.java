/*  1:   */ package org.hamcrest.internal;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Array;
/*  4:   */ import java.util.Iterator;
/*  5:   */ 
/*  6:   */ public class ArrayIterator
/*  7:   */   implements Iterator<Object>
/*  8:   */ {
/*  9:   */   private final Object array;
/* 10: 8 */   private int currentIndex = 0;
/* 11:   */   
/* 12:   */   public ArrayIterator(Object array)
/* 13:   */   {
/* 14:11 */     if (!array.getClass().isArray()) {
/* 15:12 */       throw new IllegalArgumentException("not an array");
/* 16:   */     }
/* 17:14 */     this.array = array;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean hasNext()
/* 21:   */   {
/* 22:18 */     return this.currentIndex < Array.getLength(this.array);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Object next()
/* 26:   */   {
/* 27:22 */     return Array.get(this.array, this.currentIndex++);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void remove()
/* 31:   */   {
/* 32:26 */     throw new UnsupportedOperationException("cannot remove items from an array");
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.internal.ArrayIterator
 * JD-Core Version:    0.7.0.1
 */