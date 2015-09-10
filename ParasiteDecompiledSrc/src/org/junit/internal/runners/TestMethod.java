/*  1:   */ package org.junit.internal.runners;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.InvocationTargetException;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.util.List;
/*  6:   */ import org.junit.After;
/*  7:   */ import org.junit.Before;
/*  8:   */ import org.junit.Ignore;
/*  9:   */ import org.junit.Test;
/* 10:   */ import org.junit.Test.None;
/* 11:   */ 
/* 12:   */ @Deprecated
/* 13:   */ public class TestMethod
/* 14:   */ {
/* 15:   */   private final Method fMethod;
/* 16:   */   private TestClass fTestClass;
/* 17:   */   
/* 18:   */   public TestMethod(Method method, TestClass testClass)
/* 19:   */   {
/* 20:25 */     this.fMethod = method;
/* 21:26 */     this.fTestClass = testClass;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean isIgnored()
/* 25:   */   {
/* 26:30 */     return this.fMethod.getAnnotation(Ignore.class) != null;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public long getTimeout()
/* 30:   */   {
/* 31:34 */     Test annotation = (Test)this.fMethod.getAnnotation(Test.class);
/* 32:35 */     if (annotation == null) {
/* 33:36 */       return 0L;
/* 34:   */     }
/* 35:37 */     long timeout = annotation.timeout();
/* 36:38 */     return timeout;
/* 37:   */   }
/* 38:   */   
/* 39:   */   protected Class<? extends Throwable> getExpectedException()
/* 40:   */   {
/* 41:42 */     Test annotation = (Test)this.fMethod.getAnnotation(Test.class);
/* 42:43 */     if ((annotation == null) || (annotation.expected() == Test.None.class)) {
/* 43:44 */       return null;
/* 44:   */     }
/* 45:46 */     return annotation.expected();
/* 46:   */   }
/* 47:   */   
/* 48:   */   boolean isUnexpected(Throwable exception)
/* 49:   */   {
/* 50:50 */     return !getExpectedException().isAssignableFrom(exception.getClass());
/* 51:   */   }
/* 52:   */   
/* 53:   */   boolean expectsException()
/* 54:   */   {
/* 55:54 */     return getExpectedException() != null;
/* 56:   */   }
/* 57:   */   
/* 58:   */   List<Method> getBefores()
/* 59:   */   {
/* 60:58 */     return this.fTestClass.getAnnotatedMethods(Before.class);
/* 61:   */   }
/* 62:   */   
/* 63:   */   List<Method> getAfters()
/* 64:   */   {
/* 65:62 */     return this.fTestClass.getAnnotatedMethods(After.class);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public void invoke(Object test)
/* 69:   */     throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
/* 70:   */   {
/* 71:66 */     this.fMethod.invoke(test, new Object[0]);
/* 72:   */   }
/* 73:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.junit.internal.runners.TestMethod
 * JD-Core Version:    0.7.0.1
 */