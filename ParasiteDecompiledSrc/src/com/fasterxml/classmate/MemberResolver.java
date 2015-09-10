/*   1:    */ package com.fasterxml.classmate;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.members.HierarchicType;
/*   4:    */ import com.fasterxml.classmate.members.RawConstructor;
/*   5:    */ import com.fasterxml.classmate.members.RawField;
/*   6:    */ import com.fasterxml.classmate.members.RawMethod;
/*   7:    */ import com.fasterxml.classmate.util.ClassKey;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ 
/*  13:    */ public class MemberResolver
/*  14:    */ {
/*  15:    */   protected final TypeResolver _typeResolver;
/*  16:    */   protected boolean _cfgIncludeLangObject;
/*  17:    */   protected Filter<RawField> _fieldFilter;
/*  18:    */   protected Filter<RawMethod> _methodFilter;
/*  19:    */   protected Filter<RawConstructor> _constructorFilter;
/*  20:    */   
/*  21:    */   public MemberResolver(TypeResolver typeResolver)
/*  22:    */   {
/*  23: 70 */     this._typeResolver = typeResolver;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public MemberResolver setIncludeLangObject(boolean state)
/*  27:    */   {
/*  28: 79 */     this._cfgIncludeLangObject = state;
/*  29: 80 */     return this;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public MemberResolver setFieldFilter(Filter<RawField> f)
/*  33:    */   {
/*  34: 84 */     this._fieldFilter = f;
/*  35: 85 */     return this;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public MemberResolver setMethodFilter(Filter<RawMethod> f)
/*  39:    */   {
/*  40: 89 */     this._methodFilter = f;
/*  41: 90 */     return this;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public MemberResolver setConstructorFilter(Filter<RawConstructor> f)
/*  45:    */   {
/*  46: 94 */     this._constructorFilter = f;
/*  47: 95 */     return this;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public ResolvedTypeWithMembers resolve(ResolvedType mainType, AnnotationConfiguration annotationConfig, AnnotationOverrides annotationOverrides)
/*  51:    */   {
/*  52:119 */     HashSet<ClassKey> seenTypes = new HashSet();
/*  53:120 */     ArrayList<ResolvedType> types = new ArrayList();
/*  54:121 */     _gatherTypes(mainType, seenTypes, types);
/*  55:    */     
/*  56:    */ 
/*  57:    */ 
/*  58:125 */     HierarchicType mainHierarchicType = null;
/*  59:    */     HierarchicType[] htypes;
/*  60:128 */     if (annotationOverrides == null)
/*  61:    */     {
/*  62:129 */       int len = types.size();
/*  63:130 */       HierarchicType[] htypes = new HierarchicType[len];
/*  64:131 */       for (int i = 0; i < len; i++) {
/*  65:133 */         htypes[i] = new HierarchicType((ResolvedType)types.get(i), false, i);
/*  66:    */       }
/*  67:135 */       mainHierarchicType = htypes[0];
/*  68:    */     }
/*  69:    */     else
/*  70:    */     {
/*  71:137 */       ArrayList<HierarchicType> typesWithMixins = new ArrayList();
/*  72:138 */       for (ResolvedType type : types)
/*  73:    */       {
/*  74:140 */         List<Class<?>> m = annotationOverrides.mixInsFor(type.getErasedType());
/*  75:141 */         if (m != null) {
/*  76:142 */           for (Class<?> mixinClass : m) {
/*  77:143 */             _addOverrides(typesWithMixins, seenTypes, mixinClass);
/*  78:    */           }
/*  79:    */         }
/*  80:148 */         HierarchicType ht = new HierarchicType(type, false, typesWithMixins.size());
/*  81:149 */         if (mainHierarchicType == null) {
/*  82:150 */           mainHierarchicType = ht;
/*  83:    */         }
/*  84:152 */         typesWithMixins.add(ht);
/*  85:    */       }
/*  86:154 */       htypes = (HierarchicType[])typesWithMixins.toArray(new HierarchicType[typesWithMixins.size()]);
/*  87:    */     }
/*  88:157 */     return new ResolvedTypeWithMembers(this._typeResolver, annotationConfig, mainHierarchicType, htypes, this._constructorFilter, this._fieldFilter, this._methodFilter);
/*  89:    */   }
/*  90:    */   
/*  91:    */   private void _addOverrides(List<HierarchicType> typesWithOverrides, Set<ClassKey> seenTypes, Class<?> override)
/*  92:    */   {
/*  93:163 */     ClassKey key = new ClassKey(override);
/*  94:164 */     if (!seenTypes.contains(key))
/*  95:    */     {
/*  96:165 */       seenTypes.add(key);
/*  97:166 */       ResolvedType resolvedOverride = this._typeResolver.resolve(override);
/*  98:167 */       typesWithOverrides.add(new HierarchicType(resolvedOverride, true, typesWithOverrides.size()));
/*  99:168 */       for (ResolvedType r : resolvedOverride.getImplementedInterfaces()) {
/* 100:169 */         _addOverrides(typesWithOverrides, seenTypes, r);
/* 101:    */       }
/* 102:171 */       ResolvedType superClass = resolvedOverride.getParentClass();
/* 103:172 */       _addOverrides(typesWithOverrides, seenTypes, superClass);
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void _addOverrides(List<HierarchicType> typesWithOverrides, Set<ClassKey> seenTypes, ResolvedType override)
/* 108:    */   {
/* 109:178 */     if (override == null) {
/* 110:178 */       return;
/* 111:    */     }
/* 112:180 */     Class<?> raw = override.getErasedType();
/* 113:181 */     if ((!this._cfgIncludeLangObject) && (Object.class == raw)) {
/* 114:181 */       return;
/* 115:    */     }
/* 116:182 */     ClassKey key = new ClassKey(raw);
/* 117:183 */     if (!seenTypes.contains(key))
/* 118:    */     {
/* 119:184 */       seenTypes.add(key);
/* 120:185 */       typesWithOverrides.add(new HierarchicType(override, true, typesWithOverrides.size()));
/* 121:186 */       for (ResolvedType r : override.getImplementedInterfaces()) {
/* 122:187 */         _addOverrides(typesWithOverrides, seenTypes, r);
/* 123:    */       }
/* 124:189 */       ResolvedType superClass = override.getParentClass();
/* 125:190 */       if (superClass != null) {
/* 126:191 */         _addOverrides(typesWithOverrides, seenTypes, superClass);
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected void _gatherTypes(ResolvedType currentType, Set<ClassKey> seenTypes, List<ResolvedType> types)
/* 132:    */   {
/* 133:205 */     if (currentType == null) {
/* 134:206 */       return;
/* 135:    */     }
/* 136:208 */     Class<?> raw = currentType.getErasedType();
/* 137:210 */     if ((!this._cfgIncludeLangObject) && (raw == Object.class)) {
/* 138:211 */       return;
/* 139:    */     }
/* 140:214 */     ClassKey key = new ClassKey(currentType.getErasedType());
/* 141:215 */     if (seenTypes.contains(key)) {
/* 142:216 */       return;
/* 143:    */     }
/* 144:219 */     seenTypes.add(key);
/* 145:220 */     types.add(currentType);
/* 146:227 */     for (ResolvedType t : currentType.getImplementedInterfaces()) {
/* 147:228 */       _gatherTypes(t, seenTypes, types);
/* 148:    */     }
/* 149:231 */     _gatherTypes(currentType.getParentClass(), seenTypes, types);
/* 150:    */   }
/* 151:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.MemberResolver
 * JD-Core Version:    0.7.0.1
 */