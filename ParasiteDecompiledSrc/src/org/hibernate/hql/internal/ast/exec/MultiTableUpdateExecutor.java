/*   1:    */ package org.hibernate.hql.internal.ast.exec;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.cfg.Settings;
/*  10:    */ import org.hibernate.dialect.Dialect;
/*  11:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  12:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  13:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  14:    */ import org.hibernate.engine.spi.QueryParameters;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  18:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  19:    */ import org.hibernate.hql.internal.ast.tree.AssignmentSpecification;
/*  20:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  21:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  22:    */ import org.hibernate.hql.internal.ast.tree.UpdateStatement;
/*  23:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.util.StringHelper;
/*  26:    */ import org.hibernate.param.ParameterSpecification;
/*  27:    */ import org.hibernate.persister.entity.Queryable;
/*  28:    */ import org.hibernate.sql.Update;
/*  29:    */ import org.jboss.logging.Logger;
/*  30:    */ 
/*  31:    */ public class MultiTableUpdateExecutor
/*  32:    */   extends AbstractStatementExecutor
/*  33:    */ {
/*  34: 55 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MultiTableUpdateExecutor.class.getName());
/*  35:    */   private final Queryable persister;
/*  36:    */   private final String idInsertSelect;
/*  37:    */   private final String[] updates;
/*  38:    */   private final ParameterSpecification[][] hqlParameters;
/*  39:    */   
/*  40:    */   public MultiTableUpdateExecutor(HqlSqlWalker walker)
/*  41:    */   {
/*  42: 64 */     super(walker, null);
/*  43: 66 */     if (!walker.getSessionFactoryHelper().getFactory().getDialect().supportsTemporaryTables()) {
/*  44: 67 */       throw new HibernateException("cannot doAfterTransactionCompletion multi-table updates using dialect not supporting temp tables");
/*  45:    */     }
/*  46: 70 */     UpdateStatement updateStatement = (UpdateStatement)walker.getAST();
/*  47: 71 */     FromElement fromElement = updateStatement.getFromClause().getFromElement();
/*  48: 72 */     String bulkTargetAlias = fromElement.getTableAlias();
/*  49: 73 */     this.persister = fromElement.getQueryable();
/*  50:    */     
/*  51: 75 */     this.idInsertSelect = generateIdInsertSelect(this.persister, bulkTargetAlias, updateStatement.getWhereClause());
/*  52: 76 */     LOG.tracev("Generated ID-INSERT-SELECT SQL (multi-table update) : {0}", this.idInsertSelect);
/*  53:    */     
/*  54: 78 */     String[] tableNames = this.persister.getConstraintOrderedTableNameClosure();
/*  55: 79 */     String[][] columnNames = this.persister.getContraintOrderedTableKeyColumnClosure();
/*  56:    */     
/*  57: 81 */     String idSubselect = generateIdSubselect(this.persister);
/*  58: 82 */     List assignmentSpecifications = walker.getAssignmentSpecifications();
/*  59:    */     
/*  60: 84 */     this.updates = new String[tableNames.length];
/*  61: 85 */     this.hqlParameters = new ParameterSpecification[tableNames.length][];
/*  62: 86 */     for (int tableIndex = 0; tableIndex < tableNames.length; tableIndex++)
/*  63:    */     {
/*  64: 87 */       boolean affected = false;
/*  65: 88 */       List parameterList = new ArrayList();
/*  66: 89 */       Update update = new Update(getFactory().getDialect()).setTableName(tableNames[tableIndex]).setWhere("(" + StringHelper.join(", ", columnNames[tableIndex]) + ") IN (" + idSubselect + ")");
/*  67: 92 */       if (getFactory().getSettings().isCommentsEnabled()) {
/*  68: 93 */         update.setComment("bulk update");
/*  69:    */       }
/*  70: 95 */       Iterator itr = assignmentSpecifications.iterator();
/*  71: 96 */       while (itr.hasNext())
/*  72:    */       {
/*  73: 97 */         AssignmentSpecification specification = (AssignmentSpecification)itr.next();
/*  74: 98 */         if (specification.affectsTable(tableNames[tableIndex]))
/*  75:    */         {
/*  76: 99 */           affected = true;
/*  77:100 */           update.appendAssignmentFragment(specification.getSqlAssignmentFragment());
/*  78:101 */           if (specification.getParameters() != null) {
/*  79:102 */             for (int paramIndex = 0; paramIndex < specification.getParameters().length; paramIndex++) {
/*  80:103 */               parameterList.add(specification.getParameters()[paramIndex]);
/*  81:    */             }
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:108 */       if (affected)
/*  86:    */       {
/*  87:109 */         this.updates[tableIndex] = update.toStatementString();
/*  88:110 */         this.hqlParameters[tableIndex] = ((ParameterSpecification[])(ParameterSpecification[])parameterList.toArray(new ParameterSpecification[0]));
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Queryable getAffectedQueryable()
/*  94:    */   {
/*  95:116 */     return this.persister;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String[] getSqlStatements()
/*  99:    */   {
/* 100:120 */     return this.updates;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int execute(QueryParameters parameters, SessionImplementor session)
/* 104:    */     throws HibernateException
/* 105:    */   {
/* 106:124 */     coordinateSharedCacheCleanup(session);
/* 107:    */     
/* 108:126 */     createTemporaryTableIfNecessary(this.persister, session);
/* 109:    */     try
/* 110:    */     {
/* 111:130 */       PreparedStatement ps = null;
/* 112:131 */       int resultCount = 0;
/* 113:    */       try
/* 114:    */       {
/* 115:    */         try
/* 116:    */         {
/* 117:134 */           ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.idInsertSelect, false);
/* 118:    */           
/* 119:    */ 
/* 120:    */ 
/* 121:138 */           Iterator whereParams = getIdSelectParameterSpecifications().iterator();
/* 122:139 */           int sum = 1;
/* 123:140 */           while (whereParams.hasNext()) {
/* 124:141 */             sum += ((ParameterSpecification)whereParams.next()).bind(ps, parameters, session, sum);
/* 125:    */           }
/* 126:143 */           resultCount = ps.executeUpdate();
/* 127:    */         }
/* 128:    */         finally
/* 129:    */         {
/* 130:146 */           if (ps != null) {
/* 131:147 */             ps.close();
/* 132:    */           }
/* 133:    */         }
/* 134:    */       }
/* 135:    */       catch (SQLException e)
/* 136:    */       {
/* 137:152 */         throw getFactory().getSQLExceptionHelper().convert(e, "could not insert/select ids for bulk update", this.idInsertSelect);
/* 138:    */       }
/* 139:160 */       for (int i = 0; i < this.updates.length; i++) {
/* 140:161 */         if (this.updates[i] != null) {
/* 141:    */           try
/* 142:    */           {
/* 143:    */             try
/* 144:    */             {
/* 145:166 */               ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.updates[i], false);
/* 146:167 */               if (this.hqlParameters[i] != null)
/* 147:    */               {
/* 148:168 */                 int position = 1;
/* 149:169 */                 for (int x = 0; x < this.hqlParameters[i].length; x++) {
/* 150:170 */                   position += this.hqlParameters[i][x].bind(ps, parameters, session, position);
/* 151:    */                 }
/* 152:    */               }
/* 153:173 */               ps.executeUpdate();
/* 154:    */             }
/* 155:    */             finally
/* 156:    */             {
/* 157:176 */               if (ps != null) {
/* 158:177 */                 ps.close();
/* 159:    */               }
/* 160:    */             }
/* 161:    */           }
/* 162:    */           catch (SQLException e)
/* 163:    */           {
/* 164:182 */             throw getFactory().getSQLExceptionHelper().convert(e, "error performing bulk update", this.updates[i]);
/* 165:    */           }
/* 166:    */         }
/* 167:    */       }
/* 168:190 */       return resultCount;
/* 169:    */     }
/* 170:    */     finally
/* 171:    */     {
/* 172:193 */       dropTemporaryTableIfNecessary(this.persister, session);
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   protected Queryable[] getAffectedQueryables()
/* 177:    */   {
/* 178:199 */     return new Queryable[] { this.persister };
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.exec.MultiTableUpdateExecutor
 * JD-Core Version:    0.7.0.1
 */