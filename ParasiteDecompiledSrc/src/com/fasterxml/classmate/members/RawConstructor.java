/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.ResolvedType;
/*  4:   */ import com.fasterxml.classmate.util.MethodKey;
/*  5:   */ import java.lang.reflect.Constructor;
/*  6:   */ 
/*  7:   */ public final class RawConstructor
/*  8:   */   extends RawMember
/*  9:   */ {
/* 10:   */   protected final Constructor<?> _constructor;
/* 11:   */   
/* 12:   */   public RawConstructor(ResolvedType context, Constructor<?> constructor)
/* 13:   */   {
/* 14:14 */     super(context);
/* 15:15 */     this._constructor = constructor;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public MethodKey createKey()
/* 19:   */   {
/* 20:24 */     String name = this._constructor.getName();
/* 21:25 */     Class<?>[] argTypes = this._constructor.getParameterTypes();
/* 22:26 */     if (argTypes == null) {
/* 23:27 */       return new MethodKey(name);
/* 24:   */     }
/* 25:29 */     return new MethodKey(name, argTypes);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Constructor<?> getRawMember()
/* 29:   */   {
/* 30:40 */     return this._constructor;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int hashCode()
/* 34:   */   {
/* 35:50 */     return this._constructor.getName().hashCode();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean equals(Object o)
/* 39:   */   {
/* 40:55 */     if (o == this) {
/* 41:55 */       return true;
/* 42:   */     }
/* 43:56 */     if ((o == null) || (o.getClass() != getClass())) {
/* 44:56 */       return false;
/* 45:   */     }
/* 46:57 */     RawConstructor other = (RawConstructor)o;
/* 47:58 */     return other._constructor == this._constructor;
/* 48:   */   }
/* 49:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.RawConstructor
 * JD-Core Version:    0.7.0.1
 */