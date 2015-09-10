/*   1:    */ package org.hibernate.bytecode.internal.javassist;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.lang.reflect.Modifier;
/*   5:    */ import java.security.ProtectionDomain;
/*   6:    */ import javassist.CannotCompileException;
/*   7:    */ import javassist.bytecode.Bytecode;
/*   8:    */ import javassist.bytecode.ClassFile;
/*   9:    */ import javassist.bytecode.ConstPool;
/*  10:    */ import javassist.bytecode.MethodInfo;
/*  11:    */ import javassist.util.proxy.FactoryHelper;
/*  12:    */ import javassist.util.proxy.RuntimeSupport;
/*  13:    */ 
/*  14:    */ class BulkAccessorFactory
/*  15:    */ {
/*  16:    */   private static final String PACKAGE_NAME_PREFIX = "org.javassist.tmp.";
/*  17: 48 */   private static final String BULKACESSOR_CLASS_NAME = BulkAccessor.class.getName();
/*  18: 49 */   private static final String OBJECT_CLASS_NAME = Object.class.getName();
/*  19:    */   private static final String GENERATED_GETTER_NAME = "getPropertyValues";
/*  20:    */   private static final String GENERATED_SETTER_NAME = "setPropertyValues";
/*  21:    */   private static final String GET_SETTER_DESC = "(Ljava/lang/Object;[Ljava/lang/Object;)V";
/*  22: 53 */   private static final String THROWABLE_CLASS_NAME = Throwable.class.getName();
/*  23: 54 */   private static final String BULKEXCEPTION_CLASS_NAME = BulkAccessorException.class.getName();
/*  24: 55 */   private static int counter = 0;
/*  25:    */   private Class targetBean;
/*  26:    */   private String[] getterNames;
/*  27:    */   private String[] setterNames;
/*  28:    */   private Class[] types;
/*  29:    */   public String writeDirectory;
/*  30:    */   
/*  31:    */   BulkAccessorFactory(Class target, String[] getterNames, String[] setterNames, Class[] types)
/*  32:    */   {
/*  33: 68 */     this.targetBean = target;
/*  34: 69 */     this.getterNames = getterNames;
/*  35: 70 */     this.setterNames = setterNames;
/*  36: 71 */     this.types = types;
/*  37: 72 */     this.writeDirectory = null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   BulkAccessor create()
/*  41:    */   {
/*  42: 76 */     Method[] getters = new Method[this.getterNames.length];
/*  43: 77 */     Method[] setters = new Method[this.setterNames.length];
/*  44: 78 */     findAccessors(this.targetBean, this.getterNames, this.setterNames, this.types, getters, setters);
/*  45:    */     try
/*  46:    */     {
/*  47: 82 */       ClassFile classfile = make(getters, setters);
/*  48: 83 */       ClassLoader loader = getClassLoader();
/*  49: 84 */       if (this.writeDirectory != null) {
/*  50: 85 */         FactoryHelper.writeFile(classfile, this.writeDirectory);
/*  51:    */       }
/*  52: 88 */       Class beanClass = FactoryHelper.toClass(classfile, loader, getDomain());
/*  53: 89 */       return (BulkAccessor)newInstance(beanClass);
/*  54:    */     }
/*  55:    */     catch (Exception e)
/*  56:    */     {
/*  57: 92 */       throw new BulkAccessorException(e.getMessage(), e);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   private ProtectionDomain getDomain()
/*  62:    */   {
/*  63:    */     Class cl;
/*  64:    */     Class cl;
/*  65: 98 */     if (this.targetBean != null) {
/*  66: 99 */       cl = this.targetBean;
/*  67:    */     } else {
/*  68:102 */       cl = getClass();
/*  69:    */     }
/*  70:104 */     return cl.getProtectionDomain();
/*  71:    */   }
/*  72:    */   
/*  73:    */   private ClassFile make(Method[] getters, Method[] setters)
/*  74:    */     throws CannotCompileException
/*  75:    */   {
/*  76:108 */     String className = this.targetBean.getName();
/*  77:    */     
/*  78:110 */     className = className + "_$$_bulkaccess_" + counter++;
/*  79:111 */     if (className.startsWith("java.")) {
/*  80:112 */       className = "org.javassist.tmp." + className;
/*  81:    */     }
/*  82:115 */     ClassFile classfile = new ClassFile(false, className, BULKACESSOR_CLASS_NAME);
/*  83:116 */     classfile.setAccessFlags(1);
/*  84:117 */     addDefaultConstructor(classfile);
/*  85:118 */     addGetter(classfile, getters);
/*  86:119 */     addSetter(classfile, setters);
/*  87:120 */     return classfile;
/*  88:    */   }
/*  89:    */   
/*  90:    */   private ClassLoader getClassLoader()
/*  91:    */   {
/*  92:124 */     if ((this.targetBean != null) && (this.targetBean.getName().equals(OBJECT_CLASS_NAME))) {
/*  93:125 */       return this.targetBean.getClassLoader();
/*  94:    */     }
/*  95:128 */     return getClass().getClassLoader();
/*  96:    */   }
/*  97:    */   
/*  98:    */   private Object newInstance(Class type)
/*  99:    */     throws Exception
/* 100:    */   {
/* 101:133 */     BulkAccessor instance = (BulkAccessor)type.newInstance();
/* 102:134 */     instance.target = this.targetBean;
/* 103:135 */     int len = this.getterNames.length;
/* 104:136 */     instance.getters = new String[len];
/* 105:137 */     instance.setters = new String[len];
/* 106:138 */     instance.types = new Class[len];
/* 107:139 */     for (int i = 0; i < len; i++)
/* 108:    */     {
/* 109:140 */       instance.getters[i] = this.getterNames[i];
/* 110:141 */       instance.setters[i] = this.setterNames[i];
/* 111:142 */       instance.types[i] = this.types[i];
/* 112:    */     }
/* 113:145 */     return instance;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void addDefaultConstructor(ClassFile classfile)
/* 117:    */     throws CannotCompileException
/* 118:    */   {
/* 119:155 */     ConstPool cp = classfile.getConstPool();
/* 120:156 */     String cons_desc = "()V";
/* 121:157 */     MethodInfo mi = new MethodInfo(cp, "<init>", cons_desc);
/* 122:    */     
/* 123:159 */     Bytecode code = new Bytecode(cp, 0, 1);
/* 124:    */     
/* 125:161 */     code.addAload(0);
/* 126:    */     
/* 127:163 */     code.addInvokespecial(BulkAccessor.class.getName(), "<init>", cons_desc);
/* 128:    */     
/* 129:165 */     code.addOpcode(177);
/* 130:    */     
/* 131:167 */     mi.setCodeAttribute(code.toCodeAttribute());
/* 132:168 */     mi.setAccessFlags(1);
/* 133:169 */     classfile.addMethod(mi);
/* 134:    */   }
/* 135:    */   
/* 136:    */   private void addGetter(ClassFile classfile, Method[] getters)
/* 137:    */     throws CannotCompileException
/* 138:    */   {
/* 139:173 */     ConstPool cp = classfile.getConstPool();
/* 140:174 */     int target_type_index = cp.addClassInfo(this.targetBean.getName());
/* 141:175 */     String desc = "(Ljava/lang/Object;[Ljava/lang/Object;)V";
/* 142:176 */     MethodInfo mi = new MethodInfo(cp, "getPropertyValues", desc);
/* 143:    */     
/* 144:178 */     Bytecode code = new Bytecode(cp, 6, 4);
/* 145:180 */     if (getters.length >= 0)
/* 146:    */     {
/* 147:182 */       code.addAload(1);
/* 148:    */       
/* 149:184 */       code.addCheckcast(this.targetBean.getName());
/* 150:    */       
/* 151:186 */       code.addAstore(3);
/* 152:187 */       for (int i = 0; i < getters.length; i++) {
/* 153:188 */         if (getters[i] != null)
/* 154:    */         {
/* 155:189 */           Method getter = getters[i];
/* 156:    */           
/* 157:191 */           code.addAload(2);
/* 158:    */           
/* 159:193 */           code.addIconst(i);
/* 160:194 */           Class returnType = getter.getReturnType();
/* 161:195 */           int typeIndex = -1;
/* 162:196 */           if (returnType.isPrimitive())
/* 163:    */           {
/* 164:197 */             typeIndex = FactoryHelper.typeIndex(returnType);
/* 165:    */             
/* 166:199 */             code.addNew(FactoryHelper.wrapperTypes[typeIndex]);
/* 167:    */             
/* 168:201 */             code.addOpcode(89);
/* 169:    */           }
/* 170:205 */           code.addAload(3);
/* 171:206 */           String getter_desc = RuntimeSupport.makeDescriptor(getter);
/* 172:207 */           String getterName = getter.getName();
/* 173:208 */           if (this.targetBean.isInterface()) {
/* 174:210 */             code.addInvokeinterface(target_type_index, getterName, getter_desc, 1);
/* 175:    */           } else {
/* 176:214 */             code.addInvokevirtual(target_type_index, getterName, getter_desc);
/* 177:    */           }
/* 178:217 */           if (typeIndex >= 0) {
/* 179:219 */             code.addInvokespecial(FactoryHelper.wrapperTypes[typeIndex], "<init>", FactoryHelper.wrapperDesc[typeIndex]);
/* 180:    */           }
/* 181:227 */           code.add(83);
/* 182:228 */           code.growStack(-3);
/* 183:    */         }
/* 184:    */       }
/* 185:    */     }
/* 186:233 */     code.addOpcode(177);
/* 187:    */     
/* 188:235 */     mi.setCodeAttribute(code.toCodeAttribute());
/* 189:236 */     mi.setAccessFlags(1);
/* 190:237 */     classfile.addMethod(mi);
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void addSetter(ClassFile classfile, Method[] setters)
/* 194:    */     throws CannotCompileException
/* 195:    */   {
/* 196:241 */     ConstPool cp = classfile.getConstPool();
/* 197:242 */     int target_type_index = cp.addClassInfo(this.targetBean.getName());
/* 198:243 */     String desc = "(Ljava/lang/Object;[Ljava/lang/Object;)V";
/* 199:244 */     MethodInfo mi = new MethodInfo(cp, "setPropertyValues", desc);
/* 200:    */     
/* 201:246 */     Bytecode code = new Bytecode(cp, 4, 6);
/* 202:248 */     if (setters.length > 0)
/* 203:    */     {
/* 204:251 */       code.addIconst(0);
/* 205:    */       
/* 206:253 */       code.addIstore(3);
/* 207:    */       
/* 208:255 */       code.addAload(1);
/* 209:    */       
/* 210:257 */       code.addCheckcast(this.targetBean.getName());
/* 211:    */       
/* 212:259 */       code.addAstore(4);
/* 213:    */       
/* 214:    */ 
/* 215:262 */       int start = code.currentPc();
/* 216:263 */       int lastIndex = 0;
/* 217:264 */       for (int i = 0; i < setters.length; i++)
/* 218:    */       {
/* 219:265 */         if (setters[i] != null)
/* 220:    */         {
/* 221:266 */           int diff = i - lastIndex;
/* 222:267 */           if (diff > 0)
/* 223:    */           {
/* 224:269 */             code.addOpcode(132);
/* 225:270 */             code.add(3);
/* 226:271 */             code.add(diff);
/* 227:272 */             lastIndex = i;
/* 228:    */           }
/* 229:    */         }
/* 230:277 */         code.addAload(4);
/* 231:    */         
/* 232:279 */         code.addAload(2);
/* 233:    */         
/* 234:281 */         code.addIconst(i);
/* 235:    */         
/* 236:283 */         code.addOpcode(50);
/* 237:    */         
/* 238:285 */         Class[] setterParamTypes = setters[i].getParameterTypes();
/* 239:286 */         Class setterParamType = setterParamTypes[0];
/* 240:287 */         if (setterParamType.isPrimitive()) {
/* 241:290 */           addUnwrapper(classfile, code, setterParamType);
/* 242:    */         } else {
/* 243:294 */           code.addCheckcast(setterParamType.getName());
/* 244:    */         }
/* 245:297 */         String rawSetterMethod_desc = RuntimeSupport.makeDescriptor(setters[i]);
/* 246:298 */         if (!this.targetBean.isInterface())
/* 247:    */         {
/* 248:300 */           code.addInvokevirtual(target_type_index, setters[i].getName(), rawSetterMethod_desc);
/* 249:    */         }
/* 250:    */         else
/* 251:    */         {
/* 252:304 */           Class[] params = setters[i].getParameterTypes();
/* 253:    */           int size;
/* 254:    */           int size;
/* 255:306 */           if ((params[0].equals(Double.TYPE)) || (params[0].equals(Long.TYPE))) {
/* 256:307 */             size = 3;
/* 257:    */           } else {
/* 258:310 */             size = 2;
/* 259:    */           }
/* 260:313 */           code.addInvokeinterface(target_type_index, setters[i].getName(), rawSetterMethod_desc, size);
/* 261:    */         }
/* 262:    */       }
/* 263:318 */       int end = code.currentPc();
/* 264:    */       
/* 265:320 */       code.addOpcode(177);
/* 266:    */       
/* 267:    */ 
/* 268:323 */       int throwableType_index = cp.addClassInfo(THROWABLE_CLASS_NAME);
/* 269:324 */       code.addExceptionHandler(start, end, code.currentPc(), throwableType_index);
/* 270:    */       
/* 271:326 */       code.addAstore(5);
/* 272:    */       
/* 273:328 */       code.addNew(BULKEXCEPTION_CLASS_NAME);
/* 274:    */       
/* 275:330 */       code.addOpcode(89);
/* 276:    */       
/* 277:332 */       code.addAload(5);
/* 278:    */       
/* 279:334 */       code.addIload(3);
/* 280:    */       
/* 281:336 */       String cons_desc = "(Ljava/lang/Throwable;I)V";
/* 282:337 */       code.addInvokespecial(BULKEXCEPTION_CLASS_NAME, "<init>", cons_desc);
/* 283:    */       
/* 284:339 */       code.addOpcode(191);
/* 285:    */     }
/* 286:    */     else
/* 287:    */     {
/* 288:343 */       code.addOpcode(177);
/* 289:    */     }
/* 290:346 */     mi.setCodeAttribute(code.toCodeAttribute());
/* 291:347 */     mi.setAccessFlags(1);
/* 292:348 */     classfile.addMethod(mi);
/* 293:    */   }
/* 294:    */   
/* 295:    */   private void addUnwrapper(ClassFile classfile, Bytecode code, Class type)
/* 296:    */   {
/* 297:355 */     int index = FactoryHelper.typeIndex(type);
/* 298:356 */     String wrapperType = FactoryHelper.wrapperTypes[index];
/* 299:    */     
/* 300:358 */     code.addCheckcast(wrapperType);
/* 301:    */     
/* 302:360 */     code.addInvokevirtual(wrapperType, FactoryHelper.unwarpMethods[index], FactoryHelper.unwrapDesc[index]);
/* 303:    */   }
/* 304:    */   
/* 305:    */   private static void findAccessors(Class clazz, String[] getterNames, String[] setterNames, Class[] types, Method[] getters, Method[] setters)
/* 306:    */   {
/* 307:370 */     int length = types.length;
/* 308:371 */     if ((setterNames.length != length) || (getterNames.length != length)) {
/* 309:372 */       throw new BulkAccessorException("bad number of accessors");
/* 310:    */     }
/* 311:375 */     Class[] getParam = new Class[0];
/* 312:376 */     Class[] setParam = new Class[1];
/* 313:377 */     for (int i = 0; i < length; i++)
/* 314:    */     {
/* 315:378 */       if (getterNames[i] != null)
/* 316:    */       {
/* 317:379 */         Method getter = findAccessor(clazz, getterNames[i], getParam, i);
/* 318:380 */         if (getter.getReturnType() != types[i]) {
/* 319:381 */           throw new BulkAccessorException("wrong return type: " + getterNames[i], i);
/* 320:    */         }
/* 321:384 */         getters[i] = getter;
/* 322:    */       }
/* 323:387 */       if (setterNames[i] != null)
/* 324:    */       {
/* 325:388 */         setParam[0] = types[i];
/* 326:389 */         setters[i] = findAccessor(clazz, setterNames[i], setParam, i);
/* 327:    */       }
/* 328:    */     }
/* 329:    */   }
/* 330:    */   
/* 331:    */   private static Method findAccessor(Class clazz, String name, Class[] params, int index)
/* 332:    */     throws BulkAccessorException
/* 333:    */   {
/* 334:    */     try
/* 335:    */     {
/* 336:400 */       Method method = clazz.getDeclaredMethod(name, params);
/* 337:401 */       if (Modifier.isPrivate(method.getModifiers())) {
/* 338:402 */         throw new BulkAccessorException("private property", index);
/* 339:    */       }
/* 340:405 */       return method;
/* 341:    */     }
/* 342:    */     catch (NoSuchMethodException e)
/* 343:    */     {
/* 344:408 */       throw new BulkAccessorException("cannot find an accessor", index);
/* 345:    */     }
/* 346:    */   }
/* 347:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.bytecode.internal.javassist.BulkAccessorFactory
 * JD-Core Version:    0.7.0.1
 */