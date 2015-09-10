/*  1:   */ package org.hibernate.service.jdbc.dialect.internal;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import java.util.Map;
/*  6:   */ import org.hibernate.HibernateException;
/*  7:   */ import org.hibernate.internal.util.StringHelper;
/*  8:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  9:   */ import org.hibernate.service.jdbc.dialect.spi.DialectResolver;
/* 10:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/* 11:   */ import org.hibernate.service.spi.ServiceException;
/* 12:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/* 13:   */ 
/* 14:   */ public class DialectResolverInitiator
/* 15:   */   implements BasicServiceInitiator<DialectResolver>
/* 16:   */ {
/* 17:45 */   public static final DialectResolverInitiator INSTANCE = new DialectResolverInitiator();
/* 18:   */   
/* 19:   */   public Class<DialectResolver> getServiceInitiated()
/* 20:   */   {
/* 21:49 */     return DialectResolver.class;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public DialectResolver initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 25:   */   {
/* 26:54 */     return new DialectResolverSet(determineResolvers(configurationValues, registry));
/* 27:   */   }
/* 28:   */   
/* 29:   */   private List<DialectResolver> determineResolvers(Map configurationValues, ServiceRegistryImplementor registry)
/* 30:   */   {
/* 31:58 */     List<DialectResolver> resolvers = new ArrayList();
/* 32:   */     
/* 33:60 */     String resolverImplNames = (String)configurationValues.get("hibernate.dialect_resolvers");
/* 34:62 */     if (StringHelper.isNotEmpty(resolverImplNames))
/* 35:   */     {
/* 36:63 */       ClassLoaderService classLoaderService = (ClassLoaderService)registry.getService(ClassLoaderService.class);
/* 37:64 */       for (String resolverImplName : StringHelper.split(", \n\r\f\t", resolverImplNames)) {
/* 38:   */         try
/* 39:   */         {
/* 40:66 */           resolvers.add((DialectResolver)classLoaderService.classForName(resolverImplName).newInstance());
/* 41:   */         }
/* 42:   */         catch (HibernateException e)
/* 43:   */         {
/* 44:69 */           throw e;
/* 45:   */         }
/* 46:   */         catch (Exception e)
/* 47:   */         {
/* 48:72 */           throw new ServiceException("Unable to instantiate named dialect resolver [" + resolverImplName + "]", e);
/* 49:   */         }
/* 50:   */       }
/* 51:   */     }
/* 52:77 */     resolvers.add(new StandardDialectResolver());
/* 53:78 */     return resolvers;
/* 54:   */   }
/* 55:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.DialectResolverInitiator
 * JD-Core Version:    0.7.0.1
 */