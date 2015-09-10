/*  1:   */ package org.hibernate.metamodel.source.annotations;
/*  2:   */ 
/*  3:   */ import java.beans.Introspector;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ import java.lang.reflect.Member;
/*  6:   */ import java.lang.reflect.Method;
/*  7:   */ import java.lang.reflect.Modifier;
/*  8:   */ 
/*  9:   */ public class ReflectionHelper
/* 10:   */ {
/* 11:   */   public static String getPropertyName(Member member)
/* 12:   */   {
/* 13:51 */     String name = null;
/* 14:53 */     if ((member instanceof Field)) {
/* 15:54 */       name = member.getName();
/* 16:   */     }
/* 17:57 */     if ((member instanceof Method))
/* 18:   */     {
/* 19:58 */       String methodName = member.getName();
/* 20:59 */       if (methodName.startsWith("is")) {
/* 21:60 */         name = Introspector.decapitalize(methodName.substring(2));
/* 22:62 */       } else if (methodName.startsWith("has")) {
/* 23:63 */         name = Introspector.decapitalize(methodName.substring(3));
/* 24:65 */       } else if (methodName.startsWith("get")) {
/* 25:66 */         name = Introspector.decapitalize(methodName.substring(3));
/* 26:   */       }
/* 27:   */     }
/* 28:69 */     return name;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static boolean isProperty(Member m)
/* 32:   */   {
/* 33:73 */     if ((m instanceof Method))
/* 34:   */     {
/* 35:74 */       Method method = (Method)m;
/* 36:75 */       return (!method.isSynthetic()) && (!method.isBridge()) && (!Modifier.isStatic(method.getModifiers())) && (method.getParameterTypes().length == 0) && ((method.getName().startsWith("get")) || (method.getName().startsWith("is")));
/* 37:   */     }
/* 38:82 */     return (!Modifier.isTransient(m.getModifiers())) && (!m.isSynthetic());
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.ReflectionHelper
 * JD-Core Version:    0.7.0.1
 */