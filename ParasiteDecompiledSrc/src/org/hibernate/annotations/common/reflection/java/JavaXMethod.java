/*  1:   */ package org.hibernate.annotations.common.reflection.java;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Member;
/*  4:   */ import java.lang.reflect.Method;
/*  5:   */ import java.lang.reflect.Type;
/*  6:   */ import org.hibernate.annotations.common.reflection.XMethod;
/*  7:   */ import org.hibernate.annotations.common.reflection.java.generics.TypeEnvironment;
/*  8:   */ 
/*  9:   */ public class JavaXMethod
/* 10:   */   extends JavaXMember
/* 11:   */   implements XMethod
/* 12:   */ {
/* 13:   */   static JavaXMethod create(Member member, TypeEnvironment context, JavaReflectionManager factory)
/* 14:   */   {
/* 15:41 */     Type propType = typeOf(member, context);
/* 16:42 */     JavaXType xType = factory.toXType(context, propType);
/* 17:43 */     return new JavaXMethod(member, propType, context, factory, xType);
/* 18:   */   }
/* 19:   */   
/* 20:   */   private JavaXMethod(Member member, Type type, TypeEnvironment env, JavaReflectionManager factory, JavaXType xType)
/* 21:   */   {
/* 22:47 */     super(member, type, env, factory, xType);
/* 23:48 */     assert ((member instanceof Method));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getName()
/* 27:   */   {
/* 28:52 */     return getMember().getName();
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Object invoke(Object target, Object... parameters)
/* 32:   */   {
/* 33:   */     try
/* 34:   */     {
/* 35:57 */       return ((Method)getMember()).invoke(target, parameters);
/* 36:   */     }
/* 37:   */     catch (NullPointerException e)
/* 38:   */     {
/* 39:60 */       throw new IllegalArgumentException("Invoking " + getName() + " on a  null object", e);
/* 40:   */     }
/* 41:   */     catch (IllegalArgumentException e)
/* 42:   */     {
/* 43:63 */       throw new IllegalArgumentException("Invoking " + getName() + " with wrong parameters", e);
/* 44:   */     }
/* 45:   */     catch (Exception e)
/* 46:   */     {
/* 47:66 */       throw new IllegalStateException("Unable to invoke " + getName(), e);
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.JavaXMethod
 * JD-Core Version:    0.7.0.1
 */