/*   1:    */ package com.fasterxml.classmate.types;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.TypeBindings;
/*   5:    */ import com.fasterxml.classmate.members.RawConstructor;
/*   6:    */ import com.fasterxml.classmate.members.RawField;
/*   7:    */ import com.fasterxml.classmate.members.RawMethod;
/*   8:    */ import java.lang.reflect.Modifier;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.List;
/*  11:    */ 
/*  12:    */ public class ResolvedRecursiveType
/*  13:    */   extends ResolvedType
/*  14:    */ {
/*  15:    */   protected ResolvedType _referencedType;
/*  16:    */   
/*  17:    */   public ResolvedRecursiveType(Class<?> erased, TypeBindings bindings)
/*  18:    */   {
/*  19: 35 */     super(erased, bindings);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean canCreateSubtypes()
/*  23:    */   {
/*  24: 40 */     return this._referencedType.canCreateSubtypes();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setReference(ResolvedType ref)
/*  28:    */   {
/*  29: 46 */     if (this._referencedType != null) {
/*  30: 47 */       throw new IllegalStateException("Trying to re-set self reference; old value = " + this._referencedType + ", new = " + ref);
/*  31:    */     }
/*  32: 49 */     this._referencedType = ref;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public ResolvedType getParentClass()
/*  36:    */   {
/*  37: 64 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public ResolvedType getSelfReferencedType()
/*  41:    */   {
/*  42: 68 */     return this._referencedType;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<ResolvedType> getImplementedInterfaces()
/*  46:    */   {
/*  47: 75 */     return Collections.emptyList();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ResolvedType getArrayElementType()
/*  51:    */   {
/*  52: 83 */     return null;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean isInterface()
/*  56:    */   {
/*  57: 93 */     return this._erasedType.isInterface();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean isAbstract()
/*  61:    */   {
/*  62: 96 */     return Modifier.isAbstract(this._erasedType.getModifiers());
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isArray()
/*  66:    */   {
/*  67: 99 */     return this._erasedType.isArray();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean isPrimitive()
/*  71:    */   {
/*  72:102 */     return false;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<RawField> getMemberFields()
/*  76:    */   {
/*  77:110 */     return this._referencedType.getMemberFields();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List<RawField> getStaticFields()
/*  81:    */   {
/*  82:111 */     return this._referencedType.getStaticFields();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<RawMethod> getStaticMethods()
/*  86:    */   {
/*  87:112 */     return this._referencedType.getStaticMethods();
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<RawMethod> getMemberMethods()
/*  91:    */   {
/*  92:113 */     return this._referencedType.getMemberMethods();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<RawConstructor> getConstructors()
/*  96:    */   {
/*  97:114 */     return this._referencedType.getConstructors();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public StringBuilder appendSignature(StringBuilder sb)
/* 101:    */   {
/* 102:125 */     return appendErasedSignature(sb);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public StringBuilder appendErasedSignature(StringBuilder sb)
/* 106:    */   {
/* 107:130 */     return _appendErasedClassSignature(sb);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public StringBuilder appendBriefDescription(StringBuilder sb)
/* 111:    */   {
/* 112:135 */     return _appendClassDescription(sb);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public StringBuilder appendFullDescription(StringBuilder sb)
/* 116:    */   {
/* 117:142 */     return appendBriefDescription(sb);
/* 118:    */   }
/* 119:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.types.ResolvedRecursiveType
 * JD-Core Version:    0.7.0.1
 */