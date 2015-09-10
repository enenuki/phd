/*  1:   */ package org.apache.commons.collections.collection;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.apache.commons.collections.functors.InstanceofPredicate;
/*  5:   */ 
/*  6:   */ public class TypedCollection
/*  7:   */ {
/*  8:   */   public static Collection decorate(Collection coll, Class type)
/*  9:   */   {
/* 10:51 */     return new PredicatedCollection(coll, InstanceofPredicate.getInstance(type));
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.collection.TypedCollection
 * JD-Core Version:    0.7.0.1
 */