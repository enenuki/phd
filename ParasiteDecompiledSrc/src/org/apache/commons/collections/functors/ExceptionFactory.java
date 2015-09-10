/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Factory;
/*  5:   */ import org.apache.commons.collections.FunctorException;
/*  6:   */ 
/*  7:   */ public final class ExceptionFactory
/*  8:   */   implements Factory, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 7179106032121985545L;
/* 11:39 */   public static final Factory INSTANCE = new ExceptionFactory();
/* 12:   */   
/* 13:   */   public static Factory getInstance()
/* 14:   */   {
/* 15:48 */     return INSTANCE;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Object create()
/* 19:   */   {
/* 20:65 */     throw new FunctorException("ExceptionFactory invoked");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ExceptionFactory
 * JD-Core Version:    0.7.0.1
 */