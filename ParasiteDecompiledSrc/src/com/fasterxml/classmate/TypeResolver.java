/*   1:    */ package com.fasterxml.classmate;
/*   2:    */ 
/*   3:    */ import com.fasterxml.classmate.types.ResolvedArrayType;
/*   4:    */ import com.fasterxml.classmate.types.ResolvedInterfaceType;
/*   5:    */ import com.fasterxml.classmate.types.ResolvedObjectType;
/*   6:    */ import com.fasterxml.classmate.types.ResolvedPrimitiveType;
/*   7:    */ import com.fasterxml.classmate.types.ResolvedRecursiveType;
/*   8:    */ import com.fasterxml.classmate.types.TypePlaceHolder;
/*   9:    */ import com.fasterxml.classmate.util.ClassKey;
/*  10:    */ import com.fasterxml.classmate.util.ResolvedTypeCache;
/*  11:    */ import com.fasterxml.classmate.util.ResolvedTypeCache.Key;
/*  12:    */ import java.lang.reflect.Array;
/*  13:    */ import java.lang.reflect.GenericArrayType;
/*  14:    */ import java.lang.reflect.ParameterizedType;
/*  15:    */ import java.lang.reflect.Type;
/*  16:    */ import java.lang.reflect.TypeVariable;
/*  17:    */ import java.lang.reflect.WildcardType;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ 
/*  22:    */ public class TypeResolver
/*  23:    */ {
/*  24: 23 */   private static final ResolvedType[] NO_TYPES = new ResolvedType[0];
/*  25: 36 */   private static final ResolvedObjectType sJavaLangObject = new ResolvedObjectType(Object.class, null, null, NO_TYPES);
/*  26: 46 */   protected static final HashMap<ClassKey, ResolvedType> _primitiveTypes = new HashMap(16);
/*  27:    */   
/*  28:    */   static
/*  29:    */   {
/*  30: 47 */     for (ResolvedPrimitiveType type : ResolvedPrimitiveType.all()) {
/*  31: 48 */       _primitiveTypes.put(new ClassKey(type.getErasedType()), type);
/*  32:    */     }
/*  33: 51 */     _primitiveTypes.put(new ClassKey(Void.TYPE), ResolvedPrimitiveType.voidType());
/*  34:    */     
/*  35: 53 */     _primitiveTypes.put(new ClassKey(Object.class), sJavaLangObject);
/*  36:    */   }
/*  37:    */   
/*  38: 69 */   protected final ResolvedTypeCache _resolvedTypes = new ResolvedTypeCache(200);
/*  39:    */   
/*  40:    */   public ResolvedType resolve(Class<?> rawType)
/*  41:    */   {
/*  42: 92 */     return _fromClass(null, rawType, TypeBindings.emptyBindings());
/*  43:    */   }
/*  44:    */   
/*  45:    */   public ResolvedType resolve(Class<?> type, Class<?>... typeParameters)
/*  46:    */   {
/*  47:109 */     if ((typeParameters == null) || (typeParameters.length == 0)) {
/*  48:110 */       return resolve(type);
/*  49:    */     }
/*  50:113 */     TypeBindings bindings = TypeBindings.emptyBindings();
/*  51:    */     
/*  52:115 */     int len = typeParameters.length;
/*  53:116 */     ResolvedType[] resolvedParams = new ResolvedType[len];
/*  54:117 */     for (int i = 0; i < len; i++) {
/*  55:118 */       resolvedParams[i] = _fromClass(null, typeParameters[i], bindings);
/*  56:    */     }
/*  57:120 */     return resolve(type, resolvedParams);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public ResolvedType resolve(Class<?> type, ResolvedType... typeParameters)
/*  61:    */   {
/*  62:138 */     if ((typeParameters == null) || (typeParameters.length == 0)) {
/*  63:139 */       return resolve(type);
/*  64:    */     }
/*  65:141 */     return _fromClass(null, type, TypeBindings.create(type, typeParameters));
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ResolvedType resolve(GenericType<?> generic)
/*  69:    */   {
/*  70:155 */     ResolvedType type = _fromClass(null, generic.getClass(), TypeBindings.emptyBindings());
/*  71:156 */     ResolvedType genType = type.findSupertype(GenericType.class);
/*  72:157 */     if (genType == null) {
/*  73:158 */       throw new IllegalArgumentException("Unparameterized GenericType instance (" + generic.getClass().getName() + ")");
/*  74:    */     }
/*  75:160 */     TypeBindings b = genType.getTypeBindings();
/*  76:161 */     ResolvedType[] params = b.typeParameterArray();
/*  77:162 */     if (params.length == 0) {
/*  78:163 */       throw new IllegalArgumentException("Unparameterized GenericType instance (" + generic.getClass().getName() + ")");
/*  79:    */     }
/*  80:165 */     return params[0];
/*  81:    */   }
/*  82:    */   
/*  83:    */   public ResolvedArrayType arrayType(ResolvedType elementType)
/*  84:    */   {
/*  85:174 */     Object emptyArray = Array.newInstance(elementType.getErasedType(), 0);
/*  86:    */     
/*  87:176 */     return new ResolvedArrayType(emptyArray.getClass(), TypeBindings.emptyBindings(), sJavaLangObject, elementType);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public ResolvedType resolve(Type jdkType, TypeBindings typeBindings)
/*  91:    */   {
/*  92:190 */     return _fromAny(null, jdkType, typeBindings);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public ResolvedType resolveSubtype(ResolvedType supertype, Class<?> subtype)
/*  96:    */     throws IllegalArgumentException, UnsupportedOperationException
/*  97:    */   {
/*  98:230 */     ResolvedType refType = supertype.getSelfReferencedType();
/*  99:231 */     if (refType != null) {
/* 100:232 */       supertype = refType;
/* 101:    */     }
/* 102:235 */     if (supertype.getErasedType() == subtype) {
/* 103:236 */       return supertype;
/* 104:    */     }
/* 105:239 */     if (!supertype.canCreateSubtypes()) {
/* 106:240 */       throw new UnsupportedOperationException("Can not subtype primitive or array types (type " + supertype.getFullDescription() + ")");
/* 107:    */     }
/* 108:243 */     Class<?> superclass = supertype.getErasedType();
/* 109:244 */     if (!superclass.isAssignableFrom(subtype)) {
/* 110:245 */       throw new IllegalArgumentException("Can not sub-class " + supertype.getBriefDescription() + " into " + subtype.getName());
/* 111:    */     }
/* 112:250 */     int paramCount = subtype.getTypeParameters().length;
/* 113:    */     ResolvedType resolvedSubtype;
/* 114:    */     TypePlaceHolder[] placeholders;
/* 115:    */     ResolvedType resolvedSubtype;
/* 116:253 */     if (paramCount == 0)
/* 117:    */     {
/* 118:254 */       TypePlaceHolder[] placeholders = null;
/* 119:255 */       resolvedSubtype = resolve(subtype);
/* 120:    */     }
/* 121:    */     else
/* 122:    */     {
/* 123:257 */       placeholders = new TypePlaceHolder[paramCount];
/* 124:258 */       for (int i = 0; i < paramCount; i++) {
/* 125:259 */         placeholders[i] = new TypePlaceHolder(i);
/* 126:    */       }
/* 127:261 */       resolvedSubtype = resolve(subtype, placeholders);
/* 128:    */     }
/* 129:263 */     ResolvedType rawSupertype = resolvedSubtype.findSupertype(superclass);
/* 130:264 */     if (rawSupertype == null) {
/* 131:265 */       throw new IllegalArgumentException("Internal error: unable to locate supertype (" + subtype.getName() + ") for type " + supertype.getBriefDescription());
/* 132:    */     }
/* 133:268 */     _resolveTypePlaceholders(supertype, rawSupertype);
/* 134:271 */     if (paramCount == 0) {
/* 135:272 */       return resolvedSubtype;
/* 136:    */     }
/* 137:275 */     ResolvedType[] typeParams = new ResolvedType[paramCount];
/* 138:276 */     for (int i = 0; i < paramCount; i++)
/* 139:    */     {
/* 140:277 */       ResolvedType t = placeholders[i].actualType();
/* 141:281 */       if (t == null) {
/* 142:282 */         throw new IllegalArgumentException("Failed to find type parameter #" + (i + 1) + "/" + paramCount + " for " + subtype.getName());
/* 143:    */       }
/* 144:285 */       typeParams[i] = t;
/* 145:    */     }
/* 146:287 */     return resolve(subtype, typeParams);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static boolean isSelfReference(ResolvedType type)
/* 150:    */   {
/* 151:304 */     return type instanceof ResolvedRecursiveType;
/* 152:    */   }
/* 153:    */   
/* 154:313 */   int depth = 0;
/* 155:    */   
/* 156:    */   private ResolvedType _fromAny(ClassStack context, Type mainType, TypeBindings typeBindings)
/* 157:    */   {
/* 158:317 */     if ((mainType instanceof Class)) {
/* 159:318 */       return _fromClass(context, (Class)mainType, typeBindings);
/* 160:    */     }
/* 161:320 */     if ((mainType instanceof ParameterizedType)) {
/* 162:321 */       return _fromParamType(context, (ParameterizedType)mainType, typeBindings);
/* 163:    */     }
/* 164:323 */     if ((mainType instanceof GenericArrayType)) {
/* 165:324 */       return _fromArrayType(context, (GenericArrayType)mainType, typeBindings);
/* 166:    */     }
/* 167:326 */     if ((mainType instanceof TypeVariable)) {
/* 168:327 */       return _fromVariable(context, (TypeVariable)mainType, typeBindings);
/* 169:    */     }
/* 170:329 */     if ((mainType instanceof WildcardType)) {
/* 171:330 */       return _fromWildcard(context, (WildcardType)mainType, typeBindings);
/* 172:    */     }
/* 173:333 */     throw new IllegalArgumentException("Unrecognized type class: " + mainType.getClass().getName());
/* 174:    */   }
/* 175:    */   
/* 176:    */   private ResolvedType _fromClass(ClassStack context, Class<?> rawType, TypeBindings typeBindings)
/* 177:    */   {
/* 178:339 */     ResolvedType type = (ResolvedType)_primitiveTypes.get(new ClassKey(rawType));
/* 179:340 */     if (type != null) {
/* 180:341 */       return type;
/* 181:    */     }
/* 182:344 */     if (context == null)
/* 183:    */     {
/* 184:345 */       context = new ClassStack(rawType);
/* 185:    */     }
/* 186:    */     else
/* 187:    */     {
/* 188:347 */       ClassStack prev = context.find(rawType);
/* 189:348 */       if (prev != null)
/* 190:    */       {
/* 191:350 */         ResolvedRecursiveType selfRef = new ResolvedRecursiveType(rawType, typeBindings);
/* 192:351 */         prev.addSelfReference(selfRef);
/* 193:352 */         return selfRef;
/* 194:    */       }
/* 195:355 */       context = context.child(rawType);
/* 196:    */     }
/* 197:359 */     ResolvedType[] typeParameters = typeBindings.typeParameterArray();
/* 198:360 */     ResolvedTypeCache.Key key = this._resolvedTypes.key(rawType, typeParameters);
/* 199:    */     
/* 200:362 */     type = this._resolvedTypes.find(key);
/* 201:363 */     if (type == null)
/* 202:    */     {
/* 203:364 */       type = _constructType(context, rawType, typeBindings);
/* 204:365 */       this._resolvedTypes.put(key, type);
/* 205:    */     }
/* 206:367 */     context.resolveSelfReferences(type);
/* 207:368 */     return type;
/* 208:    */   }
/* 209:    */   
/* 210:    */   private ResolvedType _constructType(ClassStack context, Class<?> rawType, TypeBindings typeBindings)
/* 211:    */   {
/* 212:374 */     if (rawType.isArray())
/* 213:    */     {
/* 214:375 */       ResolvedType elementType = _fromAny(context, rawType.getComponentType(), typeBindings);
/* 215:376 */       return new ResolvedArrayType(rawType, typeBindings, sJavaLangObject, elementType);
/* 216:    */     }
/* 217:379 */     if (rawType.isInterface()) {
/* 218:380 */       return new ResolvedInterfaceType(rawType, typeBindings, _resolveSuperInterfaces(context, rawType, typeBindings));
/* 219:    */     }
/* 220:384 */     return new ResolvedObjectType(rawType, typeBindings, _resolveSuperClass(context, rawType, typeBindings), _resolveSuperInterfaces(context, rawType, typeBindings));
/* 221:    */   }
/* 222:    */   
/* 223:    */   private ResolvedType[] _resolveSuperInterfaces(ClassStack context, Class<?> rawType, TypeBindings typeBindings)
/* 224:    */   {
/* 225:391 */     Type[] types = rawType.getGenericInterfaces();
/* 226:392 */     if ((types == null) || (types.length == 0)) {
/* 227:393 */       return NO_TYPES;
/* 228:    */     }
/* 229:395 */     int len = types.length;
/* 230:396 */     ResolvedType[] resolved = new ResolvedType[len];
/* 231:397 */     for (int i = 0; i < len; i++) {
/* 232:398 */       resolved[i] = _fromAny(context, types[i], typeBindings);
/* 233:    */     }
/* 234:400 */     return resolved;
/* 235:    */   }
/* 236:    */   
/* 237:    */   private ResolvedObjectType _resolveSuperClass(ClassStack context, Class<?> rawType, TypeBindings typeBindings)
/* 238:    */   {
/* 239:405 */     Type parent = rawType.getGenericSuperclass();
/* 240:406 */     if (parent == null) {
/* 241:407 */       return null;
/* 242:    */     }
/* 243:409 */     ResolvedType rt = _fromAny(context, parent, typeBindings);
/* 244:    */     
/* 245:411 */     return (ResolvedObjectType)rt;
/* 246:    */   }
/* 247:    */   
/* 248:    */   private ResolvedType _fromParamType(ClassStack context, ParameterizedType ptype, TypeBindings parentBindings)
/* 249:    */   {
/* 250:420 */     Class<?> rawType = (Class)ptype.getRawType();
/* 251:421 */     Type[] params = ptype.getActualTypeArguments();
/* 252:422 */     int len = params.length;
/* 253:423 */     ResolvedType[] types = new ResolvedType[len];
/* 254:425 */     for (int i = 0; i < len; i++) {
/* 255:426 */       types[i] = _fromAny(context, params[i], parentBindings);
/* 256:    */     }
/* 257:429 */     TypeBindings newBindings = TypeBindings.create(rawType, types);
/* 258:430 */     return _fromClass(context, rawType, newBindings);
/* 259:    */   }
/* 260:    */   
/* 261:    */   private ResolvedType _fromArrayType(ClassStack context, GenericArrayType arrayType, TypeBindings typeBindings)
/* 262:    */   {
/* 263:435 */     ResolvedType elementType = _fromAny(context, arrayType.getGenericComponentType(), typeBindings);
/* 264:    */     
/* 265:437 */     Object emptyArray = Array.newInstance(elementType.getErasedType(), 0);
/* 266:438 */     return new ResolvedArrayType(emptyArray.getClass(), typeBindings, sJavaLangObject, elementType);
/* 267:    */   }
/* 268:    */   
/* 269:    */   private ResolvedType _fromWildcard(ClassStack context, WildcardType wildType, TypeBindings typeBindings)
/* 270:    */   {
/* 271:449 */     return _fromAny(context, wildType.getUpperBounds()[0], typeBindings);
/* 272:    */   }
/* 273:    */   
/* 274:    */   private ResolvedType _fromVariable(ClassStack context, TypeVariable<?> variable, TypeBindings typeBindings)
/* 275:    */   {
/* 276:455 */     String name = variable.getName();
/* 277:456 */     ResolvedType type = typeBindings.findBoundType(name);
/* 278:457 */     if (type != null) {
/* 279:458 */       return type;
/* 280:    */     }
/* 281:466 */     typeBindings = typeBindings.withAdditionalBinding(name, sJavaLangObject);
/* 282:467 */     Type[] bounds = variable.getBounds();
/* 283:468 */     return _fromAny(context, bounds[0], typeBindings);
/* 284:    */   }
/* 285:    */   
/* 286:    */   private void _resolveTypePlaceholders(ResolvedType expectedType, ResolvedType actualType)
/* 287:    */     throws IllegalArgumentException
/* 288:    */   {
/* 289:483 */     List<ResolvedType> expectedTypes = expectedType.getTypeParameters();
/* 290:484 */     List<ResolvedType> actualTypes = actualType.getTypeParameters();
/* 291:485 */     int i = 0;
/* 292:485 */     for (int len = expectedTypes.size(); i < len; i++)
/* 293:    */     {
/* 294:486 */       ResolvedType exp = (ResolvedType)expectedTypes.get(i);
/* 295:487 */       ResolvedType act = (ResolvedType)actualTypes.get(i);
/* 296:488 */       if (!_typesMatch(exp, act)) {
/* 297:489 */         throw new IllegalArgumentException("Type parameter #" + (i + 1) + "/" + len + " differs; expected " + exp.getBriefDescription() + ", got " + act.getBriefDescription());
/* 298:    */       }
/* 299:    */     }
/* 300:    */   }
/* 301:    */   
/* 302:    */   private boolean _typesMatch(ResolvedType exp, ResolvedType act)
/* 303:    */   {
/* 304:498 */     if ((act instanceof TypePlaceHolder))
/* 305:    */     {
/* 306:499 */       ((TypePlaceHolder)act).actualType(exp);
/* 307:500 */       return true;
/* 308:    */     }
/* 309:503 */     if (exp.getErasedType() != act.getErasedType()) {
/* 310:504 */       return false;
/* 311:    */     }
/* 312:507 */     List<ResolvedType> expectedTypes = exp.getTypeParameters();
/* 313:508 */     List<ResolvedType> actualTypes = act.getTypeParameters();
/* 314:509 */     int i = 0;
/* 315:509 */     for (int len = expectedTypes.size(); i < len; i++)
/* 316:    */     {
/* 317:510 */       ResolvedType exp2 = (ResolvedType)expectedTypes.get(i);
/* 318:511 */       ResolvedType act2 = (ResolvedType)actualTypes.get(i);
/* 319:512 */       if (!_typesMatch(exp2, act2)) {
/* 320:513 */         return false;
/* 321:    */       }
/* 322:    */     }
/* 323:516 */     return true;
/* 324:    */   }
/* 325:    */   
/* 326:    */   private static final class ClassStack
/* 327:    */   {
/* 328:    */     private final ClassStack _parent;
/* 329:    */     private final Class<?> _current;
/* 330:    */     private ArrayList<ResolvedRecursiveType> _selfRefs;
/* 331:    */     
/* 332:    */     public ClassStack(Class<?> rootType)
/* 333:    */     {
/* 334:537 */       this(null, rootType);
/* 335:    */     }
/* 336:    */     
/* 337:    */     private ClassStack(ClassStack parent, Class<?> curr)
/* 338:    */     {
/* 339:541 */       this._parent = parent;
/* 340:542 */       this._current = curr;
/* 341:    */     }
/* 342:    */     
/* 343:    */     public ClassStack child(Class<?> cls)
/* 344:    */     {
/* 345:550 */       return new ClassStack(this, cls);
/* 346:    */     }
/* 347:    */     
/* 348:    */     public void addSelfReference(ResolvedRecursiveType ref)
/* 349:    */     {
/* 350:559 */       if (this._selfRefs == null) {
/* 351:560 */         this._selfRefs = new ArrayList();
/* 352:    */       }
/* 353:562 */       this._selfRefs.add(ref);
/* 354:    */     }
/* 355:    */     
/* 356:    */     public void resolveSelfReferences(ResolvedType resolved)
/* 357:    */     {
/* 358:572 */       if (this._selfRefs != null) {
/* 359:573 */         for (ResolvedRecursiveType ref : this._selfRefs) {
/* 360:574 */           ref.setReference(resolved);
/* 361:    */         }
/* 362:    */       }
/* 363:    */     }
/* 364:    */     
/* 365:    */     public ClassStack find(Class<?> cls)
/* 366:    */     {
/* 367:581 */       if (this._current == cls) {
/* 368:581 */         return this;
/* 369:    */       }
/* 370:582 */       if (this._parent != null) {
/* 371:583 */         return this._parent.find(cls);
/* 372:    */       }
/* 373:585 */       return null;
/* 374:    */     }
/* 375:    */   }
/* 376:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     com.fasterxml.classmate.TypeResolver
 * JD-Core Version:    0.7.0.1
 */