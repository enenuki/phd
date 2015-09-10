/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import javassist.util.proxy.MethodFilter;
/*   6:    */ import javassist.util.proxy.MethodHandler;
/*   7:    */ import javassist.util.proxy.ProxyObject;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.bytecode.spi.BasicProxyFactory;
/*  11:    */ import org.hibernate.bytecode.spi.ProxyFactoryFactory;
/*  12:    */ import org.hibernate.proxy.pojo.javassist.JavassistProxyFactory;
/*  13:    */ 
/*  14:    */ public class ProxyFactoryFactoryImpl
/*  15:    */   implements ProxyFactoryFactory
/*  16:    */ {
/*  17:    */   public org.hibernate.proxy.ProxyFactory buildProxyFactory()
/*  18:    */   {
/*  19: 53 */     return new JavassistProxyFactory();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public BasicProxyFactory buildBasicProxyFactory(Class superClass, Class[] interfaces)
/*  23:    */   {
/*  24: 57 */     return new BasicProxyFactoryImpl(superClass, interfaces);
/*  25:    */   }
/*  26:    */   
/*  27:    */   private static class BasicProxyFactoryImpl
/*  28:    */     implements BasicProxyFactory
/*  29:    */   {
/*  30:    */     private final Class proxyClass;
/*  31:    */     
/*  32:    */     public BasicProxyFactoryImpl(Class superClass, Class[] interfaces)
/*  33:    */     {
/*  34: 64 */       if ((superClass == null) && ((interfaces == null) || (interfaces.length < 1))) {
/*  35: 65 */         throw new AssertionFailure("attempting to build proxy without any superclass or interfaces");
/*  36:    */       }
/*  37: 67 */       javassist.util.proxy.ProxyFactory factory = new javassist.util.proxy.ProxyFactory();
/*  38: 68 */       factory.setFilter(ProxyFactoryFactoryImpl.FINALIZE_FILTER);
/*  39: 69 */       if (superClass != null) {
/*  40: 70 */         factory.setSuperclass(superClass);
/*  41:    */       }
/*  42: 72 */       if ((interfaces != null) && (interfaces.length > 0)) {
/*  43: 73 */         factory.setInterfaces(interfaces);
/*  44:    */       }
/*  45: 75 */       this.proxyClass = factory.createClass();
/*  46:    */     }
/*  47:    */     
/*  48:    */     public Object getProxy()
/*  49:    */     {
/*  50:    */       try
/*  51:    */       {
/*  52: 80 */         ProxyObject proxy = (ProxyObject)this.proxyClass.newInstance();
/*  53: 81 */         proxy.setHandler(new ProxyFactoryFactoryImpl.PassThroughHandler(proxy, this.proxyClass.getName()));
/*  54: 82 */         return proxy;
/*  55:    */       }
/*  56:    */       catch (Throwable t)
/*  57:    */       {
/*  58: 85 */         throw new HibernateException("Unable to instantiated proxy instance");
/*  59:    */       }
/*  60:    */     }
/*  61:    */     
/*  62:    */     public boolean isInstance(Object object)
/*  63:    */     {
/*  64: 90 */       return this.proxyClass.isInstance(object);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68: 94 */   private static final MethodFilter FINALIZE_FILTER = new MethodFilter()
/*  69:    */   {
/*  70:    */     public boolean isHandled(Method m)
/*  71:    */     {
/*  72: 97 */       return (m.getParameterTypes().length != 0) || (!m.getName().equals("finalize"));
/*  73:    */     }
/*  74:    */   };
/*  75:    */   
/*  76:    */   private static class PassThroughHandler
/*  77:    */     implements MethodHandler
/*  78:    */   {
/*  79:102 */     private HashMap data = new HashMap();
/*  80:    */     private final Object proxiedObject;
/*  81:    */     private final String proxiedClassName;
/*  82:    */     
/*  83:    */     public PassThroughHandler(Object proxiedObject, String proxiedClassName)
/*  84:    */     {
/*  85:107 */       this.proxiedObject = proxiedObject;
/*  86:108 */       this.proxiedClassName = proxiedClassName;
/*  87:    */     }
/*  88:    */     
/*  89:    */     public Object invoke(Object object, Method method, Method method1, Object[] args)
/*  90:    */       throws Exception
/*  91:    */     {
/*  92:116 */       String name = method.getName();
/*  93:117 */       if ("toString".equals(name)) {
/*  94:118 */         return this.proxiedClassName + "@" + System.identityHashCode(object);
/*  95:    */       }
/*  96:120 */       if ("equals".equals(name)) {
/*  97:121 */         return Boolean.valueOf(this.proxiedObject == object);
/*  98:    */       }
/*  99:123 */       if ("hashCode".equals(name)) {
/* 100:124 */         return Integer.valueOf(System.identityHashCode(object));
/* 101:    */       }
/* 102:126 */       boolean hasGetterSignature = (method.getParameterTypes().length == 0) && (method.getReturnType() != null);
/* 103:127 */       boolean hasSetterSignature = (method.getParameterTypes().length == 1) && ((method.getReturnType() == null) || (method.getReturnType() == Void.TYPE));
/* 104:128 */       if ((name.startsWith("get")) && (hasGetterSignature))
/* 105:    */       {
/* 106:129 */         String propName = name.substring(3);
/* 107:130 */         return this.data.get(propName);
/* 108:    */       }
/* 109:132 */       if ((name.startsWith("is")) && (hasGetterSignature))
/* 110:    */       {
/* 111:133 */         String propName = name.substring(2);
/* 112:134 */         return this.data.get(propName);
/* 113:    */       }
/* 114:136 */       if ((name.startsWith("set")) && (hasSetterSignature))
/* 115:    */       {
/* 116:137 */         String propName = name.substring(3);
/* 117:138 */         this.data.put(propName, args[0]);
/* 118:139 */         return null;
/* 119:    */       }
/* 120:143 */       return null;
/* 121:    */     }
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.ProxyFactoryFactoryImpl
 * JD-Core Version:    0.7.0.1
 */