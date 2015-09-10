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
/*  26:    */ public class PessimisticWriteUpdateLockingStrategy
/*  27:    */   implements LockingStrategy
/*  28:    */ {
/*  29: 57 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PessimisticWriteUpdateLockingStrategy.class.getName());
/*  30:    */   private final Lockable lockable;
/*  31:    */   private final LockMode lockMode;
/*  32:    */   private final String sql;
/*  33:    */   
/*  34:    */   public PessimisticWriteUpdateLockingStrategy(Lockable lockable, LockMode lockMode)
/*  35:    */   {
/*  36: 73 */     this.lockable = lockable;
/*  37: 74 */     this.lockMode = lockMode;
/*  38: 75 */     if (lockMode.lessThan(LockMode.PESSIMISTIC_READ)) {
/*  39: 76 */       throw new HibernateException("[" + lockMode + "] not valid for update statement");
/*  40:    */     }
/*  41: 78 */     if (!lockable.isVersioned())
/*  42:    */     {
/*  43: 79 */       LOG.writeLocksNotSupported(lockable.getEntityName());
/*  44: 80 */       this.sql = null;
/*  45:    */     }
/*  46:    */     else
/*  47:    */     {
/*  48: 83 */       this.sql = generateLockString();
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/*  53:    */   {
/*  54: 89 */     if (!this.lockable.isVersioned()) {
/*  55: 90 */       throw new HibernateException("write locks via update not supported for non-versioned entities [" + this.lockable.getEntityName() + "]");
/*  56:    */     }
/*  57: 92 */     SessionFactoryImplementor factory = session.getFactory();
/*  58:    */     try
/*  59:    */     {
/*  60:    */       try
/*  61:    */       {
/*  62: 95 */         PreparedStatement st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(this.sql);
/*  63:    */         try
/*  64:    */         {
/*  65: 97 */           this.lockable.getVersionType().nullSafeSet(st, version, 1, session);
/*  66: 98 */           int offset = 2;
/*  67:    */           
/*  68:100 */           this.lockable.getIdentifierType().nullSafeSet(st, id, offset, session);
/*  69:101 */           offset += this.lockable.getIdentifierType().getColumnSpan(factory);
/*  70:103 */           if (this.lockable.isVersioned()) {
/*  71:104 */             this.lockable.getVersionType().nullSafeSet(st, version, offset, session);
/*  72:    */           }
/*  73:107 */           int affected = st.executeUpdate();
/*  74:108 */           if (affected < 0)
/*  75:    */           {
/*  76:109 */             factory.getStatisticsImplementor().optimisticFailure(this.lockable.getEntityName());
/*  77:110 */             throw new StaleObjectStateException(this.lockable.getEntityName(), id);
/*  78:    */           }
/*  79:    */         }
/*  80:    */         finally
/*  81:    */         {
/*  82:115 */           st.close();
/*  83:    */         }
/*  84:    */       }
/*  85:    */       catch (SQLException e)
/*  86:    */       {
/*  87:119 */         throw session.getFactory().getSQLExceptionHelper().convert(e, "could not lock: " + MessageHelper.infoString(this.lockable, id, session.getFactory()), this.sql);
/*  88:    */       }
/*  89:    */     }
/*  90:    */     catch (JDBCException e)
/*  91:    */     {
/*  92:127 */       throw new PessimisticEntityLockException(object, "could not obtain pessimistic lock", e);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected String generateLockString()
/*  97:    */   {
/*  98:132 */     SessionFactoryImplementor factory = this.lockable.getFactory();
/*  99:133 */     Update update = new Update(factory.getDialect());
/* 100:134 */     update.setTableName(this.lockable.getRootTableName());
/* 101:135 */     update.addPrimaryKeyColumns(this.lockable.getRootTableIdentifierColumnNames());
/* 102:136 */     update.setVersionColumnName(this.lockable.getVersionColumnName());
/* 103:137 */     update.addColumn(this.lockable.getVersionColumnName());
/* 104:138 */     if (factory.getSettings().isCommentsEnabled()) {
/* 105:139 */       update.setComment(this.lockMode + " lock " + this.lockable.getEntityName());
/* 106:    */     }
/* 107:141 */     return update.toStatementString();
/* 108:    */   }
/* 109:    */   
/* 110:    */   protected LockMode getLockMode()
/* 111:    */   {
/* 112:145 */     return this.lockMode;
/* 113:    */   }
/* 114:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.PessimisticWriteUpdateLockingStrategy
 * JD-Core Version:    0.7.0.1
 */