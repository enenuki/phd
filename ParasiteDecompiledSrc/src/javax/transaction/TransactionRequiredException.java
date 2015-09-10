/*  1:   */ package javax.transaction;
/*  2:   */ 
/*  3:   */ import java.rmi.RemoteException;
/*  4:   */ 
/*  5:   */ public class TransactionRequiredException
/*  6:   */   extends RemoteException
/*  7:   */ {
/*  8:   */   public TransactionRequiredException() {}
/*  9:   */   
/* 10:   */   public TransactionRequiredException(String msg)
/* 11:   */   {
/* 12:51 */     super(msg);
/* 13:   */   }
/* 14:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.transaction.TransactionRequiredException
 * JD-Core Version:    0.7.0.1
 */