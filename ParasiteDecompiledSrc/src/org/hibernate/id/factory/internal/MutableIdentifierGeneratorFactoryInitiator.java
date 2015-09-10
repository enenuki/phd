/*  1:   */ package org.hibernate.id.factory.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
/*  5:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  6:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  7:   */ 
/*  8:   */ public class MutableIdentifierGeneratorFactoryInitiator
/*  9:   */   implements BasicServiceInitiator<MutableIdentifierGeneratorFactory>
/* 10:   */ {
/* 11:13 */   public static final MutableIdentifierGeneratorFactoryInitiator INSTANCE = new MutableIdentifierGeneratorFactoryInitiator();
/* 12:   */   
/* 13:   */   public Class<MutableIdentifierGeneratorFactory> getServiceInitiated()
/* 14:   */   {
/* 15:17 */     return MutableIdentifierGeneratorFactory.class;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MutableIdentifierGeneratorFactory initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 19:   */   {
/* 20:22 */     return new DefaultIdentifierGeneratorFactory();
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.factory.internal.MutableIdentifierGeneratorFactoryInitiator
 * JD-Core Version:    0.7.0.1
 */