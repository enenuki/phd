/*   1:    */ package org.hibernate.hql.internal.ast.exec;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.cfg.Settings;
/*   9:    */ import org.hibernate.dialect.Dialect;
/*  10:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  11:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  12:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  13:    */ import org.hibernate.engine.spi.QueryParameters;
/*  14:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  15:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  16:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  17:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  18:    */ import org.hibernate.hql.internal.ast.tree.DeleteStatement;
/*  19:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  20:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  21:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  22:    */ import org.hibernate.internal.CoreMessageLogger;
/*  23:    */ import org.hibernate.internal.util.StringHelper;
/*  24:    */ import org.hibernate.param.ParameterSpecification;
/*  25:    */ import org.hibernate.persister.entity.Queryable;
/*  26:    */ import org.hibernate.sql.Delete;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public class MultiTableDeleteExecutor
/*  30:    */   extends AbstractStatementExecutor
/*  31:    */ {
/*  32: 52 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MultiTableDeleteExecutor.class.getName());
/*  33:    */   private final Queryable persister;
/*  34:    */   private final String idInsertSelect;
/*  35:    */   private final String[] deletes;
/*  36:    */   
/*  37:    */   public MultiTableDeleteExecutor(HqlSqlWalker walker)
/*  38:    */   {
/*  39: 60 */     super(walker, null);
/*  40: 62 */     if (!walker.getSessionFactoryHelper().getFactory().getDialect().supportsTemporaryTables()) {
/*  41: 63 */       throw new HibernateException("cannot doAfterTransactionCompletion multi-table deletes using dialect not supporting temp tables");
/*  42:    */     }
/*  43: 66 */     DeleteStatement deleteStatement = (DeleteStatement)walker.getAST();
/*  44: 67 */     FromElement fromElement = deleteStatement.getFromClause().getFromElement();
/*  45: 68 */     String bulkTargetAlias = fromElement.getTableAlias();
/*  46: 69 */     this.persister = fromElement.getQueryable();
/*  47:    */     
/*  48: 71 */     this.idInsertSelect = generateIdInsertSelect(this.persister, bulkTargetAlias, deleteStatement.getWhereClause());
/*  49: 72 */     LOG.tracev("Generated ID-INSERT-SELECT SQL (multi-table delete) : {0}", this.idInsertSelect);
/*  50:    */     
/*  51: 74 */     String[] tableNames = this.persister.getConstraintOrderedTableNameClosure();
/*  52: 75 */     String[][] columnNames = this.persister.getContraintOrderedTableKeyColumnClosure();
/*  53: 76 */     String idSubselect = generateIdSubselect(this.persister);
/*  54:    */     
/*  55: 78 */     this.deletes = new String[tableNames.length];
/*  56: 79 */     for (int i = tableNames.length - 1; i >= 0; i--)
/*  57:    */     {
/*  58: 84 */       Delete delete = new Delete().setTableName(tableNames[i]).setWhere("(" + StringHelper.join(", ", columnNames[i]) + ") IN (" + idSubselect + ")");
/*  59: 87 */       if (getFactory().getSettings().isCommentsEnabled()) {
/*  60: 88 */         delete.setComment("bulk delete");
/*  61:    */       }
/*  62: 91 */       this.deletes[i] = delete.toStatementString();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String[] getSqlStatements()
/*  67:    */   {
/*  68: 96 */     return this.deletes;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int execute(QueryParameters parameters, SessionImplementor session)
/*  72:    */     throws HibernateException
/*  73:    */   {
/*  74:100 */     coordinateSharedCacheCleanup(session);
/*  75:    */     
/*  76:102 */     createTemporaryTableIfNecessary(this.persister, session);
/*  77:    */     try
/*  78:    */     {
/*  79:106 */       PreparedStatement ps = null;
/*  80:107 */       int resultCount = 0;
/*  81:    */       try
/*  82:    */       {
/*  83:    */         try
/*  84:    */         {
/*  85:110 */           ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.idInsertSelect, false);
/*  86:111 */           Iterator paramSpecifications = getIdSelectParameterSpecifications().iterator();
/*  87:112 */           int pos = 1;
/*  88:113 */           while (paramSpecifications.hasNext())
/*  89:    */           {
/*  90:114 */             ParameterSpecification paramSpec = (ParameterSpecification)paramSpecifications.next();
/*  91:115 */             pos += paramSpec.bind(ps, parameters, session, pos);
/*  92:    */           }
/*  93:117 */           resultCount = ps.executeUpdate();
/*  94:    */         }
/*  95:    */         finally
/*  96:    */         {
/*  97:120 */           if (ps != null) {
/*  98:121 */             ps.close();
/*  99:    */           }
/* 100:    */         }
/* 101:    */       }
/* 102:    */       catch (SQLException e)
/* 103:    */       {
/* 104:126 */         throw getFactory().getSQLExceptionHelper().convert(e, "could not insert/select ids for bulk delete", this.idInsertSelect);
/* 105:    */       }
/* 106:134 */       for (int i = 0; i < this.deletes.length; i++) {
/* 107:    */         try
/* 108:    */         {
/* 109:    */           try
/* 110:    */           {
/* 111:137 */             ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.deletes[i], false);
/* 112:138 */             ps.executeUpdate();
/* 113:    */           }
/* 114:    */           finally
/* 115:    */           {
/* 116:141 */             if (ps != null) {
/* 117:142 */               ps.close();
/* 118:    */             }
/* 119:    */           }
/* 120:    */         }
/* 121:    */         catch (SQLException e)
/* 122:    */         {
/* 123:147 */           throw getFactory().getSQLExceptionHelper().convert(e, "error performing bulk delete", this.deletes[i]);
/* 124:    */         }
/* 125:    */       }
/* 126:155 */       return resultCount;
/* 127:    */     }
/* 128:    */     finally
/* 129:    */     {
/* 130:158 */       dropTemporaryTableIfNecessary(this.persister, session);
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected Queryable[] getAffectedQueryables()
/* 135:    */   {
/* 136:164 */     return new Queryable[] { this.persister };
/* 137:    */   }
/* 138:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.exec.MultiTableDeleteExecutor
 * JD-Core Version:    0.7.0.1
 */