/*  1:   */ package org.hibernate.engine.jdbc.batch.internal;
/*  2:   */ 
/*  3:   */ import java.util.Map;
/*  4:   */ import org.hibernate.engine.jdbc.batch.spi.Batch;
/*  5:   */ import org.hibernate.engine.jdbc.batch.spi.BatchBuilder;
/*  6:   */ import org.hibernate.engine.jdbc.batch.spi.BatchKey;
/*  7:   */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  8:   */ import org.hibernate.internal.CoreMessageLogger;
/*  9:   */ import org.hibernate.internal.util.config.ConfigurationHelper;
/* 10:   */ import org.hibernate.service.spi.Configurable;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public class BatchBuilderImpl
/* 14:   */   implements BatchBuilder, Configurable
/* 15:   */ {
/* 16:46 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BatchBuilderImpl.class.getName());
/* 17:   */   private int size;
/* 18:   */   
/* 19:   */   public BatchBuilderImpl() {}
/* 20:   */   
/* 21:   */   public void configure(Map configurationValues)
/* 22:   */   {
/* 23:55 */     this.size = ConfigurationHelper.getInt("hibernate.jdbc.batch_size", configurationValues, this.size);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BatchBuilderImpl(int size)
/* 27:   */   {
/* 28:59 */     this.size = size;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void setJdbcBatchSize(int size)
/* 32:   */   {
/* 33:63 */     this.size = size;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Batch buildBatch(BatchKey key, JdbcCoordinator jdbcCoordinator)
/* 37:   */   {
/* 38:68 */     LOG.tracef("Building batch [size=%s]", Integer.valueOf(this.size));
/* 39:69 */     return this.size > 1 ? new BatchingBatch(key, jdbcCoordinator, this.size) : new NonBatchingBatch(key, jdbcCoordinator);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getManagementDomain()
/* 43:   */   {
/* 44:76 */     return null;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String getManagementServiceType()
/* 48:   */   {
/* 49:81 */     return null;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Object getManagementBean()
/* 53:   */   {
/* 54:86 */     return this;
/* 55:   */   }
/* 56:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.internal.BatchBuilderImpl
 * JD-Core Version:    0.7.0.1
 */