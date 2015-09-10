/*   1:    */ package org.hibernate.annotations.common.util;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.lang.reflect.Member;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.lang.reflect.Modifier;
/*   7:    */ import org.hibernate.annotations.common.AssertionFailure;
/*   8:    */ 
/*   9:    */ public final class ReflectHelper
/*  10:    */ {
/*  11: 39 */   public static final Class[] NO_PARAM_SIGNATURE = new Class[0];
/*  12: 40 */   public static final Object[] NO_PARAMS = new Object[0];
/*  13: 42 */   public static final Class[] SINGLE_OBJECT_PARAM_SIGNATURE = { Object.class };
/*  14:    */   private static final Method OBJECT_EQUALS;
/*  15:    */   private static final Method OBJECT_HASHCODE;
/*  16:    */   
/*  17:    */   static
/*  18:    */   {
/*  19:    */     Method eq;
/*  20:    */     Method hash;
/*  21:    */     try
/*  22:    */     {
/*  23: 51 */       eq = extractEqualsMethod(Object.class);
/*  24: 52 */       hash = extractHashCodeMethod(Object.class);
/*  25:    */     }
/*  26:    */     catch (Exception e)
/*  27:    */     {
/*  28: 55 */       throw new AssertionFailure("Could not find Object.equals() or Object.hashCode()", e);
/*  29:    */     }
/*  30: 57 */     OBJECT_EQUALS = eq;
/*  31: 58 */     OBJECT_HASHCODE = hash;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Method extractEqualsMethod(Class clazz)
/*  35:    */     throws NoSuchMethodException
/*  36:    */   {
/*  37: 75 */     return clazz.getMethod("equals", SINGLE_OBJECT_PARAM_SIGNATURE);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static Method extractHashCodeMethod(Class clazz)
/*  41:    */     throws NoSuchMethodException
/*  42:    */   {
/*  43: 86 */     return clazz.getMethod("hashCode", NO_PARAM_SIGNATURE);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static boolean overridesEquals(Class clazz)
/*  47:    */   {
/*  48:    */     Method equals;
/*  49:    */     try
/*  50:    */     {
/*  51: 98 */       equals = extractEqualsMethod(clazz);
/*  52:    */     }
/*  53:    */     catch (NoSuchMethodException nsme)
/*  54:    */     {
/*  55:101 */       return false;
/*  56:    */     }
/*  57:103 */     return !OBJECT_EQUALS.equals(equals);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static boolean overridesHashCode(Class clazz)
/*  61:    */   {
/*  62:    */     Method hashCode;
/*  63:    */     try
/*  64:    */     {
/*  65:115 */       hashCode = extractHashCodeMethod(clazz);
/*  66:    */     }
/*  67:    */     catch (NoSuchMethodException nsme)
/*  68:    */     {
/*  69:118 */       return false;
/*  70:    */     }
/*  71:120 */     return !OBJECT_HASHCODE.equals(hashCode);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static Class classForName(String name, Class caller)
/*  75:    */     throws ClassNotFoundException
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:136 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*  80:137 */       if (contextClassLoader != null) {
/*  81:138 */         return contextClassLoader.loadClass(name);
/*  82:    */       }
/*  83:    */     }
/*  84:    */     catch (Throwable ignore) {}
/*  85:143 */     return Class.forName(name, true, caller.getClassLoader());
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static Class classForName(String name)
/*  89:    */     throws ClassNotFoundException
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:158 */       ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*  94:159 */       if (contextClassLoader != null) {
/*  95:160 */         return contextClassLoader.loadClass(name);
/*  96:    */       }
/*  97:    */     }
/*  98:    */     catch (Throwable ignore) {}
/*  99:165 */     return Class.forName(name);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static boolean isPublic(Class clazz, Member member)
/* 103:    */   {
/* 104:176 */     return (Modifier.isPublic(member.getModifiers())) && (Modifier.isPublic(clazz.getModifiers()));
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static Object getConstantValue(String name)
/* 108:    */   {
/* 109:    */     Class clazz;
/* 110:    */     try
/* 111:    */     {
/* 112:188 */       clazz = classForName(StringHelper.qualifier(name));
/* 113:    */     }
/* 114:    */     catch (Throwable t)
/* 115:    */     {
/* 116:191 */       return null;
/* 117:    */     }
/* 118:    */     try
/* 119:    */     {
/* 120:194 */       return clazz.getField(StringHelper.unqualify(name)).get(null);
/* 121:    */     }
/* 122:    */     catch (Throwable t) {}
/* 123:197 */     return null;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static boolean isAbstractClass(Class clazz)
/* 127:    */   {
/* 128:208 */     int modifier = clazz.getModifiers();
/* 129:209 */     return (Modifier.isAbstract(modifier)) || (Modifier.isInterface(modifier));
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static boolean isFinalClass(Class clazz)
/* 133:    */   {
/* 134:219 */     return Modifier.isFinal(clazz.getModifiers());
/* 135:    */   }
/* 136:    */   
/* 137:    */   public static Method getMethod(Class clazz, Method method)
/* 138:    */   {
/* 139:    */     try
/* 140:    */     {
/* 141:224 */       return clazz.getMethod(method.getName(), method.getParameterTypes());
/* 142:    */     }
/* 143:    */     catch (Exception e) {}
/* 144:227 */     return null;
/* 145:    */   }
/* 146:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.util.ReflectHelper
 * JD-Core Version:    0.7.0.1
 */