/*  1:   */ package org.hibernate.service.jmx.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  5:   */ import org.hibernate.service.jmx.spi.JmxService;
/*  6:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  7:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  8:   */ 
/*  9:   */ public class JmxServiceInitiator
/* 10:   */   implements BasicServiceInitiator<JmxService>
/* 11:   */ {
/* 12:40 */   public static final JmxServiceInitiator INSTANCE = new JmxServiceInitiator();
/* 13:   */   
/* 14:   */   public Class<JmxService> getServiceInitiated()
/* 15:   */   {
/* 16:44 */     return JmxService.class;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public JmxService initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 20:   */   {
/* 21:49 */     return ConfigurationHelper.getBoolean("hibernate.jmx.enabled", configurationValues, false) ? new JmxServiceImpl(configurationValues) : DisabledJmxServiceImpl.INSTANCE;
/* 22:   */   }
/* 23:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jmx.internal.JmxServiceInitiator
 * JD-Core Version:    0.7.0.1
 */