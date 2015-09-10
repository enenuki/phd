/*   1:    */ package org.hibernate.property;
/*   2:    */ 
/*   3:    */ import java.beans.Introspector;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Member;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.util.Map;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.PropertyAccessException;
/*  10:    */ import org.hibernate.PropertyNotFoundException;
/*  11:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.internal.util.ReflectHelper;
/*  15:    */ import org.jboss.logging.Logger;
/*  16:    */ 
/*  17:    */ public class BasicPropertyAccessor
/*  18:    */   implements PropertyAccessor
/*  19:    */ {
/*  20: 49 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, BasicPropertyAccessor.class.getName());
/*  21:    */   
/*  22:    */   public static final class BasicSetter
/*  23:    */     implements Setter
/*  24:    */   {
/*  25:    */     private Class clazz;
/*  26:    */     private final transient Method method;
/*  27:    */     private final String propertyName;
/*  28:    */     
/*  29:    */     private BasicSetter(Class clazz, Method method, String propertyName)
/*  30:    */     {
/*  31: 57 */       this.clazz = clazz;
/*  32: 58 */       this.method = method;
/*  33: 59 */       this.propertyName = propertyName;
/*  34:    */     }
/*  35:    */     
/*  36:    */     public void set(Object target, Object value, SessionFactoryImplementor factory)
/*  37:    */       throws HibernateException
/*  38:    */     {
/*  39:    */       try
/*  40:    */       {
/*  41: 65 */         this.method.invoke(target, new Object[] { value });
/*  42:    */       }
/*  43:    */       catch (NullPointerException npe)
/*  44:    */       {
/*  45: 68 */         if ((value == null) && (this.method.getParameterTypes()[0].isPrimitive())) {
/*  46: 69 */           throw new PropertyAccessException(npe, "Null value was assigned to a property of primitive type", true, this.clazz, this.propertyName);
/*  47:    */         }
/*  48: 78 */         throw new PropertyAccessException(npe, "NullPointerException occurred while calling", true, this.clazz, this.propertyName);
/*  49:    */       }
/*  50:    */       catch (InvocationTargetException ite)
/*  51:    */       {
/*  52: 88 */         throw new PropertyAccessException(ite, "Exception occurred inside", true, this.clazz, this.propertyName);
/*  53:    */       }
/*  54:    */       catch (IllegalAccessException iae)
/*  55:    */       {
/*  56: 97 */         throw new PropertyAccessException(iae, "IllegalAccessException occurred while calling", true, this.clazz, this.propertyName);
/*  57:    */       }
/*  58:    */       catch (IllegalArgumentException iae)
/*  59:    */       {
/*  60:107 */         if ((value == null) && (this.method.getParameterTypes()[0].isPrimitive())) {
/*  61:108 */           throw new PropertyAccessException(iae, "Null value was assigned to a property of primitive type", true, this.clazz, this.propertyName);
/*  62:    */         }
/*  63:117 */         BasicPropertyAccessor.LOG.illegalPropertySetterArgument(this.clazz.getName(), this.propertyName);
/*  64:118 */         BasicPropertyAccessor.LOG.expectedType(this.method.getParameterTypes()[0].getName(), value == null ? null : value.getClass().getName());
/*  65:119 */         throw new PropertyAccessException(iae, "IllegalArgumentException occurred while calling", true, this.clazz, this.propertyName);
/*  66:    */       }
/*  67:    */     }
/*  68:    */     
/*  69:    */     public Method getMethod()
/*  70:    */     {
/*  71:131 */       return this.method;
/*  72:    */     }
/*  73:    */     
/*  74:    */     public String getMethodName()
/*  75:    */     {
/*  76:135 */       return this.method.getName();
/*  77:    */     }
/*  78:    */     
/*  79:    */     Object readResolve()
/*  80:    */     {
/*  81:139 */       return BasicPropertyAccessor.createSetter(this.clazz, this.propertyName);
/*  82:    */     }
/*  83:    */     
/*  84:    */     public String toString()
/*  85:    */     {
/*  86:144 */       return "BasicSetter(" + this.clazz.getName() + '.' + this.propertyName + ')';
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static final class BasicGetter
/*  91:    */     implements Getter
/*  92:    */   {
/*  93:    */     private Class clazz;
/*  94:    */     private final transient Method method;
/*  95:    */     private final String propertyName;
/*  96:    */     
/*  97:    */     private BasicGetter(Class clazz, Method method, String propertyName)
/*  98:    */     {
/*  99:154 */       this.clazz = clazz;
/* 100:155 */       this.method = method;
/* 101:156 */       this.propertyName = propertyName;
/* 102:    */     }
/* 103:    */     
/* 104:    */     public Object get(Object target)
/* 105:    */       throws HibernateException
/* 106:    */     {
/* 107:    */       try
/* 108:    */       {
/* 109:164 */         return this.method.invoke(target, (Object[])null);
/* 110:    */       }
/* 111:    */       catch (InvocationTargetException ite)
/* 112:    */       {
/* 113:167 */         throw new PropertyAccessException(ite, "Exception occurred inside", false, this.clazz, this.propertyName);
/* 114:    */       }
/* 115:    */       catch (IllegalAccessException iae)
/* 116:    */       {
/* 117:176 */         throw new PropertyAccessException(iae, "IllegalAccessException occurred while calling", false, this.clazz, this.propertyName);
/* 118:    */       }
/* 119:    */       catch (IllegalArgumentException iae)
/* 120:    */       {
/* 121:186 */         BasicPropertyAccessor.LOG.illegalPropertyGetterArgument(this.clazz.getName(), this.propertyName);
/* 122:187 */         throw new PropertyAccessException(iae, "IllegalArgumentException occurred calling", false, this.clazz, this.propertyName);
/* 123:    */       }
/* 124:    */     }
/* 125:    */     
/* 126:    */     public Object getForInsert(Object target, Map mergeMap, SessionImplementor session)
/* 127:    */     {
/* 128:201 */       return get(target);
/* 129:    */     }
/* 130:    */     
/* 131:    */     public Class getReturnType()
/* 132:    */     {
/* 133:208 */       return this.method.getReturnType();
/* 134:    */     }
/* 135:    */     
/* 136:    */     public Member getMember()
/* 137:    */     {
/* 138:215 */       return this.method;
/* 139:    */     }
/* 140:    */     
/* 141:    */     public Method getMethod()
/* 142:    */     {
/* 143:222 */       return this.method;
/* 144:    */     }
/* 145:    */     
/* 146:    */     public String getMethodName()
/* 147:    */     {
/* 148:229 */       return this.method.getName();
/* 149:    */     }
/* 150:    */     
/* 151:    */     public String toString()
/* 152:    */     {
/* 153:234 */       return "BasicGetter(" + this.clazz.getName() + '.' + this.propertyName + ')';
/* 154:    */     }
/* 155:    */     
/* 156:    */     Object readResolve()
/* 157:    */     {
/* 158:238 */       return BasicPropertyAccessor.createGetter(this.clazz, this.propertyName);
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Setter getSetter(Class theClass, String propertyName)
/* 163:    */     throws PropertyNotFoundException
/* 164:    */   {
/* 165:245 */     return createSetter(theClass, propertyName);
/* 166:    */   }
/* 167:    */   
/* 168:    */   private static Setter createSetter(Class theClass, String propertyName)
/* 169:    */     throws PropertyNotFoundException
/* 170:    */   {
/* 171:250 */     BasicSetter result = getSetterOrNull(theClass, propertyName);
/* 172:251 */     if (result == null) {
/* 173:252 */       throw new PropertyNotFoundException("Could not find a setter for property " + propertyName + " in class " + theClass.getName());
/* 174:    */     }
/* 175:259 */     return result;
/* 176:    */   }
/* 177:    */   
/* 178:    */   private static BasicSetter getSetterOrNull(Class theClass, String propertyName)
/* 179:    */   {
/* 180:264 */     if ((theClass == Object.class) || (theClass == null)) {
/* 181:264 */       return null;
/* 182:    */     }
/* 183:266 */     Method method = setterMethod(theClass, propertyName);
/* 184:268 */     if (method != null)
/* 185:    */     {
/* 186:269 */       if (!ReflectHelper.isPublic(theClass, method)) {
/* 187:269 */         method.setAccessible(true);
/* 188:    */       }
/* 189:270 */       return new BasicSetter(theClass, method, propertyName, null);
/* 190:    */     }
/* 191:273 */     BasicSetter setter = getSetterOrNull(theClass.getSuperclass(), propertyName);
/* 192:274 */     if (setter == null)
/* 193:    */     {
/* 194:275 */       Class[] interfaces = theClass.getInterfaces();
/* 195:276 */       for (int i = 0; (setter == null) && (i < interfaces.length); i++) {
/* 196:277 */         setter = getSetterOrNull(interfaces[i], propertyName);
/* 197:    */       }
/* 198:    */     }
/* 199:280 */     return setter;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private static Method setterMethod(Class theClass, String propertyName)
/* 203:    */   {
/* 204:287 */     BasicGetter getter = getGetterOrNull(theClass, propertyName);
/* 205:288 */     Class returnType = getter == null ? null : getter.getReturnType();
/* 206:    */     
/* 207:290 */     Method[] methods = theClass.getDeclaredMethods();
/* 208:291 */     Method potentialSetter = null;
/* 209:292 */     for (Method method : methods)
/* 210:    */     {
/* 211:293 */       String methodName = method.getName();
/* 212:295 */       if ((method.getParameterTypes().length == 1) && (methodName.startsWith("set")))
/* 213:    */       {
/* 214:296 */         String testStdMethod = Introspector.decapitalize(methodName.substring(3));
/* 215:297 */         String testOldMethod = methodName.substring(3);
/* 216:298 */         if ((testStdMethod.equals(propertyName)) || (testOldMethod.equals(propertyName)))
/* 217:    */         {
/* 218:299 */           potentialSetter = method;
/* 219:300 */           if ((returnType == null) || (method.getParameterTypes()[0].equals(returnType))) {
/* 220:301 */             return potentialSetter;
/* 221:    */           }
/* 222:    */         }
/* 223:    */       }
/* 224:    */     }
/* 225:306 */     return potentialSetter;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public Getter getGetter(Class theClass, String propertyName)
/* 229:    */     throws PropertyNotFoundException
/* 230:    */   {
/* 231:310 */     return createGetter(theClass, propertyName);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static Getter createGetter(Class theClass, String propertyName)
/* 235:    */     throws PropertyNotFoundException
/* 236:    */   {
/* 237:314 */     BasicGetter result = getGetterOrNull(theClass, propertyName);
/* 238:315 */     if (result == null) {
/* 239:316 */       throw new PropertyNotFoundException("Could not find a getter for " + propertyName + " in class " + theClass.getName());
/* 240:    */     }
/* 241:323 */     return result;
/* 242:    */   }
/* 243:    */   
/* 244:    */   private static BasicGetter getGetterOrNull(Class theClass, String propertyName)
/* 245:    */   {
/* 246:327 */     if ((theClass == Object.class) || (theClass == null)) {
/* 247:328 */       return null;
/* 248:    */     }
/* 249:331 */     Method method = getterMethod(theClass, propertyName);
/* 250:333 */     if (method != null)
/* 251:    */     {
/* 252:334 */       if (!ReflectHelper.isPublic(theClass, method)) {
/* 253:335 */         method.setAccessible(true);
/* 254:    */       }
/* 255:337 */       return new BasicGetter(theClass, method, propertyName, null);
/* 256:    */     }
/* 257:340 */     BasicGetter getter = getGetterOrNull(theClass.getSuperclass(), propertyName);
/* 258:341 */     if (getter == null)
/* 259:    */     {
/* 260:342 */       Class[] interfaces = theClass.getInterfaces();
/* 261:343 */       for (int i = 0; (getter == null) && (i < interfaces.length); i++) {
/* 262:344 */         getter = getGetterOrNull(interfaces[i], propertyName);
/* 263:    */       }
/* 264:    */     }
/* 265:347 */     return getter;
/* 266:    */   }
/* 267:    */   
/* 268:    */   private static Method getterMethod(Class theClass, String propertyName)
/* 269:    */   {
/* 270:352 */     Method[] methods = theClass.getDeclaredMethods();
/* 271:353 */     for (Method method : methods) {
/* 272:355 */       if (method.getParameterTypes().length == 0) {
/* 273:359 */         if (!method.isBridge())
/* 274:    */         {
/* 275:363 */           String methodName = method.getName();
/* 276:366 */           if (methodName.startsWith("get"))
/* 277:    */           {
/* 278:367 */             String testStdMethod = Introspector.decapitalize(methodName.substring(3));
/* 279:368 */             String testOldMethod = methodName.substring(3);
/* 280:369 */             if ((testStdMethod.equals(propertyName)) || (testOldMethod.equals(propertyName))) {
/* 281:370 */               return method;
/* 282:    */             }
/* 283:    */           }
/* 284:375 */           if (methodName.startsWith("is"))
/* 285:    */           {
/* 286:376 */             String testStdMethod = Introspector.decapitalize(methodName.substring(2));
/* 287:377 */             String testOldMethod = methodName.substring(2);
/* 288:378 */             if ((testStdMethod.equals(propertyName)) || (testOldMethod.equals(propertyName))) {
/* 289:379 */               return method;
/* 290:    */             }
/* 291:    */           }
/* 292:    */         }
/* 293:    */       }
/* 294:    */     }
/* 295:384 */     return null;
/* 296:    */   }
/* 297:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.BasicPropertyAccessor
 * JD-Core Version:    0.7.0.1
 */