/*  1:   */ package org.hibernate.service;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.List;
/*  6:   */ import org.hibernate.cache.internal.RegionFactoryInitiator;
/*  7:   */ import org.hibernate.engine.jdbc.batch.internal.BatchBuilderInitiator;
/*  8:   */ import org.hibernate.engine.jdbc.internal.JdbcServicesInitiator;
/*  9:   */ import org.hibernate.engine.transaction.internal.TransactionFactoryInitiator;
/* 10:   */ import org.hibernate.id.factory.internal.MutableIdentifierGeneratorFactoryInitiator;
/* 11:   */ import org.hibernate.persister.internal.PersisterClassResolverInitiator;
/* 12:   */ import org.hibernate.persister.internal.PersisterFactoryInitiator;
/* 13:   */ import org.hibernate.service.config.internal.ConfigurationServiceInitiator;
/* 14:   */ import org.hibernate.service.instrumentation.internal.InstrumentationServiceInitiator;
/* 15:   */ import org.hibernate.service.internal.SessionFactoryServiceRegistryFactoryInitiator;
/* 16:   */ import org.hibernate.service.jdbc.connections.internal.ConnectionProviderInitiator;
/* 17:   */ import org.hibernate.service.jdbc.connections.internal.MultiTenantConnectionProviderInitiator;
/* 18:   */ import org.hibernate.service.jdbc.dialect.internal.DialectFactoryInitiator;
/* 19:   */ import org.hibernate.service.jdbc.dialect.internal.DialectResolverInitiator;
/* 20:   */ import org.hibernate.service.jmx.internal.JmxServiceInitiator;
/* 21:   */ import org.hibernate.service.jndi.internal.JndiServiceInitiator;
/* 22:   */ import org.hibernate.service.jta.platform.internal.JtaPlatformInitiator;
/* 23:   */ import org.hibernate.service.spi.BasicServiceInitiator;
/* 24:   */ import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractorInitiator;
/* 25:   */ 
/* 26:   */ public class StandardServiceInitiators
/* 27:   */ {
/* 28:56 */   public static List<BasicServiceInitiator> LIST = ;
/* 29:   */   
/* 30:   */   private static List<BasicServiceInitiator> buildStandardServiceInitiatorList()
/* 31:   */   {
/* 32:59 */     List<BasicServiceInitiator> serviceInitiators = new ArrayList();
/* 33:   */     
/* 34:61 */     serviceInitiators.add(ConfigurationServiceInitiator.INSTANCE);
/* 35:   */     
/* 36:63 */     serviceInitiators.add(JndiServiceInitiator.INSTANCE);
/* 37:64 */     serviceInitiators.add(JmxServiceInitiator.INSTANCE);
/* 38:   */     
/* 39:66 */     serviceInitiators.add(PersisterClassResolverInitiator.INSTANCE);
/* 40:67 */     serviceInitiators.add(PersisterFactoryInitiator.INSTANCE);
/* 41:   */     
/* 42:69 */     serviceInitiators.add(ConnectionProviderInitiator.INSTANCE);
/* 43:70 */     serviceInitiators.add(MultiTenantConnectionProviderInitiator.INSTANCE);
/* 44:71 */     serviceInitiators.add(DialectResolverInitiator.INSTANCE);
/* 45:72 */     serviceInitiators.add(DialectFactoryInitiator.INSTANCE);
/* 46:73 */     serviceInitiators.add(BatchBuilderInitiator.INSTANCE);
/* 47:74 */     serviceInitiators.add(JdbcServicesInitiator.INSTANCE);
/* 48:   */     
/* 49:76 */     serviceInitiators.add(MutableIdentifierGeneratorFactoryInitiator.INSTANCE);
/* 50:   */     
/* 51:78 */     serviceInitiators.add(JtaPlatformInitiator.INSTANCE);
/* 52:79 */     serviceInitiators.add(TransactionFactoryInitiator.INSTANCE);
/* 53:   */     
/* 54:81 */     serviceInitiators.add(SessionFactoryServiceRegistryFactoryInitiator.INSTANCE);
/* 55:   */     
/* 56:83 */     serviceInitiators.add(RegionFactoryInitiator.INSTANCE);
/* 57:   */     
/* 58:85 */     serviceInitiators.add(InstrumentationServiceInitiator.INSTANCE);
/* 59:   */     
/* 60:87 */     serviceInitiators.add(ImportSqlCommandExtractorInitiator.INSTANCE);
/* 61:   */     
/* 62:89 */     return Collections.unmodifiableList(serviceInitiators);
/* 63:   */   }
/* 64:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.StandardServiceInitiators
 * JD-Core Version:    0.7.0.1
 */