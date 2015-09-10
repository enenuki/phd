/*   1:    */ package org.hibernate.engine.transaction.internal.jdbc;
/*   2:    */ 
/*   3:    */ import java.sql.Connection;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.TransactionException;
/*   7:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*   8:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*   9:    */ import org.hibernate.engine.transaction.spi.AbstractTransactionImpl;
/*  10:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*  11:    */ import org.hibernate.engine.transaction.spi.JoinStatus;
/*  12:    */ import org.hibernate.engine.transaction.spi.LocalStatus;
/*  13:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  14:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class JdbcTransaction
/*  19:    */   extends AbstractTransactionImpl
/*  20:    */ {
/*  21: 51 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, JdbcTransaction.class.getName());
/*  22:    */   private Connection managedConnection;
/*  23:    */   private boolean wasInitiallyAutoCommit;
/*  24:    */   private boolean isDriver;
/*  25:    */   
/*  26:    */   protected JdbcTransaction(TransactionCoordinator transactionCoordinator)
/*  27:    */   {
/*  28: 58 */     super(transactionCoordinator);
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void doBegin()
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 64 */       if (this.managedConnection != null) {
/*  36: 65 */         throw new TransactionException("Already have an associated managed connection");
/*  37:    */       }
/*  38: 67 */       this.managedConnection = transactionCoordinator().getJdbcCoordinator().getLogicalConnection().getConnection();
/*  39: 68 */       this.wasInitiallyAutoCommit = this.managedConnection.getAutoCommit();
/*  40: 69 */       LOG.debugv("initial autocommit status: {0}", Boolean.valueOf(this.wasInitiallyAutoCommit));
/*  41: 70 */       if (this.wasInitiallyAutoCommit)
/*  42:    */       {
/*  43: 71 */         LOG.debug("disabling autocommit");
/*  44: 72 */         this.managedConnection.setAutoCommit(false);
/*  45:    */       }
/*  46:    */     }
/*  47:    */     catch (SQLException e)
/*  48:    */     {
/*  49: 76 */       throw new TransactionException("JDBC begin transaction failed: ", e);
/*  50:    */     }
/*  51: 79 */     this.isDriver = transactionCoordinator().takeOwnership();
/*  52:    */   }
/*  53:    */   
/*  54:    */   protected void afterTransactionBegin()
/*  55:    */   {
/*  56: 84 */     if (getTimeout() > 0) {
/*  57: 85 */       transactionCoordinator().getJdbcCoordinator().setTransactionTimeOut(getTimeout());
/*  58:    */     }
/*  59: 87 */     transactionCoordinator().sendAfterTransactionBeginNotifications(this);
/*  60: 88 */     if (this.isDriver) {
/*  61: 89 */       transactionCoordinator().getTransactionContext().afterTransactionBegin(this);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void beforeTransactionCommit()
/*  66:    */   {
/*  67: 95 */     transactionCoordinator().sendBeforeTransactionCompletionNotifications(this);
/*  68: 99 */     if ((this.isDriver) && (!transactionCoordinator().getTransactionContext().isFlushModeNever())) {
/*  69:101 */       transactionCoordinator().getTransactionContext().managedFlush();
/*  70:    */     }
/*  71:104 */     if (this.isDriver) {
/*  72:105 */       transactionCoordinator().getTransactionContext().beforeTransactionCompletion(this);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected void doCommit()
/*  77:    */     throws TransactionException
/*  78:    */   {
/*  79:    */     try
/*  80:    */     {
/*  81:112 */       this.managedConnection.commit();
/*  82:113 */       LOG.debug("committed JDBC Connection");
/*  83:    */     }
/*  84:    */     catch (SQLException e)
/*  85:    */     {
/*  86:116 */       throw new TransactionException("unable to commit against JDBC connection", e);
/*  87:    */     }
/*  88:    */     finally
/*  89:    */     {
/*  90:119 */       releaseManagedConnection();
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private void releaseManagedConnection()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:125 */       if (this.wasInitiallyAutoCommit)
/*  99:    */       {
/* 100:126 */         LOG.debug("re-enabling autocommit");
/* 101:127 */         this.managedConnection.setAutoCommit(true);
/* 102:    */       }
/* 103:129 */       this.managedConnection = null;
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:132 */       LOG.debug("Could not toggle autocommit", e);
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void afterTransactionCompletion(int status)
/* 112:    */   {
/* 113:138 */     transactionCoordinator().afterTransaction(this, status);
/* 114:    */   }
/* 115:    */   
/* 116:    */   protected void afterAfterCompletion()
/* 117:    */   {
/* 118:143 */     if ((this.isDriver) && (transactionCoordinator().getTransactionContext().shouldAutoClose()) && (!transactionCoordinator().getTransactionContext().isClosed())) {
/* 119:    */       try
/* 120:    */       {
/* 121:147 */         transactionCoordinator().getTransactionContext().managedClose();
/* 122:    */       }
/* 123:    */       catch (HibernateException e)
/* 124:    */       {
/* 125:150 */         LOG.unableToCloseSessionButSwallowingError(e);
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   protected void beforeTransactionRollBack() {}
/* 131:    */   
/* 132:    */   protected void doRollback()
/* 133:    */     throws TransactionException
/* 134:    */   {
/* 135:    */     try
/* 136:    */     {
/* 137:163 */       this.managedConnection.rollback();
/* 138:164 */       LOG.debug("rolled JDBC Connection");
/* 139:    */     }
/* 140:    */     catch (SQLException e)
/* 141:    */     {
/* 142:167 */       throw new TransactionException("unable to rollback against JDBC connection", e);
/* 143:    */     }
/* 144:    */     finally
/* 145:    */     {
/* 146:170 */       releaseManagedConnection();
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isInitiator()
/* 151:    */   {
/* 152:176 */     return isActive();
/* 153:    */   }
/* 154:    */   
/* 155:    */   public IsolationDelegate createIsolationDelegate()
/* 156:    */   {
/* 157:181 */     return new JdbcIsolationDelegate(transactionCoordinator());
/* 158:    */   }
/* 159:    */   
/* 160:    */   public JoinStatus getJoinStatus()
/* 161:    */   {
/* 162:186 */     return isActive() ? JoinStatus.JOINED : JoinStatus.NOT_JOINED;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void markRollbackOnly() {}
/* 166:    */   
/* 167:    */   public void join() {}
/* 168:    */   
/* 169:    */   public void resetJoinStatus() {}
/* 170:    */   
/* 171:    */   public boolean isActive()
/* 172:    */     throws HibernateException
/* 173:    */   {
/* 174:206 */     return getLocalStatus() == LocalStatus.ACTIVE;
/* 175:    */   }
/* 176:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jdbc.JdbcTransaction
 * JD-Core Version:    0.7.0.1
 */