/*  1:   */ package org.hibernate.persister.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.persister.spi.PersisterClassResolver;
/*  5:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  6:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  7:   */ import org.hibernate.service.spi.ServiceException;
/*  8:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  9:   */ 
/* 10:   */ public class PersisterClassResolverInitiator
/* 11:   */   implements BasicServiceInitiator<PersisterClassResolver>
/* 12:   */ {
/* 13:38 */   public static final PersisterClassResolverInitiator INSTANCE = new PersisterClassResolverInitiator();
/* 14:   */   public static final String IMPL_NAME = "hibernate.persister.resolver";
/* 15:   */   
/* 16:   */   public Class<PersisterClassResolver> getServiceInitiated()
/* 17:   */   {
/* 18:43 */     return PersisterClassResolver.class;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public PersisterClassResolver initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 22:   */   {
/* 23:49 */     Object customImpl = configurationValues.get("hibernate.persister.resolver");
/* 24:50 */     if (customImpl == null) {
/* 25:51 */       return new StandardPersisterClassResolver();
/* 26:   */     }
/* 27:54 */     if (PersisterClassResolver.class.isInstance(customImpl)) {
/* 28:55 */       return (PersisterClassResolver)customImpl;
/* 29:   */     }
/* 30:58 */     Class<? extends PersisterClassResolver> customImplClass = Class.class.isInstance(customImpl) ? (Class)customImpl : locate(registry, customImpl.toString());
/* 31:   */     try
/* 32:   */     {
/* 33:63 */       return (PersisterClassResolver)customImplClass.newInstance();
/* 34:   */     }
/* 35:   */     catch (Exception e)
/* 36:   */     {
/* 37:66 */       throw new ServiceException("Could not initialize custom PersisterClassResolver impl [" + customImplClass.getName() + "]", e);
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   private Class<? extends PersisterClassResolver> locate(ServiceRegistryImplementor registry, String className)
/* 42:   */   {
/* 43:71 */     return ((ClassLoaderService)registry.getService(ClassLoaderService.class)).classForName(className);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.internal.PersisterClassResolverInitiator
 * JD-Core Version:    0.7.0.1
 */