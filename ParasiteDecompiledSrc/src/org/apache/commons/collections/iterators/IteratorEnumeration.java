/*  1:   */ package org.apache.commons.collections.iterators;
/*  2:   */ 
/*  3:   */ import java.util.Enumeration;
/*  4:   */ import java.util.Iterator;
/*  5:   */ 
/*  6:   */ public class IteratorEnumeration
/*  7:   */   implements Enumeration
/*  8:   */ {
/*  9:   */   private Iterator iterator;
/* 10:   */   
/* 11:   */   public IteratorEnumeration() {}
/* 12:   */   
/* 13:   */   public IteratorEnumeration(Iterator iterator)
/* 14:   */   {
/* 15:53 */     this.iterator = iterator;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean hasMoreElements()
/* 19:   */   {
/* 20:65 */     return this.iterator.hasNext();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Object nextElement()
/* 24:   */   {
/* 25:76 */     return this.iterator.next();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Iterator getIterator()
/* 29:   */   {
/* 30:88 */     return this.iterator;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void setIterator(Iterator iterator)
/* 34:   */   {
/* 35:97 */     this.iterator = iterator;
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.iterators.IteratorEnumeration
 * JD-Core Version:    0.7.0.1
 */