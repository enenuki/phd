/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.Annotations;
/*  4:   */ import com.fasterxml.classmate.ResolvedType;
/*  5:   */ import java.lang.reflect.Method;
/*  6:   */ 
/*  7:   */ public class ResolvedMethod
/*  8:   */   extends ResolvedMember
/*  9:   */ {
/* 10:   */   protected final Method _method;
/* 11:   */   protected final ResolvedType _returnType;
/* 12:   */   protected final ResolvedType[] _argumentTypes;
/* 13:   */   
/* 14:   */   public ResolvedMethod(ResolvedType context, Annotations ann, Method method, ResolvedType returnType, ResolvedType[] argumentTypes)
/* 15:   */   {
/* 16:19 */     super(context, ann);
/* 17:20 */     this._method = method;
/* 18:21 */     this._returnType = returnType;
/* 19:22 */     this._argumentTypes = argumentTypes;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Method getRawMember()
/* 23:   */   {
/* 24:33 */     return this._method;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public ResolvedType getType()
/* 28:   */   {
/* 29:37 */     return this._returnType;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public ResolvedType getReturnType()
/* 33:   */   {
/* 34:45 */     return this._returnType;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getArgumentCount()
/* 38:   */   {
/* 39:51 */     return this._argumentTypes.length;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public ResolvedType getArgumentType(int index)
/* 43:   */   {
/* 44:56 */     if ((index < 0) || (index >= this._argumentTypes.length)) {
/* 45:57 */       return null;
/* 46:   */     }
/* 47:59 */     return this._argumentTypes[index];
/* 48:   */   }
/* 49:   */   
/* 50:   */   public boolean equals(Object o)
/* 51:   */   {
/* 52:70 */     if (o == this) {
/* 53:70 */       return true;
/* 54:   */     }
/* 55:71 */     if ((o == null) || (o.getClass() != getClass())) {
/* 56:71 */       return false;
/* 57:   */     }
/* 58:72 */     RawMethod other = (RawMethod)o;
/* 59:73 */     return other._method == this._method;
/* 60:   */   }
/* 61:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.ResolvedMethod
 * JD-Core Version:    0.7.0.1
 */