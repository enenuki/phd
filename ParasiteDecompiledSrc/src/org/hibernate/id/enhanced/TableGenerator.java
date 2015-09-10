/*   1:    */ package org.hibernate.id.enhanced;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.PreparedStatement;
/*   6:    */ import java.sql.ResultSet;
/*   7:    */ import java.sql.SQLException;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Properties;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.LockMode;
/*  13:    */ import org.hibernate.LockOptions;
/*  14:    */ import org.hibernate.MappingException;
/*  15:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  16:    */ import org.hibernate.dialect.Dialect;
/*  17:    */ import org.hibernate.engine.jdbc.internal.FormatStyle;
/*  18:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  19:    */ import org.hibernate.engine.jdbc.spi.SqlStatementLogger;
/*  20:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  21:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  22:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  23:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  24:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  25:    */ import org.hibernate.id.Configurable;
/*  26:    */ import org.hibernate.id.IdentifierGeneratorHelper;
/*  27:    */ import org.hibernate.id.IntegralDataTypeHolder;
/*  28:    */ import org.hibernate.id.PersistentIdentifierGenerator;
/*  29:    */ import org.hibernate.internal.CoreMessageLogger;
/*  30:    */ import org.hibernate.internal.util.StringHelper;
/*  31:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  32:    */ import org.hibernate.jdbc.AbstractReturningWork;
/*  33:    */ import org.hibernate.mapping.Table;
/*  34:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  35:    */ import org.hibernate.type.Type;
/*  36:    */ import org.jboss.logging.Logger;
/*  37:    */ 
/*  38:    */ public class TableGenerator
/*  39:    */   implements PersistentIdentifierGenerator, Configurable
/*  40:    */ {
/*  41:135 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TableGenerator.class.getName());
/*  42:    */   public static final String CONFIG_PREFER_SEGMENT_PER_ENTITY = "prefer_entity_table_as_segment_value";
/*  43:    */   public static final String TABLE_PARAM = "table_name";
/*  44:    */   public static final String DEF_TABLE = "hibernate_sequences";
/*  45:    */   public static final String VALUE_COLUMN_PARAM = "value_column_name";
/*  46:    */   public static final String DEF_VALUE_COLUMN = "next_val";
/*  47:    */   public static final String SEGMENT_COLUMN_PARAM = "segment_column_name";
/*  48:    */   public static final String DEF_SEGMENT_COLUMN = "sequence_name";
/*  49:    */   public static final String SEGMENT_VALUE_PARAM = "segment_value";
/*  50:    */   public static final String DEF_SEGMENT_VALUE = "default";
/*  51:    */   public static final String SEGMENT_LENGTH_PARAM = "segment_value_length";
/*  52:    */   public static final int DEF_SEGMENT_LENGTH = 255;
/*  53:    */   public static final String INITIAL_PARAM = "initial_value";
/*  54:    */   public static final int DEFAULT_INITIAL_VALUE = 1;
/*  55:    */   public static final String INCREMENT_PARAM = "increment_size";
/*  56:    */   public static final int DEFAULT_INCREMENT_SIZE = 1;
/*  57:    */   public static final String OPT_PARAM = "optimizer";
/*  58:    */   private Type identifierType;
/*  59:    */   private String tableName;
/*  60:    */   private String segmentColumnName;
/*  61:    */   private String segmentValue;
/*  62:    */   private int segmentValueLength;
/*  63:    */   private String valueColumnName;
/*  64:    */   private int initialValue;
/*  65:    */   private int incrementSize;
/*  66:    */   private String selectQuery;
/*  67:    */   private String insertQuery;
/*  68:    */   private String updateQuery;
/*  69:    */   private Optimizer optimizer;
/*  70:180 */   private long accessCount = 0L;
/*  71:    */   
/*  72:    */   public Object generatorKey()
/*  73:    */   {
/*  74:184 */     return this.tableName;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final Type getIdentifierType()
/*  78:    */   {
/*  79:193 */     return this.identifierType;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public final String getTableName()
/*  83:    */   {
/*  84:202 */     return this.tableName;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public final String getSegmentColumnName()
/*  88:    */   {
/*  89:212 */     return this.segmentColumnName;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public final String getSegmentValue()
/*  93:    */   {
/*  94:223 */     return this.segmentValue;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public final int getSegmentValueLength()
/*  98:    */   {
/*  99:236 */     return this.segmentValueLength;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public final String getValueColumnName()
/* 103:    */   {
/* 104:245 */     return this.valueColumnName;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public final int getInitialValue()
/* 108:    */   {
/* 109:255 */     return this.initialValue;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public final int getIncrementSize()
/* 113:    */   {
/* 114:265 */     return this.incrementSize;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public final Optimizer getOptimizer()
/* 118:    */   {
/* 119:274 */     return this.optimizer;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final long getTableAccessCount()
/* 123:    */   {
/* 124:284 */     return this.accessCount;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void configure(Type type, Properties params, Dialect dialect)
/* 128:    */     throws MappingException
/* 129:    */   {
/* 130:289 */     this.identifierType = type;
/* 131:    */     
/* 132:291 */     this.tableName = determineGeneratorTableName(params, dialect);
/* 133:292 */     this.segmentColumnName = determineSegmentColumnName(params, dialect);
/* 134:293 */     this.valueColumnName = determineValueColumnName(params, dialect);
/* 135:    */     
/* 136:295 */     this.segmentValue = determineSegmentValue(params);
/* 137:    */     
/* 138:297 */     this.segmentValueLength = determineSegmentColumnSize(params);
/* 139:298 */     this.initialValue = determineInitialValue(params);
/* 140:299 */     this.incrementSize = determineIncrementSize(params);
/* 141:    */     
/* 142:301 */     this.selectQuery = buildSelectQuery(dialect);
/* 143:302 */     this.updateQuery = buildUpdateQuery();
/* 144:303 */     this.insertQuery = buildInsertQuery();
/* 145:    */     
/* 146:    */ 
/* 147:    */ 
/* 148:307 */     String defaultPooledOptimizerStrategy = ConfigurationHelper.getBoolean("hibernate.id.optimizer.pooled.prefer_lo", params, false) ? "pooled-lo" : "pooled";
/* 149:    */     
/* 150:    */ 
/* 151:310 */     String defaultOptimizerStrategy = this.incrementSize <= 1 ? "none" : defaultPooledOptimizerStrategy;
/* 152:311 */     String optimizationStrategy = ConfigurationHelper.getString("optimizer", params, defaultOptimizerStrategy);
/* 153:312 */     this.optimizer = OptimizerFactory.buildOptimizer(optimizationStrategy, this.identifierType.getReturnedClass(), this.incrementSize, ConfigurationHelper.getInt("initial_value", params, -1));
/* 154:    */   }
/* 155:    */   
/* 156:    */   protected String determineGeneratorTableName(Properties params, Dialect dialect)
/* 157:    */   {
/* 158:331 */     String name = ConfigurationHelper.getString("table_name", params, "hibernate_sequences");
/* 159:332 */     boolean isGivenNameUnqualified = name.indexOf('.') < 0;
/* 160:333 */     if (isGivenNameUnqualified)
/* 161:    */     {
/* 162:334 */       ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/* 163:335 */       name = normalizer.normalizeIdentifierQuoting(name);
/* 164:    */       
/* 165:337 */       String schemaName = normalizer.normalizeIdentifierQuoting(params.getProperty("schema"));
/* 166:338 */       String catalogName = normalizer.normalizeIdentifierQuoting(params.getProperty("catalog"));
/* 167:339 */       name = Table.qualify(dialect.quote(catalogName), dialect.quote(schemaName), dialect.quote(name));
/* 168:    */     }
/* 169:349 */     return name;
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected String determineSegmentColumnName(Properties params, Dialect dialect)
/* 173:    */   {
/* 174:364 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/* 175:365 */     String name = ConfigurationHelper.getString("segment_column_name", params, "sequence_name");
/* 176:366 */     return dialect.quote(normalizer.normalizeIdentifierQuoting(name));
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected String determineValueColumnName(Properties params, Dialect dialect)
/* 180:    */   {
/* 181:380 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/* 182:381 */     String name = ConfigurationHelper.getString("value_column_name", params, "next_val");
/* 183:382 */     return dialect.quote(normalizer.normalizeIdentifierQuoting(name));
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected String determineSegmentValue(Properties params)
/* 187:    */   {
/* 188:395 */     String segmentValue = params.getProperty("segment_value");
/* 189:396 */     if (StringHelper.isEmpty(segmentValue)) {
/* 190:397 */       segmentValue = determineDefaultSegmentValue(params);
/* 191:    */     }
/* 192:399 */     return segmentValue;
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected String determineDefaultSegmentValue(Properties params)
/* 196:    */   {
/* 197:410 */     boolean preferSegmentPerEntity = ConfigurationHelper.getBoolean("prefer_entity_table_as_segment_value", params, false);
/* 198:411 */     String defaultToUse = preferSegmentPerEntity ? params.getProperty("target_table") : "default";
/* 199:412 */     LOG.usingDefaultIdGeneratorSegmentValue(this.tableName, this.segmentColumnName, defaultToUse);
/* 200:413 */     return defaultToUse;
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected int determineSegmentColumnSize(Properties params)
/* 204:    */   {
/* 205:426 */     return ConfigurationHelper.getInt("segment_value_length", params, 255);
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected int determineInitialValue(Properties params)
/* 209:    */   {
/* 210:430 */     return ConfigurationHelper.getInt("initial_value", params, 1);
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected int determineIncrementSize(Properties params)
/* 214:    */   {
/* 215:434 */     return ConfigurationHelper.getInt("increment_size", params, 1);
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected String buildSelectQuery(Dialect dialect)
/* 219:    */   {
/* 220:438 */     String alias = "tbl";
/* 221:439 */     String query = "select " + StringHelper.qualify("tbl", this.valueColumnName) + " from " + this.tableName + ' ' + "tbl" + " where " + StringHelper.qualify("tbl", this.segmentColumnName) + "=?";
/* 222:    */     
/* 223:    */ 
/* 224:442 */     LockOptions lockOptions = new LockOptions(LockMode.PESSIMISTIC_WRITE);
/* 225:443 */     lockOptions.setAliasSpecificLockMode("tbl", LockMode.PESSIMISTIC_WRITE);
/* 226:444 */     Map updateTargetColumnsMap = Collections.singletonMap("tbl", new String[] { this.valueColumnName });
/* 227:445 */     return dialect.applyLocksToSql(query, lockOptions, updateTargetColumnsMap);
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected String buildUpdateQuery()
/* 231:    */   {
/* 232:449 */     return "update " + this.tableName + " set " + this.valueColumnName + "=? " + " where " + this.valueColumnName + "=? and " + this.segmentColumnName + "=?";
/* 233:    */   }
/* 234:    */   
/* 235:    */   protected String buildInsertQuery()
/* 236:    */   {
/* 237:455 */     return "insert into " + this.tableName + " (" + this.segmentColumnName + ", " + this.valueColumnName + ") " + " values (?,?)";
/* 238:    */   }
/* 239:    */   
/* 240:    */   public synchronized Serializable generate(final SessionImplementor session, Object obj)
/* 241:    */   {
/* 242:460 */     final SqlStatementLogger statementLogger = ((JdbcServices)session.getFactory().getServiceRegistry().getService(JdbcServices.class)).getSqlStatementLogger();
/* 243:    */     
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:465 */     this.optimizer.generate(new AccessCallback()
/* 248:    */     {
/* 249:    */       public IntegralDataTypeHolder getNextValue()
/* 250:    */       {
/* 251:469 */         (IntegralDataTypeHolder)session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(new AbstractReturningWork()
/* 252:    */         {
/* 253:    */           public IntegralDataTypeHolder execute(Connection connection)
/* 254:    */             throws SQLException
/* 255:    */           {
/* 256:473 */             IntegralDataTypeHolder value = IdentifierGeneratorHelper.getIntegralDataTypeHolder(TableGenerator.this.identifierType.getReturnedClass());
/* 257:    */             int rows;
/* 258:    */             do
/* 259:    */             {
/* 260:476 */               TableGenerator.1.this.val$statementLogger.logStatement(TableGenerator.this.selectQuery, FormatStyle.BASIC.getFormatter());
/* 261:477 */               PreparedStatement selectPS = connection.prepareStatement(TableGenerator.this.selectQuery);
/* 262:    */               try
/* 263:    */               {
/* 264:479 */                 selectPS.setString(1, TableGenerator.this.segmentValue);
/* 265:480 */                 ResultSet selectRS = selectPS.executeQuery();
/* 266:481 */                 if (!selectRS.next())
/* 267:    */                 {
/* 268:482 */                   value.initialize(TableGenerator.this.initialValue);
/* 269:483 */                   PreparedStatement insertPS = null;
/* 270:    */                   try
/* 271:    */                   {
/* 272:485 */                     TableGenerator.1.this.val$statementLogger.logStatement(TableGenerator.this.insertQuery, FormatStyle.BASIC.getFormatter());
/* 273:486 */                     insertPS = connection.prepareStatement(TableGenerator.this.insertQuery);
/* 274:487 */                     insertPS.setString(1, TableGenerator.this.segmentValue);
/* 275:488 */                     value.bind(insertPS, 2);
/* 276:489 */                     insertPS.execute();
/* 277:    */                   }
/* 278:    */                   finally
/* 279:    */                   {
/* 280:492 */                     if (insertPS == null) {}
/* 281:    */                   }
/* 282:    */                 }
/* 283:    */                 else
/* 284:    */                 {
/* 285:498 */                   value.initialize(selectRS, 1L);
/* 286:    */                 }
/* 287:500 */                 selectRS.close();
/* 288:    */               }
/* 289:    */               catch (SQLException e)
/* 290:    */               {
/* 291:503 */                 TableGenerator.LOG.unableToReadOrInitHiValue(e);
/* 292:504 */                 throw e;
/* 293:    */               }
/* 294:    */               finally
/* 295:    */               {
/* 296:507 */                 selectPS.close();
/* 297:    */               }
/* 298:510 */               TableGenerator.1.this.val$statementLogger.logStatement(TableGenerator.this.updateQuery, FormatStyle.BASIC.getFormatter());
/* 299:511 */               PreparedStatement updatePS = connection.prepareStatement(TableGenerator.this.updateQuery);
/* 300:    */               try
/* 301:    */               {
/* 302:513 */                 IntegralDataTypeHolder updateValue = value.copy();
/* 303:514 */                 if (TableGenerator.this.optimizer.applyIncrementSizeToSourceValues()) {
/* 304:515 */                   updateValue.add(TableGenerator.this.incrementSize);
/* 305:    */                 } else {
/* 306:518 */                   updateValue.increment();
/* 307:    */                 }
/* 308:520 */                 updateValue.bind(updatePS, 1);
/* 309:521 */                 value.bind(updatePS, 2);
/* 310:522 */                 updatePS.setString(3, TableGenerator.this.segmentValue);
/* 311:523 */                 rows = updatePS.executeUpdate();
/* 312:    */               }
/* 313:    */               catch (SQLException e)
/* 314:    */               {
/* 315:526 */                 TableGenerator.LOG.unableToUpdateQueryHiValue(TableGenerator.this.tableName, e);
/* 316:527 */                 throw e;
/* 317:    */               }
/* 318:    */               finally
/* 319:    */               {
/* 320:530 */                 updatePS.close();
/* 321:    */               }
/* 322:533 */             } while (rows == 0);
/* 323:535 */             TableGenerator.access$1008(TableGenerator.this);
/* 324:    */             
/* 325:537 */             return value;
/* 326:    */           }
/* 327:537 */         }, true);
/* 328:    */       }
/* 329:    */     });
/* 330:    */   }
/* 331:    */   
/* 332:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 333:    */     throws HibernateException
/* 334:    */   {
/* 335:549 */     return new String[] { dialect.getCreateTableString() + ' ' + this.tableName + " ( " + this.segmentColumnName + ' ' + dialect.getTypeName(12, this.segmentValueLength, 0, 0) + " not null " + ",  " + this.valueColumnName + ' ' + dialect.getTypeName(-5) + ", primary key ( " + this.segmentColumnName + " ) ) " };
/* 336:    */   }
/* 337:    */   
/* 338:    */   public String[] sqlDropStrings(Dialect dialect)
/* 339:    */     throws HibernateException
/* 340:    */   {
/* 341:572 */     StringBuffer sqlDropString = new StringBuffer().append("drop table ");
/* 342:573 */     if (dialect.supportsIfExistsBeforeTableName()) {
/* 343:574 */       sqlDropString.append("if exists ");
/* 344:    */     }
/* 345:576 */     sqlDropString.append(this.tableName).append(dialect.getCascadeConstraintsString());
/* 346:577 */     if (dialect.supportsIfExistsAfterTableName()) {
/* 347:578 */       sqlDropString.append(" if exists");
/* 348:    */     }
/* 349:580 */     return new String[] { sqlDropString.toString() };
/* 350:    */   }
/* 351:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.TableGenerator
 * JD-Core Version:    0.7.0.1
 */