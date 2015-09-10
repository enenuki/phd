/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   9:    */ import org.hibernate.type.Type;
/*  10:    */ 
/*  11:    */ public class Restrictions
/*  12:    */ {
/*  13:    */   public static Criterion idEq(Object value)
/*  14:    */   {
/*  15: 55 */     return new IdentifierEqExpression(value);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static SimpleExpression eq(String propertyName, Object value)
/*  19:    */   {
/*  20: 64 */     return new SimpleExpression(propertyName, value, "=");
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static SimpleExpression ne(String propertyName, Object value)
/*  24:    */   {
/*  25: 73 */     return new SimpleExpression(propertyName, value, "<>");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static SimpleExpression like(String propertyName, Object value)
/*  29:    */   {
/*  30: 82 */     return new SimpleExpression(propertyName, value, " like ");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static SimpleExpression like(String propertyName, String value, MatchMode matchMode)
/*  34:    */   {
/*  35: 91 */     return new SimpleExpression(propertyName, matchMode.toMatchString(value), " like ");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static Criterion ilike(String propertyName, String value, MatchMode matchMode)
/*  39:    */   {
/*  40:102 */     return new LikeExpression(propertyName, value, matchMode, null, true);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static Criterion ilike(String propertyName, Object value)
/*  44:    */   {
/*  45:113 */     return new LikeExpression(propertyName, value.toString());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static SimpleExpression gt(String propertyName, Object value)
/*  49:    */   {
/*  50:122 */     return new SimpleExpression(propertyName, value, ">");
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static SimpleExpression lt(String propertyName, Object value)
/*  54:    */   {
/*  55:131 */     return new SimpleExpression(propertyName, value, "<");
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static SimpleExpression le(String propertyName, Object value)
/*  59:    */   {
/*  60:140 */     return new SimpleExpression(propertyName, value, "<=");
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static SimpleExpression ge(String propertyName, Object value)
/*  64:    */   {
/*  65:149 */     return new SimpleExpression(propertyName, value, ">=");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static Criterion between(String propertyName, Object lo, Object hi)
/*  69:    */   {
/*  70:159 */     return new BetweenExpression(propertyName, lo, hi);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static Criterion in(String propertyName, Object[] values)
/*  74:    */   {
/*  75:168 */     return new InExpression(propertyName, values);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static Criterion in(String propertyName, Collection values)
/*  79:    */   {
/*  80:177 */     return new InExpression(propertyName, values.toArray());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static Criterion isNull(String propertyName)
/*  84:    */   {
/*  85:184 */     return new NullExpression(propertyName);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static PropertyExpression eqProperty(String propertyName, String otherPropertyName)
/*  89:    */   {
/*  90:190 */     return new PropertyExpression(propertyName, otherPropertyName, "=");
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static PropertyExpression neProperty(String propertyName, String otherPropertyName)
/*  94:    */   {
/*  95:196 */     return new PropertyExpression(propertyName, otherPropertyName, "<>");
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static PropertyExpression ltProperty(String propertyName, String otherPropertyName)
/*  99:    */   {
/* 100:202 */     return new PropertyExpression(propertyName, otherPropertyName, "<");
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static PropertyExpression leProperty(String propertyName, String otherPropertyName)
/* 104:    */   {
/* 105:208 */     return new PropertyExpression(propertyName, otherPropertyName, "<=");
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static PropertyExpression gtProperty(String propertyName, String otherPropertyName)
/* 109:    */   {
/* 110:214 */     return new PropertyExpression(propertyName, otherPropertyName, ">");
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static PropertyExpression geProperty(String propertyName, String otherPropertyName)
/* 114:    */   {
/* 115:220 */     return new PropertyExpression(propertyName, otherPropertyName, ">=");
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static Criterion isNotNull(String propertyName)
/* 119:    */   {
/* 120:227 */     return new NotNullExpression(propertyName);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static LogicalExpression and(Criterion lhs, Criterion rhs)
/* 124:    */   {
/* 125:237 */     return new LogicalExpression(lhs, rhs, "and");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static LogicalExpression or(Criterion lhs, Criterion rhs)
/* 129:    */   {
/* 130:247 */     return new LogicalExpression(lhs, rhs, "or");
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static Criterion not(Criterion expression)
/* 134:    */   {
/* 135:256 */     return new NotExpression(expression);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static Criterion sqlRestriction(String sql, Object[] values, Type[] types)
/* 139:    */   {
/* 140:269 */     return new SQLCriterion(sql, values, types);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static Criterion sqlRestriction(String sql, Object value, Type type)
/* 144:    */   {
/* 145:282 */     return new SQLCriterion(sql, new Object[] { value }, new Type[] { type });
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static Criterion sqlRestriction(String sql)
/* 149:    */   {
/* 150:292 */     return new SQLCriterion(sql, ArrayHelper.EMPTY_OBJECT_ARRAY, ArrayHelper.EMPTY_TYPE_ARRAY);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static Conjunction conjunction()
/* 154:    */   {
/* 155:301 */     return new Conjunction();
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static Disjunction disjunction()
/* 159:    */   {
/* 160:310 */     return new Disjunction();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static Criterion allEq(Map propertyNameValues)
/* 164:    */   {
/* 165:321 */     Conjunction conj = conjunction();
/* 166:322 */     Iterator iter = propertyNameValues.entrySet().iterator();
/* 167:323 */     while (iter.hasNext())
/* 168:    */     {
/* 169:324 */       Map.Entry me = (Map.Entry)iter.next();
/* 170:325 */       conj.add(eq((String)me.getKey(), me.getValue()));
/* 171:    */     }
/* 172:327 */     return conj;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static Criterion isEmpty(String propertyName)
/* 176:    */   {
/* 177:334 */     return new EmptyExpression(propertyName);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static Criterion isNotEmpty(String propertyName)
/* 181:    */   {
/* 182:341 */     return new NotEmptyExpression(propertyName);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public static Criterion sizeEq(String propertyName, int size)
/* 186:    */   {
/* 187:348 */     return new SizeExpression(propertyName, size, "=");
/* 188:    */   }
/* 189:    */   
/* 190:    */   public static Criterion sizeNe(String propertyName, int size)
/* 191:    */   {
/* 192:355 */     return new SizeExpression(propertyName, size, "<>");
/* 193:    */   }
/* 194:    */   
/* 195:    */   public static Criterion sizeGt(String propertyName, int size)
/* 196:    */   {
/* 197:362 */     return new SizeExpression(propertyName, size, "<");
/* 198:    */   }
/* 199:    */   
/* 200:    */   public static Criterion sizeLt(String propertyName, int size)
/* 201:    */   {
/* 202:369 */     return new SizeExpression(propertyName, size, ">");
/* 203:    */   }
/* 204:    */   
/* 205:    */   public static Criterion sizeGe(String propertyName, int size)
/* 206:    */   {
/* 207:376 */     return new SizeExpression(propertyName, size, "<=");
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static Criterion sizeLe(String propertyName, int size)
/* 211:    */   {
/* 212:383 */     return new SizeExpression(propertyName, size, ">=");
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static NaturalIdentifier naturalId()
/* 216:    */   {
/* 217:387 */     return new NaturalIdentifier();
/* 218:    */   }
/* 219:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Restrictions
 * JD-Core Version:    0.7.0.1
 */