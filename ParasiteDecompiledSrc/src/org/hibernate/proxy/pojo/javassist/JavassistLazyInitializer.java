/*   1:    */ package org.hibernate.proxy.pojo.javassist;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import javassist.util.proxy.MethodFilter;
/*   7:    */ import javassist.util.proxy.MethodHandler;
/*   8:    */ import javassist.util.proxy.ProxyFactory;
/*   9:    */ import javassist.util.proxy.ProxyObject;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.ReflectHelper;
/*  14:    */ import org.hibernate.proxy.HibernateProxy;
/*  15:    */ import org.hibernate.proxy.pojo.BasicLazyInitializer;
/*  16:    */ import org.hibernate.type.CompositeType;
/*  17:    */ import org.jboss.logging.Logger;
/*  18:    */ 
/*  19:    */ public class JavassistLazyInitializer
/*  20:    */   extends BasicLazyInitializer
/*  21:    */   implements MethodHandler
/*  22:    */ {
/*  23: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JavassistLazyInitializer.class.getName());
/*  24: 53 */   private static final MethodFilter FINALIZE_FILTER = new MethodFilter()
/*  25:    */   {
/*  26:    */     public boolean isHandled(Method m)
/*  27:    */     {
/*  28: 56 */       return (m.getParameterTypes().length != 0) || (!m.getName().equals("finalize"));
/*  29:    */     }
/*  30:    */   };
/*  31:    */   private Class[] interfaces;
/*  32: 61 */   private boolean constructed = false;
/*  33:    */   
/*  34:    */   private JavassistLazyInitializer(String entityName, Class persistentClass, Class[] interfaces, Serializable id, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType, SessionImplementor session, boolean overridesEquals)
/*  35:    */   {
/*  36: 73 */     super(entityName, persistentClass, id, getIdentifierMethod, setIdentifierMethod, componentIdType, session, overridesEquals);
/*  37: 74 */     this.interfaces = interfaces;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static HibernateProxy getProxy(String entityName, Class persistentClass, Class[] interfaces, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType, Serializable id, SessionImplementor session)
/*  41:    */     throws HibernateException
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45: 88 */       JavassistLazyInitializer instance = new JavassistLazyInitializer(entityName, persistentClass, interfaces, id, getIdentifierMethod, setIdentifierMethod, componentIdType, session, ReflectHelper.overridesEquals(persistentClass));
/*  46:    */       
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 99 */       ProxyFactory factory = new ProxyFactory();
/*  57:100 */       factory.setSuperclass(interfaces.length == 1 ? persistentClass : null);
/*  58:101 */       factory.setInterfaces(interfaces);
/*  59:102 */       factory.setFilter(FINALIZE_FILTER);
/*  60:103 */       Class cl = factory.createClass();
/*  61:104 */       HibernateProxy proxy = (HibernateProxy)cl.newInstance();
/*  62:105 */       ((ProxyObject)proxy).setHandler(instance);
/*  63:106 */       instance.constructed = true;
/*  64:107 */       return proxy;
/*  65:    */     }
/*  66:    */     catch (Throwable t)
/*  67:    */     {
/*  68:110 */       LOG.error(LOG.javassistEnhancementFailed(entityName), t);
/*  69:111 */       throw new HibernateException(LOG.javassistEnhancementFailed(entityName), t);
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static HibernateProxy getProxy(Class factory, String entityName, Class persistentClass, Class[] interfaces, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType, Serializable id, SessionImplementor session, boolean classOverridesEquals)
/*  74:    */     throws HibernateException
/*  75:    */   {
/*  76:127 */     JavassistLazyInitializer instance = new JavassistLazyInitializer(entityName, persistentClass, interfaces, id, getIdentifierMethod, setIdentifierMethod, componentIdType, session, classOverridesEquals);
/*  77:    */     HibernateProxy proxy;
/*  78:    */     try
/*  79:    */     {
/*  80:140 */       proxy = (HibernateProxy)factory.newInstance();
/*  81:    */     }
/*  82:    */     catch (Exception e)
/*  83:    */     {
/*  84:143 */       throw new HibernateException("Javassist Enhancement failed: " + persistentClass.getName(), e);
/*  85:    */     }
/*  86:148 */     ((ProxyObject)proxy).setHandler(instance);
/*  87:149 */     instance.constructed = true;
/*  88:150 */     return proxy;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static Class getProxyFactory(Class persistentClass, Class[] interfaces)
/*  92:    */     throws HibernateException
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:159 */       ProxyFactory factory = new ProxyFactory();
/*  97:160 */       factory.setSuperclass(interfaces.length == 1 ? persistentClass : null);
/*  98:161 */       factory.setInterfaces(interfaces);
/*  99:162 */       factory.setFilter(FINALIZE_FILTER);
/* 100:163 */       return factory.createClass();
/* 101:    */     }
/* 102:    */     catch (Throwable t)
/* 103:    */     {
/* 104:166 */       LOG.error(LOG.javassistEnhancementFailed(persistentClass.getName()), t);
/* 105:167 */       throw new HibernateException(LOG.javassistEnhancementFailed(persistentClass.getName()), t);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Object invoke(Object proxy, Method thisMethod, Method proceed, Object[] args)
/* 110:    */     throws Throwable
/* 111:    */   {
/* 112:176 */     if (this.constructed)
/* 113:    */     {
/* 114:    */       Object result;
/* 115:    */       try
/* 116:    */       {
/* 117:179 */         result = invoke(thisMethod, args, proxy);
/* 118:    */       }
/* 119:    */       catch (Throwable t)
/* 120:    */       {
/* 121:182 */         throw new Exception(t.getCause());
/* 122:    */       }
/* 123:184 */       if (result == INVOKE_IMPLEMENTATION)
/* 124:    */       {
/* 125:185 */         Object target = getImplementation();
/* 126:    */         try
/* 127:    */         {
/* 128:    */           Object returnValue;
/* 129:    */           Object returnValue;
/* 130:188 */           if (ReflectHelper.isPublic(this.persistentClass, thisMethod))
/* 131:    */           {
/* 132:189 */             if (!thisMethod.getDeclaringClass().isInstance(target)) {
/* 133:190 */               throw new ClassCastException(target.getClass().getName());
/* 134:    */             }
/* 135:192 */             returnValue = thisMethod.invoke(target, args);
/* 136:    */           }
/* 137:    */           else
/* 138:    */           {
/* 139:195 */             if (!thisMethod.isAccessible()) {
/* 140:196 */               thisMethod.setAccessible(true);
/* 141:    */             }
/* 142:198 */             returnValue = thisMethod.invoke(target, args);
/* 143:    */           }
/* 144:200 */           return returnValue == target ? proxy : returnValue;
/* 145:    */         }
/* 146:    */         catch (InvocationTargetException ite)
/* 147:    */         {
/* 148:203 */           throw ite.getTargetException();
/* 149:    */         }
/* 150:    */       }
/* 151:207 */       return result;
/* 152:    */     }
/* 153:212 */     if (thisMethod.getName().equals("getHibernateLazyInitializer")) {
/* 154:213 */       return this;
/* 155:    */     }
/* 156:216 */     return proceed.invoke(proxy, args);
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected Object serializableProxy()
/* 160:    */   {
/* 161:223 */     return new SerializableProxy(getEntityName(), this.persistentClass, this.interfaces, getIdentifier(), isReadOnlySettingAvailable() ? Boolean.valueOf(isReadOnly()) : isReadOnlyBeforeAttachedToSession(), this.getIdentifierMethod, this.setIdentifierMethod, this.componentIdType);
/* 162:    */   }
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer
 * JD-Core Version:    0.7.0.1
 */