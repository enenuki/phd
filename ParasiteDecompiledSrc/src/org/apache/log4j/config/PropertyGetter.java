/*   1:    */ package org.apache.log4j.config;
/*   2:    */ 
/*   3:    */ import java.beans.BeanInfo;
/*   4:    */ import java.beans.FeatureDescriptor;
/*   5:    */ import java.beans.IntrospectionException;
/*   6:    */ import java.beans.Introspector;
/*   7:    */ import java.beans.PropertyDescriptor;
/*   8:    */ import java.io.InterruptedIOException;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import org.apache.log4j.Priority;
/*  12:    */ import org.apache.log4j.helpers.LogLog;
/*  13:    */ 
/*  14:    */ public class PropertyGetter
/*  15:    */ {
/*  16: 38 */   protected static final Object[] NULL_ARG = new Object[0];
/*  17:    */   protected Object obj;
/*  18:    */   protected PropertyDescriptor[] props;
/*  19:    */   
/*  20:    */   public PropertyGetter(Object obj)
/*  21:    */     throws IntrospectionException
/*  22:    */   {
/*  23: 55 */     BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
/*  24: 56 */     this.props = bi.getPropertyDescriptors();
/*  25: 57 */     this.obj = obj;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static void getProperties(Object obj, PropertyCallback callback, String prefix)
/*  29:    */   {
/*  30:    */     try
/*  31:    */     {
/*  32: 64 */       new PropertyGetter(obj).getProperties(callback, prefix);
/*  33:    */     }
/*  34:    */     catch (IntrospectionException ex)
/*  35:    */     {
/*  36: 66 */       LogLog.error("Failed to introspect object " + obj, ex);
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void getProperties(PropertyCallback callback, String prefix)
/*  41:    */   {
/*  42: 72 */     for (int i = 0; i < this.props.length; i++)
/*  43:    */     {
/*  44: 73 */       Method getter = this.props[i].getReadMethod();
/*  45: 74 */       if ((getter != null) && 
/*  46: 75 */         (isHandledType(getter.getReturnType())))
/*  47:    */       {
/*  48: 79 */         String name = this.props[i].getName();
/*  49:    */         try
/*  50:    */         {
/*  51: 81 */           Object result = getter.invoke(this.obj, NULL_ARG);
/*  52: 83 */           if (result != null) {
/*  53: 84 */             callback.foundProperty(this.obj, prefix, name, result);
/*  54:    */           }
/*  55:    */         }
/*  56:    */         catch (IllegalAccessException ex)
/*  57:    */         {
/*  58: 87 */           LogLog.warn("Failed to get value of property " + name);
/*  59:    */         }
/*  60:    */         catch (InvocationTargetException ex)
/*  61:    */         {
/*  62: 89 */           if (((ex.getTargetException() instanceof InterruptedException)) || ((ex.getTargetException() instanceof InterruptedIOException))) {
/*  63: 91 */             Thread.currentThread().interrupt();
/*  64:    */           }
/*  65: 93 */           LogLog.warn("Failed to get value of property " + name);
/*  66:    */         }
/*  67:    */         catch (RuntimeException ex)
/*  68:    */         {
/*  69: 95 */           LogLog.warn("Failed to get value of property " + name);
/*  70:    */         }
/*  71:    */       }
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   protected boolean isHandledType(Class type)
/*  76:    */   {
/*  77:102 */     return (String.class.isAssignableFrom(type)) || (Integer.TYPE.isAssignableFrom(type)) || (Long.TYPE.isAssignableFrom(type)) || (Boolean.TYPE.isAssignableFrom(type)) || (Priority.class.isAssignableFrom(type));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static abstract interface PropertyCallback
/*  81:    */   {
/*  82:    */     public abstract void foundProperty(Object paramObject1, String paramString1, String paramString2, Object paramObject2);
/*  83:    */   }
/*  84:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.config.PropertyGetter
 * JD-Core Version:    0.7.0.1
 */