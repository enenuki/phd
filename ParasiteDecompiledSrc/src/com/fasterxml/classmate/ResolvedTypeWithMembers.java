/*   1:    */ package com.fasterxml.classmate;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.members.HierarchicType;
/*   4:    */ import com.fasterxml.classmate.members.RawConstructor;
/*   5:    */ import com.fasterxml.classmate.members.RawField;
/*   6:    */ import com.fasterxml.classmate.members.RawMethod;
/*   7:    */ import com.fasterxml.classmate.members.ResolvedConstructor;
/*   8:    */ import com.fasterxml.classmate.members.ResolvedField;
/*   9:    */ import com.fasterxml.classmate.members.ResolvedMethod;
/*  10:    */ import com.fasterxml.classmate.util.MethodKey;
/*  11:    */ import java.lang.annotation.Annotation;
/*  12:    */ import java.lang.reflect.Constructor;
/*  13:    */ import java.lang.reflect.Field;
/*  14:    */ import java.lang.reflect.Method;
/*  15:    */ import java.lang.reflect.Type;
/*  16:    */ import java.util.Arrays;
/*  17:    */ import java.util.Collection;
/*  18:    */ import java.util.Collections;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.Iterator;
/*  21:    */ import java.util.LinkedHashMap;
/*  22:    */ import java.util.List;
/*  23:    */ 
/*  24:    */ public class ResolvedTypeWithMembers
/*  25:    */ {
/*  26: 25 */   private static final ResolvedType[] NO_RESOLVED_TYPES = new ResolvedType[0];
/*  27: 27 */   private static final ResolvedMethod[] NO_RESOLVED_METHODS = new ResolvedMethod[0];
/*  28: 28 */   private static final ResolvedField[] NO_RESOLVED_FIELDS = new ResolvedField[0];
/*  29: 29 */   private static final ResolvedConstructor[] NO_RESOLVED_CONSTRUCTORS = new ResolvedConstructor[0];
/*  30: 34 */   protected static final AnnotationConfiguration DEFAULT_ANNOTATION_CONFIG = new AnnotationConfiguration.StdConfiguration(AnnotationInclusion.DONT_INCLUDE);
/*  31:    */   protected final TypeResolver _typeResolver;
/*  32:    */   protected final AnnotationHandler _annotationHandler;
/*  33:    */   protected final HierarchicType _mainType;
/*  34:    */   protected final HierarchicType[] _types;
/*  35:    */   protected Filter<RawField> _fieldFilter;
/*  36:    */   protected Filter<RawConstructor> _constructorFilter;
/*  37:    */   protected Filter<RawMethod> _methodFilter;
/*  38: 80 */   protected ResolvedMethod[] _staticMethods = null;
/*  39: 82 */   protected ResolvedMethod[] _memberMethods = null;
/*  40: 84 */   protected ResolvedField[] _memberFields = null;
/*  41: 86 */   protected ResolvedConstructor[] _constructors = null;
/*  42:    */   
/*  43:    */   public ResolvedTypeWithMembers(TypeResolver typeResolver, AnnotationConfiguration annotationConfig, HierarchicType mainType, HierarchicType[] types, Filter<RawConstructor> constructorFilter, Filter<RawField> fieldFilter, Filter<RawMethod> methodFilter)
/*  44:    */   {
/*  45: 98 */     this._typeResolver = typeResolver;
/*  46: 99 */     this._mainType = mainType;
/*  47:100 */     this._types = types;
/*  48:101 */     if (annotationConfig == null) {
/*  49:102 */       annotationConfig = DEFAULT_ANNOTATION_CONFIG;
/*  50:    */     }
/*  51:104 */     this._annotationHandler = new AnnotationHandler(annotationConfig);
/*  52:105 */     this._constructorFilter = constructorFilter;
/*  53:106 */     this._fieldFilter = fieldFilter;
/*  54:107 */     this._methodFilter = methodFilter;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int size()
/*  58:    */   {
/*  59:116 */     return this._types.length;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public List<HierarchicType> allTypesAndOverrides()
/*  63:    */   {
/*  64:123 */     return Arrays.asList(this._types);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public List<HierarchicType> mainTypeAndOverrides()
/*  68:    */   {
/*  69:132 */     List<HierarchicType> l = Arrays.asList(this._types);
/*  70:133 */     int end = this._mainType.getPriority() + 1;
/*  71:134 */     if (end < l.size()) {
/*  72:135 */       l = l.subList(0, end);
/*  73:    */     }
/*  74:137 */     return l;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<HierarchicType> overridesOnly()
/*  78:    */   {
/*  79:145 */     int index = this._mainType.getPriority();
/*  80:146 */     if (index == 0) {
/*  81:147 */       return Collections.emptyList();
/*  82:    */     }
/*  83:149 */     List<HierarchicType> l = Arrays.asList(this._types);
/*  84:150 */     return l.subList(0, index);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ResolvedMethod[] getStaticMethods()
/*  88:    */   {
/*  89:166 */     if (this._staticMethods == null) {
/*  90:167 */       this._staticMethods = resolveStaticMethods();
/*  91:    */     }
/*  92:169 */     return this._staticMethods;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public ResolvedField[] getMemberFields()
/*  96:    */   {
/*  97:174 */     if (this._memberFields == null) {
/*  98:175 */       this._memberFields = resolveMemberFields();
/*  99:    */     }
/* 100:177 */     return this._memberFields;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public ResolvedMethod[] getMemberMethods()
/* 104:    */   {
/* 105:182 */     if (this._memberMethods == null) {
/* 106:183 */       this._memberMethods = resolveMemberMethods();
/* 107:    */     }
/* 108:185 */     return this._memberMethods;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ResolvedConstructor[] getConstructors()
/* 112:    */   {
/* 113:190 */     if (this._constructors == null) {
/* 114:191 */       this._constructors = resolveConstructors();
/* 115:    */     }
/* 116:193 */     return this._constructors;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected ResolvedConstructor[] resolveConstructors()
/* 120:    */   {
/* 121:209 */     LinkedHashMap<MethodKey, ResolvedConstructor> constructors = new LinkedHashMap();
/* 122:210 */     for (RawConstructor constructor : this._mainType.getType().getConstructors()) {
/* 123:212 */       if ((this._constructorFilter == null) || (this._constructorFilter.include(constructor))) {
/* 124:213 */         constructors.put(constructor.createKey(), resolveConstructor(constructor));
/* 125:    */       }
/* 126:    */     }
/* 127:217 */     for (HierarchicType type : overridesOnly()) {
/* 128:218 */       for (RawConstructor raw : type.getType().getConstructors())
/* 129:    */       {
/* 130:219 */         ResolvedConstructor constructor = (ResolvedConstructor)constructors.get(raw.createKey());
/* 131:221 */         if (constructor != null) {
/* 132:222 */           for (Annotation ann : raw.getAnnotations()) {
/* 133:223 */             if (this._annotationHandler.includeMethodAnnotation(ann)) {
/* 134:224 */               constructor.applyOverride(ann);
/* 135:    */             }
/* 136:    */           }
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:230 */     if (constructors.size() == 0) {
/* 141:231 */       return NO_RESOLVED_CONSTRUCTORS;
/* 142:    */     }
/* 143:233 */     return (ResolvedConstructor[])constructors.values().toArray(new ResolvedConstructor[constructors.size()]);
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected ResolvedField[] resolveMemberFields()
/* 147:    */   {
/* 148:244 */     LinkedHashMap<String, ResolvedField> fields = new LinkedHashMap();
/* 149:    */     
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:250 */     int typeIndex = this._types.length;
/* 155:    */     for (;;)
/* 156:    */     {
/* 157:250 */       typeIndex--;
/* 158:250 */       if (typeIndex < 0) {
/* 159:    */         break;
/* 160:    */       }
/* 161:251 */       HierarchicType thisType = this._types[typeIndex];
/* 162:253 */       if (thisType.isMixin()) {
/* 163:254 */         for (RawField raw : thisType.getType().getMemberFields())
/* 164:    */         {
/* 165:255 */           ResolvedField field = (ResolvedField)fields.get(raw.getName());
/* 166:256 */           if (field != null) {
/* 167:257 */             for (Annotation ann : raw.getAnnotations()) {
/* 168:258 */               if (this._annotationHandler.includeMethodAnnotation(ann)) {
/* 169:259 */                 field.applyOverride(ann);
/* 170:    */               }
/* 171:    */             }
/* 172:    */           }
/* 173:    */         }
/* 174:    */       } else {
/* 175:265 */         for (RawField field : thisType.getType().getMemberFields()) {
/* 176:266 */           fields.put(field.getName(), resolveField(field));
/* 177:    */         }
/* 178:    */       }
/* 179:    */     }
/* 180:271 */     if (fields.size() == 0) {
/* 181:272 */       return NO_RESOLVED_FIELDS;
/* 182:    */     }
/* 183:274 */     return (ResolvedField[])fields.values().toArray(new ResolvedField[fields.size()]);
/* 184:    */   }
/* 185:    */   
/* 186:    */   protected ResolvedMethod[] resolveStaticMethods()
/* 187:    */   {
/* 188:284 */     LinkedHashMap<MethodKey, ResolvedMethod> methods = new LinkedHashMap();
/* 189:285 */     for (RawMethod method : this._mainType.getType().getStaticMethods()) {
/* 190:286 */       if ((this._methodFilter == null) || (this._methodFilter.include(method))) {
/* 191:287 */         methods.put(method.createKey(), resolveMethod(method));
/* 192:    */       }
/* 193:    */     }
/* 194:291 */     for (HierarchicType type : overridesOnly()) {
/* 195:292 */       for (RawMethod raw : type.getType().getStaticMethods())
/* 196:    */       {
/* 197:293 */         ResolvedMethod method = (ResolvedMethod)methods.get(raw.createKey());
/* 198:295 */         if (method != null) {
/* 199:296 */           for (Annotation ann : raw.getAnnotations()) {
/* 200:297 */             if (this._annotationHandler.includeMethodAnnotation(ann)) {
/* 201:298 */               method.applyOverride(ann);
/* 202:    */             }
/* 203:    */           }
/* 204:    */         }
/* 205:    */       }
/* 206:    */     }
/* 207:304 */     if (methods.size() == 0) {
/* 208:305 */       return NO_RESOLVED_METHODS;
/* 209:    */     }
/* 210:307 */     return (ResolvedMethod[])methods.values().toArray(new ResolvedMethod[methods.size()]);
/* 211:    */   }
/* 212:    */   
/* 213:    */   protected ResolvedMethod[] resolveMemberMethods()
/* 214:    */   {
/* 215:312 */     LinkedHashMap<MethodKey, ResolvedMethod> methods = new LinkedHashMap();
/* 216:313 */     LinkedHashMap<MethodKey, Annotations> overrides = new LinkedHashMap();
/* 217:319 */     for (Iterator i$ = allTypesAndOverrides().iterator(); i$.hasNext();)
/* 218:    */     {
/* 219:319 */       type = (HierarchicType)i$.next();
/* 220:320 */       for (RawMethod method : type.getType().getMemberMethods()) {
/* 221:322 */         if ((this._methodFilter == null) || (this._methodFilter.include(method)))
/* 222:    */         {
/* 223:326 */           MethodKey key = method.createKey();
/* 224:327 */           ResolvedMethod old = (ResolvedMethod)methods.get(key);
/* 225:330 */           if (type.isMixin())
/* 226:    */           {
/* 227:331 */             for (Annotation ann : method.getAnnotations()) {
/* 228:333 */               if (old != null)
/* 229:    */               {
/* 230:334 */                 if (this._annotationHandler.methodInclusion(ann) == AnnotationInclusion.INCLUDE_AND_INHERIT) {
/* 231:338 */                   old.applyDefault(ann);
/* 232:    */                 }
/* 233:    */               }
/* 234:    */               else
/* 235:    */               {
/* 236:340 */                 Annotations oldAnn = (Annotations)overrides.get(key);
/* 237:341 */                 if (oldAnn == null)
/* 238:    */                 {
/* 239:342 */                   oldAnn = new Annotations();
/* 240:343 */                   oldAnn.add(ann);
/* 241:344 */                   overrides.put(key, oldAnn);
/* 242:    */                 }
/* 243:    */                 else
/* 244:    */                 {
/* 245:346 */                   oldAnn.addAsDefault(ann);
/* 246:    */                 }
/* 247:    */               }
/* 248:    */             }
/* 249:    */           }
/* 250:351 */           else if (old == null)
/* 251:    */           {
/* 252:352 */             ResolvedMethod newMethod = resolveMethod(method);
/* 253:353 */             methods.put(key, newMethod);
/* 254:    */             
/* 255:355 */             Annotations overrideAnn = (Annotations)overrides.get(key);
/* 256:356 */             if (overrideAnn != null) {
/* 257:357 */               newMethod.applyOverrides(overrideAnn);
/* 258:    */             }
/* 259:    */           }
/* 260:    */           else
/* 261:    */           {
/* 262:360 */             for (Annotation ann : method.getAnnotations()) {
/* 263:361 */               if (this._annotationHandler.methodInclusion(ann) == AnnotationInclusion.INCLUDE_AND_INHERIT) {
/* 264:362 */                 old.applyDefault(ann);
/* 265:    */               }
/* 266:    */             }
/* 267:    */           }
/* 268:    */         }
/* 269:    */       }
/* 270:    */     }
/* 271:    */     HierarchicType type;
/* 272:370 */     if (methods.size() == 0) {
/* 273:371 */       return NO_RESOLVED_METHODS;
/* 274:    */     }
/* 275:373 */     return (ResolvedMethod[])methods.values().toArray(new ResolvedMethod[methods.size()]);
/* 276:    */   }
/* 277:    */   
/* 278:    */   protected ResolvedConstructor resolveConstructor(RawConstructor raw)
/* 279:    */   {
/* 280:387 */     ResolvedType context = raw.getDeclaringType();
/* 281:388 */     TypeBindings bindings = context.getTypeBindings();
/* 282:389 */     Constructor<?> ctor = raw.getRawMember();
/* 283:390 */     Type[] rawTypes = ctor.getGenericParameterTypes();
/* 284:    */     ResolvedType[] argTypes;
/* 285:    */     ResolvedType[] argTypes;
/* 286:392 */     if ((rawTypes == null) || (rawTypes.length == 0))
/* 287:    */     {
/* 288:393 */       argTypes = NO_RESOLVED_TYPES;
/* 289:    */     }
/* 290:    */     else
/* 291:    */     {
/* 292:395 */       argTypes = new ResolvedType[rawTypes.length];
/* 293:396 */       int i = 0;
/* 294:396 */       for (int len = rawTypes.length; i < len; i++) {
/* 295:397 */         argTypes[i] = this._typeResolver.resolve(rawTypes[i], bindings);
/* 296:    */       }
/* 297:    */     }
/* 298:401 */     Annotations anns = new Annotations();
/* 299:402 */     for (Annotation ann : ctor.getAnnotations()) {
/* 300:403 */       if (this._annotationHandler.includeConstructorAnnotation(ann)) {
/* 301:404 */         anns.add(ann);
/* 302:    */       }
/* 303:    */     }
/* 304:407 */     return new ResolvedConstructor(context, anns, ctor, argTypes);
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected ResolvedField resolveField(RawField raw)
/* 308:    */   {
/* 309:415 */     ResolvedType context = raw.getDeclaringType();
/* 310:416 */     Field field = raw.getRawMember();
/* 311:417 */     ResolvedType type = this._typeResolver.resolve(field.getGenericType(), context.getTypeBindings());
/* 312:    */     
/* 313:419 */     Annotations anns = new Annotations();
/* 314:420 */     for (Annotation ann : field.getAnnotations()) {
/* 315:421 */       if (this._annotationHandler.includeFieldAnnotation(ann)) {
/* 316:422 */         anns.add(ann);
/* 317:    */       }
/* 318:    */     }
/* 319:425 */     return new ResolvedField(context, anns, field, type);
/* 320:    */   }
/* 321:    */   
/* 322:    */   protected ResolvedMethod resolveMethod(RawMethod raw)
/* 323:    */   {
/* 324:433 */     ResolvedType context = raw.getDeclaringType();
/* 325:434 */     TypeBindings bindings = context.getTypeBindings();
/* 326:435 */     Method m = raw.getRawMember();
/* 327:436 */     Type rawType = m.getGenericReturnType();
/* 328:437 */     ResolvedType rt = rawType == Void.TYPE ? null : this._typeResolver.resolve(rawType, bindings);
/* 329:438 */     Type[] rawTypes = m.getGenericParameterTypes();
/* 330:    */     ResolvedType[] argTypes;
/* 331:    */     ResolvedType[] argTypes;
/* 332:440 */     if ((rawTypes == null) || (rawTypes.length == 0))
/* 333:    */     {
/* 334:441 */       argTypes = NO_RESOLVED_TYPES;
/* 335:    */     }
/* 336:    */     else
/* 337:    */     {
/* 338:443 */       argTypes = new ResolvedType[rawTypes.length];
/* 339:444 */       int i = 0;
/* 340:444 */       for (int len = rawTypes.length; i < len; i++) {
/* 341:445 */         argTypes[i] = this._typeResolver.resolve(rawTypes[i], bindings);
/* 342:    */       }
/* 343:    */     }
/* 344:449 */     Annotations anns = new Annotations();
/* 345:450 */     for (Annotation ann : m.getAnnotations()) {
/* 346:451 */       if (this._annotationHandler.includeMethodAnnotation(ann)) {
/* 347:452 */         anns.add(ann);
/* 348:    */       }
/* 349:    */     }
/* 350:455 */     return new ResolvedMethod(context, anns, m, rt, argTypes);
/* 351:    */   }
/* 352:    */   
/* 353:    */   private static final class AnnotationHandler
/* 354:    */   {
/* 355:    */     private final AnnotationConfiguration _annotationConfig;
/* 356:    */     private HashMap<Class<? extends Annotation>, AnnotationInclusion> _fieldInclusions;
/* 357:    */     private HashMap<Class<? extends Annotation>, AnnotationInclusion> _constructorInclusions;
/* 358:    */     private HashMap<Class<? extends Annotation>, AnnotationInclusion> _methodInclusions;
/* 359:    */     
/* 360:    */     public AnnotationHandler(AnnotationConfiguration annotationConfig)
/* 361:    */     {
/* 362:477 */       this._annotationConfig = annotationConfig;
/* 363:    */     }
/* 364:    */     
/* 365:    */     public boolean includeConstructorAnnotation(Annotation ann)
/* 366:    */     {
/* 367:482 */       Class<? extends Annotation> annType = ann.annotationType();
/* 368:483 */       if (this._constructorInclusions == null)
/* 369:    */       {
/* 370:484 */         this._constructorInclusions = new HashMap();
/* 371:    */       }
/* 372:    */       else
/* 373:    */       {
/* 374:486 */         AnnotationInclusion incl = (AnnotationInclusion)this._constructorInclusions.get(annType);
/* 375:487 */         if (incl != null) {
/* 376:488 */           return incl != AnnotationInclusion.DONT_INCLUDE;
/* 377:    */         }
/* 378:    */       }
/* 379:491 */       AnnotationInclusion incl = this._annotationConfig.getInclusionForConstructor(annType);
/* 380:492 */       this._constructorInclusions.put(annType, incl);
/* 381:493 */       return incl != AnnotationInclusion.DONT_INCLUDE;
/* 382:    */     }
/* 383:    */     
/* 384:    */     public boolean includeFieldAnnotation(Annotation ann)
/* 385:    */     {
/* 386:498 */       Class<? extends Annotation> annType = ann.annotationType();
/* 387:499 */       if (this._fieldInclusions == null)
/* 388:    */       {
/* 389:500 */         this._fieldInclusions = new HashMap();
/* 390:    */       }
/* 391:    */       else
/* 392:    */       {
/* 393:502 */         AnnotationInclusion incl = (AnnotationInclusion)this._fieldInclusions.get(annType);
/* 394:503 */         if (incl != null) {
/* 395:504 */           return incl != AnnotationInclusion.DONT_INCLUDE;
/* 396:    */         }
/* 397:    */       }
/* 398:507 */       AnnotationInclusion incl = this._annotationConfig.getInclusionForField(annType);
/* 399:508 */       this._fieldInclusions.put(annType, incl);
/* 400:509 */       return incl != AnnotationInclusion.DONT_INCLUDE;
/* 401:    */     }
/* 402:    */     
/* 403:    */     public boolean includeMethodAnnotation(Annotation ann)
/* 404:    */     {
/* 405:514 */       return methodInclusion(ann) != AnnotationInclusion.DONT_INCLUDE;
/* 406:    */     }
/* 407:    */     
/* 408:    */     public AnnotationInclusion methodInclusion(Annotation ann)
/* 409:    */     {
/* 410:519 */       Class<? extends Annotation> annType = ann.annotationType();
/* 411:520 */       if (this._methodInclusions == null)
/* 412:    */       {
/* 413:521 */         this._methodInclusions = new HashMap();
/* 414:    */       }
/* 415:    */       else
/* 416:    */       {
/* 417:523 */         AnnotationInclusion incl = (AnnotationInclusion)this._methodInclusions.get(annType);
/* 418:524 */         if (incl != null) {
/* 419:525 */           return incl;
/* 420:    */         }
/* 421:    */       }
/* 422:528 */       AnnotationInclusion incl = this._annotationConfig.getInclusionForField(annType);
/* 423:529 */       this._methodInclusions.put(annType, incl);
/* 424:530 */       return incl;
/* 425:    */     }
/* 426:    */   }
/* 427:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.ResolvedTypeWithMembers
 * JD-Core Version:    0.7.0.1
 */