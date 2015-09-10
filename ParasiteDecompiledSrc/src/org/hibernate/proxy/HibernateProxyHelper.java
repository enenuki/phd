/*  1:   */ package org.hibernate.proxy;
/*  2:   */ 
/*  3:   */ public final class HibernateProxyHelper
/*  4:   */ {
/*  5:   */   public static Class getClassWithoutInitializingProxy(Object object)
/*  6:   */   {
/*  7:41 */     if ((object instanceof HibernateProxy))
/*  8:   */     {
/*  9:42 */       HibernateProxy proxy = (HibernateProxy)object;
/* 10:43 */       LazyInitializer li = proxy.getHibernateLazyInitializer();
/* 11:44 */       return li.getPersistentClass();
/* 12:   */     }
/* 13:47 */     return object.getClass();
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.HibernateProxyHelper
 * JD-Core Version:    0.7.0.1
 */