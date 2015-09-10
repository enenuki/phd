/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.sql.CallableStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Properties;
/*   8:    */ import org.hibernate.dialect.function.AnsiTrimEmulationFunction;
/*   9:    */ import org.hibernate.dialect.function.AvgWithArgumentCastFunction;
/*  10:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  11:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  12:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  13:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  14:    */ import org.hibernate.type.StandardBasicTypes;
/*  15:    */ 
/*  16:    */ public class DB2Dialect
/*  17:    */   extends Dialect
/*  18:    */ {
/*  19:    */   public DB2Dialect()
/*  20:    */   {
/*  21: 49 */     registerColumnType(-7, "smallint");
/*  22: 50 */     registerColumnType(-5, "bigint");
/*  23: 51 */     registerColumnType(5, "smallint");
/*  24: 52 */     registerColumnType(-6, "smallint");
/*  25: 53 */     registerColumnType(4, "integer");
/*  26: 54 */     registerColumnType(1, "char(1)");
/*  27: 55 */     registerColumnType(12, "varchar($l)");
/*  28: 56 */     registerColumnType(6, "float");
/*  29: 57 */     registerColumnType(8, "double");
/*  30: 58 */     registerColumnType(91, "date");
/*  31: 59 */     registerColumnType(92, "time");
/*  32: 60 */     registerColumnType(93, "timestamp");
/*  33: 61 */     registerColumnType(-3, "varchar($l) for bit data");
/*  34: 62 */     registerColumnType(2, "numeric($p,$s)");
/*  35: 63 */     registerColumnType(2004, "blob($l)");
/*  36: 64 */     registerColumnType(2005, "clob($l)");
/*  37: 65 */     registerColumnType(-1, "long varchar");
/*  38: 66 */     registerColumnType(-4, "long varchar for bit data");
/*  39: 67 */     registerColumnType(-2, "varchar($l) for bit data");
/*  40: 68 */     registerColumnType(-2, 254L, "char($l) for bit data");
/*  41: 69 */     registerColumnType(16, "smallint");
/*  42:    */     
/*  43: 71 */     registerFunction("avg", new AvgWithArgumentCastFunction("double"));
/*  44:    */     
/*  45: 73 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  46: 74 */     registerFunction("absval", new StandardSQLFunction("absval"));
/*  47: 75 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  48:    */     
/*  49: 77 */     registerFunction("ceiling", new StandardSQLFunction("ceiling"));
/*  50: 78 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/*  51: 79 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  52: 80 */     registerFunction("round", new StandardSQLFunction("round"));
/*  53:    */     
/*  54: 82 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  55: 83 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  56: 84 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  57: 85 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  58: 86 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/*  59: 87 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/*  60: 88 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  61: 89 */     registerFunction("float", new StandardSQLFunction("float", StandardBasicTypes.DOUBLE));
/*  62: 90 */     registerFunction("hex", new StandardSQLFunction("hex", StandardBasicTypes.STRING));
/*  63: 91 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  64: 92 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  65: 93 */     registerFunction("log10", new StandardSQLFunction("log10", StandardBasicTypes.DOUBLE));
/*  66: 94 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/*  67: 95 */     registerFunction("rand", new NoArgSQLFunction("rand", StandardBasicTypes.DOUBLE));
/*  68: 96 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  69: 97 */     registerFunction("soundex", new StandardSQLFunction("soundex", StandardBasicTypes.STRING));
/*  70: 98 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  71: 99 */     registerFunction("stddev", new StandardSQLFunction("stddev", StandardBasicTypes.DOUBLE));
/*  72:100 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  73:101 */     registerFunction("variance", new StandardSQLFunction("variance", StandardBasicTypes.DOUBLE));
/*  74:    */     
/*  75:103 */     registerFunction("julian_day", new StandardSQLFunction("julian_day", StandardBasicTypes.INTEGER));
/*  76:104 */     registerFunction("microsecond", new StandardSQLFunction("microsecond", StandardBasicTypes.INTEGER));
/*  77:105 */     registerFunction("midnight_seconds", new StandardSQLFunction("midnight_seconds", StandardBasicTypes.INTEGER));
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:109 */     registerFunction("minute", new StandardSQLFunction("minute", StandardBasicTypes.INTEGER));
/*  82:110 */     registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
/*  83:111 */     registerFunction("monthname", new StandardSQLFunction("monthname", StandardBasicTypes.STRING));
/*  84:112 */     registerFunction("quarter", new StandardSQLFunction("quarter", StandardBasicTypes.INTEGER));
/*  85:113 */     registerFunction("hour", new StandardSQLFunction("hour", StandardBasicTypes.INTEGER));
/*  86:114 */     registerFunction("second", new StandardSQLFunction("second", StandardBasicTypes.INTEGER));
/*  87:115 */     registerFunction("current_date", new NoArgSQLFunction("current date", StandardBasicTypes.DATE, false));
/*  88:116 */     registerFunction("date", new StandardSQLFunction("date", StandardBasicTypes.DATE));
/*  89:117 */     registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
/*  90:118 */     registerFunction("dayname", new StandardSQLFunction("dayname", StandardBasicTypes.STRING));
/*  91:119 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/*  92:120 */     registerFunction("dayofweek_iso", new StandardSQLFunction("dayofweek_iso", StandardBasicTypes.INTEGER));
/*  93:121 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/*  94:122 */     registerFunction("days", new StandardSQLFunction("days", StandardBasicTypes.LONG));
/*  95:123 */     registerFunction("current_time", new NoArgSQLFunction("current time", StandardBasicTypes.TIME, false));
/*  96:124 */     registerFunction("time", new StandardSQLFunction("time", StandardBasicTypes.TIME));
/*  97:125 */     registerFunction("current_timestamp", new NoArgSQLFunction("current timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  98:    */     
/*  99:    */ 
/* 100:    */ 
/* 101:129 */     registerFunction("timestamp", new StandardSQLFunction("timestamp", StandardBasicTypes.TIMESTAMP));
/* 102:130 */     registerFunction("timestamp_iso", new StandardSQLFunction("timestamp_iso", StandardBasicTypes.TIMESTAMP));
/* 103:131 */     registerFunction("week", new StandardSQLFunction("week", StandardBasicTypes.INTEGER));
/* 104:132 */     registerFunction("week_iso", new StandardSQLFunction("week_iso", StandardBasicTypes.INTEGER));
/* 105:133 */     registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
/* 106:    */     
/* 107:135 */     registerFunction("double", new StandardSQLFunction("double", StandardBasicTypes.DOUBLE));
/* 108:136 */     registerFunction("varchar", new StandardSQLFunction("varchar", StandardBasicTypes.STRING));
/* 109:137 */     registerFunction("real", new StandardSQLFunction("real", StandardBasicTypes.FLOAT));
/* 110:138 */     registerFunction("bigint", new StandardSQLFunction("bigint", StandardBasicTypes.LONG));
/* 111:139 */     registerFunction("char", new StandardSQLFunction("char", StandardBasicTypes.CHARACTER));
/* 112:140 */     registerFunction("integer", new StandardSQLFunction("integer", StandardBasicTypes.INTEGER));
/* 113:141 */     registerFunction("smallint", new StandardSQLFunction("smallint", StandardBasicTypes.SHORT));
/* 114:    */     
/* 115:143 */     registerFunction("digits", new StandardSQLFunction("digits", StandardBasicTypes.STRING));
/* 116:144 */     registerFunction("chr", new StandardSQLFunction("chr", StandardBasicTypes.CHARACTER));
/* 117:145 */     registerFunction("upper", new StandardSQLFunction("upper"));
/* 118:146 */     registerFunction("lower", new StandardSQLFunction("lower"));
/* 119:147 */     registerFunction("ucase", new StandardSQLFunction("ucase"));
/* 120:148 */     registerFunction("lcase", new StandardSQLFunction("lcase"));
/* 121:149 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/* 122:150 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/* 123:151 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 124:152 */     registerFunction("posstr", new StandardSQLFunction("posstr", StandardBasicTypes.INTEGER));
/* 125:    */     
/* 126:154 */     registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 127:155 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "length(?1)*8"));
/* 128:156 */     registerFunction("trim", new AnsiTrimEmulationFunction());
/* 129:    */     
/* 130:158 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
/* 131:    */     
/* 132:160 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(char(?1))"));
/* 133:    */     
/* 134:162 */     registerKeyword("current");
/* 135:163 */     registerKeyword("date");
/* 136:164 */     registerKeyword("time");
/* 137:165 */     registerKeyword("timestamp");
/* 138:166 */     registerKeyword("fetch");
/* 139:167 */     registerKeyword("first");
/* 140:168 */     registerKeyword("rows");
/* 141:169 */     registerKeyword("only");
/* 142:    */     
/* 143:171 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "0");
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getLowercaseFunction()
/* 147:    */   {
/* 148:175 */     return "lcase";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getAddColumnString()
/* 152:    */   {
/* 153:179 */     return "add column";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean dropConstraints()
/* 157:    */   {
/* 158:183 */     return false;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean supportsIdentityColumns()
/* 162:    */   {
/* 163:187 */     return true;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getIdentitySelectString()
/* 167:    */   {
/* 168:191 */     return "values identity_val_local()";
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getIdentityColumnString()
/* 172:    */   {
/* 173:195 */     return "generated by default as identity";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getIdentityInsertString()
/* 177:    */   {
/* 178:199 */     return "default";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getSequenceNextValString(String sequenceName)
/* 182:    */   {
/* 183:203 */     return "values nextval for " + sequenceName;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getCreateSequenceString(String sequenceName)
/* 187:    */   {
/* 188:207 */     return "create sequence " + sequenceName;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String getDropSequenceString(String sequenceName)
/* 192:    */   {
/* 193:211 */     return "drop sequence " + sequenceName + " restrict";
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean supportsSequences()
/* 197:    */   {
/* 198:215 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean supportsPooledSequences()
/* 202:    */   {
/* 203:219 */     return true;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getQuerySequencesString()
/* 207:    */   {
/* 208:223 */     return "select seqname from sysibm.syssequences";
/* 209:    */   }
/* 210:    */   
/* 211:    */   public boolean supportsLimit()
/* 212:    */   {
/* 213:227 */     return true;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean supportsVariableLimit()
/* 217:    */   {
/* 218:231 */     return false;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getLimitString(String sql, int offset, int limit)
/* 222:    */   {
/* 223:235 */     if (offset == 0) {
/* 224:236 */       return sql + " fetch first " + limit + " rows only";
/* 225:    */     }
/* 226:238 */     StringBuilder pagingSelect = new StringBuilder(sql.length() + 200).append("select * from ( select inner2_.*, rownumber() over(order by order of inner2_) as rownumber_ from ( ").append(sql).append(" fetch first ").append(limit).append(" rows only ) as inner2_ ) as inner1_ where rownumber_ > ").append(offset).append(" order by rownumber_");
/* 227:    */     
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:248 */     return pagingSelect.toString();
/* 237:    */   }
/* 238:    */   
/* 239:    */   public int convertToFirstRowValue(int zeroBasedFirstResult)
/* 240:    */   {
/* 241:260 */     return zeroBasedFirstResult;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public String getForUpdateString()
/* 245:    */   {
/* 246:264 */     return " for read only with rs";
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean useMaxForLimit()
/* 250:    */   {
/* 251:268 */     return true;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public boolean supportsOuterJoinForUpdate()
/* 255:    */   {
/* 256:272 */     return false;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean supportsNotNullUnique()
/* 260:    */   {
/* 261:276 */     return false;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean supportsExistsInSelect()
/* 265:    */   {
/* 266:280 */     return false;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public String getSelectClauseNullString(int sqlType)
/* 270:    */   {
/* 271:    */     String literal;
/* 272:285 */     switch (sqlType)
/* 273:    */     {
/* 274:    */     case 1: 
/* 275:    */     case 12: 
/* 276:288 */       literal = "'x'";
/* 277:289 */       break;
/* 278:    */     case 91: 
/* 279:291 */       literal = "'2000-1-1'";
/* 280:292 */       break;
/* 281:    */     case 93: 
/* 282:294 */       literal = "'2000-1-1 00:00:00'";
/* 283:295 */       break;
/* 284:    */     case 92: 
/* 285:297 */       literal = "'00:00:00'";
/* 286:298 */       break;
/* 287:    */     default: 
/* 288:300 */       literal = "0";
/* 289:    */     }
/* 290:302 */     return "nullif(" + literal + ',' + literal + ')';
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static void main(String[] args)
/* 294:    */   {
/* 295:306 */     System.out.println(new DB2Dialect().getLimitString("/*foo*/ select * from foos", true));
/* 296:307 */     System.out.println(new DB2Dialect().getLimitString("/*foo*/ select distinct * from foos", true));
/* 297:308 */     System.out.println(new DB2Dialect().getLimitString("/*foo*/ select * from foos foo order by foo.bar, foo.baz", true));
/* 298:    */     
/* 299:    */ 
/* 300:    */ 
/* 301:    */ 
/* 302:    */ 
/* 303:    */ 
/* 304:315 */     System.out.println(new DB2Dialect().getLimitString("/*foo*/ select distinct * from foos foo order by foo.bar, foo.baz", true));
/* 305:    */   }
/* 306:    */   
/* 307:    */   public boolean supportsUnionAll()
/* 308:    */   {
/* 309:325 */     return true;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 313:    */     throws SQLException
/* 314:    */   {
/* 315:329 */     return col;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public ResultSet getResultSet(CallableStatement ps)
/* 319:    */     throws SQLException
/* 320:    */   {
/* 321:333 */     boolean isResultSet = ps.execute();
/* 322:335 */     while ((!isResultSet) && (ps.getUpdateCount() != -1)) {
/* 323:336 */       isResultSet = ps.getMoreResults();
/* 324:    */     }
/* 325:338 */     ResultSet rs = ps.getResultSet();
/* 326:    */     
/* 327:    */ 
/* 328:341 */     return rs;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public boolean supportsCommentOn()
/* 332:    */   {
/* 333:345 */     return true;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean supportsTemporaryTables()
/* 337:    */   {
/* 338:349 */     return true;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public String getCreateTemporaryTableString()
/* 342:    */   {
/* 343:353 */     return "declare global temporary table";
/* 344:    */   }
/* 345:    */   
/* 346:    */   public String getCreateTemporaryTablePostfix()
/* 347:    */   {
/* 348:357 */     return "not logged";
/* 349:    */   }
/* 350:    */   
/* 351:    */   public String generateTemporaryTableName(String baseTableName)
/* 352:    */   {
/* 353:361 */     return "session." + super.generateTemporaryTableName(baseTableName);
/* 354:    */   }
/* 355:    */   
/* 356:    */   public boolean supportsCurrentTimestampSelection()
/* 357:    */   {
/* 358:365 */     return true;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public String getCurrentTimestampSelectString()
/* 362:    */   {
/* 363:369 */     return "values current timestamp";
/* 364:    */   }
/* 365:    */   
/* 366:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 367:    */   {
/* 368:373 */     return false;
/* 369:    */   }
/* 370:    */   
/* 371:    */   public boolean supportsParametersInInsertSelect()
/* 372:    */   {
/* 373:385 */     return true;
/* 374:    */   }
/* 375:    */   
/* 376:    */   public boolean requiresCastingOfParametersInSelectClause()
/* 377:    */   {
/* 378:395 */     return true;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public boolean supportsResultSetPositionQueryMethodsOnForwardOnlyCursor()
/* 382:    */   {
/* 383:399 */     return false;
/* 384:    */   }
/* 385:    */   
/* 386:    */   public String getCrossJoinSeparator()
/* 387:    */   {
/* 388:404 */     return ", ";
/* 389:    */   }
/* 390:    */   
/* 391:    */   public boolean supportsEmptyInList()
/* 392:    */   {
/* 393:409 */     return false;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public boolean supportsLobValueChangePropogation()
/* 397:    */   {
/* 398:413 */     return false;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 402:    */   {
/* 403:417 */     return true;
/* 404:    */   }
/* 405:    */   
/* 406:    */   public boolean supportsTupleDistinctCounts()
/* 407:    */   {
/* 408:421 */     return false;
/* 409:    */   }
/* 410:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.DB2Dialect
 * JD-Core Version:    0.7.0.1
 */