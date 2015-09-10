/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Factory;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public class FactoryTransformer
/*  8:   */   implements Transformer, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = -6817674502475353160L;
/* 11:   */   private final Factory iFactory;
/* 12:   */   
/* 13:   */   public static Transformer getInstance(Factory factory)
/* 14:   */   {
/* 15:48 */     if (factory == null) {
/* 16:49 */       throw new IllegalArgumentException("Factory must not be null");
/* 17:   */     }
/* 18:51 */     return new FactoryTransformer(factory);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public FactoryTransformer(Factory factory)
/* 22:   */   {
/* 23:62 */     this.iFactory = factory;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object transform(Object input)
/* 27:   */   {
/* 28:73 */     return this.iFactory.create();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Factory getFactory()
/* 32:   */   {
/* 33:83 */     return this.iFactory;
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.FactoryTransformer
 * JD-Core Version:    0.7.0.1
 */