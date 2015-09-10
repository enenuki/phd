/*   1:    */ package org.hibernate.service.jdbc.connections.internal;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.DriverManager;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Properties;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.cfg.Environment;
/*  11:    */ import org.hibernate.internal.CoreMessageLogger;
/*  12:    */ import org.hibernate.internal.util.ReflectHelper;
/*  13:    */ import org.hibernate.internal.util.config.ConfigurationHelper;
/*  14:    */ import org.hibernate.service.UnknownUnwrapTypeException;
/*  15:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  16:    */ import org.hibernate.service.spi.Configurable;
/*  17:    */ import org.hibernate.service.spi.Stoppable;
/*  18:    */ import org.jboss.logging.Logger;
/*  19:    */ 
/*  20:    */ public class DriverManagerConnectionProviderImpl
/*  21:    */   implements ConnectionProvider, Configurable, Stoppable
/*  22:    */ {
/*  23: 58 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DriverManagerConnectionProviderImpl.class.getName());
/*  24:    */   private String url;
/*  25:    */   private Properties connectionProps;
/*  26:    */   private Integer isolation;
/*  27:    */   private int poolSize;
/*  28:    */   private boolean autocommit;
/*  29: 66 */   private final ArrayList<Connection> pool = new ArrayList();
/*  30: 67 */   private int checkedOut = 0;
/*  31:    */   private boolean stopped;
/*  32:    */   
/*  33:    */   public boolean isUnwrappableAs(Class unwrapType)
/*  34:    */   {
/*  35: 73 */     return (ConnectionProvider.class.equals(unwrapType)) || (DriverManagerConnectionProviderImpl.class.isAssignableFrom(unwrapType));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public <T> T unwrap(Class<T> unwrapType)
/*  39:    */   {
/*  40: 80 */     if ((ConnectionProvider.class.equals(unwrapType)) || (DriverManagerConnectionProviderImpl.class.isAssignableFrom(unwrapType))) {
/*  41: 82 */       return this;
/*  42:    */     }
/*  43: 85 */     throw new UnknownUnwrapTypeException(unwrapType);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void configure(Map configurationValues)
/*  47:    */   {
/*  48: 90 */     LOG.usingHibernateBuiltInConnectionPool();
/*  49:    */     
/*  50: 92 */     String driverClassName = (String)configurationValues.get("hibernate.connection.driver_class");
/*  51: 93 */     if (driverClassName == null) {
/*  52: 93 */       LOG.jdbcDriverNotSpecified("hibernate.connection.driver_class");
/*  53:    */     } else {
/*  54:    */       try
/*  55:    */       {
/*  56: 97 */         Class.forName(driverClassName);
/*  57:    */       }
/*  58:    */       catch (ClassNotFoundException cnfe)
/*  59:    */       {
/*  60:    */         try
/*  61:    */         {
/*  62:101 */           ReflectHelper.classForName(driverClassName);
/*  63:    */         }
/*  64:    */         catch (ClassNotFoundException e)
/*  65:    */         {
/*  66:104 */           throw new HibernateException("Specified JDBC Driver " + driverClassName + " class not found", e);
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70:109 */     this.poolSize = ConfigurationHelper.getInt("hibernate.connection.pool_size", configurationValues, 20);
/*  71:110 */     LOG.hibernateConnectionPoolSize(this.poolSize);
/*  72:    */     
/*  73:112 */     this.autocommit = ConfigurationHelper.getBoolean("hibernate.connection.autocommit", configurationValues);
/*  74:113 */     LOG.autoCommitMode(this.autocommit);
/*  75:    */     
/*  76:115 */     this.isolation = ConfigurationHelper.getInteger("hibernate.connection.isolation", configurationValues);
/*  77:116 */     if (this.isolation != null) {
/*  78:116 */       LOG.jdbcIsolationLevel(Environment.isolationLevelToString(this.isolation.intValue()));
/*  79:    */     }
/*  80:118 */     this.url = ((String)configurationValues.get("hibernate.connection.url"));
/*  81:119 */     if (this.url == null)
/*  82:    */     {
/*  83:120 */       String msg = LOG.jdbcUrlNotSpecified("hibernate.connection.url");
/*  84:121 */       LOG.error(msg);
/*  85:122 */       throw new HibernateException(msg);
/*  86:    */     }
/*  87:125 */     this.connectionProps = ConnectionProviderInitiator.getConnectionProperties(configurationValues);
/*  88:    */     
/*  89:127 */     LOG.usingDriver(driverClassName, this.url);
/*  90:129 */     if (LOG.isDebugEnabled()) {
/*  91:130 */       LOG.connectionProperties(this.connectionProps);
/*  92:    */     } else {
/*  93:132 */       LOG.connectionProperties(ConfigurationHelper.maskOut(this.connectionProps, "password"));
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void stop()
/*  98:    */   {
/*  99:136 */     LOG.cleaningUpConnectionPool(this.url);
/* 100:138 */     for (Connection connection : this.pool) {
/* 101:    */       try
/* 102:    */       {
/* 103:140 */         connection.close();
/* 104:    */       }
/* 105:    */       catch (SQLException sqle)
/* 106:    */       {
/* 107:143 */         LOG.unableToClosePooledConnection(sqle);
/* 108:    */       }
/* 109:    */     }
/* 110:146 */     this.pool.clear();
/* 111:147 */     this.stopped = true;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Connection getConnection()
/* 115:    */     throws SQLException
/* 116:    */   {
/* 117:151 */     LOG.tracev("Total checked-out connections: {0}", Integer.valueOf(this.checkedOut));
/* 118:154 */     synchronized (this.pool)
/* 119:    */     {
/* 120:155 */       if (!this.pool.isEmpty())
/* 121:    */       {
/* 122:156 */         int last = this.pool.size() - 1;
/* 123:157 */         LOG.tracev("Using pooled JDBC connection, pool size: {0}", Integer.valueOf(last));
/* 124:158 */         Connection pooled = (Connection)this.pool.remove(last);
/* 125:159 */         if (this.isolation != null) {
/* 126:160 */           pooled.setTransactionIsolation(this.isolation.intValue());
/* 127:    */         }
/* 128:162 */         if (pooled.getAutoCommit() != this.autocommit) {
/* 129:163 */           pooled.setAutoCommit(this.autocommit);
/* 130:    */         }
/* 131:165 */         this.checkedOut += 1;
/* 132:166 */         return pooled;
/* 133:    */       }
/* 134:    */     }
/* 135:172 */     LOG.debug("Opening new JDBC connection");
/* 136:173 */     Connection conn = DriverManager.getConnection(this.url, this.connectionProps);
/* 137:174 */     if (this.isolation != null) {
/* 138:175 */       conn.setTransactionIsolation(this.isolation.intValue());
/* 139:    */     }
/* 140:177 */     if (conn.getAutoCommit() != this.autocommit) {
/* 141:178 */       conn.setAutoCommit(this.autocommit);
/* 142:    */     }
/* 143:181 */     if (LOG.isDebugEnabled()) {
/* 144:182 */       LOG.debugf("Created connection to: %s, Isolation Level: %s", this.url, Integer.valueOf(conn.getTransactionIsolation()));
/* 145:    */     }
/* 146:185 */     this.checkedOut += 1;
/* 147:186 */     return conn;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void closeConnection(Connection conn)
/* 151:    */     throws SQLException
/* 152:    */   {
/* 153:190 */     this.checkedOut -= 1;
/* 154:193 */     synchronized (this.pool)
/* 155:    */     {
/* 156:194 */       int currentSize = this.pool.size();
/* 157:195 */       if (currentSize < this.poolSize)
/* 158:    */       {
/* 159:196 */         LOG.tracev("Returning connection to pool, pool size: {0}", Integer.valueOf(currentSize + 1));
/* 160:197 */         this.pool.add(conn);
/* 161:198 */         return;
/* 162:    */       }
/* 163:    */     }
/* 164:202 */     LOG.debug("Closing JDBC connection");
/* 165:203 */     conn.close();
/* 166:    */   }
/* 167:    */   
/* 168:    */   protected void finalize()
/* 169:    */     throws Throwable
/* 170:    */   {
/* 171:208 */     if (!this.stopped) {
/* 172:209 */       stop();
/* 173:    */     }
/* 174:211 */     super.finalize();
/* 175:    */   }
/* 176:    */   
/* 177:    */   public boolean supportsAggressiveRelease()
/* 178:    */   {
/* 179:215 */     return false;
/* 180:    */   }
/* 181:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.internal.DriverManagerConnectionProviderImpl
 * JD-Core Version:    0.7.0.1
 */