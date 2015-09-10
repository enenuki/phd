/*  1:   */ package org.apache.commons.collections.functors;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.apache.commons.collections.Closure;
/*  5:   */ import org.apache.commons.collections.FunctorException;
/*  6:   */ 
/*  7:   */ public final class ExceptionClosure
/*  8:   */   implements Closure, Serializable
/*  9:   */ {
/* 10:   */   private static final long serialVersionUID = 7179106032121985545L;
/* 11:39 */   public static final Closure INSTANCE = new ExceptionClosure();
/* 12:   */   
/* 13:   */   public static Closure getInstance()
/* 14:   */   {
/* 15:48 */     return INSTANCE;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void execute(Object input)
/* 19:   */   {
/* 20:65 */     throw new FunctorException("ExceptionClosure invoked");
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.ExceptionClosure
 * JD-Core Version:    0.7.0.1
 */