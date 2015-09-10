/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ 
/*   5:    */ public class InterfaceAdapter
/*   6:    */ {
/*   7:    */   private final Object proxyHelper;
/*   8:    */   
/*   9:    */   static Object create(Context cx, Class<?> cl, Callable function)
/*  10:    */   {
/*  11: 61 */     if (!cl.isInterface()) {
/*  12: 61 */       throw new IllegalArgumentException();
/*  13:    */     }
/*  14: 63 */     Scriptable topScope = ScriptRuntime.getTopCallScope(cx);
/*  15: 64 */     ClassCache cache = ClassCache.get(topScope);
/*  16:    */     
/*  17: 66 */     InterfaceAdapter adapter = (InterfaceAdapter)cache.getInterfaceAdapter(cl);
/*  18: 67 */     ContextFactory cf = cx.getFactory();
/*  19: 68 */     if (adapter == null)
/*  20:    */     {
/*  21: 69 */       Method[] methods = cl.getMethods();
/*  22: 70 */       if (methods.length == 0) {
/*  23: 71 */         throw Context.reportRuntimeError2("msg.no.empty.interface.conversion", String.valueOf(function), cl.getClass().getName());
/*  24:    */       }
/*  25: 76 */       boolean canCallFunction = false;
/*  26:    */       
/*  27: 78 */       Class<?>[] argTypes = methods[0].getParameterTypes();
/*  28: 80 */       for (int i = 1; i != methods.length; i++)
/*  29:    */       {
/*  30: 81 */         Class<?>[] types2 = methods[i].getParameterTypes();
/*  31: 82 */         if (types2.length != argTypes.length) {
/*  32:    */           break label164;
/*  33:    */         }
/*  34: 85 */         for (int j = 0; j != argTypes.length; j++) {
/*  35: 86 */           if (types2[j] != argTypes[j]) {
/*  36:    */             break label164;
/*  37:    */           }
/*  38:    */         }
/*  39:    */       }
/*  40: 91 */       canCallFunction = true;
/*  41:    */       label164:
/*  42: 93 */       if (!canCallFunction) {
/*  43: 94 */         throw Context.reportRuntimeError2("msg.no.function.interface.conversion", String.valueOf(function), cl.getClass().getName());
/*  44:    */       }
/*  45: 99 */       adapter = new InterfaceAdapter(cf, cl);
/*  46:100 */       cache.cacheInterfaceAdapter(cl, adapter);
/*  47:    */     }
/*  48:102 */     return VMBridge.instance.newInterfaceProxy(adapter.proxyHelper, cf, adapter, function, topScope);
/*  49:    */   }
/*  50:    */   
/*  51:    */   private InterfaceAdapter(ContextFactory cf, Class<?> cl)
/*  52:    */   {
/*  53:108 */     this.proxyHelper = VMBridge.instance.getInterfaceProxyHelper(cf, new Class[] { cl });
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object invoke(ContextFactory cf, final Object target, final Scriptable topScope, final Method method, final Object[] args)
/*  57:    */   {
/*  58:119 */     ContextAction action = new ContextAction()
/*  59:    */     {
/*  60:    */       public Object run(Context cx)
/*  61:    */       {
/*  62:122 */         return InterfaceAdapter.this.invokeImpl(cx, target, topScope, method, args);
/*  63:    */       }
/*  64:124 */     };
/*  65:125 */     return cf.call(action);
/*  66:    */   }
/*  67:    */   
/*  68:    */   Object invokeImpl(Context cx, Object target, Scriptable topScope, Method method, Object[] args)
/*  69:    */   {
/*  70:134 */     int N = args == null ? 0 : args.length;
/*  71:    */     
/*  72:136 */     Callable function = (Callable)target;
/*  73:137 */     Scriptable thisObj = topScope;
/*  74:138 */     Object[] jsargs = new Object[N + 1];
/*  75:139 */     jsargs[N] = method.getName();
/*  76:140 */     if (N != 0)
/*  77:    */     {
/*  78:141 */       WrapFactory wf = cx.getWrapFactory();
/*  79:142 */       for (int i = 0; i != N; i++) {
/*  80:143 */         jsargs[i] = wf.wrap(cx, topScope, args[i], null);
/*  81:    */       }
/*  82:    */     }
/*  83:147 */     Object result = function.call(cx, topScope, thisObj, jsargs);
/*  84:148 */     Class<?> javaResultType = method.getReturnType();
/*  85:149 */     if (javaResultType == Void.TYPE) {
/*  86:150 */       result = null;
/*  87:    */     } else {
/*  88:152 */       result = Context.jsToJava(result, javaResultType);
/*  89:    */     }
/*  90:154 */     return result;
/*  91:    */   }
/*  92:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.InterfaceAdapter
 * JD-Core Version:    0.7.0.1
 */