/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.HibernateException;
/*   5:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   6:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   7:    */ import org.hibernate.type.StandardBasicTypes;
/*   8:    */ 
/*   9:    */ public class TeradataDialect
/*  10:    */   extends Dialect
/*  11:    */ {
/*  12:    */   public TeradataDialect()
/*  13:    */   {
/*  14: 47 */     registerColumnType(2, "NUMERIC($p,$s)");
/*  15: 48 */     registerColumnType(8, "DOUBLE PRECISION");
/*  16: 49 */     registerColumnType(-5, "NUMERIC(18,0)");
/*  17: 50 */     registerColumnType(-7, "BYTEINT");
/*  18: 51 */     registerColumnType(-6, "BYTEINT");
/*  19: 52 */     registerColumnType(-3, "VARBYTE($l)");
/*  20: 53 */     registerColumnType(-2, "BYTEINT");
/*  21: 54 */     registerColumnType(-1, "LONG VARCHAR");
/*  22: 55 */     registerColumnType(1, "CHAR(1)");
/*  23: 56 */     registerColumnType(3, "DECIMAL");
/*  24: 57 */     registerColumnType(4, "INTEGER");
/*  25: 58 */     registerColumnType(5, "SMALLINT");
/*  26: 59 */     registerColumnType(6, "FLOAT");
/*  27: 60 */     registerColumnType(12, "VARCHAR($l)");
/*  28: 61 */     registerColumnType(91, "DATE");
/*  29: 62 */     registerColumnType(92, "TIME");
/*  30: 63 */     registerColumnType(93, "TIMESTAMP");
/*  31: 64 */     registerColumnType(16, "BYTEINT");
/*  32: 65 */     registerColumnType(2004, "BLOB");
/*  33: 66 */     registerColumnType(2005, "CLOB");
/*  34:    */     
/*  35: 68 */     registerFunction("year", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "extract(year from ?1)"));
/*  36: 69 */     registerFunction("length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "character_length(?1)"));
/*  37: 70 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/*  38: 71 */     registerFunction("substring", new SQLFunctionTemplate(StandardBasicTypes.STRING, "substring(?1 from ?2 for ?3)"));
/*  39: 72 */     registerFunction("locate", new SQLFunctionTemplate(StandardBasicTypes.STRING, "position(?1 in ?2)"));
/*  40: 73 */     registerFunction("mod", new SQLFunctionTemplate(StandardBasicTypes.STRING, "?1 mod ?2"));
/*  41: 74 */     registerFunction("str", new SQLFunctionTemplate(StandardBasicTypes.STRING, "cast(?1 as varchar(255))"));
/*  42:    */     
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48: 81 */     registerFunction("bit_length", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "octet_length(cast(?1 as char))*4"));
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56: 89 */     registerFunction("current_timestamp", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "current_timestamp"));
/*  57: 90 */     registerFunction("current_time", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "current_time"));
/*  58: 91 */     registerFunction("current_date", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "current_date"));
/*  59:    */     
/*  60:    */ 
/*  61: 94 */     registerKeyword("password");
/*  62: 95 */     registerKeyword("type");
/*  63: 96 */     registerKeyword("title");
/*  64: 97 */     registerKeyword("year");
/*  65: 98 */     registerKeyword("month");
/*  66: 99 */     registerKeyword("summary");
/*  67:100 */     registerKeyword("alias");
/*  68:101 */     registerKeyword("value");
/*  69:102 */     registerKeyword("first");
/*  70:103 */     registerKeyword("role");
/*  71:104 */     registerKeyword("account");
/*  72:105 */     registerKeyword("class");
/*  73:    */     
/*  74:    */ 
/*  75:108 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "false");
/*  76:    */     
/*  77:110 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "0");
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getForUpdateString()
/*  81:    */   {
/*  82:119 */     return "";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean supportsIdentityColumns()
/*  86:    */   {
/*  87:123 */     return false;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean supportsSequences()
/*  91:    */   {
/*  92:127 */     return false;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getAddColumnString()
/*  96:    */   {
/*  97:131 */     return "Add Column";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean supportsTemporaryTables()
/* 101:    */   {
/* 102:135 */     return true;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String getCreateTemporaryTableString()
/* 106:    */   {
/* 107:139 */     return "create global temporary table";
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getCreateTemporaryTablePostfix()
/* 111:    */   {
/* 112:143 */     return " on commit preserve rows";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Boolean performTemporaryTableDDLInIsolation()
/* 116:    */   {
/* 117:147 */     return Boolean.TRUE;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean dropTemporaryTableAfterUse()
/* 121:    */   {
/* 122:151 */     return false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getTypeName(int code, int length, int precision, int scale)
/* 126:    */     throws HibernateException
/* 127:    */   {
/* 128:172 */     float f = precision > 0 ? scale / precision : 0.0F;
/* 129:173 */     int p = precision > 18 ? 18 : precision;
/* 130:174 */     int s = scale > 18 ? 18 : precision > 18 ? (int)(18.0D * f) : scale;
/* 131:    */     
/* 132:176 */     return super.getTypeName(code, length, p, s);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean supportsCascadeDelete()
/* 136:    */   {
/* 137:180 */     return false;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean supportsCircularCascadeDeleteConstraints()
/* 141:    */   {
/* 142:184 */     return false;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public boolean areStringComparisonsCaseInsensitive()
/* 146:    */   {
/* 147:188 */     return true;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean supportsEmptyInList()
/* 151:    */   {
/* 152:192 */     return false;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getSelectClauseNullString(int sqlType)
/* 156:    */   {
/* 157:196 */     String v = "null";
/* 158:198 */     switch (sqlType)
/* 159:    */     {
/* 160:    */     case -7: 
/* 161:    */     case -6: 
/* 162:    */     case -5: 
/* 163:    */     case 2: 
/* 164:    */     case 3: 
/* 165:    */     case 4: 
/* 166:    */     case 5: 
/* 167:    */     case 6: 
/* 168:    */     case 7: 
/* 169:    */     case 8: 
/* 170:209 */       v = "cast(null as decimal)";
/* 171:210 */       break;
/* 172:    */     case -1: 
/* 173:    */     case 1: 
/* 174:    */     case 12: 
/* 175:214 */       v = "cast(null as varchar(255))";
/* 176:215 */       break;
/* 177:    */     case 91: 
/* 178:    */     case 92: 
/* 179:    */     case 93: 
/* 180:219 */       v = "cast(null as timestamp)";
/* 181:220 */       break;
/* 182:    */     }
/* 183:237 */     return v;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getCreateMultisetTableString()
/* 187:    */   {
/* 188:241 */     return "create multiset table ";
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean supportsLobValueChangePropogation()
/* 192:    */   {
/* 193:245 */     return false;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public boolean doesReadCommittedCauseWritersToBlockReaders()
/* 197:    */   {
/* 198:249 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public boolean doesRepeatableReadCauseReadersToBlockWriters()
/* 202:    */   {
/* 203:253 */     return true;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public boolean supportsBindAsCallableArgument()
/* 207:    */   {
/* 208:257 */     return false;
/* 209:    */   }
/* 210:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.TeradataDialect
 * JD-Core Version:    0.7.0.1
 */