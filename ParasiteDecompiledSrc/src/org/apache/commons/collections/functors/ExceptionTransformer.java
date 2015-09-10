/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.FunctorException;
/*  5:   */ import org.apache.commons.collections.Transformer;
/*  6:   */ 
/*  7:   */ public final class ExceptionTransformer
/*  8:   */   implements Transformer, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 7179106032121985545L;
/* 11:39 */   public static final Transformer INSTANCE = new ExceptionTransformer();
/* 12:   */   
/* 13:   */   public static Transformer getInstance()
/* 14:   */   {
/* 15:48 */     return INSTANCE;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object transform(Object input)
/* 19:   */   {
/* 20:66 */     throw new FunctorException("ExceptionTransformer invoked");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ExceptionTransformer
 * JD-Core Version:    0.7.0.1
 */