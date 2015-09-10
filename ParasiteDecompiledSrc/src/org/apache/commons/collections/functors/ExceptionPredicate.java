/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.FunctorException;
/*  5:   */ import org.apache.commons.collections.Predicate;
/*  6:   */ 
/*  7:   */ public final class ExceptionPredicate
/*  8:   */   implements Predicate, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 7179106032121985545L;
/* 11:38 */   public static final Predicate INSTANCE = new ExceptionPredicate();
/* 12:   */   
/* 13:   */   public static Predicate getInstance()
/* 14:   */   {
/* 15:47 */     return INSTANCE;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public boolean evaluate(Object object)
/* 19:   */   {
/* 20:65 */     throw new FunctorException("ExceptionPredicate invoked");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ExceptionPredicate
 * JD-Core Version:    0.7.0.1
 */