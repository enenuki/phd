/*  1:   */ package org.apache.http.util;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ 
/*  5:   */ public final class ExceptionUtils
/*  6:   */ {
/*  7:40 */   private static final Method INIT_CAUSE_METHOD = ;
/*  8:   */   
/*  9:   */   private static Method getInitCauseMethod()
/* 10:   */   {
/* 11:   */     try
/* 12:   */     {
/* 13:53 */       Class[] paramsClasses = { Throwable.class };
/* 14:54 */       return Throwable.class.getMethod("initCause", paramsClasses);
/* 15:   */     }
/* 16:   */     catch (NoSuchMethodException e) {}
/* 17:56 */     return null;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static void initCause(Throwable throwable, Throwable cause)
/* 21:   */   {
/* 22:67 */     if (INIT_CAUSE_METHOD != null) {
/* 23:   */       try
/* 24:   */       {
/* 25:69 */         INIT_CAUSE_METHOD.invoke(throwable, new Object[] { cause });
/* 26:   */       }
/* 27:   */       catch (Exception e) {}
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.util.ExceptionUtils
 * JD-Core Version:    0.7.0.1
 */