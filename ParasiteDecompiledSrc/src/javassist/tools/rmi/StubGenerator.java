/*   1:    */ package javassist.tools.rmi;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.util.Hashtable;
/*   5:    */ import javassist.CannotCompileException;
/*   6:    */ import javassist.ClassPool;
/*   7:    */ import javassist.CtClass;
/*   8:    */ import javassist.CtConstructor;
/*   9:    */ import javassist.CtField;
/*  10:    */ import javassist.CtField.Initializer;
/*  11:    */ import javassist.CtMethod;
/*  12:    */ import javassist.CtMethod.ConstParameter;
/*  13:    */ import javassist.CtNewConstructor;
/*  14:    */ import javassist.CtNewMethod;
/*  15:    */ import javassist.Modifier;
/*  16:    */ import javassist.NotFoundException;
/*  17:    */ import javassist.Translator;
/*  18:    */ 
/*  19:    */ public class StubGenerator
/*  20:    */   implements Translator
/*  21:    */ {
/*  22:    */   private static final String fieldImporter = "importer";
/*  23:    */   private static final String fieldObjectId = "objectId";
/*  24:    */   private static final String accessorObjectId = "_getObjectId";
/*  25:    */   private static final String sampleClass = "javassist.tools.rmi.Sample";
/*  26:    */   private ClassPool classPool;
/*  27:    */   private Hashtable proxyClasses;
/*  28:    */   private CtMethod forwardMethod;
/*  29:    */   private CtMethod forwardStaticMethod;
/*  30:    */   private CtClass[] proxyConstructorParamTypes;
/*  31:    */   private CtClass[] interfacesForProxy;
/*  32:    */   private CtClass[] exceptionForProxy;
/*  33:    */   
/*  34:    */   public StubGenerator()
/*  35:    */   {
/*  36: 61 */     this.proxyClasses = new Hashtable();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void start(ClassPool pool)
/*  40:    */     throws NotFoundException
/*  41:    */   {
/*  42: 71 */     this.classPool = pool;
/*  43: 72 */     CtClass c = pool.get("javassist.tools.rmi.Sample");
/*  44: 73 */     this.forwardMethod = c.getDeclaredMethod("forward");
/*  45: 74 */     this.forwardStaticMethod = c.getDeclaredMethod("forwardStatic");
/*  46:    */     
/*  47: 76 */     this.proxyConstructorParamTypes = pool.get(new String[] { "javassist.tools.rmi.ObjectImporter", "int" });
/*  48:    */     
/*  49:    */ 
/*  50: 79 */     this.interfacesForProxy = pool.get(new String[] { "java.io.Serializable", "javassist.tools.rmi.Proxy" });
/*  51:    */     
/*  52:    */ 
/*  53: 82 */     this.exceptionForProxy = new CtClass[] { pool.get("javassist.tools.rmi.RemoteException") };
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void onLoad(ClassPool pool, String classname) {}
/*  57:    */   
/*  58:    */   public boolean isProxyClass(String name)
/*  59:    */   {
/*  60:100 */     return this.proxyClasses.get(name) != null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public synchronized boolean makeProxyClass(Class clazz)
/*  64:    */     throws CannotCompileException, NotFoundException
/*  65:    */   {
/*  66:115 */     String classname = clazz.getName();
/*  67:116 */     if (this.proxyClasses.get(classname) != null) {
/*  68:117 */       return false;
/*  69:    */     }
/*  70:119 */     CtClass ctclazz = produceProxyClass(this.classPool.get(classname), clazz);
/*  71:    */     
/*  72:121 */     this.proxyClasses.put(classname, ctclazz);
/*  73:122 */     modifySuperclass(ctclazz);
/*  74:123 */     return true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   private CtClass produceProxyClass(CtClass orgclass, Class orgRtClass)
/*  78:    */     throws CannotCompileException, NotFoundException
/*  79:    */   {
/*  80:130 */     int modify = orgclass.getModifiers();
/*  81:131 */     if ((Modifier.isAbstract(modify)) || (Modifier.isNative(modify)) || (!Modifier.isPublic(modify))) {
/*  82:133 */       throw new CannotCompileException(orgclass.getName() + " must be public, non-native, and non-abstract.");
/*  83:    */     }
/*  84:136 */     CtClass proxy = this.classPool.makeClass(orgclass.getName(), orgclass.getSuperclass());
/*  85:    */     
/*  86:    */ 
/*  87:139 */     proxy.setInterfaces(this.interfacesForProxy);
/*  88:    */     
/*  89:141 */     CtField f = new CtField(this.classPool.get("javassist.tools.rmi.ObjectImporter"), "importer", proxy);
/*  90:    */     
/*  91:    */ 
/*  92:144 */     f.setModifiers(2);
/*  93:145 */     proxy.addField(f, CtField.Initializer.byParameter(0));
/*  94:    */     
/*  95:147 */     f = new CtField(CtClass.intType, "objectId", proxy);
/*  96:148 */     f.setModifiers(2);
/*  97:149 */     proxy.addField(f, CtField.Initializer.byParameter(1));
/*  98:    */     
/*  99:151 */     proxy.addMethod(CtNewMethod.getter("_getObjectId", f));
/* 100:    */     
/* 101:153 */     proxy.addConstructor(CtNewConstructor.defaultConstructor(proxy));
/* 102:154 */     CtConstructor cons = CtNewConstructor.skeleton(this.proxyConstructorParamTypes, null, proxy);
/* 103:    */     
/* 104:    */ 
/* 105:157 */     proxy.addConstructor(cons);
/* 106:    */     try
/* 107:    */     {
/* 108:160 */       addMethods(proxy, orgRtClass.getMethods());
/* 109:161 */       return proxy;
/* 110:    */     }
/* 111:    */     catch (SecurityException e)
/* 112:    */     {
/* 113:164 */       throw new CannotCompileException(e);
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   private CtClass toCtClass(Class rtclass)
/* 118:    */     throws NotFoundException
/* 119:    */   {
/* 120:    */     String name;
/* 121:    */     String name;
/* 122:170 */     if (!rtclass.isArray())
/* 123:    */     {
/* 124:171 */       name = rtclass.getName();
/* 125:    */     }
/* 126:    */     else
/* 127:    */     {
/* 128:173 */       StringBuffer sbuf = new StringBuffer();
/* 129:    */       do
/* 130:    */       {
/* 131:175 */         sbuf.append("[]");
/* 132:176 */         rtclass = rtclass.getComponentType();
/* 133:177 */       } while (rtclass.isArray());
/* 134:178 */       sbuf.insert(0, rtclass.getName());
/* 135:179 */       name = sbuf.toString();
/* 136:    */     }
/* 137:182 */     return this.classPool.get(name);
/* 138:    */   }
/* 139:    */   
/* 140:    */   private CtClass[] toCtClass(Class[] rtclasses)
/* 141:    */     throws NotFoundException
/* 142:    */   {
/* 143:186 */     int n = rtclasses.length;
/* 144:187 */     CtClass[] ctclasses = new CtClass[n];
/* 145:188 */     for (int i = 0; i < n; i++) {
/* 146:189 */       ctclasses[i] = toCtClass(rtclasses[i]);
/* 147:    */     }
/* 148:191 */     return ctclasses;
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void addMethods(CtClass proxy, Method[] ms)
/* 152:    */     throws CannotCompileException, NotFoundException
/* 153:    */   {
/* 154:201 */     for (int i = 0; i < ms.length; i++)
/* 155:    */     {
/* 156:202 */       Method m = ms[i];
/* 157:203 */       int mod = m.getModifiers();
/* 158:204 */       if ((m.getDeclaringClass() != Object.class) && (!Modifier.isFinal(mod))) {
/* 159:206 */         if (Modifier.isPublic(mod))
/* 160:    */         {
/* 161:    */           CtMethod body;
/* 162:    */           CtMethod body;
/* 163:208 */           if (Modifier.isStatic(mod)) {
/* 164:209 */             body = this.forwardStaticMethod;
/* 165:    */           } else {
/* 166:211 */             body = this.forwardMethod;
/* 167:    */           }
/* 168:213 */           CtMethod wmethod = CtNewMethod.wrapped(toCtClass(m.getReturnType()), m.getName(), toCtClass(m.getParameterTypes()), this.exceptionForProxy, body, CtMethod.ConstParameter.integer(i), proxy);
/* 169:    */           
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:221 */           wmethod.setModifiers(mod);
/* 177:222 */           proxy.addMethod(wmethod);
/* 178:    */         }
/* 179:224 */         else if ((!Modifier.isProtected(mod)) && (!Modifier.isPrivate(mod)))
/* 180:    */         {
/* 181:227 */           throw new CannotCompileException("the methods must be public, protected, or private.");
/* 182:    */         }
/* 183:    */       }
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void modifySuperclass(CtClass orgclass)
/* 188:    */     throws CannotCompileException, NotFoundException
/* 189:    */   {
/* 190:    */     CtClass superclazz;
/* 191:239 */     for (;; orgclass = superclazz)
/* 192:    */     {
/* 193:240 */       superclazz = orgclass.getSuperclass();
/* 194:241 */       if (superclazz == null) {
/* 195:    */         break;
/* 196:    */       }
/* 197:    */       try
/* 198:    */       {
/* 199:245 */         superclazz.getDeclaredConstructor(null);
/* 200:    */       }
/* 201:    */       catch (NotFoundException e)
/* 202:    */       {
/* 203:251 */         superclazz.addConstructor(CtNewConstructor.defaultConstructor(superclazz));
/* 204:    */       }
/* 205:    */     }
/* 206:    */   }
/* 207:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.rmi.StubGenerator
 * JD-Core Version:    0.7.0.1
 */