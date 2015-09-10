/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.Configuration;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  6:   */ import org.hibernate.service.Service;
/*  7:   */ import org.hibernate.service.spi.ServiceBinding;
/*  8:   */ import org.hibernate.service.spi.ServiceInitiator;
/*  9:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/* 10:   */ import org.hibernate.service.spi.SessionFactoryServiceInitiator;
/* 11:   */ import org.hibernate.service.spi.SessionFactoryServiceRegistry;
/* 12:   */ 
/* 13:   */ public class SessionFactoryServiceRegistryImpl
/* 14:   */   extends AbstractServiceRegistryImpl
/* 15:   */   implements SessionFactoryServiceRegistry
/* 16:   */ {
/* 17:   */   private final Configuration configuration;
/* 18:   */   private final MetadataImplementor metadata;
/* 19:   */   private final SessionFactoryImplementor sessionFactory;
/* 20:   */   
/* 21:   */   public SessionFactoryServiceRegistryImpl(ServiceRegistryImplementor parent, SessionFactoryImplementor sessionFactory, Configuration configuration)
/* 22:   */   {
/* 23:51 */     super(parent);
/* 24:   */     
/* 25:53 */     this.sessionFactory = sessionFactory;
/* 26:54 */     this.configuration = configuration;
/* 27:55 */     this.metadata = null;
/* 28:58 */     for (SessionFactoryServiceInitiator initiator : StandardSessionFactoryServiceInitiators.LIST) {
/* 29:60 */       createServiceBinding(initiator);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public SessionFactoryServiceRegistryImpl(ServiceRegistryImplementor parent, SessionFactoryImplementor sessionFactory, MetadataImplementor metadata)
/* 34:   */   {
/* 35:69 */     super(parent);
/* 36:   */     
/* 37:71 */     this.sessionFactory = sessionFactory;
/* 38:72 */     this.configuration = null;
/* 39:73 */     this.metadata = metadata;
/* 40:76 */     for (SessionFactoryServiceInitiator initiator : StandardSessionFactoryServiceInitiators.LIST) {
/* 41:78 */       createServiceBinding(initiator);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public <R extends Service> R initiateService(ServiceInitiator<R> serviceInitiator)
/* 46:   */   {
/* 47:85 */     SessionFactoryServiceInitiator<R> sessionFactoryServiceInitiator = (SessionFactoryServiceInitiator)serviceInitiator;
/* 48:87 */     if (this.metadata != null) {
/* 49:88 */       return sessionFactoryServiceInitiator.initiateService(this.sessionFactory, this.metadata, this);
/* 50:   */     }
/* 51:90 */     if (this.configuration != null) {
/* 52:91 */       return sessionFactoryServiceInitiator.initiateService(this.sessionFactory, this.configuration, this);
/* 53:   */     }
/* 54:94 */     throw new IllegalStateException("Both metadata and configuration are null.");
/* 55:   */   }
/* 56:   */   
/* 57:   */   public <R extends Service> void configureService(ServiceBinding<R> serviceBinding) {}
/* 58:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.SessionFactoryServiceRegistryImpl
 * JD-Core Version:    0.7.0.1
 */