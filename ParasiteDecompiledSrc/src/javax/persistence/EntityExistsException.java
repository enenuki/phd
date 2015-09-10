/*  1:   */ package javax.persistence;
/*  2:   */ 
/*  3:   */ public class EntityExistsException
/*  4:   */   extends PersistenceException
/*  5:   */ {
/*  6:   */   public EntityExistsException() {}
/*  7:   */   
/*  8:   */   public EntityExistsException(String message)
/*  9:   */   {
/* 10:51 */     super(message);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public EntityExistsException(String message, Throwable cause)
/* 14:   */   {
/* 15:64 */     super(message, cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public EntityExistsException(Throwable cause)
/* 19:   */   {
/* 20:75 */     super(cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.EntityExistsException
 * JD-Core Version:    0.7.0.1
 */