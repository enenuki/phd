/*  1:   */ package javax.persistence;
/*  2:   */ 
/*  3:   */ public class PersistenceException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public PersistenceException() {}
/*  7:   */   
/*  8:   */   public PersistenceException(String message)
/*  9:   */   {
/* 10:45 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public PersistenceException(String message, Throwable cause)
/* 14:   */   {
/* 15:56 */     super(message, cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public PersistenceException(Throwable cause)
/* 19:   */   {
/* 20:66 */     super(cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.PersistenceException
 * JD-Core Version:    0.7.0.1
 */