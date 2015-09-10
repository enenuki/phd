/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import org.hibernate.LockMode;
/*   4:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   5:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   6:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   7:    */ import org.hibernate.dialect.lock.LockingStrategy;
/*   8:    */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*   9:    */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*  10:    */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*  11:    */ import org.hibernate.dialect.lock.PessimisticReadUpdateLockingStrategy;
/*  12:    */ import org.hibernate.dialect.lock.PessimisticWriteUpdateLockingStrategy;
/*  13:    */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*  14:    */ import org.hibernate.dialect.lock.UpdateLockingStrategy;
/*  15:    */ import org.hibernate.internal.CoreMessageLogger;
/*  16:    */ import org.hibernate.persister.entity.Lockable;
/*  17:    */ import org.hibernate.sql.CaseFragment;
/*  18:    */ import org.hibernate.sql.DecodeCaseFragment;
/*  19:    */ import org.hibernate.type.StandardBasicTypes;
/*  20:    */ import org.jboss.logging.Logger;
/*  21:    */ 
/*  22:    */ public class RDMSOS2200Dialect
/*  23:    */   extends Dialect
/*  24:    */ {
/*  25: 61 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, RDMSOS2200Dialect.class.getName());
/*  26:    */   
/*  27:    */   public RDMSOS2200Dialect()
/*  28:    */   {
/*  29: 66 */     LOG.rdmsOs2200Dialect();
/*  30:    */     
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40: 77 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  41: 78 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  42:    */     
/*  43: 80 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/*  44: 81 */     registerFunction("char_length", new StandardSQLFunction("char_length", StandardBasicTypes.INTEGER));
/*  45: 82 */     registerFunction("character_length", new StandardSQLFunction("character_length", StandardBasicTypes.INTEGER));
/*  46:    */     
/*  47:    */ 
/*  48: 85 */     registerFunction("concat", new SQLFunctionTemplate(StandardBasicTypes.STRING, "concat(?1, ?2)"));
/*  49: 86 */     registerFunction("instr", new StandardSQLFunction("instr", StandardBasicTypes.STRING));
/*  50: 87 */     registerFunction("lpad", new StandardSQLFunction("lpad", StandardBasicTypes.STRING));
/*  51: 88 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/*  52: 89 */     registerFunction("rpad", new StandardSQLFunction("rpad", StandardBasicTypes.STRING));
/*  53: 90 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/*  54:    */     
/*  55: 92 */     registerFunction("lcase", new StandardSQLFunction("lcase"));
/*  56: 93 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  57: 94 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/*  58: 95 */     registerFunction("reverse", new StandardSQLFunction("reverse"));
/*  59: 96 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  60:    */     
/*  61:    */ 
/*  62: 99 */     registerFunction("trim", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "ltrim(rtrim(?1))"));
/*  63:100 */     registerFunction("soundex", new StandardSQLFunction("soundex"));
/*  64:101 */     registerFunction("space", new StandardSQLFunction("space", StandardBasicTypes.STRING));
/*  65:102 */     registerFunction("ucase", new StandardSQLFunction("ucase"));
/*  66:103 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  67:    */     
/*  68:105 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  69:106 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  70:107 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  71:108 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  72:109 */     registerFunction("cosh", new StandardSQLFunction("cosh", StandardBasicTypes.DOUBLE));
/*  73:110 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/*  74:111 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  75:112 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  76:113 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  77:114 */     registerFunction("log10", new StandardSQLFunction("log10", StandardBasicTypes.DOUBLE));
/*  78:115 */     registerFunction("pi", new NoArgSQLFunction("pi", StandardBasicTypes.DOUBLE));
/*  79:116 */     registerFunction("rand", new NoArgSQLFunction("rand", StandardBasicTypes.DOUBLE));
/*  80:117 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  81:118 */     registerFunction("sinh", new StandardSQLFunction("sinh", StandardBasicTypes.DOUBLE));
/*  82:119 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  83:120 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  84:121 */     registerFunction("tanh", new StandardSQLFunction("tanh", StandardBasicTypes.DOUBLE));
/*  85:    */     
/*  86:123 */     registerFunction("round", new StandardSQLFunction("round"));
/*  87:124 */     registerFunction("trunc", new StandardSQLFunction("trunc"));
/*  88:125 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/*  89:126 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  90:    */     
/*  91:128 */     registerFunction("chr", new StandardSQLFunction("chr", StandardBasicTypes.CHARACTER));
/*  92:129 */     registerFunction("initcap", new StandardSQLFunction("initcap"));
/*  93:    */     
/*  94:131 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING, false));
/*  95:    */     
/*  96:133 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  97:134 */     registerFunction("current_time", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIME, false));
/*  98:135 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  99:136 */     registerFunction("curdate", new NoArgSQLFunction("curdate", StandardBasicTypes.DATE));
/* 100:137 */     registerFunction("curtime", new NoArgSQLFunction("curtime", StandardBasicTypes.TIME));
/* 101:138 */     registerFunction("days", new StandardSQLFunction("days", StandardBasicTypes.INTEGER));
/* 102:139 */     registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", StandardBasicTypes.INTEGER));
/* 103:140 */     registerFunction("dayname", new StandardSQLFunction("dayname", StandardBasicTypes.STRING));
/* 104:141 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/* 105:142 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/* 106:143 */     registerFunction("hour", new StandardSQLFunction("hour", StandardBasicTypes.INTEGER));
/* 107:144 */     registerFunction("last_day", new StandardSQLFunction("last_day", StandardBasicTypes.DATE));
/* 108:145 */     registerFunction("microsecond", new StandardSQLFunction("microsecond", StandardBasicTypes.INTEGER));
/* 109:146 */     registerFunction("minute", new StandardSQLFunction("minute", StandardBasicTypes.INTEGER));
/* 110:147 */     registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
/* 111:148 */     registerFunction("monthname", new StandardSQLFunction("monthname", StandardBasicTypes.STRING));
/* 112:149 */     registerFunction("now", new NoArgSQLFunction("now", StandardBasicTypes.TIMESTAMP));
/* 113:150 */     registerFunction("quarter", new StandardSQLFunction("quarter", StandardBasicTypes.INTEGER));
/* 114:151 */     registerFunction("second", new StandardSQLFunction("second", StandardBasicTypes.INTEGER));
/* 115:152 */     registerFunction("time", new StandardSQLFunction("time", StandardBasicTypes.TIME));
/* 116:153 */     registerFunction("timestamp", new StandardSQLFunction("timestamp", StandardBasicTypes.TIMESTAMP));
/* 117:154 */     registerFunction("week", new StandardSQLFunction("week", StandardBasicTypes.INTEGER));
/* 118:155 */     registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
/* 119:    */     
/* 120:157 */     registerFunction("atan2", new StandardSQLFunction("atan2", StandardBasicTypes.DOUBLE));
/* 121:158 */     registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
/* 122:159 */     registerFunction("nvl", new StandardSQLFunction("nvl"));
/* 123:160 */     registerFunction("power", new StandardSQLFunction("power", StandardBasicTypes.DOUBLE));
/* 124:    */     
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:187 */     registerColumnType(-7, "SMALLINT");
/* 151:188 */     registerColumnType(-6, "SMALLINT");
/* 152:189 */     registerColumnType(-5, "NUMERIC(21,0)");
/* 153:190 */     registerColumnType(5, "SMALLINT");
/* 154:191 */     registerColumnType(1, "CHARACTER(1)");
/* 155:192 */     registerColumnType(8, "DOUBLE PRECISION");
/* 156:193 */     registerColumnType(6, "FLOAT");
/* 157:194 */     registerColumnType(7, "REAL");
/* 158:195 */     registerColumnType(4, "INTEGER");
/* 159:196 */     registerColumnType(2, "NUMERIC(21,$l)");
/* 160:197 */     registerColumnType(3, "NUMERIC(21,$l)");
/* 161:198 */     registerColumnType(91, "DATE");
/* 162:199 */     registerColumnType(92, "TIME");
/* 163:200 */     registerColumnType(93, "TIMESTAMP");
/* 164:201 */     registerColumnType(12, "CHARACTER($l)");
/* 165:202 */     registerColumnType(2004, "BLOB($l)");
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean qualifyIndexName()
/* 169:    */   {
/* 170:220 */     return false;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean forUpdateOfColumns()
/* 174:    */   {
/* 175:230 */     return false;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getForUpdateString()
/* 179:    */   {
/* 180:240 */     return "";
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean supportsUniqueConstraintInCreateAlterTable()
/* 184:    */   {
/* 185:247 */     return true;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean supportsCascadeDelete()
/* 189:    */   {
/* 190:256 */     return false;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean supportsOuterJoinForUpdate()
/* 194:    */   {
/* 195:264 */     return false;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getAddColumnString()
/* 199:    */   {
/* 200:268 */     return "add";
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getNullColumnString()
/* 204:    */   {
/* 205:273 */     return " null";
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean supportsSequences()
/* 209:    */   {
/* 210:281 */     return true;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public String getSequenceNextValString(String sequenceName)
/* 214:    */   {
/* 215:286 */     return "select permuted_id('NEXT',31) from rdms.rdms_dummy where key_col = 1 ";
/* 216:    */   }
/* 217:    */   
/* 218:    */   public String getCreateSequenceString(String sequenceName)
/* 219:    */   {
/* 220:292 */     return "";
/* 221:    */   }
/* 222:    */   
/* 223:    */   public String getDropSequenceString(String sequenceName)
/* 224:    */   {
/* 225:298 */     return "";
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getCascadeConstraintsString()
/* 229:    */   {
/* 230:305 */     return " including contents";
/* 231:    */   }
/* 232:    */   
/* 233:    */   public CaseFragment createCaseFragment()
/* 234:    */   {
/* 235:309 */     return new DecodeCaseFragment();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean supportsLimit()
/* 239:    */   {
/* 240:313 */     return true;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean supportsLimitOffset()
/* 244:    */   {
/* 245:317 */     return false;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getLimitString(String sql, int offset, int limit)
/* 249:    */   {
/* 250:321 */     if (offset > 0) {
/* 251:322 */       throw new UnsupportedOperationException("query result offset is not supported");
/* 252:    */     }
/* 253:324 */     return sql.length() + 40 + sql + " fetch first " + limit + " rows only ";
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean supportsVariableLimit()
/* 257:    */   {
/* 258:333 */     return false;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public boolean supportsUnionAll()
/* 262:    */   {
/* 263:338 */     return true;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/* 267:    */   {
/* 268:343 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/* 269:344 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 270:    */     }
/* 271:346 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/* 272:347 */       return new PessimisticWriteUpdateLockingStrategy(lockable, lockMode);
/* 273:    */     }
/* 274:349 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/* 275:350 */       return new PessimisticReadUpdateLockingStrategy(lockable, lockMode);
/* 276:    */     }
/* 277:352 */     if (lockMode == LockMode.OPTIMISTIC) {
/* 278:353 */       return new OptimisticLockingStrategy(lockable, lockMode);
/* 279:    */     }
/* 280:355 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/* 281:356 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 282:    */     }
/* 283:358 */     if (lockMode.greaterThan(LockMode.READ)) {
/* 284:359 */       return new UpdateLockingStrategy(lockable, lockMode);
/* 285:    */     }
/* 286:362 */     return new SelectLockingStrategy(lockable, lockMode);
/* 287:    */   }
/* 288:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.RDMSOS2200Dialect
 * JD-Core Version:    0.7.0.1
 */