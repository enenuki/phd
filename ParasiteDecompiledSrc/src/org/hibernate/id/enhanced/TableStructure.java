/*   1:    */ package org.hibernate.id.enhanced;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*  11:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  12:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  13:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  14:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  15:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  16:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  17:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  18:    */ import org.hibernate.id.IdentifierGenerationException;
/*  19:    */ import org.hibernate.id.IdentifierGeneratorHelper;
/*  20:    */ import org.hibernate.id.IntegralDataTypeHolder;
/*  21:    */ import org.hibernate.internal.CoreMessageLogger;
/*  22:    */ import org.hibernate.jdbc.AbstractReturningWork;
/*  23:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  24:    */ import org.jboss.logging.Logger;
/*  25:    */ 
/*  26:    */ public class TableStructure
/*  27:    */   implements DatabaseStructure
/*  28:    */ {
/*  29: 54 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TableStructure.class.getName());
/*  30:    */   private final String tableName;
/*  31:    */   private final String valueColumnName;
/*  32:    */   private final int initialValue;
/*  33:    */   private final int incrementSize;
/*  34:    */   private final Class numberType;
/*  35:    */   private final String selectQuery;
/*  36:    */   private final String updateQuery;
/*  37:    */   private boolean applyIncrementSizeToSourceValues;
/*  38:    */   private int accessCounter;
/*  39:    */   
/*  40:    */   public TableStructure(Dialect dialect, String tableName, String valueColumnName, int initialValue, int incrementSize, Class numberType)
/*  41:    */   {
/*  42: 74 */     this.tableName = tableName;
/*  43: 75 */     this.initialValue = initialValue;
/*  44: 76 */     this.incrementSize = incrementSize;
/*  45: 77 */     this.valueColumnName = valueColumnName;
/*  46: 78 */     this.numberType = numberType;
/*  47:    */     
/*  48: 80 */     this.selectQuery = ("select " + valueColumnName + " as id_val" + " from " + dialect.appendLockHint(LockMode.PESSIMISTIC_WRITE, tableName) + dialect.getForUpdateString());
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52: 84 */     this.updateQuery = ("update " + tableName + " set " + valueColumnName + "= ?" + " where " + valueColumnName + "=?");
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String getName()
/*  56:    */   {
/*  57: 91 */     return this.tableName;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getInitialValue()
/*  61:    */   {
/*  62: 96 */     return this.initialValue;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIncrementSize()
/*  66:    */   {
/*  67:101 */     return this.incrementSize;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getTimesAccessed()
/*  71:    */   {
/*  72:106 */     return this.accessCounter;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void prepare(Optimizer optimizer)
/*  76:    */   {
/*  77:111 */     this.applyIncrementSizeToSourceValues = optimizer.applyIncrementSizeToSourceValues();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public AccessCallback buildCallback(final SessionImplementor session)
/*  81:    */   {
/*  82:116 */     new AccessCallback()
/*  83:    */     {
/*  84:    */       public IntegralDataTypeHolder getNextValue()
/*  85:    */       {
/*  86:119 */         (IntegralDataTypeHolder)session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(new AbstractReturningWork()
/*  87:    */         {
/*  88:    */           public IntegralDataTypeHolder execute(Connection connection)
/*  89:    */             throws SQLException
/*  90:    */           {
/*  91:123 */             SqlStatementLogger statementLogger = ((JdbcServices)TableStructure.1.this.val$session.getFactory().getServiceRegistry().getService(JdbcServices.class)).getSqlStatementLogger();
/*  92:    */             
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:128 */             IntegralDataTypeHolder value = IdentifierGeneratorHelper.getIntegralDataTypeHolder(TableStructure.this.numberType);
/*  97:    */             int rows;
/*  98:    */             do
/*  99:    */             {
/* 100:131 */               statementLogger.logStatement(TableStructure.this.selectQuery, FormatStyle.BASIC.getFormatter());
/* 101:132 */               PreparedStatement selectStatement = connection.prepareStatement(TableStructure.this.selectQuery);
/* 102:    */               try
/* 103:    */               {
/* 104:134 */                 ResultSet selectRS = selectStatement.executeQuery();
/* 105:135 */                 if (!selectRS.next())
/* 106:    */                 {
/* 107:136 */                   String err = "could not read a hi value - you need to populate the table: " + TableStructure.this.tableName;
/* 108:137 */                   TableStructure.LOG.error(err);
/* 109:138 */                   throw new IdentifierGenerationException(err);
/* 110:    */                 }
/* 111:140 */                 value.initialize(selectRS, 1L);
/* 112:141 */                 selectRS.close();
/* 113:    */               }
/* 114:    */               catch (SQLException sqle)
/* 115:    */               {
/* 116:144 */                 TableStructure.LOG.error("could not read a hi value", sqle);
/* 117:145 */                 throw sqle;
/* 118:    */               }
/* 119:    */               finally
/* 120:    */               {
/* 121:148 */                 selectStatement.close();
/* 122:    */               }
/* 123:151 */               statementLogger.logStatement(TableStructure.this.updateQuery, FormatStyle.BASIC.getFormatter());
/* 124:152 */               PreparedStatement updatePS = connection.prepareStatement(TableStructure.this.updateQuery);
/* 125:    */               try
/* 126:    */               {
/* 127:154 */                 int increment = TableStructure.this.applyIncrementSizeToSourceValues ? TableStructure.this.incrementSize : 1;
/* 128:155 */                 IntegralDataTypeHolder updateValue = value.copy().add(increment);
/* 129:156 */                 updateValue.bind(updatePS, 1);
/* 130:157 */                 value.bind(updatePS, 2);
/* 131:158 */                 rows = updatePS.executeUpdate();
/* 132:    */               }
/* 133:    */               catch (SQLException e)
/* 134:    */               {
/* 135:161 */                 TableStructure.LOG.unableToUpdateQueryHiValue(TableStructure.this.tableName, e);
/* 136:162 */                 throw e;
/* 137:    */               }
/* 138:    */               finally
/* 139:    */               {
/* 140:165 */                 updatePS.close();
/* 141:    */               }
/* 142:167 */             } while (rows == 0);
/* 143:169 */             TableStructure.access$708(TableStructure.this);
/* 144:    */             
/* 145:171 */             return value;
/* 146:    */           }
/* 147:171 */         }, true);
/* 148:    */       }
/* 149:    */     };
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 153:    */     throws HibernateException
/* 154:    */   {
/* 155:182 */     return new String[] { dialect.getCreateTableString() + " " + this.tableName + " ( " + this.valueColumnName + " " + dialect.getTypeName(-5) + " )", "insert into " + this.tableName + " values ( " + this.initialValue + " )" };
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String[] sqlDropStrings(Dialect dialect)
/* 159:    */     throws HibernateException
/* 160:    */   {
/* 161:190 */     StringBuffer sqlDropString = new StringBuffer().append("drop table ");
/* 162:191 */     if (dialect.supportsIfExistsBeforeTableName()) {
/* 163:192 */       sqlDropString.append("if exists ");
/* 164:    */     }
/* 165:194 */     sqlDropString.append(this.tableName).append(dialect.getCascadeConstraintsString());
/* 166:195 */     if (dialect.supportsIfExistsAfterTableName()) {
/* 167:196 */       sqlDropString.append(" if exists");
/* 168:    */     }
/* 169:198 */     return new String[] { sqlDropString.toString() };
/* 170:    */   }
/* 171:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.TableStructure
 * JD-Core Version:    0.7.0.1
 */