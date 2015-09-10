/*   1:    */ package javax.persistence;
/*   2:    */ 
/*   3:    */ public class OptimisticLockException
/*   4:    */   extends PersistenceException
/*   5:    */ {
/*   6:    */   Object entity;
/*   7:    */   
/*   8:    */   public OptimisticLockException() {}
/*   9:    */   
/*  10:    */   public OptimisticLockException(String message)
/*  11:    */   {
/*  12: 57 */     super(message);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public OptimisticLockException(String message, Throwable cause)
/*  16:    */   {
/*  17: 70 */     super(message, cause);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public OptimisticLockException(Throwable cause)
/*  21:    */   {
/*  22: 81 */     super(cause);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public OptimisticLockException(Object entity)
/*  26:    */   {
/*  27: 92 */     this.entity = entity;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public OptimisticLockException(String message, Throwable cause, Object entity)
/*  31:    */   {
/*  32:107 */     super(message, cause);
/*  33:108 */     this.entity = entity;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object getEntity()
/*  37:    */   {
/*  38:117 */     return this.entity;
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.OptimisticLockException
 * JD-Core Version:    0.7.0.1
 */