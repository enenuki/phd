/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.LinkedHashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.dialect.Dialect;
/*   9:    */ import org.hibernate.type.LiteralType;
/*  10:    */ 
/*  11:    */ public class Update
/*  12:    */ {
/*  13:    */   private String tableName;
/*  14:    */   private String versionColumnName;
/*  15:    */   private String where;
/*  16:    */   private String assignments;
/*  17:    */   private String comment;
/*  18: 46 */   private Map primaryKeyColumns = new LinkedHashMap();
/*  19: 47 */   private Map columns = new LinkedHashMap();
/*  20: 48 */   private Map whereColumns = new LinkedHashMap();
/*  21:    */   private Dialect dialect;
/*  22:    */   
/*  23:    */   public Update(Dialect dialect)
/*  24:    */   {
/*  25: 53 */     this.dialect = dialect;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public String getTableName()
/*  29:    */   {
/*  30: 57 */     return this.tableName;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public Update appendAssignmentFragment(String fragment)
/*  34:    */   {
/*  35: 61 */     if (this.assignments == null) {
/*  36: 62 */       this.assignments = fragment;
/*  37:    */     } else {
/*  38: 65 */       this.assignments = (this.assignments + ", " + fragment);
/*  39:    */     }
/*  40: 67 */     return this;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Update setTableName(String tableName)
/*  44:    */   {
/*  45: 71 */     this.tableName = tableName;
/*  46: 72 */     return this;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Update setPrimaryKeyColumnNames(String[] columnNames)
/*  50:    */   {
/*  51: 76 */     this.primaryKeyColumns.clear();
/*  52: 77 */     addPrimaryKeyColumns(columnNames);
/*  53: 78 */     return this;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Update addPrimaryKeyColumns(String[] columnNames)
/*  57:    */   {
/*  58: 82 */     for (int i = 0; i < columnNames.length; i++) {
/*  59: 83 */       addPrimaryKeyColumn(columnNames[i], "?");
/*  60:    */     }
/*  61: 85 */     return this;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Update addPrimaryKeyColumns(String[] columnNames, boolean[] includeColumns, String[] valueExpressions)
/*  65:    */   {
/*  66: 89 */     for (int i = 0; i < columnNames.length; i++) {
/*  67: 90 */       if (includeColumns[i] != 0) {
/*  68: 90 */         addPrimaryKeyColumn(columnNames[i], valueExpressions[i]);
/*  69:    */       }
/*  70:    */     }
/*  71: 92 */     return this;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Update addPrimaryKeyColumns(String[] columnNames, String[] valueExpressions)
/*  75:    */   {
/*  76: 96 */     for (int i = 0; i < columnNames.length; i++) {
/*  77: 97 */       addPrimaryKeyColumn(columnNames[i], valueExpressions[i]);
/*  78:    */     }
/*  79: 99 */     return this;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Update addPrimaryKeyColumn(String columnName, String valueExpression)
/*  83:    */   {
/*  84:103 */     this.primaryKeyColumns.put(columnName, valueExpression);
/*  85:104 */     return this;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Update setVersionColumnName(String versionColumnName)
/*  89:    */   {
/*  90:108 */     this.versionColumnName = versionColumnName;
/*  91:109 */     return this;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Update setComment(String comment)
/*  95:    */   {
/*  96:114 */     this.comment = comment;
/*  97:115 */     return this;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Update addColumns(String[] columnNames)
/* 101:    */   {
/* 102:119 */     for (int i = 0; i < columnNames.length; i++) {
/* 103:120 */       addColumn(columnNames[i]);
/* 104:    */     }
/* 105:122 */     return this;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Update addColumns(String[] columnNames, boolean[] updateable, String[] valueExpressions)
/* 109:    */   {
/* 110:126 */     for (int i = 0; i < columnNames.length; i++) {
/* 111:127 */       if (updateable[i] != 0) {
/* 112:127 */         addColumn(columnNames[i], valueExpressions[i]);
/* 113:    */       }
/* 114:    */     }
/* 115:129 */     return this;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Update addColumns(String[] columnNames, String valueExpression)
/* 119:    */   {
/* 120:133 */     for (int i = 0; i < columnNames.length; i++) {
/* 121:134 */       addColumn(columnNames[i], valueExpression);
/* 122:    */     }
/* 123:136 */     return this;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Update addColumn(String columnName)
/* 127:    */   {
/* 128:140 */     return addColumn(columnName, "?");
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Update addColumn(String columnName, String valueExpression)
/* 132:    */   {
/* 133:144 */     this.columns.put(columnName, valueExpression);
/* 134:145 */     return this;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Update addColumn(String columnName, Object value, LiteralType type)
/* 138:    */     throws Exception
/* 139:    */   {
/* 140:149 */     return addColumn(columnName, type.objectToSQLString(value, this.dialect));
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Update addWhereColumns(String[] columnNames)
/* 144:    */   {
/* 145:153 */     for (int i = 0; i < columnNames.length; i++) {
/* 146:154 */       addWhereColumn(columnNames[i]);
/* 147:    */     }
/* 148:156 */     return this;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Update addWhereColumns(String[] columnNames, String valueExpression)
/* 152:    */   {
/* 153:160 */     for (int i = 0; i < columnNames.length; i++) {
/* 154:161 */       addWhereColumn(columnNames[i], valueExpression);
/* 155:    */     }
/* 156:163 */     return this;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Update addWhereColumn(String columnName)
/* 160:    */   {
/* 161:167 */     return addWhereColumn(columnName, "=?");
/* 162:    */   }
/* 163:    */   
/* 164:    */   public Update addWhereColumn(String columnName, String valueExpression)
/* 165:    */   {
/* 166:171 */     this.whereColumns.put(columnName, valueExpression);
/* 167:172 */     return this;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Update setWhere(String where)
/* 171:    */   {
/* 172:176 */     this.where = where;
/* 173:177 */     return this;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String toStatementString()
/* 177:    */   {
/* 178:181 */     StringBuffer buf = new StringBuffer(this.columns.size() * 15 + this.tableName.length() + 10);
/* 179:182 */     if (this.comment != null) {
/* 180:183 */       buf.append("/* ").append(this.comment).append(" */ ");
/* 181:    */     }
/* 182:185 */     buf.append("update ").append(this.tableName).append(" set ");
/* 183:186 */     boolean assignmentsAppended = false;
/* 184:187 */     Iterator iter = this.columns.entrySet().iterator();
/* 185:188 */     while (iter.hasNext())
/* 186:    */     {
/* 187:189 */       Map.Entry e = (Map.Entry)iter.next();
/* 188:190 */       buf.append(e.getKey()).append('=').append(e.getValue());
/* 189:191 */       if (iter.hasNext()) {
/* 190:192 */         buf.append(", ");
/* 191:    */       }
/* 192:194 */       assignmentsAppended = true;
/* 193:    */     }
/* 194:196 */     if (this.assignments != null)
/* 195:    */     {
/* 196:197 */       if (assignmentsAppended) {
/* 197:198 */         buf.append(", ");
/* 198:    */       }
/* 199:200 */       buf.append(this.assignments);
/* 200:    */     }
/* 201:203 */     boolean conditionsAppended = false;
/* 202:204 */     if ((!this.primaryKeyColumns.isEmpty()) || (this.where != null) || (!this.whereColumns.isEmpty()) || (this.versionColumnName != null)) {
/* 203:205 */       buf.append(" where ");
/* 204:    */     }
/* 205:207 */     iter = this.primaryKeyColumns.entrySet().iterator();
/* 206:208 */     while (iter.hasNext())
/* 207:    */     {
/* 208:209 */       Map.Entry e = (Map.Entry)iter.next();
/* 209:210 */       buf.append(e.getKey()).append('=').append(e.getValue());
/* 210:211 */       if (iter.hasNext()) {
/* 211:212 */         buf.append(" and ");
/* 212:    */       }
/* 213:214 */       conditionsAppended = true;
/* 214:    */     }
/* 215:216 */     if (this.where != null)
/* 216:    */     {
/* 217:217 */       if (conditionsAppended) {
/* 218:218 */         buf.append(" and ");
/* 219:    */       }
/* 220:220 */       buf.append(this.where);
/* 221:221 */       conditionsAppended = true;
/* 222:    */     }
/* 223:223 */     iter = this.whereColumns.entrySet().iterator();
/* 224:224 */     while (iter.hasNext())
/* 225:    */     {
/* 226:225 */       Map.Entry e = (Map.Entry)iter.next();
/* 227:226 */       if (conditionsAppended) {
/* 228:227 */         buf.append(" and ");
/* 229:    */       }
/* 230:229 */       buf.append(e.getKey()).append(e.getValue());
/* 231:230 */       conditionsAppended = true;
/* 232:    */     }
/* 233:232 */     if (this.versionColumnName != null)
/* 234:    */     {
/* 235:233 */       if (conditionsAppended) {
/* 236:234 */         buf.append(" and ");
/* 237:    */       }
/* 238:236 */       buf.append(this.versionColumnName).append("=?");
/* 239:    */     }
/* 240:239 */     return buf.toString();
/* 241:    */   }
/* 242:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.Update
 * JD-Core Version:    0.7.0.1
 */