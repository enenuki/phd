/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.List;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.MultiTenancyStrategy;
/*  10:    */ import org.hibernate.Query;
/*  11:    */ import org.hibernate.SQLQuery;
/*  12:    */ import org.hibernate.ScrollableResults;
/*  13:    */ import org.hibernate.SessionException;
/*  14:    */ import org.hibernate.SharedSessionContract;
/*  15:    */ import org.hibernate.cache.spi.CacheKey;
/*  16:    */ import org.hibernate.cfg.Settings;
/*  17:    */ import org.hibernate.engine.jdbc.LobCreationContext.Callback;
/*  18:    */ import org.hibernate.engine.jdbc.spi.JdbcConnectionAccess;
/*  19:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  20:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  21:    */ import org.hibernate.engine.query.spi.HQLQueryPlan;
/*  22:    */ import org.hibernate.engine.query.spi.NativeSQLQueryPlan;
/*  23:    */ import org.hibernate.engine.query.spi.QueryPlanCache;
/*  24:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*  25:    */ import org.hibernate.engine.spi.EntityKey;
/*  26:    */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*  27:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  28:    */ import org.hibernate.engine.spi.QueryParameters;
/*  29:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  30:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  31:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  32:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  33:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  34:    */ import org.hibernate.jdbc.WorkExecutor;
/*  35:    */ import org.hibernate.jdbc.WorkExecutorVisitable;
/*  36:    */ import org.hibernate.persister.entity.EntityPersister;
/*  37:    */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  38:    */ import org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider;
/*  39:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  40:    */ import org.hibernate.type.Type;
/*  41:    */ 
/*  42:    */ public abstract class AbstractSessionImpl
/*  43:    */   implements Serializable, SharedSessionContract, SessionImplementor, TransactionContext
/*  44:    */ {
/*  45:    */   protected transient SessionFactoryImpl factory;
/*  46:    */   private final String tenantIdentifier;
/*  47: 69 */   private boolean closed = false;
/*  48:    */   private transient JdbcConnectionAccess jdbcConnectionAccess;
/*  49:    */   
/*  50:    */   protected AbstractSessionImpl(SessionFactoryImpl factory, String tenantIdentifier)
/*  51:    */   {
/*  52: 72 */     this.factory = factory;
/*  53: 73 */     this.tenantIdentifier = tenantIdentifier;
/*  54: 74 */     if (MultiTenancyStrategy.NONE == factory.getSettings().getMultiTenancyStrategy())
/*  55:    */     {
/*  56: 75 */       if (tenantIdentifier != null) {
/*  57: 76 */         throw new HibernateException("SessionFactory was not configured for multi-tenancy");
/*  58:    */       }
/*  59:    */     }
/*  60: 80 */     else if (tenantIdentifier == null) {
/*  61: 81 */       throw new HibernateException("SessionFactory configured for multi-tenancy, but no tenant identifier specified");
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public SessionFactoryImplementor getFactory()
/*  66:    */   {
/*  67: 87 */     return this.factory;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public TransactionEnvironment getTransactionEnvironment()
/*  71:    */   {
/*  72: 92 */     return this.factory.getTransactionEnvironment();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public <T> T execute(final LobCreationContext.Callback<T> callback)
/*  76:    */   {
/*  77: 97 */     getTransactionCoordinator().getJdbcCoordinator().coordinateWork(new WorkExecutorVisitable()
/*  78:    */     {
/*  79:    */       public T accept(WorkExecutor<T> workExecutor, Connection connection)
/*  80:    */         throws SQLException
/*  81:    */       {
/*  82:    */         try
/*  83:    */         {
/*  84:102 */           return callback.executeOnConnection(connection);
/*  85:    */         }
/*  86:    */         catch (SQLException e)
/*  87:    */         {
/*  88:105 */           throw AbstractSessionImpl.this.getFactory().getSQLExceptionHelper().convert(e, "Error creating contextual LOB : " + e.getMessage());
/*  89:    */         }
/*  90:    */       }
/*  91:    */     });
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isClosed()
/*  95:    */   {
/*  96:117 */     return this.closed;
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected void setClosed()
/* 100:    */   {
/* 101:121 */     this.closed = true;
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected void errorIfClosed()
/* 105:    */   {
/* 106:125 */     if (this.closed) {
/* 107:126 */       throw new SessionException("Session is closed!");
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Query getNamedQuery(String queryName)
/* 112:    */     throws MappingException
/* 113:    */   {
/* 114:132 */     errorIfClosed();
/* 115:133 */     NamedQueryDefinition nqd = this.factory.getNamedQuery(queryName);
/* 116:    */     Query query;
/* 117:135 */     if (nqd != null)
/* 118:    */     {
/* 119:136 */       String queryString = nqd.getQueryString();
/* 120:137 */       Query query = new QueryImpl(queryString, nqd.getFlushMode(), this, getHQLQueryPlan(queryString, false).getParameterMetadata());
/* 121:    */       
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:143 */       query.setComment("named HQL query " + queryName);
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:146 */       NamedSQLQueryDefinition nsqlqd = this.factory.getNamedSQLQuery(queryName);
/* 131:147 */       if (nsqlqd == null) {
/* 132:148 */         throw new MappingException("Named query not known: " + queryName);
/* 133:    */       }
/* 134:150 */       query = new SQLQueryImpl(nsqlqd, this, this.factory.getQueryPlanCache().getSQLParameterMetadata(nsqlqd.getQueryString()));
/* 135:    */       
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:155 */       query.setComment("named native SQL query " + queryName);
/* 140:156 */       nqd = nsqlqd;
/* 141:    */     }
/* 142:158 */     initQuery(query, nqd);
/* 143:159 */     return query;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Query getNamedSQLQuery(String queryName)
/* 147:    */     throws MappingException
/* 148:    */   {
/* 149:164 */     errorIfClosed();
/* 150:165 */     NamedSQLQueryDefinition nsqlqd = this.factory.getNamedSQLQuery(queryName);
/* 151:166 */     if (nsqlqd == null) {
/* 152:167 */       throw new MappingException("Named SQL query not known: " + queryName);
/* 153:    */     }
/* 154:169 */     Query query = new SQLQueryImpl(nsqlqd, this, this.factory.getQueryPlanCache().getSQLParameterMetadata(nsqlqd.getQueryString()));
/* 155:    */     
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:174 */     query.setComment("named native SQL query " + queryName);
/* 160:175 */     initQuery(query, nsqlqd);
/* 161:176 */     return query;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private void initQuery(Query query, NamedQueryDefinition nqd)
/* 165:    */   {
/* 166:180 */     query.setCacheable(nqd.isCacheable());
/* 167:181 */     query.setCacheRegion(nqd.getCacheRegion());
/* 168:182 */     if (nqd.getTimeout() != null) {
/* 169:182 */       query.setTimeout(nqd.getTimeout().intValue());
/* 170:    */     }
/* 171:183 */     if (nqd.getFetchSize() != null) {
/* 172:183 */       query.setFetchSize(nqd.getFetchSize().intValue());
/* 173:    */     }
/* 174:184 */     if (nqd.getCacheMode() != null) {
/* 175:184 */       query.setCacheMode(nqd.getCacheMode());
/* 176:    */     }
/* 177:185 */     query.setReadOnly(nqd.isReadOnly());
/* 178:186 */     if (nqd.getComment() != null) {
/* 179:186 */       query.setComment(nqd.getComment());
/* 180:    */     }
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Query createQuery(String queryString)
/* 184:    */   {
/* 185:191 */     errorIfClosed();
/* 186:192 */     QueryImpl query = new QueryImpl(queryString, this, getHQLQueryPlan(queryString, false).getParameterMetadata());
/* 187:    */     
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:197 */     query.setComment(queryString);
/* 192:198 */     return query;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public SQLQuery createSQLQuery(String sql)
/* 196:    */   {
/* 197:203 */     errorIfClosed();
/* 198:204 */     SQLQueryImpl query = new SQLQueryImpl(sql, this, this.factory.getQueryPlanCache().getSQLParameterMetadata(sql));
/* 199:    */     
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:209 */     query.setComment("dynamic native SQL query");
/* 204:210 */     return query;
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected HQLQueryPlan getHQLQueryPlan(String query, boolean shallow)
/* 208:    */     throws HibernateException
/* 209:    */   {
/* 210:214 */     return this.factory.getQueryPlanCache().getHQLQueryPlan(query, shallow, getEnabledFilters());
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected NativeSQLQueryPlan getNativeSQLQueryPlan(NativeSQLQuerySpecification spec)
/* 214:    */     throws HibernateException
/* 215:    */   {
/* 216:218 */     return this.factory.getQueryPlanCache().getNativeSQLQueryPlan(spec);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List list(NativeSQLQuerySpecification spec, QueryParameters queryParameters)
/* 220:    */     throws HibernateException
/* 221:    */   {
/* 222:224 */     return listCustomQuery(getNativeSQLQueryPlan(spec).getCustomQuery(), queryParameters);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public ScrollableResults scroll(NativeSQLQuerySpecification spec, QueryParameters queryParameters)
/* 226:    */     throws HibernateException
/* 227:    */   {
/* 228:230 */     return scrollCustomQuery(getNativeSQLQueryPlan(spec).getCustomQuery(), queryParameters);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getTenantIdentifier()
/* 232:    */   {
/* 233:235 */     return this.tenantIdentifier;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public EntityKey generateEntityKey(Serializable id, EntityPersister persister)
/* 237:    */   {
/* 238:240 */     return new EntityKey(id, persister, getTenantIdentifier());
/* 239:    */   }
/* 240:    */   
/* 241:    */   public CacheKey generateCacheKey(Serializable id, Type type, String entityOrRoleName)
/* 242:    */   {
/* 243:245 */     return new CacheKey(id, type, entityOrRoleName, getTenantIdentifier(), getFactory());
/* 244:    */   }
/* 245:    */   
/* 246:    */   public JdbcConnectionAccess getJdbcConnectionAccess()
/* 247:    */   {
/* 248:252 */     if (this.jdbcConnectionAccess == null) {
/* 249:253 */       if (MultiTenancyStrategy.NONE == this.factory.getSettings().getMultiTenancyStrategy()) {
/* 250:254 */         this.jdbcConnectionAccess = new NonContextualJdbcConnectionAccess((ConnectionProvider)this.factory.getServiceRegistry().getService(ConnectionProvider.class), null);
/* 251:    */       } else {
/* 252:259 */         this.jdbcConnectionAccess = new ContextualJdbcConnectionAccess((MultiTenantConnectionProvider)this.factory.getServiceRegistry().getService(MultiTenantConnectionProvider.class), null);
/* 253:    */       }
/* 254:    */     }
/* 255:264 */     return this.jdbcConnectionAccess;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private static class NonContextualJdbcConnectionAccess
/* 259:    */     implements JdbcConnectionAccess, Serializable
/* 260:    */   {
/* 261:    */     private final ConnectionProvider connectionProvider;
/* 262:    */     
/* 263:    */     private NonContextualJdbcConnectionAccess(ConnectionProvider connectionProvider)
/* 264:    */     {
/* 265:271 */       this.connectionProvider = connectionProvider;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public Connection obtainConnection()
/* 269:    */       throws SQLException
/* 270:    */     {
/* 271:276 */       return this.connectionProvider.getConnection();
/* 272:    */     }
/* 273:    */     
/* 274:    */     public void releaseConnection(Connection connection)
/* 275:    */       throws SQLException
/* 276:    */     {
/* 277:281 */       this.connectionProvider.closeConnection(connection);
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   private class ContextualJdbcConnectionAccess
/* 282:    */     implements JdbcConnectionAccess, Serializable
/* 283:    */   {
/* 284:    */     private final MultiTenantConnectionProvider connectionProvider;
/* 285:    */     
/* 286:    */     private ContextualJdbcConnectionAccess(MultiTenantConnectionProvider connectionProvider)
/* 287:    */     {
/* 288:289 */       this.connectionProvider = connectionProvider;
/* 289:    */     }
/* 290:    */     
/* 291:    */     public Connection obtainConnection()
/* 292:    */       throws SQLException
/* 293:    */     {
/* 294:294 */       if (AbstractSessionImpl.this.tenantIdentifier == null) {
/* 295:295 */         throw new HibernateException("Tenant identifier required!");
/* 296:    */       }
/* 297:297 */       return this.connectionProvider.getConnection(AbstractSessionImpl.this.tenantIdentifier);
/* 298:    */     }
/* 299:    */     
/* 300:    */     public void releaseConnection(Connection connection)
/* 301:    */       throws SQLException
/* 302:    */     {
/* 303:302 */       if (AbstractSessionImpl.this.tenantIdentifier == null) {
/* 304:303 */         throw new HibernateException("Tenant identifier required!");
/* 305:    */       }
/* 306:305 */       this.connectionProvider.releaseConnection(AbstractSessionImpl.this.tenantIdentifier, connection);
/* 307:    */     }
/* 308:    */   }
/* 309:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.AbstractSessionImpl
 * JD-Core Version:    0.7.0.1
 */