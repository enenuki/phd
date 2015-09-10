/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ public class Subqueries
/*   4:    */ {
/*   5:    */   public static Criterion exists(DetachedCriteria dc)
/*   6:    */   {
/*   7: 41 */     return new ExistsSubqueryExpression("exists", dc);
/*   8:    */   }
/*   9:    */   
/*  10:    */   public static Criterion notExists(DetachedCriteria dc)
/*  11:    */   {
/*  12: 45 */     return new ExistsSubqueryExpression("not exists", dc);
/*  13:    */   }
/*  14:    */   
/*  15:    */   public static Criterion propertyEqAll(String propertyName, DetachedCriteria dc)
/*  16:    */   {
/*  17: 49 */     return new PropertySubqueryExpression(propertyName, "=", "all", dc);
/*  18:    */   }
/*  19:    */   
/*  20:    */   public static Criterion propertyIn(String propertyName, DetachedCriteria dc)
/*  21:    */   {
/*  22: 53 */     return new PropertySubqueryExpression(propertyName, "in", null, dc);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Criterion propertyNotIn(String propertyName, DetachedCriteria dc)
/*  26:    */   {
/*  27: 57 */     return new PropertySubqueryExpression(propertyName, "not in", null, dc);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Criterion propertyEq(String propertyName, DetachedCriteria dc)
/*  31:    */   {
/*  32: 61 */     return new PropertySubqueryExpression(propertyName, "=", null, dc);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Criterion propertiesEq(String[] propertyNames, DetachedCriteria dc)
/*  36:    */   {
/*  37: 65 */     return new PropertiesSubqueryExpression(propertyNames, "=", dc);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static Criterion propertiesNotEq(String[] propertyNames, DetachedCriteria dc)
/*  41:    */   {
/*  42: 69 */     return new PropertiesSubqueryExpression(propertyNames, "<>", dc);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Criterion propertiesIn(String[] propertyNames, DetachedCriteria dc)
/*  46:    */   {
/*  47: 73 */     return new PropertiesSubqueryExpression(propertyNames, "in", dc);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static Criterion propertiesNotIn(String[] propertyNames, DetachedCriteria dc)
/*  51:    */   {
/*  52: 77 */     return new PropertiesSubqueryExpression(propertyNames, "not in", dc);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Criterion propertyNe(String propertyName, DetachedCriteria dc)
/*  56:    */   {
/*  57: 81 */     return new PropertySubqueryExpression(propertyName, "<>", null, dc);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static Criterion propertyGt(String propertyName, DetachedCriteria dc)
/*  61:    */   {
/*  62: 85 */     return new PropertySubqueryExpression(propertyName, ">", null, dc);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Criterion propertyLt(String propertyName, DetachedCriteria dc)
/*  66:    */   {
/*  67: 89 */     return new PropertySubqueryExpression(propertyName, "<", null, dc);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static Criterion propertyGe(String propertyName, DetachedCriteria dc)
/*  71:    */   {
/*  72: 93 */     return new PropertySubqueryExpression(propertyName, ">=", null, dc);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Criterion propertyLe(String propertyName, DetachedCriteria dc)
/*  76:    */   {
/*  77: 97 */     return new PropertySubqueryExpression(propertyName, "<=", null, dc);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static Criterion propertyGtAll(String propertyName, DetachedCriteria dc)
/*  81:    */   {
/*  82:101 */     return new PropertySubqueryExpression(propertyName, ">", "all", dc);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static Criterion propertyLtAll(String propertyName, DetachedCriteria dc)
/*  86:    */   {
/*  87:105 */     return new PropertySubqueryExpression(propertyName, "<", "all", dc);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static Criterion propertyGeAll(String propertyName, DetachedCriteria dc)
/*  91:    */   {
/*  92:109 */     return new PropertySubqueryExpression(propertyName, ">=", "all", dc);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static Criterion propertyLeAll(String propertyName, DetachedCriteria dc)
/*  96:    */   {
/*  97:113 */     return new PropertySubqueryExpression(propertyName, "<=", "all", dc);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static Criterion propertyGtSome(String propertyName, DetachedCriteria dc)
/* 101:    */   {
/* 102:117 */     return new PropertySubqueryExpression(propertyName, ">", "some", dc);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Criterion propertyLtSome(String propertyName, DetachedCriteria dc)
/* 106:    */   {
/* 107:121 */     return new PropertySubqueryExpression(propertyName, "<", "some", dc);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static Criterion propertyGeSome(String propertyName, DetachedCriteria dc)
/* 111:    */   {
/* 112:125 */     return new PropertySubqueryExpression(propertyName, ">=", "some", dc);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static Criterion propertyLeSome(String propertyName, DetachedCriteria dc)
/* 116:    */   {
/* 117:129 */     return new PropertySubqueryExpression(propertyName, "<=", "some", dc);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public static Criterion eqAll(Object value, DetachedCriteria dc)
/* 121:    */   {
/* 122:133 */     return new SimpleSubqueryExpression(value, "=", "all", dc);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static Criterion in(Object value, DetachedCriteria dc)
/* 126:    */   {
/* 127:137 */     return new SimpleSubqueryExpression(value, "in", null, dc);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static Criterion notIn(Object value, DetachedCriteria dc)
/* 131:    */   {
/* 132:141 */     return new SimpleSubqueryExpression(value, "not in", null, dc);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static Criterion eq(Object value, DetachedCriteria dc)
/* 136:    */   {
/* 137:145 */     return new SimpleSubqueryExpression(value, "=", null, dc);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static Criterion gt(Object value, DetachedCriteria dc)
/* 141:    */   {
/* 142:149 */     return new SimpleSubqueryExpression(value, ">", null, dc);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public static Criterion lt(Object value, DetachedCriteria dc)
/* 146:    */   {
/* 147:153 */     return new SimpleSubqueryExpression(value, "<", null, dc);
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static Criterion ge(Object value, DetachedCriteria dc)
/* 151:    */   {
/* 152:157 */     return new SimpleSubqueryExpression(value, ">=", null, dc);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static Criterion le(Object value, DetachedCriteria dc)
/* 156:    */   {
/* 157:161 */     return new SimpleSubqueryExpression(value, "<=", null, dc);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static Criterion ne(Object value, DetachedCriteria dc)
/* 161:    */   {
/* 162:165 */     return new SimpleSubqueryExpression(value, "<>", null, dc);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static Criterion gtAll(Object value, DetachedCriteria dc)
/* 166:    */   {
/* 167:169 */     return new SimpleSubqueryExpression(value, ">", "all", dc);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static Criterion ltAll(Object value, DetachedCriteria dc)
/* 171:    */   {
/* 172:173 */     return new SimpleSubqueryExpression(value, "<", "all", dc);
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static Criterion geAll(Object value, DetachedCriteria dc)
/* 176:    */   {
/* 177:177 */     return new SimpleSubqueryExpression(value, ">=", "all", dc);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static Criterion leAll(Object value, DetachedCriteria dc)
/* 181:    */   {
/* 182:181 */     return new SimpleSubqueryExpression(value, "<=", "all", dc);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static Criterion gtSome(Object value, DetachedCriteria dc)
/* 186:    */   {
/* 187:185 */     return new SimpleSubqueryExpression(value, ">", "some", dc);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static Criterion ltSome(Object value, DetachedCriteria dc)
/* 191:    */   {
/* 192:189 */     return new SimpleSubqueryExpression(value, "<", "some", dc);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static Criterion geSome(Object value, DetachedCriteria dc)
/* 196:    */   {
/* 197:193 */     return new SimpleSubqueryExpression(value, ">=", "some", dc);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static Criterion leSome(Object value, DetachedCriteria dc)
/* 201:    */   {
/* 202:197 */     return new SimpleSubqueryExpression(value, "<=", "some", dc);
/* 203:    */   }
/* 204:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Subqueries
 * JD-Core Version:    0.7.0.1
 */