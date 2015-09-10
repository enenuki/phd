/*   1:    */ package org.hibernate.dialect;
/*   2:    */ 
/*   3:    */ import java.sql.SQLException;
/*   4:    */ import org.hibernate.MappingException;
/*   5:    */ import org.hibernate.dialect.function.VarArgsSQLFunction;
/*   6:    */ import org.hibernate.exception.spi.TemplatedViolatedConstraintNameExtracter;
/*   7:    */ import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;
/*   8:    */ import org.hibernate.internal.util.JdbcExceptionHelper;
/*   9:    */ import org.hibernate.internal.util.StringHelper;
/*  10:    */ import org.hibernate.type.StandardBasicTypes;
/*  11:    */ 
/*  12:    */ public class InformixDialect
/*  13:    */   extends Dialect
/*  14:    */ {
/*  15:    */   public InformixDialect()
/*  16:    */   {
/*  17: 52 */     registerColumnType(-5, "int8");
/*  18: 53 */     registerColumnType(-2, "byte");
/*  19: 54 */     registerColumnType(-7, "smallint");
/*  20: 55 */     registerColumnType(1, "char($l)");
/*  21: 56 */     registerColumnType(91, "date");
/*  22: 57 */     registerColumnType(3, "decimal");
/*  23: 58 */     registerColumnType(8, "float");
/*  24: 59 */     registerColumnType(6, "smallfloat");
/*  25: 60 */     registerColumnType(4, "integer");
/*  26: 61 */     registerColumnType(-4, "blob");
/*  27: 62 */     registerColumnType(-1, "clob");
/*  28: 63 */     registerColumnType(2, "decimal");
/*  29: 64 */     registerColumnType(7, "smallfloat");
/*  30: 65 */     registerColumnType(5, "smallint");
/*  31: 66 */     registerColumnType(93, "datetime year to fraction(5)");
/*  32: 67 */     registerColumnType(92, "datetime hour to second");
/*  33: 68 */     registerColumnType(-6, "smallint");
/*  34: 69 */     registerColumnType(-3, "byte");
/*  35: 70 */     registerColumnType(12, "varchar($l)");
/*  36: 71 */     registerColumnType(12, 255L, "varchar($l)");
/*  37: 72 */     registerColumnType(12, 32739L, "lvarchar($l)");
/*  38:    */     
/*  39: 74 */     registerFunction("concat", new VarArgsSQLFunction(StandardBasicTypes.STRING, "(", "||", ")"));
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getAddColumnString()
/*  43:    */   {
/*  44: 78 */     return "add";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean supportsIdentityColumns()
/*  48:    */   {
/*  49: 82 */     return true;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public String getIdentitySelectString(String table, String column, int type)
/*  53:    */     throws MappingException
/*  54:    */   {
/*  55: 87 */     return type == -5 ? "select dbinfo('serial8') from informix.systables where tabid=1" : "select dbinfo('sqlca.sqlerrd1') from informix.systables where tabid=1";
/*  56:    */   }
/*  57:    */   
/*  58:    */   public String getIdentityColumnString(int type)
/*  59:    */     throws MappingException
/*  60:    */   {
/*  61: 93 */     return type == -5 ? "serial8 not null" : "serial not null";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean hasDataTypeInIdentityColumn()
/*  65:    */   {
/*  66: 99 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable, String[] primaryKey, boolean referencesPrimaryKey)
/*  70:    */   {
/*  71:113 */     StringBuffer result = new StringBuffer(30).append(" add constraint ").append(" foreign key (").append(StringHelper.join(", ", foreignKey)).append(") references ").append(referencedTable);
/*  72:120 */     if (!referencesPrimaryKey) {
/*  73:121 */       result.append(" (").append(StringHelper.join(", ", primaryKey)).append(')');
/*  74:    */     }
/*  75:126 */     result.append(" constraint ").append(constraintName);
/*  76:    */     
/*  77:128 */     return result.toString();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getAddPrimaryKeyConstraintString(String constraintName)
/*  81:    */   {
/*  82:137 */     return " add constraint primary key constraint " + constraintName + " ";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getCreateSequenceString(String sequenceName)
/*  86:    */   {
/*  87:141 */     return "create sequence " + sequenceName;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getDropSequenceString(String sequenceName)
/*  91:    */   {
/*  92:144 */     return "drop sequence " + sequenceName + " restrict";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getSequenceNextValString(String sequenceName)
/*  96:    */   {
/*  97:148 */     return "select " + getSelectSequenceNextValString(sequenceName) + " from informix.systables where tabid=1";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getSelectSequenceNextValString(String sequenceName)
/* 101:    */   {
/* 102:152 */     return sequenceName + ".nextval";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean supportsSequences()
/* 106:    */   {
/* 107:156 */     return true;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public boolean supportsPooledSequences()
/* 111:    */   {
/* 112:160 */     return true;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String getQuerySequencesString()
/* 116:    */   {
/* 117:164 */     return "select tabname from informix.systables where tabtype='Q'";
/* 118:    */   }
/* 119:    */   
/* 120:    */   public boolean supportsLimit()
/* 121:    */   {
/* 122:168 */     return true;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean useMaxForLimit()
/* 126:    */   {
/* 127:172 */     return true;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean supportsLimitOffset()
/* 131:    */   {
/* 132:176 */     return false;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getLimitString(String querySelect, int offset, int limit)
/* 136:    */   {
/* 137:180 */     if (offset > 0) {
/* 138:181 */       throw new UnsupportedOperationException("query result offset is not supported");
/* 139:    */     }
/* 140:183 */     return new StringBuffer(querySelect.length() + 8).append(querySelect).insert(querySelect.toLowerCase().indexOf("select") + 6, " first " + limit).toString();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean supportsVariableLimit()
/* 144:    */   {
/* 145:190 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter()
/* 149:    */   {
/* 150:194 */     return EXTRACTER;
/* 151:    */   }
/* 152:    */   
/* 153:197 */   private static ViolatedConstraintNameExtracter EXTRACTER = new TemplatedViolatedConstraintNameExtracter()
/* 154:    */   {
/* 155:    */     public String extractConstraintName(SQLException sqle)
/* 156:    */     {
/* 157:206 */       String constraintName = null;
/* 158:    */       
/* 159:208 */       int errorCode = JdbcExceptionHelper.extractErrorCode(sqle);
/* 160:209 */       if (errorCode == -268) {
/* 161:210 */         constraintName = extractUsingTemplate("Unique constraint (", ") violated.", sqle.getMessage());
/* 162:212 */       } else if (errorCode == -691) {
/* 163:213 */         constraintName = extractUsingTemplate("Missing key in referenced table for referential constraint (", ").", sqle.getMessage());
/* 164:215 */       } else if (errorCode == -692) {
/* 165:216 */         constraintName = extractUsingTemplate("Key value for constraint (", ") is still being referenced.", sqle.getMessage());
/* 166:    */       }
/* 167:219 */       if (constraintName != null)
/* 168:    */       {
/* 169:221 */         int i = constraintName.indexOf('.');
/* 170:222 */         if (i != -1) {
/* 171:223 */           constraintName = constraintName.substring(i + 1);
/* 172:    */         }
/* 173:    */       }
/* 174:227 */       return constraintName;
/* 175:    */     }
/* 176:    */   };
/* 177:    */   
/* 178:    */   public boolean supportsCurrentTimestampSelection()
/* 179:    */   {
/* 180:233 */     return true;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public boolean isCurrentTimestampSelectStringCallable()
/* 184:    */   {
/* 185:237 */     return false;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String getCurrentTimestampSelectString()
/* 189:    */   {
/* 190:241 */     return "select distinct current timestamp from informix.systables";
/* 191:    */   }
/* 192:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.InformixDialect
 * JD-Core Version:    0.7.0.1
 */