/*   1:    */ package org.hibernate.sql;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import org.hibernate.dialect.Dialect;
/*   6:    */ 
/*   7:    */ public class QuerySelect
/*   8:    */ {
/*   9:    */   private Dialect dialect;
/*  10:    */   private JoinFragment joins;
/*  11: 38 */   private StringBuffer select = new StringBuffer();
/*  12: 39 */   private StringBuffer where = new StringBuffer();
/*  13: 40 */   private StringBuffer groupBy = new StringBuffer();
/*  14: 41 */   private StringBuffer orderBy = new StringBuffer();
/*  15: 42 */   private StringBuffer having = new StringBuffer();
/*  16:    */   private String comment;
/*  17: 44 */   private boolean distinct = false;
/*  18: 46 */   private static final HashSet DONT_SPACE_TOKENS = new HashSet();
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22: 49 */     DONT_SPACE_TOKENS.add(".");
/*  23: 50 */     DONT_SPACE_TOKENS.add("+");
/*  24: 51 */     DONT_SPACE_TOKENS.add("-");
/*  25: 52 */     DONT_SPACE_TOKENS.add("/");
/*  26: 53 */     DONT_SPACE_TOKENS.add("*");
/*  27: 54 */     DONT_SPACE_TOKENS.add("<");
/*  28: 55 */     DONT_SPACE_TOKENS.add(">");
/*  29: 56 */     DONT_SPACE_TOKENS.add("=");
/*  30: 57 */     DONT_SPACE_TOKENS.add("#");
/*  31: 58 */     DONT_SPACE_TOKENS.add("~");
/*  32: 59 */     DONT_SPACE_TOKENS.add("|");
/*  33: 60 */     DONT_SPACE_TOKENS.add("&");
/*  34: 61 */     DONT_SPACE_TOKENS.add("<=");
/*  35: 62 */     DONT_SPACE_TOKENS.add(">=");
/*  36: 63 */     DONT_SPACE_TOKENS.add("=>");
/*  37: 64 */     DONT_SPACE_TOKENS.add("=<");
/*  38: 65 */     DONT_SPACE_TOKENS.add("!=");
/*  39: 66 */     DONT_SPACE_TOKENS.add("<>");
/*  40: 67 */     DONT_SPACE_TOKENS.add("!#");
/*  41: 68 */     DONT_SPACE_TOKENS.add("!~");
/*  42: 69 */     DONT_SPACE_TOKENS.add("!<");
/*  43: 70 */     DONT_SPACE_TOKENS.add("!>");
/*  44: 71 */     DONT_SPACE_TOKENS.add("(");
/*  45: 72 */     DONT_SPACE_TOKENS.add(")");
/*  46:    */   }
/*  47:    */   
/*  48:    */   public QuerySelect(Dialect dialect)
/*  49:    */   {
/*  50: 76 */     this.dialect = dialect;
/*  51: 77 */     this.joins = new QueryJoinFragment(dialect, false);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public JoinFragment getJoinFragment()
/*  55:    */   {
/*  56: 81 */     return this.joins;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void addSelectFragmentString(String fragment)
/*  60:    */   {
/*  61: 85 */     if ((fragment.length() > 0) && (fragment.charAt(0) == ',')) {
/*  62: 85 */       fragment = fragment.substring(1);
/*  63:    */     }
/*  64: 86 */     fragment = fragment.trim();
/*  65: 87 */     if (fragment.length() > 0)
/*  66:    */     {
/*  67: 88 */       if (this.select.length() > 0) {
/*  68: 88 */         this.select.append(", ");
/*  69:    */       }
/*  70: 89 */       this.select.append(fragment);
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void addSelectColumn(String columnName, String alias)
/*  75:    */   {
/*  76: 94 */     addSelectFragmentString(columnName + ' ' + alias);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setDistinct(boolean distinct)
/*  80:    */   {
/*  81: 98 */     this.distinct = distinct;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setWhereTokens(Iterator tokens)
/*  85:    */   {
/*  86:103 */     appendTokens(this.where, tokens);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void prependWhereConditions(String conditions)
/*  90:    */   {
/*  91:107 */     if (this.where.length() > 0) {
/*  92:108 */       this.where.insert(0, conditions + " and ");
/*  93:    */     } else {
/*  94:111 */       this.where.append(conditions);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setGroupByTokens(Iterator tokens)
/*  99:    */   {
/* 100:117 */     appendTokens(this.groupBy, tokens);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setOrderByTokens(Iterator tokens)
/* 104:    */   {
/* 105:122 */     appendTokens(this.orderBy, tokens);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setHavingTokens(Iterator tokens)
/* 109:    */   {
/* 110:127 */     appendTokens(this.having, tokens);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void addOrderBy(String orderByString)
/* 114:    */   {
/* 115:131 */     if (this.orderBy.length() > 0) {
/* 116:131 */       this.orderBy.append(", ");
/* 117:    */     }
/* 118:132 */     this.orderBy.append(orderByString);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String toQueryString()
/* 122:    */   {
/* 123:136 */     StringBuffer buf = new StringBuffer(50);
/* 124:137 */     if (this.comment != null) {
/* 125:137 */       buf.append("/* ").append(this.comment).append(" */ ");
/* 126:    */     }
/* 127:138 */     buf.append("select ");
/* 128:139 */     if (this.distinct) {
/* 129:139 */       buf.append("distinct ");
/* 130:    */     }
/* 131:140 */     String from = this.joins.toFromFragmentString();
/* 132:141 */     if (from.startsWith(",")) {
/* 133:142 */       from = from.substring(1);
/* 134:144 */     } else if (from.startsWith(" inner join")) {
/* 135:145 */       from = from.substring(11);
/* 136:    */     }
/* 137:148 */     buf.append(this.select.toString()).append(" from").append(from);
/* 138:    */     
/* 139:    */ 
/* 140:    */ 
/* 141:152 */     String outerJoinsAfterWhere = this.joins.toWhereFragmentString().trim();
/* 142:153 */     String whereConditions = this.where.toString().trim();
/* 143:154 */     boolean hasOuterJoinsAfterWhere = outerJoinsAfterWhere.length() > 0;
/* 144:155 */     boolean hasWhereConditions = whereConditions.length() > 0;
/* 145:156 */     if ((hasOuterJoinsAfterWhere) || (hasWhereConditions))
/* 146:    */     {
/* 147:157 */       buf.append(" where ");
/* 148:158 */       if (hasOuterJoinsAfterWhere) {
/* 149:159 */         buf.append(outerJoinsAfterWhere.substring(4));
/* 150:    */       }
/* 151:161 */       if (hasWhereConditions)
/* 152:    */       {
/* 153:162 */         if (hasOuterJoinsAfterWhere) {
/* 154:163 */           buf.append(" and (");
/* 155:    */         }
/* 156:165 */         buf.append(whereConditions);
/* 157:166 */         if (hasOuterJoinsAfterWhere) {
/* 158:167 */           buf.append(")");
/* 159:    */         }
/* 160:    */       }
/* 161:    */     }
/* 162:172 */     if (this.groupBy.length() > 0) {
/* 163:172 */       buf.append(" group by ").append(this.groupBy.toString());
/* 164:    */     }
/* 165:173 */     if (this.having.length() > 0) {
/* 166:173 */       buf.append(" having ").append(this.having.toString());
/* 167:    */     }
/* 168:174 */     if (this.orderBy.length() > 0) {
/* 169:174 */       buf.append(" order by ").append(this.orderBy.toString());
/* 170:    */     }
/* 171:176 */     return this.dialect.transformSelectString(buf.toString());
/* 172:    */   }
/* 173:    */   
/* 174:    */   private static void appendTokens(StringBuffer buf, Iterator iter)
/* 175:    */   {
/* 176:180 */     boolean lastSpaceable = true;
/* 177:181 */     boolean lastQuoted = false;
/* 178:182 */     while (iter.hasNext())
/* 179:    */     {
/* 180:183 */       String token = (String)iter.next();
/* 181:184 */       boolean spaceable = !DONT_SPACE_TOKENS.contains(token);
/* 182:185 */       boolean quoted = token.startsWith("'");
/* 183:186 */       if ((spaceable) && (lastSpaceable) && (
/* 184:187 */         (!quoted) || (!lastQuoted))) {
/* 185:187 */         buf.append(' ');
/* 186:    */       }
/* 187:189 */       lastSpaceable = spaceable;
/* 188:190 */       buf.append(token);
/* 189:191 */       lastQuoted = token.endsWith("'");
/* 190:    */     }
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setComment(String comment)
/* 194:    */   {
/* 195:196 */     this.comment = comment;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public QuerySelect copy()
/* 199:    */   {
/* 200:200 */     QuerySelect copy = new QuerySelect(this.dialect);
/* 201:201 */     copy.joins = this.joins.copy();
/* 202:202 */     copy.select.append(this.select.toString());
/* 203:203 */     copy.where.append(this.where.toString());
/* 204:204 */     copy.groupBy.append(this.groupBy.toString());
/* 205:205 */     copy.orderBy.append(this.orderBy.toString());
/* 206:206 */     copy.having.append(this.having.toString());
/* 207:207 */     copy.comment = this.comment;
/* 208:208 */     copy.distinct = this.distinct;
/* 209:209 */     return copy;
/* 210:    */   }
/* 211:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.QuerySelect
 * JD-Core Version:    0.7.0.1
 */