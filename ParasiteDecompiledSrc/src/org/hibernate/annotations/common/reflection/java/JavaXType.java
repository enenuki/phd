/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Type;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.hibernate.annotations.common.reflection.XClass;
/*  6:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  7:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeUtils;
/*  8:   */ 
/*  9:   */ abstract class JavaXType
/* 10:   */ {
/* 11:   */   private final TypeEnvironment context;
/* 12:   */   private final JavaReflectionManager factory;
/* 13:   */   private final Type approximatedType;
/* 14:   */   private final Type boundType;
/* 15:   */   
/* 16:   */   protected JavaXType(Type unboundType, TypeEnvironment context, JavaReflectionManager factory)
/* 17:   */   {
/* 18:49 */     this.context = context;
/* 19:50 */     this.factory = factory;
/* 20:51 */     this.boundType = context.bind(unboundType);
/* 21:52 */     this.approximatedType = factory.toApproximatingEnvironment(context).bind(unboundType);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public abstract boolean isArray();
/* 25:   */   
/* 26:   */   public abstract boolean isCollection();
/* 27:   */   
/* 28:   */   public abstract XClass getElementClass();
/* 29:   */   
/* 30:   */   public abstract XClass getClassOrElementClass();
/* 31:   */   
/* 32:   */   public abstract Class<? extends Collection> getCollectionClass();
/* 33:   */   
/* 34:   */   public abstract XClass getMapKey();
/* 35:   */   
/* 36:   */   public abstract XClass getType();
/* 37:   */   
/* 38:   */   public boolean isResolved()
/* 39:   */   {
/* 40:70 */     return TypeUtils.isResolved(this.boundType);
/* 41:   */   }
/* 42:   */   
/* 43:   */   protected Type approximate()
/* 44:   */   {
/* 45:74 */     return this.approximatedType;
/* 46:   */   }
/* 47:   */   
/* 48:   */   protected XClass toXClass(Type type)
/* 49:   */   {
/* 50:78 */     return this.factory.toXClass(type, this.context);
/* 51:   */   }
/* 52:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXType
 * JD-Core Version:    0.7.0.1
 */