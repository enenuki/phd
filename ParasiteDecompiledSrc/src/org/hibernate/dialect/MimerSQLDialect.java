/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   5:    */ import org.hibernate.type.StandardBasicTypes;
/*   6:    */ 
/*   7:    */ public class MimerSQLDialect
/*   8:    */   extends Dialect
/*   9:    */ {
/*  10:    */   private static final int NATIONAL_CHAR_LENGTH = 2000;
/*  11:    */   private static final int BINARY_MAX_LENGTH = 2000;
/*  12:    */   
/*  13:    */   public MimerSQLDialect()
/*  14:    */   {
/*  15: 50 */     registerColumnType(-7, "ODBC.BIT");
/*  16: 51 */     registerColumnType(-5, "BIGINT");
/*  17: 52 */     registerColumnType(5, "SMALLINT");
/*  18: 53 */     registerColumnType(-6, "ODBC.TINYINT");
/*  19: 54 */     registerColumnType(4, "INTEGER");
/*  20: 55 */     registerColumnType(1, "NCHAR(1)");
/*  21: 56 */     registerColumnType(12, 2000L, "NATIONAL CHARACTER VARYING($l)");
/*  22: 57 */     registerColumnType(12, "NCLOB($l)");
/*  23: 58 */     registerColumnType(-1, "CLOB($1)");
/*  24: 59 */     registerColumnType(6, "FLOAT");
/*  25: 60 */     registerColumnType(8, "DOUBLE PRECISION");
/*  26: 61 */     registerColumnType(91, "DATE");
/*  27: 62 */     registerColumnType(92, "TIME");
/*  28: 63 */     registerColumnType(93, "TIMESTAMP");
/*  29: 64 */     registerColumnType(-3, 2000L, "BINARY VARYING($l)");
/*  30: 65 */     registerColumnType(-3, "BLOB($1)");
/*  31: 66 */     registerColumnType(-4, "BLOB($1)");
/*  32: 67 */     registerColumnType(-2, 2000L, "BINARY");
/*  33: 68 */     registerColumnType(-2, "BLOB($1)");
/*  34: 69 */     registerColumnType(2, "NUMERIC(19, $l)");
/*  35: 70 */     registerColumnType(2004, "BLOB($l)");
/*  36: 71 */     registerColumnType(2005, "NCLOB($l)");
/*  37:    */     
/*  38: 73 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  39: 74 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  40: 75 */     registerFunction("ceiling", new StandardSQLFunction("ceiling"));
/*  41: 76 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  42: 77 */     registerFunction("round", new StandardSQLFunction("round"));
/*  43:    */     
/*  44: 79 */     registerFunction("dacos", new StandardSQLFunction("dacos", StandardBasicTypes.DOUBLE));
/*  45: 80 */     registerFunction("acos", new StandardSQLFunction("dacos", StandardBasicTypes.DOUBLE));
/*  46: 81 */     registerFunction("dasin", new StandardSQLFunction("dasin", StandardBasicTypes.DOUBLE));
/*  47: 82 */     registerFunction("asin", new StandardSQLFunction("dasin", StandardBasicTypes.DOUBLE));
/*  48: 83 */     registerFunction("datan", new StandardSQLFunction("datan", StandardBasicTypes.DOUBLE));
/*  49: 84 */     registerFunction("atan", new StandardSQLFunction("datan", StandardBasicTypes.DOUBLE));
/*  50: 85 */     registerFunction("datan2", new StandardSQLFunction("datan2", StandardBasicTypes.DOUBLE));
/*  51: 86 */     registerFunction("atan2", new StandardSQLFunction("datan2", StandardBasicTypes.DOUBLE));
/*  52: 87 */     registerFunction("dcos", new StandardSQLFunction("dcos", StandardBasicTypes.DOUBLE));
/*  53: 88 */     registerFunction("cos", new StandardSQLFunction("dcos", StandardBasicTypes.DOUBLE));
/*  54: 89 */     registerFunction("dcot", new StandardSQLFunction("dcot", StandardBasicTypes.DOUBLE));
/*  55: 90 */     registerFunction("cot", new StandardSQLFunction("dcot", StandardBasicTypes.DOUBLE));
/*  56: 91 */     registerFunction("ddegrees", new StandardSQLFunction("ddegrees", StandardBasicTypes.DOUBLE));
/*  57: 92 */     registerFunction("degrees", new StandardSQLFunction("ddegrees", StandardBasicTypes.DOUBLE));
/*  58: 93 */     registerFunction("dexp", new StandardSQLFunction("dexp", StandardBasicTypes.DOUBLE));
/*  59: 94 */     registerFunction("exp", new StandardSQLFunction("dexp", StandardBasicTypes.DOUBLE));
/*  60: 95 */     registerFunction("dlog", new StandardSQLFunction("dlog", StandardBasicTypes.DOUBLE));
/*  61: 96 */     registerFunction("log", new StandardSQLFunction("dlog", StandardBasicTypes.DOUBLE));
/*  62: 97 */     registerFunction("dlog10", new StandardSQLFunction("dlog10", StandardBasicTypes.DOUBLE));
/*  63: 98 */     registerFunction("log10", new StandardSQLFunction("dlog10", StandardBasicTypes.DOUBLE));
/*  64: 99 */     registerFunction("dradian", new StandardSQLFunction("dradian", StandardBasicTypes.DOUBLE));
/*  65:100 */     registerFunction("radian", new StandardSQLFunction("dradian", StandardBasicTypes.DOUBLE));
/*  66:101 */     registerFunction("dsin", new StandardSQLFunction("dsin", StandardBasicTypes.DOUBLE));
/*  67:102 */     registerFunction("sin", new StandardSQLFunction("dsin", StandardBasicTypes.DOUBLE));
/*  68:103 */     registerFunction("soundex", new StandardSQLFunction("soundex", StandardBasicTypes.STRING));
/*  69:104 */     registerFunction("dsqrt", new StandardSQLFunction("dsqrt", StandardBasicTypes.DOUBLE));
/*  70:105 */     registerFunction("sqrt", new StandardSQLFunction("dsqrt", StandardBasicTypes.DOUBLE));
/*  71:106 */     registerFunction("dtan", new StandardSQLFunction("dtan", StandardBasicTypes.DOUBLE));
/*  72:107 */     registerFunction("tan", new StandardSQLFunction("dtan", StandardBasicTypes.DOUBLE));
/*  73:108 */     registerFunction("dpower", new StandardSQLFunction("dpower"));
/*  74:109 */     registerFunction("power", new StandardSQLFunction("dpower"));
/*  75:    */     
/*  76:111 */     registerFunction("date", new StandardSQLFunction("date", StandardBasicTypes.DATE));
/*  77:112 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/*  78:113 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/*  79:114 */     registerFunction("time", new StandardSQLFunction("time", StandardBasicTypes.TIME));
/*  80:115 */     registerFunction("timestamp", new StandardSQLFunction("timestamp", StandardBasicTypes.TIMESTAMP));
/*  81:116 */     registerFunction("week", new StandardSQLFunction("week", StandardBasicTypes.INTEGER));
/*  82:    */     
/*  83:    */ 
/*  84:119 */     registerFunction("varchar", new StandardSQLFunction("varchar", StandardBasicTypes.STRING));
/*  85:120 */     registerFunction("real", new StandardSQLFunction("real", StandardBasicTypes.FLOAT));
/*  86:121 */     registerFunction("bigint", new StandardSQLFunction("bigint", StandardBasicTypes.LONG));
/*  87:122 */     registerFunction("char", new StandardSQLFunction("char", StandardBasicTypes.CHARACTER));
/*  88:123 */     registerFunction("integer", new StandardSQLFunction("integer", StandardBasicTypes.INTEGER));
/*  89:124 */     registerFunction("smallint", new StandardSQLFunction("smallint", StandardBasicTypes.SHORT));
/*  90:    */     
/*  91:126 */     registerFunction("ascii_char", new StandardSQLFunction("ascii_char", StandardBasicTypes.CHARACTER));
/*  92:127 */     registerFunction("ascii_code", new StandardSQLFunction("ascii_code", StandardBasicTypes.STRING));
/*  93:128 */     registerFunction("unicode_char", new StandardSQLFunction("unicode_char", StandardBasicTypes.LONG));
/*  94:129 */     registerFunction("unicode_code", new StandardSQLFunction("unicode_code", StandardBasicTypes.STRING));
/*  95:130 */     registerFunction("upper", new StandardSQLFunction("upper"));
/*  96:131 */     registerFunction("lower", new StandardSQLFunction("lower"));
/*  97:132 */     registerFunction("char_length", new StandardSQLFunction("char_length", StandardBasicTypes.LONG));
/*  98:133 */     registerFunction("bit_length", new StandardSQLFunction("bit_length", StandardBasicTypes.STRING));
/*  99:    */     
/* 100:135 */     getDefaultProperties().setProperty("hibernate.jdbc.use_streams_for_binary", "true");
/* 101:136 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "50");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getAddColumnString()
/* 105:    */   {
/* 106:143 */     return "add column";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean dropConstraints()
/* 110:    */   {
/* 111:150 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public boolean supportsIdentityColumns()
/* 115:    */   {
/* 116:157 */     return false;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public boolean supportsSequences()
/* 120:    */   {
/* 121:165 */     return true;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getSequenceNextValString(String sequenceName)
/* 125:    */   {
/* 126:172 */     return "select next_value of " + sequenceName + " from system.onerow";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String getCreateSequenceString(String sequenceName)
/* 130:    */   {
/* 131:180 */     return "create unique sequence " + sequenceName;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public String getDropSequenceString(String sequenceName)
/* 135:    */   {
/* 136:187 */     return "drop sequence " + sequenceName + " restrict";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean supportsLimit()
/* 140:    */   {
/* 141:194 */     return false;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getCascadeConstraintsString()
/* 145:    */   {
/* 146:201 */     return " cascade";
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String getQuerySequencesString()
/* 150:    */   {
/* 151:208 */     return "select sequence_schema || '.' || sequence_name from information_schema.ext_sequences";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public boolean forUpdateOfColumns()
/* 155:    */   {
/* 156:216 */     return false;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public boolean supportsForUpdate()
/* 160:    */   {
/* 161:226 */     return false;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public boolean supportsOuterJoinForUpdate()
/* 165:    */   {
/* 166:234 */     return false;
/* 167:    */   }
/* 168:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.MimerSQLDialect
 * JD-Core Version:    0.7.0.1
 */