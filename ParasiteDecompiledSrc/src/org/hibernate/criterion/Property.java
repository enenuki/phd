/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ 
/*   5:    */ public class Property
/*   6:    */   extends PropertyProjection
/*   7:    */ {
/*   8:    */   protected Property(String propertyName)
/*   9:    */   {
/*  10: 35 */     super(propertyName);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public Criterion between(Object min, Object max)
/*  14:    */   {
/*  15: 39 */     return Restrictions.between(getPropertyName(), min, max);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public Criterion in(Collection values)
/*  19:    */   {
/*  20: 43 */     return Restrictions.in(getPropertyName(), values);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public Criterion in(Object[] values)
/*  24:    */   {
/*  25: 47 */     return Restrictions.in(getPropertyName(), values);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public SimpleExpression like(Object value)
/*  29:    */   {
/*  30: 51 */     return Restrictions.like(getPropertyName(), value);
/*  31:    */   }
/*  32:    */   
/*  33:    */   public SimpleExpression like(String value, MatchMode matchMode)
/*  34:    */   {
/*  35: 55 */     return Restrictions.like(getPropertyName(), value, matchMode);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public SimpleExpression eq(Object value)
/*  39:    */   {
/*  40: 59 */     return Restrictions.eq(getPropertyName(), value);
/*  41:    */   }
/*  42:    */   
/*  43:    */   public SimpleExpression ne(Object value)
/*  44:    */   {
/*  45: 63 */     return Restrictions.ne(getPropertyName(), value);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public SimpleExpression gt(Object value)
/*  49:    */   {
/*  50: 67 */     return Restrictions.gt(getPropertyName(), value);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public SimpleExpression lt(Object value)
/*  54:    */   {
/*  55: 71 */     return Restrictions.lt(getPropertyName(), value);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public SimpleExpression le(Object value)
/*  59:    */   {
/*  60: 75 */     return Restrictions.le(getPropertyName(), value);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public SimpleExpression ge(Object value)
/*  64:    */   {
/*  65: 79 */     return Restrictions.ge(getPropertyName(), value);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public PropertyExpression eqProperty(Property other)
/*  69:    */   {
/*  70: 83 */     return Restrictions.eqProperty(getPropertyName(), other.getPropertyName());
/*  71:    */   }
/*  72:    */   
/*  73:    */   public PropertyExpression neProperty(Property other)
/*  74:    */   {
/*  75: 87 */     return Restrictions.neProperty(getPropertyName(), other.getPropertyName());
/*  76:    */   }
/*  77:    */   
/*  78:    */   public PropertyExpression leProperty(Property other)
/*  79:    */   {
/*  80: 91 */     return Restrictions.leProperty(getPropertyName(), other.getPropertyName());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public PropertyExpression geProperty(Property other)
/*  84:    */   {
/*  85: 95 */     return Restrictions.geProperty(getPropertyName(), other.getPropertyName());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public PropertyExpression ltProperty(Property other)
/*  89:    */   {
/*  90: 99 */     return Restrictions.ltProperty(getPropertyName(), other.getPropertyName());
/*  91:    */   }
/*  92:    */   
/*  93:    */   public PropertyExpression gtProperty(Property other)
/*  94:    */   {
/*  95:103 */     return Restrictions.gtProperty(getPropertyName(), other.getPropertyName());
/*  96:    */   }
/*  97:    */   
/*  98:    */   public PropertyExpression eqProperty(String other)
/*  99:    */   {
/* 100:107 */     return Restrictions.eqProperty(getPropertyName(), other);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public PropertyExpression neProperty(String other)
/* 104:    */   {
/* 105:111 */     return Restrictions.neProperty(getPropertyName(), other);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public PropertyExpression leProperty(String other)
/* 109:    */   {
/* 110:115 */     return Restrictions.leProperty(getPropertyName(), other);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public PropertyExpression geProperty(String other)
/* 114:    */   {
/* 115:119 */     return Restrictions.geProperty(getPropertyName(), other);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public PropertyExpression ltProperty(String other)
/* 119:    */   {
/* 120:123 */     return Restrictions.ltProperty(getPropertyName(), other);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public PropertyExpression gtProperty(String other)
/* 124:    */   {
/* 125:127 */     return Restrictions.gtProperty(getPropertyName(), other);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public Criterion isNull()
/* 129:    */   {
/* 130:131 */     return Restrictions.isNull(getPropertyName());
/* 131:    */   }
/* 132:    */   
/* 133:    */   public Criterion isNotNull()
/* 134:    */   {
/* 135:135 */     return Restrictions.isNotNull(getPropertyName());
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Criterion isEmpty()
/* 139:    */   {
/* 140:139 */     return Restrictions.isEmpty(getPropertyName());
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Criterion isNotEmpty()
/* 144:    */   {
/* 145:143 */     return Restrictions.isNotEmpty(getPropertyName());
/* 146:    */   }
/* 147:    */   
/* 148:    */   public CountProjection count()
/* 149:    */   {
/* 150:147 */     return Projections.count(getPropertyName());
/* 151:    */   }
/* 152:    */   
/* 153:    */   public AggregateProjection max()
/* 154:    */   {
/* 155:151 */     return Projections.max(getPropertyName());
/* 156:    */   }
/* 157:    */   
/* 158:    */   public AggregateProjection min()
/* 159:    */   {
/* 160:155 */     return Projections.min(getPropertyName());
/* 161:    */   }
/* 162:    */   
/* 163:    */   public AggregateProjection avg()
/* 164:    */   {
/* 165:159 */     return Projections.avg(getPropertyName());
/* 166:    */   }
/* 167:    */   
/* 168:    */   public PropertyProjection group()
/* 169:    */   {
/* 170:167 */     return Projections.groupProperty(getPropertyName());
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Order asc()
/* 174:    */   {
/* 175:171 */     return Order.asc(getPropertyName());
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Order desc()
/* 179:    */   {
/* 180:175 */     return Order.desc(getPropertyName());
/* 181:    */   }
/* 182:    */   
/* 183:    */   public static Property forName(String propertyName)
/* 184:    */   {
/* 185:179 */     return new Property(propertyName);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public Property getProperty(String propertyName)
/* 189:    */   {
/* 190:186 */     return forName(getPropertyName() + '.' + propertyName);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public Criterion eq(DetachedCriteria subselect)
/* 194:    */   {
/* 195:190 */     return Subqueries.propertyEq(getPropertyName(), subselect);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public Criterion ne(DetachedCriteria subselect)
/* 199:    */   {
/* 200:194 */     return Subqueries.propertyNe(getPropertyName(), subselect);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Criterion lt(DetachedCriteria subselect)
/* 204:    */   {
/* 205:198 */     return Subqueries.propertyLt(getPropertyName(), subselect);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public Criterion le(DetachedCriteria subselect)
/* 209:    */   {
/* 210:202 */     return Subqueries.propertyLe(getPropertyName(), subselect);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Criterion gt(DetachedCriteria subselect)
/* 214:    */   {
/* 215:206 */     return Subqueries.propertyGt(getPropertyName(), subselect);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Criterion ge(DetachedCriteria subselect)
/* 219:    */   {
/* 220:210 */     return Subqueries.propertyGe(getPropertyName(), subselect);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Criterion notIn(DetachedCriteria subselect)
/* 224:    */   {
/* 225:214 */     return Subqueries.propertyNotIn(getPropertyName(), subselect);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Criterion in(DetachedCriteria subselect)
/* 229:    */   {
/* 230:218 */     return Subqueries.propertyIn(getPropertyName(), subselect);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Criterion eqAll(DetachedCriteria subselect)
/* 234:    */   {
/* 235:222 */     return Subqueries.propertyEqAll(getPropertyName(), subselect);
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Criterion gtAll(DetachedCriteria subselect)
/* 239:    */   {
/* 240:226 */     return Subqueries.propertyGtAll(getPropertyName(), subselect);
/* 241:    */   }
/* 242:    */   
/* 243:    */   public Criterion ltAll(DetachedCriteria subselect)
/* 244:    */   {
/* 245:230 */     return Subqueries.propertyLtAll(getPropertyName(), subselect);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public Criterion leAll(DetachedCriteria subselect)
/* 249:    */   {
/* 250:234 */     return Subqueries.propertyLeAll(getPropertyName(), subselect);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Criterion geAll(DetachedCriteria subselect)
/* 254:    */   {
/* 255:238 */     return Subqueries.propertyGeAll(getPropertyName(), subselect);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Criterion gtSome(DetachedCriteria subselect)
/* 259:    */   {
/* 260:242 */     return Subqueries.propertyGtSome(getPropertyName(), subselect);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public Criterion ltSome(DetachedCriteria subselect)
/* 264:    */   {
/* 265:246 */     return Subqueries.propertyLtSome(getPropertyName(), subselect);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public Criterion leSome(DetachedCriteria subselect)
/* 269:    */   {
/* 270:250 */     return Subqueries.propertyLeSome(getPropertyName(), subselect);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public Criterion geSome(DetachedCriteria subselect)
/* 274:    */   {
/* 275:254 */     return Subqueries.propertyGeSome(getPropertyName(), subselect);
/* 276:    */   }
/* 277:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Property
 * JD-Core Version:    0.7.0.1
 */