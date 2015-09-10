/*  1:   */ package org.hibernate.service.instrumentation.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  5:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ 
/*  8:   */ public class InstrumentationServiceInitiator
/*  9:   */   implements BasicServiceInitiator<InstrumentationService>
/* 10:   */ {
/* 11:15 */   public static final InstrumentationServiceInitiator INSTANCE = new InstrumentationServiceInitiator();
/* 12:   */   
/* 13:   */   public InstrumentationService initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 14:   */   {
/* 15:19 */     return new CachingInstrumentationService();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Class<InstrumentationService> getServiceInitiated()
/* 19:   */   {
/* 20:24 */     return InstrumentationService.class;
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.instrumentation.internal.InstrumentationServiceInitiator
 * JD-Core Version:    0.7.0.1
 */