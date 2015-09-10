/*   1:    */ package javax.persistence;
/*   2:    */ 
/*   3:    */ public class PessimisticLockException
/*   4:    */   extends PersistenceException
/*   5:    */ {
/*   6:    */   Object entity;
/*   7:    */   
/*   8:    */   public PessimisticLockException() {}
/*   9:    */   
/*  10:    */   public PessimisticLockException(String message)
/*  11:    */   {
/*  12: 49 */     super(message);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public PessimisticLockException(String message, Throwable cause)
/*  16:    */   {
/*  17: 60 */     super(message, cause);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public PessimisticLockException(Throwable cause)
/*  21:    */   {
/*  22: 70 */     super(cause);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public PessimisticLockException(Object entity)
/*  26:    */   {
/*  27: 80 */     this.entity = entity;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public PessimisticLockException(String message, Throwable cause, Object entity)
/*  31:    */   {
/*  32: 92 */     super(message, cause);
/*  33: 93 */     this.entity = entity;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Object getEntity()
/*  37:    */   {
/*  38:102 */     return this.entity;
/*  39:    */   }
/*  40:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.PessimisticLockException
 * JD-Core Version:    0.7.0.1
 */