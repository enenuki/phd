/*   1:    */ package javassist.util.proxy;
/*   2:    */ 
/*   3:    */ import java.io.BufferedOutputStream;
/*   4:    */ import java.io.ByteArrayOutputStream;
/*   5:    */ import java.io.DataOutputStream;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.lang.reflect.InvocationTargetException;
/*  10:    */ import java.lang.reflect.Method;
/*  11:    */ import java.security.ProtectionDomain;
/*  12:    */ import javassist.CannotCompileException;
/*  13:    */ import javassist.bytecode.ClassFile;
/*  14:    */ 
/*  15:    */ public class FactoryHelper
/*  16:    */ {
/*  17:    */   private static Method defineClass1;
/*  18:    */   private static Method defineClass2;
/*  19:    */   
/*  20:    */   static
/*  21:    */   {
/*  22:    */     try
/*  23:    */     {
/*  24: 41 */       Class cl = Class.forName("java.lang.ClassLoader");
/*  25: 42 */       defineClass1 = SecurityActions.getDeclaredMethod(cl, "defineClass", new Class[] { String.class, new byte[0].getClass(), Integer.TYPE, Integer.TYPE });
/*  26:    */       
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31: 48 */       defineClass2 = SecurityActions.getDeclaredMethod(cl, "defineClass", new Class[] { String.class, new byte[0].getClass(), Integer.TYPE, Integer.TYPE, ProtectionDomain.class });
/*  32:    */     }
/*  33:    */     catch (Exception e)
/*  34:    */     {
/*  35: 55 */       throw new RuntimeException("cannot initialize");
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static final int typeIndex(Class type)
/*  40:    */   {
/*  41: 65 */     Class[] list = primitiveTypes;
/*  42: 66 */     int n = list.length;
/*  43: 67 */     for (int i = 0; i < n; i++) {
/*  44: 68 */       if (list[i] == type) {
/*  45: 69 */         return i;
/*  46:    */       }
/*  47:    */     }
/*  48: 71 */     throw new RuntimeException("bad type:" + type.getName());
/*  49:    */   }
/*  50:    */   
/*  51: 77 */   public static final Class[] primitiveTypes = { Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE };
/*  52: 85 */   public static final String[] wrapperTypes = { "java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Void" };
/*  53: 94 */   public static final String[] wrapperDesc = { "(Z)V", "(B)V", "(C)V", "(S)V", "(I)V", "(J)V", "(F)V", "(D)V" };
/*  54:105 */   public static final String[] unwarpMethods = { "booleanValue", "byteValue", "charValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue" };
/*  55:114 */   public static final String[] unwrapDesc = { "()Z", "()B", "()C", "()S", "()I", "()J", "()F", "()D" };
/*  56:122 */   public static final int[] dataSize = { 1, 1, 1, 1, 1, 2, 1, 2 };
/*  57:    */   
/*  58:    */   public static Class toClass(ClassFile cf, ClassLoader loader)
/*  59:    */     throws CannotCompileException
/*  60:    */   {
/*  61:136 */     return toClass(cf, loader, null);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static Class toClass(ClassFile cf, ClassLoader loader, ProtectionDomain domain)
/*  65:    */     throws CannotCompileException
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:149 */       byte[] b = toBytecode(cf);
/*  70:    */       Object[] args;
/*  71:    */       Method method;
/*  72:    */       Object[] args;
/*  73:152 */       if (domain == null)
/*  74:    */       {
/*  75:153 */         Method method = defineClass1;
/*  76:154 */         args = new Object[] { cf.getName(), b, new Integer(0), new Integer(b.length) };
/*  77:    */       }
/*  78:    */       else
/*  79:    */       {
/*  80:158 */         method = defineClass2;
/*  81:159 */         args = new Object[] { cf.getName(), b, new Integer(0), new Integer(b.length), domain };
/*  82:    */       }
/*  83:163 */       return toClass2(method, loader, args);
/*  84:    */     }
/*  85:    */     catch (RuntimeException e)
/*  86:    */     {
/*  87:166 */       throw e;
/*  88:    */     }
/*  89:    */     catch (InvocationTargetException e)
/*  90:    */     {
/*  91:169 */       throw new CannotCompileException(e.getTargetException());
/*  92:    */     }
/*  93:    */     catch (Exception e)
/*  94:    */     {
/*  95:172 */       throw new CannotCompileException(e);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private static synchronized Class toClass2(Method method, ClassLoader loader, Object[] args)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102:180 */     SecurityActions.setAccessible(method, true);
/* 103:181 */     Class clazz = (Class)method.invoke(loader, args);
/* 104:182 */     SecurityActions.setAccessible(method, false);
/* 105:183 */     return clazz;
/* 106:    */   }
/* 107:    */   
/* 108:    */   private static byte[] toBytecode(ClassFile cf)
/* 109:    */     throws IOException
/* 110:    */   {
/* 111:187 */     ByteArrayOutputStream barray = new ByteArrayOutputStream();
/* 112:188 */     DataOutputStream out = new DataOutputStream(barray);
/* 113:    */     try
/* 114:    */     {
/* 115:190 */       cf.write(out);
/* 116:    */     }
/* 117:    */     finally
/* 118:    */     {
/* 119:193 */       out.close();
/* 120:    */     }
/* 121:196 */     return barray.toByteArray();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static void writeFile(ClassFile cf, String directoryName)
/* 125:    */     throws CannotCompileException
/* 126:    */   {
/* 127:    */     try
/* 128:    */     {
/* 129:205 */       writeFile0(cf, directoryName);
/* 130:    */     }
/* 131:    */     catch (IOException e)
/* 132:    */     {
/* 133:208 */       throw new CannotCompileException(e);
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   private static void writeFile0(ClassFile cf, String directoryName)
/* 138:    */     throws CannotCompileException, IOException
/* 139:    */   {
/* 140:214 */     String classname = cf.getName();
/* 141:215 */     String filename = directoryName + File.separatorChar + classname.replace('.', File.separatorChar) + ".class";
/* 142:    */     
/* 143:217 */     int pos = filename.lastIndexOf(File.separatorChar);
/* 144:218 */     if (pos > 0)
/* 145:    */     {
/* 146:219 */       String dir = filename.substring(0, pos);
/* 147:220 */       if (!dir.equals(".")) {
/* 148:221 */         new File(dir).mkdirs();
/* 149:    */       }
/* 150:    */     }
/* 151:224 */     DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
/* 152:    */     try
/* 153:    */     {
/* 154:227 */       cf.write(out);
/* 155:    */     }
/* 156:    */     catch (IOException e)
/* 157:    */     {
/* 158:230 */       throw e;
/* 159:    */     }
/* 160:    */     finally
/* 161:    */     {
/* 162:233 */       out.close();
/* 163:    */     }
/* 164:    */   }
/* 165:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.util.proxy.FactoryHelper
 * JD-Core Version:    0.7.0.1
 */