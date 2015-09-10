/*  1:   */ package org.hibernate.service.config.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.service.config.spi.ConfigurationService;
/*  5:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ 
/*  8:   */ public class ConfigurationServiceInitiator
/*  9:   */   implements BasicServiceInitiator<ConfigurationService>
/* 10:   */ {
/* 11:36 */   public static final ConfigurationServiceInitiator INSTANCE = new ConfigurationServiceInitiator();
/* 12:   */   
/* 13:   */   public ConfigurationService initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 14:   */   {
/* 15:39 */     return new ConfigurationServiceImpl(configurationValues);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Class<ConfigurationService> getServiceInitiated()
/* 19:   */   {
/* 20:44 */     return ConfigurationService.class;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.config.internal.ConfigurationServiceInitiator
 * JD-Core Version:    0.7.0.1
 */