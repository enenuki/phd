/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Collection;
/*   5:    */ import org.apache.commons.collections.Predicate;
/*   6:    */ 
/*   7:    */ public final class NonePredicate
/*   8:    */   implements Predicate, PredicateDecorator, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 2007613066565892961L;
/*  11:    */   private final Predicate[] iPredicates;
/*  12:    */   
/*  13:    */   public static Predicate getInstance(Predicate[] predicates)
/*  14:    */   {
/*  15: 57 */     FunctorUtils.validate(predicates);
/*  16: 58 */     if (predicates.length == 0) {
/*  17: 59 */       return TruePredicate.INSTANCE;
/*  18:    */     }
/*  19: 61 */     predicates = FunctorUtils.copy(predicates);
/*  20: 62 */     return new NonePredicate(predicates);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static Predicate getInstance(Collection predicates)
/*  24:    */   {
/*  25: 76 */     Predicate[] preds = FunctorUtils.validate(predicates);
/*  26: 77 */     if (preds.length == 0) {
/*  27: 78 */       return TruePredicate.INSTANCE;
/*  28:    */     }
/*  29: 80 */     return new NonePredicate(preds);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public NonePredicate(Predicate[] predicates)
/*  33:    */   {
/*  34: 91 */     this.iPredicates = predicates;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean evaluate(Object object)
/*  38:    */   {
/*  39:101 */     for (int i = 0; i < this.iPredicates.length; i++) {
/*  40:102 */       if (this.iPredicates[i].evaluate(object)) {
/*  41:103 */         return false;
/*  42:    */       }
/*  43:    */     }
/*  44:106 */     return true;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Predicate[] getPredicates()
/*  48:    */   {
/*  49:116 */     return this.iPredicates;
/*  50:    */   }
/*  51:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.NonePredicate
 * JD-Core Version:    0.7.0.1
 */