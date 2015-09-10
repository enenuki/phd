/*  1:   */ package org.junit.internal.matchers;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Method;
/*  4:   */ import org.hamcrest.BaseMatcher;
/*  5:   */ 
/*  6:   */ public abstract class TypeSafeMatcher<T>
/*  7:   */   extends BaseMatcher<T>
/*  8:   */ {
/*  9:   */   private Class<?> expectedType;
/* 10:   */   
/* 11:   */   public abstract boolean matchesSafely(T paramT);
/* 12:   */   
/* 13:   */   protected TypeSafeMatcher()
/* 14:   */   {
/* 15:24 */     this.expectedType = findExpectedType(getClass());
/* 16:   */   }
/* 17:   */   
/* 18:   */   private static Class<?> findExpectedType(Class<?> fromClass)
/* 19:   */   {
/* 20:28 */     for (Class<?> c = fromClass; c != Object.class; c = c.getSuperclass()) {
/* 21:29 */       for (Method method : c.getDeclaredMethods()) {
/* 22:30 */         if (isMatchesSafelyMethod(method)) {
/* 23:31 */           return method.getParameterTypes()[0];
/* 24:   */         }
/* 25:   */       }
/* 26:   */     }
/* 27:36 */     throw new Error("Cannot determine correct type for matchesSafely() method.");
/* 28:   */   }
/* 29:   */   
/* 30:   */   private static boolean isMatchesSafelyMethod(Method method)
/* 31:   */   {
/* 32:40 */     return (method.getName().equals("matchesSafely")) && (method.getParameterTypes().length == 1) && (!method.isSynthetic());
/* 33:   */   }
/* 34:   */   
/* 35:   */   protected TypeSafeMatcher(Class<T> expectedType)
/* 36:   */   {
/* 37:46 */     this.expectedType = expectedType;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public final boolean matches(Object item)
/* 41:   */   {
/* 42:56 */     return (item != null) && (this.expectedType.isInstance(item)) && (matchesSafely(item));
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.matchers.TypeSafeMatcher
 * JD-Core Version:    0.7.0.1
 */