/*  1:   */ package javax.transaction;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ 
/*  5:   */ public class TransactionRolledbackException
/*  6:   */   extends RemoteException
/*  7:   */ {
/*  8:   */   public TransactionRolledbackException() {}
/*  9:   */   
/* 10:   */   public TransactionRolledbackException(String msg)
/* 11:   */   {
/* 12:54 */     super(msg);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.TransactionRolledbackException
 * JD-Core Version:    0.7.0.1
 */