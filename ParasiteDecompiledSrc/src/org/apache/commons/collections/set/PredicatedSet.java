/*  1:   */ package org.apache.commons.collections.set;
/*  2:   */ 
/*  3:   */ import java.util.Set;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ import org.apache.commons.collections.collection.PredicatedCollection;
/*  6:   */ 
/*  7:   */ public class PredicatedSet
/*  8:   */   extends PredicatedCollection
/*  9:   */   implements Set
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -684521469108685117L;
/* 12:   */   
/* 13:   */   public static Set decorate(Set set, Predicate predicate)
/* 14:   */   {
/* 15:60 */     return new PredicatedSet(set, predicate);
/* 16:   */   }
/* 17:   */   
/* 18:   */   protected PredicatedSet(Set set, Predicate predicate)
/* 19:   */   {
/* 20:76 */     super(set, predicate);
/* 21:   */   }
/* 22:   */   
/* 23:   */   protected Set getSet()
/* 24:   */   {
/* 25:85 */     return (Set)getCollection();
/* 26:   */   }
/* 27:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.set.PredicatedSet
 * JD-Core Version:    0.7.0.1
 */