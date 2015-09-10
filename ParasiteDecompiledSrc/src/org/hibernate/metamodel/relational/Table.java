/*   1:    */ package org.hibernate.metamodel.relational;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.LinkedHashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import org.hibernate.dialect.Dialect;
/*   8:    */ 
/*   9:    */ public class Table
/*  10:    */   extends AbstractTableSpecification
/*  11:    */   implements Exportable
/*  12:    */ {
/*  13:    */   private final Schema database;
/*  14:    */   private final Identifier tableName;
/*  15:    */   private final ObjectName objectName;
/*  16:    */   private final String qualifiedName;
/*  17: 44 */   private final LinkedHashMap<String, Index> indexes = new LinkedHashMap();
/*  18: 45 */   private final LinkedHashMap<String, UniqueKey> uniqueKeys = new LinkedHashMap();
/*  19: 46 */   private final List<CheckConstraint> checkConstraints = new ArrayList();
/*  20: 47 */   private final List<String> comments = new ArrayList();
/*  21:    */   
/*  22:    */   public Table(Schema database, String tableName)
/*  23:    */   {
/*  24: 50 */     this(database, Identifier.toIdentifier(tableName));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public Table(Schema database, Identifier tableName)
/*  28:    */   {
/*  29: 54 */     this.database = database;
/*  30: 55 */     this.tableName = tableName;
/*  31: 56 */     this.objectName = new ObjectName(database.getName().getSchema(), database.getName().getCatalog(), tableName);
/*  32: 57 */     this.qualifiedName = this.objectName.toText();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Schema getSchema()
/*  36:    */   {
/*  37: 62 */     return this.database;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Identifier getTableName()
/*  41:    */   {
/*  42: 66 */     return this.tableName;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String getLoggableValueQualifier()
/*  46:    */   {
/*  47: 71 */     return this.qualifiedName;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String getExportIdentifier()
/*  51:    */   {
/*  52: 76 */     return this.qualifiedName;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toLoggableString()
/*  56:    */   {
/*  57: 81 */     return this.qualifiedName;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Iterable<Index> getIndexes()
/*  61:    */   {
/*  62: 86 */     return this.indexes.values();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Index getOrCreateIndex(String name)
/*  66:    */   {
/*  67: 90 */     if (this.indexes.containsKey(name)) {
/*  68: 91 */       return (Index)this.indexes.get(name);
/*  69:    */     }
/*  70: 93 */     Index index = new Index(this, name);
/*  71: 94 */     this.indexes.put(name, index);
/*  72: 95 */     return index;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Iterable<UniqueKey> getUniqueKeys()
/*  76:    */   {
/*  77:100 */     return this.uniqueKeys.values();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public UniqueKey getOrCreateUniqueKey(String name)
/*  81:    */   {
/*  82:104 */     if (this.uniqueKeys.containsKey(name)) {
/*  83:105 */       return (UniqueKey)this.uniqueKeys.get(name);
/*  84:    */     }
/*  85:107 */     UniqueKey uniqueKey = new UniqueKey(this, name);
/*  86:108 */     this.uniqueKeys.put(name, uniqueKey);
/*  87:109 */     return uniqueKey;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Iterable<CheckConstraint> getCheckConstraints()
/*  91:    */   {
/*  92:114 */     return this.checkConstraints;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void addCheckConstraint(String checkCondition)
/*  96:    */   {
/*  97:121 */     this.checkConstraints.add(new CheckConstraint(this, "", checkCondition));
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Iterable<String> getComments()
/* 101:    */   {
/* 102:126 */     return this.comments;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void addComment(String comment)
/* 106:    */   {
/* 107:131 */     this.comments.add(comment);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getQualifiedName(Dialect dialect)
/* 111:    */   {
/* 112:136 */     return this.objectName.toText(dialect);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String[] sqlCreateStrings(Dialect dialect)
/* 116:    */   {
/* 117:140 */     boolean hasPrimaryKey = getPrimaryKey().getColumns().iterator().hasNext();
/* 118:141 */     StringBuilder buf = new StringBuilder(hasPrimaryKey ? dialect.getCreateTableString() : dialect.getCreateMultisetTableString()).append(' ').append(this.objectName.toText(dialect)).append(" (");
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:152 */     boolean isPrimaryKeyIdentity = false;
/* 130:    */     
/* 131:    */ 
/* 132:155 */     String pkColName = null;
/* 133:156 */     if ((hasPrimaryKey) && (isPrimaryKeyIdentity))
/* 134:    */     {
/* 135:157 */       Column pkColumn = (Column)getPrimaryKey().getColumns().iterator().next();
/* 136:158 */       pkColName = pkColumn.getColumnName().encloseInQuotesIfQuoted(dialect);
/* 137:    */     }
/* 138:161 */     boolean isFirst = true;
/* 139:162 */     for (SimpleValue simpleValue : values()) {
/* 140:163 */       if (Column.class.isInstance(simpleValue))
/* 141:    */       {
/* 142:166 */         if (isFirst) {
/* 143:167 */           isFirst = false;
/* 144:    */         } else {
/* 145:170 */           buf.append(", ");
/* 146:    */         }
/* 147:172 */         Column col = (Column)simpleValue;
/* 148:173 */         String colName = col.getColumnName().encloseInQuotesIfQuoted(dialect);
/* 149:    */         
/* 150:175 */         buf.append(colName).append(' ');
/* 151:177 */         if ((isPrimaryKeyIdentity) && (colName.equals(pkColName)))
/* 152:    */         {
/* 153:179 */           if (dialect.hasDataTypeInIdentityColumn()) {
/* 154:180 */             buf.append(getTypeString(col, dialect));
/* 155:    */           }
/* 156:182 */           buf.append(' ').append(dialect.getIdentityColumnString(col.getDatatype().getTypeCode()));
/* 157:    */         }
/* 158:    */         else
/* 159:    */         {
/* 160:186 */           buf.append(getTypeString(col, dialect));
/* 161:    */           
/* 162:188 */           String defaultValue = col.getDefaultValue();
/* 163:189 */           if (defaultValue != null) {
/* 164:190 */             buf.append(" default ").append(defaultValue);
/* 165:    */           }
/* 166:193 */           if (col.isNullable()) {
/* 167:194 */             buf.append(dialect.getNullColumnString());
/* 168:    */           } else {
/* 169:197 */             buf.append(" not null");
/* 170:    */           }
/* 171:    */         }
/* 172:202 */         boolean useUniqueConstraint = (col.isUnique()) && ((!col.isNullable()) || (dialect.supportsNotNullUnique()));
/* 173:204 */         if (useUniqueConstraint) {
/* 174:205 */           if (dialect.supportsUnique())
/* 175:    */           {
/* 176:206 */             buf.append(" unique");
/* 177:    */           }
/* 178:    */           else
/* 179:    */           {
/* 180:209 */             UniqueKey uk = getOrCreateUniqueKey(col.getColumnName().encloseInQuotesIfQuoted(dialect) + '_');
/* 181:210 */             uk.addColumn(col);
/* 182:    */           }
/* 183:    */         }
/* 184:214 */         if ((col.getCheckCondition() != null) && (dialect.supportsColumnCheck())) {
/* 185:215 */           buf.append(" check (").append(col.getCheckCondition()).append(")");
/* 186:    */         }
/* 187:220 */         String columnComment = col.getComment();
/* 188:221 */         if (columnComment != null) {
/* 189:222 */           buf.append(dialect.getColumnComment(columnComment));
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:225 */     if (hasPrimaryKey) {
/* 194:226 */       buf.append(", ").append(getPrimaryKey().sqlConstraintStringInCreateTable(dialect));
/* 195:    */     }
/* 196:230 */     if (dialect.supportsUniqueConstraintInCreateAlterTable()) {
/* 197:231 */       for (UniqueKey uk : this.uniqueKeys.values())
/* 198:    */       {
/* 199:232 */         String constraint = uk.sqlConstraintStringInCreateTable(dialect);
/* 200:233 */         if (constraint != null) {
/* 201:234 */           buf.append(", ").append(constraint);
/* 202:    */         }
/* 203:    */       }
/* 204:    */     }
/* 205:239 */     if (dialect.supportsTableCheck()) {
/* 206:240 */       for (CheckConstraint checkConstraint : this.checkConstraints) {
/* 207:241 */         buf.append(", check (").append(checkConstraint).append(')');
/* 208:    */       }
/* 209:    */     }
/* 210:247 */     buf.append(')');
/* 211:248 */     buf.append(dialect.getTableTypeString());
/* 212:    */     
/* 213:250 */     String[] sqlStrings = new String[this.comments.size() + 1];
/* 214:251 */     sqlStrings[0] = buf.toString();
/* 215:253 */     for (int i = 0; i < this.comments.size(); i++) {
/* 216:254 */       sqlStrings[(i + 1)] = dialect.getTableComment((String)this.comments.get(i));
/* 217:    */     }
/* 218:257 */     return sqlStrings;
/* 219:    */   }
/* 220:    */   
/* 221:    */   private static String getTypeString(Column col, Dialect dialect)
/* 222:    */   {
/* 223:261 */     String typeString = null;
/* 224:262 */     if (col.getSqlType() != null)
/* 225:    */     {
/* 226:263 */       typeString = col.getSqlType();
/* 227:    */     }
/* 228:    */     else
/* 229:    */     {
/* 230:266 */       Size size = col.getSize() == null ? new Size() : col.getSize();
/* 231:    */       
/* 232:    */ 
/* 233:    */ 
/* 234:270 */       typeString = dialect.getTypeName(col.getDatatype().getTypeCode(), size.getLength(), size.getPrecision(), size.getScale());
/* 235:    */     }
/* 236:277 */     return typeString;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String[] sqlDropStrings(Dialect dialect)
/* 240:    */   {
/* 241:282 */     StringBuilder buf = new StringBuilder("drop table ");
/* 242:283 */     if (dialect.supportsIfExistsBeforeTableName()) {
/* 243:284 */       buf.append("if exists ");
/* 244:    */     }
/* 245:286 */     buf.append(getQualifiedName(dialect)).append(dialect.getCascadeConstraintsString());
/* 246:288 */     if (dialect.supportsIfExistsAfterTableName()) {
/* 247:289 */       buf.append(" if exists");
/* 248:    */     }
/* 249:291 */     return new String[] { buf.toString() };
/* 250:    */   }
/* 251:    */   
/* 252:    */   public String toString()
/* 253:    */   {
/* 254:296 */     return "Table{name=" + this.qualifiedName + '}';
/* 255:    */   }
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Table
 * JD-Core Version:    0.7.0.1
 */