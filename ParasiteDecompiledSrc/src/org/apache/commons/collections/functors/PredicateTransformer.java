/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Predicate;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public class PredicateTransformer
/*  8:   */   implements Transformer, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 5278818408044349346L;
/* 11:   */   private final Predicate iPredicate;
/* 12:   */   
/* 13:   */   public static Transformer getInstance(Predicate predicate)
/* 14:   */   {
/* 15:49 */     if (predicate == null) {
/* 16:50 */       throw new IllegalArgumentException("Predicate must not be null");
/* 17:   */     }
/* 18:52 */     return new PredicateTransformer(predicate);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public PredicateTransformer(Predicate predicate)
/* 22:   */   {
/* 23:63 */     this.iPredicate = predicate;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object transform(Object input)
/* 27:   */   {
/* 28:73 */     return this.iPredicate.evaluate(input) ? Boolean.TRUE : Boolean.FALSE;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Predicate getPredicate()
/* 32:   */   {
/* 33:83 */     return this.iPredicate;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.PredicateTransformer
 * JD-Core Version:    0.7.0.1
 */