/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Closure;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public class ClosureTransformer
/*  8:   */   implements Transformer, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 478466901448617286L;
/* 11:   */   private final Closure iClosure;
/* 12:   */   
/* 13:   */   public static Transformer getInstance(Closure closure)
/* 14:   */   {
/* 15:49 */     if (closure == null) {
/* 16:50 */       throw new IllegalArgumentException("Closure must not be null");
/* 17:   */     }
/* 18:52 */     return new ClosureTransformer(closure);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ClosureTransformer(Closure closure)
/* 22:   */   {
/* 23:63 */     this.iClosure = closure;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object transform(Object input)
/* 27:   */   {
/* 28:73 */     this.iClosure.execute(input);
/* 29:74 */     return input;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Closure getClosure()
/* 33:   */   {
/* 34:84 */     return this.iClosure;
/* 35:   */   }
/* 36:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ClosureTransformer
 * JD-Core Version:    0.7.0.1
 */