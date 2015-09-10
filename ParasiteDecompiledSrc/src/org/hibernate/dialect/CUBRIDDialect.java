/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   6:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   7:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   8:    */ import org.hibernate.type.StandardBasicTypes;
/*   9:    */ 
/*  10:    */ public class CUBRIDDialect
/*  11:    */   extends Dialect
/*  12:    */ {
/*  13:    */   protected String getIdentityColumnString()
/*  14:    */     throws MappingException
/*  15:    */   {
/*  16: 44 */     return "auto_increment";
/*  17:    */   }
/*  18:    */   
/*  19:    */   public String getIdentitySelectString(String table, String column, int type)
/*  20:    */     throws MappingException
/*  21:    */   {
/*  22: 52 */     return "select current_val from db_serial where name = '" + new StringBuilder().append(table).append("_ai_").append(column).toString().toLowerCase() + "'";
/*  23:    */   }
/*  24:    */   
/*  25:    */   public CUBRIDDialect()
/*  26:    */   {
/*  27: 58 */     registerColumnType(-7, "bit(8)");
/*  28: 59 */     registerColumnType(-5, "numeric(19,0)");
/*  29: 60 */     registerColumnType(5, "short");
/*  30: 61 */     registerColumnType(-6, "short");
/*  31: 62 */     registerColumnType(4, "integer");
/*  32: 63 */     registerColumnType(1, "char(1)");
/*  33: 64 */     registerColumnType(12, 4000L, "varchar($l)");
/*  34: 65 */     registerColumnType(6, "float");
/*  35: 66 */     registerColumnType(8, "double");
/*  36: 67 */     registerColumnType(91, "date");
/*  37: 68 */     registerColumnType(92, "time");
/*  38: 69 */     registerColumnType(93, "timestamp");
/*  39: 70 */     registerColumnType(-3, 2000L, "bit varying($l)");
/*  40: 71 */     registerColumnType(2, "numeric($p,$s)");
/*  41: 72 */     registerColumnType(2004, "blob");
/*  42: 73 */     registerColumnType(2005, "string");
/*  43:    */     
/*  44: 75 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "true");
/*  45: 76 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/*  46:    */     
/*  47: 78 */     registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/*  48: 79 */     registerFunction("trim", new StandardSQLFunction("trim"));
/*  49: 80 */     registerFunction("length", new StandardSQLFunction("length", StandardBasicTypes.INTEGER));
/*  50: 81 */     registerFunction("bit_length", new StandardSQLFunction("bit_length", StandardBasicTypes.INTEGER));
/*  51: 82 */     registerFunction("coalesce", new StandardSQLFunction("coalesce"));
/*  52: 83 */     registerFunction("nullif", new StandardSQLFunction("nullif"));
/*  53: 84 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  54: 85 */     registerFunction("mod", new StandardSQLFunction("mod"));
/*  55: 86 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  56: 87 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  57:    */     
/*  58: 89 */     registerFunction("power", new StandardSQLFunction("power"));
/*  59: 90 */     registerFunction("stddev", new StandardSQLFunction("stddev"));
/*  60: 91 */     registerFunction("variance", new StandardSQLFunction("variance"));
/*  61: 92 */     registerFunction("round", new StandardSQLFunction("round"));
/*  62: 93 */     registerFunction("trunc", new StandardSQLFunction("trunc"));
/*  63: 94 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/*  64: 95 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  65: 96 */     registerFunction("ltrim", new StandardSQLFunction("ltrim"));
/*  66: 97 */     registerFunction("rtrim", new StandardSQLFunction("rtrim"));
/*  67: 98 */     registerFunction("nvl", new StandardSQLFunction("nvl"));
/*  68: 99 */     registerFunction("nvl2", new StandardSQLFunction("nvl2"));
/*  69:100 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  70:101 */     registerFunction("chr", new StandardSQLFunction("chr", StandardBasicTypes.CHARACTER));
/*  71:102 */     registerFunction("to_char", new StandardSQLFunction("to_char", StandardBasicTypes.STRING));
/*  72:103 */     registerFunction("to_date", new StandardSQLFunction("to_date", StandardBasicTypes.TIMESTAMP));
/*  73:104 */     registerFunction("last_day", new StandardSQLFunction("last_day", StandardBasicTypes.DATE));
/*  74:105 */     registerFunction("instr", new StandardSQLFunction("instr", StandardBasicTypes.INTEGER));
/*  75:106 */     registerFunction("instrb", new StandardSQLFunction("instrb", StandardBasicTypes.INTEGER));
/*  76:107 */     registerFunction("lpad", new StandardSQLFunction("lpad", StandardBasicTypes.STRING));
/*  77:108 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/*  78:109 */     registerFunction("rpad", new StandardSQLFunction("rpad", StandardBasicTypes.STRING));
/*  79:110 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/*  80:111 */     registerFunction("substrb", new StandardSQLFunction("substrb", StandardBasicTypes.STRING));
/*  81:112 */     registerFunction("translate", new StandardSQLFunction("translate", StandardBasicTypes.STRING));
/*  82:113 */     registerFunction("add_months", new StandardSQLFunction("add_months", StandardBasicTypes.DATE));
/*  83:114 */     registerFunction("months_between", new StandardSQLFunction("months_between", StandardBasicTypes.FLOAT));
/*  84:    */     
/*  85:116 */     registerFunction("current_date", new NoArgSQLFunction("current_date", StandardBasicTypes.DATE, false));
/*  86:117 */     registerFunction("current_time", new NoArgSQLFunction("current_time", StandardBasicTypes.TIME, false));
/*  87:118 */     registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
/*  88:    */     
/*  89:    */ 
/*  90:    */ 
/*  91:122 */     registerFunction("sysdate", new NoArgSQLFunction("sysdate", StandardBasicTypes.DATE, false));
/*  92:123 */     registerFunction("systime", new NoArgSQLFunction("systime", StandardBasicTypes.TIME, false));
/*  93:124 */     registerFunction("systimestamp", new NoArgSQLFunction("systimestamp", StandardBasicTypes.TIMESTAMP, false));
/*  94:125 */     registerFunction("user", new NoArgSQLFunction("user", StandardBasicTypes.STRING, false));
/*  95:126 */     registerFunction("rownum", new NoArgSQLFunction("rownum", StandardBasicTypes.LONG, false));
/*  96:127 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""));
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getAddColumnString()
/* 100:    */   {
/* 101:131 */     return "add";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getSequenceNextValString(String sequenceName)
/* 105:    */   {
/* 106:135 */     return "select " + sequenceName + ".next_value from table({1}) as T(X)";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getCreateSequenceString(String sequenceName)
/* 110:    */   {
/* 111:139 */     return "create serial " + sequenceName;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getDropSequenceString(String sequenceName)
/* 115:    */   {
/* 116:143 */     return "drop serial " + sequenceName;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean supportsSequences()
/* 120:    */   {
/* 121:147 */     return true;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getQuerySequencesString()
/* 125:    */   {
/* 126:151 */     return "select name from db_serial";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean dropConstraints()
/* 130:    */   {
/* 131:155 */     return false;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean supportsLimit()
/* 135:    */   {
/* 136:159 */     return true;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String getLimitString(String sql, boolean hasOffset)
/* 140:    */   {
/* 141:164 */     return sql.length() + 20 + sql + (hasOffset ? " limit ?, ?" : " limit ?");
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean bindLimitParametersInReverseOrder()
/* 145:    */   {
/* 146:169 */     return true;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean useMaxForLimit()
/* 150:    */   {
/* 151:173 */     return true;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean forUpdateOfColumns()
/* 155:    */   {
/* 156:177 */     return true;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public char closeQuote()
/* 160:    */   {
/* 161:181 */     return ']';
/* 162:    */   }
/* 163:    */   
/* 164:    */   public char openQuote()
/* 165:    */   {
/* 166:185 */     return '[';
/* 167:    */   }
/* 168:    */   
/* 169:    */   public boolean hasAlterTable()
/* 170:    */   {
/* 171:189 */     return false;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getForUpdateString()
/* 175:    */   {
/* 176:193 */     return " ";
/* 177:    */   }
/* 178:    */   
/* 179:    */   public boolean supportsUnionAll()
/* 180:    */   {
/* 181:197 */     return true;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean supportsCommentOn()
/* 185:    */   {
/* 186:201 */     return false;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public boolean supportsTemporaryTables()
/* 190:    */   {
/* 191:205 */     return false;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean supportsCurrentTimestampSelection()
/* 195:    */   {
/* 196:209 */     return true;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public String getCurrentTimestampSelectString()
/* 200:    */   {
/* 201:213 */     return "select systimestamp from table({1}) as T(X)";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 205:    */   {
/* 206:217 */     return false;
/* 207:    */   }
/* 208:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.CUBRIDDialect
 * JD-Core Version:    0.7.0.1
 */