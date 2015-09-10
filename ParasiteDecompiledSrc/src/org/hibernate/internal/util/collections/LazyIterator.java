/*  1:   */ package org.hibernate.internal.util.collections;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public final class LazyIterator
/*  8:   */   implements Iterator
/*  9:   */ {
/* 10:   */   private final Map map;
/* 11:   */   private Iterator iterator;
/* 12:   */   
/* 13:   */   private Iterator getIterator()
/* 14:   */   {
/* 15:36 */     if (this.iterator == null) {
/* 16:37 */       this.iterator = this.map.values().iterator();
/* 17:   */     }
/* 18:39 */     return this.iterator;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public LazyIterator(Map map)
/* 22:   */   {
/* 23:43 */     this.map = map;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean hasNext()
/* 27:   */   {
/* 28:47 */     return getIterator().hasNext();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object next()
/* 32:   */   {
/* 33:51 */     return getIterator().next();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void remove()
/* 37:   */   {
/* 38:55 */     throw new UnsupportedOperationException();
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.collections.LazyIterator
 * JD-Core Version:    0.7.0.1
 */