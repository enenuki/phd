/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.dialect.lock.LockingStrategy;
/*   5:    */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*   6:    */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*   7:    */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*   8:    */ import org.hibernate.dialect.lock.PessimisticReadUpdateLockingStrategy;
/*   9:    */ import org.hibernate.dialect.lock.PessimisticWriteUpdateLockingStrategy;
/*  10:    */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*  11:    */ import org.hibernate.dialect.lock.UpdateLockingStrategy;
/*  12:    */ import org.hibernate.persister.entity.Lockable;
/*  13:    */ 
/*  14:    */ public class PointbaseDialect
/*  15:    */   extends Dialect
/*  16:    */ {
/*  17:    */   public PointbaseDialect()
/*  18:    */   {
/*  19: 49 */     registerColumnType(-7, "smallint");
/*  20: 50 */     registerColumnType(-5, "bigint");
/*  21: 51 */     registerColumnType(5, "smallint");
/*  22: 52 */     registerColumnType(-6, "smallint");
/*  23: 53 */     registerColumnType(4, "integer");
/*  24: 54 */     registerColumnType(1, "char(1)");
/*  25: 55 */     registerColumnType(12, "varchar($l)");
/*  26: 56 */     registerColumnType(6, "float");
/*  27: 57 */     registerColumnType(8, "double precision");
/*  28: 58 */     registerColumnType(91, "date");
/*  29: 59 */     registerColumnType(92, "time");
/*  30: 60 */     registerColumnType(93, "timestamp");
/*  31:    */     
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35: 65 */     registerColumnType(-3, "blob($l)");
/*  36: 66 */     registerColumnType(2, "numeric($p,$s)");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String getAddColumnString()
/*  40:    */   {
/*  41: 70 */     return "add";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean dropConstraints()
/*  45:    */   {
/*  46: 74 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getCascadeConstraintsString()
/*  50:    */   {
/*  51: 78 */     return " cascade";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getForUpdateString()
/*  55:    */   {
/*  56: 82 */     return "";
/*  57:    */   }
/*  58:    */   
/*  59:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/*  60:    */   {
/*  61: 87 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/*  62: 88 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/*  63:    */     }
/*  64: 90 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/*  65: 91 */       return new PessimisticWriteUpdateLockingStrategy(lockable, lockMode);
/*  66:    */     }
/*  67: 93 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/*  68: 94 */       return new PessimisticReadUpdateLockingStrategy(lockable, lockMode);
/*  69:    */     }
/*  70: 96 */     if (lockMode == LockMode.OPTIMISTIC) {
/*  71: 97 */       return new OptimisticLockingStrategy(lockable, lockMode);
/*  72:    */     }
/*  73: 99 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/*  74:100 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/*  75:    */     }
/*  76:102 */     if (lockMode.greaterThan(LockMode.READ)) {
/*  77:103 */       return new UpdateLockingStrategy(lockable, lockMode);
/*  78:    */     }
/*  79:106 */     return new SelectLockingStrategy(lockable, lockMode);
/*  80:    */   }
/*  81:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.PointbaseDialect
 * JD-Core Version:    0.7.0.1
 */