/*  1:   */ package org.hibernate.service.spi;
/*  2:   */ 
/*  3:   */ import org.hibernate.service.Service;
/*  4:   */ import org.jboss.logging.Logger;
/*  5:   */ 
/*  6:   */ public final class ServiceBinding<R extends Service>
/*  7:   */ {
/*  8:36 */   private static final Logger log = Logger.getLogger(ServiceBinding.class);
/*  9:   */   private final ServiceLifecycleOwner lifecycleOwner;
/* 10:   */   private final Class<R> serviceRole;
/* 11:   */   private final ServiceInitiator<R> serviceInitiator;
/* 12:   */   private R service;
/* 13:   */   
/* 14:   */   public ServiceBinding(ServiceLifecycleOwner lifecycleOwner, Class<R> serviceRole, R service)
/* 15:   */   {
/* 16:54 */     this.lifecycleOwner = lifecycleOwner;
/* 17:55 */     this.serviceRole = serviceRole;
/* 18:56 */     this.serviceInitiator = null;
/* 19:57 */     this.service = service;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public ServiceBinding(ServiceLifecycleOwner lifecycleOwner, ServiceInitiator<R> serviceInitiator)
/* 23:   */   {
/* 24:61 */     this.lifecycleOwner = lifecycleOwner;
/* 25:62 */     this.serviceRole = serviceInitiator.getServiceInitiated();
/* 26:63 */     this.serviceInitiator = serviceInitiator;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public ServiceLifecycleOwner getLifecycleOwner()
/* 30:   */   {
/* 31:67 */     return this.lifecycleOwner;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Class<R> getServiceRole()
/* 35:   */   {
/* 36:71 */     return this.serviceRole;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public ServiceInitiator<R> getServiceInitiator()
/* 40:   */   {
/* 41:75 */     return this.serviceInitiator;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public R getService()
/* 45:   */   {
/* 46:79 */     return this.service;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void setService(R service)
/* 50:   */   {
/* 51:83 */     if ((this.service != null) && 
/* 52:84 */       (log.isDebugEnabled())) {
/* 53:85 */       log.debug("Overriding existing service binding [" + this.serviceRole.getName() + "]");
/* 54:   */     }
/* 55:88 */     this.service = service;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static abstract interface ServiceLifecycleOwner
/* 59:   */   {
/* 60:   */     public abstract <R extends Service> R initiateService(ServiceInitiator<R> paramServiceInitiator);
/* 61:   */     
/* 62:   */     public abstract <R extends Service> void configureService(ServiceBinding<R> paramServiceBinding);
/* 63:   */     
/* 64:   */     public abstract <R extends Service> void injectDependencies(ServiceBinding<R> paramServiceBinding);
/* 65:   */     
/* 66:   */     public abstract <R extends Service> void startService(ServiceBinding<R> paramServiceBinding);
/* 67:   */     
/* 68:   */     public abstract <R extends Service> void stopService(ServiceBinding<R> paramServiceBinding);
/* 69:   */   }
/* 70:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.ServiceBinding
 * JD-Core Version:    0.7.0.1
 */