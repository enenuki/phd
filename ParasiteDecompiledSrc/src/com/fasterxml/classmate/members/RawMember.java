/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.ResolvedType;
/*  4:   */ import java.lang.annotation.Annotation;
/*  5:   */ import java.lang.reflect.AnnotatedElement;
/*  6:   */ import java.lang.reflect.Member;
/*  7:   */ import java.lang.reflect.Modifier;
/*  8:   */ 
/*  9:   */ public abstract class RawMember
/* 10:   */ {
/* 11:   */   protected final ResolvedType _declaringType;
/* 12:   */   
/* 13:   */   protected RawMember(ResolvedType context)
/* 14:   */   {
/* 15:33 */     this._declaringType = context;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public final ResolvedType getDeclaringType()
/* 19:   */   {
/* 20:43 */     return this._declaringType;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public abstract Member getRawMember();
/* 24:   */   
/* 25:   */   public String getName()
/* 26:   */   {
/* 27:49 */     return getRawMember().getName();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean isAbstract()
/* 31:   */   {
/* 32:53 */     return Modifier.isAbstract(getModifiers());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean isStatic()
/* 36:   */   {
/* 37:57 */     return Modifier.isStatic(getModifiers());
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Annotation[] getAnnotations()
/* 41:   */   {
/* 42:61 */     return ((AnnotatedElement)getRawMember()).getAnnotations();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public abstract boolean equals(Object paramObject);
/* 46:   */   
/* 47:   */   public int hashCode()
/* 48:   */   {
/* 49:74 */     return getName().hashCode();
/* 50:   */   }
/* 51:   */   
/* 52:   */   public String toString()
/* 53:   */   {
/* 54:78 */     return getName();
/* 55:   */   }
/* 56:   */   
/* 57:   */   protected final int getModifiers()
/* 58:   */   {
/* 59:87 */     return getRawMember().getModifiers();
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.RawMember
 * JD-Core Version:    0.7.0.1
 */