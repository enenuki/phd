/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.ref.SoftReference;
/*   4:    */ import java.lang.reflect.UndeclaredThrowableException;
/*   5:    */ import java.security.AccessController;
/*   6:    */ import java.security.CodeSource;
/*   7:    */ import java.security.PrivilegedAction;
/*   8:    */ import java.security.PrivilegedActionException;
/*   9:    */ import java.security.PrivilegedExceptionAction;
/*  10:    */ import java.security.SecureClassLoader;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.WeakHashMap;
/*  13:    */ 
/*  14:    */ public abstract class SecureCaller
/*  15:    */ {
/*  16: 57 */   private static final byte[] secureCallerImplBytecode = ;
/*  17: 64 */   private static final Map<CodeSource, Map<ClassLoader, SoftReference<SecureCaller>>> callers = new WeakHashMap();
/*  18:    */   
/*  19:    */   public abstract Object call(Callable paramCallable, Context paramContext, Scriptable paramScriptable1, Scriptable paramScriptable2, Object[] paramArrayOfObject);
/*  20:    */   
/*  21:    */   static Object callSecurely(final CodeSource codeSource, Callable callable, Context cx, Scriptable scope, Scriptable thisObj, Object[] args)
/*  22:    */   {
/*  23: 77 */     Thread thread = Thread.currentThread();
/*  24:    */     
/*  25:    */ 
/*  26: 80 */     ClassLoader classLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  27:    */     {
/*  28:    */       public Object run()
/*  29:    */       {
/*  30: 83 */         return this.val$thread.getContextClassLoader();
/*  31:    */       }
/*  32:    */     });
/*  33:    */     Map<ClassLoader, SoftReference<SecureCaller>> classLoaderMap;
/*  34: 87 */     synchronized (callers)
/*  35:    */     {
/*  36: 89 */       classLoaderMap = (Map)callers.get(codeSource);
/*  37: 90 */       if (classLoaderMap == null)
/*  38:    */       {
/*  39: 92 */         classLoaderMap = new WeakHashMap();
/*  40: 93 */         callers.put(codeSource, classLoaderMap);
/*  41:    */       }
/*  42:    */     }
/*  43:    */     SecureCaller caller;
/*  44: 97 */     synchronized (classLoaderMap)
/*  45:    */     {
/*  46: 99 */       SoftReference<SecureCaller> ref = (SoftReference)classLoaderMap.get(classLoader);
/*  47:    */       SecureCaller caller;
/*  48:100 */       if (ref != null) {
/*  49:101 */         caller = (SecureCaller)ref.get();
/*  50:    */       } else {
/*  51:103 */         caller = null;
/*  52:    */       }
/*  53:105 */       if (caller == null) {
/*  54:    */         try
/*  55:    */         {
/*  56:110 */           caller = (SecureCaller)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*  57:    */           {
/*  58:    */             public Object run()
/*  59:    */               throws Exception
/*  60:    */             {
/*  61:116 */               Class<?> thisClass = getClass();
/*  62:    */               ClassLoader effectiveClassLoader;
/*  63:    */               ClassLoader effectiveClassLoader;
/*  64:117 */               if (this.val$classLoader.loadClass(thisClass.getName()) != thisClass) {
/*  65:118 */                 effectiveClassLoader = thisClass.getClassLoader();
/*  66:    */               } else {
/*  67:120 */                 effectiveClassLoader = this.val$classLoader;
/*  68:    */               }
/*  69:122 */               SecureCaller.SecureClassLoaderImpl secCl = new SecureCaller.SecureClassLoaderImpl(effectiveClassLoader);
/*  70:    */               
/*  71:124 */               Class<?> c = secCl.defineAndLinkClass(SecureCaller.class.getName() + "Impl", SecureCaller.secureCallerImplBytecode, codeSource);
/*  72:    */               
/*  73:    */ 
/*  74:127 */               return c.newInstance();
/*  75:    */             }
/*  76:129 */           });
/*  77:130 */           classLoaderMap.put(classLoader, new SoftReference(caller));
/*  78:    */         }
/*  79:    */         catch (PrivilegedActionException ex)
/*  80:    */         {
/*  81:134 */           throw new UndeclaredThrowableException(ex.getCause());
/*  82:    */         }
/*  83:    */       }
/*  84:    */     }
/*  85:138 */     return caller.call(callable, cx, scope, thisObj, args);
/*  86:    */   }
/*  87:    */   
/*  88:    */   private static class SecureClassLoaderImpl
/*  89:    */     extends SecureClassLoader
/*  90:    */   {
/*  91:    */     SecureClassLoaderImpl(ClassLoader parent)
/*  92:    */     {
/*  93:145 */       super();
/*  94:    */     }
/*  95:    */     
/*  96:    */     Class<?> defineAndLinkClass(String name, byte[] bytes, CodeSource cs)
/*  97:    */     {
/*  98:150 */       Class<?> cl = defineClass(name, bytes, 0, bytes.length, cs);
/*  99:151 */       resolveClass(cl);
/* 100:152 */       return cl;
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private static byte[] loadBytecode()
/* 105:    */   {
/* 106:158 */     (byte[])AccessController.doPrivileged(new PrivilegedAction()
/* 107:    */     {
/* 108:    */       public Object run()
/* 109:    */       {
/* 110:162 */         return SecureCaller.access$100();
/* 111:    */       }
/* 112:    */     });
/* 113:    */   }
/* 114:    */   
/* 115:    */   /* Error */
/* 116:    */   private static byte[] loadBytecodePrivileged()
/* 117:    */   {
/* 118:    */     // Byte code:
/* 119:    */     //   0: ldc_w 17
/* 120:    */     //   3: ldc 30
/* 121:    */     //   5: invokevirtual 31	java/lang/Class:getResource	(Ljava/lang/String;)Ljava/net/URL;
/* 122:    */     //   8: astore_0
/* 123:    */     //   9: aload_0
/* 124:    */     //   10: invokevirtual 32	java/net/URL:openStream	()Ljava/io/InputStream;
/* 125:    */     //   13: astore_1
/* 126:    */     //   14: new 33	java/io/ByteArrayOutputStream
/* 127:    */     //   17: dup
/* 128:    */     //   18: invokespecial 34	java/io/ByteArrayOutputStream:<init>	()V
/* 129:    */     //   21: astore_2
/* 130:    */     //   22: aload_1
/* 131:    */     //   23: invokevirtual 35	java/io/InputStream:read	()I
/* 132:    */     //   26: istore_3
/* 133:    */     //   27: iload_3
/* 134:    */     //   28: iconst_m1
/* 135:    */     //   29: if_icmpne +16 -> 45
/* 136:    */     //   32: aload_2
/* 137:    */     //   33: invokevirtual 36	java/io/ByteArrayOutputStream:toByteArray	()[B
/* 138:    */     //   36: astore 4
/* 139:    */     //   38: aload_1
/* 140:    */     //   39: invokevirtual 37	java/io/InputStream:close	()V
/* 141:    */     //   42: aload 4
/* 142:    */     //   44: areturn
/* 143:    */     //   45: aload_2
/* 144:    */     //   46: iload_3
/* 145:    */     //   47: invokevirtual 38	java/io/ByteArrayOutputStream:write	(I)V
/* 146:    */     //   50: goto -28 -> 22
/* 147:    */     //   53: astore 5
/* 148:    */     //   55: aload_1
/* 149:    */     //   56: invokevirtual 37	java/io/InputStream:close	()V
/* 150:    */     //   59: aload 5
/* 151:    */     //   61: athrow
/* 152:    */     //   62: astore_1
/* 153:    */     //   63: new 23	java/lang/reflect/UndeclaredThrowableException
/* 154:    */     //   66: dup
/* 155:    */     //   67: aload_1
/* 156:    */     //   68: invokespecial 25	java/lang/reflect/UndeclaredThrowableException:<init>	(Ljava/lang/Throwable;)V
/* 157:    */     //   71: athrow
/* 158:    */     // Line number table:
/* 159:    */     //   Java source line #169	-> byte code offset #0
/* 160:    */     //   Java source line #172	-> byte code offset #9
/* 161:    */     //   Java source line #175	-> byte code offset #14
/* 162:    */     //   Java source line #178	-> byte code offset #22
/* 163:    */     //   Java source line #179	-> byte code offset #27
/* 164:    */     //   Java source line #181	-> byte code offset #32
/* 165:    */     //   Java source line #188	-> byte code offset #38
/* 166:    */     //   Java source line #183	-> byte code offset #45
/* 167:    */     //   Java source line #184	-> byte code offset #50
/* 168:    */     //   Java source line #188	-> byte code offset #53
/* 169:    */     //   Java source line #191	-> byte code offset #62
/* 170:    */     //   Java source line #193	-> byte code offset #63
/* 171:    */     // Local variable table:
/* 172:    */     //   start	length	slot	name	signature
/* 173:    */     //   8	2	0	url	java.net.URL
/* 174:    */     //   13	43	1	in	java.io.InputStream
/* 175:    */     //   62	6	1	e	java.io.IOException
/* 176:    */     //   21	25	2	bout	java.io.ByteArrayOutputStream
/* 177:    */     //   26	21	3	r	int
/* 178:    */     //   53	7	5	localObject	Object
/* 179:    */     // Exception table:
/* 180:    */     //   from	to	target	type
/* 181:    */     //   14	38	53	finally
/* 182:    */     //   45	55	53	finally
/* 183:    */     //   9	42	62	java/io/IOException
/* 184:    */     //   45	62	62	java/io/IOException
/* 185:    */   }
/* 186:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.SecureCaller
 * JD-Core Version:    0.7.0.1
 */