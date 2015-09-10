/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.ResolvedType;
/*  4:   */ import com.fasterxml.classmate.util.MethodKey;
/*  5:   */ import java.lang.reflect.Method;
/*  6:   */ 
/*  7:   */ public class RawMethod
/*  8:   */   extends RawMember
/*  9:   */ {
/* 10:   */   protected final Method _method;
/* 11:   */   
/* 12:   */   public RawMethod(ResolvedType context, Method method)
/* 13:   */   {
/* 14:14 */     super(context);
/* 15:15 */     this._method = method;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public Method getRawMember()
/* 19:   */   {
/* 20:27 */     return this._method;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MethodKey createKey()
/* 24:   */   {
/* 25:32 */     String name = this._method.getName();
/* 26:33 */     Class<?>[] argTypes = this._method.getParameterTypes();
/* 27:34 */     if (argTypes == null) {
/* 28:35 */       return new MethodKey(name);
/* 29:   */     }
/* 30:37 */     return new MethodKey(name, argTypes);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean equals(Object o)
/* 34:   */   {
/* 35:48 */     if (o == this) {
/* 36:48 */       return true;
/* 37:   */     }
/* 38:49 */     if ((o == null) || (o.getClass() != getClass())) {
/* 39:49 */       return false;
/* 40:   */     }
/* 41:50 */     RawMethod other = (RawMethod)o;
/* 42:51 */     return other._method == this._method;
/* 43:   */   }
/* 44:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.RawMethod
 * JD-Core Version:    0.7.0.1
 */