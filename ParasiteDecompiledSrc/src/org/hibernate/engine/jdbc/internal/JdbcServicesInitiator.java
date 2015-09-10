/*  1:   */ package org.hibernate.engine.jdbc.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  5:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ 
/*  8:   */ public class JdbcServicesInitiator
/*  9:   */   implements BasicServiceInitiator<JdbcServices>
/* 10:   */ {
/* 11:40 */   public static final JdbcServicesInitiator INSTANCE = new JdbcServicesInitiator();
/* 12:   */   
/* 13:   */   public Class<JdbcServices> getServiceInitiated()
/* 14:   */   {
/* 15:44 */     return JdbcServices.class;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public JdbcServices initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 19:   */   {
/* 20:49 */     return new JdbcServicesImpl();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.JdbcServicesInitiator
 * JD-Core Version:    0.7.0.1
 */