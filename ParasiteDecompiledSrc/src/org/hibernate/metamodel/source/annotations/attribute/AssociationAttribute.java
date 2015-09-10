/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ import javax.persistence.CascadeType;
/*   8:    */ import javax.persistence.FetchType;
/*   9:    */ import org.hibernate.annotations.NotFoundAction;
/*  10:    */ import org.hibernate.mapping.PropertyGeneration;
/*  11:    */ import org.hibernate.metamodel.source.MappingException;
/*  12:    */ import org.hibernate.metamodel.source.annotations.EnumConversionHelper;
/*  13:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  14:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  15:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  16:    */ import org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolver;
/*  17:    */ import org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolverImpl;
/*  18:    */ import org.hibernate.metamodel.source.annotations.attribute.type.CompositeAttributeTypeResolver;
/*  19:    */ import org.hibernate.metamodel.source.annotations.entity.EntityBindingContext;
/*  20:    */ import org.jboss.jandex.AnnotationInstance;
/*  21:    */ import org.jboss.jandex.AnnotationValue;
/*  22:    */ import org.jboss.jandex.DotName;
/*  23:    */ import org.jboss.jandex.Type;
/*  24:    */ 
/*  25:    */ public class AssociationAttribute
/*  26:    */   extends MappedAttribute
/*  27:    */ {
/*  28:    */   private final AttributeNature associationNature;
/*  29:    */   private final boolean ignoreNotFound;
/*  30:    */   private final String referencedEntityType;
/*  31:    */   private final String mappedBy;
/*  32:    */   private final Set<CascadeType> cascadeTypes;
/*  33:    */   private final boolean isOptional;
/*  34:    */   private final boolean isLazy;
/*  35:    */   private final boolean isOrphanRemoval;
/*  36:    */   private final org.hibernate.FetchMode fetchMode;
/*  37:    */   private final boolean mapsId;
/*  38:    */   private final String referencedIdAttributeName;
/*  39: 69 */   private boolean isInsertable = true;
/*  40: 70 */   private boolean isUpdatable = true;
/*  41:    */   private AttributeTypeResolver resolver;
/*  42:    */   
/*  43:    */   public static AssociationAttribute createAssociationAttribute(String name, Class<?> attributeType, AttributeNature attributeNature, String accessType, Map<DotName, List<AnnotationInstance>> annotations, EntityBindingContext context)
/*  44:    */   {
/*  45: 79 */     return new AssociationAttribute(name, attributeType, attributeNature, accessType, annotations, context);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private AssociationAttribute(String name, Class<?> javaType, AttributeNature associationType, String accessType, Map<DotName, List<AnnotationInstance>> annotations, EntityBindingContext context)
/*  49:    */   {
/*  50: 95 */     super(name, javaType, accessType, annotations, context);
/*  51: 96 */     this.associationNature = associationType;
/*  52: 97 */     this.ignoreNotFound = ignoreNotFound();
/*  53:    */     
/*  54: 99 */     AnnotationInstance associationAnnotation = JandexHelper.getSingleAnnotation(annotations, associationType.getAnnotationDotName());
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:105 */     this.referencedEntityType = determineReferencedEntityType(associationAnnotation);
/*  61:106 */     this.mappedBy = determineMappedByAttributeName(associationAnnotation);
/*  62:107 */     this.isOptional = determineOptionality(associationAnnotation);
/*  63:108 */     this.isLazy = determineFetchType(associationAnnotation);
/*  64:109 */     this.isOrphanRemoval = determineOrphanRemoval(associationAnnotation);
/*  65:110 */     this.cascadeTypes = determineCascadeTypes(associationAnnotation);
/*  66:    */     
/*  67:112 */     this.fetchMode = determineFetchMode();
/*  68:113 */     this.referencedIdAttributeName = determineMapsId();
/*  69:114 */     this.mapsId = (this.referencedIdAttributeName != null);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isIgnoreNotFound()
/*  73:    */   {
/*  74:118 */     return this.ignoreNotFound;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getReferencedEntityType()
/*  78:    */   {
/*  79:122 */     return this.referencedEntityType;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getMappedBy()
/*  83:    */   {
/*  84:126 */     return this.mappedBy;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public AttributeNature getAssociationNature()
/*  88:    */   {
/*  89:130 */     return this.associationNature;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Set<CascadeType> getCascadeTypes()
/*  93:    */   {
/*  94:134 */     return this.cascadeTypes;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public boolean isOrphanRemoval()
/*  98:    */   {
/*  99:138 */     return this.isOrphanRemoval;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public org.hibernate.FetchMode getFetchMode()
/* 103:    */   {
/* 104:142 */     return this.fetchMode;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getReferencedIdAttributeName()
/* 108:    */   {
/* 109:146 */     return this.referencedIdAttributeName;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean mapsId()
/* 113:    */   {
/* 114:150 */     return this.mapsId;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public AttributeTypeResolver getHibernateTypeResolver()
/* 118:    */   {
/* 119:155 */     if (this.resolver == null) {
/* 120:156 */       this.resolver = getDefaultHibernateTypeResolver();
/* 121:    */     }
/* 122:158 */     return this.resolver;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean isLazy()
/* 126:    */   {
/* 127:163 */     return this.isLazy;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isOptional()
/* 131:    */   {
/* 132:168 */     return this.isOptional;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean isInsertable()
/* 136:    */   {
/* 137:173 */     return this.isInsertable;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isUpdatable()
/* 141:    */   {
/* 142:178 */     return this.isUpdatable;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public PropertyGeneration getPropertyGeneration()
/* 146:    */   {
/* 147:183 */     return PropertyGeneration.NEVER;
/* 148:    */   }
/* 149:    */   
/* 150:    */   private AttributeTypeResolver getDefaultHibernateTypeResolver()
/* 151:    */   {
/* 152:187 */     return new CompositeAttributeTypeResolver(new AttributeTypeResolverImpl(this));
/* 153:    */   }
/* 154:    */   
/* 155:    */   private boolean ignoreNotFound()
/* 156:    */   {
/* 157:191 */     NotFoundAction action = NotFoundAction.EXCEPTION;
/* 158:192 */     AnnotationInstance notFoundAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.NOT_FOUND);
/* 159:196 */     if (notFoundAnnotation != null)
/* 160:    */     {
/* 161:197 */       AnnotationValue actionValue = notFoundAnnotation.value("action");
/* 162:198 */       if (actionValue != null) {
/* 163:199 */         action = (NotFoundAction)Enum.valueOf(NotFoundAction.class, actionValue.asEnum());
/* 164:    */       }
/* 165:    */     }
/* 166:203 */     return NotFoundAction.IGNORE.equals(action);
/* 167:    */   }
/* 168:    */   
/* 169:    */   private boolean determineOptionality(AnnotationInstance associationAnnotation)
/* 170:    */   {
/* 171:207 */     boolean optional = true;
/* 172:    */     
/* 173:209 */     AnnotationValue optionalValue = associationAnnotation.value("optional");
/* 174:210 */     if (optionalValue != null) {
/* 175:211 */       optional = optionalValue.asBoolean();
/* 176:    */     }
/* 177:214 */     return optional;
/* 178:    */   }
/* 179:    */   
/* 180:    */   private boolean determineOrphanRemoval(AnnotationInstance associationAnnotation)
/* 181:    */   {
/* 182:218 */     boolean orphanRemoval = false;
/* 183:219 */     AnnotationValue orphanRemovalValue = associationAnnotation.value("orphanRemoval");
/* 184:220 */     if (orphanRemovalValue != null) {
/* 185:221 */       orphanRemoval = orphanRemovalValue.asBoolean();
/* 186:    */     }
/* 187:223 */     return orphanRemoval;
/* 188:    */   }
/* 189:    */   
/* 190:    */   private boolean determineFetchType(AnnotationInstance associationAnnotation)
/* 191:    */   {
/* 192:227 */     boolean lazy = false;
/* 193:228 */     AnnotationValue fetchValue = associationAnnotation.value("fetch");
/* 194:229 */     if (fetchValue != null)
/* 195:    */     {
/* 196:230 */       FetchType fetchType = (FetchType)Enum.valueOf(FetchType.class, fetchValue.asEnum());
/* 197:231 */       if (FetchType.LAZY.equals(fetchType)) {
/* 198:232 */         lazy = true;
/* 199:    */       }
/* 200:    */     }
/* 201:235 */     return lazy;
/* 202:    */   }
/* 203:    */   
/* 204:    */   private String determineReferencedEntityType(AnnotationInstance associationAnnotation)
/* 205:    */   {
/* 206:239 */     String targetTypeName = getAttributeType().getName();
/* 207:    */     
/* 208:241 */     AnnotationInstance targetAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.TARGET);
/* 209:245 */     if (targetAnnotation != null) {
/* 210:246 */       targetTypeName = targetAnnotation.value().asClass().name().toString();
/* 211:    */     }
/* 212:249 */     AnnotationValue targetEntityValue = associationAnnotation.value("targetEntity");
/* 213:250 */     if (targetEntityValue != null) {
/* 214:251 */       targetTypeName = targetEntityValue.asClass().name().toString();
/* 215:    */     }
/* 216:254 */     return targetTypeName;
/* 217:    */   }
/* 218:    */   
/* 219:    */   private String determineMappedByAttributeName(AnnotationInstance associationAnnotation)
/* 220:    */   {
/* 221:258 */     String mappedBy = null;
/* 222:259 */     AnnotationValue mappedByAnnotationValue = associationAnnotation.value("mappedBy");
/* 223:260 */     if (mappedByAnnotationValue != null) {
/* 224:261 */       mappedBy = mappedByAnnotationValue.asString();
/* 225:    */     }
/* 226:264 */     return mappedBy;
/* 227:    */   }
/* 228:    */   
/* 229:    */   private Set<CascadeType> determineCascadeTypes(AnnotationInstance associationAnnotation)
/* 230:    */   {
/* 231:268 */     Set<CascadeType> cascadeTypes = new HashSet();
/* 232:269 */     AnnotationValue cascadeValue = associationAnnotation.value("cascade");
/* 233:270 */     if (cascadeValue != null)
/* 234:    */     {
/* 235:271 */       String[] cascades = cascadeValue.asEnumArray();
/* 236:272 */       for (String s : cascades) {
/* 237:273 */         cascadeTypes.add(Enum.valueOf(CascadeType.class, s));
/* 238:    */       }
/* 239:    */     }
/* 240:276 */     return cascadeTypes;
/* 241:    */   }
/* 242:    */   
/* 243:    */   private org.hibernate.FetchMode determineFetchMode()
/* 244:    */   {
/* 245:280 */     org.hibernate.FetchMode mode = org.hibernate.FetchMode.DEFAULT;
/* 246:    */     
/* 247:282 */     AnnotationInstance fetchAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.FETCH);
/* 248:283 */     if (fetchAnnotation != null)
/* 249:    */     {
/* 250:284 */       org.hibernate.annotations.FetchMode annotationFetchMode = (org.hibernate.annotations.FetchMode)JandexHelper.getEnumValue(fetchAnnotation, "value", org.hibernate.annotations.FetchMode.class);
/* 251:    */       
/* 252:    */ 
/* 253:    */ 
/* 254:    */ 
/* 255:289 */       mode = EnumConversionHelper.annotationFetchModeToHibernateFetchMode(annotationFetchMode);
/* 256:    */     }
/* 257:292 */     return mode;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private String determineMapsId()
/* 261:    */   {
/* 262:297 */     AnnotationInstance mapsIdAnnotation = JandexHelper.getSingleAnnotation(annotations(), JPADotNames.MAPS_ID);
/* 263:298 */     if (mapsIdAnnotation == null) {
/* 264:299 */       return null;
/* 265:    */     }
/* 266:302 */     if ((!AttributeNature.MANY_TO_ONE.equals(getAssociationNature())) && (!AttributeNature.MANY_TO_ONE.equals(getAssociationNature()))) {
/* 267:304 */       throw new MappingException("@MapsId can only be specified on a many-to-one or one-to-one associations", getContext().getOrigin());
/* 268:    */     }
/* 269:310 */     String referencedIdAttributeName = (String)JandexHelper.getValue(mapsIdAnnotation, "value", String.class);
/* 270:    */     
/* 271:312 */     return referencedIdAttributeName;
/* 272:    */   }
/* 273:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.AssociationAttribute
 * JD-Core Version:    0.7.0.1
 */