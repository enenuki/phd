/*  1:   */ package org.hibernate.annotations.common.reflection;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Field;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.lang.reflect.Modifier;
/*  6:   */ import java.lang.reflect.Type;
/*  7:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;
/*  8:   */ 
/*  9:   */ public class ReflectionUtil
/* 10:   */ {
/* 11:   */   public static boolean isProperty(Method m, Type boundType, Filter filter)
/* 12:   */   {
/* 13:39 */     return (isPropertyType(boundType)) && (!m.isSynthetic()) && (!m.isBridge()) && ((filter.returnStatic()) || (!Modifier.isStatic(m.getModifiers()))) && (m.getParameterTypes().length == 0) && ((m.getName().startsWith("get")) || (m.getName().startsWith("is")));
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static boolean isProperty(Field f, Type boundType, Filter filter)
/* 17:   */   {
/* 18:49 */     return ((filter.returnStatic()) || (!Modifier.isStatic(f.getModifiers()))) && ((filter.returnTransient()) || (!Modifier.isTransient(f.getModifiers()))) && (!f.isSynthetic()) && (isPropertyType(boundType));
/* 19:   */   }
/* 20:   */   
/* 21:   */   private static boolean isPropertyType(Type type)
/* 22:   */   {
/* 23:57 */     return !TypeUtils.isVoid(type);
/* 24:   */   }
/* 25:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.ReflectionUtil
 * JD-Core Version:    0.7.0.1
 */