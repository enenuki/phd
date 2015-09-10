/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.sql.CallableStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Properties;
/*  10:    */ import org.hibernate.LockMode;
/*  11:    */ import org.hibernate.LockOptions;
/*  12:    */ import org.hibernate.dialect.function.CharIndexFunction;
/*  13:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*  14:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*  15:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*  16:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*  17:    */ import org.hibernate.type.StandardBasicTypes;
/*  18:    */ 
/*  19:    */ abstract class AbstractTransactSQLDialect
/*  20:    */   extends Dialect
/*  21:    */ {
/*  22:    */   public AbstractTransactSQLDialect()
/*  23:    */   {
/*  24: 50 */     registerColumnType(-2, "binary($l)");
/*  25: 51 */     registerColumnType(-7, "tinyint");
/*  26: 52 */     registerColumnType(-5, "numeric(19,0)");
/*  27: 53 */     registerColumnType(5, "smallint");
/*  28: 54 */     registerColumnType(-6, "smallint");
/*  29: 55 */     registerColumnType(4, "int");
/*  30: 56 */     registerColumnType(1, "char(1)");
/*  31: 57 */     registerColumnType(12, "varchar($l)");
/*  32: 58 */     registerColumnType(6, "float");
/*  33: 59 */     registerColumnType(8, "double precision");
/*  34: 60 */     registerColumnType(91, "datetime");
/*  35: 61 */     registerColumnType(92, "datetime");
/*  36: 62 */     registerColumnType(93, "datetime");
/*  37: 63 */     registerColumnType(-3, "varbinary($l)");
/*  38: 64 */     registerColumnType(2, "numeric($p,$s)");
/*  39: 65 */     registerColumnType(2004, "image");
/*  40: 66 */     registerColumnType(2005, "text");
/*  41:    */     
/*  42: 68 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.INTEGER));
/*  43: 69 */     registerFunction("char", new StandardSQLFunction("char", StandardBasicTypes.CHARACTER));
/*  44: 70 */     registerFunction("len", new StandardSQLFunction("len", StandardBasicTypes.LONG));
/*  45: 71 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  46: 72 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  47: 73 */     registerFunction("str", new StandardSQLFunction("str", StandardBasicTypes.STRING));
/*  48: 74 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/*  49: 75 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  50: 76 */     registerFunction("reverse", new StandardSQLFunction("reverse"));
/*  51: 77 */     registerFunction("space", new StandardSQLFunction("space", StandardBasicTypes.STRING));
/*  52:    */     
/*  53: 79 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING));
/*  54:    */     
/*  55: 81 */     registerFunction("current_timestamp", new NoArgSQLFunction("getdate", StandardBasicTypes.TIMESTAMP));
/*  56: 82 */     registerFunction("current_time", new NoArgSQLFunction("getdate", StandardBasicTypes.TIME));
/*  57: 83 */     registerFunction("current_date", new NoArgSQLFunction("getdate", StandardBasicTypes.DATE));
/*  58:    */     
/*  59: 85 */     registerFunction("getdate", new NoArgSQLFunction("getdate", StandardBasicTypes.TIMESTAMP));
/*  60: 86 */     registerFunction("getutcdate", new NoArgSQLFunction("getutcdate", StandardBasicTypes.TIMESTAMP));
/*  61: 87 */     registerFunction("day", new StandardSQLFunction("day", StandardBasicTypes.INTEGER));
/*  62: 88 */     registerFunction("month", new StandardSQLFunction("month", StandardBasicTypes.INTEGER));
/*  63: 89 */     registerFunction("year", new StandardSQLFunction("year", StandardBasicTypes.INTEGER));
/*  64: 90 */     registerFunction("datename", new StandardSQLFunction("datename", StandardBasicTypes.STRING));
/*  65:    */     
/*  66: 92 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  67: 93 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  68:    */     
/*  69: 95 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  70: 96 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  71: 97 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  72: 98 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  73: 99 */     registerFunction("cot", new StandardSQLFunction("cot", StandardBasicTypes.DOUBLE));
/*  74:100 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  75:101 */     registerFunction("log", new StandardSQLFunction("log", StandardBasicTypes.DOUBLE));
/*  76:102 */     registerFunction("log10", new StandardSQLFunction("log10", StandardBasicTypes.DOUBLE));
/*  77:103 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  78:104 */     registerFunction("sqrt", new StandardSQLFunction("sqrt", StandardBasicTypes.DOUBLE));
/*  79:105 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  80:106 */     registerFunction("pi", new NoArgSQLFunction("pi", StandardBasicTypes.DOUBLE));
/*  81:107 */     registerFunction("square", new StandardSQLFunction("square"));
/*  82:108 */     registerFunction("rand", new StandardSQLFunction("rand", StandardBasicTypes.FLOAT));
/*  83:    */     
/*  84:110 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/*  85:111 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/*  86:    */     
/*  87:113 */     registerFunction("round", new StandardSQLFunction("round"));
/*  88:114 */     registerFunction("ceiling", new StandardSQLFunction("ceiling"));
/*  89:115 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  90:    */     
/*  91:117 */     registerFunction("isnull", new StandardSQLFunction("isnull"));
/*  92:    */     
/*  93:119 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "+", ")"));
/*  94:    */     
/*  95:121 */     registerFunction("length", new StandardSQLFunction("len", StandardBasicTypes.INTEGER));
/*  96:122 */     registerFunction("trim", new SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(rtrim(?1))"));
/*  97:123 */     registerFunction("locate", new CharIndexFunction());
/*  98:    */     
/*  99:125 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "0");
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getAddColumnString()
/* 103:    */   {
/* 104:129 */     return "add";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getNullColumnString()
/* 108:    */   {
/* 109:132 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean qualifyIndexName()
/* 113:    */   {
/* 114:135 */     return false;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getForUpdateString()
/* 118:    */   {
/* 119:139 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean supportsIdentityColumns()
/* 123:    */   {
/* 124:143 */     return true;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getIdentitySelectString()
/* 128:    */   {
/* 129:146 */     return "select @@identity";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getIdentityColumnString()
/* 133:    */   {
/* 134:149 */     return "identity not null";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean supportsInsertSelectIdentity()
/* 138:    */   {
/* 139:153 */     return true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String appendIdentitySelectToInsert(String insertSQL)
/* 143:    */   {
/* 144:157 */     return insertSQL + "\nselect @@identity";
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String appendLockHint(LockMode mode, String tableName)
/* 148:    */   {
/* 149:161 */     if (mode.greaterThan(LockMode.READ)) {
/* 150:162 */       return tableName + " holdlock";
/* 151:    */     }
/* 152:165 */     return tableName;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String applyLocksToSql(String sql, LockOptions aliasedLockOptions, Map keyColumnNames)
/* 156:    */   {
/* 157:171 */     Iterator itr = aliasedLockOptions.getAliasLockIterator();
/* 158:172 */     StringBuffer buffer = new StringBuffer(sql);
/* 159:173 */     int correction = 0;
/* 160:174 */     while (itr.hasNext())
/* 161:    */     {
/* 162:175 */       Map.Entry entry = (Map.Entry)itr.next();
/* 163:176 */       LockMode lockMode = (LockMode)entry.getValue();
/* 164:177 */       if (lockMode.greaterThan(LockMode.READ))
/* 165:    */       {
/* 166:178 */         String alias = (String)entry.getKey();
/* 167:179 */         int start = -1;int end = -1;
/* 168:180 */         if (sql.endsWith(" " + alias))
/* 169:    */         {
/* 170:181 */           start = sql.length() - alias.length() + correction;
/* 171:182 */           end = start + alias.length();
/* 172:    */         }
/* 173:    */         else
/* 174:    */         {
/* 175:185 */           int position = sql.indexOf(" " + alias + " ");
/* 176:186 */           if (position <= -1) {
/* 177:187 */             position = sql.indexOf(" " + alias + ",");
/* 178:    */           }
/* 179:189 */           if (position > -1)
/* 180:    */           {
/* 181:190 */             start = position + correction + 1;
/* 182:191 */             end = start + alias.length();
/* 183:    */           }
/* 184:    */         }
/* 185:195 */         if (start > -1)
/* 186:    */         {
/* 187:196 */           String lockHint = appendLockHint(lockMode, alias);
/* 188:197 */           buffer.replace(start, end, lockHint);
/* 189:198 */           correction += lockHint.length() - alias.length();
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:202 */     return buffer.toString();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int registerResultSetOutParameter(CallableStatement statement, int col)
/* 197:    */     throws SQLException
/* 198:    */   {
/* 199:206 */     return col;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public ResultSet getResultSet(CallableStatement ps)
/* 203:    */     throws SQLException
/* 204:    */   {
/* 205:210 */     boolean isResultSet = ps.execute();
/* 206:212 */     while ((!isResultSet) && (ps.getUpdateCount() != -1)) {
/* 207:213 */       isResultSet = ps.getMoreResults();
/* 208:    */     }
/* 209:217 */     return ps.getResultSet();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean supportsCurrentTimestampSelection()
/* 213:    */   {
/* 214:221 */     return true;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 218:    */   {
/* 219:225 */     return false;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public String getCurrentTimestampSelectString()
/* 223:    */   {
/* 224:229 */     return "select getdate()";
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean supportsTemporaryTables()
/* 228:    */   {
/* 229:233 */     return true;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public String generateTemporaryTableName(String baseTableName)
/* 233:    */   {
/* 234:237 */     return "#" + baseTableName;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public boolean dropTemporaryTableAfterUse()
/* 238:    */   {
/* 239:241 */     return true;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public String getSelectGUIDString()
/* 243:    */   {
/* 244:244 */     return "select newid()";
/* 245:    */   }
/* 246:    */   
/* 247:    */   public boolean supportsEmptyInList()
/* 248:    */   {
/* 249:250 */     return false;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public boolean supportsUnionAll()
/* 253:    */   {
/* 254:254 */     return true;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public boolean supportsExistsInSelect()
/* 258:    */   {
/* 259:258 */     return false;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 263:    */   {
/* 264:262 */     return true;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean doesRepeatableReadCauseReadersToBlockWriters()
/* 268:    */   {
/* 269:266 */     return true;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public boolean supportsTupleDistinctCounts()
/* 273:    */   {
/* 274:269 */     return false;
/* 275:    */   }
/* 276:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.AbstractTransactSQLDialect
 * JD-Core Version:    0.7.0.1
 */