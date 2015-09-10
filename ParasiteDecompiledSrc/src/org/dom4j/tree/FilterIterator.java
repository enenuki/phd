/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import java.util.NoSuchElementException;
/*  5:   */ 
/*  6:   */ /**
/*  7:   */  * @deprecated
/*  8:   */  */
/*  9:   */ public abstract class FilterIterator
/* 10:   */   implements Iterator
/* 11:   */ {
/* 12:   */   protected Iterator proxy;
/* 13:   */   private Object next;
/* 14:29 */   private boolean first = true;
/* 15:   */   
/* 16:   */   public FilterIterator(Iterator proxy)
/* 17:   */   {
/* 18:32 */     this.proxy = proxy;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean hasNext()
/* 22:   */   {
/* 23:36 */     if (this.first)
/* 24:   */     {
/* 25:37 */       this.next = findNext();
/* 26:38 */       this.first = false;
/* 27:   */     }
/* 28:41 */     return this.next != null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object next()
/* 32:   */     throws NoSuchElementException
/* 33:   */   {
/* 34:45 */     if (!hasNext()) {
/* 35:46 */       throw new NoSuchElementException();
/* 36:   */     }
/* 37:49 */     Object answer = this.next;
/* 38:50 */     this.next = findNext();
/* 39:   */     
/* 40:52 */     return answer;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void remove()
/* 44:   */   {
/* 45:63 */     throw new UnsupportedOperationException();
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected abstract boolean matches(Object paramObject);
/* 49:   */   
/* 50:   */   protected Object findNext()
/* 51:   */   {
/* 52:78 */     if (this.proxy != null)
/* 53:   */     {
/* 54:79 */       while (this.proxy.hasNext())
/* 55:   */       {
/* 56:80 */         Object nextObject = this.proxy.next();
/* 57:82 */         if ((nextObject != null) && (matches(nextObject))) {
/* 58:83 */           return nextObject;
/* 59:   */         }
/* 60:   */       }
/* 61:87 */       this.proxy = null;
/* 62:   */     }
/* 63:90 */     return null;
/* 64:   */   }
/* 65:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.FilterIterator
 * JD-Core Version:    0.7.0.1
 */