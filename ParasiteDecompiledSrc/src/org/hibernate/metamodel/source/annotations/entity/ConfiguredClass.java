/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.ResolvedTypeWithMembers;
/*   5:    */ import com.fasterxml.classmate.members.HierarchicType;
/*   6:    */ import com.fasterxml.classmate.members.ResolvedMember;
/*   7:    */ import java.lang.reflect.Field;
/*   8:    */ import java.lang.reflect.Member;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import java.lang.reflect.Type;
/*  11:    */ import java.util.ArrayList;
/*  12:    */ import java.util.Collections;
/*  13:    */ import java.util.EnumMap;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Set;
/*  20:    */ import java.util.TreeMap;
/*  21:    */ import javax.persistence.AccessType;
/*  22:    */ import org.hibernate.AnnotationException;
/*  23:    */ import org.hibernate.AssertionFailure;
/*  24:    */ import org.hibernate.EntityMode;
/*  25:    */ import org.hibernate.HibernateException;
/*  26:    */ import org.hibernate.cfg.NotYetImplementedException;
/*  27:    */ import org.hibernate.metamodel.source.MappingException;
/*  28:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  29:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  30:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  31:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  32:    */ import org.hibernate.metamodel.source.annotations.ReflectionHelper;
/*  33:    */ import org.hibernate.metamodel.source.annotations.attribute.AssociationAttribute;
/*  34:    */ import org.hibernate.metamodel.source.annotations.attribute.AttributeNature;
/*  35:    */ import org.hibernate.metamodel.source.annotations.attribute.AttributeOverride;
/*  36:    */ import org.hibernate.metamodel.source.annotations.attribute.BasicAttribute;
/*  37:    */ import org.jboss.jandex.AnnotationInstance;
/*  38:    */ import org.jboss.jandex.AnnotationTarget;
/*  39:    */ import org.jboss.jandex.AnnotationValue;
/*  40:    */ import org.jboss.jandex.ClassInfo;
/*  41:    */ import org.jboss.jandex.DotName;
/*  42:    */ import org.jboss.jandex.FieldInfo;
/*  43:    */ import org.jboss.jandex.MethodInfo;
/*  44:    */ import org.jboss.logging.Logger;
/*  45:    */ 
/*  46:    */ public class ConfiguredClass
/*  47:    */ {
/*  48: 74 */   public static final Logger LOG = Logger.getLogger(ConfiguredClass.class.getName());
/*  49:    */   private final ConfiguredClass parent;
/*  50:    */   private final ClassInfo classInfo;
/*  51:    */   private final Class<?> clazz;
/*  52:    */   private final AccessType classAccessType;
/*  53:    */   private final ConfiguredClassType configuredClassType;
/*  54:    */   private final Map<String, BasicAttribute> idAttributeMap;
/*  55:    */   private final Map<String, AssociationAttribute> associationAttributeMap;
/*  56:    */   private final Map<String, BasicAttribute> simpleAttributeMap;
/*  57:    */   private BasicAttribute versionAttribute;
/*  58:124 */   private final Map<String, EmbeddableClass> embeddedClasses = new HashMap();
/*  59:    */   private final Map<String, AttributeOverride> attributeOverrideMap;
/*  60:132 */   private final Set<String> transientFieldNames = new HashSet();
/*  61:133 */   private final Set<String> transientMethodNames = new HashSet();
/*  62:    */   private final String customTuplizer;
/*  63:    */   private final EntityBindingContext localBindingContext;
/*  64:    */   
/*  65:    */   public ConfiguredClass(ClassInfo classInfo, AccessType defaultAccessType, ConfiguredClass parent, AnnotationBindingContext context)
/*  66:    */   {
/*  67:147 */     this.parent = parent;
/*  68:148 */     this.classInfo = classInfo;
/*  69:149 */     this.clazz = context.locateClassByName(classInfo.toString());
/*  70:150 */     this.configuredClassType = determineType();
/*  71:151 */     this.classAccessType = determineClassAccessType(defaultAccessType);
/*  72:152 */     this.customTuplizer = determineCustomTuplizer();
/*  73:    */     
/*  74:154 */     this.simpleAttributeMap = new TreeMap();
/*  75:155 */     this.idAttributeMap = new TreeMap();
/*  76:156 */     this.associationAttributeMap = new TreeMap();
/*  77:    */     
/*  78:158 */     this.localBindingContext = new EntityBindingContext(context, this);
/*  79:    */     
/*  80:160 */     collectAttributes();
/*  81:161 */     this.attributeOverrideMap = Collections.unmodifiableMap(findAttributeOverrides());
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getName()
/*  85:    */   {
/*  86:165 */     return this.clazz.getName();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Class<?> getConfiguredClass()
/*  90:    */   {
/*  91:169 */     return this.clazz;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public ClassInfo getClassInfo()
/*  95:    */   {
/*  96:173 */     return this.classInfo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ConfiguredClass getParent()
/* 100:    */   {
/* 101:177 */     return this.parent;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public EntityBindingContext getLocalBindingContext()
/* 105:    */   {
/* 106:181 */     return this.localBindingContext;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Iterable<BasicAttribute> getSimpleAttributes()
/* 110:    */   {
/* 111:185 */     return this.simpleAttributeMap.values();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Iterable<BasicAttribute> getIdAttributes()
/* 115:    */   {
/* 116:189 */     return this.idAttributeMap.values();
/* 117:    */   }
/* 118:    */   
/* 119:    */   public BasicAttribute getVersionAttribute()
/* 120:    */   {
/* 121:193 */     return this.versionAttribute;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Iterable<AssociationAttribute> getAssociationAttributes()
/* 125:    */   {
/* 126:197 */     return this.associationAttributeMap.values();
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Map<String, EmbeddableClass> getEmbeddedClasses()
/* 130:    */   {
/* 131:201 */     return this.embeddedClasses;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Map<String, AttributeOverride> getAttributeOverrideMap()
/* 135:    */   {
/* 136:205 */     return this.attributeOverrideMap;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public AccessType getClassAccessType()
/* 140:    */   {
/* 141:209 */     return this.classAccessType;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getCustomTuplizer()
/* 145:    */   {
/* 146:213 */     return this.customTuplizer;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:218 */     StringBuilder sb = new StringBuilder();
/* 152:219 */     sb.append("ConfiguredClass");
/* 153:220 */     sb.append("{clazz=").append(this.clazz.getSimpleName());
/* 154:221 */     sb.append('}');
/* 155:222 */     return sb.toString();
/* 156:    */   }
/* 157:    */   
/* 158:    */   private ConfiguredClassType determineType()
/* 159:    */   {
/* 160:226 */     if (this.classInfo.annotations().containsKey(JPADotNames.ENTITY)) {
/* 161:227 */       return ConfiguredClassType.ENTITY;
/* 162:    */     }
/* 163:229 */     if (this.classInfo.annotations().containsKey(JPADotNames.MAPPED_SUPERCLASS)) {
/* 164:230 */       return ConfiguredClassType.MAPPED_SUPERCLASS;
/* 165:    */     }
/* 166:232 */     if (this.classInfo.annotations().containsKey(JPADotNames.EMBEDDABLE)) {
/* 167:233 */       return ConfiguredClassType.EMBEDDABLE;
/* 168:    */     }
/* 169:236 */     return ConfiguredClassType.NON_ENTITY;
/* 170:    */   }
/* 171:    */   
/* 172:    */   private AccessType determineClassAccessType(AccessType defaultAccessType)
/* 173:    */   {
/* 174:242 */     AccessType accessType = defaultAccessType;
/* 175:    */     
/* 176:244 */     AnnotationInstance accessAnnotation = JandexHelper.getSingleAnnotation(this.classInfo, JPADotNames.ACCESS);
/* 177:245 */     if ((accessAnnotation != null) && (accessAnnotation.target().getClass().equals(ClassInfo.class))) {
/* 178:246 */       accessType = (AccessType)JandexHelper.getEnumValue(accessAnnotation, "value", AccessType.class);
/* 179:    */     }
/* 180:249 */     return accessType;
/* 181:    */   }
/* 182:    */   
/* 183:    */   private void collectAttributes()
/* 184:    */   {
/* 185:257 */     findTransientFieldAndMethodNames();
/* 186:    */     
/* 187:    */ 
/* 188:260 */     ResolvedTypeWithMembers resolvedType = this.localBindingContext.resolveMemberTypes(this.localBindingContext.getResolvedType(this.clazz));
/* 189:265 */     for (HierarchicType hierarchicType : resolvedType.allTypesAndOverrides()) {
/* 190:266 */       if (hierarchicType.getType().getErasedType().equals(this.clazz))
/* 191:    */       {
/* 192:267 */         resolvedType = this.localBindingContext.resolveMemberTypes(hierarchicType.getType());
/* 193:268 */         break;
/* 194:    */       }
/* 195:    */     }
/* 196:272 */     if (resolvedType == null) {
/* 197:273 */       throw new AssertionFailure("Unable to resolve types for " + this.clazz.getName());
/* 198:    */     }
/* 199:276 */     Set<String> explicitlyConfiguredMemberNames = createExplicitlyConfiguredAccessProperties(resolvedType);
/* 200:278 */     if (AccessType.FIELD.equals(this.classAccessType))
/* 201:    */     {
/* 202:279 */       Field[] fields = this.clazz.getDeclaredFields();
/* 203:280 */       Field.setAccessible(fields, true);
/* 204:281 */       for (Field field : fields) {
/* 205:282 */         if (isPersistentMember(this.transientFieldNames, explicitlyConfiguredMemberNames, field)) {
/* 206:283 */           createMappedAttribute(field, resolvedType, AccessType.FIELD);
/* 207:    */         }
/* 208:    */       }
/* 209:    */     }
/* 210:    */     else
/* 211:    */     {
/* 212:288 */       Method[] methods = this.clazz.getDeclaredMethods();
/* 213:289 */       Method.setAccessible(methods, true);
/* 214:290 */       for (Method method : methods) {
/* 215:291 */         if (isPersistentMember(this.transientMethodNames, explicitlyConfiguredMemberNames, method)) {
/* 216:292 */           createMappedAttribute(method, resolvedType, AccessType.PROPERTY);
/* 217:    */         }
/* 218:    */       }
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   private boolean isPersistentMember(Set<String> transientNames, Set<String> explicitlyConfiguredMemberNames, Member member)
/* 223:    */   {
/* 224:299 */     if (!ReflectionHelper.isProperty(member)) {
/* 225:300 */       return false;
/* 226:    */     }
/* 227:303 */     if (transientNames.contains(member.getName())) {
/* 228:304 */       return false;
/* 229:    */     }
/* 230:307 */     if (explicitlyConfiguredMemberNames.contains(ReflectionHelper.getPropertyName(member))) {
/* 231:308 */       return false;
/* 232:    */     }
/* 233:311 */     return true;
/* 234:    */   }
/* 235:    */   
/* 236:    */   private Set<String> createExplicitlyConfiguredAccessProperties(ResolvedTypeWithMembers resolvedMembers)
/* 237:    */   {
/* 238:322 */     Set<String> explicitAccessPropertyNames = new HashSet();
/* 239:    */     
/* 240:324 */     List<AnnotationInstance> accessAnnotations = (List)this.classInfo.annotations().get(JPADotNames.ACCESS);
/* 241:325 */     if (accessAnnotations == null) {
/* 242:326 */       return explicitAccessPropertyNames;
/* 243:    */     }
/* 244:330 */     for (AnnotationInstance accessAnnotation : accessAnnotations)
/* 245:    */     {
/* 246:332 */       AnnotationTarget annotationTarget = accessAnnotation.target();
/* 247:333 */       if ((annotationTarget.getClass().equals(MethodInfo.class)) || (annotationTarget.getClass().equals(FieldInfo.class)))
/* 248:    */       {
/* 249:338 */         AccessType accessType = (AccessType)JandexHelper.getEnumValue(accessAnnotation, "value", AccessType.class);
/* 250:340 */         if (isExplicitAttributeAccessAnnotationPlacedCorrectly(annotationTarget, accessType))
/* 251:    */         {
/* 252:    */           Member member;
/* 253:346 */           if ((annotationTarget instanceof MethodInfo))
/* 254:    */           {
/* 255:    */             Method m;
/* 256:    */             try
/* 257:    */             {
/* 258:349 */               m = this.clazz.getMethod(((MethodInfo)annotationTarget).name(), new Class[0]);
/* 259:    */             }
/* 260:    */             catch (NoSuchMethodException e)
/* 261:    */             {
/* 262:352 */               throw new HibernateException("Unable to load method " + ((MethodInfo)annotationTarget).name() + " of class " + this.clazz.getName());
/* 263:    */             }
/* 264:358 */             Member member = m;
/* 265:359 */             accessType = AccessType.PROPERTY;
/* 266:    */           }
/* 267:    */           else
/* 268:    */           {
/* 269:    */             Field f;
/* 270:    */             try
/* 271:    */             {
/* 272:364 */               f = this.clazz.getField(((FieldInfo)annotationTarget).name());
/* 273:    */             }
/* 274:    */             catch (NoSuchFieldException e)
/* 275:    */             {
/* 276:367 */               throw new HibernateException("Unable to load field " + ((FieldInfo)annotationTarget).name() + " of class " + this.clazz.getName());
/* 277:    */             }
/* 278:373 */             member = f;
/* 279:374 */             accessType = AccessType.FIELD;
/* 280:    */           }
/* 281:376 */           if (ReflectionHelper.isProperty(member))
/* 282:    */           {
/* 283:377 */             createMappedAttribute(member, resolvedMembers, accessType);
/* 284:378 */             explicitAccessPropertyNames.add(ReflectionHelper.getPropertyName(member));
/* 285:    */           }
/* 286:    */         }
/* 287:    */       }
/* 288:    */     }
/* 289:381 */     return explicitAccessPropertyNames;
/* 290:    */   }
/* 291:    */   
/* 292:    */   private boolean isExplicitAttributeAccessAnnotationPlacedCorrectly(AnnotationTarget annotationTarget, AccessType accessType)
/* 293:    */   {
/* 294:387 */     if (AccessType.FIELD.equals(this.classAccessType))
/* 295:    */     {
/* 296:388 */       if (!(annotationTarget instanceof MethodInfo))
/* 297:    */       {
/* 298:389 */         LOG.tracef("The access type of class %s is AccessType.FIELD. To override the access for an attribute @Access has to be placed on the property (getter)", this.classInfo.name().toString());
/* 299:    */         
/* 300:    */ 
/* 301:    */ 
/* 302:393 */         return false;
/* 303:    */       }
/* 304:396 */       if (!AccessType.PROPERTY.equals(accessType))
/* 305:    */       {
/* 306:397 */         LOG.tracef("The access type of class %s is AccessType.FIELD. To override the access for an attribute @Access has to be placed on the property (getter) with an access type of AccessType.PROPERTY. Using AccessType.FIELD on the property has no effect", this.classInfo.name().toString());
/* 307:    */         
/* 308:    */ 
/* 309:    */ 
/* 310:    */ 
/* 311:    */ 
/* 312:403 */         return false;
/* 313:    */       }
/* 314:    */     }
/* 315:409 */     if (AccessType.PROPERTY.equals(this.classAccessType))
/* 316:    */     {
/* 317:410 */       if (!(annotationTarget instanceof FieldInfo))
/* 318:    */       {
/* 319:411 */         LOG.tracef("The access type of class %s is AccessType.PROPERTY. To override the access for a field @Access has to be placed on the field ", this.classInfo.name().toString());
/* 320:    */         
/* 321:    */ 
/* 322:    */ 
/* 323:415 */         return false;
/* 324:    */       }
/* 325:418 */       if (!AccessType.FIELD.equals(accessType))
/* 326:    */       {
/* 327:419 */         LOG.tracef("The access type of class %s is AccessType.PROPERTY. To override the access for a field @Access has to be placed on the field with an access type of AccessType.FIELD. Using AccessType.PROPERTY on the field has no effect", this.classInfo.name().toString());
/* 328:    */         
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:425 */         return false;
/* 334:    */       }
/* 335:    */     }
/* 336:428 */     return true;
/* 337:    */   }
/* 338:    */   
/* 339:    */   private void createMappedAttribute(Member member, ResolvedTypeWithMembers resolvedType, AccessType accessType)
/* 340:    */   {
/* 341:432 */     String attributeName = ReflectionHelper.getPropertyName(member);
/* 342:    */     ResolvedMember[] resolvedMembers;
/* 343:    */     ResolvedMember[] resolvedMembers;
/* 344:434 */     if ((member instanceof Field)) {
/* 345:435 */       resolvedMembers = resolvedType.getMemberFields();
/* 346:    */     } else {
/* 347:438 */       resolvedMembers = resolvedType.getMemberMethods();
/* 348:    */     }
/* 349:440 */     Class<?> attributeType = (Class)findResolvedType(member.getName(), resolvedMembers);
/* 350:441 */     Map<DotName, List<AnnotationInstance>> annotations = JandexHelper.getMemberAnnotations(this.classInfo, member.getName());
/* 351:    */     
/* 352:    */ 
/* 353:    */ 
/* 354:445 */     AttributeNature attributeNature = determineAttributeNature(annotations);
/* 355:446 */     String accessTypeString = accessType.toString().toLowerCase();
/* 356:447 */     switch (1.$SwitchMap$org$hibernate$metamodel$source$annotations$attribute$AttributeNature[attributeNature.ordinal()])
/* 357:    */     {
/* 358:    */     case 1: 
/* 359:449 */       BasicAttribute attribute = BasicAttribute.createSimpleAttribute(attributeName, attributeType, annotations, accessTypeString, getLocalBindingContext());
/* 360:452 */       if (attribute.isId()) {
/* 361:453 */         this.idAttributeMap.put(attributeName, attribute);
/* 362:455 */       } else if (attribute.isVersioned())
/* 363:    */       {
/* 364:456 */         if (this.versionAttribute == null) {
/* 365:457 */           this.versionAttribute = attribute;
/* 366:    */         } else {
/* 367:460 */           throw new MappingException("Multiple version attributes", this.localBindingContext.getOrigin());
/* 368:    */         }
/* 369:    */       }
/* 370:    */       else {
/* 371:464 */         this.simpleAttributeMap.put(attributeName, attribute);
/* 372:    */       }
/* 373:466 */       break;
/* 374:    */     case 2: 
/* 375:469 */       throw new NotYetImplementedException("Element collections must still be implemented.");
/* 376:    */     case 3: 
/* 377:472 */       throw new NotYetImplementedException("Embedded ids must still be implemented.");
/* 378:    */     case 4: 
/* 379:475 */       AnnotationInstance targetAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.TARGET);
/* 380:479 */       if (targetAnnotation != null) {
/* 381:480 */         attributeType = this.localBindingContext.locateClassByName((String)JandexHelper.getValue(targetAnnotation, "value", String.class));
/* 382:    */       }
/* 383:484 */       resolveEmbeddable(attributeName, attributeType);
/* 384:485 */       break;
/* 385:    */     default: 
/* 386:489 */       AssociationAttribute attribute = AssociationAttribute.createAssociationAttribute(attributeName, attributeType, attributeNature, accessTypeString, annotations, getLocalBindingContext());
/* 387:    */       
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:    */ 
/* 394:497 */       this.associationAttributeMap.put(attributeName, attribute);
/* 395:    */     }
/* 396:    */   }
/* 397:    */   
/* 398:    */   private void resolveEmbeddable(String attributeName, Class<?> type)
/* 399:    */   {
/* 400:503 */     ClassInfo embeddableClassInfo = this.localBindingContext.getClassInfo(type.getName());
/* 401:504 */     if (embeddableClassInfo == null)
/* 402:    */     {
/* 403:505 */       String msg = String.format("Attribute '%s#%s' is annotated with @Embedded, but '%s' does not seem to be annotated with @Embeddable. Are all annotated classes added to the configuration?", new Object[] { getConfiguredClass().getSimpleName(), attributeName, type.getSimpleName() });
/* 404:    */       
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:    */ 
/* 409:    */ 
/* 410:512 */       throw new AnnotationException(msg);
/* 411:    */     }
/* 412:515 */     this.localBindingContext.resolveAllTypes(type.getName());
/* 413:516 */     EmbeddableHierarchy hierarchy = EmbeddableHierarchy.createEmbeddableHierarchy(this.localBindingContext.locateClassByName(embeddableClassInfo.toString()), attributeName, this.classAccessType, this.localBindingContext);
/* 414:    */     
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:522 */     this.embeddedClasses.put(attributeName, hierarchy.getLeaf());
/* 420:    */   }
/* 421:    */   
/* 422:    */   private AttributeNature determineAttributeNature(Map<DotName, List<AnnotationInstance>> annotations)
/* 423:    */   {
/* 424:533 */     EnumMap<AttributeNature, AnnotationInstance> discoveredAttributeTypes = new EnumMap(AttributeNature.class);
/* 425:    */     
/* 426:    */ 
/* 427:536 */     AnnotationInstance oneToOne = JandexHelper.getSingleAnnotation(annotations, JPADotNames.ONE_TO_ONE);
/* 428:537 */     if (oneToOne != null) {
/* 429:538 */       discoveredAttributeTypes.put(AttributeNature.ONE_TO_ONE, oneToOne);
/* 430:    */     }
/* 431:541 */     AnnotationInstance oneToMany = JandexHelper.getSingleAnnotation(annotations, JPADotNames.ONE_TO_MANY);
/* 432:542 */     if (oneToMany != null) {
/* 433:543 */       discoveredAttributeTypes.put(AttributeNature.ONE_TO_MANY, oneToMany);
/* 434:    */     }
/* 435:546 */     AnnotationInstance manyToOne = JandexHelper.getSingleAnnotation(annotations, JPADotNames.MANY_TO_ONE);
/* 436:547 */     if (manyToOne != null) {
/* 437:548 */       discoveredAttributeTypes.put(AttributeNature.MANY_TO_ONE, manyToOne);
/* 438:    */     }
/* 439:551 */     AnnotationInstance manyToMany = JandexHelper.getSingleAnnotation(annotations, JPADotNames.MANY_TO_MANY);
/* 440:552 */     if (manyToMany != null) {
/* 441:553 */       discoveredAttributeTypes.put(AttributeNature.MANY_TO_MANY, manyToMany);
/* 442:    */     }
/* 443:556 */     AnnotationInstance embedded = JandexHelper.getSingleAnnotation(annotations, JPADotNames.EMBEDDED);
/* 444:557 */     if (embedded != null) {
/* 445:558 */       discoveredAttributeTypes.put(AttributeNature.EMBEDDED, embedded);
/* 446:    */     }
/* 447:561 */     AnnotationInstance embeddedId = JandexHelper.getSingleAnnotation(annotations, JPADotNames.EMBEDDED_ID);
/* 448:562 */     if (embeddedId != null) {
/* 449:563 */       discoveredAttributeTypes.put(AttributeNature.EMBEDDED_ID, embeddedId);
/* 450:    */     }
/* 451:566 */     AnnotationInstance elementCollection = JandexHelper.getSingleAnnotation(annotations, JPADotNames.ELEMENT_COLLECTION);
/* 452:570 */     if (elementCollection != null) {
/* 453:571 */       discoveredAttributeTypes.put(AttributeNature.ELEMENT_COLLECTION, elementCollection);
/* 454:    */     }
/* 455:574 */     if (discoveredAttributeTypes.size() == 0) {
/* 456:575 */       return AttributeNature.BASIC;
/* 457:    */     }
/* 458:577 */     if (discoveredAttributeTypes.size() == 1) {
/* 459:578 */       return (AttributeNature)discoveredAttributeTypes.keySet().iterator().next();
/* 460:    */     }
/* 461:581 */     throw new AnnotationException("More than one association type configured for property  " + getName() + " of class " + getName());
/* 462:    */   }
/* 463:    */   
/* 464:    */   private Type findResolvedType(String name, ResolvedMember[] resolvedMembers)
/* 465:    */   {
/* 466:586 */     for (ResolvedMember resolvedMember : resolvedMembers) {
/* 467:587 */       if (resolvedMember.getName().equals(name)) {
/* 468:588 */         return resolvedMember.getType().getErasedType();
/* 469:    */       }
/* 470:    */     }
/* 471:591 */     throw new AssertionFailure(String.format("Unable to resolve type of attribute %s of class %s", new Object[] { name, this.classInfo.name().toString() }));
/* 472:    */   }
/* 473:    */   
/* 474:    */   private void findTransientFieldAndMethodNames()
/* 475:    */   {
/* 476:604 */     List<AnnotationInstance> transientMembers = (List)this.classInfo.annotations().get(JPADotNames.TRANSIENT);
/* 477:605 */     if (transientMembers == null) {
/* 478:606 */       return;
/* 479:    */     }
/* 480:609 */     for (AnnotationInstance transientMember : transientMembers)
/* 481:    */     {
/* 482:610 */       AnnotationTarget target = transientMember.target();
/* 483:611 */       if ((target instanceof FieldInfo)) {
/* 484:612 */         this.transientFieldNames.add(((FieldInfo)target).name());
/* 485:    */       } else {
/* 486:615 */         this.transientMethodNames.add(((MethodInfo)target).name());
/* 487:    */       }
/* 488:    */     }
/* 489:    */   }
/* 490:    */   
/* 491:    */   private Map<String, AttributeOverride> findAttributeOverrides()
/* 492:    */   {
/* 493:621 */     Map<String, AttributeOverride> attributeOverrideList = new HashMap();
/* 494:    */     
/* 495:623 */     AnnotationInstance attributeOverrideAnnotation = JandexHelper.getSingleAnnotation(this.classInfo, JPADotNames.ATTRIBUTE_OVERRIDE);
/* 496:627 */     if (attributeOverrideAnnotation != null)
/* 497:    */     {
/* 498:628 */       String prefix = createPathPrefix(attributeOverrideAnnotation.target());
/* 499:629 */       AttributeOverride override = new AttributeOverride(prefix, attributeOverrideAnnotation);
/* 500:630 */       attributeOverrideList.put(override.getAttributePath(), override);
/* 501:    */     }
/* 502:633 */     AnnotationInstance attributeOverridesAnnotation = JandexHelper.getSingleAnnotation(this.classInfo, JPADotNames.ATTRIBUTE_OVERRIDES);
/* 503:637 */     if (attributeOverridesAnnotation != null)
/* 504:    */     {
/* 505:638 */       AnnotationInstance[] annotationInstances = attributeOverridesAnnotation.value().asNestedArray();
/* 506:639 */       for (AnnotationInstance annotationInstance : annotationInstances)
/* 507:    */       {
/* 508:640 */         String prefix = createPathPrefix(attributeOverridesAnnotation.target());
/* 509:641 */         AttributeOverride override = new AttributeOverride(prefix, annotationInstance);
/* 510:642 */         attributeOverrideList.put(override.getAttributePath(), override);
/* 511:    */       }
/* 512:    */     }
/* 513:645 */     return attributeOverrideList;
/* 514:    */   }
/* 515:    */   
/* 516:    */   private String createPathPrefix(AnnotationTarget target)
/* 517:    */   {
/* 518:649 */     String prefix = null;
/* 519:650 */     if (((target instanceof FieldInfo)) || ((target instanceof MethodInfo))) {
/* 520:651 */       prefix = JandexHelper.getPropertyName(target);
/* 521:    */     }
/* 522:653 */     return prefix;
/* 523:    */   }
/* 524:    */   
/* 525:    */   private List<AnnotationInstance> findAssociationOverrides()
/* 526:    */   {
/* 527:657 */     List<AnnotationInstance> associationOverrideList = new ArrayList();
/* 528:    */     
/* 529:659 */     AnnotationInstance associationOverrideAnnotation = JandexHelper.getSingleAnnotation(this.classInfo, JPADotNames.ASSOCIATION_OVERRIDE);
/* 530:663 */     if (associationOverrideAnnotation != null) {
/* 531:664 */       associationOverrideList.add(associationOverrideAnnotation);
/* 532:    */     }
/* 533:667 */     AnnotationInstance associationOverridesAnnotation = JandexHelper.getSingleAnnotation(this.classInfo, JPADotNames.ASSOCIATION_OVERRIDES);
/* 534:671 */     if (associationOverrideAnnotation != null)
/* 535:    */     {
/* 536:672 */       AnnotationInstance[] attributeOverride = associationOverridesAnnotation.value().asNestedArray();
/* 537:673 */       Collections.addAll(associationOverrideList, attributeOverride);
/* 538:    */     }
/* 539:676 */     return associationOverrideList;
/* 540:    */   }
/* 541:    */   
/* 542:    */   private String determineCustomTuplizer()
/* 543:    */   {
/* 544:680 */     AnnotationInstance tuplizersAnnotation = JandexHelper.getSingleAnnotation(this.classInfo, HibernateDotNames.TUPLIZERS);
/* 545:683 */     if (tuplizersAnnotation == null) {
/* 546:684 */       return null;
/* 547:    */     }
/* 548:687 */     AnnotationInstance[] annotations = (AnnotationInstance[])JandexHelper.getValue(tuplizersAnnotation, "value", [Lorg.jboss.jandex.AnnotationInstance.class);
/* 549:    */     
/* 550:    */ 
/* 551:    */ 
/* 552:    */ 
/* 553:    */ 
/* 554:693 */     AnnotationInstance pojoTuplizerAnnotation = null;
/* 555:694 */     for (AnnotationInstance tuplizerAnnotation : annotations) {
/* 556:695 */       if (EntityMode.valueOf(tuplizerAnnotation.value("entityModeType").asEnum()) == EntityMode.POJO)
/* 557:    */       {
/* 558:696 */         pojoTuplizerAnnotation = tuplizerAnnotation;
/* 559:697 */         break;
/* 560:    */       }
/* 561:    */     }
/* 562:701 */     String customTuplizer = null;
/* 563:702 */     if (pojoTuplizerAnnotation != null) {
/* 564:703 */       customTuplizer = pojoTuplizerAnnotation.value("impl").asString();
/* 565:    */     }
/* 566:705 */     return customTuplizer;
/* 567:    */   }
/* 568:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.ConfiguredClass
 * JD-Core Version:    0.7.0.1
 */