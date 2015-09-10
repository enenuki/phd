/*   1:    */ package org.apache.commons.lang.reflect;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Modifier;
/*   6:    */ import org.apache.commons.lang.ArrayUtils;
/*   7:    */ import org.apache.commons.lang.ClassUtils;
/*   8:    */ 
/*   9:    */ public class ConstructorUtils
/*  10:    */ {
/*  11:    */   public static Object invokeConstructor(Class cls, Object arg)
/*  12:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*  13:    */   {
/*  14: 92 */     return invokeConstructor(cls, new Object[] { arg });
/*  15:    */   }
/*  16:    */   
/*  17:    */   public static Object invokeConstructor(Class cls, Object[] args)
/*  18:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*  19:    */   {
/*  20:120 */     if (null == args) {
/*  21:121 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  22:    */     }
/*  23:123 */     Class[] parameterTypes = new Class[args.length];
/*  24:124 */     for (int i = 0; i < args.length; i++) {
/*  25:125 */       parameterTypes[i] = args[i].getClass();
/*  26:    */     }
/*  27:127 */     return invokeConstructor(cls, args, parameterTypes);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Object invokeConstructor(Class cls, Object[] args, Class[] parameterTypes)
/*  31:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*  32:    */   {
/*  33:151 */     if (parameterTypes == null) {
/*  34:152 */       parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  35:    */     }
/*  36:154 */     if (args == null) {
/*  37:155 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  38:    */     }
/*  39:157 */     Constructor ctor = getMatchingAccessibleConstructor(cls, parameterTypes);
/*  40:158 */     if (null == ctor) {
/*  41:159 */       throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName());
/*  42:    */     }
/*  43:162 */     return ctor.newInstance(args);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static Object invokeExactConstructor(Class cls, Object arg)
/*  47:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*  48:    */   {
/*  49:188 */     return invokeExactConstructor(cls, new Object[] { arg });
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Object invokeExactConstructor(Class cls, Object[] args)
/*  53:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*  54:    */   {
/*  55:217 */     if (null == args) {
/*  56:218 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  57:    */     }
/*  58:220 */     int arguments = args.length;
/*  59:221 */     Class[] parameterTypes = new Class[arguments];
/*  60:222 */     for (int i = 0; i < arguments; i++) {
/*  61:223 */       parameterTypes[i] = args[i].getClass();
/*  62:    */     }
/*  63:225 */     return invokeExactConstructor(cls, args, parameterTypes);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static Object invokeExactConstructor(Class cls, Object[] args, Class[] parameterTypes)
/*  67:    */     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException
/*  68:    */   {
/*  69:249 */     if (args == null) {
/*  70:250 */       args = ArrayUtils.EMPTY_OBJECT_ARRAY;
/*  71:    */     }
/*  72:252 */     if (parameterTypes == null) {
/*  73:253 */       parameterTypes = ArrayUtils.EMPTY_CLASS_ARRAY;
/*  74:    */     }
/*  75:255 */     Constructor ctor = getAccessibleConstructor(cls, parameterTypes);
/*  76:256 */     if (null == ctor) {
/*  77:257 */       throw new NoSuchMethodException("No such accessible constructor on object: " + cls.getName());
/*  78:    */     }
/*  79:260 */     return ctor.newInstance(args);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static Constructor getAccessibleConstructor(Class cls, Class parameterType)
/*  83:    */   {
/*  84:273 */     return getAccessibleConstructor(cls, new Class[] { parameterType });
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static Constructor getAccessibleConstructor(Class cls, Class[] parameterTypes)
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:287 */       return getAccessibleConstructor(cls.getConstructor(parameterTypes));
/*  92:    */     }
/*  93:    */     catch (NoSuchMethodException e) {}
/*  94:289 */     return null;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static Constructor getAccessibleConstructor(Constructor ctor)
/*  98:    */   {
/*  99:300 */     return (MemberUtils.isAccessible(ctor)) && (Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) ? ctor : null;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static Constructor getMatchingAccessibleConstructor(Class cls, Class[] parameterTypes)
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:325 */       Constructor ctor = cls.getConstructor(parameterTypes);
/* 107:326 */       MemberUtils.setAccessibleWorkaround(ctor);
/* 108:327 */       return ctor;
/* 109:    */     }
/* 110:    */     catch (NoSuchMethodException e)
/* 111:    */     {
/* 112:330 */       Constructor result = null;
/* 113:    */       
/* 114:332 */       Constructor[] ctors = cls.getConstructors();
/* 115:334 */       for (int i = 0; i < ctors.length; i++) {
/* 116:337 */         if (ClassUtils.isAssignable(parameterTypes, ctors[i].getParameterTypes(), true))
/* 117:    */         {
/* 118:339 */           Constructor ctor = getAccessibleConstructor(ctors[i]);
/* 119:340 */           if (ctor != null)
/* 120:    */           {
/* 121:341 */             MemberUtils.setAccessibleWorkaround(ctor);
/* 122:342 */             if ((result == null) || (MemberUtils.compareParameterTypes(ctor.getParameterTypes(), result.getParameterTypes(), parameterTypes) < 0)) {
/* 123:345 */               result = ctor;
/* 124:    */             }
/* 125:    */           }
/* 126:    */         }
/* 127:    */       }
/* 128:350 */       return result;
/* 129:    */     }
/* 130:    */   }
/* 131:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.reflect.ConstructorUtils
 * JD-Core Version:    0.7.0.1
 */