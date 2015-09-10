/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import org.hibernate.dialect.function.NoArgSQLFunction;
/*   5:    */ import org.hibernate.dialect.function.SQLFunctionTemplate;
/*   6:    */ import org.hibernate.dialect.function.StandardSQLFunction;
/*   7:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   8:    */ import org.hibernate.internal.util.StringHelper;
/*   9:    */ import org.hibernate.sql.CaseFragment;
/*  10:    */ import org.hibernate.sql.DecodeCaseFragment;
/*  11:    */ import org.hibernate.type.StandardBasicTypes;
/*  12:    */ 
/*  13:    */ public class SAPDBDialect
/*  14:    */   extends Dialect
/*  15:    */ {
/*  16:    */   public SAPDBDialect()
/*  17:    */   {
/*  18: 46 */     registerColumnType(-7, "boolean");
/*  19: 47 */     registerColumnType(-5, "fixed(19,0)");
/*  20: 48 */     registerColumnType(5, "smallint");
/*  21: 49 */     registerColumnType(-6, "fixed(3,0)");
/*  22: 50 */     registerColumnType(4, "int");
/*  23: 51 */     registerColumnType(1, "char(1)");
/*  24: 52 */     registerColumnType(12, "varchar($l)");
/*  25: 53 */     registerColumnType(6, "float");
/*  26: 54 */     registerColumnType(8, "double precision");
/*  27: 55 */     registerColumnType(91, "date");
/*  28: 56 */     registerColumnType(92, "time");
/*  29: 57 */     registerColumnType(93, "timestamp");
/*  30: 58 */     registerColumnType(-3, "long byte");
/*  31: 59 */     registerColumnType(2, "fixed($p,$s)");
/*  32: 60 */     registerColumnType(2005, "long varchar");
/*  33: 61 */     registerColumnType(2004, "long byte");
/*  34:    */     
/*  35: 63 */     registerFunction("abs", new StandardSQLFunction("abs"));
/*  36: 64 */     registerFunction("sign", new StandardSQLFunction("sign", StandardBasicTypes.INTEGER));
/*  37:    */     
/*  38: 66 */     registerFunction("exp", new StandardSQLFunction("exp", StandardBasicTypes.DOUBLE));
/*  39: 67 */     registerFunction("ln", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  40: 68 */     registerFunction("log", new StandardSQLFunction("ln", StandardBasicTypes.DOUBLE));
/*  41: 69 */     registerFunction("pi", new NoArgSQLFunction("pi", StandardBasicTypes.DOUBLE));
/*  42: 70 */     registerFunction("power", new StandardSQLFunction("power"));
/*  43: 71 */     registerFunction("acos", new StandardSQLFunction("acos", StandardBasicTypes.DOUBLE));
/*  44: 72 */     registerFunction("asin", new StandardSQLFunction("asin", StandardBasicTypes.DOUBLE));
/*  45: 73 */     registerFunction("atan", new StandardSQLFunction("atan", StandardBasicTypes.DOUBLE));
/*  46: 74 */     registerFunction("cos", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  47: 75 */     registerFunction("cosh", new StandardSQLFunction("cosh", StandardBasicTypes.DOUBLE));
/*  48: 76 */     registerFunction("cot", new StandardSQLFunction("cos", StandardBasicTypes.DOUBLE));
/*  49: 77 */     registerFunction("sin", new StandardSQLFunction("sin", StandardBasicTypes.DOUBLE));
/*  50: 78 */     registerFunction("sinh", new StandardSQLFunction("sinh", StandardBasicTypes.DOUBLE));
/*  51: 79 */     registerFunction("tan", new StandardSQLFunction("tan", StandardBasicTypes.DOUBLE));
/*  52: 80 */     registerFunction("tanh", new StandardSQLFunction("tanh", StandardBasicTypes.DOUBLE));
/*  53: 81 */     registerFunction("radians", new StandardSQLFunction("radians", StandardBasicTypes.DOUBLE));
/*  54: 82 */     registerFunction("degrees", new StandardSQLFunction("degrees", StandardBasicTypes.DOUBLE));
/*  55: 83 */     registerFunction("atan2", new StandardSQLFunction("atan2", StandardBasicTypes.DOUBLE));
/*  56:    */     
/*  57: 85 */     registerFunction("round", new StandardSQLFunction("round"));
/*  58: 86 */     registerFunction("trunc", new StandardSQLFunction("trunc"));
/*  59: 87 */     registerFunction("ceil", new StandardSQLFunction("ceil"));
/*  60: 88 */     registerFunction("floor", new StandardSQLFunction("floor"));
/*  61: 89 */     registerFunction("greatest", new StandardSQLFunction("greatest"));
/*  62: 90 */     registerFunction("least", new StandardSQLFunction("least"));
/*  63:    */     
/*  64: 92 */     registerFunction("time", new StandardSQLFunction("time", StandardBasicTypes.TIME));
/*  65: 93 */     registerFunction("timestamp", new StandardSQLFunction("timestamp", StandardBasicTypes.TIMESTAMP));
/*  66: 94 */     registerFunction("date", new StandardSQLFunction("date", StandardBasicTypes.DATE));
/*  67: 95 */     registerFunction("microsecond", new StandardSQLFunction("microsecond", StandardBasicTypes.INTEGER));
/*  68:    */     
/*  69: 97 */     registerFunction("second", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "second(?1)"));
/*  70: 98 */     registerFunction("minute", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "minute(?1)"));
/*  71: 99 */     registerFunction("hour", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "hour(?1)"));
/*  72:100 */     registerFunction("day", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "day(?1)"));
/*  73:101 */     registerFunction("month", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "month(?1)"));
/*  74:102 */     registerFunction("year", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "year(?1)"));
/*  75:    */     
/*  76:104 */     registerFunction("extract", new SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1(?3)"));
/*  77:    */     
/*  78:106 */     registerFunction("dayname", new StandardSQLFunction("dayname", StandardBasicTypes.STRING));
/*  79:107 */     registerFunction("monthname", new StandardSQLFunction("monthname", StandardBasicTypes.STRING));
/*  80:108 */     registerFunction("dayofmonth", new StandardSQLFunction("dayofmonth", StandardBasicTypes.INTEGER));
/*  81:109 */     registerFunction("dayofweek", new StandardSQLFunction("dayofweek", StandardBasicTypes.INTEGER));
/*  82:110 */     registerFunction("dayofyear", new StandardSQLFunction("dayofyear", StandardBasicTypes.INTEGER));
/*  83:111 */     registerFunction("weekofyear", new StandardSQLFunction("weekofyear", StandardBasicTypes.INTEGER));
/*  84:    */     
/*  85:113 */     registerFunction("replace", new StandardSQLFunction("replace", StandardBasicTypes.STRING));
/*  86:114 */     registerFunction("translate", new StandardSQLFunction("translate", StandardBasicTypes.STRING));
/*  87:115 */     registerFunction("lpad", new StandardSQLFunction("lpad", StandardBasicTypes.STRING));
/*  88:116 */     registerFunction("rpad", new StandardSQLFunction("rpad", StandardBasicTypes.STRING));
/*  89:117 */     registerFunction("substr", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/*  90:118 */     registerFunction("initcap", new StandardSQLFunction("initcap", StandardBasicTypes.STRING));
/*  91:119 */     registerFunction("lower", new StandardSQLFunction("lower", StandardBasicTypes.STRING));
/*  92:120 */     registerFunction("ltrim", new StandardSQLFunction("ltrim", StandardBasicTypes.STRING));
/*  93:121 */     registerFunction("rtrim", new StandardSQLFunction("rtrim", StandardBasicTypes.STRING));
/*  94:122 */     registerFunction("lfill", new StandardSQLFunction("ltrim", StandardBasicTypes.STRING));
/*  95:123 */     registerFunction("rfill", new StandardSQLFunction("rtrim", StandardBasicTypes.STRING));
/*  96:124 */     registerFunction("soundex", new StandardSQLFunction("soundex", StandardBasicTypes.STRING));
/*  97:125 */     registerFunction("upper", new StandardSQLFunction("upper", StandardBasicTypes.STRING));
/*  98:126 */     registerFunction("ascii", new StandardSQLFunction("ascii", StandardBasicTypes.STRING));
/*  99:127 */     registerFunction("index", new StandardSQLFunction("index", StandardBasicTypes.INTEGER));
/* 100:    */     
/* 101:129 */     registerFunction("value", new StandardSQLFunction("value"));
/* 102:    */     
/* 103:131 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/* 104:132 */     registerFunction("substring", new StandardSQLFunction("substr", StandardBasicTypes.STRING));
/* 105:133 */     registerFunction("locate", new StandardSQLFunction("index", StandardBasicTypes.INTEGER));
/* 106:134 */     registerFunction("coalesce", new StandardSQLFunction("value"));
/* 107:    */     
/* 108:136 */     getDefaultProperties().setProperty("hibernate.jdbc.batch_size", "15");
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean dropConstraints()
/* 112:    */   {
/* 113:141 */     return false;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getAddColumnString()
/* 117:    */   {
/* 118:145 */     return "add";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey)
/* 122:    */   {
/* 123:154 */     StringBuffer res = new StringBuffer(30).append(" foreign key ").append(constraintName).append(" (").append(StringHelper.join(", ", foreignKey)).append(") references ").append(referencedTable);
/* 124:162 */     if (!referencesPrimaryKey) {
/* 125:163 */       res.append(" (").append(StringHelper.join(", ", primaryKey)).append(')');
/* 126:    */     }
/* 127:168 */     return res.toString();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getAddPrimaryKeyConstraintString(String constraintName)
/* 131:    */   {
/* 132:172 */     return " primary key ";
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNullColumnString()
/* 136:    */   {
/* 137:176 */     return " null";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getSequenceNextValString(String sequenceName)
/* 141:    */   {
/* 142:180 */     return "select " + getSelectSequenceNextValString(sequenceName) + " from dual";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 146:    */   {
/* 147:184 */     return sequenceName + ".nextval";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String getCreateSequenceString(String sequenceName)
/* 151:    */   {
/* 152:188 */     return "create sequence " + sequenceName;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String getDropSequenceString(String sequenceName)
/* 156:    */   {
/* 157:192 */     return "drop sequence " + sequenceName;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getQuerySequencesString()
/* 161:    */   {
/* 162:196 */     return "select sequence_name from domain.sequences";
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean supportsSequences()
/* 166:    */   {
/* 167:200 */     return true;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public CaseFragment createCaseFragment()
/* 171:    */   {
/* 172:204 */     return new DecodeCaseFragment();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean supportsTemporaryTables()
/* 176:    */   {
/* 177:208 */     return true;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getCreateTemporaryTablePostfix()
/* 181:    */   {
/* 182:212 */     return "ignore rollback";
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String generateTemporaryTableName(String baseTableName)
/* 186:    */   {
/* 187:216 */     return "temp." + super.generateTemporaryTableName(baseTableName);
/* 188:    */   }
/* 189:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.SAPDBDialect
 * JD-Core Version:    0.7.0.1
 */