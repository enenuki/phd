/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.sql.SQLException;
/*   5:    */ import java.util.Properties;
/*   6:    */ import org.hibernate.dialect.function.AvgWithArgumentCastFunction;
/*   7:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   8:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   9:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  10:    */ import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
/*  11:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.ReflectHelper;
/*  14:    */ import org.hibernate.type.StandardBasicTypes;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public class H2Dialect
/*  18:    */   extends Dialect
/*  19:    */ {
/*  20: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, H2Dialect.class.getName());
/*  21:    */   private final String querySequenceString;
/*  22:    */   
/*  23:    */   public H2Dialect()
/*  24:    */   {
/*  25: 56 */     String querySequenceString = "select sequence_name from information_schema.sequences";
/*  26:    */     try
/*  27:    */     {
/*  28: 59 */       Class h2ConstantsClass = ReflectHelper.classForName("org.h2.engine.Constants");
/*  29: 60 */       int majorVersion = ((Integer)h2ConstantsClass.getDeclaredField("VERSION_MAJOR").get(null)).intValue();
/*  30: 61 */       int minorVersion = ((Integer)h2ConstantsClass.getDeclaredField("VERSION_MINOR").get(null)).intValue();
/*  31: 62 */       int buildId = ((Integer)h2ConstantsClass.getDeclaredField("BUILD_ID").get(null)).intValue();
/*  32: 63 */       if (buildId < 32) {
/*  33: 64 */         querySequenceString = "select name from information_schema.sequences";
/*  34:    */       }
/*  35: 66 */       if ((majorVersion <= 1) && (minorVersion <= 2) && (buildId < 139)) {
/*  36: 67 */         LOG.unsupportedMultiTableBulkHqlJpaql(majorVersion, minorVersion, buildId);
/*  37:    */       }
/*  38:    */     }
/*  39:    */     catch (Exception e)
/*  40:    */     {
/*  41: 73 */       LOG.undeterminedH2Version();
/*  42:    */     }
/*  43: 76 */     this.querySequenceString = querySequenceString;
/*  44:    */     
/*  45: 78 */     registerColumnType(16, "boolean");
/*  46: 79 */     registerColumnType(-5, "bigint");
/*  47: 80 */     registerColumnType(-2, "binary");
/*  48: 81 */     registerColumnType(-7, "boolean");
/*  49: 82 */     registerColumnType(1, "char($l)");
/*  50: 83 */     registerColumnType(91, "date");
/*  51: 84 */     registerColumnType(3, "decimal($p,$s)");
/*  52: 85 */     registerColumnType(2, "decimal($p,$s)");
/*  53: 86 */     registerColumnType(8, "double");
/*  54: 87 */     registerColumnType(6, "float");
/*  55: 88 */     registerColumnType(4, "integer");
/*  56: 89 */     registerColumnType(-4, "longvarbinary");
/*  57: 90 */     registerColumnType(-1, "longvarchar");
/*  58: 91 */     registerColumnType(7, "real");
/*  59: 92 */     registerColumnType(5, "smallint");
/*  60: 93 */     registerColumnType(-6, "tinyint");
/*  61: 94 */     registerColumnType(92, "time");
/*  62: 95 */     registerColumnType(93, "timestamp");
/*  63: 96 */     registerColumnType(12, "varchar($l)");
/*  64: 97 */     registerColumnType(-3, "binary($l)");
/*  65: 98 */     registerColumnType(2004, "blob");
/*  66: 99 */     registerColumnType(2005, "clob");
/*  67:    */     
/*  68:    */ 
/*  69:102 */     registerFunction("avg", new AvgWithArgumentCastFunction("double"));
/*  70:    */     
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:110 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  78:111 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  79:112 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  80:113 */     registerFunction("atan2", new StandardSQLFunction("atan2", StandardBasicTypes.DOUBLE));
/*  81:114 */     registerFunction("bitand", new StandardSQLFunction("bitand", StandardBasicTypes.INTEGER));
/*  82:115 */     registerFunction("bitor", new StandardSQLFunction("bitor", StandardBasicTypes.INTEGER));
/*  83:116 */     registerFunction("bitxor", new StandardSQLFunction("bitxor", StandardBasicTypes.INTEGER));
/*  84:117 */     registerFunction("ceiling", new StandardSQLFunction("ceiling", StandardBasicTypes.DOUBLE));
/*  85:118 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  86:119 */     registerFunction("compress", new StandardSQLFunction("compress", StandardBasicTypes.BINARY));
/*  87:120 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/*  88:121 */     registerFunction("decrypt", new StandardSQLFunction("decrypt", StandardBasicTypes.BINARY));
/*  89:122 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/*  90:123 */     registerFunction("encrypt", new StandardSQLFunction("encrypt", StandardBasicTypes.BINARY));
/*  91:124 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  92:125 */     registerFunction("expand", new StandardSQLFunction("compress", StandardBasicTypes.BINARY));
/*  93:126 */     registerFunction("floor", new StandardSQLFunction("floor", StandardBasicTypes.DOUBLE));
/*  94:127 */     registerFunction("hash", new StandardSQLFunction("hash", StandardBasicTypes.BINARY));
/*  95:128 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  96:129 */     registerFunction("log10", new StandardSQLFunction("log10", StandardBasicTypes.DOUBLE));
/*  97:130 */     registerFunction("pi", new NoArgSQLFunction("pi", StandardBasicTypes.DOUBLE));
/*  98:131 */     registerFunction("power", new StandardSQLFunction("power", StandardBasicTypes.DOUBLE));
/*  99:132 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/* 100:133 */     registerFunction("rand", new NoArgSQLFunction("rand", StandardBasicTypes.DOUBLE));
/* 101:134 */     registerFunction("round", new StandardSQLFunction("round", StandardBasicTypes.DOUBLE));
/* 102:135 */     registerFunction("roundmagic", new StandardSQLFunction("roundmagic", StandardBasicTypes.DOUBLE));
/* 103:136 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/* 104:137 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/* 105:138 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/* 106:139 */     registerFunction("truncate", new StandardSQLFunction("truncate", StandardBasicTypes.DOUBLE));
/* 107:    */     
/* 108:    */ 
/* 109:142 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/* 110:143 */     registerFunction("char", new StandardSQLFunction("char", StandardBasicTypes.CHARACTER));
/* 111:144 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/* 112:145 */     registerFunction("difference", new StandardSQLFunction("difference", StandardBasicTypes.INTEGER));
/* 113:146 */     registerFunction("hextoraw", new StandardSQLFunction("hextoraw", StandardBasicTypes.STRING));
/* 114:147 */     registerFunction("insert", new StandardSQLFunction("lower", StandardBasicTypes.STRING));
/* 115:148 */     registerFunction("left", new StandardSQLFunction("left", StandardBasicTypes.STRING));
/* 116:149 */     registerFunction("lcase", new StandardSQLFunction("lcase", StandardBasicTypes.STRING));
/* 117:150 */     registerFunction("ltrim", new StandardSQLFunction("ltrim", StandardBasicTypes.STRING));
/* 118:151 */     registerFunction("octet_length", new StandardSQLFunction("octet_length", StandardBasicTypes.INTEGER));
/* 119:152 */     registerFunction("position", new StandardSQLFunction("position", StandardBasicTypes.INTEGER));
/* 120:153 */     registerFunction("rawtohex", new StandardSQLFunction("rawtohex", StandardBasicTypes.STRING));
/* 121:154 */     registerFunction("repeat", new StandardSQLFunction("repeat", StandardBasicTypes.STRING));
/* 122:155 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/* 123:156 */     registerFunction("right", new StandardSQLFunction("right", StandardBasicTypes.STRING));
/* 124:157 */     registerFunction("rtrim", new StandardSQLFunction("rtrim", StandardBasicTypes.STRING));
/* 125:158 */     registerFunction("soundex", new StandardSQLFunction("soundex", StandardBasicTypes.STRING));
/* 126:159 */     registerFunction("space", new StandardSQLFunction("space", StandardBasicTypes.STRING));
/* 127:160 */     registerFunction("stringencode", new StandardSQLFunction("stringencode", StandardBasicTypes.STRING));
/* 128:161 */     registerFunction("stringdecode", new StandardSQLFunction("stringdecode", StandardBasicTypes.STRING));
/* 129:162 */     registerFunction("stringtoutf8", new StandardSQLFunction("stringtoutf8", StandardBasicTypes.BINARY));
/* 130:163 */     registerFunction("ucase", new StandardSQLFunction("ucase", StandardBasicTypes.STRING));
/* 131:164 */     registerFunction("utf8tostring", new StandardSQLFunction("utf8tostring", StandardBasicTypes.STRING));
/* 132:    */     
/* 133:    */ 
/* 134:167 */     registerFunction("curdate", new NoArgSQLFunction("curdate", StandardBasicTypes.DATE));
/* 135:168 */     registerFunction("curtime", new NoArgSQLFunction("curtime", StandardBasicTypes.TIME));
/* 136:169 */     registerFunction("curtimestamp", new NoArgSQLFunction("curtimestamp", StandardBasicTypes.TIME));
/* 137:170 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE));
/* 138:171 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME));
/* 139:172 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP));
/* 140:173 */     registerFunction("datediff", new StandardSQLFunction("datediff", StandardBasicTypes.INTEGER));
/* 141:174 */     registerFunction("dayname", new StandardSQLFunction("dayname", StandardBasicTypes.STRING));
/* 142:175 */     registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", StandardBasicTypes.INTEGER));
/* 143:176 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/* 144:177 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/* 145:178 */     registerFunction("monthname", new StandardSQLFunction("monthname", StandardBasicTypes.STRING));
/* 146:179 */     registerFunction("now", new NoArgSQLFunction("now", StandardBasicTypes.TIMESTAMP));
/* 147:180 */     registerFunction("quarter", new StandardSQLFunction("quarter", StandardBasicTypes.INTEGER));
/* 148:181 */     registerFunction("week", new StandardSQLFunction("week", StandardBasicTypes.INTEGER));
/* 149:    */     
/* 150:    */ 
/* 151:184 */     registerFunction("database", new NoArgSQLFunction("database", StandardBasicTypes.STRING));
/* 152:185 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING));
/* 153:    */     
/* 154:187 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 155:188 */     getDefaultProperties().setProperty("hibernate.jdbc.lob.non_contextual_creation", "true");
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String getAddColumnString()
/* 159:    */   {
/* 160:192 */     return "add column";
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean supportsIdentityColumns()
/* 164:    */   {
/* 165:196 */     return true;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public String getIdentityColumnString()
/* 169:    */   {
/* 170:200 */     return "generated by default as identity";
/* 171:    */   }
/* 172:    */   
/* 173:    */   public String getIdentitySelectString()
/* 174:    */   {
/* 175:204 */     return "call identity()";
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String getIdentityInsertString()
/* 179:    */   {
/* 180:208 */     return "null";
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String getForUpdateString()
/* 184:    */   {
/* 185:212 */     return " for update";
/* 186:    */   }
/* 187:    */   
/* 188:    */   public boolean supportsUnique()
/* 189:    */   {
/* 190:216 */     return true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean supportsLimit()
/* 194:    */   {
/* 195:220 */     return true;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getLimitString(String sql, boolean hasOffset)
/* 199:    */   {
/* 200:224 */     return sql.length() + 20 + sql + (hasOffset ? " limit ? offset ?" : " limit ?");
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean bindLimitParametersInReverseOrder()
/* 204:    */   {
/* 205:231 */     return true;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public boolean bindLimitParametersFirst()
/* 209:    */   {
/* 210:235 */     return false;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean supportsIfExistsAfterTableName()
/* 214:    */   {
/* 215:239 */     return true;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public boolean supportsSequences()
/* 219:    */   {
/* 220:243 */     return true;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public boolean supportsPooledSequences()
/* 224:    */   {
/* 225:247 */     return true;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public String getCreateSequenceString(String sequenceName)
/* 229:    */   {
/* 230:251 */     return "create sequence " + sequenceName;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public String getDropSequenceString(String sequenceName)
/* 234:    */   {
/* 235:255 */     return "drop sequence " + sequenceName;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 239:    */   {
/* 240:259 */     return "next value for " + sequenceName;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public String getSequenceNextValString(String sequenceName)
/* 244:    */   {
/* 245:263 */     return "call next value for " + sequenceName;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public String getQuerySequencesString()
/* 249:    */   {
/* 250:267 */     return this.querySequenceString;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/* 254:    */   {
/* 255:271 */     return EXTRACTER;
/* 256:    */   }
/* 257:    */   
/* 258:274 */   private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter()
/* 259:    */   {
/* 260:    */     public String extractConstraintName(SQLException sqle)
/* 261:    */     {
/* 262:282 */       String constraintName = null;
/* 263:285 */       if (sqle.getSQLState().startsWith("23"))
/* 264:    */       {
/* 265:286 */         String message = sqle.getMessage();
/* 266:287 */         int idx = message.indexOf("violation: ");
/* 267:288 */         if (idx > 0) {
/* 268:289 */           constraintName = message.substring(idx + "violation: ".length());
/* 269:    */         }
/* 270:    */       }
/* 271:292 */       return constraintName;
/* 272:    */     }
/* 273:    */   };
/* 274:    */   
/* 275:    */   public boolean supportsTemporaryTables()
/* 276:    */   {
/* 277:298 */     return true;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public String getCreateTemporaryTableString()
/* 281:    */   {
/* 282:303 */     return "create cached local temporary table if not exists";
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String getCreateTemporaryTablePostfix()
/* 286:    */   {
/* 287:311 */     return "on commit drop transactional";
/* 288:    */   }
/* 289:    */   
/* 290:    */   public Boolean performTemporaryTableDDLInIsolation()
/* 291:    */   {
/* 292:317 */     return Boolean.FALSE;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public boolean dropTemporaryTableAfterUse()
/* 296:    */   {
/* 297:322 */     return false;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public boolean supportsCurrentTimestampSelection()
/* 301:    */   {
/* 302:326 */     return true;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 306:    */   {
/* 307:330 */     return false;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String getCurrentTimestampSelectString()
/* 311:    */   {
/* 312:334 */     return "call current_timestamp()";
/* 313:    */   }
/* 314:    */   
/* 315:    */   public boolean supportsUnionAll()
/* 316:    */   {
/* 317:338 */     return true;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public boolean supportsLobValueChangePropogation()
/* 321:    */   {
/* 322:346 */     return false;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public boolean supportsTupleDistinctCounts()
/* 326:    */   {
/* 327:351 */     return false;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 331:    */   {
/* 332:357 */     return true;
/* 333:    */   }
/* 334:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.H2Dialect
 * JD-Core Version:    0.7.0.1
 */