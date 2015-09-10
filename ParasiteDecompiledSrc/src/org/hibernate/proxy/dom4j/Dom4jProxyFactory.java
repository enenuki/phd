/*  1:   */ package org.hibernate.proxy.dom4j;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.util.Set;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.proxy.HibernateProxy;
/*  9:   */ import org.hibernate.proxy.ProxyFactory;
/* 10:   */ import org.hibernate.type.CompositeType;
/* 11:   */ 
/* 12:   */ public class Dom4jProxyFactory
/* 13:   */   implements ProxyFactory
/* 14:   */ {
/* 15:   */   private String entityName;
/* 16:   */   
/* 17:   */   public void postInstantiate(String entityName, Class persistentClass, Set interfaces, Method getIdentifierMethod, Method setIdentifierMethod, CompositeType componentIdType)
/* 18:   */     throws HibernateException
/* 19:   */   {
/* 20:54 */     this.entityName = entityName;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public HibernateProxy getProxy(Serializable id, SessionImplementor session)
/* 24:   */     throws HibernateException
/* 25:   */   {
/* 26:61 */     return new Dom4jProxy(new Dom4jLazyInitializer(this.entityName, id, session));
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.dom4j.Dom4jProxyFactory
 * JD-Core Version:    0.7.0.1
 */