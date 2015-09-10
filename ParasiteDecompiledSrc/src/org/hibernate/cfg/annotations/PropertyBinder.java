/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.Map;
/*   4:    */ import javax.persistence.EmbeddedId;
/*   5:    */ import javax.persistence.Id;
/*   6:    */ import javax.persistence.Version;
/*   7:    */ import org.hibernate.AnnotationException;
/*   8:    */ import org.hibernate.annotations.Generated;
/*   9:    */ import org.hibernate.annotations.GenerationTime;
/*  10:    */ import org.hibernate.annotations.Immutable;
/*  11:    */ import org.hibernate.annotations.NaturalId;
/*  12:    */ import org.hibernate.annotations.OptimisticLock;
/*  13:    */ import org.hibernate.annotations.common.AssertionFailure;
/*  14:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  15:    */ import org.hibernate.annotations.common.reflection.XProperty;
/*  16:    */ import org.hibernate.cfg.AccessType;
/*  17:    */ import org.hibernate.cfg.AnnotationBinder;
/*  18:    */ import org.hibernate.cfg.BinderHelper;
/*  19:    */ import org.hibernate.cfg.Ejb3Column;
/*  20:    */ import org.hibernate.cfg.InheritanceState;
/*  21:    */ import org.hibernate.cfg.Mappings;
/*  22:    */ import org.hibernate.cfg.PropertyHolder;
/*  23:    */ import org.hibernate.cfg.PropertyPreloadedData;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.util.StringHelper;
/*  26:    */ import org.hibernate.mapping.Component;
/*  27:    */ import org.hibernate.mapping.KeyValue;
/*  28:    */ import org.hibernate.mapping.MappedSuperclass;
/*  29:    */ import org.hibernate.mapping.Property;
/*  30:    */ import org.hibernate.mapping.PropertyGeneration;
/*  31:    */ import org.hibernate.mapping.RootClass;
/*  32:    */ import org.hibernate.mapping.SimpleValue;
/*  33:    */ import org.hibernate.mapping.Value;
/*  34:    */ import org.jboss.logging.Logger;
/*  35:    */ 
/*  36:    */ public class PropertyBinder
/*  37:    */ {
/*  38: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PropertyBinder.class.getName());
/*  39:    */   private String name;
/*  40:    */   private String returnedClassName;
/*  41:    */   private boolean lazy;
/*  42:    */   private AccessType accessType;
/*  43:    */   private Ejb3Column[] columns;
/*  44:    */   private PropertyHolder holder;
/*  45:    */   private Mappings mappings;
/*  46:    */   private Value value;
/*  47: 73 */   private boolean insertable = true;
/*  48: 74 */   private boolean updatable = true;
/*  49:    */   private String cascade;
/*  50:    */   private SimpleValueBinder simpleValueBinder;
/*  51:    */   private XClass declaringClass;
/*  52:    */   private boolean declaringClassSet;
/*  53:    */   private boolean embedded;
/*  54:    */   private EntityBinder entityBinder;
/*  55:    */   private boolean isXToMany;
/*  56:    */   private String referencedEntityName;
/*  57:    */   private XProperty property;
/*  58:    */   private XClass returnedClass;
/*  59:    */   private boolean isId;
/*  60:    */   private Map<XClass, InheritanceState> inheritanceStatePerClass;
/*  61:    */   private Property mappingProperty;
/*  62:    */   
/*  63:    */   public void setReferencedEntityName(String referencedEntityName)
/*  64:    */   {
/*  65: 85 */     this.referencedEntityName = referencedEntityName;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setEmbedded(boolean embedded)
/*  69:    */   {
/*  70: 89 */     this.embedded = embedded;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setEntityBinder(EntityBinder entityBinder)
/*  74:    */   {
/*  75: 93 */     this.entityBinder = entityBinder;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setInsertable(boolean insertable)
/*  79:    */   {
/*  80:107 */     this.insertable = insertable;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setUpdatable(boolean updatable)
/*  84:    */   {
/*  85:111 */     this.updatable = updatable;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setName(String name)
/*  89:    */   {
/*  90:115 */     this.name = name;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setReturnedClassName(String returnedClassName)
/*  94:    */   {
/*  95:119 */     this.returnedClassName = returnedClassName;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setLazy(boolean lazy)
/*  99:    */   {
/* 100:123 */     this.lazy = lazy;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setAccessType(AccessType accessType)
/* 104:    */   {
/* 105:127 */     this.accessType = accessType;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setColumns(Ejb3Column[] columns)
/* 109:    */   {
/* 110:131 */     this.insertable = columns[0].isInsertable();
/* 111:132 */     this.updatable = columns[0].isUpdatable();
/* 112:    */     
/* 113:134 */     this.columns = columns;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setHolder(PropertyHolder holder)
/* 117:    */   {
/* 118:138 */     this.holder = holder;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setValue(Value value)
/* 122:    */   {
/* 123:142 */     this.value = value;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setCascade(String cascadeStrategy)
/* 127:    */   {
/* 128:146 */     this.cascade = cascadeStrategy;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setMappings(Mappings mappings)
/* 132:    */   {
/* 133:150 */     this.mappings = mappings;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setDeclaringClass(XClass declaringClass)
/* 137:    */   {
/* 138:154 */     this.declaringClass = declaringClass;
/* 139:155 */     this.declaringClassSet = true;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void validateBind()
/* 143:    */   {
/* 144:159 */     if (this.property.isAnnotationPresent(Immutable.class)) {
/* 145:160 */       throw new AnnotationException("@Immutable on property not allowed. Only allowed on entity level or on a collection.");
/* 146:    */     }
/* 147:165 */     if (!this.declaringClassSet) {
/* 148:166 */       throw new AssertionFailure("declaringClass has not been set before a bind");
/* 149:    */     }
/* 150:    */   }
/* 151:    */   
/* 152:    */   private void validateMake() {}
/* 153:    */   
/* 154:    */   private Property makePropertyAndValue()
/* 155:    */   {
/* 156:175 */     validateBind();
/* 157:176 */     LOG.debugf("MetadataSourceProcessor property %s with lazy=%s", this.name, Boolean.valueOf(this.lazy));
/* 158:177 */     String containerClassName = this.holder == null ? null : this.holder.getClassName();
/* 159:    */     
/* 160:    */ 
/* 161:180 */     this.simpleValueBinder = new SimpleValueBinder();
/* 162:181 */     this.simpleValueBinder.setMappings(this.mappings);
/* 163:182 */     this.simpleValueBinder.setPropertyName(this.name);
/* 164:183 */     this.simpleValueBinder.setReturnedClassName(this.returnedClassName);
/* 165:184 */     this.simpleValueBinder.setColumns(this.columns);
/* 166:185 */     this.simpleValueBinder.setPersistentClassName(containerClassName);
/* 167:186 */     this.simpleValueBinder.setType(this.property, this.returnedClass);
/* 168:187 */     this.simpleValueBinder.setMappings(this.mappings);
/* 169:188 */     this.simpleValueBinder.setReferencedEntityName(this.referencedEntityName);
/* 170:189 */     SimpleValue propertyValue = this.simpleValueBinder.make();
/* 171:190 */     setValue(propertyValue);
/* 172:191 */     return makeProperty();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Property makePropertyAndBind()
/* 176:    */   {
/* 177:196 */     return bind(makeProperty());
/* 178:    */   }
/* 179:    */   
/* 180:    */   public Property makePropertyValueAndBind()
/* 181:    */   {
/* 182:201 */     return bind(makePropertyAndValue());
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setXToMany(boolean xToMany)
/* 186:    */   {
/* 187:205 */     this.isXToMany = xToMany;
/* 188:    */   }
/* 189:    */   
/* 190:    */   private Property bind(Property prop)
/* 191:    */   {
/* 192:209 */     if (this.isId)
/* 193:    */     {
/* 194:210 */       RootClass rootClass = (RootClass)this.holder.getPersistentClass();
/* 195:213 */       if ((this.isXToMany) || (this.entityBinder.wrapIdsInEmbeddedComponents()))
/* 196:    */       {
/* 197:214 */         Component identifier = (Component)rootClass.getIdentifier();
/* 198:215 */         if (identifier == null)
/* 199:    */         {
/* 200:216 */           identifier = AnnotationBinder.createComponent(this.holder, new PropertyPreloadedData(null, null, null), true, false, this.mappings);
/* 201:217 */           rootClass.setIdentifier(identifier);
/* 202:218 */           identifier.setNullValue("undefined");
/* 203:219 */           rootClass.setEmbeddedIdentifier(true);
/* 204:220 */           rootClass.setIdentifierMapper(identifier);
/* 205:    */         }
/* 206:223 */         identifier.addProperty(prop);
/* 207:    */       }
/* 208:    */       else
/* 209:    */       {
/* 210:226 */         rootClass.setIdentifier((KeyValue)getValue());
/* 211:227 */         if (this.embedded)
/* 212:    */         {
/* 213:228 */           rootClass.setEmbeddedIdentifier(true);
/* 214:    */         }
/* 215:    */         else
/* 216:    */         {
/* 217:231 */           rootClass.setIdentifierProperty(prop);
/* 218:232 */           MappedSuperclass superclass = BinderHelper.getMappedSuperclassOrNull(this.declaringClass, this.inheritanceStatePerClass, this.mappings);
/* 219:237 */           if (superclass != null) {
/* 220:238 */             superclass.setDeclaredIdentifierProperty(prop);
/* 221:    */           } else {
/* 222:242 */             rootClass.setDeclaredIdentifierProperty(prop);
/* 223:    */           }
/* 224:    */         }
/* 225:    */       }
/* 226:    */     }
/* 227:    */     else
/* 228:    */     {
/* 229:248 */       this.holder.addProperty(prop, this.columns, this.declaringClass);
/* 230:    */     }
/* 231:250 */     return prop;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public Property makeProperty()
/* 235:    */   {
/* 236:255 */     validateMake();
/* 237:256 */     LOG.debugf("Building property %s", this.name);
/* 238:257 */     Property prop = new Property();
/* 239:258 */     prop.setName(this.name);
/* 240:259 */     prop.setNodeName(this.name);
/* 241:260 */     prop.setValue(this.value);
/* 242:261 */     prop.setLazy(this.lazy);
/* 243:262 */     prop.setCascade(this.cascade);
/* 244:263 */     prop.setPropertyAccessorName(this.accessType.getType());
/* 245:264 */     Generated ann = this.property != null ? (Generated)this.property.getAnnotation(Generated.class) : null;
/* 246:    */     
/* 247:    */ 
/* 248:267 */     GenerationTime generated = ann != null ? ann.value() : null;
/* 249:270 */     if ((generated != null) && 
/* 250:271 */       (!GenerationTime.NEVER.equals(generated)))
/* 251:    */     {
/* 252:272 */       if ((this.property.isAnnotationPresent(Version.class)) && (GenerationTime.INSERT.equals(generated))) {
/* 253:274 */         throw new AnnotationException("@Generated(INSERT) on a @Version property not allowed, use ALWAYS: " + StringHelper.qualify(this.holder.getPath(), this.name));
/* 254:    */       }
/* 255:279 */       this.insertable = false;
/* 256:280 */       if (GenerationTime.ALWAYS.equals(generated)) {
/* 257:281 */         this.updatable = false;
/* 258:    */       }
/* 259:283 */       prop.setGeneration(PropertyGeneration.parse(generated.toString().toLowerCase()));
/* 260:    */     }
/* 261:286 */     NaturalId naturalId = this.property != null ? (NaturalId)this.property.getAnnotation(NaturalId.class) : null;
/* 262:289 */     if (naturalId != null)
/* 263:    */     {
/* 264:290 */       if (!naturalId.mutable()) {
/* 265:291 */         this.updatable = false;
/* 266:    */       }
/* 267:293 */       prop.setNaturalIdentifier(true);
/* 268:    */     }
/* 269:295 */     prop.setInsertable(this.insertable);
/* 270:296 */     prop.setUpdateable(this.updatable);
/* 271:297 */     OptimisticLock lockAnn = this.property != null ? (OptimisticLock)this.property.getAnnotation(OptimisticLock.class) : null;
/* 272:300 */     if (lockAnn != null)
/* 273:    */     {
/* 274:301 */       prop.setOptimisticLocked(!lockAnn.excluded());
/* 275:303 */       if ((lockAnn.excluded()) && ((this.property.isAnnotationPresent(Version.class)) || (this.property.isAnnotationPresent(Id.class)) || (this.property.isAnnotationPresent(EmbeddedId.class)))) {
/* 276:307 */         throw new AnnotationException("@OptimisticLock.exclude=true incompatible with @Id, @EmbeddedId and @Version: " + StringHelper.qualify(this.holder.getPath(), this.name));
/* 277:    */       }
/* 278:    */     }
/* 279:313 */     LOG.tracev("Cascading {0} with {1}", this.name, this.cascade);
/* 280:314 */     this.mappingProperty = prop;
/* 281:315 */     return prop;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setProperty(XProperty property)
/* 285:    */   {
/* 286:319 */     this.property = property;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setReturnedClass(XClass returnedClass)
/* 290:    */   {
/* 291:323 */     this.returnedClass = returnedClass;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public SimpleValueBinder getSimpleValueBinder()
/* 295:    */   {
/* 296:327 */     return this.simpleValueBinder;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public Value getValue()
/* 300:    */   {
/* 301:331 */     return this.value;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public void setId(boolean id)
/* 305:    */   {
/* 306:335 */     this.isId = id;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setInheritanceStatePerClass(Map<XClass, InheritanceState> inheritanceStatePerClass)
/* 310:    */   {
/* 311:339 */     this.inheritanceStatePerClass = inheritanceStatePerClass;
/* 312:    */   }
/* 313:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.PropertyBinder
 * JD-Core Version:    0.7.0.1
 */