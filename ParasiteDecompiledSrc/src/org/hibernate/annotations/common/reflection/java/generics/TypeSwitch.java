/*  1:   */ package org.hibernate.annotations.common.reflection.java.generics;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.GenericArrayType;
/*  4:   */ import java.lang.reflect.ParameterizedType;
/*  5:   */ import java.lang.reflect.Type;
/*  6:   */ import java.lang.reflect.TypeVariable;
/*  7:   */ import java.lang.reflect.WildcardType;
/*  8:   */ 
/*  9:   */ public class TypeSwitch<T>
/* 10:   */ {
/* 11:   */   public final T doSwitch(Type type)
/* 12:   */   {
/* 13:41 */     if ((type instanceof Class)) {
/* 14:42 */       return caseClass((Class)type);
/* 15:   */     }
/* 16:44 */     if ((type instanceof GenericArrayType)) {
/* 17:45 */       return caseGenericArrayType((GenericArrayType)type);
/* 18:   */     }
/* 19:47 */     if ((type instanceof ParameterizedType)) {
/* 20:48 */       return caseParameterizedType((ParameterizedType)type);
/* 21:   */     }
/* 22:50 */     if ((type instanceof TypeVariable)) {
/* 23:51 */       return caseTypeVariable((TypeVariable)type);
/* 24:   */     }
/* 25:53 */     if ((type instanceof WildcardType)) {
/* 26:54 */       return caseWildcardType((WildcardType)type);
/* 27:   */     }
/* 28:56 */     return defaultCase(type);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public T caseWildcardType(WildcardType wildcardType)
/* 32:   */   {
/* 33:60 */     return defaultCase(wildcardType);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public T caseTypeVariable(TypeVariable typeVariable)
/* 37:   */   {
/* 38:64 */     return defaultCase(typeVariable);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public T caseClass(Class classType)
/* 42:   */   {
/* 43:68 */     return defaultCase(classType);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public T caseGenericArrayType(GenericArrayType genericArrayType)
/* 47:   */   {
/* 48:72 */     return defaultCase(genericArrayType);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public T caseParameterizedType(ParameterizedType parameterizedType)
/* 52:   */   {
/* 53:76 */     return defaultCase(parameterizedType);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public T defaultCase(Type t)
/* 57:   */   {
/* 58:80 */     return null;
/* 59:   */   }
/* 60:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.TypeSwitch
 * JD-Core Version:    0.7.0.1
 */