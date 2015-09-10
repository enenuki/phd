/*  1:   */ package org.hibernate.internal.util.beans;
/*  2:   */ 
/*  3:   */ import java.beans.BeanInfo;
/*  4:   */ import java.beans.IntrospectionException;
/*  5:   */ import java.beans.Introspector;
/*  6:   */ import java.lang.reflect.InvocationTargetException;
/*  7:   */ 
/*  8:   */ public class BeanInfoHelper
/*  9:   */ {
/* 10:   */   private final Class beanClass;
/* 11:   */   private final Class stopClass;
/* 12:   */   
/* 13:   */   public BeanInfoHelper(Class beanClass)
/* 14:   */   {
/* 15:44 */     this(beanClass, Object.class);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public BeanInfoHelper(Class beanClass, Class stopClass)
/* 19:   */   {
/* 20:48 */     this.beanClass = beanClass;
/* 21:49 */     this.stopClass = stopClass;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void applyToBeanInfo(Object bean, BeanInfoDelegate delegate)
/* 25:   */   {
/* 26:53 */     if (!this.beanClass.isInstance(bean)) {
/* 27:54 */       throw new BeanIntrospectionException("Bean [" + bean + "] was not of declared bean type [" + this.beanClass.getName() + "]");
/* 28:   */     }
/* 29:57 */     visitBeanInfo(this.beanClass, this.stopClass, delegate);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void visitBeanInfo(Class beanClass, BeanInfoDelegate delegate)
/* 33:   */   {
/* 34:61 */     visitBeanInfo(beanClass, Object.class, delegate);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void visitBeanInfo(Class beanClass, Class stopClass, BeanInfoDelegate delegate)
/* 38:   */   {
/* 39:   */     try
/* 40:   */     {
/* 41:66 */       BeanInfo info = Introspector.getBeanInfo(beanClass, stopClass);
/* 42:   */       try
/* 43:   */       {
/* 44:68 */         delegate.processBeanInfo(info);
/* 45:   */       }
/* 46:   */       catch (RuntimeException e)
/* 47:   */       {
/* 48:71 */         throw e;
/* 49:   */       }
/* 50:   */       catch (InvocationTargetException e)
/* 51:   */       {
/* 52:74 */         throw new BeanIntrospectionException("Error delegating bean info use", e.getTargetException());
/* 53:   */       }
/* 54:   */       catch (Exception e)
/* 55:   */       {
/* 56:77 */         throw new BeanIntrospectionException("Error delegating bean info use", e);
/* 57:   */       }
/* 58:   */       finally
/* 59:   */       {
/* 60:80 */         Introspector.flushFromCaches(beanClass);
/* 61:   */       }
/* 62:   */     }
/* 63:   */     catch (IntrospectionException e)
/* 64:   */     {
/* 65:84 */       throw new BeanIntrospectionException("Unable to determine bean info from class [" + beanClass.getName() + "]", e);
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   public static abstract interface BeanInfoDelegate
/* 70:   */   {
/* 71:   */     public abstract void processBeanInfo(BeanInfo paramBeanInfo)
/* 72:   */       throws Exception;
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.beans.BeanInfoHelper
 * JD-Core Version:    0.7.0.1
 */