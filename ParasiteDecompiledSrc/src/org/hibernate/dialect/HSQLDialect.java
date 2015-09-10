/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.JDBCException;
/*   8:    */ import org.hibernate.LockMode;
/*   9:    */ import org.hibernate.StaleObjectStateException;
/*  10:    */ import org.hibernate.dialect.function.AvgWithArgumentCastFunction;
/*  11:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  12:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  13:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  14:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  15:    */ import org.hibernate.dialect.lock.LockingStrategy;
/*  16:    */ import org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy;
/*  17:    */ import org.hibernate.dialect.lock.OptimisticLockingStrategy;
/*  18:    */ import org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy;
/*  19:    */ import org.hibernate.dialect.lock.PessimisticReadSelectLockingStrategy;
/*  20:    */ import org.hibernate.dialect.lock.PessimisticWriteSelectLockingStrategy;
/*  21:    */ import org.hibernate.dialect.lock.SelectLockingStrategy;
/*  22:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  23:    */ import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
/*  24:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  25:    */ import org.hibernate.internal.CoreMessageLogger;
/*  26:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*  27:    */ import org.hibernate.internal.util.ReflectHelper;
/*  28:    */ import org.hibernate.persister.entity.Lockable;
/*  29:    */ import org.hibernate.type.StandardBasicTypes;
/*  30:    */ import org.jboss.logging.Logger;
/*  31:    */ 
/*  32:    */ public class HSQLDialect
/*  33:    */   extends Dialect
/*  34:    */ {
/*  35: 71 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, HSQLDialect.class.getName());
/*  36: 76 */   private int hsqldbVersion = 18;
/*  37:    */   
/*  38:    */   public HSQLDialect()
/*  39:    */   {
/*  40:    */     try
/*  41:    */     {
/*  42: 83 */       Class props = ReflectHelper.classForName("org.hsqldb.persist.HsqlDatabaseProperties");
/*  43: 84 */       String versionString = (String)props.getDeclaredField("THIS_VERSION").get(null);
/*  44:    */       
/*  45: 86 */       this.hsqldbVersion = (Integer.parseInt(versionString.substring(0, 1)) * 10);
/*  46: 87 */       this.hsqldbVersion += Integer.parseInt(versionString.substring(2, 3));
/*  47:    */     }
/*  48:    */     catch (Throwable e) {}
/*  49: 93 */     registerColumnType(-5, "bigint");
/*  50: 94 */     registerColumnType(-2, "binary($l)");
/*  51: 95 */     registerColumnType(-7, "bit");
/*  52: 96 */     registerColumnType(16, "boolean");
/*  53: 97 */     registerColumnType(1, "char($l)");
/*  54: 98 */     registerColumnType(91, "date");
/*  55:    */     
/*  56:100 */     registerColumnType(3, "decimal($p,$s)");
/*  57:101 */     registerColumnType(8, "double");
/*  58:102 */     registerColumnType(6, "float");
/*  59:103 */     registerColumnType(4, "integer");
/*  60:104 */     registerColumnType(-4, "longvarbinary");
/*  61:105 */     registerColumnType(-1, "longvarchar");
/*  62:106 */     registerColumnType(5, "smallint");
/*  63:107 */     registerColumnType(-6, "tinyint");
/*  64:108 */     registerColumnType(92, "time");
/*  65:109 */     registerColumnType(93, "timestamp");
/*  66:110 */     registerColumnType(12, "varchar($l)");
/*  67:111 */     registerColumnType(-3, "varbinary($l)");
/*  68:113 */     if (this.hsqldbVersion < 20) {
/*  69:114 */       registerColumnType(2, "numeric");
/*  70:    */     } else {
/*  71:117 */       registerColumnType(2, "numeric($p,$s)");
/*  72:    */     }
/*  73:121 */     if (this.hsqldbVersion < 20)
/*  74:    */     {
/*  75:122 */       registerColumnType(2004, "longvarbinary");
/*  76:123 */       registerColumnType(2005, "longvarchar");
/*  77:    */     }
/*  78:    */     else
/*  79:    */     {
/*  80:126 */       registerColumnType(2004, "blob");
/*  81:127 */       registerColumnType(2005, "clob");
/*  82:    */     }
/*  83:131 */     registerFunction("avg", new AvgWithArgumentCastFunction("double"));
/*  84:    */     
/*  85:    */ 
/*  86:134 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/*  87:135 */     registerFunction("char", new StandardSQLFunction("char", StandardBasicTypes.CHARACTER));
/*  88:136 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  89:137 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  90:138 */     registerFunction("lcase", new StandardSQLFunction("lcase"));
/*  91:139 */     registerFunction("ucase", new StandardSQLFunction("ucase"));
/*  92:140 */     registerFunction("soundex", new StandardSQLFunction("soundex", StandardBasicTypes.STRING));
/*  93:141 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/*  94:142 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  95:143 */     registerFunction("reverse", new StandardSQLFunction("reverse"));
/*  96:144 */     registerFunction("space", new StandardSQLFunction("space", StandardBasicTypes.STRING));
/*  97:145 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as varchar(256))"));
/*  98:146 */     registerFunction("rawtohex", new StandardSQLFunction("rawtohex"));
/*  99:147 */     registerFunction("hextoraw", new StandardSQLFunction("hextoraw"));
/* 100:    */     
/* 101:    */ 
/* 102:150 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING));
/* 103:151 */     registerFunction("database", new NoArgSQLFunction("database", StandardBasicTypes.STRING));
/* 104:154 */     if (this.hsqldbVersion < 20) {
/* 105:155 */       registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.DATE, false));
/* 106:    */     } else {
/* 107:157 */       registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.TIMESTAMP, false));
/* 108:    */     }
/* 109:159 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/* 110:160 */     registerFunction("curdate", new NoArgSQLFunction("curdate", StandardBasicTypes.DATE));
/* 111:161 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/* 112:    */     
/* 113:    */ 
/* 114:164 */     registerFunction("now", new NoArgSQLFunction("now", StandardBasicTypes.TIMESTAMP));
/* 115:165 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME, false));
/* 116:166 */     registerFunction("curtime", new NoArgSQLFunction("curtime", StandardBasicTypes.TIME));
/* 117:167 */     registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
/* 118:168 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/* 119:169 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/* 120:170 */     registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", StandardBasicTypes.INTEGER));
/* 121:171 */     registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
/* 122:172 */     registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
/* 123:173 */     registerFunction("week", new StandardSQLFunction("week", StandardBasicTypes.INTEGER));
/* 124:174 */     registerFunction("quarter", new StandardSQLFunction("quarter", StandardBasicTypes.INTEGER));
/* 125:175 */     registerFunction("hour", new StandardSQLFunction("hour", StandardBasicTypes.INTEGER));
/* 126:176 */     registerFunction("minute", new StandardSQLFunction("minute", StandardBasicTypes.INTEGER));
/* 127:177 */     registerFunction("second", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "cast(second(?1) as int)"));
/* 128:178 */     registerFunction("dayname", new StandardSQLFunction("dayname", StandardBasicTypes.STRING));
/* 129:179 */     registerFunction("monthname", new StandardSQLFunction("monthname", StandardBasicTypes.STRING));
/* 130:    */     
/* 131:    */ 
/* 132:182 */     registerFunction("abs", new StandardSQLFunction("abs"));
/* 133:183 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/* 134:    */     
/* 135:185 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/* 136:186 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/* 137:187 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/* 138:188 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/* 139:189 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/* 140:190 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/* 141:191 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/* 142:192 */     registerFunction("log10", new StandardSQLFunction("log10", StandardBasicTypes.DOUBLE));
/* 143:193 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/* 144:194 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/* 145:195 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/* 146:196 */     registerFunction("pi", new NoArgSQLFunction("pi", StandardBasicTypes.DOUBLE));
/* 147:197 */     registerFunction("rand", new StandardSQLFunction("rand", StandardBasicTypes.FLOAT));
/* 148:    */     
/* 149:199 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/* 150:200 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/* 151:201 */     registerFunction("round", new StandardSQLFunction("round"));
/* 152:202 */     registerFunction("roundmagic", new StandardSQLFunction("roundmagic"));
/* 153:203 */     registerFunction("truncate", new StandardSQLFunction("truncate"));
/* 154:    */     
/* 155:205 */     registerFunction("ceiling", new StandardSQLFunction("ceiling"));
/* 156:206 */     registerFunction("floor", new StandardSQLFunction("floor"));
/* 157:210 */     if (this.hsqldbVersion > 21) {
/* 158:211 */       registerFunction("rownum", new NoArgSQLFunction("rownum", StandardBasicTypes.INTEGER));
/* 159:    */     }
/* 160:216 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/* 161:    */     
/* 162:218 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getAddColumnString()
/* 166:    */   {
/* 167:222 */     return "add column";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean supportsIdentityColumns()
/* 171:    */   {
/* 172:226 */     return true;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String getIdentityColumnString()
/* 176:    */   {
/* 177:230 */     return "generated by default as identity (start with 1)";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getIdentitySelectString()
/* 181:    */   {
/* 182:234 */     return "call identity()";
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getIdentityInsertString()
/* 186:    */   {
/* 187:238 */     return this.hsqldbVersion < 20 ? "null" : "default";
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean supportsLockTimeouts()
/* 191:    */   {
/* 192:242 */     return false;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String getForUpdateString()
/* 196:    */   {
/* 197:246 */     return "";
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean supportsUnique()
/* 201:    */   {
/* 202:250 */     return false;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean supportsLimit()
/* 206:    */   {
/* 207:254 */     return true;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getLimitString(String sql, boolean hasOffset)
/* 211:    */   {
/* 212:258 */     if (this.hsqldbVersion < 20) {
/* 213:259 */       return new StringBuffer(sql.length() + 10).append(sql).insert(sql.toLowerCase().indexOf("select") + 6, hasOffset ? " limit ? ?" : " top ?").toString();
/* 214:    */     }
/* 215:268 */     return sql.length() + 20 + sql + (hasOffset ? " offset ? limit ?" : " limit ?");
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean bindLimitParametersFirst()
/* 219:    */   {
/* 220:276 */     return this.hsqldbVersion < 20;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean supportsIfExistsAfterTableName()
/* 224:    */   {
/* 225:280 */     return true;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public boolean supportsColumnCheck()
/* 229:    */   {
/* 230:284 */     return this.hsqldbVersion >= 20;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public boolean supportsSequences()
/* 234:    */   {
/* 235:288 */     return true;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public boolean supportsPooledSequences()
/* 239:    */   {
/* 240:292 */     return true;
/* 241:    */   }
/* 242:    */   
/* 243:    */   protected String getCreateSequenceString(String sequenceName)
/* 244:    */   {
/* 245:296 */     return "create sequence " + sequenceName;
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected String getDropSequenceString(String sequenceName)
/* 249:    */   {
/* 250:300 */     return "drop sequence " + sequenceName;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 254:    */   {
/* 255:304 */     return "next value for " + sequenceName;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String getSequenceNextValString(String sequenceName)
/* 259:    */   {
/* 260:308 */     return "call next value for " + sequenceName;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public String getQuerySequencesString()
/* 264:    */   {
/* 265:313 */     return "select sequence_name from information_schema.system_sequences";
/* 266:    */   }
/* 267:    */   
/* 268:    */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/* 269:    */   {
/* 270:317 */     return this.hsqldbVersion < 20 ? EXTRACTER_18 : EXTRACTER_20;
/* 271:    */   }
/* 272:    */   
/* 273:320 */   private static ViolatedConstraintNameExtracter EXTRACTER_18 = new TemplatedViolatedConstraintNameExtracter()
/* 274:    */   {
/* 275:    */     public String extractConstraintName(SQLException sqle)
/* 276:    */     {
/* 277:329 */       String constraintName = null;
/* 278:    */       
/* 279:331 */       int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
/* 280:333 */       if (errorCode == -8) {
/* 281:334 */         constraintName = extractUsingTemplate("Integrity constraint violation ", " table:", sqle.getMessage());
/* 282:338 */       } else if (errorCode == -9) {
/* 283:339 */         constraintName = extractUsingTemplate("Violation of unique index: ", " in statement [", sqle.getMessage());
/* 284:343 */       } else if (errorCode == -104) {
/* 285:344 */         constraintName = extractUsingTemplate("Unique constraint violation: ", " in statement [", sqle.getMessage());
/* 286:348 */       } else if (errorCode == -177) {
/* 287:349 */         constraintName = extractUsingTemplate("Integrity constraint violation - no parent ", " table:", sqle.getMessage());
/* 288:    */       }
/* 289:354 */       return constraintName;
/* 290:    */     }
/* 291:    */   };
/* 292:363 */   private static ViolatedConstraintNameExtracter EXTRACTER_20 = new TemplatedViolatedConstraintNameExtracter()
/* 293:    */   {
/* 294:    */     public String extractConstraintName(SQLException sqle)
/* 295:    */     {
/* 296:366 */       String constraintName = null;
/* 297:    */       
/* 298:368 */       int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
/* 299:370 */       if (errorCode == -8) {
/* 300:371 */         constraintName = extractUsingTemplate("; ", " table: ", sqle.getMessage());
/* 301:375 */       } else if (errorCode == -9) {
/* 302:376 */         constraintName = extractUsingTemplate("; ", " table: ", sqle.getMessage());
/* 303:380 */       } else if (errorCode == -104) {
/* 304:381 */         constraintName = extractUsingTemplate("; ", " table: ", sqle.getMessage());
/* 305:385 */       } else if (errorCode == -177) {
/* 306:386 */         constraintName = extractUsingTemplate("; ", " table: ", sqle.getMessage());
/* 307:    */       }
/* 308:390 */       return constraintName;
/* 309:    */     }
/* 310:    */   };
/* 311:    */   
/* 312:    */   public String getSelectClauseNullString(int sqlType)
/* 313:    */   {
/* 314:    */     String literal;
/* 315:396 */     switch (sqlType)
/* 316:    */     {
/* 317:    */     case -1: 
/* 318:    */     case 1: 
/* 319:    */     case 12: 
/* 320:400 */       literal = "cast(null as varchar(100))";
/* 321:401 */       break;
/* 322:    */     case -4: 
/* 323:    */     case -3: 
/* 324:    */     case -2: 
/* 325:405 */       literal = "cast(null as varbinary(100))";
/* 326:406 */       break;
/* 327:    */     case 2005: 
/* 328:408 */       literal = "cast(null as clob)";
/* 329:409 */       break;
/* 330:    */     case 2004: 
/* 331:411 */       literal = "cast(null as blob)";
/* 332:412 */       break;
/* 333:    */     case 91: 
/* 334:414 */       literal = "cast(null as date)";
/* 335:415 */       break;
/* 336:    */     case 93: 
/* 337:417 */       literal = "cast(null as timestamp)";
/* 338:418 */       break;
/* 339:    */     case 16: 
/* 340:420 */       literal = "cast(null as boolean)";
/* 341:421 */       break;
/* 342:    */     case -7: 
/* 343:423 */       literal = "cast(null as bit)";
/* 344:424 */       break;
/* 345:    */     case 92: 
/* 346:426 */       literal = "cast(null as time)";
/* 347:427 */       break;
/* 348:    */     default: 
/* 349:429 */       literal = "cast(null as int)";
/* 350:    */     }
/* 351:431 */     return literal;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean supportsUnionAll()
/* 355:    */   {
/* 356:435 */     return true;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean supportsTemporaryTables()
/* 360:    */   {
/* 361:454 */     return true;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String generateTemporaryTableName(String baseTableName)
/* 365:    */   {
/* 366:466 */     if (this.hsqldbVersion < 20) {
/* 367:467 */       return "HT_" + baseTableName;
/* 368:    */     }
/* 369:470 */     return "MODULE.HT_" + baseTableName;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String getCreateTemporaryTableString()
/* 373:    */   {
/* 374:480 */     if (this.hsqldbVersion < 20) {
/* 375:481 */       return "create global temporary table";
/* 376:    */     }
/* 377:484 */     return "declare local temporary table";
/* 378:    */   }
/* 379:    */   
/* 380:    */   public String getCreateTemporaryTablePostfix()
/* 381:    */   {
/* 382:495 */     return "";
/* 383:    */   }
/* 384:    */   
/* 385:    */   public String getDropTemporaryTableString()
/* 386:    */   {
/* 387:504 */     return "drop table";
/* 388:    */   }
/* 389:    */   
/* 390:    */   public Boolean performTemporaryTableDDLInIsolation()
/* 391:    */   {
/* 392:522 */     if (this.hsqldbVersion < 20) {
/* 393:523 */       return Boolean.TRUE;
/* 394:    */     }
/* 395:526 */     return Boolean.FALSE;
/* 396:    */   }
/* 397:    */   
/* 398:    */   public boolean dropTemporaryTableAfterUse()
/* 399:    */   {
/* 400:544 */     return true;
/* 401:    */   }
/* 402:    */   
/* 403:    */   public boolean supportsCurrentTimestampSelection()
/* 404:    */   {
/* 405:558 */     return true;
/* 406:    */   }
/* 407:    */   
/* 408:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 409:    */   {
/* 410:573 */     return false;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public String getCurrentTimestampSelectString()
/* 414:    */   {
/* 415:583 */     return "call current_timestamp";
/* 416:    */   }
/* 417:    */   
/* 418:    */   public String getCurrentTimestampSQLFunctionName()
/* 419:    */   {
/* 420:594 */     return "current_timestamp";
/* 421:    */   }
/* 422:    */   
/* 423:    */   public LockingStrategy getLockingStrategy(Lockable lockable, LockMode lockMode)
/* 424:    */   {
/* 425:609 */     if (lockMode == LockMode.PESSIMISTIC_FORCE_INCREMENT) {
/* 426:610 */       return new PessimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 427:    */     }
/* 428:612 */     if (lockMode == LockMode.PESSIMISTIC_WRITE) {
/* 429:613 */       return new PessimisticWriteSelectLockingStrategy(lockable, lockMode);
/* 430:    */     }
/* 431:615 */     if (lockMode == LockMode.PESSIMISTIC_READ) {
/* 432:616 */       return new PessimisticReadSelectLockingStrategy(lockable, lockMode);
/* 433:    */     }
/* 434:618 */     if (lockMode == LockMode.OPTIMISTIC) {
/* 435:619 */       return new OptimisticLockingStrategy(lockable, lockMode);
/* 436:    */     }
/* 437:621 */     if (lockMode == LockMode.OPTIMISTIC_FORCE_INCREMENT) {
/* 438:622 */       return new OptimisticForceIncrementLockingStrategy(lockable, lockMode);
/* 439:    */     }
/* 440:625 */     if (this.hsqldbVersion < 20) {
/* 441:626 */       return new ReadUncommittedLockingStrategy(lockable, lockMode);
/* 442:    */     }
/* 443:629 */     return new SelectLockingStrategy(lockable, lockMode);
/* 444:    */   }
/* 445:    */   
/* 446:    */   public static class ReadUncommittedLockingStrategy
/* 447:    */     extends SelectLockingStrategy
/* 448:    */   {
/* 449:    */     public ReadUncommittedLockingStrategy(Lockable lockable, LockMode lockMode)
/* 450:    */     {
/* 451:635 */       super(lockMode);
/* 452:    */     }
/* 453:    */     
/* 454:    */     public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/* 455:    */       throws StaleObjectStateException, JDBCException
/* 456:    */     {
/* 457:640 */       if (getLockMode().greaterThan(LockMode.READ)) {
/* 458:641 */         HSQLDialect.LOG.hsqldbSupportsOnlyReadCommittedIsolation();
/* 459:    */       }
/* 460:643 */       super.lock(id, version, object, timeout, session);
/* 461:    */     }
/* 462:    */   }
/* 463:    */   
/* 464:    */   public boolean supportsCommentOn()
/* 465:    */   {
/* 466:648 */     return this.hsqldbVersion >= 20;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public boolean supportsEmptyInList()
/* 470:    */   {
/* 471:655 */     return false;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public boolean requiresCastingOfParametersInSelectClause()
/* 475:    */   {
/* 476:674 */     return true;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 480:    */   {
/* 481:684 */     return this.hsqldbVersion >= 20;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public boolean doesRepeatableReadCauseReadersToBlockWriters()
/* 485:    */   {
/* 486:694 */     return this.hsqldbVersion >= 20;
/* 487:    */   }
/* 488:    */   
/* 489:    */   public boolean supportsLobValueChangePropogation()
/* 490:    */   {
/* 491:699 */     return false;
/* 492:    */   }
/* 493:    */   
/* 494:    */   public String toBooleanValueString(boolean bool)
/* 495:    */   {
/* 496:703 */     return String.valueOf(bool);
/* 497:    */   }
/* 498:    */   
/* 499:    */   public boolean supportsTupleDistinctCounts()
/* 500:    */   {
/* 501:707 */     return false;
/* 502:    */   }
/* 503:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.HSQLDialect
 * JD-Core Version:    0.7.0.1
 */