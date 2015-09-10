/*   1:    */ package org.hibernate.metamodel.source.annotations;
/*   2:    */ 
/*   3:    */ import java.beans.Introspector;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStream;
/*   6:    */ import java.lang.reflect.Array;
/*   7:    */ import java.lang.reflect.Method;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import org.hibernate.AssertionFailure;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  15:    */ import org.jboss.jandex.AnnotationInstance;
/*  16:    */ import org.jboss.jandex.AnnotationTarget;
/*  17:    */ import org.jboss.jandex.AnnotationValue;
/*  18:    */ import org.jboss.jandex.ClassInfo;
/*  19:    */ import org.jboss.jandex.DotName;
/*  20:    */ import org.jboss.jandex.FieldInfo;
/*  21:    */ import org.jboss.jandex.Index;
/*  22:    */ import org.jboss.jandex.Indexer;
/*  23:    */ import org.jboss.jandex.MethodInfo;
/*  24:    */ import org.jboss.jandex.Type;
/*  25:    */ 
/*  26:    */ public class JandexHelper
/*  27:    */ {
/*  28: 56 */   private static final Map<String, Object> DEFAULT_VALUES_BY_ELEMENT = new HashMap();
/*  29:    */   
/*  30:    */   public static <T> T getValue(AnnotationInstance annotation, String element, Class<T> type)
/*  31:    */     throws AssertionFailure
/*  32:    */   {
/*  33: 92 */     if (Class.class.equals(type)) {
/*  34: 93 */       throw new AssertionFailure("Annotation parameters of type Class should be retrieved as strings (fully qualified class names)");
/*  35:    */     }
/*  36: 99 */     AnnotationValue annotationValue = annotation.value(element);
/*  37:    */     try
/*  38:    */     {
/*  39:102 */       if (annotationValue != null) {
/*  40:103 */         return explicitAnnotationParameter(annotationValue, type);
/*  41:    */       }
/*  42:106 */       return defaultAnnotationParameter(getDefaultValue(annotation, element), type);
/*  43:    */     }
/*  44:    */     catch (ClassCastException e)
/*  45:    */     {
/*  46:110 */       throw new AssertionFailure(String.format("the annotation property %s of annotation %s is not of type %s", new Object[] { element, annotation.name(), type.getName() }));
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static <T extends Enum<T>> T getEnumValue(AnnotationInstance annotation, String element, Class<T> type)
/*  51:    */   {
/*  52:137 */     AnnotationValue val = annotation.value(element);
/*  53:138 */     if (val == null) {
/*  54:139 */       return (Enum)getDefaultValue(annotation, element);
/*  55:    */     }
/*  56:141 */     return Enum.valueOf(type, val.asEnum());
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static String getPropertyName(AnnotationTarget target)
/*  60:    */   {
/*  61:153 */     if ((!(target instanceof MethodInfo)) && (!(target instanceof FieldInfo))) {
/*  62:154 */       throw new AssertionFailure("Unexpected annotation target " + target.toString());
/*  63:    */     }
/*  64:157 */     if ((target instanceof FieldInfo)) {
/*  65:158 */       return ((FieldInfo)target).name();
/*  66:    */     }
/*  67:161 */     String methodName = ((MethodInfo)target).name();
/*  68:    */     String propertyName;
/*  69:163 */     if (methodName.startsWith("is"))
/*  70:    */     {
/*  71:164 */       propertyName = Introspector.decapitalize(methodName.substring(2));
/*  72:    */     }
/*  73:    */     else
/*  74:    */     {
/*  75:    */       String propertyName;
/*  76:166 */       if (methodName.startsWith("has"))
/*  77:    */       {
/*  78:167 */         propertyName = Introspector.decapitalize(methodName.substring(3));
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82:    */         String propertyName;
/*  83:169 */         if (methodName.startsWith("get")) {
/*  84:170 */           propertyName = Introspector.decapitalize(methodName.substring(3));
/*  85:    */         } else {
/*  86:173 */           throw new AssertionFailure("Expected a method following the Java Bean notation");
/*  87:    */         }
/*  88:    */       }
/*  89:    */     }
/*  90:    */     String propertyName;
/*  91:175 */     return propertyName;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static AnnotationInstance getSingleAnnotation(ClassInfo classInfo, DotName annotationName)
/*  95:    */     throws AssertionFailure
/*  96:    */   {
/*  97:189 */     return getSingleAnnotation(classInfo.annotations(), annotationName);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static AnnotationInstance getSingleAnnotation(Map<DotName, List<AnnotationInstance>> annotations, DotName annotationName)
/* 101:    */     throws AssertionFailure
/* 102:    */   {
/* 103:202 */     List<AnnotationInstance> annotationList = (List)annotations.get(annotationName);
/* 104:203 */     if (annotationList == null) {
/* 105:204 */       return null;
/* 106:    */     }
/* 107:206 */     if (annotationList.size() == 1) {
/* 108:207 */       return (AnnotationInstance)annotationList.get(0);
/* 109:    */     }
/* 110:210 */     throw new AssertionFailure("Found more than one instance of the annotation " + ((AnnotationInstance)annotationList.get(0)).name().toString() + ". Expected was one.");
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static boolean containsSingleAnnotations(Map<DotName, List<AnnotationInstance>> annotations, DotName annotationName)
/* 114:    */     throws AssertionFailure
/* 115:    */   {
/* 116:228 */     return getSingleAnnotation(annotations, annotationName) != null;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static Index indexForClass(ClassLoaderService classLoaderService, Class<?>... classes)
/* 120:    */   {
/* 121:240 */     Indexer indexer = new Indexer();
/* 122:241 */     for (Class<?> clazz : classes)
/* 123:    */     {
/* 124:242 */       InputStream stream = classLoaderService.locateResourceStream(clazz.getName().replace('.', '/') + ".class");
/* 125:    */       try
/* 126:    */       {
/* 127:246 */         indexer.index(stream);
/* 128:    */       }
/* 129:    */       catch (IOException e)
/* 130:    */       {
/* 131:249 */         StringBuilder builder = new StringBuilder();
/* 132:250 */         builder.append("[");
/* 133:251 */         int count = 0;
/* 134:252 */         for (Class<?> c : classes)
/* 135:    */         {
/* 136:253 */           builder.append(c.getName());
/* 137:254 */           if (count < classes.length - 1) {
/* 138:255 */             builder.append(",");
/* 139:    */           }
/* 140:257 */           count++;
/* 141:    */         }
/* 142:259 */         builder.append("]");
/* 143:260 */         throw new HibernateException("Unable to create annotation index for " + builder.toString());
/* 144:    */       }
/* 145:    */     }
/* 146:263 */     return indexer.complete();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static Map<DotName, List<AnnotationInstance>> getMemberAnnotations(ClassInfo classInfo, String name)
/* 150:    */   {
/* 151:267 */     if (classInfo == null) {
/* 152:268 */       throw new IllegalArgumentException("classInfo cannot be null");
/* 153:    */     }
/* 154:271 */     if (name == null) {
/* 155:272 */       throw new IllegalArgumentException("name cannot be null");
/* 156:    */     }
/* 157:275 */     Map<DotName, List<AnnotationInstance>> annotations = new HashMap();
/* 158:276 */     for (List<AnnotationInstance> annotationList : classInfo.annotations().values()) {
/* 159:277 */       for (AnnotationInstance instance : annotationList)
/* 160:    */       {
/* 161:278 */         String targetName = null;
/* 162:279 */         if ((instance.target() instanceof FieldInfo)) {
/* 163:280 */           targetName = ((FieldInfo)instance.target()).name();
/* 164:282 */         } else if ((instance.target() instanceof MethodInfo)) {
/* 165:283 */           targetName = ((MethodInfo)instance.target()).name();
/* 166:    */         }
/* 167:285 */         if ((targetName != null) && (name.equals(targetName))) {
/* 168:286 */           addAnnotationToMap(instance, annotations);
/* 169:    */         }
/* 170:    */       }
/* 171:    */     }
/* 172:290 */     return annotations;
/* 173:    */   }
/* 174:    */   
/* 175:    */   private static void addAnnotationToMap(AnnotationInstance instance, Map<DotName, List<AnnotationInstance>> annotations)
/* 176:    */   {
/* 177:294 */     DotName dotName = instance.name();
/* 178:    */     List<AnnotationInstance> list;
/* 179:    */     List<AnnotationInstance> list;
/* 180:296 */     if (annotations.containsKey(dotName))
/* 181:    */     {
/* 182:297 */       list = (List)annotations.get(dotName);
/* 183:    */     }
/* 184:    */     else
/* 185:    */     {
/* 186:300 */       list = new ArrayList();
/* 187:301 */       annotations.put(dotName, list);
/* 188:    */     }
/* 189:303 */     list.add(instance);
/* 190:    */   }
/* 191:    */   
/* 192:    */   private static Object getDefaultValue(AnnotationInstance annotation, String element)
/* 193:    */   {
/* 194:307 */     String name = annotation.name().toString();
/* 195:308 */     String fqElement = name + '.' + element;
/* 196:309 */     Object val = DEFAULT_VALUES_BY_ELEMENT.get(fqElement);
/* 197:310 */     if (val != null) {
/* 198:311 */       return val;
/* 199:    */     }
/* 200:    */     try
/* 201:    */     {
/* 202:314 */       val = Index.class.getClassLoader().loadClass(name).getMethod(element, new Class[0]).getDefaultValue();
/* 203:315 */       DEFAULT_VALUES_BY_ELEMENT.put(fqElement, val);
/* 204:316 */       return val == null ? null : val;
/* 205:    */     }
/* 206:    */     catch (RuntimeException error)
/* 207:    */     {
/* 208:319 */       throw error;
/* 209:    */     }
/* 210:    */     catch (Exception error)
/* 211:    */     {
/* 212:322 */       throw new AssertionFailure(String.format("The annotation %s does not define a parameter '%s'", new Object[] { name, element }), error);
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   private static <T> T defaultAnnotationParameter(Object defaultValue, Class<T> type)
/* 217:    */   {
/* 218:330 */     Object returnValue = defaultValue;
/* 219:335 */     if ((defaultValue.getClass().isArray()) && (defaultValue.getClass().getComponentType().isAnnotation())) {
/* 220:336 */       returnValue = new AnnotationInstance[0];
/* 221:    */     }
/* 222:338 */     return type.cast(returnValue);
/* 223:    */   }
/* 224:    */   
/* 225:    */   private static <T> T explicitAnnotationParameter(AnnotationValue annotationValue, Class<T> type)
/* 226:    */   {
/* 227:342 */     Object returnValue = annotationValue.value();
/* 228:346 */     if ((returnValue instanceof Type)) {
/* 229:347 */       returnValue = ((Type)returnValue).name().toString();
/* 230:    */     }
/* 231:351 */     if (type.isArray())
/* 232:    */     {
/* 233:352 */       AnnotationValue[] values = (AnnotationValue[])returnValue;
/* 234:353 */       Class<?> componentType = type.getComponentType();
/* 235:354 */       Object[] arr = (Object[])Array.newInstance(componentType, values.length);
/* 236:355 */       for (int i = 0; i < values.length; i++) {
/* 237:356 */         arr[i] = componentType.cast(values[i].value());
/* 238:    */       }
/* 239:358 */       returnValue = arr;
/* 240:    */     }
/* 241:361 */     return type.cast(returnValue);
/* 242:    */   }
/* 243:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.JandexHelper
 * JD-Core Version:    0.7.0.1
 */