/*   1:    */ package org.hibernate.engine.transaction.synchronization.internal;
/*   2:    */ 
/*   3:    */ import javax.transaction.SystemException;
/*   4:    */ import org.hibernate.TransactionException;
/*   5:    */ import org.hibernate.engine.transaction.internal.jta.JtaStatusHelper;
/*   6:    */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*   7:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*   8:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*   9:    */ import org.hibernate.engine.transaction.synchronization.spi.AfterCompletionAction;
/*  10:    */ import org.hibernate.engine.transaction.synchronization.spi.ExceptionMapper;
/*  11:    */ import org.hibernate.engine.transaction.synchronization.spi.ManagedFlushChecker;
/*  12:    */ import org.hibernate.engine.transaction.synchronization.spi.SynchronizationCallbackCoordinator;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public class SynchronizationCallbackCoordinatorImpl
/*  18:    */   implements SynchronizationCallbackCoordinator
/*  19:    */ {
/*  20: 47 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SynchronizationCallbackCoordinatorImpl.class.getName());
/*  21:    */   private final TransactionCoordinator transactionCoordinator;
/*  22:    */   private ManagedFlushChecker managedFlushChecker;
/*  23:    */   private AfterCompletionAction afterCompletionAction;
/*  24:    */   private ExceptionMapper exceptionMapper;
/*  25:    */   
/*  26:    */   public SynchronizationCallbackCoordinatorImpl(TransactionCoordinator transactionCoordinator)
/*  27:    */   {
/*  28: 56 */     this.transactionCoordinator = transactionCoordinator;
/*  29: 57 */     reset();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void reset()
/*  33:    */   {
/*  34: 61 */     this.managedFlushChecker = STANDARD_MANAGED_FLUSH_CHECKER;
/*  35: 62 */     this.exceptionMapper = STANDARD_EXCEPTION_MAPPER;
/*  36: 63 */     this.afterCompletionAction = STANDARD_AFTER_COMPLETION_ACTION;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setManagedFlushChecker(ManagedFlushChecker managedFlushChecker)
/*  40:    */   {
/*  41: 68 */     this.managedFlushChecker = managedFlushChecker;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setExceptionMapper(ExceptionMapper exceptionMapper)
/*  45:    */   {
/*  46: 73 */     this.exceptionMapper = exceptionMapper;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setAfterCompletionAction(AfterCompletionAction afterCompletionAction)
/*  50:    */   {
/*  51: 78 */     this.afterCompletionAction = afterCompletionAction;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void beforeCompletion()
/*  55:    */   {
/*  56: 85 */     LOG.trace("Transaction before completion callback");
/*  57:    */     boolean flush;
/*  58:    */     try
/*  59:    */     {
/*  60: 89 */       int status = this.transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJtaPlatform().getCurrentStatus();
/*  61:    */       
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 94 */       flush = this.managedFlushChecker.shouldDoManagedFlush(this.transactionCoordinator, status);
/*  66:    */     }
/*  67:    */     catch (SystemException se)
/*  68:    */     {
/*  69: 97 */       setRollbackOnly();
/*  70: 98 */       throw this.exceptionMapper.mapStatusCheckFailure("could not determine transaction status in beforeCompletion()", se);
/*  71:    */     }
/*  72:    */     try
/*  73:    */     {
/*  74:102 */       if (flush)
/*  75:    */       {
/*  76:103 */         LOG.trace("Automatically flushing session");
/*  77:104 */         this.transactionCoordinator.getTransactionContext().managedFlush();
/*  78:    */       }
/*  79:    */     }
/*  80:    */     catch (RuntimeException re)
/*  81:    */     {
/*  82:108 */       setRollbackOnly();
/*  83:109 */       throw this.exceptionMapper.mapManagedFlushFailure("error during managed flush", re);
/*  84:    */     }
/*  85:    */     finally
/*  86:    */     {
/*  87:112 */       this.transactionCoordinator.sendBeforeTransactionCompletionNotifications(null);
/*  88:113 */       this.transactionCoordinator.getTransactionContext().beforeTransactionCompletion(null);
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void setRollbackOnly()
/*  93:    */   {
/*  94:118 */     this.transactionCoordinator.setRollbackOnly();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void afterCompletion(int status)
/*  98:    */   {
/*  99:122 */     LOG.tracev("Transaction after completion callback [status={0}]", Integer.valueOf(status));
/* 100:    */     try
/* 101:    */     {
/* 102:125 */       this.afterCompletionAction.doAction(this.transactionCoordinator, status);
/* 103:126 */       this.transactionCoordinator.afterTransaction(null, status);
/* 104:    */     }
/* 105:    */     finally
/* 106:    */     {
/* 107:129 */       reset();
/* 108:130 */       if ((transactionContext().shouldAutoClose()) && (!transactionContext().isClosed()))
/* 109:    */       {
/* 110:131 */         LOG.trace("Automatically closing session");
/* 111:132 */         transactionContext().managedClose();
/* 112:    */       }
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   private TransactionContext transactionContext()
/* 117:    */   {
/* 118:138 */     return this.transactionCoordinator.getTransactionContext();
/* 119:    */   }
/* 120:    */   
/* 121:141 */   private static final ManagedFlushChecker STANDARD_MANAGED_FLUSH_CHECKER = new ManagedFlushChecker()
/* 122:    */   {
/* 123:    */     public boolean shouldDoManagedFlush(TransactionCoordinator coordinator, int jtaStatus)
/* 124:    */     {
/* 125:144 */       return (!coordinator.getTransactionContext().isClosed()) && (!coordinator.getTransactionContext().isFlushModeNever()) && (coordinator.getTransactionContext().isFlushBeforeCompletionEnabled()) && (!JtaStatusHelper.isRollback(jtaStatus));
/* 126:    */     }
/* 127:    */   };
/* 128:151 */   private static final ExceptionMapper STANDARD_EXCEPTION_MAPPER = new ExceptionMapper()
/* 129:    */   {
/* 130:    */     public RuntimeException mapStatusCheckFailure(String message, SystemException systemException)
/* 131:    */     {
/* 132:153 */       SynchronizationCallbackCoordinatorImpl.LOG.error(SynchronizationCallbackCoordinatorImpl.LOG.unableToDetermineTransactionStatus(), systemException);
/* 133:154 */       return new TransactionException("could not determine transaction status in beforeCompletion()", systemException);
/* 134:    */     }
/* 135:    */     
/* 136:    */     public RuntimeException mapManagedFlushFailure(String message, RuntimeException failure)
/* 137:    */     {
/* 138:158 */       SynchronizationCallbackCoordinatorImpl.LOG.unableToPerformManagedFlush(failure.getMessage());
/* 139:159 */       return failure;
/* 140:    */     }
/* 141:    */   };
/* 142:163 */   private static final AfterCompletionAction STANDARD_AFTER_COMPLETION_ACTION = new AfterCompletionAction()
/* 143:    */   {
/* 144:    */     public void doAction(TransactionCoordinator transactionCoordinator, int status) {}
/* 145:    */   };
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.synchronization.internal.SynchronizationCallbackCoordinatorImpl
 * JD-Core Version:    0.7.0.1
 */