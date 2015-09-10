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
/*  13:    */ import net.sourceforge.htmlunit.corejs.classfile.ClassFileWriter;
/*  14:    */ 
/*  15:    */ public class PolicySecurityController
/*  16:    */   extends SecurityController
/*  17:    */ {
/*  18: 65 */   private static final byte[] secureCallerImplBytecode = ;
/*  19: 72 */   private static final Map<CodeSource, Map<ClassLoader, SoftReference<SecureCaller>>> callers = new WeakHashMap();
/*  20:    */   
/*  21:    */   public Class<?> getStaticSecurityDomainClassInternal()
/*  22:    */   {
/*  23: 77 */     return CodeSource.class;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static abstract class SecureCaller
/*  27:    */   {
/*  28:    */     public abstract Object call(Callable paramCallable, Context paramContext, Scriptable paramScriptable1, Scriptable paramScriptable2, Object[] paramArrayOfObject);
/*  29:    */   }
/*  30:    */   
/*  31:    */   private static class Loader
/*  32:    */     extends SecureClassLoader
/*  33:    */     implements GeneratedClassLoader
/*  34:    */   {
/*  35:    */     private final CodeSource codeSource;
/*  36:    */     
/*  37:    */     Loader(ClassLoader parent, CodeSource codeSource)
/*  38:    */     {
/*  39: 87 */       super();
/*  40: 88 */       this.codeSource = codeSource;
/*  41:    */     }
/*  42:    */     
/*  43:    */     public Class<?> defineClass(String name, byte[] data)
/*  44:    */     {
/*  45: 93 */       return defineClass(name, data, 0, data.length, this.codeSource);
/*  46:    */     }
/*  47:    */     
/*  48:    */     public void linkClass(Class<?> cl)
/*  49:    */     {
/*  50: 98 */       resolveClass(cl);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public GeneratedClassLoader createClassLoader(final ClassLoader parent, final Object securityDomain)
/*  55:    */   {
/*  56:106 */     (Loader)AccessController.doPrivileged(new PrivilegedAction()
/*  57:    */     {
/*  58:    */       public Object run()
/*  59:    */       {
/*  60:111 */         return new PolicySecurityController.Loader(parent, (CodeSource)securityDomain);
/*  61:    */       }
/*  62:    */     });
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object getDynamicSecurityDomain(Object securityDomain)
/*  66:    */   {
/*  67:121 */     return securityDomain;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Object callWithDomain(Object securityDomain, final Context cx, Callable callable, Scriptable scope, Scriptable thisObj, Object[] args)
/*  71:    */   {
/*  72:131 */     final ClassLoader classLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction()
/*  73:    */     {
/*  74:    */       public Object run()
/*  75:    */       {
/*  76:134 */         return cx.getApplicationClassLoader();
/*  77:    */       }
/*  78:136 */     });
/*  79:137 */     final CodeSource codeSource = (CodeSource)securityDomain;
/*  80:    */     Map<ClassLoader, SoftReference<SecureCaller>> classLoaderMap;
/*  81:139 */     synchronized (callers)
/*  82:    */     {
/*  83:140 */       classLoaderMap = (Map)callers.get(codeSource);
/*  84:141 */       if (classLoaderMap == null)
/*  85:    */       {
/*  86:142 */         classLoaderMap = new WeakHashMap();
/*  87:143 */         callers.put(codeSource, classLoaderMap);
/*  88:    */       }
/*  89:    */     }
/*  90:    */     SecureCaller caller;
/*  91:147 */     synchronized (classLoaderMap)
/*  92:    */     {
/*  93:148 */       SoftReference<SecureCaller> ref = (SoftReference)classLoaderMap.get(classLoader);
/*  94:    */       SecureCaller caller;
/*  95:149 */       if (ref != null) {
/*  96:150 */         caller = (SecureCaller)ref.get();
/*  97:    */       } else {
/*  98:152 */         caller = null;
/*  99:    */       }
/* 100:154 */       if (caller == null) {
/* 101:    */         try
/* 102:    */         {
/* 103:160 */           caller = (SecureCaller)AccessController.doPrivileged(new PrivilegedExceptionAction()
/* 104:    */           {
/* 105:    */             public Object run()
/* 106:    */               throws Exception
/* 107:    */             {
/* 108:165 */               PolicySecurityController.Loader loader = new PolicySecurityController.Loader(classLoader, codeSource);
/* 109:    */               
/* 110:167 */               Class<?> c = loader.defineClass(PolicySecurityController.SecureCaller.class.getName() + "Impl", PolicySecurityController.secureCallerImplBytecode);
/* 111:    */               
/* 112:    */ 
/* 113:170 */               return c.newInstance();
/* 114:    */             }
/* 115:172 */           });
/* 116:173 */           classLoaderMap.put(classLoader, new SoftReference(caller));
/* 117:    */         }
/* 118:    */         catch (PrivilegedActionException ex)
/* 119:    */         {
/* 120:177 */           throw new UndeclaredThrowableException(ex.getCause());
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:181 */     return caller.call(callable, cx, scope, thisObj, args);
/* 125:    */   }
/* 126:    */   
/* 127:    */   private static byte[] loadBytecode()
/* 128:    */   {
/* 129:193 */     String secureCallerClassName = SecureCaller.class.getName();
/* 130:194 */     ClassFileWriter cfw = new ClassFileWriter(secureCallerClassName + "Impl", secureCallerClassName, "<generated>");
/* 131:    */     
/* 132:    */ 
/* 133:197 */     cfw.startMethod("<init>", "()V", (short)1);
/* 134:198 */     cfw.addALoad(0);
/* 135:199 */     cfw.addInvoke(183, secureCallerClassName, "<init>", "()V");
/* 136:    */     
/* 137:201 */     cfw.add(177);
/* 138:202 */     cfw.stopMethod((short)1);
/* 139:203 */     String callableCallSig = "Lnet/sourceforge/htmlunit/corejs/javascript/Context;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;Lnet/sourceforge/htmlunit/corejs/javascript/Scriptable;[Ljava/lang/Object;)Ljava/lang/Object;";
/* 140:    */     
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:209 */     cfw.startMethod("call", "(Lnet/sourceforge/htmlunit/corejs/javascript/Callable;" + callableCallSig, (short)17);
/* 146:213 */     for (int i = 1; i < 6; i++) {
/* 147:214 */       cfw.addALoad(i);
/* 148:    */     }
/* 149:216 */     cfw.addInvoke(185, "net/sourceforge/htmlunit/corejs/javascript/Callable", "call", "(" + callableCallSig);
/* 150:    */     
/* 151:    */ 
/* 152:219 */     cfw.add(176);
/* 153:220 */     cfw.stopMethod((short)6);
/* 154:221 */     return cfw.toByteArray();
/* 155:    */   }
/* 156:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.PolicySecurityController
 * JD-Core Version:    0.7.0.1
 */