/*   1:    */ package com.fasterxml.classmate.types;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.TypeBindings;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public final class ResolvedArrayType
/*   9:    */   extends ResolvedType
/*  10:    */ {
/*  11:    */   protected final ResolvedType _elementType;
/*  12:    */   
/*  13:    */   public ResolvedArrayType(Class<?> erased, TypeBindings bindings, ResolvedObjectType superclass, ResolvedType elementType)
/*  14:    */   {
/*  15: 22 */     super(erased, bindings);
/*  16: 23 */     this._elementType = elementType;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public boolean canCreateSubtypes()
/*  20:    */   {
/*  21: 28 */     return false;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public ResolvedType getParentClass()
/*  25:    */   {
/*  26: 38 */     return null;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ResolvedType getSelfReferencedType()
/*  30:    */   {
/*  31: 41 */     return null;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public List<ResolvedType> getImplementedInterfaces()
/*  35:    */   {
/*  36: 45 */     return Collections.emptyList();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isInterface()
/*  40:    */   {
/*  41: 56 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean isAbstract()
/*  45:    */   {
/*  46: 60 */     return false;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public ResolvedType getArrayElementType()
/*  50:    */   {
/*  51: 63 */     return this._elementType;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isArray()
/*  55:    */   {
/*  56: 66 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isPrimitive()
/*  60:    */   {
/*  61: 69 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public StringBuilder appendSignature(StringBuilder sb)
/*  65:    */   {
/*  66: 87 */     sb.append('[');
/*  67: 88 */     return this._elementType.appendSignature(sb);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public StringBuilder appendErasedSignature(StringBuilder sb)
/*  71:    */   {
/*  72: 93 */     sb.append('[');
/*  73: 94 */     return this._elementType.appendErasedSignature(sb);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public StringBuilder appendBriefDescription(StringBuilder sb)
/*  77:    */   {
/*  78: 99 */     sb = this._elementType.appendBriefDescription(sb);
/*  79:100 */     sb.append("[]");
/*  80:101 */     return sb;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public StringBuilder appendFullDescription(StringBuilder sb)
/*  84:    */   {
/*  85:105 */     return appendBriefDescription(sb);
/*  86:    */   }
/*  87:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.types.ResolvedArrayType
 * JD-Core Version:    0.7.0.1
 */