/*  1:   */ package com.fasterxml.classmate.members;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.ResolvedType;
/*  4:   */ 
/*  5:   */ public final class HierarchicType
/*  6:   */ {
/*  7:   */   protected final boolean _isMixin;
/*  8:   */   protected final ResolvedType _type;
/*  9:   */   protected final int _priority;
/* 10:   */   
/* 11:   */   public HierarchicType(ResolvedType type, boolean mixin, int priority)
/* 12:   */   {
/* 13:35 */     this._type = type;
/* 14:36 */     this._isMixin = mixin;
/* 15:37 */     this._priority = priority;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public ResolvedType getType()
/* 19:   */   {
/* 20:46 */     return this._type;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Class<?> getErasedType()
/* 24:   */   {
/* 25:47 */     return this._type.getErasedType();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public boolean isMixin()
/* 29:   */   {
/* 30:48 */     return this._isMixin;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int getPriority()
/* 34:   */   {
/* 35:49 */     return this._priority;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toString()
/* 39:   */   {
/* 40:57 */     return this._type.toString();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int hashCode()
/* 44:   */   {
/* 45:58 */     return this._type.hashCode();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean equals(Object o)
/* 49:   */   {
/* 50:62 */     if (o == this) {
/* 51:62 */       return true;
/* 52:   */     }
/* 53:63 */     if ((o == null) || (o.getClass() != getClass())) {
/* 54:63 */       return false;
/* 55:   */     }
/* 56:64 */     HierarchicType other = (HierarchicType)o;
/* 57:65 */     return this._type.equals(other._type);
/* 58:   */   }
/* 59:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.members.HierarchicType
 * JD-Core Version:    0.7.0.1
 */