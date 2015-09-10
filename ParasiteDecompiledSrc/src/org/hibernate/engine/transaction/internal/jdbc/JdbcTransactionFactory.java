/*  1:   */ package org.hibernate.engine.transaction.internal.jdbc;
/*  2:   */ 
/*  3:   */ import org.hibernate.ConnectionReleaseMode;
/*  4:   */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  5:   */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/*  6:   */ 
/*  7:   */ public final class JdbcTransactionFactory
/*  8:   */   implements TransactionFactory<JdbcTransaction>
/*  9:   */ {
/* 10:   */   public JdbcTransaction createTransaction(TransactionCoordinator transactionCoordinator)
/* 11:   */   {
/* 12:39 */     return new JdbcTransaction(transactionCoordinator);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public boolean canBeDriver()
/* 16:   */   {
/* 17:44 */     return true;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public ConnectionReleaseMode getDefaultReleaseMode()
/* 21:   */   {
/* 22:49 */     return ConnectionReleaseMode.ON_CLOSE;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean compatibleWithJtaSynchronization()
/* 26:   */   {
/* 27:54 */     return false;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean isJoinableJtaTransaction(TransactionCoordinator transactionCoordinator, JdbcTransaction transaction)
/* 31:   */   {
/* 32:59 */     return false;
/* 33:   */   }
/* 34:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.jdbc.JdbcTransactionFactory
 * JD-Core Version:    0.7.0.1
 */