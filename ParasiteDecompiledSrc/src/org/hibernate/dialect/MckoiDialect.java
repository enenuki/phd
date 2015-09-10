/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   6:    */ import org.hibernate.dialect.lock.LockingStrategy;
/*   7:    */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*   8:    */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*   9:    */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*  10:    */ import org.hibernate.dialect.lock.PessimisticReadUpdateLockingStrategy;
/*  11:    */ import org.hibernate.dialect.lock.PessimisticWriteUpdateLockingStrategy;
/*  12:    */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*  13:    */ import org.hibernate.dialect.lock.UpdateLockingStrategy;
/*  14:    */ import org.hibernate.persister.entity.Lockable;
/*  15:    */ import org.hibernate.sql.CaseFragment;
/*  16:    */ import org.hibernate.sql.MckoiCaseFragment;
/*  17:    */ import org.hibernate.type.StandardBasicTypes;
/*  18:    */ 
/*  19:    */ public class MckoiDialect
/*  20:    */   extends Dialect
/*  21:    */ {
/*  22:    */   public MckoiDialect()
/*  23:    */   {
/*  24: 52 */     registerColumnType(-7, "bit");
/*  25: 53 */     registerColumnType(-5, "bigint");
/*  26: 54 */     registerColumnType(5, "smallint");
/*  27: 55 */     registerColumnType(-6, "tinyint");
/*  28: 56 */     registerColumnType(4, "integer");
/*  29: 57 */     registerColumnType(1, "char(1)");
/*  30: 58 */     registerColumnType(12, "varchar($l)");
/*  31: 59 */     registerColumnType(6, "float");
/*  32: 60 */     registerColumnType(8, "double");
/*  33: 61 */     registerColumnType(91, "date");
/*  34: 62 */     registerColumnType(92, "time");
/*  35: 63 */     registerColumnType(93, "timestamp");
/*  36: 64 */     registerColumnType(-3, "varbinary");
/*  37: 65 */     registerColumnType(2, "numeric");
/*  38: 66 */     registerColumnType(2004, "blob");
/*  39: 67 */     registerColumnType(2005, "clob");
/*  40:    */     
/*  41: 69 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  42: 70 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  43: 71 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  44: 72 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  45: 73 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  46: 74 */     registerFunction("round", new StandardSQLFunction("round", StandardBasicTypes.INTEGER));
/*  47: 75 */     registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
/*  48: 76 */     registerFunction("least", new StandardSQLFunction("least"));
/*  49: 77 */     registerFunction("greatest", new StandardSQLFunction("greatest"));
/*  50: 78 */     registerFunction("user", new StandardSQLFunction("user", StandardBasicTypes.STRING));
/*  51: 79 */     registerFunction("concat", new StandardSQLFunction("concat", StandardBasicTypes.STRING));
/*  52:    */     
/*  53: 81 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "0");
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String getAddColumnString()
/*  57:    */   {
/*  58: 85 */     return "add column";
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String getSequenceNextValString(String sequenceName)
/*  62:    */   {
/*  63: 89 */     return "select " + getSelectSequenceNextValString(sequenceName);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getSelectSequenceNextValString(String sequenceName)
/*  67:    */   {
/*  68: 93 */     return "nextval('" + sequenceName + "')";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String getCreateSequenceString(String sequenceName)
/*  72:    */   {
/*  73: 97 */     return "create sequence " + sequenceName;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getDropSequenceString(String sequenceName)
/*  77:    */   {
/*  78:101 */     return "drop sequence " + sequenceName;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getForUpdateString()
/*  82:    */   {
/*  83:105 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean supportsSequences()
/*  87:    */   {
/*  88:109 */     return true;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public CaseFragment createCaseFragment()
/*  92:    */   {
/*  93:113 */     return new MckoiCaseFragment();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/*  97:    */   {
/*  98:118 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/*  99:119 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 100:    */     }
/* 101:121 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/* 102:122 */       return new PessimisticWriteUpdateLockingStrategy(lockable, lockMode);
/* 103:    */     }
/* 104:124 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/* 105:125 */       return new PessimisticReadUpdateLockingStrategy(lockable, lockMode);
/* 106:    */     }
/* 107:127 */     if (lockMode == LockMode.OPTIMISTIC) {
/* 108:128 */       return new OptimisticLockingStrategy(lockable, lockMode);
/* 109:    */     }
/* 110:130 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/* 111:131 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 112:    */     }
/* 113:133 */     if (lockMode.greaterThan(LockMode.READ)) {
/* 114:134 */       return new UpdateLockingStrategy(lockable, lockMode);
/* 115:    */     }
/* 116:137 */     return new SelectLockingStrategy(lockable, lockMode);
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.MckoiDialect
 * JD-Core Version:    0.7.0.1
 */