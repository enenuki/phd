/*   1:    */ package org.hibernate.internal.util;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.Member;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Modifier;
/*   8:    */ import org.hibernate.AssertionFailure;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.PropertyNotFoundException;
/*  11:    */ import org.hibernate.property.BasicPropertyAccessor;
/*  12:    */ import org.hibernate.property.DirectPropertyAccessor;
/*  13:    */ import org.hibernate.property.Getter;
/*  14:    */ import org.hibernate.property.PropertyAccessor;
/*  15:    */ import org.hibernate.type.PrimitiveType;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ 
/*  18:    */ public final class ReflectHelper
/*  19:    */ {
/*  20:    */   private static final PropertyAccessor BASIC_PROPERTY_ACCESSOR;
/*  21:    */   private static final PropertyAccessor DIRECT_PROPERTY_ACCESSOR;
/*  22:    */   public static final Class[] NO_PARAM_SIGNATURE;
/*  23:    */   public static final Object[] NO_PARAMS;
/*  24:    */   public static final Class[] SINGLE_OBJECT_PARAM_SIGNATURE;
/*  25:    */   private static final Method OBJECT_EQUALS;
/*  26:    */   private static final Method OBJECT_HASHCODE;
/*  27:    */   
/*  28:    */   static
/*  29:    */   {
/*  30: 51 */     BASIC_PROPERTY_ACCESSOR = new BasicPropertyAccessor();
/*  31: 52 */     DIRECT_PROPERTY_ACCESSOR = new DirectPropertyAccessor();
/*  32:    */     
/*  33: 54 */     NO_PARAM_SIGNATURE = new Class[0];
/*  34: 55 */     NO_PARAMS = new Object[0];
/*  35:    */     
/*  36: 57 */     SINGLE_OBJECT_PARAM_SIGNATURE = new Class[] { Object.class };
/*  37:    */     Method eq;
/*  38:    */     Method hash;
/*  39:    */     try
/*  40:    */     {
/*  41: 66 */       eq = extractEqualsMethod(Object.class);
/*  42: 67 */       hash = extractHashCodeMethod(Object.class);
/*  43:    */     }
/*  44:    */     catch (Exception e)
/*  45:    */     {
/*  46: 70 */       throw new AssertionFailure("Could not find Object.equals() or Object.hashCode()", e);
/*  47:    */     }
/*  48: 72 */     OBJECT_EQUALS = eq;
/*  49: 73 */     OBJECT_HASHCODE = hash;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Method extractEqualsMethod(Class clazz)
/*  53:    */     throws NoSuchMethodException
/*  54:    */   {
/*  55: 90 */     return clazz.getMethod("equals", SINGLE_OBJECT_PARAM_SIGNATURE);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static Method extractHashCodeMethod(Class clazz)
/*  59:    */     throws NoSuchMethodException
/*  60:    */   {
/*  61:101 */     return clazz.getMethod("hashCode", NO_PARAM_SIGNATURE);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static boolean overridesEquals(Class clazz)
/*  65:    */   {
/*  66:    */     Method equals;
/*  67:    */     try
/*  68:    */     {
/*  69:113 */       equals = extractEqualsMethod(clazz);
/*  70:    */     }
/*  71:    */     catch (NoSuchMethodException nsme)
/*  72:    */     {
/*  73:116 */       return false;
/*  74:    */     }
/*  75:118 */     return !OBJECT_EQUALS.equals(equals);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static boolean overridesHashCode(Class clazz)
/*  79:    */   {
/*  80:    */     Method hashCode;
/*  81:    */     try
/*  82:    */     {
/*  83:130 */       hashCode = extractHashCodeMethod(clazz);
/*  84:    */     }
/*  85:    */     catch (NoSuchMethodException nsme)
/*  86:    */     {
/*  87:133 */       return false;
/*  88:    */     }
/*  89:135 */     return !OBJECT_HASHCODE.equals(hashCode);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public static boolean implementsInterface(Class clazz, Class intf)
/*  93:    */   {
/*  94:146 */     assert (intf.isInterface()) : "Interface to check was not an interface";
/*  95:147 */     return intf.isAssignableFrom(clazz);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Class classForName(String name, Class caller)
/*  99:    */     throws ClassNotFoundException
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103:163 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/* 104:164 */       if (contextClassLoader != null) {
/* 105:165 */         return contextClassLoader.loadClass(name);
/* 106:    */       }
/* 107:    */     }
/* 108:    */     catch (Throwable ignore) {}
/* 109:170 */     return Class.forName(name, true, caller.getClassLoader());
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static Class classForName(String name)
/* 113:    */     throws ClassNotFoundException
/* 114:    */   {
/* 115:    */     try
/* 116:    */     {
/* 117:185 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/* 118:186 */       if (contextClassLoader != null) {
/* 119:187 */         return contextClassLoader.loadClass(name);
/* 120:    */       }
/* 121:    */     }
/* 122:    */     catch (Throwable ignore) {}
/* 123:192 */     return Class.forName(name);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static boolean isPublic(Member member)
/* 127:    */   {
/* 128:204 */     return isPublic(member.getDeclaringClass(), member);
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static boolean isPublic(Class clazz, Member member)
/* 132:    */   {
/* 133:215 */     return (Modifier.isPublic(member.getModifiers())) && (Modifier.isPublic(clazz.getModifiers()));
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static Class reflectedPropertyClass(String className, String name)
/* 137:    */     throws MappingException
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:228 */       Class clazz = classForName(className);
/* 142:229 */       return getter(clazz, name).getReturnType();
/* 143:    */     }
/* 144:    */     catch (ClassNotFoundException cnfe)
/* 145:    */     {
/* 146:232 */       throw new MappingException("class " + className + " not found while looking for property: " + name, cnfe);
/* 147:    */     }
/* 148:    */   }
/* 149:    */   
/* 150:    */   public static Class reflectedPropertyClass(Class clazz, String name)
/* 151:    */     throws MappingException
/* 152:    */   {
/* 153:245 */     return getter(clazz, name).getReturnType();
/* 154:    */   }
/* 155:    */   
/* 156:    */   private static Getter getter(Class clazz, String name)
/* 157:    */     throws MappingException
/* 158:    */   {
/* 159:    */     try
/* 160:    */     {
/* 161:250 */       return BASIC_PROPERTY_ACCESSOR.getGetter(clazz, name);
/* 162:    */     }
/* 163:    */     catch (PropertyNotFoundException pnfe) {}
/* 164:253 */     return DIRECT_PROPERTY_ACCESSOR.getGetter(clazz, name);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static Getter getGetter(Class theClass, String name)
/* 168:    */     throws MappingException
/* 169:    */   {
/* 170:266 */     return BASIC_PROPERTY_ACCESSOR.getGetter(theClass, name);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public static Object getConstantValue(String name)
/* 174:    */   {
/* 175:    */     Class clazz;
/* 176:    */     try
/* 177:    */     {
/* 178:278 */       clazz = classForName(StringHelper.qualifier(name));
/* 179:    */     }
/* 180:    */     catch (Throwable t)
/* 181:    */     {
/* 182:281 */       return null;
/* 183:    */     }
/* 184:    */     try
/* 185:    */     {
/* 186:284 */       return clazz.getField(StringHelper.unqualify(name)).get(null);
/* 187:    */     }
/* 188:    */     catch (Throwable t) {}
/* 189:287 */     return null;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static Constructor getDefaultConstructor(Class clazz)
/* 193:    */     throws PropertyNotFoundException
/* 194:    */   {
/* 195:299 */     if (isAbstractClass(clazz)) {
/* 196:300 */       return null;
/* 197:    */     }
/* 198:    */     try
/* 199:    */     {
/* 200:304 */       Constructor constructor = clazz.getDeclaredConstructor(NO_PARAM_SIGNATURE);
/* 201:305 */       if (!isPublic(clazz, constructor)) {
/* 202:306 */         constructor.setAccessible(true);
/* 203:    */       }
/* 204:308 */       return constructor;
/* 205:    */     }
/* 206:    */     catch (NoSuchMethodException nme)
/* 207:    */     {
/* 208:311 */       throw new PropertyNotFoundException("Object class [" + clazz.getName() + "] must declare a default (no-argument) constructor");
/* 209:    */     }
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static boolean isAbstractClass(Class clazz)
/* 213:    */   {
/* 214:324 */     int modifier = clazz.getModifiers();
/* 215:325 */     return (Modifier.isAbstract(modifier)) || (Modifier.isInterface(modifier));
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static boolean isFinalClass(Class clazz)
/* 219:    */   {
/* 220:335 */     return Modifier.isFinal(clazz.getModifiers());
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static Constructor getConstructor(Class clazz, Type[] types)
/* 224:    */     throws PropertyNotFoundException
/* 225:    */   {
/* 226:348 */     Constructor[] candidates = clazz.getConstructors();
/* 227:349 */     for (int i = 0; i < candidates.length; i++)
/* 228:    */     {
/* 229:350 */       Constructor constructor = candidates[i];
/* 230:351 */       Class[] params = constructor.getParameterTypes();
/* 231:352 */       if (params.length == types.length)
/* 232:    */       {
/* 233:353 */         boolean found = true;
/* 234:354 */         for (int j = 0; j < params.length; j++)
/* 235:    */         {
/* 236:355 */           boolean ok = (params[j].isAssignableFrom(types[j].getReturnedClass())) || (((types[j] instanceof PrimitiveType)) && (params[j] == ((PrimitiveType)types[j]).getPrimitiveClass()));
/* 237:359 */           if (!ok)
/* 238:    */           {
/* 239:360 */             found = false;
/* 240:361 */             break;
/* 241:    */           }
/* 242:    */         }
/* 243:364 */         if (found)
/* 244:    */         {
/* 245:365 */           if (!isPublic(clazz, constructor)) {
/* 246:366 */             constructor.setAccessible(true);
/* 247:    */           }
/* 248:368 */           return constructor;
/* 249:    */         }
/* 250:    */       }
/* 251:    */     }
/* 252:372 */     throw new PropertyNotFoundException("no appropriate constructor in class: " + clazz.getName());
/* 253:    */   }
/* 254:    */   
/* 255:    */   public static Method getMethod(Class clazz, Method method)
/* 256:    */   {
/* 257:    */     try
/* 258:    */     {
/* 259:377 */       return clazz.getMethod(method.getName(), method.getParameterTypes());
/* 260:    */     }
/* 261:    */     catch (Exception e) {}
/* 262:380 */     return null;
/* 263:    */   }
/* 264:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.util.ReflectHelper
 * JD-Core Version:    0.7.0.1
 */