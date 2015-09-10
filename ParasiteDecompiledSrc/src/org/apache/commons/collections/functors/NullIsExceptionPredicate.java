/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.FunctorException;
/*  5:   */ import org.apache.commons.collections.Predicate;
/*  6:   */ 
/*  7:   */ public final class NullIsExceptionPredicate
/*  8:   */   implements Predicate, PredicateDecorator, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 3243449850504576071L;
/* 11:   */   private final Predicate iPredicate;
/* 12:   */   
/* 13:   */   public static Predicate getInstance(Predicate predicate)
/* 14:   */   {
/* 15:48 */     if (predicate == null) {
/* 16:49 */       throw new IllegalArgumentException("Predicate must not be null");
/* 17:   */     }
/* 18:51 */     return new NullIsExceptionPredicate(predicate);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public NullIsExceptionPredicate(Predicate predicate)
/* 22:   */   {
/* 23:62 */     this.iPredicate = predicate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean evaluate(Object object)
/* 27:   */   {
/* 28:74 */     if (object == null) {
/* 29:75 */       throw new FunctorException("Input Object must not be null");
/* 30:   */     }
/* 31:77 */     return this.iPredicate.evaluate(object);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Predicate[] getPredicates()
/* 35:   */   {
/* 36:87 */     return new Predicate[] { this.iPredicate };
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.NullIsExceptionPredicate
 * JD-Core Version:    0.7.0.1
 */