/*   1:    */ package javassist.tools.reflect;
/*   2:    */ 
/*   3:    */ import javassist.CannotCompileException;
/*   4:    */ import javassist.ClassPool;
/*   5:    */ import javassist.CodeConverter;
/*   6:    */ import javassist.CtClass;
/*   7:    */ import javassist.CtField;
/*   8:    */ import javassist.CtField.Initializer;
/*   9:    */ import javassist.CtMethod;
/*  10:    */ import javassist.CtMethod.ConstParameter;
/*  11:    */ import javassist.CtNewMethod;
/*  12:    */ import javassist.Modifier;
/*  13:    */ import javassist.NotFoundException;
/*  14:    */ import javassist.Translator;
/*  15:    */ 
/*  16:    */ public class Reflection
/*  17:    */   implements Translator
/*  18:    */ {
/*  19:    */   static final String classobjectField = "_classobject";
/*  20:    */   static final String classobjectAccessor = "_getClass";
/*  21:    */   static final String metaobjectField = "_metaobject";
/*  22:    */   static final String metaobjectGetter = "_getMetaobject";
/*  23:    */   static final String metaobjectSetter = "_setMetaobject";
/*  24:    */   static final String readPrefix = "_r_";
/*  25:    */   static final String writePrefix = "_w_";
/*  26:    */   static final String metaobjectClassName = "javassist.tools.reflect.Metaobject";
/*  27:    */   static final String classMetaobjectClassName = "javassist.tools.reflect.ClassMetaobject";
/*  28:    */   protected CtMethod trapMethod;
/*  29:    */   protected CtMethod trapStaticMethod;
/*  30:    */   protected CtMethod trapRead;
/*  31:    */   protected CtMethod trapWrite;
/*  32:    */   protected CtClass[] readParam;
/*  33:    */   protected ClassPool classPool;
/*  34:    */   protected CodeConverter converter;
/*  35:    */   
/*  36:    */   private boolean isExcluded(String name)
/*  37:    */   {
/*  38: 84 */     return (name.startsWith("_m_")) || (name.equals("_getClass")) || (name.equals("_setMetaobject")) || (name.equals("_getMetaobject")) || (name.startsWith("_r_")) || (name.startsWith("_w_"));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Reflection()
/*  42:    */   {
/*  43: 96 */     this.classPool = null;
/*  44: 97 */     this.converter = new CodeConverter();
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void start(ClassPool pool)
/*  48:    */     throws NotFoundException
/*  49:    */   {
/*  50:104 */     this.classPool = pool;
/*  51:105 */     String msg = "javassist.tools.reflect.Sample is not found or broken.";
/*  52:    */     try
/*  53:    */     {
/*  54:108 */       CtClass c = this.classPool.get("javassist.tools.reflect.Sample");
/*  55:109 */       this.trapMethod = c.getDeclaredMethod("trap");
/*  56:110 */       this.trapStaticMethod = c.getDeclaredMethod("trapStatic");
/*  57:111 */       this.trapRead = c.getDeclaredMethod("trapRead");
/*  58:112 */       this.trapWrite = c.getDeclaredMethod("trapWrite");
/*  59:113 */       this.readParam = new CtClass[] { this.classPool.get("java.lang.Object") };
/*  60:    */     }
/*  61:    */     catch (NotFoundException e)
/*  62:    */     {
/*  63:117 */       throw new RuntimeException("javassist.tools.reflect.Sample is not found or broken.");
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void onLoad(ClassPool pool, String classname)
/*  68:    */     throws CannotCompileException, NotFoundException
/*  69:    */   {
/*  70:128 */     CtClass clazz = pool.get(classname);
/*  71:129 */     clazz.instrument(this.converter);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public boolean makeReflective(String classname, String metaobject, String metaclass)
/*  75:    */     throws CannotCompileException, NotFoundException
/*  76:    */   {
/*  77:149 */     return makeReflective(this.classPool.get(classname), this.classPool.get(metaobject), this.classPool.get(metaclass));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean makeReflective(Class clazz, Class metaobject, Class metaclass)
/*  81:    */     throws CannotCompileException, NotFoundException
/*  82:    */   {
/*  83:175 */     return makeReflective(clazz.getName(), metaobject.getName(), metaclass.getName());
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean makeReflective(CtClass clazz, CtClass metaobject, CtClass metaclass)
/*  87:    */     throws CannotCompileException, CannotReflectException, NotFoundException
/*  88:    */   {
/*  89:202 */     if (clazz.isInterface()) {
/*  90:203 */       throw new CannotReflectException("Cannot reflect an interface: " + clazz.getName());
/*  91:    */     }
/*  92:206 */     if (clazz.subclassOf(this.classPool.get("javassist.tools.reflect.ClassMetaobject"))) {
/*  93:207 */       throw new CannotReflectException("Cannot reflect a subclass of ClassMetaobject: " + clazz.getName());
/*  94:    */     }
/*  95:211 */     if (clazz.subclassOf(this.classPool.get("javassist.tools.reflect.Metaobject"))) {
/*  96:212 */       throw new CannotReflectException("Cannot reflect a subclass of Metaobject: " + clazz.getName());
/*  97:    */     }
/*  98:216 */     registerReflectiveClass(clazz);
/*  99:217 */     return modifyClassfile(clazz, metaobject, metaclass);
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void registerReflectiveClass(CtClass clazz)
/* 103:    */   {
/* 104:225 */     CtField[] fs = clazz.getDeclaredFields();
/* 105:226 */     for (int i = 0; i < fs.length; i++)
/* 106:    */     {
/* 107:227 */       CtField f = fs[i];
/* 108:228 */       int mod = f.getModifiers();
/* 109:229 */       if (((mod & 0x1) != 0) && ((mod & 0x10) == 0))
/* 110:    */       {
/* 111:230 */         String name = f.getName();
/* 112:231 */         this.converter.replaceFieldRead(f, clazz, "_r_" + name);
/* 113:232 */         this.converter.replaceFieldWrite(f, clazz, "_w_" + name);
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private boolean modifyClassfile(CtClass clazz, CtClass metaobject, CtClass metaclass)
/* 119:    */     throws CannotCompileException, NotFoundException
/* 120:    */   {
/* 121:241 */     if (clazz.getAttribute("Reflective") != null) {
/* 122:242 */       return false;
/* 123:    */     }
/* 124:244 */     clazz.setAttribute("Reflective", new byte[0]);
/* 125:    */     
/* 126:246 */     CtClass mlevel = this.classPool.get("javassist.tools.reflect.Metalevel");
/* 127:247 */     boolean addMeta = !clazz.subtypeOf(mlevel);
/* 128:248 */     if (addMeta) {
/* 129:249 */       clazz.addInterface(mlevel);
/* 130:    */     }
/* 131:251 */     processMethods(clazz, addMeta);
/* 132:252 */     processFields(clazz);
/* 133:255 */     if (addMeta)
/* 134:    */     {
/* 135:256 */       CtField f = new CtField(this.classPool.get("javassist.tools.reflect.Metaobject"), "_metaobject", clazz);
/* 136:    */       
/* 137:258 */       f.setModifiers(4);
/* 138:259 */       clazz.addField(f, CtField.Initializer.byNewWithParams(metaobject));
/* 139:    */       
/* 140:261 */       clazz.addMethod(CtNewMethod.getter("_getMetaobject", f));
/* 141:262 */       clazz.addMethod(CtNewMethod.setter("_setMetaobject", f));
/* 142:    */     }
/* 143:265 */     CtField f = new CtField(this.classPool.get("javassist.tools.reflect.ClassMetaobject"), "_classobject", clazz);
/* 144:    */     
/* 145:267 */     f.setModifiers(10);
/* 146:268 */     clazz.addField(f, CtField.Initializer.byNew(metaclass, new String[] { clazz.getName() }));
/* 147:    */     
/* 148:    */ 
/* 149:271 */     clazz.addMethod(CtNewMethod.getter("_getClass", f));
/* 150:272 */     return true;
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void processMethods(CtClass clazz, boolean dontSearch)
/* 154:    */     throws CannotCompileException, NotFoundException
/* 155:    */   {
/* 156:278 */     CtMethod[] ms = clazz.getMethods();
/* 157:279 */     for (int i = 0; i < ms.length; i++)
/* 158:    */     {
/* 159:280 */       CtMethod m = ms[i];
/* 160:281 */       int mod = m.getModifiers();
/* 161:282 */       if ((Modifier.isPublic(mod)) && (!Modifier.isAbstract(mod))) {
/* 162:283 */         processMethods0(mod, clazz, m, i, dontSearch);
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void processMethods0(int mod, CtClass clazz, CtMethod m, int identifier, boolean dontSearch)
/* 168:    */     throws CannotCompileException, NotFoundException
/* 169:    */   {
/* 170:292 */     String name = m.getName();
/* 171:294 */     if (isExcluded(name)) {
/* 172:    */       return;
/* 173:    */     }
/* 174:    */     CtMethod m2;
/* 175:298 */     if (m.getDeclaringClass() == clazz)
/* 176:    */     {
/* 177:299 */       if (Modifier.isNative(mod)) {
/* 178:300 */         return;
/* 179:    */       }
/* 180:302 */       CtMethod m2 = m;
/* 181:303 */       if (Modifier.isFinal(mod))
/* 182:    */       {
/* 183:304 */         mod &= 0xFFFFFFEF;
/* 184:305 */         m2.setModifiers(mod);
/* 185:    */       }
/* 186:    */     }
/* 187:    */     else
/* 188:    */     {
/* 189:309 */       if (Modifier.isFinal(mod)) {
/* 190:310 */         return;
/* 191:    */       }
/* 192:312 */       mod &= 0xFFFFFEFF;
/* 193:313 */       m2 = CtNewMethod.delegator(findOriginal(m, dontSearch), clazz);
/* 194:314 */       m2.setModifiers(mod);
/* 195:315 */       clazz.addMethod(m2);
/* 196:    */     }
/* 197:318 */     m2.setName("_m_" + identifier + "_" + name);
/* 198:    */     CtMethod body;
/* 199:    */     CtMethod body;
/* 200:321 */     if (Modifier.isStatic(mod)) {
/* 201:322 */       body = this.trapStaticMethod;
/* 202:    */     } else {
/* 203:324 */       body = this.trapMethod;
/* 204:    */     }
/* 205:326 */     CtMethod wmethod = CtNewMethod.wrapped(m.getReturnType(), name, m.getParameterTypes(), m.getExceptionTypes(), body, CtMethod.ConstParameter.integer(identifier), clazz);
/* 206:    */     
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:331 */     wmethod.setModifiers(mod);
/* 211:332 */     clazz.addMethod(wmethod);
/* 212:    */   }
/* 213:    */   
/* 214:    */   private CtMethod findOriginal(CtMethod m, boolean dontSearch)
/* 215:    */     throws NotFoundException
/* 216:    */   {
/* 217:338 */     if (dontSearch) {
/* 218:339 */       return m;
/* 219:    */     }
/* 220:341 */     String name = m.getName();
/* 221:342 */     CtMethod[] ms = m.getDeclaringClass().getDeclaredMethods();
/* 222:343 */     for (int i = 0; i < ms.length; i++)
/* 223:    */     {
/* 224:344 */       String orgName = ms[i].getName();
/* 225:345 */       if ((orgName.endsWith(name)) && (orgName.startsWith("_m_")) && (ms[i].getSignature().equals(m.getSignature()))) {
/* 226:348 */         return ms[i];
/* 227:    */       }
/* 228:    */     }
/* 229:351 */     return m;
/* 230:    */   }
/* 231:    */   
/* 232:    */   private void processFields(CtClass clazz)
/* 233:    */     throws CannotCompileException, NotFoundException
/* 234:    */   {
/* 235:357 */     CtField[] fs = clazz.getDeclaredFields();
/* 236:358 */     for (int i = 0; i < fs.length; i++)
/* 237:    */     {
/* 238:359 */       CtField f = fs[i];
/* 239:360 */       int mod = f.getModifiers();
/* 240:361 */       if (((mod & 0x1) != 0) && ((mod & 0x10) == 0))
/* 241:    */       {
/* 242:362 */         mod |= 0x8;
/* 243:363 */         String name = f.getName();
/* 244:364 */         CtClass ftype = f.getType();
/* 245:365 */         CtMethod wmethod = CtNewMethod.wrapped(ftype, "_r_" + name, this.readParam, null, this.trapRead, CtMethod.ConstParameter.string(name), clazz);
/* 246:    */         
/* 247:    */ 
/* 248:    */ 
/* 249:    */ 
/* 250:370 */         wmethod.setModifiers(mod);
/* 251:371 */         clazz.addMethod(wmethod);
/* 252:372 */         CtClass[] writeParam = new CtClass[2];
/* 253:373 */         writeParam[0] = this.classPool.get("java.lang.Object");
/* 254:374 */         writeParam[1] = ftype;
/* 255:375 */         wmethod = CtNewMethod.wrapped(CtClass.voidType, "_w_" + name, writeParam, null, this.trapWrite, CtMethod.ConstParameter.string(name), clazz);
/* 256:    */         
/* 257:    */ 
/* 258:    */ 
/* 259:379 */         wmethod.setModifiers(mod);
/* 260:380 */         clazz.addMethod(wmethod);
/* 261:    */       }
/* 262:    */     }
/* 263:    */   }
/* 264:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.Reflection
 * JD-Core Version:    0.7.0.1
 */