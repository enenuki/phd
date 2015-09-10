/*   1:    */ package org.hibernate.engine.transaction.internal.jta;
/*   2:    */ 
/*   3:    */ import javax.transaction.SystemException;
/*   4:    */ import javax.transaction.TransactionManager;
/*   5:    */ import org.hibernate.TransactionException;
/*   6:    */ import org.hibernate.engine.transaction.spi.AbstractTransactionImpl;
/*   7:    */ import org.hibernate.engine.transaction.spi.IsolationDelegate;
/*   8:    */ import org.hibernate.engine.transaction.spi.JoinStatus;
/*   9:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  10:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  11:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  12:    */ 
/*  13:    */ public class CMTTransaction
/*  14:    */   extends AbstractTransactionImpl
/*  15:    */ {
/*  16: 50 */   private JoinStatus joinStatus = JoinStatus.NOT_JOINED;
/*  17:    */   
/*  18:    */   protected CMTTransaction(TransactionCoordinator transactionCoordinator)
/*  19:    */   {
/*  20: 53 */     super(transactionCoordinator);
/*  21:    */   }
/*  22:    */   
/*  23:    */   protected TransactionManager transactionManager()
/*  24:    */   {
/*  25: 57 */     return jtaPlatform().retrieveTransactionManager();
/*  26:    */   }
/*  27:    */   
/*  28:    */   private TransactionManager getTransactionManager()
/*  29:    */   {
/*  30: 61 */     return transactionManager();
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected void doBegin()
/*  34:    */   {
/*  35: 66 */     transactionCoordinator().pulse();
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void afterTransactionBegin()
/*  39:    */   {
/*  40: 71 */     if (!transactionCoordinator().isSynchronizationRegistered()) {
/*  41: 72 */       throw new TransactionException("Could not register synchronization for container transaction");
/*  42:    */     }
/*  43: 74 */     transactionCoordinator().sendAfterTransactionBeginNotifications(this);
/*  44: 75 */     transactionCoordinator().getTransactionContext().afterTransactionBegin(this);
/*  45:    */   }
/*  46:    */   
/*  47:    */   protected void beforeTransactionCommit()
/*  48:    */   {
/*  49: 80 */     boolean flush = (!transactionCoordinator().getTransactionContext().isFlushModeNever()) && (!transactionCoordinator().getTransactionContext().isFlushBeforeCompletionEnabled());
/*  50: 82 */     if (flush) {
/*  51: 84 */       transactionCoordinator().getTransactionContext().managedFlush();
/*  52:    */     }
/*  53:    */   }
/*  54:    */   
/*  55:    */   protected void doCommit() {}
/*  56:    */   
/*  57:    */   protected void beforeTransactionRollBack() {}
/*  58:    */   
/*  59:    */   protected void doRollback()
/*  60:    */   {
/*  61:100 */     markRollbackOnly();
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void afterTransactionCompletion(int status) {}
/*  65:    */   
/*  66:    */   protected void afterAfterCompletion() {}
/*  67:    */   
/*  68:    */   public boolean isActive()
/*  69:    */     throws TransactionException
/*  70:    */   {
/*  71:115 */     return JtaStatusHelper.isActive(getTransactionManager());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public IsolationDelegate createIsolationDelegate()
/*  75:    */   {
/*  76:120 */     return new JtaIsolationDelegate(transactionCoordinator());
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean isInitiator()
/*  80:    */   {
/*  81:125 */     return false;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void markRollbackOnly()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:131 */       getTransactionManager().setRollbackOnly();
/*  89:    */     }
/*  90:    */     catch (SystemException se)
/*  91:    */     {
/*  92:134 */       throw new TransactionException("Could not set transaction to rollback only", se);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void markForJoin()
/*  97:    */   {
/*  98:140 */     this.joinStatus = JoinStatus.MARKED_FOR_JOINED;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void join()
/* 102:    */   {
/* 103:145 */     if (this.joinStatus != JoinStatus.MARKED_FOR_JOINED) {
/* 104:146 */       return;
/* 105:    */     }
/* 106:149 */     if (JtaStatusHelper.isActive(transactionManager()))
/* 107:    */     {
/* 108:151 */       transactionCoordinator().pulse();
/* 109:152 */       this.joinStatus = JoinStatus.JOINED;
/* 110:    */     }
/* 111:    */     else
/* 112:    */     {
/* 113:155 */       this.joinStatus = JoinStatus.NOT_JOINED;
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void resetJoinStatus()
/* 118:    */   {
/* 119:161 */     this.joinStatus = JoinStatus.NOT_JOINED;
/* 120:    */   }
/* 121:    */   
/* 122:    */   boolean isJoinable()
/* 123:    */   {
/* 124:165 */     return ((this.joinStatus == JoinStatus.JOINED) || (this.joinStatus == JoinStatus.MARKED_FOR_JOINED)) && (JtaStatusHelper.isActive(transactionManager()));
/* 125:    */   }
/* 126:    */   
/* 127:    */   public JoinStatus getJoinStatus()
/* 128:    */   {
/* 129:171 */     return this.joinStatus;
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jta.CMTTransaction
 * JD-Core Version:    0.7.0.1
 */