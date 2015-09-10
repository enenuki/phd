/*   1:    */ package org.hibernate.engine.jdbc.batch.internal;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.LinkedHashMap;
/*   7:    */ import java.util.LinkedHashSet;
/*   8:    */ import org.hibernate.engine.jdbc.batch.spi.Batch;
/*   9:    */ import org.hibernate.engine.jdbc.batch.spi.BatchKey;
/*  10:    */ import org.hibernate.engine.jdbc.batch.spi.BatchObserver;
/*  11:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  13:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  14:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  15:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  16:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  17:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  18:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  19:    */ import org.hibernate.internal.CoreMessageLogger;
/*  20:    */ import org.jboss.logging.Logger;
/*  21:    */ 
/*  22:    */ public abstract class AbstractBatchImpl
/*  23:    */   implements Batch
/*  24:    */ {
/*  25: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractBatchImpl.class.getName());
/*  26:    */   private final BatchKey key;
/*  27:    */   private final JdbcCoordinator jdbcCoordinator;
/*  28: 53 */   private LinkedHashMap<String, PreparedStatement> statements = new LinkedHashMap();
/*  29: 54 */   private LinkedHashSet<BatchObserver> observers = new LinkedHashSet();
/*  30:    */   
/*  31:    */   protected AbstractBatchImpl(BatchKey key, JdbcCoordinator jdbcCoordinator)
/*  32:    */   {
/*  33: 57 */     if (key == null) {
/*  34: 58 */       throw new IllegalArgumentException("batch key cannot be null");
/*  35:    */     }
/*  36: 60 */     if (jdbcCoordinator == null) {
/*  37: 61 */       throw new IllegalArgumentException("JDBC coordinator cannot be null");
/*  38:    */     }
/*  39: 63 */     this.key = key;
/*  40: 64 */     this.jdbcCoordinator = jdbcCoordinator;
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected abstract void doExecuteBatch();
/*  44:    */   
/*  45:    */   protected SqlExceptionHelper sqlExceptionHelper()
/*  46:    */   {
/*  47: 81 */     return this.jdbcCoordinator.getTransactionCoordinator().getTransactionContext().getTransactionEnvironment().getJdbcServices().getSqlExceptionHelper();
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected SqlStatementLogger sqlStatementLogger()
/*  51:    */   {
/*  52: 94 */     return this.jdbcCoordinator.getTransactionCoordinator().getTransactionContext().getTransactionEnvironment().getJdbcServices().getSqlStatementLogger();
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected LinkedHashMap<String, PreparedStatement> getStatements()
/*  56:    */   {
/*  57:107 */     return this.statements;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final BatchKey getKey()
/*  61:    */   {
/*  62:112 */     return this.key;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void addObserver(BatchObserver observer)
/*  66:    */   {
/*  67:117 */     this.observers.add(observer);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public PreparedStatement getBatchStatement(String sql, boolean callable)
/*  71:    */   {
/*  72:122 */     if (sql == null) {
/*  73:123 */       throw new IllegalArgumentException("sql must be non-null.");
/*  74:    */     }
/*  75:125 */     PreparedStatement statement = (PreparedStatement)this.statements.get(sql);
/*  76:126 */     if (statement == null)
/*  77:    */     {
/*  78:127 */       statement = buildBatchStatement(sql, callable);
/*  79:128 */       this.statements.put(sql, statement);
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:131 */       LOG.debug("Reusing batch statement");
/*  84:132 */       sqlStatementLogger().logStatement(sql);
/*  85:    */     }
/*  86:134 */     return statement;
/*  87:    */   }
/*  88:    */   
/*  89:    */   private PreparedStatement buildBatchStatement(String sql, boolean callable)
/*  90:    */   {
/*  91:138 */     sql = this.jdbcCoordinator.getTransactionCoordinator().getTransactionContext().onPrepareStatement(sql);
/*  92:    */     try
/*  93:    */     {
/*  94:140 */       if (callable) {
/*  95:141 */         return this.jdbcCoordinator.getLogicalConnection().getShareableConnectionProxy().prepareCall(sql);
/*  96:    */       }
/*  97:144 */       return this.jdbcCoordinator.getLogicalConnection().getShareableConnectionProxy().prepareStatement(sql);
/*  98:    */     }
/*  99:    */     catch (SQLException sqle)
/* 100:    */     {
/* 101:148 */       LOG.sqlExceptionEscapedProxy(sqle);
/* 102:149 */       throw sqlExceptionHelper().convert(sqle, "could not prepare batch statement", sql);
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public final void execute()
/* 107:    */   {
/* 108:155 */     notifyObserversExplicitExecution();
/* 109:156 */     if (this.statements.isEmpty()) {
/* 110:157 */       return;
/* 111:    */     }
/* 112:    */     try
/* 113:    */     {
/* 114:    */       try
/* 115:    */       {
/* 116:161 */         doExecuteBatch();
/* 117:    */       }
/* 118:    */       finally
/* 119:    */       {
/* 120:164 */         releaseStatements();
/* 121:    */       }
/* 122:    */     }
/* 123:    */     finally
/* 124:    */     {
/* 125:168 */       this.statements.clear();
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   private void releaseStatements()
/* 130:    */   {
/* 131:173 */     for (PreparedStatement statement : getStatements().values()) {
/* 132:    */       try
/* 133:    */       {
/* 134:175 */         statement.close();
/* 135:    */       }
/* 136:    */       catch (SQLException e)
/* 137:    */       {
/* 138:178 */         LOG.unableToReleaseBatchStatement();
/* 139:179 */         LOG.sqlExceptionEscapedProxy(e);
/* 140:    */       }
/* 141:    */     }
/* 142:182 */     getStatements().clear();
/* 143:    */   }
/* 144:    */   
/* 145:    */   protected final void notifyObserversExplicitExecution()
/* 146:    */   {
/* 147:189 */     for (BatchObserver observer : this.observers) {
/* 148:190 */       observer.batchExplicitlyExecuted();
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   protected final void notifyObserversImplicitExecution()
/* 153:    */   {
/* 154:198 */     for (BatchObserver observer : this.observers) {
/* 155:199 */       observer.batchImplicitlyExecuted();
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void release()
/* 160:    */   {
/* 161:205 */     if ((getStatements() != null) && (!getStatements().isEmpty())) {
/* 162:205 */       LOG.batchContainedStatementsOnRelease();
/* 163:    */     }
/* 164:206 */     releaseStatements();
/* 165:207 */     this.observers.clear();
/* 166:    */   }
/* 167:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.batch.internal.AbstractBatchImpl
 * JD-Core Version:    0.7.0.1
 */