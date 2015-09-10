/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.ParameterizedType;
/*  4:   */ import java.lang.reflect.Type;
/*  5:   */ import java.util.Collection;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.SortedMap;
/*  8:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  9:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/* 10:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeSwitch;
/* 11:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;
/* 12:   */ 
/* 13:   */ class JavaXCollectionType
/* 14:   */   extends JavaXType
/* 15:   */ {
/* 16:   */   public JavaXCollectionType(Type type, TypeEnvironment context, JavaReflectionManager factory)
/* 17:   */   {
/* 18:47 */     super(type, context, factory);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isArray()
/* 22:   */   {
/* 23:51 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public boolean isCollection()
/* 27:   */   {
/* 28:55 */     return true;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public XClass getElementClass()
/* 32:   */   {
/* 33:59 */     (XClass)new TypeSwitch()
/* 34:   */     {
/* 35:   */       public XClass caseParameterizedType(ParameterizedType parameterizedType)
/* 36:   */       {
/* 37:62 */         Type[] args = parameterizedType.getActualTypeArguments();
/* 38:   */         
/* 39:64 */         Class<? extends Collection> collectionClass = JavaXCollectionType.this.getCollectionClass();
/* 40:   */         Type componentType;
/* 41:   */         Type componentType;
/* 42:65 */         if ((Map.class.isAssignableFrom(collectionClass)) || (SortedMap.class.isAssignableFrom(collectionClass))) {
/* 43:67 */           componentType = args[1];
/* 44:   */         } else {
/* 45:70 */           componentType = args[0];
/* 46:   */         }
/* 47:72 */         return JavaXCollectionType.this.toXClass(componentType);
/* 48:   */       }
/* 49:72 */     }.doSwitch(approximate());
/* 50:   */   }
/* 51:   */   
/* 52:   */   public XClass getMapKey()
/* 53:   */   {
/* 54:78 */     (XClass)new TypeSwitch()
/* 55:   */     {
/* 56:   */       public XClass caseParameterizedType(ParameterizedType parameterizedType)
/* 57:   */       {
/* 58:81 */         if (Map.class.isAssignableFrom(JavaXCollectionType.this.getCollectionClass())) {
/* 59:82 */           return JavaXCollectionType.this.toXClass(parameterizedType.getActualTypeArguments()[0]);
/* 60:   */         }
/* 61:84 */         return null;
/* 62:   */       }
/* 63:84 */     }.doSwitch(approximate());
/* 64:   */   }
/* 65:   */   
/* 66:   */   public XClass getClassOrElementClass()
/* 67:   */   {
/* 68:90 */     return toXClass(approximate());
/* 69:   */   }
/* 70:   */   
/* 71:   */   public Class<? extends Collection> getCollectionClass()
/* 72:   */   {
/* 73:94 */     return TypeUtils.getCollectionClass(approximate());
/* 74:   */   }
/* 75:   */   
/* 76:   */   public XClass getType()
/* 77:   */   {
/* 78:98 */     return toXClass(approximate());
/* 79:   */   }
/* 80:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXCollectionType
 * JD-Core Version:    0.7.0.1
 */