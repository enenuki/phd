/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Properties;
/*   7:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   8:    */ import org.hibernate.dialect.function.PositionSubstringFunction;
/*   9:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  10:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  11:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  12:    */ import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
/*  13:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  14:    */ import org.hibernate.id.SequenceGenerator;
/*  15:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*  16:    */ import org.hibernate.type.StandardBasicTypes;
/*  17:    */ import org.hibernate.type.descriptor.sql.BlobTypeDescriptor;
/*  18:    */ import org.hibernate.type.descriptor.sql.ClobTypeDescriptor;
/*  19:    */ import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
/*  20:    */ 
/*  21:    */ public class PostgreSQLDialect
/*  22:    */   extends Dialect
/*  23:    */ {
/*  24:    */   public PostgreSQLDialect()
/*  25:    */   {
/*  26: 60 */     registerColumnType(-7, "bool");
/*  27: 61 */     registerColumnType(-5, "int8");
/*  28: 62 */     registerColumnType(5, "int2");
/*  29: 63 */     registerColumnType(-6, "int2");
/*  30: 64 */     registerColumnType(4, "int4");
/*  31: 65 */     registerColumnType(1, "char(1)");
/*  32: 66 */     registerColumnType(12, "varchar($l)");
/*  33: 67 */     registerColumnType(6, "float4");
/*  34: 68 */     registerColumnType(8, "float8");
/*  35: 69 */     registerColumnType(91, "date");
/*  36: 70 */     registerColumnType(92, "time");
/*  37: 71 */     registerColumnType(93, "timestamp");
/*  38: 72 */     registerColumnType(-3, "bytea");
/*  39: 73 */     registerColumnType(-2, "bytea");
/*  40: 74 */     registerColumnType(-1, "text");
/*  41: 75 */     registerColumnType(-4, "bytea");
/*  42: 76 */     registerColumnType(2005, "text");
/*  43: 77 */     registerColumnType(2004, "oid");
/*  44: 78 */     registerColumnType(2, "numeric($p, $s)");
/*  45: 79 */     registerColumnType(1111, "uuid");
/*  46:    */     
/*  47: 81 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  48: 82 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  49:    */     
/*  50: 84 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  51: 85 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  52: 86 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  53: 87 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  54: 88 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/*  55: 89 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  56: 90 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  57: 91 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  58: 92 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  59: 93 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  60: 94 */     registerFunction("cbrt", new StandardSQLFunction("cbrt", StandardBasicTypes.DOUBLE));
/*  61: 95 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  62: 96 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/*  63: 97 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/*  64:    */     
/*  65: 99 */     registerFunction("stddev", new StandardSQLFunction("stddev", StandardBasicTypes.DOUBLE));
/*  66:100 */     registerFunction("variance", new StandardSQLFunction("variance", StandardBasicTypes.DOUBLE));
/*  67:    */     
/*  68:102 */     registerFunction("random", new NoArgSQLFunction("random", StandardBasicTypes.DOUBLE));
/*  69:    */     
/*  70:104 */     registerFunction("round", new StandardSQLFunction("round"));
/*  71:105 */     registerFunction("trunc", new StandardSQLFunction("trunc"));
/*  72:106 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/*  73:107 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  74:    */     
/*  75:109 */     registerFunction("chr", new StandardSQLFunction("chr", StandardBasicTypes.CHARACTER));
/*  76:110 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  77:111 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  78:112 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/*  79:113 */     registerFunction("initcap", new StandardSQLFunction("initcap"));
/*  80:114 */     registerFunction("to_ascii", new StandardSQLFunction("to_ascii"));
/*  81:115 */     registerFunction("quote_ident", new StandardSQLFunction("quote_ident", StandardBasicTypes.STRING));
/*  82:116 */     registerFunction("quote_literal", new StandardSQLFunction("quote_literal", StandardBasicTypes.STRING));
/*  83:117 */     registerFunction("md5", new StandardSQLFunction("md5"));
/*  84:118 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/*  85:119 */     registerFunction("char_length", new StandardSQLFunction("char_length", StandardBasicTypes.LONG));
/*  86:120 */     registerFunction("bit_length", new StandardSQLFunction("bit_length", StandardBasicTypes.LONG));
/*  87:121 */     registerFunction("octet_length", new StandardSQLFunction("octet_length", StandardBasicTypes.LONG));
/*  88:    */     
/*  89:123 */     registerFunction("age", new StandardSQLFunction("age"));
/*  90:124 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  91:125 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME, false));
/*  92:126 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  93:127 */     registerFunction("date_trunc", new StandardSQLFunction("date_trunc", StandardBasicTypes.TIMESTAMP));
/*  94:128 */     registerFunction("localtime", new NoArgSQLFunction("localtime", StandardBasicTypes.TIME, false));
/*  95:129 */     registerFunction("localtimestamp", new NoArgSQLFunction("localtimestamp", StandardBasicTypes.TIMESTAMP, false));
/*  96:130 */     registerFunction("now", new NoArgSQLFunction("now", StandardBasicTypes.TIMESTAMP));
/*  97:131 */     registerFunction("timeofday", new NoArgSQLFunction("timeofday", StandardBasicTypes.STRING));
/*  98:    */     
/*  99:133 */     registerFunction("current_user", new NoArgSQLFunction("current_user", StandardBasicTypes.STRING, false));
/* 100:134 */     registerFunction("session_user", new NoArgSQLFunction("session_user", StandardBasicTypes.STRING, false));
/* 101:135 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING, false));
/* 102:136 */     registerFunction("current_database", new NoArgSQLFunction("current_database", StandardBasicTypes.STRING, true));
/* 103:137 */     registerFunction("current_schema", new NoArgSQLFunction("current_schema", StandardBasicTypes.STRING, true));
/* 104:    */     
/* 105:139 */     registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/* 106:140 */     registerFunction("to_date", new StandardSQLFunction("to_date", StandardBasicTypes.DATE));
/* 107:141 */     registerFunction("to_timestamp", new StandardSQLFunction("to_timestamp", StandardBasicTypes.TIMESTAMP));
/* 108:142 */     registerFunction("to_number", new StandardSQLFunction("to_number", StandardBasicTypes.BIG_DECIMAL));
/* 109:    */     
/* 110:144 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/* 111:    */     
/* 112:146 */     registerFunction("locate", new PositionSubstringFunction());
/* 113:    */     
/* 114:148 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as varchar)"));
/* 115:    */     
/* 116:150 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 117:151 */     getDefaultProperties().setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
/* 118:    */   }
/* 119:    */   
/* 120:    */   public SqlTypeDescriptor getSqlTypeDescriptorOverride(int sqlCode)
/* 121:    */   {
/* 122:    */     SqlTypeDescriptor descriptor;
/* 123:160 */     switch (sqlCode)
/* 124:    */     {
/* 125:    */     case 2004: 
/* 126:162 */       descriptor = BlobTypeDescriptor.BLOB_BINDING;
/* 127:163 */       break;
/* 128:    */     case 2005: 
/* 129:166 */       descriptor = ClobTypeDescriptor.CLOB_BINDING;
/* 130:167 */       break;
/* 131:    */     default: 
/* 132:170 */       descriptor = super.getSqlTypeDescriptorOverride(sqlCode);
/* 133:    */     }
/* 134:174 */     return descriptor;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public String getAddColumnString()
/* 138:    */   {
/* 139:178 */     return "add column";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getSequenceNextValString(String sequenceName)
/* 143:    */   {
/* 144:182 */     return "select " + getSelectSequenceNextValString(sequenceName);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 148:    */   {
/* 149:186 */     return "nextval ('" + sequenceName + "')";
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getCreateSequenceString(String sequenceName)
/* 153:    */   {
/* 154:190 */     return "create sequence " + sequenceName;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getDropSequenceString(String sequenceName)
/* 158:    */   {
/* 159:194 */     return "drop sequence " + sequenceName;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getCascadeConstraintsString()
/* 163:    */   {
/* 164:198 */     return " cascade";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean dropConstraints()
/* 168:    */   {
/* 169:201 */     return true;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean supportsSequences()
/* 173:    */   {
/* 174:205 */     return true;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getQuerySequencesString()
/* 178:    */   {
/* 179:209 */     return "select relname from pg_class where relkind='S'";
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean supportsLimit()
/* 183:    */   {
/* 184:213 */     return true;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getLimitString(String sql, boolean hasOffset)
/* 188:    */   {
/* 189:217 */     return sql.length() + 20 + sql + (hasOffset ? " limit ? offset ?" : " limit ?");
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean bindLimitParametersInReverseOrder()
/* 193:    */   {
/* 194:224 */     return true;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public boolean supportsIdentityColumns()
/* 198:    */   {
/* 199:228 */     return true;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String getForUpdateString(String aliases)
/* 203:    */   {
/* 204:232 */     return getForUpdateString() + " of " + aliases;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getIdentitySelectString(String table, String column, int type)
/* 208:    */   {
/* 209:236 */     return "select currval('" + table + '_' + column + "_seq')";
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String getIdentityColumnString(int type)
/* 213:    */   {
/* 214:245 */     return type == -5 ? "bigserial not null" : "serial not null";
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean hasDataTypeInIdentityColumn()
/* 218:    */   {
/* 219:251 */     return false;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String getNoColumnsInsertString()
/* 223:    */   {
/* 224:255 */     return "default values";
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getCaseInsensitiveLike()
/* 228:    */   {
/* 229:259 */     return "ilike";
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean supportsCaseInsensitiveLike()
/* 233:    */   {
/* 234:264 */     return true;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Class getNativeIdentifierGeneratorClass()
/* 238:    */   {
/* 239:268 */     return SequenceGenerator.class;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean supportsOuterJoinForUpdate()
/* 243:    */   {
/* 244:272 */     return false;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public boolean useInputStreamToInsertBlob()
/* 248:    */   {
/* 249:276 */     return false;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean supportsUnionAll()
/* 253:    */   {
/* 254:280 */     return true;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public String getSelectClauseNullString(int sqlType)
/* 258:    */   {
/* 259:287 */     String typeName = getTypeName(sqlType, 1L, 1, 0);
/* 260:    */     
/* 261:289 */     int loc = typeName.indexOf('(');
/* 262:290 */     if (loc > -1) {
/* 263:291 */       typeName = typeName.substring(0, loc);
/* 264:    */     }
/* 265:293 */     return "null::" + typeName;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean supportsCommentOn()
/* 269:    */   {
/* 270:297 */     return true;
/* 271:    */   }
/* 272:    */   
/* 273:    */   public boolean supportsTemporaryTables()
/* 274:    */   {
/* 275:301 */     return true;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public String getCreateTemporaryTableString()
/* 279:    */   {
/* 280:305 */     return "create temporary table";
/* 281:    */   }
/* 282:    */   
/* 283:    */   public String getCreateTemporaryTablePostfix()
/* 284:    */   {
/* 285:309 */     return "on commit drop";
/* 286:    */   }
/* 287:    */   
/* 288:    */   public boolean supportsCurrentTimestampSelection()
/* 289:    */   {
/* 290:319 */     return true;
/* 291:    */   }
/* 292:    */   
/* 293:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 294:    */   {
/* 295:323 */     return false;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String getCurrentTimestampSelectString()
/* 299:    */   {
/* 300:327 */     return "select now()";
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean supportsTupleDistinctCounts()
/* 304:    */   {
/* 305:331 */     return false;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public String toBooleanValueString(boolean bool)
/* 309:    */   {
/* 310:335 */     return bool ? "true" : "false";
/* 311:    */   }
/* 312:    */   
/* 313:    */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/* 314:    */   {
/* 315:339 */     return EXTRACTER;
/* 316:    */   }
/* 317:    */   
/* 318:346 */   private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter()
/* 319:    */   {
/* 320:    */     public String extractConstraintName(SQLException sqle)
/* 321:    */     {
/* 322:    */       try
/* 323:    */       {
/* 324:349 */         int sqlState = Integer.valueOf(JdbcExceptionHelper.extractSqlState(sqle)).intValue();
/* 325:350 */         switch (sqlState)
/* 326:    */         {
/* 327:    */         case 23514: 
/* 328:352 */           return extractUsingTemplate("violates check constraint \"", "\"", sqle.getMessage());
/* 329:    */         case 23505: 
/* 330:354 */           return extractUsingTemplate("violates unique constraint \"", "\"", sqle.getMessage());
/* 331:    */         case 23503: 
/* 332:356 */           return extractUsingTemplate("violates foreign key constraint \"", "\"", sqle.getMessage());
/* 333:    */         case 23502: 
/* 334:358 */           return extractUsingTemplate("null value in column \"", "\" violates not-null constraint", sqle.getMessage());
/* 335:    */         case 23001: 
/* 336:360 */           return null;
/* 337:    */         }
/* 338:362 */         return null;
/* 339:    */       }
/* 340:    */       catch (NumberFormatException nfe) {}
/* 341:365 */       return null;
/* 342:    */     }
/* 343:    */   };
/* 344:    */   
/* 345:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 346:    */     throws SQLException
/* 347:    */   {
/* 348:372 */     statement.registerOutParameter(col++, 1111);
/* 349:373 */     return col;
/* 350:    */   }
/* 351:    */   
/* 352:    */   public ResultSet getResultSet(CallableStatement ps)
/* 353:    */     throws SQLException
/* 354:    */   {
/* 355:377 */     ps.execute();
/* 356:378 */     return (ResultSet)ps.getObject(1);
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean supportsPooledSequences()
/* 360:    */   {
/* 361:382 */     return true;
/* 362:    */   }
/* 363:    */   
/* 364:    */   protected String getCreateSequenceString(String sequenceName, int initialValue, int incrementSize)
/* 365:    */   {
/* 366:388 */     return getCreateSequenceString(sequenceName) + " start " + initialValue + " increment " + incrementSize;
/* 367:    */   }
/* 368:    */   
/* 369:    */   public boolean supportsIfExistsBeforeTableName()
/* 370:    */   {
/* 371:401 */     return true;
/* 372:    */   }
/* 373:    */   
/* 374:    */   public boolean supportsEmptyInList()
/* 375:    */   {
/* 376:405 */     return false;
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean supportsExpectedLobUsagePattern()
/* 380:    */   {
/* 381:410 */     return true;
/* 382:    */   }
/* 383:    */   
/* 384:    */   public boolean supportsLobValueChangePropogation()
/* 385:    */   {
/* 386:415 */     return false;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public boolean supportsUnboundedLobLocatorMaterialization()
/* 390:    */   {
/* 391:420 */     return false;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public String getForUpdateString()
/* 395:    */   {
/* 396:425 */     return " for update";
/* 397:    */   }
/* 398:    */   
/* 399:    */   public String getWriteLockString(int timeout)
/* 400:    */   {
/* 401:429 */     if (timeout == 0) {
/* 402:430 */       return " for update nowait";
/* 403:    */     }
/* 404:432 */     return " for update";
/* 405:    */   }
/* 406:    */   
/* 407:    */   public String getReadLockString(int timeout)
/* 408:    */   {
/* 409:436 */     if (timeout == 0) {
/* 410:437 */       return " for share nowait";
/* 411:    */     }
/* 412:439 */     return " for share";
/* 413:    */   }
/* 414:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.PostgreSQLDialect
 * JD-Core Version:    0.7.0.1
 */