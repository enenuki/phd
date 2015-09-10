/*   1:    */ package org.apache.commons.lang.reflect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationTargetException;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import org.apache.commons.lang.ArrayUtils;
/*   7:    */ import org.apache.commons.lang.ClassUtils;
/*   8:    */ 
/*   9:    */ public class MethodUtils
/*  10:    */ {
/*  11:    */   public static Object invokeMethod(Object object, String methodName, Object arg)
/*  12:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  13:    */   {
/*  14: 95 */     return invokeMethod(object, methodName, new Object[] { arg });
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static Object invokeMethod(Object object, String methodName, Object[] args)
/*  18:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  19:    */   {
/*  20:123 */     if (args == null) {
/*  21:124 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  22:    */     }
/*  23:126 */     int arguments = args.length;
/*  24:127 */     Class[] parameterTypes = new Class[arguments];
/*  25:128 */     for (int i = 0; i < arguments; i++) {
/*  26:129 */       parameterTypes[i] = args[i].getClass();
/*  27:    */     }
/*  28:131 */     return invokeMethod(object, methodName, args, parameterTypes);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Object invokeMethod(Object object, String methodName, Object[] args, Class[] parameterTypes)
/*  32:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  33:    */   {
/*  34:157 */     if (parameterTypes == null) {
/*  35:158 */       parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  36:    */     }
/*  37:160 */     if (args == null) {
/*  38:161 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  39:    */     }
/*  40:163 */     Method method = getMatchingAccessibleMethod(object.getClass(), methodName, parameterTypes);
/*  41:165 */     if (method == null) {
/*  42:166 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object.getClass().getName());
/*  43:    */     }
/*  44:170 */     return method.invoke(object, args);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static Object invokeExactMethod(Object object, String methodName, Object arg)
/*  48:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  49:    */   {
/*  50:195 */     return invokeExactMethod(object, methodName, new Object[] { arg });
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static Object invokeExactMethod(Object object, String methodName, Object[] args)
/*  54:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  55:    */   {
/*  56:219 */     if (args == null) {
/*  57:220 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  58:    */     }
/*  59:222 */     int arguments = args.length;
/*  60:223 */     Class[] parameterTypes = new Class[arguments];
/*  61:224 */     for (int i = 0; i < arguments; i++) {
/*  62:225 */       parameterTypes[i] = args[i].getClass();
/*  63:    */     }
/*  64:227 */     return invokeExactMethod(object, methodName, args, parameterTypes);
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static Object invokeExactMethod(Object object, String methodName, Object[] args, Class[] parameterTypes)
/*  68:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  69:    */   {
/*  70:253 */     if (args == null) {
/*  71:254 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  72:    */     }
/*  73:256 */     if (parameterTypes == null) {
/*  74:257 */       parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  75:    */     }
/*  76:259 */     Method method = getAccessibleMethod(object.getClass(), methodName, parameterTypes);
/*  77:261 */     if (method == null) {
/*  78:262 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object.getClass().getName());
/*  79:    */     }
/*  80:266 */     return method.invoke(object, args);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static Object invokeExactStaticMethod(Class cls, String methodName, Object[] args, Class[] parameterTypes)
/*  84:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/*  85:    */   {
/*  86:292 */     if (args == null) {
/*  87:293 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  88:    */     }
/*  89:295 */     if (parameterTypes == null) {
/*  90:296 */       parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  91:    */     }
/*  92:298 */     Method method = getAccessibleMethod(cls, methodName, parameterTypes);
/*  93:299 */     if (method == null) {
/*  94:300 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName());
/*  95:    */     }
/*  96:303 */     return method.invoke(null, args);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static Object invokeStaticMethod(Class cls, String methodName, Object arg)
/* 100:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/* 101:    */   {
/* 102:333 */     return invokeStaticMethod(cls, methodName, new Object[] { arg });
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static Object invokeStaticMethod(Class cls, String methodName, Object[] args)
/* 106:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/* 107:    */   {
/* 108:363 */     if (args == null) {
/* 109:364 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/* 110:    */     }
/* 111:366 */     int arguments = args.length;
/* 112:367 */     Class[] parameterTypes = new Class[arguments];
/* 113:368 */     for (int i = 0; i < arguments; i++) {
/* 114:369 */       parameterTypes[i] = args[i].getClass();
/* 115:    */     }
/* 116:371 */     return invokeStaticMethod(cls, methodName, args, parameterTypes);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static Object invokeStaticMethod(Class cls, String methodName, Object[] args, Class[] parameterTypes)
/* 120:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/* 121:    */   {
/* 122:400 */     if (parameterTypes == null) {
/* 123:401 */       parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
/* 124:    */     }
/* 125:403 */     if (args == null) {
/* 126:404 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/* 127:    */     }
/* 128:406 */     Method method = getMatchingAccessibleMethod(cls, methodName, parameterTypes);
/* 129:408 */     if (method == null) {
/* 130:409 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName());
/* 131:    */     }
/* 132:412 */     return method.invoke(null, args);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static Object invokeExactStaticMethod(Class cls, String methodName, Object arg)
/* 136:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/* 137:    */   {
/* 138:437 */     return invokeExactStaticMethod(cls, methodName, new Object[] { arg });
/* 139:    */   }
/* 140:    */   
/* 141:    */   public static Object invokeExactStaticMethod(Class cls, String methodName, Object[] args)
/* 142:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
/* 143:    */   {
/* 144:461 */     if (args == null) {
/* 145:462 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/* 146:    */     }
/* 147:464 */     int arguments = args.length;
/* 148:465 */     Class[] parameterTypes = new Class[arguments];
/* 149:466 */     for (int i = 0; i < arguments; i++) {
/* 150:467 */       parameterTypes[i] = args[i].getClass();
/* 151:    */     }
/* 152:469 */     return invokeExactStaticMethod(cls, methodName, args, parameterTypes);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static Method getAccessibleMethod(Class cls, String methodName, Class parameterType)
/* 156:    */   {
/* 157:486 */     return getAccessibleMethod(cls, methodName, new Class[] { parameterType });
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static Method getAccessibleMethod(Class cls, String methodName, Class[] parameterTypes)
/* 161:    */   {
/* 162:    */     try
/* 163:    */     {
/* 164:505 */       return getAccessibleMethod(cls.getMethod(methodName, parameterTypes));
/* 165:    */     }
/* 166:    */     catch (NoSuchMethodException e) {}
/* 167:508 */     return null;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static Method getAccessibleMethod(Method method)
/* 171:    */   {
/* 172:521 */     if (!MemberUtils.isAccessible(method)) {
/* 173:522 */       return null;
/* 174:    */     }
/* 175:525 */     Class cls = method.getDeclaringClass();
/* 176:526 */     if (Modifier.isPublic(cls.getModifiers())) {
/* 177:527 */       return method;
/* 178:    */     }
/* 179:529 */     String methodName = method.getName();
/* 180:530 */     Class[] parameterTypes = method.getParameterTypes();
/* 181:    */     
/* 182:    */ 
/* 183:533 */     method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes);
/* 184:537 */     if (method == null) {
/* 185:538 */       method = getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
/* 186:    */     }
/* 187:541 */     return method;
/* 188:    */   }
/* 189:    */   
/* 190:    */   private static Method getAccessibleMethodFromSuperclass(Class cls, String methodName, Class[] parameterTypes)
/* 191:    */   {
/* 192:556 */     Class parentClass = cls.getSuperclass();
/* 193:557 */     while (parentClass != null)
/* 194:    */     {
/* 195:558 */       if (Modifier.isPublic(parentClass.getModifiers())) {
/* 196:    */         try
/* 197:    */         {
/* 198:560 */           return parentClass.getMethod(methodName, parameterTypes);
/* 199:    */         }
/* 200:    */         catch (NoSuchMethodException e)
/* 201:    */         {
/* 202:562 */           return null;
/* 203:    */         }
/* 204:    */       }
/* 205:565 */       parentClass = parentClass.getSuperclass();
/* 206:    */     }
/* 207:567 */     return null;
/* 208:    */   }
/* 209:    */   
/* 210:    */   private static Method getAccessibleMethodFromInterfaceNest(Class cls, String methodName, Class[] parameterTypes)
/* 211:    */   {
/* 212:587 */     Method method = null;
/* 213:590 */     for (; cls != null; cls = cls.getSuperclass())
/* 214:    */     {
/* 215:593 */       Class[] interfaces = cls.getInterfaces();
/* 216:594 */       for (int i = 0; i < interfaces.length; i++) {
/* 217:596 */         if (Modifier.isPublic(interfaces[i].getModifiers()))
/* 218:    */         {
/* 219:    */           try
/* 220:    */           {
/* 221:601 */             method = interfaces[i].getDeclaredMethod(methodName, parameterTypes);
/* 222:    */           }
/* 223:    */           catch (NoSuchMethodException e) {}
/* 224:609 */           if (method != null) {
/* 225:    */             break;
/* 226:    */           }
/* 227:613 */           method = getAccessibleMethodFromInterfaceNest(interfaces[i], methodName, parameterTypes);
/* 228:615 */           if (method != null) {
/* 229:    */             break;
/* 230:    */           }
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:620 */     return method;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static Method getMatchingAccessibleMethod(Class cls, String methodName, Class[] parameterTypes)
/* 238:    */   {
/* 239:    */     try
/* 240:    */     {
/* 241:646 */       Method method = cls.getMethod(methodName, parameterTypes);
/* 242:647 */       MemberUtils.setAccessibleWorkaround(method);
/* 243:648 */       return method;
/* 244:    */     }
/* 245:    */     catch (NoSuchMethodException e)
/* 246:    */     {
/* 247:652 */       Method bestMatch = null;
/* 248:653 */       Method[] methods = cls.getMethods();
/* 249:654 */       int i = 0;
/* 250:654 */       for (int size = methods.length; i < size; i++) {
/* 251:655 */         if (methods[i].getName().equals(methodName)) {
/* 252:657 */           if (ClassUtils.isAssignable(parameterTypes, methods[i].getParameterTypes(), true))
/* 253:    */           {
/* 254:660 */             Method accessibleMethod = getAccessibleMethod(methods[i]);
/* 255:661 */             if ((accessibleMethod != null) && (
/* 256:662 */               (bestMatch == null) || (MemberUtils.compareParameterTypes(accessibleMethod.getParameterTypes(), bestMatch.getParameterTypes(), parameterTypes) < 0))) {
/* 257:667 */               bestMatch = accessibleMethod;
/* 258:    */             }
/* 259:    */           }
/* 260:    */         }
/* 261:    */       }
/* 262:673 */       if (bestMatch != null) {
/* 263:674 */         MemberUtils.setAccessibleWorkaround(bestMatch);
/* 264:    */       }
/* 265:676 */       return bestMatch;
/* 266:    */     }
/* 267:    */   }
/* 268:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.reflect.MethodUtils
 * JD-Core Version:    0.7.0.1
 */