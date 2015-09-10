/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import org.hibernate.TypeHelper;
/*   6:    */ import org.hibernate.type.BasicType;
/*   7:    */ import org.hibernate.type.Type;
/*   8:    */ import org.hibernate.type.TypeFactory;
/*   9:    */ import org.hibernate.type.TypeResolver;
/*  10:    */ import org.hibernate.usertype.CompositeUserType;
/*  11:    */ 
/*  12:    */ public class TypeLocatorImpl
/*  13:    */   implements TypeHelper, Serializable
/*  14:    */ {
/*  15:    */   private final TypeResolver typeResolver;
/*  16:    */   
/*  17:    */   public TypeLocatorImpl(TypeResolver typeResolver)
/*  18:    */   {
/*  19: 45 */     this.typeResolver = typeResolver;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public BasicType basic(String name)
/*  23:    */   {
/*  24: 52 */     return this.typeResolver.basic(name);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public BasicType basic(Class javaType)
/*  28:    */   {
/*  29: 59 */     BasicType type = this.typeResolver.basic(javaType.getName());
/*  30: 60 */     if (type == null)
/*  31:    */     {
/*  32: 61 */       Class variant = resolvePrimitiveOrPrimitiveWrapperVariantJavaType(javaType);
/*  33: 62 */       if (variant != null) {
/*  34: 63 */         type = this.typeResolver.basic(variant.getName());
/*  35:    */       }
/*  36:    */     }
/*  37: 66 */     return type;
/*  38:    */   }
/*  39:    */   
/*  40:    */   private Class resolvePrimitiveOrPrimitiveWrapperVariantJavaType(Class javaType)
/*  41:    */   {
/*  42: 71 */     if (Boolean.TYPE.equals(javaType)) {
/*  43: 72 */       return Boolean.class;
/*  44:    */     }
/*  45: 74 */     if (Boolean.class.equals(javaType)) {
/*  46: 75 */       return Boolean.TYPE;
/*  47:    */     }
/*  48: 79 */     if (Character.TYPE.equals(javaType)) {
/*  49: 80 */       return Character.class;
/*  50:    */     }
/*  51: 82 */     if (Character.class.equals(javaType)) {
/*  52: 83 */       return Character.TYPE;
/*  53:    */     }
/*  54: 87 */     if (Byte.TYPE.equals(javaType)) {
/*  55: 88 */       return Byte.class;
/*  56:    */     }
/*  57: 90 */     if (Byte.class.equals(javaType)) {
/*  58: 91 */       return Byte.TYPE;
/*  59:    */     }
/*  60: 95 */     if (Short.TYPE.equals(javaType)) {
/*  61: 96 */       return Short.class;
/*  62:    */     }
/*  63: 98 */     if (Short.class.equals(javaType)) {
/*  64: 99 */       return Short.TYPE;
/*  65:    */     }
/*  66:103 */     if (Integer.TYPE.equals(javaType)) {
/*  67:104 */       return Integer.class;
/*  68:    */     }
/*  69:106 */     if (Integer.class.equals(javaType)) {
/*  70:107 */       return Integer.TYPE;
/*  71:    */     }
/*  72:111 */     if (Long.TYPE.equals(javaType)) {
/*  73:112 */       return Long.class;
/*  74:    */     }
/*  75:114 */     if (Long.class.equals(javaType)) {
/*  76:115 */       return Long.TYPE;
/*  77:    */     }
/*  78:119 */     if (Float.TYPE.equals(javaType)) {
/*  79:120 */       return Float.class;
/*  80:    */     }
/*  81:122 */     if (Float.class.equals(javaType)) {
/*  82:123 */       return Float.TYPE;
/*  83:    */     }
/*  84:127 */     if (Double.TYPE.equals(javaType)) {
/*  85:128 */       return Double.class;
/*  86:    */     }
/*  87:130 */     if (Double.class.equals(javaType)) {
/*  88:131 */       return Double.TYPE;
/*  89:    */     }
/*  90:134 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Type heuristicType(String name)
/*  94:    */   {
/*  95:141 */     return this.typeResolver.heuristicType(name);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Type entity(Class entityClass)
/*  99:    */   {
/* 100:148 */     return entity(entityClass.getName());
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Type entity(String entityName)
/* 104:    */   {
/* 105:155 */     return this.typeResolver.getTypeFactory().manyToOne(entityName);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Type custom(Class userTypeClass)
/* 109:    */   {
/* 110:163 */     return custom(userTypeClass, null);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Type custom(Class userTypeClass, Properties parameters)
/* 114:    */   {
/* 115:171 */     if (CompositeUserType.class.isAssignableFrom(userTypeClass)) {
/* 116:172 */       return this.typeResolver.getTypeFactory().customComponent(userTypeClass, parameters);
/* 117:    */     }
/* 118:175 */     return this.typeResolver.getTypeFactory().custom(userTypeClass, parameters);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Type any(Type metaType, Type identifierType)
/* 122:    */   {
/* 123:183 */     return this.typeResolver.getTypeFactory().any(metaType, identifierType);
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.TypeLocatorImpl
 * JD-Core Version:    0.7.0.1
 */