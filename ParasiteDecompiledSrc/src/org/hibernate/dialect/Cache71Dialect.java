/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.LockMode;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.dialect.function.ConditionalParenthesisFunction;
/*  10:    */ import org.hibernate.dialect.function.ConvertFunction;
/*  11:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  12:    */ import org.hibernate.dialect.function.NvlFunction;
/*  13:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  14:    */ import org.hibernate.dialect.function.StandardJDBCEscapeFunction;
/*  15:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  16:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  17:    */ import org.hibernate.dialect.lock.LockingStrategy;
/*  18:    */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*  19:    */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*  20:    */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*  21:    */ import org.hibernate.dialect.lock.PessimisticReadUpdateLockingStrategy;
/*  22:    */ import org.hibernate.dialect.lock.PessimisticWriteUpdateLockingStrategy;
/*  23:    */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*  24:    */ import org.hibernate.dialect.lock.UpdateLockingStrategy;
/*  25:    */ import org.hibernate.exception.internal.CacheSQLStateConverter;
/*  26:    */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*  27:    */ import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
/*  28:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  29:    */ import org.hibernate.id.IdentityGenerator;
/*  30:    */ import org.hibernate.internal.util.StringHelper;
/*  31:    */ import org.hibernate.persister.entity.Lockable;
/*  32:    */ import org.hibernate.sql.CacheJoinFragment;
/*  33:    */ import org.hibernate.sql.JoinFragment;
/*  34:    */ import org.hibernate.type.StandardBasicTypes;
/*  35:    */ 
/*  36:    */ public class Cache71Dialect
/*  37:    */   extends Dialect
/*  38:    */ {
/*  39:    */   public Cache71Dialect()
/*  40:    */   {
/*  41:223 */     commonRegistration();
/*  42:224 */     register71Functions();
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected final void commonRegistration()
/*  46:    */   {
/*  47:233 */     registerColumnType(-2, "varbinary($1)");
/*  48:234 */     registerColumnType(-5, "BigInt");
/*  49:235 */     registerColumnType(-7, "bit");
/*  50:236 */     registerColumnType(1, "char(1)");
/*  51:237 */     registerColumnType(91, "date");
/*  52:238 */     registerColumnType(3, "decimal");
/*  53:239 */     registerColumnType(8, "double");
/*  54:240 */     registerColumnType(6, "float");
/*  55:241 */     registerColumnType(4, "integer");
/*  56:242 */     registerColumnType(-4, "longvarbinary");
/*  57:243 */     registerColumnType(-1, "longvarchar");
/*  58:244 */     registerColumnType(2, "numeric($p,$s)");
/*  59:245 */     registerColumnType(7, "real");
/*  60:246 */     registerColumnType(5, "smallint");
/*  61:247 */     registerColumnType(93, "timestamp");
/*  62:248 */     registerColumnType(92, "time");
/*  63:249 */     registerColumnType(-6, "tinyint");
/*  64:    */     
/*  65:    */ 
/*  66:252 */     registerColumnType(-3, "longvarbinary");
/*  67:253 */     registerColumnType(12, "varchar($l)");
/*  68:254 */     registerColumnType(2004, "longvarbinary");
/*  69:255 */     registerColumnType(2005, "longvarchar");
/*  70:    */     
/*  71:257 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "false");
/*  72:258 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/*  73:    */     
/*  74:    */ 
/*  75:261 */     getDefaultProperties().setProperty("hibernate.use_sql_comments", "false");
/*  76:    */     
/*  77:263 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  78:264 */     registerFunction("acos", new StandardJDBCEscapeFunction("acos", StandardBasicTypes.DOUBLE));
/*  79:265 */     registerFunction("%alphaup", new StandardSQLFunction("%alphaup", StandardBasicTypes.STRING));
/*  80:266 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.STRING));
/*  81:267 */     registerFunction("asin", new StandardJDBCEscapeFunction("asin", StandardBasicTypes.DOUBLE));
/*  82:268 */     registerFunction("atan", new StandardJDBCEscapeFunction("atan", StandardBasicTypes.DOUBLE));
/*  83:269 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "($length(?1)*8)"));
/*  84:    */     
/*  85:271 */     registerFunction("ceiling", new StandardSQLFunction("ceiling", StandardBasicTypes.INTEGER));
/*  86:272 */     registerFunction("char", new StandardJDBCEscapeFunction("char", StandardBasicTypes.CHARACTER));
/*  87:273 */     registerFunction("character_length", new StandardSQLFunction("character_length", StandardBasicTypes.INTEGER));
/*  88:274 */     registerFunction("char_length", new StandardSQLFunction("char_length", StandardBasicTypes.INTEGER));
/*  89:275 */     registerFunction("cos", new StandardJDBCEscapeFunction("cos", StandardBasicTypes.DOUBLE));
/*  90:276 */     registerFunction("cot", new StandardJDBCEscapeFunction("cot", StandardBasicTypes.DOUBLE));
/*  91:277 */     registerFunction("coalesce", new VarArgsSQLFunction("coalesce(", ",", ")"));
/*  92:278 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
/*  93:279 */     registerFunction("convert", new ConvertFunction());
/*  94:280 */     registerFunction("curdate", new StandardJDBCEscapeFunction("curdate", StandardBasicTypes.DATE));
/*  95:281 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  96:282 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME, false));
/*  97:283 */     registerFunction("current_timestamp", new ConditionalParenthesisFunction("current_timestamp", StandardBasicTypes.TIMESTAMP));
/*  98:    */     
/*  99:    */ 
/* 100:286 */     registerFunction("curtime", new StandardJDBCEscapeFunction("curtime", StandardBasicTypes.TIME));
/* 101:287 */     registerFunction("database", new StandardJDBCEscapeFunction("database", StandardBasicTypes.STRING));
/* 102:288 */     registerFunction("dateadd", new VarArgsSQLFunction(StandardBasicTypes.TIMESTAMP, "dateadd(", ",", ")"));
/* 103:289 */     registerFunction("datediff", new VarArgsSQLFunction(StandardBasicTypes.INTEGER, "datediff(", ",", ")"));
/* 104:290 */     registerFunction("datename", new VarArgsSQLFunction(StandardBasicTypes.STRING, "datename(", ",", ")"));
/* 105:291 */     registerFunction("datepart", new VarArgsSQLFunction(StandardBasicTypes.INTEGER, "datepart(", ",", ")"));
/* 106:292 */     registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
/* 107:293 */     registerFunction("dayname", new StandardJDBCEscapeFunction("dayname", StandardBasicTypes.STRING));
/* 108:294 */     registerFunction("dayofmonth", new StandardJDBCEscapeFunction("dayofmonth", StandardBasicTypes.INTEGER));
/* 109:295 */     registerFunction("dayofweek", new StandardJDBCEscapeFunction("dayofweek", StandardBasicTypes.INTEGER));
/* 110:296 */     registerFunction("dayofyear", new StandardJDBCEscapeFunction("dayofyear", StandardBasicTypes.INTEGER));
/* 111:    */     
/* 112:298 */     registerFunction("%exact", new StandardSQLFunction("%exact", StandardBasicTypes.STRING));
/* 113:299 */     registerFunction("exp", new StandardJDBCEscapeFunction("exp", StandardBasicTypes.DOUBLE));
/* 114:300 */     registerFunction("%external", new StandardSQLFunction("%external", StandardBasicTypes.STRING));
/* 115:301 */     registerFunction("$extract", new VarArgsSQLFunction(StandardBasicTypes.INTEGER, "$extract(", ",", ")"));
/* 116:302 */     registerFunction("$find", new VarArgsSQLFunction(StandardBasicTypes.INTEGER, "$find(", ",", ")"));
/* 117:303 */     registerFunction("floor", new StandardSQLFunction("floor", StandardBasicTypes.INTEGER));
/* 118:304 */     registerFunction("getdate", new StandardSQLFunction("getdate", StandardBasicTypes.TIMESTAMP));
/* 119:305 */     registerFunction("hour", new StandardJDBCEscapeFunction("hour", StandardBasicTypes.INTEGER));
/* 120:306 */     registerFunction("ifnull", new VarArgsSQLFunction("ifnull(", ",", ")"));
/* 121:307 */     registerFunction("%internal", new StandardSQLFunction("%internal"));
/* 122:308 */     registerFunction("isnull", new VarArgsSQLFunction("isnull(", ",", ")"));
/* 123:309 */     registerFunction("isnumeric", new StandardSQLFunction("isnumeric", StandardBasicTypes.INTEGER));
/* 124:310 */     registerFunction("lcase", new StandardJDBCEscapeFunction("lcase", StandardBasicTypes.STRING));
/* 125:311 */     registerFunction("left", new StandardJDBCEscapeFunction("left", StandardBasicTypes.STRING));
/* 126:312 */     registerFunction("len", new StandardSQLFunction("len", StandardBasicTypes.INTEGER));
/* 127:313 */     registerFunction("$length", new VarArgsSQLFunction("$length(", ",", ")"));
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:317 */     registerFunction("$list", new VarArgsSQLFunction("$list(", ",", ")"));
/* 132:318 */     registerFunction("$listdata", new VarArgsSQLFunction("$listdata(", ",", ")"));
/* 133:319 */     registerFunction("$listfind", new VarArgsSQLFunction("$listfind(", ",", ")"));
/* 134:320 */     registerFunction("$listget", new VarArgsSQLFunction("$listget(", ",", ")"));
/* 135:321 */     registerFunction("$listlength", new StandardSQLFunction("$listlength", StandardBasicTypes.INTEGER));
/* 136:322 */     registerFunction("locate", new StandardSQLFunction("$FIND", StandardBasicTypes.INTEGER));
/* 137:323 */     registerFunction("log", new StandardJDBCEscapeFunction("log", StandardBasicTypes.DOUBLE));
/* 138:324 */     registerFunction("log10", new StandardJDBCEscapeFunction("log", StandardBasicTypes.DOUBLE));
/* 139:325 */     registerFunction("lower", new StandardSQLFunction("lower"));
/* 140:326 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/* 141:327 */     registerFunction("minute", new StandardJDBCEscapeFunction("minute", StandardBasicTypes.INTEGER));
/* 142:328 */     registerFunction("mod", new StandardJDBCEscapeFunction("mod", StandardBasicTypes.DOUBLE));
/* 143:329 */     registerFunction("month", new StandardJDBCEscapeFunction("month", StandardBasicTypes.INTEGER));
/* 144:330 */     registerFunction("monthname", new StandardJDBCEscapeFunction("monthname", StandardBasicTypes.STRING));
/* 145:331 */     registerFunction("now", new StandardJDBCEscapeFunction("monthname", StandardBasicTypes.TIMESTAMP));
/* 146:332 */     registerFunction("nullif", new VarArgsSQLFunction("nullif(", ",", ")"));
/* 147:333 */     registerFunction("nvl", new NvlFunction());
/* 148:334 */     registerFunction("%odbcin", new StandardSQLFunction("%odbcin"));
/* 149:335 */     registerFunction("%odbcout", new StandardSQLFunction("%odbcin"));
/* 150:336 */     registerFunction("%pattern", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "%pattern", ""));
/* 151:337 */     registerFunction("pi", new StandardJDBCEscapeFunction("pi", StandardBasicTypes.DOUBLE));
/* 152:338 */     registerFunction("$piece", new VarArgsSQLFunction(StandardBasicTypes.STRING, "$piece(", ",", ")"));
/* 153:339 */     registerFunction("position", new VarArgsSQLFunction(StandardBasicTypes.INTEGER, "position(", " in ", ")"));
/* 154:340 */     registerFunction("power", new VarArgsSQLFunction(StandardBasicTypes.STRING, "power(", ",", ")"));
/* 155:341 */     registerFunction("quarter", new StandardJDBCEscapeFunction("quarter", StandardBasicTypes.INTEGER));
/* 156:342 */     registerFunction("repeat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "repeat(", ",", ")"));
/* 157:343 */     registerFunction("replicate", new VarArgsSQLFunction(StandardBasicTypes.STRING, "replicate(", ",", ")"));
/* 158:344 */     registerFunction("right", new StandardJDBCEscapeFunction("right", StandardBasicTypes.STRING));
/* 159:345 */     registerFunction("round", new VarArgsSQLFunction(StandardBasicTypes.FLOAT, "round(", ",", ")"));
/* 160:346 */     registerFunction("rtrim", new StandardSQLFunction("rtrim", StandardBasicTypes.STRING));
/* 161:347 */     registerFunction("second", new StandardJDBCEscapeFunction("second", StandardBasicTypes.INTEGER));
/* 162:348 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/* 163:349 */     registerFunction("sin", new StandardJDBCEscapeFunction("sin", StandardBasicTypes.DOUBLE));
/* 164:350 */     registerFunction("space", new StandardSQLFunction("space", StandardBasicTypes.STRING));
/* 165:351 */     registerFunction("%sqlstring", new VarArgsSQLFunction(StandardBasicTypes.STRING, "%sqlstring(", ",", ")"));
/* 166:352 */     registerFunction("%sqlupper", new VarArgsSQLFunction(StandardBasicTypes.STRING, "%sqlupper(", ",", ")"));
/* 167:353 */     registerFunction("sqrt", new StandardJDBCEscapeFunction("SQRT", StandardBasicTypes.DOUBLE));
/* 168:354 */     registerFunction("%startswith", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "%startswith", ""));
/* 169:    */     
/* 170:356 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as char varying)"));
/* 171:357 */     registerFunction("string", new VarArgsSQLFunction(StandardBasicTypes.STRING, "string(", ",", ")"));
/* 172:    */     
/* 173:359 */     registerFunction("%string", new VarArgsSQLFunction(StandardBasicTypes.STRING, "%string(", ",", ")"));
/* 174:360 */     registerFunction("substr", new VarArgsSQLFunction(StandardBasicTypes.STRING, "substr(", ",", ")"));
/* 175:361 */     registerFunction("substring", new VarArgsSQLFunction(StandardBasicTypes.STRING, "substring(", ",", ")"));
/* 176:362 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.TIMESTAMP, false));
/* 177:363 */     registerFunction("tan", new StandardJDBCEscapeFunction("tan", StandardBasicTypes.DOUBLE));
/* 178:364 */     registerFunction("timestampadd", new StandardJDBCEscapeFunction("timestampadd", StandardBasicTypes.DOUBLE));
/* 179:365 */     registerFunction("timestampdiff", new StandardJDBCEscapeFunction("timestampdiff", StandardBasicTypes.DOUBLE));
/* 180:366 */     registerFunction("tochar", new VarArgsSQLFunction(StandardBasicTypes.STRING, "tochar(", ",", ")"));
/* 181:367 */     registerFunction("to_char", new VarArgsSQLFunction(StandardBasicTypes.STRING, "to_char(", ",", ")"));
/* 182:368 */     registerFunction("todate", new VarArgsSQLFunction(StandardBasicTypes.STRING, "todate(", ",", ")"));
/* 183:369 */     registerFunction("to_date", new VarArgsSQLFunction(StandardBasicTypes.STRING, "todate(", ",", ")"));
/* 184:370 */     registerFunction("tonumber", new StandardSQLFunction("tonumber"));
/* 185:371 */     registerFunction("to_number", new StandardSQLFunction("tonumber"));
/* 186:    */     
/* 187:    */ 
/* 188:    */ 
/* 189:375 */     registerFunction("truncate", new StandardJDBCEscapeFunction("truncate", StandardBasicTypes.STRING));
/* 190:376 */     registerFunction("ucase", new StandardJDBCEscapeFunction("ucase", StandardBasicTypes.STRING));
/* 191:377 */     registerFunction("upper", new StandardSQLFunction("upper"));
/* 192:    */     
/* 193:379 */     registerFunction("%upper", new StandardSQLFunction("%upper"));
/* 194:380 */     registerFunction("user", new StandardJDBCEscapeFunction("user", StandardBasicTypes.STRING));
/* 195:381 */     registerFunction("week", new StandardJDBCEscapeFunction("user", StandardBasicTypes.INTEGER));
/* 196:382 */     registerFunction("xmlconcat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "xmlconcat(", ",", ")"));
/* 197:383 */     registerFunction("xmlelement", new VarArgsSQLFunction(StandardBasicTypes.STRING, "xmlelement(", ",", ")"));
/* 198:    */     
/* 199:385 */     registerFunction("year", new StandardJDBCEscapeFunction("year", StandardBasicTypes.INTEGER));
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected final void register71Functions()
/* 203:    */   {
/* 204:389 */     registerFunction("str", new VarArgsSQLFunction(StandardBasicTypes.STRING, "str(", ",", ")"));
/* 205:    */   }
/* 206:    */   
/* 207:    */   public boolean hasAlterTable()
/* 208:    */   {
/* 209:396 */     return true;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean qualifyIndexName()
/* 213:    */   {
/* 214:401 */     return false;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean supportsUnique()
/* 218:    */   {
/* 219:406 */     return true;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey)
/* 223:    */   {
/* 224:421 */     return 300 + " ADD CONSTRAINT " + constraintName + " FOREIGN KEY " + constraintName + " (" + StringHelper.join(", ", foreignKey) + ") REFERENCES " + referencedTable + " (" + StringHelper.join(", ", primaryKey) + ") ";
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean supportsCheck()
/* 228:    */   {
/* 229:438 */     return false;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String getAddColumnString()
/* 233:    */   {
/* 234:443 */     return " add column";
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String getCascadeConstraintsString()
/* 238:    */   {
/* 239:448 */     return "";
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean dropConstraints()
/* 243:    */   {
/* 244:453 */     return true;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public boolean supportsCascadeDelete()
/* 248:    */   {
/* 249:457 */     return true;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean hasSelfReferentialForeignKeyBug()
/* 253:    */   {
/* 254:461 */     return true;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean supportsTemporaryTables()
/* 258:    */   {
/* 259:467 */     return true;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public String generateTemporaryTableName(String baseTableName)
/* 263:    */   {
/* 264:471 */     String name = super.generateTemporaryTableName(baseTableName);
/* 265:472 */     return name.length() > 25 ? name.substring(1, 25) : name;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public String getCreateTemporaryTableString()
/* 269:    */   {
/* 270:476 */     return "create global temporary table";
/* 271:    */   }
/* 272:    */   
/* 273:    */   public Boolean performTemporaryTableDDLInIsolation()
/* 274:    */   {
/* 275:480 */     return Boolean.FALSE;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String getCreateTemporaryTablePostfix()
/* 279:    */   {
/* 280:484 */     return "";
/* 281:    */   }
/* 282:    */   
/* 283:    */   public boolean dropTemporaryTableAfterUse()
/* 284:    */   {
/* 285:488 */     return true;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public boolean supportsIdentityColumns()
/* 289:    */   {
/* 290:494 */     return true;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public Class getNativeIdentifierGeneratorClass()
/* 294:    */   {
/* 295:498 */     return IdentityGenerator.class;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public boolean hasDataTypeInIdentityColumn()
/* 299:    */   {
/* 300:504 */     return true;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public String getIdentityColumnString()
/* 304:    */     throws MappingException
/* 305:    */   {
/* 306:509 */     return "identity";
/* 307:    */   }
/* 308:    */   
/* 309:    */   public String getIdentitySelectString()
/* 310:    */   {
/* 311:513 */     return "SELECT LAST_IDENTITY() FROM %TSQL_sys.snf";
/* 312:    */   }
/* 313:    */   
/* 314:    */   public boolean supportsSequences()
/* 315:    */   {
/* 316:519 */     return false;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public boolean supportsForUpdate()
/* 320:    */   {
/* 321:549 */     return false;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public boolean supportsForUpdateOf()
/* 325:    */   {
/* 326:554 */     return false;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public boolean supportsForUpdateNowait()
/* 330:    */   {
/* 331:559 */     return false;
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean supportsOuterJoinForUpdate()
/* 335:    */   {
/* 336:563 */     return false;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/* 340:    */   {
/* 341:569 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/* 342:570 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 343:    */     }
/* 344:572 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/* 345:573 */       return new PessimisticWriteUpdateLockingStrategy(lockable, lockMode);
/* 346:    */     }
/* 347:575 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/* 348:576 */       return new PessimisticReadUpdateLockingStrategy(lockable, lockMode);
/* 349:    */     }
/* 350:578 */     if (lockMode == LockMode.OPTIMISTIC) {
/* 351:579 */       return new OptimisticLockingStrategy(lockable, lockMode);
/* 352:    */     }
/* 353:581 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/* 354:582 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 355:    */     }
/* 356:584 */     if (lockMode.greaterThan(LockMode.READ)) {
/* 357:585 */       return new UpdateLockingStrategy(lockable, lockMode);
/* 358:    */     }
/* 359:588 */     return new SelectLockingStrategy(lockable, lockMode);
/* 360:    */   }
/* 361:    */   
/* 362:    */   public boolean supportsLimit()
/* 363:    */   {
/* 364:595 */     return true;
/* 365:    */   }
/* 366:    */   
/* 367:    */   public boolean supportsLimitOffset()
/* 368:    */   {
/* 369:599 */     return false;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public boolean supportsVariableLimit()
/* 373:    */   {
/* 374:603 */     return true;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public boolean bindLimitParametersFirst()
/* 378:    */   {
/* 379:608 */     return true;
/* 380:    */   }
/* 381:    */   
/* 382:    */   public boolean useMaxForLimit()
/* 383:    */   {
/* 384:613 */     return true;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public String getLimitString(String sql, boolean hasOffset)
/* 388:    */   {
/* 389:617 */     if (hasOffset) {
/* 390:618 */       throw new UnsupportedOperationException("query result offset is not supported");
/* 391:    */     }
/* 392:623 */     int insertionPoint = sql.startsWith("select distinct") ? 15 : 6;
/* 393:    */     
/* 394:625 */     return new StringBuffer(sql.length() + 8).append(sql).insert(insertionPoint, " TOP ? ").toString();
/* 395:    */   }
/* 396:    */   
/* 397:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 398:    */     throws SQLException
/* 399:    */   {
/* 400:634 */     return col;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public ResultSet getResultSet(CallableStatement ps)
/* 404:    */     throws SQLException
/* 405:    */   {
/* 406:638 */     ps.execute();
/* 407:639 */     return (ResultSet)ps.getObject(1);
/* 408:    */   }
/* 409:    */   
/* 410:    */   public String getLowercaseFunction()
/* 411:    */   {
/* 412:646 */     return "lower";
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String getNullColumnString()
/* 416:    */   {
/* 417:651 */     return " null";
/* 418:    */   }
/* 419:    */   
/* 420:    */   public JoinFragment createOuterJoinFragment()
/* 421:    */   {
/* 422:656 */     return new CacheJoinFragment();
/* 423:    */   }
/* 424:    */   
/* 425:    */   public String getNoColumnsInsertString()
/* 426:    */   {
/* 427:662 */     return " default values";
/* 428:    */   }
/* 429:    */   
/* 430:    */   public SQLExceptionConverter buildSQLExceptionConverter()
/* 431:    */   {
/* 432:666 */     return new CacheSQLStateConverter(EXTRACTER);
/* 433:    */   }
/* 434:    */   
/* 435:669 */   public static final ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter()
/* 436:    */   {
/* 437:    */     public String extractConstraintName(SQLException sqle)
/* 438:    */     {
/* 439:677 */       return extractUsingTemplate("constraint (", ") violated", sqle.getMessage());
/* 440:    */     }
/* 441:    */   };
/* 442:    */   
/* 443:    */   public boolean supportsEmptyInList()
/* 444:    */   {
/* 445:685 */     return false;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean areStringComparisonsCaseInsensitive()
/* 449:    */   {
/* 450:689 */     return true;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public boolean supportsResultSetPositionQueryMethodsOnForwardOnlyCursor()
/* 454:    */   {
/* 455:693 */     return false;
/* 456:    */   }
/* 457:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Cache71Dialect
 * JD-Core Version:    0.7.0.1
 */