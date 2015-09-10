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
/*  26:    */ public class SelectLockingStrategy
/*  27:    */   extends AbstractSelectLockingStrategy
/*  28:    */ {
/*  29:    */   public SelectLockingStrategy(Lockable lockable, LockMode lockMode)
/*  30:    */   {
/*  31: 61 */     super(lockable, lockMode);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/*  35:    */     throws StaleObjectStateException, JDBCException
/*  36:    */   {
/*  37: 73 */     String sql = determineSql(timeout);
/*  38: 74 */     SessionFactoryImplementor factory = session.getFactory();
/*  39:    */     try
/*  40:    */     {
/*  41: 76 */       PreparedStatement st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql);
/*  42:    */       try
/*  43:    */       {
/*  44: 78 */         getLockable().getIdentifierType().nullSafeSet(st, id, 1, session);
/*  45: 79 */         if (getLockable().isVersioned()) {
/*  46: 80 */           getLockable().getVersionType().nullSafeSet(st, version, getLockable().getIdentifierType().getColumnSpan(factory) + 1, session);
/*  47:    */         }
/*  48: 88 */         ResultSet rs = st.executeQuery();
/*  49:    */         try
/*  50:    */         {
/*  51: 90 */           if (!rs.next())
/*  52:    */           {
/*  53: 91 */             if (factory.getStatistics().isStatisticsEnabled()) {
/*  54: 92 */               factory.getStatisticsImplementor().optimisticFailure(getLockable().getEntityName());
/*  55:    */             }
/*  56: 95 */             throw new StaleObjectStateException(getLockable().getEntityName(), id);
/*  57:    */           }
/*  58:    */         }
/*  59:    */         finally {}
/*  60:    */       }
/*  61:    */       finally
/*  62:    */       {
/*  63:103 */         st.close();
/*  64:    */       }
/*  65:    */     }
/*  66:    */     catch (SQLException sqle)
/*  67:    */     {
/*  68:108 */       throw session.getFactory().getSQLExceptionHelper().convert(sqle, "could not lock: " + MessageHelper.infoString(getLockable(), id, session.getFactory()), sql);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected String generateLockString(int timeout)
/*  73:    */   {
/*  74:117 */     SessionFactoryImplementor factory = getLockable().getFactory();
/*  75:118 */     LockOptions lockOptions = new LockOptions(getLockMode());
/*  76:119 */     lockOptions.setTimeOut(timeout);
/*  77:120 */     SimpleSelect select = new SimpleSelect(factory.getDialect()).setLockOptions(lockOptions).setTableName(getLockable().getRootTableName()).addColumn(getLockable().getRootTableIdentifierColumnNames()[0]).addCondition(getLockable().getRootTableIdentifierColumnNames(), "=?");
/*  78:125 */     if (getLockable().isVersioned()) {
/*  79:126 */       select.addCondition(getLockable().getVersionColumnName(), "=?");
/*  80:    */     }
/*  81:128 */     if (factory.getSettings().isCommentsEnabled()) {
/*  82:129 */       select.setComment(getLockMode() + " lock " + getLockable().getEntityName());
/*  83:    */     }
/*  84:131 */     return select.toStatementString();
/*  85:    */   }
/*  86:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.SelectLockingStrategy
 * JD-Core Version:    0.7.0.1
 */