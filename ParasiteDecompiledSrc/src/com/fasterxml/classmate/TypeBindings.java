/*   1:    */ package com.fasterxml.classmate;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.TypeVariable;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public final class TypeBindings
/*   9:    */ {
/*  10: 12 */   private static final String[] NO_STRINGS = new String[0];
/*  11: 14 */   private static final ResolvedType[] NO_TYPES = new ResolvedType[0];
/*  12: 16 */   private static final TypeBindings EMPTY = new TypeBindings(NO_STRINGS, NO_TYPES);
/*  13:    */   private final String[] _names;
/*  14:    */   private final ResolvedType[] _types;
/*  15:    */   private final int _hashCode;
/*  16:    */   
/*  17:    */   private TypeBindings(String[] names, ResolvedType[] types)
/*  18:    */   {
/*  19: 38 */     this._names = (names == null ? NO_STRINGS : names);
/*  20: 39 */     this._types = (types == null ? NO_TYPES : types);
/*  21: 40 */     if (this._names.length != this._types.length) {
/*  22: 41 */       throw new IllegalArgumentException("Mismatching names (" + this._names.length + "), types (" + this._types.length + ")");
/*  23:    */     }
/*  24: 43 */     int h = 1;
/*  25: 44 */     int i = 0;
/*  26: 44 */     for (int len = types.length; i < len; i++) {
/*  27: 45 */       h += types[i].hashCode();
/*  28:    */     }
/*  29: 47 */     this._hashCode = h;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static TypeBindings emptyBindings()
/*  33:    */   {
/*  34: 51 */     return EMPTY;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static TypeBindings create(Class<?> erasedType, List<ResolvedType> typeList)
/*  38:    */   {
/*  39: 60 */     ResolvedType[] types = (typeList == null) || (typeList.isEmpty()) ? NO_TYPES : (ResolvedType[])typeList.toArray(new ResolvedType[typeList.size()]);
/*  40:    */     
/*  41: 62 */     return create(erasedType, types);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static TypeBindings create(Class<?> erasedType, ResolvedType[] types)
/*  45:    */   {
/*  46: 67 */     if (types == null) {
/*  47: 68 */       types = NO_TYPES;
/*  48:    */     }
/*  49: 70 */     TypeVariable<?>[] vars = erasedType.getTypeParameters();
/*  50:    */     String[] names;
/*  51:    */     String[] names;
/*  52: 72 */     if ((vars == null) || (vars.length == 0))
/*  53:    */     {
/*  54: 73 */       names = NO_STRINGS;
/*  55:    */     }
/*  56:    */     else
/*  57:    */     {
/*  58: 75 */       int len = vars.length;
/*  59: 76 */       names = new String[len];
/*  60: 77 */       for (int i = 0; i < len; i++) {
/*  61: 78 */         names[i] = vars[i].getName();
/*  62:    */       }
/*  63:    */     }
/*  64: 82 */     if (names.length != types.length) {
/*  65: 83 */       throw new IllegalArgumentException("Can not create TypeBinding for class " + erasedType.getName() + " with " + types.length + " type parameter" + (types.length == 1 ? "" : "s") + ": class expects " + names.length);
/*  66:    */     }
/*  67: 87 */     return new TypeBindings(names, types);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public TypeBindings withAdditionalBinding(String name, ResolvedType type)
/*  71:    */   {
/*  72: 96 */     int len = this._names.length;
/*  73: 97 */     String[] newNames = (String[])Arrays.copyOf(this._names, len + 1);
/*  74: 98 */     newNames[len] = name;
/*  75: 99 */     ResolvedType[] newTypes = (ResolvedType[])Arrays.copyOf(this._types, len + 1);
/*  76:100 */     newTypes[len] = type;
/*  77:101 */     return new TypeBindings(newNames, newTypes);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public ResolvedType findBoundType(String name)
/*  81:    */   {
/*  82:115 */     int i = 0;
/*  83:115 */     for (int len = this._names.length; i < len; i++) {
/*  84:116 */       if (name.equals(this._names[i])) {
/*  85:117 */         return this._types[i];
/*  86:    */       }
/*  87:    */     }
/*  88:120 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean isEmpty()
/*  92:    */   {
/*  93:124 */     return this._types.length == 0;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int size()
/*  97:    */   {
/*  98:131 */     return this._types.length;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getBoundName(int index)
/* 102:    */   {
/* 103:136 */     if ((index < 0) || (index >= this._names.length)) {
/* 104:137 */       return null;
/* 105:    */     }
/* 106:139 */     return this._names[index];
/* 107:    */   }
/* 108:    */   
/* 109:    */   public ResolvedType getBoundType(int index)
/* 110:    */   {
/* 111:144 */     if ((index < 0) || (index >= this._types.length)) {
/* 112:145 */       return null;
/* 113:    */     }
/* 114:147 */     return this._types[index];
/* 115:    */   }
/* 116:    */   
/* 117:    */   public List<ResolvedType> getTypeParameters()
/* 118:    */   {
/* 119:155 */     if (this._types.length == 0) {
/* 120:156 */       return Collections.emptyList();
/* 121:    */     }
/* 122:158 */     return Arrays.asList(this._types);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String toString()
/* 126:    */   {
/* 127:169 */     if (this._types.length == 0) {
/* 128:170 */       return "";
/* 129:    */     }
/* 130:172 */     StringBuilder sb = new StringBuilder();
/* 131:173 */     sb.append('<');
/* 132:174 */     int i = 0;
/* 133:174 */     for (int len = this._types.length; i < len; i++)
/* 134:    */     {
/* 135:175 */       if (i > 0) {
/* 136:176 */         sb.append(',');
/* 137:    */       }
/* 138:178 */       sb = this._types[i].appendBriefDescription(sb);
/* 139:    */     }
/* 140:180 */     sb.append('>');
/* 141:181 */     return sb.toString();
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int hashCode()
/* 145:    */   {
/* 146:184 */     return this._hashCode;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public boolean equals(Object o)
/* 150:    */   {
/* 151:188 */     if (o == this) {
/* 152:188 */       return true;
/* 153:    */     }
/* 154:189 */     if ((o == null) || (o.getClass() != getClass())) {
/* 155:189 */       return false;
/* 156:    */     }
/* 157:190 */     TypeBindings other = (TypeBindings)o;
/* 158:191 */     int len = this._types.length;
/* 159:192 */     if (len != other.size()) {
/* 160:193 */       return false;
/* 161:    */     }
/* 162:195 */     ResolvedType[] otherTypes = other._types;
/* 163:196 */     for (int i = 0; i < len; i++) {
/* 164:197 */       if (!otherTypes[i].equals(this._types[i])) {
/* 165:198 */         return false;
/* 166:    */       }
/* 167:    */     }
/* 168:201 */     return true;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected ResolvedType[] typeParameterArray()
/* 172:    */   {
/* 173:211 */     return this._types;
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.TypeBindings
 * JD-Core Version:    0.7.0.1
 */