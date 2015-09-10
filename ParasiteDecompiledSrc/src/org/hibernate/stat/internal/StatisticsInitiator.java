/*   1:    */ package org.hibernate.stat.internal;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.cfg.Configuration;
/*   6:    */ import org.hibernate.cfg.Settings;
/*   7:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   8:    */ import org.hibernate.internal.CoreMessageLogger;
/*   9:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  10:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  11:    */ import org.hibernate.service.config.spi.ConfigurationService;
/*  12:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  13:    */ import org.hibernate.service.spi.SessionFactoryServiceInitiator;
/*  14:    */ import org.hibernate.stat.spi.StatisticsFactory;
/*  15:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class StatisticsInitiator
/*  19:    */   implements SessionFactoryServiceInitiator<StatisticsImplementor>
/*  20:    */ {
/*  21: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, StatisticsInitiator.class.getName());
/*  22: 46 */   public static final StatisticsInitiator INSTANCE = new StatisticsInitiator();
/*  23:    */   public static final String STATS_BUILDER = "hibernate.stats.factory";
/*  24:    */   
/*  25:    */   public Class<StatisticsImplementor> getServiceInitiated()
/*  26:    */   {
/*  27: 56 */     return StatisticsImplementor.class;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public StatisticsImplementor initiateService(SessionFactoryImplementor sessionFactory, Configuration configuration, ServiceRegistryImplementor registry)
/*  31:    */   {
/*  32: 64 */     Object configValue = configuration.getProperties().get("hibernate.stats.factory");
/*  33: 65 */     return initiateServiceInternal(sessionFactory, configValue, registry);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public StatisticsImplementor initiateService(SessionFactoryImplementor sessionFactory, MetadataImplementor metadata, ServiceRegistryImplementor registry)
/*  37:    */   {
/*  38: 73 */     ConfigurationService configurationService = (ConfigurationService)registry.getService(ConfigurationService.class);
/*  39: 74 */     Object configValue = configurationService.getSetting("hibernate.stats.factory", null);
/*  40: 75 */     return initiateServiceInternal(sessionFactory, configValue, registry);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private StatisticsImplementor initiateServiceInternal(SessionFactoryImplementor sessionFactory, Object configValue, ServiceRegistryImplementor registry)
/*  44:    */   {
/*  45:    */     StatisticsFactory statisticsFactory;
/*  46:    */     StatisticsFactory statisticsFactory;
/*  47: 84 */     if (configValue == null)
/*  48:    */     {
/*  49: 85 */       statisticsFactory = DEFAULT_STATS_BUILDER;
/*  50:    */     }
/*  51:    */     else
/*  52:    */     {
/*  53:    */       StatisticsFactory statisticsFactory;
/*  54: 87 */       if (StatisticsFactory.class.isInstance(configValue))
/*  55:    */       {
/*  56: 88 */         statisticsFactory = (StatisticsFactory)configValue;
/*  57:    */       }
/*  58:    */       else
/*  59:    */       {
/*  60: 92 */         ClassLoaderService classLoaderService = (ClassLoaderService)registry.getService(ClassLoaderService.class);
/*  61:    */         try
/*  62:    */         {
/*  63: 94 */           statisticsFactory = (StatisticsFactory)classLoaderService.classForName(configValue.toString()).newInstance();
/*  64:    */         }
/*  65:    */         catch (HibernateException e)
/*  66:    */         {
/*  67: 97 */           throw e;
/*  68:    */         }
/*  69:    */         catch (Exception e)
/*  70:    */         {
/*  71:100 */           throw new HibernateException("Unable to instantiate specified StatisticsFactory implementation [" + configValue.toString() + "]", e);
/*  72:    */         }
/*  73:    */       }
/*  74:    */     }
/*  75:107 */     StatisticsImplementor statistics = statisticsFactory.buildStatistics(sessionFactory);
/*  76:108 */     boolean enabled = sessionFactory.getSettings().isStatisticsEnabled();
/*  77:109 */     statistics.setStatisticsEnabled(enabled);
/*  78:110 */     LOG.debugf("Statistics initialized [enabled=%s]", Boolean.valueOf(enabled));
/*  79:111 */     return statistics;
/*  80:    */   }
/*  81:    */   
/*  82:114 */   private static StatisticsFactory DEFAULT_STATS_BUILDER = new StatisticsFactory()
/*  83:    */   {
/*  84:    */     public StatisticsImplementor buildStatistics(SessionFactoryImplementor sessionFactory)
/*  85:    */     {
/*  86:117 */       return new ConcurrentStatisticsImpl(sessionFactory);
/*  87:    */     }
/*  88:    */   };
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.internal.StatisticsInitiator
 * JD-Core Version:    0.7.0.1
 */