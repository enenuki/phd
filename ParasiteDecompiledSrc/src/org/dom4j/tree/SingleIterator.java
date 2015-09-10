/*  1:   */ package org.dom4j.tree;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ 
/*  5:   */ public class SingleIterator
/*  6:   */   implements Iterator
/*  7:   */ {
/*  8:22 */   private boolean first = true;
/*  9:   */   private Object object;
/* 10:   */   
/* 11:   */   public SingleIterator(Object object)
/* 12:   */   {
/* 13:27 */     this.object = object;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean hasNext()
/* 17:   */   {
/* 18:31 */     return this.first;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public Object next()
/* 22:   */   {
/* 23:35 */     Object answer = this.object;
/* 24:36 */     this.object = null;
/* 25:37 */     this.first = false;
/* 26:   */     
/* 27:39 */     return answer;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void remove()
/* 31:   */   {
/* 32:43 */     throw new UnsupportedOperationException("remove() is not supported by this iterator");
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.tree.SingleIterator
 * JD-Core Version:    0.7.0.1
 */