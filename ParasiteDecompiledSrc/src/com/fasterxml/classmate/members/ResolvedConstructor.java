/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.Annotations;
/*  4:   */ import com.fasterxml.classmate.ResolvedType;
/*  5:   */ import java.lang.reflect.Constructor;
/*  6:   */ 
/*  7:   */ public final class ResolvedConstructor
/*  8:   */   extends ResolvedMember
/*  9:   */ {
/* 10:   */   protected final Constructor<?> _constructor;
/* 11:   */   protected final ResolvedType[] _argumentTypes;
/* 12:   */   
/* 13:   */   public ResolvedConstructor(ResolvedType context, Annotations ann, Constructor<?> constructor, ResolvedType[] argumentTypes)
/* 14:   */   {
/* 15:21 */     super(context, ann);
/* 16:22 */     this._constructor = constructor;
/* 17:23 */     this._argumentTypes = argumentTypes;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Constructor<?> getRawMember()
/* 21:   */   {
/* 22:34 */     return this._constructor;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ResolvedType getType()
/* 26:   */   {
/* 27:38 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public int getArgumentCount()
/* 31:   */   {
/* 32:50 */     return this._argumentTypes.length;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public ResolvedType getArgumentType(int index)
/* 36:   */   {
/* 37:55 */     if ((index < 0) || (index >= this._argumentTypes.length)) {
/* 38:56 */       return null;
/* 39:   */     }
/* 40:58 */     return this._argumentTypes[index];
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int hashCode()
/* 44:   */   {
/* 45:68 */     return this._constructor.getName().hashCode();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean equals(Object o)
/* 49:   */   {
/* 50:73 */     if (o == this) {
/* 51:73 */       return true;
/* 52:   */     }
/* 53:74 */     if ((o == null) || (o.getClass() != getClass())) {
/* 54:74 */       return false;
/* 55:   */     }
/* 56:75 */     RawConstructor other = (RawConstructor)o;
/* 57:76 */     return other._constructor == this._constructor;
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.ResolvedConstructor
 * JD-Core Version:    0.7.0.1
 */