/*  1:   */ package javax.persistence;
/*  2:   */ 
/*  3:   */ public class RollbackException
/*  4:   */   extends PersistenceException
/*  5:   */ {
/*  6:   */   public RollbackException() {}
/*  7:   */   
/*  8:   */   public RollbackException(String message)
/*  9:   */   {
/* 10:43 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public RollbackException(String message, Throwable cause)
/* 14:   */   {
/* 15:54 */     super(message, cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public RollbackException(Throwable cause)
/* 19:   */   {
/* 20:64 */     super(cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.RollbackException
 * JD-Core Version:    0.7.0.1
 */