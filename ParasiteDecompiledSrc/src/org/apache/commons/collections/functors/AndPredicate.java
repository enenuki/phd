/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ 
/*  6:   */ public final class AndPredicate
/*  7:   */   implements Predicate, PredicateDecorator, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 4189014213763186912L;
/* 10:   */   private final Predicate iPredicate1;
/* 11:   */   private final Predicate iPredicate2;
/* 12:   */   
/* 13:   */   public static Predicate getInstance(Predicate predicate1, Predicate predicate2)
/* 14:   */   {
/* 15:50 */     if ((predicate1 == null) || (predicate2 == null)) {
/* 16:51 */       throw new IllegalArgumentException("Predicate must not be null");
/* 17:   */     }
/* 18:53 */     return new AndPredicate(predicate1, predicate2);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public AndPredicate(Predicate predicate1, Predicate predicate2)
/* 22:   */   {
/* 23:65 */     this.iPredicate1 = predicate1;
/* 24:66 */     this.iPredicate2 = predicate2;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean evaluate(Object object)
/* 28:   */   {
/* 29:76 */     return (this.iPredicate1.evaluate(object)) && (this.iPredicate2.evaluate(object));
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Predicate[] getPredicates()
/* 33:   */   {
/* 34:86 */     return new Predicate[] { this.iPredicate1, this.iPredicate2 };
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.AndPredicate
 * JD-Core Version:    0.7.0.1
 */