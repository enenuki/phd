/*  1:   */ package org.hibernate.tool.hbm2ddl;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.internal.util.StringHelper;
/*  6:   */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  7:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/*  8:   */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  9:   */ 
/* 10:   */ public class ImportSqlCommandExtractorInitiator
/* 11:   */   implements BasicServiceInitiator<ImportSqlCommandExtractor>
/* 12:   */ {
/* 13:19 */   public static final ImportSqlCommandExtractorInitiator INSTANCE = new ImportSqlCommandExtractorInitiator();
/* 14:20 */   public static final ImportSqlCommandExtractor DEFAULT_EXTRACTOR = new SingleLineSqlCommandExtractor();
/* 15:   */   
/* 16:   */   public ImportSqlCommandExtractor initiateService(Map configurationValues, ServiceRegistryImplementor registry)
/* 17:   */   {
/* 18:24 */     String extractorClassName = (String)configurationValues.get("hibernate.hbm2ddl.import_files_sql_extractor");
/* 19:25 */     if (StringHelper.isEmpty(extractorClassName)) {
/* 20:26 */       return DEFAULT_EXTRACTOR;
/* 21:   */     }
/* 22:28 */     ClassLoaderService classLoaderService = (ClassLoaderService)registry.getService(ClassLoaderService.class);
/* 23:29 */     return instantiateExplicitCommandExtractor(extractorClassName, classLoaderService);
/* 24:   */   }
/* 25:   */   
/* 26:   */   private ImportSqlCommandExtractor instantiateExplicitCommandExtractor(String extractorClassName, ClassLoaderService classLoaderService)
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:35 */       return (ImportSqlCommandExtractor)classLoaderService.classForName(extractorClassName).newInstance();
/* 31:   */     }
/* 32:   */     catch (Exception e)
/* 33:   */     {
/* 34:38 */       throw new HibernateException("Could not instantiate import sql command extractor [" + extractorClassName + "]", e);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Class<ImportSqlCommandExtractor> getServiceInitiated()
/* 39:   */   {
/* 40:46 */     return ImportSqlCommandExtractor.class;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractorInitiator
 * JD-Core Version:    0.7.0.1
 */