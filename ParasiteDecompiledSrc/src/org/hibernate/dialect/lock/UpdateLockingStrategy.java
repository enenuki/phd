/*   1:    */ package org.hibernate.dialect.lock;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.PreparedStatement;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.JDBCException;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.StaleObjectStateException;
/*  10:    */ import org.hibernate.cfg.Settings;
/*  11:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  12:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  13:    */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*  14:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  15:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  16:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  17:    */ import org.hibernate.internal.CoreMessageLogger;
/*  18:    */ import org.hibernate.persister.entity.Lockable;
/*  19:    */ import org.hibernate.pretty.MessageHelper;
/*  20:    */ import org.hibernate.sql.Update;
/*  21:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  22:    */ import org.hibernate.type.Type;
/*  23:    */ import org.hibernate.type.VersionType;
/*  24:    */ import org.jboss.logging.Logger;
/*  25:    */ 
/*  26:    */ public class UpdateLockingStrategy
/*  27:    */   implements LockingStrategy
/*  28:    */ {
/*  29: 53 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, UpdateLockingStrategy.class.getName());
/*  30:    */   private final Lockable lockable;
/*  31:    */   private final LockMode lockMode;
/*  32:    */   private final String sql;
/*  33:    */   
/*  34:    */   public UpdateLockingStrategy(Lockable lockable, LockMode lockMode)
/*  35:    */   {
/*  36: 70 */     this.lockable = lockable;
/*  37: 71 */     this.lockMode = lockMode;
/*  38: 72 */     if (lockMode.lessThan(LockMode.UPGRADE)) {
/*  39: 73 */       throw new HibernateException("[" + lockMode + "] not valid for update statement");
/*  40:    */     }
/*  41: 75 */     if (!lockable.isVersioned())
/*  42:    */     {
/*  43: 76 */       LOG.writeLocksNotSupported(lockable.getEntityName());
/*  44: 77 */       this.sql = null;
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 80 */       this.sql = generateLockString();
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/*  53:    */     throws StaleObjectStateException, JDBCException
/*  54:    */   {
/*  55: 91 */     if (!this.lockable.isVersioned()) {
/*  56: 92 */       throw new HibernateException("write locks via update not supported for non-versioned entities [" + this.lockable.getEntityName() + "]");
/*  57:    */     }
/*  58: 95 */     SessionFactoryImplementor factory = session.getFactory();
/*  59:    */     try
/*  60:    */     {
/*  61: 97 */       PreparedStatement st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.sql);
/*  62:    */       try
/*  63:    */       {
/*  64: 99 */         this.lockable.getVersionType().nullSafeSet(st, version, 1, session);
/*  65:100 */         int offset = 2;
/*  66:    */         
/*  67:102 */         this.lockable.getIdentifierType().nullSafeSet(st, id, offset, session);
/*  68:103 */         offset += this.lockable.getIdentifierType().getColumnSpan(factory);
/*  69:105 */         if (this.lockable.isVersioned()) {
/*  70:106 */           this.lockable.getVersionType().nullSafeSet(st, version, offset, session);
/*  71:    */         }
/*  72:109 */         int affected = st.executeUpdate();
/*  73:110 */         if (affected < 0)
/*  74:    */         {
/*  75:111 */           factory.getStatisticsImplementor().optimisticFailure(this.lockable.getEntityName());
/*  76:112 */           throw new StaleObjectStateException(this.lockable.getEntityName(), id);
/*  77:    */         }
/*  78:    */       }
/*  79:    */       finally
/*  80:    */       {
/*  81:117 */         st.close();
/*  82:    */       }
/*  83:    */     }
/*  84:    */     catch (SQLException sqle)
/*  85:    */     {
/*  86:122 */       throw session.getFactory().getSQLExceptionHelper().convert(sqle, "could not lock: " + MessageHelper.infoString(this.lockable, id, session.getFactory()), this.sql);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected String generateLockString()
/*  91:    */   {
/*  92:131 */     SessionFactoryImplementor factory = this.lockable.getFactory();
/*  93:132 */     Update update = new Update(factory.getDialect());
/*  94:133 */     update.setTableName(this.lockable.getRootTableName());
/*  95:134 */     update.addPrimaryKeyColumns(this.lockable.getRootTableIdentifierColumnNames());
/*  96:135 */     update.setVersionColumnName(this.lockable.getVersionColumnName());
/*  97:136 */     update.addColumn(this.lockable.getVersionColumnName());
/*  98:137 */     if (factory.getSettings().isCommentsEnabled()) {
/*  99:138 */       update.setComment(this.lockMode + " lock " + this.lockable.getEntityName());
/* 100:    */     }
/* 101:140 */     return update.toStatementString();
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected LockMode getLockMode()
/* 105:    */   {
/* 106:144 */     return this.lockMode;
/* 107:    */   }
/* 108:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.UpdateLockingStrategy
 * JD-Core Version:    0.7.0.1
 */