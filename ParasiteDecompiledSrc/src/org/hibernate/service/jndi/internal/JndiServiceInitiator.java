/*  1:   */ package org.hibernate.service.jndi.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.service.jndi.spi.JndiService;
/*  5:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ 
/*  8:   */ public class JndiServiceInitiator
/*  9:   */   implements BasicServiceInitiator<JndiService>
/* 10:   */ {
/* 11:38 */   public static final JndiServiceInitiator INSTANCE = new JndiServiceInitiator();
/* 12:   */   
/* 13:   */   public Class<JndiService> getServiceInitiated()
/* 14:   */   {
/* 15:42 */     return JndiService.class;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public JndiService initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 19:   */   {
/* 20:47 */     return new JndiServiceImpl(configurationValues);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jndi.internal.JndiServiceInitiator
 * JD-Core Version:    0.7.0.1
 */