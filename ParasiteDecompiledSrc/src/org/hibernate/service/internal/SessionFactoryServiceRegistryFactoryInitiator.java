/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  5:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  6:   */ import org.hibernate.service.spi.SessionFactoryServiceRegistryFactory;
/*  7:   */ 
/*  8:   */ public class SessionFactoryServiceRegistryFactoryInitiator
/*  9:   */   implements BasicServiceInitiator<SessionFactoryServiceRegistryFactory>
/* 10:   */ {
/* 11:36 */   public static final SessionFactoryServiceRegistryFactoryInitiator INSTANCE = new SessionFactoryServiceRegistryFactoryInitiator();
/* 12:   */   
/* 13:   */   public Class<SessionFactoryServiceRegistryFactory> getServiceInitiated()
/* 14:   */   {
/* 15:40 */     return SessionFactoryServiceRegistryFactory.class;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public SessionFactoryServiceRegistryFactoryImpl initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 19:   */   {
/* 20:45 */     return new SessionFactoryServiceRegistryFactoryImpl(registry);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.SessionFactoryServiceRegistryFactoryInitiator
 * JD-Core Version:    0.7.0.1
 */