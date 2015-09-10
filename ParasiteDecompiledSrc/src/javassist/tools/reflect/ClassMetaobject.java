/*   1:    */ package javassist.tools.reflect;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.lang.reflect.Constructor;
/*   8:    */ import java.lang.reflect.Field;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.util.Arrays;
/*  12:    */ 
/*  13:    */ public class ClassMetaobject
/*  14:    */   implements Serializable
/*  15:    */ {
/*  16:    */   static final String methodPrefix = "_m_";
/*  17:    */   static final int methodPrefixLen = 3;
/*  18:    */   private Class javaClass;
/*  19:    */   private Constructor[] constructors;
/*  20:    */   private Method[] methods;
/*  21: 62 */   public static boolean useContextClassLoader = false;
/*  22:    */   
/*  23:    */   public ClassMetaobject(String[] params)
/*  24:    */   {
/*  25:    */     try
/*  26:    */     {
/*  27: 73 */       this.javaClass = getClassObject(params[0]);
/*  28:    */     }
/*  29:    */     catch (ClassNotFoundException e)
/*  30:    */     {
/*  31: 76 */       throw new RuntimeException("not found: " + params[0] + ", useContextClassLoader: " + Boolean.toString(useContextClassLoader), e);
/*  32:    */     }
/*  33: 81 */     this.constructors = this.javaClass.getConstructors();
/*  34: 82 */     this.methods = null;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private void writeObject(ObjectOutputStream out)
/*  38:    */     throws IOException
/*  39:    */   {
/*  40: 86 */     out.writeUTF(this.javaClass.getName());
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void readObject(ObjectInputStream in)
/*  44:    */     throws IOException, ClassNotFoundException
/*  45:    */   {
/*  46: 92 */     this.javaClass = getClassObject(in.readUTF());
/*  47: 93 */     this.constructors = this.javaClass.getConstructors();
/*  48: 94 */     this.methods = null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   private Class getClassObject(String name)
/*  52:    */     throws ClassNotFoundException
/*  53:    */   {
/*  54: 98 */     if (useContextClassLoader) {
/*  55: 99 */       return Thread.currentThread().getContextClassLoader().loadClass(name);
/*  56:    */     }
/*  57:102 */     return Class.forName(name);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public final Class getJavaClass()
/*  61:    */   {
/*  62:109 */     return this.javaClass;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public final String getName()
/*  66:    */   {
/*  67:116 */     return this.javaClass.getName();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public final boolean isInstance(Object obj)
/*  71:    */   {
/*  72:123 */     return this.javaClass.isInstance(obj);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public final Object newInstance(Object[] args)
/*  76:    */     throws CannotCreateException
/*  77:    */   {
/*  78:134 */     int n = this.constructors.length;
/*  79:135 */     for (int i = 0; i < n; i++) {
/*  80:    */       try
/*  81:    */       {
/*  82:137 */         return this.constructors[i].newInstance(args);
/*  83:    */       }
/*  84:    */       catch (IllegalArgumentException e) {}catch (InstantiationException e)
/*  85:    */       {
/*  86:143 */         throw new CannotCreateException(e);
/*  87:    */       }
/*  88:    */       catch (IllegalAccessException e)
/*  89:    */       {
/*  90:146 */         throw new CannotCreateException(e);
/*  91:    */       }
/*  92:    */       catch (InvocationTargetException e)
/*  93:    */       {
/*  94:149 */         throw new CannotCreateException(e);
/*  95:    */       }
/*  96:    */     }
/*  97:153 */     throw new CannotCreateException("no constructor matches");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Object trapFieldRead(String name)
/* 101:    */   {
/* 102:164 */     Class jc = getJavaClass();
/* 103:    */     try
/* 104:    */     {
/* 105:166 */       return jc.getField(name).get(null);
/* 106:    */     }
/* 107:    */     catch (NoSuchFieldException e)
/* 108:    */     {
/* 109:169 */       throw new RuntimeException(e.toString());
/* 110:    */     }
/* 111:    */     catch (IllegalAccessException e)
/* 112:    */     {
/* 113:172 */       throw new RuntimeException(e.toString());
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void trapFieldWrite(String name, Object value)
/* 118:    */   {
/* 119:184 */     Class jc = getJavaClass();
/* 120:    */     try
/* 121:    */     {
/* 122:186 */       jc.getField(name).set(null, value);
/* 123:    */     }
/* 124:    */     catch (NoSuchFieldException e)
/* 125:    */     {
/* 126:189 */       throw new RuntimeException(e.toString());
/* 127:    */     }
/* 128:    */     catch (IllegalAccessException e)
/* 129:    */     {
/* 130:192 */       throw new RuntimeException(e.toString());
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public static Object invoke(Object target, int identifier, Object[] args)
/* 135:    */     throws Throwable
/* 136:    */   {
/* 137:205 */     Method[] allmethods = target.getClass().getMethods();
/* 138:206 */     int n = allmethods.length;
/* 139:207 */     String head = "_m_" + identifier;
/* 140:208 */     for (int i = 0; i < n; i++) {
/* 141:209 */       if (allmethods[i].getName().startsWith(head)) {
/* 142:    */         try
/* 143:    */         {
/* 144:211 */           return allmethods[i].invoke(target, args);
/* 145:    */         }
/* 146:    */         catch (InvocationTargetException e)
/* 147:    */         {
/* 148:213 */           throw e.getTargetException();
/* 149:    */         }
/* 150:    */         catch (IllegalAccessException e)
/* 151:    */         {
/* 152:215 */           throw new CannotInvokeException(e);
/* 153:    */         }
/* 154:    */       }
/* 155:    */     }
/* 156:219 */     throw new CannotInvokeException("cannot find a method");
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Object trapMethodcall(int identifier, Object[] args)
/* 160:    */     throws Throwable
/* 161:    */   {
/* 162:    */     try
/* 163:    */     {
/* 164:234 */       Method[] m = getReflectiveMethods();
/* 165:235 */       return m[identifier].invoke(null, args);
/* 166:    */     }
/* 167:    */     catch (InvocationTargetException e)
/* 168:    */     {
/* 169:238 */       throw e.getTargetException();
/* 170:    */     }
/* 171:    */     catch (IllegalAccessException e)
/* 172:    */     {
/* 173:241 */       throw new CannotInvokeException(e);
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public final Method[] getReflectiveMethods()
/* 178:    */   {
/* 179:250 */     if (this.methods != null) {
/* 180:251 */       return this.methods;
/* 181:    */     }
/* 182:253 */     Class baseclass = getJavaClass();
/* 183:254 */     Method[] allmethods = baseclass.getDeclaredMethods();
/* 184:255 */     int n = allmethods.length;
/* 185:256 */     int[] index = new int[n];
/* 186:257 */     int max = 0;
/* 187:258 */     for (int i = 0; i < n; i++)
/* 188:    */     {
/* 189:259 */       Method m = allmethods[i];
/* 190:260 */       String mname = m.getName();
/* 191:261 */       if (mname.startsWith("_m_"))
/* 192:    */       {
/* 193:262 */         int k = 0;
/* 194:263 */         for (int j = 3;; j++)
/* 195:    */         {
/* 196:264 */           char c = mname.charAt(j);
/* 197:265 */           if (('0' > c) || (c > '9')) {
/* 198:    */             break;
/* 199:    */           }
/* 200:266 */           k = k * 10 + c - 48;
/* 201:    */         }
/* 202:271 */         index[i] = (++k);
/* 203:272 */         if (k > max) {
/* 204:273 */           max = k;
/* 205:    */         }
/* 206:    */       }
/* 207:    */     }
/* 208:277 */     this.methods = new Method[max];
/* 209:278 */     for (int i = 0; i < n; i++) {
/* 210:279 */       if (index[i] > 0) {
/* 211:280 */         this.methods[(index[i] - 1)] = allmethods[i];
/* 212:    */       }
/* 213:    */     }
/* 214:282 */     return this.methods;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public final Method getMethod(int identifier)
/* 218:    */   {
/* 219:298 */     return getReflectiveMethods()[identifier];
/* 220:    */   }
/* 221:    */   
/* 222:    */   public final String getMethodName(int identifier)
/* 223:    */   {
/* 224:306 */     String mname = getReflectiveMethods()[identifier].getName();
/* 225:307 */     int j = 3;
/* 226:    */     for (;;)
/* 227:    */     {
/* 228:309 */       char c = mname.charAt(j++);
/* 229:310 */       if ((c < '0') || ('9' < c)) {
/* 230:    */         break;
/* 231:    */       }
/* 232:    */     }
/* 233:314 */     return mname.substring(j);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public final Class[] getParameterTypes(int identifier)
/* 237:    */   {
/* 238:323 */     return getReflectiveMethods()[identifier].getParameterTypes();
/* 239:    */   }
/* 240:    */   
/* 241:    */   public final Class getReturnType(int identifier)
/* 242:    */   {
/* 243:331 */     return getReflectiveMethods()[identifier].getReturnType();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public final int getMethodIndex(String originalName, Class[] argTypes)
/* 247:    */     throws NoSuchMethodException
/* 248:    */   {
/* 249:355 */     Method[] mthds = getReflectiveMethods();
/* 250:356 */     for (int i = 0; i < mthds.length; i++) {
/* 251:357 */       if (mthds[i] != null) {
/* 252:361 */         if ((getMethodName(i).equals(originalName)) && (Arrays.equals(argTypes, mthds[i].getParameterTypes()))) {
/* 253:363 */           return i;
/* 254:    */         }
/* 255:    */       }
/* 256:    */     }
/* 257:366 */     throw new NoSuchMethodException("Method " + originalName + " not found");
/* 258:    */   }
/* 259:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.tools.reflect.ClassMetaobject
 * JD-Core Version:    0.7.0.1
 */