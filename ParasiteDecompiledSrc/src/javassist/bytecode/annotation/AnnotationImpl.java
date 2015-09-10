/*   1:    */ package javassist.bytecode.annotation;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.InvocationHandler;
/*   4:    */ import java.lang.reflect.Method;
/*   5:    */ import java.lang.reflect.Proxy;
/*   6:    */ import javassist.ClassPool;
/*   7:    */ import javassist.CtClass;
/*   8:    */ import javassist.NotFoundException;
/*   9:    */ import javassist.bytecode.AnnotationDefaultAttribute;
/*  10:    */ import javassist.bytecode.ClassFile;
/*  11:    */ import javassist.bytecode.MethodInfo;
/*  12:    */ 
/*  13:    */ public class AnnotationImpl
/*  14:    */   implements InvocationHandler
/*  15:    */ {
/*  16:    */   private static final String JDK_ANNOTATION_CLASS_NAME = "java.lang.annotation.Annotation";
/*  17: 39 */   private static Method JDK_ANNOTATION_TYPE_METHOD = null;
/*  18:    */   private Annotation annotation;
/*  19:    */   private ClassPool pool;
/*  20:    */   private ClassLoader classLoader;
/*  21:    */   private transient Class annotationType;
/*  22: 45 */   private transient int cachedHashCode = -2147483648;
/*  23:    */   
/*  24:    */   static
/*  25:    */   {
/*  26:    */     try
/*  27:    */     {
/*  28: 50 */       Class clazz = Class.forName("java.lang.annotation.Annotation");
/*  29: 51 */       JDK_ANNOTATION_TYPE_METHOD = clazz.getMethod("annotationType", (Class[])null);
/*  30:    */     }
/*  31:    */     catch (Exception ignored) {}
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Object make(ClassLoader cl, Class clazz, ClassPool cp, Annotation anon)
/*  35:    */   {
/*  36: 70 */     AnnotationImpl handler = new AnnotationImpl(anon, cp, cl);
/*  37: 71 */     return Proxy.newProxyInstance(cl, new Class[] { clazz }, handler);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private AnnotationImpl(Annotation a, ClassPool cp, ClassLoader loader)
/*  41:    */   {
/*  42: 75 */     this.annotation = a;
/*  43: 76 */     this.pool = cp;
/*  44: 77 */     this.classLoader = loader;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getTypeName()
/*  48:    */   {
/*  49: 86 */     return this.annotation.getTypeName();
/*  50:    */   }
/*  51:    */   
/*  52:    */   private Class getAnnotationType()
/*  53:    */   {
/*  54: 96 */     if (this.annotationType == null)
/*  55:    */     {
/*  56: 97 */       String typeName = this.annotation.getTypeName();
/*  57:    */       try
/*  58:    */       {
/*  59: 99 */         this.annotationType = this.classLoader.loadClass(typeName);
/*  60:    */       }
/*  61:    */       catch (ClassNotFoundException e)
/*  62:    */       {
/*  63:102 */         NoClassDefFoundError error = new NoClassDefFoundError("Error loading annotation class: " + typeName);
/*  64:103 */         error.setStackTrace(e.getStackTrace());
/*  65:104 */         throw error;
/*  66:    */       }
/*  67:    */     }
/*  68:107 */     return this.annotationType;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Annotation getAnnotation()
/*  72:    */   {
/*  73:116 */     return this.annotation;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object invoke(Object proxy, Method method, Object[] args)
/*  77:    */     throws Throwable
/*  78:    */   {
/*  79:129 */     String name = method.getName();
/*  80:130 */     if (Object.class == method.getDeclaringClass())
/*  81:    */     {
/*  82:131 */       if ("equals".equals(name))
/*  83:    */       {
/*  84:132 */         Object obj = args[0];
/*  85:133 */         return new Boolean(checkEquals(obj));
/*  86:    */       }
/*  87:135 */       if ("toString".equals(name)) {
/*  88:136 */         return this.annotation.toString();
/*  89:    */       }
/*  90:137 */       if ("hashCode".equals(name)) {
/*  91:138 */         return new Integer(hashCode());
/*  92:    */       }
/*  93:    */     }
/*  94:140 */     else if (("annotationType".equals(name)) && (method.getParameterTypes().length == 0))
/*  95:    */     {
/*  96:142 */       return getAnnotationType();
/*  97:    */     }
/*  98:144 */     MemberValue mv = this.annotation.getMemberValue(name);
/*  99:145 */     if (mv == null) {
/* 100:146 */       return getDefault(name, method);
/* 101:    */     }
/* 102:148 */     return mv.getValue(this.classLoader, this.pool, method);
/* 103:    */   }
/* 104:    */   
/* 105:    */   private Object getDefault(String name, Method method)
/* 106:    */     throws ClassNotFoundException, RuntimeException
/* 107:    */   {
/* 108:154 */     String classname = this.annotation.getTypeName();
/* 109:155 */     if (this.pool != null) {
/* 110:    */       try
/* 111:    */       {
/* 112:157 */         CtClass cc = this.pool.get(classname);
/* 113:158 */         ClassFile cf = cc.getClassFile2();
/* 114:159 */         MethodInfo minfo = cf.getMethod(name);
/* 115:160 */         if (minfo != null)
/* 116:    */         {
/* 117:161 */           AnnotationDefaultAttribute ainfo = (AnnotationDefaultAttribute)minfo.getAttribute("AnnotationDefault");
/* 118:164 */           if (ainfo != null)
/* 119:    */           {
/* 120:165 */             MemberValue mv = ainfo.getDefaultValue();
/* 121:166 */             return mv.getValue(this.classLoader, this.pool, method);
/* 122:    */           }
/* 123:    */         }
/* 124:    */       }
/* 125:    */       catch (NotFoundException e)
/* 126:    */       {
/* 127:171 */         throw new RuntimeException("cannot find a class file: " + classname);
/* 128:    */       }
/* 129:    */     }
/* 130:176 */     throw new RuntimeException("no default value: " + classname + "." + name + "()");
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int hashCode()
/* 134:    */   {
/* 135:184 */     if (this.cachedHashCode == -2147483648)
/* 136:    */     {
/* 137:185 */       int hashCode = 0;
/* 138:    */       
/* 139:    */ 
/* 140:188 */       getAnnotationType();
/* 141:    */       
/* 142:190 */       Method[] methods = this.annotationType.getDeclaredMethods();
/* 143:191 */       for (int i = 0; i < methods.length; i++)
/* 144:    */       {
/* 145:192 */         String name = methods[i].getName();
/* 146:193 */         int valueHashCode = 0;
/* 147:    */         
/* 148:    */ 
/* 149:196 */         MemberValue mv = this.annotation.getMemberValue(name);
/* 150:197 */         Object value = null;
/* 151:    */         try
/* 152:    */         {
/* 153:199 */           if (mv != null) {
/* 154:200 */             value = mv.getValue(this.classLoader, this.pool, methods[i]);
/* 155:    */           }
/* 156:201 */           if (value == null) {
/* 157:202 */             value = getDefault(name, methods[i]);
/* 158:    */           }
/* 159:    */         }
/* 160:    */         catch (RuntimeException e)
/* 161:    */         {
/* 162:205 */           throw e;
/* 163:    */         }
/* 164:    */         catch (Exception e)
/* 165:    */         {
/* 166:208 */           throw new RuntimeException("Error retrieving value " + name + " for annotation " + this.annotation.getTypeName(), e);
/* 167:    */         }
/* 168:212 */         if (value != null) {
/* 169:213 */           if (value.getClass().isArray()) {
/* 170:214 */             valueHashCode = arrayHashCode(value);
/* 171:    */           } else {
/* 172:216 */             valueHashCode = value.hashCode();
/* 173:    */           }
/* 174:    */         }
/* 175:218 */         hashCode += (127 * name.hashCode() ^ valueHashCode);
/* 176:    */       }
/* 177:221 */       this.cachedHashCode = hashCode;
/* 178:    */     }
/* 179:223 */     return this.cachedHashCode;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private boolean checkEquals(Object obj)
/* 183:    */     throws Exception
/* 184:    */   {
/* 185:234 */     if (obj == null) {
/* 186:235 */       return false;
/* 187:    */     }
/* 188:238 */     if ((obj instanceof Proxy))
/* 189:    */     {
/* 190:239 */       InvocationHandler ih = Proxy.getInvocationHandler(obj);
/* 191:240 */       if ((ih instanceof AnnotationImpl))
/* 192:    */       {
/* 193:241 */         AnnotationImpl other = (AnnotationImpl)ih;
/* 194:242 */         return this.annotation.equals(other.annotation);
/* 195:    */       }
/* 196:    */     }
/* 197:246 */     Class otherAnnotationType = (Class)JDK_ANNOTATION_TYPE_METHOD.invoke(obj, (Object[])null);
/* 198:247 */     if (!getAnnotationType().equals(otherAnnotationType)) {
/* 199:248 */       return false;
/* 200:    */     }
/* 201:250 */     Method[] methods = this.annotationType.getDeclaredMethods();
/* 202:251 */     for (int i = 0; i < methods.length; i++)
/* 203:    */     {
/* 204:252 */       String name = methods[i].getName();
/* 205:    */       
/* 206:    */ 
/* 207:255 */       MemberValue mv = this.annotation.getMemberValue(name);
/* 208:256 */       Object value = null;
/* 209:257 */       Object otherValue = null;
/* 210:    */       try
/* 211:    */       {
/* 212:259 */         if (mv != null) {
/* 213:260 */           value = mv.getValue(this.classLoader, this.pool, methods[i]);
/* 214:    */         }
/* 215:261 */         if (value == null) {
/* 216:262 */           value = getDefault(name, methods[i]);
/* 217:    */         }
/* 218:263 */         otherValue = methods[i].invoke(obj, (Object[])null);
/* 219:    */       }
/* 220:    */       catch (RuntimeException e)
/* 221:    */       {
/* 222:266 */         throw e;
/* 223:    */       }
/* 224:    */       catch (Exception e)
/* 225:    */       {
/* 226:269 */         throw new RuntimeException("Error retrieving value " + name + " for annotation " + this.annotation.getTypeName(), e);
/* 227:    */       }
/* 228:272 */       if ((value == null) && (otherValue != null)) {
/* 229:273 */         return false;
/* 230:    */       }
/* 231:274 */       if ((value != null) && (!value.equals(otherValue))) {
/* 232:275 */         return false;
/* 233:    */       }
/* 234:    */     }
/* 235:278 */     return true;
/* 236:    */   }
/* 237:    */   
/* 238:    */   private static int arrayHashCode(Object object)
/* 239:    */   {
/* 240:290 */     if (object == null) {
/* 241:291 */       return 0;
/* 242:    */     }
/* 243:293 */     int result = 1;
/* 244:    */     
/* 245:295 */     Object[] array = (Object[])object;
/* 246:296 */     for (int i = 0; i < array.length; i++)
/* 247:    */     {
/* 248:297 */       int elementHashCode = 0;
/* 249:298 */       if (array[i] != null) {
/* 250:299 */         elementHashCode = array[i].hashCode();
/* 251:    */       }
/* 252:300 */       result = 31 * result + elementHashCode;
/* 253:    */     }
/* 254:302 */     return result;
/* 255:    */   }
/* 256:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.bytecode.annotation.AnnotationImpl
 * JD-Core Version:    0.7.0.1
 */