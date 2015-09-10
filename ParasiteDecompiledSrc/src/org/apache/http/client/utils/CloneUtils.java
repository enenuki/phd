/*  1:   */ package org.apache.http.client.utils;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import org.apache.http.annotation.Immutable;
/*  6:   */ 
/*  7:   */ @Immutable
/*  8:   */ public class CloneUtils
/*  9:   */ {
/* 10:   */   public static Object clone(Object obj)
/* 11:   */     throws CloneNotSupportedException
/* 12:   */   {
/* 13:46 */     if (obj == null) {
/* 14:47 */       return null;
/* 15:   */     }
/* 16:49 */     if ((obj instanceof Cloneable))
/* 17:   */     {
/* 18:50 */       Class<?> clazz = obj.getClass();
/* 19:   */       Method m;
/* 20:   */       try
/* 21:   */       {
/* 22:53 */         m = clazz.getMethod("clone", (Class[])null);
/* 23:   */       }
/* 24:   */       catch (NoSuchMethodException ex)
/* 25:   */       {
/* 26:55 */         throw new NoSuchMethodError(ex.getMessage());
/* 27:   */       }
/* 28:   */       try
/* 29:   */       {
/* 30:58 */         return m.invoke(obj, (Object[])null);
/* 31:   */       }
/* 32:   */       catch (InvocationTargetException ex)
/* 33:   */       {
/* 34:60 */         Throwable cause = ex.getCause();
/* 35:61 */         if ((cause instanceof CloneNotSupportedException)) {
/* 36:62 */           throw ((CloneNotSupportedException)cause);
/* 37:   */         }
/* 38:64 */         throw new Error("Unexpected exception", cause);
/* 39:   */       }
/* 40:   */       catch (IllegalAccessException ex)
/* 41:   */       {
/* 42:67 */         throw new IllegalAccessError(ex.getMessage());
/* 43:   */       }
/* 44:   */     }
/* 45:70 */     throw new CloneNotSupportedException();
/* 46:   */   }
/* 47:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.http.client.utils.CloneUtils
 * JD-Core Version:    0.7.0.1
 */