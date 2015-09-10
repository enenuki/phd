/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ 
/*  6:   */ public final class NullIsTruePredicate
/*  7:   */   implements Predicate, PredicateDecorator, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = -7625133768987126273L;
/* 10:   */   private final Predicate iPredicate;
/* 11:   */   
/* 12:   */   public static Predicate getInstance(Predicate predicate)
/* 13:   */   {
/* 14:47 */     if (predicate == null) {
/* 15:48 */       throw new IllegalArgumentException("Predicate must not be null");
/* 16:   */     }
/* 17:50 */     return new NullIsTruePredicate(predicate);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public NullIsTruePredicate(Predicate predicate)
/* 21:   */   {
/* 22:61 */     this.iPredicate = predicate;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean evaluate(Object object)
/* 26:   */   {
/* 27:72 */     if (object == null) {
/* 28:73 */       return true;
/* 29:   */     }
/* 30:75 */     return this.iPredicate.evaluate(object);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Predicate[] getPredicates()
/* 34:   */   {
/* 35:85 */     return new Predicate[] { this.iPredicate };
/* 36:   */   }
/* 37:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.NullIsTruePredicate
 * JD-Core Version:    0.7.0.1
 */