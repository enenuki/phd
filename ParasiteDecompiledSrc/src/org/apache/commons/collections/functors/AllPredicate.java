/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public final class AllPredicate
/*   8:    */   implements Predicate, PredicateDecorator, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -3094696765038308799L;
/*  11:    */   private final Predicate[] iPredicates;
/*  12:    */   
/*  13:    */   public static Predicate getInstance(Predicate[] predicates)
/*  14:    */   {
/*  15: 58 */     FunctorUtils.validate(predicates);
/*  16: 59 */     if (predicates.length == 0) {
/*  17: 60 */       return TruePredicate.INSTANCE;
/*  18:    */     }
/*  19: 62 */     if (predicates.length == 1) {
/*  20: 63 */       return predicates[0];
/*  21:    */     }
/*  22: 65 */     predicates = FunctorUtils.copy(predicates);
/*  23: 66 */     return new AllPredicate(predicates);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Predicate getInstance(Collection predicates)
/*  27:    */   {
/*  28: 81 */     Predicate[] preds = FunctorUtils.validate(predicates);
/*  29: 82 */     if (preds.length == 0) {
/*  30: 83 */       return TruePredicate.INSTANCE;
/*  31:    */     }
/*  32: 85 */     if (preds.length == 1) {
/*  33: 86 */       return preds[0];
/*  34:    */     }
/*  35: 88 */     return new AllPredicate(preds);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public AllPredicate(Predicate[] predicates)
/*  39:    */   {
/*  40: 99 */     this.iPredicates = predicates;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public boolean evaluate(Object object)
/*  44:    */   {
/*  45:109 */     for (int i = 0; i < this.iPredicates.length; i++) {
/*  46:110 */       if (!this.iPredicates[i].evaluate(object)) {
/*  47:111 */         return false;
/*  48:    */       }
/*  49:    */     }
/*  50:114 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public Predicate[] getPredicates()
/*  54:    */   {
/*  55:124 */     return this.iPredicates;
/*  56:    */   }
/*  57:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.AllPredicate
 * JD-Core Version:    0.7.0.1
 */