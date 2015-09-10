/*  1:   */ package org.hibernate.service.jdbc.dialect.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.service.jdbc.dialect.spi.DialectFactory;
/*  5:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ 
/*  8:   */ public class DialectFactoryInitiator
/*  9:   */   implements BasicServiceInitiator<DialectFactory>
/* 10:   */ {
/* 11:38 */   public static final DialectFactoryInitiator INSTANCE = new DialectFactoryInitiator();
/* 12:   */   
/* 13:   */   public Class<DialectFactory> getServiceInitiated()
/* 14:   */   {
/* 15:42 */     return DialectFactory.class;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public DialectFactory initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 19:   */   {
/* 20:47 */     return new DialectFactoryImpl();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.internal.DialectFactoryInitiator
 * JD-Core Version:    0.7.0.1
 */