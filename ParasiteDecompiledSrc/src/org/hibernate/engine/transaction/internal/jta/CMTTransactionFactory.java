/*  1:   */ package org.hibernate.engine.transaction.internal.jta;
/*  2:   */ 
/*  3:   */ import javax.transaction.SystemException;
/*  4:   */ import org.hibernate.ConnectionReleaseMode;
/*  5:   */ import org.hibernate.TransactionException;
/*  6:   */ import org.hibernate.engine.transaction.spi.TransactionContext;
/*  7:   */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  8:   */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  9:   */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/* 10:   */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/* 11:   */ 
/* 12:   */ public class CMTTransactionFactory
/* 13:   */   implements TransactionFactory<CMTTransaction>
/* 14:   */ {
/* 15:   */   public CMTTransaction createTransaction(TransactionCoordinator transactionCoordinator)
/* 16:   */   {
/* 17:42 */     return new CMTTransaction(transactionCoordinator);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean canBeDriver()
/* 21:   */   {
/* 22:47 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ConnectionReleaseMode getDefaultReleaseMode()
/* 26:   */   {
/* 27:52 */     return ConnectionReleaseMode.AFTER_STATEMENT;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean compatibleWithJtaSynchronization()
/* 31:   */   {
/* 32:57 */     return true;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean isJoinableJtaTransaction(TransactionCoordinator transactionCoordinator, CMTTransaction transaction)
/* 36:   */   {
/* 37:   */     try
/* 38:   */     {
/* 39:63 */       int status = transactionCoordinator.getTransactionContext().getTransactionEnvironment().getJtaPlatform().getCurrentStatus();
/* 40:   */       
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:68 */       return JtaStatusHelper.isActive(status);
/* 45:   */     }
/* 46:   */     catch (SystemException se)
/* 47:   */     {
/* 48:71 */       throw new TransactionException("Unable to check transaction status", se);
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jta.CMTTransactionFactory
 * JD-Core Version:    0.7.0.1
 */