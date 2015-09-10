/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public final class AnyPredicate
/*   8:    */   implements Predicate, PredicateDecorator, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 7429999530934647542L;
/*  11:    */   private final Predicate[] iPredicates;
/*  12:    */   
/*  13:    */   public static Predicate getInstance(Predicate[] predicates)
/*  14:    */   {
/*  15: 58 */     FunctorUtils.validate(predicates);
/*  16: 59 */     if (predicates.length == 0) {
/*  17: 60 */       return FalsePredicate.INSTANCE;
/*  18:    */     }
/*  19: 62 */     if (predicates.length == 1) {
/*  20: 63 */       return predicates[0];
/*  21:    */     }
/*  22: 65 */     return new AnyPredicate(FunctorUtils.copy(predicates));
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Predicate getInstance(Collection predicates)
/*  26:    */   {
/*  27: 80 */     Predicate[] preds = FunctorUtils.validate(predicates);
/*  28: 81 */     if (preds.length == 0) {
/*  29: 82 */       return FalsePredicate.INSTANCE;
/*  30:    */     }
/*  31: 84 */     if (preds.length == 1) {
/*  32: 85 */       return preds[0];
/*  33:    */     }
/*  34: 87 */     return new AnyPredicate(preds);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public AnyPredicate(Predicate[] predicates)
/*  38:    */   {
/*  39: 98 */     this.iPredicates = predicates;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean evaluate(Object object)
/*  43:    */   {
/*  44:108 */     for (int i = 0; i < this.iPredicates.length; i++) {
/*  45:109 */       if (this.iPredicates[i].evaluate(object)) {
/*  46:110 */         return true;
/*  47:    */       }
/*  48:    */     }
/*  49:113 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Predicate[] getPredicates()
/*  53:    */   {
/*  54:123 */     return this.iPredicates;
/*  55:    */   }
/*  56:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.AnyPredicate
 * JD-Core Version:    0.7.0.1
 */