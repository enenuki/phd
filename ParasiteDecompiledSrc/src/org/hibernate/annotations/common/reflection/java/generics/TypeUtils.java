/*   1:    */ package org.hibernate.annotations.common.reflection.java.generics;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.GenericArrayType;
/*   4:    */ import java.lang.reflect.ParameterizedType;
/*   5:    */ import java.lang.reflect.Type;
/*   6:    */ import java.lang.reflect.TypeVariable;
/*   7:    */ import java.lang.reflect.WildcardType;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.SortedMap;
/*  13:    */ import java.util.SortedSet;
/*  14:    */ 
/*  15:    */ public class TypeUtils
/*  16:    */ {
/*  17:    */   public static boolean isResolved(Type t)
/*  18:    */   {
/*  19: 39 */     ((Boolean)new TypeSwitch()
/*  20:    */     {
/*  21:    */       public Boolean caseClass(Class classType)
/*  22:    */       {
/*  23: 42 */         return Boolean.valueOf(true);
/*  24:    */       }
/*  25:    */       
/*  26:    */       public Boolean caseGenericArrayType(GenericArrayType genericArrayType)
/*  27:    */       {
/*  28: 47 */         return Boolean.valueOf(TypeUtils.isResolved(genericArrayType.getGenericComponentType()));
/*  29:    */       }
/*  30:    */       
/*  31:    */       public Boolean caseParameterizedType(ParameterizedType parameterizedType)
/*  32:    */       {
/*  33: 52 */         Type[] typeArgs = parameterizedType.getActualTypeArguments();
/*  34: 53 */         for (Type arg : typeArgs) {
/*  35: 54 */           if (!TypeUtils.isResolved(arg)) {
/*  36: 55 */             return Boolean.valueOf(false);
/*  37:    */           }
/*  38:    */         }
/*  39: 58 */         return Boolean.valueOf(TypeUtils.isResolved(parameterizedType.getRawType()));
/*  40:    */       }
/*  41:    */       
/*  42:    */       public Boolean caseTypeVariable(TypeVariable typeVariable)
/*  43:    */       {
/*  44: 63 */         return Boolean.valueOf(false);
/*  45:    */       }
/*  46:    */       
/*  47:    */       public Boolean caseWildcardType(WildcardType wildcardType)
/*  48:    */       {
/*  49: 68 */         return Boolean.valueOf((TypeUtils.areResolved(wildcardType.getUpperBounds()).booleanValue()) && (TypeUtils.areResolved(wildcardType.getLowerBounds()).booleanValue()));
/*  50:    */       }
/*  51: 68 */     }.doSwitch(t)).booleanValue();
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static Boolean areResolved(Type[] types)
/*  55:    */   {
/*  56: 74 */     for (Type t : types) {
/*  57: 75 */       if (!isResolved(t)) {
/*  58: 76 */         return Boolean.valueOf(false);
/*  59:    */       }
/*  60:    */     }
/*  61: 79 */     return Boolean.valueOf(true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Class<? extends Collection> getCollectionClass(Type type)
/*  65:    */   {
/*  66: 83 */     (Class)new TypeSwitch()
/*  67:    */     {
/*  68:    */       public Class<? extends Collection> caseClass(Class clazz)
/*  69:    */       {
/*  70: 86 */         return TypeUtils.isCollectionClass(clazz) ? clazz : null;
/*  71:    */       }
/*  72:    */       
/*  73:    */       public Class<? extends Collection> caseParameterizedType(ParameterizedType parameterizedType)
/*  74:    */       {
/*  75: 91 */         return TypeUtils.getCollectionClass(parameterizedType.getRawType());
/*  76:    */       }
/*  77:    */       
/*  78:    */       public Class<? extends Collection> caseWildcardType(WildcardType wildcardType)
/*  79:    */       {
/*  80: 96 */         Type[] upperBounds = wildcardType.getUpperBounds();
/*  81: 97 */         if (upperBounds.length == 0) {
/*  82: 98 */           return null;
/*  83:    */         }
/*  84:100 */         return TypeUtils.getCollectionClass(upperBounds[0]);
/*  85:    */       }
/*  86:    */       
/*  87:    */       public Class<? extends Collection> defaultCase(Type t)
/*  88:    */       {
/*  89:105 */         return null;
/*  90:    */       }
/*  91:105 */     }.doSwitch(type);
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static boolean isCollectionClass(Class<?> clazz)
/*  95:    */   {
/*  96:111 */     return (clazz == Collection.class) || (clazz == List.class) || (clazz == Set.class) || (clazz == Map.class) || (clazz == SortedSet.class) || (clazz == SortedMap.class);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static boolean isSimple(Type type)
/* 100:    */   {
/* 101:120 */     ((Boolean)new TypeSwitch()
/* 102:    */     {
/* 103:    */       public Boolean caseClass(Class clazz)
/* 104:    */       {
/* 105:123 */         return Boolean.valueOf((!clazz.isArray()) && (!TypeUtils.isCollectionClass(clazz)));
/* 106:    */       }
/* 107:    */       
/* 108:    */       public Boolean caseParameterizedType(ParameterizedType parameterizedType)
/* 109:    */       {
/* 110:128 */         return Boolean.valueOf(TypeUtils.isSimple(parameterizedType.getRawType()));
/* 111:    */       }
/* 112:    */       
/* 113:    */       public Boolean caseWildcardType(WildcardType wildcardType)
/* 114:    */       {
/* 115:133 */         return Boolean.valueOf((TypeUtils.areSimple(wildcardType.getUpperBounds()).booleanValue()) && (TypeUtils.areSimple(wildcardType.getLowerBounds()).booleanValue()));
/* 116:    */       }
/* 117:    */       
/* 118:    */       public Boolean defaultCase(Type t)
/* 119:    */       {
/* 120:138 */         return Boolean.valueOf(false);
/* 121:    */       }
/* 122:138 */     }.doSwitch(type)).booleanValue();
/* 123:    */   }
/* 124:    */   
/* 125:    */   private static Boolean areSimple(Type[] types)
/* 126:    */   {
/* 127:144 */     for (Type t : types) {
/* 128:145 */       if (!isSimple(t)) {
/* 129:146 */         return Boolean.valueOf(false);
/* 130:    */       }
/* 131:    */     }
/* 132:149 */     return Boolean.valueOf(true);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static boolean isVoid(Type type)
/* 136:    */   {
/* 137:153 */     return Void.TYPE.equals(type);
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static boolean isArray(Type t)
/* 141:    */   {
/* 142:157 */     ((Boolean)new TypeSwitch()
/* 143:    */     {
/* 144:    */       public Boolean caseClass(Class clazz)
/* 145:    */       {
/* 146:160 */         return Boolean.valueOf(clazz.isArray());
/* 147:    */       }
/* 148:    */       
/* 149:    */       public Boolean caseGenericArrayType(GenericArrayType genericArrayType)
/* 150:    */       {
/* 151:165 */         return Boolean.valueOf(TypeUtils.isSimple(genericArrayType.getGenericComponentType()));
/* 152:    */       }
/* 153:    */       
/* 154:    */       public Boolean defaultCase(Type type)
/* 155:    */       {
/* 156:170 */         return Boolean.valueOf(false);
/* 157:    */       }
/* 158:170 */     }.doSwitch(t)).booleanValue();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static boolean isCollection(Type t)
/* 162:    */   {
/* 163:176 */     return getCollectionClass(t) != null;
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.TypeUtils
 * JD-Core Version:    0.7.0.1
 */