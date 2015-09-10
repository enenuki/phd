/*  1:   */ package org.hibernate.service.jdbc.connections.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.MultiTenancyStrategy;
/*  5:   */ import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
/*  6:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  7:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  8:   */ 
/*  9:   */ public class MultiTenantConnectionProviderInitiator
/* 10:   */   implements BasicServiceInitiator<MultiTenantConnectionProvider>
/* 11:   */ {
/* 12:37 */   public static final MultiTenantConnectionProviderInitiator INSTANCE = new MultiTenantConnectionProviderInitiator();
/* 13:   */   
/* 14:   */   public Class<MultiTenantConnectionProvider> getServiceInitiated()
/* 15:   */   {
/* 16:41 */     return MultiTenantConnectionProvider.class;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public MultiTenantConnectionProvider initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 20:   */   {
/* 21:46 */     if (MultiTenancyStrategy.determineMultiTenancyStrategy(configurationValues) == MultiTenancyStrategy.NONE) {}
/* 22:51 */     return null;
/* 23:   */   }
/* 24:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.internal.MultiTenantConnectionProviderInitiator
 * JD-Core Version:    0.7.0.1
 */