/*   1:    */ package org.hibernate.criterion;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.hibernate.Criteria;
/*   9:    */ import org.hibernate.EntityMode;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  12:    */ import org.hibernate.engine.spi.TypedValue;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.hibernate.persister.entity.EntityPersister;
/*  15:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  16:    */ import org.hibernate.tuple.entity.EntityTuplizer;
/*  17:    */ import org.hibernate.type.CompositeType;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class Example
/*  21:    */   implements Criterion
/*  22:    */ {
/*  23:    */   private final Object entity;
/*  24: 57 */   private final Set excludedProperties = new HashSet();
/*  25:    */   private PropertySelector selector;
/*  26:    */   private boolean isLikeEnabled;
/*  27:    */   private Character escapeCharacter;
/*  28:    */   private boolean isIgnoreCaseEnabled;
/*  29:    */   private MatchMode matchMode;
/*  30: 73 */   private static final PropertySelector NOT_NULL = new NotNullPropertySelector();
/*  31: 74 */   private static final PropertySelector ALL = new AllPropertySelector();
/*  32: 75 */   private static final PropertySelector NOT_NULL_OR_ZERO = new NotNullOrZeroPropertySelector();
/*  33:    */   
/*  34:    */   public static abstract interface PropertySelector
/*  35:    */     extends Serializable
/*  36:    */   {
/*  37:    */     public abstract boolean include(Object paramObject, String paramString, Type paramType);
/*  38:    */   }
/*  39:    */   
/*  40:    */   static final class AllPropertySelector
/*  41:    */     implements Example.PropertySelector
/*  42:    */   {
/*  43:    */     public boolean include(Object object, String propertyName, Type type)
/*  44:    */     {
/*  45: 79 */       return true;
/*  46:    */     }
/*  47:    */     
/*  48:    */     private Object readResolve()
/*  49:    */     {
/*  50: 83 */       return Example.ALL;
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   static final class NotNullPropertySelector
/*  55:    */     implements Example.PropertySelector
/*  56:    */   {
/*  57:    */     public boolean include(Object object, String propertyName, Type type)
/*  58:    */     {
/*  59: 89 */       return object != null;
/*  60:    */     }
/*  61:    */     
/*  62:    */     private Object readResolve()
/*  63:    */     {
/*  64: 93 */       return Example.NOT_NULL;
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   static final class NotNullOrZeroPropertySelector
/*  69:    */     implements Example.PropertySelector
/*  70:    */   {
/*  71:    */     public boolean include(Object object, String propertyName, Type type)
/*  72:    */     {
/*  73: 99 */       return (object != null) && ((!(object instanceof Number)) || (((Number)object).longValue() != 0L));
/*  74:    */     }
/*  75:    */     
/*  76:    */     private Object readResolve()
/*  77:    */     {
/*  78:105 */       return Example.NOT_NULL_OR_ZERO;
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Example setEscapeCharacter(Character escapeCharacter)
/*  83:    */   {
/*  84:113 */     this.escapeCharacter = escapeCharacter;
/*  85:114 */     return this;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Example setPropertySelector(PropertySelector selector)
/*  89:    */   {
/*  90:121 */     this.selector = selector;
/*  91:122 */     return this;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Example excludeZeroes()
/*  95:    */   {
/*  96:129 */     setPropertySelector(NOT_NULL_OR_ZERO);
/*  97:130 */     return this;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Example excludeNone()
/* 101:    */   {
/* 102:137 */     setPropertySelector(ALL);
/* 103:138 */     return this;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Example enableLike(MatchMode matchMode)
/* 107:    */   {
/* 108:145 */     this.isLikeEnabled = true;
/* 109:146 */     this.matchMode = matchMode;
/* 110:147 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Example enableLike()
/* 114:    */   {
/* 115:154 */     return enableLike(MatchMode.EXACT);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Example ignoreCase()
/* 119:    */   {
/* 120:161 */     this.isIgnoreCaseEnabled = true;
/* 121:162 */     return this;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Example excludeProperty(String name)
/* 125:    */   {
/* 126:169 */     this.excludedProperties.add(name);
/* 127:170 */     return this;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static Example create(Object entity)
/* 131:    */   {
/* 132:180 */     if (entity == null) {
/* 133:180 */       throw new NullPointerException("null example");
/* 134:    */     }
/* 135:181 */     return new Example(entity, NOT_NULL);
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected Example(Object entity, PropertySelector selector)
/* 139:    */   {
/* 140:185 */     this.entity = entity;
/* 141:186 */     this.selector = selector;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String toString()
/* 145:    */   {
/* 146:190 */     return "example (" + this.entity + ')';
/* 147:    */   }
/* 148:    */   
/* 149:    */   private boolean isPropertyIncluded(Object value, String name, Type type)
/* 150:    */   {
/* 151:194 */     return (!this.excludedProperties.contains(name)) && (!type.isAssociationType()) && (this.selector.include(value, name, type));
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
/* 155:    */     throws HibernateException
/* 156:    */   {
/* 157:202 */     StringBuffer buf = new StringBuffer().append('(');
/* 158:203 */     EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
/* 159:204 */     String[] propertyNames = meta.getPropertyNames();
/* 160:205 */     Type[] propertyTypes = meta.getPropertyTypes();
/* 161:    */     
/* 162:207 */     Object[] propertyValues = meta.getPropertyValues(this.entity);
/* 163:208 */     for (int i = 0; i < propertyNames.length; i++)
/* 164:    */     {
/* 165:209 */       Object propertyValue = propertyValues[i];
/* 166:210 */       String propertyName = propertyNames[i];
/* 167:    */       
/* 168:212 */       boolean isPropertyIncluded = (i != meta.getVersionProperty()) && (isPropertyIncluded(propertyValue, propertyName, propertyTypes[i]));
/* 169:214 */       if (isPropertyIncluded) {
/* 170:215 */         if (propertyTypes[i].isComponentType()) {
/* 171:216 */           appendComponentCondition(propertyName, propertyValue, (CompositeType)propertyTypes[i], criteria, criteriaQuery, buf);
/* 172:    */         } else {
/* 173:226 */           appendPropertyCondition(propertyName, propertyValue, criteria, criteriaQuery, buf);
/* 174:    */         }
/* 175:    */       }
/* 176:    */     }
/* 177:236 */     if (buf.length() == 1) {
/* 178:236 */       buf.append("1=1");
/* 179:    */     }
/* 180:237 */     return ')';
/* 181:    */   }
/* 182:    */   
/* 183:240 */   private static final Object[] TYPED_VALUES = new TypedValue[0];
/* 184:    */   
/* 185:    */   public TypedValue[] getTypedValues(Criteria criteria, CriteriaQuery criteriaQuery)
/* 186:    */     throws HibernateException
/* 187:    */   {
/* 188:245 */     EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
/* 189:    */     
/* 190:247 */     String[] propertyNames = meta.getPropertyNames();
/* 191:248 */     Type[] propertyTypes = meta.getPropertyTypes();
/* 192:    */     
/* 193:250 */     Object[] values = meta.getPropertyValues(this.entity);
/* 194:251 */     List list = new ArrayList();
/* 195:252 */     for (int i = 0; i < propertyNames.length; i++)
/* 196:    */     {
/* 197:253 */       Object value = values[i];
/* 198:254 */       Type type = propertyTypes[i];
/* 199:255 */       String name = propertyNames[i];
/* 200:    */       
/* 201:257 */       boolean isPropertyIncluded = (i != meta.getVersionProperty()) && (isPropertyIncluded(value, name, type));
/* 202:260 */       if (isPropertyIncluded) {
/* 203:261 */         if (propertyTypes[i].isComponentType()) {
/* 204:262 */           addComponentTypedValues(name, value, (CompositeType)type, list, criteria, criteriaQuery);
/* 205:    */         } else {
/* 206:265 */           addPropertyTypedValue(value, type, list);
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:269 */     return (TypedValue[])list.toArray(TYPED_VALUES);
/* 211:    */   }
/* 212:    */   
/* 213:    */   private EntityMode getEntityMode(Criteria criteria, CriteriaQuery criteriaQuery)
/* 214:    */   {
/* 215:273 */     EntityPersister meta = criteriaQuery.getFactory().getEntityPersister(criteriaQuery.getEntityName(criteria));
/* 216:    */     
/* 217:275 */     EntityMode result = meta.getEntityMode();
/* 218:276 */     if (!meta.getEntityMetamodel().getTuplizer().isInstance(this.entity)) {
/* 219:277 */       throw new ClassCastException(this.entity.getClass().getName());
/* 220:    */     }
/* 221:279 */     return result;
/* 222:    */   }
/* 223:    */   
/* 224:    */   protected void addPropertyTypedValue(Object value, Type type, List list)
/* 225:    */   {
/* 226:283 */     if (value != null)
/* 227:    */     {
/* 228:284 */       if ((value instanceof String))
/* 229:    */       {
/* 230:285 */         String string = (String)value;
/* 231:286 */         if (this.isIgnoreCaseEnabled) {
/* 232:286 */           string = string.toLowerCase();
/* 233:    */         }
/* 234:287 */         if (this.isLikeEnabled) {
/* 235:287 */           string = this.matchMode.toMatchString(string);
/* 236:    */         }
/* 237:288 */         value = string;
/* 238:    */       }
/* 239:290 */       list.add(new TypedValue(type, value, null));
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   protected void addComponentTypedValues(String path, Object component, CompositeType type, List list, Criteria criteria, CriteriaQuery criteriaQuery)
/* 244:    */     throws HibernateException
/* 245:    */   {
/* 246:303 */     if (component != null)
/* 247:    */     {
/* 248:304 */       String[] propertyNames = type.getPropertyNames();
/* 249:305 */       Type[] subtypes = type.getSubtypes();
/* 250:306 */       Object[] values = type.getPropertyValues(component, getEntityMode(criteria, criteriaQuery));
/* 251:307 */       for (int i = 0; i < propertyNames.length; i++)
/* 252:    */       {
/* 253:308 */         Object value = values[i];
/* 254:309 */         Type subtype = subtypes[i];
/* 255:310 */         String subpath = StringHelper.qualify(path, propertyNames[i]);
/* 256:311 */         if (isPropertyIncluded(value, subpath, subtype)) {
/* 257:312 */           if (subtype.isComponentType()) {
/* 258:313 */             addComponentTypedValues(subpath, value, (CompositeType)subtype, list, criteria, criteriaQuery);
/* 259:    */           } else {
/* 260:316 */             addPropertyTypedValue(value, subtype, list);
/* 261:    */           }
/* 262:    */         }
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   protected void appendPropertyCondition(String propertyName, Object propertyValue, Criteria criteria, CriteriaQuery cq, StringBuffer buf)
/* 268:    */     throws HibernateException
/* 269:    */   {
/* 270:    */     Criterion crit;
/* 271:    */     Criterion crit;
/* 272:331 */     if (propertyValue != null)
/* 273:    */     {
/* 274:332 */       boolean isString = propertyValue instanceof String;
/* 275:    */       Criterion crit;
/* 276:333 */       if ((this.isLikeEnabled) && (isString)) {
/* 277:334 */         crit = new LikeExpression(propertyName, (String)propertyValue, this.matchMode, this.escapeCharacter, this.isIgnoreCaseEnabled);
/* 278:    */       } else {
/* 279:343 */         crit = new SimpleExpression(propertyName, propertyValue, "=", (this.isIgnoreCaseEnabled) && (isString));
/* 280:    */       }
/* 281:    */     }
/* 282:    */     else
/* 283:    */     {
/* 284:347 */       crit = new NullExpression(propertyName);
/* 285:    */     }
/* 286:349 */     String critCondition = crit.toSqlString(criteria, cq);
/* 287:350 */     if ((buf.length() > 1) && (critCondition.trim().length() > 0)) {
/* 288:350 */       buf.append(" and ");
/* 289:    */     }
/* 290:351 */     buf.append(critCondition);
/* 291:    */   }
/* 292:    */   
/* 293:    */   protected void appendComponentCondition(String path, Object component, CompositeType type, Criteria criteria, CriteriaQuery criteriaQuery, StringBuffer buf)
/* 294:    */     throws HibernateException
/* 295:    */   {
/* 296:363 */     if (component != null)
/* 297:    */     {
/* 298:364 */       String[] propertyNames = type.getPropertyNames();
/* 299:365 */       Object[] values = type.getPropertyValues(component, getEntityMode(criteria, criteriaQuery));
/* 300:366 */       Type[] subtypes = type.getSubtypes();
/* 301:367 */       for (int i = 0; i < propertyNames.length; i++)
/* 302:    */       {
/* 303:368 */         String subpath = StringHelper.qualify(path, propertyNames[i]);
/* 304:369 */         Object value = values[i];
/* 305:370 */         if (isPropertyIncluded(value, subpath, subtypes[i]))
/* 306:    */         {
/* 307:371 */           Type subtype = subtypes[i];
/* 308:372 */           if (subtype.isComponentType()) {
/* 309:373 */             appendComponentCondition(subpath, value, (CompositeType)subtype, criteria, criteriaQuery, buf);
/* 310:    */           } else {
/* 311:383 */             appendPropertyCondition(subpath, value, criteria, criteriaQuery, buf);
/* 312:    */           }
/* 313:    */         }
/* 314:    */       }
/* 315:    */     }
/* 316:    */   }
/* 317:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Example
 * JD-Core Version:    0.7.0.1
 */