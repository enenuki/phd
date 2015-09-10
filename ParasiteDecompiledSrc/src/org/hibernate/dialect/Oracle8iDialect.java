/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.sql.CallableStatement;
/*   5:    */ import java.sql.ResultSet;
/*   6:    */ import java.sql.SQLException;
/*   7:    */ import java.util.Properties;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  10:    */ import org.hibernate.dialect.function.NvlFunction;
/*  11:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  12:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  13:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  14:    */ import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
/*  15:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  16:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*  17:    */ import org.hibernate.internal.util.ReflectHelper;
/*  18:    */ import org.hibernate.sql.CaseFragment;
/*  19:    */ import org.hibernate.sql.DecodeCaseFragment;
/*  20:    */ import org.hibernate.sql.JoinFragment;
/*  21:    */ import org.hibernate.sql.OracleJoinFragment;
/*  22:    */ import org.hibernate.type.StandardBasicTypes;
/*  23:    */ import org.hibernate.type.descriptor.sql.BitTypeDescriptor;
/*  24:    */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*  25:    */ 
/*  26:    */ public class Oracle8iDialect
/*  27:    */   extends Dialect
/*  28:    */ {
/*  29:    */   public Oracle8iDialect()
/*  30:    */   {
/*  31: 59 */     registerCharacterTypeMappings();
/*  32: 60 */     registerNumericTypeMappings();
/*  33: 61 */     registerDateTimeTypeMappings();
/*  34: 62 */     registerLargeObjectTypeMappings();
/*  35:    */     
/*  36: 64 */     registerReverseHibernateTypeMappings();
/*  37:    */     
/*  38: 66 */     registerFunctions();
/*  39:    */     
/*  40: 68 */     registerDefaultProperties();
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected void registerCharacterTypeMappings()
/*  44:    */   {
/*  45: 72 */     registerColumnType(1, "char(1)");
/*  46: 73 */     registerColumnType(12, 4000L, "varchar2($l)");
/*  47: 74 */     registerColumnType(12, "long");
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected void registerNumericTypeMappings()
/*  51:    */   {
/*  52: 78 */     registerColumnType(-7, "number(1,0)");
/*  53: 79 */     registerColumnType(-5, "number(19,0)");
/*  54: 80 */     registerColumnType(5, "number(5,0)");
/*  55: 81 */     registerColumnType(-6, "number(3,0)");
/*  56: 82 */     registerColumnType(4, "number(10,0)");
/*  57:    */     
/*  58: 84 */     registerColumnType(6, "float");
/*  59: 85 */     registerColumnType(8, "double precision");
/*  60: 86 */     registerColumnType(2, "number($p,$s)");
/*  61: 87 */     registerColumnType(3, "number($p,$s)");
/*  62:    */     
/*  63: 89 */     registerColumnType(16, "number(1,0)");
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected void registerDateTimeTypeMappings()
/*  67:    */   {
/*  68: 93 */     registerColumnType(91, "date");
/*  69: 94 */     registerColumnType(92, "date");
/*  70: 95 */     registerColumnType(93, "date");
/*  71:    */   }
/*  72:    */   
/*  73:    */   protected void registerLargeObjectTypeMappings()
/*  74:    */   {
/*  75: 99 */     registerColumnType(-2, 2000L, "raw($l)");
/*  76:100 */     registerColumnType(-2, "long raw");
/*  77:    */     
/*  78:102 */     registerColumnType(-3, 2000L, "raw($l)");
/*  79:103 */     registerColumnType(-3, "long raw");
/*  80:    */     
/*  81:105 */     registerColumnType(2004, "blob");
/*  82:106 */     registerColumnType(2005, "clob");
/*  83:    */     
/*  84:108 */     registerColumnType(-1, "long");
/*  85:109 */     registerColumnType(-4, "long raw");
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected void registerReverseHibernateTypeMappings() {}
/*  89:    */   
/*  90:    */   protected void registerFunctions()
/*  91:    */   {
/*  92:116 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  93:117 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  94:    */     
/*  95:119 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  96:120 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  97:121 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  98:122 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  99:123 */     registerFunction("cosh", new StandardSQLFunction("cosh", StandardBasicTypes.DOUBLE));
/* 100:124 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/* 101:125 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/* 102:126 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/* 103:127 */     registerFunction("sinh", new StandardSQLFunction("sinh", StandardBasicTypes.DOUBLE));
/* 104:128 */     registerFunction("stddev", new StandardSQLFunction("stddev", StandardBasicTypes.DOUBLE));
/* 105:129 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/* 106:130 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/* 107:131 */     registerFunction("tanh", new StandardSQLFunction("tanh", StandardBasicTypes.DOUBLE));
/* 108:132 */     registerFunction("variance", new StandardSQLFunction("variance", StandardBasicTypes.DOUBLE));
/* 109:    */     
/* 110:134 */     registerFunction("round", new StandardSQLFunction("round"));
/* 111:135 */     registerFunction("trunc", new StandardSQLFunction("trunc"));
/* 112:136 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/* 113:137 */     registerFunction("floor", new StandardSQLFunction("floor"));
/* 114:    */     
/* 115:139 */     registerFunction("chr", new StandardSQLFunction("chr", StandardBasicTypes.CHARACTER));
/* 116:140 */     registerFunction("initcap", new StandardSQLFunction("initcap"));
/* 117:141 */     registerFunction("lower", new StandardSQLFunction("lower"));
/* 118:142 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/* 119:143 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/* 120:144 */     registerFunction("soundex", new StandardSQLFunction("soundex"));
/* 121:145 */     registerFunction("upper", new StandardSQLFunction("upper"));
/* 122:146 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/* 123:    */     
/* 124:148 */     registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/* 125:149 */     registerFunction("to_date", new StandardSQLFunction("to_date", StandardBasicTypes.TIMESTAMP));
/* 126:    */     
/* 127:151 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/* 128:152 */     registerFunction("current_time", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIME, false));
/* 129:153 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/* 130:    */     
/* 131:155 */     registerFunction("last_day", new StandardSQLFunction("last_day", StandardBasicTypes.DATE));
/* 132:156 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.DATE, false));
/* 133:157 */     registerFunction("systimestamp", new NoArgSQLFunction("systimestamp", StandardBasicTypes.TIMESTAMP, false));
/* 134:158 */     registerFunction("uid", new NoArgSQLFunction("uid", StandardBasicTypes.INTEGER, false));
/* 135:159 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING, false));
/* 136:    */     
/* 137:161 */     registerFunction("rowid", new NoArgSQLFunction("rowid", StandardBasicTypes.LONG, false));
/* 138:162 */     registerFunction("rownum", new NoArgSQLFunction("rownum", StandardBasicTypes.LONG, false));
/* 139:    */     
/* 140:    */ 
/* 141:165 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
/* 142:166 */     registerFunction("instr", new StandardSQLFunction("instr", StandardBasicTypes.INTEGER));
/* 143:167 */     registerFunction("instrb", new StandardSQLFunction("instrb", StandardBasicTypes.INTEGER));
/* 144:168 */     registerFunction("lpad", new StandardSQLFunction("lpad", StandardBasicTypes.STRING));
/* 145:169 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/* 146:170 */     registerFunction("rpad", new StandardSQLFunction("rpad", StandardBasicTypes.STRING));
/* 147:171 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 148:172 */     registerFunction("substrb", new StandardSQLFunction("substrb", StandardBasicTypes.STRING));
/* 149:173 */     registerFunction("translate", new StandardSQLFunction("translate", StandardBasicTypes.STRING));
/* 150:    */     
/* 151:175 */     registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 152:176 */     registerFunction("locate", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "instr(?2,?1)"));
/* 153:177 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "vsize(?1)*8"));
/* 154:178 */     registerFunction("coalesce", new NvlFunction());
/* 155:    */     
/* 156:    */ 
/* 157:181 */     registerFunction("atan2", new StandardSQLFunction("atan2", StandardBasicTypes.FLOAT));
/* 158:182 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.INTEGER));
/* 159:183 */     registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
/* 160:184 */     registerFunction("nvl", new StandardSQLFunction("nvl"));
/* 161:185 */     registerFunction("nvl2", new StandardSQLFunction("nvl2"));
/* 162:186 */     registerFunction("power", new StandardSQLFunction("power", StandardBasicTypes.FLOAT));
/* 163:    */     
/* 164:    */ 
/* 165:189 */     registerFunction("add_months", new StandardSQLFunction("add_months", StandardBasicTypes.DATE));
/* 166:190 */     registerFunction("months_between", new StandardSQLFunction("months_between", StandardBasicTypes.FLOAT));
/* 167:191 */     registerFunction("next_day", new StandardSQLFunction("next_day", StandardBasicTypes.DATE));
/* 168:    */     
/* 169:193 */     registerFunction("str", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void registerDefaultProperties()
/* 173:    */   {
/* 174:197 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "true");
/* 175:198 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 176:    */     
/* 177:    */ 
/* 178:    */ 
/* 179:    */ 
/* 180:203 */     getDefaultProperties().setProperty("hibernate.jdbc.use_get_generated_keys", "false");
/* 181:    */   }
/* 182:    */   
/* 183:    */   protected SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode)
/* 184:    */   {
/* 185:208 */     return sqlCode == 16 ? BitTypeDescriptor.INSTANCE : super.getSqlTypeDescriptorOverride(sqlCode);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public JoinFragment createOuterJoinFragment()
/* 189:    */   {
/* 190:220 */     return new OracleJoinFragment();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public String getCrossJoinSeparator()
/* 194:    */   {
/* 195:227 */     return ", ";
/* 196:    */   }
/* 197:    */   
/* 198:    */   public CaseFragment createCaseFragment()
/* 199:    */   {
/* 200:237 */     return new DecodeCaseFragment();
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getLimitString(String sql, boolean hasOffset)
/* 204:    */   {
/* 205:241 */     sql = sql.trim();
/* 206:242 */     boolean isForUpdate = false;
/* 207:243 */     if (sql.toLowerCase().endsWith(" for update"))
/* 208:    */     {
/* 209:244 */       sql = sql.substring(0, sql.length() - 11);
/* 210:245 */       isForUpdate = true;
/* 211:    */     }
/* 212:248 */     StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
/* 213:249 */     if (hasOffset) {
/* 214:250 */       pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
/* 215:    */     } else {
/* 216:253 */       pagingSelect.append("select * from ( ");
/* 217:    */     }
/* 218:255 */     pagingSelect.append(sql);
/* 219:256 */     if (hasOffset) {
/* 220:257 */       pagingSelect.append(" ) row_ ) where rownum_ <= ? and rownum_ > ?");
/* 221:    */     } else {
/* 222:260 */       pagingSelect.append(" ) where rownum <= ?");
/* 223:    */     }
/* 224:263 */     if (isForUpdate) {
/* 225:264 */       pagingSelect.append(" for update");
/* 226:    */     }
/* 227:267 */     return pagingSelect.toString();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public String getBasicSelectClauseNullString(int sqlType)
/* 231:    */   {
/* 232:278 */     return super.getSelectClauseNullString(sqlType);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public String getSelectClauseNullString(int sqlType)
/* 236:    */   {
/* 237:282 */     switch (sqlType)
/* 238:    */     {
/* 239:    */     case 1: 
/* 240:    */     case 12: 
/* 241:285 */       return "to_char(null)";
/* 242:    */     case 91: 
/* 243:    */     case 92: 
/* 244:    */     case 93: 
/* 245:289 */       return "to_date(null)";
/* 246:    */     }
/* 247:291 */     return "to_number(null)";
/* 248:    */   }
/* 249:    */   
/* 250:    */   public String getCurrentTimestampSelectString()
/* 251:    */   {
/* 252:296 */     return "select sysdate from dual";
/* 253:    */   }
/* 254:    */   
/* 255:    */   public String getCurrentTimestampSQLFunctionName()
/* 256:    */   {
/* 257:300 */     return "sysdate";
/* 258:    */   }
/* 259:    */   
/* 260:    */   public String getAddColumnString()
/* 261:    */   {
/* 262:307 */     return "add";
/* 263:    */   }
/* 264:    */   
/* 265:    */   public String getSequenceNextValString(String sequenceName)
/* 266:    */   {
/* 267:311 */     return "select " + getSelectSequenceNextValString(sequenceName) + " from dual";
/* 268:    */   }
/* 269:    */   
/* 270:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 271:    */   {
/* 272:315 */     return sequenceName + ".nextval";
/* 273:    */   }
/* 274:    */   
/* 275:    */   public String getCreateSequenceString(String sequenceName)
/* 276:    */   {
/* 277:319 */     return "create sequence " + sequenceName;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public String getDropSequenceString(String sequenceName)
/* 281:    */   {
/* 282:323 */     return "drop sequence " + sequenceName;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getCascadeConstraintsString()
/* 286:    */   {
/* 287:327 */     return " cascade constraints";
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean dropConstraints()
/* 291:    */   {
/* 292:331 */     return false;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public String getForUpdateNowaitString()
/* 296:    */   {
/* 297:335 */     return " for update nowait";
/* 298:    */   }
/* 299:    */   
/* 300:    */   public boolean supportsSequences()
/* 301:    */   {
/* 302:339 */     return true;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public boolean supportsPooledSequences()
/* 306:    */   {
/* 307:343 */     return true;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public boolean supportsLimit()
/* 311:    */   {
/* 312:347 */     return true;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String getForUpdateString(String aliases)
/* 316:    */   {
/* 317:351 */     return getForUpdateString() + " of " + aliases;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getForUpdateNowaitString(String aliases)
/* 321:    */   {
/* 322:355 */     return getForUpdateString() + " of " + aliases + " nowait";
/* 323:    */   }
/* 324:    */   
/* 325:    */   public boolean bindLimitParametersInReverseOrder()
/* 326:    */   {
/* 327:359 */     return true;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public boolean useMaxForLimit()
/* 331:    */   {
/* 332:363 */     return true;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public boolean forUpdateOfColumns()
/* 336:    */   {
/* 337:367 */     return true;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String getQuerySequencesString()
/* 341:    */   {
/* 342:371 */     return " select sequence_name from all_sequences  union select synonym_name   from all_synonyms us, all_sequences asq  where asq.sequence_name = us.table_name    and asq.sequence_owner = us.table_owner";
/* 343:    */   }
/* 344:    */   
/* 345:    */   public String getSelectGUIDString()
/* 346:    */   {
/* 347:380 */     return "select rawtohex(sys_guid()) from dual";
/* 348:    */   }
/* 349:    */   
/* 350:    */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/* 351:    */   {
/* 352:384 */     return EXTRACTER;
/* 353:    */   }
/* 354:    */   
/* 355:387 */   private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter()
/* 356:    */   {
/* 357:    */     public String extractConstraintName(SQLException sqle)
/* 358:    */     {
/* 359:396 */       int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
/* 360:397 */       if ((errorCode == 1) || (errorCode == 2291) || (errorCode == 2292)) {
/* 361:398 */         return extractUsingTemplate("(", ")", sqle.getMessage());
/* 362:    */       }
/* 363:400 */       if (errorCode == 1400) {
/* 364:402 */         return null;
/* 365:    */       }
/* 366:405 */       return null;
/* 367:    */     }
/* 368:    */   };
/* 369:    */   public static final String ORACLE_TYPES_CLASS_NAME = "oracle.jdbc.OracleTypes";
/* 370:    */   public static final String DEPRECATED_ORACLE_TYPES_CLASS_NAME = "oracle.jdbc.driver.OracleTypes";
/* 371:    */   public static final int INIT_ORACLETYPES_CURSOR_VALUE = -99;
/* 372:417 */   private int oracleCursorTypeSqlType = -99;
/* 373:    */   
/* 374:    */   public int getOracleCursorTypeSqlType()
/* 375:    */   {
/* 376:420 */     if (this.oracleCursorTypeSqlType == -99) {
/* 377:422 */       this.oracleCursorTypeSqlType = extractOracleCursorTypeValue();
/* 378:    */     }
/* 379:424 */     return this.oracleCursorTypeSqlType;
/* 380:    */   }
/* 381:    */   
/* 382:    */   protected int extractOracleCursorTypeValue()
/* 383:    */   {
/* 384:    */     Class oracleTypesClass;
/* 385:    */     try
/* 386:    */     {
/* 387:430 */       oracleTypesClass = ReflectHelper.classForName("oracle.jdbc.OracleTypes");
/* 388:    */     }
/* 389:    */     catch (ClassNotFoundException cnfe)
/* 390:    */     {
/* 391:    */       try
/* 392:    */       {
/* 393:434 */         oracleTypesClass = ReflectHelper.classForName("oracle.jdbc.driver.OracleTypes");
/* 394:    */       }
/* 395:    */       catch (ClassNotFoundException e)
/* 396:    */       {
/* 397:437 */         throw new HibernateException("Unable to locate OracleTypes class", e);
/* 398:    */       }
/* 399:    */     }
/* 400:    */     try
/* 401:    */     {
/* 402:442 */       return oracleTypesClass.getField("CURSOR").getInt(null);
/* 403:    */     }
/* 404:    */     catch (Exception se)
/* 405:    */     {
/* 406:445 */       throw new HibernateException("Unable to access OracleTypes.CURSOR value", se);
/* 407:    */     }
/* 408:    */   }
/* 409:    */   
/* 410:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 411:    */     throws SQLException
/* 412:    */   {
/* 413:451 */     statement.registerOutParameter(col, getOracleCursorTypeSqlType());
/* 414:452 */     col++;
/* 415:453 */     return col;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public ResultSet getResultSet(CallableStatement ps)
/* 419:    */     throws SQLException
/* 420:    */   {
/* 421:457 */     ps.execute();
/* 422:458 */     return (ResultSet)ps.getObject(1);
/* 423:    */   }
/* 424:    */   
/* 425:    */   public boolean supportsUnionAll()
/* 426:    */   {
/* 427:462 */     return true;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public boolean supportsCommentOn()
/* 431:    */   {
/* 432:466 */     return true;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public boolean supportsTemporaryTables()
/* 436:    */   {
/* 437:470 */     return true;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public String generateTemporaryTableName(String baseTableName)
/* 441:    */   {
/* 442:474 */     String name = super.generateTemporaryTableName(baseTableName);
/* 443:475 */     return name.length() > 30 ? name.substring(1, 30) : name;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public String getCreateTemporaryTableString()
/* 447:    */   {
/* 448:479 */     return "create global temporary table";
/* 449:    */   }
/* 450:    */   
/* 451:    */   public String getCreateTemporaryTablePostfix()
/* 452:    */   {
/* 453:483 */     return "on commit delete rows";
/* 454:    */   }
/* 455:    */   
/* 456:    */   public boolean dropTemporaryTableAfterUse()
/* 457:    */   {
/* 458:487 */     return false;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public boolean supportsCurrentTimestampSelection()
/* 462:    */   {
/* 463:491 */     return true;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 467:    */   {
/* 468:495 */     return false;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public boolean supportsEmptyInList()
/* 472:    */   {
/* 473:502 */     return false;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public boolean supportsExistsInSelect()
/* 477:    */   {
/* 478:506 */     return false;
/* 479:    */   }
/* 480:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Oracle8iDialect
 * JD-Core Version:    0.7.0.1
 */