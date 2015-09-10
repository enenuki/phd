/*   1:    */ package org.hibernate.id;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Properties;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.cfg.ObjectNameNormalizer;
/*  11:    */ import org.hibernate.dialect.Dialect;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  13:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  14:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  18:    */ import org.hibernate.internal.CoreMessageLogger;
/*  19:    */ import org.hibernate.internal.util.StringHelper;
/*  20:    */ import org.hibernate.mapping.Table;
/*  21:    */ import org.hibernate.type.Type;
/*  22:    */ import org.jboss.logging.Logger;
/*  23:    */ 
/*  24:    */ public class IncrementGenerator
/*  25:    */   implements IdentifierGenerator, Configurable
/*  26:    */ {
/*  27: 59 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IncrementGenerator.class.getName());
/*  28:    */   private Class returnClass;
/*  29:    */   private String sql;
/*  30:    */   private IntegralDataTypeHolder previousValueHolder;
/*  31:    */   
/*  32:    */   public synchronized Serializable generate(SessionImplementor session, Object object)
/*  33:    */     throws HibernateException
/*  34:    */   {
/*  35: 67 */     if (this.sql != null) {
/*  36: 68 */       initializePreviousValueHolder(session);
/*  37:    */     }
/*  38: 70 */     return this.previousValueHolder.makeValueThenIncrement();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void configure(Type type, Properties params, Dialect dialect)
/*  42:    */     throws MappingException
/*  43:    */   {
/*  44: 74 */     this.returnClass = type.getReturnedClass();
/*  45:    */     
/*  46: 76 */     ObjectNameNormalizer normalizer = (ObjectNameNormalizer)params.get("identifier_normalizer");
/*  47:    */     
/*  48:    */ 
/*  49: 79 */     String column = params.getProperty("column");
/*  50: 80 */     if (column == null) {
/*  51: 81 */       column = params.getProperty("target_column");
/*  52:    */     }
/*  53: 83 */     column = dialect.quote(normalizer.normalizeIdentifierQuoting(column));
/*  54:    */     
/*  55: 85 */     String tableList = params.getProperty("tables");
/*  56: 86 */     if (tableList == null) {
/*  57: 87 */       tableList = params.getProperty("identity_tables");
/*  58:    */     }
/*  59: 89 */     String[] tables = StringHelper.split(", ", tableList);
/*  60:    */     
/*  61: 91 */     String schema = dialect.quote(normalizer.normalizeIdentifierQuoting(params.getProperty("schema")));
/*  62:    */     
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66: 96 */     String catalog = dialect.quote(normalizer.normalizeIdentifierQuoting(params.getProperty("catalog")));
/*  67:    */     
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:102 */     StringBuffer buf = new StringBuffer();
/*  73:103 */     for (int i = 0; i < tables.length; i++)
/*  74:    */     {
/*  75:104 */       String tableName = dialect.quote(normalizer.normalizeIdentifierQuoting(tables[i]));
/*  76:105 */       if (tables.length > 1) {
/*  77:106 */         buf.append("select ").append(column).append(" from ");
/*  78:    */       }
/*  79:108 */       buf.append(Table.qualify(catalog, schema, tableName));
/*  80:109 */       if (i < tables.length - 1) {
/*  81:110 */         buf.append(" union ");
/*  82:    */       }
/*  83:    */     }
/*  84:113 */     if (tables.length > 1)
/*  85:    */     {
/*  86:114 */       buf.insert(0, "( ").append(" ) ids_");
/*  87:115 */       column = "ids_." + column;
/*  88:    */     }
/*  89:118 */     this.sql = ("select max(" + column + ") from " + buf.toString());
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void initializePreviousValueHolder(SessionImplementor session)
/*  93:    */   {
/*  94:122 */     this.previousValueHolder = IdentifierGeneratorHelper.getIntegralDataTypeHolder(this.returnClass);
/*  95:    */     
/*  96:124 */     LOG.debugf("Fetching initial value: %s", this.sql);
/*  97:    */     try
/*  98:    */     {
/*  99:126 */       PreparedStatement st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.sql);
/* 100:    */       try
/* 101:    */       {
/* 102:128 */         ResultSet rs = st.executeQuery();
/* 103:    */         try
/* 104:    */         {
/* 105:130 */           if (rs.next()) {
/* 106:130 */             this.previousValueHolder.initialize(rs, 0L).increment();
/* 107:    */           } else {
/* 108:131 */             this.previousValueHolder.initialize(1L);
/* 109:    */           }
/* 110:132 */           this.sql = null;
/* 111:133 */           if (LOG.isDebugEnabled()) {
/* 112:134 */             LOG.debugf("First free id: %s", this.previousValueHolder.makeValue());
/* 113:    */           }
/* 114:    */         }
/* 115:    */         finally {}
/* 116:    */       }
/* 117:    */       finally
/* 118:    */       {
/* 119:142 */         st.close();
/* 120:    */       }
/* 121:    */     }
/* 122:    */     catch (SQLException sqle)
/* 123:    */     {
/* 124:146 */       throw session.getFactory().getSQLExceptionHelper().convert(sqle, "could not fetch initial value for increment generator", this.sql);
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.id.IncrementGenerator
 * JD-Core Version:    0.7.0.1
 */