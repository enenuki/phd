/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Type;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  6:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  7:   */ 
/*  8:   */ class JavaXSimpleType
/*  9:   */   extends JavaXType
/* 10:   */ {
/* 11:   */   public JavaXSimpleType(Type type, TypeEnvironment context, JavaReflectionManager factory)
/* 12:   */   {
/* 13:39 */     super(type, context, factory);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public boolean isArray()
/* 17:   */   {
/* 18:43 */     return false;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public boolean isCollection()
/* 22:   */   {
/* 23:47 */     return false;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public XClass getElementClass()
/* 27:   */   {
/* 28:51 */     return toXClass(approximate());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public XClass getClassOrElementClass()
/* 32:   */   {
/* 33:55 */     return getElementClass();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Class<? extends Collection> getCollectionClass()
/* 37:   */   {
/* 38:59 */     return null;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public XClass getType()
/* 42:   */   {
/* 43:63 */     return toXClass(approximate());
/* 44:   */   }
/* 45:   */   
/* 46:   */   public XClass getMapKey()
/* 47:   */   {
/* 48:67 */     return null;
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXSimpleType
 * JD-Core Version:    0.7.0.1
 */