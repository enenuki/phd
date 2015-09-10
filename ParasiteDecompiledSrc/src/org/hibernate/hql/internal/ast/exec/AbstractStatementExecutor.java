/*   1:    */ package org.hibernate.hql.internal.ast.exec;
/*   2:    */ 
/*   3:    */ import antlr.RecognitionException;
/*   4:    */ import antlr.collections.AST;
/*   5:    */ import java.sql.Connection;
/*   6:    */ import java.sql.PreparedStatement;
/*   7:    */ import java.sql.SQLWarning;
/*   8:    */ import java.sql.Statement;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.List;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.action.internal.BulkOperationCleanupAction;
/*  13:    */ import org.hibernate.action.spi.AfterTransactionCompletionProcess;
/*  14:    */ import org.hibernate.cfg.Settings;
/*  15:    */ import org.hibernate.dialect.Dialect;
/*  16:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  17:    */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*  18:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  19:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  20:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper.WarningHandler;
/*  21:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper.WarningHandlerLoggingSupport;
/*  22:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  23:    */ import org.hibernate.engine.spi.ActionQueue;
/*  24:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  25:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  26:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  27:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  28:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  29:    */ import org.hibernate.event.spi.EventSource;
/*  30:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  31:    */ import org.hibernate.hql.internal.ast.SqlGenerator;
/*  32:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  33:    */ import org.hibernate.internal.CoreMessageLogger;
/*  34:    */ import org.hibernate.internal.util.StringHelper;
/*  35:    */ import org.hibernate.jdbc.AbstractWork;
/*  36:    */ import org.hibernate.persister.entity.Queryable;
/*  37:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  38:    */ import org.hibernate.sql.InsertSelect;
/*  39:    */ import org.hibernate.sql.Select;
/*  40:    */ import org.hibernate.sql.SelectFragment;
/*  41:    */ import org.jboss.logging.Logger;
/*  42:    */ 
/*  43:    */ public abstract class AbstractStatementExecutor
/*  44:    */   implements StatementExecutor
/*  45:    */ {
/*  46: 61 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractStatementExecutor.class.getName());
/*  47:    */   private final HqlSqlWalker walker;
/*  48: 65 */   private List idSelectParameterSpecifications = Collections.EMPTY_LIST;
/*  49:    */   
/*  50:    */   public AbstractStatementExecutor(HqlSqlWalker walker, CoreMessageLogger log)
/*  51:    */   {
/*  52: 69 */     this.walker = walker;
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected HqlSqlWalker getWalker()
/*  56:    */   {
/*  57: 73 */     return this.walker;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected SessionFactoryImplementor getFactory()
/*  61:    */   {
/*  62: 77 */     return this.walker.getSessionFactoryHelper().getFactory();
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected List getIdSelectParameterSpecifications()
/*  66:    */   {
/*  67: 81 */     return this.idSelectParameterSpecifications;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected abstract Queryable[] getAffectedQueryables();
/*  71:    */   
/*  72:    */   protected String generateIdInsertSelect(Queryable persister, String tableAlias, AST whereClause)
/*  73:    */   {
/*  74: 87 */     Select select = new Select(getFactory().getDialect());
/*  75: 88 */     SelectFragment selectFragment = new SelectFragment().addColumns(tableAlias, persister.getIdentifierColumnNames(), persister.getIdentifierColumnNames());
/*  76:    */     
/*  77: 90 */     select.setSelectClause(selectFragment.toFragmentString().substring(2));
/*  78:    */     
/*  79: 92 */     String rootTableName = persister.getTableName();
/*  80: 93 */     String fromJoinFragment = persister.fromJoinFragment(tableAlias, true, false);
/*  81: 94 */     String whereJoinFragment = persister.whereJoinFragment(tableAlias, true, false);
/*  82:    */     
/*  83: 96 */     select.setFromClause(rootTableName + ' ' + tableAlias + fromJoinFragment);
/*  84: 98 */     if (whereJoinFragment == null)
/*  85:    */     {
/*  86: 99 */       whereJoinFragment = "";
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90:102 */       whereJoinFragment = whereJoinFragment.trim();
/*  91:103 */       if (whereJoinFragment.startsWith("and")) {
/*  92:104 */         whereJoinFragment = whereJoinFragment.substring(4);
/*  93:    */       }
/*  94:    */     }
/*  95:108 */     String userWhereClause = "";
/*  96:109 */     if (whereClause.getNumberOfChildren() != 0)
/*  97:    */     {
/*  98:    */       try
/*  99:    */       {
/* 100:113 */         SqlGenerator sqlGenerator = new SqlGenerator(getFactory());
/* 101:114 */         sqlGenerator.whereClause(whereClause);
/* 102:115 */         userWhereClause = sqlGenerator.getSQL().substring(7);
/* 103:116 */         this.idSelectParameterSpecifications = sqlGenerator.getCollectedParameters();
/* 104:    */       }
/* 105:    */       catch (RecognitionException e)
/* 106:    */       {
/* 107:119 */         throw new HibernateException("Unable to generate id select for DML operation", e);
/* 108:    */       }
/* 109:121 */       if (whereJoinFragment.length() > 0) {
/* 110:122 */         whereJoinFragment = whereJoinFragment + " and ";
/* 111:    */       }
/* 112:    */     }
/* 113:126 */     select.setWhereClause(whereJoinFragment + userWhereClause);
/* 114:    */     
/* 115:128 */     InsertSelect insert = new InsertSelect(getFactory().getDialect());
/* 116:129 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 117:130 */       insert.setComment("insert-select for " + persister.getEntityName() + " ids");
/* 118:    */     }
/* 119:132 */     insert.setTableName(persister.getTemporaryIdTableName());
/* 120:133 */     insert.setSelect(select);
/* 121:134 */     return insert.toStatementString();
/* 122:    */   }
/* 123:    */   
/* 124:    */   protected String generateIdSubselect(Queryable persister)
/* 125:    */   {
/* 126:138 */     return "select " + StringHelper.join(", ", persister.getIdentifierColumnNames()) + " from " + persister.getTemporaryIdTableName();
/* 127:    */   }
/* 128:    */   
/* 129:    */   private static class TemporaryTableCreationWork
/* 130:    */     extends AbstractWork
/* 131:    */   {
/* 132:    */     private final Queryable persister;
/* 133:    */     
/* 134:    */     private TemporaryTableCreationWork(Queryable persister)
/* 135:    */     {
/* 136:146 */       this.persister = persister;
/* 137:    */     }
/* 138:    */     
/* 139:    */     public void execute(Connection connection)
/* 140:    */     {
/* 141:    */       try
/* 142:    */       {
/* 143:152 */         Statement statement = connection.createStatement();
/* 144:    */         try
/* 145:    */         {
/* 146:154 */           statement.executeUpdate(this.persister.getTemporaryIdTableDDL());
/* 147:155 */           ((JdbcServices)this.persister.getFactory().getServiceRegistry().getService(JdbcServices.class)).getSqlExceptionHelper().handleAndClearWarnings(statement, AbstractStatementExecutor.CREATION_WARNING_HANDLER);
/* 148:    */         }
/* 149:    */         finally
/* 150:    */         {
/* 151:    */           try
/* 152:    */           {
/* 153:163 */             statement.close();
/* 154:    */           }
/* 155:    */           catch (Throwable ignore) {}
/* 156:    */         }
/* 157:173 */         return;
/* 158:    */       }
/* 159:    */       catch (Exception e)
/* 160:    */       {
/* 161:171 */         AbstractStatementExecutor.LOG.debug("unable to create temporary id table [" + e.getMessage() + "]");
/* 162:    */       }
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   protected void createTemporaryTableIfNecessary(Queryable persister, SessionImplementor session)
/* 167:    */   {
/* 168:178 */     TemporaryTableCreationWork work = new TemporaryTableCreationWork(persister, null);
/* 169:179 */     if (shouldIsolateTemporaryTableDDL())
/* 170:    */     {
/* 171:180 */       session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(work, getFactory().getSettings().isDataDefinitionInTransactionSupported());
/* 172:    */     }
/* 173:    */     else
/* 174:    */     {
/* 175:186 */       Connection connection = session.getTransactionCoordinator().getJdbcCoordinator().getLogicalConnection().getShareableConnectionProxy();
/* 176:    */       
/* 177:    */ 
/* 178:    */ 
/* 179:190 */       work.execute(connection);
/* 180:191 */       session.getTransactionCoordinator().getJdbcCoordinator().getLogicalConnection().afterStatementExecution();
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:198 */   private static SqlExceptionHelper.WarningHandler CREATION_WARNING_HANDLER = new SqlExceptionHelper.WarningHandlerLoggingSupport()
/* 185:    */   {
/* 186:    */     public boolean doProcess()
/* 187:    */     {
/* 188:200 */       return AbstractStatementExecutor.LOG.isDebugEnabled();
/* 189:    */     }
/* 190:    */     
/* 191:    */     public void prepare(SQLWarning warning)
/* 192:    */     {
/* 193:204 */       AbstractStatementExecutor.LOG.warningsCreatingTempTable(warning);
/* 194:    */     }
/* 195:    */     
/* 196:    */     protected void logWarning(String description, String message)
/* 197:    */     {
/* 198:209 */       AbstractStatementExecutor.LOG.debug(description);
/* 199:210 */       AbstractStatementExecutor.LOG.debug(message);
/* 200:    */     }
/* 201:    */   };
/* 202:    */   
/* 203:    */   private static class TemporaryTableDropWork
/* 204:    */     extends AbstractWork
/* 205:    */   {
/* 206:    */     private final Queryable persister;
/* 207:    */     private final SessionImplementor session;
/* 208:    */     
/* 209:    */     private TemporaryTableDropWork(Queryable persister, SessionImplementor session)
/* 210:    */     {
/* 211:219 */       this.persister = persister;
/* 212:220 */       this.session = session;
/* 213:    */     }
/* 214:    */     
/* 215:    */     public void execute(Connection connection)
/* 216:    */     {
/* 217:225 */       String command = this.session.getFactory().getDialect().getDropTemporaryTableString() + ' ' + this.persister.getTemporaryIdTableName();
/* 218:    */       try
/* 219:    */       {
/* 220:228 */         Statement statement = connection.createStatement();
/* 221:    */         try
/* 222:    */         {
/* 223:230 */           statement = connection.createStatement();
/* 224:231 */           statement.executeUpdate(command);
/* 225:    */         }
/* 226:    */         finally
/* 227:    */         {
/* 228:    */           try
/* 229:    */           {
/* 230:235 */             statement.close();
/* 231:    */           }
/* 232:    */           catch (Throwable ignore) {}
/* 233:    */         }
/* 234:    */         return;
/* 235:    */       }
/* 236:    */       catch (Exception e)
/* 237:    */       {
/* 238:243 */         AbstractStatementExecutor.LOG.warn("unable to drop temporary id table after use [" + e.getMessage() + "]");
/* 239:    */       }
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   protected void dropTemporaryTableIfNecessary(Queryable persister, SessionImplementor session)
/* 244:    */   {
/* 245:249 */     if (getFactory().getDialect().dropTemporaryTableAfterUse())
/* 246:    */     {
/* 247:250 */       TemporaryTableDropWork work = new TemporaryTableDropWork(persister, session, null);
/* 248:251 */       if (shouldIsolateTemporaryTableDDL())
/* 249:    */       {
/* 250:252 */         session.getTransactionCoordinator().getTransaction().createIsolationDelegate().delegateWork(work, getFactory().getSettings().isDataDefinitionInTransactionSupported());
/* 251:    */       }
/* 252:    */       else
/* 253:    */       {
/* 254:258 */         Connection connection = session.getTransactionCoordinator().getJdbcCoordinator().getLogicalConnection().getShareableConnectionProxy();
/* 255:    */         
/* 256:    */ 
/* 257:    */ 
/* 258:262 */         work.execute(connection);
/* 259:263 */         session.getTransactionCoordinator().getJdbcCoordinator().getLogicalConnection().afterStatementExecution();
/* 260:    */       }
/* 261:    */     }
/* 262:    */     else
/* 263:    */     {
/* 264:271 */       PreparedStatement ps = null;
/* 265:    */       try
/* 266:    */       {
/* 267:273 */         String sql = "delete from " + persister.getTemporaryIdTableName();
/* 268:274 */         ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, false);
/* 269:275 */         ps.executeUpdate();
/* 270:    */       }
/* 271:    */       catch (Throwable t)
/* 272:    */       {
/* 273:278 */         LOG.unableToCleanupTemporaryIdTable(t);
/* 274:    */       }
/* 275:    */       finally
/* 276:    */       {
/* 277:281 */         if (ps != null) {
/* 278:    */           try
/* 279:    */           {
/* 280:283 */             ps.close();
/* 281:    */           }
/* 282:    */           catch (Throwable ignore) {}
/* 283:    */         }
/* 284:    */       }
/* 285:    */     }
/* 286:    */   }
/* 287:    */   
/* 288:    */   protected void coordinateSharedCacheCleanup(SessionImplementor session)
/* 289:    */   {
/* 290:294 */     BulkOperationCleanupAction action = new BulkOperationCleanupAction(session, getAffectedQueryables());
/* 291:296 */     if (session.isEventSource()) {
/* 292:297 */       ((EventSource)session).getActionQueue().addAction(action);
/* 293:    */     } else {
/* 294:300 */       action.getAfterTransactionCompletionProcess().doAfterTransactionCompletion(true, session);
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   protected boolean shouldIsolateTemporaryTableDDL()
/* 299:    */   {
/* 300:306 */     Boolean dialectVote = getFactory().getDialect().performTemporaryTableDDLInIsolation();
/* 301:307 */     if (dialectVote != null) {
/* 302:307 */       return dialectVote.booleanValue();
/* 303:    */     }
/* 304:308 */     return getFactory().getSettings().isDataDefinitionImplicitCommit();
/* 305:    */   }
/* 306:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.exec.AbstractStatementExecutor
 * JD-Core Version:    0.7.0.1
 */