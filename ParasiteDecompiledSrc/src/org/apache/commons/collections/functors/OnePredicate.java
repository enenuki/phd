/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public final class OnePredicate
/*   8:    */   implements Predicate, PredicateDecorator, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -8125389089924745785L;
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
/*  22: 65 */     predicates = FunctorUtils.copy(predicates);
/*  23: 66 */     return new OnePredicate(predicates);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Predicate getInstance(Collection predicates)
/*  27:    */   {
/*  28: 78 */     Predicate[] preds = FunctorUtils.validate(predicates);
/*  29: 79 */     return new OnePredicate(preds);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public OnePredicate(Predicate[] predicates)
/*  33:    */   {
/*  34: 90 */     this.iPredicates = predicates;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean evaluate(Object object)
/*  38:    */   {
/*  39:101 */     boolean match = false;
/*  40:102 */     for (int i = 0; i < this.iPredicates.length; i++) {
/*  41:103 */       if (this.iPredicates[i].evaluate(object))
/*  42:    */       {
/*  43:104 */         if (match) {
/*  44:105 */           return false;
/*  45:    */         }
/*  46:107 */         match = true;
/*  47:    */       }
/*  48:    */     }
/*  49:110 */     return match;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Predicate[] getPredicates()
/*  53:    */   {
/*  54:120 */     return this.iPredicates;
/*  55:    */   }
/*  56:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.OnePredicate
 * JD-Core Version:    0.7.0.1
 */