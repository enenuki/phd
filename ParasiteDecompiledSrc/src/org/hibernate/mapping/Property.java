/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.MappingException;
/*   9:    */ import org.hibernate.PropertyNotFoundException;
/*  10:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  11:    */ import org.hibernate.engine.spi.CascadeStyle.MultipleCascadeStyle;
/*  12:    */ import org.hibernate.engine.spi.Mapping;
/*  13:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  14:    */ import org.hibernate.property.Getter;
/*  15:    */ import org.hibernate.property.PropertyAccessor;
/*  16:    */ import org.hibernate.property.PropertyAccessorFactory;
/*  17:    */ import org.hibernate.property.Setter;
/*  18:    */ import org.hibernate.type.CompositeType;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ 
/*  21:    */ public class Property
/*  22:    */   implements Serializable, MetaAttributable
/*  23:    */ {
/*  24:    */   private String name;
/*  25:    */   private Value value;
/*  26:    */   private String cascade;
/*  27: 52 */   private boolean updateable = true;
/*  28: 53 */   private boolean insertable = true;
/*  29: 54 */   private boolean selectable = true;
/*  30: 55 */   private boolean optimisticLocked = true;
/*  31: 56 */   private PropertyGeneration generation = PropertyGeneration.NEVER;
/*  32:    */   private String propertyAccessorName;
/*  33:    */   private boolean lazy;
/*  34:    */   private boolean optional;
/*  35:    */   private String nodeName;
/*  36:    */   private Map metaAttributes;
/*  37:    */   private PersistentClass persistentClass;
/*  38:    */   private boolean naturalIdentifier;
/*  39:    */   
/*  40:    */   public boolean isBackRef()
/*  41:    */   {
/*  42: 66 */     return false;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean isSynthetic()
/*  46:    */   {
/*  47: 77 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Type getType()
/*  51:    */     throws MappingException
/*  52:    */   {
/*  53: 81 */     return this.value.getType();
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getColumnSpan()
/*  57:    */   {
/*  58: 85 */     return this.value.getColumnSpan();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Iterator getColumnIterator()
/*  62:    */   {
/*  63: 89 */     return this.value.getColumnIterator();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getName()
/*  67:    */   {
/*  68: 93 */     return this.name;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isComposite()
/*  72:    */   {
/*  73: 97 */     return this.value instanceof Component;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Value getValue()
/*  77:    */   {
/*  78:101 */     return this.value;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean isPrimitive(Class clazz)
/*  82:    */   {
/*  83:105 */     return getGetter(clazz).getReturnType().isPrimitive();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public CascadeStyle getCascadeStyle()
/*  87:    */     throws MappingException
/*  88:    */   {
/*  89:109 */     Type type = this.value.getType();
/*  90:110 */     if ((type.isComponentType()) && (!type.isAnyType()))
/*  91:    */     {
/*  92:111 */       CompositeType actype = (CompositeType)type;
/*  93:112 */       int length = actype.getSubtypes().length;
/*  94:113 */       for (int i = 0; i < length; i++) {
/*  95:114 */         if (actype.getCascadeStyle(i) != CascadeStyle.NONE) {
/*  96:114 */           return CascadeStyle.ALL;
/*  97:    */         }
/*  98:    */       }
/*  99:116 */       return CascadeStyle.NONE;
/* 100:    */     }
/* 101:118 */     if ((this.cascade == null) || (this.cascade.equals("none"))) {
/* 102:119 */       return CascadeStyle.NONE;
/* 103:    */     }
/* 104:122 */     StringTokenizer tokens = new StringTokenizer(this.cascade, ", ");
/* 105:123 */     CascadeStyle[] styles = new CascadeStyle[tokens.countTokens()];
/* 106:124 */     int i = 0;
/* 107:125 */     while (tokens.hasMoreTokens()) {
/* 108:126 */       styles[(i++)] = CascadeStyle.getCascadeStyle(tokens.nextToken());
/* 109:    */     }
/* 110:128 */     return new CascadeStyle.MultipleCascadeStyle(styles);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getCascade()
/* 114:    */   {
/* 115:133 */     return this.cascade;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setCascade(String cascade)
/* 119:    */   {
/* 120:137 */     this.cascade = cascade;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setName(String name)
/* 124:    */   {
/* 125:141 */     this.name = (name == null ? null : name.intern());
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setValue(Value value)
/* 129:    */   {
/* 130:145 */     this.value = value;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean isUpdateable()
/* 134:    */   {
/* 135:151 */     boolean[] columnUpdateability = this.value.getColumnUpdateability();
/* 136:152 */     return (this.updateable) && (!ArrayHelper.isAllFalse(columnUpdateability));
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isInsertable()
/* 140:    */   {
/* 141:161 */     boolean[] columnInsertability = this.value.getColumnInsertability();
/* 142:162 */     return (this.insertable) && ((columnInsertability.length == 0) || (!ArrayHelper.isAllFalse(columnInsertability)));
/* 143:    */   }
/* 144:    */   
/* 145:    */   public PropertyGeneration getGeneration()
/* 146:    */   {
/* 147:169 */     return this.generation;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setGeneration(PropertyGeneration generation)
/* 151:    */   {
/* 152:173 */     this.generation = generation;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setUpdateable(boolean mutable)
/* 156:    */   {
/* 157:177 */     this.updateable = mutable;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setInsertable(boolean insertable)
/* 161:    */   {
/* 162:181 */     this.insertable = insertable;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getPropertyAccessorName()
/* 166:    */   {
/* 167:185 */     return this.propertyAccessorName;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setPropertyAccessorName(String string)
/* 171:    */   {
/* 172:189 */     this.propertyAccessorName = string;
/* 173:    */   }
/* 174:    */   
/* 175:    */   boolean isNullable()
/* 176:    */   {
/* 177:196 */     return (this.value == null) || (this.value.isNullable());
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isBasicPropertyAccessor()
/* 181:    */   {
/* 182:200 */     return (this.propertyAccessorName == null) || ("property".equals(this.propertyAccessorName));
/* 183:    */   }
/* 184:    */   
/* 185:    */   public Map getMetaAttributes()
/* 186:    */   {
/* 187:204 */     return this.metaAttributes;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public MetaAttribute getMetaAttribute(String attributeName)
/* 191:    */   {
/* 192:208 */     return this.metaAttributes == null ? null : (MetaAttribute)this.metaAttributes.get(attributeName);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setMetaAttributes(Map metas)
/* 196:    */   {
/* 197:212 */     this.metaAttributes = metas;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isValid(Mapping mapping)
/* 201:    */     throws MappingException
/* 202:    */   {
/* 203:216 */     return getValue().isValid(mapping);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String toString()
/* 207:    */   {
/* 208:220 */     return getClass().getName() + '(' + this.name + ')';
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void setLazy(boolean lazy)
/* 212:    */   {
/* 213:224 */     this.lazy = lazy;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public boolean isLazy()
/* 217:    */   {
/* 218:228 */     if ((this.value instanceof ToOne))
/* 219:    */     {
/* 220:246 */       ToOne toOneValue = (ToOne)this.value;
/* 221:247 */       return (toOneValue.isLazy()) && (toOneValue.isUnwrapProxy());
/* 222:    */     }
/* 223:249 */     return this.lazy;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isOptimisticLocked()
/* 227:    */   {
/* 228:253 */     return this.optimisticLocked;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setOptimisticLocked(boolean optimisticLocked)
/* 232:    */   {
/* 233:257 */     this.optimisticLocked = optimisticLocked;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public boolean isOptional()
/* 237:    */   {
/* 238:261 */     return (this.optional) || (isNullable());
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setOptional(boolean optional)
/* 242:    */   {
/* 243:265 */     this.optional = optional;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public PersistentClass getPersistentClass()
/* 247:    */   {
/* 248:269 */     return this.persistentClass;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setPersistentClass(PersistentClass persistentClass)
/* 252:    */   {
/* 253:273 */     this.persistentClass = persistentClass;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean isSelectable()
/* 257:    */   {
/* 258:277 */     return this.selectable;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setSelectable(boolean selectable)
/* 262:    */   {
/* 263:281 */     this.selectable = selectable;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public String getNodeName()
/* 267:    */   {
/* 268:285 */     return this.nodeName;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setNodeName(String nodeName)
/* 272:    */   {
/* 273:289 */     this.nodeName = nodeName;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public String getAccessorPropertyName(EntityMode mode)
/* 277:    */   {
/* 278:293 */     return getName();
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Getter getGetter(Class clazz)
/* 282:    */     throws PropertyNotFoundException, MappingException
/* 283:    */   {
/* 284:298 */     return getPropertyAccessor(clazz).getGetter(clazz, this.name);
/* 285:    */   }
/* 286:    */   
/* 287:    */   public Setter getSetter(Class clazz)
/* 288:    */     throws PropertyNotFoundException, MappingException
/* 289:    */   {
/* 290:303 */     return getPropertyAccessor(clazz).getSetter(clazz, this.name);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public PropertyAccessor getPropertyAccessor(Class clazz)
/* 294:    */     throws MappingException
/* 295:    */   {
/* 296:308 */     return PropertyAccessorFactory.getPropertyAccessor(clazz, getPropertyAccessorName());
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean isNaturalIdentifier()
/* 300:    */   {
/* 301:312 */     return this.naturalIdentifier;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setNaturalIdentifier(boolean naturalIdentifier)
/* 305:    */   {
/* 306:316 */     this.naturalIdentifier = naturalIdentifier;
/* 307:    */   }
/* 308:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Property
 * JD-Core Version:    0.7.0.1
 */