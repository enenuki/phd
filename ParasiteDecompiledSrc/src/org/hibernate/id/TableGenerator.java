/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Properties;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.LockMode;
/*  11:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  12:    */ import org.hibernate.dialect.Dialect;
/*  13:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*  14:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  15:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  18:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  19:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  20:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  21:    */ import org.hibernate.internal.CoreMessageLogger;
/*  22:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  23:    */ import org.hibernate.jdbc.AbstractReturningWork;
/*  24:    */ import org.hibernate.mapping.Table;
/*  25:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public class TableGenerator
/*  30:    */   implements PersistentIdentifierGenerator, Configurable
/*  31:    */ {
/*  32:    */   public static final String COLUMN = "column";
/*  33:    */   public static final String DEFAULT_COLUMN_NAME = "next_hi";
/*  34:    */   public static final String TABLE = "table";
/*  35:    */   public static final String DEFAULT_TABLE_NAME = "hibernate_unique_key";
/*  36: 89 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TableGenerator.class.getName());
/*  37:    */   private Type identifierType;
/*  38:    */   private String tableName;
/*  39:    */   private String columnName;
/*  40:    */   private String query;
/*  41:    */   private String update;
/*  42:    */   
/*  43:    */   public void configure(Type type, Properties params, Dialect dialect)
/*  44:    */   {
/*  45: 98 */     this.identifierType = type;
/*  46:    */     
/*  47:100 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/*  48:    */     
/*  49:102 */     this.tableName = ConfigurationHelper.getString("table", params, "hibernate_unique_key");
/*  50:103 */     if (this.tableName.indexOf('.') < 0)
/*  51:    */     {
/*  52:104 */       String schemaName = normalizer.normalizeIdentifierQuoting(params.getProperty("schema"));
/*  53:105 */       String catalogName = normalizer.normalizeIdentifierQuoting(params.getProperty("catalog"));
/*  54:106 */       this.tableName = Table.qualify(dialect.quote(catalogName), dialect.quote(schemaName), dialect.quote(this.tableName));
/*  55:    */     }
/*  56:117 */     this.columnName = dialect.quote(normalizer.normalizeIdentifierQuoting(ConfigurationHelper.getString("column", params, "next_hi")));
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:123 */     this.query = ("select " + this.columnName + " from " + dialect.appendLockHint(LockMode.PESSIMISTIC_WRITE, this.tableName) + dialect.getForUpdateString());
/*  63:    */     
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:129 */     this.update = ("update " + this.tableName + " set " + this.columnName + " = ? where " + this.columnName + " = ?");
/*  69:    */   }
/*  70:    */   
/*  71:    */   public synchronized Serializable generate(SessionImplementor session, Object object)
/*  72:    */   {
/*  73:139 */     return generateHolder(session).makeValue();
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected IntegralDataTypeHolder generateHolder(SessionImplementor session)
/*  77:    */   {
/*  78:143 */     final SqlStatementLogger statementLogger = ((JdbcServices)session.getFactory().getServiceRegistry().getService(JdbcServices.class)).getSqlStatementLogger();
/*  79:    */     
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:148 */     (IntegralDataTypeHolder)session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(new AbstractReturningWork()
/*  84:    */     {
/*  85:    */       public IntegralDataTypeHolder execute(Connection connection)
/*  86:    */         throws SQLException
/*  87:    */       {
/*  88:152 */         IntegralDataTypeHolder value = TableGenerator.this.buildHolder();
/*  89:    */         int rows;
/*  90:    */         do
/*  91:    */         {
/*  92:159 */           statementLogger.logStatement(TableGenerator.this.query, FormatStyle.BASIC.getFormatter());
/*  93:160 */           PreparedStatement qps = connection.prepareStatement(TableGenerator.this.query);
/*  94:    */           try
/*  95:    */           {
/*  96:162 */             ResultSet rs = qps.executeQuery();
/*  97:163 */             if (!rs.next())
/*  98:    */             {
/*  99:164 */               String err = "could not read a hi value - you need to populate the table: " + TableGenerator.this.tableName;
/* 100:165 */               TableGenerator.LOG.error(err);
/* 101:166 */               throw new IdentifierGenerationException(err);
/* 102:    */             }
/* 103:168 */             value.initialize(rs, 1L);
/* 104:169 */             rs.close();
/* 105:    */           }
/* 106:    */           catch (SQLException e)
/* 107:    */           {
/* 108:172 */             TableGenerator.LOG.error("Could not read a hi value", e);
/* 109:173 */             throw e;
/* 110:    */           }
/* 111:    */           finally
/* 112:    */           {
/* 113:176 */             qps.close();
/* 114:    */           }
/* 115:179 */           statementLogger.logStatement(TableGenerator.this.update, FormatStyle.BASIC.getFormatter());
/* 116:180 */           PreparedStatement ups = connection.prepareStatement(TableGenerator.this.update);
/* 117:    */           try
/* 118:    */           {
/* 119:182 */             value.copy().increment().bind(ups, 1);
/* 120:183 */             value.bind(ups, 2);
/* 121:184 */             rows = ups.executeUpdate();
/* 122:    */           }
/* 123:    */           catch (SQLException sqle)
/* 124:    */           {
/* 125:187 */             TableGenerator.LOG.error(TableGenerator.LOG.unableToUpdateHiValue(TableGenerator.this.tableName), sqle);
/* 126:188 */             throw sqle;
/* 127:    */           }
/* 128:    */           finally
/* 129:    */           {
/* 130:191 */             ups.close();
/* 131:    */           }
/* 132:194 */         } while (rows == 0);
/* 133:195 */         return value;
/* 134:    */       }
/* 135:195 */     }, true);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 139:    */     throws HibernateException
/* 140:    */   {
/* 141:203 */     return new String[] { dialect.getCreateTableString() + " " + this.tableName + " ( " + this.columnName + " " + dialect.getTypeName(4) + " )", "insert into " + this.tableName + " values ( 0 )" };
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String[] sqlDropStrings(Dialect dialect)
/* 145:    */   {
/* 146:210 */     StringBuffer sqlDropString = new StringBuffer("drop table ");
/* 147:211 */     if (dialect.supportsIfExistsBeforeTableName()) {
/* 148:212 */       sqlDropString.append("if exists ");
/* 149:    */     }
/* 150:214 */     sqlDropString.append(this.tableName).append(dialect.getCascadeConstraintsString());
/* 151:215 */     if (dialect.supportsIfExistsAfterTableName()) {
/* 152:216 */       sqlDropString.append(" if exists");
/* 153:    */     }
/* 154:218 */     return new String[] { sqlDropString.toString() };
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Object generatorKey()
/* 158:    */   {
/* 159:222 */     return this.tableName;
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected IntegralDataTypeHolder buildHolder()
/* 163:    */   {
/* 164:226 */     return IdentifierGeneratorHelper.getIntegralDataTypeHolder(this.identifierType.getReturnedClass());
/* 165:    */   }
/* 166:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.TableGenerator
 * JD-Core Version:    0.7.0.1
 */