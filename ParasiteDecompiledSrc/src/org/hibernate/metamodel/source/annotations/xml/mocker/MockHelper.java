/*   1:    */ package org.hibernate.metamodel.source.annotations.xml.mocker;
/*   2:    */ 
/*   3:    */ import java.beans.Introspector;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.CascadeType;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.internal.jaxb.mapping.orm.JaxbCascadeType;
/*  12:    */ import org.hibernate.internal.util.StringHelper;
/*  13:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  14:    */ import org.hibernate.service.ServiceRegistry;
/*  15:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  16:    */ import org.jboss.jandex.AnnotationInstance;
/*  17:    */ import org.jboss.jandex.AnnotationTarget;
/*  18:    */ import org.jboss.jandex.AnnotationValue;
/*  19:    */ import org.jboss.jandex.ClassInfo;
/*  20:    */ import org.jboss.jandex.DotName;
/*  21:    */ import org.jboss.jandex.FieldInfo;
/*  22:    */ import org.jboss.jandex.MethodInfo;
/*  23:    */ import org.jboss.jandex.Type;
/*  24:    */ import org.jboss.jandex.Type.Kind;
/*  25:    */ 
/*  26:    */ public class MockHelper
/*  27:    */ {
/*  28: 54 */   static final AnnotationValue[] EMPTY_ANNOTATION_VALUE_ARRAY = new AnnotationValue[0];
/*  29: 55 */   static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
/*  30:    */   
/*  31:    */   static void stringArrayValue(String name, List<String> values, List<AnnotationValue> annotationValueList)
/*  32:    */   {
/*  33: 65 */     if (isNotEmpty(values))
/*  34:    */     {
/*  35: 66 */       AnnotationValue[] annotationValues = new AnnotationValue[values.size()];
/*  36: 67 */       for (int j = 0; j < values.size(); j++) {
/*  37: 68 */         annotationValues[j] = stringValue("", (String)values.get(j));
/*  38:    */       }
/*  39: 70 */       annotationValueList.add(AnnotationValue.createArrayValue(name, annotationValues));
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   static AnnotationValue[] stringValueArray(String name, String value)
/*  44:    */   {
/*  45: 82 */     return nullSafe(stringValue(name, value));
/*  46:    */   }
/*  47:    */   
/*  48:    */   private static AnnotationValue stringValue(String name, String value)
/*  49:    */   {
/*  50: 86 */     if (StringHelper.isNotEmpty(value)) {
/*  51: 87 */       return AnnotationValue.createStringValue(name, value);
/*  52:    */     }
/*  53: 89 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   static void stringValue(String name, String value, List<AnnotationValue> annotationValueList)
/*  57:    */   {
/*  58: 93 */     addToCollectionIfNotNull(annotationValueList, stringValue(name, value));
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static AnnotationValue integerValue(String name, Integer value)
/*  62:    */   {
/*  63: 97 */     if (value == null) {
/*  64: 98 */       return null;
/*  65:    */     }
/*  66:100 */     return AnnotationValue.createIntegerValue(name, value.intValue());
/*  67:    */   }
/*  68:    */   
/*  69:    */   static void integerValue(String name, Integer value, List<AnnotationValue> annotationValueList)
/*  70:    */   {
/*  71:104 */     addToCollectionIfNotNull(annotationValueList, integerValue(name, value));
/*  72:    */   }
/*  73:    */   
/*  74:    */   static AnnotationValue[] booleanValueArray(String name, Boolean value)
/*  75:    */   {
/*  76:108 */     return nullSafe(booleanValue(name, value));
/*  77:    */   }
/*  78:    */   
/*  79:    */   static void booleanValue(String name, Boolean value, List<AnnotationValue> annotationValueList)
/*  80:    */   {
/*  81:112 */     addToCollectionIfNotNull(annotationValueList, booleanValue(name, value));
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static AnnotationValue booleanValue(String name, Boolean value)
/*  85:    */   {
/*  86:116 */     if (value == null) {
/*  87:117 */       return null;
/*  88:    */     }
/*  89:119 */     return AnnotationValue.createBooleanValue(name, value.booleanValue());
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static AnnotationValue classValue(String name, String className, ServiceRegistry serviceRegistry)
/*  93:    */   {
/*  94:123 */     if (StringHelper.isNotEmpty(className)) {
/*  95:124 */       return AnnotationValue.createClassValue(name, getType(className, serviceRegistry));
/*  96:    */     }
/*  97:126 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   static void classValue(String name, String className, List<AnnotationValue> list, ServiceRegistry serviceRegistry)
/* 101:    */   {
/* 102:131 */     addToCollectionIfNotNull(list, classValue(name, className, serviceRegistry));
/* 103:    */   }
/* 104:    */   
/* 105:    */   static AnnotationValue[] classValueArray(String name, String className, ServiceRegistry serviceRegistry)
/* 106:    */   {
/* 107:135 */     return nullSafe(classValue(name, className, serviceRegistry));
/* 108:    */   }
/* 109:    */   
/* 110:    */   static AnnotationValue nestedAnnotationValue(String name, AnnotationInstance value)
/* 111:    */   {
/* 112:139 */     if (value == null) {
/* 113:140 */       return null;
/* 114:    */     }
/* 115:142 */     return AnnotationValue.createNestedAnnotationValue(name, value);
/* 116:    */   }
/* 117:    */   
/* 118:    */   static void nestedAnnotationValue(String name, AnnotationInstance value, List<AnnotationValue> list)
/* 119:    */   {
/* 120:148 */     addToCollectionIfNotNull(list, nestedAnnotationValue(name, value));
/* 121:    */   }
/* 122:    */   
/* 123:    */   private static AnnotationValue[] nullSafe(AnnotationValue value)
/* 124:    */   {
/* 125:152 */     return new AnnotationValue[] { value == null ? EMPTY_ANNOTATION_VALUE_ARRAY : value };
/* 126:    */   }
/* 127:    */   
/* 128:    */   static void classArrayValue(String name, List<String> classNameList, List<AnnotationValue> list, ServiceRegistry serviceRegistry)
/* 129:    */   {
/* 130:158 */     if (isNotEmpty(classNameList))
/* 131:    */     {
/* 132:160 */       List<AnnotationValue> clazzValueList = new ArrayList(classNameList.size());
/* 133:161 */       for (String clazz : classNameList) {
/* 134:162 */         addToCollectionIfNotNull(clazzValueList, classValue("", clazz, serviceRegistry));
/* 135:    */       }
/* 136:165 */       list.add(AnnotationValue.createArrayValue(name, toArray(clazzValueList)));
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static AnnotationValue[] toArray(List<AnnotationValue> list)
/* 141:    */   {
/* 142:174 */     AnnotationValue[] values = EMPTY_ANNOTATION_VALUE_ARRAY;
/* 143:175 */     if (isNotEmpty(list)) {
/* 144:176 */       values = (AnnotationValue[])list.toArray(new AnnotationValue[list.size()]);
/* 145:    */     }
/* 146:178 */     return values;
/* 147:    */   }
/* 148:    */   
/* 149:    */   private static AnnotationValue enumValue(String name, DotName typeName, Enum value)
/* 150:    */   {
/* 151:182 */     if ((value != null) && (StringHelper.isNotEmpty(value.toString()))) {
/* 152:183 */       return AnnotationValue.createEnumValue(name, typeName, value.toString());
/* 153:    */     }
/* 154:185 */     return null;
/* 155:    */   }
/* 156:    */   
/* 157:    */   static void cascadeValue(String name, JaxbCascadeType cascadeType, boolean isCascadePersistDefault, List<AnnotationValue> annotationValueList)
/* 158:    */   {
/* 159:189 */     List<Enum> enumList = new ArrayList();
/* 160:190 */     if (isCascadePersistDefault) {
/* 161:191 */       enumList.add(CascadeType.PERSIST);
/* 162:    */     }
/* 163:193 */     if (cascadeType != null)
/* 164:    */     {
/* 165:194 */       if (cascadeType.getCascadeAll() != null) {
/* 166:195 */         enumList.add(CascadeType.ALL);
/* 167:    */       }
/* 168:197 */       if ((cascadeType.getCascadePersist() != null) && (!isCascadePersistDefault)) {
/* 169:198 */         enumList.add(CascadeType.PERSIST);
/* 170:    */       }
/* 171:200 */       if (cascadeType.getCascadeMerge() != null) {
/* 172:201 */         enumList.add(CascadeType.MERGE);
/* 173:    */       }
/* 174:203 */       if (cascadeType.getCascadeRemove() != null) {
/* 175:204 */         enumList.add(CascadeType.REMOVE);
/* 176:    */       }
/* 177:206 */       if (cascadeType.getCascadeRefresh() != null) {
/* 178:207 */         enumList.add(CascadeType.REFRESH);
/* 179:    */       }
/* 180:209 */       if (cascadeType.getCascadeDetach() != null) {
/* 181:210 */         enumList.add(CascadeType.DETACH);
/* 182:    */       }
/* 183:    */     }
/* 184:213 */     if (!enumList.isEmpty()) {
/* 185:214 */       enumArrayValue(name, JPADotNames.CASCADE_TYPE, enumList, annotationValueList);
/* 186:    */     }
/* 187:    */   }
/* 188:    */   
/* 189:    */   static void enumArrayValue(String name, DotName typeName, List<Enum> valueList, List<AnnotationValue> list)
/* 190:    */   {
/* 191:219 */     if (isNotEmpty(valueList))
/* 192:    */     {
/* 193:221 */       List<AnnotationValue> enumValueList = new ArrayList(valueList.size());
/* 194:222 */       for (Enum e : valueList) {
/* 195:223 */         addToCollectionIfNotNull(enumValueList, enumValue("", typeName, e));
/* 196:    */       }
/* 197:225 */       list.add(AnnotationValue.createArrayValue(name, toArray(enumValueList)));
/* 198:    */     }
/* 199:    */   }
/* 200:    */   
/* 201:    */   static void enumValue(String name, DotName typeName, Enum value, List<AnnotationValue> list)
/* 202:    */   {
/* 203:234 */     addToCollectionIfNotNull(list, enumValue(name, typeName, value));
/* 204:    */   }
/* 205:    */   
/* 206:    */   static AnnotationValue[] enumValueArray(String name, DotName typeName, Enum value)
/* 207:    */   {
/* 208:238 */     return nullSafe(enumValue(name, typeName, value));
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static void addToCollectionIfNotNull(Collection collection, Object value)
/* 212:    */   {
/* 213:242 */     if ((value != null) && (collection != null)) {
/* 214:243 */       collection.add(value);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static boolean targetEquals(AnnotationTarget t1, AnnotationTarget t2)
/* 219:    */   {
/* 220:253 */     if (t1 == t2) {
/* 221:254 */       return true;
/* 222:    */     }
/* 223:256 */     if ((t1 != null) && (t2 != null)) {
/* 224:258 */       if (t1.getClass() == t2.getClass())
/* 225:    */       {
/* 226:259 */         if (t1.getClass() == ClassInfo.class) {
/* 227:260 */           return ((ClassInfo)t1).name().equals(((ClassInfo)t2).name());
/* 228:    */         }
/* 229:262 */         if (t1.getClass() == MethodInfo.class) {
/* 230:263 */           return ((MethodInfo)t1).name().equals(((MethodInfo)t2).name());
/* 231:    */         }
/* 232:266 */         return ((FieldInfo)t1).name().equals(((FieldInfo)t2).name());
/* 233:    */       }
/* 234:    */     }
/* 235:270 */     return false;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public static boolean isNotEmpty(Collection collection)
/* 239:    */   {
/* 240:274 */     return (collection != null) && (!collection.isEmpty());
/* 241:    */   }
/* 242:    */   
/* 243:    */   static AnnotationInstance create(DotName name, AnnotationTarget target, List<AnnotationValue> annotationValueList)
/* 244:    */   {
/* 245:279 */     return create(name, target, toArray(annotationValueList));
/* 246:    */   }
/* 247:    */   
/* 248:    */   static String buildSafeClassName(String className, String defaultPackageName)
/* 249:    */   {
/* 250:286 */     if ((className.indexOf('.') < 0) && (StringHelper.isNotEmpty(defaultPackageName))) {
/* 251:287 */       className = StringHelper.qualify(defaultPackageName, className);
/* 252:    */     }
/* 253:289 */     return className;
/* 254:    */   }
/* 255:    */   
/* 256:    */   static AnnotationInstance create(DotName name, AnnotationTarget target, AnnotationValue[] values)
/* 257:    */   {
/* 258:293 */     if ((values == null) || (values.length == 0)) {
/* 259:294 */       values = EMPTY_ANNOTATION_VALUE_ARRAY;
/* 260:    */     }
/* 261:296 */     return AnnotationInstance.create(name, target, addMockMark(values));
/* 262:    */   }
/* 263:    */   
/* 264:    */   private static AnnotationValue[] addMockMark(AnnotationValue[] values)
/* 265:    */   {
/* 266:300 */     AnnotationValue[] newValues = new AnnotationValue[values.length + 1];
/* 267:301 */     System.arraycopy(values, 0, newValues, 0, values.length);
/* 268:302 */     newValues[values.length] = booleanValue("isMocked", Boolean.valueOf(true));
/* 269:303 */     return newValues;
/* 270:    */   }
/* 271:    */   
/* 272:    */   private static MethodInfo getMethodInfo(ClassInfo classInfo, Method method)
/* 273:    */   {
/* 274:308 */     Class returnTypeClass = method.getReturnType();
/* 275:309 */     short access_flags = (short)method.getModifiers();
/* 276:310 */     return MethodInfo.create(classInfo, method.getName(), getTypes(method.getParameterTypes()), getType(returnTypeClass), access_flags);
/* 277:    */   }
/* 278:    */   
/* 279:    */   static enum TargetType
/* 280:    */   {
/* 281:319 */     METHOD,  FIELD,  PROPERTY;
/* 282:    */     
/* 283:    */     private TargetType() {}
/* 284:    */   }
/* 285:    */   
/* 286:    */   static AnnotationTarget getTarget(ServiceRegistry serviceRegistry, ClassInfo classInfo, String name, TargetType type)
/* 287:    */   {
/* 288:322 */     Class clazz = ((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName(classInfo.toString());
/* 289:    */     Method method;
/* 290:323 */     switch (1.$SwitchMap$org$hibernate$metamodel$source$annotations$xml$mocker$MockHelper$TargetType[type.ordinal()])
/* 291:    */     {
/* 292:    */     case 1: 
/* 293:325 */       Field field = getField(clazz, name);
/* 294:326 */       if (field == null) {
/* 295:327 */         throw new HibernateException("Unable to load field " + name + " of class " + clazz.getName());
/* 296:    */       }
/* 297:334 */       return FieldInfo.create(classInfo, name, getType(field.getType()), (short)field.getModifiers());
/* 298:    */     case 2: 
/* 299:338 */       method = getMethod(clazz, name);
/* 300:339 */       if (method == null) {
/* 301:340 */         throw new HibernateException("Unable to load method " + name + " of class " + clazz.getName());
/* 302:    */       }
/* 303:346 */       return getMethodInfo(classInfo, method);
/* 304:    */     case 3: 
/* 305:348 */       method = getterMethod(clazz, name);
/* 306:349 */       if (method == null) {
/* 307:350 */         throw new HibernateException("Unable to load property " + name + " of class " + clazz.getName());
/* 308:    */       }
/* 309:356 */       return getMethodInfo(classInfo, method);
/* 310:    */     }
/* 311:359 */     throw new HibernateException("");
/* 312:    */   }
/* 313:    */   
/* 314:    */   private static Method getterMethod(Class theClass, String propertyName)
/* 315:    */   {
/* 316:364 */     Method[] methods = theClass.getDeclaredMethods();
/* 317:365 */     Method.setAccessible(methods, true);
/* 318:366 */     for (Method method : methods) {
/* 319:368 */       if (method.getParameterTypes().length == 0) {
/* 320:372 */         if (!method.isBridge())
/* 321:    */         {
/* 322:376 */           String methodName = method.getName();
/* 323:379 */           if ((methodName.startsWith("get")) || (methodName.startsWith("has")))
/* 324:    */           {
/* 325:380 */             String testStdMethod = Introspector.decapitalize(methodName.substring(3));
/* 326:381 */             String testOldMethod = methodName.substring(3);
/* 327:382 */             if ((testStdMethod.equals(propertyName)) || (testOldMethod.equals(propertyName))) {
/* 328:383 */               return method;
/* 329:    */             }
/* 330:    */           }
/* 331:388 */           if (methodName.startsWith("is"))
/* 332:    */           {
/* 333:389 */             String testStdMethod = Introspector.decapitalize(methodName.substring(2));
/* 334:390 */             String testOldMethod = methodName.substring(2);
/* 335:391 */             if ((testStdMethod.equals(propertyName)) || (testOldMethod.equals(propertyName))) {
/* 336:392 */               return method;
/* 337:    */             }
/* 338:    */           }
/* 339:    */         }
/* 340:    */       }
/* 341:    */     }
/* 342:397 */     return null;
/* 343:    */   }
/* 344:    */   
/* 345:    */   private static Method getMethod(Class theClass, String propertyName)
/* 346:    */   {
/* 347:402 */     Method[] methods = theClass.getDeclaredMethods();
/* 348:403 */     Method.setAccessible(methods, true);
/* 349:404 */     for (Method method : methods) {
/* 350:406 */       if (method.getParameterTypes().length == 0) {
/* 351:410 */         if (!method.isBridge())
/* 352:    */         {
/* 353:414 */           String methodName = method.getName();
/* 354:415 */           if (methodName.equals(propertyName)) {
/* 355:416 */             return method;
/* 356:    */           }
/* 357:    */         }
/* 358:    */       }
/* 359:    */     }
/* 360:420 */     return null;
/* 361:    */   }
/* 362:    */   
/* 363:    */   private static Field getField(Class clazz, String name)
/* 364:    */   {
/* 365:424 */     Field[] fields = clazz.getDeclaredFields();
/* 366:425 */     Field.setAccessible(fields, true);
/* 367:426 */     for (Field field : fields) {
/* 368:427 */       if (field.getName().equals(name)) {
/* 369:428 */         return field;
/* 370:    */       }
/* 371:    */     }
/* 372:431 */     return null;
/* 373:    */   }
/* 374:    */   
/* 375:    */   private static Type[] getTypes(Class[] classes)
/* 376:    */   {
/* 377:435 */     if ((classes == null) || (classes.length == 0)) {
/* 378:436 */       return EMPTY_TYPE_ARRAY;
/* 379:    */     }
/* 380:438 */     Type[] types = new Type[classes.length];
/* 381:439 */     for (int i = 0; i < types.length; i++) {
/* 382:440 */       types[i] = getType(classes[i]);
/* 383:    */     }
/* 384:442 */     return types;
/* 385:    */   }
/* 386:    */   
/* 387:    */   private static Type getType(String className, ServiceRegistry serviceRegistry)
/* 388:    */   {
/* 389:447 */     return getType(((ClassLoaderService)serviceRegistry.getService(ClassLoaderService.class)).classForName(className));
/* 390:    */   }
/* 391:    */   
/* 392:    */   private static Type getType(Class clazz)
/* 393:    */   {
/* 394:451 */     return Type.create(DotName.createSimple(clazz.getName()), getTypeKind(clazz));
/* 395:    */   }
/* 396:    */   
/* 397:    */   private static Type.Kind getTypeKind(Class clazz)
/* 398:    */   {
/* 399:    */     Type.Kind kind;
/* 400:    */     Type.Kind kind;
/* 401:456 */     if (clazz == Void.TYPE)
/* 402:    */     {
/* 403:457 */       kind = Type.Kind.VOID;
/* 404:    */     }
/* 405:    */     else
/* 406:    */     {
/* 407:    */       Type.Kind kind;
/* 408:459 */       if (clazz.isPrimitive())
/* 409:    */       {
/* 410:460 */         kind = Type.Kind.PRIMITIVE;
/* 411:    */       }
/* 412:    */       else
/* 413:    */       {
/* 414:    */         Type.Kind kind;
/* 415:462 */         if (clazz.isArray()) {
/* 416:463 */           kind = Type.Kind.ARRAY;
/* 417:    */         } else {
/* 418:466 */           kind = Type.Kind.CLASS;
/* 419:    */         }
/* 420:    */       }
/* 421:    */     }
/* 422:468 */     return kind;
/* 423:    */   }
/* 424:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.xml.mocker.MockHelper
 * JD-Core Version:    0.7.0.1
 */