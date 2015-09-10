/*   1:    */ package javassist.util.proxy;
/*   2:    */ 
/*   3:    */ import java.io.InvalidClassException;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ 
/*   7:    */ public class RuntimeSupport
/*   8:    */ {
/*   9: 30 */   public static MethodHandler default_interceptor = new DefaultMethodHandler();
/*  10:    */   
/*  11:    */   static class DefaultMethodHandler
/*  12:    */     implements MethodHandler, Serializable
/*  13:    */   {
/*  14:    */     public Object invoke(Object self, Method m, Method proceed, Object[] args)
/*  15:    */       throws Exception
/*  16:    */     {
/*  17: 37 */       return proceed.invoke(self, args);
/*  18:    */     }
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static void find2Methods(Object self, String superMethod, String thisMethod, int index, String desc, Method[] methods)
/*  22:    */   {
/*  23: 52 */     synchronized (methods)
/*  24:    */     {
/*  25: 53 */       if (methods[index] == null)
/*  26:    */       {
/*  27: 54 */         methods[(index + 1)] = (thisMethod == null ? null : findMethod(self, thisMethod, desc));
/*  28:    */         
/*  29: 56 */         methods[index] = findSuperMethod(self, superMethod, desc);
/*  30:    */       }
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static Method findMethod(Object self, String name, String desc)
/*  35:    */   {
/*  36: 68 */     Method m = findMethod2(self.getClass(), name, desc);
/*  37: 69 */     if (m == null) {
/*  38: 70 */       error(self, name, desc);
/*  39:    */     }
/*  40: 72 */     return m;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static Method findSuperMethod(Object self, String name, String desc)
/*  44:    */   {
/*  45: 82 */     Class clazz = self.getClass();
/*  46: 83 */     Method m = findSuperMethod2(clazz.getSuperclass(), name, desc);
/*  47: 84 */     if (m == null) {
/*  48: 85 */       m = searchInterfaces(clazz, name, desc);
/*  49:    */     }
/*  50: 87 */     if (m == null) {
/*  51: 88 */       error(self, name, desc);
/*  52:    */     }
/*  53: 90 */     return m;
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static void error(Object self, String name, String desc)
/*  57:    */   {
/*  58: 94 */     throw new RuntimeException("not found " + name + ":" + desc + " in " + self.getClass().getName());
/*  59:    */   }
/*  60:    */   
/*  61:    */   private static Method findSuperMethod2(Class clazz, String name, String desc)
/*  62:    */   {
/*  63: 99 */     Method m = findMethod2(clazz, name, desc);
/*  64:100 */     if (m != null) {
/*  65:101 */       return m;
/*  66:    */     }
/*  67:103 */     Class superClass = clazz.getSuperclass();
/*  68:104 */     if (superClass != null)
/*  69:    */     {
/*  70:105 */       m = findSuperMethod2(superClass, name, desc);
/*  71:106 */       if (m != null) {
/*  72:107 */         return m;
/*  73:    */       }
/*  74:    */     }
/*  75:110 */     return searchInterfaces(clazz, name, desc);
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static Method searchInterfaces(Class clazz, String name, String desc)
/*  79:    */   {
/*  80:114 */     Method m = null;
/*  81:115 */     Class[] interfaces = clazz.getInterfaces();
/*  82:116 */     for (int i = 0; i < interfaces.length; i++)
/*  83:    */     {
/*  84:117 */       m = findSuperMethod2(interfaces[i], name, desc);
/*  85:118 */       if (m != null) {
/*  86:119 */         return m;
/*  87:    */       }
/*  88:    */     }
/*  89:122 */     return m;
/*  90:    */   }
/*  91:    */   
/*  92:    */   private static Method findMethod2(Class clazz, String name, String desc)
/*  93:    */   {
/*  94:126 */     Method[] methods = SecurityActions.getDeclaredMethods(clazz);
/*  95:127 */     int n = methods.length;
/*  96:128 */     for (int i = 0; i < n; i++) {
/*  97:129 */       if ((methods[i].getName().equals(name)) && (makeDescriptor(methods[i]).equals(desc))) {
/*  98:131 */         return methods[i];
/*  99:    */       }
/* 100:    */     }
/* 101:133 */     return null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static String makeDescriptor(Method m)
/* 105:    */   {
/* 106:140 */     Class[] params = m.getParameterTypes();
/* 107:141 */     return makeDescriptor(params, m.getReturnType());
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static String makeDescriptor(Class[] params, Class retType)
/* 111:    */   {
/* 112:151 */     StringBuffer sbuf = new StringBuffer();
/* 113:152 */     sbuf.append('(');
/* 114:153 */     for (int i = 0; i < params.length; i++) {
/* 115:154 */       makeDesc(sbuf, params[i]);
/* 116:    */     }
/* 117:156 */     sbuf.append(')');
/* 118:157 */     makeDesc(sbuf, retType);
/* 119:158 */     return sbuf.toString();
/* 120:    */   }
/* 121:    */   
/* 122:    */   private static void makeDesc(StringBuffer sbuf, Class type)
/* 123:    */   {
/* 124:162 */     if (type.isArray())
/* 125:    */     {
/* 126:163 */       sbuf.append('[');
/* 127:164 */       makeDesc(sbuf, type.getComponentType());
/* 128:    */     }
/* 129:166 */     else if (type.isPrimitive())
/* 130:    */     {
/* 131:167 */       if (type == Void.TYPE) {
/* 132:168 */         sbuf.append('V');
/* 133:169 */       } else if (type == Integer.TYPE) {
/* 134:170 */         sbuf.append('I');
/* 135:171 */       } else if (type == Byte.TYPE) {
/* 136:172 */         sbuf.append('B');
/* 137:173 */       } else if (type == Long.TYPE) {
/* 138:174 */         sbuf.append('J');
/* 139:175 */       } else if (type == Double.TYPE) {
/* 140:176 */         sbuf.append('D');
/* 141:177 */       } else if (type == Float.TYPE) {
/* 142:178 */         sbuf.append('F');
/* 143:179 */       } else if (type == Character.TYPE) {
/* 144:180 */         sbuf.append('C');
/* 145:181 */       } else if (type == Short.TYPE) {
/* 146:182 */         sbuf.append('S');
/* 147:183 */       } else if (type == Boolean.TYPE) {
/* 148:184 */         sbuf.append('Z');
/* 149:    */       } else {
/* 150:186 */         throw new RuntimeException("bad type: " + type.getName());
/* 151:    */       }
/* 152:    */     }
/* 153:    */     else
/* 154:    */     {
/* 155:189 */       sbuf.append('L').append(type.getName().replace('.', '/')).append(';');
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static SerializedProxy makeSerializedProxy(Object proxy)
/* 160:    */     throws InvalidClassException
/* 161:    */   {
/* 162:203 */     Class clazz = proxy.getClass();
/* 163:    */     
/* 164:205 */     MethodHandler methodHandler = null;
/* 165:206 */     if ((proxy instanceof ProxyObject)) {
/* 166:207 */       methodHandler = ((ProxyObject)proxy).getHandler();
/* 167:    */     }
/* 168:209 */     return new SerializedProxy(clazz, ProxyFactory.getFilterSignature(clazz), methodHandler);
/* 169:    */   }
/* 170:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.RuntimeSupport
 * JD-Core Version:    0.7.0.1
 */