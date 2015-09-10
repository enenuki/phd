/*  1:   */ package org.hibernate.cache.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.cache.spi.RegionFactory;
/*  5:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  6:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  7:   */ import org.hibernate.service.spi.ServiceException;
/*  8:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  9:   */ 
/* 10:   */ public class RegionFactoryInitiator
/* 11:   */   implements BasicServiceInitiator<RegionFactory>
/* 12:   */ {
/* 13:40 */   public static final RegionFactoryInitiator INSTANCE = new RegionFactoryInitiator();
/* 14:   */   public static final String IMPL_NAME = "hibernate.cache.region.factory_class";
/* 15:   */   
/* 16:   */   public Class<RegionFactory> getServiceInitiated()
/* 17:   */   {
/* 18:49 */     return RegionFactory.class;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public RegionFactory initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 22:   */   {
/* 23:55 */     Object impl = configurationValues.get("hibernate.cache.region.factory_class");
/* 24:56 */     if (impl == null) {
/* 25:57 */       return new NoCachingRegionFactory();
/* 26:   */     }
/* 27:60 */     if (getServiceInitiated().isInstance(impl)) {
/* 28:61 */       return (RegionFactory)impl;
/* 29:   */     }
/* 30:64 */     Class<? extends RegionFactory> customImplClass = null;
/* 31:65 */     if (Class.class.isInstance(impl)) {
/* 32:66 */       customImplClass = (Class)impl;
/* 33:   */     } else {
/* 34:69 */       customImplClass = ((ClassLoaderService)registry.getService(ClassLoaderService.class)).classForName(mapLegacyNames(impl.toString()));
/* 35:   */     }
/* 36:   */     try
/* 37:   */     {
/* 38:74 */       return (RegionFactory)customImplClass.newInstance();
/* 39:   */     }
/* 40:   */     catch (Exception e)
/* 41:   */     {
/* 42:77 */       throw new ServiceException("Could not initialize custom RegionFactory impl [" + customImplClass.getName() + "]", e);
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static String mapLegacyNames(String name)
/* 47:   */   {
/* 48:86 */     if ("org.hibernate.cache.EhCacheRegionFactory".equals(name)) {
/* 49:87 */       return "org.hibernate.cache.ehcache.EhCacheRegionFactory";
/* 50:   */     }
/* 51:90 */     if ("org.hibernate.cache.SingletonEhCacheRegionFactory".equals(name)) {
/* 52:91 */       return "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory";
/* 53:   */     }
/* 54:94 */     return name;
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.internal.RegionFactoryInitiator
 * JD-Core Version:    0.7.0.1
 */