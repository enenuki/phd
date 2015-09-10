/*  1:   */ package org.apache.commons.collections.bag;
/*  2:   */ 
/*  3:   */ import org.apache.commons.collections.Bag;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedBag
/*  7:   */ {
/*  8:   */   public static Bag decorate(Bag bag, Class type)
/*  9:   */   {
/* 10:51 */     return new PredicatedBag(bag, InstanceofPredicate.getInstance(type));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bag.TypedBag
 * JD-Core Version:    0.7.0.1
 */