/*  1:   */ package javax.transaction;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ 
/*  5:   */ public class InvalidTransactionException
/*  6:   */   extends RemoteException
/*  7:   */ {
/*  8:   */   public InvalidTransactionException() {}
/*  9:   */   
/* 10:   */   public InvalidTransactionException(String msg)
/* 11:   */   {
/* 12:55 */     super(msg);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.InvalidTransactionException
 * JD-Core Version:    0.7.0.1
 */