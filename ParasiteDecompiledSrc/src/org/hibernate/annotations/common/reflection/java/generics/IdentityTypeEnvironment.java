/*  1:   */ package org.hibernate.annotations.common.reflection.java.generics;
/*  2:   */ 
/*  3:   */ import java.lang.reflect.Type;
/*  4:   */ 
/*  5:   */ public class IdentityTypeEnvironment
/*  6:   */   implements TypeEnvironment
/*  7:   */ {
/*  8:36 */   public static final TypeEnvironment INSTANCE = new IdentityTypeEnvironment();
/*  9:   */   
/* 10:   */   public Type bind(Type type)
/* 11:   */   {
/* 12:42 */     return type;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String toString()
/* 16:   */   {
/* 17:46 */     return "{}";
/* 18:   */   }
/* 19:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.annotations.common.reflection.java.generics.IdentityTypeEnvironment
 * JD-Core Version:    0.7.0.1
 */