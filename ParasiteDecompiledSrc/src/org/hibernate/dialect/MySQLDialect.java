/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   8:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.type.StandardBasicTypes;
/*  11:    */ 
/*  12:    */ public class MySQLDialect
/*  13:    */   extends Dialect
/*  14:    */ {
/*  15:    */   public MySQLDialect()
/*  16:    */   {
/*  17: 45 */     registerColumnType(-7, "bit");
/*  18: 46 */     registerColumnType(-5, "bigint");
/*  19: 47 */     registerColumnType(5, "smallint");
/*  20: 48 */     registerColumnType(-6, "tinyint");
/*  21: 49 */     registerColumnType(4, "integer");
/*  22: 50 */     registerColumnType(1, "char(1)");
/*  23: 51 */     registerColumnType(6, "float");
/*  24: 52 */     registerColumnType(8, "double precision");
/*  25: 53 */     registerColumnType(91, "date");
/*  26: 54 */     registerColumnType(92, "time");
/*  27: 55 */     registerColumnType(93, "datetime");
/*  28: 56 */     registerColumnType(-3, "longblob");
/*  29: 57 */     registerColumnType(-3, 16777215L, "mediumblob");
/*  30: 58 */     registerColumnType(-3, 65535L, "blob");
/*  31: 59 */     registerColumnType(-3, 255L, "tinyblob");
/*  32: 60 */     registerColumnType(-2, "binary($l)");
/*  33: 61 */     registerColumnType(-4, "longblob");
/*  34: 62 */     registerColumnType(-4, 16777215L, "mediumblob");
/*  35: 63 */     registerColumnType(2, "decimal($p,$s)");
/*  36: 64 */     registerColumnType(2004, "longblob");
/*  37:    */     
/*  38:    */ 
/*  39: 67 */     registerColumnType(2005, "longtext");
/*  40:    */     
/*  41:    */ 
/*  42: 70 */     registerVarcharTypes();
/*  43:    */     
/*  44: 72 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/*  45: 73 */     registerFunction("bin", new StandardSQLFunction("bin", StandardBasicTypes.STRING));
/*  46: 74 */     registerFunction("char_length", new StandardSQLFunction("char_length", StandardBasicTypes.LONG));
/*  47: 75 */     registerFunction("character_length", new StandardSQLFunction("character_length", StandardBasicTypes.LONG));
/*  48: 76 */     registerFunction("lcase", new StandardSQLFunction("lcase"));
/*  49: 77 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  50: 78 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/*  51: 79 */     registerFunction("ord", new StandardSQLFunction("ord", StandardBasicTypes.INTEGER));
/*  52: 80 */     registerFunction("quote", new StandardSQLFunction("quote"));
/*  53: 81 */     registerFunction("reverse", new StandardSQLFunction("reverse"));
/*  54: 82 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  55: 83 */     registerFunction("soundex", new StandardSQLFunction("soundex"));
/*  56: 84 */     registerFunction("space", new StandardSQLFunction("space", StandardBasicTypes.STRING));
/*  57: 85 */     registerFunction("ucase", new StandardSQLFunction("ucase"));
/*  58: 86 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  59: 87 */     registerFunction("unhex", new StandardSQLFunction("unhex", StandardBasicTypes.STRING));
/*  60:    */     
/*  61: 89 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  62: 90 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  63:    */     
/*  64: 92 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  65: 93 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  66: 94 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  67: 95 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  68: 96 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/*  69: 97 */     registerFunction("crc32", new StandardSQLFunction("crc32", StandardBasicTypes.LONG));
/*  70: 98 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  71: 99 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  72:100 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  73:101 */     registerFunction("log2", new StandardSQLFunction("log2", StandardBasicTypes.DOUBLE));
/*  74:102 */     registerFunction("log10", new StandardSQLFunction("log10", StandardBasicTypes.DOUBLE));
/*  75:103 */     registerFunction("pi", new NoArgSQLFunction("pi", StandardBasicTypes.DOUBLE));
/*  76:104 */     registerFunction("rand", new NoArgSQLFunction("rand", StandardBasicTypes.DOUBLE));
/*  77:105 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  78:106 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  79:107 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  80:    */     
/*  81:109 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/*  82:110 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/*  83:    */     
/*  84:112 */     registerFunction("ceiling", new StandardSQLFunction("ceiling", StandardBasicTypes.INTEGER));
/*  85:113 */     registerFunction("ceil", new StandardSQLFunction("ceil", StandardBasicTypes.INTEGER));
/*  86:114 */     registerFunction("floor", new StandardSQLFunction("floor", StandardBasicTypes.INTEGER));
/*  87:115 */     registerFunction("round", new StandardSQLFunction("round"));
/*  88:    */     
/*  89:117 */     registerFunction("datediff", new StandardSQLFunction("datediff", StandardBasicTypes.INTEGER));
/*  90:118 */     registerFunction("timediff", new StandardSQLFunction("timediff", StandardBasicTypes.TIME));
/*  91:119 */     registerFunction("date_format", new StandardSQLFunction("date_format", StandardBasicTypes.STRING));
/*  92:    */     
/*  93:121 */     registerFunction("curdate", new NoArgSQLFunction("curdate", StandardBasicTypes.DATE));
/*  94:122 */     registerFunction("curtime", new NoArgSQLFunction("curtime", StandardBasicTypes.TIME));
/*  95:123 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  96:124 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME, false));
/*  97:125 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  98:126 */     registerFunction("date", new StandardSQLFunction("date", StandardBasicTypes.DATE));
/*  99:127 */     registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
/* 100:128 */     registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", StandardBasicTypes.INTEGER));
/* 101:129 */     registerFunction("dayname", new StandardSQLFunction("dayname", StandardBasicTypes.STRING));
/* 102:130 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/* 103:131 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/* 104:132 */     registerFunction("from_days", new StandardSQLFunction("from_days", StandardBasicTypes.DATE));
/* 105:133 */     registerFunction("from_unixtime", new StandardSQLFunction("from_unixtime", StandardBasicTypes.TIMESTAMP));
/* 106:134 */     registerFunction("hour", new StandardSQLFunction("hour", StandardBasicTypes.INTEGER));
/* 107:135 */     registerFunction("last_day", new StandardSQLFunction("last_day", StandardBasicTypes.DATE));
/* 108:136 */     registerFunction("localtime", new NoArgSQLFunction("localtime", StandardBasicTypes.TIMESTAMP));
/* 109:137 */     registerFunction("localtimestamp", new NoArgSQLFunction("localtimestamp", StandardBasicTypes.TIMESTAMP));
/* 110:138 */     registerFunction("microseconds", new StandardSQLFunction("microseconds", StandardBasicTypes.INTEGER));
/* 111:139 */     registerFunction("minute", new StandardSQLFunction("minute", StandardBasicTypes.INTEGER));
/* 112:140 */     registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
/* 113:141 */     registerFunction("monthname", new StandardSQLFunction("monthname", StandardBasicTypes.STRING));
/* 114:142 */     registerFunction("now", new NoArgSQLFunction("now", StandardBasicTypes.TIMESTAMP));
/* 115:143 */     registerFunction("quarter", new StandardSQLFunction("quarter", StandardBasicTypes.INTEGER));
/* 116:144 */     registerFunction("second", new StandardSQLFunction("second", StandardBasicTypes.INTEGER));
/* 117:145 */     registerFunction("sec_to_time", new StandardSQLFunction("sec_to_time", StandardBasicTypes.TIME));
/* 118:146 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.TIMESTAMP));
/* 119:147 */     registerFunction("time", new StandardSQLFunction("time", StandardBasicTypes.TIME));
/* 120:148 */     registerFunction("timestamp", new StandardSQLFunction("timestamp", StandardBasicTypes.TIMESTAMP));
/* 121:149 */     registerFunction("time_to_sec", new StandardSQLFunction("time_to_sec", StandardBasicTypes.INTEGER));
/* 122:150 */     registerFunction("to_days", new StandardSQLFunction("to_days", StandardBasicTypes.LONG));
/* 123:151 */     registerFunction("unix_timestamp", new StandardSQLFunction("unix_timestamp", StandardBasicTypes.LONG));
/* 124:152 */     registerFunction("utc_date", new NoArgSQLFunction("utc_date", StandardBasicTypes.STRING));
/* 125:153 */     registerFunction("utc_time", new NoArgSQLFunction("utc_time", StandardBasicTypes.STRING));
/* 126:154 */     registerFunction("utc_timestamp", new NoArgSQLFunction("utc_timestamp", StandardBasicTypes.STRING));
/* 127:155 */     registerFunction("week", new StandardSQLFunction("week", StandardBasicTypes.INTEGER));
/* 128:156 */     registerFunction("weekday", new StandardSQLFunction("weekday", StandardBasicTypes.INTEGER));
/* 129:157 */     registerFunction("weekofyear", new StandardSQLFunction("weekofyear", StandardBasicTypes.INTEGER));
/* 130:158 */     registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
/* 131:159 */     registerFunction("yearweek", new StandardSQLFunction("yearweek", StandardBasicTypes.INTEGER));
/* 132:    */     
/* 133:161 */     registerFunction("hex", new StandardSQLFunction("hex", StandardBasicTypes.STRING));
/* 134:162 */     registerFunction("oct", new StandardSQLFunction("oct", StandardBasicTypes.STRING));
/* 135:    */     
/* 136:164 */     registerFunction("octet_length", new StandardSQLFunction("octet_length", StandardBasicTypes.LONG));
/* 137:165 */     registerFunction("bit_length", new StandardSQLFunction("bit_length", StandardBasicTypes.LONG));
/* 138:    */     
/* 139:167 */     registerFunction("bit_count", new StandardSQLFunction("bit_count", StandardBasicTypes.LONG));
/* 140:168 */     registerFunction("encrypt", new StandardSQLFunction("encrypt", StandardBasicTypes.STRING));
/* 141:169 */     registerFunction("md5", new StandardSQLFunction("md5", StandardBasicTypes.STRING));
/* 142:170 */     registerFunction("sha1", new StandardSQLFunction("sha1", StandardBasicTypes.STRING));
/* 143:171 */     registerFunction("sha", new StandardSQLFunction("sha", StandardBasicTypes.STRING));
/* 144:    */     
/* 145:173 */     registerFunction("concat", new StandardSQLFunction("concat", StandardBasicTypes.STRING));
/* 146:    */     
/* 147:175 */     getDefaultProperties().setProperty("hibernate.max_fetch_depth", "2");
/* 148:176 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 149:    */   }
/* 150:    */   
/* 151:    */   protected void registerVarcharTypes()
/* 152:    */   {
/* 153:180 */     registerColumnType(12, "longtext");
/* 154:    */     
/* 155:    */ 
/* 156:183 */     registerColumnType(12, 255L, "varchar($l)");
/* 157:184 */     registerColumnType(-1, "longtext");
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getAddColumnString()
/* 161:    */   {
/* 162:188 */     return "add column";
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean qualifyIndexName()
/* 166:    */   {
/* 167:192 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean supportsIdentityColumns()
/* 171:    */   {
/* 172:196 */     return true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getIdentitySelectString()
/* 176:    */   {
/* 177:200 */     return "select last_insert_id()";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getIdentityColumnString()
/* 181:    */   {
/* 182:204 */     return "not null auto_increment";
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey)
/* 186:    */   {
/* 187:213 */     String cols = StringHelper.join(", ", foreignKey);
/* 188:214 */     return 30 + " add index " + constraintName + " (" + cols + "), add constraint " + constraintName + " foreign key (" + cols + ") references " + referencedTable + " (" + StringHelper.join(", ", primaryKey) + ')';
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean supportsLimit()
/* 192:    */   {
/* 193:232 */     return true;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String getDropForeignKeyString()
/* 197:    */   {
/* 198:236 */     return " drop foreign key ";
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getLimitString(String sql, boolean hasOffset)
/* 202:    */   {
/* 203:240 */     return sql.length() + 20 + sql + (hasOffset ? " limit ?, ?" : " limit ?");
/* 204:    */   }
/* 205:    */   
/* 206:    */   public char closeQuote()
/* 207:    */   {
/* 208:247 */     return '`';
/* 209:    */   }
/* 210:    */   
/* 211:    */   public char openQuote()
/* 212:    */   {
/* 213:251 */     return '`';
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean supportsIfExistsBeforeTableName()
/* 217:    */   {
/* 218:255 */     return true;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getSelectGUIDString()
/* 222:    */   {
/* 223:259 */     return "select uuid()";
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean supportsCascadeDelete()
/* 227:    */   {
/* 228:263 */     return false;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public String getTableComment(String comment)
/* 232:    */   {
/* 233:267 */     return " comment='" + comment + "'";
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String getColumnComment(String comment)
/* 237:    */   {
/* 238:271 */     return " comment '" + comment + "'";
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean supportsTemporaryTables()
/* 242:    */   {
/* 243:275 */     return true;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public String getCreateTemporaryTableString()
/* 247:    */   {
/* 248:279 */     return "create temporary table if not exists";
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getDropTemporaryTableString()
/* 252:    */   {
/* 253:283 */     return "drop temporary table";
/* 254:    */   }
/* 255:    */   
/* 256:    */   public Boolean performTemporaryTableDDLInIsolation()
/* 257:    */   {
/* 258:289 */     return Boolean.FALSE;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public String getCastTypeName(int code)
/* 262:    */   {
/* 263:293 */     if (code == 4) {
/* 264:294 */       return "signed";
/* 265:    */     }
/* 266:296 */     if (code == 12) {
/* 267:297 */       return "char";
/* 268:    */     }
/* 269:299 */     if (code == -3) {
/* 270:300 */       return "binary";
/* 271:    */     }
/* 272:303 */     return super.getCastTypeName(code);
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean supportsCurrentTimestampSelection()
/* 276:    */   {
/* 277:308 */     return true;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 281:    */   {
/* 282:312 */     return false;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getCurrentTimestampSelectString()
/* 286:    */   {
/* 287:316 */     return "select now()";
/* 288:    */   }
/* 289:    */   
/* 290:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 291:    */     throws SQLException
/* 292:    */   {
/* 293:320 */     return col;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public ResultSet getResultSet(CallableStatement ps)
/* 297:    */     throws SQLException
/* 298:    */   {
/* 299:324 */     boolean isResultSet = ps.execute();
/* 300:325 */     while ((!isResultSet) && (ps.getUpdateCount() != -1)) {
/* 301:326 */       isResultSet = ps.getMoreResults();
/* 302:    */     }
/* 303:328 */     return ps.getResultSet();
/* 304:    */   }
/* 305:    */   
/* 306:    */   public boolean supportsRowValueConstructorSyntax()
/* 307:    */   {
/* 308:332 */     return true;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public String getForUpdateString()
/* 312:    */   {
/* 313:339 */     return " for update";
/* 314:    */   }
/* 315:    */   
/* 316:    */   public String getWriteLockString(int timeout)
/* 317:    */   {
/* 318:343 */     return " for update";
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String getReadLockString(int timeout)
/* 322:    */   {
/* 323:347 */     return " lock in share mode";
/* 324:    */   }
/* 325:    */   
/* 326:    */   public boolean supportsEmptyInList()
/* 327:    */   {
/* 328:354 */     return false;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public boolean areStringComparisonsCaseInsensitive()
/* 332:    */   {
/* 333:358 */     return true;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean supportsLobValueChangePropogation()
/* 337:    */   {
/* 338:363 */     return false;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public boolean supportsSubqueryOnMutatingTable()
/* 342:    */   {
/* 343:367 */     return false;
/* 344:    */   }
/* 345:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.MySQLDialect
 * JD-Core Version:    0.7.0.1
 */