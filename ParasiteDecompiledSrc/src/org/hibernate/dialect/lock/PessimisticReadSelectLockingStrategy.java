/*   1:    */ package org.hibernate.dialect.lock;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import org.hibernate.JDBCException;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.LockOptions;
/*  10:    */ import org.hibernate.StaleObjectStateException;
/*  11:    */ import org.hibernate.cfg.Settings;
/*  12:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  13:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  14:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  18:    */ import org.hibernate.persister.entity.Lockable;
/*  19:    */ import org.hibernate.pretty.MessageHelper;
/*  20:    */ import org.hibernate.sql.SimpleSelect;
/*  21:    */ import org.hibernate.stat.Statistics;
/*  22:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  23:    */ import org.hibernate.type.Type;
/*  24:    */ import org.hibernate.type.VersionType;
/*  25:    */ 
/*  26:    */ public class PessimisticReadSelectLockingStrategy
/*  27:    */   extends AbstractSelectLockingStrategy
/*  28:    */ {
/*  29:    */   public PessimisticReadSelectLockingStrategy(Lockable lockable, LockMode lockMode)
/*  30:    */   {
/*  31: 68 */     super(lockable, lockMode);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/*  35:    */   {
/*  36: 73 */     String sql = determineSql(timeout);
/*  37: 74 */     SessionFactoryImplementor factory = session.getFactory();
/*  38:    */     try
/*  39:    */     {
/*  40:    */       try
/*  41:    */       {
/*  42: 77 */         PreparedStatement st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql);
/*  43:    */         try
/*  44:    */         {
/*  45: 79 */           getLockable().getIdentifierType().nullSafeSet(st, id, 1, session);
/*  46: 80 */           if (getLockable().isVersioned()) {
/*  47: 81 */             getLockable().getVersionType().nullSafeSet(st, version, getLockable().getIdentifierType().getColumnSpan(factory) + 1, session);
/*  48:    */           }
/*  49: 89 */           ResultSet rs = st.executeQuery();
/*  50:    */           try
/*  51:    */           {
/*  52: 91 */             if (!rs.next())
/*  53:    */             {
/*  54: 92 */               if (factory.getStatistics().isStatisticsEnabled()) {
/*  55: 93 */                 factory.getStatisticsImplementor().optimisticFailure(getLockable().getEntityName());
/*  56:    */               }
/*  57: 96 */               throw new StaleObjectStateException(getLockable().getEntityName(), id);
/*  58:    */             }
/*  59:    */           }
/*  60:    */           finally {}
/*  61:    */         }
/*  62:    */         finally
/*  63:    */         {
/*  64:104 */           st.close();
/*  65:    */         }
/*  66:    */       }
/*  67:    */       catch (SQLException e)
/*  68:    */       {
/*  69:109 */         throw session.getFactory().getSQLExceptionHelper().convert(e, "could not lock: " + MessageHelper.infoString(getLockable(), id, session.getFactory()), sql);
/*  70:    */       }
/*  71:    */     }
/*  72:    */     catch (JDBCException e)
/*  73:    */     {
/*  74:117 */       throw new PessimisticEntityLockException(object, "could not obtain pessimistic lock", e);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected String generateLockString(int lockTimeout)
/*  79:    */   {
/*  80:122 */     SessionFactoryImplementor factory = getLockable().getFactory();
/*  81:123 */     LockOptions lockOptions = new LockOptions(getLockMode());
/*  82:124 */     lockOptions.setTimeOut(lockTimeout);
/*  83:125 */     SimpleSelect select = new SimpleSelect(factory.getDialect()).setLockOptions(lockOptions).setTableName(getLockable().getRootTableName()).addColumn(getLockable().getRootTableIdentifierColumnNames()[0]).addCondition(getLockable().getRootTableIdentifierColumnNames(), "=?");
/*  84:130 */     if (getLockable().isVersioned()) {
/*  85:131 */       select.addCondition(getLockable().getVersionColumnName(), "=?");
/*  86:    */     }
/*  87:133 */     if (factory.getSettings().isCommentsEnabled()) {
/*  88:134 */       select.setComment(getLockMode() + " lock " + getLockable().getEntityName());
/*  89:    */     }
/*  90:136 */     return select.toStatementString();
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.PessimisticReadSelectLockingStrategy
 * JD-Core Version:    0.7.0.1
 */