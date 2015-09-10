/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.LockMode;
/*  11:    */ import org.hibernate.LockOptions;
/*  12:    */ import org.hibernate.dialect.Dialect;
/*  13:    */ 
/*  14:    */ public class SimpleSelect
/*  15:    */ {
/*  16:    */   private String tableName;
/*  17:    */   private String orderBy;
/*  18:    */   private Dialect dialect;
/*  19:    */   
/*  20:    */   public SimpleSelect(Dialect dialect)
/*  21:    */   {
/*  22: 46 */     this.dialect = dialect;
/*  23:    */   }
/*  24:    */   
/*  25: 54 */   private LockOptions lockOptions = new LockOptions(LockMode.READ);
/*  26:    */   private String comment;
/*  27: 57 */   private List columns = new ArrayList();
/*  28: 58 */   private Map aliases = new HashMap();
/*  29: 59 */   private List whereTokens = new ArrayList();
/*  30:    */   
/*  31:    */   public SimpleSelect addColumns(String[] columnNames, String[] columnAliases)
/*  32:    */   {
/*  33: 62 */     for (int i = 0; i < columnNames.length; i++) {
/*  34: 63 */       if (columnNames[i] != null) {
/*  35: 64 */         addColumn(columnNames[i], columnAliases[i]);
/*  36:    */       }
/*  37:    */     }
/*  38: 67 */     return this;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public SimpleSelect addColumns(String[] columns, String[] aliases, boolean[] ignore)
/*  42:    */   {
/*  43: 71 */     for (int i = 0; i < ignore.length; i++) {
/*  44: 72 */       if ((ignore[i] == 0) && (columns[i] != null)) {
/*  45: 73 */         addColumn(columns[i], aliases[i]);
/*  46:    */       }
/*  47:    */     }
/*  48: 76 */     return this;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public SimpleSelect addColumns(String[] columnNames)
/*  52:    */   {
/*  53: 80 */     for (int i = 0; i < columnNames.length; i++) {
/*  54: 81 */       if (columnNames[i] != null) {
/*  55: 81 */         addColumn(columnNames[i]);
/*  56:    */       }
/*  57:    */     }
/*  58: 83 */     return this;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public SimpleSelect addColumn(String columnName)
/*  62:    */   {
/*  63: 86 */     this.columns.add(columnName);
/*  64:    */     
/*  65: 88 */     return this;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public SimpleSelect addColumn(String columnName, String alias)
/*  69:    */   {
/*  70: 92 */     this.columns.add(columnName);
/*  71: 93 */     this.aliases.put(columnName, alias);
/*  72: 94 */     return this;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public SimpleSelect setTableName(String tableName)
/*  76:    */   {
/*  77: 98 */     this.tableName = tableName;
/*  78: 99 */     return this;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public SimpleSelect setLockOptions(LockOptions lockOptions)
/*  82:    */   {
/*  83:103 */     LockOptions.copy(lockOptions, this.lockOptions);
/*  84:104 */     return this;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public SimpleSelect setLockMode(LockMode lockMode)
/*  88:    */   {
/*  89:108 */     this.lockOptions.setLockMode(lockMode);
/*  90:109 */     return this;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public SimpleSelect addWhereToken(String token)
/*  94:    */   {
/*  95:113 */     this.whereTokens.add(token);
/*  96:114 */     return this;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void and()
/* 100:    */   {
/* 101:118 */     if (this.whereTokens.size() > 0) {
/* 102:119 */       this.whereTokens.add("and");
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public SimpleSelect addCondition(String lhs, String op, String rhs)
/* 107:    */   {
/* 108:124 */     and();
/* 109:125 */     this.whereTokens.add(lhs + ' ' + op + ' ' + rhs);
/* 110:126 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public SimpleSelect addCondition(String lhs, String condition)
/* 114:    */   {
/* 115:130 */     and();
/* 116:131 */     this.whereTokens.add(lhs + ' ' + condition);
/* 117:132 */     return this;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public SimpleSelect addCondition(String[] lhs, String op, String[] rhs)
/* 121:    */   {
/* 122:136 */     for (int i = 0; i < lhs.length; i++) {
/* 123:137 */       addCondition(lhs[i], op, rhs[i]);
/* 124:    */     }
/* 125:139 */     return this;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public SimpleSelect addCondition(String[] lhs, String condition)
/* 129:    */   {
/* 130:143 */     for (int i = 0; i < lhs.length; i++) {
/* 131:144 */       if (lhs[i] != null) {
/* 132:144 */         addCondition(lhs[i], condition);
/* 133:    */       }
/* 134:    */     }
/* 135:146 */     return this;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String toStatementString()
/* 139:    */   {
/* 140:150 */     StringBuffer buf = new StringBuffer(this.columns.size() * 10 + this.tableName.length() + this.whereTokens.size() * 10 + 10);
/* 141:157 */     if (this.comment != null) {
/* 142:158 */       buf.append("/* ").append(this.comment).append(" */ ");
/* 143:    */     }
/* 144:161 */     buf.append("select ");
/* 145:162 */     Set uniqueColumns = new HashSet();
/* 146:163 */     Iterator iter = this.columns.iterator();
/* 147:164 */     boolean appendComma = false;
/* 148:165 */     while (iter.hasNext())
/* 149:    */     {
/* 150:166 */       String col = (String)iter.next();
/* 151:167 */       String alias = (String)this.aliases.get(col);
/* 152:168 */       if (uniqueColumns.add(alias == null ? col : alias))
/* 153:    */       {
/* 154:169 */         if (appendComma) {
/* 155:169 */           buf.append(", ");
/* 156:    */         }
/* 157:170 */         buf.append(col);
/* 158:171 */         if ((alias != null) && (!alias.equals(col))) {
/* 159:172 */           buf.append(" as ").append(alias);
/* 160:    */         }
/* 161:175 */         appendComma = true;
/* 162:    */       }
/* 163:    */     }
/* 164:179 */     buf.append(" from ").append(this.dialect.appendLockHint(this.lockOptions.getLockMode(), this.tableName));
/* 165:182 */     if (this.whereTokens.size() > 0) {
/* 166:183 */       buf.append(" where ").append(toWhereClause());
/* 167:    */     }
/* 168:187 */     if (this.orderBy != null) {
/* 169:187 */       buf.append(this.orderBy);
/* 170:    */     }
/* 171:189 */     if (this.lockOptions != null) {
/* 172:190 */       buf.append(this.dialect.getForUpdateString(this.lockOptions));
/* 173:    */     }
/* 174:193 */     return this.dialect.transformSelectString(buf.toString());
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String toWhereClause()
/* 178:    */   {
/* 179:197 */     StringBuffer buf = new StringBuffer(this.whereTokens.size() * 5);
/* 180:198 */     Iterator iter = this.whereTokens.iterator();
/* 181:199 */     while (iter.hasNext())
/* 182:    */     {
/* 183:200 */       buf.append(iter.next());
/* 184:201 */       if (iter.hasNext()) {
/* 185:201 */         buf.append(' ');
/* 186:    */       }
/* 187:    */     }
/* 188:203 */     return buf.toString();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public SimpleSelect setOrderBy(String orderBy)
/* 192:    */   {
/* 193:207 */     this.orderBy = orderBy;
/* 194:208 */     return this;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public SimpleSelect setComment(String comment)
/* 198:    */   {
/* 199:212 */     this.comment = comment;
/* 200:213 */     return this;
/* 201:    */   }
/* 202:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.SimpleSelect
 * JD-Core Version:    0.7.0.1
 */