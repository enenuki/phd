/*   1:    */ package org.hibernate.metamodel.source.annotations;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import javax.persistence.AccessType;
/*  10:    */ import org.hibernate.AnnotationException;
/*  11:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  12:    */ import org.hibernate.metamodel.source.annotations.entity.EntityClass;
/*  13:    */ import org.hibernate.metamodel.source.annotations.entity.RootEntitySourceImpl;
/*  14:    */ import org.hibernate.metamodel.source.annotations.entity.SubclassEntitySourceImpl;
/*  15:    */ import org.hibernate.metamodel.source.binder.EntityHierarchy;
/*  16:    */ import org.hibernate.metamodel.source.binder.EntitySource;
/*  17:    */ import org.hibernate.metamodel.source.binder.SubclassEntitySource;
/*  18:    */ import org.jboss.jandex.AnnotationInstance;
/*  19:    */ import org.jboss.jandex.AnnotationValue;
/*  20:    */ import org.jboss.jandex.ClassInfo;
/*  21:    */ import org.jboss.jandex.DotName;
/*  22:    */ import org.jboss.jandex.FieldInfo;
/*  23:    */ import org.jboss.jandex.Index;
/*  24:    */ import org.jboss.jandex.MethodInfo;
/*  25:    */ 
/*  26:    */ public class EntityHierarchyBuilder
/*  27:    */ {
/*  28: 58 */   private static final DotName OBJECT = DotName.createSimple(Object.class.getName());
/*  29:    */   
/*  30:    */   public static Set<EntityHierarchy> createEntityHierarchies(AnnotationBindingContext bindingContext)
/*  31:    */   {
/*  32: 69 */     Set<EntityHierarchy> hierarchies = new HashSet();
/*  33:    */     
/*  34: 71 */     List<DotName> processedEntities = new ArrayList();
/*  35: 72 */     Map<DotName, List<ClassInfo>> classToDirectSubClassMap = new HashMap();
/*  36: 73 */     Index index = bindingContext.getIndex();
/*  37: 74 */     for (ClassInfo info : index.getKnownClasses()) {
/*  38: 75 */       if ((isEntityClass(info)) && 
/*  39:    */       
/*  40:    */ 
/*  41:    */ 
/*  42: 79 */         (!processedEntities.contains(info.name())))
/*  43:    */       {
/*  44: 83 */         ClassInfo rootClassInfo = findRootEntityClassInfo(index, info);
/*  45: 84 */         List<ClassInfo> rootClassWithAllSubclasses = new ArrayList();
/*  46:    */         
/*  47:    */ 
/*  48: 87 */         addMappedSuperclasses(index, rootClassInfo, rootClassWithAllSubclasses);
/*  49:    */         
/*  50:    */ 
/*  51: 90 */         processHierarchy(bindingContext, rootClassInfo, rootClassWithAllSubclasses, processedEntities, classToDirectSubClassMap);
/*  52:    */         
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 98 */         AccessType defaultAccessType = determineDefaultAccessType(rootClassWithAllSubclasses);
/*  60: 99 */         org.hibernate.metamodel.binding.InheritanceType hierarchyInheritanceType = determineInheritanceType(rootClassInfo, rootClassWithAllSubclasses);
/*  61:    */         
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:105 */         EntityClass rootEntityClass = new EntityClass(rootClassInfo, null, defaultAccessType, hierarchyInheritanceType, bindingContext);
/*  67:    */         
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:112 */         RootEntitySourceImpl rootSource = new RootEntitySourceImpl(rootEntityClass);
/*  74:    */         
/*  75:114 */         addSubclassEntitySources(bindingContext, classToDirectSubClassMap, defaultAccessType, hierarchyInheritanceType, rootEntityClass, rootSource);
/*  76:    */         
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:124 */         hierarchies.add(new EntityHierarchyImpl(rootSource, hierarchyInheritanceType));
/*  86:    */       }
/*  87:    */     }
/*  88:126 */     return hierarchies;
/*  89:    */   }
/*  90:    */   
/*  91:    */   private static void addSubclassEntitySources(AnnotationBindingContext bindingContext, Map<DotName, List<ClassInfo>> classToDirectSubClassMap, AccessType defaultAccessType, org.hibernate.metamodel.binding.InheritanceType hierarchyInheritanceType, EntityClass entityClass, EntitySource entitySource)
/*  92:    */   {
/*  93:135 */     List<ClassInfo> subClassInfoList = (List)classToDirectSubClassMap.get(DotName.createSimple(entitySource.getClassName()));
/*  94:136 */     if (subClassInfoList == null) {
/*  95:137 */       return;
/*  96:    */     }
/*  97:139 */     for (ClassInfo subClassInfo : subClassInfoList)
/*  98:    */     {
/*  99:140 */       EntityClass subclassEntityClass = new EntityClass(subClassInfo, entityClass, defaultAccessType, hierarchyInheritanceType, bindingContext);
/* 100:    */       
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:147 */       SubclassEntitySource subclassEntitySource = new SubclassEntitySourceImpl(subclassEntityClass);
/* 107:148 */       entitySource.add(subclassEntitySource);
/* 108:149 */       addSubclassEntitySources(bindingContext, classToDirectSubClassMap, defaultAccessType, hierarchyInheritanceType, subclassEntityClass, subclassEntitySource);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   private static ClassInfo findRootEntityClassInfo(Index index, ClassInfo info)
/* 113:    */   {
/* 114:170 */     ClassInfo rootEntity = info;
/* 115:    */     
/* 116:172 */     DotName superName = info.superName();
/* 117:175 */     while (!OBJECT.equals(superName))
/* 118:    */     {
/* 119:176 */       ClassInfo tmpInfo = index.getClassByName(superName);
/* 120:177 */       if (isEntityClass(tmpInfo)) {
/* 121:178 */         rootEntity = tmpInfo;
/* 122:    */       }
/* 123:180 */       superName = tmpInfo.superName();
/* 124:    */     }
/* 125:182 */     return rootEntity;
/* 126:    */   }
/* 127:    */   
/* 128:    */   private static void addMappedSuperclasses(Index index, ClassInfo info, List<ClassInfo> classInfoList)
/* 129:    */   {
/* 130:186 */     DotName superName = info.superName();
/* 131:189 */     while (!OBJECT.equals(superName))
/* 132:    */     {
/* 133:190 */       ClassInfo tmpInfo = index.getClassByName(superName);
/* 134:191 */       if (isMappedSuperclass(tmpInfo)) {
/* 135:192 */         classInfoList.add(tmpInfo);
/* 136:    */       }
/* 137:194 */       superName = tmpInfo.superName();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   private static void processHierarchy(AnnotationBindingContext bindingContext, ClassInfo classInfo, List<ClassInfo> rootClassWithAllSubclasses, List<DotName> processedEntities, Map<DotName, List<ClassInfo>> classToDirectSubclassMap)
/* 142:    */   {
/* 143:217 */     processedEntities.add(classInfo.name());
/* 144:218 */     rootClassWithAllSubclasses.add(classInfo);
/* 145:219 */     List<ClassInfo> subClasses = bindingContext.getIndex().getKnownDirectSubclasses(classInfo.name());
/* 146:223 */     if (subClasses.isEmpty()) {
/* 147:224 */       bindingContext.resolveAllTypes(classInfo.name().toString());
/* 148:    */     }
/* 149:227 */     for (ClassInfo subClassInfo : subClasses)
/* 150:    */     {
/* 151:228 */       addSubClassToSubclassMap(classInfo.name(), subClassInfo, classToDirectSubclassMap);
/* 152:229 */       processHierarchy(bindingContext, subClassInfo, rootClassWithAllSubclasses, processedEntities, classToDirectSubclassMap);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private static void addSubClassToSubclassMap(DotName name, ClassInfo subClassInfo, Map<DotName, List<ClassInfo>> classToDirectSubclassMap)
/* 157:    */   {
/* 158:240 */     if (classToDirectSubclassMap.containsKey(name))
/* 159:    */     {
/* 160:241 */       ((List)classToDirectSubclassMap.get(name)).add(subClassInfo);
/* 161:    */     }
/* 162:    */     else
/* 163:    */     {
/* 164:244 */       List<ClassInfo> subclassList = new ArrayList();
/* 165:245 */       subclassList.add(subClassInfo);
/* 166:246 */       classToDirectSubclassMap.put(name, subclassList);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   private static boolean isEntityClass(ClassInfo info)
/* 171:    */   {
/* 172:258 */     if (info == null) {
/* 173:259 */       return false;
/* 174:    */     }
/* 175:263 */     AnnotationInstance jpaEntityAnnotation = JandexHelper.getSingleAnnotation(info, JPADotNames.ENTITY);
/* 176:264 */     if (jpaEntityAnnotation == null) {
/* 177:265 */       return false;
/* 178:    */     }
/* 179:269 */     AnnotationInstance mappedSuperClassAnnotation = JandexHelper.getSingleAnnotation(info, JPADotNames.MAPPED_SUPERCLASS);
/* 180:    */     
/* 181:    */ 
/* 182:272 */     String className = info.toString();
/* 183:273 */     assertNotEntityAndMappedSuperClass(jpaEntityAnnotation, mappedSuperClassAnnotation, className);
/* 184:    */     
/* 185:275 */     AnnotationInstance embeddableAnnotation = JandexHelper.getSingleAnnotation(info, JPADotNames.EMBEDDABLE);
/* 186:    */     
/* 187:    */ 
/* 188:278 */     assertNotEntityAndEmbeddable(jpaEntityAnnotation, embeddableAnnotation, className);
/* 189:    */     
/* 190:280 */     return true;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private static boolean isMappedSuperclass(ClassInfo info)
/* 194:    */   {
/* 195:291 */     if (info == null) {
/* 196:292 */       return false;
/* 197:    */     }
/* 198:296 */     AnnotationInstance mappedSuperclassAnnotation = JandexHelper.getSingleAnnotation(info, JPADotNames.MAPPED_SUPERCLASS);
/* 199:    */     
/* 200:    */ 
/* 201:    */ 
/* 202:300 */     return mappedSuperclassAnnotation != null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static void assertNotEntityAndMappedSuperClass(AnnotationInstance jpaEntityAnnotation, AnnotationInstance mappedSuperClassAnnotation, String className)
/* 206:    */   {
/* 207:304 */     if ((jpaEntityAnnotation != null) && (mappedSuperClassAnnotation != null)) {
/* 208:305 */       throw new AnnotationException("An entity cannot be annotated with both @Entity and @MappedSuperclass. " + className + " has both annotations.");
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   private static void assertNotEntityAndEmbeddable(AnnotationInstance jpaEntityAnnotation, AnnotationInstance embeddableAnnotation, String className)
/* 213:    */   {
/* 214:312 */     if ((jpaEntityAnnotation != null) && (embeddableAnnotation != null)) {
/* 215:313 */       throw new AnnotationException("An entity cannot be annotated with both @Entity and @Embeddable. " + className + " has both annotations.");
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   private static AccessType determineDefaultAccessType(List<ClassInfo> classes)
/* 220:    */   {
/* 221:327 */     AccessType accessTypeByEmbeddedIdPlacement = null;
/* 222:328 */     AccessType accessTypeByIdPlacement = null;
/* 223:329 */     for (ClassInfo info : classes)
/* 224:    */     {
/* 225:330 */       List<AnnotationInstance> idAnnotations = (List)info.annotations().get(JPADotNames.ID);
/* 226:331 */       List<AnnotationInstance> embeddedIdAnnotations = (List)info.annotations().get(JPADotNames.EMBEDDED_ID);
/* 227:333 */       if (CollectionHelper.isNotEmpty(embeddedIdAnnotations)) {
/* 228:334 */         accessTypeByEmbeddedIdPlacement = determineAccessTypeByIdPlacement(embeddedIdAnnotations);
/* 229:    */       }
/* 230:336 */       if (CollectionHelper.isNotEmpty(idAnnotations)) {
/* 231:337 */         accessTypeByIdPlacement = determineAccessTypeByIdPlacement(idAnnotations);
/* 232:    */       }
/* 233:    */     }
/* 234:340 */     if (accessTypeByEmbeddedIdPlacement != null) {
/* 235:341 */       return accessTypeByEmbeddedIdPlacement;
/* 236:    */     }
/* 237:343 */     if (accessTypeByIdPlacement != null) {
/* 238:344 */       return accessTypeByIdPlacement;
/* 239:    */     }
/* 240:347 */     return throwIdNotFoundAnnotationException(classes);
/* 241:    */   }
/* 242:    */   
/* 243:    */   private static AccessType determineAccessTypeByIdPlacement(List<AnnotationInstance> idAnnotations)
/* 244:    */   {
/* 245:352 */     AccessType accessType = null;
/* 246:353 */     for (AnnotationInstance annotation : idAnnotations)
/* 247:    */     {
/* 248:    */       AccessType tmpAccessType;
/* 249:355 */       if ((annotation.target() instanceof FieldInfo))
/* 250:    */       {
/* 251:356 */         tmpAccessType = AccessType.FIELD;
/* 252:    */       }
/* 253:    */       else
/* 254:    */       {
/* 255:    */         AccessType tmpAccessType;
/* 256:358 */         if ((annotation.target() instanceof MethodInfo)) {
/* 257:359 */           tmpAccessType = AccessType.PROPERTY;
/* 258:    */         } else {
/* 259:362 */           throw new AnnotationException("Invalid placement of @Id annotation");
/* 260:    */         }
/* 261:    */       }
/* 262:    */       AccessType tmpAccessType;
/* 263:365 */       if (accessType == null) {
/* 264:366 */         accessType = tmpAccessType;
/* 265:369 */       } else if (!accessType.equals(tmpAccessType)) {
/* 266:370 */         throw new AnnotationException("Inconsistent placement of @Id annotation within hierarchy ");
/* 267:    */       }
/* 268:    */     }
/* 269:374 */     return accessType;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private static org.hibernate.metamodel.binding.InheritanceType determineInheritanceType(ClassInfo rootClassInfo, List<ClassInfo> classes)
/* 273:    */   {
/* 274:378 */     if (classes.size() == 1) {
/* 275:379 */       return org.hibernate.metamodel.binding.InheritanceType.NO_INHERITANCE;
/* 276:    */     }
/* 277:383 */     org.hibernate.metamodel.binding.InheritanceType inheritanceType = org.hibernate.metamodel.binding.InheritanceType.SINGLE_TABLE;
/* 278:384 */     AnnotationInstance inheritanceAnnotation = JandexHelper.getSingleAnnotation(rootClassInfo, JPADotNames.INHERITANCE);
/* 279:387 */     if (inheritanceAnnotation != null)
/* 280:    */     {
/* 281:388 */       String enumName = inheritanceAnnotation.value("strategy").asEnum();
/* 282:389 */       javax.persistence.InheritanceType jpaInheritanceType = (javax.persistence.InheritanceType)Enum.valueOf(javax.persistence.InheritanceType.class, enumName);
/* 283:    */       
/* 284:    */ 
/* 285:392 */       inheritanceType = org.hibernate.metamodel.binding.InheritanceType.get(jpaInheritanceType);
/* 286:    */     }
/* 287:396 */     for (ClassInfo info : classes) {
/* 288:397 */       if (!rootClassInfo.equals(info))
/* 289:    */       {
/* 290:400 */         inheritanceAnnotation = JandexHelper.getSingleAnnotation(info, JPADotNames.INHERITANCE);
/* 291:403 */         if (inheritanceAnnotation != null) {
/* 292:404 */           throw new AnnotationException(String.format("The inheritance type for %s must be specified on the root entity %s", new Object[] { hierarchyListString(classes), rootClassInfo.name().toString() }));
/* 293:    */         }
/* 294:    */       }
/* 295:    */     }
/* 296:414 */     return inheritanceType;
/* 297:    */   }
/* 298:    */   
/* 299:    */   private static AccessType throwIdNotFoundAnnotationException(List<ClassInfo> classes)
/* 300:    */   {
/* 301:418 */     StringBuilder builder = new StringBuilder();
/* 302:419 */     builder.append("Unable to determine identifier attribute for class hierarchy consisting of the classe(s) ");
/* 303:420 */     builder.append(hierarchyListString(classes));
/* 304:421 */     throw new AnnotationException(builder.toString());
/* 305:    */   }
/* 306:    */   
/* 307:    */   private static String hierarchyListString(List<ClassInfo> classes)
/* 308:    */   {
/* 309:425 */     StringBuilder builder = new StringBuilder();
/* 310:426 */     builder.append("[");
/* 311:    */     
/* 312:428 */     int count = 0;
/* 313:429 */     for (ClassInfo info : classes)
/* 314:    */     {
/* 315:430 */       builder.append(info.name().toString());
/* 316:431 */       if (count < classes.size() - 1) {
/* 317:432 */         builder.append(", ");
/* 318:    */       }
/* 319:434 */       count++;
/* 320:    */     }
/* 321:436 */     builder.append("]");
/* 322:437 */     return builder.toString();
/* 323:    */   }
/* 324:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.EntityHierarchyBuilder
 * JD-Core Version:    0.7.0.1
 */