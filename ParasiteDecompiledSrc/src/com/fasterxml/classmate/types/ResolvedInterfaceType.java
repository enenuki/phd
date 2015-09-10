/*   1:    */ package com.fasterxml.classmate.types;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.TypeBindings;
/*   5:    */ import com.fasterxml.classmate.members.RawField;
/*   6:    */ import com.fasterxml.classmate.members.RawMethod;
/*   7:    */ import java.util.Arrays;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.List;
/*  10:    */ 
/*  11:    */ public class ResolvedInterfaceType
/*  12:    */   extends ResolvedType
/*  13:    */ {
/*  14:    */   protected final ResolvedType[] _superInterfaces;
/*  15:    */   protected RawField[] _constantFields;
/*  16:    */   protected RawMethod[] _memberMethods;
/*  17:    */   
/*  18:    */   public ResolvedInterfaceType(Class<?> erased, TypeBindings bindings, ResolvedType[] superInterfaces)
/*  19:    */   {
/*  20: 36 */     super(erased, bindings);
/*  21: 37 */     this._superInterfaces = superInterfaces;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public boolean canCreateSubtypes()
/*  25:    */   {
/*  26: 42 */     return true;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ResolvedType getParentClass()
/*  30:    */   {
/*  31: 53 */     return null;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ResolvedType getSelfReferencedType()
/*  35:    */   {
/*  36: 57 */     return null;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public List<ResolvedType> getImplementedInterfaces()
/*  40:    */   {
/*  41: 61 */     return this._superInterfaces.length == 0 ? Collections.emptyList() : Arrays.asList(this._superInterfaces);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public ResolvedType getArrayElementType()
/*  45:    */   {
/*  46: 67 */     return null;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public boolean isInterface()
/*  50:    */   {
/*  51: 77 */     return true;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isAbstract()
/*  55:    */   {
/*  56: 80 */     return true;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean isArray()
/*  60:    */   {
/*  61: 83 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isPrimitive()
/*  65:    */   {
/*  66: 86 */     return false;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public synchronized List<RawField> getStaticFields()
/*  70:    */   {
/*  71: 97 */     if (this._constantFields == null) {
/*  72: 98 */       this._constantFields = _getFields(true);
/*  73:    */     }
/*  74:100 */     if (this._constantFields.length == 0) {
/*  75:101 */       return Collections.emptyList();
/*  76:    */     }
/*  77:103 */     return Arrays.asList(this._constantFields);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public synchronized List<RawMethod> getMemberMethods()
/*  81:    */   {
/*  82:108 */     if (this._memberMethods == null) {
/*  83:109 */       this._memberMethods = _getMethods(false);
/*  84:    */     }
/*  85:111 */     if (this._memberMethods.length == 0) {
/*  86:112 */       return Collections.emptyList();
/*  87:    */     }
/*  88:114 */     return Arrays.asList(this._memberMethods);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public StringBuilder appendSignature(StringBuilder sb)
/*  92:    */   {
/*  93:125 */     return _appendClassSignature(sb);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public StringBuilder appendErasedSignature(StringBuilder sb)
/*  97:    */   {
/*  98:130 */     return _appendErasedClassSignature(sb);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public StringBuilder appendBriefDescription(StringBuilder sb)
/* 102:    */   {
/* 103:135 */     return _appendClassDescription(sb);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public StringBuilder appendFullDescription(StringBuilder sb)
/* 107:    */   {
/* 108:141 */     sb = _appendClassDescription(sb);
/* 109:    */     
/* 110:143 */     int count = this._superInterfaces.length;
/* 111:144 */     if (count > 0)
/* 112:    */     {
/* 113:145 */       sb.append(" extends ");
/* 114:146 */       for (int i = 0; i < count; i++)
/* 115:    */       {
/* 116:147 */         if (i > 0) {
/* 117:148 */           sb.append(",");
/* 118:    */         }
/* 119:150 */         sb = this._superInterfaces[i].appendBriefDescription(sb);
/* 120:    */       }
/* 121:    */     }
/* 122:153 */     return sb;
/* 123:    */   }
/* 124:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.types.ResolvedInterfaceType
 * JD-Core Version:    0.7.0.1
 */