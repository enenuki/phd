/*  1:   */ package org.hibernate.service.internal;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import java.util.Map;
/*  5:   */ import org.hibernate.service.BootstrapServiceRegistry;
/*  6:   */ import org.hibernate.service.Service;
/*  7:   */ import org.hibernate.service.ServiceRegistry;
/*  8:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  9:   */ import org.hibernate.service.spi.Configurable;
/* 10:   */ import org.hibernate.service.spi.ServiceBinding;
/* 11:   */ import org.hibernate.service.spi.ServiceInitiator;
/* 12:   */ 
/* 13:   */ public class StandardServiceRegistryImpl
/* 14:   */   extends AbstractServiceRegistryImpl
/* 15:   */   implements ServiceRegistry
/* 16:   */ {
/* 17:   */   private final Map configurationValues;
/* 18:   */   
/* 19:   */   public StandardServiceRegistryImpl(BootstrapServiceRegistry bootstrapServiceRegistry, List<BasicServiceInitiator> serviceInitiators, List<ProvidedService> providedServices, Map<?, ?> configurationValues)
/* 20:   */   {
/* 21:51 */     super(bootstrapServiceRegistry);
/* 22:   */     
/* 23:53 */     this.configurationValues = configurationValues;
/* 24:56 */     for (ServiceInitiator initiator : serviceInitiators) {
/* 25:57 */       createServiceBinding(initiator);
/* 26:   */     }
/* 27:61 */     for (ProvidedService providedService : providedServices) {
/* 28:62 */       createServiceBinding(providedService);
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public <R extends Service> R initiateService(ServiceInitiator<R> serviceInitiator)
/* 33:   */   {
/* 34:69 */     return ((BasicServiceInitiator)serviceInitiator).initiateService(this.configurationValues, this);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public <R extends Service> void configureService(ServiceBinding<R> serviceBinding)
/* 38:   */   {
/* 39:74 */     if (Configurable.class.isInstance(serviceBinding.getService())) {
/* 40:75 */       ((Configurable)serviceBinding.getService()).configure(this.configurationValues);
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.StandardServiceRegistryImpl
 * JD-Core Version:    0.7.0.1
 */