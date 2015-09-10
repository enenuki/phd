/*   1:    */ package com.fasterxml.classmate.types;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.ResolvedType;
/*   4:    */ import com.fasterxml.classmate.TypeBindings;
/*   5:    */ import com.fasterxml.classmate.members.RawConstructor;
/*   6:    */ import com.fasterxml.classmate.members.RawField;
/*   7:    */ import com.fasterxml.classmate.members.RawMethod;
/*   8:    */ import java.lang.reflect.Modifier;
/*   9:    */ import java.util.Arrays;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.List;
/*  12:    */ 
/*  13:    */ public class ResolvedObjectType
/*  14:    */   extends ResolvedType
/*  15:    */ {
/*  16:    */   protected final ResolvedObjectType _superClass;
/*  17:    */   protected final ResolvedType[] _superInterfaces;
/*  18:    */   protected final int _modifiers;
/*  19:    */   protected RawConstructor[] _constructors;
/*  20:    */   protected RawField[] _memberFields;
/*  21:    */   protected RawField[] _staticFields;
/*  22:    */   protected RawMethod[] _memberMethods;
/*  23:    */   protected RawMethod[] _staticMethods;
/*  24:    */   
/*  25:    */   public ResolvedObjectType(Class<?> erased, TypeBindings bindings, ResolvedObjectType superClass, List<ResolvedType> interfaces)
/*  26:    */   {
/*  27: 51 */     this(erased, bindings, superClass, (interfaces == null) || (interfaces.isEmpty()) ? NO_TYPES : (ResolvedType[])interfaces.toArray(new ResolvedType[interfaces.size()]));
/*  28:    */   }
/*  29:    */   
/*  30:    */   public ResolvedObjectType(Class<?> erased, TypeBindings bindings, ResolvedObjectType superClass, ResolvedType[] interfaces)
/*  31:    */   {
/*  32: 59 */     super(erased, bindings);
/*  33: 60 */     this._superClass = superClass;
/*  34: 61 */     this._superInterfaces = (interfaces == null ? NO_TYPES : interfaces);
/*  35: 62 */     this._modifiers = erased.getModifiers();
/*  36:    */   }
/*  37:    */   
/*  38:    */   public boolean canCreateSubtypes()
/*  39:    */   {
/*  40: 67 */     return true;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public ResolvedObjectType getParentClass()
/*  44:    */   {
/*  45: 77 */     return this._superClass;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ResolvedType getSelfReferencedType()
/*  49:    */   {
/*  50: 80 */     return null;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<ResolvedType> getImplementedInterfaces()
/*  54:    */   {
/*  55: 84 */     return this._superInterfaces.length == 0 ? Collections.emptyList() : Arrays.asList(this._superInterfaces);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public final ResolvedType getArrayElementType()
/*  59:    */   {
/*  60: 95 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public final boolean isInterface()
/*  64:    */   {
/*  65:104 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean isAbstract()
/*  69:    */   {
/*  70:108 */     return Modifier.isAbstract(this._modifiers);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public final boolean isArray()
/*  74:    */   {
/*  75:112 */     return false;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public final boolean isPrimitive()
/*  79:    */   {
/*  80:115 */     return false;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized List<RawField> getMemberFields()
/*  84:    */   {
/*  85:126 */     if (this._memberFields == null) {
/*  86:127 */       this._memberFields = _getFields(false);
/*  87:    */     }
/*  88:129 */     if (this._memberFields.length == 0) {
/*  89:130 */       return Collections.emptyList();
/*  90:    */     }
/*  91:132 */     return Arrays.asList(this._memberFields);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public synchronized List<RawField> getStaticFields()
/*  95:    */   {
/*  96:137 */     if (this._staticFields == null) {
/*  97:138 */       this._staticFields = _getFields(true);
/*  98:    */     }
/*  99:140 */     if (this._staticFields.length == 0) {
/* 100:141 */       return Collections.emptyList();
/* 101:    */     }
/* 102:143 */     return Arrays.asList(this._staticFields);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public synchronized List<RawMethod> getMemberMethods()
/* 106:    */   {
/* 107:148 */     if (this._memberMethods == null) {
/* 108:149 */       this._memberMethods = _getMethods(false);
/* 109:    */     }
/* 110:151 */     if (this._memberMethods.length == 0) {
/* 111:152 */       return Collections.emptyList();
/* 112:    */     }
/* 113:154 */     return Arrays.asList(this._memberMethods);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public synchronized List<RawMethod> getStaticMethods()
/* 117:    */   {
/* 118:159 */     if (this._staticMethods == null) {
/* 119:160 */       this._staticMethods = _getMethods(true);
/* 120:    */     }
/* 121:162 */     if (this._staticMethods.length == 0) {
/* 122:163 */       return Collections.emptyList();
/* 123:    */     }
/* 124:165 */     return Arrays.asList(this._staticMethods);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<RawConstructor> getConstructors()
/* 128:    */   {
/* 129:170 */     if (this._constructors == null) {
/* 130:171 */       this._constructors = _getConstructors();
/* 131:    */     }
/* 132:173 */     if (this._constructors.length == 0) {
/* 133:174 */       return Collections.emptyList();
/* 134:    */     }
/* 135:176 */     return Arrays.asList(this._constructors);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public StringBuilder appendSignature(StringBuilder sb)
/* 139:    */   {
/* 140:187 */     return _appendClassSignature(sb);
/* 141:    */   }
/* 142:    */   
/* 143:    */   public StringBuilder appendErasedSignature(StringBuilder sb)
/* 144:    */   {
/* 145:192 */     return _appendErasedClassSignature(sb);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public StringBuilder appendBriefDescription(StringBuilder sb)
/* 149:    */   {
/* 150:197 */     return _appendClassDescription(sb);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public StringBuilder appendFullDescription(StringBuilder sb)
/* 154:    */   {
/* 155:203 */     sb = _appendClassDescription(sb);
/* 156:204 */     if (this._superClass != null)
/* 157:    */     {
/* 158:205 */       sb.append(" extends ");
/* 159:206 */       sb = this._superClass.appendBriefDescription(sb);
/* 160:    */     }
/* 161:209 */     int count = this._superInterfaces.length;
/* 162:210 */     if (count > 0)
/* 163:    */     {
/* 164:211 */       sb.append(" implements ");
/* 165:212 */       for (int i = 0; i < count; i++)
/* 166:    */       {
/* 167:213 */         if (i > 0) {
/* 168:214 */           sb.append(",");
/* 169:    */         }
/* 170:216 */         sb = this._superInterfaces[i].appendBriefDescription(sb);
/* 171:    */       }
/* 172:    */     }
/* 173:219 */     return sb;
/* 174:    */   }
/* 175:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.types.ResolvedObjectType
 * JD-Core Version:    0.7.0.1
 */