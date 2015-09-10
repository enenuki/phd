/*   1:    */ package org.hibernate.engine.jdbc.batch.internal;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.LinkedHashMap;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.engine.jdbc.batch.spi.BatchKey;
/*   9:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  10:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.hibernate.jdbc.Expectation;
/*  13:    */ import org.jboss.logging.Logger;
/*  14:    */ 
/*  15:    */ public class BatchingBatch
/*  16:    */   extends AbstractBatchImpl
/*  17:    */ {
/*  18: 44 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BatchingBatch.class.getName());
/*  19:    */   private final int batchSize;
/*  20:    */   private int batchPosition;
/*  21:    */   private int statementPosition;
/*  22:    */   private String currentStatementSql;
/*  23:    */   private PreparedStatement currentStatement;
/*  24:    */   
/*  25:    */   public BatchingBatch(BatchKey key, JdbcCoordinator jdbcCoordinator, int batchSize)
/*  26:    */   {
/*  27: 56 */     super(key, jdbcCoordinator);
/*  28: 57 */     if (!key.getExpectation().canBeBatched()) {
/*  29: 58 */       throw new HibernateException("attempting to batch an operation which cannot be batched");
/*  30:    */     }
/*  31: 60 */     this.batchSize = batchSize;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public PreparedStatement getBatchStatement(String sql, boolean callable)
/*  35:    */   {
/*  36: 68 */     this.currentStatementSql = sql;
/*  37: 69 */     this.currentStatement = super.getBatchStatement(sql, callable);
/*  38: 70 */     return this.currentStatement;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void addToBatch()
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45: 76 */       this.currentStatement.addBatch();
/*  46:    */     }
/*  47:    */     catch (SQLException e)
/*  48:    */     {
/*  49: 79 */       LOG.debugf("SQLException escaped proxy", e);
/*  50: 80 */       throw sqlExceptionHelper().convert(e, "could not perform addBatch", this.currentStatementSql);
/*  51:    */     }
/*  52: 82 */     this.statementPosition += 1;
/*  53: 83 */     if (this.statementPosition >= getKey().getBatchedStatementCount())
/*  54:    */     {
/*  55: 84 */       this.batchPosition += 1;
/*  56: 85 */       if (this.batchPosition == this.batchSize)
/*  57:    */       {
/*  58: 86 */         notifyObserversImplicitExecution();
/*  59: 87 */         performExecution();
/*  60: 88 */         this.batchPosition = 0;
/*  61:    */       }
/*  62: 90 */       this.statementPosition = 0;
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void doExecuteBatch()
/*  67:    */   {
/*  68: 96 */     if (this.batchPosition == 0)
/*  69:    */     {
/*  70: 97 */       LOG.debug("No batched statements to execute");
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74:100 */       LOG.debugf("Executing batch size: %s", Integer.valueOf(this.batchPosition));
/*  75:101 */       performExecution();
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void performExecution()
/*  80:    */   {
/*  81:    */     try
/*  82:    */     {
/*  83:107 */       for (Map.Entry<String, PreparedStatement> entry : getStatements().entrySet()) {
/*  84:    */         try
/*  85:    */         {
/*  86:109 */           PreparedStatement statement = (PreparedStatement)entry.getValue();
/*  87:110 */           checkRowCounts(statement.executeBatch(), statement);
/*  88:    */         }
/*  89:    */         catch (SQLException e)
/*  90:    */         {
/*  91:113 */           LOG.debug("SQLException escaped proxy", e);
/*  92:114 */           throw sqlExceptionHelper().convert(e, "could not perform addBatch", (String)entry.getKey());
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:    */     catch (RuntimeException re)
/*  97:    */     {
/*  98:119 */       LOG.unableToExecuteBatch(re.getMessage());
/*  99:120 */       throw re;
/* 100:    */     }
/* 101:    */     finally
/* 102:    */     {
/* 103:123 */       this.batchPosition = 0;
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void checkRowCounts(int[] rowCounts, PreparedStatement ps)
/* 108:    */     throws SQLException, HibernateException
/* 109:    */   {
/* 110:128 */     int numberOfRowCounts = rowCounts.length;
/* 111:129 */     if (numberOfRowCounts != this.batchPosition) {
/* 112:130 */       LOG.unexpectedRowCounts();
/* 113:    */     }
/* 114:132 */     for (int i = 0; i < numberOfRowCounts; i++) {
/* 115:133 */       getKey().getExpectation().verifyOutcome(rowCounts[i], ps, i);
/* 116:    */     }
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.internal.BatchingBatch
 * JD-Core Version:    0.7.0.1
 */