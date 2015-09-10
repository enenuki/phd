/*   1:    */ package org.hibernate.service.internal;
/*   2:    */ 
/*   3:    */ import java.util.LinkedHashSet;
/*   4:    */ import org.hibernate.integrator.internal.IntegratorServiceImpl;
/*   5:    */ import org.hibernate.integrator.spi.Integrator;
/*   6:    */ import org.hibernate.integrator.spi.IntegratorService;
/*   7:    */ import org.hibernate.service.BootstrapServiceRegistry;
/*   8:    */ import org.hibernate.service.Service;
/*   9:    */ import org.hibernate.service.ServiceRegistry;
/*  10:    */ import org.hibernate.service.classloading.internal.ClassLoaderServiceImpl;
/*  11:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  12:    */ import org.hibernate.service.spi.ServiceBinding;
/*  13:    */ import org.hibernate.service.spi.ServiceBinding.ServiceLifecycleOwner;
/*  14:    */ import org.hibernate.service.spi.ServiceException;
/*  15:    */ import org.hibernate.service.spi.ServiceInitiator;
/*  16:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  17:    */ 
/*  18:    */ public class BootstrapServiceRegistryImpl
/*  19:    */   implements ServiceRegistryImplementor, BootstrapServiceRegistry, ServiceBinding.ServiceLifecycleOwner
/*  20:    */ {
/*  21: 51 */   private static final LinkedHashSet<Integrator> NO_INTEGRATORS = new LinkedHashSet();
/*  22:    */   private final ServiceBinding<ClassLoaderService> classLoaderServiceBinding;
/*  23:    */   private final ServiceBinding<IntegratorService> integratorServiceBinding;
/*  24:    */   
/*  25:    */   public BootstrapServiceRegistryImpl()
/*  26:    */   {
/*  27: 57 */     this(new ClassLoaderServiceImpl(), NO_INTEGRATORS);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public BootstrapServiceRegistryImpl(ClassLoaderService classLoaderService, IntegratorService integratorService)
/*  31:    */   {
/*  32: 63 */     this.classLoaderServiceBinding = new ServiceBinding(this, ClassLoaderService.class, classLoaderService);
/*  33:    */     
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38: 69 */     this.integratorServiceBinding = new ServiceBinding(this, IntegratorService.class, integratorService);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public BootstrapServiceRegistryImpl(ClassLoaderService classLoaderService, LinkedHashSet<Integrator> providedIntegrators)
/*  42:    */   {
/*  43: 80 */     this(classLoaderService, new IntegratorServiceImpl(providedIntegrators, classLoaderService));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public <R extends Service> R getService(Class<R> serviceRole)
/*  47:    */   {
/*  48: 87 */     ServiceBinding<R> binding = locateServiceBinding(serviceRole);
/*  49: 88 */     return binding == null ? null : binding.getService();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public <R extends Service> ServiceBinding<R> locateServiceBinding(Class<R> serviceRole)
/*  53:    */   {
/*  54: 94 */     if (ClassLoaderService.class.equals(serviceRole)) {
/*  55: 95 */       return this.classLoaderServiceBinding;
/*  56:    */     }
/*  57: 97 */     if (IntegratorService.class.equals(serviceRole)) {
/*  58: 98 */       return this.integratorServiceBinding;
/*  59:    */     }
/*  60:101 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void destroy() {}
/*  64:    */   
/*  65:    */   public ServiceRegistry getParentServiceRegistry()
/*  66:    */   {
/*  67:110 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public <R extends Service> R initiateService(ServiceInitiator<R> serviceInitiator)
/*  71:    */   {
/*  72:115 */     throw new ServiceException("Boot-strap registry should only contain provided services");
/*  73:    */   }
/*  74:    */   
/*  75:    */   public <R extends Service> void configureService(ServiceBinding<R> binding)
/*  76:    */   {
/*  77:120 */     throw new ServiceException("Boot-strap registry should only contain provided services");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public <R extends Service> void injectDependencies(ServiceBinding<R> binding)
/*  81:    */   {
/*  82:125 */     throw new ServiceException("Boot-strap registry should only contain provided services");
/*  83:    */   }
/*  84:    */   
/*  85:    */   public <R extends Service> void startService(ServiceBinding<R> binding)
/*  86:    */   {
/*  87:130 */     throw new ServiceException("Boot-strap registry should only contain provided services");
/*  88:    */   }
/*  89:    */   
/*  90:    */   public <R extends Service> void stopService(ServiceBinding<R> binding)
/*  91:    */   {
/*  92:135 */     throw new ServiceException("Boot-strap registry should only contain provided services");
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.internal.BootstrapServiceRegistryImpl
 * JD-Core Version:    0.7.0.1
 */