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
/*  11:    */ import org.hibernate.MappingException;
/*  12:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  13:    */ import org.hibernate.dialect.Dialect;
/*  14:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*  15:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  16:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  20:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  21:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  22:    */ import org.hibernate.id.enhanced.AccessCallback;
/*  23:    */ import org.hibernate.id.enhanced.OptimizerFactory.LegacyHiLoAlgorithmOptimizer;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  26:    */ import org.hibernate.jdbc.AbstractReturningWork;
/*  27:    */ import org.hibernate.jdbc.WorkExecutorVisitable;
/*  28:    */ import org.hibernate.mapping.Table;
/*  29:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  30:    */ import org.hibernate.type.Type;
/*  31:    */ import org.jboss.logging.Logger;
/*  32:    */ 
/*  33:    */ public class MultipleHiLoPerTableGenerator
/*  34:    */   implements PersistentIdentifierGenerator, Configurable
/*  35:    */ {
/*  36: 87 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MultipleHiLoPerTableGenerator.class.getName());
/*  37:    */   public static final String ID_TABLE = "table";
/*  38:    */   public static final String PK_COLUMN_NAME = "primary_key_column";
/*  39:    */   public static final String PK_VALUE_NAME = "primary_key_value";
/*  40:    */   public static final String VALUE_COLUMN_NAME = "value_column";
/*  41:    */   public static final String PK_LENGTH_NAME = "primary_key_length";
/*  42:    */   private static final int DEFAULT_PK_LENGTH = 255;
/*  43:    */   public static final String DEFAULT_TABLE = "hibernate_sequences";
/*  44:    */   private static final String DEFAULT_PK_COLUMN = "sequence_name";
/*  45:    */   private static final String DEFAULT_VALUE_COLUMN = "sequence_next_hi_value";
/*  46:    */   private String tableName;
/*  47:    */   private String pkColumnName;
/*  48:    */   private String valueColumnName;
/*  49:    */   private String query;
/*  50:    */   private String insert;
/*  51:    */   private String update;
/*  52:    */   public static final String MAX_LO = "max_lo";
/*  53:    */   private int maxLo;
/*  54:    */   private OptimizerFactory.LegacyHiLoAlgorithmOptimizer hiloOptimizer;
/*  55:    */   private Class returnClass;
/*  56:    */   private int keySize;
/*  57:    */   
/*  58:    */   public String[] sqlCreateStrings(Dialect dialect)
/*  59:    */     throws HibernateException
/*  60:    */   {
/*  61:119 */     return new String[] { dialect.getCreateTableString() + ' ' + this.tableName + " ( " + this.pkColumnName + ' ' + dialect.getTypeName(12, this.keySize, 0, 0) + ",  " + this.valueColumnName + ' ' + dialect.getTypeName(4) + " ) " };
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String[] sqlDropStrings(Dialect dialect)
/*  65:    */     throws HibernateException
/*  66:    */   {
/*  67:137 */     StringBuffer sqlDropString = new StringBuffer("drop table ");
/*  68:138 */     if (dialect.supportsIfExistsBeforeTableName()) {
/*  69:139 */       sqlDropString.append("if exists ");
/*  70:    */     }
/*  71:141 */     sqlDropString.append(this.tableName).append(dialect.getCascadeConstraintsString());
/*  72:142 */     if (dialect.supportsIfExistsAfterTableName()) {
/*  73:143 */       sqlDropString.append(" if exists");
/*  74:    */     }
/*  75:145 */     return new String[] { sqlDropString.toString() };
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object generatorKey()
/*  79:    */   {
/*  80:149 */     return this.tableName;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized Serializable generate(final SessionImplementor session, Object obj)
/*  84:    */   {
/*  85:153 */     final WorkExecutorVisitable<IntegralDataTypeHolder> work = new AbstractReturningWork()
/*  86:    */     {
/*  87:    */       public IntegralDataTypeHolder execute(Connection connection)
/*  88:    */         throws SQLException
/*  89:    */       {
/*  90:156 */         IntegralDataTypeHolder value = IdentifierGeneratorHelper.getIntegralDataTypeHolder(MultipleHiLoPerTableGenerator.this.returnClass);
/*  91:157 */         SqlStatementLogger statementLogger = ((JdbcServices)session.getFactory().getServiceRegistry().getService(JdbcServices.class)).getSqlStatementLogger();
/*  92:    */         int rows;
/*  93:    */         do
/*  94:    */         {
/*  95:164 */           statementLogger.logStatement(MultipleHiLoPerTableGenerator.this.query, FormatStyle.BASIC.getFormatter());
/*  96:165 */           PreparedStatement qps = connection.prepareStatement(MultipleHiLoPerTableGenerator.this.query);
/*  97:166 */           PreparedStatement ips = null;
/*  98:    */           try
/*  99:    */           {
/* 100:168 */             ResultSet rs = qps.executeQuery();
/* 101:169 */             boolean isInitialized = rs.next();
/* 102:170 */             if (!isInitialized)
/* 103:    */             {
/* 104:171 */               value.initialize(0L);
/* 105:172 */               statementLogger.logStatement(MultipleHiLoPerTableGenerator.this.insert, FormatStyle.BASIC.getFormatter());
/* 106:173 */               ips = connection.prepareStatement(MultipleHiLoPerTableGenerator.this.insert);
/* 107:174 */               value.bind(ips, 1);
/* 108:175 */               ips.execute();
/* 109:    */             }
/* 110:    */             else
/* 111:    */             {
/* 112:178 */               value.initialize(rs, 0L);
/* 113:    */             }
/* 114:180 */             rs.close();
/* 115:    */           }
/* 116:    */           catch (SQLException sqle)
/* 117:    */           {
/* 118:183 */             MultipleHiLoPerTableGenerator.LOG.unableToReadOrInitHiValue(sqle);
/* 119:184 */             throw sqle;
/* 120:    */           }
/* 121:    */           finally
/* 122:    */           {
/* 123:187 */             if (ips != null) {
/* 124:188 */               ips.close();
/* 125:    */             }
/* 126:190 */             qps.close();
/* 127:    */           }
/* 128:193 */           statementLogger.logStatement(MultipleHiLoPerTableGenerator.this.update, FormatStyle.BASIC.getFormatter());
/* 129:194 */           PreparedStatement ups = connection.prepareStatement(MultipleHiLoPerTableGenerator.this.update);
/* 130:    */           try
/* 131:    */           {
/* 132:196 */             value.copy().increment().bind(ups, 1);
/* 133:197 */             value.bind(ups, 2);
/* 134:198 */             rows = ups.executeUpdate();
/* 135:    */           }
/* 136:    */           catch (SQLException sqle)
/* 137:    */           {
/* 138:201 */             MultipleHiLoPerTableGenerator.LOG.error(MultipleHiLoPerTableGenerator.LOG.unableToUpdateHiValue(MultipleHiLoPerTableGenerator.this.tableName), sqle);
/* 139:202 */             throw sqle;
/* 140:    */           }
/* 141:    */           finally
/* 142:    */           {
/* 143:205 */             ups.close();
/* 144:    */           }
/* 145:207 */         } while (rows == 0);
/* 146:209 */         return value;
/* 147:    */       }
/* 148:    */     };
/* 149:214 */     if (this.maxLo < 1)
/* 150:    */     {
/* 151:216 */       IntegralDataTypeHolder value = null;
/* 152:217 */       while ((value == null) || (value.lt(1L))) {
/* 153:218 */         value = (IntegralDataTypeHolder)session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(work, true);
/* 154:    */       }
/* 155:220 */       return value.makeValue();
/* 156:    */     }
/* 157:223 */     this.hiloOptimizer.generate(new AccessCallback()
/* 158:    */     {
/* 159:    */       public IntegralDataTypeHolder getNextValue()
/* 160:    */       {
/* 161:226 */         return (IntegralDataTypeHolder)session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(work, true);
/* 162:    */       }
/* 163:    */     });
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void configure(Type type, Properties params, Dialect dialect)
/* 167:    */     throws MappingException
/* 168:    */   {
/* 169:233 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/* 170:    */     
/* 171:235 */     this.tableName = normalizer.normalizeIdentifierQuoting(ConfigurationHelper.getString("table", params, "hibernate_sequences"));
/* 172:236 */     if (this.tableName.indexOf('.') < 0)
/* 173:    */     {
/* 174:237 */       this.tableName = dialect.quote(this.tableName);
/* 175:238 */       String schemaName = dialect.quote(normalizer.normalizeIdentifierQuoting(params.getProperty("schema")));
/* 176:    */       
/* 177:    */ 
/* 178:241 */       String catalogName = dialect.quote(normalizer.normalizeIdentifierQuoting(params.getProperty("catalog")));
/* 179:    */       
/* 180:    */ 
/* 181:244 */       this.tableName = Table.qualify(catalogName, schemaName, this.tableName);
/* 182:    */     }
/* 183:251 */     this.pkColumnName = dialect.quote(normalizer.normalizeIdentifierQuoting(ConfigurationHelper.getString("primary_key_column", params, "sequence_name")));
/* 184:    */     
/* 185:    */ 
/* 186:    */ 
/* 187:    */ 
/* 188:256 */     this.valueColumnName = dialect.quote(normalizer.normalizeIdentifierQuoting(ConfigurationHelper.getString("value_column", params, "sequence_next_hi_value")));
/* 189:    */     
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:261 */     this.keySize = ConfigurationHelper.getInt("primary_key_length", params, 255);
/* 194:262 */     String keyValue = ConfigurationHelper.getString("primary_key_value", params, params.getProperty("target_table"));
/* 195:    */     
/* 196:264 */     this.query = ("select " + this.valueColumnName + " from " + dialect.appendLockHint(LockMode.PESSIMISTIC_WRITE, this.tableName) + " where " + this.pkColumnName + " = '" + keyValue + "'" + dialect.getForUpdateString());
/* 197:    */     
/* 198:    */ 
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:271 */     this.update = ("update " + this.tableName + " set " + this.valueColumnName + " = ? where " + this.valueColumnName + " = ? and " + this.pkColumnName + " = '" + keyValue + "'");
/* 204:    */     
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:283 */     this.insert = ("insert into " + this.tableName + "(" + this.pkColumnName + ", " + this.valueColumnName + ") " + "values('" + keyValue + "', ?)");
/* 216:    */     
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:289 */     this.maxLo = ConfigurationHelper.getInt("max_lo", params, 32767);
/* 222:290 */     this.returnClass = type.getReturnedClass();
/* 223:292 */     if (this.maxLo >= 1) {
/* 224:293 */       this.hiloOptimizer = new OptimizerFactory.LegacyHiLoAlgorithmOptimizer(this.returnClass, this.maxLo);
/* 225:    */     }
/* 226:    */   }
/* 227:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.MultipleHiLoPerTableGenerator
 * JD-Core Version:    0.7.0.1
 */