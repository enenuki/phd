/*   1:    */ package org.hibernate.id.enhanced;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  10:    */ import org.hibernate.id.Configurable;
/*  11:    */ import org.hibernate.id.PersistentIdentifierGenerator;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  14:    */ import org.hibernate.mapping.Table;
/*  15:    */ import org.hibernate.type.Type;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class SequenceStyleGenerator
/*  19:    */   implements PersistentIdentifierGenerator, Configurable
/*  20:    */ {
/*  21:100 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SequenceStyleGenerator.class.getName());
/*  22:    */   public static final String SEQUENCE_PARAM = "sequence_name";
/*  23:    */   public static final String DEF_SEQUENCE_NAME = "hibernate_sequence";
/*  24:    */   public static final String INITIAL_PARAM = "initial_value";
/*  25:    */   public static final int DEFAULT_INITIAL_VALUE = 1;
/*  26:    */   public static final String INCREMENT_PARAM = "increment_size";
/*  27:    */   public static final int DEFAULT_INCREMENT_SIZE = 1;
/*  28:    */   public static final String OPT_PARAM = "optimizer";
/*  29:    */   public static final String FORCE_TBL_PARAM = "force_table_use";
/*  30:    */   public static final String VALUE_COLUMN_PARAM = "value_column";
/*  31:    */   public static final String DEF_VALUE_COLUMN = "next_val";
/*  32:    */   private DatabaseStructure databaseStructure;
/*  33:    */   private Optimizer optimizer;
/*  34:    */   private Type identifierType;
/*  35:    */   
/*  36:    */   public DatabaseStructure getDatabaseStructure()
/*  37:    */   {
/*  38:134 */     return this.databaseStructure;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Optimizer getOptimizer()
/*  42:    */   {
/*  43:143 */     return this.optimizer;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Type getIdentifierType()
/*  47:    */   {
/*  48:152 */     return this.identifierType;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void configure(Type type, Properties params, Dialect dialect)
/*  52:    */     throws MappingException
/*  53:    */   {
/*  54:162 */     this.identifierType = type;
/*  55:163 */     boolean forceTableUse = ConfigurationHelper.getBoolean("force_table_use", params, false);
/*  56:    */     
/*  57:165 */     String sequenceName = determineSequenceName(params, dialect);
/*  58:    */     
/*  59:167 */     int initialValue = determineInitialValue(params);
/*  60:168 */     int incrementSize = determineIncrementSize(params);
/*  61:    */     
/*  62:170 */     String optimizationStrategy = determineOptimizationStrategy(params, incrementSize);
/*  63:171 */     incrementSize = determineAdjustedIncrementSize(optimizationStrategy, incrementSize);
/*  64:173 */     if ((dialect.supportsSequences()) && (!forceTableUse) && 
/*  65:174 */       ("pooled".equals(optimizationStrategy)) && (!dialect.supportsPooledSequences()))
/*  66:    */     {
/*  67:175 */       forceTableUse = true;
/*  68:176 */       LOG.forcingTableUse();
/*  69:    */     }
/*  70:180 */     this.databaseStructure = buildDatabaseStructure(type, params, dialect, forceTableUse, sequenceName, initialValue, incrementSize);
/*  71:    */     
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:189 */     this.optimizer = OptimizerFactory.buildOptimizer(optimizationStrategy, this.identifierType.getReturnedClass(), incrementSize, ConfigurationHelper.getInt("initial_value", params, -1));
/*  80:    */     
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:195 */     this.databaseStructure.prepare(this.optimizer);
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected String determineSequenceName(Properties params, Dialect dialect)
/*  89:    */   {
/*  90:209 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/*  91:210 */     String sequenceName = ConfigurationHelper.getString("sequence_name", params, "hibernate_sequence");
/*  92:211 */     if (sequenceName.indexOf('.') < 0)
/*  93:    */     {
/*  94:212 */       sequenceName = normalizer.normalizeIdentifierQuoting(sequenceName);
/*  95:213 */       String schemaName = params.getProperty("schema");
/*  96:214 */       String catalogName = params.getProperty("catalog");
/*  97:215 */       sequenceName = Table.qualify(dialect.quote(catalogName), dialect.quote(schemaName), dialect.quote(sequenceName));
/*  98:    */     }
/*  99:225 */     return sequenceName;
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected String determineValueColumnName(Properties params, Dialect dialect)
/* 103:    */   {
/* 104:240 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/* 105:241 */     String name = ConfigurationHelper.getString("value_column", params, "next_val");
/* 106:242 */     return dialect.quote(normalizer.normalizeIdentifierQuoting(name));
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected int determineInitialValue(Properties params)
/* 110:    */   {
/* 111:256 */     return ConfigurationHelper.getInt("initial_value", params, 1);
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected int determineIncrementSize(Properties params)
/* 115:    */   {
/* 116:269 */     return ConfigurationHelper.getInt("increment_size", params, 1);
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected String determineOptimizationStrategy(Properties params, int incrementSize)
/* 120:    */   {
/* 121:284 */     String defaultPooledOptimizerStrategy = ConfigurationHelper.getBoolean("hibernate.id.optimizer.pooled.prefer_lo", params, false) ? "pooled-lo" : "pooled";
/* 122:    */     
/* 123:    */ 
/* 124:287 */     String defaultOptimizerStrategy = incrementSize <= 1 ? "none" : defaultPooledOptimizerStrategy;
/* 125:288 */     return ConfigurationHelper.getString("optimizer", params, defaultOptimizerStrategy);
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected int determineAdjustedIncrementSize(String optimizationStrategy, int incrementSize)
/* 129:    */   {
/* 130:300 */     if (("none".equals(optimizationStrategy)) && (incrementSize > 1))
/* 131:    */     {
/* 132:301 */       LOG.honoringOptimizerSetting("none", "increment_size", incrementSize);
/* 133:302 */       incrementSize = 1;
/* 134:    */     }
/* 135:304 */     return incrementSize;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected DatabaseStructure buildDatabaseStructure(Type type, Properties params, Dialect dialect, boolean forceTableUse, String sequenceName, int initialValue, int incrementSize)
/* 139:    */   {
/* 140:328 */     boolean useSequence = (dialect.supportsSequences()) && (!forceTableUse);
/* 141:329 */     if (useSequence) {
/* 142:330 */       return new SequenceStructure(dialect, sequenceName, initialValue, incrementSize, type.getReturnedClass());
/* 143:    */     }
/* 144:333 */     String valueColumnName = determineValueColumnName(params, dialect);
/* 145:334 */     return new TableStructure(dialect, sequenceName, valueColumnName, initialValue, incrementSize, type.getReturnedClass());
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Serializable generate(SessionImplementor session, Object object)
/* 149:    */     throws HibernateException
/* 150:    */   {
/* 151:345 */     return this.optimizer.generate(this.databaseStructure.buildCallback(session));
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Object generatorKey()
/* 155:    */   {
/* 156:355 */     return this.databaseStructure.getName();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 160:    */     throws HibernateException
/* 161:    */   {
/* 162:362 */     return this.databaseStructure.sqlCreateStrings(dialect);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String[] sqlDropStrings(Dialect dialect)
/* 166:    */     throws HibernateException
/* 167:    */   {
/* 168:369 */     return this.databaseStructure.sqlDropStrings(dialect);
/* 169:    */   }
/* 170:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.enhanced.SequenceStyleGenerator
 * JD-Core Version:    0.7.0.1
 */