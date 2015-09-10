/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.FunctorException;
/*  5:   */ import org.apache.commons.collections.Predicate;
/*  6:   */ import org.apache.commons.collections.Transformer;
/*  7:   */ 
/*  8:   */ public final class TransformerPredicate
/*  9:   */   implements Predicate, Serializable
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -2407966402920578741L;
/* 12:   */   private final Transformer iTransformer;
/* 13:   */   
/* 14:   */   public static Predicate getInstance(Transformer transformer)
/* 15:   */   {
/* 16:49 */     if (transformer == null) {
/* 17:50 */       throw new IllegalArgumentException("The transformer to call must not be null");
/* 18:   */     }
/* 19:52 */     return new TransformerPredicate(transformer);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public TransformerPredicate(Transformer transformer)
/* 23:   */   {
/* 24:63 */     this.iTransformer = transformer;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public boolean evaluate(Object object)
/* 28:   */   {
/* 29:74 */     Object result = this.iTransformer.transform(object);
/* 30:75 */     if (!(result instanceof Boolean)) {
/* 31:76 */       throw new FunctorException("Transformer must return an instanceof Boolean, it was a " + (result == null ? "null object" : result.getClass().getName()));
/* 32:   */     }
/* 33:80 */     return ((Boolean)result).booleanValue();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Transformer getTransformer()
/* 37:   */   {
/* 38:90 */     return this.iTransformer;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.TransformerPredicate
 * JD-Core Version:    0.7.0.1
 */