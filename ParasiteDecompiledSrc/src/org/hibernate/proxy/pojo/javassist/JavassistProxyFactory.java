/*  1:   */ package org.hibernate.proxy.pojo.javassist;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.internal.util.ReflectHelper;
/*  9:   */ import org.hibernate.proxy.HibernateProxy;
/* 10:   */ import org.hibernate.proxy.ProxyFactory;
/* 11:   */ import org.hibernate.type.CompositeType;
/* 12:   */ 
/* 13:   */ public class JavassistProxyFactory
/* 14:   */   implements ProxyFactory, Serializable
/* 15:   */ {
/* 16:43 */   protected static final Class[] NO_CLASSES = new Class[0];
/* 17:   */   private Class persistentClass;
/* 18:   */   private String entityName;
/* 19:   */   private Class[] interfaces;
/* 20:   */   private Method getIdentifierMethod;
/* 21:   */   private Method setIdentifierMethod;
/* 22:   */   private CompositeType componentIdType;
/* 23:   */   private Class factory;
/* 24:   */   private boolean overridesEquals;
/* 25:   */   
/* 26:   */   public void postInstantiate(String entityName, Class persistentClass, Set interfaces, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType)
/* 27:   */     throws HibernateException
/* 28:   */   {
/* 29:60 */     this.entityName = entityName;
/* 30:61 */     this.persistentClass = persistentClass;
/* 31:62 */     this.interfaces = ((Class[])interfaces.toArray(NO_CLASSES));
/* 32:63 */     this.getIdentifierMethod = getIdentifierMethod;
/* 33:64 */     this.setIdentifierMethod = setIdentifierMethod;
/* 34:65 */     this.componentIdType = componentIdType;
/* 35:66 */     this.factory = JavassistLazyInitializer.getProxyFactory(persistentClass, this.interfaces);
/* 36:67 */     this.overridesEquals = ReflectHelper.overridesEquals(persistentClass);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public HibernateProxy getProxy(Serializable id, SessionImplementor session)
/* 40:   */     throws HibernateException
/* 41:   */   {
/* 42:73 */     return JavassistLazyInitializer.getProxy(this.factory, this.entityName, this.persistentClass, this.interfaces, this.getIdentifierMethod, this.setIdentifierMethod, this.componentIdType, id, session, this.overridesEquals);
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.pojo.javassist.JavassistProxyFactory
 * JD-Core Version:    0.7.0.1
 */