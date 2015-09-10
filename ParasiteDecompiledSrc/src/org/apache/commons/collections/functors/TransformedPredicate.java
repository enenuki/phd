/*   1:    */ package org.apache.commons.collections.functors;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.apache.commons.collections.Predicate;
/*   5:    */ import org.apache.commons.collections.Transformer;
/*   6:    */ 
/*   7:    */ public final class TransformedPredicate
/*   8:    */   implements Predicate, PredicateDecorator, Serializable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = -5596090919668315834L;
/*  11:    */   private final Transformer iTransformer;
/*  12:    */   private final Predicate iPredicate;
/*  13:    */   
/*  14:    */   public static Predicate getInstance(Transformer transformer, Predicate predicate)
/*  15:    */   {
/*  16: 52 */     if (transformer == null) {
/*  17: 53 */       throw new IllegalArgumentException("The transformer to call must not be null");
/*  18:    */     }
/*  19: 55 */     if (predicate == null) {
/*  20: 56 */       throw new IllegalArgumentException("The predicate to call must not be null");
/*  21:    */     }
/*  22: 58 */     return new TransformedPredicate(transformer, predicate);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public TransformedPredicate(Transformer transformer, Predicate predicate)
/*  26:    */   {
/*  27: 69 */     this.iTransformer = transformer;
/*  28: 70 */     this.iPredicate = predicate;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean evaluate(Object object)
/*  32:    */   {
/*  33: 81 */     Object result = this.iTransformer.transform(object);
/*  34: 82 */     return this.iPredicate.evaluate(result);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public Predicate[] getPredicates()
/*  38:    */   {
/*  39: 92 */     return new Predicate[] { this.iPredicate };
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Transformer getTransformer()
/*  43:    */   {
/*  44:101 */     return this.iTransformer;
/*  45:    */   }
/*  46:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.TransformedPredicate
 * JD-Core Version:    0.7.0.1
 */