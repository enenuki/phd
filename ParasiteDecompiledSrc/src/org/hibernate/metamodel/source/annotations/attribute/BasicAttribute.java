/*   1:    */ package org.hibernate.metamodel.source.annotations.attribute;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Map;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import org.hibernate.AnnotationException;
/*  10:    */ import org.hibernate.annotations.GenerationTime;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.mapping.PropertyGeneration;
/*  13:    */ import org.hibernate.metamodel.Metadata.Options;
/*  14:    */ import org.hibernate.metamodel.binding.IdGenerator;
/*  15:    */ import org.hibernate.metamodel.source.MappingException;
/*  16:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  17:    */ import org.hibernate.metamodel.source.annotations.EnumConversionHelper;
/*  18:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  19:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  20:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  21:    */ import org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolver;
/*  22:    */ import org.hibernate.metamodel.source.annotations.attribute.type.AttributeTypeResolverImpl;
/*  23:    */ import org.hibernate.metamodel.source.annotations.attribute.type.CompositeAttributeTypeResolver;
/*  24:    */ import org.hibernate.metamodel.source.annotations.attribute.type.EnumeratedTypeResolver;
/*  25:    */ import org.hibernate.metamodel.source.annotations.attribute.type.LobTypeResolver;
/*  26:    */ import org.hibernate.metamodel.source.annotations.attribute.type.TemporalTypeResolver;
/*  27:    */ import org.hibernate.metamodel.source.annotations.entity.EntityBindingContext;
/*  28:    */ import org.jboss.jandex.AnnotationInstance;
/*  29:    */ import org.jboss.jandex.AnnotationValue;
/*  30:    */ import org.jboss.jandex.DotName;
/*  31:    */ 
/*  32:    */ public class BasicAttribute
/*  33:    */   extends MappedAttribute
/*  34:    */ {
/*  35:    */   private final IdGenerator idGenerator;
/*  36:    */   private final boolean isVersioned;
/*  37: 76 */   private boolean isLazy = false;
/*  38: 81 */   private boolean isOptional = true;
/*  39:    */   private PropertyGeneration propertyGeneration;
/*  40: 87 */   private boolean isInsertable = true;
/*  41: 88 */   private boolean isUpdatable = true;
/*  42:    */   private final String customWriteFragment;
/*  43:    */   private final String customReadFragment;
/*  44:    */   private final String checkCondition;
/*  45:    */   private AttributeTypeResolver resolver;
/*  46:    */   
/*  47:    */   public static BasicAttribute createSimpleAttribute(String name, Class<?> attributeType, Map<DotName, List<AnnotationInstance>> annotations, String accessType, EntityBindingContext context)
/*  48:    */   {
/*  49:100 */     return new BasicAttribute(name, attributeType, accessType, annotations, context);
/*  50:    */   }
/*  51:    */   
/*  52:    */   BasicAttribute(String name, Class<?> attributeType, String accessType, Map<DotName, List<AnnotationInstance>> annotations, EntityBindingContext context)
/*  53:    */   {
/*  54:108 */     super(name, attributeType, accessType, annotations, context);
/*  55:    */     
/*  56:110 */     AnnotationInstance versionAnnotation = JandexHelper.getSingleAnnotation(annotations, JPADotNames.VERSION);
/*  57:111 */     this.isVersioned = (versionAnnotation != null);
/*  58:113 */     if (isId())
/*  59:    */     {
/*  60:115 */       getColumnValues().setUnique(true);
/*  61:116 */       getColumnValues().setNullable(false);
/*  62:117 */       this.idGenerator = checkGeneratedValueAnnotation();
/*  63:    */     }
/*  64:    */     else
/*  65:    */     {
/*  66:120 */       this.idGenerator = null;
/*  67:    */     }
/*  68:123 */     checkBasicAnnotation();
/*  69:124 */     checkGeneratedAnnotation();
/*  70:    */     
/*  71:126 */     List<AnnotationInstance> columnTransformerAnnotations = getAllColumnTransformerAnnotations();
/*  72:127 */     String[] readWrite = createCustomReadWrite(columnTransformerAnnotations);
/*  73:128 */     this.customReadFragment = readWrite[0];
/*  74:129 */     this.customWriteFragment = readWrite[1];
/*  75:130 */     this.checkCondition = parseCheckAnnotation();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isVersioned()
/*  79:    */   {
/*  80:134 */     return this.isVersioned;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isLazy()
/*  84:    */   {
/*  85:138 */     return this.isLazy;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isOptional()
/*  89:    */   {
/*  90:142 */     return this.isOptional;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isInsertable()
/*  94:    */   {
/*  95:146 */     return this.isInsertable;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isUpdatable()
/*  99:    */   {
/* 100:150 */     return this.isUpdatable;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public PropertyGeneration getPropertyGeneration()
/* 104:    */   {
/* 105:154 */     return this.propertyGeneration;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getCustomWriteFragment()
/* 109:    */   {
/* 110:158 */     return this.customWriteFragment;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getCustomReadFragment()
/* 114:    */   {
/* 115:162 */     return this.customReadFragment;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getCheckCondition()
/* 119:    */   {
/* 120:166 */     return this.checkCondition;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public IdGenerator getIdGenerator()
/* 124:    */   {
/* 125:170 */     return this.idGenerator;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String toString()
/* 129:    */   {
/* 130:175 */     StringBuilder sb = new StringBuilder();
/* 131:176 */     sb.append("SimpleAttribute");
/* 132:177 */     sb.append("{name=").append(getName());
/* 133:178 */     return sb.toString();
/* 134:    */   }
/* 135:    */   
/* 136:    */   private void checkBasicAnnotation()
/* 137:    */   {
/* 138:182 */     AnnotationInstance basicAnnotation = JandexHelper.getSingleAnnotation(annotations(), JPADotNames.BASIC);
/* 139:183 */     if (basicAnnotation != null)
/* 140:    */     {
/* 141:184 */       FetchType fetchType = FetchType.LAZY;
/* 142:185 */       AnnotationValue fetchValue = basicAnnotation.value("fetch");
/* 143:186 */       if (fetchValue != null) {
/* 144:187 */         fetchType = (FetchType)Enum.valueOf(FetchType.class, fetchValue.asEnum());
/* 145:    */       }
/* 146:189 */       this.isLazy = (fetchType == FetchType.LAZY);
/* 147:    */       
/* 148:191 */       AnnotationValue optionalValue = basicAnnotation.value("optional");
/* 149:192 */       if (optionalValue != null) {
/* 150:193 */         this.isOptional = optionalValue.asBoolean();
/* 151:    */       }
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void checkGeneratedAnnotation()
/* 156:    */   {
/* 157:200 */     AnnotationInstance generatedAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.GENERATED);
/* 158:204 */     if (generatedAnnotation != null)
/* 159:    */     {
/* 160:205 */       this.isInsertable = false;
/* 161:    */       
/* 162:207 */       AnnotationValue generationTimeValue = generatedAnnotation.value();
/* 163:208 */       if (generationTimeValue != null)
/* 164:    */       {
/* 165:209 */         GenerationTime genTime = (GenerationTime)Enum.valueOf(GenerationTime.class, generationTimeValue.asEnum());
/* 166:210 */         if (GenerationTime.ALWAYS.equals(genTime))
/* 167:    */         {
/* 168:211 */           this.isUpdatable = false;
/* 169:212 */           this.propertyGeneration = PropertyGeneration.parse(genTime.toString().toLowerCase());
/* 170:    */         }
/* 171:    */       }
/* 172:    */     }
/* 173:    */   }
/* 174:    */   
/* 175:    */   private List<AnnotationInstance> getAllColumnTransformerAnnotations()
/* 176:    */   {
/* 177:219 */     List<AnnotationInstance> allColumnTransformerAnnotations = new ArrayList();
/* 178:    */     
/* 179:    */ 
/* 180:222 */     AnnotationInstance columnTransformersAnnotations = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.COLUMN_TRANSFORMERS);
/* 181:226 */     if (columnTransformersAnnotations != null)
/* 182:    */     {
/* 183:227 */       AnnotationInstance[] annotationInstances = ((AnnotationInstance)allColumnTransformerAnnotations.get(0)).value().asNestedArray();
/* 184:228 */       allColumnTransformerAnnotations.addAll(Arrays.asList(annotationInstances));
/* 185:    */     }
/* 186:231 */     AnnotationInstance columnTransformerAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.COLUMN_TRANSFORMER);
/* 187:235 */     if (columnTransformerAnnotation != null) {
/* 188:236 */       allColumnTransformerAnnotations.add(columnTransformerAnnotation);
/* 189:    */     }
/* 190:238 */     return allColumnTransformerAnnotations;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private String[] createCustomReadWrite(List<AnnotationInstance> columnTransformerAnnotations)
/* 194:    */   {
/* 195:242 */     String[] readWrite = new String[2];
/* 196:    */     
/* 197:244 */     boolean alreadyProcessedForColumn = false;
/* 198:245 */     for (AnnotationInstance annotationInstance : columnTransformerAnnotations)
/* 199:    */     {
/* 200:246 */       String forColumn = annotationInstance.value("forColumn") == null ? null : annotationInstance.value("forColumn").asString();
/* 201:249 */       if ((forColumn == null) || (forColumn.equals(getName())))
/* 202:    */       {
/* 203:253 */         if (alreadyProcessedForColumn) {
/* 204:254 */           throw new AnnotationException("Multiple definition of read/write conditions for column " + getName());
/* 205:    */         }
/* 206:257 */         readWrite[0] = (annotationInstance.value("read") == null ? null : annotationInstance.value("read").asString());
/* 207:    */         
/* 208:259 */         readWrite[1] = (annotationInstance.value("write") == null ? null : annotationInstance.value("write").asString());
/* 209:    */         
/* 210:    */ 
/* 211:262 */         alreadyProcessedForColumn = true;
/* 212:    */       }
/* 213:    */     }
/* 214:264 */     return readWrite;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private String parseCheckAnnotation()
/* 218:    */   {
/* 219:268 */     String checkCondition = null;
/* 220:269 */     AnnotationInstance checkAnnotation = JandexHelper.getSingleAnnotation(annotations(), HibernateDotNames.CHECK);
/* 221:270 */     if (checkAnnotation != null) {
/* 222:271 */       checkCondition = checkAnnotation.value("constraints").toString();
/* 223:    */     }
/* 224:273 */     return checkCondition;
/* 225:    */   }
/* 226:    */   
/* 227:    */   private IdGenerator checkGeneratedValueAnnotation()
/* 228:    */   {
/* 229:277 */     IdGenerator generator = null;
/* 230:278 */     AnnotationInstance generatedValueAnnotation = JandexHelper.getSingleAnnotation(annotations(), JPADotNames.GENERATED_VALUE);
/* 231:282 */     if (generatedValueAnnotation != null)
/* 232:    */     {
/* 233:283 */       String name = (String)JandexHelper.getValue(generatedValueAnnotation, "generator", String.class);
/* 234:284 */       if (StringHelper.isNotEmpty(name))
/* 235:    */       {
/* 236:285 */         generator = getContext().getMetadataImplementor().getIdGenerator(name);
/* 237:286 */         if (generator == null) {
/* 238:287 */           throw new MappingException(String.format("Unable to find named generator %s", new Object[] { name }), null);
/* 239:    */         }
/* 240:    */       }
/* 241:    */       else
/* 242:    */       {
/* 243:291 */         GenerationType genType = (GenerationType)JandexHelper.getEnumValue(generatedValueAnnotation, "strategy", GenerationType.class);
/* 244:    */         
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:296 */         String strategy = EnumConversionHelper.generationTypeToGeneratorStrategyName(genType, getContext().getMetadataImplementor().getOptions().useNewIdentifierGenerators());
/* 249:    */         
/* 250:    */ 
/* 251:    */ 
/* 252:300 */         generator = new IdGenerator(null, strategy, null);
/* 253:    */       }
/* 254:    */     }
/* 255:303 */     return generator;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public AttributeTypeResolver getHibernateTypeResolver()
/* 259:    */   {
/* 260:308 */     if (this.resolver == null) {
/* 261:309 */       this.resolver = getDefaultHibernateTypeResolver();
/* 262:    */     }
/* 263:311 */     return this.resolver;
/* 264:    */   }
/* 265:    */   
/* 266:    */   private AttributeTypeResolver getDefaultHibernateTypeResolver()
/* 267:    */   {
/* 268:315 */     CompositeAttributeTypeResolver resolver = new CompositeAttributeTypeResolver(new AttributeTypeResolverImpl(this));
/* 269:    */     
/* 270:    */ 
/* 271:    */ 
/* 272:    */ 
/* 273:320 */     resolver.addHibernateTypeResolver(new TemporalTypeResolver(this));
/* 274:321 */     resolver.addHibernateTypeResolver(new LobTypeResolver(this));
/* 275:322 */     resolver.addHibernateTypeResolver(new EnumeratedTypeResolver(this));
/* 276:323 */     return resolver;
/* 277:    */   }
/* 278:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.attribute.BasicAttribute
 * JD-Core Version:    0.7.0.1
 */