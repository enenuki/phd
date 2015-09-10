/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript.jdk13;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.AccessibleObject;
/*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationHandler;
/*   6:    */ import java.lang.reflect.InvocationTargetException;
/*   7:    */ import java.lang.reflect.Member;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ import java.lang.reflect.Proxy;
/*  10:    */ import net.sourceforge.htmlunit.corejs.javascript.Context;
/*  11:    */ import net.sourceforge.htmlunit.corejs.javascript.ContextFactory;
/*  12:    */ import net.sourceforge.htmlunit.corejs.javascript.InterfaceAdapter;
/*  13:    */ import net.sourceforge.htmlunit.corejs.javascript.Kit;
/*  14:    */ import net.sourceforge.htmlunit.corejs.javascript.Scriptable;
/*  15:    */ import net.sourceforge.htmlunit.corejs.javascript.VMBridge;
/*  16:    */ 
/*  17:    */ public class VMBridge_jdk13
/*  18:    */   extends VMBridge
/*  19:    */ {
/*  20: 52 */   private ThreadLocal<Object[]> contextLocal = new ThreadLocal();
/*  21:    */   
/*  22:    */   protected Object getThreadContextHelper()
/*  23:    */   {
/*  24: 66 */     Object[] storage = (Object[])this.contextLocal.get();
/*  25: 67 */     if (storage == null)
/*  26:    */     {
/*  27: 68 */       storage = new Object[1];
/*  28: 69 */       this.contextLocal.set(storage);
/*  29:    */     }
/*  30: 71 */     return storage;
/*  31:    */   }
/*  32:    */   
/*  33:    */   protected Context getContext(Object contextHelper)
/*  34:    */   {
/*  35: 77 */     Object[] storage = (Object[])contextHelper;
/*  36: 78 */     return (Context)storage[0];
/*  37:    */   }
/*  38:    */   
/*  39:    */   protected void setContext(Object contextHelper, Context cx)
/*  40:    */   {
/*  41: 84 */     Object[] storage = (Object[])contextHelper;
/*  42: 85 */     storage[0] = cx;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected ClassLoader getCurrentThreadClassLoader()
/*  46:    */   {
/*  47: 91 */     return Thread.currentThread().getContextClassLoader();
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected boolean tryToMakeAccessible(Object accessibleObject)
/*  51:    */   {
/*  52: 97 */     if (!(accessibleObject instanceof AccessibleObject)) {
/*  53: 98 */       return false;
/*  54:    */     }
/*  55:100 */     AccessibleObject accessible = (AccessibleObject)accessibleObject;
/*  56:101 */     if (accessible.isAccessible()) {
/*  57:102 */       return true;
/*  58:    */     }
/*  59:    */     try
/*  60:    */     {
/*  61:105 */       accessible.setAccessible(true);
/*  62:    */     }
/*  63:    */     catch (Exception ex) {}
/*  64:108 */     return accessible.isAccessible();
/*  65:    */   }
/*  66:    */   
/*  67:    */   protected Object getInterfaceProxyHelper(ContextFactory cf, Class<?>[] interfaces)
/*  68:    */   {
/*  69:117 */     ClassLoader loader = interfaces[0].getClassLoader();
/*  70:118 */     Class<?> cl = Proxy.getProxyClass(loader, interfaces);
/*  71:    */     Constructor<?> c;
/*  72:    */     try
/*  73:    */     {
/*  74:121 */       c = cl.getConstructor(new Class[] { InvocationHandler.class });
/*  75:    */     }
/*  76:    */     catch (NoSuchMethodException ex)
/*  77:    */     {
/*  78:124 */       throw Kit.initCause(new IllegalStateException(), ex);
/*  79:    */     }
/*  80:126 */     return c;
/*  81:    */   }
/*  82:    */   
/*  83:    */   protected Object newInterfaceProxy(Object proxyHelper, final ContextFactory cf, final InterfaceAdapter adapter, final Object target, final Scriptable topScope)
/*  84:    */   {
/*  85:136 */     Constructor<?> c = (Constructor)proxyHelper;
/*  86:    */     
/*  87:138 */     InvocationHandler handler = new InvocationHandler()
/*  88:    */     {
/*  89:    */       public Object invoke(Object proxy, Method method, Object[] args)
/*  90:    */       {
/*  91:143 */         return adapter.invoke(cf, target, topScope, method, args);
/*  92:    */       }
/*  93:    */     };
/*  94:    */     Object proxy;
/*  95:    */     try
/*  96:    */     {
/*  97:148 */       proxy = c.newInstance(new Object[] { handler });
/*  98:    */     }
/*  99:    */     catch (InvocationTargetException ex)
/* 100:    */     {
/* 101:150 */       throw Context.throwAsScriptRuntimeEx(ex);
/* 102:    */     }
/* 103:    */     catch (IllegalAccessException ex)
/* 104:    */     {
/* 105:153 */       throw Kit.initCause(new IllegalStateException(), ex);
/* 106:    */     }
/* 107:    */     catch (InstantiationException ex)
/* 108:    */     {
/* 109:156 */       throw Kit.initCause(new IllegalStateException(), ex);
/* 110:    */     }
/* 111:158 */     return proxy;
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected boolean isVarArgs(Member member)
/* 115:    */   {
/* 116:163 */     return false;
/* 117:    */   }
/* 118:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.jdk13.VMBridge_jdk13
 * JD-Core Version:    0.7.0.1
 */