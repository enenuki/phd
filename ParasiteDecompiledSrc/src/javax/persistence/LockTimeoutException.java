/*  1:   */ package javax.persistence;
/*  2:   */ 
/*  3:   */ public class LockTimeoutException
/*  4:   */   extends PersistenceException
/*  5:   */ {
/*  6:   */   Object entity;
/*  7:   */   
/*  8:   */   public LockTimeoutException() {}
/*  9:   */   
/* 10:   */   public LockTimeoutException(String message)
/* 11:   */   {
/* 12:47 */     super(message);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public LockTimeoutException(String message, Throwable cause)
/* 16:   */   {
/* 17:57 */     super(message, cause);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public LockTimeoutException(Throwable cause)
/* 21:   */   {
/* 22:66 */     super(cause);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public LockTimeoutException(Object entity)
/* 26:   */   {
/* 27:75 */     this.entity = entity;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public LockTimeoutException(String message, Throwable cause, Object entity)
/* 31:   */   {
/* 32:86 */     super(message, cause);
/* 33:87 */     this.entity = entity;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Object getObject()
/* 37:   */   {
/* 38:95 */     return this.entity;
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.LockTimeoutException
 * JD-Core Version:    0.7.0.1
 */