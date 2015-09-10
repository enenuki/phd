/*   1:    */ package org.hibernate.engine.jdbc.internal;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.sql.Connection;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.TransactionException;
/*  10:    */ import org.hibernate.engine.jdbc.batch.spi.Batch;
/*  11:    */ import org.hibernate.engine.jdbc.batch.spi.BatchBuilder;
/*  12:    */ import org.hibernate.engine.jdbc.batch.spi.BatchKey;
/*  13:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  14:    */ import org.hibernate.engine.jdbc.spi.JdbcResourceRegistry;
/*  15:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  16:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  17:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  18:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  19:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  20:    */ import org.hibernate.engine.transaction.internal.TransactionCoordinatorImpl;
/*  21:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  22:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  23:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.jdbc.WorkExecutor;
/*  26:    */ import org.hibernate.jdbc.WorkExecutorVisitable;
/*  27:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  28:    */ import org.jboss.logging.Logger;
/*  29:    */ 
/*  30:    */ public class JdbcCoordinatorImpl
/*  31:    */   implements JdbcCoordinator
/*  32:    */ {
/*  33: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JdbcCoordinatorImpl.class.getName());
/*  34:    */   private transient TransactionCoordinatorImpl transactionCoordinator;
/*  35:    */   private final transient LogicalConnectionImpl logicalConnection;
/*  36:    */   private transient Batch currentBatch;
/*  37: 69 */   private transient long transactionTimeOutInstant = -1L;
/*  38:    */   
/*  39:    */   public JdbcCoordinatorImpl(Connection userSuppliedConnection, TransactionCoordinatorImpl transactionCoordinator)
/*  40:    */   {
/*  41: 74 */     this.transactionCoordinator = transactionCoordinator;
/*  42: 75 */     this.logicalConnection = new LogicalConnectionImpl(userSuppliedConnection, transactionCoordinator.getTransactionContext().getConnectionReleaseMode(), transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJdbcServices(), transactionCoordinator.getTransactionContext().getJdbcConnectionAccess());
/*  43:    */   }
/*  44:    */   
/*  45:    */   private JdbcCoordinatorImpl(LogicalConnectionImpl logicalConnection)
/*  46:    */   {
/*  47: 84 */     this.logicalConnection = logicalConnection;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public TransactionCoordinator getTransactionCoordinator()
/*  51:    */   {
/*  52: 89 */     return this.transactionCoordinator;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public LogicalConnectionImplementor getLogicalConnection()
/*  56:    */   {
/*  57: 94 */     return this.logicalConnection;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected TransactionEnvironment transactionEnvironment()
/*  61:    */   {
/*  62: 98 */     return getTransactionCoordinator().getTransactionContext().getTransactionEnvironment();
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected SessionFactoryImplementor sessionFactory()
/*  66:    */   {
/*  67:102 */     return transactionEnvironment().getSessionFactory();
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected BatchBuilder batchBuilder()
/*  71:    */   {
/*  72:106 */     return (BatchBuilder)sessionFactory().getServiceRegistry().getService(BatchBuilder.class);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private SqlExceptionHelper sqlExceptionHelper()
/*  76:    */   {
/*  77:110 */     return transactionEnvironment().getJdbcServices().getSqlExceptionHelper();
/*  78:    */   }
/*  79:    */   
/*  80:114 */   private int flushDepth = 0;
/*  81:    */   private transient StatementPreparer statementPreparer;
/*  82:    */   
/*  83:    */   public void flushBeginning()
/*  84:    */   {
/*  85:118 */     if (this.flushDepth == 0) {
/*  86:119 */       this.logicalConnection.disableReleases();
/*  87:    */     }
/*  88:121 */     this.flushDepth += 1;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void flushEnding()
/*  92:    */   {
/*  93:126 */     this.flushDepth -= 1;
/*  94:127 */     if (this.flushDepth < 0) {
/*  95:128 */       throw new HibernateException("Mismatched flush handling");
/*  96:    */     }
/*  97:130 */     if (this.flushDepth == 0) {
/*  98:131 */       this.logicalConnection.enableReleases();
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Connection close()
/* 103:    */   {
/* 104:137 */     if (this.currentBatch != null)
/* 105:    */     {
/* 106:138 */       LOG.closingUnreleasedBatch();
/* 107:139 */       this.currentBatch.release();
/* 108:    */     }
/* 109:141 */     return this.logicalConnection.close();
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Batch getBatch(BatchKey key)
/* 113:    */   {
/* 114:146 */     if (this.currentBatch != null)
/* 115:    */     {
/* 116:147 */       if (this.currentBatch.getKey().equals(key)) {
/* 117:148 */         return this.currentBatch;
/* 118:    */       }
/* 119:151 */       this.currentBatch.execute();
/* 120:152 */       this.currentBatch.release();
/* 121:    */     }
/* 122:155 */     this.currentBatch = batchBuilder().buildBatch(key, this);
/* 123:156 */     return this.currentBatch;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void executeBatch()
/* 127:    */   {
/* 128:161 */     if (this.currentBatch != null)
/* 129:    */     {
/* 130:162 */       this.currentBatch.execute();
/* 131:163 */       this.currentBatch.release();
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void abortBatch()
/* 136:    */   {
/* 137:169 */     if (this.currentBatch != null) {
/* 138:170 */       this.currentBatch.release();
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public StatementPreparer getStatementPreparer()
/* 143:    */   {
/* 144:178 */     if (this.statementPreparer == null) {
/* 145:179 */       this.statementPreparer = new StatementPreparerImpl(this);
/* 146:    */     }
/* 147:181 */     return this.statementPreparer;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setTransactionTimeOut(int seconds)
/* 151:    */   {
/* 152:186 */     this.transactionTimeOutInstant = (System.currentTimeMillis() + seconds * 1000);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int determineRemainingTransactionTimeOutPeriod()
/* 156:    */   {
/* 157:191 */     if (this.transactionTimeOutInstant < 0L) {
/* 158:192 */       return -1;
/* 159:    */     }
/* 160:194 */     int secondsRemaining = (int)((this.transactionTimeOutInstant - System.currentTimeMillis()) / 1000L);
/* 161:195 */     if (secondsRemaining <= 0) {
/* 162:196 */       throw new TransactionException("transaction timeout expired");
/* 163:    */     }
/* 164:198 */     return secondsRemaining;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void afterTransaction()
/* 168:    */   {
/* 169:203 */     this.logicalConnection.afterTransaction();
/* 170:204 */     this.transactionTimeOutInstant = -1L;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public <T> T coordinateWork(WorkExecutorVisitable<T> work)
/* 174:    */   {
/* 175:209 */     Connection connection = getLogicalConnection().getDistinctConnectionProxy();
/* 176:    */     try
/* 177:    */     {
/* 178:211 */       T result = work.accept(new WorkExecutor(), connection);
/* 179:212 */       getLogicalConnection().afterStatementExecution();
/* 180:213 */       return result;
/* 181:    */     }
/* 182:    */     catch (SQLException e)
/* 183:    */     {
/* 184:216 */       throw sqlExceptionHelper().convert(e, "error executing work");
/* 185:    */     }
/* 186:    */     finally
/* 187:    */     {
/* 188:    */       try
/* 189:    */       {
/* 190:220 */         if (!connection.isClosed()) {
/* 191:221 */           connection.close();
/* 192:    */         }
/* 193:    */       }
/* 194:    */       catch (SQLException e)
/* 195:    */       {
/* 196:225 */         LOG.debug("Error closing connection proxy", e);
/* 197:    */       }
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void cancelLastQuery()
/* 202:    */   {
/* 203:232 */     this.logicalConnection.getResourceRegistry().cancelLastQuery();
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void serialize(ObjectOutputStream oos)
/* 207:    */     throws IOException
/* 208:    */   {
/* 209:237 */     if (!this.logicalConnection.isReadyForSerialization()) {
/* 210:238 */       throw new HibernateException("Cannot serialize Session while connected");
/* 211:    */     }
/* 212:240 */     this.logicalConnection.serialize(oos);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static JdbcCoordinatorImpl deserialize(ObjectInputStream ois, TransactionContext transactionContext)
/* 216:    */     throws IOException, ClassNotFoundException
/* 217:    */   {
/* 218:246 */     return new JdbcCoordinatorImpl(LogicalConnectionImpl.deserialize(ois, transactionContext));
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void afterDeserialize(TransactionCoordinatorImpl transactionCoordinator)
/* 222:    */   {
/* 223:250 */     this.transactionCoordinator = transactionCoordinator;
/* 224:    */   }
/* 225:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.internal.JdbcCoordinatorImpl
 * JD-Core Version:    0.7.0.1
 */