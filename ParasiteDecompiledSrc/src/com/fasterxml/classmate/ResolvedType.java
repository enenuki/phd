/*   1:    */ package com.fasterxml.classmate;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.members.RawConstructor;
/*   4:    */ import com.fasterxml.classmate.members.RawField;
/*   5:    */ import com.fasterxml.classmate.members.RawMethod;
/*   6:    */ import java.lang.reflect.Constructor;
/*   7:    */ import java.lang.reflect.Field;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ import java.lang.reflect.Modifier;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collections;
/*  12:    */ import java.util.List;
/*  13:    */ 
/*  14:    */ public abstract class ResolvedType
/*  15:    */ {
/*  16: 13 */   protected static final ResolvedType[] NO_TYPES = new ResolvedType[0];
/*  17: 15 */   protected static final RawConstructor[] NO_CONSTRUCTORS = new RawConstructor[0];
/*  18: 16 */   protected static final RawField[] NO_FIELDS = new RawField[0];
/*  19: 17 */   protected static final RawMethod[] NO_METHODS = new RawMethod[0];
/*  20:    */   protected final Class<?> _erasedType;
/*  21:    */   protected final TypeBindings _typeBindings;
/*  22:    */   
/*  23:    */   protected ResolvedType(Class<?> cls, TypeBindings bindings)
/*  24:    */   {
/*  25: 35 */     this._erasedType = cls;
/*  26: 36 */     this._typeBindings = (bindings == null ? TypeBindings.emptyBindings() : bindings);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public abstract boolean canCreateSubtypes();
/*  30:    */   
/*  31:    */   public final boolean canCreateSubtype(Class<?> subtype)
/*  32:    */   {
/*  33: 51 */     return (canCreateSubtypes()) && (this._erasedType.isAssignableFrom(subtype));
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Class<?> getErasedType()
/*  37:    */   {
/*  38: 63 */     return this._erasedType;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public abstract ResolvedType getParentClass();
/*  42:    */   
/*  43:    */   public abstract ResolvedType getSelfReferencedType();
/*  44:    */   
/*  45:    */   public abstract ResolvedType getArrayElementType();
/*  46:    */   
/*  47:    */   public abstract List<ResolvedType> getImplementedInterfaces();
/*  48:    */   
/*  49:    */   public List<ResolvedType> getTypeParameters()
/*  50:    */   {
/*  51:102 */     return this._typeBindings.getTypeParameters();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public TypeBindings getTypeBindings()
/*  55:    */   {
/*  56:113 */     return this._typeBindings;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public List<ResolvedType> typeParametersFor(Class<?> erasedSupertype)
/*  60:    */   {
/*  61:125 */     ResolvedType type = findSupertype(erasedSupertype);
/*  62:126 */     if (type != null) {
/*  63:127 */       return type.getTypeParameters();
/*  64:    */     }
/*  65:130 */     return null;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ResolvedType findSupertype(Class<?> erasedSupertype)
/*  69:    */   {
/*  70:142 */     if (erasedSupertype == this._erasedType) {
/*  71:143 */       return this;
/*  72:    */     }
/*  73:146 */     if (erasedSupertype.isInterface()) {
/*  74:147 */       for (ResolvedType it : getImplementedInterfaces())
/*  75:    */       {
/*  76:148 */         ResolvedType type = it.findSupertype(erasedSupertype);
/*  77:149 */         if (type != null) {
/*  78:150 */           return type;
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82:155 */     ResolvedType pc = getParentClass();
/*  83:156 */     if (pc != null)
/*  84:    */     {
/*  85:157 */       ResolvedType type = pc.findSupertype(erasedSupertype);
/*  86:158 */       if (type != null) {
/*  87:159 */         return type;
/*  88:    */       }
/*  89:    */     }
/*  90:163 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public abstract boolean isInterface();
/*  94:    */   
/*  95:    */   public final boolean isConcrete()
/*  96:    */   {
/*  97:174 */     return !isAbstract();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public abstract boolean isAbstract();
/* 101:    */   
/* 102:    */   public abstract boolean isArray();
/* 103:    */   
/* 104:    */   public abstract boolean isPrimitive();
/* 105:    */   
/* 106:    */   public final boolean isInstanceOf(Class<?> type)
/* 107:    */   {
/* 108:190 */     return type.isAssignableFrom(this._erasedType);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public List<RawConstructor> getConstructors()
/* 112:    */   {
/* 113:199 */     return Collections.emptyList();
/* 114:    */   }
/* 115:    */   
/* 116:    */   public List<RawField> getMemberFields()
/* 117:    */   {
/* 118:200 */     return Collections.emptyList();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<RawMethod> getMemberMethods()
/* 122:    */   {
/* 123:201 */     return Collections.emptyList();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<RawField> getStaticFields()
/* 127:    */   {
/* 128:202 */     return Collections.emptyList();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<RawMethod> getStaticMethods()
/* 132:    */   {
/* 133:203 */     return Collections.emptyList();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getSignature()
/* 137:    */   {
/* 138:216 */     StringBuilder sb = new StringBuilder();
/* 139:217 */     return appendSignature(sb).toString();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getErasedSignature()
/* 143:    */   {
/* 144:225 */     StringBuilder sb = new StringBuilder();
/* 145:226 */     return appendErasedSignature(sb).toString();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getFullDescription()
/* 149:    */   {
/* 150:234 */     StringBuilder sb = new StringBuilder();
/* 151:235 */     return appendFullDescription(sb).toString();
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String getBriefDescription()
/* 155:    */   {
/* 156:243 */     StringBuilder sb = new StringBuilder();
/* 157:244 */     return appendBriefDescription(sb).toString();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public abstract StringBuilder appendBriefDescription(StringBuilder paramStringBuilder);
/* 161:    */   
/* 162:    */   public abstract StringBuilder appendFullDescription(StringBuilder paramStringBuilder);
/* 163:    */   
/* 164:    */   public abstract StringBuilder appendSignature(StringBuilder paramStringBuilder);
/* 165:    */   
/* 166:    */   public abstract StringBuilder appendErasedSignature(StringBuilder paramStringBuilder);
/* 167:    */   
/* 168:    */   public String toString()
/* 169:    */   {
/* 170:259 */     return getBriefDescription();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public int hashCode()
/* 174:    */   {
/* 175:263 */     return this._erasedType.getName().hashCode() + this._typeBindings.hashCode();
/* 176:    */   }
/* 177:    */   
/* 178:    */   public boolean equals(Object o)
/* 179:    */   {
/* 180:268 */     if (o == this) {
/* 181:268 */       return true;
/* 182:    */     }
/* 183:270 */     if ((o == null) || (o.getClass() != getClass())) {
/* 184:270 */       return false;
/* 185:    */     }
/* 186:272 */     ResolvedType other = (ResolvedType)o;
/* 187:273 */     if (other._erasedType != this._erasedType) {
/* 188:274 */       return false;
/* 189:    */     }
/* 190:277 */     return this._typeBindings.equals(other._typeBindings);
/* 191:    */   }
/* 192:    */   
/* 193:    */   protected StringBuilder _appendClassSignature(StringBuilder sb)
/* 194:    */   {
/* 195:288 */     sb.append('L');
/* 196:289 */     sb = _appendClassName(sb);
/* 197:290 */     int count = this._typeBindings.size();
/* 198:291 */     if (count > 0)
/* 199:    */     {
/* 200:292 */       sb.append('<');
/* 201:293 */       for (int i = 0; i < count; i++) {
/* 202:294 */         sb = this._typeBindings.getBoundType(i).appendErasedSignature(sb);
/* 203:    */       }
/* 204:296 */       sb.append('>');
/* 205:    */     }
/* 206:298 */     sb.append(';');
/* 207:299 */     return sb;
/* 208:    */   }
/* 209:    */   
/* 210:    */   protected StringBuilder _appendErasedClassSignature(StringBuilder sb)
/* 211:    */   {
/* 212:304 */     sb.append('L');
/* 213:305 */     sb = _appendClassName(sb);
/* 214:306 */     sb.append(';');
/* 215:307 */     return sb;
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected StringBuilder _appendClassDescription(StringBuilder sb)
/* 219:    */   {
/* 220:312 */     sb.append(this._erasedType.getName());
/* 221:313 */     int count = this._typeBindings.size();
/* 222:314 */     if (count > 0)
/* 223:    */     {
/* 224:315 */       sb.append('<');
/* 225:316 */       for (int i = 0; i < count; i++)
/* 226:    */       {
/* 227:317 */         if (i > 0) {
/* 228:318 */           sb.append(',');
/* 229:    */         }
/* 230:320 */         sb = this._typeBindings.getBoundType(i).appendBriefDescription(sb);
/* 231:    */       }
/* 232:322 */       sb.append('>');
/* 233:    */     }
/* 234:324 */     return sb;
/* 235:    */   }
/* 236:    */   
/* 237:    */   protected StringBuilder _appendClassName(StringBuilder sb)
/* 238:    */   {
/* 239:329 */     String name = this._erasedType.getName();
/* 240:330 */     int i = 0;
/* 241:330 */     for (int len = name.length(); i < len; i++)
/* 242:    */     {
/* 243:331 */       char c = name.charAt(i);
/* 244:332 */       if (c == '.') {
/* 245:332 */         c = '/';
/* 246:    */       }
/* 247:333 */       sb.append(c);
/* 248:    */     }
/* 249:335 */     return sb;
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected RawField[] _getFields(boolean statics)
/* 253:    */   {
/* 254:349 */     ArrayList<RawField> fields = new ArrayList();
/* 255:350 */     for (Field f : this._erasedType.getDeclaredFields()) {
/* 256:352 */       if ((!f.isSynthetic()) && 
/* 257:353 */         (Modifier.isStatic(f.getModifiers()) == statics)) {
/* 258:354 */         fields.add(new RawField(this, f));
/* 259:    */       }
/* 260:    */     }
/* 261:358 */     if (fields.isEmpty()) {
/* 262:359 */       return NO_FIELDS;
/* 263:    */     }
/* 264:361 */     return (RawField[])fields.toArray(new RawField[fields.size()]);
/* 265:    */   }
/* 266:    */   
/* 267:    */   protected RawMethod[] _getMethods(boolean statics)
/* 268:    */   {
/* 269:369 */     ArrayList<RawMethod> methods = new ArrayList();
/* 270:370 */     for (Method m : this._erasedType.getDeclaredMethods()) {
/* 271:372 */       if ((!m.isSynthetic()) && 
/* 272:373 */         (Modifier.isStatic(m.getModifiers()) == statics)) {
/* 273:374 */         methods.add(new RawMethod(this, m));
/* 274:    */       }
/* 275:    */     }
/* 276:378 */     if (methods.isEmpty()) {
/* 277:379 */       return NO_METHODS;
/* 278:    */     }
/* 279:381 */     return (RawMethod[])methods.toArray(new RawMethod[methods.size()]);
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected RawConstructor[] _getConstructors()
/* 283:    */   {
/* 284:386 */     ArrayList<RawConstructor> ctors = new ArrayList();
/* 285:387 */     for (Constructor<?> c : this._erasedType.getDeclaredConstructors()) {
/* 286:389 */       if (!c.isSynthetic()) {
/* 287:390 */         ctors.add(new RawConstructor(this, c));
/* 288:    */       }
/* 289:    */     }
/* 290:393 */     if (ctors.isEmpty()) {
/* 291:394 */       return NO_CONSTRUCTORS;
/* 292:    */     }
/* 293:396 */     return (RawConstructor[])ctors.toArray(new RawConstructor[ctors.size()]);
/* 294:    */   }
/* 295:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.ResolvedType
 * JD-Core Version:    0.7.0.1
 */