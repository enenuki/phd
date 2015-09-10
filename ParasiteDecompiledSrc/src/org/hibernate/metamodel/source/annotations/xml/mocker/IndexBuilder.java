/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ import org.hibernate.internal.CoreMessageLogger;
/*  11:    */ import org.hibernate.internal.util.StringHelper;
/*  12:    */ import org.hibernate.metamodel.source.annotations.xml.filter.IndexedAnnotationFilter;
/*  13:    */ import org.hibernate.service.ServiceRegistry;
/*  14:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  15:    */ import org.jboss.jandex.AnnotationInstance;
/*  16:    */ import org.jboss.jandex.ClassInfo;
/*  17:    */ import org.jboss.jandex.DotName;
/*  18:    */ import org.jboss.jandex.Index;
/*  19:    */ import org.jboss.logging.Logger;
/*  20:    */ 
/*  21:    */ public class IndexBuilder
/*  22:    */ {
/*  23: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, IndexBuilder.class.getName());
/*  24:    */   private Map<DotName, List<AnnotationInstance>> annotations;
/*  25:    */   private Map<DotName, List<ClassInfo>> subclasses;
/*  26:    */   private Map<DotName, List<ClassInfo>> implementors;
/*  27:    */   private Map<DotName, ClassInfo> classes;
/*  28:    */   private Index index;
/*  29:    */   private Map<DotName, Map<DotName, List<AnnotationInstance>>> classInfoAnnotationsMap;
/*  30:    */   private Map<DotName, Map<DotName, List<AnnotationInstance>>> indexedClassInfoAnnotationsMap;
/*  31:    */   private ServiceRegistry serviceRegistry;
/*  32:    */   
/*  33:    */   IndexBuilder(Index index, ServiceRegistry serviceRegistry)
/*  34:    */   {
/*  35: 63 */     this.index = index;
/*  36: 64 */     this.serviceRegistry = serviceRegistry;
/*  37: 65 */     this.annotations = new HashMap();
/*  38: 66 */     this.subclasses = new HashMap();
/*  39: 67 */     this.implementors = new HashMap();
/*  40: 68 */     this.classes = new HashMap();
/*  41: 69 */     this.classInfoAnnotationsMap = new HashMap();
/*  42: 70 */     this.indexedClassInfoAnnotationsMap = new HashMap();
/*  43:    */   }
/*  44:    */   
/*  45:    */   Index build(EntityMappingsMocker.Default globalDefaults)
/*  46:    */   {
/*  47: 83 */     for (ClassInfo ci : this.index.getKnownClasses())
/*  48:    */     {
/*  49: 84 */       DotName name = ci.name();
/*  50: 85 */       if (!this.indexedClassInfoAnnotationsMap.containsKey(name)) {
/*  51: 89 */         if ((ci.annotations() != null) && (!ci.annotations().isEmpty()))
/*  52:    */         {
/*  53: 90 */           Map<DotName, List<AnnotationInstance>> tmp = new HashMap(ci.annotations());
/*  54: 91 */           DefaultConfigurationHelper.INSTANCE.applyDefaults(tmp, globalDefaults);
/*  55: 92 */           mergeAnnotationMap(tmp, this.annotations);
/*  56: 93 */           this.classes.put(name, ci);
/*  57: 94 */           if (ci.superName() != null) {
/*  58: 95 */             addSubClasses(ci.superName(), ci);
/*  59:    */           }
/*  60: 97 */           if ((ci.interfaces() != null) && (ci.interfaces().length > 0)) {
/*  61: 98 */             addImplementors(ci.interfaces(), ci);
/*  62:    */           }
/*  63:    */         }
/*  64:    */       }
/*  65:    */     }
/*  66:102 */     return Index.create(this.annotations, this.subclasses, this.implementors, this.classes);
/*  67:    */   }
/*  68:    */   
/*  69:    */   Map<DotName, List<AnnotationInstance>> getAnnotations()
/*  70:    */   {
/*  71:107 */     return Collections.unmodifiableMap(this.annotations);
/*  72:    */   }
/*  73:    */   
/*  74:    */   void mappingMetadataComplete()
/*  75:    */   {
/*  76:113 */     LOG.debug("xml-mapping-metadata-complete is specified in persistence-unit-metadata, ignore JPA annotations.");
/*  77:    */     
/*  78:    */ 
/*  79:116 */     this.index = Index.create(new HashMap(), new HashMap(), new HashMap(), new HashMap());
/*  80:    */   }
/*  81:    */   
/*  82:    */   void metadataComplete(DotName name)
/*  83:    */   {
/*  84:128 */     LOG.debug("metadata-complete is specified in " + name + ", ignore JPA annotations.");
/*  85:    */     
/*  86:    */ 
/*  87:131 */     getIndexedAnnotations(name).clear();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Map<DotName, List<AnnotationInstance>> getIndexedAnnotations(DotName name)
/*  91:    */   {
/*  92:135 */     Map<DotName, List<AnnotationInstance>> map = (Map)this.indexedClassInfoAnnotationsMap.get(name);
/*  93:136 */     if (map == null)
/*  94:    */     {
/*  95:137 */       ClassInfo ci = this.index.getClassByName(name);
/*  96:138 */       if ((ci == null) || (ci.annotations() == null))
/*  97:    */       {
/*  98:139 */         map = Collections.emptyMap();
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102:142 */         map = new HashMap(ci.annotations());
/* 103:144 */         for (DotName globalAnnotationName : DefaultConfigurationHelper.GLOBAL_ANNOTATIONS) {
/* 104:145 */           if (map.containsKey(globalAnnotationName)) {
/* 105:146 */             map.put(globalAnnotationName, Collections.emptyList());
/* 106:    */           }
/* 107:    */         }
/* 108:    */       }
/* 109:150 */       this.indexedClassInfoAnnotationsMap.put(name, map);
/* 110:    */     }
/* 111:152 */     return map;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Map<DotName, List<AnnotationInstance>> getClassInfoAnnotationsMap(DotName name)
/* 115:    */   {
/* 116:156 */     return (Map)this.classInfoAnnotationsMap.get(name);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public ClassInfo getClassInfo(DotName name)
/* 120:    */   {
/* 121:160 */     return (ClassInfo)this.classes.get(name);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public ClassInfo getIndexedClassInfo(DotName name)
/* 125:    */   {
/* 126:164 */     return this.index.getClassByName(name);
/* 127:    */   }
/* 128:    */   
/* 129:    */   void collectGlobalConfigurationFromIndex(GlobalAnnotations globalAnnotations)
/* 130:    */   {
/* 131:168 */     for (DotName annName : DefaultConfigurationHelper.GLOBAL_ANNOTATIONS)
/* 132:    */     {
/* 133:169 */       List<AnnotationInstance> annotationInstanceList = this.index.getAnnotations(annName);
/* 134:170 */       if (MockHelper.isNotEmpty(annotationInstanceList)) {
/* 135:171 */         globalAnnotations.addIndexedAnnotationInstance(annotationInstanceList);
/* 136:    */       }
/* 137:    */     }
/* 138:174 */     globalAnnotations.filterIndexedAnnotations();
/* 139:    */   }
/* 140:    */   
/* 141:    */   void finishGlobalConfigurationMocking(GlobalAnnotations globalAnnotations)
/* 142:    */   {
/* 143:178 */     this.annotations.putAll(globalAnnotations.getAnnotationInstanceMap());
/* 144:    */   }
/* 145:    */   
/* 146:    */   void finishEntityObject(DotName name, EntityMappingsMocker.Default defaults)
/* 147:    */   {
/* 148:182 */     Map<DotName, List<AnnotationInstance>> map = (Map)this.classInfoAnnotationsMap.get(name);
/* 149:183 */     if (map == null) {
/* 150:184 */       throw new AssertionFailure("Calling finish entity object " + name + " before create it.");
/* 151:    */     }
/* 152:187 */     if (this.indexedClassInfoAnnotationsMap.containsKey(name))
/* 153:    */     {
/* 154:188 */       Map<DotName, List<AnnotationInstance>> tmp = getIndexedAnnotations(name);
/* 155:189 */       mergeAnnotationMap(tmp, map);
/* 156:    */     }
/* 157:191 */     DefaultConfigurationHelper.INSTANCE.applyDefaults(map, defaults);
/* 158:    */     
/* 159:193 */     mergeAnnotationMap(map, this.annotations);
/* 160:    */   }
/* 161:    */   
/* 162:    */   void addAnnotationInstance(DotName targetClassName, AnnotationInstance annotationInstance)
/* 163:    */   {
/* 164:198 */     if (annotationInstance == null) {
/* 165:199 */       return;
/* 166:    */     }
/* 167:201 */     for (IndexedAnnotationFilter indexedAnnotationFilter : IndexedAnnotationFilter.ALL_FILTERS) {
/* 168:202 */       indexedAnnotationFilter.beforePush(this, targetClassName, annotationInstance);
/* 169:    */     }
/* 170:204 */     Map<DotName, List<AnnotationInstance>> map = (Map)this.classInfoAnnotationsMap.get(targetClassName);
/* 171:205 */     if (map == null) {
/* 172:206 */       throw new AssertionFailure("Can't find " + targetClassName + " in internal cache, should call createClassInfo first");
/* 173:    */     }
/* 174:209 */     List<AnnotationInstance> annotationInstanceList = (List)map.get(annotationInstance.name());
/* 175:210 */     if (annotationInstanceList == null)
/* 176:    */     {
/* 177:211 */       annotationInstanceList = new ArrayList();
/* 178:212 */       map.put(annotationInstance.name(), annotationInstanceList);
/* 179:    */     }
/* 180:214 */     annotationInstanceList.add(annotationInstance);
/* 181:    */   }
/* 182:    */   
/* 183:    */   ServiceRegistry getServiceRegistry()
/* 184:    */   {
/* 185:218 */     return this.serviceRegistry;
/* 186:    */   }
/* 187:    */   
/* 188:    */   ClassInfo createClassInfo(String className)
/* 189:    */   {
/* 190:222 */     if (StringHelper.isEmpty(className)) {
/* 191:223 */       throw new AssertionFailure("Class Name used to create ClassInfo is empty.");
/* 192:    */     }
/* 193:225 */     DotName classDotName = DotName.createSimple(className);
/* 194:226 */     if (this.classes.containsKey(classDotName)) {
/* 195:228 */       return (ClassInfo)this.classes.get(classDotName);
/* 196:    */     }
/* 197:230 */     Class clazz = ((ClassLoaderService)this.serviceRegistry.getService(ClassLoaderService.class)).classForName(className);
/* 198:231 */     DotName superName = null;
/* 199:232 */     DotName[] interfaces = null;
/* 200:    */     
/* 201:234 */     ClassInfo annClassInfo = this.index.getClassByName(classDotName);
/* 202:    */     short access_flag;
/* 203:    */     short access_flag;
/* 204:235 */     if (annClassInfo != null)
/* 205:    */     {
/* 206:236 */       superName = annClassInfo.superName();
/* 207:237 */       interfaces = annClassInfo.interfaces();
/* 208:238 */       access_flag = annClassInfo.flags();
/* 209:    */     }
/* 210:    */     else
/* 211:    */     {
/* 212:241 */       Class superClass = clazz.getSuperclass();
/* 213:242 */       if (superClass != null) {
/* 214:243 */         superName = DotName.createSimple(superClass.getName());
/* 215:    */       }
/* 216:245 */       Class[] classInterfaces = clazz.getInterfaces();
/* 217:246 */       if ((classInterfaces != null) && (classInterfaces.length > 0))
/* 218:    */       {
/* 219:247 */         interfaces = new DotName[classInterfaces.length];
/* 220:248 */         for (int i = 0; i < classInterfaces.length; i++) {
/* 221:249 */           interfaces[i] = DotName.createSimple(classInterfaces[i].getName());
/* 222:    */         }
/* 223:    */       }
/* 224:252 */       access_flag = (short)(clazz.getModifiers() | 0x20);
/* 225:    */     }
/* 226:254 */     Map<DotName, List<AnnotationInstance>> map = new HashMap();
/* 227:255 */     this.classInfoAnnotationsMap.put(classDotName, map);
/* 228:256 */     ClassInfo classInfo = ClassInfo.create(classDotName, superName, access_flag, interfaces, map);
/* 229:    */     
/* 230:    */ 
/* 231:259 */     this.classes.put(classDotName, classInfo);
/* 232:260 */     addSubClasses(superName, classInfo);
/* 233:261 */     addImplementors(interfaces, classInfo);
/* 234:262 */     return classInfo;
/* 235:    */   }
/* 236:    */   
/* 237:    */   private void addSubClasses(DotName superClassDotName, ClassInfo classInfo)
/* 238:    */   {
/* 239:266 */     if (superClassDotName != null)
/* 240:    */     {
/* 241:267 */       List<ClassInfo> classInfoList = (List)this.subclasses.get(superClassDotName);
/* 242:268 */       if (classInfoList == null)
/* 243:    */       {
/* 244:269 */         classInfoList = new ArrayList();
/* 245:270 */         this.subclasses.put(superClassDotName, classInfoList);
/* 246:    */       }
/* 247:272 */       classInfoList.add(classInfo);
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   private void addImplementors(DotName[] dotNames, ClassInfo classInfo)
/* 252:    */   {
/* 253:277 */     if ((dotNames != null) && (dotNames.length > 0)) {
/* 254:278 */       for (DotName dotName : dotNames)
/* 255:    */       {
/* 256:279 */         List<ClassInfo> classInfoList = (List)this.implementors.get(dotName);
/* 257:280 */         if (classInfoList == null)
/* 258:    */         {
/* 259:281 */           classInfoList = new ArrayList();
/* 260:282 */           this.implementors.put(dotName, classInfoList);
/* 261:    */         }
/* 262:284 */         classInfoList.add(classInfo);
/* 263:    */       }
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private void mergeAnnotationMap(Map<DotName, List<AnnotationInstance>> source, Map<DotName, List<AnnotationInstance>> target)
/* 268:    */   {
/* 269:291 */     if (source != null) {
/* 270:292 */       for (Map.Entry<DotName, List<AnnotationInstance>> el : source.entrySet()) {
/* 271:293 */         if (!((List)el.getValue()).isEmpty())
/* 272:    */         {
/* 273:296 */           DotName annotationName = (DotName)el.getKey();
/* 274:297 */           List<AnnotationInstance> value = (List)el.getValue();
/* 275:298 */           List<AnnotationInstance> annotationInstanceList = (List)target.get(annotationName);
/* 276:299 */           if (annotationInstanceList == null)
/* 277:    */           {
/* 278:300 */             annotationInstanceList = new ArrayList();
/* 279:301 */             target.put(annotationName, annotationInstanceList);
/* 280:    */           }
/* 281:303 */           annotationInstanceList.addAll(value);
/* 282:    */         }
/* 283:    */       }
/* 284:    */     }
/* 285:    */   }
/* 286:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.IndexBuilder
 * JD-Core Version:    0.7.0.1
 */