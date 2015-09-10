/*   1:    */ package com.fasterxml.classmate.types;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.TypeBindings;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.List;
/*   8:    */ 
/*   9:    */ public final class ResolvedPrimitiveType
/*  10:    */   extends ResolvedType
/*  11:    */ {
/*  12: 17 */   private static final ResolvedPrimitiveType VOID = new ResolvedPrimitiveType(Void.TYPE, 'V', "void");
/*  13:    */   protected final String _signature;
/*  14:    */   protected final String _description;
/*  15:    */   
/*  16:    */   protected ResolvedPrimitiveType(Class<?> erased, char sig, String desc)
/*  17:    */   {
/*  18: 38 */     super(erased, TypeBindings.emptyBindings());
/*  19: 39 */     this._signature = String.valueOf(sig);
/*  20: 40 */     this._description = desc;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static List<ResolvedPrimitiveType> all()
/*  24:    */   {
/*  25: 45 */     ArrayList<ResolvedPrimitiveType> all = new ArrayList();
/*  26: 46 */     all.add(new ResolvedPrimitiveType(Boolean.TYPE, 'Z', "boolean"));
/*  27: 47 */     all.add(new ResolvedPrimitiveType(Byte.TYPE, 'B', "byte"));
/*  28: 48 */     all.add(new ResolvedPrimitiveType(Short.TYPE, 'S', "short"));
/*  29: 49 */     all.add(new ResolvedPrimitiveType(Character.TYPE, 'C', "char"));
/*  30: 50 */     all.add(new ResolvedPrimitiveType(Integer.TYPE, 'I', "int"));
/*  31: 51 */     all.add(new ResolvedPrimitiveType(Long.TYPE, 'J', "long"));
/*  32: 52 */     all.add(new ResolvedPrimitiveType(Float.TYPE, 'F', "float"));
/*  33: 53 */     all.add(new ResolvedPrimitiveType(Double.TYPE, 'D', "double"));
/*  34: 54 */     return all;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static ResolvedPrimitiveType voidType()
/*  38:    */   {
/*  39: 59 */     return VOID;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public boolean canCreateSubtypes()
/*  43:    */   {
/*  44: 64 */     return false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ResolvedType getSelfReferencedType()
/*  48:    */   {
/*  49: 74 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public ResolvedType getParentClass()
/*  53:    */   {
/*  54: 77 */     return null;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean isInterface()
/*  58:    */   {
/*  59: 86 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isAbstract()
/*  63:    */   {
/*  64: 89 */     return false;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ResolvedType getArrayElementType()
/*  68:    */   {
/*  69: 92 */     return null;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public boolean isArray()
/*  73:    */   {
/*  74: 95 */     return false;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean isPrimitive()
/*  78:    */   {
/*  79: 98 */     return true;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<ResolvedType> getImplementedInterfaces()
/*  83:    */   {
/*  84:102 */     return Collections.emptyList();
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getSignature()
/*  88:    */   {
/*  89:121 */     return this._signature;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getErasedSignature()
/*  93:    */   {
/*  94:126 */     return this._signature;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getFullDescription()
/*  98:    */   {
/*  99:131 */     return this._description;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public StringBuilder appendSignature(StringBuilder sb)
/* 103:    */   {
/* 104:136 */     sb.append(this._signature);
/* 105:137 */     return sb;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public StringBuilder appendErasedSignature(StringBuilder sb)
/* 109:    */   {
/* 110:142 */     sb.append(this._signature);
/* 111:143 */     return sb;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public StringBuilder appendFullDescription(StringBuilder sb)
/* 115:    */   {
/* 116:148 */     sb.append(this._description);
/* 117:149 */     return sb;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public StringBuilder appendBriefDescription(StringBuilder sb)
/* 121:    */   {
/* 122:154 */     sb.append(this._description);
/* 123:155 */     return sb;
/* 124:    */   }
/* 125:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.types.ResolvedPrimitiveType
 * JD-Core Version:    0.7.0.1
 */