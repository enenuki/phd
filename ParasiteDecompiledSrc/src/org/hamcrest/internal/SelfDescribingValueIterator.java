/*  1:   */ package org.hamcrest.internal;
/*  2:   */ 
/*  3:   */ import java.util.Iterator;
/*  4:   */ import org.hamcrest.SelfDescribing;
/*  5:   */ 
/*  6:   */ public class SelfDescribingValueIterator<T>
/*  7:   */   implements Iterator<SelfDescribing>
/*  8:   */ {
/*  9:   */   private Iterator<T> values;
/* 10:   */   
/* 11:   */   public SelfDescribingValueIterator(Iterator<T> values)
/* 12:   */   {
/* 13:11 */     this.values = values;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean hasNext()
/* 17:   */   {
/* 18:15 */     return this.values.hasNext();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SelfDescribing next()
/* 22:   */   {
/* 23:19 */     return new SelfDescribingValue(this.values.next());
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void remove()
/* 27:   */   {
/* 28:23 */     this.values.remove();
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hamcrest.internal.SelfDescribingValueIterator
 * JD-Core Version:    0.7.0.1
 */