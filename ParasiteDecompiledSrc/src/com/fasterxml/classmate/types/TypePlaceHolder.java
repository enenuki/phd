/*  1:   */ package com.fasterxml.classmate.types;
/*  2:   */ 
/*  3:   */ import com.fasterxml.classmate.ResolvedType;
/*  4:   */ import com.fasterxml.classmate.TypeBindings;
/*  5:   */ import java.util.Collections;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public class TypePlaceHolder
/*  9:   */   extends ResolvedType
/* 10:   */ {
/* 11:   */   protected final int _ordinal;
/* 12:   */   protected ResolvedType _actualType;
/* 13:   */   
/* 14:   */   public TypePlaceHolder(int ordinal)
/* 15:   */   {
/* 16:23 */     super(Object.class, TypeBindings.emptyBindings());
/* 17:24 */     this._ordinal = ordinal;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean canCreateSubtypes()
/* 21:   */   {
/* 22:28 */     return false;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public ResolvedType actualType()
/* 26:   */   {
/* 27:30 */     return this._actualType;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void actualType(ResolvedType t)
/* 31:   */   {
/* 32:31 */     this._actualType = t;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public ResolvedType getParentClass()
/* 36:   */   {
/* 37:40 */     return null;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public ResolvedType getSelfReferencedType()
/* 41:   */   {
/* 42:43 */     return null;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public List<ResolvedType> getImplementedInterfaces()
/* 46:   */   {
/* 47:46 */     return Collections.emptyList();
/* 48:   */   }
/* 49:   */   
/* 50:   */   public ResolvedType getArrayElementType()
/* 51:   */   {
/* 52:49 */     return null;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public boolean isInterface()
/* 56:   */   {
/* 57:58 */     return false;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public boolean isAbstract()
/* 61:   */   {
/* 62:61 */     return true;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean isArray()
/* 66:   */   {
/* 67:64 */     return false;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public boolean isPrimitive()
/* 71:   */   {
/* 72:67 */     return false;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public StringBuilder appendSignature(StringBuilder sb)
/* 76:   */   {
/* 77:83 */     return _appendClassSignature(sb);
/* 78:   */   }
/* 79:   */   
/* 80:   */   public StringBuilder appendErasedSignature(StringBuilder sb)
/* 81:   */   {
/* 82:88 */     return _appendErasedClassSignature(sb);
/* 83:   */   }
/* 84:   */   
/* 85:   */   public StringBuilder appendBriefDescription(StringBuilder sb)
/* 86:   */   {
/* 87:93 */     sb.append('<').append(this._ordinal).append('>');
/* 88:94 */     return sb;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public StringBuilder appendFullDescription(StringBuilder sb)
/* 92:   */   {
/* 93:99 */     return appendBriefDescription(sb);
/* 94:   */   }
/* 95:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.types.TypePlaceHolder
 * JD-Core Version:    0.7.0.1
 */