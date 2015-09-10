/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.LockMode;
/*   5:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   6:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   7:    */ import org.hibernate.dialect.lock.LockingStrategy;
/*   8:    */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*   9:    */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*  10:    */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*  11:    */ import org.hibernate.dialect.lock.PessimisticReadUpdateLockingStrategy;
/*  12:    */ import org.hibernate.dialect.lock.PessimisticWriteUpdateLockingStrategy;
/*  13:    */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*  14:    */ import org.hibernate.dialect.lock.UpdateLockingStrategy;
/*  15:    */ import org.hibernate.persister.entity.Lockable;
/*  16:    */ import org.hibernate.sql.JoinFragment;
/*  17:    */ import org.hibernate.sql.OracleJoinFragment;
/*  18:    */ import org.hibernate.type.StandardBasicTypes;
/*  19:    */ 
/*  20:    */ public class TimesTenDialect
/*  21:    */   extends Dialect
/*  22:    */ {
/*  23:    */   public TimesTenDialect()
/*  24:    */   {
/*  25: 64 */     registerColumnType(-7, "TINYINT");
/*  26: 65 */     registerColumnType(-5, "BIGINT");
/*  27: 66 */     registerColumnType(5, "SMALLINT");
/*  28: 67 */     registerColumnType(-6, "TINYINT");
/*  29: 68 */     registerColumnType(4, "INTEGER");
/*  30: 69 */     registerColumnType(1, "CHAR(1)");
/*  31: 70 */     registerColumnType(12, "VARCHAR($l)");
/*  32: 71 */     registerColumnType(6, "FLOAT");
/*  33: 72 */     registerColumnType(8, "DOUBLE");
/*  34: 73 */     registerColumnType(91, "DATE");
/*  35: 74 */     registerColumnType(92, "TIME");
/*  36: 75 */     registerColumnType(93, "TIMESTAMP");
/*  37: 76 */     registerColumnType(-3, "VARBINARY($l)");
/*  38: 77 */     registerColumnType(2, "DECIMAL($p, $s)");
/*  39:    */     
/*  40:    */ 
/*  41: 80 */     registerColumnType(2004, "VARBINARY(4000000)");
/*  42: 81 */     registerColumnType(2005, "VARCHAR(4000000)");
/*  43:    */     
/*  44: 83 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "true");
/*  45: 84 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/*  46: 85 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  47: 86 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  48: 87 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  49: 88 */     registerFunction("concat", new StandardSQLFunction("concat", StandardBasicTypes.STRING));
/*  50: 89 */     registerFunction("mod", new StandardSQLFunction("mod"));
/*  51: 90 */     registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/*  52: 91 */     registerFunction("to_date", new StandardSQLFunction("to_date", StandardBasicTypes.TIMESTAMP));
/*  53: 92 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.TIMESTAMP, false));
/*  54: 93 */     registerFunction("getdate", new NoArgSQLFunction("getdate", StandardBasicTypes.TIMESTAMP, false));
/*  55: 94 */     registerFunction("nvl", new StandardSQLFunction("nvl"));
/*  56:    */   }
/*  57:    */   
/*  58:    */   public boolean dropConstraints()
/*  59:    */   {
/*  60: 99 */     return true;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean qualifyIndexName()
/*  64:    */   {
/*  65:103 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean supportsUnique()
/*  69:    */   {
/*  70:107 */     return false;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean supportsUniqueConstraintInCreateAlterTable()
/*  74:    */   {
/*  75:111 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getAddColumnString()
/*  79:    */   {
/*  80:115 */     return "add";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean supportsSequences()
/*  84:    */   {
/*  85:119 */     return true;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getSelectSequenceNextValString(String sequenceName)
/*  89:    */   {
/*  90:123 */     return sequenceName + ".nextval";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getSequenceNextValString(String sequenceName)
/*  94:    */   {
/*  95:127 */     return "select first 1 " + sequenceName + ".nextval from sys.tables";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getCreateSequenceString(String sequenceName)
/*  99:    */   {
/* 100:131 */     return "create sequence " + sequenceName;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getDropSequenceString(String sequenceName)
/* 104:    */   {
/* 105:135 */     return "drop sequence " + sequenceName;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getQuerySequencesString()
/* 109:    */   {
/* 110:139 */     return "select NAME from sys.sequences";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public JoinFragment createOuterJoinFragment()
/* 114:    */   {
/* 115:143 */     return new OracleJoinFragment();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCrossJoinSeparator()
/* 119:    */   {
/* 120:147 */     return ", ";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getForUpdateString()
/* 124:    */   {
/* 125:156 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean supportsColumnCheck()
/* 129:    */   {
/* 130:160 */     return false;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean supportsTableCheck()
/* 134:    */   {
/* 135:164 */     return false;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean supportsLimitOffset()
/* 139:    */   {
/* 140:168 */     return false;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean supportsVariableLimit()
/* 144:    */   {
/* 145:172 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public boolean supportsLimit()
/* 149:    */   {
/* 150:176 */     return true;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean useMaxForLimit()
/* 154:    */   {
/* 155:180 */     return true;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getLimitString(String querySelect, int offset, int limit)
/* 159:    */   {
/* 160:184 */     if (offset > 0) {
/* 161:185 */       throw new UnsupportedOperationException("query result offset is not supported");
/* 162:    */     }
/* 163:187 */     return new StringBuffer(querySelect.length() + 8).append(querySelect).insert(6, " first " + limit).toString();
/* 164:    */   }
/* 165:    */   
/* 166:    */   public boolean supportsCurrentTimestampSelection()
/* 167:    */   {
/* 168:194 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getCurrentTimestampSelectString()
/* 172:    */   {
/* 173:198 */     return "select first 1 sysdate from sys.tables";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 177:    */   {
/* 178:202 */     return false;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public boolean supportsTemporaryTables()
/* 182:    */   {
/* 183:206 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String generateTemporaryTableName(String baseTableName)
/* 187:    */   {
/* 188:210 */     String name = super.generateTemporaryTableName(baseTableName);
/* 189:211 */     return name.length() > 30 ? name.substring(1, 30) : name;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getCreateTemporaryTableString()
/* 193:    */   {
/* 194:215 */     return "create global temporary table";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getCreateTemporaryTablePostfix()
/* 198:    */   {
/* 199:219 */     return "on commit delete rows";
/* 200:    */   }
/* 201:    */   
/* 202:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/* 203:    */   {
/* 204:224 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/* 205:225 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 206:    */     }
/* 207:227 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/* 208:228 */       return new PessimisticWriteUpdateLockingStrategy(lockable, lockMode);
/* 209:    */     }
/* 210:230 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/* 211:231 */       return new PessimisticReadUpdateLockingStrategy(lockable, lockMode);
/* 212:    */     }
/* 213:233 */     if (lockMode == LockMode.OPTIMISTIC) {
/* 214:234 */       return new OptimisticLockingStrategy(lockable, lockMode);
/* 215:    */     }
/* 216:236 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/* 217:237 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 218:    */     }
/* 219:239 */     if (lockMode.greaterThan(LockMode.READ)) {
/* 220:240 */       return new UpdateLockingStrategy(lockable, lockMode);
/* 221:    */     }
/* 222:243 */     return new SelectLockingStrategy(lockable, lockMode);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean supportsEmptyInList()
/* 226:    */   {
/* 227:250 */     return false;
/* 228:    */   }
/* 229:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.TimesTenDialect
 * JD-Core Version:    0.7.0.1
 */