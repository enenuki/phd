/*  1:   */ package org.hibernate.engine.jdbc.batch.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.engine.jdbc.batch.spi.BatchBuilder;
/*  5:   */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  6:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  7:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  8:   */ import org.hibernate.service.spi.ServiceException;
/*  9:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/* 10:   */ 
/* 11:   */ public class BatchBuilderInitiator
/* 12:   */   implements BasicServiceInitiator<BatchBuilder>
/* 13:   */ {
/* 14:42 */   public static final BatchBuilderInitiator INSTANCE = new BatchBuilderInitiator();
/* 15:   */   public static final String BUILDER = "hibernate.jdbc.batch.builder";
/* 16:   */   
/* 17:   */   public Class<BatchBuilder> getServiceInitiated()
/* 18:   */   {
/* 19:47 */     return BatchBuilder.class;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public BatchBuilder initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 23:   */   {
/* 24:52 */     Object builder = configurationValues.get("hibernate.jdbc.batch.builder");
/* 25:53 */     if (builder == null) {
/* 26:54 */       return new BatchBuilderImpl(ConfigurationHelper.getInt("hibernate.jdbc.batch_size", configurationValues, 1));
/* 27:   */     }
/* 28:59 */     if (BatchBuilder.class.isInstance(builder)) {
/* 29:60 */       return (BatchBuilder)builder;
/* 30:   */     }
/* 31:63 */     String builderClassName = builder.toString();
/* 32:   */     try
/* 33:   */     {
/* 34:65 */       return (BatchBuilder)((ClassLoaderService)registry.getService(ClassLoaderService.class)).classForName(builderClassName).newInstance();
/* 35:   */     }
/* 36:   */     catch (Exception e)
/* 37:   */     {
/* 38:68 */       throw new ServiceException("Could not build explicit BatchBuilder [" + builderClassName + "]", e);
/* 39:   */     }
/* 40:   */   }
/* 41:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.internal.BatchBuilderInitiator
 * JD-Core Version:    0.7.0.1
 */