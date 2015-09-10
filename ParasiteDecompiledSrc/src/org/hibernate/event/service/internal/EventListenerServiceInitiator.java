/*  1:   */ package org.hibernate.event.service.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.cfg.Configuration;
/*  4:   */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  5:   */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  6:   */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  7:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  8:   */ import org.hibernate.service.spi.SessionFactoryServiceInitiator;
/*  9:   */ 
/* 10:   */ public class EventListenerServiceInitiator
/* 11:   */   implements SessionFactoryServiceInitiator<EventListenerRegistry>
/* 12:   */ {
/* 13:39 */   public static final EventListenerServiceInitiator INSTANCE = new EventListenerServiceInitiator();
/* 14:   */   
/* 15:   */   public Class<EventListenerRegistry> getServiceInitiated()
/* 16:   */   {
/* 17:43 */     return EventListenerRegistry.class;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public EventListenerRegistry initiateService(SessionFactoryImplementor sessionFactory, Configuration configuration, ServiceRegistryImplementor registry)
/* 21:   */   {
/* 22:51 */     return new EventListenerRegistryImpl();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public EventListenerRegistry initiateService(SessionFactoryImplementor sessionFactory, MetadataImplementor metadata, ServiceRegistryImplementor registry)
/* 26:   */   {
/* 27:59 */     return new EventListenerRegistryImpl();
/* 28:   */   }
/* 29:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.service.internal.EventListenerServiceInitiator
 * JD-Core Version:    0.7.0.1
 */