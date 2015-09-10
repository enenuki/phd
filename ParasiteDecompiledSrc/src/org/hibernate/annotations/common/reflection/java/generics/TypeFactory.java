/*   1:    */ package org.hibernate.annotations.common.reflection.java.generics;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Array;
/*   4:    */ import java.lang.reflect.GenericArrayType;
/*   5:    */ import java.lang.reflect.ParameterizedType;
/*   6:    */ import java.lang.reflect.Type;
/*   7:    */ import java.util.Arrays;
/*   8:    */ 
/*   9:    */ class TypeFactory
/*  10:    */ {
/*  11:    */   static ParameterizedType createParameterizedType(final Type rawType, Type[] substTypeArgs, final Type ownerType)
/*  12:    */   {
/*  13: 45 */     new ParameterizedType()
/*  14:    */     {
/*  15:    */       public Type[] getActualTypeArguments()
/*  16:    */       {
/*  17: 48 */         return this.val$substTypeArgs;
/*  18:    */       }
/*  19:    */       
/*  20:    */       public Type getRawType()
/*  21:    */       {
/*  22: 52 */         return rawType;
/*  23:    */       }
/*  24:    */       
/*  25:    */       public Type getOwnerType()
/*  26:    */       {
/*  27: 56 */         return ownerType;
/*  28:    */       }
/*  29:    */       
/*  30:    */       public boolean equals(Object obj)
/*  31:    */       {
/*  32: 61 */         if (!(obj instanceof ParameterizedType)) {
/*  33: 62 */           return false;
/*  34:    */         }
/*  35: 64 */         ParameterizedType other = (ParameterizedType)obj;
/*  36: 65 */         return (Arrays.equals(getActualTypeArguments(), other.getActualTypeArguments())) && (TypeFactory.safeEquals(getRawType(), other.getRawType())) && (TypeFactory.safeEquals(getOwnerType(), other.getOwnerType()));
/*  37:    */       }
/*  38:    */       
/*  39:    */       public int hashCode()
/*  40:    */       {
/*  41: 73 */         return TypeFactory.safeHashCode(getActualTypeArguments()) ^ TypeFactory.safeHashCode(getRawType()) ^ TypeFactory.safeHashCode(getOwnerType());
/*  42:    */       }
/*  43:    */     };
/*  44:    */   }
/*  45:    */   
/*  46:    */   static Type createArrayType(Type componentType)
/*  47:    */   {
/*  48: 81 */     if ((componentType instanceof Class)) {
/*  49: 82 */       return Array.newInstance((Class)componentType, 0).getClass();
/*  50:    */     }
/*  51: 84 */     return createGenericArrayType(componentType);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static GenericArrayType createGenericArrayType(Type componentType)
/*  55:    */   {
/*  56: 88 */     new GenericArrayType()
/*  57:    */     {
/*  58:    */       public Type getGenericComponentType()
/*  59:    */       {
/*  60: 91 */         return this.val$componentType;
/*  61:    */       }
/*  62:    */       
/*  63:    */       public boolean equals(Object obj)
/*  64:    */       {
/*  65: 96 */         if (!(obj instanceof GenericArrayType)) {
/*  66: 97 */           return false;
/*  67:    */         }
/*  68: 99 */         GenericArrayType other = (GenericArrayType)obj;
/*  69:100 */         return TypeFactory.safeEquals(getGenericComponentType(), other.getGenericComponentType());
/*  70:    */       }
/*  71:    */       
/*  72:    */       public int hashCode()
/*  73:    */       {
/*  74:105 */         return TypeFactory.safeHashCode(getGenericComponentType());
/*  75:    */       }
/*  76:    */     };
/*  77:    */   }
/*  78:    */   
/*  79:    */   private static boolean safeEquals(Type t1, Type t2)
/*  80:    */   {
/*  81:111 */     if (t1 == null) {
/*  82:112 */       return t2 == null;
/*  83:    */     }
/*  84:114 */     return t1.equals(t2);
/*  85:    */   }
/*  86:    */   
/*  87:    */   private static int safeHashCode(Object o)
/*  88:    */   {
/*  89:118 */     if (o == null) {
/*  90:119 */       return 1;
/*  91:    */     }
/*  92:121 */     return o.hashCode();
/*  93:    */   }
/*  94:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.TypeFactory
 * JD-Core Version:    0.7.0.1
 */