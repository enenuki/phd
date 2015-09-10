/*  1:   */ package org.apache.commons.collections.bag;
/*  2:   */ 
/*  3:   */ import java.util.Comparator;
/*  4:   */ import org.apache.commons.collections.SortedBag;
/*  5:   */ 
/*  6:   */ public abstract class AbstractSortedBagDecorator
/*  7:   */   extends AbstractBagDecorator
/*  8:   */   implements SortedBag
/*  9:   */ {
/* 10:   */   protected AbstractSortedBagDecorator() {}
/* 11:   */   
/* 12:   */   protected AbstractSortedBagDecorator(SortedBag bag)
/* 13:   */   {
/* 14:51 */     super(bag);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected SortedBag getSortedBag()
/* 18:   */   {
/* 19:60 */     return (SortedBag)getCollection();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Object first()
/* 23:   */   {
/* 24:65 */     return getSortedBag().first();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Object last()
/* 28:   */   {
/* 29:69 */     return getSortedBag().last();
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Comparator comparator()
/* 33:   */   {
/* 34:73 */     return getSortedBag().comparator();
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.AbstractSortedBagDecorator
 * JD-Core Version:    0.7.0.1
 */