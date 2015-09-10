/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Closure;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public class TransformerClosure
/*  8:   */   implements Closure, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -5194992589193388969L;
/* 11:   */   private final Transformer iTransformer;
/* 12:   */   
/* 13:   */   public static Closure getInstance(Transformer transformer)
/* 14:   */   {
/* 15:50 */     if (transformer == null) {
/* 16:51 */       return NOPClosure.INSTANCE;
/* 17:   */     }
/* 18:53 */     return new TransformerClosure(transformer);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public TransformerClosure(Transformer transformer)
/* 22:   */   {
/* 23:64 */     this.iTransformer = transformer;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void execute(Object input)
/* 27:   */   {
/* 28:73 */     this.iTransformer.transform(input);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Transformer getTransformer()
/* 32:   */   {
/* 33:83 */     return this.iTransformer;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.TransformerClosure
 * JD-Core Version:    0.7.0.1
 */