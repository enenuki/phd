/*  1:   */ package org.hibernate.proxy.pojo.javassist;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.proxy.AbstractSerializableProxy;
/*  7:   */ import org.hibernate.proxy.HibernateProxy;
/*  8:   */ import org.hibernate.type.CompositeType;
/*  9:   */ 
/* 10:   */ public final class SerializableProxy
/* 11:   */   extends AbstractSerializableProxy
/* 12:   */ {
/* 13:   */   private Class persistentClass;
/* 14:   */   private Class[] interfaces;
/* 15:   */   private Class getIdentifierMethodClass;
/* 16:   */   private Class setIdentifierMethodClass;
/* 17:   */   private String getIdentifierMethodName;
/* 18:   */   private String setIdentifierMethodName;
/* 19:   */   private Class[] setIdentifierMethodParams;
/* 20:   */   private CompositeType componentIdType;
/* 21:   */   
/* 22:   */   public SerializableProxy() {}
/* 23:   */   
/* 24:   */   public SerializableProxy(String entityName, Class persistentClass, Class[] interfaces, Serializable id, Boolean readOnly, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType)
/* 25:   */   {
/* 26:59 */     super(entityName, id, readOnly);
/* 27:60 */     this.persistentClass = persistentClass;
/* 28:61 */     this.interfaces = interfaces;
/* 29:62 */     if (getIdentifierMethod != null)
/* 30:   */     {
/* 31:63 */       this.getIdentifierMethodClass = getIdentifierMethod.getDeclaringClass();
/* 32:64 */       this.getIdentifierMethodName = getIdentifierMethod.getName();
/* 33:   */     }
/* 34:66 */     if (setIdentifierMethod != null)
/* 35:   */     {
/* 36:67 */       this.setIdentifierMethodClass = setIdentifierMethod.getDeclaringClass();
/* 37:68 */       this.setIdentifierMethodName = setIdentifierMethod.getName();
/* 38:69 */       this.setIdentifierMethodParams = setIdentifierMethod.getParameterTypes();
/* 39:   */     }
/* 40:71 */     this.componentIdType = componentIdType;
/* 41:   */   }
/* 42:   */   
/* 43:   */   private Object readResolve()
/* 44:   */   {
/* 45:   */     try
/* 46:   */     {
/* 47:76 */       HibernateProxy proxy = JavassistLazyInitializer.getProxy(getEntityName(), this.persistentClass, this.interfaces, this.getIdentifierMethodName == null ? null : this.getIdentifierMethodClass.getDeclaredMethod(this.getIdentifierMethodName, (Class[])null), this.setIdentifierMethodName == null ? null : this.setIdentifierMethodClass.getDeclaredMethod(this.setIdentifierMethodName, this.setIdentifierMethodParams), this.componentIdType, getId(), null);
/* 48:   */       
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:90 */       setReadOnlyBeforeAttachedToSession((JavassistLazyInitializer)proxy.getHibernateLazyInitializer());
/* 62:91 */       return proxy;
/* 63:   */     }
/* 64:   */     catch (NoSuchMethodException nsme)
/* 65:   */     {
/* 66:94 */       throw new HibernateException("could not create proxy for entity: " + getEntityName(), nsme);
/* 67:   */     }
/* 68:   */   }
/* 69:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.pojo.javassist.SerializableProxy
 * JD-Core Version:    0.7.0.1
 */