/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   5:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   6:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   7:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   8:    */ import org.hibernate.type.StandardBasicTypes;
/*   9:    */ 
/*  10:    */ public class IngresDialect
/*  11:    */   extends Dialect
/*  12:    */ {
/*  13:    */   public IngresDialect()
/*  14:    */   {
/*  15: 54 */     registerColumnType(-7, "tinyint");
/*  16: 55 */     registerColumnType(-6, "tinyint");
/*  17: 56 */     registerColumnType(5, "smallint");
/*  18: 57 */     registerColumnType(4, "integer");
/*  19: 58 */     registerColumnType(-5, "bigint");
/*  20: 59 */     registerColumnType(7, "real");
/*  21: 60 */     registerColumnType(6, "float");
/*  22: 61 */     registerColumnType(8, "float");
/*  23: 62 */     registerColumnType(2, "decimal($p, $s)");
/*  24: 63 */     registerColumnType(3, "decimal($p, $s)");
/*  25: 64 */     registerColumnType(-2, 32000L, "byte($l)");
/*  26: 65 */     registerColumnType(-2, "long byte");
/*  27: 66 */     registerColumnType(-3, 32000L, "varbyte($l)");
/*  28: 67 */     registerColumnType(-3, "long byte");
/*  29: 68 */     registerColumnType(-4, "long byte");
/*  30: 69 */     registerColumnType(1, 32000L, "char($l)");
/*  31: 70 */     registerColumnType(12, 32000L, "varchar($l)");
/*  32: 71 */     registerColumnType(12, "long varchar");
/*  33: 72 */     registerColumnType(-1, "long varchar");
/*  34: 73 */     registerColumnType(91, "date");
/*  35: 74 */     registerColumnType(92, "time with time zone");
/*  36: 75 */     registerColumnType(93, "timestamp with time zone");
/*  37: 76 */     registerColumnType(2004, "blob");
/*  38: 77 */     registerColumnType(2005, "clob");
/*  39:    */     
/*  40: 79 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  41: 80 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  42: 81 */     registerFunction("bit_add", new StandardSQLFunction("bit_add"));
/*  43: 82 */     registerFunction("bit_and", new StandardSQLFunction("bit_and"));
/*  44: 83 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "octet_length(hex(?1))*4"));
/*  45: 84 */     registerFunction("bit_not", new StandardSQLFunction("bit_not"));
/*  46: 85 */     registerFunction("bit_or", new StandardSQLFunction("bit_or"));
/*  47: 86 */     registerFunction("bit_xor", new StandardSQLFunction("bit_xor"));
/*  48: 87 */     registerFunction("character_length", new StandardSQLFunction("character_length", StandardBasicTypes.LONG));
/*  49: 88 */     registerFunction("charextract", new StandardSQLFunction("charextract", StandardBasicTypes.STRING));
/*  50: 89 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "+", ")"));
/*  51: 90 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  52: 91 */     registerFunction("current_user", new NoArgSQLFunction("current_user", StandardBasicTypes.STRING, false));
/*  53: 92 */     registerFunction("current_time", new NoArgSQLFunction("date('now')", StandardBasicTypes.TIMESTAMP, false));
/*  54: 93 */     registerFunction("current_timestamp", new NoArgSQLFunction("date('now')", StandardBasicTypes.TIMESTAMP, false));
/*  55: 94 */     registerFunction("current_date", new NoArgSQLFunction("date('now')", StandardBasicTypes.TIMESTAMP, false));
/*  56: 95 */     registerFunction("date_trunc", new StandardSQLFunction("date_trunc", StandardBasicTypes.TIMESTAMP));
/*  57: 96 */     registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
/*  58: 97 */     registerFunction("dba", new NoArgSQLFunction("dba", StandardBasicTypes.STRING, true));
/*  59: 98 */     registerFunction("dow", new StandardSQLFunction("dow", StandardBasicTypes.STRING));
/*  60: 99 */     registerFunction("extract", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "date_part('?1', ?3)"));
/*  61:100 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  62:101 */     registerFunction("gmt_timestamp", new StandardSQLFunction("gmt_timestamp", StandardBasicTypes.STRING));
/*  63:102 */     registerFunction("hash", new StandardSQLFunction("hash", StandardBasicTypes.INTEGER));
/*  64:103 */     registerFunction("hex", new StandardSQLFunction("hex", StandardBasicTypes.STRING));
/*  65:104 */     registerFunction("hour", new StandardSQLFunction("hour", StandardBasicTypes.INTEGER));
/*  66:105 */     registerFunction("initial_user", new NoArgSQLFunction("initial_user", StandardBasicTypes.STRING, false));
/*  67:106 */     registerFunction("intextract", new StandardSQLFunction("intextract", StandardBasicTypes.INTEGER));
/*  68:107 */     registerFunction("left", new StandardSQLFunction("left", StandardBasicTypes.STRING));
/*  69:108 */     registerFunction("locate", new SQLFunctionTemplate(StandardBasicTypes.LONG, "locate(?1, ?2)"));
/*  70:109 */     registerFunction("length", new StandardSQLFunction("length", StandardBasicTypes.LONG));
/*  71:110 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  72:111 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  73:112 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  74:113 */     registerFunction("lowercase", new StandardSQLFunction("lowercase"));
/*  75:114 */     registerFunction("minute", new StandardSQLFunction("minute", StandardBasicTypes.INTEGER));
/*  76:115 */     registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
/*  77:116 */     registerFunction("octet_length", new StandardSQLFunction("octet_length", StandardBasicTypes.LONG));
/*  78:117 */     registerFunction("pad", new StandardSQLFunction("pad", StandardBasicTypes.STRING));
/*  79:118 */     registerFunction("position", new StandardSQLFunction("position", StandardBasicTypes.LONG));
/*  80:119 */     registerFunction("power", new StandardSQLFunction("power", StandardBasicTypes.DOUBLE));
/*  81:120 */     registerFunction("random", new NoArgSQLFunction("random", StandardBasicTypes.LONG, true));
/*  82:121 */     registerFunction("randomf", new NoArgSQLFunction("randomf", StandardBasicTypes.DOUBLE, true));
/*  83:122 */     registerFunction("right", new StandardSQLFunction("right", StandardBasicTypes.STRING));
/*  84:123 */     registerFunction("session_user", new NoArgSQLFunction("session_user", StandardBasicTypes.STRING, false));
/*  85:124 */     registerFunction("second", new StandardSQLFunction("second", StandardBasicTypes.INTEGER));
/*  86:125 */     registerFunction("size", new NoArgSQLFunction("size", StandardBasicTypes.LONG, true));
/*  87:126 */     registerFunction("squeeze", new StandardSQLFunction("squeeze"));
/*  88:127 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  89:128 */     registerFunction("soundex", new StandardSQLFunction("soundex", StandardBasicTypes.STRING));
/*  90:129 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  91:130 */     registerFunction("substring", new SQLFunctionTemplate(StandardBasicTypes.STRING, "substring(?1 FROM ?2 FOR ?3)"));
/*  92:131 */     registerFunction("system_user", new NoArgSQLFunction("system_user", StandardBasicTypes.STRING, false));
/*  93:    */     
/*  94:133 */     registerFunction("unhex", new StandardSQLFunction("unhex", StandardBasicTypes.STRING));
/*  95:134 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  96:135 */     registerFunction("uppercase", new StandardSQLFunction("uppercase"));
/*  97:136 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING, false));
/*  98:137 */     registerFunction("usercode", new NoArgSQLFunction("usercode", StandardBasicTypes.STRING, true));
/*  99:138 */     registerFunction("username", new NoArgSQLFunction("username", StandardBasicTypes.STRING, true));
/* 100:139 */     registerFunction("uuid_create", new StandardSQLFunction("uuid_create", StandardBasicTypes.BYTE));
/* 101:140 */     registerFunction("uuid_compare", new StandardSQLFunction("uuid_compare", StandardBasicTypes.INTEGER));
/* 102:141 */     registerFunction("uuid_from_char", new StandardSQLFunction("uuid_from_char", StandardBasicTypes.BYTE));
/* 103:142 */     registerFunction("uuid_to_char", new StandardSQLFunction("uuid_to_char", StandardBasicTypes.STRING));
/* 104:143 */     registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:147 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as varchar)"));
/* 109:    */     
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:158 */     getDefaultProperties().setProperty("hibernate.jdbc.use_get_generated_keys", "false");
/* 120:    */     
/* 121:    */ 
/* 122:    */ 
/* 123:162 */     getDefaultProperties().setProperty("hibernate.query.substitutions", "true=1,false=0");
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getSelectGUIDString()
/* 127:    */   {
/* 128:168 */     return "select uuid_to_char(uuid_create())";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean dropConstraints()
/* 132:    */   {
/* 133:176 */     return false;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean supportsForUpdateOf()
/* 137:    */   {
/* 138:186 */     return true;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String getAddColumnString()
/* 142:    */   {
/* 143:193 */     return "add column";
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getNullColumnString()
/* 147:    */   {
/* 148:202 */     return " with null";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public boolean supportsSequences()
/* 152:    */   {
/* 153:211 */     return true;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getSequenceNextValString(String sequenceName)
/* 157:    */   {
/* 158:222 */     return "select nextval for " + sequenceName;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 162:    */   {
/* 163:226 */     return sequenceName + ".nextval";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getCreateSequenceString(String sequenceName)
/* 167:    */   {
/* 168:237 */     return "create sequence " + sequenceName;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String getDropSequenceString(String sequenceName)
/* 172:    */   {
/* 173:248 */     return "drop sequence " + sequenceName + " restrict";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getQuerySequencesString()
/* 177:    */   {
/* 178:255 */     return "select seq_name from iisequence";
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String getLowercaseFunction()
/* 182:    */   {
/* 183:265 */     return "lowercase";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean supportsLimit()
/* 187:    */   {
/* 188:272 */     return true;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean supportsLimitOffset()
/* 192:    */   {
/* 193:279 */     return false;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public String getLimitString(String querySelect, int offset, int limit)
/* 197:    */   {
/* 198:288 */     if (offset > 0) {
/* 199:289 */       throw new UnsupportedOperationException("query result offset is not supported");
/* 200:    */     }
/* 201:291 */     return new StringBuffer(querySelect.length() + 16).append(querySelect).insert(6, " first " + limit).toString();
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean supportsVariableLimit()
/* 205:    */   {
/* 206:298 */     return false;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean useMaxForLimit()
/* 210:    */   {
/* 211:306 */     return true;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public boolean supportsNotNullUnique()
/* 215:    */   {
/* 216:313 */     return false;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean supportsTemporaryTables()
/* 220:    */   {
/* 221:320 */     return true;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public String getCreateTemporaryTableString()
/* 225:    */   {
/* 226:324 */     return "declare global temporary table";
/* 227:    */   }
/* 228:    */   
/* 229:    */   public String getCreateTemporaryTablePostfix()
/* 230:    */   {
/* 231:328 */     return "on commit preserve rows with norecovery";
/* 232:    */   }
/* 233:    */   
/* 234:    */   public String generateTemporaryTableName(String baseTableName)
/* 235:    */   {
/* 236:332 */     return "session." + super.generateTemporaryTableName(baseTableName);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String getCurrentTimestampSQLFunctionName()
/* 240:    */   {
/* 241:340 */     return "date(now)";
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean supportsSubselectAsInPredicateLHS()
/* 245:    */   {
/* 246:346 */     return false;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public boolean supportsEmptyInList()
/* 250:    */   {
/* 251:350 */     return false;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public boolean supportsExpectedLobUsagePattern()
/* 255:    */   {
/* 256:354 */     return false;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean supportsTupleDistinctCounts()
/* 260:    */   {
/* 261:363 */     return false;
/* 262:    */   }
/* 263:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.IngresDialect
 * JD-Core Version:    0.7.0.1
 */