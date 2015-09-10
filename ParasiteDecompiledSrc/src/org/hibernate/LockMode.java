/*   1:    */ package org.hibernate;
/*   2:    */ 
/*   3:    */ public enum LockMode
/*   4:    */ {
/*   5: 47 */   NONE(0),  READ(5),  UPGRADE(10),  UPGRADE_NOWAIT(10),  WRITE(10),  FORCE(15),  OPTIMISTIC(6),  OPTIMISTIC_FORCE_INCREMENT(7),  PESSIMISTIC_READ(12),  PESSIMISTIC_WRITE(13),  PESSIMISTIC_FORCE_INCREMENT(17);
/*   6:    */   
/*   7:    */   private final int level;
/*   8:    */   
/*   9:    */   private LockMode(int level)
/*  10:    */   {
/*  11:121 */     this.level = level;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public boolean greaterThan(LockMode mode)
/*  15:    */   {
/*  16:132 */     return this.level > mode.level;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean lessThan(LockMode mode)
/*  20:    */   {
/*  21:143 */     return this.level < mode.level;
/*  22:    */   }
/*  23:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.LockMode
 * JD-Core Version:    0.7.0.1
 */