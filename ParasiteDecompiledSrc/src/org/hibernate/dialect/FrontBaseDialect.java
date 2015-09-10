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
/*  14:    */ public class FrontBaseDialect
/*  15:    */   extends Dialect
/*  16:    */ {
/*  17:    */   public FrontBaseDialect()
/*  18:    */   {
/*  19: 59 */     registerColumnType(-7, "bit");
/*  20: 60 */     registerColumnType(-5, "longint");
/*  21: 61 */     registerColumnType(5, "smallint");
/*  22: 62 */     registerColumnType(-6, "tinyint");
/*  23: 63 */     registerColumnType(4, "integer");
/*  24: 64 */     registerColumnType(1, "char(1)");
/*  25: 65 */     registerColumnType(12, "varchar($l)");
/*  26: 66 */     registerColumnType(6, "float");
/*  27: 67 */     registerColumnType(8, "double precision");
/*  28: 68 */     registerColumnType(91, "date");
/*  29: 69 */     registerColumnType(92, "time");
/*  30: 70 */     registerColumnType(93, "timestamp");
/*  31: 71 */     registerColumnType(-3, "bit varying($l)");
/*  32: 72 */     registerColumnType(2, "numeric($p,$s)");
/*  33: 73 */     registerColumnType(2004, "blob");
/*  34: 74 */     registerColumnType(2005, "clob");
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getAddColumnString()
/*  38:    */   {
/*  39: 78 */     return "add column";
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getCascadeConstraintsString()
/*  43:    */   {
/*  44: 82 */     return " cascade";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean dropConstraints()
/*  48:    */   {
/*  49: 86 */     return false;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getForUpdateString()
/*  53:    */   {
/*  54: 96 */     return "";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getCurrentTimestampCallString()
/*  58:    */   {
/*  59:101 */     return "{?= call current_timestamp}";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isCurrentTimestampSelectStringCallable()
/*  63:    */   {
/*  64:105 */     return true;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/*  68:    */   {
/*  69:110 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/*  70:111 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/*  71:    */     }
/*  72:113 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/*  73:114 */       return new PessimisticWriteUpdateLockingStrategy(lockable, lockMode);
/*  74:    */     }
/*  75:116 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/*  76:117 */       return new PessimisticReadUpdateLockingStrategy(lockable, lockMode);
/*  77:    */     }
/*  78:119 */     if (lockMode == LockMode.OPTIMISTIC) {
/*  79:120 */       return new OptimisticLockingStrategy(lockable, lockMode);
/*  80:    */     }
/*  81:122 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/*  82:123 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/*  83:    */     }
/*  84:125 */     if (lockMode.greaterThan(LockMode.READ)) {
/*  85:126 */       return new UpdateLockingStrategy(lockable, lockMode);
/*  86:    */     }
/*  87:129 */     return new SelectLockingStrategy(lockable, lockMode);
/*  88:    */   }
/*  89:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.FrontBaseDialect
 * JD-Core Version:    0.7.0.1
 */