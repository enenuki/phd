/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Array;
/*  4:   */ import java.lang.reflect.GenericArrayType;
/*  5:   */ import java.lang.reflect.Type;
/*  6:   */ import java.util.Collection;
/*  7:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  8:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  9:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeSwitch;
/* 10:   */ 
/* 11:   */ class JavaXArrayType
/* 12:   */   extends JavaXType
/* 13:   */ {
/* 14:   */   public JavaXArrayType(Type type, TypeEnvironment context, JavaReflectionManager factory)
/* 15:   */   {
/* 16:42 */     super(type, context, factory);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean isArray()
/* 20:   */   {
/* 21:46 */     return true;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public boolean isCollection()
/* 25:   */   {
/* 26:50 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public XClass getElementClass()
/* 30:   */   {
/* 31:54 */     return toXClass(getElementType());
/* 32:   */   }
/* 33:   */   
/* 34:   */   private Type getElementType()
/* 35:   */   {
/* 36:59 */     (Type)new TypeSwitch()
/* 37:   */     {
/* 38:   */       public Type caseClass(Class classType)
/* 39:   */       {
/* 40:62 */         return classType.getComponentType();
/* 41:   */       }
/* 42:   */       
/* 43:   */       public Type caseGenericArrayType(GenericArrayType genericArrayType)
/* 44:   */       {
/* 45:67 */         return genericArrayType.getGenericComponentType();
/* 46:   */       }
/* 47:   */       
/* 48:   */       public Type defaultCase(Type t)
/* 49:   */       {
/* 50:72 */         throw new IllegalArgumentException(t + " is not an array type");
/* 51:   */       }
/* 52:72 */     }.doSwitch(approximate());
/* 53:   */   }
/* 54:   */   
/* 55:   */   public XClass getClassOrElementClass()
/* 56:   */   {
/* 57:78 */     return getElementClass();
/* 58:   */   }
/* 59:   */   
/* 60:   */   public Class<? extends Collection> getCollectionClass()
/* 61:   */   {
/* 62:82 */     return null;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public XClass getMapKey()
/* 66:   */   {
/* 67:86 */     return null;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public XClass getType()
/* 71:   */   {
/* 72:90 */     Type boundType = getElementType();
/* 73:91 */     if ((boundType instanceof Class)) {
/* 74:92 */       boundType = arrayTypeOf((Class)boundType);
/* 75:   */     }
/* 76:94 */     return toXClass(boundType);
/* 77:   */   }
/* 78:   */   
/* 79:   */   private Class<? extends Object> arrayTypeOf(Class componentType)
/* 80:   */   {
/* 81:98 */     return Array.newInstance(componentType, 0).getClass();
/* 82:   */   }
/* 83:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXArrayType
 * JD-Core Version:    0.7.0.1
 */