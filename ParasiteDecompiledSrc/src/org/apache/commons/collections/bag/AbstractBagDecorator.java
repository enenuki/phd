/*  1:   */ package org.apache.commons.collections.bag;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.apache.commons.collections.Bag;
/*  5:   */ import org.apache.commons.collections.collection.AbstractCollectionDecorator;
/*  6:   */ 
/*  7:   */ public abstract class AbstractBagDecorator
/*  8:   */   extends AbstractCollectionDecorator
/*  9:   */   implements Bag
/* 10:   */ {
/* 11:   */   protected AbstractBagDecorator() {}
/* 12:   */   
/* 13:   */   protected AbstractBagDecorator(Bag bag)
/* 14:   */   {
/* 15:52 */     super(bag);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected Bag getBag()
/* 19:   */   {
/* 20:61 */     return (Bag)getCollection();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getCount(Object object)
/* 24:   */   {
/* 25:66 */     return getBag().getCount(object);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean add(Object object, int count)
/* 29:   */   {
/* 30:70 */     return getBag().add(object, count);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean remove(Object object, int count)
/* 34:   */   {
/* 35:74 */     return getBag().remove(object, count);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Set uniqueSet()
/* 39:   */   {
/* 40:78 */     return getBag().uniqueSet();
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.AbstractBagDecorator
 * JD-Core Version:    0.7.0.1
 */