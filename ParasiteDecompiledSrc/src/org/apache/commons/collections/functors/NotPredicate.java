/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ 
/*  6:   */ public final class NotPredicate
/*  7:   */   implements Predicate, PredicateDecorator, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -2654603322338049674L;
/* 10:   */   private final Predicate iPredicate;
/* 11:   */   
/* 12:   */   public static Predicate getInstance(Predicate predicate)
/* 13:   */   {
/* 14:47 */     if (predicate == null) {
/* 15:48 */       throw new IllegalArgumentException("Predicate must not be null");
/* 16:   */     }
/* 17:50 */     return new NotPredicate(predicate);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public NotPredicate(Predicate predicate)
/* 21:   */   {
/* 22:61 */     this.iPredicate = predicate;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean evaluate(Object object)
/* 26:   */   {
/* 27:71 */     return !this.iPredicate.evaluate(object);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Predicate[] getPredicates()
/* 31:   */   {
/* 32:81 */     return new Predicate[] { this.iPredicate };
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.NotPredicate
 * JD-Core Version:    0.7.0.1
 */