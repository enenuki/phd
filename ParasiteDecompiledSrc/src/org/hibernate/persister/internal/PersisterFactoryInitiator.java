/*  1:   */ package org.hibernate.persister.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.persister.spi.PersisterFactory;
/*  5:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  6:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  7:   */ import org.hibernate.service.spi.ServiceException;
/*  8:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  9:   */ 
/* 10:   */ public class PersisterFactoryInitiator
/* 11:   */   implements BasicServiceInitiator<PersisterFactory>
/* 12:   */ {
/* 13:38 */   public static final PersisterFactoryInitiator INSTANCE = new PersisterFactoryInitiator();
/* 14:   */   public static final String IMPL_NAME = "hibernate.persister.factory";
/* 15:   */   
/* 16:   */   public Class<PersisterFactory> getServiceInitiated()
/* 17:   */   {
/* 18:44 */     return PersisterFactory.class;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public PersisterFactory initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 22:   */   {
/* 23:50 */     Object customImpl = configurationValues.get("hibernate.persister.factory");
/* 24:51 */     if (customImpl == null) {
/* 25:52 */       return new PersisterFactoryImpl();
/* 26:   */     }
/* 27:55 */     if (PersisterFactory.class.isInstance(customImpl)) {
/* 28:56 */       return (PersisterFactory)customImpl;
/* 29:   */     }
/* 30:59 */     Class<? extends PersisterFactory> customImplClass = Class.class.isInstance(customImpl) ? (Class)customImpl : locate(registry, customImpl.toString());
/* 31:   */     try
/* 32:   */     {
/* 33:63 */       return (PersisterFactory)customImplClass.newInstance();
/* 34:   */     }
/* 35:   */     catch (Exception e)
/* 36:   */     {
/* 37:66 */       throw new ServiceException("Could not initialize custom PersisterFactory impl [" + customImplClass.getName() + "]", e);
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   private Class<? extends PersisterFactory> locate(ServiceRegistryImplementor registry, String className)
/* 42:   */   {
/* 43:71 */     return ((ClassLoaderService)registry.getService(ClassLoaderService.class)).classForName(className);
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.internal.PersisterFactoryInitiator
 * JD-Core Version:    0.7.0.1
 */