/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.beans.Introspector;
/*  4:   */ import java.lang.reflect.Field;
/*  5:   */ import java.lang.reflect.Member;
/*  6:   */ import java.lang.reflect.Method;
/*  7:   */ import java.lang.reflect.Type;
/*  8:   */ import org.hibernate.annotations.common.reflection.XProperty;
/*  9:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/* 10:   */ 
/* 11:   */ class JavaXProperty
/* 12:   */   extends JavaXMember
/* 13:   */   implements XProperty
/* 14:   */ {
/* 15:   */   static JavaXProperty create(Member member, TypeEnvironment context, JavaReflectionManager factory)
/* 16:   */   {
/* 17:42 */     Type propType = typeOf(member, context);
/* 18:43 */     JavaXType xType = factory.toXType(context, propType);
/* 19:44 */     return new JavaXProperty(member, propType, context, factory, xType);
/* 20:   */   }
/* 21:   */   
/* 22:   */   private JavaXProperty(Member member, Type type, TypeEnvironment env, JavaReflectionManager factory, JavaXType xType)
/* 23:   */   {
/* 24:48 */     super(member, type, env, factory, xType);
/* 25:49 */     assert (((member instanceof Field)) || ((member instanceof Method)));
/* 26:   */   }
/* 27:   */   
/* 28:   */   public String getName()
/* 29:   */   {
/* 30:53 */     String fullName = getMember().getName();
/* 31:54 */     if ((getMember() instanceof Method))
/* 32:   */     {
/* 33:55 */       if (fullName.startsWith("get")) {
/* 34:56 */         return Introspector.decapitalize(fullName.substring("get".length()));
/* 35:   */       }
/* 36:58 */       if (fullName.startsWith("is")) {
/* 37:59 */         return Introspector.decapitalize(fullName.substring("is".length()));
/* 38:   */       }
/* 39:61 */       throw new RuntimeException("Method " + fullName + " is not a property getter");
/* 40:   */     }
/* 41:64 */     return fullName;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Object invoke(Object target, Object... parameters)
/* 45:   */   {
/* 46:69 */     if (parameters.length != 0) {
/* 47:70 */       throw new IllegalArgumentException("An XProperty cannot have invoke parameters");
/* 48:   */     }
/* 49:   */     try
/* 50:   */     {
/* 51:73 */       if ((getMember() instanceof Method)) {
/* 52:74 */         return ((Method)getMember()).invoke(target, new Object[0]);
/* 53:   */       }
/* 54:77 */       return ((Field)getMember()).get(target);
/* 55:   */     }
/* 56:   */     catch (NullPointerException e)
/* 57:   */     {
/* 58:81 */       throw new IllegalArgumentException("Invoking " + getName() + " on a  null object", e);
/* 59:   */     }
/* 60:   */     catch (IllegalArgumentException e)
/* 61:   */     {
/* 62:84 */       throw new IllegalArgumentException("Invoking " + getName() + " with wrong parameters", e);
/* 63:   */     }
/* 64:   */     catch (Exception e)
/* 65:   */     {
/* 66:87 */       throw new IllegalStateException("Unable to invoke " + getName(), e);
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String toString()
/* 71:   */   {
/* 72:93 */     return getName();
/* 73:   */   }
/* 74:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXProperty
 * JD-Core Version:    0.7.0.1
 */