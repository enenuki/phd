/*  1:   */ package org.hibernate.internal.util.collections;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ 
/*  5:   */ public final class SingletonIterator
/*  6:   */   implements Iterator
/*  7:   */ {
/*  8:   */   private Object value;
/*  9:35 */   private boolean hasNext = true;
/* 10:   */   
/* 11:   */   public boolean hasNext()
/* 12:   */   {
/* 13:38 */     return this.hasNext;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Object next()
/* 17:   */   {
/* 18:42 */     if (this.hasNext)
/* 19:   */     {
/* 20:43 */       this.hasNext = false;
/* 21:44 */       return this.value;
/* 22:   */     }
/* 23:47 */     throw new IllegalStateException();
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void remove()
/* 27:   */   {
/* 28:52 */     throw new UnsupportedOperationException();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public SingletonIterator(Object value)
/* 32:   */   {
/* 33:56 */     this.value = value;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.SingletonIterator
 * JD-Core Version:    0.7.0.1
 */