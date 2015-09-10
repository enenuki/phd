/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Transformer;
/*  5:   */ 
/*  6:   */ public class ConstantTransformer
/*  7:   */   implements Transformer, Serializable
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 6374440726369055124L;
/* 10:41 */   public static final Transformer NULL_INSTANCE = new ConstantTransformer(null);
/* 11:   */   private final Object iConstant;
/* 12:   */   
/* 13:   */   public static Transformer getInstance(Object constantToReturn)
/* 14:   */   {
/* 15:53 */     if (constantToReturn == null) {
/* 16:54 */       return NULL_INSTANCE;
/* 17:   */     }
/* 18:56 */     return new ConstantTransformer(constantToReturn);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public ConstantTransformer(Object constantToReturn)
/* 22:   */   {
/* 23:67 */     this.iConstant = constantToReturn;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object transform(Object input)
/* 27:   */   {
/* 28:77 */     return this.iConstant;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object getConstant()
/* 32:   */   {
/* 33:87 */     return this.iConstant;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ConstantTransformer
 * JD-Core Version:    0.7.0.1
 */