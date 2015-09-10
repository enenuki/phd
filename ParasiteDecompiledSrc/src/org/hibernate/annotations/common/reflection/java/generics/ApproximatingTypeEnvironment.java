/*   1:    */ package org.hibernate.annotations.common.reflection.java.generics;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.lang.reflect.GenericArrayType;
/*   5:    */ import java.lang.reflect.ParameterizedType;
/*   6:    */ import java.lang.reflect.Type;
/*   7:    */ import java.lang.reflect.TypeVariable;
/*   8:    */ import java.lang.reflect.WildcardType;
/*   9:    */ 
/*  10:    */ class ApproximatingTypeEnvironment
/*  11:    */   implements TypeEnvironment
/*  12:    */ {
/*  13:    */   public Type bind(Type type)
/*  14:    */   {
/*  15: 66 */     Type result = fineApproximation(type);
/*  16: 67 */     assert (TypeUtils.isResolved(result));
/*  17: 68 */     return result;
/*  18:    */   }
/*  19:    */   
/*  20:    */   private Type fineApproximation(Type type)
/*  21:    */   {
/*  22: 72 */     (Type)new TypeSwitch()
/*  23:    */     {
/*  24:    */       public Type caseWildcardType(WildcardType wildcardType)
/*  25:    */       {
/*  26: 74 */         return wildcardType;
/*  27:    */       }
/*  28:    */       
/*  29:    */       public Type caseClass(Class classType)
/*  30:    */       {
/*  31: 79 */         return classType;
/*  32:    */       }
/*  33:    */       
/*  34:    */       public Type caseGenericArrayType(GenericArrayType genericArrayType)
/*  35:    */       {
/*  36: 84 */         if (TypeUtils.isResolved(genericArrayType)) {
/*  37: 85 */           return genericArrayType;
/*  38:    */         }
/*  39: 87 */         Type componentType = genericArrayType.getGenericComponentType();
/*  40: 88 */         Type boundComponentType = ApproximatingTypeEnvironment.this.bind(componentType);
/*  41: 89 */         if ((boundComponentType instanceof Class)) {
/*  42: 90 */           return Array.newInstance((Class)boundComponentType, 0).getClass();
/*  43:    */         }
/*  44: 94 */         return [Ljava.lang.Object.class;
/*  45:    */       }
/*  46:    */       
/*  47:    */       public Type caseParameterizedType(ParameterizedType parameterizedType)
/*  48:    */       {
/*  49: 99 */         if (TypeUtils.isResolved(parameterizedType)) {
/*  50:100 */           return parameterizedType;
/*  51:    */         }
/*  52:103 */         if (!TypeUtils.isCollection(parameterizedType)) {
/*  53:104 */           return Object.class;
/*  54:    */         }
/*  55:107 */         Type[] typeArguments = parameterizedType.getActualTypeArguments();
/*  56:108 */         Type[] approximatedTypeArguments = new Type[typeArguments.length];
/*  57:109 */         for (int i = 0; i < typeArguments.length; i++) {
/*  58:110 */           approximatedTypeArguments[i] = ApproximatingTypeEnvironment.this.coarseApproximation(typeArguments[i]);
/*  59:    */         }
/*  60:113 */         return TypeFactory.createParameterizedType(ApproximatingTypeEnvironment.this.bind(parameterizedType.getRawType()), approximatedTypeArguments, parameterizedType.getOwnerType());
/*  61:    */       }
/*  62:    */       
/*  63:    */       public Type defaultCase(Type t)
/*  64:    */       {
/*  65:122 */         return ApproximatingTypeEnvironment.this.coarseApproximation(t);
/*  66:    */       }
/*  67:122 */     }.doSwitch(type);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private Type coarseApproximation(Type type)
/*  71:    */   {
/*  72:128 */     Type result = (Type)new TypeSwitch()
/*  73:    */     {
/*  74:    */       public Type caseWildcardType(WildcardType wildcardType)
/*  75:    */       {
/*  76:130 */         return approximateTo(wildcardType.getUpperBounds());
/*  77:    */       }
/*  78:    */       
/*  79:    */       public Type caseGenericArrayType(GenericArrayType genericArrayType)
/*  80:    */       {
/*  81:135 */         if (TypeUtils.isResolved(genericArrayType)) {
/*  82:136 */           return genericArrayType;
/*  83:    */         }
/*  84:138 */         return [Ljava.lang.Object.class;
/*  85:    */       }
/*  86:    */       
/*  87:    */       public Type caseParameterizedType(ParameterizedType parameterizedType)
/*  88:    */       {
/*  89:143 */         if (TypeUtils.isResolved(parameterizedType)) {
/*  90:144 */           return parameterizedType;
/*  91:    */         }
/*  92:146 */         return Object.class;
/*  93:    */       }
/*  94:    */       
/*  95:    */       public Type caseTypeVariable(TypeVariable typeVariable)
/*  96:    */       {
/*  97:151 */         return approximateTo(typeVariable.getBounds());
/*  98:    */       }
/*  99:    */       
/* 100:    */       private Type approximateTo(Type[] bounds)
/* 101:    */       {
/* 102:155 */         if (bounds.length != 1) {
/* 103:156 */           return Object.class;
/* 104:    */         }
/* 105:158 */         return ApproximatingTypeEnvironment.this.coarseApproximation(bounds[0]);
/* 106:    */       }
/* 107:    */       
/* 108:    */       public Type defaultCase(Type t)
/* 109:    */       {
/* 110:163 */         return t;
/* 111:    */       }
/* 112:163 */     }.doSwitch(type);
/* 113:    */     
/* 114:    */ 
/* 115:166 */     assert (TypeUtils.isResolved(result));
/* 116:167 */     return result;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String toString()
/* 120:    */   {
/* 121:172 */     return "approximated_types";
/* 122:    */   }
/* 123:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.ApproximatingTypeEnvironment
 * JD-Core Version:    0.7.0.1
 */