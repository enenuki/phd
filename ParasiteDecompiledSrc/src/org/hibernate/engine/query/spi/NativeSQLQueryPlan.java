/*   1:    */ package org.hibernate.engine.query.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.QueryException;
/*  13:    */ import org.hibernate.action.internal.BulkOperationCleanupAction;
/*  14:    */ import org.hibernate.action.spi.AfterTransactionCompletionProcess;
/*  15:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  16:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  17:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  18:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*  19:    */ import org.hibernate.engine.spi.ActionQueue;
/*  20:    */ import org.hibernate.engine.spi.QueryParameters;
/*  21:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  22:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  23:    */ import org.hibernate.engine.spi.TypedValue;
/*  24:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  25:    */ import org.hibernate.event.spi.EventSource;
/*  26:    */ import org.hibernate.internal.CoreMessageLogger;
/*  27:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  28:    */ import org.hibernate.loader.custom.sql.SQLCustomQuery;
/*  29:    */ import org.hibernate.type.Type;
/*  30:    */ import org.jboss.logging.Logger;
/*  31:    */ 
/*  32:    */ public class NativeSQLQueryPlan
/*  33:    */   implements Serializable
/*  34:    */ {
/*  35:    */   private final String sourceQuery;
/*  36:    */   private final SQLCustomQuery customQuery;
/*  37: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, NativeSQLQueryPlan.class.getName());
/*  38:    */   
/*  39:    */   public NativeSQLQueryPlan(NativeSQLQuerySpecification specification, SessionFactoryImplementor factory)
/*  40:    */   {
/*  41: 65 */     this.sourceQuery = specification.getQueryString();
/*  42:    */     
/*  43: 67 */     this.customQuery = new SQLCustomQuery(specification.getQueryString(), specification.getQueryReturns(), specification.getQuerySpaces(), factory);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String getSourceQuery()
/*  47:    */   {
/*  48: 75 */     return this.sourceQuery;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SQLCustomQuery getCustomQuery()
/*  52:    */   {
/*  53: 79 */     return this.customQuery;
/*  54:    */   }
/*  55:    */   
/*  56:    */   private int[] getNamedParameterLocs(String name)
/*  57:    */     throws QueryException
/*  58:    */   {
/*  59: 83 */     Object loc = this.customQuery.getNamedParameterBindPoints().get(name);
/*  60: 84 */     if (loc == null) {
/*  61: 85 */       throw new QueryException("Named parameter does not appear in Query: " + name, this.customQuery.getSQL());
/*  62:    */     }
/*  63: 89 */     if ((loc instanceof Integer)) {
/*  64: 90 */       return new int[] { ((Integer)loc).intValue() };
/*  65:    */     }
/*  66: 93 */     return ArrayHelper.toIntArray((List)loc);
/*  67:    */   }
/*  68:    */   
/*  69:    */   private int bindPositionalParameters(PreparedStatement st, QueryParameters queryParameters, int start, SessionImplementor session)
/*  70:    */     throws SQLException
/*  71:    */   {
/*  72:118 */     Object[] values = queryParameters.getFilteredPositionalParameterValues();
/*  73:119 */     Type[] types = queryParameters.getFilteredPositionalParameterTypes();
/*  74:120 */     int span = 0;
/*  75:121 */     for (int i = 0; i < values.length; i++)
/*  76:    */     {
/*  77:122 */       types[i].nullSafeSet(st, values[i], start + span, session);
/*  78:123 */       span += types[i].getColumnSpan(session.getFactory());
/*  79:    */     }
/*  80:125 */     return span;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private int bindNamedParameters(PreparedStatement ps, Map namedParams, int start, SessionImplementor session)
/*  84:    */     throws SQLException
/*  85:    */   {
/*  86:148 */     if (namedParams != null)
/*  87:    */     {
/*  88:150 */       Iterator iter = namedParams.entrySet().iterator();
/*  89:151 */       int result = 0;
/*  90:152 */       while (iter.hasNext())
/*  91:    */       {
/*  92:153 */         Map.Entry e = (Map.Entry)iter.next();
/*  93:154 */         String name = (String)e.getKey();
/*  94:155 */         TypedValue typedval = (TypedValue)e.getValue();
/*  95:156 */         int[] locs = getNamedParameterLocs(name);
/*  96:157 */         for (int i = 0; i < locs.length; i++)
/*  97:    */         {
/*  98:158 */           LOG.debugf("bindNamedParameters() %s -> %s [%s]", typedval.getValue(), name, Integer.valueOf(locs[i] + start));
/*  99:159 */           typedval.getType().nullSafeSet(ps, typedval.getValue(), locs[i] + start, session);
/* 100:    */         }
/* 101:162 */         result += locs.length;
/* 102:    */       }
/* 103:164 */       return result;
/* 104:    */     }
/* 105:166 */     return 0;
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void coordinateSharedCacheCleanup(SessionImplementor session)
/* 109:    */   {
/* 110:170 */     BulkOperationCleanupAction action = new BulkOperationCleanupAction(session, getCustomQuery().getQuerySpaces());
/* 111:172 */     if (session.isEventSource()) {
/* 112:173 */       ((EventSource)session).getActionQueue().addAction(action);
/* 113:    */     } else {
/* 114:176 */       action.getAfterTransactionCompletionProcess().doAfterTransactionCompletion(true, session);
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int performExecuteUpdate(QueryParameters queryParameters, SessionImplementor session)
/* 119:    */     throws HibernateException
/* 120:    */   {
/* 121:183 */     coordinateSharedCacheCleanup(session);
/* 122:185 */     if (queryParameters.isCallable()) {
/* 123:186 */       throw new IllegalArgumentException("callable not yet supported for native queries");
/* 124:    */     }
/* 125:189 */     int result = 0;
/* 126:    */     try
/* 127:    */     {
/* 128:192 */       queryParameters.processFilters(this.customQuery.getSQL(), session);
/* 129:    */       
/* 130:194 */       String sql = queryParameters.getFilteredSQL();
/* 131:    */       
/* 132:196 */       PreparedStatement ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, false);
/* 133:    */       try
/* 134:    */       {
/* 135:199 */         int col = 1;
/* 136:200 */         col += bindPositionalParameters(ps, queryParameters, col, session);
/* 137:    */         
/* 138:202 */         col += bindNamedParameters(ps, queryParameters.getNamedParameters(), col, session);
/* 139:    */         
/* 140:204 */         result = ps.executeUpdate();
/* 141:    */       }
/* 142:    */       finally
/* 143:    */       {
/* 144:207 */         if (ps != null) {
/* 145:208 */           ps.close();
/* 146:    */         }
/* 147:    */       }
/* 148:    */     }
/* 149:    */     catch (SQLException sqle)
/* 150:    */     {
/* 151:213 */       throw session.getFactory().getSQLExceptionHelper().convert(sqle, "could not execute native bulk manipulation query", this.sourceQuery);
/* 152:    */     }
/* 153:217 */     return result;
/* 154:    */   }
/* 155:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.query.spi.NativeSQLQueryPlan
 * JD-Core Version:    0.7.0.1
 */