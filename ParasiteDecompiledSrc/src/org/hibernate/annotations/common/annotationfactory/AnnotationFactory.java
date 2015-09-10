/*  1:   */ package org.hibernate.annotations.common.annotationfactory;
/*  2:   */ 
/*  3:   */ import java.lang.annotation.Annotation;
/*  4:   */ import java.lang.reflect.Constructor;
/*  5:   */ import java.lang.reflect.InvocationHandler;
/*  6:   */ import java.lang.reflect.InvocationTargetException;
/*  7:   */ import java.lang.reflect.Proxy;
/*  8:   */ 
/*  9:   */ public class AnnotationFactory
/* 10:   */ {
/* 11:   */   public static <T extends Annotation> T create(AnnotationDescriptor descriptor)
/* 12:   */   {
/* 13:43 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 14:   */     
/* 15:   */ 
/* 16:46 */     Class<T> proxyClass = Proxy.getProxyClass(classLoader, new Class[] { descriptor.type() });
/* 17:47 */     InvocationHandler handler = new AnnotationProxy(descriptor);
/* 18:   */     try
/* 19:   */     {
/* 20:49 */       return getProxyInstance(proxyClass, handler);
/* 21:   */     }
/* 22:   */     catch (RuntimeException e)
/* 23:   */     {
/* 24:52 */       throw e;
/* 25:   */     }
/* 26:   */     catch (Exception e)
/* 27:   */     {
/* 28:55 */       throw new RuntimeException(e);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   private static <T extends Annotation> T getProxyInstance(Class<T> proxyClass, InvocationHandler handler)
/* 33:   */     throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
/* 34:   */   {
/* 35:62 */     Constructor<T> constructor = proxyClass.getConstructor(new Class[] { InvocationHandler.class });
/* 36:63 */     return (Annotation)constructor.newInstance(new Object[] { handler });
/* 37:   */   }
/* 38:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.annotationfactory.AnnotationFactory
 * JD-Core Version:    0.7.0.1
 */