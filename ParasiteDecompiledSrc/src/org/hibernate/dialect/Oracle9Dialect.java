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
/*  16:    */ import org.hibernate.internal.CoreMessageLogger;
/*  17:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*  18:    */ import org.hibernate.internal.util.ReflectHelper;
/*  19:    */ import org.hibernate.type.StandardBasicTypes;
/*  20:    */ import org.jboss.logging.Logger;
/*  21:    */ 
/*  22:    */ @Deprecated
/*  23:    */ public class Oracle9Dialect
/*  24:    */   extends Dialect
/*  25:    */ {
/*  26: 56 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Oracle9Dialect.class.getName());
/*  27:    */   
/*  28:    */   public Oracle9Dialect()
/*  29:    */   {
/*  30: 60 */     LOG.deprecatedOracle9Dialect();
/*  31: 61 */     registerColumnType(-7, "number(1,0)");
/*  32: 62 */     registerColumnType(-5, "number(19,0)");
/*  33: 63 */     registerColumnType(5, "number(5,0)");
/*  34: 64 */     registerColumnType(-6, "number(3,0)");
/*  35: 65 */     registerColumnType(4, "number(10,0)");
/*  36: 66 */     registerColumnType(1, "char(1 char)");
/*  37: 67 */     registerColumnType(12, 4000L, "varchar2($l char)");
/*  38: 68 */     registerColumnType(12, "long");
/*  39: 69 */     registerColumnType(6, "float");
/*  40: 70 */     registerColumnType(8, "double precision");
/*  41: 71 */     registerColumnType(91, "date");
/*  42: 72 */     registerColumnType(92, "date");
/*  43: 73 */     registerColumnType(93, "timestamp");
/*  44: 74 */     registerColumnType(-3, 2000L, "raw($l)");
/*  45: 75 */     registerColumnType(-3, "long raw");
/*  46: 76 */     registerColumnType(2, "number($p,$s)");
/*  47: 77 */     registerColumnType(3, "number($p,$s)");
/*  48: 78 */     registerColumnType(2004, "blob");
/*  49: 79 */     registerColumnType(2005, "clob");
/*  50:    */     
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55: 85 */     getDefaultProperties().setProperty("hibernate.jdbc.use_get_generated_keys", "false");
/*  56: 86 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "true");
/*  57: 87 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/*  58:    */     
/*  59: 89 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  60: 90 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  61:    */     
/*  62: 92 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  63: 93 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  64: 94 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  65: 95 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  66: 96 */     registerFunction("cosh", new StandardSQLFunction("cosh", StandardBasicTypes.DOUBLE));
/*  67: 97 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  68: 98 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  69: 99 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  70:100 */     registerFunction("sinh", new StandardSQLFunction("sinh", StandardBasicTypes.DOUBLE));
/*  71:101 */     registerFunction("stddev", new StandardSQLFunction("stddev", StandardBasicTypes.DOUBLE));
/*  72:102 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  73:103 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  74:104 */     registerFunction("tanh", new StandardSQLFunction("tanh", StandardBasicTypes.DOUBLE));
/*  75:105 */     registerFunction("variance", new StandardSQLFunction("variance", StandardBasicTypes.DOUBLE));
/*  76:    */     
/*  77:107 */     registerFunction("round", new StandardSQLFunction("round"));
/*  78:108 */     registerFunction("trunc", new StandardSQLFunction("trunc"));
/*  79:109 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/*  80:110 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  81:    */     
/*  82:112 */     registerFunction("chr", new StandardSQLFunction("chr", StandardBasicTypes.CHARACTER));
/*  83:113 */     registerFunction("initcap", new StandardSQLFunction("initcap"));
/*  84:114 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  85:115 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/*  86:116 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  87:117 */     registerFunction("soundex", new StandardSQLFunction("soundex"));
/*  88:118 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  89:119 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/*  90:    */     
/*  91:121 */     registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/*  92:122 */     registerFunction("to_date", new StandardSQLFunction("to_date", StandardBasicTypes.TIMESTAMP));
/*  93:    */     
/*  94:124 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  95:125 */     registerFunction("current_time", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIME, false));
/*  96:126 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  97:    */     
/*  98:128 */     registerFunction("last_day", new StandardSQLFunction("last_day", StandardBasicTypes.DATE));
/*  99:129 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.DATE, false));
/* 100:130 */     registerFunction("systimestamp", new NoArgSQLFunction("systimestamp", StandardBasicTypes.TIMESTAMP, false));
/* 101:131 */     registerFunction("uid", new NoArgSQLFunction("uid", StandardBasicTypes.INTEGER, false));
/* 102:132 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING, false));
/* 103:    */     
/* 104:134 */     registerFunction("rowid", new NoArgSQLFunction("rowid", StandardBasicTypes.LONG, false));
/* 105:135 */     registerFunction("rownum", new NoArgSQLFunction("rownum", StandardBasicTypes.LONG, false));
/* 106:    */     
/* 107:    */ 
/* 108:138 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
/* 109:139 */     registerFunction("instr", new StandardSQLFunction("instr", StandardBasicTypes.INTEGER));
/* 110:140 */     registerFunction("instrb", new StandardSQLFunction("instrb", StandardBasicTypes.INTEGER));
/* 111:141 */     registerFunction("lpad", new StandardSQLFunction("lpad", StandardBasicTypes.STRING));
/* 112:142 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/* 113:143 */     registerFunction("rpad", new StandardSQLFunction("rpad", StandardBasicTypes.STRING));
/* 114:144 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 115:145 */     registerFunction("substrb", new StandardSQLFunction("substrb", StandardBasicTypes.STRING));
/* 116:146 */     registerFunction("translate", new StandardSQLFunction("translate", StandardBasicTypes.STRING));
/* 117:    */     
/* 118:148 */     registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 119:149 */     registerFunction("locate", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "instr(?2,?1)"));
/* 120:150 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "vsize(?1)*8"));
/* 121:151 */     registerFunction("coalesce", new NvlFunction());
/* 122:    */     
/* 123:    */ 
/* 124:154 */     registerFunction("atan2", new StandardSQLFunction("atan2", StandardBasicTypes.FLOAT));
/* 125:155 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.INTEGER));
/* 126:156 */     registerFunction("mod", new StandardSQLFunction("mod", StandardBasicTypes.INTEGER));
/* 127:157 */     registerFunction("nvl", new StandardSQLFunction("nvl"));
/* 128:158 */     registerFunction("nvl2", new StandardSQLFunction("nvl2"));
/* 129:159 */     registerFunction("power", new StandardSQLFunction("power", StandardBasicTypes.FLOAT));
/* 130:    */     
/* 131:    */ 
/* 132:162 */     registerFunction("add_months", new StandardSQLFunction("add_months", StandardBasicTypes.DATE));
/* 133:163 */     registerFunction("months_between", new StandardSQLFunction("months_between", StandardBasicTypes.FLOAT));
/* 134:164 */     registerFunction("next_day", new StandardSQLFunction("next_day", StandardBasicTypes.DATE));
/* 135:    */     
/* 136:166 */     registerFunction("str", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getAddColumnString()
/* 140:    */   {
/* 141:170 */     return "add";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getSequenceNextValString(String sequenceName)
/* 145:    */   {
/* 146:174 */     return "select " + getSelectSequenceNextValString(sequenceName) + " from dual";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 150:    */   {
/* 151:178 */     return sequenceName + ".nextval";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String getCreateSequenceString(String sequenceName)
/* 155:    */   {
/* 156:182 */     return "create sequence " + sequenceName;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String getDropSequenceString(String sequenceName)
/* 160:    */   {
/* 161:186 */     return "drop sequence " + sequenceName;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getCascadeConstraintsString()
/* 165:    */   {
/* 166:190 */     return " cascade constraints";
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean dropConstraints()
/* 170:    */   {
/* 171:194 */     return false;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getForUpdateNowaitString()
/* 175:    */   {
/* 176:198 */     return " for update nowait";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean supportsSequences()
/* 180:    */   {
/* 181:202 */     return true;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean supportsPooledSequences()
/* 185:    */   {
/* 186:206 */     return true;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean supportsLimit()
/* 190:    */   {
/* 191:210 */     return true;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String getLimitString(String sql, boolean hasOffset)
/* 195:    */   {
/* 196:215 */     sql = sql.trim();
/* 197:216 */     boolean isForUpdate = false;
/* 198:217 */     if (sql.toLowerCase().endsWith(" for update"))
/* 199:    */     {
/* 200:218 */       sql = sql.substring(0, sql.length() - 11);
/* 201:219 */       isForUpdate = true;
/* 202:    */     }
/* 203:222 */     StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
/* 204:223 */     if (hasOffset) {
/* 205:224 */       pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
/* 206:    */     } else {
/* 207:227 */       pagingSelect.append("select * from ( ");
/* 208:    */     }
/* 209:229 */     pagingSelect.append(sql);
/* 210:230 */     if (hasOffset) {
/* 211:231 */       pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
/* 212:    */     } else {
/* 213:234 */       pagingSelect.append(" ) where rownum <= ?");
/* 214:    */     }
/* 215:237 */     if (isForUpdate) {
/* 216:238 */       pagingSelect.append(" for update");
/* 217:    */     }
/* 218:241 */     return pagingSelect.toString();
/* 219:    */   }
/* 220:    */   
/* 221:    */   public String getForUpdateString(String aliases)
/* 222:    */   {
/* 223:245 */     return getForUpdateString() + " of " + aliases;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public String getForUpdateNowaitString(String aliases)
/* 227:    */   {
/* 228:249 */     return getForUpdateString() + " of " + aliases + " nowait";
/* 229:    */   }
/* 230:    */   
/* 231:    */   public boolean bindLimitParametersInReverseOrder()
/* 232:    */   {
/* 233:253 */     return true;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean useMaxForLimit()
/* 237:    */   {
/* 238:257 */     return true;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public boolean forUpdateOfColumns()
/* 242:    */   {
/* 243:261 */     return true;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public String getQuerySequencesString()
/* 247:    */   {
/* 248:265 */     return "select sequence_name from user_sequences";
/* 249:    */   }
/* 250:    */   
/* 251:    */   public String getSelectGUIDString()
/* 252:    */   {
/* 253:269 */     return "select rawtohex(sys_guid()) from dual";
/* 254:    */   }
/* 255:    */   
/* 256:    */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/* 257:    */   {
/* 258:273 */     return EXTRACTER;
/* 259:    */   }
/* 260:    */   
/* 261:276 */   private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter()
/* 262:    */   {
/* 263:    */     public String extractConstraintName(SQLException sqle)
/* 264:    */     {
/* 265:285 */       int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
/* 266:286 */       if ((errorCode == 1) || (errorCode == 2291) || (errorCode == 2292)) {
/* 267:287 */         return extractUsingTemplate("constraint (", ") violated", sqle.getMessage());
/* 268:    */       }
/* 269:289 */       if (errorCode == 1400) {
/* 270:291 */         return null;
/* 271:    */       }
/* 272:294 */       return null;
/* 273:    */     }
/* 274:    */   };
/* 275:301 */   int oracletypes_cursor_value = 0;
/* 276:    */   
/* 277:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 278:    */     throws SQLException
/* 279:    */   {
/* 280:303 */     if (this.oracletypes_cursor_value == 0) {
/* 281:    */       try
/* 282:    */       {
/* 283:305 */         Class types = ReflectHelper.classForName("oracle.jdbc.driver.OracleTypes");
/* 284:306 */         this.oracletypes_cursor_value = types.getField("CURSOR").getInt(types.newInstance());
/* 285:    */       }
/* 286:    */       catch (Exception se)
/* 287:    */       {
/* 288:308 */         throw new HibernateException("Problem while trying to load or access OracleTypes.CURSOR value", se);
/* 289:    */       }
/* 290:    */     }
/* 291:312 */     statement.registerOutParameter(col, this.oracletypes_cursor_value);
/* 292:313 */     col++;
/* 293:314 */     return col;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public ResultSet getResultSet(CallableStatement ps)
/* 297:    */     throws SQLException
/* 298:    */   {
/* 299:318 */     ps.execute();
/* 300:319 */     return (ResultSet)ps.getObject(1);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean supportsUnionAll()
/* 304:    */   {
/* 305:323 */     return true;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public boolean supportsCommentOn()
/* 309:    */   {
/* 310:327 */     return true;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public boolean supportsTemporaryTables()
/* 314:    */   {
/* 315:331 */     return true;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public String generateTemporaryTableName(String baseTableName)
/* 319:    */   {
/* 320:335 */     String name = super.generateTemporaryTableName(baseTableName);
/* 321:336 */     return name.length() > 30 ? name.substring(1, 30) : name;
/* 322:    */   }
/* 323:    */   
/* 324:    */   public String getCreateTemporaryTableString()
/* 325:    */   {
/* 326:340 */     return "create global temporary table";
/* 327:    */   }
/* 328:    */   
/* 329:    */   public String getCreateTemporaryTablePostfix()
/* 330:    */   {
/* 331:344 */     return "on commit delete rows";
/* 332:    */   }
/* 333:    */   
/* 334:    */   public boolean dropTemporaryTableAfterUse()
/* 335:    */   {
/* 336:348 */     return false;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public boolean supportsCurrentTimestampSelection()
/* 340:    */   {
/* 341:352 */     return true;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public String getCurrentTimestampSelectString()
/* 345:    */   {
/* 346:356 */     return "select systimestamp from dual";
/* 347:    */   }
/* 348:    */   
/* 349:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 350:    */   {
/* 351:360 */     return false;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public boolean supportsEmptyInList()
/* 355:    */   {
/* 356:367 */     return false;
/* 357:    */   }
/* 358:    */   
/* 359:    */   public boolean supportsExistsInSelect()
/* 360:    */   {
/* 361:371 */     return false;
/* 362:    */   }
/* 363:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.Oracle9Dialect
 * JD-Core Version:    0.7.0.1
 */