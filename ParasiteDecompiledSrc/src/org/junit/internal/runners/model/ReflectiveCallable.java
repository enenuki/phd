/*  1:   */ package org.junit.internal.runners.model;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ 
/*  5:   */ public abstract class ReflectiveCallable
/*  6:   */ {
/*  7:   */   public Object run()
/*  8:   */     throws Throwable
/*  9:   */   {
/* 10:   */     try
/* 11:   */     {
/* 12:15 */       return runReflectiveCall();
/* 13:   */     }
/* 14:   */     catch (InvocationTargetException e)
/* 15:   */     {
/* 16:17 */       throw e.getTargetException();
/* 17:   */     }
/* 18:   */   }
/* 19:   */   
/* 20:   */   protected abstract Object runReflectiveCall()
/* 21:   */     throws Throwable;
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.model.ReflectiveCallable
 * JD-Core Version:    0.7.0.1
 */