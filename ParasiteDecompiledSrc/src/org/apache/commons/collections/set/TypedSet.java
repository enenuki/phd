/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedSet
/*  7:   */ {
/*  8:   */   public static Set decorate(Set set, Class type)
/*  9:   */   {
/* 10:51 */     return new PredicatedSet(set, InstanceofPredicate.getInstance(type));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.TypedSet
 * JD-Core Version:    0.7.0.1
 */