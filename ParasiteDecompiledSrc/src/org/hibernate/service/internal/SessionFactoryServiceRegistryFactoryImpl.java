/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.Configuration;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ import org.hibernate.service.spi.SessionFactoryServiceRegistryFactory;
/*  8:   */ 
/*  9:   */ public class SessionFactoryServiceRegistryFactoryImpl
/* 10:   */   implements SessionFactoryServiceRegistryFactory
/* 11:   */ {
/* 12:   */   private final ServiceRegistryImplementor theBasicServiceRegistry;
/* 13:   */   
/* 14:   */   public SessionFactoryServiceRegistryFactoryImpl(ServiceRegistryImplementor theBasicServiceRegistry)
/* 15:   */   {
/* 16:43 */     this.theBasicServiceRegistry = theBasicServiceRegistry;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public SessionFactoryServiceRegistryImpl buildServiceRegistry(SessionFactoryImplementor sessionFactory, Configuration configuration)
/* 20:   */   {
/* 21:50 */     return new SessionFactoryServiceRegistryImpl(this.theBasicServiceRegistry, sessionFactory, configuration);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public SessionFactoryServiceRegistryImpl buildServiceRegistry(SessionFactoryImplementor sessionFactory, MetadataImplementor metadata)
/* 25:   */   {
/* 26:57 */     return new SessionFactoryServiceRegistryImpl(this.theBasicServiceRegistry, sessionFactory, metadata);
/* 27:   */   }
/* 28:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.SessionFactoryServiceRegistryFactoryImpl
 * JD-Core Version:    0.7.0.1
 */